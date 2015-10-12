package org.rosuda.deducer.menu.twosample;


import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import org.rosuda.JGR.JGR;
import org.rosuda.JGR.RController;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.menu.OneWayPlotModel;
import org.rosuda.deducer.menu.SubsetDialog;


public class TwoSampleModel{
	public String dataName=null;
	public DefaultListModel variables = new DefaultListModel();
	public DefaultListModel factorName = new DefaultListModel();
	public String subset = "";
	
	public boolean doT=true;
	public boolean doBoot=false;
	public boolean doKS=false;
	public boolean doMW=false;
	public boolean doBM=false;
	public boolean tEqVar=false;
	public String bootStat="t";
	
	public OptionsModel optMod = new OptionsModel();
	public SplitModel splitMod = new SplitModel();
	public OneWayPlotModel plots = new OneWayPlotModel();
	
	public class OptionsModel{
		public boolean descriptives = true;
		public String digits = "<auto>";
		public String alternative = "two.sided";
		public double confLevel=.95;
	}		
	
	public class SplitModel{
		public boolean isCut=false;
		public String cutPoint="";
		public Vector group1 = new Vector();
		public Vector group2 = new Vector();
		
		public String getFactorName(){
			if(factorName.size()>0)
				return (String) factorName.firstElement();
			else
				return null;
		}
		public String getDataName(){
			return dataName;
		}
		public String dich(String factorName){
			if(isCut){
				String cut;
				try{
					Double.parseDouble(splitMod.cutPoint);
					cut=splitMod.cutPoint;
				}catch(Exception e){
					cut="\""+splitMod.cutPoint+"\"";
				}
				return "dich("+factorName+",cut="+cut+")";
			}
			if(group1.size()!=0 || group2.size()!=0){
				
				return "dich("+factorName+
				(group1.size()>0 ? (",group1="+Deducer.makeRCollection(group1, "c", true)) : "")+
				(group2.size()>0 ? (",group2="+Deducer.makeRCollection(group2, "c", true)) : "")+")";
			}
			return factorName;
		}
		
	}
	
	public boolean run(){
		
		if(dataName==null)
			return false;
		if(variables.size()==0){
			JOptionPane.showMessageDialog(null, "Please select one or more outcome variables.");
			return false;
		}
		if(factorName.size()==0){
			JOptionPane.showMessageDialog(null, "Please select a factor.");
			return false;			
		}
		subset = subset.trim();
		String cmd="";
		String subn;
		String outcomes = Deducer.makeRCollection(variables, "d", false);
		String factor = splitMod.dich((String) factorName.get(0));
		
		
		
		boolean isSubset=false;
		if(!subset.equals("") ){
			if(!SubsetDialog.isValidSubsetExp(subset,dataName)){
				JOptionPane.showMessageDialog(null, "Sorry, the subset expression seems to be invalid.");
				return false;
			}
			subn = Deducer.getUniqueName(dataName+".sub");
			cmd=subn+"<-subset("+dataName+","+subset+")"+"\n";
			isSubset=true;
		}else
			subn=dataName;
		if(optMod.descriptives){
			cmd+="descriptive.table("+outcomes + ","+factor+","+subn+
							",func.names =c(\"Mean\",\"St. Deviation\",\"Valid N\"))\n";
		}
		if(doT){
			cmd += "print(two.sample.test(formula="+outcomes+" ~ "+factor+",\n\t\tdata="+subn+
				",\n\t\ttest=t.test"+
				(tEqVar?",\n\t\tvar.equal=TRUE":"")+
				(optMod.confLevel==.95 ? "" : ",\n\t\tconf.level="+optMod.confLevel)+	
				",\n\t\talternative=\""+optMod.alternative+"\""+
				")"+
				(optMod.digits.trim().equals("<auto>") ? "\n)" : ",\n\tdigits="+optMod.digits+")")+"\n";
		}
		if(doMW){
			cmd += "print(two.sample.test(formula="+outcomes+" ~ "+factor+",\n\t\tdata="+subn+
				",\n\t\ttest=wilcox.test"+
				(optMod.confLevel==.95 ? "" : ",\n\t\tconf.level="+optMod.confLevel)+	
				",\n\t\talternative=\""+optMod.alternative+"\""+
				(",\n\t\t correct=FALSE")+
				")"+
				(optMod.digits.trim().equals("<auto>") ? "\n)" : ",\n\tdigits="+optMod.digits+")")+"\n";
		}
		if(doBoot){
			cmd += "print(two.sample.test(formula="+outcomes+" ~ "+factor+",\n\t\tdata="+subn+
				",\n\t\ttest=perm.t.test"+
				(optMod.confLevel==.95 ? "" : ",\n\t\tconf.level="+optMod.confLevel)+	
				",\n\t\talternative=\""+optMod.alternative+"\""+
				(",\n\t\tstatistic='"+bootStat+"'")+
				")"+
				(optMod.digits.trim().equals("<auto>") ? "\n)" : ",\n\tdigits="+optMod.digits+")")+"\n";
		}
		if(doKS){
			cmd += "print(two.sample.test(formula="+outcomes+" ~ "+factor+",\n\t\tdata="+subn+
				",\n\t\ttest=ks.test"+
				(optMod.confLevel==.95 ? "" : ",\n\t\tconf.level="+optMod.confLevel)+	
				",\n\t\talternative=\""+optMod.alternative+"\""+
				")"+
				(optMod.digits.trim().equals("<auto>") ? "\n)" : ",\n\tdigits="+optMod.digits+")")+"\n";
		}
		if(doBM){
			String packStatus = Deducer.requirePackage("lawstat");
			if(packStatus=="not-installed"){
				JOptionPane.showMessageDialog(null, "Package lawstat must be installed in order to do the Brunner-Munszel test");
				return false;
			}else if(packStatus=="installed")
				cmd+=("library(lawstat)")+"\n";
			cmd += "print(two.sample.test(formula="+outcomes+" ~ "+factor+",\n\t\tdata="+subn+
				",\n\t\ttest=brunner.munzel.test"+
				(optMod.confLevel==.95 ? "" : ",\n\t\tconf.level="+optMod.confLevel)+	
				",\n\t\talternative=\""+optMod.alternative+"\""+
			")"+
				(optMod.digits.trim().equals("<auto>") ? "\n)" : ",\n\tdigits="+optMod.digits+")")+"\n";
		}
		if(plots.plot){
			String dataCall=subn;
			if(splitMod.group1.size()>0 || splitMod.group2.size()>0 || splitMod.isCut){
				dataCall= subn;
			}
			cmd+=plots.getCmd(dataCall, outcomes, factor);
		}
		if(isSubset)
			cmd+="rm("+subn+")\n";
		
		Deducer.execute(cmd);
		return true;
	}
	
}

