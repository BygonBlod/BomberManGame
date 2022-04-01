package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import json.CreateJson;

/**
 * 
 * @author Antonin
 *
 */
public class Server {

	private final int port;

	private ArrayList<ThreadedClient> clients = new ArrayList<>();
	private ArrayList<Partie> games = new ArrayList<>();

	public Server(int port) {
		this.port = port;
	}

	public List<ThreadedClient> getClients() {
		return clients;
	}

	public void listening() {

		ServerSocket serverSocket = null;

		try {

			serverSocket = new ServerSocket(this.port);
			int i = 0;

			while (!serverSocket.isClosed()) {

				Socket socket = serverSocket.accept();
				ThreadedClient threadedClient = new ThreadedClient(socket, this);

				clients.add(threadedClient);

				threadedClient.start();

			}

		} catch (SocketException e) {
			System.out.println("Le serveur à couper sa connection");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void broadcast(String message, Socket s) {
		for (ThreadedClient threadedClient : this.clients) {
			if (threadedClient.getSocket() != s) {
				threadedClient.sendMessage(message);
			}
		}
	}

	public void removeClient(ThreadedClient threadedClient) {
		broadcast(CreateJson.JsonTchat("[SERVER]", threadedClient.getNameClient() + " s'est déconnecter"),
				threadedClient.getSocket());// envoie un message aux autres joueurs que ce joueurs est déconnecter
		threadedClient.interrupt();
		this.clients.remove(threadedClient);

	}

	/**
	 * si la partie existe il ajoute le joueur a cette partie sinon il l'a créer
	 * 
	 * @param lvl
	 * @param client
	 */
	public void addGame(String lvl, ThreadedClient client) {
		boolean exist = false;
		for (Partie game : games) {
			if (lvl.equals(game.name) && !game.isFool()) {
				exist = true;
				game.addGamer(client);
			}
		}
		if (!exist) {
			Partie newPart = new Partie(lvl, this);
			newPart.addGamer(client);
			games.add(newPart);
		}
	}

	public void removeParty(Partie partie) {
		if (games.contains(partie)) {
			games.remove(partie);
		}

	}

}