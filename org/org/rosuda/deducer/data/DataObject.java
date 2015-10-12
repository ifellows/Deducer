package org.rosuda.deducer.data;

public class DataObject {
	public String name = "";
	public String clss = "";
	public String clssShortName = "";
	
	public DataObject(String nm, String cls,String scls){
		name=nm;
		clss=cls;
		clssShortName=scls;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public String getType(){
		return clss;
	}
	
	public void setType(String newClass){
		clss = newClass;
	}
	
	public String getShortClassName(){
		return clssShortName;
	}
	
	public void setShortClassName(String newName){
		clssShortName = newName;;
	}
	
	public boolean equals(DataObject o){
		if(o==null)
			return false;
		return name.equals(o.getName()) && clss.equals(o.getType()) && clssShortName.equals(o.getShortClassName());
	}
	
	public String toString(){
		return "(" + clssShortName + ") " + name;
	}
	
}
