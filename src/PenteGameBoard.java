import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PenteGameBoard extends JPanel implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int EMPTY = 0;
	public static final int BLACKSTONE = 1;
	public static final int WHITESTONE = -1;
	public static final int NUM_SQUARES_SIDE = 19;
	public static final int INNER_START = 7;
	public static final int INNER_END = 11;
	public static final int PLAYER1_TURN = 1;
	public static final int PLAYER2_TURN = -1;
	public static final int MAX_CAPTURES = 10;
	public static final int SLEEP_TIME = 100;

	private int bWidth, bHeight;
	
	//private PenteBoardSquare testSquare; 
	private int squareW, squareH;
	private JFrame myFrame;
	private PenteScore myScoreBoard;
	
	private PenteBoardSquare [][] gameBoard;
	
	//Variables for playing the game
	private int playerTurn;
	private boolean p1IsComputer = false;
	private boolean p2IsComputer = false;
	private String p1Name, p2Name;
	private int p1Captures, p2Captures;
	private boolean darkStoneMove2Taken = false;
	
	//Variables for Computer Game Players
	private ComputerMoveGenerator p1ComputerPlayer = null;
	private ComputerMoveGenerator p2ComputerPlayer = null;
	
	private boolean gameOver;
	
	
	public PenteGameBoard(int w, int h, PenteScore sb)
	{
		//store these variables
		bWidth = w;
		bHeight = h;
		myScoreBoard = sb;
		
		p1Captures = 0;
		p2Captures = 0;

		this.setSize(w,h);
		this.setBackground(Color.BLACK);
		
		squareW = bWidth/NUM_SQUARES_SIDE;
		squareH = bHeight/NUM_SQUARES_SIDE;
		
		//testSquare = new PenteBoardSquare (0, 0, squareW, squareH);
		
		gameBoard = new PenteBoardSquare[NUM_SQUARES_SIDE][NUM_SQUARES_SIDE];
		
		for(int row = 0; row < NUM_SQUARES_SIDE; row++)
		{
			for (int col = 0; col < NUM_SQUARES_SIDE; col ++)
			{
				gameBoard[row][col] = new PenteBoardSquare(col*squareW, row*squareH, squareW, squareH);
				if(col>=INNER_START && col<= INNER_END)
				{
					if(row>=INNER_START && row<= INNER_END)
					{
						gameBoard[row][col].setInner();
					}
				}
			}
		}
		
	startingScreen();
	repaint();
	
	
	addMouseListener(this);
	//gameBoard
	this.setFocusable(true);
		
		
	}
	
	//Method to do drawing
	//We do this by overriding
	
	public void paintComponent(Graphics g)
	{
		//updateSizes();
			
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, bWidth, bHeight);
		
		//do this 19x19 times
		for(int row = 0; row < NUM_SQUARES_SIDE; row++)
		{
			for (int col = 0; col < NUM_SQUARES_SIDE; col ++)
			{
				gameBoard[row][col].drawMe(g);
			}
		}
		
	}
	
	public void resetBoard()
	{
		for(int row = 0; row < NUM_SQUARES_SIDE; row++)
		{
			for (int col = 0; col < NUM_SQUARES_SIDE; col ++)
			{
				gameBoard[row][col].setState(EMPTY);
				gameBoard[row][col].setWinningSquare(false);
			}
		}
		this.paintImmediately(0,0, this.bWidth, this.bHeight);
		
		p1Captures = 0;
		p2Captures = 0;
		gameOver = false;
	}
	
	public void startNewGame(boolean firstGame)
	{
		
		p1Captures = 0;
		p2Captures = 0;
		gameOver = false;
		
		JOptionPane myPane = new JOptionPane();
		
		myPane.setVisible(true);
		
		if(firstGame)
		{
			p1Name = JOptionPane.showInputDialog("Name of Player 1 (or type 'c' for computer)");
			if(
				p1Name != null && p1Name.toLowerCase().equals("c") || 
				p1Name.toLowerCase().equals("computer") || p1Name.toLowerCase().equals("comp") )
			{
				p1IsComputer = true;
				p1ComputerPlayer = new ComputerMoveGenerator(this, BLACKSTONE);
			}
		}
		
		myScoreBoard.setName(p1Name, BLACKSTONE);
		myScoreBoard.setCaptures(p1Captures, BLACKSTONE);
		
		if(firstGame)
		{
			p2Name = JOptionPane.showInputDialog("Name of Player 2 (or type 'c' for computer)");
			if( p2Name != null && p2Name.toLowerCase().equals("c") || 
					p2Name.toLowerCase().equals("computer") || p2Name.toLowerCase().equals("comp") )
			{
				p2IsComputer = true;
				p2ComputerPlayer = new ComputerMoveGenerator(this, WHITESTONE);
			}
		}
		
		
		myScoreBoard.setName(p2Name, WHITESTONE);
		myScoreBoard.setCaptures(p2Captures, WHITESTONE);
		
		resetBoard();
		
		//We place the first stone
		playerTurn = PLAYER1_TURN;
		//Sets center square as darkStone
		this.gameBoard[NUM_SQUARES_SIDE/2][NUM_SQUARES_SIDE/2].setState(BLACKSTONE);
		this.darkStoneMove2Taken = false;
		myScoreBoard.setPlayerTurn(playerTurn);
		//changePlayerTurn();
		//System.out.println("It is now the turn of " + playerTurn);
		checkForComputerMove(playerTurn);
		
		
		this.repaint();
	}
	
	public void changePlayerTurn()
	{
		playerTurn *= -1;
		myScoreBoard.setPlayerTurn(playerTurn);
	}
	
	public void updateSizes()
	{
		if(myFrame.getWidth() != bWidth || myFrame.getHeight() != bHeight + 20)
		{
			bWidth = myFrame.getWidth();
			bHeight = myFrame.getHeight() - 20;
			
			squareW = bWidth/NUM_SQUARES_SIDE;
			squareH = bHeight/NUM_SQUARES_SIDE;
			
			resetSquares(squareW, squareH);
			
		}
		
	}
	
	public void resetSquares(int w, int h)
	{
		for(int row = 0; row < NUM_SQUARES_SIDE; row++)
		{
			for (int col = 0; col < NUM_SQUARES_SIDE; col ++)
			{
				gameBoard[row][col].setXLoc(col * w);
				gameBoard[row][col].setYLoc(row * h);
				gameBoard[row][col].setWidth(w);
				gameBoard[row][col].setHeight(h);
			}
		}
	}
	
	public void checkClick(int clickX, int clickY)
	{
		for(int row = 0; row < NUM_SQUARES_SIDE; row++)
		{
			for (int col = 0; col < NUM_SQUARES_SIDE; col ++)
			{
				boolean squareClicked = gameBoard[row][col].isClicked(clickX, clickY);
				
				if(squareClicked && gameBoard[row][col].getState() == EMPTY)
				{
					//one more check to see about the second dark move
					
					if(!darkStoneProblem(row, col)) //if there is no darkStone there
					{
					
						
						//System.out.println("You cliked the square at [ " + row + ", " + col + "]");
						gameBoard[row][col].setState(playerTurn);
					
						//this.repaint();
						this.paintImmediately(0, 0, this.bWidth, this.bHeight);
						this.changePlayerTurn();
						checkForComputerMove(playerTurn);
						
						checkForCaptures(row, col, playerTurn);
						checkForWins(playerTurn);
						
						//System.out.println("It is now the turn of " + playerTurn);
						
					}else {
						JOptionPane.showMessageDialog(null, "Second dark stone move has to be outside of starting square.");
					}
				}

			}
		}
	}
	
	public  void checkForComputerMove(int whichPlayer)
	{
		if(whichPlayer == this.PLAYER1_TURN && this.p1IsComputer)
		{
			int [] nextMove = this.p1ComputerPlayer.getComputerMove();
			int newR = nextMove[0];
			int newC = nextMove[1];
			gameBoard[newR][newC].setState(playerTurn);
			this.paintImmediately(0, 0, bWidth, bHeight);
			checkForCaptures(newR, newC, playerTurn);
			checkForWins(playerTurn);
			this.repaint();
			if(!gameOver)
			{
				this.changePlayerTurn();
				checkForComputerMove(playerTurn);
			}
		}else if (whichPlayer == this.PLAYER2_TURN && this.p2IsComputer)
		{
			int [] nextMove = this.p2ComputerPlayer.getComputerMove();
			int newR = nextMove[0];
			int newC = nextMove[1];
			gameBoard[newR][newC].setState(playerTurn);
			this.paintImmediately(0, 0, bWidth, bHeight);
			checkForCaptures(newR, newC, playerTurn);
			checkForWins(playerTurn);
			this.repaint();
			if(!gameOver)
			{
				this.changePlayerTurn(); 
				checkForComputerMove(playerTurn);
			}
			
		}
		this.repaint();
	}
	
	public boolean darkStoneProblem(int r, int c)
	{
		boolean darkStoneProblem = false;
		
		if(
			darkStoneMove2Taken == false &&
			playerTurn == BLACKSTONE
			)
		{
			if(
					r >= this.INNER_START && r <= this.INNER_END &&
					c >= this.INNER_START && c <= this.INNER_END
						)
					{
				
						darkStoneProblem = true;
				
				
				
					}else {
						darkStoneMove2Taken = true;
					}
					
			
			
			
			
			
		}
		
		
		
		return darkStoneProblem;
	}
	
