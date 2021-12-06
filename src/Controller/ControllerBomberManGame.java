package Controller;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import View.ViewBomberManGame;
import View.ViewCommand;
import model.BomberManGame;
import model.utils.AgentAction;

public class ControllerBomberManGame extends AbstractController {
	ViewBomberManGame view;
	ViewCommand view2;
	private String plateau;
	
	public ControllerBomberManGame(String p) {
		this.plateau=p;
		this.game=new BomberManGame(500,p);;
		try {
			view=new ViewBomberManGame(plateau,this);
			view2=new ViewCommand(this,view.getTaillex(),view.getTailley());
			game.addObserver(view);
			game.addObserver(view2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		game.launch();
	}
	public void moveBomberman(AgentAction action) {
		((BomberManGame) game).stratBomberman.setAction(action);;
		
	}
	@Override
	public void changeLvl() {
		pause();
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
			      //ControllerBomberManGame bomber=new ControllerBomberManGame(layout);
			      this.view.getFrame().dispose();
			      this.view2.getFrame().dispose();
			      this.plateau=layout;
			      
					this.game=new BomberManGame(500,layout);;
					try {
						view=new ViewBomberManGame(plateau,this);
						view2=new ViewCommand(this,view.getTaillex(),view.getTailley());
						game.addObserver(view);
						game.addObserver(view2);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					game.launch();
			    }
		
		
	}

}
