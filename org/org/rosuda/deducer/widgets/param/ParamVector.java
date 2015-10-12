package org.rosuda.deducer.widgets.param;

import javax.swing.DefaultComboBoxModel;

import org.rosuda.deducer.Deducer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ParamVector extends Param{

	protected String[] value = new String[]{};
	protected String[] defaultValue = new String[]{};			//default	
	protected boolean numeric = true;
	
	
	public ParamVector(){
		name = "";
		title = "";
		view = Param.VIEW_VECTOR_BUILDER;
	}
	
	public ParamVector(String nm){
		name = nm;
		title = nm;
		view = Param.VIEW_VECTOR_BUILDER;
	}
	
	public ParamVector(String nm,String[] val){
		name = nm;
		title = nm;
		value = val;
		defaultValue = val;
		view = Param.VIEW_VECTOR_BUILDER;
	}
	
	public ParamVector(String theName, String theTitle, String theView,
			String[] theValue,String[] theDefaultValue,boolean isNumeric){
		name = theName;
		title = theTitle;
		view = theView;
		value = theValue;
		defaultValue = theDefaultValue;
		view = Param.VIEW_VECTOR_BUILDER;
		setNumeric(isNumeric);
	}
	
	public ParamWidget getView(){
		if(getViewType().equals(Param.VIEW_TWO_VALUE_ENTER))
			return new ParamTwoValueWidget(this);
		else if(getViewType().equals(Param.VIEW_VECTOR_BUILDER))
			return new ParamVectorBuilderWidget(this);
		System.out.println("invalid view");
		(new Exception()).printStackTrace();
		return null;
	}
	
	public Object clone(){
		ParamVector p = new ParamVector();
		p.setName(this.getName());
		p.setTitle(this.getTitle());
		if(this.getLowerBound()!=null)
			p.setLowerBound(new Double(this.getLowerBound().doubleValue()));
		if(this.getUpperBound()!=null)
			p.setUpperBound(new Double(this.getUpperBound().doubleValue()));
		p.setViewType(this.getViewType());
		if(getValue()!=null){
			String[] oldV = (String[]) getValue();
			String[] v = new String[oldV.length];
			for(int i=0;i<oldV.length;i++)
				v[i] = oldV[i];
			p.setValue(v);			
		}
		if(getDefaultValue()!=null){
			String[] oldV = (String[]) getDefaultValue();
			String[] v = new String[oldV.length];
			for(int i=0;i<oldV.length;i++)
				v[i] = oldV[i];
			p.setDefaultValue(v);			
		}
		p.setNumeric(isNumeric());
		p.required = required;
		return p;
	}
	
	public String[] getParamCalls(){
		String[] calls;
		if(getValue()!=null && !getValue().equals(getDefaultValue())){
			String val ="";
			String[] vecVals = (String[]) getValue();
			String[] dvecVals = (String[]) getDefaultValue();
			boolean identical = true;
			if(vecVals==null)
				identical = true;
			else if(dvecVals==null)
				identical=false;
			else if(vecVals.length!=dvecVals.length)
				identical=false;
			else
				for(int i=0;i<vecVals.length;i++)
					if(!vecVals[i].equals(dvecVals[i]))
						identical=false;
			if(!identical && vecVals!=null){			
				val = Deducer.makeRCollection(new DefaultComboBoxModel(vecVals), "c",false);
			}else
				val="";
			if(val.length()>0)
				calls = new String[]{(name!=null ? (name + " = ") : "")+val};
			else
				calls = new String[]{};
		}else
			calls = new String[]{};
	return calls;
	}
	
	public void setDefaultValue(Object defaultValue) {
		if(defaultValue instanceof String[] || defaultValue ==null)
			this.defaultValue = (String[]) defaultValue;
		else
			System.out.println("ParamVector: invalid setDefaultValue");
	}
	
	public Object getDefaultValue() {
		return defaultValue;
	}
	
	public void setValue(Object value) {
		if(value instanceof String[] || value==null)
			this.value = (String[]) value;
		else{
			System.out.println("ParamVector: invalid setValue");
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	public Object getValue() {
		return value;
	}

	public void setNumeric(boolean numeric) {
		this.numeric = numeric;
	}

	public boolean isNumeric() {
		return numeric;
	}

	public Element toXML(){
		Element e = super.toXML();
		Document doc = e.getOwnerDocument();
		Element node = doc.createElement("value");
		if(value!=null){
			for(int i=0;i<value.length;i++)
				node.setAttribute("element_"+i, value[i]);
		}
		e.appendChild(node);
		
		node = doc.createElement("defaultValue");
		if(defaultValue!=null){
			for(int i=0;i<defaultValue.length;i++)
				node.setAttribute("element_"+i, defaultValue[i]);
		}
		e.appendChild(node);
		e.setAttribute("numeric", numeric ? "true" : "false");
		e.setAttribute("className", "org.rosuda.deducer.widgets.param.ParamVector");
		return e;
	}
	
	public void setFromXML(Element node){
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.widgets.param.ParamVector")){
			System.out.println("Error ParamVector: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		super.setFromXML(node);
		value = new String[]{};
		defaultValue = new String[]{};
		Node vNode =node.getElementsByTagName("value").item(0);
		NamedNodeMap attr = vNode.getAttributes();
		if(attr.getLength()>0){
			value = new String[attr.getLength()];
			for(int i=0;i<attr.getLength();i++)
				value[i] = attr.item(i).getNodeValue();
		}
		
		vNode =node.getElementsByTagName("defaultValue").item(0);
		attr = vNode.getAttributes();
		if(attr.getLength()>0){
			defaultValue = new String[attr.getLength()];
			for(int i=0;i<attr.getLength();i++)
				defaultValue[i] = attr.item(i).getNodeValue();
		}
		
	}
	
	public boolean hasValidEntry(){
		return value!=null && value.length>0 || (defaultValue!=null && defaultValue.length>0);
	}
}
