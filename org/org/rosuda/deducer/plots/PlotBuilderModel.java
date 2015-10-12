package org.rosuda.deducer.plots;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.plots.Template.MaskingAes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class PlotBuilderModel {

	private DefaultListModel listModel = new DefaultListModel();
	
	public DefaultListModel getListModel(){
		return listModel;
	}
	
	public void setListModel(DefaultListModel m){
		listModel = m;
	}
	
	public Object clone(){
		PlotBuilderModel b = new PlotBuilderModel();
		for(int i=0;i<listModel.getSize();i++){
			PlottingElement e = (PlottingElement) listModel.get(i);
			b.listModel.addElement(e.clone());
		}
		return b;
	}
	
	public boolean isValidAddition(ElementModel em){
		return true;
	}
	
	public String getCall(){
		String cmd ="";
		cmd+="ggplot()";
		boolean hasLayerOrTemplate = false;
		for(int i=0;i<listModel.getSize();i++){
			PlottingElement e = (PlottingElement) listModel.get(i);
			if(e.getModel().getType().equals("layer") ||
					e.getModel().getType().equals("template"))
				hasLayerOrTemplate=true;
		}
		if(!hasLayerOrTemplate)
			return null;
		for(int i=0;i<listModel.getSize();i++){
			PlottingElement e = (PlottingElement) listModel.get(i);
			if(e.isActive()){
				String addition = e.getModel().getCall();
				if(addition!=null && addition.length()>0 && !addition.equals("null"))
					cmd += " +\n\t" + addition;
			}
		}
		return cmd;
	}
	
	
	
	public void tryToFillRequiredAess(Layer l){
		Vector aess = l.aess;
		for(int j=0;j<aess.size();j++){
			Aes aes = (Aes) aess.get(j);
			if(!aes.required)
				continue;
			if(!(aes.variable==null || aes.variable.length()==0) || aes.value!=null)
				continue;
			for(int i=listModel.size()-1;i>=0;i--){
				if(aes.variable!=null && aes.variable.length()>0)
					continue;
				PlottingElement e = (PlottingElement) listModel.get(i);
				ElementModel em = e.getModel();
				if(em instanceof Layer){
					Vector laess = ((Layer)em).aess;
					if(l.data != null && !l.data.equals(((Layer)em).data))
						continue;
					for(int k=0;k<laess.size();k++){
						Aes laes = (Aes) laess.get(k);
						if(laes.name.equals(aes.name) && laes.variable!=null && laes.variable.length()>0
								&& !laes.variable.startsWith("..")){
							aes.variable = laes.variable;
							l.data = ((Layer)em).data;
						}
					}
				}
			}
		}
	}
	
	public String[] getAes(){
		TreeSet aess = new TreeSet();
		for(int i=0;i<listModel.size();i++){
			PlottingElement e = (PlottingElement) listModel.get(i);
			ElementModel em = e.getModel();
			if(em instanceof Layer){
				Vector laess = ((Layer)em).aess;
				for(int k=0;k<laess.size();k++){
					Aes laes = (Aes) laess.get(k);
					if(laes.variable!=null && laes.variable.length()>0)
						aess.add(laes.name);
				}
			}else if(em instanceof Template){
				Vector els = ((Template)em).getMAess();
				for(int k=0;k<els.size();k++){
					MaskingAes maes = (MaskingAes) els.get(k);
					aess.add(maes.name);
				}
			}
		}
		Object[] a = aess.toArray();;
		String[] tmp = new String[a.length];
		for(int i = 0;i<a.length;i++){
			tmp[i] = (String) a[i];
			//System.out.println(tmp[i]);
		}
		return tmp;
	}
	
	
	public Element toXML(){
		try{
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			Element node = doc.createElement("PlottingBuilderModel");
			for(int i=0;i<listModel.getSize();i++){
				Element e = ((PlottingElement)listModel.get(i)).toXML();
				e = (Element) doc.importNode(e, true);
				node.appendChild(e);
			}
			node.setAttribute("className", "org.rosuda.deducer.plots.PlotBuilderModel");
			doc.appendChild(node);
			return node;
			
        }catch(Exception e){e.printStackTrace();return null;}
	}
	
	public void setFromXML(Element node){
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.plots.PlotBuilderModel")){
			System.out.println("Error PlotBuilderModel: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		try{
			listModel = new DefaultListModel();
			NodeList nl = node.getChildNodes();// .getElementsByTagName("PlottingElement");
			for(int i=0;i<nl.getLength();i++){
				if(!(nl.item(i) instanceof Element))
					continue;
				Element e = (Element) nl.item(i);
				String className = e.getAttribute("className");
				PlottingElement pe = (PlottingElement) Class.forName(className).newInstance();
				pe.setFromXML(e);
				listModel.addElement(pe);
			}
		} catch (Exception e1) {e1.printStackTrace();}
	}
	
	public void saveToFile(File f,boolean withData){
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
			
			String dir = f.getParent();
			//System.out.println("dir="+dir);
			
			File tmpDir = new File(dir , "tmp_plot_dir");
			tmpDir.mkdir();
			File plotFile = new File(tmpDir.getPath() , "plot.xml");
			//plotFile.mkdirs();
			//System.out.println(plotFile.getPath() + "  " + plotFile.exists());
			
			FileOutputStream fos = new FileOutputStream(plotFile);
			OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8"); 
			out.write(xmlString);
			
			out.close();
			fos.close();
			sw.close();			
			
			if(withData){
				Vector dataFrames = new Vector();
				for(int i=0;i<listModel.size();i++){
					PlottingElement el = (PlottingElement) listModel.get(i);
					ElementModel em = el.getModel();
					if(em.getData()!=null && !dataFrames.contains(em.getData()))
						dataFrames.add(em.getData());
				}
				if(dataFrames.size()>0){
					String dat = Deducer.makeRCollection(dataFrames,"c",true);
					String fileCall = tmpDir.getAbsolutePath() + "/" + "data.RData";
					if(System.getProperties().getProperty( "file.separator" ).equals("\""))
						fileCall.replace('\\','/');
					String call = "save(list="+dat+",file=\""+Deducer.addSlashes(fileCall)+"\")";
					//System.out.println(call);
					Deducer.timedEval(call);
				}
			}
			
			byte[] buffer = new byte[18024];
			FileOutputStream fos1 = new FileOutputStream(f);
			ZipOutputStream zout = new ZipOutputStream(fos1);
			File[] filesToZip = tmpDir.listFiles();
			for(int i=0;i<filesToZip.length;i++){
				FileInputStream in = new FileInputStream(filesToZip[i]);
				zout.putNextEntry(new ZipEntry(filesToZip[i].getName()));
				int len;
		        while ((len = in.read(buffer)) > 0){
		        	zout.write(buffer, 0, len);
		        }
		        zout.closeEntry();
		        in.close();		        
		        filesToZip[i].delete();
			}
			zout.close();				
			fos1.close();	
			tmpDir.delete();

			//FileOutputStream fos = new FileOutputStream(f);
			//OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8"); 
			//out.write(xmlString);
			

			
		}catch(Exception ex){ex.printStackTrace();}
	}
	
	public void setFromFile(File f){
		try{
			int BUFFER = 2048;
			String dataName = "data.RData";
			String plotName = "plot.xml";		
			
			String dir = f.getParent();
			//System.out.println("dir="+dir);
			
			File tmpDir = new File(dir , "tmp_plot_dir");
			tmpDir.mkdir();
			
			File plotFile = new File(tmpDir.getPath() , plotName);
			
			File dataFile = new File(tmpDir.getPath() , dataName);
			
			ZipFile zipFile = new ZipFile(f, ZipFile.OPEN_READ);
			
			ZipEntry entry = zipFile.getEntry(dataName);
			
			if(entry!=null){
				BufferedInputStream is =
					new BufferedInputStream(zipFile.getInputStream(entry));
				int currentByte;
				// establish buffer for writing file
				byte data[] = new byte[BUFFER];
				
				// write the current file to disk
				FileOutputStream fos = new FileOutputStream(dataFile);
				BufferedOutputStream dest =new BufferedOutputStream(fos, BUFFER);
				
				// read and write until last byte is encountered
				while ((currentByte = is.read(data, 0, BUFFER)) != -1){
					dest.write(data, 0, currentByte);
				}
				dest.flush();
				dest.close();
				is.close();
				fos.close();
				String fileCall = dataFile.getAbsolutePath();
				if(System.getProperties().getProperty( "file.separator" ).equals("\""))
					fileCall.replace('\\','/');
				String call = "load(\"" +Deducer.addSlashes(fileCall) + "\"" +")";
				System.out.println("\nNote: loading data from " + f.getName() + "\n");
				Deducer.timedEval(call);
				dataFile.delete();
			}
			
			entry = zipFile.getEntry(plotName);
			
			BufferedInputStream is = new BufferedInputStream(zipFile.getInputStream(entry));
			// establish buffer for writing file
			byte[] data = new byte[BUFFER];
			int currentByte;
			// write the current file to disk
			FileOutputStream fos = new FileOutputStream(plotFile);
			BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
			
			// read and write until last byte is encountered
			while ((currentByte = is.read(data, 0, BUFFER)) != -1){
				dest.write(data, 0, currentByte);
			}
			dest.flush();
			dest.close();
			is.close();
			fos.close();
			
			
			listModel.removeAllElements();
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(plotFile);
			Element e = (Element)doc.getChildNodes().item(0);
			this.setFromXML(e);
			plotFile.delete();
			tmpDir.delete();
			
		}catch(Exception ex){ex.printStackTrace();}
	}
	
}
