package org.rosuda.deducer.plots;

import java.awt.Color;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.rosuda.deducer.Deducer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Aes {
	
	final public static String DATA_ANY = "any";
	final public static String DATA_NONE = "none";
	final public static String DATA_NUMERIC = "numeric";
	//final public static String DATA_CHARACTER = "char";
	final public static String DATA_COLOUR = "col";
	final public static String DATA_LINE= "ln";
	final public static String DATA_SHAPE= "shape";
	final public static String DATA_NUMERIC_BOUNDED = "bounded";
	
	public boolean preferNumeric = false;
	public boolean preferCategorical = false;
	
	public String name;					//name of aes
	public String title;				//title for aes
	
	public String variable;
	public String defaultVariable;	
	
	public Object value;
	public Object defaultValue;			//default	
	
	public boolean required = false;	//is required
	
	public boolean defaultUseVariable = false;//view should default to variable view (rather than value)
	public boolean useVariable;				//is toggled to variable;

	public String dataType;	//Type of data the parameter takes
	public Double lowerBound ;			//If bounded, the lower bound
	public Double upperBound ;			//if bounded, the upper bound
	//public Vector charValues;			//if character, a vector of the valid values
	
	public Object clone(){
		Aes a = new Aes();
		a.name = this.name;
		a.title = this.title;
		a.variable = this.variable;
		a.defaultVariable = this.defaultVariable;
		a.required = this.required;
		a.defaultUseVariable = this.defaultUseVariable;
		a.useVariable = this.useVariable;
		a.dataType = this.dataType;
		a.preferCategorical = this.preferCategorical;
		a.preferNumeric = this.preferNumeric;
		if(this.lowerBound!=null)
			a.lowerBound = new Double(this.lowerBound.doubleValue());
		if(this.upperBound!=null)
			a.upperBound = new Double(this.upperBound.doubleValue());
		if(dataType.equals(Aes.DATA_NUMERIC) || dataType.equals(Aes.DATA_NUMERIC_BOUNDED) ){
			if(value!=null){
				a.value = new Double(((Double)value).doubleValue());
			}
			if(defaultValue!=null){
				a.defaultValue = new Double(((Double)defaultValue).doubleValue());
			}
		}else if(dataType.equals(Aes.DATA_SHAPE) ||
			dataType.equals(Aes.DATA_LINE)){
			if(value!=null){
				a.value = new Integer(((Integer)value).intValue());
			}
			if(defaultValue!=null){
				a.defaultValue = new Integer(((Integer)defaultValue).intValue());
			}			
		}else if(dataType.equals(Aes.DATA_COLOUR)){
			if(value!=null){
				Color c = (Color) value;
				a.value = new Color(c.getRGB());
			}
			if(defaultValue!=null){
				Color c = (Color) defaultValue;
				a.defaultValue = new Color(c.getRGB());
			}
		}else{
			a.value = this.value;
			a.defaultValue = this.defaultValue;
		}
		return a;
	}
	
	public String[] getAesCalls(){
		String[] calls;
		boolean useVar = useVariable || value==null;
		if(variable!=null && variable.length()>0 && variable!=defaultVariable && useVar){
			calls = new String[] {name + " = " + variable};
		}else
			calls = new String[]{};
		return calls;
	}
	
	public String[] getParamCalls(){
		String[] calls;
		boolean useVar = useVariable && variable!=null && variable.length()>0;
		if(value!=null && !value.equals(defaultValue) && !useVar){
			String val;
			if(value instanceof Color){
				val = "'#"+ Integer.toHexString(((Color)value).getRGB()).substring(2)+"'";
			}else if(dataType == Aes.DATA_ANY && value.toString().length()>0){
				try{
					Double.parseDouble(value.toString());
					val = value.toString();
				}catch(Exception e){
					val = "'" + Deducer.addSlashes(value.toString()) + "'";
				}
			}else
				val = value.toString();
			if(val.length()>0)
				calls = new String[]{name + " = "+val};
			else
				calls = new String[]{};
		}else
			calls = new String[]{};
		return calls;
	}
	
	public static Aes makeAes(String type){
		Aes a = new Aes();
		a.name=type;
		String e = type.substring(1);
		a.title = type.substring(0, 1).toUpperCase().concat(e);
		if(type.equals("x")){
			a.dataType = DATA_ANY;
			a.required = true;
			a.defaultUseVariable = true;
		}else if(type.equals("y")){
			a.dataType = DATA_ANY;
			a.required = true;
			a.defaultUseVariable = true;
		}else if(type.equals("z")){
			a.dataType = DATA_ANY;
			a.required = true;
			a.defaultUseVariable = true;
		}else if(type.equals("colour")){
			a.dataType = DATA_COLOUR;
		}else if(type.equals("fill")){
			a.dataType = DATA_COLOUR;
		}else if(type.equals("label")){
			a.dataType = DATA_ANY;
		}else if(type.equals("size")){
			a.dataType = DATA_NUMERIC_BOUNDED;
			a.defaultValue = new Double(0.5);
			a.lowerBound=new Double(0.0);
		}else if(type.equals("alpha")){
			a.dataType = DATA_NUMERIC_BOUNDED;
			a.defaultValue = new Double(1.0);
			a.lowerBound = new Double(0.0);
			a.upperBound = new Double(1.0);
			a.defaultUseVariable = false;
			a.title = "Alpha";
			a.preferNumeric=true;
		}else if(type.equals("angle")){
			a.dataType = DATA_NUMERIC_BOUNDED;
			a.defaultValue = new Double(0.0);
			a.lowerBound = new Double(0.0);
			a.upperBound = new Double(360.0);
		}else if(type.equals("radius")){
			a.required = true;
			a.dataType = DATA_NUMERIC_BOUNDED;
			a.lowerBound = new Double(0.0);
		}else if(type.equals("hjust")){
			a.name = "hjust";
			a.dataType = DATA_NUMERIC_BOUNDED;
			a.lowerBound = new Double(0.0);
			a.upperBound = new Double(1.0);
			a.defaultValue = new Double(.5);
			a.defaultUseVariable = false;
		}else if(type.equals("vjust")){
			a.name = "vjust";
			a.dataType = DATA_NUMERIC_BOUNDED;
			a.lowerBound = new Double(0.0);
			a.upperBound = new Double(1.0);
			a.defaultValue = new Double(.5);
			a.defaultUseVariable = false;
		}else if(type.equals("intercept")){
			a.dataType = DATA_NUMERIC;
		}else if(type.equals("xintercept")){
			a.dataType = DATA_NUMERIC;
		}else if(type.equals("yintercept")){
			a.dataType = DATA_NUMERIC;
		}else if(type.equals("slope")){
			a.dataType = DATA_NUMERIC;
		}else if(type.equals("linetype")){
			a.dataType = DATA_LINE;
			a.defaultValue = new Integer(1);
			a.title = "Line";
			a.preferCategorical=true;
		}else if(type.equals("size")){
			a.dataType = DATA_NUMERIC_BOUNDED;
			a.lowerBound = new Double(0.0);
		}else if(type.equals("xmin")){
			a.dataType = DATA_NUMERIC;
			a.required = true;
			a.defaultUseVariable = true;
		}else if(type.equals("xmax")){
			a.dataType = DATA_NUMERIC;
			a.required = true;
			a.defaultUseVariable = true;
		}else if(type.equals("ymin")){
			a.dataType = DATA_NUMERIC;
			a.required = true;
			a.defaultUseVariable = true;
		}else if(type.equals("ymax")){
			a.dataType = DATA_NUMERIC;
			a.required = true;
			a.defaultUseVariable = true;
		}else if(type.equals("weight")){
			a.dataType = DATA_NUMERIC_BOUNDED;
			a.lowerBound = new Double(0.0);
			a.defaultValue =  new Double(1.0);
		}else if(type.equals("lower")){
			a.dataType = DATA_NONE;
		}else if(type.equals("upper")){
			a.dataType = DATA_NONE;
		}else if(type.equals("middle")){
			a.dataType = DATA_NONE;
		}else if(type.equals("width")){
			a.dataType = DATA_NUMERIC_BOUNDED;
			a.lowerBound=new Double(0.0);
			a.defaultValue = new Double(0.5);
		}else if(type.equals("sample")){
			a.dataType = DATA_NONE;
			a.required = true;
			a.defaultUseVariable = true;
		}else if(type.equals("shape")){
			a.dataType = DATA_SHAPE;
			a.defaultValue = new Integer(16);
			a.preferCategorical=true;
		}else if(type.equals("xend")){
			a.dataType = DATA_ANY;
			a.defaultUseVariable = true;
		}else if(type.equals("yend")){
			a.dataType = DATA_ANY;
			a.defaultUseVariable = true;
		}else if(type.equals("group")){
			a.dataType = DATA_NONE;
		}else
			a.dataType = DATA_NONE;
		a.value=a.defaultValue;
		a.variable=a.defaultVariable;
		a.useVariable = a.defaultUseVariable;
		return a;
	}
	
	public Element toXML(){
		try{
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			Element node = doc.createElement("Aes");
			if(name!=null)
				node.setAttribute("name", name);
			if(title!=null)
				node.setAttribute("title", title);
			if(dataType!=null)
				node.setAttribute("dataType", dataType);
			if(lowerBound!=null)
				node.setAttribute("lowerBound", lowerBound.toString());
			if(upperBound!=null)
				node.setAttribute("upperBound", upperBound.toString());
			node.setAttribute("useVariable", useVariable ? "true" : "false");
			node.setAttribute("defaultUseVariable", defaultUseVariable ? "true" : "false");
			node.setAttribute("required", required ? "true" : "false");
			node.setAttribute("preferNumeric", preferNumeric ? "true" : "false");
			node.setAttribute("preferCategorical", preferCategorical ? "true" : "false");
			if(variable!=null)
				node.setAttribute("variable", variable);
			if(defaultVariable!=null)
				node.setAttribute("defaultVariable", defaultVariable);
			
			if(value instanceof String)
				node.setAttribute("value", value.toString());
			else if(value instanceof Double || value instanceof Integer)
				node.setAttribute("value", value.toString());
			else if(value instanceof Color)
				node.setAttribute("value", "#"+ Integer.toHexString(((Color)value).getRGB()).substring(2));
			if(defaultValue instanceof String)
				node.setAttribute("defaultValue", defaultValue.toString());
			else if(defaultValue instanceof Double || defaultValue instanceof Integer)
				node.setAttribute("defaultValue", defaultValue.toString());
			else if(defaultValue instanceof Color)
				node.setAttribute("defaultValue", "#"+ Integer.toHexString(((Color)defaultValue).getRGB()).substring(2));
			
			node.setAttribute("className", "Aes");
			
			doc.appendChild(node);
			return node;
			
        }catch(Exception e){e.printStackTrace();return null;}
	}
	
	public void setFromXML(Element node){
		if(node.hasAttribute("name"))
			name = node.getAttribute("name");
		else
			name=null;
		
		if(node.hasAttribute("title"))
			title = node.getAttribute("title");
		else
			title=null;
		
		if(node.hasAttribute("dataType"))
			dataType = node.getAttribute("dataType");
		else
			dataType=null;
		
		if(node.hasAttribute("lowerBound"))
			lowerBound = new Double(Double.parseDouble(node.getAttribute("lowerBound")));
		else
			lowerBound=null;
		
		if(node.hasAttribute("upperBound"))
			upperBound = new Double(Double.parseDouble(node.getAttribute("upperBound")));
		else
			upperBound=null;
		
		if(node.hasAttribute("useVariable"))
			useVariable = node.getAttribute("useVariable").equals("true");
		else
			useVariable=false;
		
		if(node.hasAttribute("preferNumeric"))
			preferNumeric = node.getAttribute("preferNumeric").equals("true");
		else
			preferNumeric=false;
		
		if(node.hasAttribute("preferCategorical"))
			preferCategorical = node.getAttribute("preferCategorical").equals("true");
		else
			preferCategorical=false;
		
		if(node.hasAttribute("defaultUseVariable"))
			defaultUseVariable = node.getAttribute("defaultUseVariable").equals("true");
		else
			defaultUseVariable=false;
		
		if(node.hasAttribute("required"))
			required = node.getAttribute("required").equals("true");
		else
			required=false;
		
		if(node.hasAttribute("variable"))
			variable = node.getAttribute("variable");
		else
			variable=null;
		
		if(node.hasAttribute("defaultVariable"))
			defaultVariable = node.getAttribute("defaultVariable");
		else
			defaultVariable=null;
		
		if(node.hasAttribute("value")){
			if(dataType.equals(Aes.DATA_NUMERIC) ||
					dataType.equals(Aes.DATA_NUMERIC_BOUNDED))
				value = new Double(Double.parseDouble(node.getAttribute("value")));
			else if(dataType.equals(Aes.DATA_SHAPE) ||
					dataType.equals(Aes.DATA_LINE))
				value = new Integer(Integer.parseInt(node.getAttribute("value")));
			else if(dataType.equals(Aes.DATA_COLOUR))
				value = Color.decode(node.getAttribute("value"));
			else
				value = node.getAttribute("value");
		}
		if(node.hasAttribute("defaultValue")){
			if(dataType.equals(Aes.DATA_NUMERIC) ||
					dataType.equals(Aes.DATA_NUMERIC_BOUNDED))
				defaultValue = new Double(Double.parseDouble(node.getAttribute("defaultValue")));
			else if(dataType.equals(Aes.DATA_SHAPE) ||
					dataType.equals(Aes.DATA_LINE))
				defaultValue = new Integer(Integer.parseInt(node.getAttribute("defaultValue")));
			else if(dataType.equals(Aes.DATA_COLOUR))
				defaultValue = Color.decode(node.getAttribute("defaultValue"));
			else
				defaultValue = node.getAttribute("defaultValue");
		}
	}
	
	
	
	public static Aes makeAes(String type,Object dValue,String dVariable){
		Aes aes = makeAes(type);
		if(dValue!=null){
			aes.defaultUseVariable=false;
			aes.useVariable=false;
			aes.value = dValue;
			aes.defaultValue = dValue;
		}
		if(dVariable!=null){
			aes.defaultUseVariable=true;
			aes.useVariable=true;
			aes.variable = dVariable;
			aes.defaultVariable = dVariable;			
		}
		
		return aes;
	}
}

