package org.rosuda.deducer.widgets.param;

import java.awt.Dimension;

public class ParamNullWidget extends ParamWidget{

	public ParamNullWidget(Param p){
		super();
		this.setSize(new Dimension(0,0));
		this.setPreferredSize(new Dimension(0,0));
		this.setMaximumSize(new Dimension(0,0));
	}
	public void setModel(Param p) {}
	public void updateModel() {}
}
