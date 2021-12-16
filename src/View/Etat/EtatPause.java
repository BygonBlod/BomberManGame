package View.Etat;

import View.ViewCommand;

public class EtatPause implements Etat {
	ViewCommand vc;
	
	public EtatPause(ViewCommand v) {
		this.vc=v;
	}

	@Override
	public void play() {
		vc.playButton.setEnabled(false);
		vc.pauseButton.setEnabled(true);
		vc.stepButton.setEnabled(false);
		vc.etat=new EtatPlay(vc);
		
	}

	@Override
	public void pause() {
		System.out.println("vous êtes déjà en pause");
		
	}

	@Override
	public void restart() {
		vc.playButton.setEnabled(false);
		vc.stepButton.setEnabled(false);
		vc.pauseButton.setEnabled(true);
		
	}

}
