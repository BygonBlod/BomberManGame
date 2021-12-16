package View;

import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Controller.AbstractController;
import Controller.ControllerSimpleGame;
import View.Etat.*;
import model.Game;

public class ViewCommand implements Observer {
	JFrame jFrame;
	AbstractController controller;
	JLabel labelTurn;
	JLabel labelEnd;
	public final JButton restartButton;
	public final JButton playButton;
	public final JButton pauseButton;
	public final JButton stepButton;
	public Etat etat;
	
	
	public ViewCommand(AbstractController contro,int viewx,int viewy) {
		etat=new EtatPlay(this);
		controller=contro;
		jFrame=new JFrame();
		int x=viewx;
		int y=300;
		jFrame.setSize(x,y);
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint=ge.getCenterPoint();
	    int dx=centerPoint.x - viewx/ 2 ;
		int dy=viewy+20;
		jFrame.setLocation(dx,dy);
		jFrame.setLayout(new GridLayout(2,1));
		
		//haut
		JPanel panel1=new JPanel();
		GridLayout g1=new GridLayout(1,4);
		panel1.setLayout(g1);
		Icon restartIcon=new ImageIcon("icons/icon_restart.png");
		restartButton=new JButton(restartIcon);
		Icon playIcon=new ImageIcon("icons/icon_play.png");
		playButton=new JButton(playIcon);
		Icon stepIcon=new ImageIcon("icons/icon_step.png");
		stepButton=new JButton(stepIcon);
		Icon pauseIcon=new ImageIcon("icons/icon_pause.png");
		pauseButton=new JButton(pauseIcon);
		playButton.setEnabled(false);
		stepButton.setEnabled(false);
		panel1.add(restartButton);
		panel1.add(playButton);
		panel1.add(stepButton);
		panel1.add(pauseButton);
		jFrame.add(panel1);
		
		restartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				//System.out.println("restart");
				controller.restart();
				etat.restart();
			}
		});
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				//System.out.println("play");
				controller.play();
				etat.play();
				
			}
		});
		stepButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				//System.out.println("step");
				controller.step();
			}
		});
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				//System.out.println("pause");
				controller.pause();
				etat.pause();
			}
		});
		
		
		//bas
		JPanel panel2=new JPanel();
		GridLayout g2=new GridLayout(1,2);
		panel2.setLayout(g2);
		
		final JSlider slider=new JSlider(JSlider.HORIZONTAL,0,10,(int)controller.getGame().getSleep()/1000);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintTrack(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				controller.setSpeed(slider.getValue());
				
			}
			
		});
		
		panel2.add(slider);
		labelTurn=new JLabel("turn: 0",JLabel.CENTER);
		//panel2.add(labelTurn);
		jFrame.add(panel2);
		
		JPanel panel3=new JPanel();
		GridLayout g3=new GridLayout(2,1);
		panel3.setLayout(g3);
		panel3.add(labelTurn);
		labelEnd=new JLabel("",JLabel.CENTER);
		panel3.add(labelEnd);
		jFrame.add(panel3);
		
		JButton buttonChangeLvl=new JButton("Changer niveau");
		jFrame.add(buttonChangeLvl);
		buttonChangeLvl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				System.out.println("changement de niveau");
				playButton.setEnabled(true);
				pauseButton.setEnabled(false);
				stepButton.setEnabled(true);
				controller.changeLvl();
			}
		});
		
		//affiche la fenêtre
		jFrame.setVisible(true);
		
		
		
		
	}


	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		Game game=(Game) o;
		labelTurn.setText("turn :"+game.getTurn());
		labelEnd.setText(game.getEnd());
		
		
	}
	public JFrame getFrame() {
		return this.jFrame;
	}

}