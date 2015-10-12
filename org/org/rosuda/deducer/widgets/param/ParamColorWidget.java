package org.rosuda.deducer.widgets.param;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;

public class ParamColorWidget extends ParamWidget implements ActionListener{
	private JButton colourButton;
	private Color colourValue;

	public ParamColorWidget(){
		super();
	}
	
	public ParamColorWidget(Param p){
		super();
		setModel(p);
	}
	
	public void setModel(Param p){
		model = p;
		initAsColour();
		if(model.getValue()!=null){
			colourButton.setForeground((Color) model.getValue());
			colourValue = (Color) model.getValue();
		}
	}
	
	public void updateModel(){
		if(colourValue!=null){
			model.setValue(colourValue);
		}
	}
	
	public Param getModel(){
		updateModel();
		return model;
	}
	
	private void initAsColour(){
		this.removeAll();
		AnchorLayout thisLayout = new AnchorLayout();
		this.setLayout(thisLayout);
		this.setPreferredSize(new java.awt.Dimension(241, 37));
		int labelWidth = leftPos-22; 
		{
			label = new JLabel();
			this.add(label, new AnchorConstraint(202, 234, 689, 12, 
					AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, 
					AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
			if(model!=null){
				label.setText(model.getTitle());
				labelWidth = SwingUtilities.computeStringWidth(
						label.getFontMetrics(label.getFont()),
						model.getTitle());
			}

		}	
		{
			int textPos = Math.max(labelWidth+22, leftPos);
			colourButton = new JButton("Set Colour");
			this.add(colourButton, new AnchorConstraint(148, 12, 743, textPos, 
					AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, 
					AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
			colourButton.setPreferredSize(new java.awt.Dimension(122, 22));
			colourButton.removeActionListener(this);
			colourButton.addActionListener(this);
		}

		this.setPreferredSize(new Dimension(200,30));
		this.setMaximumSize(new Dimension(2000,30));
	}
	
	public void actionPerformed(ActionEvent arg0) {
		Color c =JColorChooser.showDialog(this, "Choose Colour", colourButton.getForeground());
		if(c!=null){
			colourButton.setForeground(c);
			colourValue = c;
		}
	}
}
