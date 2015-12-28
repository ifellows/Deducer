package org.rosuda.deducer.plots;

import java.util.LinkedHashMap;
import java.util.Map;

public class PlotController {

	public static boolean initialized = false;
	protected static String[] names = {"Templates","Geometric Elements",
		"Statistics","Scales","Facets","Coordinates","Other"};
	protected static Map templates;
	protected static Map geoms;
	protected static Map stats;
	protected static Map scales;
	protected static Map facets;
	protected static Map themes;
	protected static Map coords;
	protected static Map pos;
	protected static Map menuElements;
	protected static String[] templateNames = new String[]{
		"bar","grouped_bar","histogram","density","grouped_density",
		"simple_dotplot","grouped_dotplot","mean","line","grouped_line",
		"simple_boxplot","grouped_boxplot","scatter","scatter_smooth",
		"histogram_2d","bubble"};
	protected static String[] geomNames = {"abline","area","bar","bin2d","blank","boxplot","contour","crossbar","density","density_2d",
			"dotplot","errorbar","errorbarh","freqpoly","hex","histogram","hline","jitter","line","linerange",
			"path","point","pointrange","polygon","quantile","raster", "rect","ribbon","rug","segment","smooth","step","text",
			"tile","violin","vline"};
	protected static String[] statNames = {"bin","bin_2d","bindot","bin_hex","boxplot","contour", "count","density","density_2d","ecdf","function",
			"identity","qq","quantile","smooth","spoke","sum","summary","unique","ydensity"};
	protected static String[] scaleNames = {"area","size","size_manual","size_identity",
		"colour_gradient", "colour_hue","colour_brewer","colour_gradient2","colour_gradientn","colour_grey","colour_manual","colour_identity",
		"fill_gradient","fill_hue","fill_brewer","fill_gradient2","fill_gradientn","fill_grey","fill_manual","fill_identity",
		"x_continuous","x_discrete","x_date","x_datetime",
		"y_continuous","y_discrete","y_date","y_datetime",
		"z_continuous","z_discrete","z_date","z_datetime",
		"linetype","linetype_identity","linetype_manual",
		"shape","shape_identity","shape_manual",
		"alpha"};
	protected static String[] facetNames = {"grid","wrap"};
	protected static String[] coordNames = {"cartesian","equal","flip","map","polar","trans"};
	protected static String[] posNames = {"dodge","identity","jitter","stack","fill"};
	protected static String[] themeNames = { "grey", "dark", "bw","classic","minimal","theme","title","xlab","ylab"};	
	
	
	public static void init(){
		if(initialized==false){
			templates = new LinkedHashMap();
			menuElements = new LinkedHashMap();
			geoms = new LinkedHashMap();
			for(int j=0;j<geomNames.length;j++)
				geoms.put(geomNames[j], PlottingElement.createElement("geom",geomNames[j]));
			stats = new LinkedHashMap();
			for(int j=0;j<statNames.length;j++)
				stats.put(statNames[j], PlottingElement.createElement("stat",statNames[j]));			
			scales = new LinkedHashMap();
			for(int j=0;j<scaleNames.length;j++)
				scales.put(scaleNames[j], PlottingElement.createElement("scale",scaleNames[j]));
			facets = new LinkedHashMap();
			for(int j=0;j<facetNames.length;j++)
				facets.put(facetNames[j], PlottingElement.createElement("facet",facetNames[j]));
			coords = new LinkedHashMap();
			for(int j=0;j<coordNames.length;j++)
				coords.put(coordNames[j], PlottingElement.createElement("coord",coordNames[j]));
			themes = new LinkedHashMap();
			for(int j=0;j<themeNames.length;j++)
				themes.put(themeNames[j], PlottingElement.createElement("theme",themeNames[j]));
			for(int j=0;j<templateNames.length;j++)
				templates.put(templateNames[j], PlottingElement.createElement("template",templateNames[j]));
			pos = new LinkedHashMap();
			pos.put("identity", new Position("identity",null,null));
			pos.put("stack", new Position("stack",null,null));
			pos.put("dodge", new Position("dodge",null,null));
			pos.put("jitter", new Position("jitter",null,null));
			initialized = true;
		}
	}
	
