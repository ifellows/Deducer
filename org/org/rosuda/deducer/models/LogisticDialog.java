package org.rosuda.deducer.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.deducer.WindowTracker;
import org.rosuda.deducer.toolkit.HelpButton;

public class LogisticDialog extends GLMDialog {
	JButton split;
	JLabel splitDef;
	LogisticDialog theDialog = this;
	public static LogisticModel lastLogisticModel;
	
	public LogisticDialog(JDialog d,GLMModel mod) {
		super(d,mod);
		help.setUrl(HelpButton.baseUrl + "pmwiki.php?n=Main.LogisticModel");
		startRefresher();
	}
	public LogisticDialog(JFrame frame,GLMModel mod) {
		super(frame,mod);
		help.setUrl(HelpButton.baseUrl + "pmwiki.php?n=Main.LogisticModel");
		startRefresher();
	}
	public LogisticDialog(GLMModel mod) {
		super(mod);
		help.setUrl(HelpButton.baseUrl + "pmwiki.php?n=Main.LogisticModel");
		startRefresher();
	}
	public LogisticDialog(JFrame frame) {
		this(frame,lastLogisticModel==null ? new LogisticModel() : lastLogisticModel);
	}
	
	protected void initGUI(){
		super.initGUI();
		getContentPane().remove(contPanel);
		getContentPane().add(contPanel, new AnchorConstraint(160, 978, 352, 568, 
				AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
				AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
		split = new JButton();
		split.setText("Split");
		split.setSize(100, 32);
		split.addActionListener(new SplitListener());
		getContentPane().add(split, new AnchorConstraint(108, 978, 352, 575, 
				AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, 
				AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
		splitDef = new JLabel();
		splitDef.setText("");
		splitDef.setSize(100, 32);
		splitDef.setVerticalTextPosition(SwingConstants.CENTER);
		getContentPane().add(splitDef, new AnchorConstraint(115, 995, 352, 710, 
				AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
				AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
		type.setSelectedItem("binomial()");
		type.setVisible(false);
		typeLabel.setVisible(false);
		this.setTitle("Logistic Regression Model");
	}
	
	public void startRefresher(){
		String vname ;
		if(outcome.getModel().getSize()!=0)
			vname= (String)outcome.getModel().getElementAt(0);
		else
			vname="";
		new Thread(new Refresher(vname)).start();
		if(((LogisticModel)model).split!=null)
			splitDef.setText(((LogisticModel)model).split.getLHS());
	}
	public void resetModel(){
		setModel(new LogisticModel());
	}
	public void setModel(GLMModel mod){
		if(!(mod instanceof LogisticModel))
			setModel(new LogisticModel());
		else
			super.setModel(mod);
	}
	
	public void updateModel(){
		super.updateModel();
		model.outcomes=new DefaultListModel();
		model.outcomes.addElement(splitDef.getText());
	}
	
	public void continueClicked(){
		if(!valid())
			return;
		updateModel();
		LogisticBuilder builder = new LogisticBuilder(model);
		builder.setLocationRelativeTo(this);
		builder.setVisible(true);
		WindowTracker.addWindow(builder);
		this.dispose();
	}
	
	public static void setLastModel(GLMModel mod){
		if((mod instanceof LogisticModel))
				lastLogisticModel = (LogisticModel) mod;
		else
			new ErrorMsg("Invalid cast in LogisticDialog.setLastModel");
	}
	
	class SplitListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			try{
			if(outcome.getModel().getSize()==0)
				return;
			if(((LogisticModel)model).split==null ||
					!((LogisticModel)model).split.variable.equals(
							variableSelector.getSelectedData()+"$"+
							(String)outcome.getModel().getElementAt(0)))			
				((LogisticModel)model).split=new LogisticDialogSplitModel(
						variableSelector.getSelectedData()+
						"$"+(String)outcome.getModel().getElementAt(0));
			LogisticDialogSplit s = new LogisticDialogSplit(theDialog,((LogisticModel)model).split);
			s.setLocationRelativeTo(split);
			s.setVisible(true);
			splitDef.setText(((LogisticModel)model).split.getLHS());
			}catch(Exception e){new ErrorMsg(e);}
			
		}
		
	}
	
	class Refresher implements Runnable {
		String variableName = "";
		public Refresher(String name) {
			variableName = name;
		}

		public void run() {
			while (true)
				try {
					Thread.sleep(1000);
					Runnable doWorkRunnable = new Runnable() {
						public void run() { 
							
							if(outcome.getModel().getSize()==0){
								if(variableName!=""){
									splitDef.setText("");
									((LogisticModel)model).split=null;
									variableName="";
								}
							}else{
								if(!variableName.equals(outcome.getModel().getElementAt(0))){
									((LogisticModel)model).split=new LogisticDialogSplitModel(
											variableSelector.getSelectedData()+
											"$"+(String)outcome.getModel().getElementAt(0));
									splitDef.setText(((LogisticModel)model).split.getLHS());
									variableName=(String)outcome.getModel().getElementAt(0);
								}
							}
						}};
					SwingUtilities.invokeLater(doWorkRunnable);
				} catch (Exception e) {
					new ErrorMsg(e);
				}
		}
	}
	
}
