import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class PenteBoardSquare extends JPanel implements MouseListener{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9017982720198178703L;
	private int xLoc, yLoc;
	private int sWidth, sHeight;
	private int stoneWidth, stoneHeight;
	private int stoneXLoc, stoneYLoc;
	
	private int sState; //Open, Player1, Player2
	
	private Color sColor =  new Color (92, 135, 196); //Color of the square
	private Color lColor = new Color (109, 152, 214); //Color of the cross line
	private Color bColor = new Color (72, 83, 186); //Color of border aroundS
	private Color innerC = new Color (142, 173, 219);
	private Color bStone = new Color (11, 13, 48);
	private Color bStoneShadow = new Color (5, 8, 33);
	private Color bStoneBoardShadow = new Color (105, 106, 114);
	private Color bStoneHighlight = new Color (88, 92, 153);
	private Color wStone = new Color (216, 207, 164);
	private Color wStoneShadow = new Color (165, 160, 135);
	private Color wStoneBoardShadow= new Color (66, 65, 64);
	private Color wStoneHighlight= new Color (255, 255, 255);
	private Color hoverColor = new Color (132, 163, 206);
	private boolean isWinningSquare = false;
	private Color winningSquareColor = new Color (242, 230,96);
	
	
	boolean isInner = false;
	
	//Constructor
	public PenteBoardSquare(int x, int y, int w, int h)
	{
		xLoc = x;
		yLoc = y;
		sWidth = w;
		sHeight = h;
		this.setSize(sWidth, sHeight);
		//System.out.println("Dimension is: [" + sWidth + ", " + sHeight + "]");
		this.setLocation(xLoc, yLoc);
		//System.out.println("Location is: [" + xLoc + ", " + yLoc + "]");
		this.setOpaque(true);
		
		stoneWidth = sWidth - 10;
		stoneHeight = sHeight - 10;
		stoneXLoc = xLoc + 5;
		stoneYLoc = yLoc + 5;
		
		
	sState = PenteGameBoard.EMPTY;
	//PenteBoardSquare.addMouseListener(this);
	this.addMouseListener(this);
	this.setVisible(true);
	
	
	}

	public void setInner()
	{
		isInner = true;
	}
	
	public void drawMe(Graphics g)
	{
		if(isInner)
		{
			g.setColor(innerC);
		}else {
			g.setColor(sColor);
		}
		
		g.fillRect(xLoc, yLoc, sWidth, sHeight);
		
		//line color
		g.setColor(lColor);
		g.drawLine(xLoc, yLoc + sHeight/2, xLoc + sWidth, yLoc + sHeight/2);
		g.drawLine(xLoc + sWidth/2, yLoc, xLoc + sWidth/2, yLoc + sHeight);
		
		
		//border color
		g.setColor(bColor);
		g.drawRect(xLoc, yLoc, sWidth, sHeight);
		
		//Whitestone and BlackSTone
		if(sState == PenteGameBoard.BLACKSTONE)
		{
			
			//System.out.println("The stone state is now " + sState);
			
			//Shadow on board
			g.setColor(bStoneBoardShadow);
			g.fillOval(stoneXLoc - 3, stoneYLoc + 8, stoneWidth, stoneHeight - 5);
			//Shadow on stone
			g.setColor(bStoneShadow);
			g.fillOval(stoneXLoc, stoneYLoc, stoneWidth, stoneHeight);
			//Stone
			g.setColor(bStone);
			g.fillOval(stoneXLoc + 5, stoneYLoc, stoneWidth - 7, stoneHeight - 3);
		
			//Make highlight
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));
			
			g.setColor(bStoneHighlight);
			
			g.drawArc(
					xLoc + (int)(sWidth * 0.45), 
					yLoc + 10,
					(int)(sWidth * 0.30), 
					(int)(sHeight * 0.35), 
					0, 
					90);
			
			g2.setStroke(new BasicStroke(1));
			
			
			//Paint the winning square
			if(isWinningSquare)
			{
				g2.setStroke(new BasicStroke(2));
				g2.setColor(winningSquareColor);
				g2.drawOval(xLoc + 2, yLoc + 2, sWidth - 4, sHeight-4);
				g2.setStroke(new BasicStroke(1));
				
				
				
				
			}
			
		}
		
		
		if(sState == PenteGameBoard.WHITESTONE)
		{
			
			//System.out.println("The stone state is now " + sState);
			
			
			//Shadow on board
			g.setColor(wStoneBoardShadow);
			g.fillOval(stoneXLoc - 3, stoneYLoc + 8, stoneWidth, stoneHeight - 5);
			//Shadow on stone
			g.setColor(wStoneShadow);
			g.fillOval(stoneXLoc, stoneYLoc, stoneWidth, stoneHeight);
			//Stone
			g.setColor(wStone);
			g.fillOval(stoneXLoc + 5, stoneYLoc, stoneWidth - 7, stoneHeight - 3);
			
			//Make highlight
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));
			
			g.setColor(wStoneHighlight);
			
			g.drawArc(
					xLoc + (int)(sWidth * 0.45), 
					yLoc + 10,
					(int)(sWidth * 0.30), 
					(int)(sHeight * 0.35), 
					0, 
					90);
			
			g2.setStroke(new BasicStroke(1));
			
		}
	
	}
	
	//Methods
	//there can only be 3 states: EMPTY, BLACKSTONE, WHITESTONE
	public void setState(int newState)
	{
		if(newState < -1 || newState > 1)
		{
			System.out.println("Can't move here.");
		}else {
			sState = newState;
		}
	}
	
	public int getState()
	{
		return sState;
	}
	
	public void setXLoc (int newX)
	{
		xLoc = newX;
		stoneXLoc = newX;
	}
	
	public void setYLoc (int newY)
	{
		yLoc = newY;
		stoneYLoc = newY;
	}
	
	public void setWidth (int newW)
	{
		sWidth = newW;
		stoneWidth = newW;
	}
	
	public void setHeight (int newH)
	{
		sHeight = newH;
		stoneHeight = newH;
	}
	
	public boolean isClicked(int clickX, int clickY)
	{
		boolean didYouClickMe = false;
		
		if(xLoc < clickX && clickX < xLoc + sWidth)
		{
			if(yLoc < clickY && clickY < yLoc + sHeight)
			{
				didYouClickMe = true;
			}
		}
		
		
		return didYouClickMe;
	}
	
