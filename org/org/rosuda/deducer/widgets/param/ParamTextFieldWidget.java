package org.rosuda.deducer.widgets.param;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;

public class ParamTextFieldWidget extends ParamWidget implements FocusListener{
	private JTextField textField;
	public ParamTextFieldWidget(){
		super();
	}
	
	public ParamTextFieldWidget(Param p){
		super();
		setModel(p);
	}
	
	public void setModel(Param p){
		model = p;
		if(p.getViewType().equals(Param.VIEW_ENTER)){
			initAsShortTextField();
			label.setText(p.getTitle());
			if(p.getValue() !=null)
				textField.setText(p.getValue().toString());
			if(p instanceof ParamNumeric)
				textField.addFocusListener(this);
		}else{
			initAsLongTextField();
			label.setText(p.getTitle());
			if(p.getValue() !=null)
				textField.setText(p.getValue().toString());
			if(p instanceof ParamNumeric)
				textField.addFocusListener(this);
		}
	}
	
	public void updateModel(){
		if(textField.getText().length()>0)
			model.setValue(textField.getText());
		else
			model.setValue(null);
	}
	
	public Param getModel(){
		updateModel();
		return model;
	}
	
	
	private void initAsShortTextField(){
		this.removeAll();
		AnchorLayout thisLayout = new AnchorLayout();
		this.setLayout(thisLayout);
		this.setPreferredSize(new java.awt.Dimension(241, 37));
		this.setMaximumSize(new java.awt.Dimension(2000, 37));
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
			textField = new JTextField();
			this.add(textField, new AnchorConstraint(148, 529, 743, textPos, 
					AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE,
					AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
			textField.setPreferredSize(new java.awt.Dimension(71, 22));
			textField.setHorizontalAlignment(SwingConstants.CENTER);
		}
	
	}
	
	private void initAsLongTextField(){
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
			textField = new JTextField();
			this.add(textField, new AnchorConstraint(148, 12, 743, textPos, 
					AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, 
					AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
			textField.setPreferredSize(new java.awt.Dimension(161, 22));
			textField.setHorizontalAlignment(SwingConstants.CENTER);
		}
		this.setPreferredSize(new Dimension(200,37));
		this.setMaximumSize(new Dimension(2000,37));
	
	}
	
	public void focusGained(FocusEvent fe) {}

	public void focusLost(FocusEvent fe) {
		JTextField field = (JTextField) fe.getSource();
		String s = field.getText();
		try{
			double d = Double.parseDouble(s);
			ParamNumeric p = (ParamNumeric) model;
			if(p.getLowerBound()!=null && d<p.getLowerBound().doubleValue())
				field.setText(p.getLowerBound().toString());
			if(p.getUpperBound()!=null && d>p.getUpperBound().doubleValue())
				field.setText(p.getUpperBound().toString());
		}catch(Exception e){
			field.setText("");
		}
		
	}
}
