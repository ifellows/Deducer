package org.rosuda.deducer.plots;


import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;


public class PositionPanel extends javax.swing.JPanel {
	private JTextField height;
	private JLabel hieghtLabel;
	private JTextField width;
	private JLabel widthLabel;
	private JComboBox position;
	
	private Position model;
	
	public PositionPanel(Position pos) {
		super();
		initGUI();
		setModel(pos);
	}
	
	public PositionPanel(){
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(190, 109));
			this.setBorder(BorderFactory.createTitledBorder("Position"));
			{
				height = new JTextField();
				this.add(height, new AnchorConstraint(655, 899, 857, 557, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				height.setPreferredSize(new java.awt.Dimension(56, 22));
			}
			{
				hieghtLabel = new JLabel();
				this.add(hieghtLabel, new AnchorConstraint(692, 557, 830, 106, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				hieghtLabel.setText("Height:");
				hieghtLabel.setPreferredSize(new java.awt.Dimension(74, 15));
				hieghtLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			}
			{
				width = new JTextField();
				this.add(width, new AnchorConstraint(463, 899, 665, 557, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				width.setPreferredSize(new java.awt.Dimension(56, 22));
			}
			{
				widthLabel = new JLabel();
				this.add(widthLabel, new AnchorConstraint(500, 557, 637, 106, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				widthLabel.setText("Width:");
				widthLabel.setPreferredSize(new java.awt.Dimension(74, 15));
				widthLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			}
			{
				ComboBoxModel positionModel = 
					new DefaultComboBoxModel(PlotController.getPositionNames());
				position = new JComboBox();
				this.add(position, new AnchorConstraint(188, 960, 389, 40, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				position.setModel(positionModel);
				position.setPreferredSize(new java.awt.Dimension(170, 22));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setModel(Position pos){
		model = pos;
		position.setSelectedItem(pos.name);
		if(pos.height!=null)
			height.setText(pos.height.toString());
		if(pos.width!=null)
			width.setText(pos.width.toString());
	}
	
	public void updateModel(){
		model.name = position.getSelectedItem().toString();
		String txt = height.getText();
		if(txt!=null && txt.length()>0){
			try{
				model.height = new Double(Double.parseDouble(txt));
			}catch(Exception e){}
		}
		txt = width.getText();
		if(txt!=null && txt.length()>0){
			try{
				model.width = new Double(Double.parseDouble(txt));
			}catch(Exception e){}
		}
	}
	
	public Position getModel(){
		updateModel();
		return model;
	}
		
}
