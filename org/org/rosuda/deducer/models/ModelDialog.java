package org.rosuda.deducer.models;

import javax.swing.JDialog;
import javax.swing.JFrame;

public abstract class ModelDialog extends JDialog{

	public ModelDialog(JFrame frame){
		super(frame);
	}
	public ModelDialog(JDialog d){
		super(d);
	}
	public abstract void callBack(JDialog d,ModelModel mod);

}