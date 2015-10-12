package org.rosuda.deducer.toolkit;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDropEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.border.BevelBorder;

import org.rosuda.JGR.util.ErrorMsg;

public class SingletonDJList extends DJList{

	public SingletonDJList(){
		super();
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	}
	
	public void drop(DropTargetDropEvent dtde) {
		try{	
			dtde.acceptDrop(DnDConstants.ACTION_MOVE);
			boolean dropped =false;
			DefaultListModel curModel = (DefaultListModel) this.getModel();
			if(curModel.getSize()>0)
				dtde.rejectDrop();
			else{
				ArrayList ary = (ArrayList)dtde.getTransferable().getTransferData(new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType
					      + ";class=java.util.ArrayList"));
				if(ary.size()>1)
					dtde.rejectDrop();
				else{
				dropped = arrayListHandler.importData(this,dtde.getTransferable());
				DefaultListModel newModel = (DefaultListModel) this.getModel();
				if(newModel.getSize()>1){
					dtde.rejectDrop();
					this.setModel(curModel);
				}
				}
			}
			
			dtde.dropComplete(dropped);
			dragIndex=-1;
			isAtEnd=false;
			repaint();
        }catch (Exception e){
        	new ErrorMsg(e);
        }
	}
	
}
