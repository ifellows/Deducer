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




public class LinearExplorerOptions extends javax.swing.JDialog implements ActionListener {
	private JPanel modelInfPanel;
	private JCheckBox anova;
	private JLabel type;
	private JCheckBox summary;
	private JCheckBox hccm;
	private JPanel robustPanel;
	private JPanel okayCancel;
	private JCheckBox influence;
	private JCheckBox vif;
	private JSeparator sep;
	private JPanel diagPanel;
	private JCheckBox cor;
	private JRadioButton typeIII;
	private JRadioButton typeII;
	private LinearModel model;
	
	public LinearExplorerOptions(JFrame frame,LinearModel mod) {
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
					modelInfPanel.setBounds(17, 12, 260, 129);
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
						typeIII.setBounds(125, 43, 51, 19);
					}
					{
						summary = new JCheckBox();
						modelInfPanel.add(summary);
						summary.setText("Summary Table");
						summary.setBounds(17, 78, 204, 19);
					}

					{
						cor = new JCheckBox();
						modelInfPanel.add(cor);
						cor.setText("Parameter Correlations");
						cor.setBounds(37, 103, 189, 19);
					}
					{
						sep = new JSeparator();
						modelInfPanel.add(sep);
						sep.setBounds(61, 68, 113, 10);
					}
				}
				{
					diagPanel = new JPanel();
					getContentPane().add(diagPanel);
					diagPanel.setBounds(17, 222, 260, 70);
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
					okayCancel.setBounds(88, 298, 189, 31);
					{
						robustPanel = new JPanel();
						getContentPane().add(robustPanel);
						robustPanel.setBounds(17, 141, 260, 75);
						robustPanel.setBorder(BorderFactory.createTitledBorder("Robust to:"));
						robustPanel.setLayout(null);
						{
							hccm = new JCheckBox();
							robustPanel.add(hccm);
							hccm.setText("Unequal Variance (HCCM)");
							hccm.setBounds(17, 32, 238, 19);
						}
					}
				}
			}
			ButtonGroup b = new ButtonGroup();
			b.add(typeII);
			b.add(typeIII);
			this.setTitle("Options");
			this.setSize(289, 363);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	
	public void setModel(LinearModel mod){
		model = mod;
		anova.setSelected(mod.options.anova);
		typeII.setSelected(true);
		typeIII.setSelected(mod.options.type=="III");
		hccm.setSelected(model.hccm);
		summary.setSelected(mod.options.summary);
		cor.setSelected(mod.options.paramCor);
		vif.setSelected(mod.options.vif);
		influence.setSelected(mod.options.influence);
	}

	public void updateModel(){
		model.hccm = hccm.isSelected();
		model.options.anova=anova.isSelected();
		model.options.type=(typeII.isSelected() ? "II" : "III");
		model.options.test="F";
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

