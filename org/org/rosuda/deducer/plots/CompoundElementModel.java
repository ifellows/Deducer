package org.rosuda.deducer.plots;

public abstract interface CompoundElementModel extends ElementModel{

	public abstract Object clone();
	public abstract PlottingElement[] getElements();
	
}
