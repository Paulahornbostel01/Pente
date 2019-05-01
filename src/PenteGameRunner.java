import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class PenteGameRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int gWidth = 800;
		int gHeight = 800;
		int sbWidth = (int)(gWidth * 0.30);
		//int sbHeight = (int)(gHeight * 0.30);
		
		JFrame theGame = new JFrame ("PLAY PENTE");
		
		theGame.setSize(gWidth + sbWidth, gHeight + 20);
		theGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theGame.setLayout(new BorderLayout());
		theGame.setResizable(false);
		
		PenteScore sb = new PenteScore(sbWidth, gHeight);
		sb.setPreferredSize(new Dimension(sbWidth, gHeight));
		//sb.setMaximumSize(new Dimension(sbWidth, gHeight));
		
		PenteGameBoard gb = new PenteGameBoard(gWidth, gHeight, sb);
		gb.setPreferredSize(new Dimension(gWidth, gHeight));
		theGame.add(gb);
		
		sb.setGameBoard(gb);
		
		theGame.add(gb, BorderLayout.CENTER);
		theGame.add(sb, BorderLayout.EAST);
		
		theGame.setVisible(true);
		gb.startNewGame(true);
	}

}