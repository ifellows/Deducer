package org.rosuda.deducer.widgets.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RMouseListener extends RListener implements MouseListener{

	public void mouseClicked(MouseEvent arg0) {
		eventOccured(arg0,"mouseClicked");
	}

	public void mouseEntered(MouseEvent arg0) {
		eventOccured(arg0,"mouseEntered");
	}

	public void mouseExited(MouseEvent arg0) {
		eventOccured(arg0,"mouseExited");
	}

	public void mousePressed(MouseEvent arg0) {
		eventOccured(arg0,"mousePressed");
	}

	public void mouseReleased(MouseEvent arg0) {
		eventOccured(arg0,"mouseReleased");
	}

}
