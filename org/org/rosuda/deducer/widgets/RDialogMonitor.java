package org.rosuda.deducer.widgets;

import java.awt.Dialog;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPLanguage;
import org.rosuda.REngine.REXPReference;
import org.rosuda.REngine.RList;

/**
 * Calls an R function at periodic intervals while the dialog is active. Designed to work with RDialog (started with RDialog.run().
 * If it is used with other dialogs, it assumes that if the dialog is visible
 * that the REPL is blocked. Otherwise this will cause problems on non-JGR consoles because R is not thread safe.
 * @author Ian
 *
 */
public class RDialogMonitor {

	private REXPReference fn = null;
	private boolean isActive = true;
	private boolean started =false;
	private Dialog dialog;
	private int rate = 2000;
	
	public RDialogMonitor(Dialog d){
		dialog = d;
	}
	
	public RDialogMonitor(Dialog d, int monitorRateMS){
		dialog = d;
		rate = monitorRateMS;
	}
	
	/**
	 * get callback function
	 * @return
	 */
	public REXPReference getFunction(){return fn;}
	
	/**
	 * Set callback function
	 * @param function the function to call upon action. should take no parameters.
	 */
	public void setFunction(REXPReference function) {fn = function;}
	
	/**
	 * Start the monitor
	 */
	public void start(){
		if(!started)
			new Thread(new Run()).start();
	}
	
	/**
	 * Stop the monitor
	 */
	public void stop(){
		isActive=false;
		started=false;
	}
	
	private class Run implements Runnable{

		public void run() {
			while(isActive){
				if(dialog!=null && dialog.isDisplayable() && dialog.isVisible()){
					try {
						if(fn!=null)
							fn.getEngine().eval(new REXPLanguage(new RList(new REXP[] { fn })), null, false);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
				try {
					Thread.sleep(rate);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
