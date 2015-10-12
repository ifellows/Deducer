package org.rosuda.deducer.menu;




import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.DJList;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import javax.swing.WindowConstants;

import org.rosuda.JGR.*;
import org.rosuda.deducer.data.*;
import org.rosuda.JGR.util.*;




public class MergeData extends javax.swing.JFrame implements ActionListener {
	private JPanel varPanel1;
	private JPanel varPanel2;
	private JCheckBox key1;
	private JList pairedList;
	private JButton mergeButton;
	private JButton useSecondary;
	private JButton usePrimary;
	private JButton pairAllButton;
	private JButton removeButton1;
	private JButton unpairButton;
	private JList mergeByList;
	private JButton useBoth;
	private JPanel mergeByPanel;
	private JPanel pairedPanel;
	private JCheckBox key2;
	private JCheckBox includeCheckBox2;
	private JCheckBox includeCheckBox1;
	private JList dataList2;
	private JList dataList1;
	private JButton pairButton;
	private JButton help;
	
	private static DefaultListModel mergeByListModel;
	private static DefaultListModel pairedListModel;
	private static DefaultComboBoxModel  dataList1Model;
	private static DefaultComboBoxModel  dataList2Model;
	private static String lastDataName1;
	private static Vector lastVarNames1;
	private static String lastDataName2;
	private static Vector lastVarNames2;
	private static String lastDataSetName;

	public MergeData(String newDataSetName,String dataName1,String dataName2) {
		super();
		initGUI(newDataSetName,dataName1,dataName2);
	}
	
