package org.rosuda.deducer.widgets;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import java.util.EventListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.rosuda.deducer.Deducer;

/**
 * 
 * A DeducerWidget containing a series of check boxes
 * 
 * The state (or model) is a Vector of the 
 * names of the selected buttons
 * @author Ian
 *
 */
public class CheckBoxesWidget extends javax.swing.JPanel implements DeducerWidget {
	private JPanel TitledPanel;
	private String title;
	private Vector checkboxes;
	private Vector names;
	
	private Vector initialModel;
	private Vector lastModel;
	
	/**
	 * Creates a new check box widget
	 * @param panelTitle	Title
	 * @param checkBoxNames Names of the check boxes
	 * @param numColumns	Number of columns to lay out the boxes in
	 */
	public CheckBoxesWidget(String panelTitle,String[] checkBoxNames,int numColumns) {
		super();
		names = new Vector();
		checkboxes = new Vector();
		title=panelTitle;
		JCheckBox tmp;
		for(int i=0;i<checkBoxNames.length;i++){
			names.add(checkBoxNames[i]);
			tmp = new JCheckBox();
			tmp.setText(checkBoxNames[i]);
			checkboxes.add(tmp);
		}
		
		initGUI(numColumns);
	}
	
	/**
	 * Creates a new untitled check box widget
	 * @param checkBoxNames Names of the check boxes
	 * @param numColumns	Number of columns to lay out the boxes in
	 */
	public CheckBoxesWidget(String[] checkBoxNames,int numColumns) {
		this(null,checkBoxNames,numColumns);
	}
	
	/**
	 * Creates a new untitled check box widget with one column
	 * @param checkBoxNames Names of the check boxes
	 */
	public CheckBoxesWidget(String[] checkBoxNames) {
		this(null,checkBoxNames,1);
	}
	
	/**
	 * Creates a new check box widget with one column
	 * @param panelTitle	Title
	 * @param checkBoxNames Names of the check boxes
	 */
	public CheckBoxesWidget(String panelTitle,String[] checkBoxNames) {
		this(panelTitle,checkBoxNames,1);
	}
	
