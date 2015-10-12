package org.rosuda.deducer.menu;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import org.rosuda.JGR.JGR;
import org.rosuda.JGR.RController;
import org.rosuda.deducer.Deducer;

public class CorModel {
	
	public String dataName = "";
	public DefaultListModel variables = new DefaultListModel();
	public DefaultListModel with = new DefaultListModel();
	
	public String subset = "";
	public String method = "pearson";
	
	public OptModel options= new OptModel();
	public Plots plots= new Plots();
	
	public boolean run(){
		boolean withExists = true;
		if(dataName==null)
			return false;
		if(variables.size()==0){
			JOptionPane.showMessageDialog(null, "Please select one or more outcome variables.");
			return false;
		}
		if(with.size()==0){
			withExists=false;			
		}
		subset = subset.trim();
		String cmd="";
		String subn;
		String outcomes = Deducer.makeRCollection(variables, "d", false);
		String withVec;
		if(withExists)
			withVec = Deducer.makeRCollection(with, "d", false);
		else
			withVec ="";
		String name = Deducer.getUniqueName("corr.mat");
		if(dataName=="")
			return false;
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
		
		if(plots.matrix && withExists){
			int contin =JOptionPane.showConfirmDialog(null, "The type of plot selected can only be used when\n" +
												"no with variables are selected.\nWould you like to" +
												"continue without the plot?","Invalid plot type", 
												JOptionPane.YES_NO_OPTION);
			if(contin==JOptionPane.CANCEL_OPTION)
				return false;
			else
				plots.matrix=false;
		}
		
		cmd+=name+"<-cor.matrix(variables="+outcomes+
				(withExists ? ",\n\twith.variables="+withVec :",")+
				",\n\t data="+subn+
				",\n\t test=cor.test"+
				",\n\t method='"+method+"'"+
				(options.confLevel==.95 ? "" : ",\n\tconf.level="+options.confLevel)+
				",\n\talternative=\""+options.alternative+"\""+
				(method.equals("spearman") ? ",\n\texact=FALSE" : "")+
				")\n";
		if(options.showTable){
			cmd+="print("+name+(options.digits.equals("<auto>")?"":",digits="+options.digits)+
						(options.n?"":",N=FALSE")+(options.ci?"":",CI=FALSE")+(options.stat?"":",stat=FALSE")+
						(options.pValue?"":",p.value=FALSE")+")\n";
		}
		
		if(!plots.none){
			if(plots.scatterArray){
				cmd+="qscatter_array("+outcomes+
				",\n\t"+(withExists ? withVec : outcomes)+
				",\n\tdata="+subn+
				(!plots.common ? ",common.scales=FALSE":"")+
				(plots.saAlpha!=.25 ? ",alpha="+plots.saAlpha:"")+
				")";
			
				if(plots.saLines.equals("Linear")){
					cmd+=" + geom_smooth(method=\"lm\")\n";
				}else if(plots.saLines.equals("Loess")){
					cmd+=" + geom_smooth()\n";
				}else
					cmd+="\n";
			}
			if(plots.matrix){
				cmd+="ggcorplot(cor.mat="+name+",data="+subn+
				",\n\tcor_text_limits=c(5,"+plots.mSize+")"+
				(plots.mAlpha!=.25 ? ",\n\talpha="+(new Double(plots.mAlpha)).toString() :"");
				
				if(plots.mLines.equals("Linear")){
					cmd+=",\n\tline.method=\"lm\"";
				}else if(plots.mLines.equals("Loess")){
					cmd+=",\n\tline.method=\"loess\"";
				}else
					cmd+=",\n\tlines=FALSE";
				cmd+=")\n";
			}
			if(plots.circles){
				cmd+="plot("+name+
				(plots.cRadius!=10 ? ",size="+plots.cRadius : "")+")\n";
			}
		}
		
		
		if(isSubset)
			cmd+="rm('"+subn+"','"+name+"')\n";
		else
			cmd+="rm('"+name+"')\n";
		
		Deducer.execute(cmd);
		return true;
	}
	
	public class OptModel{
		public boolean showTable=true;
		public boolean ci=true;
		public boolean n=true;
		public boolean stat=true;
		public boolean pValue=true;

		public String digits = "<auto>";
		public String alternative = "two.sided";
		public double confLevel=.95;
	}
	
	public class Plots{
		public boolean scatterArray =false;
		
		public boolean common =true;
		public String saLines= "Linear";
		public double saAlpha = .25;
		
		public boolean matrix =false;
		public String mLines= "Linear";
		public int mSize = 20;
		public double mAlpha = .25;
		
		public boolean ellipse =false;
		
		public boolean circles =false;
		public int cRadius = 10;
		
		public boolean none =true;
		
	}

	
}
