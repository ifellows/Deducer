package org.rosuda.deducer.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.BevelBorder;

import org.rosuda.JGR.JGR;
import org.rosuda.JGR.RController;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.DJList;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;
import org.rosuda.deducer.toolkit.VariableSelector;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.JGR.util.ErrorMsg;


public class FrequencyDialog extends javax.swing.JDialog implements ActionListener{
	private VariableSelector variableSelector;
	private IconButton options;
	private OkayCancelPanel okCancel;
	private IconButton remove;
	private IconButton Add;
	private JList freqList;
	private JScrollPane freqScroller;
	private JPanel frequencyPanel;
	private HelpButton help;
	private int digits=1;
	
	private static Integer lastDigits;
	private static String lastDataName;
	private static DefaultListModel lastListModel;

	
	public FrequencyDialog(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			{
				options = new IconButton("/icons/advanced_32.png","Options",this,"Options");
				getContentPane().add(options, new AnchorConstraint(829, 550, 966, 470, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				options.setPreferredSize(new java.awt.Dimension(40, 41));
				options.setContentAreaFilled(false);
			}
			{
				okCancel=new OkayCancelPanel(false,false,this);
				getContentPane().add(okCancel, new AnchorConstraint(867, 978, 963, 651, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				
			}
			{
				help = new HelpButton("pmwiki.php?n=Main.Frequencies");
				getContentPane().add(help, new AnchorConstraint(867, 978, 975, 23, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				help.setPreferredSize(new java.awt.Dimension(32, 32));
			}
			{
				remove = new IconButton("/icons/1leftarrow_32.png","Remove",this,"Remove");
				getContentPane().add(remove, new AnchorConstraint(397, 550, 535, 470, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				remove.setPreferredSize(new java.awt.Dimension(40, 41));
				remove.setContentAreaFilled(false);
			}
			{
				Add = new IconButton("/icons/1rightarrow_32.png","Add",this,"Add");
				getContentPane().add(Add, new AnchorConstraint(250, 550, 390, 470, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				Add.setPreferredSize(new java.awt.Dimension(40, 41));
				Add.setContentAreaFilled(false);
			}
			{
				frequencyPanel = new JPanel();
				BorderLayout frequencyPanelLayout = new BorderLayout();
				getContentPane().add(frequencyPanel, new AnchorConstraint(39, 978, 774, 579, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				frequencyPanel.setPreferredSize(new java.awt.Dimension(209, 230));
				frequencyPanel.setLayout(frequencyPanelLayout);
				frequencyPanel.setBorder(BorderFactory.createTitledBorder("Run Frequencies On:"));
				{
					freqScroller = new JScrollPane();
					frequencyPanel.add(freqScroller, BorderLayout.CENTER);
					{
						ListModel freqListModel= new DefaultListModel();
						freqList = new DJList();
						freqScroller.setViewportView(freqList);
						freqList.setModel(freqListModel);
					}
				}
			}
			{
				variableSelector = new VariableSelector();
				getContentPane().add(variableSelector, new AnchorConstraint(39, 434, 867, 23, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				variableSelector.setPreferredSize(new java.awt.Dimension(215, 289));
				variableSelector.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				variableSelector.getJComboBox().addActionListener(this);
			}
			if(lastDataName!=null)
				variableSelector.setSelectedData(lastDataName);
			if(lastListModel!=null && lastDataName!=null){
				freqList.setModel(lastListModel);
			}
			boolean allExist=variableSelector.removeAll((DefaultListModel) freqList.getModel());
			if(!allExist)
				freqList.setModel(new DefaultListModel());
			if(lastDigits!=null && allExist)
				digits=lastDigits.intValue();
			this.setTitle("Run Frequencies");
			this.setMinimumSize(new Dimension(500,300));
			this.setSize(524, 335);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	
	public void setDataName(String dataName){
		if(!dataName.equals(variableSelector.getSelectedData()))
			variableSelector.setSelectedData(dataName);
	}
	
	public void actionPerformed(ActionEvent event) {
		
		String cmd = event.getActionCommand();
		if(cmd == "comboBoxChanged"){
			freqList.setModel(new DefaultListModel());
		}else if(cmd=="Cancel")
			this.dispose();
		else if(cmd == "Add"){
			Object[] objs=variableSelector.getJList().getSelectedValues();
			for(int i=0;i<objs.length;i++){
				variableSelector.remove(objs[i]);
				((DefaultListModel)freqList.getModel()).addElement(objs[i]);
			}
		}else if(cmd == "Remove"){
			Object[] objs=freqList.getSelectedValues();
			for(int i=0;i<objs.length;i++){
				variableSelector.add(objs[i]);
				((DefaultListModel)freqList.getModel()).removeElement(objs[i]);
			}			
		}else if(cmd == "Options"){
			boolean valid = false;
			while(!valid){
				String result =JOptionPane.showInputDialog(this, "How many digits should the \npercentages be rounded to?", digits+"");
				if(result.length()==0){
					digits=1;
					valid=true;
				}
				try{
				digits =Integer.parseInt(result);
				valid = true;
				}catch(Exception e){}
			}
		}else if(cmd == "OK"){
			if(freqList.getModel().getSize()==0){
				JOptionPane.showMessageDialog(this, "Please select some variables to\nrun frequencies on.");
				return;
			}
			String dataName = variableSelector.getSelectedData();
			ArrayList varList = new ArrayList();
			for(int i=0;i<freqList.getModel().getSize();i++)
				varList.add(freqList.getModel().getElementAt(i));
			this.dispose();	
			Deducer.execute("frequencies("+dataName+
					"["+RController.makeRStringVector(varList)+"] , r.digits = "+digits+")");
			lastDataName=dataName;
			lastListModel = (DefaultListModel) freqList.getModel();
			lastDigits = new Integer(digits);
			Deducer.setRecentData(dataName);

		}
		
	}
}
