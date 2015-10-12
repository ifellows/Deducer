package org.rosuda.deducer.models;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.rosuda.deducer.WindowTracker;
import org.rosuda.deducer.toolkit.HelpButton;

public class GLMBuilder extends ModelBuilder {

	public GLMBuilder(ModelModel mod) {
		super(mod);
		if(! (mod instanceof GLMModel)){
			JOptionPane.showMessageDialog(this, "Internal Error: Invalid ModelModel");
			mod=new GLMModel();
		}
		help.setUrl(HelpButton.baseUrl + "pmwiki.php?n=Main.GeneralizedLinearModel");
		setModel(mod);
	}
	

	public void specify() {
		if(! (model instanceof GLMModel)){
			JOptionPane.showMessageDialog(this, "Internal Error: Invalid ModelModel");
			setModel(new GLMModel());
		}
		GLMDialog dia = new GLMDialog((GLMModel)model);
		dia.setLocationRelativeTo(this);
		dia.setVisible(true);
		WindowTracker.addWindow(dia);
		this.dispose();
	}
	
	public void done(){
		if(! (model instanceof GLMModel)){
			JOptionPane.showMessageDialog(this, "Internal Error: Invalid ModelModel");
			setModel(new GLMModel());
			return;
		}
		if(modelTerms.getModel().getSize()<1){
			JOptionPane.showMessageDialog(this, "Please enter some terms into the model.");
			return;
		}
		updateModel();
		GLMExplorer exp = new GLMExplorer((GLMModel)model);
		exp.setLocationRelativeTo(this);
		exp.setVisible(true);
		WindowTracker.addWindow(exp);
		this.dispose();
	}
	
	public void reset(){
		modelTermsModel.removeAllElements();
	}


}
