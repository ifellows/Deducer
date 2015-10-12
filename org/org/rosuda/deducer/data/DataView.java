package org.rosuda.deducer.data;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

import org.rosuda.JGR.RController;
import org.rosuda.JGR.editor.Editor;
import org.rosuda.JGR.toolkit.AboutDialog;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.LoadData;
import org.rosuda.deducer.toolkit.SaveData;
import org.rosuda.ibase.Common;
import org.rosuda.ibase.toolkit.EzMenuSwing;

public class DataView extends DataViewerTab implements ActionListener {

	protected ExTable table;
	protected ExScrollableTable dataScrollPane;
	protected String dataName;

	public DataView(String dataName){
		super();
		init(dataName);
	}
	
	protected void init(String dataName){
		this.dataName = dataName;		
		RDataFrameModel dataModel = new RDataFrameModel(dataName);
		table =new ExTable(dataModel){
			public boolean editCellAt(int row, int column, EventObject e){
				boolean result = super.editCellAt(row, column, e);	
				final Component editor = getEditorComponent();
				if (editor != null && editor instanceof JTextComponent){
					if (e == null || e.getClass().toString().endsWith("KeyEvent")){
						if(((JTextComponent)editor).getText().equals(RDataFrameModel.NA_STRING)){
							((JTextComponent)editor).setText("NA");
							((JTextComponent)editor).selectAll();
						}
					}
					else
					{
						SwingUtilities.invokeLater(new Runnable(){
							public void run(){
								if(((JTextComponent)editor).getText().equals(RDataFrameModel.NA_STRING)){
									((JTextComponent)editor).setText("NA");
									((JTextComponent)editor).selectAll();
								}
							}
						});
					}
				}

				return result;
			}
		};
		dataScrollPane = new ExScrollableTable(table);
		dataScrollPane.setRowNamesModel(((RDataFrameModel) dataScrollPane.
				getExTable().getModel()).getRowNamesModel());
		dataScrollPane.getExTable().setDefaultRenderer(Object.class,
				dataModel.new RCellRenderer());
		table.setColumnListener(new DataViewColumnHeaderListener(table));
		table.getTableHeader().setReorderingAllowed(false);
		this.setLayout(new BorderLayout());
		this.add(dataScrollPane);
	}

	public void setData(String data) {
		dataName = data;
		RDataFrameModel dataModel = new RDataFrameModel(dataName);
		((RDataFrameModel)table.getModel()).removeCachedData();
		table.setModel(dataModel);
		dataScrollPane.setRowNamesModel(((RDataFrameModel) dataScrollPane.
				getExTable().getModel()).getRowNamesModel());		
		dataScrollPane.getExTable().setDefaultRenderer(Object.class,
				dataModel.new RCellRenderer());
	}

	public void refresh() {
		boolean changed = ((RDataFrameModel)dataScrollPane.getExTable().getModel()).refresh();
		if(changed){
			SwingUtilities.invokeLater(new Runnable(){

				public void run() {
					dataScrollPane.getRowNamesModel().refresh();  
					dataScrollPane.autoAdjustRowWidth();
				}
				
			});
  
		}
	}

	public JMenuBar generateMenuBar() {
		JFrame f = new JFrame();
		String[] Menu = { "+", "File", "@NNew Data","newdata", "@LOpen Data", "loaddata","@SSave Data", "Save Data", "-",
				 "-","@PPrint","print","~File.Quit", 
				"+","Edit","@CCopy","copy","@XCut","cut", "@VPaste","paste","-","Remove Data from Workspace", "Clear Data",
				"~Window", "+","Help","R Help","help", "~About","0" };
			JMenuBar mb = EzMenuSwing.getEzMenu(f, this, Menu);
			
			//preference and about for non-mac systems
			if(!Common.isMac()){
				EzMenuSwing.addMenuSeparator(f, "Edit");
				EzMenuSwing.addJMenuItem(f, "Help", "About", "about", this);	
				for(int i=0;i<mb.getMenuCount();i++){
					if(mb.getMenu(i).getText().equals("About")){
						mb.remove(i);
						i--;
					}
				}
			}
		return f.getJMenuBar();
	}

