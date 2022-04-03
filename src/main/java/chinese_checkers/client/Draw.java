package chinese_checkers.client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;

import chinese_checkers.serwer.Board;

/**
 * 
 * @author Szymon
 * 
 */
public class Draw extends JFrame implements MouseListener{
private Board currentBoard = null;

private PrintWriter out;
private Socket socket;


	/**
	 * Initializing board
	 * @param socket client socket for sending the data between gui and client class
	 */
	public Draw(Board gameBoard, Socket socket, int playerid) 
	{     
		this.socket = socket;
		try
		{
		 this.out = new PrintWriter(socket.getOutputStream());
		}
		catch (IOException e)
		{
	     e.printStackTrace();
		}
		this.setTitle("Player " + playerid);
		this.setLayout(new BorderLayout());
		this.currentBoard = gameBoard;
	    setSize(1000, 1000);
	    this.setBounds(0,0, 800, 1000);
	    addMouseListener(this);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setAlwaysOnTop(true);
	    this.setResizable(false);
	    this.setVisible(true);	
	}
	/**
	 * painting the board
	 */
    public void paint(Graphics g)
    {
       // drawBoard(g, this.currentBoard);
        drawPlayers(g, this.currentBoard);
    }
    /**
	 * painting the board
	 */
    public void drawPlayers(Graphics g, Board gameboard) {
    	Image img= createImage(getSize().width, getSize().height);
        Graphics2D g2= (Graphics2D)img.getGraphics();
        for(int i=0;i<17;i++)
        {
            for(int j=0; j<13;j++)
            {
                int r = 50;
                switch(gameboard.getBoard()[i][j].getPlayerId())
                {
                    
                	case -1:
                        g2.setColor(Color.black);
                        g2.fillOval(j+r+j*r+(i%2)*r/2, i+r+i*r, r , r); 
                        break;
                    case 0:
                        g2.setColor(Color.white);
                        g2.fillOval(j+r+j*r+(i%2)*r/2, i+r+i*r, r , r);                                         
                        break;  
                    case 1:
                        g2.setColor(Color.red);                         
                        g2.fillOval(j+r+j*r+(i%2)*r/2, i+r+i*r, r , r); 
                        break;  
                    case 2:
                        g2.setColor(Color.pink);
                        g2.fillOval(j+r+j*r+(i%2)*r/2, i+r+i*r, r , r);                                   
                        break;
                    case 3:
                        g2.setColor(Color.green);
                        g2.fillOval(j+r+j*r+(i%2)*r/2, i+r+i*r, r , r); 
                        break;
                    case 4:
                        g2.setColor(Color.blue);
                        g2.fillOval(j+r+j*r+(i%2)*r/2, i+r+i*r, r , r);                                         
                        break;  
                    case 5:
                        g2.setColor(Color.yellow);
                        g2.fillOval(j+r+j*r+(i%2)*r/2, i+r+i*r, r , r);                                           
                        break;  
                    case 6:
                        g2.setColor(Color.CYAN);
                        g2.fillOval(j+r+j*r+(i%2)*r/2, i+r+i*r, r , r);                                          
                        break; 

            }
       }
     }
        g.drawImage(img, 0, 0, this);
    }
   
    
    public int  fieldC = 0;
    public	String xold = "x";
	public String yold = "y";
	/**
	 * method for checking player moving pieces on the board
	 * @param x x-cord of mouse input
	 * @param y	y-cord of mouse input
	 */
    public void Field(int x, int y)
      {   	

    	if(((y/50 - 1) % 2 )  ==  1  ) {
    		 x = x - 25;   		 		 
    		 String x1 = Integer.toString(((x/50) - 1));
    		 String y1 = Integer.toString((y/50 - 1));
    		 //System.out.println(((x/50) - 1) +" "+ ((y/50 - 1)) );
       		 if(fieldC == 1){  			 
    			 fieldC = 0;
    			 //text("mouse ".concat(xold + " ").concat(yold + " ").concat(x1 + " ").concat(y1));
    			 out.println("mouse ".concat(xold + " ").concat(yold + " ").concat(x1 + " ").concat(y1));
				 out.flush(); 
       		 }
    		 else {
    			 fieldC = 1;
    			 xold = x1;
    			 yold = y1;
    			 return;
    		 }
    		 
    	 }
    	 else {
    		 String x1 = Integer.toString(((x/50) - 1));
    		 String y1 = Integer.toString((y/50 - 1));
    		 //System.out.println(((x/50) - 1) +" "+ ((y/50 - 1)) );
    		 if(fieldC == 1){  			 
    			 fieldC = 0;
    			 //System.out.print(x1.concat(y1).concat(xold).concat(yold)+ "\n");
    			 //text("mouse ".concat(xold + " ").concat(yold + " ").concat(x1 + " ").concat(y1));
    			 out.println("mouse ".concat(xold + " ").concat(yold + " ").concat(x1 + " ").concat(y1));
    			 out.flush(); 
    		
    		 }
    		 else {
    			 fieldC = 1;
    			 xold = x1;
    			 yold = y1;
    			return;
    		 }
    	 }   	    
      }
          
    public void mouseClicked(MouseEvent e) {        
   
    }
    /**
     * listening for mouse pressed
     */
	public void mousePressed(MouseEvent e) {
		Field(e.getX(), e.getY());
	}
	/**
     * listening for mouse released
     */
	public void mouseReleased(MouseEvent e) {
		Field(e.getX(), e.getY()); 
		//System.out.print("\n reelased:"+  fieldC +"\n");

	}
	
	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
	}
	/**
	 * uppdating current board state
	 * @param gameBoard current board state
	 */
	
	public void getCurrentBoard(Board gameBoard)
	{
		this.currentBoard = gameBoard;
		repaint();
	}
}