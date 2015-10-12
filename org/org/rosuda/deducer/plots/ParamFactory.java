package org.rosuda.deducer.plots;

import java.awt.Color;
import java.util.Vector;

import org.rosuda.deducer.widgets.param.Param;
import org.rosuda.deducer.widgets.param.ParamAny;
import org.rosuda.deducer.widgets.param.ParamCharacter;
import org.rosuda.deducer.widgets.param.ParamColor;
import org.rosuda.deducer.widgets.param.ParamLogical;
import org.rosuda.deducer.widgets.param.ParamNone;
import org.rosuda.deducer.widgets.param.ParamNumeric;
import org.rosuda.deducer.widgets.param.ParamVector;
import org.rosuda.deducer.widgets.param.RFunction;
import org.rosuda.deducer.widgets.param.RFunctionList;

public class ParamFactory {

	public static Param makeParam(String name){
		Param p = new ParamAny(name);
		if(name=="na.rm"){
			ParamLogical pl = new ParamLogical("na.rm");
			pl.setValue(new Boolean(false));
			pl.setDefaultValue(new Boolean(false));
			pl.setTitle("remove missing");
			p = pl;
		}else if(name=="drop"){
			ParamLogical pl = new ParamLogical("drop");
			pl.setValue(new Boolean(true));	
			pl.setDefaultValue(new Boolean(true));		
			p=pl;
		}else if(name=="width"){
			ParamNumeric pn = new ParamNumeric("width");
			pn.setViewType(Param.VIEW_ENTER);
			pn.setLowerBound(new Double(0.0));
			p=pn;
		}else if(name=="outlier.colour"){
			ParamColor pcol = new ParamColor("outlier.colour");
			pcol.setViewType(Param.VIEW_COLOR);
			pcol.setDefaultValue(Color.black);
			pcol.setValue(Color.black);
			p=pcol;
		}else if(name=="outlier.shape"){
			
		}else if(name=="outlier.size"){
			
		}else if(name=="scalebreaks"){
			RFunctionList fList = new RFunctionList("Breaks");
			fList.setName("breaks");

			//////////
			RFunction rf = new RFunction("scales::pretty_breaks");
			rf.setTitle("Standard");
			
			ParamNumeric rfpn = new ParamNumeric();
			rfpn.setName("n");
			rfpn.setTitle("Approx. N");
			rfpn.setViewType(Param.VIEW_ENTER);
			rfpn.setDefaultValue(5.0);
			rfpn.setValue(5.0);
			rfpn.setLowerBound(new Double(1));
			rf.add(rfpn);
			
			rfpn = new ParamNumeric();
			rfpn.setName("min.n");
			rfpn.setTitle("Min N");
			rfpn.setViewType(Param.VIEW_ENTER);
			rfpn.setLowerBound(new Double(1));
			rfpn.setRequired(false);
			rf.add(rfpn);
			
			fList.addRFunction("Standard", rf);
			
			//////////////
			rf = new RFunction("scales::date_breaks");
			rf.setTitle("Date");
			
			ParamCharacter rfpc = new ParamCharacter();
			rfpc.setName("width");
			rfpc.setTitle("Width");
			rfpc.setDefaultValue("1 month");
			rfpc.setValue("1 month");
			rfpc.setViewType(Param.VIEW_EDITABLE_COMBO);
			rfpc.setOptions(new String[] {"1 hour", "1 day", "1 month", "1 year"});
			rf.add(rfpc);
			
			fList.addRFunction("Date", rf);
			
			//////////////
			rf = new RFunction("scales::log_breaks");
			rf.setTitle("Log");
			
			rfpn = new ParamNumeric();
			rfpn.setName("n");
			rfpn.setTitle("Approx. N");
			rfpn.setViewType(Param.VIEW_ENTER);
			rfpn.setDefaultValue(5.0);
			rfpn.setValue(5.0);
			rfpn.setLowerBound(new Double(1));
			rf.add(rfpn);
			
			rfpn = new ParamNumeric();
			rfpn.setName("base");
			rfpn.setTitle("Log base");
			rfpn.setViewType(Param.VIEW_ENTER);
			rfpn.setDefaultValue(10.0);
			rfpn.setValue(10.0);
			rfpn.setLowerBound(new Double(1));
			rf.add(rfpn);
			
			fList.addRFunction("Log", rf);
			
			//////////////
			rf = new RFunction("scales::trans_breaks");
			rf.setTitle("Transformation");
			
			ParamAny rfpa = new ParamAny("trans");
			rfpa.setValue("log");
			rf.add(rfpa);
			
			rfpa = new ParamAny("inv");
			rfpa.setValue("exp");
			rf.add(rfpa);
			
			fList.addRFunction("Transformation", rf);
			
			//////////////
			rf = new RFunction("c");
			rf.setTitle("Manual");
			
			ParamVector rfpv = new ParamVector("breaks");
			rfpv.setName(null);
			rf.add(rfpv);
			fList.addRFunction("Manual", rf);
			
			p = fList;
			
		}else if(name=="labels"){
			RFunctionList fList = new RFunctionList("Labels");
			fList.setName("labels");
			//////////
			RFunction rf = new RFunction("comma_format");
			rf.setTitle("Comma");
			
			ParamNumeric rfpn = new ParamNumeric();
			rfpn.setName("digits");
			rfpn.setTitle("digits");
			rfpn.setViewType(Param.VIEW_ENTER);
			rfpn.setLowerBound(new Double(0));
			rfpn.setRequired(false);
			rf.add(rfpn);
			
			fList.addRFunction("Comma", rf);
			
			//////////////
			rf = new RFunction("dollar_format");
			rf.setTitle("Dollar");
			fList.addRFunction("Dollar", rf);
			
			/////////////
			rf = new RFunction("percent_format");
			rf.setTitle("Percent");
			fList.addRFunction("Percent", rf);
			
			/////////////
			rf = new RFunction("scientific_format");
			rf.setTitle("Scientific");
			
			rfpn = new ParamNumeric();
			rfpn.setName("digits");
			rfpn.setTitle("digits");
			rfpn.setViewType(Param.VIEW_ENTER);
			rfpn.setValue(3.0);
			rfpn.setDefaultValue(3.0);
			rfpn.setLowerBound(new Double(0));
			rf.add(rfpn);
			
			fList.addRFunction("Scientific", rf);			
			
			
			/////////////
			rf = new RFunction("date_format");
			rf.setTitle("Date");
			
			ParamCharacter rfpc = new ParamCharacter("format");
			rfpc.setValue("%Y-%m-%d");
			rfpc.setDefaultValue("%Y-%m-%d");
			rf.add(rfpc);
			fList.addRFunction("Date", rf);
			
			/////////////
			rf = new RFunction("parse_format");
			rf.setTitle("Expression");
			fList.addRFunction("Expression", rf);
			
			/////////////
			rf = new RFunction("math_format");
			rf.setTitle("Math");
			
			ParamAny rfpa = new ParamAny("expr");
			rfpa.setValue("10^.x");
			rfpa.setDefaultValue("10^.x");
			rf.add(rfpa);
			
			fList.addRFunction("Math", rf);
			
			/////////////
			rf = new RFunction("trans_format");
			rf.setTitle("Transformation");
			
			rfpc = new ParamCharacter("trans");
			rfpc.setViewType(Param.VIEW_COMBO);
			rfpc.setOptions(new String[] {"asn", "atanh", "boxcox", "exp",
					"identity", "log1p", "log" ,"logit", "probability", 
					"probit", "reciprocal", "reverse", "sqrt"});
			rf.add(rfpc);
			
			rfpa = new ParamAny("format");
			rfpa.setValue("format_scientific()");
			rfpa.setDefaultValue("format_scientific()");
			rf.add(rfpa);
			
			fList.addRFunction("Transformation", rf);

			//////////////
			rf = new RFunction("c");
			rf.setTitle("Manual");
			
			ParamVector rfpv = new ParamVector("breaks");
			rfpv.setName(null);
			rfpv.setNumeric(false);
			rf.add(rfpv);
			
			fList.addRFunction("Manual", rf);
			
			/////////////
			p=fList;
			
		}else if(name=="guide"){
			RFunctionList fList = new RFunctionList("Guide");
			fList.setName("guide");
			fList.setViewType(RFunctionList.VIEW_RFUNCTION_CHOOSER);
			fList.setRequired(false);
			
			//////////
			RFunction rf = new RFunction("c");
			rf.setTitle("None");
			
			ParamCharacter rfpc = new ParamCharacter("pos");
			rfpc.setValue("none");
			rfpc.setViewType(ParamCharacter.VIEW_HIDDEN);
			rf.add(rfpc);
			
			fList.addRFunction("None", rf);
			
			
			///////////
			rf = new RFunction("guide_legend");
			rf.setTitle("Legend Guide");
			
			ParamNone pnone = new ParamNone("Layout");
			pnone.setViewType(ParamNone.VIEW_SEPERATOR);
			rf.add(pnone);
			
			rfpc = new ParamCharacter("direction");
			rfpc.setTitle("Legend orientation");
			rfpc.setOptions(new String[]{"horizontal","vertical"});
			rfpc.setViewType(ParamCharacter.VIEW_COMBO);
			rfpc.setRequired(false);
			rf.add(rfpc);
			
			ParamNumeric rfpn = new ParamNumeric();
			rfpn.setName("nrow");
			rfpn.setTitle("# Rows");
			rfpn.setViewType(ParamNumeric.VIEW_ENTER);
			rfpn.setLowerBound(new Double(1));
			rfpn.setRequired(false);
			rf.add(rfpn);
			
			rfpn = new ParamNumeric();
			rfpn.setName("ncol");
			rfpn.setTitle("# Columns");
			rfpn.setViewType(ParamNumeric.VIEW_ENTER);
			rfpn.setLowerBound(new Double(1));
			rfpn.setRequired(false);
			rf.add(rfpn);
			
			ParamLogical rfpl = new ParamLogical();
			rfpl.setName("byrow");
			rfpl.setTitle("By row");	
			rfpl.setDefaultValue(false);
			rfpl.setValue(false);
			rf.add(rfpl);
			
			rfpl = new ParamLogical();
			rfpl.setName("reverse");
			rfpl.setTitle("Reverse order");	
			rfpl.setDefaultValue(false);
			rfpl.setValue(false);
			rf.add(rfpl);
			
			pnone = new ParamNone("Title");
			pnone.setViewType(ParamNone.VIEW_SEPERATOR);
			rf.add(pnone);
			
			rfpc = new ParamCharacter("title.position");
			rfpc.setTitle("Position");
			rfpc.setOptions(new String[]{"top","right","bottom","left"});
			rfpc.setViewType(ParamCharacter.VIEW_COMBO);
			rfpc.setRequired(false);
			rf.add(rfpc);
			
			RFunctionList pf = new RFunctionList();
			pf.setName("title.theme");
			pf.setTitle("Theme");
			pf.addRFunction("theme_blank", Theme.makeThemeBlank());
			pf.addRFunction("theme_text", Theme.makeThemeText());
			pf.setRequired(false);
			rf.add(pf);
			
			rfpn = new ParamNumeric();
			rfpn.setName("title.hjust");
			rfpn.setTitle("horizontal");
			rfpn.setViewType(ParamNumeric.VIEW_ENTER);
			rfpn.setLowerBound(new Double(0));
			rfpn.setUpperBound(new Double(1));
			rfpn.setRequired(false);
			rf.add(rfpn);
			
			rfpn = new ParamNumeric();
			rfpn.setName("title.vjust");
			rfpn.setTitle("vertical");
			rfpn.setViewType(ParamNumeric.VIEW_ENTER);
			rfpn.setLowerBound(new Double(0));
			rfpn.setUpperBound(new Double(1));
			rfpn.setRequired(false);
			rf.add(rfpn);
			
			pnone = new ParamNone("Labels");
			pnone.setViewType(ParamNone.VIEW_SEPERATOR);
			rf.add(pnone);
			
			rfpl = new ParamLogical();
			rfpl.setName("label");
			rfpl.setTitle("Show");	
			rfpl.setDefaultValue(true);
			rfpl.setValue(true);
			rf.add(rfpl);
			
			rfpn = new ParamNumeric();
			rfpn.setName("label.hjust");
			rfpn.setTitle("horizontal");
			rfpn.setViewType(ParamNumeric.VIEW_ENTER);
			rfpn.setLowerBound(new Double(0));
			rfpn.setUpperBound(new Double(1));
			rfpn.setRequired(false);
			rf.add(rfpn);
			
			rfpn = new ParamNumeric();
			rfpn.setName("label.vjust");
			rfpn.setTitle("vertical");
			rfpn.setViewType(ParamNumeric.VIEW_ENTER);
			rfpn.setLowerBound(new Double(0));
			rfpn.setUpperBound(new Double(1));
			rfpn.setRequired(false);
			rf.add(rfpn);
			
			pnone = new ParamNone("Key");
			pnone.setViewType(ParamNone.VIEW_SEPERATOR);
			rf.add(pnone);
			
			rfpn = new ParamNumeric();
			rfpn.setName("keywidth");
			rfpn.setTitle("Width");
			rfpn.setViewType(ParamNumeric.VIEW_ENTER);
			rfpn.setLowerBound(new Double(0));
			rfpn.setRequired(false);
			rf.add(rfpn);
			
			rfpn = new ParamNumeric();
			rfpn.setName("keyheight");
			rfpn.setTitle("Height");
			rfpn.setViewType(ParamNumeric.VIEW_ENTER);
			rfpn.setLowerBound(new Double(0));
			rfpn.setRequired(false);
			rf.add(rfpn);
			
			pf = new RFunctionList();
			pf.setName("default.unit");
			pf.setTitle("Scale");
			pf.addRFunction("unit", Theme.makeUnit());
			pf.setRequired(false);
			rf.add(pf);
			
			pnone = new ParamNone("Misc.");
			pnone.setViewType(ParamNone.VIEW_SEPERATOR);
			rf.add(pnone);
			
			ParamAny pa = new ParamAny("override.aes");
			pa.setTitle("Overrides");
			pa.setValue("list()");
			pa.setDefaultValue("list()");
			rf.add(pa);
			
			fList.addRFunction("Legend", rf);
			
			Vector v = new Vector();
			v.add("Legend");
			fList.setDefaultActiveFunctions(v);
			fList.setActiveFunctions(v);
			
			p = fList;
		}else if(name=="guidecolourbar"){
			RFunction rf = new RFunction("guide_colourbar");
			rf.setTitle("Colour Bar Guide");
			
			ParamNone pnone = new ParamNone("Layout");
			pnone.setViewType(ParamNone.VIEW_SEPERATOR);
			rf.add(pnone);
			
			ParamCharacter rfpc = new ParamCharacter("direction");
			rfpc.setTitle("Legend orientation");
			rfpc.setOptions(new String[]{"horizontal","vertical"});
			rfpc.setViewType(ParamCharacter.VIEW_COMBO);
			rfpc.setRequired(false);
			rf.add(rfpc);
			
			ParamLogical rfpl = new ParamLogical();
			rfpl.setName("reverse");
			rfpl.setTitle("Reverse order");	
			rfpl.setDefaultValue(false);
			rfpl.setValue(false);
			rf.add(rfpl);
			
			pnone = new ParamNone("Title");
			pnone.setViewType(ParamNone.VIEW_SEPERATOR);
			rf.add(pnone);
			
			rfpc = new ParamCharacter("title.position");
			rfpc.setTitle("Position");
			rfpc.setOptions(new String[]{"top","right","bottom","left"});
			rfpc.setViewType(ParamCharacter.VIEW_COMBO);
			rfpc.setRequired(false);
			rf.add(rfpc);
			
			RFunctionList pf = new RFunctionList();
			pf.setName("title.theme");
			pf.setTitle("Theme");
			pf.addRFunction("theme_blank", Theme.makeThemeBlank());
			pf.addRFunction("theme_text", Theme.makeThemeText());
			pf.setRequired(false);
			rf.add(pf);
			
			ParamNumeric rfpn = new ParamNumeric();
			rfpn.setName("title.hjust");
			rfpn.setTitle("horizontal");
			rfpn.setViewType(ParamNumeric.VIEW_ENTER);
			rfpn.setLowerBound(new Double(0));
			rfpn.setUpperBound(new Double(1));
			rfpn.setRequired(false);
			rf.add(rfpn);
			
			rfpn = new ParamNumeric();
			rfpn.setName("title.vjust");
			rfpn.setTitle("vertical");
			rfpn.setViewType(ParamNumeric.VIEW_ENTER);
			rfpn.setLowerBound(new Double(0));
			rfpn.setUpperBound(new Double(1));
			rfpn.setRequired(false);
			rf.add(rfpn);
			
			pnone = new ParamNone("Labels");
			pnone.setViewType(ParamNone.VIEW_SEPERATOR);
			rf.add(pnone);
			
			rfpl = new ParamLogical();
			rfpl.setName("label");
			rfpl.setTitle("Show");	
			rfpl.setDefaultValue(true);
			rfpl.setValue(true);
			rf.add(rfpl);
			
			rfpn = new ParamNumeric();
			rfpn.setName("label.hjust");
			rfpn.setTitle("horizontal");
			rfpn.setViewType(ParamNumeric.VIEW_ENTER);
			rfpn.setLowerBound(new Double(0));
			rfpn.setUpperBound(new Double(1));
			rfpn.setRequired(false);
			rf.add(rfpn);
			
			rfpn = new ParamNumeric();
			rfpn.setName("label.vjust");
			rfpn.setTitle("vertical");
			rfpn.setViewType(ParamNumeric.VIEW_ENTER);
			rfpn.setLowerBound(new Double(0));
			rfpn.setUpperBound(new Double(1));
			rfpn.setRequired(false);
			rf.add(rfpn);
			
			pnone = new ParamNone("Colour bar");
			pnone.setViewType(ParamNone.VIEW_SEPERATOR);
			rf.add(pnone);
			
			rfpn = new ParamNumeric();
			rfpn.setName("barwidth");
			rfpn.setTitle("Width");
			rfpn.setViewType(ParamNumeric.VIEW_ENTER);
			rfpn.setLowerBound(new Double(0));
			rfpn.setRequired(false);
			rf.add(rfpn);
			
			rfpn = new ParamNumeric();
			rfpn.setName("barheigth");
			rfpn.setTitle("Height");
			rfpn.setViewType(ParamNumeric.VIEW_ENTER);
			rfpn.setLowerBound(new Double(0));
			rfpn.setRequired(false);
			rf.add(rfpn);
			
			rfpn = new ParamNumeric();
			rfpn.setName("nbins");
			rfpn.setTitle("# bins");
			rfpn.setViewType(ParamNumeric.VIEW_ENTER);
			rfpn.setLowerBound(new Double(1));
			rfpn.setDefaultValue(20.0);
			rfpn.setValue(20.0);
			rfpn.setRequired(false);
			rf.add(rfpn);
			
			rfpl = new ParamLogical();
			rfpl.setName("raster");
			rfpl.setTitle("Raster");	
			rfpl.setDefaultValue(true);
			rfpl.setValue(true);
			rf.add(rfpl);
			
			pnone = new ParamNone("Tick marks");
			pnone.setViewType(ParamNone.VIEW_SEPERATOR);
			rf.add(pnone);
			
			rfpl = new ParamLogical();
			rfpl.setName("ticks");
			rfpl.setTitle("Show");	
			rfpl.setDefaultValue(true);
			rfpl.setValue(true);
			rf.add(rfpl);
			
			rfpl = new ParamLogical();
			rfpl.setName("draw.ulim");
			rfpl.setTitle("Upper");	
			rfpl.setDefaultValue(true);
			rfpl.setValue(true);
			rf.add(rfpl);
			
			rfpl = new ParamLogical();
			rfpl.setName("draw.llim");
			rfpl.setTitle("Lower");	
			rfpl.setDefaultValue(true);
			rfpl.setValue(true);
			rf.add(rfpl);
			
			pnone = new ParamNone("Misc.");
			pnone.setViewType(ParamNone.VIEW_SEPERATOR);
			rf.add(pnone);
			
			ParamAny pa = new ParamAny("override.aes");
			pa.setTitle("Overrides");
			pa.setValue("list()");
			pa.setDefaultValue("list()");
			rf.add(pa);
			
			p = rf;			
		}else if(name=="arrow"){
		
			RFunctionList arrowList = new RFunctionList("arrow");
			RFunction arrow = new RFunction("arrow");
			arrow.setTitle("arrow");
			
			ParamNumeric rfpn = new ParamNumeric();
			rfpn.setName("angle");
			rfpn.setTitle("angle");
			rfpn.setViewType(Param.VIEW_ENTER);
			rfpn.setValue(new Double(30));
			rfpn.setDefaultValue(new Double(30));
			rfpn.setLowerBound(new Double(0));
			arrow.add(rfpn);
			
			RFunctionList rfl = new RFunctionList("length");
			RFunction rf = new RFunction();
			rf.setName("unit");
			rf.setTitle("unit");
			Param rfp;
			rfpn = new ParamNumeric();
			rfpn.setName("x");
			rfpn.setTitle("size");
			rfpn.setViewType(Param.VIEW_ENTER);
			rfpn.setValue(new Double(.1));
			rfpn.setDefaultValue(new Double(0));
			rfpn.setLowerBound(new Double(0));
			rf.add(rfpn);		
			rfp = new ParamCharacter("units");
			rfp.setName("units");
			rfp.setTitle("units");
			rfp.setViewType(Param.VIEW_EDITABLE_COMBO);
			rfp.setOptions(new String[] {"npc", "cm", "inches", "mm",
					"points", "picas", "bigpts" ,"dida", "cicero", 
					"scaledpts", "lines", "char", "native", "snpc", 
					"strwidth" ,"strheight","grobwidth","grobheight"});
			rfp.setDefaultValue("inches");
			rfp.setValue("cm");
			rf.add(rfp);	
			rfl.addRFunction("unit", rf);
			arrow.add(rfl);
			
			rfp = new ParamCharacter("ends");
			rfp.setName("ends");
			rfp.setTitle("ends");
			rfp.setViewType(Param.VIEW_COMBO);
			rfp.setOptions(new String[] {"last","first","both"});
			rfp.setDefaultValue("last");
			rfp.setValue("last");			
			arrow.add(rfp);
			
			rfp = new ParamCharacter("type");
			rfp.setName("type");
			rfp.setTitle("type");
			rfp.setViewType(Param.VIEW_COMBO);
			rfp.setOptions(new String[] {"open","closed"});
			rfp.setDefaultValue("open");
			rfp.setValue("open");			
			arrow.add(rfp);
			arrowList.addRFunction("arrow", arrow);
			p=arrowList;
		}else if(name == "bins"){
			ParamNumeric pn = new ParamNumeric("bins");
			pn.setViewType(Param.VIEW_ENTER);
			pn.setLowerBound(new Double(1.0));
			p=pn;		
		}else if(name == "breaks"){
			ParamVector pv = new ParamVector("breaks");
			pv.setNumeric(true);
			p = pv;
		}else if(name =="binwidth"){
			ParamNumeric pn = new ParamNumeric("binwidth");
			pn.setViewType(Param.VIEW_ENTER);
			pn.setLowerBound(new Double(0.0));
			p=pn;		
		}else if(name =="coef"){
			ParamNumeric pn = new ParamNumeric("coef");
			pn.setDefaultValue(new Double(2));
			pn.setValue(new Double(2));
			p=pn;
		}else if(name =="adjust"){
			ParamNumeric pn = new ParamNumeric("adjust");
			pn.setViewType(Param.VIEW_ENTER);
			pn.setLowerBound(new Double(0.0));
			p=pn;
		}else if(name =="kernel"){
			ParamCharacter pc = new ParamCharacter("kernel");
			pc.setViewType(Param.VIEW_COMBO);
			pc.setOptions(new String[] {"gaussian", "epanechnikov", "rectangular",
	                   "triangular", "biweight",
	                   "cosine", "optcosine"});
			pc.setLabels(new String[] {"gaussian", "epanechnikov", "rectangular",
	                   "triangular", "biweight",
	                   "cosine", "optcosine"});
			p = pc;
		}else if(name =="trim"){
			ParamNumeric pn = new ParamNumeric("trim");
			pn.setViewType(Param.VIEW_ENTER);
			pn.setLowerBound(new Double(1.0));
			p=pn;
		}else if(name=="contour"){
			ParamLogical pl = new ParamLogical("contour");
			pl.setDefaultValue(new Boolean(true));	
			pl.setValue(new Boolean(true));	
			p=pl;
		}else if(name =="quantiles"){
			ParamVector pv = new ParamVector("quantiles");
			pv.setDefaultValue(new String[] {"0.25","0.5","0.75"});
			pv.setValue(new String[] {"0.25","0.5","0.75"});
			p = pv;
		}else if(name =="method"){
			ParamAny pa = new ParamAny("method");
			pa.setDefaultValue("");
			pa.setValue("");
			pa.setViewType(Param.VIEW_COMBO);
			pa.setOptions(new String[] {"lm", "rlm", "gam", "loess", 
					"glm, family=binomial","glm, family=poisson","glm, family=Gamma"});
			pa.setLabels(new String[] {"Linear","Robust linear",  "Generalized additive",
									"Smooth",
									"Logistic", "Poisson", "Gamma"});
			p = pa;
		}else if(name =="formula"){
			ParamCharacter pc = new ParamCharacter("formula");
			pc.setViewType(Param.VIEW_EDITABLE_COMBO);
			pc.setOptions(new String[] {"y ~ x", "y ~ poly(x,2)", "y ~ poly(x,3)"});
			p=pc;
		}else if(name =="fun"){
	
		}else if(name =="args"){
	
		}else if(name =="se"){
			ParamLogical pl = new ParamLogical("se");
			pl.setDefaultValue(new Boolean(true));	
			pl.setValue(new Boolean(true));	
			p=pl;	
			p.setTitle("Show confidence");
		}else if(name=="fullrange"){
			ParamLogical pl = new ParamLogical("fullrange");
			pl.setDefaultValue(new Boolean(false));	
			pl.setValue(new Boolean(false));	
			p=pl;	
			p.setTitle("Full data range");
		}else if(name =="level"){
			ParamNumeric pn = new ParamNumeric("width");
			pn.setViewType(Param.VIEW_ENTER);
			pn.setLowerBound(new Double(0.0));
			pn.setUpperBound(new Double(1.0));
			pn.setDefaultValue(new Double(0.95));
			p = pn;
		}else if(name =="direction"){
			ParamCharacter pc = new ParamCharacter("kernel");
			pc.setOptions(new String[]{"vh","hv"});
			pc.setLabels(new String[] {"Vertical then horizontal",
									"Horizontal then vertical"});
			pc.setDefaultValue("vh");
			pc.setViewType(Param.VIEW_COMBO);
			p=pc;
		}
		if(p.getValue()==null)
			p.setValue(p.getDefaultValue());
		return p;
	}

}
