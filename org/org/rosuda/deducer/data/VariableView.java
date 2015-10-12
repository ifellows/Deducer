package org.rosuda.deducer.data;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.rosuda.JGR.RController;
import org.rosuda.JGR.editor.Editor;
import org.rosuda.JGR.toolkit.AboutDialog;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.REngine.REXPLogical;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.data.RDataFrameVariableModel.VariableNumberListModel;
import org.rosuda.deducer.toolkit.LoadData;
import org.rosuda.deducer.toolkit.SaveData;
import org.rosuda.deducer.menu.FactorDialog;
import org.rosuda.ibase.Common;
import org.rosuda.ibase.toolkit.EzMenuSwing;

public class VariableView extends DataViewerTab implements ActionListener{
	
	protected String dataName;
	
	protected ExScrollableTable variableScrollPane;
	protected ExTable ex;
	protected JComboBox comboBox;
	public VariableView(String dataName){
		super();
		init(dataName);
	}
	
	protected void init(String dataName){
		this.dataName = dataName;
		comboBox = new JComboBox();
		comboBox.addItem("Character");
		comboBox.addItem("Factor");
		comboBox.addItem("Double");
		comboBox.addItem("Integer");
		comboBox.addItem("Logical");
		comboBox.addItem("Date");
		comboBox.addItem("Time");
		comboBox.addItem("Other");
		RDataFrameVariableModel varModel = new RDataFrameVariableModel(dataName);
		ex = new ExTable();	
		ex.setModel(varModel);			
		ex.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(comboBox));
		ex.getColumnModel().getColumn(0).setPreferredWidth(200);
		ex.getColumnModel().getColumn(1).setPreferredWidth(70);
		ex.getColumnModel().getColumn(2).setPreferredWidth(300);
		ex.setColumnSelectionAllowed(true);
		ex.setRowSelectionAllowed(true);		
		ex.getTableHeader().removeMouseListener(ex.getColumnListener());
		ex.getTableHeader().setReorderingAllowed(false);
		ex.addMouseListener(new MouseAdapter(){
		     public void mouseClicked(MouseEvent e){
		    	 ExTable extab = (ExTable)e.getSource();
		    	 if(extab.getSelectedColumn()==2){
		    		final int row = extab.getSelectedRow();
		    		final String varName = (String)extab.getModel().getValueAt(row, 0);
		    		final String datName = VariableView.this.dataName;
		    		REXPLogical tmp;
					tmp = (REXPLogical) Deducer.timedEval("is.factor("+datName+"$"+varName+")");
		    		if(tmp!=null && tmp.isTRUE()[0]){
		    			new Thread(new Runnable(){
							public void run() {
								FactorDialog fact = new FactorDialog(null,datName+"[,"+(row+1)+"]");
				    			fact.setLocationRelativeTo(ex);
				    			fact.setTitle("Factor Editor: "+varName);
				    			fact.setVisible(true);
							}
		    				
		    			}).start();
		    			
		    		}
		    	 }
		      }
		     } );

		variableScrollPane = new ExScrollableTable(ex);			
		variableScrollPane.setRowNamesModel(varModel.new VariableNumberListModel());
		variableScrollPane.displayContextualMenu(false);
		this.setLayout(new BorderLayout());
		this.add(variableScrollPane);
	}

	public void setData(String data) {
		dataName = data;
		RDataFrameVariableModel varModel = new RDataFrameVariableModel(dataName);
		ex.setModel(varModel);
		ex.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(comboBox));
		ex.getColumnModel().getColumn(0).setPreferredWidth(200);
		ex.getColumnModel().getColumn(1).setPreferredWidth(50);
		ex.getColumnModel().getColumn(2).setPreferredWidth(300);
		variableScrollPane.setRowNamesModel(varModel.new VariableNumberListModel());
	}

	public void refresh() {
/*		int colStart = -1;
		int colEnd = -1;
		int rowStart = -1;
		int rowEnd=-1;
		int[] cols = ex.getSelectedColumns();
		if(cols.length>0){
			colStart = cols[0];
			colEnd = cols[cols.length-1];
		}
		int[] rows = ex.getSelectedRows();
		if(rows.length>0){
			rowStart = rows[0];
			rowEnd = rows[rows.length-1];
		}*/
		boolean changed =((RDataFrameVariableModel)variableScrollPane.getExTable().getModel()).refresh();
		if(changed){
			variableScrollPane.getRowNamesModel().refresh();
			variableScrollPane.autoAdjustRowWidth();
	/*		
			if(colStart != -1 && colEnd != -1 && rowStart != -1 && rowEnd != -1){
				ex.changeSelection(rowStart, colStart, false, false);
				ex.changeSelection(rowEnd, colEnd, false, true);
			}*/
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
					ex.cutSelection();
			}else if (cmd == "copy") {
					ex.copySelection();
			}else if (cmd == "paste") {
					ex.pasteSelection();
			} else if (cmd == "print"){
				try{
					ex.print(JTable.PrintMode.NORMAL);
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
					//RController.refreshObjects();
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
		((RDataFrameVariableModel)ex.getModel()).removeCachedData();
	}
}
