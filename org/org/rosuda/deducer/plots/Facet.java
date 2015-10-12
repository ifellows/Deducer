package org.rosuda.deducer.plots;

import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.rosuda.deducer.widgets.param.Param;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Facet implements ElementModel{

	private String name;
	
	public ParamFacet param;
	
	public String data;

	public String facetType;
	
	
	public static Facet makeFacet(String n){
		Facet f = new Facet();
		f.setName("facet_" + n);
		f.facetType = n;
		
		ParamFacet p = new ParamFacet();
		p.facetType = n;
		f.param = p;
		return f;
	}
	
	
	public Object clone(){
		Facet f = new Facet();
		f.setName(name);
		f.data = data;
		f.param = (ParamFacet) param.clone();
		f.facetType = facetType;
		return f;
	}

	
	public String checkValid() {
		return null;
	}

	public String getCall() {
		String[] p = param.getParamCalls();
		if(p.length==0)
			return null;
		String call = "facet_" + param.facetType + "(";
		for(int i =0;i<p.length;i++)
			call+=p[i];
		call+=")";
		return call;
	}

	public String getType() {
		return "facet";
	}

	public ElementView getView() {
		FacetPanel fp = new FacetPanel();
		fp.setModel(this);
		return fp;
	}


	public Vector getParams() {
		Vector v = new Vector();
		v.add(param);
		return v;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}
	
	public Element toXML() {
		try{
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element node = doc.createElement("ElementModel");
			node.setAttribute("className", "org.rosuda.deducer.plots.Facet");
			if(name!=null)
				node.setAttribute("name", name);
			if(data!=null)
				node.setAttribute("data", data);
			if(facetType!=null)
				node.setAttribute("facetType", facetType);
			
			Element pEl = param.toXML();
			pEl = (Element) doc.importNode(pEl, true);
			node.appendChild(pEl);
			
			doc.appendChild(node);
			return node;
		}catch(Exception ex){ex.printStackTrace();return null;}		
	}

	public void setFromXML(Element node) {
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.plots.Facet")){
			System.out.println("Error Facet: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		if(node.hasAttribute("name"))
			name = node.getAttribute("name");
		else 
			name = null;
		
		if(node.hasAttribute("data"))
			data = node.getAttribute("data");
		else 
			data = null;
		
		if(node.hasAttribute("facetType"))
			facetType = node.getAttribute("facetType");
		else 
			facetType = null;
		
		param = new ParamFacet();
		Element el = (Element) node.getElementsByTagName("*").item(0);
		param.setFromXML(el);
	}	
	public String getData() {
		return data;
	}	
}
