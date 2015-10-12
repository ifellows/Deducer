package org.rosuda.deducer.plots;

import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.widgets.param.Param;
import org.rosuda.deducer.widgets.param.ParamCharacter;
import org.rosuda.deducer.widgets.param.ParamLogical;
import org.rosuda.deducer.widgets.param.ParamNumeric;
import org.rosuda.deducer.widgets.param.ParamVector;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Coord implements ElementModel{
	
	private String name;
	
	public Vector params = new Vector();
	
	
	public static Coord makeCartesian(){
		Coord c = new Coord();
		c.setName("coord_cartesian");
		Param p;
		
		
		p = new ParamVector();
		p.setName("xlim");
		p.setTitle("x axis range");
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		c.params.add(p);
		
		p = new ParamVector();
		p.setName("ylim");
		p.setTitle("y axis range");
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		c.params.add(p);
		
		
		return c;
	}
	
	public static Coord makeEqual(){
		Coord c = new Coord();
		c.setName("coord_equal");
		ParamNumeric pn;
		
		pn = new ParamNumeric();
		pn.setName("ratio");
		pn.setTitle("x to y ratio");
		pn.setViewType(Param.VIEW_ENTER);
		pn.setValue(new Double(1));
		pn.setDefaultValue(new Double(1));
		c.params.add(pn);
		
		return c;
	}
	
	public static Coord makeFlip(){
		Coord c = new Coord();
		c.setName("coord_flip");
		Param p;
		
		
		p = new ParamVector();
		p.setName("xlim");
		p.setTitle("x axis range");
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		c.params.add(p);
		
		p = new ParamVector();
		p.setName("ylim");
		p.setTitle("y axis range");
		p.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		c.params.add(p);
		
		return c;
	}
	
	public static Coord makeMap(){
		Coord c = new Coord();
		c.setName("coord_map");
		Param p;
		ParamNumeric pn;
		ParamCharacter pc;
		
		pc = new ParamCharacter();
		pc.setName("projection");
		pc.setTitle("Projection");
		pc.setViewType(Param.VIEW_COMBO);
		pc.setValue(null);
		pc.setDefaultValue(null);		
		pc.setOptions(new String[]{ "mercator","sinusoidal","cylequalarea","cylindrical","rectangular",
				"gall","mollweide","gilbert","","azequidistant","azequalarea","gnomonic","perspective","orthographic",
				"stereographic","laue","fisheye","newyorker","","conic","simpleconic","lambert","albers","bonne",
				"","polyconic","aitoff","lagrange","bicentric","elliptic","globular","vandergrinten","eisenlohr","",
				"guyou","square","tetra","hex","","harrison","trapezoidal","lune","","mecca","homing"});
		pc.setLabels(new String[]{ "equally spaced straight meridians, conformal, straight compass courses",
				"equally spaced parallels, equal-area",
				"equally spaced straight meridians, equal-area, true scale on lat0",
				"central projection on tangent cylinder",
				"equally spaced parallels, equally spaced straight meridians, true scale on lat0",
				"parallels spaced stereographically on prime meridian, equally spaced straight meridians, true scale on lat0",
				"(homalographic) equal-area, hemisphere is a circle",
				"sphere conformally mapped on hemisphere and viewed orthographically",
				"",
				"equally spaced parallels, true distances from pole",
				"equal-area",
				"central projection on tangent plane, straight great circles",
				"viewed along earth's axis dist earth radii from center of earth",
				"viewed from infinity",
				"conformal, projected from opposite pole",
				"radius = tan(2 * colatitude) used in xray crystallography",
				"",
				"stereographic seen through medium with refractive index n",
				"radius = log(colatitude/r) map from viewing pedestal of radius r degrees",
				"central projection on cone tangent at lat0",
				"equally spaced parallels, true scale on lat0 and lat1",
				"conformal, true scale on lat0 and lat",
				"equal-area, true scale on lat0 and lat1",
				"equally spaced parallels, equal-area, parallel lat0 developed from tangent cone",
				"",
				"parallels developed from tangent cones, equally spaced along Prime Meridian",
				"equal-area projection of globe onto 2-to-1 ellipse, based on azequalarea",
				"conformal, maps whole sphere into a circle",
				"points plotted at true azimuth from two centers on the equator at longitudes +lon0 and -lon0",
				"points are plotted at true distance from two centers on the equator at longitudes +lon0 and -lon0",
				"hemisphere is circle, circular arc meridians equally spaced on equator, circular arc parallels equally spaced on 0- and 90-degree meridians",
				"sphere is circle, meridians as in globular, circular arc parallels resemble mercator",
				"conformal with no singularities, shaped like polyconic",
				"",
				"W and E hemispheres are square",
				"world is square with Poles at diagonally opposite corners",
				"map on tetrahedron with edge tangent to Prime Meridian at S Pole, unfolded into equilateral triangle",
				"world is hexagon centered on N Pole, N and S hemispheres are equilateral triangles",
				"",
				"oblique perspective from above the North Pole, dist earth radii from center of earth, looking along the Date Line angle degrees off vertical",
				"equally spaced parallels, straight meridians equally spaced along parallels, true scale at lat0 and lat1 on Prime Meridian",
				"conformal, polar cap above latitude lat maps to convex lune with given angle at 90E and 90W",
				"",
				"equally spaced vertical meridians",
				"distances to Mecca are true"
				});
		c.params.add(pc);
		
		pn = new ParamNumeric();
		pn.setName("lat0");
		pn.setTitle("lat0");
		pn.setViewType(Param.VIEW_ENTER);
		c.params.add(pn);
		
		pn = new ParamNumeric();
		pn.setName("lat1");
		pn.setTitle("lat2");
		pn.setViewType(Param.VIEW_ENTER);
		c.params.add(pn);
		
		pn = new ParamNumeric();
		pn.setName("dist");
		pn.setTitle("dist");
		pn.setViewType(Param.VIEW_ENTER);
		c.params.add(pn);
		
		pn = new ParamNumeric();
		pn.setName("n");
		pn.setTitle("n");
		pn.setViewType(Param.VIEW_ENTER);
		c.params.add(pn);
		
		pn = new ParamNumeric();
		pn.setName("r");
		pn.setTitle("r");
		pn.setViewType(Param.VIEW_ENTER);
		c.params.add(pn);
		
		pn = new ParamNumeric();
		pn.setName("angle");
		pn.setTitle("angle");
		pn.setViewType(Param.VIEW_ENTER);
		c.params.add(pn);
		
		pn = new ParamNumeric();
		pn.setName("lat");
		pn.setTitle("lat");
		pn.setViewType(Param.VIEW_ENTER);
		c.params.add(pn);
		return c;
	}
	
	public static Coord makePolar(){
		Coord c = new Coord();
		c.setName("coord_polar");
		Param p;
		ParamNumeric pn;
		ParamCharacter pc;
		
		pc = new ParamCharacter();
		pc.setName("theta");
		pc.setTitle("Angle is");
		pc.setViewType(Param.VIEW_COMBO);
		pc.setValue("x");
		pc.setDefaultValue("x");
		pc.setOptions(new String[]{"x","y"});
		c.params.add(pc);
		
		pn = new ParamNumeric();
		pn.setName("start");
		pn.setTitle("Offset from 12 o'clock in radians");
		pn.setViewType(Param.VIEW_ENTER);
		pn.setValue(new Double(0));
		pn.setDefaultValue(new Double(0));
		c.params.add(pn);
		
		pn = new ParamNumeric();
		pn.setName("direction");
		pn.setTitle("Direction");
		pn.setViewType(Param.VIEW_COMBO);
		pn.setValue(new Double(1));
		pn.setDefaultValue(new Double(1));
		pn.setOptions(new String[] {"1","-1"});
		pn.setLabels(new String[] {"clockwise","counter clockwise"});
		c.params.add(pn);
		
		p = new ParamLogical();
		p.setName("expand");
		p.setTitle("Expand axis");
		p.setValue(new Boolean(false));
		p.setDefaultValue(new Boolean(false));
		c.params.add(p);
		
		
		return c;
	}
	
	public static Coord makeTrans(){
		Coord c = new Coord();
		c.setName("coord_trans");
		ParamCharacter pc;
		
		pc = new ParamCharacter();
		pc.setName("xtrans");
		pc.setTitle("x-axis transformation");
		pc.setViewType(Param.VIEW_COMBO);
		pc.setValue("identity");
		pc.setDefaultValue("identity");
		pc.setOptions(new String[] {"asn","exp","identity","log","log10","probit","recip","reverse","sqrt"});
		c.params.add(pc);
		
		pc = new ParamCharacter();
		pc.setName("ytrans");
		pc.setTitle("y-axis transformation");
		pc.setViewType(Param.VIEW_COMBO);
		pc.setValue("identity");
		pc.setDefaultValue("identity");
		pc.setOptions(new String[] {"asn","exp","identity","log","log10","probit","recip","reverse","sqrt"});
		c.params.add(pc);
		
		return c;
	}
	
	
	public static Coord makeCoord(String coord){
		if(coord.equals("cartesian"))
			return makeCartesian();
		else if(coord.equals("equal"))
			return makeEqual();		
		else if(coord.equals("flip"))
			return makeFlip();	
		else if(coord.equals("map"))
			return makeMap();	
		else if(coord.equals("polar"))
			return makePolar();	
		else if(coord.equals("trans"))
			return makeTrans();	
		return null;
	}
	
	
	public Object clone(){
		Coord c = new Coord();
		for(int i=0;i<params.size();i++)
			c.params.add(((Param)params.get(i)).clone());
		c.setName(name);
		return c;
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
		return new DefaultElementView(this);
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

	public Element toXML() {
		try{
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element node = doc.createElement("ElementModel");
			node.setAttribute("className", "org.rosuda.deducer.plots.Coord");
			if(name!=null)
				node.setAttribute("name", name);
			
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
		if(!cn.equals("org.rosuda.deducer.plots.Coord")){
			System.out.println("Error Coord: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		if(node.hasAttribute("name"))
			name = node.getAttribute("name");
		else 
			name = null;
		params = new Vector();
		NodeList nl = node.getElementsByTagName("Param");
		for(int i=0;i<nl.getLength();i++){
			Element n = (Element) nl.item(i);
			cn = n.getAttribute("className");
			Param p = Param.makeParam(cn);
			p.setFromXML(n);
			params.add(p);
		}
	}

	public String getData() {
		return null;
	}	
	
	
	
	
	
	
}
