package org.rosuda.deducer.data;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;

import org.rosuda.ibase.Common;


/**
 * 		A ScrollPane containing an Extable
 * 
 * @author ifellows
 *
 */
public class ExScrollableTable extends JScrollPane{

	private ExTable table;
	private RowNamesListModel rowNamesHeaderModel;
	private JList rowHeader;
	private static int widthMin=40;
	private static int widthMax=150;
	private static int widthMult=8;
	private boolean displayContextualMenu;
	private RowListener rLis;
	
	public ExScrollableTable(ExTable t){
		super();
		table=t;
		setViewportView(table);
		table.parentPane=this;
		rowNamesHeaderModel = new RowNamesListModel();
		rowNamesHeaderModel.initHeaders(table.getRowCount());
		rowHeader = new JList(rowNamesHeaderModel);
		rowHeader.setFixedCellWidth(Math.min(widthMax,Math.max(widthMin, rowNamesHeaderModel.getMaxNumChar()*widthMult+10)));
	    rowHeader.setFixedCellHeight(table.getRowHeight());
	    rowHeader.setCellRenderer(new RowHeaderRenderer(table));
	    setRowHeaderView(rowHeader);
	    rLis=new RowListener();	
	    displayContextualMenu=true;
	}
	
	public ExTable getExTable(){return table;}
	
	
	/**
	 * 		should the contextual menu be shown for rows on right click.
	 * 
	 * @param display
	 * 					true (default) if table should be displayed
	 * 
	 */
	public void displayContextualMenu(boolean display){displayContextualMenu=display;}
	
	/**
	 *     returns the model for the row names
	 * @return
	 */
	public RowNamesListModel getRowNamesModel(){return rowNamesHeaderModel;}
	
	/**
	 * 		Sets row name model
	 * 
	 * @param model
	 */
	public void setRowNamesModel(RowNamesListModel model){
		rowNamesHeaderModel=model;
		rowHeader = new JList(rowNamesHeaderModel);
		rowHeader.setFixedCellWidth(Math.min(widthMax,Math.max(widthMin, rowNamesHeaderModel.getMaxNumChar()*widthMult+10)));
	    rowHeader.setFixedCellHeight(table.getRowHeight());
	    rowHeader.setCellRenderer(new RowHeaderRenderer(table));
	    setRowHeaderView(rowHeader);
	    new RowListener();
	}
	
	/**
	 * 		sets row name width to a reasonable size
	 */
	public void autoAdjustRowWidth(){
		rowHeader.setFixedCellWidth(Math.min(widthMax,Math.max(widthMin, rowNamesHeaderModel.getMaxNumChar()*widthMult+10)));		
	}
	
	/**
	 * 		Inserts a new row into the ExTable
	 * 
	 * @param index
	 */
	public void insertNewRow(int index) {
		table.insertNewRow(index);
		getRowNamesModel().refresh();
	}
	
	/**
	 * 		Inserts clipboard contents into a row.
	 * 
	 * @param index
	 */
	public void insertRow(int index){
		if(table.getCopyPasteAdapter().getClipBoard().indexOf("\n")<(table.getCopyPasteAdapter().getClipBoard().length()-2) && 
				table.getCopyPasteAdapter().getClipBoard().length()>0){
			JOptionPane.showMessageDialog(null,"The values in the clipboard do not seem to be a row.\nTry copying the desired data again.",
					"Invalid Row Insertion",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		insertNewRow(index);
		table.selectRow(index);
		table.requestFocus();
		table.getCopyPasteAdapter().paste();
	}
	
	public void removeRowListener(){
		rowHeader.removeMouseListener(rLis);
	}
	
	
	/**
	 * 		This Class Renders the Row Headers for the table.
	 * 
	 *
	 */
	class RowHeaderRenderer extends JLabel implements ListCellRenderer {
		   
		RowHeaderRenderer(JTable table) {
			JTableHeader header = table.getTableHeader();
			setOpaque(true);
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
			setHorizontalAlignment(CENTER);
			setForeground(header.getForeground());
			setBackground(header.getBackground());
			setFont(header.getFont());
		}
		
		public Component getListCellRendererComponent( JList list, 
				Object value, int index, boolean isSelected, boolean cellHasFocus) {
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}


	/**
	 * 		Handles mouse events on the row names
	 * 		
	 * 		This can be extended further. currently all
	 * 		that is allowed is single row selection.
	 *
	 */
	class RowListener extends MouseAdapter {

		public RowListener(){
			rowHeader.addMouseListener(this);
		}
		
		
		public void mouseClicked(MouseEvent evt){
			if(evt.getButton()==MouseEvent.BUTTON3 && !Common.isMac()){
				int selectedRow =rowHeader.locationToIndex(evt.getPoint());
				table.requestFocus();
				table.selectRow(selectedRow);
				if(displayContextualMenu)
					new RowContextMenu(evt,selectedRow);

			}
		}
		
		
		public void mousePressed(MouseEvent evt){
			int selectedRow =rowHeader.locationToIndex(evt.getPoint());
			table.requestFocus();			
			table.selectRow(selectedRow);
			if(evt.isPopupTrigger() && Common.isMac()){
				if(displayContextualMenu)
					new RowContextMenu(evt,selectedRow);	
			}
			
		}
	}
	
	/**
	 * 	The pop-up menu to display for right clicks on row names
	 * 
	 * @author ifellows
	 *
	 */
	class RowContextMenu implements ActionListener{
		int index;
		private JPopupMenu menu;;
		
		public RowContextMenu(MouseEvent evt,int selectedRow){
			index = selectedRow;
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
			JMenuItem insertNewItem = new JMenuItem ("Insert New Row");
			insertNewItem.addActionListener(this);
			menu.add( insertNewItem );
			JMenuItem removeItem = new JMenuItem ("Remove Row");
			removeItem.addActionListener(this);
			menu.add( removeItem );
			menu.addSeparator();
			JMenuItem editItem = new JMenuItem ("Edit Row Name");
			editItem.addActionListener(this);
			menu.add( editItem );
			menu.show(evt.getComponent(), evt.getX(), evt.getY());
		}
		
		public void actionPerformed(ActionEvent e){
			JMenuItem source = (JMenuItem)(e.getSource());
			if(source.getText()=="Copy"){
				table.getCopyPasteAdapter().copy();
			} else if(source.getText()=="Cut"){
				table.cutRow(index);
			} else if(source.getText()=="Paste"){
				table.getCopyPasteAdapter().paste();
			} else if(source.getText()=="Insert"){
				insertRow(index);
			} else if(source.getText()=="Insert New Row"){
				insertNewRow(index);
			} else if(source.getText()=="Remove Row"){
				table.removeRow(index);
				getRowNamesModel().refresh();
			}else if(source.getText()=="Edit Row Name"){
				String curValue = getRowNamesModel().getElementAt(index).toString();
				String selection = JOptionPane.showInputDialog((JMenuItem)(e.getSource()), "Edit row name:", curValue);
				if(selection!=null && selection.length()>0){
					getRowNamesModel().setElementAt(index, selection);
				}
			}
			menu.setVisible(false);
		}
		
	}
	
	
	
}
