package org.rosuda.deducer.data;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Frame;
import java.awt.Point;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.JGR.util.*;
import org.rosuda.JGR.robjects.*;
import org.rosuda.deducer.toolkit.OkayCancelPanel;
import org.rosuda.ibase.Common;


public class DataFrameSelector extends javax.swing.JDialog implements ActionListener {
	private DataFrameList jPanel1;
	private OkayCancelPanel okcan;
	
	private RObject selectedData = null;
	
	public DataFrameSelector(Frame owner) {
		super(owner,true);
		initGUI();
	}
	
	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

			jPanel1 = new DataFrameList();
			getContentPane().add(jPanel1, new AnchorConstraint(37, 866, 710, 146, 
					AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
					AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			jPanel1.setPreferredSize(new java.awt.Dimension(165, 224));

			okcan = new OkayCancelPanel(false,false,this);
			getContentPane().add(okcan, new AnchorConstraint(775, 948, 898, 61, 
					AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
					AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			okcan.getApproveButton().setText("Select");
			this.setTitle("Select a Data Frame");
			pack();
			this.setSize(211, 334);
			Point center = new Point(Common.screenRes.width/2-this.getWidth()/2,40);
			setLocation(center);
			this.setVisible(true);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	
	public RObject getSelection(){
		return selectedData;
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd == "Cancel"){
			selectedData=null;
			setVisible(false); 
		}else if(cmd == "Select"){
			selectedData = jPanel1.getSelectedValue();
			setVisible(false); 
		}

	  }
}
