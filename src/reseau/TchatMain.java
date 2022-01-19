package reseau;

public class TchatMain {

    public static Server server;

    public static void main(String[] args) {

        if (args.length >= 1 && args[0].equals("-server")){
            server = new Server(args.length == 2 ? Integer.parseInt(args[1]) : 4042);

            server.listening();
        } else {
            Client client = new Client("127.0.0.1",4042,args.length >= 1 ? args[0]:"Bob");

            client.start();
        }

    }

}
