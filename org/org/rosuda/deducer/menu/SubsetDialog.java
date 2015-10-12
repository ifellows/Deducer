package org.rosuda.deducer.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import org.rosuda.deducer.Deducer;
import org.rosuda.JGR.toolkit.SyntaxArea;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;
import org.rosuda.deducer.toolkit.VariableSelector;
import org.rosuda.JGR.JGR;
import org.rosuda.JGR.RController;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPLogical;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;


public class SubsetDialog extends JDialog implements ActionListener, MouseListener{
	private VariableSelector variableSelector;
	private JPanel subsetPanel;
	private JScrollPane subsetScroller;
	private JTextField subName;
	private JButton logicLT;
	private JLabel recentLabel;
	private JComboBox recent;
	private JButton help;
	private JButton funcHelp;
	private JButton logicEQ;
	private JButton logicNE;
	private JButton logicNot;
	private JButton logicOr;
	private JButton logicAnd;
	private JButton logicGT;
	private JButton logicLTE;
	private JButton logicGTE;
	private JList funcList;
	private JScrollPane funcScroller;
	private JPanel funcPanel;
	private JPanel logPanel;
	private JLabel jLabel1;
	private SyntaxArea subsetEditor;
	private OkayCancelPanel okcan;
	public static HashMap historyMap;
	private static String lastDataName;
	
	public SubsetDialog(JFrame frame) {
		super(frame);
		if(historyMap==null)
			historyMap=new HashMap();
		initGUI();
		if(lastDataName!=null)
			variableSelector.setSelectedData(lastDataName);
		refreshRecent();
	}
	
	public void refreshRecent(){
		ArrayList lis = (ArrayList) historyMap.get(variableSelector.getSelectedData());
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		recent.setModel(model);
		model.addElement("");
		if(lis!=null){
			for(int i=0;i<lis.size();i++)
				model.addElement(lis.get(i));
		}
	}
	
	public static DefaultComboBoxModel getRecent(String data){
		if(historyMap==null)
			historyMap=new HashMap();
		ArrayList lis = (ArrayList) historyMap.get(data);
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		model.addElement("");
		if(lis!=null){
			for(int i=0;i<lis.size();i++)
				model.addElement(lis.get(i));
		}
		return model;
	}
	
	public void setDataName(String dataName,boolean resetIfNotSame){
		if(!dataName.equals(variableSelector.getSelectedData())){
			variableSelector.setSelectedData(dataName);;
		}
	}
	
	public static void addToHistory(String dataName,String sub){
		if(sub.trim().length()==0)
			return;
		if(historyMap.containsKey(dataName))
			((ArrayList)historyMap.get(dataName)).add(0, sub);
		else{
			ArrayList nl= new ArrayList();
			nl.add(sub);
			historyMap.put(dataName, nl);
		}
	}
	
