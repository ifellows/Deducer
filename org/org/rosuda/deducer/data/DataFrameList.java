package org.rosuda.deducer.data;

import java.awt.BorderLayout;


import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;

import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.ListModel;

import org.rosuda.JGR.robjects.*;
import org.rosuda.JGR.util.*;
import org.rosuda.JGR.JGR;
import org.rosuda.JGR.RController;

import org.rosuda.deducer.Deducer;

public class DataFrameList extends javax.swing.JPanel {
	public JList dataList;


	
	public DataFrameList() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			RController.refreshObjects();
			this.setPreferredSize(new java.awt.Dimension(169, 174));
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setBorder(BorderFactory.createTitledBorder("Data Frames"));
			DefaultListModel data = new DefaultListModel();
			for(int i=0;i<Deducer.DATA.size();i++)
				data.addElement(Deducer.DATA.elementAt(i));
			dataList = new JList();
			this.add(dataList, BorderLayout.CENTER);
			dataList.setModel(data);

		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	public void setMultipleSelection(boolean mult){
		if(mult)
			dataList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);			
		else
			dataList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	public RObject getSelectedValue(){
		return (RObject) dataList.getSelectedValue();
	}

	public RObject[] getMultSelection(){
		return (RObject[]) dataList.getSelectedValues();
	}
}
