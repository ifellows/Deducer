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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Geom {

	public String name;
	public Vector aess = new Vector();
	public Vector params = new Vector();
	
	public String defaultStat;
	
	public String defaultPosition;
	
	public Object clone(){
		Geom g = new Geom();
		g.name = this.name;
		g.defaultStat = this.defaultStat;
		g.defaultPosition = this.defaultPosition;
		for(int i=0;i<aess.size();i++){
			Aes aes = (Aes) aess.get(i);
			g.aess.add(aes.clone());
		}
		for(int i=0;i<params.size();i++){
			Param p = (Param) params.get(i);
			g.params.add(p.clone());
		}
		return g;
	}
	
	public static Geom makeAbline(){
		Geom g = new Geom();
		
		g.name = "abline";
		g.defaultPosition = "identity";
		g.defaultStat = "identity";
		Aes aes;
		
		aes = Aes.makeAes("intercept");
		aes.required = false;
		g.aess.add(aes);	
		
		aes = Aes.makeAes("slope");
		aes.required = false;
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour");
		aes.required = false;
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		aes.required = false;
		aes.defaultValue = new Double(0.5);
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		aes.required = false;
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		aes.required = false;
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		aes.required = false;
		g.aess.add(aes);
		/*
		Param p = ParamFactory.makeParam("slope");
		g.params.add(p);
		
		p = ParamFactory.makeParam("intercept");
		g.params.add(p);
		*/
		return g;
	}
	
	public static Geom makeArea(){
		Geom g = new Geom();
		
		g.name = "area";
		g.defaultPosition = "identity";
		g.defaultStat = "identity";
		Aes aes;
		
		aes = Aes.makeAes("x");
		aes.required = true;
		g.aess.add(aes);	
		
		aes = Aes.makeAes("y");
		aes.required = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour");
		aes.required = false;
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("fill");
		aes.defaultValue = new Color(51,51,51);
		aes.value = new Color(51,51,51);
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		aes.required = false;
		aes.defaultValue = new Double(.5);
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		aes.required = false;
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		aes.required = false;
		g.aess.add(aes);
		
		Param p = ParamFactory.makeParam("na.rm");
		g.params.add(p);
		
		return g;
	}
	
	public static Geom makeBar(){
		Geom g = new Geom();
		
		g.name = "bar";
		g.defaultStat = "count";
		g.defaultPosition = "stack";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		aes.required = true;
		g.aess.add(aes);	

		aes = Aes.makeAes("y");
		aes.required = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour");
		aes.required = false;
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("fill");
		aes.value = new Color(51,51,51);
		aes.defaultValue = new Color(51,51,51);
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		aes.required = false;
		aes.defaultValue = new Double(.5);
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		aes.required = false;
		g.aess.add(aes);
		
		aes = Aes.makeAes("weight");
		aes.required = false;
		aes.preferNumeric=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		aes.required = false;
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		return g;
	}
	
	public static Geom makeBin2d(){
		Geom g = new Geom();
		
		g.name = "bin2d";
		g.defaultStat = "bin_2d";
		g.defaultPosition = "identity";
		
		Aes aes;
		
		aes = Aes.makeAes("xmin");
		aes.required = true;
		g.aess.add(aes);	
		
		aes = Aes.makeAes("xmax");
		aes.required = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("ymin");
		aes.required = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("ymax");
		aes.required = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour");
		aes.required = false;
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("fill");
		aes.value = new Color(153,153,153);
		aes.defaultValue = new Color(153,153,153);
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		aes.required = false;
		aes.defaultValue = new Double(.5);
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		aes.required = false;
		g.aess.add(aes);
		
		aes = Aes.makeAes("weight");
		aes.required = false;
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		aes.required = false;
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		return g;
	}
	
	public static Geom makeBlank(){
		Geom g = new Geom();
		
		g.name = "blank";
		g.defaultStat = "identity";
		g.defaultPosition = "identity";
		
		return g;
	}
	
	public static Geom makeBoxplot(){
		Geom g = new Geom();
		
		g.name = "boxplot";
		g.defaultStat = "boxplot";
		g.defaultPosition = "dodge";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		aes.required = true;
		aes.preferCategorical=true;
		g.aess.add(aes);	
		
		aes = Aes.makeAes("lower");
		aes.required = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("upper");
		aes.required = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("middle");
		aes.required = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("ymin");
		aes.required = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("ymax");
		aes.required = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour");
		aes.required = false;
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("fill");
		aes.value = Color.white;
		aes.defaultValue = Color.white;
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		aes.required = false;
		aes.defaultValue = new Double(.5);
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("weight");
		aes.required = false;
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		aes.required = false;
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		Param p = ParamFactory.makeParam("outlier.colour");
		g.params.add(p);
		
		p = ParamFactory.makeParam("outlier.shape");
		g.params.add(p);
		
		p = ParamFactory.makeParam("outlier.size");
		g.params.add(p);
		
		return g;
	}
	
	public static Geom makeContour(){
		Geom g = new Geom();
		
		g.name = "contour";
		g.defaultStat = "contour";
		g.defaultPosition = "identity";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		aes.required = true;
		g.aess.add(aes);	
		
		aes = Aes.makeAes("y");
		aes.required = true;
		g.aess.add(aes);	
		
		aes = Aes.makeAes("colour");
		aes.required = false;
		aes.value = Color.decode("#3366FF");
		aes.defaultValue = Color.decode("#3366FF");
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		aes.required = false;
		aes.defaultValue = new Double(.5);
		g.aess.add(aes);
		
		aes = Aes.makeAes("weight");
		aes.required = false;
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		aes.required = false;
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		aes.required = false;
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		Param p = ParamFactory.makeParam("arrow");
		g.params.add(p);
		
		return g;
	}
	
	public static Geom makeCrossbar(){
		Geom g = new Geom();
		
		g.name = "crossbar";
		g.defaultStat = "identity";
		g.defaultPosition = "identity";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		aes.required = true;
		g.aess.add(aes);	
		
		aes = Aes.makeAes("y");
		aes.required = true;
		g.aess.add(aes);	
		aes = Aes.makeAes("ymin");
		aes.required = true;
		g.aess.add(aes);	
		
		aes = Aes.makeAes("ymax");
		aes.required = true;
		g.aess.add(aes);	
		
		aes = Aes.makeAes("colour");
		aes.value = Color.black;
		aes.defaultValue = Color.black;
		g.aess.add(aes);
		
		aes = Aes.makeAes("fill");
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		Param p = ParamFactory.makeParam("fatten");
		g.params.add(p);
		
		p = ParamFactory.makeParam("width");
		p.setTitle("Middle bar width");
		g.params.add(p);
		
		return g;
	}
	
	public static Geom makeDensity(){
		Geom point = new Geom();
		
		point.name = "density";
		
		point.defaultStat = "density";
		
		point.defaultPosition = "identity";
		
		Aes aes = Aes.makeAes("x");
		aes.required = true;
		point.aess.add(aes);
		
		aes = Aes.makeAes("y");
		aes.required = true;
		point.aess.add(aes);		
		
		aes = Aes.makeAes("colour");
		aes.value = Color.black;
		aes.defaultValue = Color.black;
		aes.preferCategorical=true;
		point.aess.add(aes);
		
		aes = Aes.makeAes("fill");
		aes.preferCategorical=true;
		point.aess.add(aes);
		
		aes = Aes.makeAes("weight");
		point.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		aes.preferCategorical=true;
		point.aess.add(aes);
		
		aes = Aes.makeAes("size");
		aes.preferCategorical=true;
		point.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		point.aess.add(aes);
		
		aes = Aes.makeAes("group");
		aes.required = false;
		point.aess.add(aes);
		
		Param p = ParamFactory.makeParam("na.rm");
		point.params.add(p);
		
		return point;
	}	
	
	public static Geom makeDensity2d(){
		Geom point = new Geom();
		
		point.name = "density_2d";
		
		point.defaultStat = "density_2d";
		
		point.defaultPosition = "identity";
		
		Aes aes = Aes.makeAes("x");
		aes.required = true;
		aes.preferNumeric=true;
		point.aess.add(aes);
		
		aes = Aes.makeAes("y");
		aes.required = true;
		aes.preferNumeric=true;
		point.aess.add(aes);		
		
		aes = Aes.makeAes("colour");
		aes.value = Color.decode("#3366FF");
		aes.defaultValue = Color.decode("#3366FF");
		aes.preferCategorical=true;
		point.aess.add(aes);
		
		aes = Aes.makeAes("weight");
		point.aess.add(aes);
		
		aes = Aes.makeAes("size");
		aes.preferCategorical=true;
		point.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		point.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		aes.preferCategorical=true;
		point.aess.add(aes);
		
		aes = Aes.makeAes("group");
		aes.required = false;
		point.aess.add(aes);
		
		Param p = ParamFactory.makeParam("na.rm");
		point.params.add(p);
		
		return point;
	}	
	
	public static Geom makeDotPlot(){
		Geom g = new Geom();
		
		g.name = "dotplot";
		
		g.defaultStat = "bindot";
		
		g.defaultPosition = "identity";
			
		
		Aes aes = Aes.makeAes("x");
		g.aess.add(aes);
		
		aes = Aes.makeAes("y","","..count..");
		aes.preferCategorical = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour",Color.black,null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("fill");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		aes.preferNumeric=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		ParamCharacter pc = new ParamCharacter("stackdir");
		pc.setOptions(new String[]{"up","down","center","centerwhole"});
		pc.setViewType(ParamCharacter.VIEW_COMBO);
		pc.setDefaultValue("up");
		pc.setValue("up");
		g.params.add(pc);
		
		ParamNumeric pn = new ParamNumeric("stackratio");
		pn.setDefaultValue(1);
		pn.setValue(1);
		g.params.add(pn);
		
		pn = new ParamNumeric("dotsize");
		pn.setDefaultValue(1);
		pn.setValue(1);
		g.params.add(pn);
		
		return g;
	}
	
	public static Geom makeErrorbar(){
		Geom g = new Geom();
		
		g.name = "errorbar";
		g.defaultStat = "identity";
		g.defaultPosition = "identity";
		
		Aes aes;
		
		aes = Aes.makeAes("x");
		aes.required = true;
		g.aess.add(aes);	
		
		aes = Aes.makeAes("ymin");
		aes.required = true;
		g.aess.add(aes);	
		
		aes = Aes.makeAes("ymax");
		aes.required = true;
		g.aess.add(aes);	
		
		aes = Aes.makeAes("colour");
		aes.value = Color.black;
		aes.defaultValue = Color.black;
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("width");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		
		return g;
	}
	
	public static Geom makeErrorbarh(){
		Geom g = new Geom();
		
		g.name = "errorbarh";
		g.defaultStat = "identity";
		g.defaultPosition = "identity";
		
		Aes aes;
		
		aes = Aes.makeAes("y");
		aes.required = true;
		g.aess.add(aes);	

		aes = Aes.makeAes("x");
		aes.required = true;
		g.aess.add(aes);	
		
		aes = Aes.makeAes("xmin");
		aes.required = true;
		g.aess.add(aes);	
		
		aes = Aes.makeAes("xmax");
		aes.required = true;
		g.aess.add(aes);	
		
		aes = Aes.makeAes("colour");
		aes.value = Color.black;
		aes.defaultValue = Color.black;
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("width");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		
		return g;
	}
	
	public static Geom makeFreqpoly(){
		Geom point = new Geom();
		
		point.name = "freqpoly";
		
		point.defaultStat = "bin";
		
		point.defaultPosition = "identity";
			
		
		Aes aes = Aes.makeAes("colour");
		aes.value = Color.black;
		aes.defaultValue = Color.black;
		aes.preferCategorical=true;
		point.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		point.aess.add(aes);
		
		aes = Aes.makeAes("size");
		point.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		point.aess.add(aes);
		
		aes = Aes.makeAes("group");
		aes.required = false;
		point.aess.add(aes);
		
		return point;
	}	
	
	public static Geom makeHex(){
		Geom g = new Geom();
		
		g.name = "hex";
		
		g.defaultStat = "bin_hex";
		
		g.defaultPosition = "identity";
			
		
		Aes aes = Aes.makeAes("x");
		aes.preferNumeric=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("y");
		aes.preferNumeric=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour");
		aes.defaultValue = new Color(127,127,127);
		aes.value = aes.defaultValue;
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("fill");
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		aes.required = false;
		g.aess.add(aes);
		
		return g;
	}	
	
	public static Geom makeHistogram(){
		Geom g = new Geom();
		
		g.name = "histogram";
		
		g.defaultStat = "bin";
		
		g.defaultPosition = "stack";
			
		
		Aes aes = Aes.makeAes("x");
		g.aess.add(aes);
		
		
		aes = Aes.makeAes("colour");
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("fill");
		aes.defaultValue = new Color(51,51,51);
		aes.value = aes.defaultValue;
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("weight");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		aes.required = false;
		g.aess.add(aes);
		
		return g;
	}	
	
	public static Geom makeHline(){
		Geom g = new Geom();
		
		g.name = "hline";
		
		g.defaultStat = "identity";
		
		g.defaultPosition = "identity";
		
		Aes aes = Aes.makeAes("yintercept");
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour");
		aes.value = Color.black;
		aes.defaultValue = aes.value;
		g.aess.add(aes);

		
		aes = Aes.makeAes("size");
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		/*
		Param p = ParamFactory.makeParam("yintercept");
		g.params.add(p);
		*/
		return g;
	}	
	
	public static Geom makeJitter(){
		Geom g = new Geom();
		
		g.name = "jitter";
		
		g.defaultStat = "identity";
		
		g.defaultPosition = "jitter";
			
		
		Aes aes = Aes.makeAes("x");
		g.aess.add(aes);
		
		aes = Aes.makeAes("y");
		g.aess.add(aes);
		
		aes = Aes.makeAes("shape");
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour");
		aes.value = Color.black;
		aes.defaultValue = aes.value;
		g.aess.add(aes);
		
		aes = Aes.makeAes("fill");
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		aes.value = new Double(5);
		aes.defaultValue = new Double(5);
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		aes.preferNumeric=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		Param p = ParamFactory.makeParam("na.rm");
		g.params.add(p);
		
		return g;
	}
	
	public static Geom makeLine(){
		Geom g = new Geom();
		
		g.name = "line";
		
		g.defaultStat = "identity";
		
		g.defaultPosition = "identity";
			
		
		Aes aes = Aes.makeAes("x");
		g.aess.add(aes);
		
		aes = Aes.makeAes("y");
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour");
		aes.value = Color.black;
		aes.defaultValue = aes.value;
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		aes.preferNumeric=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		Param p = ParamFactory.makeParam("arrow");
		g.params.add(p);
		
		return g;
	}
	
	public static Geom makeLinerange(){
		Geom g = new Geom();
		
		g.name = "linerange";
		
		g.defaultStat = "identity";
		
		g.defaultPosition = "identity";
			
		
		Aes aes = Aes.makeAes("x");
		g.aess.add(aes);
		
		aes = Aes.makeAes("ymin");
		aes.required = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("ymax");
		aes.required = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour",Color.black,null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		aes.preferNumeric=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		return g;
	}
	
	public static Geom makePath(){
		Geom g = new Geom();
		
		g.name = "path";
		
		g.defaultStat = "identity";
		
		g.defaultPosition = "identity";
			
		
		Aes aes = Aes.makeAes("x");
		g.aess.add(aes);
		
		aes = Aes.makeAes("y");
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour",Color.black,null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		aes.preferNumeric=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		Param p = ParamFactory.makeParam("arrow");
		g.params.add(p);
		
		return g;
	}
	
	public static Geom makePoint(){
		Geom point = new Geom();
		
		point.name = "point";
		
		point.defaultStat = "identity";
		
		point.defaultPosition = "identity";
		
		Aes aes = Aes.makeAes("x");
		point.aess.add(aes);
		
		aes = Aes.makeAes("y");
		point.aess.add(aes);
		
		aes = Aes.makeAes("shape");
		aes.value = new Integer(19);
		aes.defaultValue = new Integer(19);
		point.aess.add(aes);
		
		aes = Aes.makeAes("colour",Color.black,null);
		point.aess.add(aes);
		
		aes = Aes.makeAes("size",new Double(2.0),null);
		point.aess.add(aes);
		
		aes = Aes.makeAes("fill");
		point.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		aes.preferNumeric=true;
		point.aess.add(aes);
		
		aes = Aes.makeAes("group");
		point.aess.add(aes);
		
		Param p = ParamFactory.makeParam("na.rm");
		point.params.add(p);
		
		return point;
	}
	
	public static Geom makePointrange(){
		Geom g = new Geom();
		
		g.name = "pointrange";
		g.defaultStat = "identity";
		g.defaultPosition = "identity";
			
		
		Aes aes = Aes.makeAes("x");
		g.aess.add(aes);
		
		aes = Aes.makeAes("y");
		g.aess.add(aes);
		
		aes = Aes.makeAes("ymin");
		aes.required = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("ymax");
		aes.required = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour",Color.black,null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("fill");
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("shape");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		aes.preferNumeric=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		return g;
	}
	
	public static Geom makePolygon(){
		Geom g = new Geom();
		
		g.name = "polygon";
		g.defaultStat = "identity";
		g.defaultPosition = "identity";
			
		
		Aes aes = Aes.makeAes("x");
		g.aess.add(aes);
		
		aes = Aes.makeAes("y");
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour");
		g.aess.add(aes);
		
		aes = Aes.makeAes("fill",new Color(51,51,51),null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		return g;
	}
	
	public static Geom makeQuantile(){
		Geom g = new Geom();
		
		g.name = "quantile";
		g.defaultStat = "quantile";
		g.defaultPosition = "identity";
			
		
		Aes aes = Aes.makeAes("x");
		g.aess.add(aes);
		
		aes = Aes.makeAes("y");
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour",Color.decode("#3366FF"),null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		Param p = ParamFactory.makeParam("arrow");
		g.params.add(p);
		
		return g;
	}
	
	public static Geom makeRaster(){
		Geom g = new Geom();
		
		g.name = "raster";
		g.defaultStat = "identity";
		g.defaultPosition = "identity";
			
		
		Aes aes = Aes.makeAes("x");
		g.aess.add(aes);
		
		aes = Aes.makeAes("y");
		g.aess.add(aes);
		
		aes = Aes.makeAes("fill",new Color(51,51,51),null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("size",new Double(0.1),null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		ParamNumeric p = new ParamNumeric("hjust");
		p.setLowerBound(new Double(0));
		p.setUpperBound(new Double(1));
		p.setDefaultValue(.5);
		p.setValue(.5);
		g.params.add(p);
		
		p = new ParamNumeric("vjust");
		p.setLowerBound(new Double(0));
		p.setUpperBound(new Double(1));
		p.setDefaultValue(.5);
		p.setValue(.5);
		g.params.add(p);
		
		return g;
	}
	
	public static Geom makeRect(){
		Geom g = new Geom();
		
		g.name = "rect";
		g.defaultStat = "identity";
		g.defaultPosition = "identity";
			
		
		Aes aes = Aes.makeAes("xmin");
		aes.required=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("xmax");
		aes.required = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("ymin");
		aes.required=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("ymax");
		aes.required=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour");
		g.aess.add(aes);
		
		aes = Aes.makeAes("fill",new Color(51,51,51),null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		return g;
	}
	
	public static Geom makeRibbon(){
		Geom g = new Geom();
		
		g.name = "ribbon";
		g.defaultStat = "identity";
		g.defaultPosition = "identity";
			
		
		Aes aes = Aes.makeAes("x");
		g.aess.add(aes);
		
		aes = Aes.makeAes("ymin");
		aes.required=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("ymax");
		aes.required=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour");
		g.aess.add(aes);
		
		aes = Aes.makeAes("fill",new Color(51,51,51),null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		return g;
	}
	
	public static Geom makeRug(){
		Geom g = new Geom();
		
		g.name = "rug";
		g.defaultStat = "identity";
		g.defaultPosition = "identity";
		
		Aes aes = Aes.makeAes("x");
		aes.required=false;
		g.aess.add(aes);
		
		aes = Aes.makeAes("y");
		aes.required=false;
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour");
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		return g;
	}
	
	public static Geom makeSegment(){
		Geom g = new Geom();
		
		g.name = "segment";
		g.defaultStat = "identity";
		g.defaultPosition = "identity";
			
		
		Aes aes = Aes.makeAes("x");
		g.aess.add(aes);
		
		aes = Aes.makeAes("y");
		g.aess.add(aes);
		
		aes = Aes.makeAes("xend");
		aes.required=true;
		aes.defaultUseVariable = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("yend");
		aes.required=true;
		aes.defaultUseVariable = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour",Color.black,null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		Param p = ParamFactory.makeParam("arrow");
		g.params.add(p);
		
		return g;
	}
	
	public static Geom makeSmooth(){
		Geom g = new Geom();
		
		g.name = "smooth";
		g.defaultStat = "smooth";
		g.defaultPosition = "identity";
			
		
		Aes aes = Aes.makeAes("x");
		g.aess.add(aes);
		
		aes = Aes.makeAes("y");
		g.aess.add(aes);
		
		
		aes = Aes.makeAes("colour",Color.decode("#3366FF"),null);
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("fill",new Color(153,153,153),null);
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("weight");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha",new Double(0.4),null);
		aes.preferCategorical=true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		return g;
	}
	
	public static Geom makeStep(){
		Geom g = new Geom();
		
		g.name = "step";
		g.defaultStat = "identity";
		g.defaultPosition = "identity";
			
		
		Aes aes = Aes.makeAes("x");
		g.aess.add(aes);
		
		aes = Aes.makeAes("y");
		g.aess.add(aes);
		
		
		aes = Aes.makeAes("colour",Color.black,null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha",new Double(0.4),null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		Param p = ParamFactory.makeParam("direction");
		g.params.add(p);
		
		return g;
	}
	
	public static Geom makeText(){
		Geom g = new Geom();
		
		g.name = "text";
		g.defaultStat = "identity";
		g.defaultPosition = "identity";
			
		
		Aes aes = Aes.makeAes("x");
		g.aess.add(aes);
		
		aes = Aes.makeAes("y");
		g.aess.add(aes);
		
		aes = Aes.makeAes("label");
		aes.required = true;
		aes.defaultUseVariable = true;
		aes.useVariable = true;
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour",Color.black,null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("size",new Double(5.0),null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("angle");
		g.aess.add(aes);
		
		aes = Aes.makeAes("hjust");
		g.aess.add(aes);
		
		aes = Aes.makeAes("vjust");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha",new Double(0.4),null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		Param p = new ParamLogical("parse");
		g.params.add(p);
		
		return g;
	}
	
	public static Geom makeTile(){
		Geom g = new Geom();
		
		g.name = "tile";
		g.defaultStat = "identity";
		g.defaultPosition = "identity";
			
		
		Aes aes = Aes.makeAes("x");
		g.aess.add(aes);
		
		aes = Aes.makeAes("y");
		g.aess.add(aes);
		
		aes = Aes.makeAes("fill",new Color(51,51,51),null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("size",new Double(0.1),null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		return g;
	}
	
	public static Geom makeViolin(){
		Geom g = new Geom();
		
		g.name = "violin";
		g.defaultStat = "ydensity";
		g.defaultPosition = "dodge";
			
		
		Aes aes = Aes.makeAes("x");
		g.aess.add(aes);
		
		aes = Aes.makeAes("y");
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour",new Color(51,51,51),null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("fill",Color.white,null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("size",new Double(.5),null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		
		return g;
	}
	
	public static Geom makeVline(){
		Geom g = new Geom();
		
		g.name = "vline";
		g.defaultStat = "identity";
		g.defaultPosition = "identity";
		
		Aes aes = Aes.makeAes("xintercept");
		g.aess.add(aes);
		
		aes = Aes.makeAes("colour",Color.black,null);
		g.aess.add(aes);
		
		aes = Aes.makeAes("size");
		g.aess.add(aes);
		
		aes = Aes.makeAes("linetype");
		g.aess.add(aes);
		
		aes = Aes.makeAes("alpha");
		g.aess.add(aes);
		
		aes = Aes.makeAes("group");
		g.aess.add(aes);
		/*
		Param p = ParamFactory.makeParam("xintercept");
		g.params.add(p);
		*/
		return g;
	}
	
	public static Geom makeGeom(String geomName){
		if(geomName=="point")
			return Geom.makePoint();
		else if(geomName=="abline")
			return Geom.makeAbline();
		else if(geomName=="area")
			return Geom.makeArea();
		else if(geomName=="bar")
			return Geom.makeBar();
		else if(geomName=="bin2d")
			return Geom.makeBin2d();
		else if(geomName=="blank")
			return Geom.makeBlank();
		else if(geomName=="boxplot")
			return Geom.makeBoxplot();
		else if(geomName=="contour")
			return Geom.makeContour();
		else if(geomName=="crossbar")
			return Geom.makeCrossbar();
		else if(geomName=="density")
			return Geom.makeDensity();
		else if(geomName=="density_2d")
			return Geom.makeDensity2d();
		else if(geomName=="dotplot")
			return Geom.makeDotPlot();
		else if(geomName=="errorbar")
			return Geom.makeErrorbar();
		else if(geomName=="errorbarh")
			return Geom.makeErrorbarh();
		else if(geomName=="freqpoly")
			return Geom.makeFreqpoly();
		else if(geomName=="hex")
			return Geom.makeHex();
		else if(geomName=="histogram")
			return Geom.makeHistogram();
		else if(geomName=="hline")
			return Geom.makeHline();
		else if(geomName=="jitter")
			return Geom.makeJitter();
		else if(geomName=="line")
			return Geom.makeLine();
		else if(geomName=="linerange")
			return Geom.makeLinerange();
		else if(geomName=="path")
			return Geom.makePath();
		else if(geomName=="pointrange")
			return Geom.makePointrange();
		else if(geomName=="polygon")
			return Geom.makePolygon();
		else if(geomName=="quantile")
			return Geom.makeQuantile();
		else if(geomName=="raster")
			return Geom.makeRaster();
		else if(geomName=="rect")
			return Geom.makeRect();
		else if(geomName=="ribbon")
			return Geom.makeRibbon();
		else if(geomName=="rug")
			return Geom.makeRug();
		else if(geomName=="segment")
			return Geom.makeSegment();
		else if(geomName=="smooth")
			return Geom.makeSmooth();
		else if(geomName=="step")
			return Geom.makeStep();
		else if(geomName=="text")
			return Geom.makeText();
		else if(geomName=="tile")
			return Geom.makeTile();
		else if(geomName=="violin")
			return Geom.makeViolin();
		else if(geomName=="vline")
			return Geom.makeVline();
		
		return null;
	}

	
	public Element toXML() {
		try{
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element node = doc.createElement("Geom");
			node.setAttribute("className", "org.rosuda.deducer.plots.Geom");
			if(name!=null)
				node.setAttribute("name", name);
			if(defaultStat!=null)
				node.setAttribute("defaultStat", defaultStat);
			if(defaultPosition!=null)
				node.setAttribute("defaultPosition", defaultPosition);
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
			
			
			doc.appendChild(node);
			
			return node;
		}catch(Exception ex){ex.printStackTrace();return null;}		
	}

	public void setFromXML(Element node) {
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.plots.Geom")){
			System.out.println("Error Geom: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		if(node.hasAttribute("name"))
			name = node.getAttribute("name");
		else 
			name = null;
		if(node.hasAttribute("defaultStat"))
			defaultStat = node.getAttribute("defaultStat");
		else 
			defaultStat = null;
		if(node.hasAttribute("defaultPosition"))
			defaultPosition = node.getAttribute("defaultPosition");
		else 
			defaultPosition = null;
		Element child = (Element) node.getElementsByTagName("params").item(0);
		params = new Vector();
		Vector nl = XMLHelper.getChildrenElementsByTag(child, "Param");
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
	}	
	
}
