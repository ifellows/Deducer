package org.rosuda.deducer.widgets.param;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSeparator;

public class ParamSeperatorWidget extends ParamWidget {
	

	private JSeparator sep1;
	private JSeparator sep2;
	private JLabel lab;

	public ParamSeperatorWidget(Param p){
		super();
		setModel(p);
	}
	
	public void setModel(Param p) {
		model = p;
		this.removeAll();
		initGui();
	}

	public void updateModel() {}
	
	private void initGui(){
		Color sepColor = new Color(50,50,50);
		this.setPreferredSize(new java.awt.Dimension(400, 30));
		this.setMinimumSize(new java.awt.Dimension(250, 30));
		this.setMaximumSize(new java.awt.Dimension(400, 30));
		BoxLayout panelLayout = new BoxLayout(this, javax.swing.BoxLayout.X_AXIS);
		this.setLayout(panelLayout);
		{
			sep1 = new JSeparator();
			this.add(sep1);
			sep1.setPreferredSize(new java.awt.Dimension(50, 2));
			sep1.setMaximumSize(new java.awt.Dimension(50, 2));
			sep1.setAlignmentY(Component.CENTER_ALIGNMENT);
			sep1.setForeground(sepColor);
		}
		this.add(Box.createHorizontalGlue());
		this.add(Box.createRigidArea(new Dimension(5, 0)));
		lab = new JLabel(model.title);
		lab.setForeground(new Color(70,70,70));
		this.add(lab);
		this.add(Box.createRigidArea(new Dimension(5, 0)));
		this.add(Box.createHorizontalGlue());
		{
			sep2 = new JSeparator();
			this.add(sep2);
			sep2.setPreferredSize(new java.awt.Dimension(50, 2));
			sep2.setMaximumSize(new java.awt.Dimension(50, 2));
			sep2.setAlignmentY(Component.CENTER_ALIGNMENT);
			sep2.setForeground(sepColor);
		}
	}

}
