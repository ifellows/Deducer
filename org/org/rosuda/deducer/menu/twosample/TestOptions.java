package org.rosuda.deducer.menu.twosample;

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
import javax.swing.JTextField;

import org.rosuda.deducer.toolkit.OkayCancelPanel;



public class TestOptions extends JDialog implements ActionListener{
	private JPanel alternativePanel;
	private JRadioButton twoSided;
	private JRadioButton greater;
	private JTextField confLevel;
	private ButtonGroup alternative;
	private JPanel navPanel;
	private JTextField digits;
	private JLabel digitLabel;
	private JPanel outputPanel;
	private JCheckBox descriptives;
	private JLabel percLabel;
	private JLabel ConfidenceLabel;
	private JRadioButton lessThan;
	private TwoSampleModel.OptionsModel model;

	
	public TestOptions(JDialog d,TwoSampleModel.OptionsModel opt) {
		super(d);
		initGUI();
		model=opt;
		reset();
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
				alternativePanel.setBounds(32, 12, 202, 99);
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
				ConfidenceLabel.setBounds(49, 123, 120, 15);
			}
			{
				confLevel = new JTextField();
				getContentPane().add(getConfLevel());
				confLevel.setText("95");
				confLevel.setBounds(166, 119, 46, 22);
			}
			{
				percLabel = new JLabel();
				getContentPane().add(percLabel);
				percLabel.setText("%");
				percLabel.setBounds(217, 123, 17, 15);
			}
			{
				outputPanel = new JPanel();
				getContentPane().add(outputPanel);
				getContentPane().add(getNavPanel());
				outputPanel.setLayout(null);
				outputPanel.setBounds(32, 148, 202, 90);
				outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));
				{
					descriptives = new JCheckBox();
					outputPanel.add(descriptives);
					descriptives.setText("Descriptive Table");
					descriptives.setBounds(17, 20, 154, 19);
				}
				{
					digitLabel = new JLabel();
					outputPanel.add(digitLabel);
					digitLabel.setText(" Digits:");
					digitLabel.setBounds(17, 55, 60, 15);
				}
				{
					digits = new JTextField();
					outputPanel.add(digits);
					digits.setText("<auto>");
					digits.setBounds(67, 51, 68, 22);
				}
			}
			getAlternative();
			this.setTitle("Test Options");
			this.setResizable(false);
			this.setSize(268, 310);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JTextField getConfLevel() {
		return confLevel;
	}
	
	private ButtonGroup getAlternative() {
		if(alternative == null) {
			alternative = new ButtonGroup();
			alternative.add(twoSided);
			alternative.add(lessThan);
			alternative.add(greater);
		}
		return alternative;
	}
	
	private JPanel getNavPanel() {
		if(navPanel == null) {
			navPanel = new OkayCancelPanel(false,false,this);
			navPanel.setBounds(50, 244, 206, 50);
		}
		return navPanel;
	}
	
	public void reset(){
		if(model.alternative=="two.sided")
			twoSided.setSelected(true);
		else if(model.alternative=="less")
			lessThan.setSelected(true);
		else
			greater.setSelected(true);
		confLevel.setText(Double.toString(model.confLevel*100.0));
		digits.setText(model.digits);
		descriptives.setSelected(model.descriptives);
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
			model.confLevel=tmp/100.0;
			String tmpText=digits.getText();
			int tmpInt=0;
			if(!tmpText.equals("<auto>")){
				try{
					tmpInt=Integer.parseInt(tmpText);
				}catch(Exception er){
					JOptionPane.showMessageDialog(this, "Digits: Please enter a number.");
					return;				
				}
				if(tmpInt<0){
					JOptionPane.showMessageDialog(this, "Digits: Please enter a positive integer.");
					return;					
				}
			}
			model.digits=tmpText;
			model.descriptives=descriptives.isSelected();
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
