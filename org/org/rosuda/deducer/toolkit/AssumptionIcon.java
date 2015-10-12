package org.rosuda.deducer.toolkit;

import java.awt.event.ActionListener;

public class AssumptionIcon extends IconButton {

	public AssumptionIcon(String iconUrl, String tooltip, ActionListener al,
			String cmd) {
		super(iconUrl, tooltip, al, cmd);
		this.setContentAreaFilled(false);
	}

}
