package org.rosuda.deducer.models;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.deducer.toolkit.OkayCancelPanel;


public class GLMExplorerOptions extends javax.swing.JDialog implements ActionListener {
	private JPanel modelInfPanel;
	private JCheckBox anova;
	private JLabel type;
	private JCheckBox summary;
	private JPanel okayCancel;
	private JCheckBox influence;
	private JCheckBox vif;
	private JSeparator sep;
	private JPanel diagPanel;
	private JCheckBox cor;
	private JRadioButton testF;
	private JRadioButton testWald;
	private JRadioButton testLR;
	private JLabel test;
	private JRadioButton typeIII;
	private JRadioButton typeII;
	private GLMModel model;
	
	public GLMExplorerOptions(JFrame frame,GLMModel mod) {
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
					modelInfPanel = new JPanel();
					getContentPane().add(modelInfPanel);
					modelInfPanel.setBounds(17, 12, 260, 155);
					modelInfPanel.setBorder(BorderFactory.createTitledBorder("Tables"));
					modelInfPanel.setLayout(null);
					{
						anova = new JCheckBox();
						modelInfPanel.add(anova);
						anova.setText("Anova Table");
						anova.setBounds(17, 20, 227, 19);
					}
					{
						type = new JLabel();
						modelInfPanel.add(type);
						type.setText("Type:");
						type.setBounds(17, 45, 53, 15);
						type.setHorizontalAlignment(SwingConstants.RIGHT);
					}
					{
						typeII = new JRadioButton();
						modelInfPanel.add(typeII);
						typeII.setText("II");
						typeII.setBounds(76, 43, 51, 19);
					}
					{
						typeIII = new JRadioButton();
						modelInfPanel.add(typeIII);
						typeIII.setText("III");
						typeIII.setBounds(127, 43, 51, 19);
					}
					{
						summary = new JCheckBox();
						modelInfPanel.add(summary);
						summary.setText("Summary Table");
						summary.setBounds(17, 99, 204, 19);
					}
					{
						test = new JLabel();
						modelInfPanel.add(test);
						test.setText("Test:");
						test.setBounds(17, 66, 53, 15);
						test.setHorizontalAlignment(SwingConstants.RIGHT);
					}
					{
						testLR = new JRadioButton();
						modelInfPanel.add(testLR);
						testLR.setText("LR");
						testLR.setBounds(76, 64, 51, 19);
					}
					{
						testWald = new JRadioButton();
						modelInfPanel.add(testWald);
						testWald.setText("Wald");
						testWald.setBounds(127, 64, 70, 19);
					}
					{
						testF = new JRadioButton();
						modelInfPanel.add(testF);
						testF.setText("F");
						testF.setBounds(195, 64, 40, 19);
					}
					{
						cor = new JCheckBox();
						modelInfPanel.add(cor);
						cor.setText("Parameter Correlations");
						cor.setBounds(37, 120, 189, 19);
					}
					{
						sep = new JSeparator();
						modelInfPanel.add(sep);
						sep.setBounds(61, 89, 113, 10);
					}
				}
				{
					diagPanel = new JPanel();
					getContentPane().add(diagPanel);
					diagPanel.setBounds(17, 173, 260, 89);
					diagPanel.setBorder(BorderFactory.createTitledBorder("Model Diagnostics"));
					diagPanel.setLayout(null);
					{
						vif = new JCheckBox();
						diagPanel.add(vif);
						vif.setText("Variance Inflation Factors");
						vif.setBounds(17, 20, 226, 19);
					}
					{
						influence = new JCheckBox();
						diagPanel.add(influence);
						influence.setText("Influence Summary");
						influence.setBounds(17, 45, 226, 19);
					}
				}
				{
					okayCancel = new OkayCancelPanel(false,false,this);
					getContentPane().add(okayCancel);
					okayCancel.setBounds(88, 268, 189, 31);
				}
			}
			ButtonGroup b = new ButtonGroup();
			b.add(typeII);
			b.add(typeIII);
			ButtonGroup b2 = new ButtonGroup();
			b2.add(testWald);
			b2.add(testLR);
			b2.add(testF);
			this.setTitle("Options");
			this.setSize(289, 333);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	
	public void setModel(GLMModel mod){
		model = mod;
		anova.setSelected(mod.options.anova);
		typeII.setSelected(true);
		typeIII.setSelected(mod.options.type=="III");
		testWald.setSelected(true);
		if(mod.options.test=="LR")
			testLR.setSelected(true);
		else if(mod.options.test=="F")
			testF.setSelected(true);
		if(!mod.family.startsWith("gaussian()"))
			testF.setEnabled(false);
		summary.setSelected(mod.options.summary);
		cor.setSelected(mod.options.paramCor);
		vif.setSelected(mod.options.vif);
		influence.setSelected(mod.options.influence);
	}

	public void updateModel(){
		model.options.anova=anova.isSelected();
		model.options.type=(typeII.isSelected() ? "II" : "III");
		if(testWald.isSelected())
			model.options.test="Wald";
		else if(testF.isSelected())
			model.options.test="F";
		else
			model.options.test="LR";
		model.options.summary=summary.isSelected();
		model.options.paramCor=cor.isSelected();
		model.options.vif=vif.isSelected();
		model.options.influence=influence.isSelected();		
	}
	
	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if(cmd == "OK"){
			updateModel();
			this.dispose();
		}else if(cmd == "Cancel"){
			this.dispose();
		}
		
	}

}