	/**
	 * Set-up GUI
	 * @param cols number of columns
	 */
	private void initGUI(int cols) {
		try {
			this.setLayout(new BorderLayout());
			
			this.setPreferredSize(new java.awt.Dimension(191, 127));
			{
				TitledPanel = new JPanel();
				TitledPanel.setLayout(new GridLayout(0,cols));
				this.add(TitledPanel, BorderLayout.CENTER);
				if(title!=null)
					TitledPanel.setBorder(BorderFactory.createTitledBorder(title));
				JCheckBox checkBox;
				for(int i=0;i<checkboxes.size();i++){
					checkBox = (JCheckBox) checkboxes.get(i);
					TitledPanel.add(checkBox);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Get the check boxes
	 * @return A Vector of JCheckBoxes
	 */
	public Vector getBoxes(){
		return checkboxes;
	}
	
	/**
	 * Get box names
	 * 
	 * @return A Vector of Strings representing the box names
	 */
	public String[] getNames(){
		
		return (String[]) names.toArray();
	}
	
	/**
	 * Get the text of the selected Item
	 * @return text of selected item
	 */
	public String[] getSelectedItemText(){
		Vector m = (Vector)getModel();
		String[] tmp = new String[m.size()];
		for(int i=0;i<m.size();i++)
			tmp[i] = (String) m.elementAt(i);
		return tmp;
	}
	
	/**
	 * Get Selected
	 * @return the checked boxes
	 */
	public JCheckBox[] getSelectedButtons(){
		JCheckBox tmp;
		Vector selBoxes = new Vector();
		for(int i=0;i<checkboxes.size();i++){
			tmp = (JCheckBox) checkboxes.get(i);
			if(tmp.isSelected())
				selBoxes.add(tmp);
		}
		return null;
	}
	
	/**
	 * Sets the selected button
	 * @param name text of box
	 * @param selected set to selected or deselected?
	 */
	public void setSelected(String name, boolean selected){
		JCheckBox tmp;
		for(int i=0;i<checkboxes.size();i++){
			tmp = (JCheckBox) checkboxes.get(i);
			if(tmp.getText().equals(name))
				tmp.setSelected(selected);
		}
	}
	
	/**
	 * Sets the selected button
	 * @param button selected button
	 */
	public void setSelected(JCheckBox button,boolean selected){
		setSelected(button.getText(),selected);
	}
	
	/**
	 *  sets the default selected boxes
	 * @param model names
	 */
	public void setDefaultModel(String[] model){
		Vector vec = new Vector();
		for(int i=0;i<model.length;i++)
			vec.add(model[i]);
		setDefaultModel(vec);
	}
	
	/**
	 *  sets the default selected box
	 * @param model names
	 */
	public void setDefaultModel(String model){
		Vector vec = new Vector();
		vec.add(model);
		setDefaultModel(vec);
	}
	
	/**
	 * Adds a button to the group
	 * @param button A JCheckBox to add
	 */
	public void addButton(JCheckBox button){
		TitledPanel.add(button);
		checkboxes.add(button);
		names.add(button.getText());
	}
	
	/**
	 * Adds a button to the group
	 * @param buttonText
	 */
	public void addButton(String buttonText){
		JCheckBox button = new JCheckBox(buttonText);
		TitledPanel.add(button);
		checkboxes.add(button);
		names.add(button.getText());
	}
	
	/**
	 * remove a button
	 * 
	 * @param i the index of the button to remove
	 */
	public void removeButton(int i){
		JCheckBox but = (JCheckBox) checkboxes.remove(i);
		if(but!=null){
			names.remove(i);
			TitledPanel.remove(but);
		}
	}
	
	
	/**
	 * adds either an action or mouse listener to each box
	 * @param lis
	 */
	public void addListener(EventListener lis) {
		JCheckBox tmp;
		for(int i=0;i<checkboxes.size();i++){
			tmp = (JCheckBox) checkboxes.get(i);
			if(lis instanceof ActionListener)
				tmp.addActionListener((ActionListener) lis);
			if(lis instanceof MouseListener)
				tmp.addMouseListener((MouseListener) lis);
		}
	}
	
	/*
	 * Start DeducerWidget methods
	 * 
	 * The state (or model) is a Vector of the 
	 * names of the selected buttons
	 */
	
	public Object getModel() {
		JCheckBox tmp;
		Vector selectedItems = new Vector();
		for(int i=0;i<checkboxes.size();i++){
			tmp = (JCheckBox) checkboxes.get(i);
			if(tmp.isSelected())
				selectedItems.add(tmp.getText());
		}
		return selectedItems;
	}

	public String getRModel() {
		Vector items = (Vector) getModel();
		return Deducer.makeRCollection(items,"c",true);
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
		if(model==null){
			initialModel = new Vector();
			if(lastModel==null)
				lastModel = new Vector();
		}else{
			initialModel = (Vector) model;
			if(lastModel==null)
				lastModel = (Vector) model;
		}
		
	}

	public void setLastModel(Object model){
		if(model==null){
			lastModel = new Vector();
		}else{
			lastModel = (Vector) model;
		}
		
	}

	public void setModel(Object model){
		if(model==null)
			model = new Vector();
		for(int i=0;i<checkboxes.size();i++){
			((JCheckBox)checkboxes.get(i)).setSelected(false);
		}
		for(int j=0;j<((Vector) model).size();j++){
			String selectedName = (String) ((Vector) model).get(j);
			for(int i=0;i<checkboxes.size();i++){
				String name = (String) names.get(i);
				if(name.equals(selectedName))
					((JCheckBox)checkboxes.get(i)).setSelected(true);
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
	
	/*
	 * End DeducerWidget methods
	 */

}
