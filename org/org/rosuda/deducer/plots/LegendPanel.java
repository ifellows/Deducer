package org.rosuda.deducer.plots;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.rosuda.deducer.data.ExDefaultTableModel;
import org.rosuda.deducer.data.ExScrollableTable;
import org.rosuda.deducer.data.ExTable;
import org.rosuda.deducer.widgets.param.RFunctionListChooserWidget;


public class LegendPanel extends JPanel implements ActionListener {
	private JLabel nameLabel;
	private ExScrollableTable tableScroller;
	private JPanel tablePanel;
//	private JCheckBox showCheckBox;
	private JTextField nameField;
	private ExTable table;
	private ExDefaultTableModel tableModel;
	private JButton addButton;
	private RFunctionListChooserWidget breaksWidget;
	private RFunctionListChooserWidget labelsWidget;
	private RFunctionListChooserWidget guideWidget;
	
	private boolean numeric = true;
	
	public LegendPanel() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			//this.setBorder(BorderFactory.createLoweredBevelBorder());
			this.setPreferredSize(new java.awt.Dimension(255, 250));
			int endTable = 110;
			{
				tablePanel = new JPanel();
				BorderLayout tablePanelLayout = new BorderLayout();
				tablePanel.setLayout(tablePanelLayout);
				this.add(tablePanel, new AnchorConstraint(65, 10, 220, 10, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
				tablePanel.setPreferredSize(new java.awt.Dimension(125, 109));
				{
					tableModel = new LegendTableModel();
					tableModel.addColumn("Value");
					tableModel.addColumn("Label");
					tableModel.addRow(new String[] {"",""});
					table = new ExTable(tableModel);
					table.getTableHeader().removeMouseListener(table.getColumnListener());
				}
				{
					tableScroller = new ExScrollableTable(table);
					tablePanel.add(tableScroller, BorderLayout.CENTER);
				}
			}
/*			{
				showCheckBox = new JCheckBox();
				this.add(showCheckBox, new AnchorConstraint(822, 0, 968, 0, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
				showCheckBox.setText("Show");
				showCheckBox.setSelected(true);
				showCheckBox.setPreferredSize(new java.awt.Dimension(133, 19));
			}
*/			{
				nameField = new JTextField("");
				this.add(nameField, new AnchorConstraint(40, 10, 200, 10, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
				nameField.setPreferredSize(new java.awt.Dimension(125, 22));
			}
			{
				nameLabel = new JLabel();
				this.add(nameLabel, new AnchorConstraint(25, 922, 90, 10, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
				nameLabel.setText("Title");
				nameLabel.setPreferredSize(new java.awt.Dimension(125, 15));
			}
			{
				addButton = new JButton("+");
				this.add(addButton, new AnchorConstraint(175, 10, 9, 10, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));		
				addButton.setPreferredSize(new java.awt.Dimension(40, 22));
				addButton.addActionListener(this);
			}
			if(numeric){
				{
					breaksWidget = new RFunctionListChooserWidget();
					this.add(breaksWidget, new AnchorConstraint(210, 10, 9, 10, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
				}
				{
					labelsWidget = new RFunctionListChooserWidget();
					this.add(labelsWidget, new AnchorConstraint(240, 10, 9, 10, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
				}
			}
			{
				guideWidget = new RFunctionListChooserWidget();
				this.add(guideWidget, new AnchorConstraint(270, 10, 9, 10, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isNumeric(){
		return numeric;
	}
	
	public void setNumeric(boolean num){
		if(num && !numeric){
			this.add(breaksWidget, new AnchorConstraint(210, 10, 9, 10, 
					AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, 
					AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
			this.add(labelsWidget, new AnchorConstraint(240, 10, 9, 10, 
					AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, 
					AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));			
			this.repaint();
		}
		if(!num && numeric){
			this.remove(breaksWidget);
			this.remove(labelsWidget);
			this.repaint();
		}
		numeric = num;
		
	}
	
	public void showGuide(boolean show){
		guideWidget.setVisible(show);
	}
	
	public String getName(){
		if(nameField==null)
			return "";
		return nameField.getText();
	}
	
	public void setName(String n){
		nameField.setText(n);
	}
	
	public RFunctionListChooserWidget getBreaksWidget(){
		return breaksWidget;
	}
	public RFunctionListChooserWidget getLabelsWidget(){
		return labelsWidget;
	}
	
	public RFunctionListChooserWidget getGuideWidget(){
		return guideWidget;
	}
	
	
	public ExDefaultTableModel getTableModel(){
		return tableModel;
	}
	
	public void setTableModel(ExDefaultTableModel tm){
		tableModel = tm;
		table.setModel(tm);
		tableScroller.getRowNamesModel().initHeaders(tableModel.getRowCount());
	}
/*	
	public boolean getShowLegend(){
		return showCheckBox.isSelected();
	}
	
	public void setShowLegend(boolean b){
		showCheckBox.setSelected(b);
	}
*/	

	class LegendTableModel extends ExDefaultTableModel{
	
		public void removeRow(int row){
			super.removeRow(row);
			tableScroller.getRowNamesModel().initHeaders(tableModel.getRowCount());
		}
		public void insertNewRow(int index){
			this.insertRow(index, new String[] {"",""});
			tableScroller.getRowNamesModel().initHeaders(tableModel.getRowCount());
		}
		public void removeColumn(int row){}
		public void insertNewColumn(int index){}
	}

	public void actionPerformed(ActionEvent a) {
		String cmd = a.getActionCommand();
		if(cmd == "+"){
			tableModel.addRow(new String[] {"",""});
			tableScroller.getRowNamesModel().initHeaders(tableModel.getRowCount());
		}
	}
}
