package Main;

import Client.Client;
import Server.Server;

public class TchatMain {

	public static Server server;

	public static void main(String[] args) {

		if (args.length >= 1 && args[0].equals("-server")) {// lancer le serveur
			server = new Server(args.length == 2 ? Integer.parseInt(args[1]) : 4042);

			server.listening();
		} else {// lancer le client avec ou sans l'ip pour se connecter
			Client client = new Client(args.length >= 1 ? args[0] : "127.0.0.1", 4042);

			client.start();
		}

	}

}
