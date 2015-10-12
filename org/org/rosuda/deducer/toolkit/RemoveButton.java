package org.rosuda.deducer.toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.TransferHandler;

public class RemoveButton extends IconButton implements ActionListener{
	JList rightList;
	JList leftList;
	
	public RemoveButton(String name,VariableSelector var,JList list){
		super("/icons/1leftarrow_32.png", name, null,name);
		this.addActionListener(this);
		leftList = var.getJList();
		rightList=list;
		this.setContentAreaFilled(false);
	}
	
	public RemoveButton(String name,JList left,JList right){
		super("/icons/1leftarrow_32.png", name, null,name);
		this.addActionListener(this);
		leftList = left;
		rightList=right;
		this.setContentAreaFilled(false);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		Object[] objs=rightList.getSelectedValues();
		for(int i=0;i<objs.length;i++){
			if(objs[i]!=null){
				if(!(leftList instanceof DJList && ((DJList)leftList).getTransferMode()==TransferHandler.COPY))
					((DefaultListModel)leftList.getModel()).addElement(objs[i]);
				if(!(rightList instanceof DJList && ((DJList)rightList).getTransferMode()==TransferHandler.COPY))
					((DefaultListModel)rightList.getModel()).removeElement(objs[i]);
			}
		}
		
	}

}
