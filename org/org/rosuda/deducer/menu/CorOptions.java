package org.rosuda.deducer.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import org.rosuda.deducer.toolkit.OkayCancelPanel;



public class CorOptions extends javax.swing.JDialog implements ActionListener{
	private JPanel alternativePanel;
	private JRadioButton twoSided;
	private JRadioButton greater;
	private JTextField confLevel;
	private JSeparator sep;
	private JCheckBox n;
	private JCheckBox pValue;
	private JCheckBox stat;
	private JCheckBox ci;
	private ButtonGroup alternative;
	private JPanel navPanel;
	private JTextField digits;
	private JLabel digitLabel;
	private JPanel outputPanel;
	private JCheckBox print;
	private JLabel percLabel;
	private JLabel ConfidenceLabel;
	private JRadioButton lessThan;
	
	private CorModel.OptModel model;
	
	public CorOptions(JDialog d,CorModel.OptModel mod) {
		super(d);
		initGUI();
		setModel(mod);
	}


	
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
			}
			{
				alternativePanel = new JPanel();
				getContentPane().add(alternativePanel);
				alternativePanel.setLayout(null);
				alternativePanel.setBounds(49, 12, 202, 99);
				alternativePanel.setBorder(BorderFactory.createTitledBorder("Alternative"));
				{
					twoSided = new JRadioButton();
					alternativePanel.add(twoSided);
					twoSided.setText("Two-sided");
					twoSided.setBounds(17, 20, 180, 19);
				}
				{
					lessThan = new JRadioButton();
					alternativePanel.add(lessThan);
					lessThan.setText("Less");
					lessThan.setBounds(17, 45, 180, 19);
				}
				{
					greater = new JRadioButton();
					alternativePanel.add(greater);
					greater.setText("Greater");
					greater.setBounds(17, 70, 168, 19);
				}
			}
			{
				ConfidenceLabel = new JLabel();
				getContentPane().add(ConfidenceLabel);
				ConfidenceLabel.setText("Confidence Level:");
				ConfidenceLabel.setBounds(58, 123, 120, 15);
			}
			{
				confLevel = new JTextField();
				getContentPane().add(confLevel);
				confLevel.setText("95");
				confLevel.setBounds(178, 119, 46, 22);
			}
			{
				percLabel = new JLabel();
				getContentPane().add(percLabel);
				percLabel.setText("%");
				percLabel.setBounds(230, 123, 17, 15);
			}
			{
				navPanel = new OkayCancelPanel(false,false,this);
				navPanel.setBounds(83, 284, 206, 55);
				getContentPane().add(navPanel);
			}
			{
				outputPanel = new JPanel();
				getContentPane().add(outputPanel);
				
				outputPanel.setLayout(null);
				outputPanel.setBounds(12, 150, 277, 117);
				outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));
				{
					print = new JCheckBox();
					outputPanel.add(print);
					print.setText("Print Matrix");
					print.setBounds(17, 20, 154, 19);
				}
				{
					digitLabel = new JLabel();
					outputPanel.add(digitLabel);
					digitLabel.setText("Digits:");
					digitLabel.setBounds(17, 80, 44, 15);
				}
				{
					digits = new JTextField();
					outputPanel.add(digits);
					digits.setText("<auto>");
					digits.setBounds(67, 76, 68, 22);
				}
				{
					ci = new JCheckBox();
					outputPanel.add(ci);
					ci.setText("CI");
					ci.setBounds(82, 39, 39, 19);
				}
				{
					stat = new JCheckBox();
					outputPanel.add(stat);
					stat.setText("Stat");
					stat.setBounds(127, 39, 53, 19);
				}
				{
					pValue = new JCheckBox();
					outputPanel.add(pValue);
					pValue.setText("p-value");
					pValue.setBounds(186, 39, 86, 19);
				}
				{
					n = new JCheckBox();
					outputPanel.add(n);
					n.setText("N");
					n.setBounds(37, 39, 39, 19);
				}
				{
					sep = new JSeparator();
					outputPanel.add(sep);
					sep.setBounds(37, 64, 167, 10);
				}
			}
			alternative = new ButtonGroup();
			alternative.add(twoSided);
			alternative.add(lessThan);
			alternative.add(greater);
			this.setTitle("Test Options");
			this.setResizable(false);
			this.setSize(301, 373);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setModel(CorModel.OptModel mod){
		model = mod;
		if(model.alternative=="two.sided")
			twoSided.setSelected(true);
		else if(model.alternative=="less")
			lessThan.setSelected(true);
		else
			greater.setSelected(true);
		confLevel.setText(Double.toString(model.confLevel*100.0));
		digits.setText(model.digits);
		print.setSelected(model.showTable);
		n.setSelected(model.n);
		ci.setSelected(model.ci);
		stat.setSelected(model.stat);
		pValue.setSelected(model.pValue);
	}

	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if(cmd=="OK"){
			double tmp;
			try{
				tmp=Double.parseDouble(confLevel.getText());
			}catch(Exception ep){
				JOptionPane.showMessageDialog(this, "Confidence Level: Please enter a number between 0 and 100");
				return;
			}
			if(tmp<0 || tmp>100){
				JOptionPane.showMessageDialog(this, "Confidence Level: Please enter a number between 0 and 100");
				return;
			}
			String tmpText=digits.getText();
			int tmpInt=0;
			if(!tmpText.equals("<auto>")){
				try{
					tmpInt=Integer.parseInt(tmpText);
				}catch(Exception er){
					JOptionPane.showMessageDialog(this, "Digits: Please enter a number.");
					return;				
				}
				if(tmpInt<1){
					JOptionPane.showMessageDialog(this, "Digits: Please enter a positive integer.");
					return;					
				}
			}
			
			model.confLevel=tmp/100.0;
			model.digits=tmpText;
			model.showTable=print.isSelected();
			model.n=n.isSelected();
			model.stat=stat.isSelected();
			model.pValue=pValue.isSelected();
			model.ci=ci.isSelected();
			if(twoSided.isSelected())
				model.alternative="two.sided";
			else if(lessThan.isSelected())
				model.alternative="less";
			else
				model.alternative="greater";

			this.dispose();
		}if(cmd=="Cancel"){
			this.dispose();
		}
		
	}

}
