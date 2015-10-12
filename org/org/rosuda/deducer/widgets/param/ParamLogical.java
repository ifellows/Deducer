package org.rosuda.deducer.widgets.param;

import org.w3c.dom.Element;

public class ParamLogical extends Param{
	protected Boolean value;
	protected Boolean defaultValue;			//default	
	
	
	public ParamLogical(){
		name = "";
		title = "";
		value = null;
		defaultValue = null;
		view = VIEW_CHECK_BOX;
	}
	
	public ParamLogical(String nm){
		name = nm;
		title = nm;
		value = null;
		defaultValue = null;
		view = VIEW_CHECK_BOX;
	}
	
	public ParamLogical(String nm,boolean val){
		name = nm;
		title = nm;
		value = new Boolean(val);
		defaultValue = new Boolean(val);
		view = VIEW_CHECK_BOX;
	}
	
	public ParamLogical(String theName, String theTitle, String theView,
			Boolean theValue,Boolean theDefaultValue){
		name = theName;
		title = theTitle;
		view = theView;
		value = theValue;
		defaultValue = theDefaultValue;
		view = VIEW_CHECK_BOX;
	}
	/*
	public ParamWidget getView(){
		if(getViewType().equals(Param.VIEW_CHECK_BOX))
			return new ParamCheckBoxWidget(this);
		System.out.println("invalid view: " + getViewType());
		(new Exception()).printStackTrace();
		return null;
	}*/
	
	public Object clone(){
		ParamLogical p = new ParamLogical();
		p.setName(this.getName());
		p.setTitle(this.getTitle());
		p.setViewType(this.getViewType());
		if(value!=null)
			p.setValue(new Boolean(value.booleanValue()));
		if(defaultValue!=null)
			p.setDefaultValue(new Boolean(defaultValue.booleanValue()));
		p.required = required;
		return p;
	}
	
	public String[] getParamCalls(){
		String[] calls;
		if(getValue()!=null && !getValue().equals(getDefaultValue())){
			String val = "";
			if(getDefaultValue()==null || (getValue()!=null && !getDefaultValue().toString().equals(getValue().toString())))
				val = value.booleanValue() ? "TRUE" : "FALSE";
			else
				val ="";
			if(val.length()>0)
				calls = new String[]{(name!=null ? (name + " = ") : "")+val};
			else
				calls = new String[]{};
			
		}else
			calls = new String[]{};
	return calls;
	}
	
	public void setDefaultValue(Object defaultValue) {
		if(defaultValue instanceof Boolean || defaultValue ==null)
			this.defaultValue = (Boolean) defaultValue;
		else
			System.out.println("ParamBoolean: invalid setDefaultValue");
	}
	
	public void setDefaultValue(boolean value){
		this.defaultValue = new Boolean(value);
	}
	
	public Object getDefaultValue() {
		return defaultValue;
	}
	
	public void setValue(Object value) {
		if(value instanceof Boolean || value==null){
			this.value = (Boolean) value;
		}else{
			System.out.println("ParamNumeric: invalid setValue");
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	public void setValue(boolean value){
		this.value = new Boolean(value);
	}
	
	public Object getValue() {
		return value;
	}
	
	public Element toXML(){
		Element e = super.toXML();
		if(value!=null)
			e.setAttribute("value", value.toString());
		if(defaultValue!=null)
			e.setAttribute("defaultValue", defaultValue.toString());
		e.setAttribute("className", "org.rosuda.deducer.widgets.param.ParamLogical");
		return e;
	}
	
	public void setFromXML(Element node){
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.widgets.param.ParamLogical")){
			System.out.println("Error ParamLogical: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		super.setFromXML(node);
		if(node.hasAttribute("value"))
			value = new Boolean(node.getAttribute("value").equals("true"));
		else
			value = null;
		if(node.hasAttribute("defaultValue"))
			defaultValue = new Boolean(node.getAttribute("defaultValue").equals("true"));
		else
			defaultValue = null;
	}
	
	public boolean hasValidEntry(){
		return value!=null || defaultValue!=null;
	}
}
