


package org.rosuda.deducer.data;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.datatransfer.*;
import java.util.*;

import org.rosuda.ibase.Common;
/**
 * 
 * Adapter copies and pastes from the clipboard in tab delimited format.
 * Compatible with Excel
 * 
 */
public class CopyPasteAdapter implements ActionListener{
	private String rowstring,value;
	private Clipboard system;
	private StringSelection stsel;
	private ExTable jTable1 ;
	
	

	public CopyPasteAdapter(ExTable myJTable)
	{
			KeyStroke copy,cut, paste;
			jTable1 = myJTable;
			if(!Common.isMac()){
				copy = KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK,false);
				cut = KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK,false);
				paste = KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK,false);
			}else{
				copy = KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.META_MASK,false);
				cut = KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.META_MASK,false);
				paste = KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.META_MASK,false);   	  
			}
			jTable1.registerKeyboardAction(this,"Copy",copy,JComponent.WHEN_FOCUSED);
			jTable1.registerKeyboardAction(this,"Cut",cut,JComponent.WHEN_FOCUSED);
			jTable1.registerKeyboardAction(this,"Paste",paste,JComponent.WHEN_FOCUSED);
			system = Toolkit.getDefaultToolkit().getSystemClipboard();
	}
	

	public ExTable getJTable() {return jTable1;}
	public void setJTable(ExTable jTable1) {this.jTable1=jTable1;}
	
	public void copyCut(boolean isCut){
		StringBuffer sbf=new StringBuffer();
		// Check to ensure we have selected only a contiguous block of
		// cells
		int numcols=jTable1.getSelectedColumnCount();
		int numrows=jTable1.getSelectedRowCount();
		int[] rowsselected=jTable1.getSelectedRows();
		
		int[] colsselected=jTable1.getSelectedColumns();
		
		if (!((numrows-1==rowsselected[rowsselected.length-1]-rowsselected[0] &&
				numrows==rowsselected.length) &&
				(numcols-1==colsselected[colsselected.length-1]-colsselected[0] &&
						numcols==colsselected.length))){
			JOptionPane.showMessageDialog(null, "Invalid Copy Selection",
					"Invalid Copy Selection",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		int rowStart = rowsselected[0];
		int colStart = colsselected[0];
		
		try{jTable1.getCellEditor().cancelCellEditing();}catch(Exception e){}
		Object[][] values = ((ExDefaultTableModel)jTable1.getModel()).getRange(
				rowStart,rowStart+numrows,colStart,colStart+numcols);
		if(isCut){
			((ExDefaultTableModel)jTable1.getModel()).eraseRange(
					rowStart,rowStart+numrows,colStart,colStart+numcols);
		}		
		numcols = values.length;
		if(values.length>0)
			numrows = values[0].length;
		else
			numrows=0;
		for (int j=0;j<numcols;j++){
			for (int i=0;i<numrows;i++){
				Object temp = values[j][i];
				sbf.append((temp==null) ? "" : temp.toString());
				if (i<numrows-1) sbf.append("\t");				
			}
			if(j<numcols-1)
				sbf.append("\n");
		}

		stsel  = new StringSelection(sbf.toString());
		system = Toolkit.getDefaultToolkit().getSystemClipboard();
		system.setContents(stsel,stsel);
	}
	
	public void cut(){ copyCut(true);}
	public void copy(){ copyCut(false);}
	
	public String getClipBoard(){
		String aString=null;
		try{aString = (String)(system.getContents(this).getTransferData(DataFlavor.stringFlavor));}catch(Exception e){}
		return aString;
	}
	
	public void paste(){
		int startRow=(jTable1.getSelectedRows())[0];
		int startCol=(jTable1.getSelectedColumns())[0];
		try{
			StringTokenizer st1;
			String trstring= getClipBoard();
			if(trstring.startsWith("\n")|trstring.startsWith("\r"))
				trstring="\t".concat(trstring);
			//System.out.println("String is:"+trstring.indexOf("\r"));
			if(trstring.indexOf("\n")<0)
				st1=new StringTokenizer(trstring,"\r");
			else
				st1=new StringTokenizer(trstring,"\n");
			try{jTable1.getCellEditor().cancelCellEditing();}catch(Exception e){}
			ArrayList rowList = new ArrayList();
			for(int row=0;st1.hasMoreTokens();row++){
				rowstring=st1.nextToken();
				StringTokenizer st2=new StringTokenizer(rowstring,"\t",true);
				int col=0;
				String lastValue="";
				ArrayList colList = new ArrayList();
				for(int j=0;st2.hasMoreTokens();j++){
					value=(String)st2.nextToken();
					if(value.indexOf("\t")<0){
						if (startRow+row< jTable1.getRowCount()  &&
								startCol+col< jTable1.getColumnCount())
							colList.add(value);
							//jTable1.setValueAt(value,startRow+row,startCol+col);
						col++;
					}else if((lastValue.indexOf("\t")>=0) && (value.indexOf("\t")>=0)){
						if (startRow+row< jTable1.getRowCount()  &&
								startCol+col< jTable1.getColumnCount())
							colList.add(null);
							//jTable1.setValueAt(null,startRow+row,startCol+col);
						col++;                	   
					}
					if(value.indexOf("\t")>=0 && !st2.hasMoreTokens()){
						if (startRow+row< jTable1.getRowCount()  &&
								startCol+col< jTable1.getColumnCount())
							colList.add(null);
							//jTable1.setValueAt(null,startRow+row,startCol+col);
					}
					if(j==0 && value.indexOf("\t")>=0){
						if (startRow+row< jTable1.getRowCount()  &&
								startCol+col< jTable1.getColumnCount())
							colList.add(null);
							//jTable1.setValueAt(null,startRow+row,startCol+col);
						col++;
					}
					lastValue=value;
				}
				rowList.add(colList);
			}
			Object[][] values = new Object[rowList.size()][];
			for(int i=0;i<rowList.size();i++){
				values[i] = ((ArrayList)rowList.get(i)).toArray();
			}
			((ExDefaultTableModel)jTable1.getModel()).setRange(values, startRow, startCol);
		}
		catch(Exception ex){ex.printStackTrace();}
	}
	
	/**
	 * This method is activated on the Keystrokes we are listening to
	 * in this implementation. Here it listens for Copy and Paste ActionCommands.
	 * Selections comprising non-adjacent cells result in invalid selection and
	 * then copy action cannot be performed.
	 * Paste is done by aligning the upper left corner of the selection with the
	 * 1st element in the current selection of the JTable.
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().compareTo("Copy")==0 || e.getActionCommand().compareTo("Cut")==0){
			copyCut(e.getActionCommand().compareTo("Cut")==0);
		}
		if (e.getActionCommand().compareTo("Paste")==0){
			paste();
		}
	}
}
