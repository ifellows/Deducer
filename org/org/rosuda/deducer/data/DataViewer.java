package org.rosuda.deducer.data;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.rosuda.JGR.JGR;
import org.rosuda.JGR.RController;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.REngine.REXP;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.toolkit.LoadData;
import org.rosuda.deducer.toolkit.SaveData;
import org.rosuda.ibase.Common;
import org.rosuda.ibase.toolkit.EzMenuSwing;
import org.rosuda.ibase.toolkit.TJFrame;

public class DataViewer extends TJFrame implements ActionListener{
	private JTabbedPane tabbedPane;
	private JPanel dataSelectorPanel;
	private IconButton saveButton;
	private IconButton openButton;
	private IconButton removeButton;
	private JComboBox dataSelector;
	private ArrayList tabs = new ArrayList();
	private DataObject dataObj;
	
	
	
	public DataViewer() {
		super("Data Viewer", false, TJFrame.clsPackageUtil);
		try{
			DataViewerController.init();
			initGUI();
			DataViewerController.addViewerWindow(this);
			DataObject robj = (DataObject)dataSelector.getSelectedItem();
			String data = null;
			if(robj != null)
				data = robj.getName();
			dataObj=robj;
			reloadTabs(dataObj);
		}catch(Exception e){
			reloadTabs(null);
			e.printStackTrace();
		}
		new Thread(new Refresher(this)).start();
	}
	
	
	private void initGUI() {
		try {
			this.setName("Data Viewer");
			DataViewerController.refreshData();
			
			BorderLayout thisLayout = new BorderLayout();
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setMinimumSize(new java.awt.Dimension(400, 400));

			{
				dataSelectorPanel = new JPanel();
				getContentPane().add(dataSelectorPanel, BorderLayout.NORTH);
				AnchorLayout dataSelectorPanelLayout = new AnchorLayout();
				dataSelectorPanel.setLayout(dataSelectorPanelLayout);
				dataSelectorPanel.setPreferredSize(new java.awt.Dimension(839, 52));
				dataSelectorPanel.setSize(10, 10);
				dataSelectorPanel.setMinimumSize(new java.awt.Dimension(100, 100));
				{
					saveButton = new IconButton("/icons/kfloppy.png","Save Data",this,"Save Data");
					saveButton.setContentAreaFilled(false);
					if(DataViewerController.showSaveDataButton())
						dataSelectorPanel.add(saveButton, new AnchorConstraint(12, 60, 805, 62, 
										AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, 
										AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
					saveButton.setFont(new java.awt.Font("Dialog",0,8));
					saveButton.setPreferredSize(new java.awt.Dimension(32,32));
					
				
				}
				{
					JSeparator separator2 = new JSeparator();
					dataSelectorPanel.add(separator2, new AnchorConstraint(-76, 620, 1008, 608, 
										AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
										AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					separator2.setPreferredSize(new java.awt.Dimension(10, 64));
					separator2.setOrientation(SwingConstants.VERTICAL);
				}
				{
					JSeparator separator1 = new JSeparator();
					dataSelectorPanel.add(separator1, new AnchorConstraint(8, 415, 1076, 402, 
										AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
										AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					separator1.setPreferredSize(new java.awt.Dimension(11, 63));
					separator1.setOrientation(SwingConstants.VERTICAL);
				}
				{
					JLabel dataSelectorLabel = new JLabel();
					dataSelectorPanel.add(dataSelectorLabel, new AnchorConstraint(48, 595, 432, 415, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					dataSelectorLabel.setText("Data Set");
					dataSelectorLabel.setPreferredSize(new java.awt.Dimension(151, 20));
					dataSelectorLabel.setFont(new java.awt.Font("Dialog",0,14));
					dataSelectorLabel.setHorizontalAlignment(SwingConstants.CENTER);
					dataSelectorLabel.setHorizontalTextPosition(SwingConstants.CENTER);
				}
				{
					DataFrameComboBoxModel dataSelectorModel = //new DataFrameComboBoxModel();
						new DataFrameComboBoxModel(DataViewerController.getDataSets());
					BasicComboBoxRenderer dataSelectorRenderer = new BasicComboBoxRenderer(){
						public Component getListCellRendererComponent(JList list, Object value, 
								int index, boolean isSelected, boolean cellHasFocus){
							return super.getListCellRendererComponent(list, value==null ? null : ((DataObject)value).getName(), index, isSelected, cellHasFocus);
						}
					};
					dataSelector = new JComboBox();
					dataSelectorPanel.add(dataSelector, new AnchorConstraint(432, 594, 906, 415, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					dataSelector.setModel(dataSelectorModel);
					//dataSelector.setRenderer(dataSelectorRenderer);
					dataSelector.setPreferredSize(new java.awt.Dimension(149, 28));
					dataSelector.addActionListener(this);
				}
				{
					openButton = new IconButton("/icons/opendata_24.png","Open Data",this,"Open Data");
					openButton.setContentAreaFilled(false);
					if(DataViewerController.showOpenDataButton())
						dataSelectorPanel.add(openButton, new AnchorConstraint(12, 60, 805, 12, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
					openButton.setPreferredSize(new java.awt.Dimension(32,32));
				}
				{
					removeButton = new IconButton("/icons/trashcan_remove_32.png","Remove from Workspace",
							this,"Clear Data");
					removeButton.setContentAreaFilled(false);
					if(DataViewerController.showClearDataButton())
						dataSelectorPanel.add(removeButton, new AnchorConstraint(144, 12, 971, 863, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE));
					removeButton.setPreferredSize(new java.awt.Dimension(40,40));
				}
			}
			
			{
				tabbedPane = new JTabbedPane();
				getContentPane().add(tabbedPane, BorderLayout.CENTER);
				tabbedPane.setPreferredSize(new java.awt.Dimension(839, 395));
				tabbedPane.setTabPlacement(JTabbedPane.LEFT);
			}
			
			if(!Common.isMac()){
				tabbedPane.setTabPlacement(JTabbedPane.TOP);
			}
			
			pack();
			int mw = Toolkit.getDefaultToolkit().getScreenSize().width-100;
			int mh = Toolkit.getDefaultToolkit().getScreenSize().height-50;
			this.setSize(Math.min(839,mw), Math.min(839,mh));
			
			final JFrame theFrame = this;
			this.addComponentListener(new java.awt.event.ComponentAdapter() {
				  public void componentResized(ComponentEvent event) {
					  theFrame.setSize(
				      Math.max(300, theFrame.getWidth()),
				      theFrame.getHeight());
				  }
			});
			

			tabbedPane.addChangeListener(new ChangeListener(){
				public void stateChanged(ChangeEvent changeEvent) {
					Component comp = tabbedPane.getSelectedComponent();
					//System.out.println(comp.getClass().toString());
					if(comp instanceof org.rosuda.deducer.data.DataViewerTab){
						DataViewerTab dvt = (DataViewerTab) comp;
						setJMenuBar(dvt.generateMenuBar());
					}
				}
			});
			
			this.addWindowListener(new WindowAdapter(){
				public void windowClosed(WindowEvent arg0) {
					DataViewerController.removeViewerWindow(DataViewer.this);
					cleanUp();
				}
				
			});

		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}

	public void reloadTabs(DataObject data){
		cleanUp();
		if(data==null){
			dataObj=null;
			JPanel p = DataViewerController.getDefaultPanel();
			tabbedPane.removeAll();
			tabs.clear();
			tabbedPane.addTab("Get Started", p);
			return;
		}
		try{
			dataObj = data;
			String[] tabNames = DataViewerController.getTabNames(data.getType());
			tabbedPane.removeAll();
			tabs.clear();
			for(int i=0; i<tabNames.length; i++){
				DataViewerTab t = DataViewerController.generateTab(data.getType(),tabNames[i], data.getName());
				tabs.add(t);
				tabbedPane.addTab(tabNames[i], t);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void setData(DataObject data){
		if(tabs.size()==0){
			reloadTabs(data);
			return;
		}
		if(!data.getType().equals(dataObj.getType())){
			reloadTabs(data);
			return;
		}
		dataObj = data;
		//System.out.println("Setting data: " +data);
		for(int i=0; i < tabs.size(); i++){
			DataViewerTab t = (DataViewerTab) tabs.get(i);
			t.setData(data.getName());
		}
	}
	
	public DataObject getData(){return dataObj;}

	public void cleanUp(){
		for(int i=0; i < tabs.size(); i++){
			DataViewerTab t = (DataViewerTab) tabs.get(i);
			t.cleanUp();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		try{
			String cmd = e.getActionCommand();
			if(cmd.equals("comboBoxChanged") ){
				DataObject data = ((DataObject)dataSelector.getSelectedItem());
				if(data == null)
					return;
				if(!data.equals(dataObj)){
					setData(data);
					Deducer.setRecentData(data.getName());
				}
				
			}else if(cmd=="Open Data"){
				new LoadData();	
			}else if(cmd=="Save Data"){
				new SaveData(dataObj.getName());
			}else if(cmd=="Clear Data"){
				if(dataSelector.getSelectedItem()==null){
					JOptionPane.showMessageDialog(this, "Invalid selection: There is no data loaded.");
					return;
				}
				String data = ((DataObject)dataSelector.getSelectedItem()).getName();
				int confirm = JOptionPane.showConfirmDialog(null, "Remove Data Frame "+
						data+" from environment?\n" +
								"Unsaved changes will be lost.",
						"Clear Data Frame", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if(confirm == JOptionPane.NO_OPTION)
					return;
				Deducer.execute("rm("+data + ")");
				RController.refreshObjects();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	public void refresh() {
		((DataFrameComboBoxModel) dataSelector.getModel()).refresh(DataViewerController.getDataSets());
		if(((DataObject)dataSelector.getSelectedItem()) == null && dataObj!=null){
			reloadTabs(null);
			return;
		}
		Component t =  tabbedPane.getSelectedComponent();
		if(t instanceof DataViewerTab)
			((DataViewerTab)t).refresh();
//		for(int i=0; i < tabs.size(); i++){
//			DataViewerTab t = (DataViewerTab) tabs.get(i);
//			t.refresh();
//		}
	}
	
}

class DataFrameComboBoxModel extends DefaultComboBoxModel{

	private Vector items;
	private int selectedIndex=0;
	
	public Object getElementAt(int index){
		return items.elementAt(index);
	}
	public int getIndexOf(Object anObject){
		for(int i = 0;i<items.size();i++)
			if(items.elementAt(i)==anObject)
				return i;
		return -1;
	}
	public int 	getSize() {
		return items.size();
	}
	
	public Object getSelectedItem(){
		if(items.size()>0)
			return items.elementAt(selectedIndex);
		else
			return null;
	}
	
	public String getNameAt(int index){
		return ((DataObject)items.elementAt(index)).getName();
	}
	
	public void setSelectedItem(Object obj){
		selectedIndex = getIndexOf(obj);
	}
	
	
	public DataFrameComboBoxModel(Collection v){
		items = new Vector(v);
	}
	public void refresh(LinkedList v){
		DataObject data = null;
		int prevSize = items.size();
		if(getSelectedItem()!=null)
			data = ((DataObject)getSelectedItem());	
		this.removeAllElements();			
		items = new Vector(v);
		selectedIndex=0;			
		if(items.size()>0){
			for(int i = 0;i<items.size();i++){
				if(((DataObject)items.elementAt(i)).equals(data))
					selectedIndex =i;
				//System.out.println(items.elementAt(i).toString() + data + " " + i);
			}
			this.fireContentsChanged(this,0,prevSize);
		}

	}	
	
}

class Refresher implements Runnable {
	final DataViewer viewer;
	public Refresher(DataViewer v) {
		viewer=v;
	}

	public void run() {
		boolean cont = true;
		while(cont){
			try {
				Thread.sleep(5000);
				if(viewer==null || !viewer.isDisplayable())
					cont=false;
				else{
					viewer.refresh();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}