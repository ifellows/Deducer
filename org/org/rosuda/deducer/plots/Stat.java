package org.rosuda.deducer.plots;

import java.awt.Color;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.rosuda.deducer.toolkit.XMLHelper;
import org.rosuda.deducer.widgets.param.Param;
import org.rosuda.deducer.widgets.param.ParamCharacter;
import org.rosuda.deducer.widgets.param.ParamLogical;
import org.rosuda.deducer.widgets.param.ParamNumeric;
import org.rosuda.deducer.widgets.param.RFunctionList;
import org.rosuda.deducer.widgets.param.ParamVector;
import org.rosuda.deducer.widgets.param.RFunction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Stat {
	
	public String name;
	public Vector aess = new Vector();
	public Vector params = new Vector();
	
	public String defaultGeom;
	
	public Vector generated = new Vector();
	
	public Object clone(){
		Stat s = new Stat();
		s.name = this.name;
		s.defaultGeom = this.defaultGeom;
		for(int i=0;i<aess.size();i++){
			Aes aes = (Aes) aess.get(i);
			s.aess.add(aes.clone());
		}
		for(int i=0;i<params.size();i++){
			Param p = (Param) params.get(i);
			s.params.add(p.clone());
		}
		for(int i=0;i<generated.size();i++)
			s.generated.add(generated.get(i));
		
		return s;
	}
	
	public static Stat makeIdentity(){
		Stat s = new Stat();
		s.name = "identity";
		s.defaultGeom = "point";
		return s;
	}
	
	public static Stat makeAbline(){
		Stat s = new Stat();
		s.name = "abline";
		s.defaultGeom = "abline";
		
		return s;
	}
	
	public static Stat makeBin(){
		Stat s = new Stat();
		s.name = "bin";
		s.defaultGeom = "bar";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		aes.required = true;
		s.aess.add(aes);
		
		aes = Aes.makeAes("y");
		aes.defaultVariable = "..count..";
		aes.variable = "..count..";
		s.aess.add(aes);
		
		Param p ;
		ParamNumeric pn;
		
		p= ParamFactory.makeParam("binwidth");
		s.params.add(p);
		
		p= ParamFactory.makeParam("origin");
		s.params.add(p);
		
		p= ParamFactory.makeParam("breaks");
		s.params.add(p);
		
		p= ParamFactory.makeParam("width");
		s.params.add(p);
		
		p= ParamFactory.makeParam("drop");
		s.params.add(p);
		
		s.generated.add("count");
		s.generated.add("density");
		s.generated.add("ncount");
		s.generated.add("ndensity");
		
		return s;
	}
	
	public static Stat makeBin2d(){
		Stat s = new Stat();
		s.name = "bin2d";
		s.defaultGeom = "rect";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		aes.required = true;
		s.aess.add(aes);
		
		aes = Aes.makeAes("y");
		aes.required = true;
		s.aess.add(aes);
		
		aes = Aes.makeAes("fill");
		aes.defaultVariable = "..count..";
		aes.variable = "..count..";
		s.aess.add(aes);
		
		Param p ;
		ParamVector pv;
		
		pv = new ParamVector("binwidth");
		pv.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		s.params.add(pv);
		
		
		pv = new ParamVector("origin");
		pv.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		s.params.add(pv);
		
		RFunctionList prf;
		RFunction rf;
		prf = new RFunctionList("breaks");
		rf = new RFunction();
		rf.setName("list");
		rf.add(new ParamVector("x"));
		rf.add(new ParamVector("y"));
		prf.addRFunction("breaks", rf);
		s.params.add(prf);
		
		
		p= ParamFactory.makeParam("drop");
		s.params.add(p);
		
		s.generated.add("count");
		s.generated.add("density");
		s.generated.add("xint");
		s.generated.add("xmin");
		s.generated.add("xmax");
		s.generated.add("yint");
		s.generated.add("ymin");
		s.generated.add("ymax");
		
		return s;
	}	
	
	public static Stat makeBinDot(){
		Stat s = new Stat();
		s.name = "bindot";
		s.defaultGeom = "dotplot";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		aes.required = true;
		s.aess.add(aes);
		
		aes = Aes.makeAes("y");
		aes.defaultVariable = "..count..";
		aes.variable = "..count..";
		s.aess.add(aes);
		
		Param p ;
		ParamNumeric pn;
		
		ParamCharacter pc = new ParamCharacter("method");
		pc.setOptions(new String[]{"dotdensity","histodot"});
		pc.setViewType(ParamCharacter.VIEW_COMBO);
		pc.setDefaultValue("dotdensity");
		pc.setValue("dotdensity");
		s.params.add(pc);
		
		pc = new ParamCharacter("binpositions");
		pc.setOptions(new String[]{"bygroup","all"});
		pc.setViewType(ParamCharacter.VIEW_COMBO);
		pc.setDefaultValue("bygroup");
		pc.setValue("bygroup");
		s.params.add(pc);
		
		pc = new ParamCharacter("binaxis");
		pc.setOptions(new String[]{"x","y"});
		pc.setViewType(ParamCharacter.VIEW_COMBO);
		pc.setDefaultValue("x");
		pc.setValue("x");
		s.params.add(pc);
		
		
		p= ParamFactory.makeParam("binwidth");
		s.params.add(p);
		
		p= ParamFactory.makeParam("origin");
		s.params.add(p);
		
		p= ParamFactory.makeParam("breaks");
		s.params.add(p);
		
		pn= (ParamNumeric) ParamFactory.makeParam("width");
		pn.setDefaultValue(.9);
		pn.setValue(.9);
		s.params.add(pn);
		
		p= ParamFactory.makeParam("drop");
		p.setDefaultValue(new Boolean(false));
		p.setValue(new Boolean(false));
		s.params.add(p);
		
		ParamLogical pl = new ParamLogical("right");
		pl.setDefaultValue(true);
		pl.setValue(true);
		s.params.add(pl);
		
		p= ParamFactory.makeParam("na.rm");
		s.params.add(p);
		
		s.generated.add("count");
		
		return s;
	}
	
	public static Stat makeBoxplot(){
		Stat s = new Stat();
		s.name = "boxplot";
		s.defaultGeom = "boxplot";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		aes.required = true;
		s.aess.add(aes);
		
		aes = Aes.makeAes("y");
		aes.required = true;
		s.aess.add(aes);
		

		Param p ;
		p= ParamFactory.makeParam("na.rm");
		s.params.add(p);
		
		p= ParamFactory.makeParam("width");
		s.params.add(p);
		
		p= ParamFactory.makeParam("coef");
		s.params.add(p);
		
		s.generated.add("width");
		s.generated.add("ymin");
		s.generated.add("lower");
		s.generated.add("middle");
		s.generated.add("upper");
		s.generated.add("ymax");
		s.generated.add("ymin");
		s.generated.add("ymax");
		
		return s;
	}	
	
	public static Stat makeContour(){
		Stat s = new Stat();
		s.name = "contour";
		s.defaultGeom = "path";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		aes.required = true;
		s.aess.add(aes);
		
		aes = Aes.makeAes("y");
		aes.required = true;
		s.aess.add(aes);
		
		aes = Aes.makeAes("z");
		aes.required = true;
		s.aess.add(aes);
		
		aes = Aes.makeAes("group");
		aes.defaultUseVariable = true;
		aes.defaultVariable = "..piece..";
		s.aess.add(aes);

		Param p;
		
		p= ParamFactory.makeParam("bins");
		s.params.add(p);
		
		
		p= ParamFactory.makeParam("binwidth");
		s.params.add(p);
		
		p= ParamFactory.makeParam("breaks");
		s.params.add(p);
		
		p= ParamFactory.makeParam("na.rm");
		s.params.add(p);
		
		s.generated.add("level");
		s.generated.add("piece");
		
		return s;
	}	
	
	public static Stat makeDensity(){
		Stat s = new Stat();
		s.name = "density";
		s.defaultGeom = "area";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		aes.required = true;
		s.aess.add(aes);
		
		aes = Aes.makeAes("y");
		aes.required = true;
		aes.defaultVariable = "..density..";
		aes.variable = "..density..";
		s.aess.add(aes);
		
		aes = Aes.makeAes("fill");
		aes.preferCategorical=true;
		s.aess.add(aes);
		
		aes = Aes.makeAes("group");
		s.aess.add(aes);

		Param p;
		
		p= ParamFactory.makeParam("adjust");
		s.params.add(p);
		
		
		p= ParamFactory.makeParam("kernel");
		s.params.add(p);
		
		p= ParamFactory.makeParam("trim");
		s.params.add(p);
		
		p= ParamFactory.makeParam("na.rm");
		s.params.add(p);
		
		s.generated.add("density");
		s.generated.add("count");
		s.generated.add("scaled");
		
		return s;
	}	
	
	public static Stat makeDensity2d(){
		Stat s = new Stat();
		s.name = "density2d";
		s.defaultGeom = "density2d";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		aes.required = true;
		s.aess.add(aes);
		
		aes = Aes.makeAes("y");
		aes.required = true;
		s.aess.add(aes);
		
		aes = Aes.makeAes("colour");
		aes.defaultValue = Color.decode("#3366FF");
		aes.value = aes.defaultValue;
		aes.preferCategorical=true;
		s.aess.add(aes);
		
		aes = Aes.makeAes("group");
		aes.defaultVariable = "interaction(..piece..,..level..)";
		aes.variable = aes.defaultVariable;
		s.aess.add(aes);

		Param p;
		
		p= ParamFactory.makeParam("na.rm");
		s.params.add(p);
		
		
		p= ParamFactory.makeParam("contour");
		s.params.add(p);
		
		s.generated.add("level");
		s.generated.add("piece");
		return s;
	}
	
	public static Stat makeEcdf(){
		Stat s = new Stat();
		s.name = "ecdf";
		s.defaultGeom = "area";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		aes.required = true;
		s.aess.add(aes);
		
		aes = Aes.makeAes("y");
		aes.required = true;
		aes.defaultVariable = "..y..";
		aes.variable = "..y..";
		s.aess.add(aes);

		Param p;
		
		//s.generated.add("y");
		
		return s;
	}	
	
	public static Stat makeFunction(){
		Stat s = new Stat();
		s.name = "function";
		s.defaultGeom = "path";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		aes.required = true;
		s.aess.add(aes);
		
		Param p;
		
		p= ParamFactory.makeParam("fun");
		s.params.add(p);
		
		p= ParamFactory.makeParam("args");
		s.params.add(p);
		
		s.generated.add("y");
		
		return s;
	}	
	
	public static Stat makeBinhex(){
		Stat s = new Stat();
		s.name = "binhex";
		s.defaultGeom = "hex";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		s.aess.add(aes);
		
		aes = Aes.makeAes("y");
		s.aess.add(aes);
		
		aes = Aes.makeAes("fill");
		aes.defaultVariable = "..count..";
		aes.variable = aes.defaultVariable;
		aes.preferCategorical=true;
		s.aess.add(aes);
		
		aes = Aes.makeAes("group");
		s.aess.add(aes);

		Param p;
		ParamVector pv;
		
		p= ParamFactory.makeParam("na.rm");
		s.params.add(p);
		
		
		pv = new ParamVector("binwidth");
		pv.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		s.params.add(pv);
		
		p= ParamFactory.makeParam("bins");
		s.params.add(p);
		
		s.generated.add("level");
		s.generated.add("piece");
		return s;
	}	
	
	public static Stat makeHline(){
		Stat s = new Stat();
		s.name = "hline";
		s.defaultGeom = "hline";
		
		Aes aes;
		
		aes = Aes.makeAes("yintercept");
		s.aess.add(aes);
		
		aes = Aes.makeAes("intercept");
		s.aess.add(aes);
		
		return s;
	}	
	
	public static Stat makeQq(){
		Stat s = new Stat();
		s.name = "qq";
		s.defaultGeom = "point";
		
		Aes aes;
		
		aes = Aes.makeAes("sample");
		aes.required=true;
		s.aess.add(aes);
		
		aes = Aes.makeAes("x",null,"..theoretical..");
		s.aess.add(aes);
		
		aes = Aes.makeAes("y",null,"..sample..");
		s.aess.add(aes);
		
		Param p;
		
		p= ParamFactory.makeParam("quantiles");
		p.setValue(new String[] {});
		p.setDefaultValue(new String[]{});
		s.params.add(p);
		
		p= ParamFactory.makeParam("distribution");
		s.params.add(p);
		
		p= ParamFactory.makeParam("na.rm");
		s.params.add(p);
		
		s.generated.add("theoretical");
		s.generated.add("sample");
		
		return s;
	}
	
	public static Stat makeQuantile(){
		Stat s = new Stat();
		s.name = "quantile";
		s.defaultGeom = "quantile";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		s.aess.add(aes);
		
		aes = Aes.makeAes("y");
		s.aess.add(aes);
		
		
		aes = Aes.makeAes("group",null,"..quantile..");
		s.aess.add(aes);
		
		Param p;
		
		p= ParamFactory.makeParam("quantiles");
		s.params.add(p);
		
		p= ParamFactory.makeParam("formula");
		s.params.add(p);
		
		p= ParamFactory.makeParam("na.rm");
		s.params.add(p);
		
		s.generated.add("quantile");
		
		return s;
	}	
	
	public static Stat makeSmooth(){
		Stat s = new Stat();
		s.name = "smooth";
		s.defaultGeom = "smooth";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		s.aess.add(aes);

		aes = Aes.makeAes("group");
		s.aess.add(aes);
		
		Param p;
		
		p= ParamFactory.makeParam("method");
		s.params.add(p);
		
		p= ParamFactory.makeParam("formula");
		s.params.add(p);
		
		p= ParamFactory.makeParam("se");
		s.params.add(p);
		
		p= ParamFactory.makeParam("fullrange");
		s.params.add(p);
		
		p= ParamFactory.makeParam("na.rm");
		s.params.add(p);
		
		s.generated.add("ymin");
		s.generated.add("ymax");
		s.generated.add("se");
		
		return s;
	}	
	
	public static Stat makeSpoke(){
		Stat s = new Stat();
		s.name = "spoke";
		s.defaultGeom = "segment";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		s.aess.add(aes);
		
		aes = Aes.makeAes("y");
		s.aess.add(aes);
		
		aes = Aes.makeAes("angle");
		aes.required = true;
		aes.value=null;
		aes.defaultValue=null;
		s.aess.add(aes);
		
		aes = Aes.makeAes("radius");
		s.aess.add(aes);
		
		aes = Aes.makeAes("xend",null,"..xend..");
		s.aess.add(aes);
		
		aes = Aes.makeAes("yend",null,"..yend..");
		s.aess.add(aes);
		
		aes = Aes.makeAes("group");
		s.aess.add(aes);
		
		s.generated.add("xend");
		s.generated.add("yend");
		
		return s;
	}	
	
	public static Stat makeSum(){
		Stat s = new Stat();
		s.name = "sum";
		s.defaultGeom = "point";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		s.aess.add(aes);
		
		aes = Aes.makeAes("y");
		s.aess.add(aes);
		
		aes = Aes.makeAes("size",null,"..prop..");
		s.aess.add(aes);
		
		aes = Aes.makeAes("group");
		s.aess.add(aes);
		
		s.generated.add("n");
		s.generated.add("prop");
		
		return s;
	}
	
	public static Stat makeSummary(){
		Stat s = new Stat();
		s.name = "summary";
		s.defaultGeom = "pointrange";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		s.aess.add(aes);
		
		aes = Aes.makeAes("y");
		s.aess.add(aes);
		
		aes = Aes.makeAes("group");
		s.aess.add(aes);

		Param p = new ParamStatSummary("Summary");
		s.params.add(p);
		
		s.generated.add("ymin");
		s.generated.add("ymax");		
		
		return s;
	}
	
	public static Stat makeUnique(){
		Stat s = new Stat();
		s.name = "unique";
		s.defaultGeom = "point";
		
		return s;
	}
	
	public static Stat makeYDensity(){
		Stat s = new Stat();
		s.name = "ydensity";
		s.defaultGeom = "violin";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		aes.required = true;
		s.aess.add(aes);
		
		aes = Aes.makeAes("y");
		aes.required = true;
		//aes.defaultVariable = "..density..";
		//aes.variable = "..density..";
		s.aess.add(aes);
		
		
		aes = Aes.makeAes("group");
		s.aess.add(aes);

		Param p;
		
		ParamCharacter pc = new ParamCharacter("scale");
		pc.setOptions(new String[]{"area","count","width"});
		pc.setViewType(ParamCharacter.VIEW_COMBO);
		pc.setDefaultValue("equal");
		pc.setValue("equal");
		s.params.add(pc);
		
		p= ParamFactory.makeParam("adjust");
		s.params.add(p);
		
		
		p= ParamFactory.makeParam("kernel");
		s.params.add(p);
		
		p= ParamFactory.makeParam("trim");
		s.params.add(p);
		
		p= ParamFactory.makeParam("na.rm");
		s.params.add(p);
		
		s.generated.add("density");
		s.generated.add("count");
		s.generated.add("scaled");
		
		return s;
	}	
	
	public static Stat makeVline(){
		Stat s = new Stat();
		s.name = "vline";
		s.defaultGeom = "vline";
		
		Aes aes;
		
		aes = Aes.makeAes("xintercept");
		s.aess.add(aes);

		aes = Aes.makeAes("intercept");
		s.aess.add(aes);
		
		return s;
	}
	
	public static Stat makeStat(String statName){
		if(statName=="identity")
			return Stat.makeIdentity();
		else if(statName=="abline")
			return Stat.makeAbline();
		else if(statName=="bin")
			return Stat.makeBin();
		else if(statName=="bin2d")
			return Stat.makeBin2d();
		else if(statName=="bindot")
			return Stat.makeBinDot();
		else if(statName=="binhex")
			return Stat.makeBinhex();		
		else if(statName=="boxplot")
			return Stat.makeBoxplot();
		else if(statName=="contour")
			return Stat.makeContour();
		else if(statName=="density")
			return Stat.makeDensity();
		else if(statName=="density2d")
			return Stat.makeDensity2d();
		else if(statName=="ecdf")
			return Stat.makeEcdf();
		else if(statName=="function")
			return Stat.makeFunction();
		else if(statName=="hline")
			return Stat.makeHline();
		else if(statName=="qq")
			return Stat.makeQq();
		else if(statName=="quantile")
			return Stat.makeQuantile();
		else if(statName=="smooth")
			return Stat.makeSmooth();
		else if(statName=="spoke")
			return Stat.makeSpoke();
		else if(statName=="sum")
			return Stat.makeSum();
		else if(statName=="summary")
			return Stat.makeSummary();
		else if(statName=="unique")
			return Stat.makeUnique();
		else if(statName=="ydensity")
			return Stat.makeYDensity();
		else if(statName=="vline")
			return Stat.makeVline();
		return null;
	}

	public Element toXML() {
		try{
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element node = doc.createElement("Stat");
			node.setAttribute("className", "org.rosuda.deducer.plots.Stat");
			if(name!=null)
				node.setAttribute("name", name);
			if(defaultGeom!=null)
				node.setAttribute("defaultGeom", defaultGeom);
			Element paramNode = doc.createElement("params");
			for(int i=0;i<params.size();i++){
				Element pEl = ((Param)params.get(i)).toXML();
				pEl = (Element) doc.importNode(pEl, true);
				paramNode.appendChild(pEl);
			}
			node.appendChild(paramNode);
			
			Element aesNode = doc.createElement("aess");
			for(int i=0;i<aess.size();i++){
				Element pEl = ((Aes)aess.get(i)).toXML();
				pEl = (Element) doc.importNode(pEl, true);
				aesNode.appendChild(pEl);
			}
			node.appendChild(aesNode);
			
			Element generatedNode = doc.createElement("generated");
			if(generated!=null)
				for(int i=0;i<generated.size();i++)
					generatedNode.setAttribute("element_"+i, generated.get(i).toString());
			node.appendChild(generatedNode);
			
			doc.appendChild(node);
			
			return node;
		}catch(Exception ex){ex.printStackTrace();return null;}		
	}

	public void setFromXML(Element node) {
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.plots.Stat")){
			System.out.println("Error Stat: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		if(node.hasAttribute("name"))
			name = node.getAttribute("name");
		else 
			name = null;
		if(node.hasAttribute("defaultGeom"))
			defaultGeom = node.getAttribute("defaultGeom");
		else 
			defaultGeom = null;
		
		Element child = (Element) node.getElementsByTagName("params").item(0);
		params = new Vector();
		Vector nl = XMLHelper.getChildrenElementsByTag(child,"Param");
		for(int i=0;i<nl.size();i++){
			Element n = (Element) nl.get(i);
			cn = n.getAttribute("className");
			Param p = Param.makeParam(cn);
			p.setFromXML(n);
			params.add(p);
		}
		
		child = (Element) node.getElementsByTagName("aess").item(0);
		aess = new Vector();
		nl = XMLHelper.getChildrenElementsByTag(child,"Aes");
		for(int i=0;i<nl.size();i++){
			Element n = (Element) nl.get(i);
			Aes aes = new Aes();
			aes.setFromXML(n);
			aess.add(aes);
		}
		
		generated = new Vector();
		Node generatedNode = node.getElementsByTagName("generated").item(0);
		NamedNodeMap attr = generatedNode.getAttributes();
		if(attr.getLength()>0){
			for(int i=0;i<attr.getLength();i++)
				generated.add(attr.item(i).getNodeValue());
		}
	}	
	
}