	public void actionPerformed(ActionEvent e) {
		//JGR.R.eval("print('"+e.getActionCommand()+"')");
		try{
			String cmd = e.getActionCommand();		
			if(cmd=="Open Data"){
				new LoadData();	
			}else if(cmd=="Save Data"){

				new SaveData(dataName);
			}else if(cmd=="Clear Data"){
				if(dataName==null){
					JOptionPane.showMessageDialog(this, "Invalid selection: There is no data loaded.");
					return;
				}
				int confirm = JOptionPane.showConfirmDialog(null, "Remove Data Frame "+
						dataName+" from environment?\n" +
								"Unsaved changes will be lost.",
						"Clear Data Frame", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if(confirm == JOptionPane.NO_OPTION)
					return;
				Deducer.threadedEval("rm("+dataName + ")");
				//RController.refreshObjects();
			}else if (cmd == "about")
				new AboutDialog(null);
			else if (cmd == "cut"){
					table.cutSelection();
			}else if (cmd == "copy") {
					table.copySelection();
			}else if (cmd == "paste") {
					table.pasteSelection();
			} else if (cmd == "print"){
				try{
					table.print(JTable.PrintMode.NORMAL);
				}catch(Exception exc){}
			}else if (cmd == "editor")
				new Editor();
			else if (cmd == "exit")
				((JFrame)this.getTopLevelAncestor()).dispose();
			else if(cmd=="newdata"){
				String inputValue = JOptionPane.showInputDialog("Data Name: ");
				inputValue = Deducer.getUniqueName(inputValue);
				if(inputValue!=null){
					Deducer.threadedEval(inputValue.trim()+"<-data.frame(Var1=NA)");
				}
			}else if (cmd == "loaddata"){
				LoadData dld= new LoadData();
			}else if (cmd == "help")
				Deducer.execute("help.start()");
			else if (cmd == "table"){
				DataViewer inst = new DataViewer();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}else if (cmd == "save")
				new SaveData(dataName);
			
		}catch(Exception e2){new ErrorMsg(e2);}
	}

	public void cleanUp() {
		((RDataFrameModel)table.getModel()).removeCachedData();
	}
	
	
	public class DataViewColumnHeaderListener extends ColumnHeaderListener  {
		private ExTable table;
		private JPopupMenu menu;
		
		public DataViewColumnHeaderListener(ExTable t){
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
		 * The popup menu for column headers.
		 * 
		 * @author ifellows
		 *
		 */
		public class ColumnContextMenu  implements ActionListener{
			int vColIndex,mColIndex;
			
			public ColumnContextMenu(MouseEvent evt){
				TableColumnModel colModel = table.getColumnModel();		
				vColIndex = colModel.getColumnIndexAtX(evt.getX());
				mColIndex = table.convertColumnIndexToModel(vColIndex);	
				menu = new JPopupMenu();
				table.getTableHeader().add(menu);
				
				JMenuItem sortItem = new JMenuItem ("Sort (Increasing)");
				sortItem.addActionListener(this);
				menu.add( sortItem );
				sortItem = new JMenuItem ("Sort (Decreasing)");
				sortItem.addActionListener(this);
				menu.add( sortItem );
				menu.add( new JSeparator() );
				
				JMenuItem copyItem = new JMenuItem ("Copy");
				copyItem.addActionListener(this);
				menu.add( copyItem );
				JMenuItem cutItem = new JMenuItem ("Cut");
				cutItem.addActionListener(this);
				menu.add( cutItem );
				JMenuItem pasteItem = new JMenuItem ("Paste");
				pasteItem.addActionListener(this);
				menu.add ( pasteItem );
				JMenuItem insertItem = new JMenuItem ("Paste into New");
				insertItem.addActionListener(this);
				menu.add( insertItem );
				menu.addSeparator();
				
				JMenuItem insertNewItem = new JMenuItem ("Insert Empty");
				insertNewItem.addActionListener(this);
				menu.add( insertNewItem );
				JMenuItem dupNewItem = new JMenuItem ("Duplicate");
				dupNewItem.addActionListener(this);
				menu.add( dupNewItem );
				JMenuItem removeItem = new JMenuItem ("Remove");
				removeItem.addActionListener(this);
				menu.add( removeItem );
				
				menu.show(evt.getComponent(), evt.getX(), evt.getY());
			}
			
			public void actionPerformed(ActionEvent e){
				
				final JMenuItem source = (JMenuItem)(e.getSource());
				final int vind = vColIndex;
				new Thread(new Runnable(){
					public void run() {
						if(source.getText()=="Copy"){
							table.getCopyPasteAdapter().copy();
						} else if(source.getText()=="Cut"){
							table.cutColumn(vind);
						} else if(source.getText()=="Paste"){
							table.getCopyPasteAdapter().paste();
						} else if(source.getText()=="Paste into New"){
							table.insertColumn(vind);
						} else if(source.getText()=="Insert Empty"){
							table.insertNewColumn(vind);
						} else if(source.getText()=="Remove"){
							table.removeColumn(vind);
						} else if(source.getText().equals("Sort (Increasing)")){
							String cmd = dataName + " <- sort(" + dataName + ", by=~" +
								table.getColumnName(vind).trim() + ")";
							Deducer.eval(cmd);
							refresh();
						} else if(source.getText().equals("Sort (Decreasing)")){
							String cmd = dataName + " <- sort(" + dataName + ", by=~ -" +
							table.getColumnName(vind).trim() + ")";
							Deducer.eval(cmd);
							refresh();
						} else if(source.getText().equals("Duplicate")){
							int ind = vind+1;
							String cmd = dataName + " <- cbind(" + dataName +"[,1:"+ind+"]," +
								dataName +"[,"+ind+"]," + 
								dataName +"[,"+ind+":ncol(" + dataName +")])";
							Deducer.eval(cmd);
							Deducer.eval("names("+dataName+")["+(ind+1)+"] <- " + 
									"paste(names("+dataName+")["+ind+"],'1',sep='')");
							refresh();
						}
					}
					
				}).start();

				menu.setVisible(false);
			}
			
		}
		
		
	}

}
