package org.rosuda.deducer.widgets;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.io.InvalidClassException;
import java.util.EventListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import org.rosuda.deducer.Deducer;


/**
 * A drop-down combo-box
 * @author Ian
 *
 */
public class ComboBoxWidget extends javax.swing.JPanel implements DeducerWidget {
	private JComboBox comboBox;
	private String title;
	private DefaultComboBoxModel model;
	private String initialModel;
	private String lastModel;
	
	public ComboBoxWidget(String panelTitle,String[] items) {
		super();
		model = new DefaultComboBoxModel(items);
		title = panelTitle;
		initGUI();
	}
	
	public ComboBoxWidget(String[] items) {
		this(null, items);
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(151, 21));
			if(title!=null)
				this.setBorder(BorderFactory.createTitledBorder(title));
			{
				comboBox = new JComboBox();
				this.add(comboBox, BorderLayout.CENTER);
				comboBox.setModel(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return The JComboBox
	 */
	public JComboBox getComboBox(){
		return comboBox;
	}
	
	/**
	 * adds either an action, item or mouse listener 
	 * @param lis
	 */
	public void addListener(EventListener lis) {
		if(lis instanceof ActionListener)
			comboBox.addActionListener((ActionListener) lis);
		if(lis instanceof MouseListener)
			comboBox.addMouseListener((MouseListener) lis);
		if(lis instanceof ItemListener)
			comboBox.addItemListener((ItemListener) lis);
	}

	
	/*
	 * Start DeducerWidget methods
	 */
	
	public Object getModel() {
		return comboBox.getSelectedItem();
	}

	public String getRModel() {
		return "\"" + Deducer.addSlashes(comboBox.getSelectedItem().toString()) + "\"";
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
		if(model==null)
			comboBox.setSelectedIndex(0);
		comboBox.setSelectedItem(model);
	}

	public void setTitle(String t, boolean show) {
		title=t;
		if(t==null)
			this.setBorder(BorderFactory.createEmptyBorder());
		else if(show)
			this.setBorder(BorderFactory.createTitledBorder(title));
		
	}

	public void setTitle(String t) {
		setTitle(t,false);
	}
	
	/*
	 * End DeducerWidget methods
	 */
}
