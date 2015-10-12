package org.rosuda.deducer.widgets.param;

import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;

public class ParamComboBoxWidget extends ParamWidget{

	protected JComboBox comboBox;
	public ParamComboBoxWidget(){
		super();
	}
	
	public ParamComboBoxWidget(Param p){
		super();
		setModel(p);
	}
	
	public void setModel(Param p){
		model = p;
		initAsComboBox(p.getViewType().equals(Param.VIEW_EDITABLE_COMBO));
		label.setText(p.getTitle());
		if(p.getValue() !=null && (p.getLabels()==null || p.getViewType().equals(Param.VIEW_EDITABLE_COMBO)))
			comboBox.setSelectedItem(p.getValue().toString());
		else if(p.getValue() !=null && p.getLabels()!=null){
			for(int i=0;i<p.getOptions().length;i++)
				if(p.getValue().toString().equals(p.getOptions()[i]))
					comboBox.setSelectedIndex(i+1);
		}
	}
	
	public void updateModel(){
		String val = (String) comboBox.getSelectedItem();
		int ind = comboBox.getSelectedIndex();
		if(ind>0){
			val = model.getOptions()[ind-1];
		}
		if(val!=null && val.length()>0)
			model.setValue(val);
		else
			model.setValue(null);
	}
	
	public Param getModel(){
		updateModel();
		return model;
	}
	
	protected void initAsComboBox(boolean editable){
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
			DefaultComboBoxModel comboBoxModel = 
				new DefaultComboBoxModel();
			comboBoxModel.addElement(null);
			if(model!=null){
				if(model.getOptions()!=null && model.getLabels()!=null)
					for(int i=0;i<model.getOptions().length;i++)
						comboBoxModel.addElement(model.getOptions()[i] + "  :  "+model.getLabels()[i]);
				if(model.getOptions()!=null && model.getLabels()==null)
					for(int i=0;i<model.getOptions().length;i++)
						comboBoxModel.addElement(model.getOptions()[i]);
			}
			comboBox = new JComboBox();
			this.add(comboBox, new AnchorConstraint(148, 12, 743, textPos, 
					AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, 
					AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
			comboBox.setEditable(editable);
			comboBox.setModel(comboBoxModel);
			comboBox.setPreferredSize(new java.awt.Dimension(122, 22));
		}

		this.setPreferredSize(new Dimension(200,30));
		this.setMaximumSize(new Dimension(2000,30));
	}
}
