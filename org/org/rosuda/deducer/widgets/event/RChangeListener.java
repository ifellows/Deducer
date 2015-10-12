package org.rosuda.deducer.widgets.event;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RChangeListener extends RListener implements ChangeListener{

	public void stateChanged(ChangeEvent arg0) {
		eventOccured(arg0,"stateChanged");
	}

}
