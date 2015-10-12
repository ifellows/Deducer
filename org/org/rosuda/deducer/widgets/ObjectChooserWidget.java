package org.rosuda.deducer.widgets;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.deducer.Deducer;

public class ObjectChooserWidget extends JPanel implements DeducerWidget, WindowListener{
	private JLabel titleLabel;
	private JComboBox  objectCombo;
	private String title = null;
	private boolean show = true;
	
	private String clss = "NULL";
	private boolean includeInherited = true;
	
	public static Color labelColor = new Color(90,90,90);
	
	String initialModel = null;
	String lastModel = null;
	
	/**
	 * 
	 * @param panelTitle title
	 */
	public ObjectChooserWidget(String title,Window parentWindow) {
		super();
		initGUI();
		this.title = title;
		if(title==null)
			show=false;
		else
			titleLabel.setText(title);
		resetLayout();
		parentWindow.addWindowListener(this);
	}
	
	public ObjectChooserWidget(Window parentWindow) {
		this(null,parentWindow);
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
					objectCombo = new JComboBox();
					this.add(objectCombo, new AnchorConstraint(22, 1000, 1000, 0, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					objectCombo.setPreferredSize(new java.awt.Dimension(186, 19));
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void resetLayout(){
		
		if(title==null || !show){
			this.remove(titleLabel);
			this.remove(objectCombo);
			this.setPreferredSize(new java.awt.Dimension(187, 29));
			{
				this.add(objectCombo, new AnchorConstraint(0, 1000, 1000, 0, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				objectCombo.setPreferredSize(new java.awt.Dimension(186, 19));
			}					
		}else{
			this.remove(titleLabel);
			this.remove(objectCombo);
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
				this.add(objectCombo, new AnchorConstraint(22, 1000, 1000, 0, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				objectCombo.setPreferredSize(new java.awt.Dimension(186, 19));
			}			
		}
	}
	
	public JLabel getLabel(){return titleLabel;}
	public JComboBox getComboBox(){return objectCombo;}
	
	
	public void refreshObjects(){
		try{
		String[] objs;
		try {
			String call = "get.objects("+getClassFilter()+",includeInherited="+
					(isIncludeInherited()? "TRUE":"FALSE")+")";
			objs = Deducer.timedEval(call).asStrings();
		} catch (Exception e) {
			//e.printStackTrace();
			objs = new String[]{};
		}
		ActionListener[] alis = objectCombo.getActionListeners();
		DefaultComboBoxModel newModel = new DefaultComboBoxModel(objs);
		String selObj = (String) objectCombo.getSelectedItem();
		for(int i=0;i<alis.length;i++)
			objectCombo.removeActionListener(alis[i]);
		
		boolean contained = false;
		for(int i=0;i<objs.length;i++)
			if(objs[i].equals(selObj))
				contained = true;
		
		if(contained){
			objectCombo.setModel(newModel);
			objectCombo.setSelectedItem(selObj);
			for(int i=0;i<alis.length;i++)
				objectCombo.addActionListener(alis[i]);
		}else{
			objectCombo.setModel(newModel);
			for(int i=0;i<alis.length;i++)
				objectCombo.addActionListener(alis[i]);
			try{
				objectCombo.setSelectedIndex(0);
			}catch(Exception e){}
		}
		}catch(Exception ex){ex.printStackTrace();}
	}
	
	/**
	 * only show R objects of class
	 * 
	 * @param className R class name
	 */
	public void setClassFilter(String className) {
		if(className.equals("NULL"))
			this.clss = "NULL";
		else
			this.clss = "\"" + className + "\"";
	}

	public String getClassFilter() {
		return clss;
	}

	/**
	 * Should objects inheriting the class filter be included
	 * @param includeInherited
	 */
	public void setIncludeInherited(boolean includeInherited) {
		this.includeInherited = includeInherited;
	}

	public boolean isIncludeInherited() {
		return includeInherited;
	}
	
	
	
	/*
	 * Start DeducerWidget methods
	 */
	
	public Object getModel() {
		return objectCombo.getSelectedItem();
	}

	public String getRModel() {
		Object str = objectCombo.getSelectedItem();
		if(str!=null)
			return  "\"" +str.toString() +"\"";
		else 
			return "NULL";
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

	public void setDefaultModel(Object model){
		initialModel = (String) model;
		if(lastModel==null)
			lastModel = (String) model;
	}

	public void setLastModel(Object model) {
		lastModel = (String) model;
	}

	public void setModel(Object model) {
		if(objectCombo.getModel().getSize()==0)
			return;
		else if(model==null)
			objectCombo.setSelectedIndex(0);
		else
			objectCombo.setSelectedItem(model);
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
	
	/*
	 * WindowListener methods
	 */
	
	public void windowOpened(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {
		refreshObjects();
	}
	public void windowDeactivated(WindowEvent e) {}


	/*
	 * End WindowListener
	 */
}
