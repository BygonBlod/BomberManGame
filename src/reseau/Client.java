package reseau;
import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controller.ControllerBomberManGame;

public class Client {

    private final String host;
    private final int port;
    private final String Id;
    private ClientListen listen;
    private ClientWrite write;
	


    public Client(String host, int port,String id){
        this.host = host;
        this.port = port;
        this.Id=id;
    }

    public void start() {

        Socket socket = null;

        try {

            socket = new Socket(this.host, this.port);
            listen=new ClientListen(socket,this);
            write=new ClientWrite(socket,Id,this);
            listen.start();
            write.start();
         
            //fa�on pour choisir dossier
    		JFileChooser choose = new JFileChooser(
    		       System.getProperty("user.dir")+"/layouts"
    		    );
    		    
    		    choose.setDialogTitle("Selectionnez un layout pour votre jeu");
    		    choose.setAcceptAllFileFilterUsed(false);
    		    FileNameExtensionFilter filter = new FileNameExtensionFilter("layout .lay", "lay");
    		    choose.addChoosableFileFilter(filter);
    		    int res = choose.showOpenDialog(null);
    		    if (res == JFileChooser.APPROVE_OPTION) {
    		      String layout=choose.getSelectedFile().getPath();
    		      ControllerBomberManGame bomber=new ControllerBomberManGame(layout);
    		    }
            
        } catch(Exception e){
            e.printStackTrace();
        }

    }
    
    public void deleteClient() {
    	listen.interrupt();
    	write.interrupt();
    	System.exit(0);
    }

}