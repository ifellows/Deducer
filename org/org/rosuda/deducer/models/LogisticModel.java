package org.rosuda.deducer.models;

import java.util.ArrayList;

public class LogisticModel extends GLMModel{
	public LogisticDialogSplitModel split=null;
	public PlotRoc roc = new PlotRoc();
	
	public LogisticModel(){
		super();
		family="binomial()";
	}
	
	protected String runPlots(String cmd,String modelName,boolean preview,ArrayList tmp,
			RModel prevModel){
		cmd = super.runPlots(cmd, modelName, preview, tmp, prevModel);
		
		if(roc.roc & !preview){
			cmd+= "\ndev.new()";
			cmd+= "\nrocplot("+modelName+
					(roc.diag ? "" : ",diag=FALSE")+
					(roc.predProbs ? ",pred.prob.labels=TRUE":"")+
					(roc.auc ? "" : ",auc=FALSE")+")";
		}
		
		return cmd;
	}
	
	
	public class PlotRoc{
		public boolean roc=false;
		public boolean diag = true;
		public boolean predProbs = false;
		public boolean auc = true;
	}	
}
