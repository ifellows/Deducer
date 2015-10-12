package org.rosuda.deducer.widgets;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPLanguage;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REXPReference;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.RList;
import org.rosuda.deducer.Deducer;

/**
 * An easy dialog designed to be called from R
 * @author Ian
 *
 */
public class SimpleRDialog extends RDialog implements ActionListener{
	
	private String rCheckFunc;
	private String rRunFunc;

	private REXPReference checkRef;
	private REXPReference runRef;
	
	/**
	 * new simple dialog
	 */
	public SimpleRDialog(){
		super();
		setOkayCancel(true,true,this);
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * create new dialog with callbacks
	 * @param rCheckFunction name of R function called to check dialog validity
	 * @param rRunFunction name of R function called to run analysis
	 */
	public SimpleRDialog(String rCheckFunction, String rRunFunction){
		super();
		rCheckFunc = rCheckFunction;
		rRunFunc = rRunFunction;
		checkRef = null;
		runRef = null;
		setOkayCancel(true,true,this);
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * create new dialog with callbacks
	 * @param rCheckFunction R function called to check dialog validity (e.g. toJava(function(state) '') )
	 * @param rRunFunction R function called to run analysis
	 */
	public SimpleRDialog(REXPReference rCheckFunction, REXPReference rRunFunction){
		super();
		rCheckFunc = null;
		rRunFunc = null;
		checkRef = rCheckFunction;
		runRef = rRunFunction;
		setOkayCancel(true,true,this);
		this.setLocationRelativeTo(null);
	}

	/**
	 * Set validity check callback
	 * @param func
	 */
	public void setCheckFunction(String func){
		rCheckFunc = func;
		checkRef=null;
	}

	/**
	 * Set validity check callback
	 * @param func
	 */
	public void setCheckFunction(REXPReference func){
		checkRef=func;
		rCheckFunc=null;
	}
	
	/**
	 * get validity check callback
	 * @return either a string or an REXPReference to a function
	 */
	public Object getCheckFunction(){
		if(rCheckFunc!=null)
			return rCheckFunc;
		return checkRef;
	}
	
	/**
	 * set run callback
	 * @param func
	 */
	public void setRunFunction(String func){
		rRunFunc = func;
		runRef = null;
	}
	
	/**
	 * set run callback
	 * @param func
	 */
	public void setRunFunction(REXPReference func){
		rRunFunc = null;
		runRef = func;
	}
	
	/**
	 * get run callback
	 * @return either a string or an REXPReference to a function
	 */
	public Object getRunFunction(){
		if(rRunFunc!=null)
			return rRunFunc;
		return runRef;		
	}

	public void actionPerformed(ActionEvent e) {
		final String cmd = e.getActionCommand();
		new Thread(new Runnable(){

			public void run() {
				runCmd(cmd);
			}
			
		}).start();
	}
	
	void runCmd(String cmd){
		if(cmd.equals("Run") || cmd.equals("OK")){
			String state = getWidgetStatesAsString();
			REXP rCheck = null;
			if(rCheckFunc!=null)
				rCheck = Deducer.timedEval(rCheckFunc + "(" + state + ")");
			else if(checkRef!=null){
				REXP st = Deducer.timedEval(state);
				try {
					rCheck = checkRef.getEngine().eval(new REXPLanguage(new RList(new REXP[] { checkRef , st
					})), null, false);
				} catch (REngineException e1) {
					e1.printStackTrace();
				} catch (REXPMismatchException e1) {
					e1.printStackTrace();
				}
			}

			
			String check = "";
			try {
				if(rCheck!=null)
					check = rCheck.asString();
			} catch (REXPMismatchException e1) {
				JOptionPane.showMessageDialog(this, "Dialog error. Check function must return a string. Return" +
													" \"\" if the check passes.");
			}
			if(check.length()<1){
				
				try {
					//System.out.println(state);
					if(rRunFunc!=null)
						Deducer.timedEval(rRunFunc + "(" + state + ")");
					else if(runRef!=null){
						REXP st = Deducer.timedEval(state);
						runRef.getEngine().eval(new REXPLanguage(new RList(new REXP[] { runRef , st
						})), null, false);
					}
				} catch (REngineException e1) {
					e1.printStackTrace();
				} catch (REXPMismatchException e1) {
					e1.printStackTrace();
				}
				SwingUtilities.invokeLater(new Runnable(){
					public void run() {
						SimpleRDialog.this.setVisible(false);
					}
					
				});
				completed();
			}else{
				JOptionPane.showMessageDialog(this, check);
				return;
			}
		}else if(cmd.equals("Cancel")){
			clearWorkingModels();
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					SimpleRDialog.this.setVisible(false);
				}
				
			});
			
		}else if(cmd.equals("Reset"))
			reset();		
	}
	
}
