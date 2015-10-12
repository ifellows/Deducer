package org.rosuda.deducer.plots;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PlottingElement implements Transferable{
	
	private ElementModel model;
	
	
	private ImageIcon icon;
	private String iconUrl;
	
	private String name;
	private String type;
	private String helpUrl = "";
	
	private boolean active = true;
	private boolean compound = false; //can break out into other elements
	
	public static DataFlavor DATAFLAVOR = new DataFlavor(PlottingElement.class,"Plotting element");
	
	public PlottingElement(){}
	
	public PlottingElement(String filename,String elementType,String elementName){
		super();
		URL url = getClass().getResource(filename);
		iconUrl = filename;
		name=elementName;
		type = elementType;
		Layer l;
		if(type.equals("geom")){
			l = Layer.makeGeomLayer(name);
			model = l;			
			helpUrl = "http://had.co.nz/ggplot2/"+type+"_"+name+".html";
			if(url==null){
				iconUrl = "/icons/ggplot_icons/layer_default.png";
				url = getClass().getResource(iconUrl);
			}
			if(url!=null){
				icon = new ImageIcon(url);	
			}
		}else if(type.equals("stat")){
			l = Layer.makeStatLayer(name);
			model = l;
			helpUrl = "http://had.co.nz/ggplot2/"+type+"_"+name+".html";
			if(url==null){
				iconUrl = "/icons/ggplot_icons/layer_default.png";
				url = getClass().getResource(iconUrl);
			}
			if(url!=null){
				icon = new ImageIcon(url);	
			}
		}else if(type.equals("scale")){
			String[] s = name.split("_");
			if(s.length>1){
				model = Scale.makeScale(s[0], s[1]);
				helpUrl = "http://had.co.nz/ggplot2/"+type+"_"+s[1]+".html";
			}else {
				model = Scale.makeScale(null, s[0]);
				helpUrl = "http://had.co.nz/ggplot2/"+type+"_"+s[0]+".html";
			}
			if(url==null){
				iconUrl = "/icons/ggplot_icons/scale_default.png";
				url = getClass().getResource(iconUrl);
			}
			if(url!=null){
				icon = new ImageIcon(url);	
			}
		}else if(type.equals("coord")){
			Coord c = Coord.makeCoord(name);
			model = c;
			helpUrl = "http://had.co.nz/ggplot2/"+type+"_"+name+".html";
			if(url==null){
				iconUrl = "/icons/ggplot_icons/default.png";
				url = getClass().getResource(iconUrl);
			}
			if(url!=null){
				icon = new ImageIcon(url);	
			}
		}else if(type.equals("facet")){
			Facet f = Facet.makeFacet(name);
			model = f;
			helpUrl = "http://had.co.nz/ggplot2/"+type+"_"+name+".html";
			if(url==null){
				iconUrl = "/icons/ggplot_icons/default.png";
				url = getClass().getResource(iconUrl);
			}
			if(url!=null){
				icon = new ImageIcon(url);	
			}
		}else if(type.equals("theme")){
			Theme t = Theme.makeTheme(name);
			model = t;
			if(url==null){
				iconUrl = "/icons/ggplot_icons/default.png";
				url = getClass().getResource(iconUrl);
			}
			if(url!=null){
				icon = new ImageIcon(url);	
			}
		}else if(type.equals("template")){
			URL templateURL = getClass().getResource("/templates/" + name+".ggtmpl");
			helpUrl = "http://www.deducer.org/pmwiki/pmwiki.php?n=Main.Templates";
			if(templateURL!=null){
				this.setFromURL(templateURL);
			}
			if(url==null){
				iconUrl = "/icons/ggplot_icons/template_default.png";
				url = getClass().getResource(iconUrl);
			}
			if(url!=null){
				icon = new ImageIcon(url);	
			}
			
		}
		if(model == null){
			//JOptionPane.showMessageDialog(null, "invalid PlottingElement: " + name);
		}
	}
	
	public void setIconFromUrl(String url){
		URL loc = getClass().getResource(url);
		if(loc!=null){
			icon = new ImageIcon(loc);
			iconUrl = url;
		}
	}
	
	public Object clone(){
		PlottingElement p = new PlottingElement();
		p.model = (ElementModel) this.model.clone();
		p.icon = this.icon;
		p.name = this.name;
		p.type = this.type;
		p.helpUrl = this.helpUrl;
		p.iconUrl = this.iconUrl;
		return p;
	}
	
	public static PlottingElement createElement(String type,String name){
		String nm = name;
		String[] s = nm.split("_");
		if(s.length>1)
			nm = s[s.length-1];
		PlottingElement el;
		if(!type.equals("template"))
			el = new PlottingElement("/icons/ggplot_icons/"+type+"_"+nm+".png",
				type,name);
		else
			el = new PlottingElement("/icons/ggplot_icons/"+type+"_"+name+".png",
					type,name);
		return el;
	}
	
	public JPanel makeComponent(){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel iconLabel;
		if(active)
			iconLabel = new JLabel(icon);
		else{
			URL url = getClass().getResource("/icons/edit_remove_32.png");
			iconLabel = new JLabel(new ImageIcon(url));
		}
		iconLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel.add(iconLabel);
		JLabel label = new JLabel(type);
		label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		label.setFont(new Font("Dialog", Font.PLAIN, 8) );
		panel.add(label);
		String[] s = name.split("_");
		for(int i=0;i<s.length;i++){
			label = new JLabel(s[i]);
			label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			label.setFont(new Font("Dialog", Font.PLAIN, 12) );
			panel.add(label);			
		}
		if(!active)
		panel.setPreferredSize(new Dimension(80,70));
		panel.setBorder(new EtchedBorder());		
		return panel;
	}
	
	public Image getImage(){
		return icon.getImage();
	}
	
	public void setImage(Image i){
		icon = new ImageIcon(i);
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String n){
		name=n;
	}
	
	public JPanel getPanel(){
		return model.getView();
	}
	
	public ElementModel getModel(){
		return model;
	}
	
	public void setModel(ElementModel m){
		model = m;
	}
	
	public String getUrl(){
		return helpUrl;
	}
	
	public void setUrl(String url){
		helpUrl = url;
	}
	
	public boolean isActive(){return active;}
	public void setActive(boolean act){
		active = act;
	}
	
	public boolean isCompound(){return compound;}
	public void setCompound(boolean comp){compound = comp;}	

	public Object getTransferData(DataFlavor arg0)
			throws UnsupportedFlavorException, IOException {
		return this;
	}

	public DataFlavor[] getTransferDataFlavors() {
			DataFlavor[] f = new DataFlavor[] {new DataFlavor(PlottingElement.class,"Plot element")};
		return f;
	}

	public boolean isDataFlavorSupported(DataFlavor arg0) {
		return true;
	}
	
	public Element toXML(){
		try{
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			Element node = doc.createElement("PlottingElement");
			if(name!=null)
				node.setAttribute("name", name);
			if(type!=null)
				node.setAttribute("type", type);
			if(helpUrl!=null)
				node.setAttribute("helpUrl", helpUrl);
			if(iconUrl!=null)
				node.setAttribute("iconUrl", iconUrl);
			node.setAttribute("active", active ? "true" : "false");
			node.setAttribute("compound", compound ? "true" : "false");
			Element e = model.toXML();
			e = (Element) doc.importNode(e, true);
			node.appendChild(e);
			
			node.setAttribute("className", "org.rosuda.deducer.plots.PlottingElement");
			doc.appendChild(node);
			return node;
			
        }catch(Exception e){e.printStackTrace();return null;}
	}
	
	public void setFromXML(Element node){
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.plots.PlottingElement")){
			System.out.println("Error Position: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		if(node.hasAttribute("name"))
			name = node.getAttribute("name");
		else
			name = null;
		
		if(node.hasAttribute("type"))
			type = node.getAttribute("type");
		else
			type = null;
		
		if(node.hasAttribute("helpUrl"))
			helpUrl = node.getAttribute("helpUrl");
		else
			helpUrl = null;
		
		if(node.hasAttribute("iconUrl"))
			iconUrl = node.getAttribute("iconUrl");
		else
			iconUrl = null;
		URL url;
		if(iconUrl==null)
			url = getClass().getResource("/icons/ggplot_icons/default.png");
		else
			url = getClass().getResource(iconUrl);
		if(url!=null)
			icon = new ImageIcon(url);
		Element e = (Element) node.getElementsByTagName("ElementModel").item(0);
		String className = e.getAttribute("className");
		try {
			ElementModel mod = (ElementModel) Class.forName(className).newInstance();
			mod.setFromXML(e);
			model = mod;
		} catch (Exception e1) {e1.printStackTrace();}
	}
	
	public void saveToFile(File f){
		
		
		Element e = this.toXML();
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
	
	public void setFromURL(URL url){
		try{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(url.openStream());
			Element e = (Element)doc.getChildNodes().item(0);
			this.setFromXML(e);
		}catch(Exception ex){ex.printStackTrace();}
	}
}

