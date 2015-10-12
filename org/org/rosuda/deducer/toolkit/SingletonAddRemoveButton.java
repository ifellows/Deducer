package org.rosuda.deducer.toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.rosuda.JGR.util.ErrorMsg;

public class SingletonAddRemoveButton extends IconButton implements ActionListener, ListDataListener{
	private SingletonDJList singList;
	private String[] cmdText;
	private String[] tooltipText;
	private SingletonAddRemoveButton theButton=this;
	private JList leftList;
	public SingletonAddRemoveButton(String[] tooltip, ActionListener al,
			String[] cmd,SingletonDJList lis){
		super("/icons/1rightarrow_32.png", tooltip[0], al,cmd[0]);
		singList=lis;
		tooltipText=tooltip;
		cmdText=cmd;
		singList.getModel().addListDataListener(this);
		this.setContentAreaFilled(false);
	}
	
	public SingletonAddRemoveButton(String[] tooltip,String[] cmd,SingletonDJList lis,VariableSelector var){
		super("/icons/1rightarrow_32.png", tooltip[0], null,cmd[0]);
		this.addActionListener(this);
		singList=lis;
		tooltipText=tooltip;
		cmdText=cmd;
		leftList = var.getJList();
		singList.getModel().addListDataListener(this);
		this.setContentAreaFilled(false);
	}
	
	public SingletonAddRemoveButton(String[] tooltip,String[] cmd,SingletonDJList lis,JList leftList){
		super("/icons/1rightarrow_32.png", tooltip[0], null,cmd[0]);
		this.addActionListener(this);
		singList=lis;
		tooltipText=tooltip;
		cmdText=cmd;
		this.leftList = leftList;
		singList.getModel().addListDataListener(this);
		this.setContentAreaFilled(false);
	}
	
	public void setList(SingletonDJList list){
		singList.getModel().removeListDataListener(this);
		singList = list;
		singList.getModel().addListDataListener(this);
	}
	
	public void refreshListListener(){
		singList.getModel().addListDataListener(this);
		refresh();
	}
	
	
	public void refresh(){
		if(singList.getModel().getSize()>0){
			theButton.setToolTipText(tooltipText[1]);
			theButton.setActionCommand(cmdText[1]);
			ImageIcon icon = new ImageIcon(getClass().getResource("/icons/1leftarrow_32.png"));
			theButton.setIcon(icon);
		}else{
			theButton.setToolTipText(tooltipText[0]);
			theButton.setActionCommand(cmdText[0]);
			ImageIcon icon = new ImageIcon(getClass().getResource("/icons/1rightarrow_32.png"));
			theButton.setIcon(icon);
		}
	}


	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals(cmdText[0])){
			Object[] objs=leftList.getSelectedValues();
			if(objs.length>1){
				leftList.setSelectedIndex(leftList.getSelectedIndex());
			}else if(objs.length==1 && singList.getModel().getSize()==0){
				((DefaultListModel)leftList.getModel()).removeElement(objs[0]);
				((DefaultListModel)singList.getModel()).addElement(objs[0]);
			}
		}else{
			DefaultListModel tmpModel =(DefaultListModel)singList.getModel();
			if(tmpModel.getSize()>0){
				((DefaultListModel)leftList.getModel()).addElement(tmpModel.remove(0));	
			}
		}
		
	}

	public void intervalAdded(ListDataEvent e) {
		refresh();
	}

	public void intervalRemoved(ListDataEvent e) {
		refresh();
	}

	public void contentsChanged(ListDataEvent e) {
		refresh();
	}
	
}
