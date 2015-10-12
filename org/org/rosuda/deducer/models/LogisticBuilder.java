package org.rosuda.deducer.models;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import org.rosuda.deducer.WindowTracker;
import org.rosuda.deducer.toolkit.HelpButton;

public class LogisticBuilder extends GLMBuilder {

	public LogisticBuilder(ModelModel mod) {
		super(mod);
		if(! (mod instanceof LogisticModel)){
			JOptionPane.showMessageDialog(this, "Internal Error: Invalid ModelModel");
			mod=new LogisticModel();
		}
		help.setUrl(HelpButton.baseUrl + "pmwiki.php?n=Main.LogisticModel");
		setModel(mod);
		this.setTitle("Logistic Regression Model Builder");
	}
	
	public void specify() {
		if(! (model instanceof LogisticModel)){
			JOptionPane.showMessageDialog(this, "Internal Error: Invalid ModelModel");
			setModel(new LogisticModel());
		}
		LogisticDialog dia = new LogisticDialog((LogisticModel)model);
		dia.setLocationRelativeTo(this);
		dia.setVisible(true);
		WindowTracker.addWindow(dia);
		this.dispose();
	}
	
	public void editSelectedOutcome(){
		super.editSelectedOutcome();
		LogisticDialogSplitModel m = ((LogisticModel)model).split;
		m.which=3;
		m.expr=(String)((DefaultListModel)outcomes.getModel()).get(outcomes.getSelectedIndex());
	}
	
	public void done(){
		if(! (model instanceof LogisticModel)){
			JOptionPane.showMessageDialog(this, "Internal Error: Invalid ModelModel");
			setModel(new LogisticModel());
			return;
		}
		if(modelTerms.getModel().getSize()<1){
			JOptionPane.showMessageDialog(this, "Please enter some terms into the model.");
			return;
		}
		updateModel();
		LogisticExplorer exp = new LogisticExplorer((LogisticModel)model);
		exp.setLocationRelativeTo(this);
		exp.setVisible(true);
		WindowTracker.addWindow(exp);
		this.dispose();
	}

}
