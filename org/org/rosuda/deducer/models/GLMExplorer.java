package org.rosuda.deducer.models;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPLogical;
import org.rosuda.REngine.REXPString;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.WindowTracker;
import org.rosuda.deducer.toolkit.AssumptionIcon;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.IconButton;

public class GLMExplorer extends ModelExplorer implements WindowListener{
	
	protected GLMModel model = new GLMModel();
	protected RModel pre;
	protected ModelPlotPanel diagnosticTab;
	protected ModelPlotPanel termTab;
	protected ModelPlotPanel addedTab;
	protected IconButton assumpHomo;
	protected IconButton assumpFunc;
	protected IconButton assumpN;
	
	GLMExplorer(GLMModel mod){
		super();
		this.setTitle("Generalized Linear Model Explorer");
		help.setUrl(HelpButton.baseUrl + "pmwiki.php?n=Main.GeneralizedLinearModel");
		final GLMModel m = mod;
		new Thread(new Runnable(){

			public void run() {
				setModel(m);
				initTabs();				
			}
			
		}).start();

		initAssumptions();
		this.addWindowListener(this);
	}
	
	public void initTabs(){

		String call="par(mfrow = c(2, 3),mar=c(5,4,2,2))\n"+
		"hist(resid("+pre.modelName+"),main=\"Residual\",xlab=\"Residuals\")\n"+
		"plot("+pre.modelName+",2,sub.caption=\"\")\n"+
		"plot("+pre.modelName+", c(1,4,3,5),sub.caption=\"\")";
		diagnosticTab = new ModelPlotPanel(call);
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run() {
					tabs.addTab("Diagnostics", diagnosticTab);
				}
				
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		REXP result = Deducer.timedEval("colnames(model.matrix("+pre.modelName+"))");
		if(result==null || ((REXPString)result).length()>9)
			return;
		
		call="par(mar=c(5,4,2,2))\n"+
			"try(crPlots("+pre.modelName+",ask=FALSE,col=1),silent=TRUE)";
		termTab = new ModelPlotPanel(call);
		boolean st = false;
		try{
			st = ((REXPLogical)Deducer.timedEval(
				"length(grep(\":\",c(attr(terms("+
				pre.modelName+"),\"term.labels\"))))==0")).isTRUE()[0];
		}catch(Exception e){}
		if(st){
			try {
				SwingUtilities.invokeAndWait(new Runnable(){
					public void run() {
						tabs.addTab("Terms", termTab);
					}
					
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
		}
		
		call="par(mar=c(5,4,2,2))\n"+
		"try(avPlots("+pre.modelName+",ask=FALSE,col=1),silent=TRUE)";
		addedTab = new ModelPlotPanel(call);
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run() {
					tabs.addTab("Added Variable", addedTab);
				}
				
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}


	}
	
	protected void initAssumptions(){
		{
			assumpN =  new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
			topPanel.add(assumpN);
			assumpN.setBounds(12, 8, 27, 27);
		}
		{
			assumpFunc = new AssumptionIcon("/icons/func_assump.png","Correct Functional Form",
					null,"Correct Functional Form");
			topPanel.add(assumpFunc);
			assumpFunc.setBounds(44, 8, 27, 27);
		}
		{
			assumpHomo = new AssumptionIcon("/icons/outlier_assump.png","No Outliers",null,"No Outliers");
			topPanel.add(assumpHomo);
			assumpHomo.setBounds(76, 8, 27, 27);
		}
	}
	
	public void setModel(GLMModel mod){
		model = mod;
		pre =model.run(true,pre);

			SwingUtilities.invokeLater(new Runnable(){

				public void run() {
					modelFormula.setText(pre.formula);
					preview.setText(pre.preview);
					preview.setCaretPosition(0);
				}
				
			});


	}
	
	
	public void run(){
		model.run(false,pre);
		this.dispose();
		GLMDialog.setLastModel(model);
		Deducer.timedEval("suppressWarnings(rm('"+pre.data.split("\\$")[1]+"','"+
				pre.modelName.split("\\$")[1]+"',envir="+Deducer.guiEnv+"))");
	}
	
	public void cancel(){
		Deducer.timedEval("suppressWarnings(rm('"+pre.data.split("\\$")[1]+"','"+
				pre.modelName.split("\\$")[1]+"',envir="+Deducer.guiEnv+"))");
		this.dispose();
	}
	
	public void updateClicked(){
		GLMBuilder bld = new GLMBuilder(model);
		bld.setLocationRelativeTo(this);
		bld.setVisible(true);
		WindowTracker.addWindow(bld);
		this.dispose();

	}
	
	public void optionsClicked(){
		GLMExplorerOptions opt = new GLMExplorerOptions(this,model);
		opt.setLocationRelativeTo(this);
		opt.setVisible(true);
		setModel(model);
	}
	
	public void postHocClicked(){
		GLMExplorerPostHoc post = new GLMExplorerPostHoc(this,model,pre);
		post.setLocationRelativeTo(this);
		post.setVisible(true);
		setModel(model);
	}
	public void exportClicked(){
		GLMExplorerExport exp = new GLMExplorerExport(this,model);
		exp.setLocationRelativeTo(this);
		exp.setVisible(true);
	}
	
	public void meansClicked(){
		GLMExplorerMeans m = new GLMExplorerMeans(this,model,pre);
		m.setLocationRelativeTo(this);
		m.setVisible(true);
		setModel(model);		
	}
	
	public void plotsClicked(){
		GLMExplorerPlots p = new GLMExplorerPlots(this,model,pre);
		p.setLocationRelativeTo(this);
		p.setVisible(true);
		setModel(model);	
	}
	
	public void testsClicked(){
		
		String[] s = new String[]{};
		try{
			s =Deducer.timedEval("names(coef("+pre.modelName+
					"))").asStrings();
		}catch(Exception e){
			e.printStackTrace();
		}
		Vector trms = new Vector();
		for(int i=0;i<s.length;i++)
			trms.add(s[i]);
		
		GLMExplorerTests p = new GLMExplorerTests(this,trms,model);
		p.setLocationRelativeTo(this);
		p.setVisible(true);
		setModel(model);			
	}

	public void windowActivated(WindowEvent arg0) {}

	public void windowClosed(WindowEvent arg0) {
		if(diagnosticTab!=null)
			diagnosticTab.executeDevOff();
		if(termTab!=null)
			termTab.executeDevOff();
		if(addedTab!=null)
			addedTab.executeDevOff();
	}

	public void windowClosing(WindowEvent arg0) {}

	public void windowDeactivated(WindowEvent arg0) {}

	public void windowDeiconified(WindowEvent arg0) {}

	public void windowIconified(WindowEvent arg0) {}

	public void windowOpened(WindowEvent arg0) {}
}
