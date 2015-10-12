package org.rosuda.deducer.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import org.rosuda.JGR.JGR;
import org.rosuda.JGR.RController;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPLogical;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.DJList;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;
import org.rosuda.deducer.toolkit.VariableSelector;
import org.rosuda.deducer.toolkit.VariableSelector.FilteringModel;

public class TransformDialog extends JDialog implements ActionListener{
	private VariableSelector variableSelector;
	private JList transformVariableList;
	private JButton intoButton;
	private IconButton removeButton;
	private IconButton addButton;
	private HelpButton help;
	private OkayCancelPanel okayCancelPanel;
	private JButton plotButton;
	private JComboBox typeComboBox;
	private JSpinner binSpinner;
	private JPanel transPanel;
	private JComboBox rankComboBox;
	private JLabel binLabel;
	private JLabel rankLabel;
	
	private String plotType;
	
	private static String[] types= {":::       Select Transformation       :::", "            --- Scalings ---", "Center:    x-mean(x)", 
		"Standardize:    (x-mean(x))/sd(x)", 
		"Robust Std.:    (x-median(x))/mad(x))","Range:    (x+min(x))/range(x)", "Box-Cox", "Rank","Natural log:    log(x)", 
		"Log + 1:    log(x+1)",
		"Square Root:    sqrt(x)", "Absolute Value:    abs(x)", "Squared:    x^2", "Inverse:    1/x",
		"Reciprocal root:    -1/sqrt(x)", "Arcsine:    asin(sqrt(x))", "            --- Binnings ---","Quantiles",
		"Equal Width", "            --- Custom ---","Enter Function..."};
	private static TransformDialogModel lastModel;

	
	public TransformDialog(JFrame frame) {
		super(frame);
		if(lastModel==null)
			lastModel = new TransformDialogModel();
		initGUI();
		setModel(lastModel);
	}
	
