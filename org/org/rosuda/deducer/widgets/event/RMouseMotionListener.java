package org.rosuda.deducer.widgets.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class RMouseMotionListener extends RListener implements MouseMotionListener{

	public void mouseDragged(MouseEvent arg0) {
		eventOccured(arg0,"mouseDragged");
		
	}

	public void mouseMoved(MouseEvent arg0) {
		eventOccured(arg0,"mouseMoved");
		
	}

}
