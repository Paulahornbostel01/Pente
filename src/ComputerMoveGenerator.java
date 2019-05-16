import java.util.ArrayList;
import java.util.Collections;

public class ComputerMoveGenerator {
	
	//To Win Everything
	
	public static final int FIVE_IN_ROW_4_X = 600;
	
	public static final int FIVE_IN_ROW_4_ = 600;
	
	public static final int FIVE_IN_ROW_3_1 = 600;
	
	public static final int FIVE_IN_ROW_2_2 = 600;
	
	public static final int FIVE_IN_ROW_1_3 = 600;
	
	//Preventing five (four)
	
	public static final int PREVENT_FIVE_4_X = 550;
	
	public static final int PREVENT_FIVE_4_ = 500;
	
	public static final int PREVENT_FIVE_3_1 = 550;
	
	public static final int PREVENT_FIVE_2_2 = 550;
	
	public static final int PREVENT_FIVE_1_3 = 550;
	
	//Prevent capture -- OFFENSE
	
	public static final int PREVENT_CAPTURE_2_X = 400;
	
	//Captures
	
	public static final int CAPTURE = 450;
	
	//Preventing four (three)
	
	public static final int PREVENT_FOUR_3_ = 310;
	
	public static final int PREVENT_FOUR_3_X = 300;
	
	public static final int PREVENT_FOUR_2_1 = 300;
	
	public static final int PREVENT_FOUR_1_2 = 300;
	
	//Preventing three (two)
	
	public static final int PREVENT_THREE_2_X = 200;
	
	public static final int PREVENT_THREE_2_ = 200;
	
	
	//Preventing two (one)

	public static final int PREVENT_TWO_1_ = 100;
	
	//Offense
	
	public static final int TWO_IN_ROW_OFF = 50;

	public static final int THREE_IN_ROW_OFF = 60;
	
	public static final int FOUR_IN_ROW_OFF = 80;
	
	

	
	
	
	PenteGameBoard myGame;
	int myStone;
	
	/* Things Computer needs to do:
	 * s
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
	
	ArrayList <CMObject> moves = new ArrayList <CMObject>();
	
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
		
		moves.clear();
		
		findMoves();
		sortPriorities();
		
		printPriorities();
		
		/*System.out.println("First Move: " + moves.get(0));
		System.out.println("Last Move: " + moves.get(moves.size()-1));*/
		
		if(moves.size() > 0)
		{
			
			if(moves.get(0).getPriority() == moves.get(8).getPriority())
			{
				CMObject ourMove = moves.get((int)(Math.random() * moves.size()));
				newMove[0] = ourMove.getRow();
				newMove[1] = ourMove.getCol();
				
			}else {
			
			
				//int whichOne = (int)(Math.random() * defense.size());
				CMObject ourMove = moves.get(0);
				newMove[0] = ourMove.getRow();
				newMove[1] = ourMove.getCol();
			}
					
					
		}else {
			newMove = generateRandomMove();
		}
		
		
		
	
		try {
			sleepForAMove();
			
		} catch (InterruptedException e) 
		{// TODO Auto-generated catch blocke.printStackTrace();
			
		}
		
