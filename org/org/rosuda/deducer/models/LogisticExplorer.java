package org.rosuda.deducer.models;

import javax.swing.JOptionPane;

import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.REngine.REXPLogical;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.WindowTracker;
import org.rosuda.deducer.toolkit.HelpButton;

public class LogisticExplorer extends GLMExplorer {

	LogisticExplorer(GLMModel mod) {
		super(mod);
		this.setTitle("Logistic Model Explorer");
		help.setUrl(HelpButton.baseUrl + "pmwiki.php?n=Main.LogisticModel");
	}
/*
	public void initTabs(){
		try{
			String call="par(mfrow = c(2, 2),mar=c(5,4,2,2))\n"+
				"hist(resid("+pre.modelName+"),main=\"Residual\",xlab=\"Residuals\")\n"+
				"plot("+pre.modelName+",2,sub.caption=\"\")\n"+
				"plot("+pre.modelName+", c(4,5),sub.caption=\"\")";
			diagnosticTab = new ModelPlotPanel(call);
			tabs.addTab("Diagnostics", diagnosticTab);
			
			call="par(mar=c(5,4,2,2))\n"+
					"try(crPlots("+pre.modelName+",ask=FALSE,col=1),silent=TRUE)";
			termTab = new ModelPlotPanel(call);
			try{
				if(((REXPLogical)Deducer.timedEval("length(grep(\":\",c(attr(terms("+pre.modelName+"),\"term.labels\"))))==0")).isTRUE()[0])
					tabs.addTab("Terms", termTab);
			}catch(Exception e){};
			call="par(mar=c(5,4,2,2))\n"+
					"try(avPlots("+pre.modelName+",ask=FALSE,col=1),silent=TRUE)";
			addedTab = new ModelPlotPanel(call);
			tabs.addTab("Added Variable", addedTab);
		}catch(Exception e){
			new ErrorMsg(e);
		}
	}
*/
	public void updateClicked(){
		LogisticBuilder bld = new LogisticBuilder(model);
		bld.setLocationRelativeTo(this);
		bld.setVisible(true);		
		WindowTracker.addWindow(bld);
		this.dispose();
	}
	public void plotsClicked(){
		String type = (String) JOptionPane.showInputDialog(plots, "Please select the type of plot", 
				"Plot Type", JOptionPane.QUESTION_MESSAGE, null, 
				new String[] { "Effect","ROC"}, "Effect");
		if(type==null)
			return;
		if(type.equals("Effect")){
			GLMExplorerPlots p = new GLMExplorerPlots(this,model,pre);
			p.setLocationRelativeTo(this);
			p.setVisible(true);
		}
		if(type.equals("ROC")){
			LogisticExplorerRoc r = new LogisticExplorerRoc(this,(LogisticModel)model);
			r.setLocationRelativeTo(plots);
			r.setVisible(true);
		}
		setModel(model);	
	}
	
	public void run(){
		model.run(false,pre);
		this.dispose();
		LogisticDialog.setLastModel(model);
		Deducer.timedEval("suppressWarnings(rm('"+pre.data.split("\\$")[1]+"','"+pre.modelName.split("\\$")[1]+"',envir="+Deducer.guiEnv+"))");
	}
}
