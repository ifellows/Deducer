package org.rosuda.deducer.plots;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Position {
	String name;
	public Double width;
	public Double height;
	
	public Position(String n,double w, double h){
		name=n;
		width=new Double(w);
		height = new Double(h);
	}
	
	public Position(String n,Double w, Double h){
		name=n;
		width=w;
		height = h;
	}
	
	public Position(){
		name="identity";
	}
	public Position(String n){
		name=n;
	}
	
	public Object clone(){
		return new Position(name,
				width==null ? null : new Double(width.doubleValue()),
				height==null ? null : new Double(height.doubleValue()));
	}
	
	public static Position makePosition(String posName){
		Position p = new Position(posName);
		return p;
	}
	
	
	public Element toXML(){
		try{
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			Element node = doc.createElement("Position");
			if(name!=null)
				node.setAttribute("name", name);
			if(width!=null)
				node.setAttribute("width", width.toString());
			if(height!=null)
				node.setAttribute("height", height.toString());
			node.setAttribute("className", "org.rosuda.deducer.plots.Position");
			doc.appendChild(node);
			return node;
			
        }catch(Exception e){e.printStackTrace();return null;}
	}
	
	public void setFromXML(Element node){
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.plots.Position")){
			System.out.println("Error Position: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		if(node.hasAttribute("name"))
			name = node.getAttribute("name");
		else
			name = null;
		if(node.hasAttribute("width"))
			width = new Double(Double.parseDouble(node.getAttribute("width")));
		else
			width = null;
		if(node.hasAttribute("height"))
			height = new Double(Double.parseDouble(node.getAttribute("height")));
		else
			height = null;
	}
}
