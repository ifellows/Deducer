package org.rosuda.deducer.widgets.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RKeyListener extends RListener implements KeyListener{

	public void keyPressed(KeyEvent arg0) {
		eventOccured(arg0,"keyPressed");
	}

	public void keyReleased(KeyEvent arg0) {
		eventOccured(arg0,"keyReleased");
	}

	public void keyTyped(KeyEvent arg0) {
		eventOccured(arg0,"keyTyped");
	}

}
