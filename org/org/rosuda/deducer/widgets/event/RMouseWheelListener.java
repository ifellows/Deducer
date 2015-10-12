package org.rosuda.deducer.widgets.event;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class RMouseWheelListener extends RListener implements MouseWheelListener{

	public void mouseWheelMoved(MouseWheelEvent arg0) {
		eventOccured(arg0,"mouseWheelMoved");
	}

}
