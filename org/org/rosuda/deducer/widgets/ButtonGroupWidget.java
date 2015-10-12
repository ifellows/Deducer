package org.rosuda.deducer.widgets;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.InvalidClassException;
import java.util.EventListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * 
 * A DeducerWidget containing a a group of radio buttons.
 * 
 * The state (or model) is represented as the text of the
 * selected button.
 * 
 * @author Ian Fellows
 *
 */
public class ButtonGroupWidget extends JPanel implements DeducerWidget {
	private ButtonGroup group;
	private String title;
	private Vector radioBoxes;
	private Vector names;
	private String initialModel;
	private String lastModel;
	
	/**
	 * Creates a new ButtonGroupWidget
	 * 
	 * @param panelTitle Title of the button group. If null, no border will be created
	 * @param radioNames The names of the radio buttons
	 */
	public ButtonGroupWidget(String panelTitle, String[] radioNames) {
		super();
		names = new Vector();
		radioBoxes = new Vector();
		title=panelTitle;
		JRadioButton tmp;
		for(int i=0;i<radioNames.length;i++){
			names.add(radioNames[i]);
			tmp = new JRadioButton();
			tmp.setText(radioNames[i]);
			radioBoxes.add(tmp);
		}
		initGUI();
	}
	
	/**
	 * Creates a new untitled button group
	 * 
	 * @param radioNames The names of the radio buttons
	 */
	public ButtonGroupWidget( String[] radioNames) {
		this(null,radioNames);
	}
	
	/**
	 * Set-up the gui components
	 */
	private void initGUI() {
		try {
			group = new ButtonGroup();
			this.setPreferredSize(new java.awt.Dimension(146, 149));
			BoxLayout thisLayout = new BoxLayout(this, javax.swing.BoxLayout.Y_AXIS);
			this.setLayout(thisLayout);
			if(title!=null)
				this.setBorder(BorderFactory.createTitledBorder(title));
			JRadioButton radioBox;
			for(int i=0;i<radioBoxes.size();i++){
				radioBox = (JRadioButton) radioBoxes.get(i);
				group.add(radioBox);
				if(i==0)
					this.add(Box.createVerticalGlue());
				this.add(radioBox);
				this.add(Box.createVerticalGlue());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the radio buttons
	 * @return A Vector of JRadioButtons
	 */
	public Vector getButtons(){
		return radioBoxes;
	}
	
	/**
	 * Get button names
	 * 
	 * @return A Vector of Strings representing the button names
	 */
	public String[] getNames(){
		
		return (String[]) names.toArray();
	}
	
	/**
	 * Get the text of the selected Item
	 * @return text of selected item
	 */
	public String getSelectedItemText(){
		return (String) getModel();
	}
	
	/**
	 * Get Selected
	 * @return the selected button
	 */
	public JRadioButton getSelectedButton(){
		JRadioButton tmp;
		for(int i=0;i<radioBoxes.size();i++){
			tmp = (JRadioButton) radioBoxes.get(i);
			if(tmp.isSelected())
				return tmp;
		}
		return null;
	}
	
	/**
	 * Sets the selected button
	 * @param name
	 */
	public void setSelected(String name){
		setModel(name);
	}
	
	/**
	 * Sets the selected button
	 * @param button selected button
	 */
	public void setSelected(JRadioButton button){
		setModel(button.getText());
	}
	
	/**
	 * Adds a button to the group
	 * @param button A JRadioButton to add
	 */
	public void addButton(JRadioButton button){
		group.add(button);
		this.add(button);
		radioBoxes.add(button);
		names.add(button.getText());
	}
	
	/**
	 * Adds a button to the group
	 * @param buttonText
	 */
	public void addButton(String buttonText){
		JRadioButton button = new JRadioButton(buttonText);
		group.add(button);
		this.add(button);
		radioBoxes.add(button);
		names.add(button.getText());
	}
	
	/**
	 * remove a button
	 * 
	 * @param i the index of the button to remove
	 */
	public void removeButton(int i){
		JRadioButton but = (JRadioButton) radioBoxes.remove(i);
		names.remove(i);
		this.remove(but);
	}

	/**
	 * adds either an action or mouse listener to each box
	 * @param lis
	 */
	public void addListener(EventListener lis) {
		JRadioButton tmp;
		for(int i=0;i<radioBoxes.size();i++){
			tmp = (JRadioButton) radioBoxes.get(i);
			if(lis instanceof ActionListener)
				tmp.addActionListener((ActionListener) lis);
			if(lis instanceof MouseListener)
				tmp.addMouseListener((MouseListener) lis);
		}
	}
	
	/*
	 * Start DeducerWidget methods
	 * 
	 * The state (or model) is represented as the text of the
	 * selected button.
	 */
	

	public Object getModel() {
		JRadioButton tmp;
		for(int i=0;i<radioBoxes.size();i++){
			tmp = (JRadioButton) radioBoxes.get(i);
			if(tmp.isSelected())
				return(tmp.getText());
		}
		return null;
	}

	public String getRModel() {
		Object butName = getModel();
		if(butName==null)
			return("c()");
		else
			return("\""+((String)butName)+"\"");
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

	public void setLastModel(Object model){
		if(model==null)
			model = names.get(0);

		lastModel = (String) model;
	}

	public void setModel(Object model) {
		if(model==null)
			model = names.get(0);

		String newModel = (String) model;
		JRadioButton tmp;
		for(int i=0;i<radioBoxes.size();i++){
			tmp = (JRadioButton) radioBoxes.get(i);
			if(tmp.getText().equals(newModel)){
				tmp.setSelected(true);
			}	
		}
		
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

	public void setDefaultModel(Object model) {
		if(model==null)
			model = names.get(0);
		initialModel = (String) model;
		if(lastModel==null)
			lastModel = (String) model;
	}

	
	/*
	 * End DeducerWidget methods
	 */
	

}