	private void initGUI() {
		try {
			this.setTitle("Transform Variables");
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			{
				help = new HelpButton("pmwiki.php?n=Main.TransformVariables");
				getContentPane().add(help, new AnchorConstraint(867, 934, 968, 21, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));

				help.setPreferredSize(new java.awt.Dimension(32, 32));
			}
			{
				removeButton = new IconButton("/icons/1leftarrow_32.png","Remove",this,"Remove");
				getContentPane().add(removeButton, new AnchorConstraint(150, 425, 680, 350, AnchorConstraint.ANCHOR_ABS, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				removeButton.setPreferredSize(new java.awt.Dimension(41, 40));
			}

			{
				addButton = new IconButton("/icons/1rightarrow_32.png","Add",this,"Add");
				getContentPane().add(addButton, new AnchorConstraint(100, 425, 525, 350, AnchorConstraint.ANCHOR_ABS, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				addButton.setPreferredSize(new java.awt.Dimension(41, 40));
			}
			{
				intoButton = new JButton();
				getContentPane().add(intoButton, new AnchorConstraint(50, 934, 295, 782, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
				intoButton.setText("\u2192 Target");
				intoButton.setPreferredSize(new java.awt.Dimension(96, 26));
				intoButton.setFont(new java.awt.Font("Tahoma",0,10));
				intoButton.addActionListener(this);
			}
			{
				JPanel transformVariablePanel = new JPanel();
				BorderLayout transformPanelLayout = new BorderLayout();
				transformVariablePanel.setLayout(transformPanelLayout);
				transformVariableList = new RecodeDJList();
				JScrollPane transformScroller = new JScrollPane(transformVariableList,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				transformVariablePanel.add(transformScroller);
				getContentPane().add(transformVariablePanel, new AnchorConstraint(25, 758, 600, 435, AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				transformVariableList.setModel(new DefaultListModel());
				transformVariablePanel.setPreferredSize(new java.awt.Dimension(184, 240));
				transformVariablePanel.setBorder(BorderFactory.createTitledBorder("Variables to Transform"));
			}
			{
				plotButton = new JButton("Plot");
				getContentPane().add(plotButton, new AnchorConstraint(601, 945, 295, 525, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				plotButton.addActionListener(this);
			}
			{
				transPanel = new JPanel();
				transPanel.setBorder(BorderFactory.createTitledBorder("Transformation"));
				getContentPane().add(transPanel, new AnchorConstraint(670, 765, 900, 350, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				transPanel.setLayout(new AnchorLayout());
			}
			{
				typeComboBox = new JComboBox(types);
				transPanel.add(typeComboBox, new AnchorConstraint(160, 980, 460, 20, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				//typeComboBox.setSelectedItem(types[2]);
				typeComboBox.setEditable(false);
				typeComboBox.addActionListener(this);				
			}
			{
				binLabel = new JLabel("# of bins:");
				binLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				binLabel.setVerticalAlignment(SwingConstants.CENTER);
				binLabel.setPreferredSize(new Dimension(60,25));
				transPanel.add(binLabel, new AnchorConstraint(500, 500, 700, 20, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				binLabel.setVisible(false);
			}
			{
				binSpinner = new JSpinner(new SpinnerNumberModel(4,2,1000,1));
				transPanel.add(binSpinner, new AnchorConstraint(500, 900, 900, 520, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));		
				binSpinner.setVisible(false);
			}
			{
				rankLabel = new JLabel("Ties:");
				rankLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				rankLabel.setVerticalAlignment(SwingConstants.CENTER);
				rankLabel.setPreferredSize(new Dimension(60,25));
				transPanel.add(rankLabel, new AnchorConstraint(500, 500, 700, 20, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));	
			}
			{
				rankComboBox = new JComboBox(new String[]{"average", "first", "random", "max", "min"});
				transPanel.add(rankComboBox, new AnchorConstraint(500, 900, 900, 520, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));				
			}	
			{
				variableSelector = new VariableSelector();
				getContentPane().add(variableSelector, new AnchorConstraint(25, 325, 835, 21, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				variableSelector.setPreferredSize(new java.awt.Dimension(192, 301));
				variableSelector.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				variableSelector.getJComboBox().addActionListener(this);
				variableSelector.setDropStringSplitter("\u2192");
				if(lastModel.data!=null)
					variableSelector.setSelectedData(lastModel.data);
			}
			{
				okayCancelPanel = new OkayCancelPanel(true, true, this);
				this.add(okayCancelPanel, new AnchorConstraint(926, 978, 980, 402, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE));
				okayCancelPanel.setPreferredSize(new java.awt.Dimension(307, 32));
			}

			this.setMinimumSize(new Dimension(450,250));
			this.setSize(640, 500);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	
	public void setDataName(String dataName){
		if(!dataName.equals(variableSelector.getSelectedData()))
			variableSelector.setSelectedData(dataName);
	}
	
	private void setModel(TransformDialogModel mod){
		try{
			if(mod.variables!=null && mod.data!=null){
				variableSelector.reset();
				transformVariableList.setModel(mod.variables);
				String temp;
				boolean exists;
				for(int i=0;i<mod.variables.getSize();i++){
					temp = (String) mod.variables.get(i);
					temp = temp.substring(0, temp.indexOf("\u2192"));
					exists=variableSelector.remove(temp);
					if(!exists){
						transformVariableList.setModel(new DefaultListModel());
						variableSelector.getJList().setModel(variableSelector.new FilteringModel(
								Deducer.timedEval("names("+variableSelector.getJComboBox().getSelectedItem()
										+")").asStrings()));
						break;
					}
				}
			}
			if(mod.variables.size()==0){
				variableSelector.reset();
				transformVariableList.setModel(mod.variables);
			}
			DefaultComboBoxModel m = (DefaultComboBoxModel) typeComboBox.getModel();
			if(m.getIndexOf(mod.transform)>=0)
				typeComboBox.setSelectedItem(mod.transform);
			else{
				typeComboBox.setEditable(true);
				typeComboBox.setSelectedItem(mod.transform);
			}
			binSpinner.setValue(mod.bins);
			rankComboBox.setSelectedItem(mod.rankTies);
			plotType = mod.plot;
		}catch(Exception e){new ErrorMsg(e);}
	}
	
	private TransformDialogModel getModel(){
		TransformDialogModel mod = new TransformDialogModel();
		mod.bins = (Integer) binSpinner.getValue();
		mod.data = variableSelector.getSelectedData();
		mod.rankTies = (String) rankComboBox.getSelectedItem();
		mod.transform = (String) typeComboBox.getSelectedItem();
		
		DefaultListModel cur = (DefaultListModel) transformVariableList.getModel();
		DefaultListModel n = new DefaultListModel();
		for(int i=0;i<cur.size();i++)
			n.addElement(cur.get(i));
		mod.variables = n;
		if(plotType!=null)
			mod.plot = plotType;
		return mod;
	}
	
	private void plot(){
		DefaultListModel lis = (DefaultListModel) transformVariableList.getModel();
		Vector vars = new Vector();
		Vector into = new Vector();
		for(int i=0;i<lis.size();i++){
			String tmp = (String) lis.get(i);
			vars.add(tmp.split("\u2192")[0]);
			into.add(tmp.split("\u2192")[1]);
		}
		String variables = Deducer.makeRCollection(vars, "c", true);
		String data = variableSelector.getSelectedData() ;
		if(lis.size()==0){
			JOptionPane.showMessageDialog(this, "Please select a variable.");
			return;
		}
		for(int i=0;i<vars.size();i++){
			REXP ev;
			ev = Deducer.timedEval("is.numeric(" + data+"$"+ (String) vars.get(i)+")");
			if(ev==null || !ev.isLogical() || ((REXPLogical)ev).isFALSE()[0]){
				JOptionPane.showMessageDialog(this, vars.get(i).toString() + " is not a numeric variable.");
				return;				
			}	
		}
		Deducer.execute("dev.new(); onesample.plot(" + data + "[,"+variables+"]"+ ")");
		
	}

	private String getSingleCall(String variable,String into, String type, String lambda, String functionName){
		String call = null;
		if(type==types[2]){				//center
			call = into + " <- " + variable + " - mean(" + variable + ", na.rm=TRUE)\n";
		}else if(type == types[3]){		//std
			call = into + " <- (" + variable + " - mean("+variable+",na.rm=TRUE)) / sd("+variable+",na.rm=TRUE)\n";
		}else if(type == types[4]){		//robust std
			call = into + " <- (" + variable + " - median("+variable+",na.rm=TRUE)) / mad("+variable+",na.rm=TRUE)\n";
		}else if(type == types[5]){		//range
			call = into + " <- (" + variable + " - min("+variable+",na.rm=TRUE)) / diff(range("+variable+",na.rm=TRUE))\n";
		}else if(type == types[6]){		//box cox
			call = into + " <- bcPower(" + variable + ","+lambda+")\n";
		}else if(type == types[7]){		//rank
			call = into + " <- rank(" + variable + ", na.last='keep', ties='" + 
					rankComboBox.getSelectedItem().toString() + "')\n";
		}else if(type == types[8]){		//log
			call = into + " <- log(" + variable + ")\n";
		}else if(type == types[9]){		//log+1
			call = into + " <- log(" + variable + " + 1)\n";
		}else if(type == types[10]){		//sqrt
			call = into + " <- ifelse(" + variable + "<0, NA, sqrt(" + variable + "))\n";
		}else if(type == types[11]){		//abs
			call = into + " <- abs(" + variable + ")\n";
		}else if(type == types[12]){		//^2
			call = into + " <- " + variable + "^2\n";
		}else if(type == types[13]){		//1/x
			call = into + " <- 1/" + variable + "\n";
		}else if(type == types[14]){		//-1/sqrt(x)
			call = into + " <- -1/sqrt(" + variable + ")\n";
		}else if(type == types[15]){		//asin
			call = into + " <- asin(sqrt(" + variable + "))\n";
		}else if(type == types[17]){		//quantile
			int val = ((Integer) binSpinner.getValue()).intValue();
			call = into + " <- cut2(" + variable + ", g=" + val + ")\n" ;
		}else if(type == types[18]){		//equal
			call = into + " <- cut(" + variable + "," + binSpinner.getValue().toString() +
					", include.lowest=TRUE)\n";
		}else if(typeComboBox.getSelectedIndex()==20 || typeComboBox.getSelectedIndex()<0){		//custom
			call = into + " <- " + functionName + "(" + variable + ")\n";
		}

		
		return call;
	}
	
	private String getCall(){
		DefaultListModel lis = (DefaultListModel) transformVariableList.getModel();
		if(lis.size()==0){
			JOptionPane.showMessageDialog(this, "Please select a variable.");
			return null;
		}
		if(typeComboBox.getSelectedIndex()==0){
			JOptionPane.showMessageDialog(this, "Please select a Transformation.");
			return null;				
		}
		Vector vars = new Vector();
		Vector into = new Vector();
		for(int i=0;i<lis.size();i++){
			String tmp = (String) lis.get(i);
			vars.add(tmp.split("\u2192")[0]);
			into.add(tmp.split("\u2192")[1]);
		}
		
		String data = variableSelector.getSelectedData();
		
		for(int i=0;i<vars.size();i++){
			REXP ev;
			ev = Deducer.timedEval("is.numeric(" + data+"$"+ (String) vars.get(i)+")");
			if(ev==null || !ev.isLogical() || ((REXPLogical)ev).isFALSE()[0]){
				JOptionPane.showMessageDialog(this, vars.get(i).toString() + " is not a numeric variable.");
				return null;				
			}	
		}
		
		String RCmd ="";

		String lam = null;
		boolean isBoxCox = typeComboBox.getSelectedIndex()==6;
		boolean isCustom = typeComboBox.getSelectedIndex()==20 || typeComboBox.getSelectedIndex()<0;
		String func = null;
		String funcName = Deducer.getUniqueName("trans.function");
		String tmpVar=null;
		String tmpInto=null;
	
		if(isBoxCox || typeComboBox.getSelectedIndex()==8){
			for(int i=0;i<vars.size();i++){
				REXP ev;
				ev = Deducer.timedEval("all(" + data+"$"+ (String) vars.get(i)+">0)");
				if(ev==null || !ev.isLogical() || ((REXPLogical)ev).isFALSE()[0]){
					int opt = JOptionPane.showOptionDialog(this, vars.get(i).toString() + 
							" Has values <=0, which is not valid for this tranformation.\n" +
							"Would you like to coninue anyway?", "Warning", JOptionPane.YES_NO_OPTION, 
							JOptionPane.WARNING_MESSAGE, 
							null, new String[]{"Continue","Cancel"}, "Cancel");
					if(opt!=JOptionPane.OK_OPTION)
						return null;				
				}	
			}		
		}
		if(typeComboBox.getSelectedIndex()==9){
			for(int i=0;i<vars.size();i++){
				REXP ev;
				ev = Deducer.timedEval("all(" + data+"$"+ (String) vars.get(i)+">(-1))");
				if(ev==null || !ev.isLogical() || ((REXPLogical)ev).isFALSE()[0]){
					int opt = JOptionPane.showOptionDialog(this, vars.get(i).toString() + 
							" Has values <= -1, which is not valid for this tranformation.\n" +
							"Would you like to coninue anyway?", "Warning", JOptionPane.YES_NO_OPTION, 
							JOptionPane.WARNING_MESSAGE, 
							null, new String[]{"Continue","Cancel"}, "Cancel");
					if(opt!=JOptionPane.OK_OPTION)
						return null;				
				}	
			}		
		}
		if(typeComboBox.getSelectedIndex()==15){
			for(int i=0;i<vars.size();i++){
				REXP ev;
				ev = Deducer.timedEval("all(" + data+"$"+ (String) vars.get(i)+">=0) && all(" + data+"$"+ (String) vars.get(i)+"<=1)");
				if(ev==null || !ev.isLogical() || ((REXPLogical)ev).isFALSE()[0]){
					int opt = JOptionPane.showOptionDialog(this, vars.get(i).toString() + 
							" Has values outside of [0 1], which is not valid for this tranformation.\n" +
							"Would you like to coninue anyway?", "Warning", JOptionPane.YES_NO_OPTION, 
							JOptionPane.WARNING_MESSAGE, 
							null, new String[]{"Continue","Cancel"}, "Cancel");
					if(opt!=JOptionPane.OK_OPTION)
						return null;				
				}	
			}		
		}
		
		if(typeComboBox.getSelectedIndex()==17){
				String res = Deducer.requirePackage("Hmisc");
				if(res == "installed")
					RCmd += "library(Hmisc)\n";
				else if(res == "not-installed")
					return null;
		}
		
		if(isCustom){
			func = "function(x) " + typeComboBox.getSelectedItem().toString();
			REXP tmp = Deducer.timedEval("is.function(try(" + func + ",silent=TRUE))");
			if( tmp==null || !tmp.isLogical() || ((REXPLogical) tmp).isFALSE()[0] ){	
				JOptionPane.showMessageDialog(this, func + " is not a valid function.");
				return null;			
			}
		}
		
		if(vars.size()==1){

			tmpVar = data + "[['" + (String)vars.get(0) + "']]";
			tmpInto = data + "[['" + (String)into.get(0) + "']]";
			if(isBoxCox){
				lam = Deducer.getUniqueName("box.cox.mle");
				RCmd += lam + " <- powerTransform(" +tmpVar+")\n";
				RCmd += "print("+lam+")\n";
			}	
			if(isCustom)
				RCmd += funcName + " <- " + func + "\n";
			RCmd += getSingleCall(tmpVar,tmpInto,
					(String)typeComboBox.getSelectedItem(),lam + "$lambda",funcName);
			if(isBoxCox)
				RCmd += "rm("+lam+")\n";
			if(isCustom)
				RCmd += "rm("+funcName+")\n";
			if(plotType == "Histogram")
				RCmd +=  "onesample.plot(d(" + tmpVar + ", " + tmpInto + "))\n";
		}else{
			String variables = Deducer.getUniqueName("variables");
			String intoVars = Deducer.getUniqueName("into.variables");
			RCmd += variables + " <- " + Deducer.makeRCollection(vars, "c", true) + "\n";
			RCmd += intoVars + " <- " + Deducer.makeRCollection(into, "c", true) + "\n";
			if(isBoxCox){
				lam = Deducer.getUniqueName("box.cox.mle");
				RCmd += lam + " <- powerTransform(" + data + "[," + variables + "])\n";
				RCmd += "print("+lam+")\n";
			}	
			tmpVar = data + "[["+ variables + "[i]]]";
			tmpInto = data + "[["+ intoVars + "[i]]]";	
			if(isCustom)
				RCmd += funcName + " <- " + func + "\n";
			RCmd += "for(i in 1:length(" + variables + "))\n\t" + getSingleCall(tmpVar,tmpInto,
					(String)typeComboBox.getSelectedItem(),lam +"$lambda[i]",funcName);
			if(plotType == "Histogram")
				RCmd +=  "onesample.plot(" + data + "[,c(" + variables + ", " + intoVars + ")])\n";
			RCmd += "rm(list=c('"+variables+"','"+intoVars+"'"+ (isBoxCox ? (",'"+lam+"'") : "") +
						(isCustom ? (",'"+funcName+"'") : "")+"))\n";
			
		}
		
		return RCmd;
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd=e.getActionCommand();
		if(cmd=="Cancel")
			this.dispose();
		else if(cmd=="Add"){
			JList list = variableSelector.getJList();
			Object[] elements=list.getSelectedValues();
			String temp;
			for(int i=0;i<elements.length;i++){
				if(elements[i]!=null){
					temp = (String) elements[i];
					((DefaultListModel)transformVariableList.getModel()).addElement(
							temp.concat("\u2192".concat(temp + ".tr")));
					variableSelector.remove(elements[i]);
				}
			}
		}else if(cmd=="Remove"){
			Object[] elements=transformVariableList.getSelectedValues();
			String temp;
			for(int i=0;i<elements.length;i++){
				if(elements[i]!=null){
					temp=(String) elements[i];
					((DefaultListModel)transformVariableList.getModel()).removeElement(elements[i]);
					variableSelector.add(temp.substring(0,temp.indexOf("\u2192")));
				}
			}			
		}else if(cmd=="\u2192 Target"){
			int selectedIndex = transformVariableList.getSelectedIndex();
			if(selectedIndex==-1)
				return;
			String entry = (String) transformVariableList.getSelectedValue();
			entry = entry.substring(0,entry.indexOf("\u2192"));
			String newVar = (String) JOptionPane.showInputDialog(this,"Recode "+entry+" into:");
			if(newVar==null || newVar.length()==0)
				return;
			newVar = RController.makeValidVariableName(newVar);
			((DefaultListModel) transformVariableList.getModel()).removeElementAt(selectedIndex);
			((DefaultListModel) transformVariableList.getModel()).addElement(entry+"\u2192"+newVar);
		}else if(cmd == "comboBoxChanged"){
			if(e.getSource()==variableSelector.getJComboBox())
				transformVariableList.setModel(new DefaultListModel());
			else{
				if(typeComboBox.getSelectedIndex()==7){
					rankComboBox.setVisible(true);
					rankLabel.setVisible(true);
					binSpinner.setVisible(false);
					binLabel.setVisible(false);
				}else if(typeComboBox.getSelectedIndex()>=17 && typeComboBox.getSelectedIndex()<=18){
					rankComboBox.setVisible(false);
					rankLabel.setVisible(false);
					binSpinner.setVisible(true);
					binLabel.setVisible(true);					
				}else{
					rankComboBox.setVisible(false);
					rankLabel.setVisible(false);
					binSpinner.setVisible(false);
					binLabel.setVisible(false);	
				}
				if(typeComboBox.getSelectedIndex()==20 || typeComboBox.getSelectedIndex()<0){
					typeComboBox.setEditable(true);
				}else{
					typeComboBox.setEditable(false);
				}
				
				
				if(((String)typeComboBox.getSelectedItem()).startsWith("            ---"))
					typeComboBox.setSelectedIndex(0);
			}
		}else if(cmd=="Run"){
			lastModel = getModel();
			String call = getCall();
			if(call!=null){
				Deducer.setRecentData(variableSelector.getSelectedData());
				Deducer.execute(call);			
				this.dispose();
			}
		}else if(cmd == "Reset"){
			setModel(new TransformDialogModel());
		}else if(cmd=="Plot"){
			String[] plots = new String[]{"None","Histogram"};
			int p = JOptionPane.showOptionDialog(this, "Select plot type:", "Plot", JOptionPane.OK_OPTION, 
							JOptionPane.QUESTION_MESSAGE, null,
							plots, plotType);
			if(p>=0)
				plotType=plots[p];
		}
	}
	
	private class RecodeDJList extends DJList{
		public void drop(DropTargetDropEvent dtde) {
			super.drop(dtde);
			int len = this.getModel().getSize();
			String temporary;
			for(int i=0;i<len;i++){
				temporary = (String)this.getModel().getElementAt(i);
				if(temporary.indexOf("\u2192")<0){
					((DefaultListModel)this.getModel()).removeElementAt(i);
					((DefaultListModel)this.getModel()).add(i, temporary+"\u2192"+temporary+".tr");
				}
			}
		}
	}
	
	class TransformDialogModel{
		public DefaultListModel variables = new DefaultListModel();
		public String data =null;
		public String transform = types[0];
		public String rankTies = "average";
		public Integer bins = new Integer(4);
		public String plot = "None";
		
	}
}
