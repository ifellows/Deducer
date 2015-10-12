package org.rosuda.deducer.models;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.rosuda.JGR.robjects.RObject;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.OkayCancelPanel;



public class GLMExplorerExport extends javax.swing.JDialog implements ActionListener {
	private JPanel residPanel;
	private JPanel predPanel;
	private JPanel okayCancel;
	private JCheckBox pred;
	private JCheckBox hat;
	private JCheckBox dffits;
	private JCheckBox dfbeta;
	private JPanel objectsPanel;
	private JLabel modelNameLabel;
	private JCheckBox keepModel;
	private JTextField modelName;
	private JCheckBox covratio;
	private JCheckBox cooks;
	private JCheckBox linearPred;
	private JCheckBox stResid;
	private JCheckBox sdResid;
	private JCheckBox resid;
	private JComboBox predData;
	private JPanel diagnosticPanel;
	private GLMModel model;

	
	public GLMExplorerExport(JFrame frame,GLMModel mod) {
		super(frame);
		initGUI();
		setModel(mod);
		this.setModal(true);
	}
	
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					residPanel = new JPanel();
					getContentPane().add(residPanel);
					residPanel.setBounds(12, 12, 173, 100);
					residPanel.setBorder(BorderFactory.createTitledBorder("Residuals"));
					residPanel.setLayout(null);
					{
						resid = new JCheckBox();
						residPanel.add(resid);
						resid.setText("Residuals");
						resid.setBounds(17, 20, 139, 19);
					}
					{
						sdResid = new JCheckBox();
						residPanel.add(sdResid);
						sdResid.setText("Standardized");
						sdResid.setBounds(17, 45, 139, 19);
					}
					{
						stResid = new JCheckBox();
						residPanel.add(stResid);
						stResid.setText("Studentized");
						stResid.setBounds(17, 70, 139, 19);
					}
				}
				{
					predPanel = new JPanel();
					getContentPane().add(predPanel);
					predPanel.setBounds(197, 12, 191, 100);
					predPanel.setBorder(BorderFactory.createTitledBorder("Predicted"));
					predPanel.setLayout(null);
					{
						pred = new JCheckBox();
						predPanel.add(pred);
						pred.setText("Response");
						pred.setBounds(17, 20, 127, 19);
					}
					{
						linearPred = new JCheckBox();
						predPanel.add(linearPred);
						linearPred.setText("Linear");
						linearPred.setBounds(17, 45, 127, 19);
					}
					{
						predData = new JComboBox();
						Vector d = Deducer.getData();
						predData.addItem("");
						for(int i=0;i<d.size();i++){
							predData.addItem(((RObject)d.get(i)).getName());
						}
						
						JLabel lab = new JLabel("Data:");
						predPanel.add(lab);
						lab.setBounds(20,72,37,22);
						predPanel.add(predData);
						predData.setBounds(67,72,100,22);
					}
				}
				{
					diagnosticPanel = new JPanel();
					getContentPane().add(diagnosticPanel);
					diagnosticPanel.setBounds(12, 118, 376, 101);
					diagnosticPanel.setBorder(BorderFactory.createTitledBorder("Diagnostics"));
					diagnosticPanel.setLayout(null);
					{
						cooks = new JCheckBox();
						diagnosticPanel.add(cooks);
						cooks.setText("Cooks Distance");
						cooks.setBounds(17, 20, 148, 19);
					}
					{
						dfbeta = new JCheckBox();
						diagnosticPanel.add(dfbeta);
						dfbeta.setText("DFBETA");
						dfbeta.setBounds(17, 45, 148, 19);
					}
					{
						dffits = new JCheckBox();
						diagnosticPanel.add(dffits);
						dffits.setText("DFFITS");
						dffits.setBounds(17, 70, 148, 19);
					}
					{
						hat = new JCheckBox();
						diagnosticPanel.add(hat);
						hat.setText("Hat");
						hat.setBounds(171, 20, 148, 19);
					}
					{
						covratio = new JCheckBox();
						diagnosticPanel.add(covratio);
						covratio.setText("Cov Ratio");
						covratio.setBounds(171, 45, 188, 19);
					}
				}
				{
					okayCancel = new OkayCancelPanel(false,false,this);
					getContentPane().add(okayCancel);
					okayCancel.setBounds(180, 297, 208, 35);
					{
						objectsPanel = new JPanel();
						getContentPane().add(objectsPanel);
						objectsPanel.setBounds(42, 219, 279, 72);
						objectsPanel.setBorder(BorderFactory.createTitledBorder("Objects"));
						objectsPanel.setLayout(null);
						{
							modelNameLabel = new JLabel();
							objectsPanel.add(modelNameLabel);
							modelNameLabel.setText("Model Name:");
							modelNameLabel.setBounds(17, 25, 103, 15);
							modelNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
						}
						{
							modelName = new JTextField();
							objectsPanel.add(modelName);
							modelName.setText("<auto>");
							modelName.setBounds(120, 21, 142, 22);
						}
						{
							keepModel = new JCheckBox();
							objectsPanel.add(keepModel);
							keepModel.setText("Keep");
							keepModel.setBounds(120, 43, 106, 19);
						}
					}
				}
			}
			this.setTitle("Save Objects");
			this.setSize(400, 366);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateModel(){
		model.export.resid=resid.isSelected();
		model.export.sdresid=sdResid.isSelected();
		model.export.stresid=stResid.isSelected();
		model.export.pred=pred.isSelected();
		model.export.linearPred=linearPred.isSelected();
		model.export.cooks=cooks.isSelected();
		model.export.dfbeta=dfbeta.isSelected();
		model.export.dffits=dffits.isSelected();
		model.export.hats=hat.isSelected();
		model.export.covratio=covratio.isSelected();
		model.export.keepModel=keepModel.isSelected();
		model.export.modelName=modelName.getText();
		model.export.data = predData.getSelectedItem().toString();
	}
	
	public void setModel(GLMModel mod){
		model=mod;
		resid.setSelected(model.export.resid);
		sdResid.setSelected(model.export.sdresid);
		stResid.setSelected(model.export.stresid);
		pred.setSelected(model.export.pred);
		linearPred.setSelected(model.export.linearPred);
		cooks.setSelected(model.export.cooks);
		dfbeta.setSelected(model.export.dfbeta);
		dffits.setSelected(model.export.dffits);
		hat.setSelected(model.export.hats);
		covratio.setSelected(model.export.covratio);
		keepModel.setSelected(model.export.keepModel);
		modelName.setText(model.export.modelName);
		predData.setSelectedItem(model.export.data);
	}

	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if(cmd == "Cancel"){
			this.dispose();
		}else if(cmd == "OK"){
			updateModel();
			this.dispose();
		}
	}
	
	public void setSinglePredicted(){
		linearPred.setVisible(false);
		pred.setText("Predicted");
	}

}
