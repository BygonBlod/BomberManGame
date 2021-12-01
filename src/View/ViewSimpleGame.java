package View;

import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Game;


public class ViewSimpleGame implements Observer  {
	JFrame frame;
	JLabel nbTurn;
	
	public ViewSimpleGame() {
		frame=new JFrame("Game");
		int x=700;
		int y=700;
		frame.setSize(x,y);
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint=ge.getCenterPoint();
	    int dx=centerPoint.x - x/ 2 ;
		int dy=centerPoint.y - y/2;
		frame.setLocation(dx,dy);
		nbTurn= new JLabel("turn: ", JLabel.CENTER);
		frame.getContentPane().add(nbTurn);
		frame.setVisible(true);
		
	}

	@Override
	public void update(Observable o, Object arg) {
		Game game=(Game) o;
		nbTurn.setText("turn :"+game.getTurn());
		
	}

}
