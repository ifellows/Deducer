package org.rosuda.deducer.widgets.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * An action listener with an R function callback
 * @author Ian
 *
 */
public class RActionListener extends RListener implements ActionListener{
	
	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		eventOccured(arg0,cmd);
	}

}
