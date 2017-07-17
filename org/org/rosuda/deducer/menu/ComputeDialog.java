package org.rosuda.deducer.menu;

import java.awt.BorderLayout;
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

import org.rosuda.deducer.Deducer;
import org.rosuda.JGR.toolkit.SyntaxArea;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;
import org.rosuda.deducer.toolkit.VariableSelector;
import org.rosuda.JGR.RController;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPLogical;


public class ComputeDialog extends JDialog implements ActionListener, MouseListener{
	private VariableSelector variableSelector;
	private JPanel computePanel;
	private JScrollPane computeScroller;
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
	private SyntaxArea computeEditor;
	private OkayCancelPanel okcan;
	public static HashMap historyMap;
	private static String lastDataName;
	
	public ComputeDialog(JFrame frame) {
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
									new String[] {"log",	"sqrt",	"abs",
											"mean",	"median",	"sd",
											"pmax",	"pmin", "round"});
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
				logPanel.setBorder(BorderFactory.createTitledBorder("Operators"));
				logPanel.setLayout(null);
				logPanel.setBounds(379, 112, 126, 173);
				{
					logicGTE = new JButton();
					logPanel.add(logicGTE);
					logicGTE.setText("*");
					logicGTE.setBounds(12, 19, 46, 21);
					logicGTE.addActionListener(this);
				}
				{
					logicLTE = new JButton();
					logPanel.add(logicLTE);
					logicLTE.setText("/");
					logicLTE.setBounds(69, 19, 46, 21);
					logicLTE.addActionListener(this);
				}
				{
					logicGT = new JButton();
					logPanel.add(logicGT);
					logicGT.setText("+");
					logicGT.setBounds(12, 45, 46, 21);
					logicGT.addActionListener(this);
				}
				{
					logicLT = new JButton();
					logPanel.add(logicLT);
					logicLT.setText("-");
					logicLT.setBounds(69, 45, 46, 21);
					logicLT.addActionListener(this);
				}
				{
					logicAnd = new JButton();
					logPanel.add(logicAnd);
					logicAnd.setText("( )");
					logicAnd.setBounds(32, 96, 62, 20);
					logicAnd.addActionListener(this);
				}
				{
					logicOr = new JButton();
					logPanel.add(logicOr);
					logicOr.setText("^4");
					logicOr.setBounds(32, 121, 62, 21);
					logicOr.addActionListener(this);
				}
				{
					logicNot = new JButton();
					logPanel.add(logicNot);
					logicNot.setText("^5");
					logicNot.setBounds(32, 147, 62, 21);
					logicNot.addActionListener(this);
				}
				{
					logicNE = new JButton();
					logPanel.add(logicNE);
					logicNE.setText("^3");
					logicNE.setBounds(69, 70, 46, 21);
					logicNE.addActionListener(this);
				}
				{
					logicEQ = new JButton();
					logPanel.add(logicEQ);
					logicEQ.setText("^2");
					logicEQ.setBounds(12, 70, 46, 21);
					logicEQ.addActionListener(this);
				}
			}
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1);
				jLabel1.setText("New Variable:");
				jLabel1.setPreferredSize(new java.awt.Dimension(88, 14));
				jLabel1.setBounds(12, 218, 88, 14);
			}
			{
				subName = new JTextField();
				getContentPane().add(subName);
				subName.setText("");
				subName.setBounds(100, 215, 102, 21);
			}
			{
				computePanel = new JPanel();
				BorderLayout computePanelLayout = new BorderLayout();
				computePanel.setLayout(computePanelLayout);
				getContentPane().add(computePanel);
				computePanel.setBorder(BorderFactory.createTitledBorder("Compute Expression"));
				computePanel.setBounds(233, 12, 272, 74);
				{
					computeScroller = new JScrollPane();
					computePanel.add(computeScroller, BorderLayout.CENTER);
					computeScroller.setPreferredSize(new java.awt.Dimension(262, 52));
					{
						computeEditor = new SyntaxArea();
						computeScroller.setViewportView(computeEditor);
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
				help = new HelpButton("pmwiki.php?n=Main.Compute");
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
			this.setTitle("Compute Variable");
			this.setResizable(false);
			this.setSize(525, 375);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}


	public void actionPerformed(ActionEvent act) {
		String cmd = act.getActionCommand();
		if(cmd=="*"){
			try{
				computeEditor.getDocument().insertString(computeEditor.getCaretPosition(), " * ", null);
			}catch(Exception e){}
				computeEditor.requestFocus();
		}else if(cmd=="/"){
			try{
				computeEditor.getDocument().insertString(computeEditor.getCaretPosition(), " / ", null);
			}catch(Exception e){}
			computeEditor.requestFocus();
		}else if(cmd=="^2"){
			try{
				computeEditor.getDocument().insertString(computeEditor.getCaretPosition(), "^2 ", null);
			}catch(Exception e){}
			computeEditor.requestFocus();
		}else if(cmd=="^3"){
			try{
				computeEditor.getDocument().insertString(computeEditor.getCaretPosition(), "^3 ", null);
			}catch(Exception e){}
			computeEditor.requestFocus();
		}else if(cmd=="( )"){
			try{
				computeEditor.getDocument().insertString(computeEditor.getCaretPosition(), "( )", null);
			}catch(Exception e){}
			computeEditor.requestFocus();
		}else if(cmd=="+"){
			try{
				computeEditor.getDocument().insertString(computeEditor.getCaretPosition(), " + ", null);
			}catch(Exception e){}
			computeEditor.requestFocus();
		}else if(cmd=="-"){
			try{
				computeEditor.getDocument().insertString(computeEditor.getCaretPosition(), " - ", null);
			}catch(Exception e){}
			computeEditor.requestFocus();
		}else if(cmd=="^4"){
			try{
				computeEditor.getDocument().insertString(computeEditor.getCaretPosition(), "^4 ", null);
			}catch(Exception e){}
			computeEditor.requestFocus();
		}else if(cmd=="^5"){
			try{
				computeEditor.getDocument().insertString(computeEditor.getCaretPosition(), "^5 ", null);
			}catch(Exception e){}
			computeEditor.requestFocus();
		}else if(cmd=="Cancel"){
			this.dispose();
		}else if(cmd == "OK"){
			String subn;
			String txt = computeEditor.getText();
			String data = variableSelector.getSelectedData();
			String comp = computeEditor.getText();
			if(!isValidComputeExp(comp,data)){
				JOptionPane.showMessageDialog(this, "Invalid Compute. Please enter a compute expression");
				return;
			}
			if(subName.getText().equals("")){
				JOptionPane.showMessageDialog(this, "Please enter a variable name");
				return;
			}
			String vname = RController.makeValidVariableName(subName.getText());
			Deducer.execute(data + "[[\"" + vname + "\"]]" +" <- with("+data+","+comp+")");
			
			addToHistory(variableSelector.getSelectedData(),comp);
			lastDataName = variableSelector.getSelectedData();
			Deducer.setRecentData(data);
			this.dispose();
		}else if(cmd == "Reset"){
			computeEditor.setText("");
			subName.setText("");
			computeEditor.requestFocus();
		}else if(cmd == "comboBoxChanged"){
			if(act.getSource()==recent){
				computeEditor.setText((String)recent.getSelectedItem());
				computeEditor.requestFocus();
			}else{
				computeEditor.setText("");
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
					computeEditor.getDocument().insertString(computeEditor.getCaretPosition(), item+"() ", null);
					computeEditor.requestFocus();
					computeEditor.setCaretPosition(computeEditor.getCaretPosition()-2);
				}catch(Exception e){}	
			}else{
				item =(String)variableSelector.getJList().getSelectedValue();
				try{
					computeEditor.getDocument().insertString(computeEditor.getCaretPosition(),item, null);
					computeEditor.requestFocus();
				}catch(Exception e){}					
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}

	public void mousePressed(MouseEvent arg0) {}

	public void mouseReleased(MouseEvent arg0) {}

	
	public static boolean isValidComputeExp(String compute,String dataName){
		if(compute==null || compute.length()<1)
			return false;
		
		REXP valid=null;
		valid = Deducer.timedEval("(function(x,compute){"+
										"result<-try(e <- substitute(compute),silent=TRUE)\n"+
										"if(class(result)==\"try-error\")\n"+
										"	return(FALSE)\n"+
										"result<-try(r <- eval(e, x, parent.frame()),silent=TRUE)\n"+
										"if(class(result)==\"try-error\")\n"+
										"	return(FALSE)\n"+
										"TRUE\n"+
										"})("+dataName+","+compute+")");
		if(valid==null){
			return false;
		}
		if(!valid.isLogical()){
			return false;
		}
		return ((REXPLogical)valid).isTRUE()[0];
	}
}