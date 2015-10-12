package org.rosuda.deducer.widgets;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.SingletonAddRemoveButton;
import org.rosuda.deducer.toolkit.SingletonDJList;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.EventListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


import javax.swing.DefaultListModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionListener;

/**
 * Creates a widget for selecting a single variable from a VariableSelector
 * @author Ian
 *
 */
public class SingleVariableWidget extends JPanel implements DeducerWidget, ActionListener{
	private SingletonAddRemoveButton addRemoveButton;
	private SingletonDJList singleList;
	private JPanel listPanel;
	private VariableSelectorWidget selector;
	private String title;
	private DefaultListModel initialModel;
	private DefaultListModel lastModel;
	
	/**
	 * Create a new SingleVariableWidget
	 * @param panelTitle title
	 * @param varSel VariableSelector to link
	 */
	public SingleVariableWidget(String panelTitle, VariableSelectorWidget varSel) {
		super();
		selector = varSel;
		title = panelTitle;
		selector.getJComboBox().addActionListener(this);
		initGUI();
	}
	/**
	 * create an untitled SingleVariableWidget
	 * @param varSel VariableSelector to link
	 */
	public SingleVariableWidget( VariableSelectorWidget varSel) {
		this(null,varSel);
	}
	
	/**
	 * set-up GUI components
	 */
	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(239, 65));
			{
				listPanel = new JPanel();
				BorderLayout listPanelLayout = new BorderLayout();
				this.add(listPanel, new AnchorConstraint(5, 1002, 940, 64, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
				listPanel.setPreferredSize(new java.awt.Dimension(195, 50));
				listPanel.setLayout(listPanelLayout);
				if(title!=null)
					listPanel.setBorder(BorderFactory.createTitledBorder(title));
				{
					singleList = new SingletonDJList();
					listPanel.add(singleList, BorderLayout.CENTER);
					singleList.setModel(new DefaultListModel());
					singleList.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				}
			}
			{
				addRemoveButton = new SingletonAddRemoveButton(new String[]{"Add","Remove"},
						new String[]{"Add","Remove"},singleList,selector);
				this.add(addRemoveButton, new AnchorConstraint(16, 156, 845, 0, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
				addRemoveButton.setPreferredSize(new java.awt.Dimension(32, 34));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * handles changes to the variableSelector
	 */
	public void actionPerformed(ActionEvent act) {
		String cmd = act.getActionCommand();
		if(initialModel == null)
			setModel(new DefaultListModel());
		else
			setModel(initialModel);
		
	}
	
	/**
	 * 
	 * @return the variable list
	 */
	public SingletonDJList getList(){
		return singleList;
	}
	
	/**
	 * 
	 * @return the add remove button
	 */
	public SingletonAddRemoveButton getButton(){
		return addRemoveButton;
	}
	
	public String getSelectedVariable(){
		DefaultListModel mod = (DefaultListModel) singleList.getModel();
		if(mod.size()==0)
			return null;
		else
			return mod.get(0).toString();
	}
	
	public void setSelectedVariable(String var){
		DefaultListModel mod = new DefaultListModel();
		if(var!=null)
			mod.addElement(var);
		setModel(mod);

	}
	
	public void setDefaultVariable(String var){
		initialModel = new DefaultListModel();
		if(var!=null)
			initialModel.addElement(var);
	}
	
	/**
	 * adds either an action, mouse or list selection listener 
	 * @param lis
	 */
	public void addListener(EventListener lis) {
		if(lis instanceof ActionListener)
			addRemoveButton.addActionListener((ActionListener) lis);
		if(lis instanceof ListSelectionListener)
			singleList.addListSelectionListener((ListSelectionListener) lis);
		if(lis instanceof MouseListener)
			singleList.addMouseListener((MouseListener) lis);
	}
	
	/*
	 * Start DeducerWidget methods
	 * 
	 * The state (or model) is a DefaultListModel
	 */
	
	
	public void setModel(DefaultListModel mod, boolean removeFromVariableSelector){
		if(removeFromVariableSelector && selector==null)
			setModel(mod,false);
		else{
/*			if(selector!=null && selector.getSelectedData()!=null)
				try {
					Vector rNames = new Vector();
					String[] names = Deducer.timedEval("names("+selector.getSelectedData()+")").asStrings();
					for(int i=0;i<names.length;i++)
						rNames.add(names[i]);
					DefaultListModel selLis = (DefaultListModel) selector.getJList().getModel();
					DefaultListModel curModel = (DefaultListModel) singleList.getModel();
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
					//if(exists)
						newModel.addElement(mod.get(i));
				}else
					newModel.addElement(mod.get(i));
			}
			singleList.getModel().removeListDataListener(addRemoveButton);
			singleList.setModel(newModel);
			addRemoveButton.refreshListListener();
		}
	}

	public Object getModel() {
		return singleList.getModel();
	}

	public String getRModel() {
		String rcall = Deducer.makeRCollection(singleList.getModel(),"c",true);
		return rcall;
	}

	public String getTitle() {
		return title;
	}

	public void reset() {
		String var = getSelectedVariable();
		if(var!=null)
			selector.add(var);
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
