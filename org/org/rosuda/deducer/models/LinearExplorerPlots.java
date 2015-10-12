package org.rosuda.deducer.models;

import javax.swing.JFrame;

public class LinearExplorerPlots extends GLMExplorerPlots {
	private boolean hccm = false;
	public LinearExplorerPlots(JFrame frame, GLMModel mod, RModel rmod,boolean hetero) {
		super(frame, mod, rmod);
		hccm=hetero;
		if(hccm){
			confInt.setEnabled(false);
		}
	}
	
	protected void initGUI(){
		super.initGUI();
		scaled.setVisible(false);
	}
	
}
