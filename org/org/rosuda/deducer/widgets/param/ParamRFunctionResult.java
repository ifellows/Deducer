package org.rosuda.deducer.widgets.param;

import org.rosuda.deducer.Deducer;
import org.w3c.dom.Element;

public class ParamRFunctionResult extends Param{
	protected RFunctionList functionList;
	protected String functionTitle;
	
	public ParamRFunctionResult(){
		name = "";
		title = "";
		view = VIEW_HIDDEN;
	}
	
	public ParamRFunctionResult(RFunctionList rfl,String funcTitle){
		name = "";
		title = "";
		view = VIEW_HIDDEN;
		functionList = rfl;
		functionTitle = funcTitle;
	}
	
	public Object clone(){
		ParamRFunctionResult p = new ParamRFunctionResult();
		p.setName(this.name);
		p.setTitle(this.title);
		p.setViewType(this.getViewType());
		p.required = required;
		p.functionTitle = this.functionTitle;
		p.functionList = this.functionList;
		return p;
	}
	
	public String[] getParamCalls(){
		String[] calls;
		if(getValue()!=null){
			String val = "";
			if(getValue().toString().length()>0){
				try{
					Double.parseDouble(getValue().toString());
					val = getValue().toString();
				}catch(Exception e){
					val = Deducer.addSlashes(getValue().toString()) ;
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
	}
	public Object getDefaultValue() {
		return null;
	}
	public void setValue(Object value) {
	}
	public Object getValue() {
		String[] titles = functionList.options;
		int i=0;
		while(i<titles.length){
			if(titles[i].equals(functionTitle))
				break;
			i++;
		}
		return functionList.assignTo[i];
	}
	
	public Element toXML(){
		Element e = super.toXML();
		if(functionTitle!=null)
			e.setAttribute("functionTitle", functionTitle);
		e.setAttribute("className", "org.rosuda.deducer.widgets.param.ParamFunctionResult");
		return e;
	}
	
	public void setFromXML(Element node){
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.widgets.param.ParamFunctionResult")){
			System.out.println("Error ParamAny: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		super.setFromXML(node);
		if(node.hasAttribute("functionTitle"))
			functionTitle = node.getAttribute("functionTitle");
		else
			functionTitle = null;
	}
	
	public boolean hasValidEntry(){
		return getValue()!=null && ((String) getValue()).length()>0;
	}

	public void setFunctionList(RFunctionList functionList) {
		this.functionList = functionList;
	}

	public RFunctionList getFunctionList() {
		return functionList;
	}

	public void setFunctionTitle(String functionTitle) {
		this.functionTitle = functionTitle;
	}

	public String getFunctionTitle() {
		return functionTitle;
	}
}
