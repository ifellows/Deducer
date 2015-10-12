package org.rosuda.deducer.widgets.param;

import java.awt.Color;
import java.io.StringWriter;
import java.lang.reflect.Constructor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.rosuda.deducer.plots.Coord;
import org.rosuda.deducer.plots.Layer;
import org.rosuda.deducer.plots.ParamFacet;
import org.rosuda.deducer.plots.ParamScaleLegend;
import org.rosuda.deducer.plots.PlottingElement;
import org.rosuda.deducer.plots.Stat;
import org.rosuda.deducer.plots.Theme;
import org.rosuda.deducer.widgets.VariableSelectorWidget;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class Param implements Cloneable{

	
	
	final public static String VIEW_ENTER = "org.rosuda.deducer.widgets.param.ParamTextFieldWidget";
	final public static String VIEW_ENTER_LONG = "org.rosuda.deducer.widgets.param.ParamTextFieldLongWidget";
	final public static String VIEW_COMBO = "org.rosuda.deducer.widgets.param.ParamComboBoxWidget";
	final public static String VIEW_EDITABLE_COMBO = "org.rosuda.deducer.widgets.param.ParamEditableComboBoxWidget";
	final public static String VIEW_CHECK_BOX = "org.rosuda.deducer.widgets.param.ParamCheckBoxWidget";
	final public static String VIEW_VECTOR_BUILDER = "org.rosuda.deducer.widgets.param.ParamVectorBuilderWidget";
	final public static String VIEW_TWO_VALUE_ENTER = "org.rosuda.deducer.widgets.param.ParamTwoValueWidget";
	final public static String VIEW_COLOR = "org.rosuda.deducer.widgets.param.ParamColorWidget";
	final public static String VIEW_RFUNCTION_CHOOSER = "org.rosuda.deducer.widgets.param.RFunctionListChooserWidget";
	final public static String VIEW_RFUNCTION_PANEL = "org.rosuda.deducer.widgets.param.RFunctionListPanelWidget";
	final public static String VIEW_SINGLE_VARIABLE = "org.rosuda.deducer.widgets.param.ParamVariableView";
	final public static String VIEW_HIDDEN = "org.rosuda.deducer.widgets.param.ParamNullWidget";
	final public static String VIEW_MULTI_VARIABLE = "org.rosuda.deducer.widgets.param.ParamMultipleVariablesWidget";
	final public static String VIEW_ROBJECT_COMBO = "org.rosuda.deducer.widgets.param.ParamRObjectComboBoxWidget";

	
	protected String name;					//name of parameter
	protected String title;				//title to be displayed
	
	protected String[] options;			//passible parameter values
	protected String[] labels;				//descr of param values
	
	protected String view = VIEW_ENTER_LONG;
	
	protected Double lowerBound ;			//If bounded, the lower bound
	protected Double upperBound ;			//if bounded, the upper bound
	
	protected boolean required = true;
	
	protected boolean requiresVariableSelector = false;
	
	public Param(){}
	
	public Param(String nm){
		setName(nm);
		setTitle(nm);
	}
	
	public ParamWidget getView(){
		try{
			Class cl =Class.forName(view);
			Constructor constructor = cl.getConstructor(new Class[]{Param.class});
			ParamWidget pw = (ParamWidget) constructor.newInstance(new Param[]{this});
			return pw;
		}catch(Exception e){e.printStackTrace();return null;}
		
	}
	
	public ParamWidget getView(VariableSelectorWidget sel){
		try{
			Class cl =Class.forName(view);
			Constructor constructor = cl.getConstructor(new Class[]{
					Param.class,VariableSelectorWidget.class});
			ParamWidget pw = (ParamWidget) constructor.newInstance(new Object[]{this,sel});
			return pw;
		}catch(Exception e){return getView();}
		
	}
	
	public abstract Object clone();
	
	public abstract String[] getParamCalls();
	
	
	public abstract void setValue(Object value);

	public abstract Object getValue() ;

	public abstract void setDefaultValue(Object defaultValue);

	public abstract Object getDefaultValue();

	public void setViewType(String view) {
		this.view = view;
	}

	public String getViewType() {
		return view;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}


	public void setOptions(String[] options) {
		this.options = options;
	}

	public String[] getOptions() {
		return options;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	public String[] getLabels() {
		return labels;
	}
	
	public void setLowerBound(Double lowerBound) {
		this.lowerBound = lowerBound;
	}
	
	public void setLowerBound(double lowerBound) {
		this.lowerBound = new Double(lowerBound);
	}

	public Double getLowerBound() {
		return lowerBound;
	}

	public void setUpperBound(Double upperBound) {
		this.upperBound = upperBound;
	}
	
	public void setUpperBound(double upperBound) {
		this.upperBound = new Double(upperBound);
	}

	public Double getUpperBound() {
		return upperBound;
	}
	
	public boolean requiresVariableSelector(){
		return requiresVariableSelector;
	}
	
	public void setRequiresVariableSelector(boolean needed){
		requiresVariableSelector = needed;
	}
	
	public Element toXML(){
		try{
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			Element node = doc.createElement("Param");
			if(name!=null)
				node.setAttribute("name", name);
			if(title!=null)
				node.setAttribute("title", title);
			if(view!=null)
				node.setAttribute("viewType", getViewType());
			node.setAttribute("required", required ? "true" : "false");
			node.setAttribute("requiresVariableSelector", requiresVariableSelector ? "true" : "false");
			Element optionNode = doc.createElement("options");
			if(options!=null)
				for(int i=0;i<options.length;i++)
					optionNode.setAttribute("element_"+i, options[i]);
			node.appendChild(optionNode);
        
			Element labelsNode = doc.createElement("labels");
			if(labels!=null)
				for(int i=0;i<labels.length;i++)
					labelsNode.setAttribute("element_"+i, labels[i]);
			node.appendChild(labelsNode);
			
			if(lowerBound!=null)
				node.setAttribute("lowerBound", lowerBound.toString());
			if(upperBound!=null)
				node.setAttribute("upperBound", upperBound.toString());
			
			doc.appendChild(node);
			return node;
			
        }catch(Exception e){e.printStackTrace();return null;}
	}
	
	public void setFromXML(Element node){
		if(node.hasAttribute("name"))
			name = node.getAttribute("name");
		else
			name = null;
		if(node.hasAttribute("title"))
			title = node.getAttribute("title");		
		else
			title = null;
		if(node.hasAttribute("viewType"))
			view = node.getAttribute("viewType");
		else
			view = VIEW_ENTER_LONG;
		
		if(node.hasAttribute("required"))
			required = node.getAttribute("required").equals("true");
		else
			required = false;
		
		if(node.hasAttribute("requiresVariableSelector"))
			requiresVariableSelector = node.getAttribute("requiresVariableSelector").equals("true");
		else
			requiresVariableSelector = false;
		Node optionNode = null;
		NodeList nl = node.getChildNodes();
		for(int i=0;i<nl.getLength();i++)
			if(nl.item(i) instanceof Element && ((Element)nl.item(i)).getTagName().equals("options"))
				optionNode = nl.item(i);
		if(optionNode!=null){
			//Node optionNode =node.getElementsByTagName("options").item(0);
			NamedNodeMap attr = optionNode.getAttributes();
			if(attr.getLength()>0){
				options = new String[attr.getLength()];
				for(int i=0;i<attr.getLength();i++)
					options[i] = attr.item(i).getNodeValue();
			}
		}
		Node labelsNode  =null;
		for(int i=0;i<nl.getLength();i++)
			if(nl.item(i) instanceof Element && ((Element)nl.item(i)).getTagName().equals("labels"))
				labelsNode = nl.item(i);
		//Node labelsNode =node.getElementsByTagName("labels").item(0);
		if(labelsNode!=null){
			NamedNodeMap attr = labelsNode.getAttributes();
			if(attr.getLength()>0){
				labels = new String[attr.getLength()];
				for(int i=0;i<attr.getLength();i++)
					labels[i] = attr.item(i).getNodeValue();
			}
		}
		if(node.hasAttribute("lowerBound"))
			lowerBound = new Double(Double.parseDouble(node.getAttribute("lowerBound")));
		else
			lowerBound = null;
		if(node.hasAttribute("upperBound"))
			upperBound = new Double(Double.parseDouble(node.getAttribute("upperBound")));
		else
			upperBound = null;
	}
	
	public static Param makeParam(String className){
		Param p;
		try {
			p = (Param) Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		return p;
	}

	public void setRequired(boolean req) {
		required = req;
	}

	public boolean isRequired() {
		return required;
	}
	
	public boolean hasValidEntry(){
		return true;
	}
}
