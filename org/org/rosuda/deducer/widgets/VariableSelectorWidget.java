package org.rosuda.deducer.widgets;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;

import org.rosuda.deducer.toolkit.VariableSelector;

public class VariableSelectorWidget extends VariableSelector implements DeducerWidget{
	private String title;
	private String initialModel;
	private String lastModel;
	
	
	/*
	 * Start DeducerWidget methods
	 * 
	 * The state (or model) is a String representing the selected data
	 */
	
	public Object getModel() {
		return this.getSelectedData();
	}

	public String getRModel() {
		return "\""+getModel() + "\"";
	}

	public String getTitle() {
		return title;
	}

	public void reset() {
		super.reset();
		setModel(initialModel);
	}

	public void resetToLast() {
		refreshDataNames();
		setModel(lastModel);
	}

	public void setDefaultModel(Object model){
		initialModel = (String) model;
		if(lastModel==null)
			lastModel = (String) model;
	}

	public void setLastModel(Object model){
		lastModel = (String) model;
	}

	public void setModel(Object model){
		super.reset();
		if(model==null)
			model = this.getSelectedData();
		String dat = (String) model;
		if(dat!=null)
			this.setSelectedData(dat);
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
