import java.util.ArrayList;
import java.util.Comparator;

public class ComputerMoveGenerator {
	
	public static final int OFFENSE = 1;
	public static final int DEFENSE = -1;
	public static final int ONE_IN_ROW_DEF = 1;
	public static final int TWO_IN_ROW_DEF = 2;
	public static final int TWO_IN_ROW_OPEN = 3;
	public static final int TWO_IN_ROW_CAP = 4; //We will adjust
	
	PenteGameBoard myGame;
	int myStone;
	
	/* Things Computer needs to do:
	 * 
	 * 1. Check if computer can win
	 * 		i. check for captures
	 * 		ii. check for FiveInARow
	 * 
	 * 2. Check if computer can block other player from winning
	 * 		i. block captures
	 * 		ii. block 5 inARow
	 * 
	 * 3. Prioritize
	 * 
	 */
	
	ArrayList <CMObject> offense = new ArrayList <CMObject>();
	ArrayList <CMObject> defense = new ArrayList <CMObject>();
	
	public ComputerMoveGenerator(PenteGameBoard gb, int stoneColor)
	{
		myStone = stoneColor;
		myGame = gb;
		
		System.out.println("Computer is playing as player " + myStone); 
	}
	
	public int [] getComputerMove()
	{
		int[] newMove = new int[2];
		
		newMove[0] = -1;
		newMove[1] = -1;
		
		defense.clear();
		offense.clear();
		
		//findDefMoves();
		//setDefPriorities();
		
		//findOffMoves();
		
		newMove = generateRandomMove();
		
	/*	if(defense.size() > 0)
		{
			int whichOne = (int)(Math.random() * defense.size());
			CMObject ourMove = defense.get(whichOne);
			newMove[0] = ourMove.getRow();
			newMove[1] = ourMove.getCol();
					
					
		}else {
			newMove = generateRandomMove();
		}*/
		
		
		
	
		try {
			sleepForAMove();
			
		} catch (InterruptedException e) 
		{// TODO Auto-generated catch blocke.printStackTrace();
			
		}
		
		return newMove;
	}
	
	/*public void setDefPriorities()
	{
		Comparator <CMObject> compareByPriority = (CMOject o1, CMObject o2) ->
		o1.getPriorityInt().compareTo(o2.getPriorityInt());
		
	}*/
	
	public void findDefMoves()
	{
		for(int row = 0; row < PenteGameBoard.NUM_SQUARES_SIDE; row++)
		{
			for (int col = 0; col < PenteGameBoard.NUM_SQUARES_SIDE; col ++)
			{
				if(myGame.getBoard()[row][col].getState() == myStone * -1
						
						)
				findOneDef(row, col);
				findTwoDef(row, col);
				//findThreeDef(row, col);
				//findFourDef(row, col);
				//findThreeCap(row, col);
			}
		}
	}
	
	public void findOneDef(int r, int c)
	{
		//We start here on Wed
		//This runs in the 8 directions
		for(int rL = -1; rL <= 1; rL++)
		{
			for(int uD = -1; uD <= 1; uD++)
			{
				try
				{
					if(myGame.getBoard()[r + rL][c + uD].getState() == PenteGameBoard.EMPTY)
					{
						CMObject newMove = new CMObject();
						newMove.setRow(r + rL);
						newMove.setCol(c + uD);
						newMove.setPriority(ONE_IN_ROW_DEF);
						newMove.setMoveType(DEFENSE);
						defense.add(newMove);
					}
					
					
					
				}catch (ArrayIndexOutOfBoundsException e)
				{
					System.out.println("Gone off board");
				}
			}
		}
	}
	
	public void findTwoDef(int r, int c)
	{
		//We start here on Wed
		//This runs in the 8 directions
		for(int rL = -1; rL <= 1; rL++)
		{
			for(int uD = -1; uD <= 1; uD++)
			{
				try
				{
					if(myGame.getBoard()[r + rL][c + uD].getState() == myStone * -1)
					{
						if(myGame.getBoard()[r + (rL *2)][c + (uD*2)].getState() == PenteGameBoard.EMPTY)
						{
							
							
							if(isOnBoard(r-rL, c-uD) == false)
							{
								setDefMove(r+(rL*2), c+(uD*2), TWO_IN_ROW_DEF);
								
							}else if (myGame.getBoard()[r - rL][c - uD].getState() == PenteGameBoard.EMPTY)
							{
								setDefMove(r+(rL*2), c+(uD*2), TWO_IN_ROW_OPEN);
								
							}else if (myGame.getBoard()[r - rL][c - uD].getState() == myStone)
							{
								setDefMove(r+(rL*2), c+(uD*2), TWO_IN_ROW_CAP);
							}
								
								
								//if r-rl, c-uD is open
								
								//if r-rl, c-uD is us-so we can capture
							
								
								//if r-rl,c-uD is Opponent (we don't care)
								
								
								
						}
					}
					
					
				}catch (ArrayIndexOutOfBoundsException e)
				{
					System.out.println("Gone off board");
				}
			}
		}
	}
	
	public boolean isOnBoard(int r, int c)
	{
		boolean onBoard = false;
		
		if(r >= 0 && r < PenteGameBoard.NUM_SQUARES_SIDE)
		{
			if(c >= 0 && c < PenteGameBoard.NUM_SQUARES_SIDE)
			{
				onBoard = true;
			}
		}
		
		
		return onBoard;
	}
	
	public void setDefMove(int r, int c, int p)
	{
		CMObject newMove = new CMObject();
		newMove.setRow(r);
		newMove.setCol(c);
		newMove.setPriority(p);
		newMove.setMoveType(DEFENSE);
		defense.add(newMove);
	}
	
	public void findOffMoves()
	{
		
	}
	
	public int [] generateRandomMove()
	{
		int[] move = new int[2]; //2 for row and col
		
		boolean done = false;
		
		int newR, newC;
		do
		{
			newR = (int)(Math.random() * PenteGameBoard.NUM_SQUARES_SIDE);
			newC = (int)(Math.random() * PenteGameBoard.NUM_SQUARES_SIDE);
			
			if(myGame.getBoard()[newR][newC].getState() == PenteGameBoard.EMPTY)
			{
				done = true;
				move[0] = newR;
				move[1] = newC;
			}
		}while(!done);
		
		return move;
	}
	
	public void sleepForAMove() throws InterruptedException
	{
		Thread currThread = Thread.currentThread();
		currThread.sleep(PenteGameBoard.SLEEP_TIME);
	}
	

}
