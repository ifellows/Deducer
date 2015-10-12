package org.rosuda.deducer.menu;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.JGR.util.ErrorMsg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class SubsetPanel extends JPanel implements ActionListener {
	protected JScrollPane subsetScroller;
	protected JTextArea subset;
	protected JComboBox subsetHistory;
	protected JComboBox dataComboBox;

	
	public SubsetPanel(JComboBox dataSelect) {
		super();
		dataSelect.addActionListener(this);
		dataComboBox=dataSelect;
		initGUI();
		String select = (String) dataComboBox.getSelectedItem();
		if(select!=null){
			DefaultComboBoxModel model = SubsetDialog.getRecent( select);
			if(model!=null)
				subsetHistory.setModel(model);
		}
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(400, 54));
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			{
				subsetScroller = new JScrollPane();
				this.add(subsetScroller, new AnchorConstraint(9, 1000, 24, 1, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
				subsetScroller.setPreferredSize(new java.awt.Dimension(400, 30));
				{
					subset = new JTextArea();
					subset.setText("");
					subsetScroller.setViewportView(subset);
				}
			}
			{
				ComboBoxModel subsetHistoryModel = 
					new DefaultComboBoxModel();
				subsetHistory = new JComboBox();
				this.add(subsetHistory, new AnchorConstraint(500, 1000, 1, 1, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
				subsetHistory.setModel(subsetHistoryModel);
				subsetHistory.setPreferredSize(new java.awt.Dimension(400, 21));
				subsetHistory.addActionListener(this);
			}
			subsetScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	public String getText(){
		return subset.getText();
	}
	
	public void setText(String text){
		subset.setText(text);
	}
	
	public void refreshComboBox(){
		String select = (String) dataComboBox.getSelectedItem();
		if(select!=null){
			DefaultComboBoxModel model = SubsetDialog.getRecent( select);
			if(model!=null)
				subsetHistory.setModel(model);
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==dataComboBox){
			subset.setText("");
			subsetHistory.setModel(SubsetDialog.getRecent( (String) dataComboBox.getSelectedItem()));
		}else{
			subset.setText((String)subsetHistory.getSelectedItem());
		}
	}
	


}
