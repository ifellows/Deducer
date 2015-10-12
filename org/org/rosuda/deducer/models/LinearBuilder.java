package org.rosuda.deducer.models;

import javax.swing.JOptionPane;

import org.rosuda.deducer.WindowTracker;
import org.rosuda.deducer.toolkit.HelpButton;

public class LinearBuilder extends GLMBuilder {

	public LinearBuilder(ModelModel mod) {
		super(mod);
		if(! (mod instanceof LinearModel)){
			JOptionPane.showMessageDialog(this, "Internal Error: Invalid ModelModel");
			mod=new LinearModel();
		}
		setModel(mod);
		help.setUrl(HelpButton.baseUrl + "pmwiki.php?n=Main.LinearModel");
		this.setTitle("Linear Regression Model Builder");
	}
	
	public void specify() {
		if(! (model instanceof LinearModel)){
			JOptionPane.showMessageDialog(this, "Internal Error: Invalid ModelModel");
			setModel(new LinearModel());
		}
		LinearDialog dia = new LinearDialog((LinearModel)model);
		dia.setLocationRelativeTo(this);
		dia.setVisible(true);
		WindowTracker.addWindow(dia);
		this.dispose();
	}
	
	public void done(){
		if(! (model instanceof LinearModel)){
			JOptionPane.showMessageDialog(this, "Internal Error: Invalid ModelModel");
			setModel(new LinearModel());
			return;
		}
		if(modelTerms.getModel().getSize()<1){
			JOptionPane.showMessageDialog(this, "Please enter some terms into the model.");
			return;
		}
		updateModel();
		LinearExplorer exp = new LinearExplorer((LinearModel)model);
		exp.setLocationRelativeTo(this);
		exp.setVisible(true);
		WindowTracker.addWindow(exp);
		this.dispose();
	}
	

}
