import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class PenteScore extends JPanel implements ActionListener
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5442116539668649591L;
	JLabel p1Name, p2Name;
	JTextField p1Captures, p2Captures;
	private JTextField whoseTurnField;
	
	JButton resetButton;
	
	Color backColor;
	Color panelColor;
	Color borderColor;
	
	int spWidth, spHeight;
	
	Font myFont = new Font ("Arial", Font.PLAIN, 24);
	private Color bStoneTextColor = new Color(11, 13, 48);
	private PenteGameBoard myBoard = null;
	
	public PenteScore(int w, int h)
	{
		
		backColor= new Color (61, 63, 91);
		panelColor = new Color (107, 110, 155);
		//borderColor = new Color (221, 196, 146);
		borderColor = bStoneTextColor;
		spWidth = w;
		spHeight = h;
		
		this.setSize(spWidth, spHeight);
		this.setBackground(backColor);
		
		this.setVisible(true);
		
		addInfoPlaces();
	}
	
	public void addInfoPlaces()
	{
		JPanel p1Panel = new JPanel();
			p1Panel.setLayout(new BoxLayout(p1Panel, BoxLayout.PAGE_AXIS));
			p1Panel.setSize(spWidth, (int)(spHeight*0.45));
			p1Panel.setBackground(panelColor);
			p1Panel.setOpaque(true);
			
			p1Name = new JLabel("Player1 Name");
			p1Name.setAlignmentX(Component.CENTER_ALIGNMENT);
			p1Name.setFont(myFont);
			p1Name.setForeground(bStoneTextColor);
			p1Name.setHorizontalAlignment(SwingConstants.CENTER);
			
			p1Captures = new JTextField("Player 1 Captures");
			p1Captures.setAlignmentX(Component.CENTER_ALIGNMENT);
			p1Captures.setFont(myFont);
			p1Captures.setForeground(Color.WHITE);
			p1Captures.setBackground(bStoneTextColor);
			p1Captures.setHorizontalAlignment(SwingConstants.CENTER);
			
			//Place and space the labels
			p1Panel.add(Box.createRigidArea(new Dimension(spWidth-40,70)));
			p1Panel.add(p1Name);
			p1Panel.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
			p1Panel.add(p1Captures);
			p1Panel.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
			
			Border b = BorderFactory.createLineBorder(borderColor, 4, true);
			
			p1Panel.setBorder(b);
		
		p1Panel.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
		this.add(p1Panel);
		p1Panel.add(Box.createRigidArea(new Dimension(spWidth-40,20)));
		
		//Add a button
		resetButton = new JButton ("New Game");
		resetButton.setFont(myFont);
		resetButton.addActionListener(this);
		this.add(resetButton);
		
		//P2 Panel
		JPanel p2Panel = new JPanel();
			p2Panel.setLayout(new BoxLayout(p2Panel, BoxLayout.PAGE_AXIS));
			p2Panel.setSize(spWidth, (int)(spHeight*0.45));
			p2Panel.setBackground(panelColor);
			p2Panel.setOpaque(true);
			
			p2Name = new JLabel("Player2 Name");
			p2Name.setAlignmentX(Component.CENTER_ALIGNMENT);
			p2Name.setFont(myFont);
			p2Name.setForeground(Color.WHITE);
			p2Name.setHorizontalAlignment(SwingConstants.CENTER);
			
			p2Captures = new JTextField("Player 2 Captures");
			p2Captures.setAlignmentX(Component.CENTER_ALIGNMENT);
			p2Captures.setFont(myFont);
			p2Captures.setForeground(bStoneTextColor);
			p2Captures.setHorizontalAlignment(SwingConstants.CENTER);
			
			//Place and space the labels
			p2Panel.add(Box.createRigidArea(new Dimension(spWidth-40,70)));
			p2Panel.add(p2Name);
			p2Panel.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
			p2Panel.add(p2Captures);
			p2Panel.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
			
			p2Panel.setBorder(b);
	
	this.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
	this.add(p2Panel);
	
	JPanel whoseTurn = new JPanel();
		whoseTurn.setLayout(new BoxLayout(whoseTurn, BoxLayout.Y_AXIS));
		whoseTurn.setSize(spWidth, (int)(spHeight*0.45));
		whoseTurn.setBackground(panelColor);
		whoseTurn.setOpaque(true);
		
		whoseTurnField = new JTextField ("It's ??? Turn Now");
		whoseTurnField.setAlignmentX(Component.CENTER_ALIGNMENT);
		whoseTurnField.setFont(myFont);
		whoseTurnField.setForeground(Color.BLACK);
		whoseTurnField.setHorizontalAlignment(SwingConstants.CENTER);
		
		whoseTurn.add(Box.createRigidArea(new Dimension(spWidth-40,20)));
		whoseTurn.add(whoseTurnField);
		whoseTurn.add(Box.createRigidArea(new Dimension(spWidth-40,20)));
		
		whoseTurn.setBorder(b);
		
		whoseTurn.add(Box.createRigidArea(new Dimension(spWidth-40,30)));
		this.add(whoseTurn);
	
	
	repaint();
		
		
	}
	
	public void setName (String n, int whichPlayer)
	{
		if(whichPlayer == PenteGameBoard.BLACKSTONE)
		{
			p1Name.setText("Player 1: " + n);
		}else {
			p2Name.setText("Player 2: " + n);
		}
		
		repaint();
	}

	public void setCaptures(int c, int whichPlayer)
	{
		if(whichPlayer == PenteGameBoard.BLACKSTONE)
		{
			p1Captures.setText(Integer.toString(c));
		}else {
			p2Captures.setText(Integer.toString(c));
		}
		
		repaint();
	}
	
	public void setPlayerTurn(int whichPlayer)
	{
		if(whichPlayer == PenteGameBoard.BLACKSTONE)
		{
			whoseTurnField.setBackground(bStoneTextColor);
			whoseTurnField.setForeground(Color.WHITE);
			p1Name.getText();
			int cLoc = p1Name.getText().indexOf(":");
			String n = p1Name.getText().substring(cLoc + 2, p1Name.getText().length());
			
			
			whoseTurnField.setText("It is " + n + "'s turn now");
			
		}else {
			whoseTurnField.setBackground(Color.WHITE);
			whoseTurnField.setForeground(bStoneTextColor);
	
			int cLoc = p2Name.getText().indexOf(":");
			String n = p2Name.getText().substring(cLoc + 2, p2Name.getText().length());
			
			
			
			whoseTurnField.setText("It is " + n + "'s turn now");
		}
	}
	
	public void setGameBoard(PenteGameBoard gb)
	{
		myBoard = gb;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		if(myBoard != null) myBoard.startNewGame(false);
		
	}
}
