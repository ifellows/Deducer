package org.rosuda.deducer.plots;

import org.rosuda.deducer.Deducer;
import org.rosuda.javaGD.GDContainer;
import org.rosuda.javaGD.GDInterface;

public class DeviceInterface extends GDInterface{
	private static GDContainer cont;
	int devNr = -1;
	
    /** requests a new device of the specified size
     *  @param w width of the device
     *  @param h height of the device */
    public void     gdOpen(double w, double h) {
        open=true;
        c=cont;
    }

    /** create a new, blank page 
     *  @param devNr device number assigned to this device by R */
    public void     gdNewPage(int devNr) { // new API: provides the device Nr.
        this.devNr=devNr;
        if (c!=null) {
            c.reset();
            c.setDeviceNumber(devNr);
        }
        //System.out.println("new page called : " + devNr + " = dev number");
    }
	
	public static void plot(String call,PlotBuilder.PlotPanel pl){
		try{
			cont = pl;
			Deducer.timedEval("Sys.setenv(\"JAVAGD_CLASS_NAME\"=\"org/rosuda/deducer/plots/DeviceInterface\")");
			if(pl.devNr==-1){
				Deducer.timedEval("JavaGD()");
			}else{
				Deducer.timedEval("dev.set(" + (pl.devNr+1) + ")");
			}
			
			String cmd = "";
			cmd = call.replace('\n', ' ');
			cmd = cmd.replace('\t', ' ');
			//System.out.println(cmd);
			Deducer.timedEval("print(" + cmd +")");
			Deducer.timedEval("Sys.setenv(\"JAVAGD_CLASS_NAME\"=\"org/rosuda/JGR/toolkit/JavaGD\")");
		}catch(Exception e){e.printStackTrace();}
		
	}

}
