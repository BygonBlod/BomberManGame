package Controller;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import View.ViewBomberManGame;
import View.ViewCommand;
import model.BomberManGame;
import model.utils.AgentAction;
import reseau.Client;

public class ControllerBomberManGame extends AbstractController {
	ViewBomberManGame view;
	ViewCommand view2;
	private String plateau;
	private Client client;

	public ControllerBomberManGame(String p, Client c) {
		this.plateau = p;
		this.client = c;
		this.game = new BomberManGame(p);
		;
		try {

			view = new ViewBomberManGame(plateau, this);
			view2 = new ViewCommand(this, view.getTaillex(), view.getTailley());
			game.addObserver(view);
			game.addObserver(view2);

		} catch (Exception e) {
			e.printStackTrace();
		}
		game.launch();
	}

	public ControllerBomberManGame(Client c) {
		this.client = c;
	}

	public void moveBomberman(AgentAction action) {
		((BomberManGame) game).stratBomberman.setAction(action);

	}

	@Override
	public void changeLvl() {
		pause();
		JFileChooser choose = new JFileChooser(System.getProperty("user.dir") + "/layouts");

		choose.setDialogTitle("Selectionnez un layout pour votre jeu");
		choose.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("layout .lay", "lay");
		choose.addChoosableFileFilter(filter);
		int res = choose.showOpenDialog(null);
		if (res == JFileChooser.APPROVE_OPTION) {
			String layout = choose.getSelectedFile().getPath();
			// ControllerBomberManGame bomber=new ControllerBomberManGame(layout);
			this.view.getFrame().dispose();
			this.view2.getFrame().dispose();
			this.plateau = layout;

			this.game = new BomberManGame(layout);
			;
			try {
				view = new ViewBomberManGame(plateau, this);
				view2 = new ViewCommand(this, view.getTaillex(), view.getTailley());
				game.addObserver(view);
				game.addObserver(view2);

			} catch (Exception e) {
				e.printStackTrace();
			}
			game.launch();
		}
	}

	public void setGame(BomberManGame game) {
		super.setGame(game);
		view = new ViewBomberManGame(game, this);
		game.addObserver(view);
	}

}
