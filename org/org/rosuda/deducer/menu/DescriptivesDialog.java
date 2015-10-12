package org.rosuda.deducer.menu;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.*;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.JGR.JGR;
import org.rosuda.JGR.RController;
import org.rosuda.REngine.REXPLogical;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;



public class DescriptivesDialog extends javax.swing.JDialog implements ActionListener {
	private JPanel firstPanel;
	private DJList strataList;
	private JButton cont;
	private IconButton addStrata;
	private OkayCancelPanel okCancel;
	private IconButton removeStrata;
	private IconButton removeDesc;
	private IconButton addDesc;
	private JScrollPane strataScroller;
	private JPanel strataPanel;
	private JPanel descPanel;
	private DJList descrList;
	private JScrollPane descScroller;
	private VariableSelector variableSelector;
	
	private JPanel secondPanel;
	private JPanel functionPanel;
	private IconButton addFunc;
	private DJList runFuncList;
	private DJList functionList;
	private JScrollPane runFuncScroller;
	private JButton custom;
	private JPanel runFuncPanel;
	private IconButton removeFunc;
	private JScrollPane functionScroller;
	private HelpButton help;
	
	private static String[] functions = new String[] {"Mean","St. Deviation", "Valid N",
													"Median","25th Percentile",
													"75th Percentile","Minimum","Maximum","Skew",
													"Kurtosis"};
	private static DefaultListModel lastDescrModel;
	private static DefaultListModel lastStrataModel;
	private static DefaultListModel lastFuncModel;
	private static String lastDataName;
	
	
	public DescriptivesDialog(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			this.setTitle("Descriptives");
			{
				okCancel = new OkayCancelPanel(true, false, this);
				getContentPane().add(okCancel, new AnchorConstraint(869, 942, 947, 400, AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				cont=okCancel.getApproveButton();
				cont.setText("Continue");
				
			}
			{
				help = new HelpButton("pmwiki.php?n=Main.Descriptives");
				getContentPane().add(help, new AnchorConstraint(869, 942, 947, 25, AnchorConstraint.ANCHOR_NONE,
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				help.setPreferredSize(new java.awt.Dimension(32, 32));
				
			}
			initFirstPanel();
			initSecondPanel();
			boolean failed =false;
			if(lastDataName!=null)
				variableSelector.setSelectedData(lastDataName);
			if(lastDescrModel!=null&& lastDescrModel.getSize()!=0){
				descrList.setModel(lastDescrModel);
				failed = !variableSelector.removeAll(lastDescrModel);
			}
			if(lastStrataModel!=null && lastStrataModel.getSize()!=0){
				strataList.setModel(lastStrataModel);
				failed = failed || !variableSelector.removeAll(lastStrataModel);
			}
			if(lastFuncModel!=null&& lastFuncModel.getSize()!=0){
				runFuncList.setModel(lastFuncModel);
				DefaultListModel model = (DefaultListModel) functionList.getModel();
				for(int i=0;i<lastFuncModel.getSize();i++)
					model.removeElement(lastFuncModel.get(i));
			}
			if(failed){
				variableSelector.reset();
				strataList.setModel(new DefaultListModel());
				descrList.setModel(new DefaultListModel());
			}
			secondPanel.setVisible(false);
			this.setMinimumSize(new Dimension(400,400));
			this.setSize(524, 443);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	private void initFirstPanel(){
		{
			firstPanel = new JPanel();
			AnchorLayout firstPanelLayout = new AnchorLayout();
			getContentPane().add(firstPanel, new AnchorConstraint(-1, 1000, 812, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			firstPanel.setLayout(firstPanelLayout);
			firstPanel.setPreferredSize(new java.awt.Dimension(516, 333));
			{
				addDesc = new IconButton("/icons/1rightarrow_32.png","Add",this,"Add");
				firstPanel.add(addDesc, new AnchorConstraint(176, 543, 286, 462, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				addDesc.setPreferredSize(new java.awt.Dimension(42, 33));
				addDesc.setContentAreaFilled(false);
			}
			{
				strataPanel = new JPanel();
				BorderLayout strataPanelLayout = new BorderLayout();
				strataPanel.setLayout(strataPanelLayout);
				firstPanel.add(strataPanel, new AnchorConstraint(623, 950, 920, 564, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				strataPanel.setPreferredSize(new java.awt.Dimension(199, 99));
				strataPanel.setBorder(BorderFactory.createTitledBorder("Stratify By:"));
				{
					strataScroller = new JScrollPane();
					strataPanel.add(strataScroller, BorderLayout.CENTER);
					{
						ListModel strataListModel = 
							new DefaultListModel();
						strataList = new DJList();
						strataScroller.setViewportView(strataList);
						strataList.setModel(strataListModel);
					}
				}
			}
			{
				descPanel = new JPanel();
				BorderLayout descLayout = new BorderLayout();
				descPanel.setLayout(descLayout);
				firstPanel.add(descPanel, new AnchorConstraint(37, 956, 605, 564, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				descPanel.setPreferredSize(new java.awt.Dimension(202, 189));
				descPanel.setBorder(BorderFactory.createTitledBorder("Descriptives of:"));
				{
					descScroller = new JScrollPane();
					descPanel.add(descScroller);
					ListModel descrListModel = 
						new DefaultListModel();
					descrList = new DJList();
					descScroller.setViewportView(descrList);
					descrList.setModel(descrListModel);
				}
			}
			{
				variableSelector = new VariableSelector();
				firstPanel.add(variableSelector, new AnchorConstraint(37, 429, 920, 24, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				variableSelector.setPreferredSize(new java.awt.Dimension(209, 294));
				variableSelector.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				variableSelector.getJComboBox().addActionListener(this);
			}
			{
				removeDesc = new IconButton("/icons/1leftarrow_32.png","Remove",this,"Remove");
				firstPanel.add(removeDesc, new AnchorConstraint(301, 543, 411, 462, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				removeDesc.setPreferredSize(new java.awt.Dimension(42, 42));
				removeDesc.setContentAreaFilled(false);
			}
			{
				addStrata = new IconButton("/icons/1rightarrow_32.png","Add Strata",this,"Add Strata");;
				firstPanel.add(addStrata, new AnchorConstraint(681, 543, 791, 462, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				addStrata.setPreferredSize(new java.awt.Dimension(42, 42));
				addStrata.setContentAreaFilled(false);
			}
			{
				removeStrata = new IconButton("/icons/1leftarrow_32.png","Remove Strata",this,"Remove Strata");;
				firstPanel.add(removeStrata, new AnchorConstraint(806, 543, 916, 462, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				removeStrata.setPreferredSize(new java.awt.Dimension(42,33));
				removeStrata.setContentAreaFilled(false);
			}
		}
	}
	
	private void initSecondPanel(){
		secondPanel = new JPanel();
		AnchorLayout firstPanelLayout = new AnchorLayout();
		getContentPane().add(secondPanel, new AnchorConstraint(-1, 1000, 812, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
		secondPanel.setLayout(firstPanelLayout);
		secondPanel.setPreferredSize(new java.awt.Dimension(516, 333));
		{
			custom = new JButton();
			secondPanel.add(custom, new AnchorConstraint(811, 901, 886, 695, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			custom.setText("Custom");
			custom.setPreferredSize(new java.awt.Dimension(93, 21));
			custom.addActionListener(this);
		}
		{
			runFuncPanel = new JPanel();
			BorderLayout runFuncPanelLayout = new BorderLayout();
			runFuncPanel.setLayout(runFuncPanelLayout);
			secondPanel.add(runFuncPanel, new AnchorConstraint(44, 974, 767, 629, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			runFuncPanel.setPreferredSize(new java.awt.Dimension(156, 201));
			runFuncPanel.setBorder(BorderFactory.createTitledBorder("Run Descriptives"));
			{
				runFuncScroller = new JScrollPane();
				runFuncPanel.add(runFuncScroller, BorderLayout.CENTER);
				{
					DefaultListModel runFuncListModel = new DefaultListModel();
					runFuncListModel.addElement("Mean");
					runFuncListModel.addElement("St. Deviation");
					runFuncListModel.addElement("Valid N");
					runFuncList = new DJList();
					runFuncScroller.setViewportView(runFuncList);
					runFuncList.setModel(runFuncListModel);
				}
			}
		}
		{
			removeFunc = new IconButton("/icons/1leftarrow_32.png","Remove Function",this,"Remove Function");;
			secondPanel.add(removeFunc, new AnchorConstraint(321, 574, 437, 478, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			removeFunc.setPreferredSize(new java.awt.Dimension(43, 32));
			removeFunc.setContentAreaFilled(false);
		}
		{
			addFunc = new IconButton("/icons/1rightarrow_32.png","Add Function",this,"Add Function");;
			secondPanel.add(addFunc, new AnchorConstraint(188, 574, 303, 478, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			addFunc.setPreferredSize(new java.awt.Dimension(43, 32));
			addFunc.setContentAreaFilled(false);
		}
		{
			functionPanel = new JPanel();
			BorderLayout functionPanelLayout = new BorderLayout();
			secondPanel.add(functionPanel, new AnchorConstraint(44, 421, 886, 27, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			functionPanel.setLayout(functionPanelLayout);
			functionPanel.setPreferredSize(new java.awt.Dimension(178, 234));
			functionPanel.setBorder(BorderFactory.createTitledBorder("Functions"));
			{
				functionScroller = new JScrollPane();
				functionPanel.add(functionScroller, BorderLayout.CENTER);
				{
					DefaultListModel functionListModel = 
						new DefaultListModel();
					for(int i=3;i<functions.length;i++)
						functionListModel.addElement(functions[i]);
					functionList = new DJList();
					functionScroller.setViewportView(functionList);
					functionList.setModel(functionListModel);
				}
			}
		}
	}
	
	public void setDataName(String dataName,boolean resetIfNotSame){
		if(!dataName.equals(variableSelector.getSelectedData())){
			variableSelector.setSelectedData(dataName);
			if(resetIfNotSame)
				reset();
		}
	}
	
	public void reset(){
		DefaultListModel runFuncListModel = new DefaultListModel();
		runFuncList.setModel(runFuncListModel);
		runFuncListModel.addElement("Mean");
		runFuncListModel.addElement("St. Deviation");
		runFuncListModel.addElement("Valid N");
		DefaultListModel functionListModel = 
			new DefaultListModel();
		functionList.setModel(functionListModel);
		for(int i=3;i<functions.length;i++)
			functionListModel.addElement(functions[i]);
		((DefaultListModel)strataList.getModel()).removeAllElements();
		((DefaultListModel)descrList.getModel()).removeAllElements();
		variableSelector.reset();
		secondPanel.setVisible(false);
		cont.setText("Continue");
		firstPanel.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if(cmd == "comboBoxChanged"){
			descrList.setModel(new DefaultListModel());
			strataList.setModel(new DefaultListModel());
		}else if(cmd == "Continue"){
			if(descrList.getModel().getSize()==0){
				JOptionPane.showMessageDialog(this, "Please choose a variable");
				return;
			}
			firstPanel.setVisible(false);
			secondPanel.setVisible(true);
			cont.setText("Run");
		}else if(cmd == "Cancel"){
			this.dispose();
		}else if(cmd == "Add"){
			Object[] objs=variableSelector.getJList().getSelectedValues();
			for(int i=0;i<objs.length;i++){
				variableSelector.remove(objs[i]);
				((DefaultListModel)descrList.getModel()).addElement(objs[i]);
			}
		}else if(cmd == "Remove"){
			Object[] objs=descrList.getSelectedValues();
			for(int i=0;i<objs.length;i++){
				variableSelector.add(objs[i]);
				((DefaultListModel)descrList.getModel()).removeElement(objs[i]);
			}		
		}else if(cmd == "Add Strata"){
			Object[] objs=variableSelector.getJList().getSelectedValues();
			for(int i=0;i<objs.length;i++){
				variableSelector.remove(objs[i]);
				((DefaultListModel)strataList.getModel()).addElement(objs[i]);
			}			
		}else if(cmd == "Remove Strata"){
			Object[] objs=strataList.getSelectedValues();
			for(int i=0;i<objs.length;i++){
				variableSelector.add(objs[i]);
				((DefaultListModel)strataList.getModel()).removeElement(objs[i]);
			}
		}else if(cmd == "Add Function"){
			Object[] objs=functionList.getSelectedValues();
			for(int i=0;i<objs.length;i++){
				((DefaultListModel)functionList.getModel()).removeElement(objs[i]);
				((DefaultListModel)runFuncList.getModel()).addElement(objs[i]);
			}
		}else if(cmd == "Remove Function"){
			Object[] objs=runFuncList.getSelectedValues();
			for(int i=0;i<objs.length;i++){
				((DefaultListModel)runFuncList.getModel()).removeElement(objs[i]);
				((DefaultListModel)functionList.getModel()).addElement(objs[i]);
			}			
		}else if(cmd == "Reset"){
			reset();
		}else if(cmd == "Run"){
			if(runFuncList.getModel().getSize()==0){
				JOptionPane.showMessageDialog(this, "Please choose at least one statistic");
				return;
			}
			String dataName = variableSelector.getSelectedData();
			ArrayList vars = new ArrayList();
			for(int i=0;i<descrList.getModel().getSize();i++)
				vars.add(descrList.getModel().getElementAt(i));
			ArrayList strata = new ArrayList();
			for(int i=0;i<strataList.getModel().getSize();i++)
				strata.add(strataList.getModel().getElementAt(i));	
			ArrayList functions = new ArrayList();
			String addFuncs = "list(";
			for(int i=0;i<runFuncList.getModel().getSize();i++){
				String element = (String)runFuncList.getModel().getElementAt(i);
				if(element.indexOf("=")<0)
					functions.add(element);
				else
					addFuncs+=element+",";
			}
			if(addFuncs=="list(")
				addFuncs=null;
			else{
				addFuncs = addFuncs.substring(0, addFuncs.length()-1)+")";
			}
			this.dispose();
			
			lastDataName=dataName;
			lastDescrModel = (DefaultListModel) descrList.getModel();
			lastStrataModel = (DefaultListModel) strataList.getModel();
			lastFuncModel = (DefaultListModel) runFuncList.getModel();
	
			Deducer.execute("descriptive.table(vars = " +
										Deducer.makeRCollection(vars, "d", false)+
										(strata.size()<1 ? "" : (" ,\n\tstrata = " + Deducer.makeRCollection(strata,"d",false))) +
										",data= "+dataName +
										",\n\tfunc.names ="+Deducer.makeRCollection(functions,"c",true)+
										(addFuncs!=null ? (",\n\tfunc.additional= "+addFuncs+")") : ")")
							);
					
			Deducer.setRecentData(dataName);
		}else if(cmd == "Custom"){
			CustomPopUp pop = new CustomPopUp(this);
			pop.setLocationRelativeTo(null);
			pop.setVisible(true);
		}
	}
	
	
	class CustomPopUp extends JDialog implements ActionListener{
		private JLabel nameLabel;
		private JLabel functionLabel;
		private JScrollPane scroller;
		private JTextPane functionPane;
		private JButton cancel;
		private JButton okay;
		private JTextArea functionText;
		private JTextField name;

		
		public CustomPopUp(JDialog frame) {
			super(frame);
			initGUI();
		}
		
		private void initGUI() {
			try {
				AnchorLayout thisLayout = new AnchorLayout();
				getContentPane().setLayout(thisLayout);
				{
					cancel = new JButton();
					getContentPane().add(cancel, new AnchorConstraint(883, 726, 959, 509, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					cancel.setText("Cancel");
					cancel.setPreferredSize(new java.awt.Dimension(71, 22));
					cancel.addActionListener(this);
				}
				{
					okay = new JButton();
					getContentPane().add(okay, new AnchorConstraint(883, 964, 959, 744, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					okay.setText("OK");
					okay.setPreferredSize(new java.awt.Dimension(72, 22));
					okay.addActionListener(this);
				}
				{
					scroller = new JScrollPane();
					getContentPane().add(scroller, new AnchorConstraint(318, 964, 817, 29, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					scroller.setPreferredSize(new java.awt.Dimension(306, 143));
					{
						functionText = new JTextArea();
						scroller.setViewportView(functionText);
					}
				}
				{
					functionLabel = new JLabel();
					getContentPane().add(functionLabel, new AnchorConstraint(207, 964, 259, 38, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					functionLabel.setText("Enter Function - e.g. function(x) sum(x)");
					functionLabel.setPreferredSize(new java.awt.Dimension(303, 15));
					functionLabel.setHorizontalAlignment(SwingConstants.CENTER);
				}
				{
					name = new JTextField();
					getContentPane().add(name, new AnchorConstraint(54, 964, 130, 368, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					name.setPreferredSize(new java.awt.Dimension(195, 22));
				}
				{
					nameLabel = new JLabel();
					getContentPane().add(nameLabel, new AnchorConstraint(67, 368, 120, 29, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					nameLabel.setText("Function Name:");
					nameLabel.setPreferredSize(new java.awt.Dimension(111, 15));
				}
				this.setSize(327, 309);
			} catch (Exception e) {
				new ErrorMsg(e);
			}
		}

		public void actionPerformed(ActionEvent arg0) {
			String cmd = arg0.getActionCommand();
			
			if(cmd == "Cancel"){
				this.dispose();
			}else if(cmd == "OK"){
				if(name.getText().length()<1){
					JOptionPane.showMessageDialog(this, "Please Enter a name for the function");
					return;
				}
				org.rosuda.REngine.REXP isFunc = new org.rosuda.REngine.REXP();
				isFunc = Deducer.timedEval("try(is.function("+functionText.getText()+"),silent=T)");
				if(isFunc==null || !(functionText.getText().length()<1 || !isFunc.isLogical() || ((REXPLogical)isFunc).isTRUE()[0])){
					JOptionPane.showMessageDialog(this, "Entered function not valid. " +
								"Please try again.\n\nHere is an example that " +
								"calculates the sum\nof a variable:\n                   " +
								"function(x) sum(x)");
					return;
				}
				((DefaultListModel)runFuncList.getModel()).addElement(name.getText()+"="+
						functionText.getText());
				this.dispose();
			}
		}

	}
	
	
	
}

