package org.rosuda.deducer.widgets.param;
import javax.swing.JLabel;


public abstract class ParamWidget extends javax.swing.JPanel{

	protected JLabel label;
	protected Param model;
	public static final int leftPos = 80;
	
	public ParamWidget() {
		super();
	}
	
	public ParamWidget(Param p){
		super();
	}
	
	public abstract void setModel(Param p) ;
	
	public abstract void updateModel();
	
	public Param getModel(){
		updateModel();
		return model;
	}
}
