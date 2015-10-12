package org.rosuda.deducer.widgets;

import java.io.InvalidClassException;
import java.util.EventListener;

public interface DeducerWidget {

	/**
	 * Sets the current state of the widget
	 * @param model The new state. Each widget accepts a different type of
	 * 				Object.
	 */
	void setModel(Object model);
	
	/**
	 * Get the current state of the widget
	 * @return  The current state of the widget. Each widget returns a different
	 * 			type of Object.
	 */
	Object getModel();
	
	/**
	 * Updates the last completed state
	 * @param model The state of the widget the last time the dialog was run. 
	 * 				Each widget returns a different
	 * 				type of Object.
	 */
	void setLastModel(Object model);
	
	/**
	 * Sets the default state of the model
	 * @param model The default state of the widget. Each widget returns a different
	 * 			type of Object.
	 */
	void setDefaultModel(Object model);
	
	/**
	 * Sets state of widget to the last time the dialog was completed
	 */
	void resetToLast();
	
	/**
	 * Sets the state of the widget to its default value
	 */
	void reset();
	
	/**
	 * Gets R representation of state
	 * 
	 * @return 	A String representing the current state of the widget which is
	 * 			interpretable by R.
	 */
	String getRModel();
	
	/**
	 * Sets the title of the widget
	 * @param t	Title
	 * @param show	Should it be displayed as a titled border.
	 */
	void setTitle(String t,boolean show);
	
	/**
	 * Sets the title of the widget with no display
	 * @param t title
	 */
	void setTitle(String t);
	
	/**
	 * title
	 * @return title
	 */
	String getTitle();
	
	//perhaps add this later?
	//void addListener(EventListener lis);
	
}
