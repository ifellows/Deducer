
package org.rosuda.deducer.menu;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.JGR.layout.ArrayFocusTraversalPolicy;
import org.rosuda.JGR.JGR;

import org.rosuda.JGR.toolkit.JGRPrefs;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPLogical;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class SetRecodingsDialog extends javax.swing.JDialog implements KeyListener, ActionListener, ListSelectionListener{
	private JPanel lsitHolder;
	private JList recodingsList;
	private JTextField doubleField1;
	private JTextField doubleField2;
	private JButton addCode3Button;
	private JTextField elseTextFiels;
	private JLabel elseLabel;
	private JSeparator jSeparator_IL1;
	private JButton clear;
	private JPanel doublePanel;
	private JTextPane infoTextArea;
	private JScrollPane textScroller;
	private JList varList;
	private JScrollPane varScrollPane;
	private JPanel VariablePanel;
	private JButton addCode2Button;
	private JTextField into2Field;
	private JLabel into2Label;
	private JLabel valueLabel;
	private JSeparator seperator;
	private JButton addCode1;
	private JTextField into1Field;
	private JLabel intoLabel;
	private JTextField singleField;
	private JComboBox boolComboBox;
	private JLabel valueLabel1;
	private JPanel logicHolder;
	private String dataName;
	private OkayCancelPanel okcan;
	private HelpButton help;
	private static DefaultComboBoxModel model;
	
	public SetRecodingsDialog(JFrame frame) {
		super(frame);
		initGUI(new String[0]);
	}
	public SetRecodingsDialog(JDialog dialog, String[] recodings,String data) {
		super(dialog);
		initGUI(recodings);
		dataName=data;
	}
	
	private void initGUI(String[] recodes) {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			{
				clear = new JButton();
				getContentPane().add(clear, new AnchorConstraint(846, 686, 896, 578, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				clear.setText("Clear");
				clear.setPreferredSize(new java.awt.Dimension(69, 21));
				clear.addActionListener(this);
			}
			{
				help = new HelpButton("pmwiki.php?n=Main.RecodeVariables");
				getContentPane().add(help, new AnchorConstraint(896, 950, 975, 19, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				help.setPreferredSize(new java.awt.Dimension(32, 32));
			}
			{
				okcan = new OkayCancelPanel(false,false,this);
				getContentPane().add(okcan, new AnchorConstraint(896, 950, 975, 670, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				
			}
			{
				VariablePanel = new JPanel();
				AnchorLayout VariablePanelLayout = new AnchorLayout();
				VariablePanel.setLayout(VariablePanelLayout);
				getContentPane().add(VariablePanel, new AnchorConstraint(29, 512, 896, 19, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				VariablePanel.setPreferredSize(new java.awt.Dimension(315, 409));
				VariablePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED),
						"Variable Information", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));
				{
					varScrollPane = new JScrollPane();
					VariablePanel.add(varScrollPane, new AnchorConstraint(59, 798, 253, 249, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					varScrollPane.setPreferredSize(new java.awt.Dimension(173, 79));
					{
						ListModel varListModel = 
							new DefaultComboBoxModel(
									recodes);
						varList = new JList();
						varScrollPane.setViewportView(varList);
						varList.setModel(varListModel);
						varList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						varList.addListSelectionListener(this);
					}
				}
				{
					textScroller = new JScrollPane();
					VariablePanel.add(textScroller, new AnchorConstraint(338, 985, 988, 17, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					textScroller.setPreferredSize(new java.awt.Dimension(305, 266));
					{
						infoTextArea = new JTextPane();
						textScroller.setViewportView(infoTextArea);
						infoTextArea.setText("Select a Variable to get information");
						infoTextArea.setFont(JGRPrefs.DefaultFont);
					}
				}
			}

			{
				logicHolder = new JPanel();
				AnchorLayout logicHolderLayout = new AnchorLayout();
				logicHolder.setLayout(logicHolderLayout);
				getContentPane().add(logicHolder, new AnchorConstraint(31, 950, 505, 539, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				logicHolder.setPreferredSize(new java.awt.Dimension(263, 203));
				logicHolder.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED),
						"Code", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));
				{
					addCode3Button = new JButton();
					logicHolder.add(addCode3Button, new AnchorConstraint(830, 937, 933, 629, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					addCode3Button.setText("Add");
					addCode3Button.setPreferredSize(new java.awt.Dimension(81, 21));
					addCode3Button.addKeyListener(this);
					addCode3Button.addActionListener(this);
				}
				{
					elseTextFiels = new JTextField();
					logicHolder.add(elseTextFiels, new AnchorConstraint(830, 454, 938, 222, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					elseTextFiels.setPreferredSize(new java.awt.Dimension(60, 22));
				}
				{
					elseLabel = new JLabel();
					logicHolder.add(elseLabel, new AnchorConstraint(844, 222, 913, 70, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					elseLabel.setText("Else:");
					elseLabel.setPreferredSize(new java.awt.Dimension(40, 14));
				}
				{
					doublePanel = new JPanel();
					AnchorLayout doublePanelLayout = new AnchorLayout();
					doublePanel.setLayout(doublePanelLayout);
					logicHolder.add(doublePanel, new AnchorConstraint(450, 979, 805, 20, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					doublePanel.setPreferredSize(new java.awt.Dimension(252, 72));
				}
				{
					into2Field = new JTextField();
					doublePanel.add(into2Field, new AnchorConstraint(450, 613, 750, 375, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					into2Field.setPreferredSize(new java.awt.Dimension(60, 17));
				}
				{
					into2Label = new JLabel();
					doublePanel.add(into2Label, new AnchorConstraint(506, 327, 715, 168, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					into2Label.setText("into");
					into2Label.setPreferredSize(new java.awt.Dimension(40, 15));
					into2Label.setHorizontalAlignment(SwingConstants.CENTER);
				}
				{
					doubleField2 = new JTextField();
					doublePanel.add(doubleField2, new AnchorConstraint(40, 958, 340, 732, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					doubleField2.setPreferredSize(new java.awt.Dimension(60, 18));
				}
				{
					valueLabel = new JLabel();
					doublePanel.add(valueLabel, new AnchorConstraint(76, 732, 298, 259, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					valueLabel.setText("\u2264     Value     \u2264");
					valueLabel.setPreferredSize(new java.awt.Dimension(119, 16));
					valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
				}
				{
					doubleField1 = new JTextField();
					doublePanel.add(doubleField1, new AnchorConstraint(40, 259, 340, 41, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					doubleField1.setPreferredSize(new java.awt.Dimension(60, 18));
				}
				{
					addCode2Button = new JButton();
					doublePanel.add(addCode2Button, new AnchorConstraint(479, 958, 743, 636, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					addCode2Button.setText("Add");
					addCode2Button.setPreferredSize(new java.awt.Dimension(81, 19));
					addCode2Button.addKeyListener(this);
					addCode2Button.addActionListener(this);
				}
				{
					jSeparator_IL1 = new JSeparator();
					doublePanel.add(jSeparator_IL1, new AnchorConstraint(895, 998, 951, 1, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					jSeparator_IL1.setPreferredSize(new java.awt.Dimension(251, 4));
				}
				{
					addCode1 = new JButton();
					logicHolder.add(addCode1, new AnchorConstraint(273, 948, 376, 629, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					addCode1.setText("Add");
					addCode1.setPreferredSize(new java.awt.Dimension(84, 21));
					addCode1.addKeyListener(this);
					addCode1.addActionListener(this);
				}
				{
					into1Field = new JTextField();
					logicHolder.add(into1Field, new AnchorConstraint(273, 614, 383, 370, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					into1Field.setPreferredSize(new java.awt.Dimension(60, 21));
				}
				{
					intoLabel = new JLabel();
					logicHolder.add(intoLabel, new AnchorConstraint(288, 351, 357, 188, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					intoLabel.setText("into");
					intoLabel.setPreferredSize(new java.awt.Dimension(43, 14));
					intoLabel.setHorizontalAlignment(SwingConstants.CENTER);
				}
				{
					singleField = new JTextField();
					logicHolder.add(singleField, new AnchorConstraint(125, 614, 235, 400, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					singleField.setPreferredSize(new java.awt.Dimension(60, 23));
				}
				{
					ComboBoxModel boolComboBoxModel = 
						new DefaultComboBoxModel(
								new String[] { "=", "\u2264","\u2265" });
					boolComboBox = new JComboBox();
					logicHolder.add(boolComboBox, new AnchorConstraint(130, 400, 233, 200, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					boolComboBox.setModel(boolComboBoxModel);
					boolComboBox.setPreferredSize(new java.awt.Dimension(60, 21));
				}
				{
					valueLabel1 = new JLabel();
					logicHolder.add(valueLabel1, new AnchorConstraint(145, 245, 219, 51, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					valueLabel1.setText(" Value");
					valueLabel1.setPreferredSize(new java.awt.Dimension(51, 15));
				}
				{
					seperator = new JSeparator();
					logicHolder.add(seperator, new AnchorConstraint(426, 979, 485, 24, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					seperator.setPreferredSize(new java.awt.Dimension(251, 12));
				}
				logicHolder.setFocusCycleRoot(true);
				logicHolder.setFocusTraversalPolicy(new ArrayFocusTraversalPolicy(new java.awt.Component[] {singleField,
						into1Field, addCode1, boolComboBox}));
			}
			{
				lsitHolder = new JPanel();
				BorderLayout lsitHolderLayout = new BorderLayout();
				lsitHolder.setLayout(lsitHolderLayout);
				getContentPane().add(lsitHolder, new AnchorConstraint(519, 889, 851, 578, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				lsitHolder.setPreferredSize(new java.awt.Dimension(199, 142));
				lsitHolder.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED), 
						"Recodings", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));
				{
					DefaultComboBoxModel theModel=new DefaultComboBoxModel();
					if(model!=null)
						for(int i=0;i<model.getSize();i++)
							theModel.addElement(model.getElementAt(i));
					recodingsList = new JList();
					lsitHolder.add(new JScrollPane(recodingsList,
	                        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
	                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
					recodingsList.setModel(theModel);
				}
			}
			doublePanel.setFocusCycleRoot(true);
			doublePanel.setFocusTraversalPolicy(new ArrayFocusTraversalPolicy(
					new java.awt.Component[] {doubleField1, 
							 doubleField2, into2Field, addCode2Button}));
			this.setTitle("Set Variable Codings");
			this.setMinimumSize(new Dimension(750,600));
			this.setSize(647, 462);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCodes(){
		String codes = "\"";
		DefaultComboBoxModel curModel = (DefaultComboBoxModel)recodingsList.getModel();
		for(int i=0;i<curModel.getSize();i++){
			codes+=(String)curModel.getElementAt(i) +";";
		}
		codes+="\"";
		return codes;
	}
	
	private static boolean isNumeric(String s){
		boolean result=false;
		try{
			double val = Double.parseDouble(s.trim());
			result = true;
		}catch(Exception e){}
		if(s.trim().equals("NA"))
			result = true;
		return result;
		
		
	}
	
	public void addCode(String rcode){
		String lastEntry="";
		DefaultComboBoxModel curModel = (DefaultComboBoxModel)recodingsList.getModel();
		if(curModel.getSize()>0)
			lastEntry = (String)curModel.getElementAt(curModel.getSize()-1);
		if(lastEntry.startsWith("else")){
				curModel.removeElement(lastEntry);
				curModel.addElement(rcode);
				if(!rcode.startsWith("else"))
					curModel.addElement(lastEntry);
		}else
			curModel.addElement(rcode);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		DefaultComboBoxModel curModel = (DefaultComboBoxModel)recodingsList.getModel();
		if(cmd == "Clear"){
			int[] selected =recodingsList.getSelectedIndices();
			if(selected.length==0){
				curModel.removeAllElements();
			}else{
				for(int i=selected.length-1;i>=0;i--)
					curModel.removeElementAt(selected[i]);
			}
			recodingsList.repaint();
		}else if(cmd == "Cancel"){
			this.dispose();
		}else if(cmd == "Add"){
			Object source = arg0.getSource();

			if(source == addCode1){
				String rcode;
				String value = singleField.getText();
				String into = into1Field.getText();
				if(into.length()==0)
					return;
				if(!isNumeric(into))
					into="'"+addSlashes(into)+"'";	
				if(!isNumeric(value))
					value="'"+addSlashes(value)+"'";				
				if(boolComboBox.getSelectedIndex() == 0){
					rcode = value+" -> "+into;
					addCode(rcode);
				}else if(boolComboBox.getSelectedIndex() == 1){
					rcode = "Lo:"+value+" -> "+into;
					addCode(rcode);
				}else if(boolComboBox.getSelectedIndex() == 2){
					rcode = value+":Hi"+" -> "+into;
					addCode(rcode);
				}
				singleField.setText("");
				into1Field.setText("");
			}else if(source==addCode2Button){
				String rcode;
				String value1 = doubleField1.getText().trim();
				String value2 = doubleField2.getText().trim();
				String into = into2Field.getText().trim();
				if((value1.length()==0 && value2.length()==0) || into.length()==0)
					return;		
				if(!isNumeric(into))
					into="'"+addSlashes(into)+"'";
				if(!isNumeric(value2))
					value2="'"+addSlashes(value2)+"'";	
				if(!isNumeric(value1))
					value1="'"+addSlashes(value1)+"'";
				
				rcode = value1+":"+value2+" -> "+into;
				addCode(rcode);
				doubleField1.setText("");
				doubleField2.setText("");
				into2Field.setText("");
			}else if(source==addCode3Button){
				String rcode;
				String into = elseTextFiels.getText().trim();
				if(into.length()==0)
					return;
				if(!isNumeric(into))
					into="'"+addSlashes(into)+"'";
				rcode = "else -> "+into;
				addCode(rcode);
				elseTextFiels.setText("");
			}
		}else if(cmd=="OK"){
			model=curModel;
			this.dispose();
		}
		
	}

	
	public void valueChanged(ListSelectionEvent arg0) {
		if(!arg0.getValueIsAdjusting()){
			String var = (String)varList.getSelectedValue();
			var = var.split("\u2192")[0];
			var =dataName+"$"+var;
			String[] output;
			REXP num;
			try {
				num = Deducer.timedEval("is.numeric("+var+")");
				REXP cha = Deducer.timedEval("is.character("+var+")");
				REXP fact = Deducer.timedEval("is.factor("+var+")");
				if(num.isLogical() && ((REXPLogical)num).isTRUE()[0]){
					output = Deducer.timedEval("capture.output(data.frame(percentiles=quantile("+var+",seq(0,1,.1),na.rm=T)))").asStrings();
				}else if((cha.isLogical() && ((REXPLogical)cha).isTRUE()[0]) || 
						(fact.isLogical() && ((REXPLogical)fact).isTRUE()[0]) ){
					output = Deducer.timedEval("capture.output(data.frame(xtabs(~"+var+")))").asStrings();
				}else
					output = Deducer.timedEval("capture.output(summary("+var+"))").asStrings();
				if(output!=null){
					String temp="";
					
					for(int i=0;i<output.length;i++){
						temp+=output[i]+"\n";
						if(i>500){
							temp+="...Truncated...";
							break;
						}
					}
					infoTextArea.setText(temp);
				}
			} catch (REXPMismatchException e) {
				new ErrorMsg(e);
			}
		}
		
	}
	
	public static String addSlashes(String str){
		if(str==null) return "";

		StringBuffer s = new StringBuffer(str);
		for(int i = 0; i < s.length(); i++){
			if(s.charAt (i) == '\\'){
				s.insert(i++, '\\');
				s.insert(i++, '\\');
				s.insert(i++, '\\');
			}else if(s.charAt (i) == '\"'){
				s.insert(i++, '\\');
				s.insert(i++, '\\');
				s.insert(i++, '\\');
			}else if(s.charAt (i) == '\''){
				s.insert(i++, '\\');
				s.insert(i++, '\\');
				s.insert(i++, '\\');
			}
		}
		
		return s.toString();
	}
	
	public void keyPressed(KeyEvent arg0) {	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
			actionPerformed(new ActionEvent(e.getSource(),ActionEvent.ACTION_PERFORMED,"Add"));	
	}
	
	public void keyTyped(KeyEvent e) {}

}
