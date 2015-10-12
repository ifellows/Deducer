


package org.rosuda.deducer.data;


import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPLogical;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.deducer.Deducer;


/**
 * A table for viewing and editing variable information
 * 
 * @author ifellows
 *
 */
class RDataFrameVariableModel extends ExDefaultTableModel {
	
	private static String guiEnv = Deducer.guiEnv;
	
	private String rDataName=null;
	
	private String tempDataName = null;
	
	
	private VariableNumberListModel rowNamesModel;
	
	private final int numExtraColumns = 1;
	
	
	String[] varNames = new String[]{};
	String[] classes = new String[]{};
	String[] factorLevels = new String[]{};
	
	
	public RDataFrameVariableModel(){}
	
	public RDataFrameVariableModel(String name){
		boolean envDefined = ((REXPLogical)Deducer.eval("'"+guiEnv+"' %in% .getOtherObjects()")).isTRUE()[0];
		
		if(!envDefined){
			Deducer.eval(guiEnv+"<-new.env(parent=emptyenv())");
		}
		if(tempDataName!=null)
			removeCachedData();
		rDataName = name;
		if(rDataName!=null){
			tempDataName = Deducer.getUniqueName(rDataName + (Math.random()+1.0),guiEnv);
			try {
				Deducer.eval(guiEnv+"$"+tempDataName+"<-"+rDataName);
			} catch (Exception e) {
				new ErrorMsg(e);
			}
			populateArrays();
		}
	}
	
