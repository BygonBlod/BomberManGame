package Client;

import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import Server.GameChange;
import View.ViewBomberManGame;
import json.CreateJson;
import model.utils.AgentAction;

public class Client {

	private final String host;
	private final int port;
	private final String Id;
	private boolean start = false;
	private ClientListen listen;
	private ClientWrite write;
	private GameChange game;
	private ViewBomberManGame view;

	public Client(String host, int port, String id) {
		this.host = host;
		this.port = port;
		this.Id = id;
	}

	public void start() {

		Socket socket = null;

		try {

			socket = new Socket(this.host, this.port);
			listen = new ClientListen(socket, this);
			write = new ClientWrite(socket, Id, this);
			listen.start();
			write.start();

			// fa�on pour choisir dossier
			JFileChooser choose = new JFileChooser(System.getProperty("user.dir") + "/layouts");

			choose.setDialogTitle("Selectionnez un layout pour votre jeu");
			choose.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("layout .lay", "lay");
			choose.addChoosableFileFilter(filter);
			int res = choose.showOpenDialog(null);
			if (res == JFileChooser.APPROVE_OPTION) {
				String layout = choose.getSelectedFile().getPath();
				write.sendMessage(CreateJson.JsonSelect(Id, choose.getSelectedFile().getPath()));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void changeGame(GameChange game) {
		if (!start) {
			this.game = game;
			view = new ViewBomberManGame(game, this);
			start = true;
		} else {
			this.game.set(game);
			view.update(this.game);
		}

	}

	public void deleteClient() {
		listen.interrupt();
		write.interrupt();
		System.exit(0);
	}

	public void setAction(AgentAction action) {
		write.sendMessage(CreateJson.JsonAction(action));
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

}