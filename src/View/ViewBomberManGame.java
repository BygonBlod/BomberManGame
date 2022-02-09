package View;

import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import Client.Client;
import Server.GameChange;
import model.BomberManGame;
import model.utils.AgentAction;

public class ViewBomberManGame implements Observer, KeyListener {
	JFrame frame;
	PanelBomberman panel;
	int Taillex;
	int Tailley;
	Client client;

	public ViewBomberManGame(GameChange game, Client c) {
		client = c;
		frame = new JFrame("BomberMan");
		Taillex = game.getWalls().length * 50;
		Tailley = game.getWalls()[0].length * 50;
		frame.setSize(Taillex, Tailley);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - Taillex / 2;
		int dy = 0;
		frame.setLocation(dx, dy);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);

		int dy2 = game.getWalls()[0].length;
		int dx2 = game.getWalls().length;
		panel = new PanelBomberman(dx2, dy2, game.getWalls(), game.getBreakable_walls(), game.getAgents(),
				game.getEnd());
		frame.add(panel);

		frame.setVisible(true);

	}

	@Override
	public void update(Observable o, Object arg) {
		BomberManGame g = (BomberManGame) o;
		int dy2 = g.getWalls()[0].length;
		int dx2 = g.getWalls().length;
		frame.remove(panel);
		panel = new PanelBomberman(dx2, dy2, g.getWalls(), g.getBreakable_walls(), g.getAgents(), g.getEnd());
		panel.listInfoBombs = g.getListBomb();
		panel.listInfoItems = g.getListItem();
		frame.add(panel);
		frame.setVisible(true);
	}

	public void update(BomberManGame g) {
		int dx2 = g.getWalls().length;
		int dy2 = g.getWalls()[0].length;
		frame.remove(panel);
		panel = new PanelBomberman(dx2, dy2, g.getWalls(), g.getBreakable_walls(), g.getAgents(), g.getEnd());
		panel.listInfoBombs = g.getListBomb();
		panel.listInfoItems = g.getListItem();
		frame.add(panel);
		frame.setVisible(true);
	}

	public void update(GameChange g) {
		int dx2 = g.getWalls().length;
		int dy2 = g.getWalls()[0].length;
		frame.remove(panel);
		panel = new PanelBomberman(dx2, dy2, g.getWalls(), g.getBreakable_walls(), g.getAgents(), g.getEnd());
		panel.listInfoBombs = g.getListBomb();
		panel.listInfoItems = g.getListItem();
		frame.add(panel);
		frame.setVisible(true);
	}

	public int getTaillex() {
		return Taillex;
	}

	public int getTailley() {
		return Tailley;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	/*
	 * @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() ==
	 * KeyEvent.VK_RIGHT) controller.moveBomberman(AgentAction.MOVE_RIGHT); else if
	 * (e.getKeyCode() == KeyEvent.VK_LEFT)
	 * controller.moveBomberman(AgentAction.MOVE_LEFT); else if (e.getKeyCode() ==
	 * KeyEvent.VK_DOWN) controller.moveBomberman(AgentAction.MOVE_DOWN); else if
	 * (e.getKeyCode() == KeyEvent.VK_UP)
	 * controller.moveBomberman(AgentAction.MOVE_UP); else if (e.getKeyCode() ==
	 * KeyEvent.VK_SPACE) controller.moveBomberman(AgentAction.PUT_BOMB);
	 * 
	 * }
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			client.setAction(AgentAction.MOVE_RIGHT);
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)
			client.setAction(AgentAction.MOVE_LEFT);
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			client.setAction(AgentAction.MOVE_DOWN);
		else if (e.getKeyCode() == KeyEvent.VK_UP)
			client.setAction(AgentAction.MOVE_UP);
		else if (e.getKeyCode() == KeyEvent.VK_SPACE)
			client.setAction(AgentAction.PUT_BOMB);

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public JFrame getFrame() {
		return this.frame;
	}

}
