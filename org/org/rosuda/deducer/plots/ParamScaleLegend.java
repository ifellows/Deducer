package org.rosuda.deducer.plots;

import java.util.Vector;

import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.data.ExDefaultTableModel;
import org.rosuda.deducer.toolkit.XMLHelper;
import org.rosuda.deducer.widgets.param.Param;
import org.rosuda.deducer.widgets.param.ParamWidget;
import org.rosuda.deducer.widgets.param.RFunction;
import org.rosuda.deducer.widgets.param.RFunctionList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParamScaleLegend extends Param{
	
	
	final public static String VIEW_SCALE = "scale";
	
	protected Vector value;
	protected RFunctionList breaksFunctionList;
	protected RFunctionList labelsFunctionList;
	protected RFunctionList guideFunctionList;
	protected Vector defaultValue;
	protected boolean numeric =true;
	protected String aes = "";
	
	public ParamScaleLegend(){
		setViewType(VIEW_SCALE);
		breaksFunctionList = (RFunctionList) ParamFactory.makeParam("scalebreaks");
		labelsFunctionList = (RFunctionList) ParamFactory.makeParam("labels");
		guideFunctionList = (RFunctionList) ParamFactory.makeParam("guide");
	}
	
	public ParamScaleLegend(String nm, String newaes,boolean num){
		setName(nm);
		setTitle(nm);
		setViewType(VIEW_SCALE);
		breaksFunctionList = (RFunctionList) ParamFactory.makeParam("scalebreaks");
		labelsFunctionList = (RFunctionList) ParamFactory.makeParam("labels");
		guideFunctionList = (RFunctionList) ParamFactory.makeParam("guide");
		this.setNumeric(num);
		this.setAes(newaes);
	}
	
	public ParamWidget getView(){
		if(getViewType().equals(ParamScaleLegend.VIEW_SCALE))
			return new ParamScaleWidget(this);
		System.out.println("invalid view");
		(new Exception()).printStackTrace();
		return null;
	}
	
	public Object clone(){
		ParamScaleLegend p = new ParamScaleLegend();
		p.setName(this.getName());
		p.setTitle(this.getTitle());
		p.setViewType(this.getViewType());
		p.setNumeric(this.isNumeric());
		p.aes = this.aes;
		if(this.getLowerBound()!=null)
			p.setLowerBound(new Double(this.getLowerBound().doubleValue()));
		if(this.getUpperBound()!=null)
			p.setUpperBound(new Double(this.getUpperBound().doubleValue()));
		p.breaksFunctionList = (RFunctionList) breaksFunctionList.clone();
		p.labelsFunctionList = (RFunctionList) labelsFunctionList.clone();
		p.guideFunctionList = (RFunctionList) guideFunctionList.clone();
		if(this.getValue()!=null){
			Vector newValue = new Vector();
			Vector curValue = (Vector) this.getValue();
			newValue.add(curValue.get(0));
			newValue.add(new Boolean(((Boolean)curValue.get(1)).booleanValue()));
			ExDefaultTableModel curTm = (ExDefaultTableModel) curValue.get(2);
			ExDefaultTableModel tm = new ExDefaultTableModel();
			tm.setRowCount(curTm.getRowCount());
			tm.setColumnCount(curTm.getColumnCount());
			for(int i=0;i<curTm.getRowCount();i++){
				for(int j=0;j<curTm.getColumnCount();j++){
					tm.setValueAt(curTm.getValueAt(i, j), i, j);
				}
			}
			newValue.add(tm);
			p.setValue(newValue);
		}else
			p.setValue(null);
		return p;
	}
	
	public String[] getAesCalls(){
		return new String[]{};
	}
	
	public String[] getParamCalls(){
		String[] calls = new String[]{};
		if(getValue()!=null && !getValue().equals(getDefaultValue())){
			Vector dBreaks = new Vector();
			Vector dLabels = new Vector();		
			String dNm = null;
			Boolean dShow = null;
			if(getDefaultValue()!=null){
				Vector v = (Vector) getDefaultValue();
				dNm = (String) v.get(0);
				dShow = ((Boolean)v.get(1));
				ExDefaultTableModel dTm = (ExDefaultTableModel) v.get(2);
				for(int i=0;i<dTm.getRowCount();i++){
					String br = (String) dTm.getValueAt(i, 0);
					String lab = (String) dTm.getValueAt(i, 1);
					if(br!=null && br.length()>0){
						dBreaks.add(br);
						if(lab!=null && lab.length()>0)
							dLabels.add(lab);
						else
							dLabels.add("");
					}
				}
			}
			boolean allLabelsMissing = true;
			if(getValue()!=null){
				Vector v = (Vector) getValue();
				String nm = (String) v.get(0);
//				Boolean show = ((Boolean)v.get(1)); //depricated
				ExDefaultTableModel tm = (ExDefaultTableModel) v.get(2);
				Vector breaks = new Vector();
				Vector labels = new Vector();
				for(int i=0;i<tm.getRowCount();i++){
					String br = (String) tm.getValueAt(i, 0);
					String lab = (String) tm.getValueAt(i, 1);
					if(br!=null && br.length()>0){
						breaks.add(br);
						if(lab!=null && lab.length()>0){
							labels.add(lab);
							allLabelsMissing = false;
						}else
							labels.add("");
					}
				}
				
				String nameCall = null;
				if(nm!=null && nm!=dNm && !(dNm==null && nm.length()==0)){
					nameCall = "name = '" +nm+"'";
				}
//				String showCall = null;
//				if(show!=null && !show.equals(dShow) && !(dShow==null && show.booleanValue()))
//					showCall = "legend = " + (show.booleanValue() ? "TRUE" : "FALSE");
				String breakCall = null;
				String labelCall = null;
				if(!(breaks.equals(dBreaks) && labels.equals(dLabels))){
					breakCall = "breaks = " + Deducer.makeRCollection(breaks, "c", 
							!numeric);
					labelCall = "labels = " + Deducer.makeRCollection(labels, "c",true);
				}
				if(breaks.size()==0){
					String[]tmp = breaksFunctionList.getParamCalls();
					if(tmp.length==1)
						breakCall = tmp[0];
				}
				
				if(allLabelsMissing){
					labelCall = null;
					String[]tmp =labelsFunctionList.getParamCalls();
					if(tmp.length==1)
						labelCall = tmp[0];
				}
				
				Vector callVector = new Vector();	
				if(!aes.equals("x") && !aes.equals("y")){
					String[] tmp = guideFunctionList.getParamCalls();
					for(int i=0;i<tmp.length;i++)
						callVector.add(tmp[i]);
				}
				
				if(nameCall!=null)
					callVector.add(nameCall);
//				if(showCall != null)
//					callVector.add(showCall);
				if(breakCall!=null)
					callVector.add(breakCall);
				if(labelCall!=null)
					callVector.add(labelCall);
				calls = new String[callVector.size()];
				for(int i=0;i<callVector.size();i++)
					calls[i] = (String) callVector.get(i);
			}
		}
		return calls;
	}

	public void setDefaultValue(Object defaultValue) {
		if(defaultValue instanceof Vector || defaultValue ==null)
			this.defaultValue = (Vector) defaultValue;
		else
			System.out.println("ParamScaleLegend: invalid setDefaultValue");
	}
	
	public Object getDefaultValue() {
		return defaultValue;
	}
	
	public void setValue(Object value) {
		if(value instanceof Vector || value==null)
			this.value = (Vector) value;
		else{
			System.out.println("ParamScaleLegend: invalid setValue");
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	public Object getValue() {
		return value;
	}

	public void setNumeric(boolean numeric) {
		this.numeric = numeric;
	}

	public boolean isNumeric() {
		return numeric;
	}
	
	public void setAes(String aesthetic){
		if(aes == aesthetic)
			return;
		aes = aesthetic;
		//System.out.println(aes);
		//System.out.println(numeric);
		
		String[] labs = guideFunctionList.getOptions();
		boolean hasColourBar = false;
		if(labs!=null){
			for(int i=0;i<labs.length;i++){
				if(labs[i].equals("Color bar"))
					hasColourBar = true;
				//System.out.println(labs[i]);
			}
		}
		if(!hasColourBar && numeric && (aes.equals("colour") || aes.equals("color") 
				|| aes.equals("fill"))){
			//System.out.println("colorbar");
			guideFunctionList = (RFunctionList) ParamFactory.makeParam("guide");
			RFunction rf = (RFunction) ParamFactory.makeParam("guidecolourbar");
			guideFunctionList.addRFunction("Color bar", rf);
		}else if(hasColourBar){
			guideFunctionList = (RFunctionList) ParamFactory.makeParam("guide");
		}
	}
	
	public String getAes(){
		return aes;
	}
	
	
	public RFunctionList getBreaksModel(){
		return breaksFunctionList;
	}
	
	public RFunctionList getLabelsModel(){
		return labelsFunctionList;
	}
	
	public RFunctionList getGuideModel(){
		return guideFunctionList;
	}
	
	public Element toXML(){
		
		Element e = super.toXML();
		Document doc = e.getOwnerDocument();
		e.setAttribute("aes",aes);
		if(value!=null){
			Vector v = (Vector) getValue();
			String text = (String) v.get(0);
			Boolean show = (Boolean) v.get(1);
			ExDefaultTableModel tm = (ExDefaultTableModel) v.get(2);
			Element node = doc.createElement("value");
			node.setAttribute("legendTitle", text);
			node.setAttribute("show", show.toString());
			node.setAttribute("nrow", tm.getRowCount()+"");
			node.setAttribute("aes",this.aes);
			Element cNode = doc.createElement("column_0");
			for(int i=0;i<tm.getRowCount();i++)
				cNode.setAttribute("element_"+i, tm.getValueAt(i, 0)==null ? null : tm.getValueAt(i, 0).toString());
			node.appendChild(cNode);
			
			cNode = doc.createElement("column_1");
			for(int i=0;i<tm.getRowCount();i++)
				cNode.setAttribute("element_"+i, tm.getValueAt(i, 1)==null ? null : tm.getValueAt(i, 1).toString());
			node.appendChild(cNode);
			e.appendChild(node);
		}
		
		if(defaultValue!=null){
			Vector v = (Vector) getDefaultValue();
			String text = (String) v.get(0);
			Boolean show = (Boolean) v.get(1);
			ExDefaultTableModel tm = (ExDefaultTableModel) v.get(2);
			Element node = doc.createElement("defaultValue");
			node.setAttribute("legendTitle", text);
			node.setAttribute("show", show.toString());
			node.setAttribute("nrow", tm.getRowCount()+"");
			
			Element cNode = doc.createElement("column_0");
			for(int i=0;i<tm.getRowCount();i++)
				cNode.setAttribute("element_"+i, tm.getValueAt(i, 0)==null ? null : tm.getValueAt(i, 0).toString());
			node.appendChild(cNode);
			
			cNode = doc.createElement("column_1");
			for(int i=0;i<tm.getRowCount();i++)
				cNode.setAttribute("element_"+i, tm.getValueAt(i, 1)==null ? null : tm.getValueAt(i, 1).toString());
			node.appendChild(cNode);
			e.appendChild(node);
		}
		Element cNode = doc.createElement("labelsFuncList");
		Element element = labelsFunctionList.toXML();
		element =  (Element) doc.importNode(element, true);
		cNode.appendChild(element);
		e.appendChild(cNode);
		
		cNode = doc.createElement("breaksFuncList");
		element = breaksFunctionList.toXML();
		element =  (Element) doc.importNode(element, true);
		cNode.appendChild(element);
		e.appendChild(cNode);
		
		cNode = doc.createElement("guideFuncList");
		element = guideFunctionList.toXML();
		element =  (Element) doc.importNode(element, true);
		cNode.appendChild(element);
		e.appendChild(cNode);
		
		e.setAttribute("className", "org.rosuda.deducer.plots.ParamScaleLegend");
		return e;
	}
	
	public void setFromXML(Element node){
		//TODO:params
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.plots.ParamScaleLegend")){
			System.out.println("Error ParamScaleLegend: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		setAes(node.getAttribute("aes"));
		Document doc = node.getOwnerDocument();
		super.setFromXML(node);
		Vector valEls = XMLHelper.getChildrenElementsByTag(node,"value");
		value = null;
		defaultValue = null;
		if(valEls.size()>0){
			Element e = (Element) valEls.get(0);
			Vector v = new Vector();
			v.add(e.getAttribute("legendTitle"));
			v.add(new Boolean(e.getAttribute("show").equals("true")));
			ExDefaultTableModel tm = new ExDefaultTableModel();
			tm.addColumn("value");
			tm.addColumn("label");
			tm.setRowCount(Integer.parseInt(e.getAttribute("nrow")));
			Node cNode =e.getElementsByTagName("column_0").item(0);
			NamedNodeMap attr = cNode.getAttributes();
			Node c1Node =e.getElementsByTagName("column_1").item(0);
			NamedNodeMap attr1 = c1Node.getAttributes();
			if(attr.getLength()>0){
				for(int i=0;i<attr.getLength();i++){
					tm.setValueAt(attr.item(i).getNodeValue(),i,0);
					tm.setValueAt(attr1.item(i).getNodeValue(),i,1);
				}
			}
			v.add(tm);
			value = v;
		}
		Vector deValEls = XMLHelper.getChildrenElementsByTag(node,"defaultValue");
		if(deValEls.size()>0){
			Element e = (Element) deValEls.get(0);
			Vector v = new Vector();
			v.add(e.getAttribute("legendTitle"));
			v.add(new Boolean(e.getAttribute("show").equals("true")));
			ExDefaultTableModel tm = new ExDefaultTableModel();
			tm.addColumn("value");
			tm.addColumn("label");
			tm.setRowCount(Integer.parseInt(e.getAttribute("nrow")));
			
			Node cNode =e.getElementsByTagName("column_0").item(0);
			NamedNodeMap attr = cNode.getAttributes();
			Node c1Node =e.getElementsByTagName("column_1").item(0);
			NamedNodeMap attr1 = c1Node.getAttributes();
			if(attr.getLength()>0){
				for(int i=0;i<attr.getLength();i++){
					tm.setValueAt(attr.item(i).getNodeValue(),i,0);
					tm.setValueAt(attr1.item(i).getNodeValue(),i,1);
				}
			}
			v.add(tm);
			defaultValue = v;
		}
		//(new Exception()).printStackTrace();
		//System.out.println("------------------------------------");
		//XMLHelper.debugPrintChildren(node);
		Element e = (Element)XMLHelper.getChildrenElementsByTag(node, "labelsFuncList").get(0);
		e = (Element) XMLHelper.getChildrenElementsByTag(e, "Param").get(0);
		labelsFunctionList.setFromXML(e);
		
		e = (Element)XMLHelper.getChildrenElementsByTag(node, "breaksFuncList").get(0);
		e = (Element) XMLHelper.getChildrenElementsByTag(e, "Param").get(0);
		breaksFunctionList.setFromXML(e);
		
		e = (Element)XMLHelper.getChildrenElementsByTag(node, "guideFuncList").get(0);
		e = (Element) XMLHelper.getChildrenElementsByTag(e, "Param").get(0);
		guideFunctionList.setFromXML(e);
	}
	
	
}
