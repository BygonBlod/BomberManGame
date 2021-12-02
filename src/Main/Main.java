package Main;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import Controller.ControllerBomberManGame;
import Controller.ControllerSimpleGame;
import View.ViewBomberManGame;
import View.ViewCommand;
import View.ViewSimpleGame;
import model.BomberManGame;
import model.SimpleGame;

public class Main {
    public static void main(String[] args) throws Exception {
        //SimpleGame test=new SimpleGame(40);
        //ControllerSimpleGame controller=new ControllerSimpleGame(test);
        //ViewBomberManGame view=new ViewBomberManGame();


        System.out.println(System.getProperty("user.dir"));
        String layout = System.getProperty("user.dir") + "/layouts/niveau3.lay";
        ControllerBomberManGame bomber = new ControllerBomberManGame(layout);


		//fa�on pour choisir dossier
		/*JFileChooser choose = new JFileChooser(
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
		    }*/
		
		
		
		
		
		
		
		
		
		//BomberManGame game=new BomberManGame(500,layout);
		
		//game.launch();
		/*game.step();
		System.out.println("------------------");
		game.step();*/
        //ViewSimpleGame view =new ViewSimpleGame();
        //ViewCommand view2=new ViewCommand(new ControllerSimpleGame);
        //test.init();
        //test.step();
        //test.launch();
        //test.run();
    }

}
