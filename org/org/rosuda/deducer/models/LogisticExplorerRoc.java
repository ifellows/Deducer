package org.rosuda.deducer.models;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.rosuda.deducer.toolkit.OkayCancelPanel;


public class LogisticExplorerRoc extends javax.swing.JDialog implements ActionListener, FocusListener {
	private JCheckBox roc;
	private JCheckBox diag;
	private JPanel okayCancel;
	private JCheckBox auc;
	private JCheckBox predProbs;
	private JPanel optionsPanel;
	private LogisticModel model;
	
	public LogisticExplorerRoc(JFrame frame,LogisticModel m) {
		super(frame);
		initGUI();
		setModel(m);
	}
	
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					roc = new JCheckBox();
					getContentPane().add(roc);
					roc.setText("Display ROC Plot");
					roc.setBounds(81, 10, 149, 18);
				}
				{
					optionsPanel = new JPanel();
					getContentPane().add(optionsPanel);
					optionsPanel.setBounds(12, 37, 255, 104);
					optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
					optionsPanel.setLayout(null);
					{
						predProbs = new JCheckBox();
						optionsPanel.add(predProbs);
						predProbs.setText("Predictive probabilities");
						predProbs.setBounds(52, 24, 187, 18);
						predProbs.addFocusListener(this);
					}
					{
						diag = new JCheckBox();
						optionsPanel.add(diag);
						diag.setText("Diagonal line");
						diag.setBounds(52, 49, 187, 18);
						diag.addFocusListener(this);
					}
					{
						auc = new JCheckBox();
						optionsPanel.add(auc);
						auc.setText("AUC");
						auc.setBounds(52, 74, 187, 18);
						auc.addFocusListener(this);
					}
				}
				{
					okayCancel = new OkayCancelPanel(false,false,this);
					getContentPane().add(okayCancel);
					okayCancel.setBounds(93, 147, 174, 37);
				}
			}
			this.setSize(287, 230);
			this.setTitle("Receiver Operating Characteristic");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setModel(LogisticModel mod){
		model = mod;
		roc.setSelected(model.roc.roc);
		predProbs.setSelected(model.roc.predProbs);
		diag.setSelected(model.roc.diag);
		auc.setSelected(model.roc.auc);
	}
	
	public void updateModel(){
		model.roc.roc=roc.isSelected();
		model.roc.predProbs=predProbs.isSelected();
		model.roc.diag=diag.isSelected();
		model.roc.auc=auc.isSelected();		
	}

	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if(cmd=="OK"){
			updateModel();
			dispose();
		}else if(cmd=="Cancel"){
			dispose();
		}
		
	}


	public void focusGained(FocusEvent arg0) {
		roc.setSelected(true);
	}
	public void focusLost(FocusEvent arg0) {}

}
