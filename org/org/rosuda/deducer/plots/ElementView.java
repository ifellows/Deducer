package org.rosuda.deducer.plots;

import javax.swing.JPanel;

public abstract class ElementView extends JPanel{
	
	public abstract void setModel(ElementModel el);
	public abstract ElementModel getModel();
	public abstract void updateModel();

}