/*	public boolean isEntered(int enterX, int enterY)
	{
		boolean didYouEnterMe = false;
		
		if(xLoc < enterX && enterX < xLoc + sWidth)
		{
			if(yLoc < enterY && enterY < yLoc + sHeight)
			{
				didYouEnterMe = true;
			}
		}
		
		if(xLoc > enterX && enterX > xLoc + sWidth)
		{
			if(yLoc > enterY && enterY > yLoc + sHeight)
			{
				didYouEnterMe = false;
			}
		}
		
		
		return didYouEnterMe;
	}*/
	
	public void drawStoneStarting(Graphics g)
	{
	/*	//Shadow on board
		g.setColor(bStoneBoardShadow);
		g.fillOval(40, 60, sWidth*16, sHeight*16);
		//Shadow on stone
		g.setColor(bStoneShadow);
		g.fillOval(50, 50, sWidth*16, sHeight * 16);
		//Stone
		g.setColor(bStone);
		g.fillOval(50, 50, sWidth*15, sHeight*16);
	
		//Make highlight
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		
		g.setColor(bStoneHighlight);
		
		g.drawArc(
				50 + (int)((sWidth*16) * 0.45), 
				60,
				(int)((sWidth*16) * 0.30), 
				(int)((sHeight*16) * 0.35), 
				0, 
				90);*/
	
		System.out.println("Drew starting stone");
	}

	public void setSquareColor()
	{
		sColor = hoverColor;
	}
	
	public void resetSquareColor()
	{
		sColor = new Color (92, 135, 196);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
		sColor = hoverColor;
		System.out.println("Entered!!");
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void setWinningSquare (boolean newState)
	{
		isWinningSquare = newState;
	}
	
	
	
}

