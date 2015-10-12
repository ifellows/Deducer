package org.rosuda.deducer.widgets;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.AddButton;
import org.rosuda.deducer.toolkit.DJList;
import org.rosuda.deducer.toolkit.RemoveButton;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.EventListener;
import java.util.Vector;

import javax.swing.BorderFactory;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionListener;

/**
 * A widget for selecting a number of variables from a data frame
 * 
 * @author Ian Fellows
 *
 */
public class VariableListWidget extends javax.swing.JPanel implements DeducerWidget, ActionListener{
	private AddButton addButton;
	private JScrollPane listScrollPane;
	private DJList varList;
	private JPanel buttonPanel;
	private JPanel listPanel;
	private RemoveButton removeButton;
	private VariableSelectorWidget selector;
	private String title;
	private DefaultListModel initialModel;
	private DefaultListModel lastModel;
	
	/**
	 * new VariableListWidget
	 * @param panelTitle title
	 * @param varSel the VariableSelector to link
	 */
	public VariableListWidget(String panelTitle, VariableSelectorWidget varSel) {
		super();
		selector = varSel;
		title = panelTitle;
		selector.getJComboBox().addActionListener(this);
		initGUI();
	}
	
	/**
	 * untitled VariableListWidget
	 * @param varSel the VariableSelector to link
	 */
	public VariableListWidget(VariableSelectorWidget varSel) {
		this(null,varSel);
	}
	
	/**
	 * Set-up GUI components
	 */
	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(239, 136));
			
			listPanel = new JPanel();
			BorderLayout listPanelLayout = new BorderLayout();
			this.add(listPanel, new AnchorConstraint(0, 1002, 1003, 64, 
					AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, 
					AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
			listPanel.setPreferredSize(new java.awt.Dimension(184, 136));
			listPanel.setLayout(listPanelLayout);
			if(title!=null)
				listPanel.setBorder(BorderFactory.createTitledBorder(title));

			listScrollPane = new JScrollPane();
			listPanel.add(listScrollPane, BorderLayout.CENTER);
			varList = new DJList();
			varList.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
			listScrollPane.setViewportView(varList);
			varList.setModel(new DefaultListModel());
			
			
			buttonPanel = new JPanel();
			this.add(buttonPanel, new AnchorConstraint(275, 140, 768, 2, 
				AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, 
				AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
			buttonPanel.setPreferredSize(new java.awt.Dimension(37, 81));
			buttonPanel.setLayout(null);
			
			addButton = new AddButton("Add",selector,varList);
			buttonPanel.add(addButton);
			addButton.setBounds(0, 0, 32, 34);
			
			removeButton = new RemoveButton("Remove",selector,varList);
			buttonPanel.add(removeButton);
			removeButton.setBounds(0, 42, 32, 34);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * monitor changes in the variableSelector
	 */
	public void actionPerformed(ActionEvent act) {
		String cmd = act.getActionCommand();
		
		if(cmd == "comboBoxChanged"){
			if(initialModel == null)
				setModel(new DefaultListModel());
			else
				setModel(initialModel);
		}
	}
	
	/**
	 * gets items in list
	 * @return items
	 */
	public String[] getVariables(){
		DefaultListModel model = (DefaultListModel) getModel();
		Object[] tmp = model.toArray();
		String[] items = new String[tmp.length];
		for(int i =0;i<tmp.length;i++)
			if(tmp[i] instanceof String)
				items[i] = (String) tmp[i];
		return items;
	}
	
	/**
	 * Set the default variables
	 * @param items
	 */
	public void setDefaultVariables(String[] items){
		initialModel = new DefaultListModel();
		for(int i=0;i<items.length;i++)
			initialModel.addElement(items[i]);
	}

	/**
	 * Sets the items in the list
	 * @param items an array containing the items
	 * @param removeFromVariableSelector should the items be removed from the VariableSelector
	 */
	public void setModel(String[] items,boolean removeFromVariableSelector){
		DefaultListModel newModel = new DefaultListModel();
		for(int i=0;i<items.length;i++)
			newModel.addElement(items[i]);
		setModel(newModel,removeFromVariableSelector);
	}
	

	/**
	 * Sets the items in the list
	 * @param mod a DefaultListModel containing the items
	 * @param removeFromVariableSelector should the items be removed from the VariableSelector
	 */
	public void setModel(DefaultListModel mod, boolean removeFromVariableSelector){

		if(removeFromVariableSelector && selector==null)
			setModel(mod,false);
		else{
/*			if(selector!=null && selector.getSelectedData()!=null)
				try {
					Vector rNames = new Vector();
					String[] names = Deducer.eval("names("+selector.getSelectedData()+")").asStrings();
					for(int i=0;i<names.length;i++)
						rNames.add(names[i]);
					DefaultListModel selLis = (DefaultListModel) selector.getJList().getModel();
					DefaultListModel curModel = (DefaultListModel) varList.getModel();
					for(int i=0; i<curModel.size(); i++){
						Object var = curModel.get(i);
						if(rNames.contains(var) && !selLis.contains(var))
							selLis.addElement(var);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
*/		
			
			if(mod==null)
				mod = new DefaultListModel();
			DefaultListModel newModel = new DefaultListModel();
			boolean exists;
			for(int i=0;i<mod.size();i++){
				if(removeFromVariableSelector){
					exists = selector.remove(mod.get(i));
					//System.out.println(exists + " " + mod.get(i));
					//if(exists)
						newModel.addElement(mod.get(i));
				}else
					newModel.addElement(mod.get(i));
			}
			varList.setModel(newModel);
		}
	}
	
	
	/**
	 * adds either an action, mouse or list selection listener 
	 * @param lis
	 */
	public void addListener(EventListener lis) {
		if(lis instanceof ActionListener){
			removeButton.addActionListener((ActionListener) lis);
			addButton.addActionListener((ActionListener) lis);
		}
		if(lis instanceof ListSelectionListener)
			varList.addListSelectionListener((ListSelectionListener) lis);
		if(lis instanceof MouseListener)
			varList.addMouseListener((MouseListener) lis);
	}

	/*
	 * Start DeducerWidget methods
	 * 
	 * The state (or model) is a DefaultListModel
	 */
	
	public Object getModel() {
		return varList.getModel();
	}

	public String getRModel() {
		String rcall = Deducer.makeRCollection(varList.getModel(),"c",true);
		return rcall;
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
		initialModel = (DefaultListModel) model;
		if(lastModel==null)
			lastModel = (DefaultListModel) model;
	}

	public void setLastModel(Object model){
		lastModel = (DefaultListModel) model;
	}

	public void setModel(Object model){
		setModel((DefaultListModel) model,true);
	}

	public void setTitle(String t, boolean show) {
		title=t;
		if(t==null)
			listPanel.setBorder(BorderFactory.createEmptyBorder());
		else if(show)
			listPanel.setBorder(BorderFactory.createTitledBorder(title));
		
	}

	public void setTitle(String t) {
		setTitle(t,false);
	}

	/*
	 * End DeducerWidget methods
	 */

}
