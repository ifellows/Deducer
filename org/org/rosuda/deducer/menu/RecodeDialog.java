
package org.rosuda.deducer.menu;

import org.rosuda.deducer.Deducer;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import org.rosuda.deducer.toolkit.DJList;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.toolkit.VariableSelector;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.JGR.RController;
import org.rosuda.JGR.JGR;


public class RecodeDialog extends javax.swing.JDialog implements ActionListener {
	private SetRecodingsDialog codes;
	private VariableSelector variableSelector;
	private JList recodeVariableList;
	private JButton intoButton;
	private IconButton removeButton;
	private JButton cancelButton;
	private JButton runButton;
	private JButton defineButton;
	private IconButton addButton;
	private HelpButton help;

	private static DefaultListModel lastListModel;
	private static String lastDataName;

	
	public RecodeDialog(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setTitle("Recode Variables");
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			{
				help = new HelpButton("pmwiki.php?n=Main.RecodeVariables");
				getContentPane().add(help, new AnchorConstraint(867, 934, 968, 21, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));

				help.setPreferredSize(new java.awt.Dimension(32, 32));
			}
			{
				removeButton = new IconButton("/icons/1leftarrow_32.png","Remove",this,"Remove");
				getContentPane().add(removeButton, new AnchorConstraint(530, 425, 680, 350, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				removeButton.setPreferredSize(new java.awt.Dimension(41, 40));
			}
			{
				cancelButton = new JButton();
				getContentPane().add(cancelButton, new AnchorConstraint(767, 934, 835, 801, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				cancelButton.setText("Cancel");
				cancelButton.setPreferredSize(new java.awt.Dimension(84, 23));
				cancelButton.addActionListener(this);
			}
			{
				runButton = new JButton();
				getContentPane().add(runButton, new AnchorConstraint(867, 934, 968, 801, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				runButton.setText("OK");
				runButton.setPreferredSize(new java.awt.Dimension(84, 34));
				runButton.addActionListener(this);
			}
			{
				defineButton = new JButton();
				getContentPane().add(defineButton, new AnchorConstraint(870, 728, 965, 496, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				defineButton.setText("Define Recode");
				defineButton.setPreferredSize(new java.awt.Dimension(147, 32));
				defineButton.addActionListener(this);
			}
			{
				addButton = new IconButton("/icons/1rightarrow_32.png","Add",this,"Add");
				getContentPane().add(addButton, new AnchorConstraint(375, 425, 525, 350, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				addButton.setPreferredSize(new java.awt.Dimension(41, 40));
			}
			{
				intoButton = new JButton();
				getContentPane().add(intoButton, new AnchorConstraint(218, 934, 295, 782, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				intoButton.setText("\u2192 Target");
				intoButton.setPreferredSize(new java.awt.Dimension(96, 26));
				intoButton.setFont(new java.awt.Font("Tahoma",0,10));
				intoButton.addActionListener(this);
			}
			{
				JPanel recodePanel = new JPanel();
				BorderLayout recodePanelLayout = new BorderLayout();
				recodePanel.setLayout(recodePanelLayout);
				recodeVariableList = new RecodeDJList();
				JScrollPane recodeScroller = new JScrollPane(recodeVariableList,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				recodePanel.add(recodeScroller);
				getContentPane().add(recodePanel, new AnchorConstraint(123, 758, 835, 467, AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				recodeVariableList.setModel(new DefaultListModel());
				recodePanel.setPreferredSize(new java.awt.Dimension(184, 240));
				recodePanel.setBorder(BorderFactory.createTitledBorder("Variables to Recode"));
			}
			{
				variableSelector = new VariableSelector();
				getContentPane().add(variableSelector, new AnchorConstraint(72, 325, 835, 21, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				variableSelector.setPreferredSize(new java.awt.Dimension(192, 301));
				variableSelector.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				variableSelector.getJComboBox().addActionListener(this);
				variableSelector.setDropStringSplitter("\u2192");
				if(lastDataName!=null)
					variableSelector.setSelectedData(lastDataName);
			}
			ListModel recodeVariableListModel ;
			if(lastListModel!=null && lastDataName!=null){
				recodeVariableList.setModel(lastListModel);
				String temp;
				boolean exists;
				for(int i=0;i<lastListModel.getSize();i++){
					temp = (String) lastListModel.get(i);
					temp = temp.substring(0, temp.indexOf("\u2192"));
					exists=variableSelector.remove(temp);
					if(!exists){
						recodeVariableList.setModel(new DefaultListModel());
						variableSelector.getJList().setModel(variableSelector.new FilteringModel(
								Deducer.timedEval("names("+variableSelector.getJComboBox().getSelectedItem()
										+")").asStrings()));
						break;
					}
				}
			}
			this.setMinimumSize(new Dimension(450,250));
			this.setSize(640, 371);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	
	public void setDataName(String dataName){
		if(!dataName.equals(variableSelector.getSelectedData()))
			variableSelector.setSelectedData(dataName);
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd=e.getActionCommand();
		if(cmd=="Cancel")
			this.dispose();
		else if(cmd=="Add"){
			JList list = variableSelector.getJList();
			Object[] elements=list.getSelectedValues();
			String temp;
			for(int i=0;i<elements.length;i++){
				if(elements[i]!=null){
					temp = (String) elements[i];
					((DefaultListModel)recodeVariableList.getModel()).addElement(
							temp.concat("\u2192".concat(temp)));
					variableSelector.remove(elements[i]);
				}
			}
		}else if(cmd=="Remove"){
			Object[] elements=recodeVariableList.getSelectedValues();
			String temp;
			for(int i=0;i<elements.length;i++){
				if(elements[i]!=null){
					temp=(String) elements[i];
					((DefaultListModel)recodeVariableList.getModel()).removeElement(elements[i]);
					variableSelector.add(temp.substring(0,temp.indexOf("\u2192")));
				}
			}			
		}else if(cmd=="\u2192 Target"){
			int selectedIndex = recodeVariableList.getSelectedIndex();
			if(selectedIndex==-1)
				return;
			String entry = (String) recodeVariableList.getSelectedValue();
			entry = entry.substring(0,entry.indexOf("\u2192"));
			String newVar = (String) JOptionPane.showInputDialog(this,"Recode "+entry+" into:");
			if(newVar == null || newVar == "")
				return;
			newVar = RController.makeValidVariableName(newVar);
			((DefaultListModel) recodeVariableList.getModel()).removeElementAt(selectedIndex);
			((DefaultListModel) recodeVariableList.getModel()).addElement(entry+"\u2192"+newVar);
		}else if(cmd=="Define Recode"){
			String[] recodes = new String[recodeVariableList.getModel().getSize()];
			for(int i=0;i<recodeVariableList.getModel().getSize();i++)
				recodes[i]=(String)recodeVariableList.getModel().getElementAt(i);
			codes = new SetRecodingsDialog(this,recodes,(String)variableSelector.getJComboBox().getSelectedItem());
			codes.setLocationRelativeTo(this);
			codes.setVisible(true);
		}else if(cmd == "comboBoxChanged"){
			recodeVariableList.setModel(new DefaultListModel());
		}else if(cmd=="OK"){
			if(codes == null || codes.getCodes()==""){
				JOptionPane.showMessageDialog(this, "No Recodings Have been defined.\nClick on the 'Define Recode' button to specify...");
				return;
			}
			String data = (String)variableSelector.getJComboBox().getSelectedItem();
			String fromVars;
			String toVars;
			ArrayList fromList = new ArrayList();
			ArrayList toList = new ArrayList();
			DefaultListModel model = ((DefaultListModel) recodeVariableList.getModel());
			String[]temp;
			for(int i=0;i<model.getSize();i++){
				temp = ((String)model.getElementAt(i)).split("\u2192");
				fromList.add(temp[0]);
				toList.add(temp[1]);
			}
			toVars = RController.makeRStringVector(toList);
			fromVars = RController.makeRStringVector(fromList);
			
			Deducer.execute(data+"["+toVars+"] <- recode.variables("+data+
						"["+fromVars+"] , "+codes.getCodes()+")");
			lastListModel = ((DefaultListModel)recodeVariableList.getModel());
			lastDataName = (String)variableSelector.getJComboBox().getSelectedItem();
			this.dispose();
			Deducer.setRecentData(data);
			//DataFrameWindow.setTopDataWindow(data);
		}
	}
	
	private class RecodeDJList extends DJList{
		public void drop(DropTargetDropEvent dtde) {
			super.drop(dtde);
			int len = this.getModel().getSize();
			String temporary;
			for(int i=0;i<len;i++){
				temporary = (String)this.getModel().getElementAt(i);
				if(temporary.indexOf("\u2192")<0){
					((DefaultListModel)this.getModel()).removeElementAt(i);
					((DefaultListModel)this.getModel()).add(i, temporary+"\u2192"+temporary);
				}
			}
		}
		
	}
}
