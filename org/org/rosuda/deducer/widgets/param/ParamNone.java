package org.rosuda.deducer.widgets.param;

import org.w3c.dom.Element;


public class ParamNone extends Param {

	final public static String VIEW_SEPERATOR = "org.rosuda.deducer.widgets.param.ParamSeperatorWidget";
	
	public ParamNone(){
		name = "";
		title = "";
		this.view = VIEW_SEPERATOR;//Param.VIEW_HIDDEN;
	}
	
	public ParamNone(String tit){
		name = tit;
		title = tit;
		this.view = VIEW_SEPERATOR;//Param.VIEW_HIDDEN;
	}
	
	public Object clone() {
		ParamNone p = new ParamNone(this.title);
		p.name = this.name;
		return p;
	}

	public String[] getParamCalls() {
		return new String[]{};
	}

	public void setValue(Object value) {
		
	}

	public Object getValue() {
		return null;
	}

	public void setDefaultValue(Object defaultValue) {
	}

	public Object getDefaultValue() {
		return null;
	}
	
	public Element toXML(){
		Element e = super.toXML();
		e.setAttribute("className", "org.rosuda.deducer.widgets.param.ParamNone");
		return e;
	}
	public void setFromXML(Element node){
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.widgets.param.ParamNone")){
			System.out.println("Error ParamNone: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		super.setFromXML(node);
	}

}
