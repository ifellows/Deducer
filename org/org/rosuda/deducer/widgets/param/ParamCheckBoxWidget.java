package org.rosuda.deducer.widgets.param;

import javax.swing.JCheckBox;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;

public class ParamCheckBoxWidget extends ParamWidget{
	private JCheckBox checkBox;
	public ParamCheckBoxWidget(){
		super();
	}
	
	public ParamCheckBoxWidget(Param p){
		super();
		setModel(p);
	}
	
	public void setModel(Param p){
		model = p;
		initAsCheckBox();
		checkBox.setText(p.getTitle());
		if(p.getValue() !=null)
			checkBox.setSelected(((Boolean) p.getValue()).booleanValue());
	}
	
	public void updateModel(){
		model.setValue(new Boolean(checkBox.isSelected()));
	}
	
	public Param getModel(){
		updateModel();
		return model;
	}
	
	private void initAsCheckBox(){
		this.removeAll();
		AnchorLayout thisLayout = new AnchorLayout();
		this.setLayout(thisLayout);
		this.setPreferredSize(new java.awt.Dimension(241, 37));
		this.setMaximumSize(new java.awt.Dimension(1000, 37));
		{
			checkBox = new JCheckBox();
			this.add(checkBox, new AnchorConstraint(175, 1002, 689, leftPos, 
					AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
					AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
			checkBox.setText("option");
			checkBox.setPreferredSize(new java.awt.Dimension(179, 19));
		}	
	}
}
