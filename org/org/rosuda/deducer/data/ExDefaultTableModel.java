package org.rosuda.deducer.data;

import javax.swing.table.*;

/**
 * A stub class
 * 
 * @author ifellows
 *
 */
public class ExDefaultTableModel extends DefaultTableModel{
	public void removeRow(int row){super.removeRow(row);}
	public void insertNewRow(int index){}
	public void removeColumn(int row){}
	public void insertNewColumn(int index){}
	public Object getActualValueAt(int row, int col){return getValueAt(row,col);}
	
	public Object[][] getRange(int row1, int row2, int col1, int col2){
		Object[][] result = new Object[row2-row1][col2-col1];
		for(int i=row1;i<row2;i++){
			for(int j=col1;j<col2;j++){
				result[i-row1][j-col1] = this.getActualValueAt(i, j);
			}
		}
		return result;
	}
	
	public void setRange(Object[][] values,int row,int col){
		for(int i=0;i<values.length;i++){
			for(int j=0;j<values[i].length;j++){
				this.setValueAt(values[i][j], i+row, j+col);
			}
		}
	}
	
	public void eraseRange(int row1, int row2, int col1, int col2){
		for(int i=row1;i<row2;i++){
			for(int j=col1;j<col2;j++){
				this.setValueAt(null, i, j);
			}
		}		
		
	}
	
}
