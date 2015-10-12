package org.rosuda.deducer.widgets.event;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class RDocumentListener extends RListener implements DocumentListener{

	public void changedUpdate(DocumentEvent arg0) {
		eventOccured(arg0,"changedUpdate");
	}

	public void insertUpdate(DocumentEvent arg0) {
		eventOccured(arg0,"insertUpdate");
	}

	public void removeUpdate(DocumentEvent arg0) {
		eventOccured(arg0,"removeUpdate");
	}

}
