package org.rosuda.deducer.widgets;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.rosuda.deducer.Deducer;


public class TextFieldWidget extends JPanel implements DeducerWidget, FocusListener{
	private JLabel titleLabel;
	private JTextField textField;
	
	private String title = null;
	private boolean show = true;
	private String initialModel;
	private String lastModel;
	
	private boolean numeric = false;
	private boolean integer = false;
	private boolean hasLowerBound = false;
	private double lowerBound=0.0;
	private boolean hasUpperBound = false;
	private double upperBound = 0.0;

	public static Color labelColor = new Color(90,90,90);
	
	/**
	 * 
	 * @param panelTitle title
	 */
	public TextFieldWidget(String title) {
		super();
		initGUI();
		this.title = title;
		if(title==null)
			show=false;
		else
			titleLabel.setText(title);
		resetLayout();
	}
	
	public TextFieldWidget() {
		this(null);
	}
	
	
	private void initGUI() {
		try {
			{
				AnchorLayout thisLayout = new AnchorLayout();
				this.setLayout(thisLayout);
				this.setPreferredSize(new java.awt.Dimension(187, 47));
				{
					titleLabel = new JLabel();
					this.add(titleLabel, new AnchorConstraint(0, 1000, 22, 0, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
					titleLabel.setText("title");
					titleLabel.setPreferredSize(new java.awt.Dimension(187, 20));
					titleLabel.setBounds(0, 0, 187, 20);
					titleLabel.setVerticalAlignment(SwingConstants.BOTTOM);
					titleLabel.setForeground(labelColor);
				}
				{
					textField = new JTextField();
					this.add(textField, new AnchorConstraint(22, 1000, 1000, 0, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					textField.setPreferredSize(new java.awt.Dimension(186, 19));
					textField.addFocusListener(this);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void resetLayout(){
		
		if(title==null || !show){
			this.remove(titleLabel);
			this.remove(textField);
			this.setPreferredSize(new java.awt.Dimension(187, 22));
			{
				this.add(textField, new AnchorConstraint(0, 1000, 1000, 0, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				textField.setPreferredSize(new java.awt.Dimension(186, 19));
			}					
		}else{
			this.remove(titleLabel);
			this.remove(textField);
			this.setPreferredSize(new java.awt.Dimension(187, 47));
			{
				this.add(titleLabel, new AnchorConstraint(0, 1000, 22, 0, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				titleLabel.setPreferredSize(new java.awt.Dimension(187, 20));
				titleLabel.setBounds(0, 0, 187, 20);
				titleLabel.setVerticalAlignment(SwingConstants.BOTTOM);
			}
			{
				this.add(textField, new AnchorConstraint(22, 1000, 1000, 0, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				textField.setPreferredSize(new java.awt.Dimension(186, 19));
			}			
		}
		
	}
	
	public JLabel getLabel(){return titleLabel;}
	public JTextField getTextField(){return textField;}
	
	public void setNumeric(boolean numeric) {
		this.numeric = numeric;
	}

	public boolean isNumeric() {
		return numeric;
	}

	public void setInteger(boolean integer) {
		this.integer = integer;
	}

	public boolean isInteger() {
		return integer;
	}

	public void setLowerBound(double lowerBound) {
		if(!numeric && !integer)
			numeric = true;
		hasLowerBound = true;
		this.lowerBound = lowerBound;
	}
	
	public void removeLowerBound(){
		hasLowerBound=false;
		lowerBound = Double.MIN_VALUE;
	}

	public double getLowerBound() {
		return lowerBound;
	}

	public void setUpperBound(double upperBound) {
		if(!numeric && !integer)
			numeric = true;
		hasUpperBound = true;
		this.upperBound = upperBound;
	}

	public double getUpperBound() {
		return upperBound;
	}
	
	public void removeUpperBound(){
		hasUpperBound = false;
		upperBound = Double.MAX_VALUE;
	}
	
	public String getValidatedText(){
		String txt = textField.getText();
		if(txt==null)
			txt = "";
		if(integer){
			try{
				double d = Double.parseDouble(txt);
				int i = (int) Math.round(d);
				txt = "" + i;
				
				if(hasLowerBound){
					if(i<lowerBound){
						txt= ""+Math.ceil(lowerBound);
					}
				}
				if(hasUpperBound){
					if(i>upperBound){
						txt= ""+ Math.floor(upperBound);
					}
				}
				
			}catch(NumberFormatException ex){
				txt= "";
			}
			
		}
		
		if(numeric){
			try{
				double i = Double.parseDouble(txt);
				
				if(hasLowerBound){
					if(i<lowerBound){
						txt= ""+lowerBound;
					}
				}
				if(hasUpperBound){
					if(i>upperBound){
						txt= ""+ upperBound;
					}
				}
				
			}catch(NumberFormatException ex){
				txt= "";
			}
		}
		return txt;
	}
	
	public void focusGained(FocusEvent e) {}

	public void focusLost(FocusEvent e) {
		String txt = getValidatedText();
		textField.setText(txt);
	}
	
	/*
	 * Start DeducerWidget methods
	 * 
	 * The state (or model) is a String
	 */
	
	public Object getModel() {
		return textField.getText();
	}

	public String getRModel() {
		return "\""+Deducer.addSlashes((String) getModel())+"\"";
	}

	public String getTitle() {
		return title;
	}

	public void reset() {
		setModel(initialModel);
	}

	public void resetToLast() {
		setModel(lastModel);
	}

	public void setDefaultModel(Object model)  {
		initialModel=(String) model;
		if(lastModel==null)
			lastModel = (String) model;
	}

	public void setLastModel(Object model){
		lastModel = (String)model;
	}

	public void setModel(Object model){
		if(model == null)
			model = "";
		textField.setText((String) model);
	}

	public void setTitle(String t, boolean show) {
		title=t;
		titleLabel.setText(title);
		this.show = show;
		resetLayout();
	}

	public void setTitle(String t) {
		setTitle(t,false);
	}


	/*
	 * End DeducerWidget methods
	 */
	

	
	

}
