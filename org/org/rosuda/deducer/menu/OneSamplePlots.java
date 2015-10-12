package org.rosuda.deducer.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.deducer.toolkit.OkayCancelPanel;


public class OneSamplePlots extends javax.swing.JDialog implements ActionListener {

	private JRadioButton plot;
	private JRadioButton noPlot;
	private JCheckBox scale;
	private JPanel okayCancelPanel;
	private JSeparator sep;
	private JLabel alphaLabel;
	private JSlider alpha;
	private JCheckBox boxPlot;
	private OneSampleDialog.OneSampleModel.PlotModel model;

	public OneSamplePlots(JDialog d,OneSampleDialog.OneSampleModel.PlotModel mod) {
		super(d);
		initGUI();
		setModel(mod);
	}
	
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					plot = new JRadioButton();
					getContentPane().add(plot);
					plot.setText("Horizontal");
					plot.setBounds(21, 37, 102, 18);
				}
				{
					noPlot = new JRadioButton();
					getContentPane().add(noPlot);
					noPlot.setText("No Plot");
					noPlot.setBounds(21, 125, 102, 18);
				}
				{
					boxPlot = new JCheckBox();
					getContentPane().add(boxPlot);
					boxPlot.setText("Box plot");
					boxPlot.setBounds(135, 17, 180, 18);
				}
				{
					scale = new JCheckBox();
					getContentPane().add(scale);
					scale.setText("Scale variables");
					scale.setBounds(134, 42, 180, 18);
				}
				{
					alpha = new JSlider();
					getContentPane().add(alpha);
					alpha.setBounds(134, 85, 140, 21);
					alpha.setMinimum(1);
					alpha.setMaximum(100);
				}
				{
					alphaLabel = new JLabel();
					getContentPane().add(alphaLabel);
					alphaLabel.setText("Transparancy");
					alphaLabel.setBounds(134, 69, 140, 14);
					alphaLabel.setHorizontalAlignment(SwingConstants.CENTER);
				}
				{
					sep = new JSeparator();
					getContentPane().add(sep);
					sep.setBounds(45, 111, 165, 10);
				}
				{
					okayCancelPanel = new OkayCancelPanel(false,false,this);
					getContentPane().add(okayCancelPanel);
					okayCancelPanel.setBounds(73, 152, 182, 36);
				}
			}
			ButtonGroup bg = new ButtonGroup();
			bg.add(plot);
			bg.add(noPlot);
			this.setTitle("Plots");
			this.setResizable(false);
			this.setSize(275, 234);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	
	public void setModel(OneSampleDialog.OneSampleModel.PlotModel mod){
		plot.setSelected(mod.plot);
		noPlot.setSelected(!mod.plot);
		boxPlot.setSelected(mod.box);
		scale.setSelected(mod.scale);
		alpha.setValue((int)(mod.alpha*100.0));
		model=mod;
	}
	
	public void updateModel(){
		model.plot=plot.isSelected();
		model.box=boxPlot.isSelected();
		model.scale=scale.isSelected();
		model.alpha=alpha.getValue()/100.0;		
	}


	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if(cmd=="Cancel")
			this.dispose();
		else if(cmd=="OK"){
			updateModel();
			this.dispose();
		}
		
	}

}
