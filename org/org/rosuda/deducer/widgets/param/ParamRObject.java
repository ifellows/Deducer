package org.rosuda.deducer.widgets.param;

import org.rosuda.deducer.Deducer;
import org.w3c.dom.Element;

public class ParamRObject extends Param{
	protected String value;
	protected String defaultValue;			//default	
	protected String rObjectClass = null;
	protected boolean isQuoted = false;
	
	public ParamRObject(){
		name = "";
		title = "";
		value = "";
		defaultValue = "";
		view = VIEW_ROBJECT_COMBO;
	}
	
	public ParamRObject(String nm){
		name = nm;
		title = nm;
		value = "";
		defaultValue = "";
		view = VIEW_ROBJECT_COMBO;
	}
	
	public ParamRObject(String nm,String value){
		name = nm;
		title = nm;
		this.value = value;
		defaultValue = value;
		view = VIEW_ROBJECT_COMBO;		
	}
	
	public ParamRObject(String theName, String theTitle, String theView,
			String theValue,String theDefaultValue){
		name = theName;
		title = theTitle;
		view = theView;
		value = theValue;
		defaultValue = theDefaultValue;
		view = VIEW_ROBJECT_COMBO;
	}
	/*
	public ParamWidget getView(){
		return new ParamTextFieldWidget(this);
	}*/
	
	public Object clone(){
		ParamRObject p = new ParamRObject();
		p.setName(this.name);
		p.setTitle(this.title);
		p.setValue(this.value);
		p.setDefaultValue(this.defaultValue);
		p.setViewType(this.getViewType());
		p.required = required;
		p.rObjectClass = this.rObjectClass;
		p.isQuoted = this.isQuoted;
		return p;
	}
	
	public String[] getParamCalls(){
		String[] calls;
		if(value!=null && !value.equals(defaultValue)){
			String val = "";
			if(value.toString().length()>0){
				try{
					Double.parseDouble(value.toString());
					if(!defaultValue.toString().equals(value.toString()))
						val = value.toString();
					else
						val = "";
				}catch(Exception e){
					val = Deducer.addSlashes(value.toString()) ;
				}
			}
			if(val.length()>0)
				calls = new String[]{ 
					(name!=null ? (name + " = ") : "")+
					(isQuoted ? "'" : "")+
					val+
					(isQuoted ? "'":"")};
			else
				calls = new String[]{};
			
		}else
			calls = new String[]{};
		return calls;
	}

	public void setDefaultValue(Object defaultValue) {
		if(defaultValue instanceof String || value==null)
			this.defaultValue = (String) defaultValue;
		else
			System.out.println("ParamAny: invalid setDefaultValue");
	}
	public Object getDefaultValue() {
		return defaultValue;
	}
	public void setValue(Object value) {
		if(value instanceof String || value==null)
			this.value = (String) value;
		else
			System.out.println("ParamRObject: invalid setValue");
	}
	public Object getValue() {
		return value;
	}
	
	public Element toXML(){
		Element e = super.toXML();
		if(value!=null)
			e.setAttribute("value", value);
		e.setAttribute("isQuoted", isQuoted ? "true":"false");
		if(defaultValue!=null)
			e.setAttribute("defaultValue", defaultValue);
		if(rObjectClass!=null)
			e.setAttribute("rObjectClass", defaultValue);
		e.setAttribute("className", "org.rosuda.deducer.widgets.param.ParamAny");
		return e;
	}
	
	public void setFromXML(Element node){
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.widgets.param.ParamAny")){
			System.out.println("Error ParamAny: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		super.setFromXML(node);
		if(node.hasAttribute("value"))
			value = node.getAttribute("value");
		else
			value = null;
		if(node.hasAttribute("defaultValue"))
			defaultValue = node.getAttribute("defaultValue");
		else
			defaultValue = null;
		if(node.hasAttribute("rObjectClass"))
			rObjectClass = node.getAttribute("rObjectClass");
		else
			rObjectClass = null;
		if(node.hasAttribute("isQuoted"))
			isQuoted = node.getAttribute("isQuoted").equals("true");
		else
			isQuoted = false;
	}
	
	public boolean hasValidEntry(){
		return value!=null && value.length()>0 || !"".equals(defaultValue);
	}

	public void setRObjectClass(String rObjectClass) {
		this.rObjectClass = rObjectClass;
	}

	public String getRObjectClass() {
		return rObjectClass;
	}

	public void setQuoted(boolean isQuoted) {
		this.isQuoted = isQuoted;
	}

	public boolean isQuoted() {
		return isQuoted;
	}
}
