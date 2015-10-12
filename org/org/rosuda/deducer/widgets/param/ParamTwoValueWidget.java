package org.rosuda.deducer.widgets.param;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.deducer.Deducer;

public class ParamTwoValueWidget extends ParamWidget implements FocusListener{
	private JTextField textField;
	private JTextField textField1;
	public ParamTwoValueWidget(){
		super();
	}
	
	public ParamTwoValueWidget(Param p){
		super();
		setModel(p);
	}
	
	public void setModel(Param p){
		model = p;
		initAsTwoTextFields();
		String[] val = (String[]) p.getValue();
		if(val!=null && val.length>1){
			textField.setText(val[0]);
			textField1.setText(val[1]);
		}
		textField.removeFocusListener(this);
		textField1.removeFocusListener(this);
		if(((ParamVector)p).isNumeric())
			textField.addFocusListener(this);
		if(((ParamVector)p).isNumeric())
			textField1.addFocusListener(this);
	}
	
	public void updateModel(){
		String a = textField.getText();
		String b = textField1.getText();
		if(!((ParamVector)model).isNumeric()){
			if(a.length()>0 && b.length()>0)
				model.setValue(new String[]{"'"+Deducer.addSlashes(a)+"'","'"+Deducer.addSlashes(b)+"'"});
			else
				model.setValue(new String[]{});
		}
		if(((ParamVector)model).isNumeric()){
			if(a.length()>0 && b.length()>0)
				model.setValue(new String[]{a,b});
			else
				model.setValue(new String[]{});
		}		
	}
	
	public Param getModel(){
		return null;
	}
	
	private void initAsTwoTextFields(){
		this.removeAll();
		AnchorLayout thisLayout = new AnchorLayout();
		this.setLayout(thisLayout);
		this.setPreferredSize(new java.awt.Dimension(241, 37));
		this.setMaximumSize(new java.awt.Dimension(1000, 37));
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
		{
			int textPos = Math.max(labelWidth+22, leftPos);
			textField1 = new JTextField();
			this.add(textField1, new AnchorConstraint(148, 529, 743, textPos+81, 
					AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE,
					AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
			textField1.setPreferredSize(new java.awt.Dimension(71, 22));
			textField1.setHorizontalAlignment(SwingConstants.CENTER);
		}
	
	}
	
	public void focusGained(FocusEvent fe) {}

	public void focusLost(FocusEvent fe) {
		JTextField field = (JTextField) fe.getSource();
		String s = field.getText();
		try{
			double d = Double.parseDouble(s);
			if(model.getLowerBound()!=null && d<model.getLowerBound().doubleValue())
				field.setText(model.getLowerBound().toString());
			if(model.getUpperBound()!=null && d>model.getUpperBound().doubleValue())
				field.setText(model.getUpperBound().toString());
		}catch(Exception e){
			field.setText("");
		}
		
	}
}
