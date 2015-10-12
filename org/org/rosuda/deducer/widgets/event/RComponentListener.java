package org.rosuda.deducer.widgets.event;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;


import org.rosuda.REngine.REXPReference;


public class RComponentListener extends RListener implements ComponentListener{

	private REXPReference fn;
	
	
	public void componentHidden(ComponentEvent arg0) {
		eventOccured(arg0,"componentHidden");
	}

	public void componentMoved(ComponentEvent arg0) {
		eventOccured(arg0,"componentMoved");
	}

	public void componentResized(ComponentEvent arg0) {
		eventOccured(arg0,"componentResized");
	}

	public void componentShown(ComponentEvent arg0) {
		eventOccured(arg0,"componentShown");
	}
	


}
