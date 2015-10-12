

package org.rosuda.deducer.data;

import java.awt.event.*;


import javax.swing.*;
import javax.swing.table.*;


/**
 * Handles the column header contextual menu
 * 
 * @author Ian Fellows
 *
 */
public class ColumnHeaderListener extends MouseAdapter  {
	private ExTable table;
	private JPopupMenu menu;
	
	public ColumnHeaderListener(){this(new ExTable());}
	public ColumnHeaderListener(ExTable t){
		table = t;
		JTableHeader header = table.getTableHeader();
		header.addMouseListener(this);
	}
	
	public void remove(){
		table.getTableHeader().removeMouseListener(this);
	}
	
	
	public void mouseClicked(MouseEvent evt){
		boolean isMac = System.getProperty("java.vendor").indexOf("Apple")>-1;
		TableColumnModel colModel = table.getColumnModel();		
		int vColIndex = colModel.getColumnIndexAtX(evt.getX());
		int mColIndex = table.convertColumnIndexToModel(vColIndex);	
		table.selectColumn(vColIndex);
		
		if(evt.getButton()==MouseEvent.BUTTON3 && !isMac){
			new ColumnContextMenu(evt);
		}
	}
	
	
	public void mousePressed(MouseEvent evt){
		boolean isMac = System.getProperty("java.vendor").indexOf("Apple")>-1;
		if(evt.isPopupTrigger() && isMac){
			new ColumnContextMenu(evt);	
		}
	}

	/** 
	 * The popup menu for column headers
	 * 
	 * @author ifellows
	 *
	 */
	class ColumnContextMenu  implements ActionListener{
		int vColIndex,mColIndex;
		
		public ColumnContextMenu(MouseEvent evt){
			TableColumnModel colModel = table.getColumnModel();		
			vColIndex = colModel.getColumnIndexAtX(evt.getX());
			mColIndex = table.convertColumnIndexToModel(vColIndex);	
			menu = new JPopupMenu();
			table.getTableHeader().add(menu);
			JMenuItem copyItem = new JMenuItem ("Copy");
			copyItem.addActionListener(this);
			menu.add( copyItem );
			JMenuItem cutItem = new JMenuItem ("Cut");
			cutItem.addActionListener(this);
			menu.add( cutItem );
			JMenuItem pasteItem = new JMenuItem ("Paste");
			pasteItem.addActionListener(this);
			menu.add ( pasteItem );
			menu.addSeparator();
			JMenuItem insertItem = new JMenuItem ("Insert");
			insertItem.addActionListener(this);
			menu.add( insertItem );
			JMenuItem insertNewItem = new JMenuItem ("Insert New Column");
			insertNewItem.addActionListener(this);
			menu.add( insertNewItem );
			JMenuItem removeItem = new JMenuItem ("Remove Column");
			removeItem.addActionListener(this);
			menu.add( removeItem );
			menu.show(evt.getComponent(), evt.getX(), evt.getY());
		}
		
		public void actionPerformed(ActionEvent e){
			
			JMenuItem source = (JMenuItem)(e.getSource());
			if(source.getText()=="Copy"){
				table.getCopyPasteAdapter().copy();
			} else if(source.getText()=="Cut"){
				table.cutColumn(vColIndex);
			} else if(source.getText()=="Paste"){
				table.getCopyPasteAdapter().paste();
			} else if(source.getText()=="Insert"){
				table.insertColumn(vColIndex);
			} else if(source.getText()=="Insert New Column"){
				table.insertNewColumn(vColIndex);
			} else if(source.getText()=="Remove Column"){
				table.removeColumn(vColIndex);
			}
			menu.setVisible(false);
		}
		
	}
	
	
}
