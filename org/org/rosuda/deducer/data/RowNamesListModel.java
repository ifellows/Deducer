package org.rosuda.deducer.data;


import java.util.List;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
/**
 * The default row name model for an ExTable
 *  
 * Row headers are implemented as a jlist nested within
 * a jscrollpane. Here we have defined them as numbers
 * 1-n where n is the number of rows.
 *
 */
public class RowNamesListModel extends AbstractListModel{
	List headers=new ArrayList();
	public void initHeaders(int n){
		headers=new ArrayList();
		for(int i=1;i<=n;i++){
			headers.add(new Integer(i).toString());
		}
	}
	public int getSize() { return headers.size(); }
	public Object getElementAt(int index) {
		return headers.get(index);
	}
	public void setElementAt(int index,Object value){
		headers.set(index, value);
		refresh();
	}
	
	public int getMaxNumChar(){
		return (new Integer(getSize())).toString().length();
	}
	
	public void addNextHeaderNumber(){
		headers.add(new Integer(getSize()+1).toString());
	}
	
	public void refresh(){
		this.fireContentsChanged(this, 0, getSize());
	}
	
};