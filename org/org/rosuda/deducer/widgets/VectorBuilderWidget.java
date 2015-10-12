package org.rosuda.deducer.widgets;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.deducer.Deducer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.event.ListSelectionListener;



public class VectorBuilderWidget extends javax.swing.JPanel implements ActionListener, DeducerWidget{

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new VectorBuilderWidget());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(100, 100);
		frame.setVisible(true);
	}
	
	protected JList list;
	protected DefaultListModel listModel;
	protected DefaultListModel lastModel;
	protected DefaultListModel initialModel;
	protected JScrollPane scroller;
	private JButton add;
	protected String title;
	private JButton remove;
	protected boolean isNumeric=true;
	
	public VectorBuilderWidget(){
		initGUI();
	}
	
	public VectorBuilderWidget(String theTitle){
		title = theTitle;
		initGUI();
	}
	
	public VectorBuilderWidget(String theTitle, String[] elements){
		title=theTitle;
		initGUI();
		setDefaultModel(elements);
	}
	
	public VectorBuilderWidget(String[] elements){
		initGUI();
		setDefaultModel(elements);		
	}
	public void initGUI(){
		AnchorLayout thisLayout = new AnchorLayout();
		this.setLayout(thisLayout);
		this.setPreferredSize(new java.awt.Dimension(89, 195));
		{
			add = new JButton();
			this.add(add, new AnchorConstraint(173, 1000, 1000, 511, 
					AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, 
					AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			add.setText("+");
			add.setPreferredSize(new java.awt.Dimension(44, 22));
			add.addActionListener(this);
		}
		{
			remove = new JButton();
			this.add(remove, new AnchorConstraint(173, 511, 1000, 0, 
					AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, 
					AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			remove.setText("-");
			remove.setPreferredSize(new java.awt.Dimension(44, 22));
			remove.addActionListener(this);
		}
		{
			scroller = new JScrollPane();
			this.add(scroller, new AnchorConstraint(2, 1005, 22, 5, 
					AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
					AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL));
			scroller.setPreferredSize(new java.awt.Dimension(89, 173));
			{
				list = new JList();
				scroller.setViewportView(list);
			}
		}
		listModel = new DefaultListModel();
		list.setModel(listModel);
		initialModel = new DefaultListModel();
		if(title!=null)
			this.setBorder(BorderFactory.createTitledBorder(title));
	}
	
	public void setNumeric(boolean numeric){
		isNumeric = numeric;
	}
	
	public boolean isNumeric(){
		return isNumeric;
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
	
	public JList getList(){return list;}
	public DefaultListModel getListModel(){return listModel;}
	
	public Object[] getSelectedItems(){
		return list.getSelectedValues();
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
		if(t==null){
			this.setBorder(BorderFactory.createEmptyBorder());
		}else if(show)
			this.setBorder(BorderFactory.createTitledBorder(title));
		
	}

	public void setTitle(String t) {
		setTitle(t,false);
	}

	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if(cmd == "-"){
			Object[] objs = list.getSelectedValues();
			if(objs!=null){
				for(int i=0;i<objs.length;i++)
					listModel.removeElement(objs[i]);
			}
		}else if(cmd == "+"){
			String a = JOptionPane.showInputDialog(add, "Enter a value");
			if(a!=null){
				if(isNumeric){
					try{
						Double.parseDouble(a);
						listModel.addElement(a);
					}catch(Exception e){
						JOptionPane.showMessageDialog(add, "Invalid entry. must be a number.");
					}
				}else{
					if(!a.startsWith("\""))
						a = "\"" + a;
					if(!a.endsWith("\""))
						a = a + "\"";
					listModel.addElement(a);
				}
			}
		}
	}

}
