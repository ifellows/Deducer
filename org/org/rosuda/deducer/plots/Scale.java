package org.rosuda.deducer.plots;

import java.awt.Color;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.XMLHelper;
import org.rosuda.deducer.widgets.param.Param;
import org.rosuda.deducer.widgets.param.ParamAny;
import org.rosuda.deducer.widgets.param.ParamCharacter;
import org.rosuda.deducer.widgets.param.ParamColor;
import org.rosuda.deducer.widgets.param.ParamLogical;
import org.rosuda.deducer.widgets.param.ParamNone;
import org.rosuda.deducer.widgets.param.ParamNumeric;
import org.rosuda.deducer.widgets.param.ParamVector;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Scale implements ElementModel{

	private static String[] transformations = new String[] {"asn","atanh","exp",
		"identity","log","log10","log1p","probit","reciprocal","reverse","sqrt"};
	private static String[] transformationNames = new String[] {"Arc-sin square root","Arc-tangent","e^x",
		"identity","log","log base 10","log + 1","probit","1 / x","reverse","square root"};
	private String name;
	public String aesName;
	
	public Vector params = new Vector();
	
	public static Scale makeAlpha(){
		Scale s = new Scale();
		s.setName("scale_alpha");
		s.aesName = "alpha";
		
		Param p;
		ParamNumeric pn;
		ParamVector pv;
		
		p = new ParamScaleLegend("legend",s.aesName,true);
		p.setTitle("");
		p.setViewType(ParamScaleLegend.VIEW_SCALE);
		s.params.add(p);		
	
		
		p = new ParamVector();
		p.setName("limits");
		p.setTitle("Data range");

		s.params.add(p);
		
		pv = new ParamVector();
		pv.setName("range");
		pv.setTitle("Alpha range");
		pv.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		pv.setValue(new String[]{"0","1"});
		pv.setDefaultValue(new String[]{"0","1"});
		pv.setLowerBound(new Double(0));
		pv.setUpperBound(new Double(1));
		s.params.add(pv);		

		p = new ParamCharacter();
		p.setName("trans");
		p.setTitle("Transform");
		p.setViewType(Param.VIEW_COMBO);
		p.setValue("identity");
		p.setDefaultValue("identity");
		p.setOptions(transformations);
		p.setLabels(transformationNames);
		s.params.add(p);
		
		
		return s;		
	}
	
	public static Scale makeBrewer(String aes){
		Scale s = new Scale();
		s.setName("scale_"+aes+"_brewer");
		s.aesName = aes;
		
		Param p;
		
		p = new ParamScaleLegend("legend",s.aesName,false);
		p.setTitle("");
		p.setViewType(ParamScaleLegend.VIEW_SCALE);
		s.params.add(p);		
		
		
		p = new ParamVector();
		p.setName("limits");
		p.setTitle("Included levels");
		s.params.add(p);
		
		p = new ParamCharacter();
		p.setName("palette");
		p.setTitle("Colour palette");
		p.setViewType(Param.VIEW_COMBO);
		p.setValue("Blues");
		p.setDefaultValue("Blues");
		p.setOptions(new String[]{"YlOrRd","YlOrBr","YlGnBu","YlGn","Reds","RdPu",
				"Purples","PuRd","PuBuGn","PuBu","OrRd","Oranges","Greys","Greens",
				"GnBu","BuPu","BuGn","Blues","","Set3","Set2","Set1","Pastel2","Pastel1",
				"Paired","Dark2","Accent","","Spectral","RdYlGn","RdYlBu","RdGy",
				"RdBu","PuOr","PRGn","PiYG","BrBG"});
		s.params.add(p);
		

		
		
		return s;		
	}
	
	public static Scale makeContinuous(String aes){
		Scale s = new Scale();
		s.setName("scale_"+aes+"_continuous");
		s.aesName = aes;
		
		Param p;
		
		p = new ParamScaleLegend("legend",s.aesName,true);
		p.setTitle("");
		p.setViewType(ParamScaleLegend.VIEW_SCALE);
		s.params.add(p);		

/*		p = new ParamCharacter();
		p.setName("name");
		p.setTitle("Title");
		s.params.add(p);
		
		p = ParamFactory.makeParam("scalebreaks");
		s.params.add(p);
		
		p = ParamFactory.makeParam("labels");
		s.params.add(p);
		*/
		p = new ParamVector();
		p.setName("expand");
		p.setTitle("Expand (*,+)");
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		p.setValue(new String[]{"0.05","0.55"});
		p.setDefaultValue(new String[]{"0.05","0.55"});
		s.params.add(p);	
		
		p = new ParamNone("Data");
		p.setViewType(ParamNone.VIEW_SEPERATOR);
		s.params.add(p);
		
		p = new ParamVector();
		p.setName("limits");
		p.setTitle("Range");
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		s.params.add(p);
		
		

		p = new ParamCharacter();
		p.setName("trans");
		p.setTitle("Transform");
		p.setViewType(Param.VIEW_COMBO);
		p.setValue("identity");
		p.setDefaultValue("identity");
		p.setOptions(transformations);
		p.setLabels(transformationNames);	
		s.params.add(p);
		
	
		return s;		
	}
	
	public static Scale makeDate(String aes){
		Scale s = new Scale();
		s.setName("scale_"+aes+"_date");
		s.aesName = aes;
		
		Param p;
		
		
		p = new ParamScaleLegend("legend",s.aesName,true);
		p.setTitle("");
		p.setViewType(ParamScaleLegend.VIEW_SCALE);
		s.params.add(p);
		
		p = new ParamVector();
		p.setName("expand");
		p.setTitle("Expand (*,+)");
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		p.setValue(new String[]{"0.05","0.55"});
		p.setDefaultValue(new String[]{"0.05","0.55"});
		s.params.add(p);	
		
		p = new ParamNone("Data");
		p.setViewType(ParamNone.VIEW_SEPERATOR);
		s.params.add(p);
		
		p = new ParamVector();
		p.setName("limits");
		p.setTitle("Range");
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		s.params.add(p);
		
		return s;		
	}
	
	public static Scale makeDatetime(String aes){
		Scale s = new Scale();
		s.setName("scale_"+aes+"_datetime");
		s.aesName = aes;
		
		Param p;
		
		p = new ParamScaleLegend("legend",s.aesName,true);
		p.setTitle("");
		p.setViewType(ParamScaleLegend.VIEW_SCALE);
		s.params.add(p);
		
		p = new ParamVector();
		p.setName("expand");
		p.setTitle("Expand (*,+)");
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		p.setValue(new String[]{"0.05","0.55"});
		p.setDefaultValue(new String[]{"0.05","0.55"});
		s.params.add(p);	
		
		p = new ParamNone("Data");
		p.setViewType(ParamNone.VIEW_SEPERATOR);
		s.params.add(p);
		
		p = new ParamVector();
		p.setName("limits");
		p.setTitle("Range");
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		s.params.add(p);
		
		return s;		
	}
	
	public static Scale makeDiscrete(String aes){
		Scale s = new Scale();
		s.setName("scale_"+aes+"_discrete");
		s.aesName = aes;
		
		Param p;
		
		p = new ParamScaleLegend("legend",s.aesName,false);

		p.setViewType(ParamScaleLegend.VIEW_SCALE);
		s.params.add(p);		
		
		
		p = new ParamVector();
		p.setName("expand");
		p.setTitle("Expand");
		p.setValue(new String[]{"0.05","0.55"});
		p.setDefaultValue(new String[]{"0.05","0.55"});
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		s.params.add(p);
		
		ParamVector pv = new ParamVector();
		pv.setName("limits");
		pv.setNumeric(false);
		pv.setTitle("Included levels");
		s.params.add(pv);
		
		p = new ParamLogical();
		p.setName("drop");
		p.setTitle("Drop unused levels");
		p.setViewType(Param.VIEW_CHECK_BOX);
		p.setValue(new Boolean(true));
		p.setDefaultValue(new Boolean(true));
		s.params.add(p);
		
		
		
		
		return s;		
	}
	
	public static Scale makeGradient(String aes){
		Scale s = new Scale();
		s.setName("scale_"+aes+"_gradient");
		s.aesName = aes;
		
		Param p;
		
		p = new ParamScaleLegend("legend",s.aesName,true);
		p.setTitle("");
		p.setViewType(ParamScaleLegend.VIEW_SCALE);
		s.params.add(p);		
		
		p = new ParamNone("Colours");
		p.setViewType(ParamNone.VIEW_SEPERATOR);
		s.params.add(p);
		
		p = new ParamColor();
		p.setName("low");
		p.setTitle("Low");
		p.setViewType(Param.VIEW_COLOR);
		p.setValue(Color.decode("#132B43"));
		p.setDefaultValue(Color.decode("#132B43"));
		s.params.add(p);

		p = new ParamColor();
		p.setName("high");
		p.setTitle("High");
		p.setViewType(Param.VIEW_COLOR);
		p.setValue(Color.decode("#56B1F7"));
		p.setDefaultValue(Color.decode("#56B1F7"));
		s.params.add(p);
		
		p = new ParamCharacter();
		p.setName("space");
		p.setTitle("Space");
		p.setViewType(Param.VIEW_COMBO);
		p.setValue("rgb");
		p.setDefaultValue("rgb");
		p.setOptions(new String[] {"rgb","Lab"});
		s.params.add(p);
		
		p = new ParamNone("Data");
		p.setViewType(ParamNone.VIEW_SEPERATOR);
		s.params.add(p);
		
		p = new ParamVector();
		p.setName("limits");
		p.setTitle("Range");
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		s.params.add(p);
		
		p = new ParamCharacter();
		p.setName("trans");
		p.setTitle("Transform");
		p.setViewType(Param.VIEW_COMBO);
		p.setValue("identity");
		p.setDefaultValue("identity");
		p.setOptions(transformations);
		p.setLabels(transformationNames);
		s.params.add(p);
		
		return s;		
	}
	
	public static Scale makeGradient2(String aes){
		Scale s = new Scale();
		s.setName("scale_"+aes+"_gradient2");
		s.aesName = aes;
		
		Param p;
		ParamNumeric pn;
		
		p = new ParamScaleLegend("legend",s.aesName,true);
		p.setTitle("");
		p.setViewType(ParamScaleLegend.VIEW_SCALE);
		s.params.add(p);
		
	
		
		p = new ParamNone("Colours");
		p.setViewType(ParamNone.VIEW_SEPERATOR);
		s.params.add(p);
		
		p = new ParamColor();
		p.setName("low");
		p.setTitle("Low");
		p.setViewType(Param.VIEW_COLOR);
		p.setValue(Color.decode("#3B4FB8"));
		p.setDefaultValue(Color.decode("#3B4FB8"));
		s.params.add(p);

		p = new ParamColor();
		p.setName("mid");
		p.setTitle("Mid-point");
		p.setViewType(Param.VIEW_COLOR);	
		s.params.add(p);
		
		
		p = new ParamColor();
		p.setName("high");
		p.setTitle("High");
		p.setViewType(Param.VIEW_COLOR);
		p.setValue(Color.decode("#B71B1A"));
		p.setDefaultValue(Color.decode("#B71B1A"));
		s.params.add(p);
		
		p = new ParamCharacter();
		p.setName("space");
		p.setTitle("Space");
		p.setViewType(Param.VIEW_COMBO);
		p.setValue("rgb");
		p.setDefaultValue("rgb");
		p.setOptions(new String[] {"rgb","Lab"});
		s.params.add(p);
		
		p = new ParamNone("Data");
		p.setViewType(ParamNone.VIEW_SEPERATOR);
		s.params.add(p);
		
		pn = new ParamNumeric();
		pn.setName("midpoint");
		pn.setTitle("Mid-point value");
		pn.setViewType(Param.VIEW_ENTER);
		s.params.add(pn);		
		
		p = new ParamVector();
		p.setName("limits");
		p.setTitle("Data range");
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		s.params.add(p);
		
		p = new ParamCharacter();
		p.setName("trans");
		p.setTitle("Transform");
		p.setViewType(Param.VIEW_COMBO);
		p.setValue("identity");
		p.setDefaultValue("identity");
		p.setOptions(transformations);
		p.setLabels(transformationNames);
		s.params.add(p);
		
		return s;		
	}
	
	public static Scale makeGradientn(String aes){
		Scale s = new Scale();
		s.setName("scale_"+aes+"_gradientn");
		s.aesName = aes;
		
		Param p;
		ParamVector pv;
		
		p = new ParamScaleLegend("legend",s.aesName,true);
		p.setTitle("");
		p.setViewType(ParamScaleLegend.VIEW_SCALE);
		s.params.add(p);		
		
		pv = new ParamVector();
		pv.setName("colours");
		pv.setTitle("Colours");
		pv.setNumeric(false);
		s.params.add(pv);
		
		p = new ParamCharacter();
		p.setName("space");
		p.setTitle("Colour space");
		p.setViewType(Param.VIEW_COMBO);
		p.setValue("rgb");
		p.setDefaultValue("rgb");
		p.setOptions(new String[] {"rgb","Lab"});
		s.params.add(p);
		
		p = new ParamNone("Data");
		p.setViewType(ParamNone.VIEW_SEPERATOR);
		s.params.add(p);
		
		p = new ParamVector();
		p.setName("limits");
		p.setTitle("Data range");
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		s.params.add(p);
		
		p = new ParamCharacter();
		p.setName("trans");
		p.setTitle("Transform");
		p.setViewType(Param.VIEW_COMBO);
		p.setValue("identity");
		p.setDefaultValue("identity");
		p.setOptions(transformations);
		p.setLabels(transformationNames);
		s.params.add(p);
		
		return s;		
	}
	
	public static Scale makeGrey(String aes){
		Scale s = new Scale();
		s.setName("scale_"+aes+"_grey");
		s.aesName = aes;
		
		Param p;
		ParamNumeric pn;
		
		
		p = new ParamScaleLegend("legend",s.aesName,false);
		p.setTitle("");
		p.setViewType(ParamScaleLegend.VIEW_SCALE);
		s.params.add(p);		
		
		p = new ParamNone("Gradient");
		p.setViewType(ParamNone.VIEW_SEPERATOR);
		s.params.add(p);
		
		pn = new ParamNumeric();
		pn.setName("start");
		pn.setTitle("Low");
		pn.setViewType(Param.VIEW_ENTER);
		pn.setValue(new Double(.2));
		pn.setDefaultValue(new Double(.2));
		pn.setLowerBound(new Double(0));
		pn.setUpperBound(new Double(1));
		s.params.add(pn);
		
		pn = new ParamNumeric();
		pn.setName("end");
		pn.setTitle("high");
		pn.setViewType(Param.VIEW_ENTER);
		pn.setValue(new Double(.2));
		pn.setDefaultValue(new Double(.2));
		pn.setLowerBound(new Double(0));
		pn.setUpperBound(new Double(1));
		s.params.add(pn);
		
		p = new ParamNone("Data");
		p.setViewType(ParamNone.VIEW_SEPERATOR);
		s.params.add(p);
		
		p = new ParamVector();
		p.setName("limits");
		p.setTitle("Data range");
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		s.params.add(p);
		
		p = new ParamCharacter();
		p.setName("trans");
		p.setTitle("Transform");
		p.setViewType(Param.VIEW_COMBO);
		p.setValue("identity");
		p.setDefaultValue("identity");
		p.setOptions(transformations);
		p.setLabels(transformationNames);
		s.params.add(p);
		
		return s;
	}
	
	public static Scale makeHue(String aes){
		Scale s = new Scale();
		s.setName("scale_"+aes+"_hue");
		s.aesName = aes;
		
		Param p;
		ParamNumeric pn;
		ParamVector pv;
		
		p = new ParamScaleLegend("legend",s.aesName,false);
		p.setTitle("");
		((ParamScaleLegend)p).setAes(aes);
		p.setViewType(ParamScaleLegend.VIEW_SCALE);
		s.params.add(p);
		
		p = new ParamNone("Colour");
		p.setViewType(ParamNone.VIEW_SEPERATOR);
		s.params.add(p);
		
		p = new ParamVector();
		p.setName("h");
		p.setTitle("Hue range");
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		p.setValue(new String[]{"15","375"});
		p.setDefaultValue(new String[]{"15","375"});
		s.params.add(p);		
		
		pn = new ParamNumeric();
		pn.setName("l");
		pn.setTitle("Luminance [0, 100]");
		pn.setViewType(Param.VIEW_ENTER);
		pn.setValue(new Double(65));
		pn.setDefaultValue(new Double(65));
		pn.setLowerBound(new Double(0));
		pn.setUpperBound(new Double(100));
		s.params.add(pn);
		
		pn = new ParamNumeric();
		pn.setName("c");
		pn.setTitle("Chroma");
		pn.setViewType(Param.VIEW_ENTER);
		pn.setValue(new Double(100));
		pn.setDefaultValue(new Double(100));
		pn.setLowerBound(new Double(0));
		s.params.add(pn);
		
		pn = new ParamNumeric();
		pn.setName("h.start");
		pn.setTitle("Hue start");
		pn.setViewType(Param.VIEW_ENTER);
		pn.setValue(new Double(0));
		pn.setDefaultValue(new Double(0));
		pn.setLowerBound(new Double(0));
		s.params.add(pn);

		pn = new ParamNumeric();
		pn.setName("direction");
		pn.setTitle("Colour wheel direction");
		pn.setViewType(Param.VIEW_COMBO);
		pn.setValue(new Double(1.0));
		pn.setDefaultValue(new Double(1.0));
		pn.setOptions(new String[] {"1.0","-1.0"});
		pn.setLabels(new String[] {"clockwise","counter clockwise"});
		s.params.add(pn);

		p = new ParamNone("Data");
		p.setViewType(ParamNone.VIEW_SEPERATOR);
		s.params.add(p);
		
		pv = new ParamVector();
		pv.setName("limits");
		pv.setTitle("Included levels");
		pv.setNumeric(false);
		s.params.add(pv);	
		
		return s;		
	}
	
	public static Scale makeIdentity(String aes){
		Scale s = new Scale();
		s.setName("scale_"+aes+"_identity");
		s.aesName = aes;
		
		Param p;
		
		p = new ParamScaleLegend("legend",s.aesName,false);
		p.setTitle("");
		p.setViewType(ParamScaleLegend.VIEW_SCALE);
		s.params.add(p);		
		
		
		return s;		
	}
	
	public static Scale makeLineType(){
		Scale s = new Scale();
		
		s.setName("scale_linetype");
		s.aesName = "linetype";
		
		Param p;
		ParamVector pv;
		
		p = new ParamScaleLegend("legend",s.aesName,false);
		p.setTitle("");
		p.setViewType(ParamScaleLegend.VIEW_SCALE);
		s.params.add(p);		
		
		pv = new ParamVector();
		pv.setName("limits");
		pv.setTitle("Included levels");
		pv.setNumeric(false);
		s.params.add(p);
		
		p = new ParamLogical();
		p.setName("drop");
		p.setTitle("Drop unused levels");
		p.setValue(new Boolean(true));
		p.setDefaultValue(new Boolean(true));
		s.params.add(p);
		
		
		
		
		return s;		
	}
	
	public static Scale makeManual(String aes){
		Scale s = new Scale();
		s.setName("scale_"+aes+"_manual");
		s.aesName = aes;
		
		Param p;
		ParamVector pv;
		
		p = new ParamScaleLegend("legend",s.aesName,false);
		p.setTitle("");
		p.setViewType(ParamScaleLegend.VIEW_SCALE);
		s.params.add(p);		

		pv = new ParamVector();
		pv.setName("values");
		pv.setTitle("Values");
		pv.setNumeric(!(aes.equals("colour") || aes.equals("fill")));
		s.params.add(pv);
		
		
		return s;		
	}
	
	public static Scale makeShape(){
		Scale s = new Scale();
		s.setName("scale_shape");
		s.aesName = "shape";
		
		Param p;
		ParamVector pv;
		
		p = new ParamScaleLegend("legend",s.aesName,false);
		p.setTitle("");
		p.setViewType(ParamScaleLegend.VIEW_SCALE);
		s.params.add(p);		
		
		pv = new ParamVector();
		pv.setName("limits");
		pv.setTitle("Included levels");
		pv.setNumeric(false);
		s.params.add(p);
		
		p = new ParamLogical();
		p.setName("solid");
		p.setTitle("Use solid points");
		p.setValue(new Boolean(true));
		p.setDefaultValue(new Boolean(true));
		s.params.add(p);
		
		return s;		
	}
	
	public static Scale makeSize(){
		Scale s = new Scale();
		s.setName("scale_size");
		s.aesName = "size";
		
		Param p;
		
		p = new ParamScaleLegend("legend",s.aesName,true);
		p.setTitle("");
		p.setViewType(ParamScaleLegend.VIEW_SCALE);
		s.params.add(p);		
		
		p = new ParamVector();
		p.setName("limits");
		p.setTitle("Data range");
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		s.params.add(p);
		
		p = new ParamVector();
		p.setName("range");
		p.setTitle("size range");
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		p.setValue(new String[]{"1","6"});
		p.setDefaultValue(new String[]{"1","6"});
		s.params.add(p);
		
		p = new ParamCharacter();
		p.setName("trans");
		p.setTitle("Transform");
		p.setViewType(Param.VIEW_COMBO);
		p.setValue("identity");
		p.setDefaultValue("identity");
		p.setOptions(transformations);
		p.setLabels(transformationNames);
		s.params.add(p);
		
		
		return s;		
	}
	
	public static Scale makeArea(){
		Scale s = Scale.makeSize();
		s.setName("scale_size_area");
		return s;
	}
	
	public static Scale makeScale(String aes,String scale){
		if(scale.equals("linetype"))
			return makeLineType();
		else if(scale.equals("discrete"))
			return makeDiscrete(aes);
		else if(scale.equals("shape"))
			return makeShape();
		else if(scale.equals("hue"))
			return makeHue(aes);
		else if(scale.equals("alpha"))
			return makeAlpha();
		else if(scale.equals("continuous"))
			return makeContinuous(aes);
		else if(scale.equals("date"))
			return makeDate(aes);
		else if(scale.equals("datetime"))
			return makeDatetime(aes);
		else if(scale.equals("gradient"))
			return makeGradient(aes);
		else if(scale.equals("gradient2"))
			return makeGradient2(aes);
		else if(scale.equals("gradientn"))
			return makeGradientn(aes);
		else if(scale.equals("grey"))
			return makeGrey(aes);
		else if(scale.equals("identity"))
			return makeIdentity(aes);
		else if(scale.equals("manual"))
			return makeManual(aes);
		else if(scale.equals("size"))
			return makeSize();
		else if(scale.equals("area"))
			return makeArea();
		else if(scale.equals("brewer"))
			return makeBrewer(aes);
		return null;
	}
	
	public Object clone(){
		Scale s = new Scale();
		for(int i=0;i<params.size();i++)
			s.params.add(((Param)params.get(i)).clone());
		s.setName(name);
		s.aesName = aesName;
		return s;
	}

	
	public String checkValid() {
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
		String call = Deducer.makeRCollection(paramCalls, getName(), false);
		return call;
	}

	public String getType() {
		return "scale";
	}

	public ElementView getView() {
		if(!this.getName().endsWith("brewer"))
			return new DefaultElementView(this);
		else
			return new ScaleBrewerPanel(this);
	}


	public Vector getParams() {
		return params;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String getData() {
		return null;
	}	
	
	public Element toXML() {
		try{
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element node = doc.createElement("ElementModel");
			node.setAttribute("className", "org.rosuda.deducer.plots.Scale");
			if(name!=null)
				node.setAttribute("name", name);
			if(aesName!=null)
				node.setAttribute("aesName", aesName);
			for(int i=0;i<params.size();i++){
				Element pEl = ((Param)params.get(i)).toXML();
				pEl = (Element) doc.importNode(pEl, true);
				node.appendChild(pEl);
			}
			doc.appendChild(node);
			return node;
		}catch(Exception ex){ex.printStackTrace();return null;}		
	}

	public void setFromXML(Element node) {
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.plots.Scale")){
			System.out.println("Error Scale: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		if(node.hasAttribute("name"))
			name = node.getAttribute("name");
		else 
			name = null;
		if(node.hasAttribute("aesName"))
			aesName = node.getAttribute("aesName");
		else 
			aesName = null;
		params = new Vector();
		Vector nl = XMLHelper.getChildrenElementsByTag(node, "Param");
			//node.getElementsByTagName("Param");
		for(int i=0;i<nl.size();i++){
			Element n = (Element) nl.get(i);
			cn = n.getAttribute("className");
			Param p = Param.makeParam(cn);
			p.setFromXML(n);
			params.add(p);
		}
	}	

}
