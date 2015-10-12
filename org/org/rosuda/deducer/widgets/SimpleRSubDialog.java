package org.rosuda.deducer.widgets;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple dialog designed to be used with an RDialog (or SimpleRDialog) owner.
 * @author Ian
 *
 */
public class SimpleRSubDialog extends RDialog implements ActionListener{

	/*
	 * JDialog overrides
	 */
	
	public SimpleRSubDialog(){
		super();
	}
	public SimpleRSubDialog(Dialog owner){
		super(owner);
	}
	public SimpleRSubDialog(Dialog owner, boolean modal){
		super(owner, modal);
	}
	public SimpleRSubDialog(Dialog owner, String title){
		super(owner, title);
	}
	public SimpleRSubDialog(Dialog owner, String title, boolean modal){
		super(owner, title, modal);
	}
	public SimpleRSubDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc){
		super(owner, title, modal,gc);
	}
	public SimpleRSubDialog(Frame owner){
		super(owner);
	}
	public SimpleRSubDialog(Frame owner, boolean modal){
		super(owner, modal);
	}
	public SimpleRSubDialog(Frame owner, String title){
		super(owner, title);
	}
	public SimpleRSubDialog(Frame owner, String title, boolean modal){
		super(owner, title, modal);
	}
	public SimpleRSubDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc){
		super(owner, title, modal,gc);
	}
	/*
	 * end
	 */
	
	public void initGUI(){
		super.initGUI();
		setOkayCancel(false,false,this);
		this.okayCancelPanel.setPreferredSize(new Dimension(180,40));
	}
	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if(cmd == "OK"){
			this.completed();
			this.setVisible(false);
		}else if(cmd=="Cancel"){
			if(parent==null)
				clearWorkingModels();
			this.setVisible(false);
		}
	}
	
	
	
}