	public static String[] getNames(){
		return names;
	}
	public static Map getGeoms(){
		return geoms;
	}
	public static String[] getGeomNames(){
		return geomNames;
	}
	public static Map getStats(){
		return stats;
	}
	public static String[] getStatNames(){
		return statNames;
	}
	public static Map getScales(){
		return scales;
	}
	public static String[] getScaleNames(){
		return scaleNames;
	}
	public static Map getFacets(){
		return facets;
	}
	public static String[] getFacetNames(){
		return facetNames;
	}
	public static Map getCoords(){
		return coords;
	}
	public static String[] getCoordNames(){
		return coordNames;
	}
	public static Map getPositions(){
		return pos;
	}
	public static String[] getPositionNames(){
		return posNames;
	}
	public static Map getThemes(){
		return themes;
	}
	public static String[] getThemeNames(){
		return themeNames;
	}
	
	public static Map getTemplates(){
		return templates;
	}
	public static String[] getTemplateNames(){
		return templateNames;
	}
	
	
	public static void addTemplate(PlottingElement pe){
		String[] nm = templateNames;
		int l = nm.length;
		String[] newNames = new String[l+1];
		for(int i=0;i<l;i++)
			newNames[i] = nm[i];
		newNames[l] = pe.getName();
		templates.put(pe.getName(), pe);
	}
	
	
	public static void addGeom(PlottingElement pe){
		String[] nm = geomNames;
		int l = nm.length;
		String[] newNames = new String[l+1];
		for(int i=0;i<l;i++)
			newNames[i] = nm[i];
		newNames[l] = pe.getName();
		geoms.put(pe.getName(), pe);
	}
	
	public static void addStat(PlottingElement pe){
		String[] nm = statNames;
		int l = nm.length;
		String[] newNames = new String[l+1];
		for(int i=0;i<l;i++)
			newNames[i] = nm[i];
		newNames[l] = pe.getName();
		stats.put(pe.getName(), pe);
	}
	
	public static void addScale(PlottingElement pe){
		String[] nm = scaleNames;
		int l = nm.length;
		String[] newNames = new String[l+1];
		for(int i=0;i<l;i++)
			newNames[i] = nm[i];
		newNames[l] = pe.getName();
		scales.put(pe.getName(), pe);
	}
	
	public static void addFacet(PlottingElement pe){
		String[] nm = facetNames;
		int l = nm.length;
		String[] newNames = new String[l+1];
		for(int i=0;i<l;i++)
			newNames[i] = nm[i];
		newNames[l] = pe.getName();
		facets.put(pe.getName(), pe);
	}
	
	public static void addCoord(PlottingElement pe){
		String[] nm = coordNames;
		int l = nm.length;
		String[] newNames = new String[l+1];
		for(int i=0;i<l;i++)
			newNames[i] = nm[i];
		newNames[l] = pe.getName();
		coords.put(pe.getName(), pe);
	}
	
	public static void addPosition(Position p){
		String[] nm = posNames;
		int l = nm.length;
		String[] newNames = new String[l+1];
		for(int i=0;i<l;i++)
			newNames[i] = nm[i];
		newNames[l] = p.name;
		pos.put(p.name, p);
	}
	
	public static void addTheme(PlottingElement pe){
		String[] nm = themeNames;
		int l = nm.length;
		String[] newNames = new String[l+1];
		for(int i=0;i<l;i++)
			newNames[i] = nm[i];
		newNames[l] = pe.getName();
		themes.put(pe.getName(), pe);
	}
	
	public static void addMenuElement(String name,PlottingElement pe){
		menuElements.put(name, pe);
	}
	
	public static PlottingElementMenuDialog getMenuDialog(String name){
		PlottingElement pe = (PlottingElement) menuElements.get(name);
		if(pe==null){
			pe = (PlottingElement) templates.get(name);
			addMenuElement(name,pe);
		}
		if(pe==null)
			return null;
		return new PlottingElementMenuDialog(null,pe);
	}
	
}
