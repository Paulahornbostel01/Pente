import java.util.ArrayList;

public class ComputerMoveGenerator {
	
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
	ArrayList <CMObject> deffense = new ArrayList <CMObject>();
	
	public ComputerMoveGenerator(PenteGameBoard gb, int stoneColor)
	{
		myStone = stoneColor;
		myGame = gb;
		
		System.out.println("Computer is playing as player " + myStone); 
	}
	
	public int [] getComputerMove()
	{
		int[] newMove = generateRandomMove();
		
		try {
			sleepForAMove();
			
		} catch (InterruptedException e) 
		{// TODO Auto-generated catch blocke.printStackTrace();
			
		}
		
		return newMove;
	}
	
	//Testing
	
	public void findDefMoves()
	{
		findOneDef();
	}
	
	public void findOneDef()
	{
		//We start here on Wed
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
