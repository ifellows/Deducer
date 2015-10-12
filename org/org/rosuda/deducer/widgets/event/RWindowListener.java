package org.rosuda.deducer.widgets.event;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class RWindowListener extends RListener implements WindowListener{

	public void windowActivated(WindowEvent arg0) {
		eventOccured(arg0,"windowActivated");
	}

	public void windowClosed(WindowEvent arg0) {
		eventOccured(arg0,"windowClosed");
	}

	public void windowClosing(WindowEvent arg0) {
		eventOccured(arg0,"windowClosing");
	}

	public void windowDeactivated(WindowEvent arg0) {
		eventOccured(arg0,"windowDeactivated");
	}

	public void windowDeiconified(WindowEvent arg0) {
		eventOccured(arg0,"windowDeiconified");
	}

	public void windowIconified(WindowEvent arg0) {
		eventOccured(arg0,"windowIconified");
	}

	public void windowOpened(WindowEvent arg0) {
		eventOccured(arg0,"windowOpened");
	}
	

}
