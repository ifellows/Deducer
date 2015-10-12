package org.rosuda.deducer.widgets.event;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class RCaretListener extends RListener implements CaretListener {

	public void caretUpdate(CaretEvent arg0) {
		eventOccured(arg0,"caretUpdate");
	}

}
