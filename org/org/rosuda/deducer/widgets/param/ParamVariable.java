package org.rosuda.deducer.widgets.param;

import org.rosuda.deducer.widgets.VariableSelectorWidget;
import org.w3c.dom.Element;

public class ParamVariable extends Param{

	protected String data;
	protected String variable;
	protected String defaultData;
	protected String defaultVariable;
	
	protected String format = FORMAT_VECTOR;
	
	protected String dataParamName = "data";
	
	public static String FORMAT_VECTOR = "data$variable";
	public static String FORMAT_DATA = "data[,'variable']";
	public static String FORMAT_VARIABLE = "=variable,data=data";
	public static String FORMAT_VARIABLE_CHARACTER = "='variable',data=data";
	
	public ParamVariable(){
		view = Param.VIEW_SINGLE_VARIABLE;
	}
	
	public ParamVariable(String name){
		this.name = name;
		this.title = name;
		view = Param.VIEW_SINGLE_VARIABLE;
	}
	
	public ParamVariable(String name, String data, String variable){
		this(name);
		this.data = data;
		this.variable = variable;
		this.defaultData = null;
		this.defaultVariable = null;
		view = Param.VIEW_SINGLE_VARIABLE;
	}
	
	public Object clone() {
		ParamVariable p = new ParamVariable();
		p.setName(this.getName());
		p.setTitle(this.getTitle());
		p.setViewType(this.getViewType());
		p.data = this.data;
		p.variable = this.variable;
		p.defaultData = this.defaultData;
		p.defaultVariable = this.defaultVariable;
		p.view = this.view;
		p.required = required;
		p.format = format;
		return p;
	}
	public Object getDefaultValue() {
		return defaultVariable;
	}
	public String[] getParamCalls() {
		String[] calls = new String[]{};
		if(getValue()!=null && !getValue().equals(getDefaultValue())){
			String val = "";
			if(getDefaultValue()==null || (getValue()!=null && !getDefaultValue().toString().equals(getValue().toString()))){
				if(getFormat().equals(FORMAT_VECTOR)){
					calls = new String[] {(name!=null ? (name + " = ") : "") + data +"$"+variable};
				}else if(getFormat().equals(FORMAT_DATA)){
					calls = new String[] {(name!=null ? (name + " = ") : "") + data +"[,'"+variable+"']"};
				}else if(getFormat().equals(FORMAT_VARIABLE)){
					calls = new String[] {(name!=null ? (name + " = ") : "") +variable ,dataParamName + " = "+ data};
				}else if(getFormat().equals(FORMAT_VARIABLE_CHARACTER)){
					calls = new String[] {(name!=null ? (name + " = ") : "") + "'" +variable+"'" , dataParamName + " = "+ data};
				}
			}else
				val ="";
		}else
			calls = new String[]{};
	return calls;
	}
	public Object getValue() {
		return variable;
	}
	public ParamWidget getView() {
		return null;
	}
	
	public ParamWidget getView(VariableSelectorWidget s) {
		return new ParamVariableWidget(this,s);
	}
	
	public boolean requiresVariableSelector(){
		return true;
	}
	
	public void setDefaultValue(Object defaultValue) {
		defaultVariable = (String)defaultValue;
	}
	public void setValue(Object value) {
		variable = (String)value;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getData() {
		return data;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	public String getVariable() {
		return variable;
	}
	public void setDefaultData(String defaultData) {
		this.defaultData = defaultData;
	}
	public String getDefaultData() {
		return defaultData;
	}
	public void setDefaultVariable(String defaultVariable) {
		this.defaultVariable = defaultVariable;
	}
	public String getDefaultVariable() {
		return defaultVariable;
	}
	
	public Element toXML(){
		Element e = super.toXML();
		if(variable!=null)
			e.setAttribute("variable", variable);
		if(defaultVariable!=null)
			e.setAttribute("defaultVariable", defaultVariable);
		if(data!=null)
			e.setAttribute("data", data);
		if(defaultData!=null)
			e.setAttribute("defaultData", defaultData);
		if(getFormat()!=null)
			e.setAttribute("format", getFormat());
		if(dataParamName!=null)
			e.setAttribute("dataParamName", dataParamName);		
		e.setAttribute("className", "org.rosuda.deducer.widgets.param.ParamVariable");
		return e;
	}
	
	public void setFromXML(Element node){
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.widgets.param.ParamLogical")){
			System.out.println("Error ParamLogical: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		super.setFromXML(node);
		if(node.hasAttribute("variable"))
			variable = node.getAttribute("variable");
		else
			variable = null;

		if(node.hasAttribute("defaultVariable"))
			defaultVariable = node.getAttribute("defaultVariable");
		else
			defaultVariable = null;
		
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
		return variable !=null && variable.length()>0 || defaultVariable!=null;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getFormat() {
		return format;
	}
}
