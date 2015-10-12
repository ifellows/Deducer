package org.rosuda.deducer.widgets.event;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class RFocusListener extends RListener implements FocusListener{

	public void focusGained(FocusEvent arg0) {
		eventOccured(arg0,"focusGained");
	}

	public void focusLost(FocusEvent arg0) {
		eventOccured(arg0,"focusLost");
	}

}
