package org.rosuda.deducer.toolkit;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.JGR.util.ErrorMsg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;


public class VariableSelectionDialog extends JDialog implements ActionListener {
	private VariableSelector selector;
	private OkayCancelPanel okcan;
	private ArrayList selectedVariables =new ArrayList();

	public VariableSelectionDialog(JFrame frame) {
		super(frame,true);
		initGUI();
	}
	
	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			{
				okcan = new OkayCancelPanel(false,false,this);
				getContentPane().add(okcan, new AnchorConstraint(858, 892, 983, 128,
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				okcan.getApproveButton().setText("Select");
				
			}

			{
				selector = new VariableSelector();
				getContentPane().add(selector, new AnchorConstraint(40, 1002, 836, 2, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				selector.setPreferredSize(new java.awt.Dimension(238, 233));
				selector.setBorder(BorderFactory.createTitledBorder("Variables"));
			}
			this.setTitle("Select a Variable");
			this.setSize(300, 600);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		try{
			String cmd = arg0.getActionCommand();
			if(cmd == "Cancel"){
				this.dispose();
			}else if(cmd == "Select"){
				Object[] sel =  selector.getJList().getSelectedValues();
				String data = (String)selector.getJComboBox().getSelectedItem();
				for(int i = 0;i<sel.length;i++){
					selectedVariables.add(data+"$"+sel[i]);
				}
				this.dispose();
			}
		}catch(Exception er){
			new ErrorMsg(er);
		}
	}
	
	
	public void SetSingleSelection(boolean selectMode){
		if(selectMode)
			selector.getJList().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		else
			selector.getJList().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}
	
	public void setComboBox(String dataName){
		selector.getJComboBox().setSelectedItem(dataName);
	}
	
	public List getSelectedVariables(){
		return selectedVariables;
	}
	
	public String getSelecteditem(){
		if(selectedVariables.size()>0)
			return (String) selectedVariables.get(0);
		else
			return null;
	}
	
	public void setRFilter(String rFunction){
		selector.setRFilter(rFunction);
	}
	
	
}
