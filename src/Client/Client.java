package Client;

import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controller.ControllerBomberManGame;
import json.CreateJson;
import model.BomberManGame;

public class Client {

	private final String host;
	private final int port;
	private final String Id;
	private ClientListen listen;
	private ClientWrite write;
	private ControllerBomberManGame bomber;

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
				bomber = new ControllerBomberManGame(this);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void changeGame(BomberManGame game) {
		bomber.setGame(game);
	}

	public void deleteClient() {
		listen.interrupt();
		write.interrupt();
		System.exit(0);
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

	public ControllerBomberManGame getBomber() {
		return bomber;
	}

	public void setBomber(ControllerBomberManGame bomber) {
		this.bomber = bomber;
	}

}