	public void populateArrays(){
		try {
			varNames = Deducer.timedEval("colnames("+rDataName+")").asStrings();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(varNames==null)
			varNames = new String[]{};
		int n = varNames.length;
		classes = new String[n];
		factorLevels = new String[n];
		String[] tmpClasses = null;
		try {
			tmpClasses = Deducer.timedEval(
					"sapply("+rDataName+",function(x){a <- class(x);return(a[length(a)])})").asStrings();
		} catch (Exception e1) {}
		
		for(int i=0;i<varNames.length;i++){
			String theClass = null;
			if(tmpClasses!=null){
				theClass = tmpClasses[i];	
			}
			if(theClass.equals("Date")) classes[i] = "Date";
			else if(theClass.equals("POSIXt")) classes[i] = "Time";
			else if (theClass.equals("NULL")) classes[i] = "NULL";
			else if (theClass.equals("factor")) classes[i] = "Factor";
			else if (theClass.equals("integer")) classes[i] = "Integer";
			else if (theClass.equals("character")) classes[i] = "Character";
			else if (theClass.equals("logical")) classes[i] = "Logical";
			else if (theClass.equals("numeric")) classes[i] = "Double";
			else classes[i] = "Other";
			
			factorLevels[i] = "";
			try{
				if(theClass.equals("factor")){
					String[] levels = Deducer.eval("levels("+rDataName+"[,"+(i+1)+"])").asStrings();
					String lev = "";
					for(int j=0;j<Math.min(levels.length,10);j++){
						lev=lev.concat("("+(j+1)+") ");
						lev=lev.concat(levels[j]);	
						lev=lev.concat("; ");
					}
					if(levels.length>10)
						lev=lev.concat(" ...");
					factorLevels[i] = lev;
				}
			}catch(Exception e){}
		}
	}
	
	public int getColumnCount( ){
			return 5;
	}
	
	public int getRowCount(){
		if(varNames!=null)
			return varNames.length + numExtraColumns;
		else
			return 0;
	}
	
	public Object getValueAt(int row, int col){
		try{
			if(row>=(getRowCount()-numExtraColumns)){
				return null;
			}else if(col==0){
				return varNames[row];
			}else if(col==1){
				return classes[row];
			}else if(col==2){
				return factorLevels[row];
			}else
				return "";
		}catch(Exception e){return "?";}
	}
	
	public void setValueAt(Object value,int row, int col){
		if(row>=(getRowCount()-numExtraColumns)){
			if(col==0){
				Deducer.timedEval(rDataName+"[,"+(row+1)+"]<-NA");	
				Deducer.timedEval("colnames("+rDataName+")["+(row+1)+"]<-'"+value.toString().trim()+"'");
				refresh();
				rowNamesModel.refresh();
			}else
				return;
		}else if(col==0){
			Deducer.eval("{rm(\"" +tempDataName+"\",envir="+guiEnv+");" + 
					"colnames("+rDataName+")["+(row+1)+"]<-'"+value.toString().trim()+"';" +
					guiEnv+"$"+tempDataName+"<-"+rDataName + "}");
			
			//Deducer.eval("rm(\"" + guiEnv+"$"+tempDataName+"\")");
			//Deducer.eval("colnames("+rDataName+")["+(row+1)+"]<-'"+value.toString().trim()+"'");
			//Deducer.eval(guiEnv+"$"+tempDataName+"<-"+rDataName);
			varNames[row] = value.toString().trim();
			this.fireTableCellUpdated(row, col);
			
		}else if(col==1){
			String curClass = (String) getValueAt(row,col);
			String type = value.toString().toLowerCase().trim();
			if(value.toString().equals(curClass))
				return;
			if(type.equals("integer")){ 
				Deducer.execute(rDataName+"[,"+(row+1)+"]<-as.integer("+rDataName+"[,"+(row+1)+"])");
				classes[row] = "Integer";
				this.fireTableCellUpdated(row, col);
			}else if(type.equals("factor")){
				Deducer.execute(rDataName+"[,"+(row+1)+"]<-as.factor("+rDataName+"[,"+(row+1)+"])");
				classes[row] = "Factor";
				this.fireTableCellUpdated(row, col);
			}else if(type.equals("double")){ 
				Deducer.execute(rDataName+"[,"+(row+1)+"]<-as.double("+rDataName+"[,"+(row+1)+"])");
				classes[row] = "Double";
				this.fireTableCellUpdated(row, col);
			}else if(type.equals("logical")){ 
				Deducer.execute(rDataName+"[,"+(row+1)+"]<-as.logical("+rDataName+"[,"+(row+1)+"])");
				classes[row] = "Logical";
				this.fireTableCellUpdated(row, col);
			}else if(type.equals("character")){ 
				Deducer.execute(rDataName+"[,"+(row+1)+"]<-as.character("+rDataName+"[,"+(row+1)+"])");
				classes[row] = "Character";
				this.fireTableCellUpdated(row, col);
			}else if(type.equals("date")){
				
				if(curClass=="Date")
					return;
				if(curClass=="Time"){
					Deducer.execute(rDataName+"[,"+(row+1)+"]<-as.Date("+rDataName+"[,"+(row+1)+"])");
					refresh();
					return;
				}
				if(curClass!="Character" && curClass!="Factor"){
					JOptionPane.showMessageDialog(null, "Unable to convert variable of type "+
							curClass+" to Date.");
					return;
				}
				String format="";
				String[] formats = {"dd/mm/yyyy",
						"dd-mm-yyyy",
						"dd/mm/yy",
						"dd-mm-yy",
						"mm/dd/yyyy",
						"mm-dd-yyyy",
						"mm/dd/yy",
						"mm-dd-yy",
						"yyyy/mm/dd",
						"yyyy-mm-dd",
						"yy/mm/dd",
						"yy-mm-dd",
						"other"};
				Object form = JOptionPane.showInputDialog(null, "Choose Date Format",
								"Date Format", JOptionPane.QUESTION_MESSAGE, null, 
								formats, formats[9]);
				if(form==null)
					return;
				if(form == "other"){
					Object user = JOptionPane.showInputDialog("Specify a format (e.g. %d %m %Y)");
					if(user==null)
						return;
					format = user.toString();
				}else{
					if(form == formats[0])
						format = "%d/%m/%Y";
					else if(form == formats[1])
						format = "%d-%m-%Y";
					else if(form == formats[2])
						format = "%d/%m/%y";
					else if(form == formats[3])
						format = "%d-%m-%y";
					else if(form == formats[4])
						format = "%m/%d/%Y";
					else if(form == formats[5])
						format = "%m-%d-%Y";
					else if(form == formats[6])
						format = "%m/%d/%y";
					else if(form == formats[7])
						format = "%m-%d-%y";
					else if(form == formats[8])
						format = "%Y/%m/%d";
					else if(form == formats[9])
						format = "%Y-%m-%d";
					else if(form == formats[10])
						format = "%y/%m/%d";
					else if(form == formats[11])
						format = "%y-%m-%d";
				}
				Deducer.execute(rDataName+"[,"+(row+1)+"]<-as.Date("+rDataName+"[,"+(row+1)+"], format= '"+format+"')");
				classes[row] = "Date";
				this.fireTableCellUpdated(row, col);
			}else if(type.equals("time")){ 
				if(curClass=="Date"){
					Deducer.execute(rDataName+"[,"+(row+1)+"]<-as.POSIXct("+rDataName+"[,"+(row+1)+"])");
					classes[row] = "Time";
					this.fireTableCellUpdated(row, col);
					return;
				}
				if(curClass=="Time"){
					return;
				}
				if(curClass!="Character" && curClass!="Factor"){
					JOptionPane.showMessageDialog(null, "Unable to convert variable of type "+
							curClass+" to Date.");
					return;
				}
				String format="";
				String[] formats = {"dd/mm/yyyy hh:mm:ss",
						"dd-mm-yyyy hh:mm:ss",
						"dd/mm/yy hh:mm:ss",
						"dd-mm-yy hh:mm:ss",
						"mm/dd/yyyy hh:mm:ss",
						"mm-dd-yyyy hh:mm:ss",
						"mm/dd/yy hh:mm:ss",
						"mm-dd-yy hh:mm:ss",
						"yyyy/mm/dd hh:mm:ss",
						"yyyy-mm-dd hh:mm:ss",
						"yy/mm/dd hh:mm:ss",
						"yy-mm-dd hh:mm:ss",
						"other"};
				Object form = JOptionPane.showInputDialog(null, "Choose Date/Time Format",
								"Date/Time Format", JOptionPane.QUESTION_MESSAGE, null, 
								formats, formats[9]);
				if(form==null)
					return;
				if(form == "other"){
					Object user = JOptionPane.showInputDialog("Specify a format (e.g. %d %m %Y)");
					if(user==null)
						return;
					format = user.toString();
				}else{
					if(form == formats[0])
						format = "%d/%m/%Y %H:%M:%S";
					else if(form == formats[1])
						format = "%d-%m-%Y %H:%M:%S";
					else if(form == formats[2])
						format = "%d/%m/%y %H:%M:%S";
					else if(form == formats[3])
						format = "%d-%m-%y %H:%M:%S";
					else if(form == formats[4])
						format = "%m/%d/%Y";
					else if(form == formats[5])
						format = "%m-%d-%Y %H:%M:%S";
					else if(form == formats[6])
						format = "%m/%d/%y %H:%M:%S";
					else if(form == formats[7])
						format = "%m-%d-%y %H:%M:%S";
					else if(form == formats[8])
						format = "%Y/%m/%d %H:%M:%S";
					else if(form == formats[9])
						format = "%Y-%m-%d %H:%M:%S";
					else if(form == formats[10])
						format = "%y/%m/%d %H:%M:%S";
					else if(form == formats[11])
						format = "%y-%m-%d %H:%M:%S";
				}
				if(curClass=="Character"){
					Deducer.execute(rDataName+"[,"+(row+1)+"]<-as.POSIXct("+rDataName+"[,"+(row+1)+"], format='"+format+"')");	
					classes[row] = "Time";
					this.fireTableCellUpdated(row, col);
					return;
				}else{
					Deducer.execute(rDataName+"[,"+(row+1)+"]<-as.POSIXct(as.character("+rDataName+"[,"+(row+1)+"]), format='"+format+"')");
					classes[row] = "Time";
					this.fireTableCellUpdated(row, col);
					return;
				}
			}
		}
	}
	
	public String getColumnName(int col){
		if(col==0){
			return "Variable";
		}else if(col==1){
			return "Type";	
		}else if(col==2){
			return "Factor Levels";
		}
		return "";
	}
	
	public boolean refresh(){
		boolean changed = false;
		REXP exist = Deducer.eval("!inherits(try(eval(parse(text=\""+Deducer.addSlashes(rDataName)+
				"\")),silent=TRUE),'try-error')");
			//Deducer.eval("exists('"+rDataName+"')");
		if(exist!=null && ((REXPLogical)exist).isTRUE()[0]){
			REXP ident =Deducer.eval("identical("+rDataName+","+guiEnv+"$"+tempDataName+")"); 
			if(ident!=null && ((REXPLogical)ident).isFALSE()[0]){
				Deducer.eval(guiEnv+"$"+tempDataName+"<-"+rDataName);
				populateArrays();
				SwingUtilities.invokeLater(new Runnable(){

					public void run() {
						RDataFrameVariableModel.this.fireTableDataChanged();
					}
					
				});		
				changed=true;
			}
		}
		return changed;
	}
	
	public void removeCachedData(){
		boolean envDefined = ((REXPLogical)Deducer.eval("'"+guiEnv+"' %in% .getOtherObjects()")).isTRUE()[0];
		
		if(!envDefined){
			Deducer.eval(guiEnv+"<-new.env(parent=emptyenv())");
		}
		boolean tempStillExists = false;
		REXP tmp = Deducer.eval("exists('"+tempDataName+"',where="+guiEnv+",inherits=FALSE)");
		if(tmp instanceof REXPLogical)
			tempStillExists = ((REXPLogical)tmp).isTRUE()[0];
		if(tempStillExists)
			Deducer.eval("rm("+tempDataName+",envir="+guiEnv+")");		
	}
	
	protected void finalize() throws Throwable {
		removeCachedData();
		super.finalize();
	}
	
	
	public class VariableNumberListModel extends RowNamesListModel{
		
		VariableNumberListModel(){
			rowNamesModel = this;
		}
		
		public Object getElementAt(int index) {
			return new Integer(index+1);
		}
		
		public int getSize() { 
			return varNames.length;
		}
	}

	

}
