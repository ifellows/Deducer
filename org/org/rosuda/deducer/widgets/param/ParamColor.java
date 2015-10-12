package org.rosuda.deducer.widgets.param;

import java.awt.Color;

import org.rosuda.deducer.Deducer;
import org.w3c.dom.Element;

public class ParamColor extends Param{
	protected Color value;
	protected Color defaultValue;			//default	
	

	
	public ParamColor(){
		name = "";
		title = "";
		value = null;
		defaultValue = null;
		view = VIEW_COLOR;
	}
	
	public ParamColor(String nm){
		name = nm;
		title = nm;
		value = null;
		defaultValue = null;
		view = VIEW_COLOR;
	}
	
	public ParamColor(String nm, String val){
		name = nm;
		title = nm;
		value = Color.decode(val);
		defaultValue = Color.decode(val);
		view = VIEW_COLOR;
	}
	
	public ParamColor(String nm, Color val){
		name = nm;
		title = nm;
		value = val;
		defaultValue = val;
		view = VIEW_COLOR;
	}
	
	public ParamColor(String theName, String theTitle, String theView,
			String theValue,String theDefaultValue){
		name = theName;
		title = theTitle;
		view = theView;
		value = Color.decode(theValue);
		defaultValue = Color.decode(theDefaultValue);
	}
	
	public ParamColor(String theName, String theTitle, String theView,
			Color theValue,Color theDefaultValue){
		name = theName;
		title = theTitle;
		view = theView;
		value = theValue;
		defaultValue =theDefaultValue;
		view = VIEW_COLOR;
	}
	/*
	public ParamWidget getView(){
		if(getViewType().equals(Param.VIEW_COLOR))
			return new ParamColorWidget(this);
		System.out.println("invalid view");
		(new Exception()).printStackTrace();
		return null;
	}*/
	
	public Object clone(){
		Param p = new ParamColor();
		p.setName(this.name);
		p.setTitle(this.title);
		if(getValue()!=null){
			Color c = (Color) getValue();
			p.setValue(new Color(c.getRGB()));
		}
		if(getDefaultValue()!=null){
			Color c = (Color) getDefaultValue();
			p.setDefaultValue(new Color(c.getRGB()));
		}
		p.setViewType(this.getViewType());
		p.required = required;
		return p;
	}
	
	public String[] getParamCalls(){
		String[] calls;
		if(value!=null && !value.equals(defaultValue)){
			String val = "";
			if(getValue().toString().length()>0)
				val = "'#"+ Integer.toHexString(((Color)getValue()).getRGB()).substring(2)+"'";
			if(val.length()>0)
				calls = new String[]{(name!=null ? (name + " = ") : "")+val};
			else
				calls = new String[]{};
		}else
			calls = new String[]{};
		return calls;
	}

	public void setDefaultValue(Object defaultValue) {
		if(defaultValue instanceof Color || value ==null)
			this.defaultValue = (Color) defaultValue;
		else
			System.out.println("ParamColor: invalid setDefaultValue");
	}
	public Object getDefaultValue() {
		return defaultValue;
	}
	public void setValue(Object value) {
		if(value instanceof Color || value==null)
			this.value = (Color) value;
		else
			System.out.println("ParamColor: invalid setValue");
	}
	public Object getValue() {
		return value;
	}
	
	public Element toXML(){
		Element e = super.toXML();
		String val;
		if(value!=null){
			val = "#"+ Integer.toHexString(((Color)getValue()).getRGB()).substring(2);
			e.setAttribute("value", val);
		}
		if(defaultValue!=null){
			val = "#"+ Integer.toHexString(((Color)getDefaultValue()).getRGB()).substring(2);
			e.setAttribute("defaultValue", val);
		}
		e.setAttribute("className", "org.rosuda.deducer.widgets.param.ParamColor");
		return e;
	}
	
	public void setFromXML(Element node){
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.widgets.param.ParamColor")){
			System.out.println("Error ParamColor: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		super.setFromXML(node);
		if(node.hasAttribute("value"))
			value = Color.decode(node.getAttribute("value"));
		if(node.hasAttribute("defaultValue"))
			defaultValue = Color.decode(node.getAttribute("defaultValue"));
	}
	
	public boolean hasValidEntry(){
		return value!=null || defaultValue!=null;
	}
}
