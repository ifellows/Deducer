package org.rosuda.deducer.widgets.event;



import javax.swing.JOptionPane;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPLanguage;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REXPReference;
import org.rosuda.REngine.REXPString;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.RList;
import org.rosuda.deducer.Deducer;
import org.rosuda.REngine.JRI.JRIEngine;

public abstract class RListener {

	private REXPReference fn;
	
	
	/**
	 * get callback function
	 * @return
	 */
	public REXPReference getFunction(){return fn;}
	
	/**
	 * Set callback function
	 * @param function the function to call upon action. should take two parameters. the first is a string representing the 
	 * type of event, the second is a java object representing the event
	 */
	public void setFunction(REXPReference function) {fn = function;}
	
	protected void eventOccured(Object event, String type){
		if(fn==null){
			JOptionPane.showMessageDialog(null, "No R function set for listener");
			return;
		}
		
		REXPReference actRef=null;
		try {
			actRef = ((JRIEngine) Deducer.getREngine()).createRJavaRef(event);
		} catch (REngineException e1) {
			e1.printStackTrace();
		}
		if(actRef==null){
			JOptionPane.showMessageDialog(null, "error creating RJavaReference");
			return;
		}
		
		try {
			fn.getEngine().eval(new REXPLanguage(new RList(new REXP[] { fn , new REXPString(type), actRef
			})), null, false);
		} catch (REngineException e) {
			e.printStackTrace();
		} catch (REXPMismatchException e) {
			e.printStackTrace();
		}
	}
}
