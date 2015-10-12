package org.rosuda.deducer.widgets;

import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;

import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.DJList;


/**
 * Implements a list
 * @author Ian
 *
 */
public class ListWidget extends JPanel implements DeducerWidget{
	
	protected DJList list;
	protected DefaultListModel listModel;
	protected DefaultListModel lastModel;
	protected DefaultListModel initialModel;
	protected JScrollPane scroller;
	protected String title;
	
	
	public ListWidget(){
		initGUI();
	}
	
	public ListWidget(String theTitle){
		title = theTitle;
		initGUI();
	}
	
	public ListWidget(String theTitle, String[] elements){
		title=theTitle;
		initGUI();
		setDefaultModel(elements);
	}
	
	public ListWidget(String[] elements){
		initGUI();
		setDefaultModel(elements);		
	}
	
	public void initGUI(){
		scroller= new JScrollPane();
		list = new DJList();
		listModel = new DefaultListModel();
		initialModel = new DefaultListModel();
		BorderLayout thisLayout = new BorderLayout();
		this.setLayout(thisLayout);
		this.add(scroller);
		scroller.setViewportView(list);
		list.setModel(listModel);
		if(title!=null)
			this.setBorder(BorderFactory.createTitledBorder(title));
			
	}
	
	public void addItems(Object[] elements){
		for(int i = 0;i<elements.length;i++)
			listModel.addElement(elements[i]);
	}
	
	public void addItem(Object element){
		listModel.addElement(element);
	}
	
	public void addItem(Object element, int index){
		listModel.add(index, element);
	}
	
	public void addItem(String element, int index){
		listModel.add(index, element);
	}
	
	public void addItems(String[] elements){
		for(int i = 0;i<elements.length;i++)
			listModel.addElement(elements[i]);
	}
	
	public void addItem(String element){
		listModel.addElement(element);
	}
	
	public void setDefaultModel(Object[] elements){
		initialModel = new DefaultListModel();
		for(int i = 0;i<elements.length;i++)
			initialModel.addElement(elements[i]);		
	}
	
	public void setDefaultModel(String[] elements){
		initialModel = new DefaultListModel();
		for(int i = 0;i<elements.length;i++)
			initialModel.addElement(elements[i]);
		/*if(lastModel==null)
			for(int i = 0;i<elements.length;i++)
				lastModel.addElement(elements[i]);*/	
	}
	
	public boolean removeItems(Object[] elements){
		boolean allExist = true;
		for(int i = 0;i<elements.length;i++){
			boolean exists =listModel.removeElement(elements[i]);
			if(!exists)
				allExist= false;
		}
		return allExist;
	}
	
	public boolean removeItems(String[] elements){
		return removeItems(elements);
	}
	
	public boolean removeItem(Object element){
		return listModel.removeElement(element);
	}
	
	public boolean removeItem(String element){
		return listModel.removeElement(element);
	}
	
	public Object removeItem(int index){
		return listModel.remove(index);
	}
	
	public void removeAllItems(){
		listModel.removeAllElements();
	}
	
	public DJList getList(){return list;}
	public DefaultListModel getListModel(){return listModel;}
	
	public Object[] getSelectedItems(){
		return list.getSelectedValues();
	}
	
	public String[] getItems(){
		String[] items = new String[list.getModel().getSize()];
		for(int i=0;i<list.getModel().getSize();i++)
			items[i] = (String) list.getModel().getElementAt(i);
		return items;
	}
	
	/**
	 * adds either an action or mouse listener to each box
	 * @param lis
	 */
	public void addListener(EventListener lis) {
			if(lis instanceof ListSelectionListener)
				list.addListSelectionListener((ListSelectionListener) lis);
			if(lis instanceof MouseListener)
				list.addMouseListener((MouseListener) lis);
			if(lis instanceof KeyListener)
				list.addKeyListener((KeyListener) lis);
	}
	
	
	/* DeducerWidget interface */
	
	public Object getModel() {
		return listModel;
	}

	public String getRModel() {
		return Deducer.makeRCollection(listModel, "c", true);
	}

	public String getTitle() {
		return title;
	}

	public void reset() {
		DefaultListModel mod = new DefaultListModel();
		for(int i=0;i<initialModel.size();i++)
			mod.addElement(initialModel.get(i));
		listModel = mod;
		list.setModel(listModel);
	}

	public void resetToLast() {
		if(lastModel==null){
			reset();
			return;
		}		
		DefaultListModel mod = new DefaultListModel();
		for(int i=0;i<lastModel.size();i++)
			mod.addElement(lastModel.get(i));
		listModel = mod;
		list.setModel(listModel);
	}

	public void setDefaultModel(Object model) {
		DefaultListModel pModel = (DefaultListModel) model;
		DefaultListModel mod = new DefaultListModel();
		for(int i=0;i<pModel.size();i++)
			mod.addElement(pModel.get(i));
		initialModel = pModel;
	}

	public void setLastModel(Object model) {
		DefaultListModel pModel = (DefaultListModel) model;
		DefaultListModel mod = new DefaultListModel();
		for(int i=0;i<pModel.size();i++)
			mod.addElement(pModel.get(i));
		lastModel = pModel;
	}

	public void setModel(Object model) {
		DefaultListModel pModel = (DefaultListModel) model;
		DefaultListModel mod = new DefaultListModel();
		for(int i=0;i<pModel.size();i++)
			mod.addElement(pModel.get(i));
		listModel = pModel;
		list.setModel(listModel);
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
	

}