	private void initGUI(String newDataSetName,String dataName1,String dataName2) {
		try {
			boolean findPairs=false;
			boolean sameData = dataName1.equals(lastDataName1) && dataName2.equals(lastDataName2);
			Vector data1vars = new Vector();
			data1vars.copyInto(Deducer.timedEval("colnames("+dataName1+")").asStrings());
			Vector data2vars = new Vector();
			data2vars.copyInto(Deducer.timedEval("colnames("+dataName2+")").asStrings());			
			if(sameData){
				if(!data1vars.equals(lastVarNames1))
					sameData=false;
				if(!data2vars.equals(lastVarNames2))
					sameData=false;
			}
			lastDataName1=dataName1;
			lastDataName2=dataName2;
			lastDataSetName=newDataSetName;
			lastVarNames1 = data1vars;
			lastVarNames2 = data2vars;		
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			JLabel sourceLabel = new JLabel();
			sourceLabel.setText("Source:");
			getContentPane().add(sourceLabel, new AnchorConstraint(525, 326, 554, 200, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
			{
				useBoth = new JButton();
				getContentPane().add(useBoth, new AnchorConstraint(599, 326, 633, 200, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				useBoth.setText("[Both]");
				useBoth.setPreferredSize(new java.awt.Dimension(86, 22));
				useBoth.addActionListener(this);
			}
			{
				useSecondary = new JButton();
				getContentPane().add(useSecondary, new AnchorConstraint(556, 326, 590, 264, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				useSecondary.setText("[2]");
				useSecondary.setPreferredSize(new java.awt.Dimension(42, 22));
				useSecondary.setFont(new java.awt.Font("Dialog",0,10));
				useSecondary.addActionListener(this);
			}
			{
				usePrimary = new JButton();
				getContentPane().add(usePrimary, new AnchorConstraint(556, 264, 590, 200, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				usePrimary.setText("[1]");
				usePrimary.setPreferredSize(new java.awt.Dimension(44, 22));
				usePrimary.setFont(new java.awt.Font("Dialog",0,10));
				usePrimary.addActionListener(this);
			}
			{
				pairAllButton = new JButton();
				getContentPane().add(pairAllButton, new AnchorConstraint(230, 566, 281, 436, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				pairAllButton.setText("Auto-Pair");
				pairAllButton.setPreferredSize(new java.awt.Dimension(89, 33));
				pairAllButton.setFont(new java.awt.Font("Dialog",0,10));
				pairAllButton.addActionListener(this);
			}
			{
				removeButton1 = new JButton();
				getContentPane().add(removeButton1, new AnchorConstraint(910, 326, 956, 200, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				removeButton1.setText("Remove");
				removeButton1.setPreferredSize(new java.awt.Dimension(86, 23));
				removeButton1.addActionListener(this);
			}
			{
				unpairButton = new JButton();
				getContentPane().add(unpairButton, new AnchorConstraint(722, 326, 755, 200, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				unpairButton.setText(" Remove ");
				unpairButton.setPreferredSize(new java.awt.Dimension(86, 22));
				unpairButton.addActionListener(this);
			}
			{
				JPanel runCancelPanel = new OkayCancelPanel(false,true,this);
				getContentPane().add(runCancelPanel, new AnchorConstraint(904, 990, 956, 690, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				runCancelPanel.setPreferredSize(new java.awt.Dimension(77, 34));
			}
			{
				mergeByPanel = new JPanel();
				BorderLayout mergeByPanelLayout = new BorderLayout();
				mergeByPanel.setLayout(mergeByPanelLayout);
				getContentPane().add(mergeByPanel, new AnchorConstraint(829, 675, 956, 335, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				mergeByPanel.setPreferredSize(new java.awt.Dimension(232, 83));
				mergeByPanel.setBorder(BorderFactory.createTitledBorder("Match Cases By"));
				{
					if(mergeByListModel==null || !sameData)
						mergeByListModel = new DefaultListModel();

					mergeByList = new MergeDJList();
					JScrollPane scrollPane = new JScrollPane(mergeByList,
                            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);					
					mergeByPanel.add(scrollPane, BorderLayout.CENTER);
					mergeByList.setModel(mergeByListModel);
				}
			}
			{
				mergeButton = new IconButton("/icons/1downarrow_32.png","Case Identifier",this,"Case Identifier");
				getContentPane().add(mergeButton, new AnchorConstraint(763, 598, 820, 408, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				mergeButton.setPreferredSize(new java.awt.Dimension(130, 32));
				mergeButton.setContentAreaFilled(false);
			}
			{
				pairedPanel = new JPanel();
				BorderLayout pairedPanelLayout = new BorderLayout();
				pairedPanel.setLayout(pairedPanelLayout);
				getContentPane().add(pairedPanel, new AnchorConstraint(383, 675, 763, 335, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				pairedPanel.setPreferredSize(new java.awt.Dimension(232, 248));
				pairedPanel.setBorder(BorderFactory.createTitledBorder("Common Variables"));
				{
					if(pairedListModel==null || !sameData)
						pairedListModel = new DefaultListModel();
					pairedList = new PairDJList();
					JScrollPane sPane = new JScrollPane(pairedList,
                            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);					
					pairedPanel.add(sPane, BorderLayout.CENTER);
					pairedList.setModel(pairedListModel);
				}
			}
			{
				key2 = new JCheckBox();
				getContentPane().add(key2, new AnchorConstraint(423, 971, 457, 707, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				key2.setText("Unique Identifiers");
				key2.setPreferredSize(new java.awt.Dimension(180, 22));
				key2.addActionListener(this);
				key2.setVisible(false);
			}
			{
				key1 = new JCheckBox();
				getContentPane().add(key1, new AnchorConstraint(423, 258, 457, 18, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				key1.setText("Unique Identifiers");
				key1.setPreferredSize(new java.awt.Dimension(164, 22));
				key1.addActionListener(this);
				key1.setVisible(false);
			}
			{
				includeCheckBox2 = new JCheckBox();
				getContentPane().add(includeCheckBox2, new AnchorConstraint(392, 971, 423, 707, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				includeCheckBox2.setText("Drop Unmatched Cases");
				includeCheckBox2.setPreferredSize(new java.awt.Dimension(180, 20));
				includeCheckBox2.addActionListener(this);
			}
			{
				includeCheckBox1 = new JCheckBox();
				getContentPane().add(includeCheckBox1, new AnchorConstraint(383, 326, 414, 18, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				includeCheckBox1.setText("Drop Unmatched Cases");
				includeCheckBox1.setPreferredSize(new java.awt.Dimension(210, 20));
				includeCheckBox1.addActionListener(this);
			}
			{
				pairButton = new IconButton("/icons/1downarrow_32.png","Pair variables",this,"Pair");
				getContentPane().add(pairButton, new AnchorConstraint(325, 566, 374, 436, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				pairButton.setPreferredSize(new java.awt.Dimension(89, 32));
				pairButton.setContentAreaFilled(false);
			}
			{
				varPanel1 = new JPanel();
				BorderLayout VarPanel1Layout = new BorderLayout();
				getContentPane().add(varPanel1, new AnchorConstraint(12, 427, 374, 12, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
				varPanel1.setPreferredSize(new java.awt.Dimension(279, 232));
				varPanel1.setLayout(VarPanel1Layout);
				varPanel1.setBorder(BorderFactory.createTitledBorder("Primary Data [1]: "+dataName1));
				{
					
					if(dataList1Model==null || !sameData){
						String[] data1Names = Deducer.timedEval("colnames("+dataName1+")").asStrings();
						dataList1Model = new DefaultComboBoxModel(data1Names);
						findPairs=true;
					}
					dataList1 = new JList();
					dataList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					JScrollPane scroll1 = new JScrollPane(dataList1,
                            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);					
					varPanel1.add(scroll1, BorderLayout.CENTER);
					dataList1.setModel(dataList1Model);
				}
			}
			{
				varPanel2 = new JPanel();
				getContentPane().add(varPanel2, new AnchorConstraint(12, 20, 374, 584, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				BorderLayout jPanel1Layout = new BorderLayout();
				varPanel2.setPreferredSize(new java.awt.Dimension(264, 232));
				varPanel2.setBorder(BorderFactory.createTitledBorder("Secondary Data [2]: "+dataName2));
				varPanel2.setLayout(jPanel1Layout);
				{
					if(dataList2Model==null || !sameData){
						String[] data2Names = Deducer.timedEval("colnames("+dataName2+")").asStrings();
						dataList2Model = new DefaultComboBoxModel(data2Names);
						findPairs=true;
					}
					dataList2 = new JList();
					JScrollPane scroll2 = new JScrollPane(dataList2,
                            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					varPanel2.add(scroll2, BorderLayout.CENTER);
					dataList2.setModel(dataList2Model);
					dataList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				}
			}
			{
				help = new HelpButton("pmwiki.php?n=Main.MergeData");
				getContentPane().add(help, new AnchorConstraint(904, 990, 956, 12, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				help.setPreferredSize(new java.awt.Dimension(32, 32));
			}
			if(findPairs)
				findPairs();
			pack();
			this.setMinimumSize(new Dimension(600,600));
			this.setSize(720, 690);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	
	public void findPairs(){
		for(int i=0;i<dataList1.getModel().getSize();i++){
			for(int j=0;j<dataList2.getModel().getSize();j++)
				if(dataList1.getModel().getElementAt(i).equals(dataList2.getModel().getElementAt(j))){
					((DefaultListModel)pairedList.getModel()).addElement("[1] "+dataList1.getModel().getElementAt(i));
					((DefaultComboBoxModel)dataList1.getModel()).removeElementAt(i);
					((DefaultComboBoxModel)dataList2.getModel()).removeElementAt(j);
					i--;
					break;
				}
		}
	}

	public void actionPerformed(ActionEvent act) {
		String cmd = act.getActionCommand();
		if(cmd =="Cancel"){
			this.dispose();
		}else if(cmd == "Case Identifier"){
			int[] ind = pairedList.getSelectedIndices();
			if(ind.length==0){
				JOptionPane.showMessageDialog(this,"Please select a variable from the common variables list to identify common cases.");
			}
			for(int i=0;i<ind.length;i++)
				mergeByListModel.addElement(((String)
						pairedListModel.getElementAt(ind[i])).substring(4));
			for(int i= (ind.length-1);i>=0;i--)
				pairedListModel.removeElementAt(ind[i]);
			
		}else if(cmd == "Remove"){
				int[] ind = mergeByList.getSelectedIndices();
				for(int i= (ind.length-1);i>=0;i--){
					pairedListModel.addElement("[1] "+
							mergeByListModel.getElementAt(ind[i]));					
					mergeByListModel.removeElementAt(ind[i]);
				}
		}else if(cmd ==" Remove "){
			int[] ind = pairedList.getSelectedIndices();
			for(int i= (ind.length-1);i>=0;i--){
				String temp = (String) pairedListModel.getElementAt(ind[i]);
				if(temp.indexOf("<==>")==(-1)){
					((DefaultComboBoxModel)dataList2.getModel()).addElement(temp.substring(4));
					((DefaultComboBoxModel)dataList1.getModel()).addElement(temp.substring(4));
				}else{
					String[] t = temp.split("<==>");	
					((DefaultComboBoxModel)dataList2.getModel()).addElement(t[1]);
					((DefaultComboBoxModel)dataList1.getModel()).addElement(t[0].substring(4));
				}
				pairedListModel.removeElementAt(ind[i]);
			}				
		}else if(cmd == "Pair"){
			String var1 = (String)dataList1.getSelectedValue();
			String var2 = (String)dataList2.getSelectedValue();
			if(var1==null || var2==null){
				JOptionPane.showMessageDialog(this,"Please select one variable from each data frame to create a variable pair");
				return;
			}
			((DefaultComboBoxModel)dataList1.getModel()).removeElement(var1);
			((DefaultComboBoxModel)dataList2.getModel()).removeElement(var2);
			if(var1.equals(var2)){
				pairedListModel.addElement("[1] "+var1);
			}else
				pairedListModel.addElement("[1] "+var1+"<==>"+var2);
		}else if(cmd == "Auto-Pair"){
			findPairs();
		}else if(cmd == "[Both]"){
			String temporary;
			int[] ind = pairedList.getSelectedIndices();
			if(ind.length==0){
				JOptionPane.showMessageDialog(this,"Please select a variable from the common variables list.\n" +
						"This will indicate that the merged dataset should contain \ntwo versions of this variable, " +
						"one from the primary data and one from the secondary.");
			}
			for(int i=0;i<ind.length;i++){
				temporary = (String)pairedList.getModel().getElementAt(ind[i]);
				pairedListModel.removeElementAt(ind[i]);
				((DefaultListModel)pairedList.getModel()).add(ind[i], "[b] "+temporary.substring(4));
			}
			pairedList.setSelectedIndices(ind);
		}else if(cmd == "[1]"){
			String temporary;
			int[] ind = pairedList.getSelectedIndices();
			if(ind.length==0){
				JOptionPane.showMessageDialog(this,"Please select a variable from the common variables list.\n" +
						"This will indicate that the merged dataset should \nuse the primary data set for this variable");
			}
			for(int i=0;i<ind.length;i++){
				temporary = (String)pairedList.getModel().getElementAt(ind[i]);
				pairedListModel.removeElementAt(ind[i]);
				((DefaultListModel)pairedList.getModel()).add(ind[i], "[1] "+temporary.substring(4));
			}
			pairedList.setSelectedIndices(ind);
		}else if(cmd == "[2]"){
			String temporary;
			int[] ind = pairedList.getSelectedIndices();	
			if(ind.length==0){
				JOptionPane.showMessageDialog(this,"Please select a variable from the common variables list.\n" +
						"This will indicate that the merged dataset should \nuse the secondary data set for this variable");
			}
			for(int i=0;i<ind.length;i++){
				temporary = (String)pairedList.getModel().getElementAt(ind[i]);
				pairedListModel.removeElementAt(ind[i]);
				((DefaultListModel)pairedList.getModel()).add(ind[i], "[2] "+temporary.substring(4));
			}
			pairedList.setSelectedIndices(ind);
		}else if(cmd == "Run"){
			if(mergeByListModel.size()==0){
				int choice =JOptionPane.showOptionDialog(this,"No variables have been selected to merge by.\n" +
						"Would you like to merge by row names?","Match By Row Names?",JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,null,null,null);
				if(choice==JOptionPane.NO_OPTION)
					return;
				
			}
			boolean merged = merge();
			if(merged){	
				this.dispose();
				//DataFrameWindow.setTopDataWindow(lastDataSetName);
			}
		}
	}
	
	public boolean merge(){
		String temp;
		String byX="";
		String byY="";
		String call = "";
		try{
			if(mergeByListModel.getSize()>0){
				byX+="c(";
				byY+="c(";
				for(int i=0;i<mergeByListModel.getSize();i++){
					temp = mergeByListModel.elementAt(i).toString();
					if(temp.indexOf("<==>")==(-1)){
						byX+="\""+temp+"\"";
						byY+="\""+temp+"\"";

					}else{
						String[] t = temp.split("<==>");	
						byX+="\""+t[0]+"\"";
						byY+="\""+t[1]+"\"";
					}
					if(i<mergeByListModel.getSize()-1){
						byX+=",";
						byY+=",";
					}				
				}
				byX+=")";
				byY+=")";	
				String[] data1Unique = Deducer.timedEval("as.character(rownames(na.omit("+lastDataName1+"["+byX+"]"+
											"))[duplicated(na.omit("+lastDataName1+"["+byX+"]))])").asStrings();
				String[] data2Unique = Deducer.timedEval("as.character(rownames(na.omit("+lastDataName2+"["+byY+"]"+
											"))[duplicated(na.omit("+lastDataName2+"["+byY+"]))])").asStrings();
				if(data1Unique.length>0 && data2Unique.length>0){
					int choice =JOptionPane.showOptionDialog(this,"Niether data frame has unique case identifiers.\n" +
							"Merging will create cases for every possible combination of duplicates.\n" +
							"Would you like to continue?","Duplicate Idenifiers Detected",JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE,null,null,null);
					if(choice==JOptionPane.NO_OPTION)
						return false;
					Deducer.timedEval("cat('\\n')");
					Deducer.timedEval("cat('\\nDuplicate Identifiers in "+lastDataName1+": "+"\\n')");
					String temp1="";
					for(int i=0;i<data1Unique.length;i++)
						temp1+=" "+data1Unique[i];
					Deducer.timedEval("cat('"+temp1+"\\n')");
					Deducer.timedEval("cat('\\nDuplicate Identifiers in "+lastDataName2+": "+"\\n')");
					temp1="";
					for(int i=0;i<data2Unique.length;i++)
						temp1+=" "+data2Unique[i];
					Deducer.timedEval("cat('"+temp1+"\\n')");
					Deducer.execute("",false);
				}
			}else{
				byX="\"row.names\"";
				byY="\"row.names\"";
			}
			ArrayList excludeX = new ArrayList();
			ArrayList excludeY = new ArrayList();
			String[] varNames = {"",""};
			char code;
			int pairedSize = pairedListModel.getSize();
			for(int i=0;i<pairedSize;i++){
				temp = (String) pairedListModel.getElementAt(i);
				code = temp.charAt(1);
				temp = temp.substring(4);
				if(temp.indexOf("<==>")==(-1)){
					varNames[0]=temp;
					varNames[1]=temp;

				}else{
					varNames = temp.split("<==>");	
				}
				if(code=='1'){
				excludeY.add(varNames[1]);
				}else if(code=='2'){
					excludeX.add(varNames[0]);
				}
			}
			String temp1 = Deducer.getUniqueName(lastDataName1+".temp");
			String temp2 = Deducer.getUniqueName(lastDataName2+".temp");
			call+=(temp1+"<-"+lastDataName1+"[setdiff(colnames("+lastDataName1+"),"+
								makeRStringVector(excludeX)+")]"	
			);
			call+="\n"+(temp2+"<-"+lastDataName2+"[setdiff(colnames("+lastDataName2+"),"+
								makeRStringVector(excludeY)+")]"	
			);
			call+="\n"+(lastDataSetName+"<-merge("+
							temp1+","+temp2+",by.x="+byX+",by.y="+byY+",incomparables = NA"+
							",all.x =" + (!includeCheckBox1.getModel().isSelected()?"T":"F")+
							",all.y =" + (!includeCheckBox2.getModel().isSelected()?"T":"F")+
							")"
			);
			call+="\n"+(
				"rm(list=c(\""+temp1+"\",\""+temp2+"\"))"
			);
			Deducer.execute(call);
			Deducer.setRecentData(lastDataSetName);
			return true;
		}catch(Exception e){new ErrorMsg(e);return false;}
	}
	
	public String makeRStringVector(ArrayList lis){
		if(lis.size()==0)
			return "c()";
		String result = "c(";
		for(int i=0;i<lis.size();i++){
			result+="\""+lis.get(i).toString()+"\"";
			if(i<lis.size()-1)
				result+=",";
		}
		result+=")";
		return result;
	}

	private class PairDJList extends DJList{
		public void drop(DropTargetDropEvent dtde) {
			super.drop(dtde);
			int len = this.getModel().getSize();
			String temporary;
			for(int i=0;i<len;i++){
				temporary = (String)this.getModel().getElementAt(i);
				if(!temporary.startsWith("[")){
					((DefaultListModel)this.getModel()).removeElementAt(i);
					((DefaultListModel)this.getModel()).add(i, "[1] "+temporary);
				}
			}
		}
		
	}
	private class MergeDJList extends DJList{
		public void drop(DropTargetDropEvent dtde) {
			super.drop(dtde);
			int len = this.getModel().getSize();
			String temporary;
			for(int i=0;i<len;i++){
				temporary = (String)this.getModel().getElementAt(i);
				if(temporary.startsWith("[")){
					((DefaultListModel)this.getModel()).removeElementAt(i);
					((DefaultListModel)this.getModel()).add(i, temporary.substring(4));
				}
			}
		}
		
	}
}
