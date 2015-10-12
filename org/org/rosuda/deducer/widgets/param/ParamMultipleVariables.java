package org.rosuda.deducer.widgets.param;

import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.XMLHelper;
import org.rosuda.deducer.widgets.VariableSelectorWidget;
import org.w3c.dom.Element;

public class ParamMultipleVariables extends Param{
	protected String data;
	protected String[] variables = new String[]{};
	protected String defaultData;
	protected String[] defaultVariables = new String[]{};
	
	protected String format = FORMAT_DATA;
	
	protected String dataParamName = "data";
	
	public static String FORMAT_DATA = "data[,'variable']";
	public static String FORMAT_VARIABLE = "=d(variable),data=data";
	public static String FORMAT_VARIABLE_CHARACTER = "=c('variable'),data=data";
	
	public ParamMultipleVariables(){
		view = Param.VIEW_MULTI_VARIABLE;
		this.requiresVariableSelector=true;
		view = Param.VIEW_MULTI_VARIABLE;
	}
	
	public ParamMultipleVariables(String name){
		this.name = name;
		this.title = name;
		this.requiresVariableSelector=true;
		view = Param.VIEW_MULTI_VARIABLE;
	}
	
	public ParamMultipleVariables(String name, String data, String[] variables){
		this(name);
		this.data = data;
		this.variables = variables;
		this.defaultData = null;
		this.defaultVariables = new String[]{};
		this.requiresVariableSelector=true;
		view = Param.VIEW_MULTI_VARIABLE;
	}
	
	public Object clone() {
		ParamMultipleVariables p = new ParamMultipleVariables();
		p.setName(this.getName());
		p.setTitle(this.getTitle());
		p.setViewType(this.getViewType());
		p.data = this.data;
		
		String[] ar = new String[variables.length];
		for(int i=0;i<variables.length;i++)
			ar[i] = variables[i];
		p.variables = ar;
		
		p.defaultData = this.defaultData;
		
		ar = new String[defaultVariables.length];
		for(int i=0;i<defaultVariables.length;i++)
			ar[i] = defaultVariables[i];
		p.defaultVariables = ar;
		p.requiresVariableSelector = this.requiresVariableSelector;
		p.required = required;
		return p;
	}
	public Object getDefaultValue() {
		return defaultVariables;
	}
	public String[] getParamCalls() {
		String[] calls = new String[]{};
		if(getValue()!=null && !getValue().equals(getDefaultValue())){
			String val = "";
			if(getDefaultValue()==null || 
					(getValue()!=null && !getDefaultValue().toString().equals(getValue().toString()))){
				if(getFormat().equals(FORMAT_DATA)){
					calls = new String[] {(name!=null ? (name + " = ") : "") + 
							data +"[,"+Deducer.makeRCollection(variables,"c",true)+", drop=FALSE]"};
				}else if(getFormat().equals(FORMAT_VARIABLE)){
					calls = new String[] {(name!=null ? (name + " = ") : "") +
							Deducer.makeRCollection(variables,"d",false) ,  
							dataParamName + " = "+ data};
				}else if(getFormat().equals(FORMAT_VARIABLE_CHARACTER)){
					calls = new String[] {(name!=null ? (name + " = ") : "") +
							Deducer.makeRCollection(variables,"c",true) 
							, dataParamName + " = "+ data};
				}
			}else
				val ="";
		}else
			calls = new String[]{};
	return calls;
	}
	public Object getValue() {
		return variables;
	}
	public ParamWidget getView() {
		return null;
	}
	
	public ParamWidget getView(VariableSelectorWidget s) {
		return new ParamMultipleVariablesWidget(this,s);
	}
	
	public boolean requiresVariableSelector(){
		return true;
	}
	
	public void setDefaultValue(Object defaultValues) {
		defaultVariables = (String[])defaultValues;
	}
	public void setValue(Object values) {
		variables = (String[])values;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getData() {
		return data;
	}
	public void setVariables(String[] variables) {
		this.variables = variables;
	}
	public void setVariables(String variable) {
		this.variables = new String[]{variable};
	}
	public String[] getVariables() {
		return variables;
	}
	public void setDefaultData(String defaultData) {
		this.defaultData = defaultData;
	}
	public String getDefaultData() {
		return defaultData;
	}
	public void setDefaultVariable(String[] defaultVariables) {
		this.defaultVariables = defaultVariables;
	}
	public void setDefaultVariable(String defaultVariable) {
		this.defaultVariables = new String[]{defaultVariable};
	}
	public String[] getDefaultVariable() {
		return defaultVariables;
	}
	
	public Element toXML(){
		Element e = super.toXML();
		if(data!=null)
			e.setAttribute("data", data);
		if(defaultData!=null)
			e.setAttribute("defaultData", defaultData);
		if(getFormat()!=null)
			e.setAttribute("format", getFormat());
		if(dataParamName!=null)
			e.setAttribute("dataParamName", dataParamName);		
		e.setAttribute("className", "org.rosuda.deducer.widgets.param.ParamVariable");
		XMLHelper.appendCollection(e, variables, "variables");
		XMLHelper.appendCollection(e, defaultVariables, "defaultVariables");
		return e;
	}
	
	public void setFromXML(Element node){
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.widgets.param.ParamLogical")){
			System.out.println("Error ParamLogical: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		super.setFromXML(node);
		
		variables = XMLHelper.getChildCollection(node, "variables");
		defaultVariables = XMLHelper.getChildCollection(node, "defaultVariables");
		
		if(node.hasAttribute("data"))
			data = node.getAttribute("data");
		else
			data = null;
		
		if(node.hasAttribute("defaultData"))
			defaultData = node.getAttribute("defaultData");
		else
			defaultData = null;
		
		if(node.hasAttribute("format"))
			setFormat(node.getAttribute("format"));
		else
			setFormat(null);
		
		if(node.hasAttribute("dataParamName"))
			dataParamName = node.getAttribute("dataParamName");
		else
			dataParamName = null;
	}

	protected void setDataParamName(String dataParamName) {
		this.dataParamName = dataParamName;
	}

	protected String getDataParamName() {
		return dataParamName;
	}
	public boolean hasValidEntry(){
		return (variables !=null && variables.length>0) || 
		(defaultVariables!=null && defaultVariables.length>0);
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getFormat() {
		return format;
	}
}
