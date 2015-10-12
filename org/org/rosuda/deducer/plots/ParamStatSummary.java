package org.rosuda.deducer.plots;

import java.util.Vector;

import org.rosuda.deducer.widgets.param.Param;
import org.rosuda.deducer.widgets.param.ParamWidget;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ParamStatSummary extends Param{

	protected Vector value;
	protected Vector defaultValue;
	
	final public static String VIEW_SUMMARY = "summary";
	
	public ParamStatSummary(){
		setViewType(VIEW_SUMMARY);
		Vector newValue = new Vector();
		newValue.add(new Integer(0));
		newValue.add("0.95");
		newValue.add("1");
		newValue.add("1000");
		newValue.add("");
		newValue.add("");
		newValue.add("");
		newValue.add("");
		setValue(newValue);
		
		newValue = new Vector();
		newValue.add(new Integer(-1));
		newValue.add("0.95");
		newValue.add("2");
		newValue.add("1000");
		newValue.add("");
		newValue.add("");
		newValue.add("");
		newValue.add("");
		setDefaultValue(newValue);
	}
	
	public ParamStatSummary(String nm){
		this();
		setName(nm);
		setTitle(nm);
	}
	
	public ParamWidget getView(){
		if(getViewType().equals(VIEW_SUMMARY))
			return new ParamStatSummaryWidget(this);
		System.out.println("invalid view");
		(new Exception()).printStackTrace();
		return null;
	}
	
	public Object clone(){
		Param p = new ParamStatSummary();
		p.setName(this.getName());
		p.setTitle(this.getTitle());
		p.setViewType(this.getViewType());
		if(this.getLowerBound()!=null)
			p.setLowerBound(new Double(this.getLowerBound().doubleValue()));
		if(this.getUpperBound()!=null)
			p.setUpperBound(new Double(this.getUpperBound().doubleValue()));
		if(this.getValue()!=null){
			Vector val = (Vector) getValue();
			Vector newVal = new Vector();
			newVal.add(new Integer(((Integer)val.get(0)).intValue()));
			for(int i=1;i<val.size();i++)
				newVal.add(val.get(i));
			p.setValue(newVal);
		}else
			p.setValue(null);
		return p;
	}
	
	public String[] getAesCalls(){
		return new String[]{};
	}
	
	public String[] getParamCalls(){
		String[] calls = new String[]{};
		Vector val = (Vector) getValue();
		if(getValue()!=null ){
			int sel = ((Integer)val.get(0)).intValue();
			if(sel==0){
				calls = new String[]{"fun.data = mean_sdl",
									"mult = " + val.get(2).toString()};
			}
			if(sel==1){
				calls = new String[]{"fun.data = mean_cl_normal",
									"conf.int = " + val.get(1).toString()};
			}
			if(sel==2){
				calls = new String[]{"fun.data = median_hilow",
									"conf.int = " + val.get(1).toString()};
			}
			if(sel==3){
				calls = new String[]{"fun.data = mean_cl_boot",
									"conf.int = " + val.get(1).toString(),
									"B = " + val.get(3).toString()};
			}
			if(sel==4){
				Vector v = new Vector();
				String y = val.get(4).toString();
				String ymin = val.get(5).toString();
				String ymax = val.get(6).toString();
				String data = val.get(7).toString();
				if(!data.equals("")){
					return new String[]{"fun.data = " + data};
				}
				if(!y.equals(""))
					v.add("fun.y = "+y);
				if(!ymin.equals(""))
					v.add("fun.ymin = "+ymin);
				if(!ymax.equals(""))
					v.add("fun.ymax = "+ymax);
				calls = new String[v.size()];
				for(int i=0;i<v.size();i++)
					calls[i] = v.get(i).toString();
			}
			
		}
		return calls;
	}

	public void setDefaultValue(Object defaultValue) {
		if(defaultValue instanceof Vector || defaultValue ==null)
			this.defaultValue = (Vector) defaultValue;
		else
			System.out.println("ParamStatSummary: invalid setDefaultValue");
	}
	
	public Object getDefaultValue() {
		return defaultValue;
	}
	
	public void setValue(Object value) {
		if(value instanceof Vector || value==null)
			this.value = (Vector) value;
		else{
			System.out.println("ParamStatSummary: invalid setValue");
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	public Object getValue() {
		return value;
	}
	
	public Element toXML(){
		Element e = super.toXML();
		Document doc = e.getOwnerDocument();
		if(value!=null){
			Vector vec = value;
			Element node = doc.createElement("value");
			node.setAttribute("combo", vec.get(0).toString());
			node.setAttribute("conf", vec.get(1).toString());
			node.setAttribute("mult", vec.get(2).toString());
			node.setAttribute("n", vec.get(3).toString());
			node.setAttribute("y", vec.get(4).toString());
			node.setAttribute("ymin", vec.get(5).toString());
			node.setAttribute("ymax", vec.get(6).toString());
			node.setAttribute("data", vec.get(7).toString());
			e.appendChild(node);
		}
		if(defaultValue!=null){
			Vector vec = defaultValue;
			Element node = doc.createElement("defaultValue");
			node.setAttribute("combo", vec.get(0).toString());
			node.setAttribute("conf", vec.get(1).toString());
			node.setAttribute("mult", vec.get(2).toString());
			node.setAttribute("n", vec.get(3).toString());
			node.setAttribute("y", vec.get(4).toString());
			node.setAttribute("ymin", vec.get(5).toString());
			node.setAttribute("ymax", vec.get(6).toString());
			node.setAttribute("data", vec.get(7).toString());
			e.appendChild(node);
		}
		e.setAttribute("className", "org.rosuda.deducer.plots.ParamStatSummary");
		return e;
	}
	
	public void setFromXML(Element node){
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.plots.ParamStatSummary")){
			System.out.println("Error ParamStatSummary: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		super.setFromXML(node);
		
		value = null;
		NodeList nl = node.getElementsByTagName("value");
		if(nl.getLength()>0){
			Element valNode = (Element) nl.item(0);
			Vector v = new Vector();
			v.add(new Integer(Integer.parseInt(valNode.getAttribute("combo"))));
			v.add(valNode.getAttribute("conf"));
			v.add(valNode.getAttribute("mult"));
			v.add(valNode.getAttribute("n"));
			v.add(valNode.getAttribute("y"));
			v.add(valNode.getAttribute("ymin"));
			v.add(valNode.getAttribute("ymax"));
			v.add(valNode.getAttribute("data"));
			value = v;
		}
		
		defaultValue = null;
		nl = node.getElementsByTagName("defaultValue");
		if(nl.getLength()>0){
			Element valNode = (Element) nl.item(0);
			Vector v = new Vector();
			v.add(new Integer(Integer.parseInt(valNode.getAttribute("combo"))));
			v.add(valNode.getAttribute("conf"));
			v.add(valNode.getAttribute("mult"));
			v.add(valNode.getAttribute("n"));
			v.add(valNode.getAttribute("y"));
			v.add(valNode.getAttribute("ymin"));
			v.add(valNode.getAttribute("ymax"));
			v.add(valNode.getAttribute("data"));
			defaultValue = v;
		}
	}
}