/*	public void checkEnter(int enterX, int enterY)
	{
		for(int row = 0; row < NUM_SQUARES_SIDE; row++)
		{
			for (int col = 0; col < NUM_SQUARES_SIDE; col ++)
			{
				boolean squareEntered = gameBoard[row][col].isEntered(enterX, enterY);
				
				if(squareEntered)
				{
					gameBoard[row][col].setSquareColor();
					repaint();
				}else{
					gameBoard[row][col].resetSquareColor();
					repaint();
					//squareEntered = false;
				}
			
				//squareEntered = false;
			
			}
		}
		
		repaint();
	}*/

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
		//System.out.println("You clicked at {" + e.getX() + ", " + e.getY() + "]");
		checkClick(e.getX(), e.getY());
	
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
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseAlways(MouseEvent e) {
		
		//checkEnter(e.getX(), e.getY());
		repaint();
		
	}
	
	public void startingScreen()
	{

		System.out.println("At starting screen");
		
		for(int i = 7; i <= 12; i++)
		{
			this.gameBoard[i][3].setState(BLACKSTONE);
			this.gameBoard[i][15].setState(BLACKSTONE);
		}
		
		for(int i = 6; i <= 13; i++)
		{
			this.gameBoard[i][4].setState(BLACKSTONE);
			this.gameBoard[i][14].setState(BLACKSTONE);
		}

		for(int i = 5; i <= 14; i++)
		{
			this.gameBoard[i][5].setState(BLACKSTONE);
			this.gameBoard[i][13].setState(BLACKSTONE);
		}
		
		
		for(int i = 4; i <= 15; i++)
		{
			this.gameBoard[i][6].setState(BLACKSTONE);
			this.gameBoard[i][12].setState(BLACKSTONE);
		}
		
		for(int z = 6; z <= 11; z++)
		{
			for(int i = 4; i <= 15; i++)
			{
				this.gameBoard[i][z].setState(BLACKSTONE);
			}
		}
		
		this.gameBoard[6][10].setState(WHITESTONE);
		this.gameBoard[6][11].setState(WHITESTONE);
		this.gameBoard[7][12].setState(WHITESTONE);
		this.gameBoard[8][13].setState(WHITESTONE);
		this.gameBoard[9][13].setState(WHITESTONE);
		
		
	}
	
	public void checkForCaptures(int r, int c, int pT)
	{
		boolean didCapture;
		//Horizontal Checks
		if(c<= NUM_SQUARES_SIDE - 4) didCapture = checkForCaptures(r, c, pT, 0, 1);
		if(c>= 3) didCapture = checkForCaptures(r, c, pT, 0, -1);
		//Vertical Checks
		if(r<= NUM_SQUARES_SIDE - 4) didCapture = checkForCaptures(r, c, pT, 1, 0);
		if(r>= 3) didCapture = checkForCaptures(r, c, pT, -1, 0);
		//Diagonal Checks
		if(r<= NUM_SQUARES_SIDE - 4 && c<= NUM_SQUARES_SIDE - 4) didCapture = checkForCaptures(r, c, pT, 1, 1);
		if(r>= 3 && c>= 3) didCapture = checkForCaptures(r, c, pT, -1, -1);
		if(r>=3 && c<= NUM_SQUARES_SIDE - 4) didCapture = checkForCaptures(r, c, pT, -1, 1);
		if(r<= NUM_SQUARES_SIDE - 4 && c>= 3) didCapture = checkForCaptures(r, c, pT, 1, -1);
		
		
		
		
	}
	
	 
	public boolean checkForCaptures(int r, int c, int pT, int upDown, int rightLeft)
	{

		try
		{
			boolean didCapture = false;
			if(gameBoard[r + upDown][c + rightLeft].getState() == pT * -1) 
			{
				if(gameBoard[r + (upDown * 2)][c + (rightLeft * 2)].getState() == pT * -1)
				{
					if(gameBoard[r + (upDown * 3)][c + (rightLeft * 3)].getState() == pT)
					{
						System.out.println("CAPTURE");
						
						gameBoard[r + upDown][c + rightLeft].setState(EMPTY);
						gameBoard[r + (upDown * 2) ][c+ (rightLeft * 2)].setState(EMPTY);
						didCapture = true;
						if(pT == this.PLAYER1_TURN)
						{
							p1Captures = p1Captures + 2;
							myScoreBoard.setCaptures(p1Captures, playerTurn);
						}else {
							p2Captures = p2Captures + 2;
							myScoreBoard.setCaptures(p2Captures, playerTurn);
						}
						
						
					}
					
				}
					
			}	
			
			return didCapture;
		
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("You have an error" + e.toString());
			return false;
			
		}
	}
	
		public void checkForWins(int whichPlayer)
	{
		if(whichPlayer == this.PLAYER1_TURN)
		{
			if(p1Captures >= MAX_CAPTURES)
			{
				JOptionPane.showMessageDialog(null, "Congratulations: " + p1Name + " wins!!" +
				"\n with " + p1Captures + " captures");
				gameOver = true;
			}else if(fiveInARow(whichPlayer))
			{
			JOptionPane.showMessageDialog(null, "Congratulations: " + p1Name + " wins!!" +
					"\n with five in a row!");
			gameOver = true;
			}
		}else { //for player 2
			if(p2Captures >= MAX_CAPTURES)
			{
				
				JOptionPane.showMessageDialog(null, "Congratulations: " + p2Name + " wins!!" +
				"\n with " + p2Captures + " captures");
				gameOver = true;
			}else if(fiveInARow(whichPlayer))
			{
			JOptionPane.showMessageDialog(null, "Congratulations: " + p2Name + " wins!!" +
					"\n with five in a row!");
			gameOver = true;
			}
		}
		
	}
	public boolean fiveInARow(int whichPlayer)
	{
		boolean isFive = false;
		
		for(int row = 0; row < NUM_SQUARES_SIDE; row++)
		{
			for (int col = 0; col < NUM_SQUARES_SIDE; col ++)
			{
				
				for(int rL = -1; rL <= 1; rL++)
				{
					for(int uD = -1; uD <= 1; uD++)
					{
						if(fiveCheck(row, col, whichPlayer, rL, uD) == true)
						{
							isFive = true;
						}
					}
				}

				
				
				
				
			}
		}
	
		return isFive;
		
	}
	

	
	public boolean fiveCheck(int r, int c, int pT, int upDown, int rightLeft)
	{
		
	//	System.out.println("Player turn is " + pT);
		
		try
		{
			boolean isFive = false;
			if( !(upDown == 0 && rightLeft == 0) &&
				gameBoard[r][c].getState() == pT &&
				gameBoard[r + upDown][c + rightLeft].getState() == pT &&
				gameBoard[r + (upDown * 2)][c + (rightLeft * 2)].getState() == pT &&
				gameBoard[r + (upDown * 3)][c + (rightLeft * 3)].getState() == pT &&
				gameBoard[r + (upDown * 4)][c + (rightLeft * 4)].getState() == pT
				)
			{
				System.out.println("FIVE IN A ROW");
				
				gameBoard[r][c].setWinningSquare(true);
				gameBoard[r + upDown][c + rightLeft].setWinningSquare(true);
				gameBoard[r + (upDown * 2)][c + (rightLeft * 2)].setWinningSquare(true);
				gameBoard[r + (upDown * 3)][c + (rightLeft * 3)].setWinningSquare(true);
				gameBoard[r + (upDown * 4)][c + (rightLeft * 4)].setWinningSquare(true);
				
				
				if(pT == PLAYER1_TURN)
				{
					System.out.println("Player 1 got 5");
					isFive = true;
					gameOver = true;
				}else {
					System.out.println("Player 2 got 5");
					isFive = true;
					gameOver = true;
				}
				
						
					
			}	
			
			return isFive;
		
		}catch(ArrayIndexOutOfBoundsException e){
			//System.out.println("You have an error" + e.toString());
			return false;
			
		}
	}
	
	//allowing access to the gameboard
	public PenteBoardSquare[][] getBoard()
	{
		return gameBoard;
		
	}
	
	
}

