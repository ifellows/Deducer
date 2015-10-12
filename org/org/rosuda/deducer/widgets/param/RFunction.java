package org.rosuda.deducer.widgets.param;

import java.util.Vector;


import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.XMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class RFunction extends Param{

	protected Vector params = new Vector();

	protected String paramName = ""; 
	
	public static String VIEW_RFUNCTION = "org.rosuda.deducer.widgets.param.DefaultRFunctionView";
	
	public RFunction(){
		super();
		view = VIEW_RFUNCTION ;
	}
	
	public RFunction(String name){
		super();
		this.setName(name);
		view = VIEW_RFUNCTION ;
	}
	
	
	
	public Vector getParams(){return params;}
	public void setParams(Vector p){params = p;}
	public String getName(){return name;}
	public void setName(String n){name = n;}
	
	
	public Object clone(){
		RFunction s = new RFunction();
		for(int i=0;i<params.size();i++)
			s.params.add(((Param)params.get(i)).clone());
		s.name = name;
		s.view = view;
		s.requiresVariableSelector = this.requiresVariableSelector;
		s.required = required;
		return s;
	}

	
	public String checkValid() {
		for(int i=0;i<params.size();i++){
			Param p = (Param) params.get(i);
			if(p instanceof RFunction){
				String s = ((RFunction)p).checkValid();
				if(s!=null)
					return s;
			}else if(p.isRequired() && !p.hasValidEntry()){
				return "'" +p.getTitle() + "' is required. Please enter a value.";
			}
		}
		return null;
	}

	public String getCall() {
		Vector paramCalls = new Vector();
		for(int i=0;i<params.size();i++){
			Param prm = (Param) params.get(i);
			String[] p = prm.getParamCalls();
			for(int j=0;j<p.length;j++)
				paramCalls.add(p[j]);				
		}
		
		//remove duplicates
		for(int i=paramCalls.size()-1;i>0;i--)
			for(int j =i-1;j>=0;j--)
				if(paramCalls.get(i).equals(paramCalls.get(j)))
					paramCalls.remove(j);

		String call = Deducer.makeRCollection(paramCalls, name, false);
		return call;
	}

	
	public void add(Param p){
		params.add(p);
	}
	public Param get(int i){
		return (Param) params.get(i);
	}
	public void remove(Param p){
		params.remove(p);
	}
	
	public Element toXML(){
		try{
		Element node = super.toXML();
		Document doc = node.getOwnerDocument();
		
		if(paramName!=null)
			node.setAttribute("paramName", paramName);
		for(int i=0;i<params.size();i++){
			Param p = (Param) params.get(i);
			Element el = p.toXML();
			Node n = doc.importNode(el, true);
			node.appendChild(n);
		}
		node.setAttribute("view", view);
		node.setAttribute("className", "org.rosuda.deducer.widgets.param.RFunction");
		
		return node;
		
		}catch(Exception e){e.printStackTrace();return null;}
	}
	
	public void setFromXML(Element node){
		super.setFromXML(node);
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.widgets.param.RFunction")){
			System.out.println("Error RFunction: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		
		if(node.hasAttribute("view"))
			view = node.getAttribute("view");
		else
			view = VIEW_RFUNCTION;
		
		if(node.hasAttribute("paramName"))
			paramName = node.getAttribute("paramName");
		else
			paramName = null;
		params = new Vector();

		Vector children = XMLHelper.getChildrenElementsByTag(node, "Param");
		for(int i=0;i<children.size();i++){
			Element n= (Element) children.get(i);
			cn = n.getAttribute("className");
			Param p = Param.makeParam(cn);
			p.setFromXML(n);
			params.add(p);
		}
	}

	public Object getDefaultValue() {
		return null;
	}

	public String[] getParamCalls() {
		return new String[] {getCall()};
	}

	public Object getValue() {
		return params;
	}

	public void setDefaultValue(Object defaultValue) {}

	public void setValue(Object value) {
		params = (Vector) value;
	}
	
}
