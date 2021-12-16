package View.Etat;

import View.ViewCommand;

public class EtatPlay  implements Etat{
	ViewCommand vc;
	
	public EtatPlay(ViewCommand v) {
		this.vc=v;
	}

	@Override
	public void play() {
		System.out.println("vous êtes déjà en play");
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		vc.playButton.setEnabled(true);
		vc.pauseButton.setEnabled(false);
		vc.stepButton.setEnabled(true);
		vc.etat=new EtatPause(vc);
		
	}

	@Override
	public void restart() {
		vc.playButton.setEnabled(false);
		vc.stepButton.setEnabled(false);
		vc.pauseButton.setEnabled(true);
		
	}

}