		return newMove;
	}
	
	public void sortPriorities()
	{
		/*Comparator <CMObject> compareByPriority = (CMObject o1, CMObject o2) ->
		o1.getPriorityInt().compareTo(o2.getPriorityInt());*/
		
		Collections.sort(moves);
		 
	}
	
	public void findMoves()
	{
		for(int row = 0; row < PenteGameBoard.NUM_SQUARES_SIDE; row++)
		{
			for (int col = 0; col < PenteGameBoard.NUM_SQUARES_SIDE; col ++)
			{
				if(myGame.getBoard()[row][col].getState() == myStone * -1
						
						)
				{
					findFourDef(row, col);
					findThreeDef(row, col);
					findTwoDef(row, col);
					findOneDef(row, col);
				
				}
				
				
				if(myGame.getBoard()[row][col].getState() == myStone)
				{
					//System.out.println("My Stone is " + myStone);
					findFiveOff(row, col);
					findFourOff(row, col);
					findThreeOff(row, col);
					findTwoOff(row, col);
				}
				
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
						newMove.setPriority(PREVENT_TWO_1_);
						//newMove.setMoveType(DEFENSE);
						moves.add(newMove);
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
					
					if(myGame.getBoard()[r + rL][c + uD].getState() == myStone)
					{
						if(myGame.getBoard()[r + (rL*2)][c + (uD*2)].getState() == myStone)
						{
							if(myGame.getBoard()[r + (rL*3)][c + (uD*3)].getState() == PenteGameBoard.EMPTY)
							{
				
							setMove(r+(rL*3), c+(uD*3), PREVENT_CAPTURE_2_X);
							
							}
						}
						
					
					
					
					
					
					}else if(myGame.getBoard()[r + rL][c + uD].getState() == myStone * -1)
					{
						if(myGame.getBoard()[r + (rL*2)][c + (uD*2)].getState() == PenteGameBoard.EMPTY)
						{
							
							if(isOnBoard(r-rL, c-uD) == false)
							{
								setMove(r+(rL*2), c+(uD*2), PREVENT_THREE_2_X);
								
							}else if (myGame.getBoard()[r - rL][c - uD].getState() == PenteGameBoard.EMPTY)
							{
								setMove(r+(rL*2), c+(uD*2), PREVENT_THREE_2_);
								
							}else if (myGame.getBoard()[r - rL][c - uD].getState() == myStone)
							{
								setMove(r+(rL*2), c+(uD*2), CAPTURE);
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
	
	public void findThreeDef(int r, int c)
	{
		for(int rL = -1; rL <= 1; rL++)
		{
			for(int uD = -1; uD <= 1; uD++)
			{
				try
				{
						
					if(myGame.getBoard()[r + rL][c + uD].getState() == myStone * -1)
					{
						if(myGame.getBoard()[r + (rL*2)][c + (uD*2)].getState() == myStone * -1)
						{
							if(myGame.getBoard()[r + (rL*3)][c + (uD*3)].getState() == PenteGameBoard.EMPTY)
							{
						
								if(myGame.getBoard()[r + (rL*4)][c + (uD*4)].getState() == myStone * -1)
								{
									setMove(r+(rL*3), c+(uD*3), PREVENT_FIVE_3_1);
								}else if(isOnBoard(r-rL, c-uD) == false)
								{
									setMove(r+ (rL*3), c+(uD*3), PREVENT_FOUR_3_X);
									
								}else if(myGame.getBoard()[r - rL][c - uD].getState() == myStone)
								{
									setMove(r+(rL*3), c+(uD*3), PREVENT_FOUR_3_X);
								}else if(myGame.getBoard()[r - rL][c - uD].getState() == PenteGameBoard.EMPTY)
								{
									setMove(r+(rL*3), c+(uD*3), PREVENT_FOUR_3_);
								}/*else if(myGame.getBoard()[r -(rL*2)][c - (uD*2)].getState() == myStone * -1)
								{
									setMove(r-rL, c-uD, THREE_IN_ROW_STOPFIVE);
								} */
							
							
							}
						}
						
					}
					
				}catch (ArrayIndexOutOfBoundsException e)
				{
					System.out.println("Gone off board");
				}
			}
		}
	}
	
	public void findFourDef(int r, int c)
	{
		for(int rL = -1; rL <= 1; rL++)
		{
			for(int uD = -1; uD <= 1; uD++)
			{
				try
				{
						
					if(myGame.getBoard()[r + rL][c + uD].getState() == myStone * -1)
					{
						if(myGame.getBoard()[r + (rL*2)][c + (uD*2)].getState() == myStone * -1)
						{
							if(myGame.getBoard()[r + (rL*3)][c + (uD*3)].getState() == myStone * -1)
							{
							
							
							if(myGame.getBoard()[r + (rL*4)][c + (uD*4)].getState() == PenteGameBoard.EMPTY)
							{
						
								if(isOnBoard(r-rL, c-uD) == false)
								{
									setMove(r+ (rL*4), c+(uD*4), PREVENT_FIVE_4_X);
									
								}else if(myGame.getBoard()[r - rL][c - uD].getState() == myStone)
								{
									setMove(r+(rL*4), c+(uD*4), PREVENT_FIVE_4_X);
								}else if(myGame.getBoard()[r - rL][c - uD].getState() == PenteGameBoard.EMPTY)
								{
									setMove(r+(rL*4), c+(uD*4), PREVENT_FIVE_4_);
								}
							
							}
							}
						}
						
					}
					
				}catch (ArrayIndexOutOfBoundsException e)
				{
					System.out.println("Gone off board");
				}
			}
		}
	}
	
	
	
	public void findTwoOff(int r, int c)
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
							if(isOnBoard(r-rL, c-uD) == false)
							{
								setMove(r + rL, c + uD, TWO_IN_ROW_OFF);
								
							}else if (myGame.getBoard()[r - rL][c - uD].getState() == PenteGameBoard.EMPTY    
									&&
									myGame.getBoard()[r - rL][c - uD].getState() != myStone * -1 )
							{
								setMove(r + rL, c + uD, TWO_IN_ROW_OFF);
								
							}
							
					}
					
					
				}catch (ArrayIndexOutOfBoundsException e)
				{
					System.out.println("Gone off board");
				}
			}
		}
	}
	
	public void findThreeOff(int r, int c)
	{
		for(int rL = -1; rL <= 1; rL++)
		{
			for(int uD = -1; uD <= 1; uD++)
			{
				try
				{
						
					if(myGame.getBoard()[r + rL][c + uD].getState() == myStone)
					{
						if(myGame.getBoard()[r + (rL*2)][c + (uD*2)].getState() == PenteGameBoard.EMPTY)
							{
							
							if(isOnBoard(r-rL, c-uD) == false)
							{
								setMove(r+ (rL*2), c+(uD*2), THREE_IN_ROW_OFF);
							}else if(myGame.getBoard()[r - rL][c - uD].getState() == PenteGameBoard.EMPTY)
							{
								setMove(r+(rL*2), c+(uD*2), THREE_IN_ROW_OFF);
							}else if(myGame.getBoard()[r - rL][c - uD].getState() == myStone * -1)
							{
								setMove(r+(rL*2), c+(uD*2), THREE_IN_ROW_OFF);
							}
											
							}	
							
					}
					
				}catch (ArrayIndexOutOfBoundsException e)
				{
					System.out.println("Gone off board");
				}
			}
		}
	}
	
	public void findFourOff(int r, int c)
	{
		for(int rL = -1; rL <= 1; rL++)
		{
			for(int uD = -1; uD <= 1; uD++)
			{
				try
				{
						
					if(myGame.getBoard()[r + rL][c + uD].getState() == myStone)
					{
						if(myGame.getBoard()[r + (rL*2)][c + (uD*2)].getState() == myStone)
						{
							if(myGame.getBoard()[r + (rL*3)][c + (uD*3)].getState() == PenteGameBoard.EMPTY)
							{
							
							
							
								
								if(isOnBoard(r-rL, c-uD) == false)
								{
									setMove(r+ (rL*3), c+(uD*3), FOUR_IN_ROW_OFF);
								}else if(myGame.getBoard()[r - rL][c - uD].getState() == PenteGameBoard.EMPTY)
								{
									setMove(r+(rL*3), c+(uD*3), FOUR_IN_ROW_OFF);
								}else if(myGame.getBoard()[r - rL][c - uD].getState() == myStone * -1)
								{
									setMove(r+(rL*3), c+(uD*3), FOUR_IN_ROW_OFF);
								}
												
							}
						}
						
							
					}
					
				}catch (ArrayIndexOutOfBoundsException e)
				{
					System.out.println("Gone off board");
				}
			}
		}
	}
	
	public void findFiveOff(int r, int c)
	{
		for(int rL = -1; rL <= 1; rL++)
		{
			for(int uD = -1; uD <= 1; uD++)
			{
				try
				{
					if(myGame.getBoard()[r + rL][c + uD].getState() == myStone)
					{
						if(myGame.getBoard()[r + (rL*2)][c + (uD*2)].getState() == PenteGameBoard.EMPTY)
						{
							if(myGame.getBoard()[r + (rL*3)][c + (uD*3)].getState() == myStone)
							{
								if(myGame.getBoard()[r + (rL*4)][c + (uD*4)].getState() == myStone)
								{
									setMove(r+(rL*2), c+(uD*2), FIVE_IN_ROW_2_2);
								}
							}	
							
						}
					
					
						if(myGame.getBoard()[r + (rL*2)][c + (uD*2)].getState() == myStone)
						{
							if(myGame.getBoard()[r + (rL*3)][c + (uD*3)].getState() == PenteGameBoard.EMPTY)
							{
								if(myGame.getBoard()[r + (rL*4)][c + (uD*4)].getState() == myStone)
								{
									setMove(r+(rL*3), c+(uD*3), FIVE_IN_ROW_3_1);
								}
							}
							
							
							
							
							
							
							if(myGame.getBoard()[r + (rL*3)][c + (uD*3)].getState() == myStone)
							{
								if(myGame.getBoard()[r + (rL*4)][c + (uD*4)].getState() == PenteGameBoard.EMPTY)
								{
							
							
							
								
								if(isOnBoard(r-rL, c-uD) == false)
								{
									setMove(r+ (rL*4), c+(uD*4), FIVE_IN_ROW_4_X);
								}else if(myGame.getBoard()[r - rL][c - uD].getState() == PenteGameBoard.EMPTY)
								{
									setMove(r+(rL*4), c+(uD*4), FIVE_IN_ROW_4_);
								}else if(myGame.getBoard()[r - rL][c - uD].getState() == myStone * -1)
								{
									setMove(r+(rL*4), c+(uD*4), FIVE_IN_ROW_4_X);
								}
								
								}
												
							}
						}
						
							
					}else if(myGame.getBoard()[r + rL][c + uD].getState() == PenteGameBoard.EMPTY)
					{
						if(myGame.getBoard()[r + (rL*2)][c + (uD*2)].getState() == myStone)
						{
							if(myGame.getBoard()[r + (rL*3)][c + (uD*3)].getState() == myStone)
							{
								if(myGame.getBoard()[r + (rL*4)][c + (uD*4)].getState() == myStone)
								{
									setMove(r+(rL*4), c+(uD*4), FIVE_IN_ROW_1_3);
								}
							}	
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
	
	public void setMove(int r, int c, int p)
	{
		CMObject newMove = new CMObject();
		newMove.setRow(r);
		newMove.setCol(c);
		newMove.setPriority(p);
		//newMove.setMoveType(DEFENSE);
		moves.add(newMove);
	
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
		Thread.sleep(PenteGameBoard.SLEEP_TIME);
	}
	
	public void printPriorities()
	{
		for(CMObject m: moves) //loop for iterables
		{
			System.out.println(m);
		}
	}

}