	private void initGUI() {
		try {
			getContentPane().setLayout(null);
			{
				funcPanel = new JPanel();
				BorderLayout funcPanelLayout = new BorderLayout();
				funcPanel.setLayout(funcPanelLayout);
				getContentPane().add(funcPanel);
				funcPanel.setPreferredSize(new java.awt.Dimension(140, 173));
				funcPanel.setBorder(BorderFactory.createTitledBorder("Logical Functions"));
				funcPanel.setBounds(233, 112, 140, 173);
				{
					funcScroller = new JScrollPane();
					funcPanel.add(funcScroller, BorderLayout.CENTER);
					{
						ListModel funcListModel = 
							new DefaultComboBoxModel(
									new String[] {"is.character",	"is.integer",	"is.logical",
											"is.complex",	"is.double",	"is.numeric",
											"is.vector",	"is.factor",	"is.finite",
											"is.infinite",	"is.na",	"is.nan",
											"is.null",	"is.element",	"is.raw"});
						funcList = new JList();
						funcScroller.setViewportView(funcList);
						funcList.setModel(funcListModel);
						funcList.addMouseListener(this);
						funcList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					}
				}
			}
			{
				logPanel = new JPanel();
				getContentPane().add(logPanel);
				logPanel.setPreferredSize(new java.awt.Dimension(126, 173));
				logPanel.setBorder(BorderFactory.createTitledBorder("Logical Operators"));
				logPanel.setLayout(null);
				logPanel.setBounds(379, 112, 126, 173);
				{
					logicGTE = new JButton();
					logPanel.add(logicGTE);
					logicGTE.setText("\u2265");
					logicGTE.setBounds(12, 19, 46, 21);
					logicGTE.addActionListener(this);
				}
				{
					logicLTE = new JButton();
					logPanel.add(logicLTE);
					logicLTE.setText("\u2264");
					logicLTE.setBounds(69, 19, 46, 21);
					logicLTE.addActionListener(this);
				}
				{
					logicGT = new JButton();
					logPanel.add(logicGT);
					logicGT.setText(">");
					logicGT.setBounds(12, 45, 46, 21);
					logicGT.addActionListener(this);
				}
				{
					logicLT = new JButton();
					logPanel.add(logicLT);
					logicLT.setText("<");
					logicLT.setBounds(69, 45, 46, 21);
					logicLT.addActionListener(this);
				}
				{
					logicAnd = new JButton();
					logPanel.add(logicAnd);
					logicAnd.setText("And");
					logicAnd.setBounds(32, 96, 62, 20);
					logicAnd.addActionListener(this);
				}
				{
					logicOr = new JButton();
					logPanel.add(logicOr);
					logicOr.setText("Or");
					logicOr.setBounds(32, 121, 62, 21);
					logicOr.addActionListener(this);
				}
				{
					logicNot = new JButton();
					logPanel.add(logicNot);
					logicNot.setText("Not");
					logicNot.setBounds(32, 147, 62, 21);
					logicNot.addActionListener(this);
				}
				{
					logicNE = new JButton();
					logPanel.add(logicNE);
					logicNE.setText("!=");
					logicNE.setBounds(69, 70, 46, 21);
					logicNE.addActionListener(this);
				}
				{
					logicEQ = new JButton();
					logPanel.add(logicEQ);
					logicEQ.setText("=");
					logicEQ.setBounds(12, 70, 46, 21);
					logicEQ.addActionListener(this);
				}
			}
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1);
				jLabel1.setText("Subset Name:");
				jLabel1.setPreferredSize(new java.awt.Dimension(88, 14));
				jLabel1.setBounds(12, 218, 88, 14);
			}
			{
				subName = new JTextField();
				getContentPane().add(subName);
				subName.setText("<auto>");
				subName.setBounds(100, 215, 102, 21);
			}
			{
				subsetPanel = new JPanel();
				BorderLayout subsetPanelLayout = new BorderLayout();
				subsetPanel.setLayout(subsetPanelLayout);
				getContentPane().add(subsetPanel);
				subsetPanel.setBorder(BorderFactory.createTitledBorder("Subset Expression"));
				subsetPanel.setBounds(233, 12, 272, 74);
				{
					subsetScroller = new JScrollPane();
					subsetPanel.add(subsetScroller, BorderLayout.CENTER);
					subsetScroller.setPreferredSize(new java.awt.Dimension(262, 52));
					{
						subsetEditor = new SyntaxArea();
						subsetScroller.setViewportView(subsetEditor);
					}
				}
			}
			{
				variableSelector = new VariableSelector();
				getContentPane().add(variableSelector);
				variableSelector.setPreferredSize(new java.awt.Dimension(215, 194));
				variableSelector.setBounds(12, 12, 215, 194);
				variableSelector.getJComboBox().addActionListener(this);
				variableSelector.getJList().addMouseListener(this);
			}
			{
				okcan = new OkayCancelPanel(true,false,this);
				getContentPane().add(okcan);
				okcan.setBounds(230, 297, 270, 40);
			}
			{
				funcHelp = new IconButton("/icons/help.png","Function Help",this,"Function Help");
				getContentPane().add(funcHelp);
				funcHelp.setBounds(202, 254, 31, 31);
			}
			{
				help = new HelpButton("pmwiki.php?n=Main.Subset");
				getContentPane().add(help);
				help.setBounds(12, 297, 35, 35);
			}
			{
				ComboBoxModel recentModel = 
					new DefaultComboBoxModel();
				recent = new JComboBox();
				getContentPane().add(recent);
				recent.setModel(recentModel);
				recent.setBounds(287, 87, 218, 21);
				recent.addActionListener(this);
			}
			{
				recentLabel = new JLabel();
				getContentPane().add(recentLabel);
				recentLabel.setText("Recent:");
				recentLabel.setBounds(237, 90, 54, 14);
			}
			this.setTitle("Data Subset");
			this.setResizable(false);
			this.setSize(525, 375);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}


	public void actionPerformed(ActionEvent act) {
		String cmd = act.getActionCommand();
		if(cmd=="And"){
			try{
				subsetEditor.getDocument().insertString(subsetEditor.getCaretPosition(), " & ", null);
			}catch(Exception e){}
				subsetEditor.requestFocus();
		}else if(cmd=="Or"){
			try{
				subsetEditor.getDocument().insertString(subsetEditor.getCaretPosition(), " | ", null);
			}catch(Exception e){}
			subsetEditor.requestFocus();
		}else if(cmd==">"){
			try{
				subsetEditor.getDocument().insertString(subsetEditor.getCaretPosition(), " > ", null);
			}catch(Exception e){}
			subsetEditor.requestFocus();
		}else if(cmd=="<"){
			try{
				subsetEditor.getDocument().insertString(subsetEditor.getCaretPosition(), " < ", null);
			}catch(Exception e){}
			subsetEditor.requestFocus();
		}else if(cmd=="\u2265"){
			try{
				subsetEditor.getDocument().insertString(subsetEditor.getCaretPosition(), " >= ", null);
			}catch(Exception e){}
			subsetEditor.requestFocus();
		}else if(cmd=="\u2264"){
			try{
				subsetEditor.getDocument().insertString(subsetEditor.getCaretPosition(), " <= ", null);
			}catch(Exception e){}
			subsetEditor.requestFocus();
		}else if(cmd=="Not"){
			try{
				subsetEditor.getDocument().insertString(subsetEditor.getCaretPosition(), " !", null);
			}catch(Exception e){}
			subsetEditor.requestFocus();
		}else if(cmd=="!="){
			try{
				subsetEditor.getDocument().insertString(subsetEditor.getCaretPosition(), " != ", null);
			}catch(Exception e){}
			subsetEditor.requestFocus();
		}else if(cmd=="="){
			try{
				subsetEditor.getDocument().insertString(subsetEditor.getCaretPosition(), " == ", null);
			}catch(Exception e){}
			subsetEditor.requestFocus();
		}else if(cmd=="Cancel"){
			this.dispose();
		}else if(cmd == "OK"){
			String subn;
			String txt = subsetEditor.getText();
			String data = variableSelector.getSelectedData();
			String sub = subsetEditor.getText();
			if(!isValidSubsetExp(sub,data)){
				JOptionPane.showMessageDialog(this, "Invalid Subset. Please enter a logical expression");
				return;
			}
			if(subName.getText().startsWith("<auto>"))
				subn = Deducer.getUniqueName(data+".sub");
			else
				subn = RController.makeValidVariableName(subName.getText());
			Deducer.execute(subn+"<-subset("+data+","+sub+")");
			
			addToHistory(variableSelector.getSelectedData(),sub);
			lastDataName = variableSelector.getSelectedData();
			Deducer.setRecentData(subn);
			this.dispose();
		}else if(cmd == "Reset"){
			subsetEditor.setText("");
			subName.setText("<auto>");
			subsetEditor.requestFocus();
		}else if(cmd == "comboBoxChanged"){
			if(act.getSource()==recent){
				subsetEditor.setText((String)recent.getSelectedItem());
				subsetEditor.requestFocus();
			}else{
				subsetEditor.setText("");
				refreshRecent();
			}
		}else if(cmd=="Function Help"){
			Object func = funcList.getSelectedValue();
			if(func!=null)
				Deducer.execute("help(\""+((String)func)+"\")",false);
		}
	}

	public void mouseClicked(MouseEvent ms) {
		if(ms.getClickCount()==2){
			String item = "";
			if(ms.getSource()==funcList){
				item =(String)funcList.getSelectedValue();
				try{
					subsetEditor.getDocument().insertString(subsetEditor.getCaretPosition(), item+"() ", null);
					subsetEditor.requestFocus();
					subsetEditor.setCaretPosition(subsetEditor.getCaretPosition()-2);
				}catch(Exception e){}	
			}else{
				item =(String)variableSelector.getJList().getSelectedValue();
				try{
					subsetEditor.getDocument().insertString(subsetEditor.getCaretPosition(),item, null);
					subsetEditor.requestFocus();
				}catch(Exception e){}					
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}

	public void mousePressed(MouseEvent arg0) {}

	public void mouseReleased(MouseEvent arg0) {}

	
	public static boolean isValidSubsetExp(String subset,String dataName){
		if(subset==null || subset.length()<1)
			return false;
		
		REXP valid=null;
		valid = Deducer.timedEval("(function(x,subset){"+
										"result<-try(e <- substitute(subset),silent=TRUE)\n"+
										"if(class(result)==\"try-error\")\n"+
										"	return(FALSE)\n"+
										"result<-try(r <- eval(e, x, parent.frame()),silent=TRUE)\n"+
										"if(class(result)==\"try-error\")\n"+
										"	return(FALSE)\n"+
										"is.logical(r)\n"+
										"})("+dataName+","+subset+")");
		if(valid==null){
			return false;
		}
		if(!valid.isLogical()){
			return false;
		}
		return ((REXPLogical)valid).isTRUE()[0];
	}
}