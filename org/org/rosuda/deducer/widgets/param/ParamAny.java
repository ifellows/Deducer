package org.rosuda.deducer.widgets.param;

import org.rosuda.deducer.Deducer;
import org.w3c.dom.Element;

public class ParamAny extends Param{
	
	protected String value;
	protected String defaultValue;			//default	
	
	public ParamAny(){
		name = "";
		title = "";
		value = "";
		defaultValue = "";
		view = VIEW_ENTER_LONG;
	}
	
	public ParamAny(String nm){
		name = nm;
		title = nm;
		value = "";
		defaultValue = "";
		view = VIEW_ENTER_LONG;
	}
	
	public ParamAny(String nm,String value){
		name = nm;
		title = nm;
		this.value = value;
		defaultValue = value;
		view = VIEW_ENTER_LONG;		
	}
	
	public ParamAny(String theName, String theTitle, String theView,
			String theValue,String theDefaultValue){
		name = theName;
		title = theTitle;
		view = theView;
		value = theValue;
		defaultValue = theDefaultValue;
		view = VIEW_ENTER_LONG;
	}
	/*
	public ParamWidget getView(){
		return new ParamTextFieldWidget(this);
	}*/
	
	public Object clone(){
		ParamAny p = new ParamAny();
		p.setName(this.name);
		p.setTitle(this.title);
		p.setValue(this.value);
		p.setDefaultValue(this.defaultValue);
		p.setViewType(this.getViewType());
		p.required = required;
		if(this.getOptions()!=null){
			String[] v = new String[this.getOptions().length];
			for(int i=0;i<this.getOptions().length;i++)
				v[i] = this.getOptions()[i];
			p.setOptions(v);
		}
		if(this.getLabels()!=null){
			String[] v = new String[this.getLabels().length];
			for(int i=0;i<this.getLabels().length;i++)
				v[i] = this.getLabels()[i];
			p.setLabels(v);
		}
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
				calls = new String[]{ (name!=null ? (name + " = ") : "")+val};
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
			System.out.println("ParamAny: invalid setValue");
	}
	public Object getValue() {
		return value;
	}
	
	public Element toXML(){
		Element e = super.toXML();
		if(value!=null)
			e.setAttribute("value", value);
		if(defaultValue!=null)
			e.setAttribute("defaultValue", defaultValue);
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
	}
	
	public boolean hasValidEntry(){
		return value!=null && value.length()>0 || defaultValue!=null;
	}
}
