package org.rosuda.deducer.menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.deducer.toolkit.OkayCancelPanel;



public class CorPlots extends JDialog implements ActionListener{
	private JPanel optionPanel;
	private JRadioButton scatterArray;
	private JComboBox scatterArrayLine;
	private JSeparator sep;
	private JPanel matrixPanel;
	private JRadioButton matrix;
	private JSeparator sep1;
	private OkayCancelPanel okayCancelPanel;
	private JRadioButton circles;
	private JRadioButton none;
	private ButtonGroup buttonGroup;
	private JRadioButton ellipse;
	private JLabel lineLabel;
	private JCheckBox scatterArrayCommon;
	
	private CorModel.Plots model;
	private JSlider mAlpha;
	private JLabel jLabel1;
	private JSlider saAlpha;
	private JLabel radiusLabel;
	private JSlider radius;
	private JSlider mSize;
	private JLabel alphaLabel;
	private JComboBox mLines;
	private JLabel lines1;
	private JLabel maxLabel;

	public CorPlots(JDialog d,CorModel.Plots mod,boolean anyWith) {
		super(d);
		initGUI();
		okayCancelPanel.addActionListener(this);
		setModel(mod);
		ellipse.setEnabled(false);
		if(anyWith){
			if(matrix.isSelected())
				none.setSelected(true);
			matrix.setEnabled(false);
		}
	}
	
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					optionPanel = new JPanel();
					getContentPane().add(optionPanel);
					optionPanel.setBounds(12, 12, 372, 195);
					optionPanel.setBorder(BorderFactory.createTitledBorder("Correlation Arrays"));
					optionPanel.setLayout(null);
					{
						scatterArray = new JRadioButton();
						optionPanel.add(scatterArray);
						scatterArray.setText("Scatter Plots");
						scatterArray.setBounds(17, 32, 120, 19);
					}
					{
						scatterArrayCommon = new JCheckBox();
						optionPanel.add(scatterArrayCommon);
						scatterArrayCommon.setText("Common Axis");
						scatterArrayCommon.setBounds(218, 65, 172, 19);
					}
					{
						ComboBoxModel scatterArrayLineModel = 
							new DefaultComboBoxModel(
									new String[] { "Linear", "Loess","None" });
						scatterArrayLine = new JComboBox();
						optionPanel.add(scatterArrayLine);
						scatterArrayLine.setModel(scatterArrayLineModel);
						scatterArrayLine.setBounds(217, 19, 138, 22);
					}
					{
						lineLabel = new JLabel();
						optionPanel.add(lineLabel);
						lineLabel.setText("Lines:");
						lineLabel.setBounds(160, 23, 45, 15);
						lineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					}
					{
						sep = new JSeparator();
						optionPanel.add(sep);
						sep.setBounds(35, 91, 288, 10);
					}
					{
						ellipse = new JRadioButton();
						optionPanel.add(ellipse);
						optionPanel.add(getCircles());
						optionPanel.add(getSep1());
						optionPanel.add(getRadius());
						optionPanel.add(getRadiusLabel());
						optionPanel.add(getSaAlpha());
						optionPanel.add(getJLabel1());
						ellipse.setText("Ellipses");
						ellipse.setBounds(17, 108, 120, 19);
					}
				}
				{
					matrixPanel = new JPanel();
					getContentPane().add(matrixPanel);
					matrixPanel.setBounds(12, 219, 372, 100);
					matrixPanel.setBorder(BorderFactory.createTitledBorder("Correlation Matrices (No 'with' variables allowed)"));
					matrixPanel.setLayout(null);
					matrixPanel.add(getMatrix());
					matrixPanel.add(getMaxLabel());
					matrixPanel.add(getLines1());
					matrixPanel.add(getMLines());
					matrixPanel.add(getMAlpha());
					matrixPanel.add(getAlphaLabel());
					matrixPanel.add(getMSize());
				}
				{
					okayCancelPanel = new OkayCancelPanel(false,false);
					getContentPane().add(okayCancelPanel);
					getContentPane().add(getNone());
					okayCancelPanel.setBounds(138, 355, 238, 42);
				}
			}
			getButtonGroup();
			this.setResizable(false);
			this.setSize(396, 443);
			this.setTitle("Plots");
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	public void setModel(CorModel.Plots mod){
		model=mod;
		scatterArray.setSelected(mod.scatterArray);
		ellipse.setSelected(mod.ellipse);
		circles.setSelected(mod.circles);
		matrix.setSelected(mod.matrix);
		none.setSelected(mod.none);
		scatterArrayCommon.setSelected(mod.common);
		scatterArrayLine.setSelectedItem(mod.saLines);
		mLines.setSelectedItem(mod.mLines);
		mSize.setValue(mod.mSize);
		mAlpha.setValue((int)(100.0*mod.mAlpha));
		radius.setValue(mod.cRadius);
		saAlpha.setValue((int)(100.0*mod.saAlpha));

	}
	
	public boolean updateModel(){
		model.scatterArray=scatterArray.isSelected();
		model.ellipse=ellipse.isSelected();
		model.circles=circles.isSelected();
		model.matrix=matrix.isSelected();
		model.none=none.isSelected();
		model.common=scatterArrayCommon.isSelected();
		model.saLines=(String)scatterArrayLine.getSelectedItem();
		model.saAlpha=((double)saAlpha.getValue())/100.0;
		model.mLines=(String)mLines.getSelectedItem();
		model.mSize=mSize.getValue();
		model.mAlpha=((double)mAlpha.getValue())/100.0;
		
		model.cRadius = radius.getValue();
		
		return true;
	}

	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if(cmd=="Cancel")
			this.dispose();
		else if(cmd=="OK"){
			if(updateModel())
				this.dispose();
		}
		
	}
	
	private ButtonGroup getButtonGroup() {
		if(buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(scatterArray);
			buttonGroup.add(ellipse);
			buttonGroup.add(circles);
			buttonGroup.add(matrix);
			buttonGroup.add(none);
		}
		return buttonGroup;
	}
	
	private JRadioButton getNone() {
		if(none == null) {
			none = new JRadioButton();
			none.setText("No Plots");
			none.setBounds(28, 323, 98, 19);
		}
		return none;
	}
	
	private JRadioButton getCircles() {
		if(circles == null) {
			circles = new JRadioButton();
			circles.setText("Circles");
			circles.setBounds(17, 148, 120, 19);
		}
		return circles;
	}
	
	private JSeparator getSep1() {
		if(sep1 == null) {
			sep1 = new JSeparator();
			sep1.setBounds(34, 136, 288, 10);
		}
		return sep1;
	}
	
	private JRadioButton getMatrix() {
		if(matrix == null) {
			matrix = new JRadioButton();
			matrix.setText("Matrix");
			matrix.setBounds(17, 37, 131, 19);
		}
		return matrix;
	}
	
	private JLabel getMaxLabel() {
		if(maxLabel == null) {
			maxLabel = new JLabel();
			maxLabel.setText("Max Size:");
			maxLabel.setBounds(143, 49, 63, 14);
			maxLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return maxLabel;
	}

	private JLabel getLines1() {
		if(lines1 == null) {
			lines1 = new JLabel();
			lines1.setText("Lines:");
			lines1.setBounds(155, 23, 48, 14);
			lines1.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return lines1;
	}
	
	private JComboBox getMLines() {
		if(mLines == null) {
			ComboBoxModel mLinesModel = 
				new DefaultComboBoxModel(
						new String[] { "Linear", "Loess","None" });
			mLines = new JComboBox();
			mLines.setModel(mLinesModel);
			mLines.setBounds(221, 20, 134, 25);
		}
		return mLines;
	}
	
	private JSlider getMAlpha() {
		if(mAlpha == null) {
			mAlpha = new JSlider();
			mAlpha.setBounds(218, 73, 138, 16);
			mAlpha.setMinimum(1);
			mAlpha.setMaximum(100);
		}
		return mAlpha;
	}
	
	private JLabel getAlphaLabel() {
		if(alphaLabel == null) {
			alphaLabel = new JLabel();
			alphaLabel.setText("Alpha:");
			alphaLabel.setBounds(149, 75, 57, 14);
			alphaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return alphaLabel;
	}
	
	private JSlider getMSize() {
		if(mSize == null) {
			mSize = new JSlider();
			mSize.setBounds(218, 50, 137, 16);
			mSize.setMinimum(5);
			mSize.setMaximum(30);
		}
		return mSize;
	}
	
	private JSlider getRadius() {
		if(radius == null) {
			radius = new JSlider();
			radius.setBounds(216, 162, 139, 16);
			radius.setMinimum(5);
			radius.setMaximum(30);
		}
		return radius;
	}
	
	private JLabel getRadiusLabel() {
		if(radiusLabel == null) {
			radiusLabel = new JLabel();
			radiusLabel.setText("Max Radius:");
			radiusLabel.setBounds(112, 162, 92, 14);
			radiusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return radiusLabel;
	}
	
	private JSlider getSaAlpha() {
		if(saAlpha == null) {
			saAlpha = new JSlider();
			saAlpha.setBounds(218, 45, 139, 16);
			saAlpha.setMinimum(1);
			saAlpha.setMaximum(100);
		}
		return saAlpha;
	}
	
	private JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Alpha:");
			jLabel1.setBounds(156, 44, 50, 14);
			jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return jLabel1;
	}

}
