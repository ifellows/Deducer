package org.rosuda.deducer.models;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.rosuda.deducer.data.*;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;



public class GLMExplorerTests extends javax.swing.JDialog implements ListSelectionListener, ActionListener {
	private JPanel testPanel;
	private JComboBox direction;
	private JLabel directionLabel;
	private JScrollPane testScroller;
	private ExScrollableTable matrixScroller;
	private JPanel okayCancelPanel;
	private JButton removeRow;
	private JButton addRow;
	private JPanel matrixPanel;
	private JButton remove;
	private JButton add;
	private DefaultListModel testModel;
	private JList testList;
	private ExTable matrix;
	private ExDefaultTableModel matrixModel;
	private Vector terms;
	private GLMModel model;

	
	public GLMExplorerTests(JFrame frame,Vector t,GLMModel mod) {
		super(frame);
		terms = t;
		this.setModal(true);
		initGUI();
		setModel(mod);
	}
	


	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					testPanel = new JPanel();
					BorderLayout testPanelLayout = new BorderLayout();
					testPanel.setLayout(testPanelLayout);
					getContentPane().add(testPanel);
					testPanel.setBounds(183, 12, 264, 185);
					testPanel.setBorder(BorderFactory.createTitledBorder("Hypotheses"));
					{
						testScroller = new JScrollPane();
						testPanel.add(testScroller, BorderLayout.CENTER);
						{
							testModel = new DefaultListModel();
							testList = new JList();
							testScroller.setViewportView(testList);
							testList.setModel(testModel);
							testList.addListSelectionListener(this);
							testList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						}
					}
				}
				{
					add = new IconButton("/icons/edit_add_32.png","Add",this,"Add");
					getContentPane().add(add);
					add.setBounds(134, 64, 38, 38);
				}
				{
					remove = new IconButton("/icons/edit_remove_32.png","Delete",this,"Delete");
					getContentPane().add(remove);
					remove.setBounds(134, 113, 38, 38);
				}
				{
					matrixPanel = new JPanel();
					BorderLayout matrixPanelLayout = new BorderLayout();
					matrixPanel.setLayout(matrixPanelLayout);
					getContentPane().add(matrixPanel);
					matrixPanel.setBounds(62, 247, 549, 165);
					matrixPanel.setBorder(BorderFactory.createTitledBorder("Build Hypothesis"));
					{
						matrixModel = new ExDefaultTableModel();
						matrix = new ExTable(matrixModel);
						matrix.getTableHeader().removeMouseListener(matrix.getColumnListener());
						matrixScroller = new ExScrollableTable(matrix);
						matrixScroller.removeRowListener();
						matrixPanel.add(matrixScroller, BorderLayout.CENTER);
					}
				}
				{
					addRow = new IconButton("/icons/edit_add_32.png","Add row",this,"Add row");
					getContentPane().add(addRow);
					addRow.setBounds(12, 314, 38, 38);
				}
				{
					removeRow =new IconButton("/icons/edit_remove_32.png","Remove last row",this,"Remove last row");
					getContentPane().add(removeRow);
					removeRow.setBounds(12, 358, 38, 38);
				}
				{
					okayCancelPanel = new OkayCancelPanel(true,false,this);
					getContentPane().add(okayCancelPanel);
					okayCancelPanel.setBounds(306, 424, 304, 45);
					{
						directionLabel = new JLabel();
						getContentPane().add(directionLabel);
						directionLabel.setText("Direction:");
						directionLabel.setBounds(183, 203, 72, 14);
						directionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					}
					{
						ComboBoxModel directionModel = 
							new DefaultComboBoxModel(
									new String[] { "two.sided", "less", "greater"});
						direction = new JComboBox();
						getContentPane().add(direction);
						direction.setModel(directionModel);
						direction.setBounds(262, 200, 127, 21);
					}
				}
			}
			this.setTitle("Build Hypotheses");
			this.setSize(635, 520);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setModel(GLMModel mod) {
		model=mod;
		direction.setSelectedItem(model.tests.direction);
		ExDefaultTableModel tmod;
		String[] colNames = new String[terms.size()+1];
		for(int i=0;i<terms.size();i++)
			colNames[i]=(String)terms.get(i);
		colNames[terms.size()]="=";
		for(int i=0;i<model.tests.size();i++){
			tmod = model.tests.getDuplicateTableModel(i);
			if(tmod.getColumnCount()==terms.size()+1){
				tmod.setColumnIdentifiers(colNames);
				LHTest test = new LHTest(model.tests.getName(i));
				test.hm=tmod;
				testModel.addElement(test);
			}
		}
	}
	
	public void updateModel(){
		model.tests.reset();
		model.tests.direction=(String)direction.getSelectedItem();
		for(int i=0;i<testModel.getSize();i++){
			LHTest test = (LHTest) testModel.get(i);
			model.tests.addTest(test.name,test.hm);
		}
	}
	
	public void valueChanged(ListSelectionEvent arg0) {
		LHTest t = (LHTest) testList.getSelectedValue();
		if(t!=null){
			matrixModel = t.hm;
			matrix.setModel(t.hm);
			matrixScroller.getRowNamesModel().initHeaders(matrixModel.getRowCount());
			matrixScroller.getRowNamesModel().refresh();		
		}else{
			matrixModel = new ExDefaultTableModel();
			matrix.setModel(matrixModel);
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		String cmd=arg0.getActionCommand();
		if(cmd=="OK"){
			for(int i=0;i<testModel.size();i++){
				LHTest t = (LHTest)testModel.get(i);
				if(!t.isValid()){
					JOptionPane.showMessageDialog(this, "Hypothesis "+testModel.getElementAt(i).toString()+
														" must contain only numbers, and at least one must be non-zero");
					return;
				}
			}
			updateModel();
			this.dispose();
		}else if(cmd=="Cancel"){
			this.dispose();
		}else if(cmd=="Reset"){
			setModel(model);
		}else if(cmd=="Add"){
			String name = JOptionPane.showInputDialog(this, "Name of Hypothesis test:",
													"hypothesis");
			if(name!=null && name!=""){
				testModel.addElement(new LHTest(name));
			}
		}else if(cmd=="Delete"){
			if(testList.getSelectedIndex()>=0){
				testModel.remove(testList.getSelectedIndex());
			}
		}else if(cmd=="Add row"){
			if(testList.getSelectedIndex()>=0){
				int ncol = matrixModel.getColumnCount();
				String[] newRow = new String[ncol];
				for(int i=0;i<newRow.length;i++)
					newRow[i]="0";
				matrixModel.addRow(newRow);
				matrixScroller.getRowNamesModel().initHeaders(matrixModel.getRowCount());
				matrixScroller.getRowNamesModel().refresh();
			}else{
				String name = JOptionPane.showInputDialog(this, "Name of Hypothesis test:",
													"hypothesis");
				if(name!=null && name!=""){
					testModel.addElement(new LHTest(name));
					testList.setSelectedIndex(testModel.getSize()-1);
				}				
			}
		}else if(cmd.equals("Remove last row")){
			if(matrixModel.getRowCount()>=2){
				matrixModel.setRowCount(matrixModel.getRowCount()-1);
				matrixScroller.getRowNamesModel().initHeaders(matrixModel.getRowCount());
				matrixScroller.getRowNamesModel().refresh();	
			}
		}
	}
	
	
	class LHTest{
		public String name = "";
		public ExDefaultTableModel hm = new ExDefaultTableModel();
		public LHTest(String n){
			name=n;
			for(int i=0;i<terms.size();i++)
				hm.addColumn(terms.get(i));
			hm.addColumn("=");
			int ncol = hm.getColumnCount();
			String[] newRow = new String[ncol];
			for(int i=0;i<newRow.length;i++)
				newRow[i]="0";
			hm.addRow(newRow);
		}
		public String toString(){
			return name;
		}
		
		public boolean isValid(){
			boolean allZero=true;
			for(int i=0;i<hm.getRowCount();i++)
				for(int j=0;j<hm.getColumnCount();j++){
					if(!((String)hm.getValueAt(i, j)).trim().matches("^[-+]?[0-9]*\\.?[0-9]+$"))
						return false;
					if(!((String)hm.getValueAt(i, j)).trim().equals("0"))
						allZero=false;
				}
			return !allZero;
		}
	}
}


