package org.rosuda.deducer.plots;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.rosuda.deducer.widgets.param.Param;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Template implements CompoundElementModel{

	protected PlottingElement[] elements = new PlottingElement[]{};
	
	private String data;
	
	protected MaskingAes[] mAess = new MaskingAes[]{};
	protected MaskingParam[] mParams = new MaskingParam[]{};
	
	public PlottingElement[] getElements() {
		return elements;
	}

	public String checkValid() {
		for(int i=0;i<getModels().length;i++){
			String s = getModels()[i].checkValid();
			if(s!=null)
				return s;
		}
		return null;
	}

	public String getCall() {
		String cmd = "";
		
		for(int i=0;i<getModels().length;i++){
			if(elements[i].isActive())
				cmd += (i==0 ? "" : " +\n\t") + getModels()[i].getCall();
		}
		
		return cmd;
	}

	public Vector getParams() {
		Vector v = new Vector();
		for(int i=0;i<mParams.length;i++)
			if(mParams[i].show)
				v.add(mParams[i].param);
		return v;
	}
	
	public Vector getAess(){
		Vector v = new Vector();
		for(int i=0;i<mAess.length;i++)
			if(mAess[i].show)
				v.add(mAess[i].aes);
		return v;
	}
	
	public Vector getMAess(){
		Vector v = new Vector();
		for(int i=0;i<mAess.length;i++)
			if(mAess[i].show)
				v.add(mAess[i]);
		return v;
	}
	
	

	public String getType() {
		return "template";
	}

	public ElementView getView() {
		return new TemplateView(this);
	}

	public void setFromXML(Element node) {
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.plots.Template")){
			System.out.println("Error Template: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		
		if(node.hasAttribute("data"))
			data = node.getAttribute("data");
		else 
			data = null;
		
		Element child;
		NodeList nl;
		
		child = (Element) node.getElementsByTagName("elements").item(0);
		nl = child.getElementsByTagName("PlottingElement");
		elements = new PlottingElement[nl.getLength()];		
		for(int i=0;i<nl.getLength();i++){
			Element n = (Element) nl.item(i);
			PlottingElement pe = new PlottingElement();
			pe.setFromXML(n);
			elements[i] = pe;
		}
		
		child = (Element) node.getElementsByTagName("mAess").item(0);
		nl = child.getElementsByTagName("MaskingAes");
		mAess = new MaskingAes[nl.getLength()];		
		for(int i=0;i<nl.getLength();i++){
			Element n = (Element) nl.item(i);
			MaskingAes pe = new MaskingAes();
			pe.setFromXML(n);
			mAess[i] = pe;
		}
		
		child = (Element) node.getElementsByTagName("mParams").item(0);
		nl = child.getElementsByTagName("MaskingParam");
		mParams = new MaskingParam[nl.getLength()];		
		for(int i=0;i<nl.getLength();i++){
			Element n = (Element) nl.item(i);
			MaskingParam pe = new MaskingParam();
			pe.setFromXML(n);
			mParams[i] = pe;
		}
		this.updateElementModels();
	}

	public Element toXML() {
		try{
			//System.out.println("toXml: "+getCall());
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element node = doc.createElement("ElementModel");
			node.setAttribute("className", "org.rosuda.deducer.plots.Template");
			if(data!=null)
				node.setAttribute("data", data);
			Element tmpNode = doc.createElement("elements");
			for(int i=0;i<elements.length;i++){
				Element pEl = elements[i].toXML();
				pEl = (Element) doc.importNode(pEl, true);
				tmpNode.appendChild(pEl);
			}
			node.appendChild(tmpNode);
			
			tmpNode = doc.createElement("mAess");
			for(int i=0;i<mAess.length;i++){
				Element pEl = mAess[i].toXML();
				pEl = (Element) doc.importNode(pEl, true);
				tmpNode.appendChild(pEl);
			}
			node.appendChild(tmpNode);
			
			tmpNode = doc.createElement("mParams");
			for(int i=0;i<mParams.length;i++){
				Element pEl = mParams[i].toXML();
				pEl = (Element) doc.importNode(pEl, true);
				tmpNode.appendChild(pEl);
			}
			node.appendChild(tmpNode);
			return node;
		}catch(Exception e){e.printStackTrace();return null;}
	}

	public Object clone(){
		Template t = new Template();
		
		t.elements = new PlottingElement[elements.length];
		for(int i=0;i<elements.length;i++)
			t.elements[i] = (PlottingElement) elements[i].clone();
		
		t.mAess = new MaskingAes[mAess.length];
		for(int i=0;i<mAess.length;i++)
			t.mAess[i] = (MaskingAes) mAess[i].clone(t);		

		t.mParams = new MaskingParam[mParams.length];
		for(int i=0;i<mParams.length;i++)
			t.mParams[i] = (MaskingParam) mParams[i].clone(t);
		t.data = this.data;
		
		
		return t;
	}
	
	public void addElement(PlottingElement em,boolean required){
		PlottingElement[] newAr = new PlottingElement[elements.length+1];
		for(int i=0;i<elements.length;i++)
			newAr[i] = elements[i];
		newAr[getModels().length] = em;
		elements = newAr;	
	}
	
	private void addMaskingAes(MaskingAes mAes){
		MaskingAes[] newAr = new MaskingAes[mAess.length+1];
		for(int i=0;i<mAess.length;i++)
			newAr[i] = mAess[i];
		newAr[mAess.length] = mAes;
		mAess = newAr;
	}
	
	private void addMaskingParam(MaskingParam mParam){
		MaskingParam[] newAr = new MaskingParam[mParams.length+1];
		for(int i=0;i<mParams.length;i++)
			newAr[i] = mParams[i];
		newAr[mParams.length] = mParam;
		mParams = newAr;		
	}

	public void addParam(Param param,int elementIndex,boolean show){
		MaskingParam p = new MaskingParam();
		p.param = (Param) param.clone();
		p.paramName = param.getName();
		p.elementIndex = elementIndex;
		p.show = show;
		this.addMaskingParam(p);
	}
	public void addAes(Aes aes,Vector layGen,int elementIndex,String title,boolean show){
		boolean exists = false;
		for(int i =0;i<mAess.length;i++){
			if(mAess[i].name.equals(aes.name) && aes.useVariable==mAess[i].isMap){
				if( aes.useVariable && (aes.variable!=null && 
						aes.variable.equals(mAess[i].aes.variable)) ){
					if(!mAess[i].elementIndices.contains(new Integer(elementIndex)))
						mAess[i].elementIndices.add(new Integer(elementIndex));
					mAess[i].aes.name = title;
					mAess[i].show = show;
					for(int j=0;j<layGen.size();j++)
						mAess[i].generated.add(layGen.get(j));
					exists=true;
					break;
					
				}
			}
		}
		if(!exists){
			MaskingAes maes = new MaskingAes();
			maes.aes = (Aes) aes.clone();
			maes.aes.name = title;
			maes.name = aes.name;
			maes.elementIndices.add(new Integer(elementIndex));
			maes.isMap = aes.useVariable;
			maes.show = show;
			for(int j=0;j<layGen.size();j++)
				maes.generated.add(layGen.get(j));
			addMaskingAes(maes);
		}
	}
	
	public void updateElementModels(){
		for(int i=0;i<elements.length;i++){
			ElementModel em =elements[i].getModel();
			if(em instanceof Layer){
				((Layer)em).data = data;
			}
		}		
		for(int i=0;i<mAess.length;i++)
			mAess[i].update();
		for(int i=0;i<mParams.length;i++)
			mParams[i].update();
	}
	
	public static Template makeTemplate(PlotBuilderModel pbm){
		Template t = new Template();
		DefaultListModel lm = pbm.getListModel();
		for(int i=0;i<lm.size();i++){

			ElementModel em = ((PlottingElement) lm.get(i)).getModel();
			if(em.getData()!=null)
				t.data = em.getData();
			t.addElement(((PlottingElement) lm.get(i)),true);
			if(em instanceof Layer){
				Vector aess = ((Layer)em).aess;
				for(int j=0;j<aess.size();j++){
					Aes aes = (Aes) aess.get(j);
					boolean show = aes.useVariable ? aes.getAesCalls().length>0
													: aes.getParamCalls().length>0;
					
					Vector gen = ((Layer)em).stat.generated;
					t.addAes((Aes)aes.clone(),gen, i, aes.name,show);
				}
			}
			Vector params = em.getParams();
			for(int j=0;j<params.size();j++){
				Param param = (Param) params.get(j);
				boolean show = param.getParamCalls().length>0;
				t.addParam((Param)param.clone(), i, show);
			}
			
		}      
		return t;
		
	}
	

	protected ElementModel[] getModels() {
		ElementModel[] models = new ElementModel[elements.length];
		for(int i=0;i<elements.length;i++)
			models[i] = elements[i].getModel();
		return models;
	}


	public class MaskingAes{
		Aes aes;
		Vector elementIndices = new Vector();
		String name;
		boolean isMap=true;
		boolean show = true;
		
		public Vector generated = new Vector();
		
		public Object clone(Template temp){
			MaskingAes ma = temp.new MaskingAes();
			ma.aes = (Aes) aes.clone();
			for(int i=0;i<elementIndices.size();i++)
				ma.elementIndices.add(new Integer(((Integer)elementIndices.get(i)).intValue()));
			ma.name = name;
			ma.isMap = isMap;
			ma.show = show;
			
			ma.generated = new Vector();
			for(int i=0;i<generated.size();i++)
				ma.generated.add(generated.get(i));
			
			return ma;
		}
		
		public void update(){
			//System.out.println(name+ " : " + elementIndices.toString());
			for(int i=0;i<elementIndices.size();i++){
				Layer l =((Layer) getModels()[((Integer)elementIndices.get(i)).intValue()]);
				Vector aess = l.aess;
				for(int j=0;j<aess.size();j++){
					Aes layerAes = (Aes) aess.get(j);
					//System.out.println(layerAes.name);
					if(layerAes.name.equals(name) && show){
						
						if(isMap)
							layerAes.variable = aes.variable;
						else
							layerAes.value = aes.value;
						//System.out.println(isMap + " aess " +layerAes.name + layerAes.variable);
					}
				}
				
				aess = l.stat.aess;
				for(int j=0;j<aess.size();j++){
					Aes layerAes = (Aes) aess.get(j);
					//System.out.println(layerAes.name);
					if(layerAes.name.equals(name) && show){
						if(isMap)
							layerAes.variable = aes.variable;
						else
							layerAes.value = aes.value;
						//System.out.println(l.getName() + " stat " + layerAes.name + layerAes.variable);
					}
				}
				
				aess = l.geom.aess;
				for(int j=0;j<aess.size();j++){
					Aes layerAes = (Aes) aess.get(j);
					//System.out.println(layerAes.name);
					if(layerAes.name.equals(name) && show){
						
						if(isMap)
							layerAes.variable = aes.variable;
						else
							layerAes.value = aes.value;
						//System.out.println(l.getName() + " geom " + layerAes.name + layerAes.variable);
					}
				}
			}
			//System.out.println(getCall());
		}
		public Element toXML() {
			try{
				DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
				Document doc = docBuilder.newDocument();
				Element node = doc.createElement("MaskingAes");
				node.setAttribute("className", "org.rosuda.deducer.plots.Template.MaskingAes");
				if(name!=null)
					node.setAttribute("name", name);
				
				Element varNode = doc.createElement("elementIndices");
				if(elementIndices!=null)
					for(int i=0;i<elementIndices.size();i++)
						varNode.setAttribute("element_"+i, elementIndices.get(i).toString());
				node.appendChild(varNode);
				
				Element generatedNode = doc.createElement("generated");
				if(generated!=null)
					for(int i=0;i<generated.size();i++)
						generatedNode.setAttribute("element_"+i, generated.get(i).toString());
				node.appendChild(generatedNode);
				
				node.setAttribute("isMap", isMap ? "true" : "false");
				node.setAttribute("show", show ? "true" : "false");
				Element pEl = aes.toXML();
				pEl = (Element) doc.importNode(pEl, true);
				node.appendChild(pEl);
				doc.appendChild(node);
				return node;
			}catch(Exception ex){ex.printStackTrace();return null;}		
		}

		public void setFromXML(Element node) {
			String cn = node.getAttribute("className");
			if(!cn.equals("org.rosuda.deducer.plots.Template.MaskingAes")){
				System.out.println("Error MaskingAes: class mismatch: " + cn);
				(new Exception()).printStackTrace();
			}
			if(node.hasAttribute("name"))
				name = node.getAttribute("name");
			else 
				name = null;
			
			Node generatedNode =node.getElementsByTagName("generated").item(0);
			NamedNodeMap attr = generatedNode.getAttributes();
			if(attr.getLength()>0){
				generated = new Vector();
				for(int i=0;i<attr.getLength();i++)
					generated.add(i,attr.item(i).getNodeValue());
			}
			
			Node varNode =node.getElementsByTagName("elementIndices").item(0);
			attr = varNode.getAttributes();
			if(attr.getLength()>0){
				elementIndices = new Vector();
				for(int i=0;i<attr.getLength();i++)
					elementIndices.add(new Integer(Integer.parseInt(attr.item(i).getNodeValue())));
			}
			
			show = node.getAttribute("show").equals("true");
			isMap = node.getAttribute("isMap").equals("true");
			Element n = (Element) node.getElementsByTagName("Aes").item(0);
			//System.out.println(node.getElementsByTagName("Aes").toString());
			Aes a = new Aes();
			a.setFromXML(n);
			aes = a;
		}
	}
	
	public class MaskingParam{
		Param param;
		String paramName = "";
		int elementIndex = -1;
		boolean show = true;
		
		public Object clone(Template temp){
			MaskingParam mp = temp.new MaskingParam();
			mp.param = (Param) param.clone();
			mp.paramName = paramName;
			mp.elementIndex = elementIndex;
			mp.show = show;
			return mp;
		}
		
		public void update(){
			ElementModel mod = getModels()[elementIndex];
			Vector params = mod.getParams();
			for(int j=0;j<params.size();j++){
				Param layerParam = (Param) params.get(j);
				if(layerParam.getName().equals(paramName) && show){
					layerParam.setValue(param.getValue());
				}
			}
		}
		
		public Element toXML() {
			try{
				DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
				Document doc = docBuilder.newDocument();
				Element node = doc.createElement("MaskingParam");
				node.setAttribute("className", "org.rosuda.deducer.plots.Template.MaskingParam");
				if(paramName!=null)
					node.setAttribute("paramName", paramName);
				node.setAttribute("elementIndex", elementIndex+"");
				node.setAttribute("show", show ? "true" : "false");
				Element pEl = param.toXML();
				pEl = (Element) doc.importNode(pEl, true);
				node.appendChild(pEl);
				doc.appendChild(node);
				return node;
			}catch(Exception ex){ex.printStackTrace();return null;}		
		}

		public void setFromXML(Element node) {
			String cn = node.getAttribute("className");
			if(!cn.equals("org.rosuda.deducer.plots.Template.MaskingParam")){
				System.out.println("Error MaskingParam: class mismatch: " + cn);
				(new Exception()).printStackTrace();
			}
			if(node.hasAttribute("paramName"))
				paramName = node.getAttribute("paramName");
			else 
				paramName = null;
			elementIndex = Integer.parseInt(node.getAttribute("elementIndex"));
			show = node.getAttribute("show").equals("true");
			
			Element n = (Element) node.getElementsByTagName("Param").item(0);
			cn = n.getAttribute("className");
			Param p = Param.makeParam(cn);
			p.setFromXML(n);
			param = p;
		}	
	}
	
	public void saveToFile(File f){
		
		Template t = (Template) this.clone();
		for(int i=0;i<t.mAess.length;i++){
			t.mAess[i].aes.variable = null;
			t.mAess[i].update();
		}
		
		Element e = t.toXML();
		Document doc = e.getOwnerDocument();
		try{
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans;
			trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(doc);
			trans.transform(source, result);
			String xmlString = sw.toString();
			//System.out.println(xmlString);
			
			FileOutputStream fos = new FileOutputStream(f);
			OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8"); 
			out.write(xmlString);
			
			out.close();
			fos.close();
			sw.close();
			
		}catch(Exception ex){ex.printStackTrace();}
	}
	
	public void setFromFile(File f){
		try{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(f);
			Element e = (Element)doc.getChildNodes().item(0);
			this.setFromXML(e);
		}catch(Exception ex){ex.printStackTrace();}
	}
	

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}
	
}




