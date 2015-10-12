package org.rosuda.deducer.widgets.event;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class RListSelectionListener extends RListener implements ListSelectionListener{

	public void valueChanged(ListSelectionEvent arg0) {
		eventOccured(arg0,"valueChanged");
	}

}
