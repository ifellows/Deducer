package org.rosuda.deducer.plots;

import java.util.Vector;

import org.w3c.dom.Element;


public interface ElementModel {

	public abstract String getCall();
	public abstract String checkValid();
	public abstract String getType();
	public abstract ElementView getView();
	public abstract Vector getParams();
	public abstract String getData();
	public abstract Object clone();
	public abstract Element toXML();
	public abstract void setFromXML(Element node);
}
