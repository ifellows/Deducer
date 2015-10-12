package org.rosuda.deducer;

import javax.swing.JPanel;

import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.javaGD.GDContainer;
import org.rosuda.javaGD.GDInterface;
import org.rosuda.javaGD.JGDBufferedPanel;

public class GDPreviewJPanel extends GDInterface{
	public JPanel p;
	public static JPanel mostRecent;
	public static Integer mostRecentDevNumber;
	
	public GDPreviewJPanel() {
        super();
    }
	public void gdOpen(double w, double h) {
		if (p!=null) gdClose();
		p=new JGDBufferedPanel(200,200);
		c=(GDContainer) p;
		mostRecent=p;
		
	}
	
	public void gdClose() {
		super.gdClose();
		if (p!=null) {
			c=null;
			p=null;
		}
    }
	
	public static void plot(String call){
		Deducer.timedEval("Sys.setenv(\"JAVAGD_CLASS_NAME\"=\"org/rosuda/deducer/GDPreviewJPanel\")");
		Deducer.timedEval("JavaGD()");
		try {
			mostRecentDevNumber = new Integer(Deducer.timedEval("as.integer(dev.cur())").asInteger());
		} catch (REXPMismatchException e) {
			new ErrorMsg(e);
		}
		
		String[] lines = call.split("\n");
		for(int i=0;i<lines.length;i++)
			Deducer.timedEval(lines[i]);
		Deducer.timedEval("Sys.setenv(\"JAVAGD_CLASS_NAME\"=\"org/rosuda/JGR/toolkit/JavaGD\")");
		
	}
	
}
