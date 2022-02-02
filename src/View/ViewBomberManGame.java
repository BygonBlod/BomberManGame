package View;

import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import Controller.ControllerBomberManGame;
import model.BomberManGame;
import model.InputMap;
import model.utils.AgentAction;

public class ViewBomberManGame implements Observer, KeyListener {
	JFrame frame;
	PanelBomberman panel;
	int Taillex;
	int Tailley;
	ControllerBomberManGame controller;

	public ViewBomberManGame(String plateau, ControllerBomberManGame c) throws Exception {
		InputMap input = new InputMap(plateau);
		controller = c;
		frame = new JFrame("BomberMan");
		Taillex = input.getSizeX() * 50;
		Tailley = input.getSizeY() * 50;
		frame.setSize(Taillex, Tailley);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - Taillex / 2;
		int dy = 0;
		frame.setLocation(dx, dy);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// trouver solution pour ne pas arrêter le jeu mais couper
																// la connexion d'abord
		frame.addKeyListener(this);

		int dy2 = input.get_walls()[0].length;
		int dx2 = input.get_walls().length;
		panel = new PanelBomberman(dx2, dy2, input.get_walls(), input.getStart_breakable_walls(),
				input.getStart_agents());
		frame.add(panel);

		frame.setVisible(true);

	}

	public ViewBomberManGame(BomberManGame game, ControllerBomberManGame c) {
		controller = c;
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
		panel = new PanelBomberman(dx2, dy2, game.getWalls(), game.getBreakable_walls(), game.getAgents());
		frame.add(panel);

		frame.setVisible(true);

	}

	@Override
	public void update(Observable o, Object arg) {
		BomberManGame g = (BomberManGame) o;
		int dy2 = g.getWalls()[0].length;
		int dx2 = g.getWalls().length;
		frame.remove(panel);
		panel = new PanelBomberman(dx2, dy2, g.getWalls(), g.getBreakable_walls(), g.getAgents());
		panel.listInfoBombs = g.getListBomb();
		panel.listInfoItems = g.getListItem();
		frame.add(panel);
		frame.setVisible(true);
	}

	public void update(BomberManGame g) {
		if (g.getWalls() == null)
			System.out.println("merde");
		int dx2 = g.getWalls().length;
		int dy2 = g.getWalls()[0].length;
		frame.remove(panel);
		panel = new PanelBomberman(dx2, dy2, g.getWalls(), g.getBreakable_walls(), g.getAgents());
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

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			controller.moveBomberman(AgentAction.MOVE_RIGHT);
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)
			controller.moveBomberman(AgentAction.MOVE_LEFT);
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			controller.moveBomberman(AgentAction.MOVE_DOWN);
		else if (e.getKeyCode() == KeyEvent.VK_UP)
			controller.moveBomberman(AgentAction.MOVE_UP);
		else if (e.getKeyCode() == KeyEvent.VK_SPACE)
			controller.moveBomberman(AgentAction.PUT_BOMB);

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public JFrame getFrame() {
		return this.frame;
	}

}
