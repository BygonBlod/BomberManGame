package model;

import java.util.Observable;

public abstract class Game extends Observable implements Runnable {
	protected int turn;
	protected int maxturn;
	protected boolean isRunning;
	protected Thread thread;
	protected long sleep = 500;

	public Game(int maxTurn) {
		maxturn = maxTurn;
	}

	public void launch() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	public void init() {
		turn = 0;
		isRunning = true;
		initializeGame();
	}

	protected abstract void initializeGame();

	public void step() {
		if (maxturn == 0) {
			isRunning = false;
			gameOver();
		} else {
			turn += 1;
			takeTurn();
			if (!gameContinue() | turn == maxturn) {
				isRunning = false;
				gameOver();
			}
			;
			setChanged();
			notifyObservers();
		}
	}

	public void run() {
		while (isRunning == true) {
			step();
			try {
				thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		thread.stop();
	}

	public int getMaxturn() {
		return maxturn;
	}

	public void setMaxturn(int maxturn) {
		this.maxturn = maxturn;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public long getSleep() {
		return sleep;
	}

	public void setSleep(long sleep) {
		this.sleep = sleep;
	}

	public void pause() {
		isRunning = false;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public abstract void gameOver();

	protected abstract boolean gameContinue();

	protected abstract void takeTurn();

	public abstract String getEnd();

}
