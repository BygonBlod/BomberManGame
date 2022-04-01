package Client;

import java.net.Socket;
/**
 * @author Antonin
 */

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import Server.GameChange;
import View.ViewBomberManGame;
import json.CreateJson;
import model.utils.AgentAction;

public class Client {

	private final String host;
	private final int port;
	String Id;
	private boolean start = false;
	private ClientListen listen;
	private ClientWrite write;
	private GameChange game;
	private ViewBomberManGame view;
	private boolean isConnected;
	private long time;
	private AgentAction action;

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
		this.Id = "";
		this.isConnected = false;
	}

	/**
	 * Se connect au serveur et lance les threads pour l'écriture et l'écoute
	 */
	public void start() {

		Socket socket = null;

		try {

			socket = new Socket(this.host, this.port);
			listen = new ClientListen(socket, this);
			write = new ClientWrite(socket, Id, this);
			listen.start();
			write.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ouvre une fenêtre pour que l'utilisateur puisse choisir son layout de jeu
	 */
	public void connect() {
		game = null;
		start = false;
		// façon pour choisir dossier
		JFileChooser choose = new JFileChooser(System.getProperty("user.dir") + "/layouts");

		choose.setDialogTitle("Selectionnez un layout pour votre jeu");
		choose.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("layout .lay", "lay");
		choose.addChoosableFileFilter(filter);
		int res = choose.showOpenDialog(null);
		if (res == JFileChooser.APPROVE_OPTION) {
			String layout = choose.getSelectedFile().getName();
			write.sendMessage(CreateJson.JsonSelect(Id, layout));

		}
	}

	/**
	 * change le jeu ou l'initialise et met à jour la vue
	 * 
	 * @param game
	 */
	public void changeGame(GameChange game) {
		if (!start) {
			time = (System.currentTimeMillis());
			this.game = game;
			if (view == null) {
				System.out.println("new view");
				view = new ViewBomberManGame(game, this);
			} else {
				view.getFrame().dispose();
				view = new ViewBomberManGame(game, this);
			}
			start = true;
		} else {
			this.game.set(game);
			view.update(this.game);
		}

	}

	/**
	 * arrête les threads du client
	 */
	public void deleteClient() {
		listen.interrupt();
		write.interrupt();
		System.exit(0);
	}

	/**
	 * envoie l'action fait par l'utilisateur avec son clavier
	 * 
	 * @param a
	 */
	public void setAction(AgentAction a) {
		long actualTime = (System.currentTimeMillis());
		action = a;
		if (actualTime - time >= 100) {// maximise à action tout les 100 millisecondes
			write.sendMessage(CreateJson.JsonAction(action));
			time = actualTime;
		}
	}

	public ClientListen getListen() {
		return listen;
	}

	public void setListen(ClientListen listen) {
		this.listen = listen;
	}

	public ClientWrite getWrite() {
		return write;
	}

	public void setWrite(ClientWrite write) {
		this.write = write;
	}

	public boolean isConnected() {
		return isConnected;
	}

	/**
	 * connect selon le boolean
	 * 
	 * @param isConnected
	 */
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
		if (isConnected) {
			System.out.println("est connecté");
			connect();
		}
	}

}