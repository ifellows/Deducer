package org.rosuda.deducer.plots;

import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.rosuda.deducer.widgets.VariableSelectorWidget;
import org.rosuda.deducer.widgets.param.Param;
import org.rosuda.deducer.widgets.param.ParamWidget;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ParamFacet extends Param{

	public static String VIEW_FACET = "facet";
	
	public String[] yVarsGrid = new String[]{};
	public String[] xVarsGrid = new String[]{};
	public String[] varsWrap = new String[]{};
	
	public String facetType;
	public String scaleGrid;
	public Boolean margins;
	public Boolean spaceFixed;
	public Boolean asTableGrid;
	
	public Integer nrow;
	public Integer ncol;
	public String scaleWrap;
	public Boolean asTableWrap;
	public Boolean drop;
	public Boolean dropGrid;
	
	public ParamFacet(){
		asTableGrid = new Boolean(true);
		asTableWrap = new Boolean(true);
		drop = new Boolean(true);
		dropGrid = new Boolean(true);
		margins = new Boolean(false);
		setName("facet");
		setTitle("facet");
		ncol = null;
		nrow = null;
		scaleGrid = "fixed";
		scaleWrap = "fixed";
		spaceFixed = new Boolean(true);
		facetType = "wrap";
		view = VIEW_FACET;
	}
	
	public ParamWidget getView(){
		return new ParamFacetWidget(new VariableSelectorWidget(),this);
	}
	
	public ParamWidget getView(VariableSelectorWidget s){
		return new ParamFacetWidget(s,this);
	}
	
	public Object clone(){
		ParamFacet f = new ParamFacet();
		String[] s = new String[yVarsGrid.length];
		for(int i=0;i<s.length;i++)
			s[i] = yVarsGrid[i];
		f.yVarsGrid =s;
		
		s = new String[xVarsGrid.length];
		for(int i=0;i<s.length;i++)
			s[i] = xVarsGrid[i];
		f.xVarsGrid = s;
		
		s = new String[varsWrap.length];
		for(int i=0;i<s.length;i++)
			s[i] = varsWrap[i];
		f.varsWrap = s;
		
		f.scaleGrid = this.scaleGrid;
		f.scaleWrap = scaleWrap;
		f.margins = new Boolean(margins.booleanValue());
		f.spaceFixed = new Boolean(spaceFixed.booleanValue());
		f.asTableGrid = new Boolean(asTableGrid.booleanValue());
		f.asTableWrap = new Boolean(asTableWrap.booleanValue());
		f.drop = new Boolean(drop.booleanValue());
		f.dropGrid = new Boolean(dropGrid.booleanValue());
		if(nrow!=null)
			f.nrow = new Integer(nrow.intValue());
		if(ncol!=null)
			f.ncol = new Integer(ncol.intValue());
		f.facetType = facetType;
		return f;
	}
	
	public String[] getParamCalls(){
		String[] calls = new String[]{};
		Vector v = new Vector();
		if(facetType.equals("grid")){
			if(yVarsGrid.length==0 && xVarsGrid.length==0)
				return new String[]{};
			String lhs = "";
			if(yVarsGrid.length==0)
				lhs = ".";
			for(int i=0;i<yVarsGrid.length;i++){
				if(i!=0)
					lhs+= " + ";
				lhs+= yVarsGrid[i];
			}
			String rhs = "";
			if(xVarsGrid.length==0)
				rhs = ".";
			for(int i=0;i<xVarsGrid.length;i++){
				if(i!=0)
					rhs+= " + ";
				rhs+= xVarsGrid[i];
			}
			v.add("facets = " + lhs + " ~ " + rhs);
			if(margins.booleanValue())
				v.add(", margins = TRUE");
			
			if(!scaleGrid.equals("fixed"))
				v.add(", scales = '" + scaleGrid + "'");
			
			if(!spaceFixed.booleanValue())
				v.add(", space = 'free'");
			
			if(!asTableGrid.booleanValue())
				v.add(", as.table = FALSE");
			
			if(!dropGrid.booleanValue())
				v.add(", drop = FALSE");
		}else{
			if(varsWrap.length==0)
				return new String[]{};
			String rhs = "";
			for(int i=0;i<varsWrap.length;i++){
				if(i!=0)
					rhs+= " + ";
				rhs+= varsWrap[i];
			}
			v.add("facets = ~" + rhs);
			
			if(nrow!=null)
				v.add(", nrow = " + nrow.toString());
			if(ncol!=null)
				v.add(", ncol = " + ncol.toString());
			if(!scaleWrap.equals("fixed"))
				v.add(", scales = '"+scaleWrap+"'");
			if(!asTableWrap.booleanValue())
				v.add(", as.table = FALSE");	
			if(!drop.booleanValue())
				v.add(", drop = FALSE");
		}
		calls = new String[v.size()];
		for(int i=0;i<v.size();i++)
			calls[i] = v.get(i).toString();
			
		return calls;
	}
	
	public Element toXML(){
		Element node = super.toXML();
		Document doc = node.getOwnerDocument();
		
		Element varNode = doc.createElement("yVarsGrid");
		if(yVarsGrid!=null)
			for(int i=0;i<yVarsGrid.length;i++)
				varNode.setAttribute("element_"+i, yVarsGrid[i]);
		node.appendChild(varNode);

		varNode = doc.createElement("xVarsGrid");
		if(xVarsGrid!=null)
			for(int i=0;i<xVarsGrid.length;i++)
				varNode.setAttribute("element_"+i, xVarsGrid[i]);
		node.appendChild(varNode);
		
		varNode = doc.createElement("varsWrap");
		if(varsWrap!=null)
			for(int i=0;i<varsWrap.length;i++)
				varNode.setAttribute("element_"+i, varsWrap[i]);
		node.appendChild(varNode);
		
		if(facetType!=null)
			node.setAttribute("facetType", facetType);
		if(scaleGrid!=null)
			node.setAttribute("scaleGrid", scaleGrid);
		if(margins!=null)
			node.setAttribute("margins", margins.toString());
		if(spaceFixed!=null)
			node.setAttribute("spaceFixed", spaceFixed.toString());
		if(asTableGrid!=null)
			node.setAttribute("asTableGrid", asTableGrid.toString());
		
		if(asTableWrap!=null)
			node.setAttribute("asTableWrap", asTableWrap.toString());
		if(drop!=null)
			node.setAttribute("drop", drop.toString());
		if(dropGrid!=null)
			node.setAttribute("dropGrid", dropGrid.toString());
		if(scaleWrap!=null)
			node.setAttribute("scaleWrap", scaleWrap);
		if(nrow!=null)
			node.setAttribute("nrow", nrow.toString());
		if(ncol!=null)
			node.setAttribute("ncol", ncol.toString());
		node.setAttribute("className", "org.rosuda.deducer.plots.ParamFacet");
		return node;
	}
	
	public void setFromXML(Element node){
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.plots.ParamFacet")){
			System.out.println("Error ParamFacet: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		super.setFromXML(node);
		if(node.hasAttribute("facetType"))
			facetType = node.getAttribute("facetType");
		else
			facetType = null;
		if(node.hasAttribute("scaleGrid"))
			scaleGrid = node.getAttribute("scaleGrid");
		else
			scaleGrid = null;
		if(node.hasAttribute("margins"))
			margins = new Boolean(node.getAttribute("margins").equals("true"));
		else
			margins = null;		
		if(node.hasAttribute("spaceFixed"))
			spaceFixed = new Boolean(node.getAttribute("spaceFixed").equals("true"));
		else
			spaceFixed = null;	
		if(node.hasAttribute("asTableGrid"))
			asTableGrid = new Boolean(node.getAttribute("asTableGrid").equals("true"));
		else
			asTableGrid = null;	
		if(node.hasAttribute("asTableWrap"))
			asTableWrap = new Boolean(node.getAttribute("asTableWrap").equals("true"));
		else
			asTableWrap = null;		
		if(node.hasAttribute("drop"))
			drop = new Boolean(node.getAttribute("drop").equals("true"));
		else
			drop = null;
		if(node.hasAttribute("dropGrid"))
			dropGrid = new Boolean(node.getAttribute("dropGrid").equals("true"));
		else
			dropGrid = new Boolean(true);
		if(node.hasAttribute("scaleWrap"))
			scaleWrap = node.getAttribute("scaleWrap");
		else
			scaleWrap = null;
		if(node.hasAttribute("nrow"))
			nrow = new Integer(Integer.parseInt(node.getAttribute("nrow")));
		else
			nrow = null;		
		if(node.hasAttribute("ncol"))
			ncol = new Integer(Integer.parseInt(node.getAttribute("ncol")));
		else
			ncol = null;
		Node varNode =node.getElementsByTagName("yVarsGrid").item(0);
		NamedNodeMap attr = varNode.getAttributes();
		if(attr.getLength()>0){
			yVarsGrid = new String[attr.getLength()];
			for(int i=0;i<attr.getLength();i++)
				yVarsGrid[i] = attr.item(i).getNodeValue();
		}
		
		varNode =node.getElementsByTagName("xVarsGrid").item(0);
		attr = varNode.getAttributes();
		if(attr.getLength()>0){
			xVarsGrid = new String[attr.getLength()];
			for(int i=0;i<attr.getLength();i++)
				xVarsGrid[i] = attr.item(i).getNodeValue();
		}
		
		varNode =node.getElementsByTagName("varsWrap").item(0);
		attr = varNode.getAttributes();
		if(attr.getLength()>0){
			varsWrap = new String[attr.getLength()];
			for(int i=0;i<attr.getLength();i++)
				varsWrap[i] = attr.item(i).getNodeValue();
		}
	}
	
	

	public Object getDefaultValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getValue() {
		Vector v = new Vector();
		v.add(yVarsGrid);
		v.add(xVarsGrid);
		v.add(varsWrap);
		v.add(facetType);
		v.add(scaleGrid);
		v.add(margins);
		v.add(spaceFixed);
		v.add(asTableGrid);
		v.add(nrow);
		v.add(ncol);
		v.add(scaleWrap);
		v.add(asTableWrap);
		v.add(drop);
		return v;
	}

	public void setDefaultValue(Object defaultValue) {
		// TODO Auto-generated method stub
		
	}

	public void setValue(Object value) {
		if(value==null)
			return;
		Vector v = (Vector) value;
		yVarsGrid= (String[]) v.get(0);
		xVarsGrid= (String[]) v.get(1);
		varsWrap= (String[]) v.get(2);
		facetType= (String) v.get(3);
		scaleGrid= (String) v.get(4);
		margins= (Boolean) v.get(5);
		spaceFixed= (Boolean) v.get(6);
		asTableGrid= (Boolean) v.get(7);
		nrow= (Integer) v.get(8);
		ncol= (Integer) v.get(9);
		scaleWrap= (String) v.get(10);
		asTableWrap= (Boolean) v.get(11);
		drop= (Boolean) v.get(12);
	}
	
	
}
