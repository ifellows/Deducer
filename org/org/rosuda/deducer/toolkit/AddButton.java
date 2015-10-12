package org.rosuda.deducer.toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.TransferHandler;

public class AddButton extends IconButton implements ActionListener{

	JList leftList;
	JList rightList;
	public AddButton(String name,VariableSelector var,JList list){
		super("/icons/1rightarrow_32.png", name, null,name);
		this.addActionListener(this);
		rightList=list;
		leftList = var.getJList();
		this.setContentAreaFilled(false);
	}
	public AddButton(String name,JList left,JList right){
		super("/icons/1rightarrow_32.png", name, null,name);
		this.addActionListener(this);
		rightList=right;
		leftList = left;
		this.setContentAreaFilled(false);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		Object[] objs=leftList.getSelectedValues();
		for(int i=0;i<objs.length;i++){
			if(!(leftList instanceof DJList && ((DJList)leftList).getTransferMode()==TransferHandler.COPY))
				((DefaultListModel)leftList.getModel()).removeElement(objs[i]);
			if(!(rightList instanceof DJList && ((DJList)rightList).getTransferMode()==TransferHandler.COPY))
				if(objs[i]!=null)
					((DefaultListModel)rightList.getModel()).addElement(objs[i]);
		}
		
	}

}
