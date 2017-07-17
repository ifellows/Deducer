package org.rosuda.deducer.menu;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.rosuda.JGR.JGR;
import org.rosuda.JGR.RController;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.AssumptionIcon;
import org.rosuda.deducer.toolkit.DJList;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;
import org.rosuda.deducer.toolkit.SingletonAddRemoveButton;
import org.rosuda.deducer.toolkit.SingletonDJList;
import org.rosuda.deducer.toolkit.VariableSelector;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;


public class ContingencyDialog extends JDialog implements ActionListener {
	protected VariableSelector variableSelector;
	protected SingletonAddRemoveButton addRemoveStratumButton;
	protected IconButton removeColumn;
	protected JButton help;
	protected SubsetPanel subsetPanel;
	protected SingletonDJList stratumList;
	protected DJList columnList;
	protected DJList rowList;
	protected JButton postHoc;
	protected JButton statistics;
	protected JButton cells;
	protected IconButton addColumn;
	protected JButton results;
	protected IconButton removeRow;
	protected IconButton addRow;
	protected OkayCancelPanel okCancel;
	protected JPanel strataPanel;
	protected JPanel columnPanel;
	protected JPanel rowPanel;
	
	protected CellOptions cellOpt;
	protected StatisticsOptions statOpt;
	protected ResultsOptions resultOpt;
	
	protected String rCmd ="";
	
	protected static CellOptions lastCellOpt;
	protected static StatisticsOptions lastStatOpt;
	protected static ResultsOptions lastResultOpt;
	protected static DefaultListModel lastRowModel;
	protected static DefaultListModel lastColumnModel;
	protected static DefaultListModel lastStratumModel;
	protected static String lastDataName;
	protected static String lastSubset;
	
	public ContingencyDialog(JFrame frame) {
		super(frame);
		initGUI();
		cellOpt = new CellOptions();
		statOpt = new StatisticsOptions();
		resultOpt = new ResultsOptions();
		setToLast();
	}
	
	public void saveToLast(){
		lastDataName = variableSelector.getSelectedData();
		lastRowModel = (DefaultListModel) rowList.getModel();
		lastColumnModel = (DefaultListModel) columnList.getModel();
		lastStratumModel = (DefaultListModel) stratumList.getModel();
		lastSubset = subsetPanel.getText();
		lastCellOpt = cellOpt;
		lastStatOpt = statOpt;
		lastResultOpt = resultOpt;
	}
	
	public void setToLast(){
		boolean allExist=false;
		if(lastDataName==null || lastStratumModel==null || lastColumnModel==null || lastRowModel==null || lastStatOpt==null
				|| lastResultOpt==null || lastCellOpt == null){
			reset(true);
			return;
		}
		variableSelector.setSelectedData(lastDataName);
		allExist=variableSelector.removeAll(lastRowModel);
		if(allExist)
			rowList.setModel(lastRowModel);
		else{
			reset(true);
			return;
		}
		allExist=variableSelector.removeAll(lastColumnModel);
		if(allExist)
			columnList.setModel(lastColumnModel);
		else{
			reset(true);
			return;
		}
		allExist=variableSelector.removeAll(lastStratumModel);
		if(allExist){
			stratumList.setModel(lastStratumModel);
			addRemoveStratumButton.setList(stratumList);
			addRemoveStratumButton.refresh();			
		}
		else{
			reset(true);
			return;
		}
		if(RController.isValidSubsetExp(lastSubset,lastDataName)){
			subsetPanel.setText(lastSubset);
		}
		cellOpt = lastCellOpt;
		statOpt = lastStatOpt;
		resultOpt = lastResultOpt;
		
	}
	
	protected void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);

			{
				help = new HelpButton("pmwiki.php?n=Main.ContingencyTables");
				getContentPane().add(help, new AnchorConstraint(910, 71, 977, 15, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				help.setPreferredSize(new java.awt.Dimension(32, 32));
				help.addActionListener(this);
			}
			{
				postHoc = new JButton();
				getContentPane().add(postHoc, new AnchorConstraint(217, 954, 269, 818, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				postHoc.setText("Post-Hoc");
				postHoc.setPreferredSize(new java.awt.Dimension(100, 27));
				postHoc.addActionListener(this);
			}
			{
				statistics = new JButton();
				getContentPane().add(statistics, new AnchorConstraint(142, 954, 194, 818, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				statistics.setText("Statistics");
				statistics.setPreferredSize(new java.awt.Dimension(100, 27));
				statistics.addActionListener(this);
			}
			{
				cells = new JButton();
				getContentPane().add(cells, new AnchorConstraint(62, 954, 118, 818, AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				cells.setText("Cells");
				cells.setPreferredSize(new Dimension(100, 29));
				cells.addActionListener(this);
			}
			{
				addRow = new IconButton("/icons/1rightarrow_32.png","Add Row",this,"Add Row");
				getContentPane().add(addRow, new AnchorConstraint(76, 442, 153, 371, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
				addRow.setPreferredSize(new Dimension(42, 42));
				addRow.setContentAreaFilled(false);
			}
			{
				okCancel = new OkayCancelPanel(true,true,this);
				getContentPane().add(okCancel, new AnchorConstraint(950, 900, 977, 550, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));	
				okCancel.setPreferredSize(new Dimension(300,40));
			}
			{
				strataPanel = new JPanel();
				BorderLayout strataPanelLayout = new BorderLayout();
				strataPanel.setLayout(strataPanelLayout);
				getContentPane().add(strataPanel, new AnchorConstraint(650, 776, 749, 469, AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				strataPanel.setPreferredSize(new java.awt.Dimension(226, 50));
				strataPanel.setBorder(BorderFactory.createTitledBorder("Stratify By"));
				{
					ListModel stratumListModel = new DefaultListModel();
					stratumList = new SingletonDJList();
					strataPanel.add(stratumList, BorderLayout.CENTER);
					stratumList.setModel(stratumListModel);
				}
			}
			{
				columnPanel = new JPanel();
				BorderLayout columnPanelLayout = new BorderLayout();
				columnPanel.setLayout(columnPanelLayout);
				getContentPane().add(columnPanel, new AnchorConstraint(349, 776, 639, 469, AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				columnPanel.setPreferredSize(new java.awt.Dimension(226, 150));
				columnPanel.setEnabled(false);
				columnPanel.setBorder(BorderFactory.createTitledBorder("Column"));
				{
					ListModel columnListModel = 
						new DefaultListModel();
					columnList = new DJList();
					columnList.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
					columnPanel.add(columnList, BorderLayout.CENTER);
					columnList.setModel(columnListModel);
				}
			}
			{
				rowPanel = new JPanel();
				BorderLayout rowPanelLayout = new BorderLayout();
				rowPanel.setLayout(rowPanelLayout);
				getContentPane().add(rowPanel, new AnchorConstraint(24, 777, 325, 469, AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				rowPanel.setPreferredSize(new java.awt.Dimension(227, 156));
				rowPanel.setBorder(BorderFactory.createTitledBorder("Row"));
				{
					ListModel rowListModel = 
						new DefaultListModel();
					rowList = new DJList();
					rowList.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
					rowPanel.add(rowList, BorderLayout.CENTER);
					rowList.setModel(rowListModel);
				}
			}
			{
				variableSelector = new VariableSelector();
				getContentPane().add(variableSelector, new AnchorConstraint(24, 355, 834, 18, AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				variableSelector.setPreferredSize(new java.awt.Dimension(248, 419));
				variableSelector.getJComboBox().addActionListener(this);
			}
			{
				removeRow = new IconButton("/icons/1leftarrow_32.png","Remove Row",this,"Remove Row");
				getContentPane().add(removeRow, new AnchorConstraint(169, 442, 153, 371, AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
				removeRow.setPreferredSize(new java.awt.Dimension(42, 42));
				removeRow.setContentAreaFilled(false);
			}
			{
				addColumn = new IconButton("/icons/1rightarrow_32.png","Add Column",this,"Add Column");
				getContentPane().add(addColumn, new AnchorConstraint(405, 442, 153, 371, AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
				addColumn.setPreferredSize(new java.awt.Dimension(42,42));
				addColumn.setContentAreaFilled(false);
			}
			{
				removeColumn = new IconButton("/icons/1leftarrow_32.png","Remove Column",this,"Remove Column");
				getContentPane().add(removeColumn, new AnchorConstraint(498, 442, 153, 371, AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
				removeColumn.setPreferredSize(new java.awt.Dimension(42,42));
				removeColumn.setContentAreaFilled(false);
			}
			{
				addRemoveStratumButton = new SingletonAddRemoveButton(new String[]{"add","remove"},
						new String[]{"add","remove"},stratumList,variableSelector);
				getContentPane().add(addRemoveStratumButton, new AnchorConstraint(674, 442, 153, 371, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
				addRemoveStratumButton.setPreferredSize(new java.awt.Dimension(42, 42));
			}

			{
				results = new JButton();
				getContentPane().add(results, new AnchorConstraint(293, 954, 345, 818, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				results.setText("Results");
				results.setPreferredSize(new java.awt.Dimension(100, 27));
				results.addActionListener(this);
			}
			{
				JPanel tmpPanel =new JPanel();
				tmpPanel.setLayout(new BorderLayout());
				tmpPanel.setBorder(BorderFactory.createTitledBorder("Subset"));
				subsetPanel = new SubsetPanel(variableSelector.getJComboBox());
				tmpPanel.add(subsetPanel);
				getContentPane().add(tmpPanel, new AnchorConstraint(750, 776, 893, 469, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				subsetPanel.setBorder(BorderFactory.createTitledBorder("Subset"));				
				subsetPanel.setPreferredSize(new java.awt.Dimension(226, 53));
	
			}
			this.setTitle("Contingency Tables");
			this.setMinimumSize(new Dimension(700,500));
			this.setSize(736, 539);
			
			//unimplemented
			postHoc.setVisible(false);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	
	public void reset(boolean resetOptions){
		rowList.setModel(new DefaultListModel());
		columnList.setModel(new DefaultListModel());
		stratumList.setModel(new DefaultListModel());
		addRemoveStratumButton.setList(stratumList);
		addRemoveStratumButton.refresh();
		subsetPanel.setText("");
		if(resetOptions){
			cellOpt = new CellOptions();
			statOpt = new StatisticsOptions();
			resultOpt = new ResultsOptions();
		}
		variableSelector.reset();
	}
	
	public boolean executeTables(){
		String data = variableSelector.getSelectedData();
		String result = resultOpt.name;
		boolean runOnSubset=false;
		if(result==""){
			result="tables";
			result=RController.makeValidVariableName(result);
			result=Deducer.getUniqueName(result);
		}else
			result=Deducer.getUniqueName(result);
		
		if(subsetPanel.getText().length()>0){
			String sub = subsetPanel.getText();
			if(SubsetDialog.isValidSubsetExp(sub,data)){
				String tmp = Deducer.getUniqueName("tmp."+data);
				rCmd+=(tmp+"<-subset("+data+", "+sub+")")+"\n";
				SubsetDialog.addToHistory(data, sub);				
				data=tmp;
				runOnSubset=true;
			}else{
				JOptionPane.showMessageDialog(this, "Invalid Subset. Please enter a logical expression");
				return false;
			}
				
		}
		if(rowList.getModel().getSize()==0 || columnList.getModel().getSize()==0){
			JOptionPane.showMessageDialog(this, "Please select both column and row variables.");
			return false;
		}
		
		rCmd+=(result+"<-contingency.tables(\n\trow.vars="+Deducer.makeRCollection(rowList.getModel(), "d", false)+
					",\n\tcol.vars="+Deducer.makeRCollection(columnList.getModel(), "d", false)+
					(stratumList.getModel().getSize()>0 ? ",\n\tstratum.var="+((DefaultListModel)stratumList.getModel()).get(0).toString(): "") +
					",data="+data+")");
		rCmd+=statOpt.addStatistics(result);

		rCmd+="\n"+ ("print("+result+
				(cellOpt.row ? ",prop.r=T" :",prop.r=F")+
				(cellOpt.col ? ",prop.c=T" :",prop.c=F")+
				(cellOpt.total ? ",prop.t=T" :",prop.t=F")+
				(cellOpt.expected ? ",expected.n=T" :"")+
				(cellOpt.residuals ? ",residuals=T" :"")+
				(cellOpt.stdResiduals ? ",std.residuals=T" :"")+
				(cellOpt.adjResiduals ? ",adj.residuals=T" :"")+
				(cellOpt.noTables ? ",no.tables=T" :"")+
				")");
		if(!resultOpt.keep){
			if(!runOnSubset)
				rCmd+="\n"+ ("remove("+result+")");	
			else
				rCmd+="\n"+ ("remove("+result+","+data+")");
		}
		return true;
	}
	
	public void setDataName(String dataName,boolean resetIfNotSame){
		if(!dataName.equals(variableSelector.getSelectedData())){
			variableSelector.setSelectedData(dataName);
		}
	}

	public void actionPerformed(ActionEvent evnt) {
		String cmd = evnt.getActionCommand();
		
		if(cmd == "comboBoxChanged"){
			reset(false);
		}if(cmd == "Cancel"){
			this.dispose();
		}else if(cmd == "Run"){
			if(executeTables()){
				Deducer.execute(rCmd);
				saveToLast();
				Deducer.setRecentData(variableSelector.getSelectedData());
				this.dispose();
			}
		}else if(cmd == "Reset"){
			reset(true);
		}else if(cmd == "Add Row"){
			Object[] objs=variableSelector.getJList().getSelectedValues();
			for(int i=0;i<objs.length;i++){
				variableSelector.remove(objs[i]);
				((DefaultListModel)rowList.getModel()).addElement(objs[i]);
			}
		}else if(cmd == "Remove Row"){
			Object[] objs=rowList.getSelectedValues();
			for(int i=0;i<objs.length;i++){
				variableSelector.add(objs[i]);
				((DefaultListModel)rowList.getModel()).removeElement(objs[i]);
			}			
		}else if(cmd == "Add Column"){
			Object[] objs=variableSelector.getJList().getSelectedValues();
			for(int i=0;i<objs.length;i++){
				variableSelector.remove(objs[i]);
				((DefaultListModel)columnList.getModel()).addElement(objs[i]);
			}
		}else if(cmd == "Remove Column"){
			Object[] objs=columnList.getSelectedValues();
			for(int i=0;i<objs.length;i++){
				variableSelector.add(objs[i]);
				((DefaultListModel)columnList.getModel()).removeElement(objs[i]);
			}			
		}else if(cmd == "Add Stratum"){
			Object[] objs=variableSelector.getJList().getSelectedValues();
			if(objs.length>1){
				variableSelector.getJList().setSelectedIndex(variableSelector.getJList().getSelectedIndex());
			}else if(objs.length==1 && stratumList.getModel().getSize()==0){
				variableSelector.remove(objs[0]);
				((DefaultListModel)stratumList.getModel()).addElement(objs[0]);

			}
		}else if(cmd == "Remove Stratum"){
			DefaultListModel tmpModel =(DefaultListModel)stratumList.getModel();
			if(tmpModel.getSize()>0){
				variableSelector.add(tmpModel.remove(0));
				
			}
		}else if(cmd == "Post-Hoc"){
			
		}else if(cmd == "Statistics"){
			StatisticsDialog dia = new StatisticsDialog(this,statOpt);
			dia.setLocationRelativeTo(this);
			dia.setVisible(true);
		}else if(cmd == "Cells"){
			CellDialog cell = new CellDialog(this,cellOpt);
			cell.setLocationRelativeTo(this);
			cell.setVisible(true);
		}else if(cmd =="Results"){
			ResultsDialog res = new ResultsDialog(this,resultOpt);
			res.setLocationRelativeTo(this);
			res.setVisible(true);
		}
		
	}

	
	
	
	public class CellOptions{
		public boolean row=true;
		public boolean col=true;
		public boolean total=false;
		public boolean expected=false;
		public boolean residuals=false;
		public boolean stdResiduals=false;
		public boolean adjResiduals=false;
		public boolean noTables=false;
	}
	
	public class LikeOptions{
		public boolean conservative = false;
		
		public LikeOptions(){}
		
		public LikeOptions(boolean con){
			conservative = con;
		}
	}
	
	public class ChiOptions {
		public boolean asy = true;
		public boolean conservative = false;
		public boolean mc = false;
		public long b = 10000;
		
		public ChiOptions(){}
		
		public ChiOptions(boolean asymptotic,boolean conserv,boolean monteCarlo,long ss){
			asy=asymptotic;
			conservative = conserv;
			mc = monteCarlo;
			b=ss;
		}
		
		public boolean isValid(){
			if((b<1 && mc) || (asy==false && mc==false))
				return false;
			else
				return true;
		}
		
		public Object clone(){
			ChiOptions tmp = new ChiOptions();
			tmp.asy=asy;
			tmp.conservative=conservative;
			tmp.mc=mc;
			tmp.b=b;
			return tmp;
		}
	}
	
	public class StatisticsOptions{
		public boolean mantelHaen=false;
		public boolean kruskal=false;
		public boolean spearmans=false;
		public boolean kendall=false;
		public boolean liklihood=false;
		public boolean fishers=false;
		public boolean chisq=true;
		public ChiOptions chiSquared=new ChiOptions();
		public LikeOptions lrTest=new LikeOptions();
		
		public String addStatistics(String result){
			String cmd = "";
			if(chisq==true)
				cmd+="\n"+ (result+"<-add.chi.squared("+result+
						(chiSquared.conservative ? ",conservative=T": "")+
						(chiSquared.mc ? (",simulate.p.value=T,B="+chiSquared.b) : "") + ")");
			if(liklihood)
				cmd+="\n"+ (result+"<-add.likelihood.ratio("+result+
						(lrTest.conservative ? ",conservative=T": "")+ ")");
			if(fishers)
				cmd+="\n"+ (result+"<-add.fishers.exact("+result+ ")");
			if(spearmans)
				cmd+="\n"+ (result+"<-add.correlation("+result+ ",method='spearman')");
			if(kendall)
				cmd+="\n"+ (result+"<-add.correlation("+result+ ",method='kendall')");
			if(kruskal)
				cmd+="\n"+ (result+"<-add.kruskal("+result+ ")");
			if(mantelHaen)
				cmd+="\n"+ (result+"<-add.mantel.haenszel("+result+ ")");
			return cmd;
		}
	}
	
	public class ResultsOptions{
		public boolean keep = false;
		public String name = "";
	}
	
	
	
	
	public class CellDialog extends JDialog implements ActionListener {
		protected JPanel cellSumPanel;
		protected JCheckBox rowPerc;
		protected JCheckBox noTables;
		protected OkayCancelPanel okcan;
		//protected JButton cancel;
		//protected JButton okay;
		protected JCheckBox adjResid;
		protected JCheckBox stdResid;
		protected JCheckBox resid;
		protected JCheckBox expected;
		protected JPanel chiSumPanel;
		protected JCheckBox totalPerc;
		protected JCheckBox colPerc;
		

		public CellDialog(JDialog d,CellOptions opt) {
			super(d);
			initGUI();
			setOptions(opt);

		}
		
		public void setOptions(CellOptions opt){
			rowPerc.setSelected(opt.row);
			colPerc.setSelected(opt.col);
			totalPerc.setSelected(opt.total);
			expected.setSelected(opt.expected);
			resid.setSelected(opt.residuals);
			stdResid.setSelected(opt.stdResiduals);
			adjResid.setSelected(opt.adjResiduals);
			noTables.setSelected(opt.noTables);			
		}
		
		public CellOptions getOptions(){
			CellOptions opt = new CellOptions();
			opt.row=rowPerc.isSelected();
			opt.col=colPerc.isSelected();
			opt.total=totalPerc.isSelected();
			opt.expected=expected.isSelected();
			opt.residuals=resid.isSelected();
			opt.stdResiduals=stdResid.isSelected();
			opt.adjResiduals=adjResid.isSelected();
			opt.noTables=noTables.isSelected();
			return opt;
		}
		
		protected void initGUI() {
			try {
				{
					getContentPane().setLayout(null);
				}
				{
					cellSumPanel = new JPanel();
					getContentPane().add(cellSumPanel);
					cellSumPanel.setBounds(12, 17, 147, 133);
					cellSumPanel.setBorder(BorderFactory.createTitledBorder("Percentages"));
					cellSumPanel.setLayout(null);
					{
						rowPerc = new JCheckBox();
						cellSumPanel.add(rowPerc);
						rowPerc.setText("Row");
						rowPerc.setBounds(17, 26, 125, 19);
					}
					{
						colPerc = new JCheckBox();
						cellSumPanel.add(colPerc);
						colPerc.setText("Column");
						colPerc.setBounds(17, 51, 125, 19);
					}
					{
						totalPerc = new JCheckBox();
						cellSumPanel.add(totalPerc);
						totalPerc.setText("Total");
						totalPerc.setBounds(17, 76, 125, 19);
					}
				}
				{
					chiSumPanel = new JPanel();
					getContentPane().add(chiSumPanel);
					chiSumPanel.setBounds(180, 17, 208, 133);
					chiSumPanel.setBorder(BorderFactory.createTitledBorder("Chi-Squared"));
					chiSumPanel.setLayout(null);
					{
						expected = new JCheckBox();
						chiSumPanel.add(expected);
						expected.setText("Expected");
						expected.setBounds(17, 26, 180, 19);
					}
					{
						resid = new JCheckBox();
						chiSumPanel.add(resid);
						resid.setText("Residuals");
						resid.setBounds(17, 51, 180, 19);
					}
					{
						stdResid = new JCheckBox();
						chiSumPanel.add(stdResid);
						stdResid.setText("Standardized Residuals");
						stdResid.setBounds(17, 72, 180, 19);
					}
					{
						adjResid = new JCheckBox();
						chiSumPanel.add(adjResid);
						adjResid.setText("Adjusted Residuals");
						adjResid.setBounds(17, 97, 180, 19);
					}
				}
				{
					okcan = new OkayCancelPanel(false,false, this);
					getContentPane().add(okcan);
					okcan.setBounds(170, 150, 200, 50);
					
				}

				{
					noTables = new JCheckBox();
					getContentPane().add(noTables);
					noTables.setText("Don't print tables");
					noTables.setBounds(12, 162, 147, 19);
				}
				this.setTitle("Table Cell Contents");
				this.setResizable(false);
				this.setSize(400, 225);
			} catch (Exception e) {
				new ErrorMsg(e);
			}
		}

		public void actionPerformed(ActionEvent arg0) {
			String cmd = arg0.getActionCommand();
			if(cmd=="OK"){
				cellOpt = this.getOptions();
				this.dispose();
			}else if(cmd == "Cancel"){
				this.dispose();
			}
			
		}

	}
	

	
	public class StatisticsDialog extends JDialog implements ActionListener {
		protected JPanel nomByNomPanel;
		protected JCheckBox mantelHaen;
		protected JButton custom;
		protected JButton helpButton;
		protected JButton kruskalOptions;
		protected JButton exchAssump;
		protected IconButton lrgAssump;
		protected JCheckBox kruskal;
		protected JPanel nomByOrdPanel;
		protected JButton spearmanOptions;
		protected IconButton lrgAssump1;
		protected IconButton lrgAssump2;
		protected JSeparator jSeparator4;
		protected IconButton lrgAssump3;
		protected IconButton lrgAssump9;
		protected JCheckBox spearmans;
		protected JButton kendallOptions;
		protected IconButton lrgAssump4;
		protected JCheckBox kendall;
		protected JPanel ordByOrdPanel;
		protected JButton homoAssump;
		protected IconButton lrgAssump5;
		protected JSeparator jSeparator3;
		protected JSeparator jSeparator2;
		protected JLabel strataLabel;
		protected JSeparator jSeparator1;
		protected JSeparator sep;
		protected IconButton lrgAssump6;
		protected IconButton lrgAssump7;
		protected JButton liklihoodOptions;
		protected JCheckBox liklihood;
		protected JButton mantelOptions;
		protected JButton fishersOptions;
		protected JCheckBox fishers;
		protected JButton chisqOptions;
		protected JCheckBox chisq;
		protected OkayCancelPanel okcan;
		protected IconButton approxAssump;
		
		protected ChiOptions chiSquared;
		protected LikeOptions lrTest;


		
		public StatisticsDialog(JDialog d,StatisticsOptions so) {
			super(d);
			initGUI();
			setOptions(so);
			if(statOpt.chiSquared.mc){
				lrgAssump7.setVisible(false);
				approxAssump.setVisible(true);
			}else{
				lrgAssump7.setVisible(true);
				approxAssump.setVisible(false);					
			}
		}
		
		public void setOptions(StatisticsOptions so){
			mantelHaen.setSelected(so.mantelHaen);
			kruskal.setSelected(so.kruskal);
			spearmans.setSelected(so.spearmans);
			kendall.setSelected(so.kendall);
			liklihood.setSelected(so.liklihood);
			fishers.setSelected(so.fishers);
			chisq.setSelected(so.chisq);
			chiSquared = so.chiSquared;
			lrTest=so.lrTest;
		}
		
		public StatisticsOptions getOptions(){
			StatisticsOptions so = new StatisticsOptions();
			so.mantelHaen = mantelHaen.isSelected();
			so.kruskal = kruskal.isSelected();
			so.spearmans = spearmans.isSelected();
			so.kendall = kendall.isSelected();
			so.liklihood = liklihood.isSelected();
			so.fishers = fishers.isSelected();
			so.chisq = chisq.isSelected();
			so.chiSquared = chiSquared;
			so.lrTest = lrTest;
			return so;
		}
		
		protected void initGUI() {
			try {
				getContentPane().setLayout(null);
				{
					okcan = new OkayCancelPanel(false,false,this);
					getContentPane().add(okcan);
					okcan.setBounds(200, 280, 200, 40);
				}
				{
					nomByNomPanel = new JPanel();
					getContentPane().add(nomByNomPanel);
					nomByNomPanel.setLayout(null);
					nomByNomPanel.setBorder(BorderFactory.createTitledBorder("Nominal By Nominal"));
					nomByNomPanel.setBounds(12, 13, 192, 246);
					{
						jSeparator3 = new JSeparator();
						nomByNomPanel.add(jSeparator3);
						jSeparator3.setBounds(105, 174, 83, 4);
					}
					{
						jSeparator2 = new JSeparator();
						nomByNomPanel.add(jSeparator2);
						jSeparator2.setPreferredSize(new java.awt.Dimension(8, 4));
						jSeparator2.setBounds(5, 174, 8, 4);
					}
					{
						strataLabel = new JLabel();
						nomByNomPanel.add(strataLabel);
						strataLabel.setText("Cross-Stratum");
						strataLabel.setPreferredSize(new java.awt.Dimension(95, 15));
						strataLabel.setHorizontalAlignment(SwingConstants.CENTER);
						strataLabel.setBounds(13, 169, 95, 15);
					}
					{
						sep = new JSeparator();
						nomByNomPanel.add(sep);
						sep.setPreferredSize(new java.awt.Dimension(79, 8));
						sep.setBounds(75, 68, 79, 8);
					}
					{
						lrgAssump7 = new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
						nomByNomPanel.add(lrgAssump7);
						lrgAssump7.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
						lrgAssump7.setBounds(32, 38, 27, 27);
					}
					{
						approxAssump = new AssumptionIcon("/icons/mcapprox_assump.png","Monte Carlo Approximation",
								null,"Monte Carlo Approximation");
						nomByNomPanel.add(approxAssump);
						approxAssump.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
						approxAssump.setBounds(32, 38, 27,27);
					}
					{
						lrgAssump9 = new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
						nomByNomPanel.add(lrgAssump9);
						lrgAssump9.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
						lrgAssump9.setBounds(32, 95, 27,27);
					}
					{
						liklihood = new JCheckBox();
						nomByNomPanel.add(liklihood);
						liklihood.setText("Likelihood");
						liklihood.setBounds(17, 76, 109, 19);
					}
					{
						mantelHaen = new JCheckBox();
						nomByNomPanel.add(mantelHaen);
						mantelHaen.setText("Mantel Haenszel");
						mantelHaen.setBounds(17, 190, 172, 19);
					}
					{
						fishers = new JCheckBox();
						nomByNomPanel.add(fishers);
						fishers.setText("Fisher's Exact");
						fishers.setBounds(17, 131, 174, 19);
					}
					{
						chisqOptions = new IconButton("/icons/advanced_21.png","Chi-Squared Options",this,"Chi-Squared Options");
						nomByNomPanel.add(chisqOptions);
						chisqOptions.setBounds(149, 27, 27,27);
					}
					{
						chisq = new JCheckBox();
						nomByNomPanel.add(chisq);
						chisq.setText("Chi-Squared");
						chisq.setBounds(17, 19, 170, 19);
					}
					{
						fishersOptions = new JButton();
						nomByNomPanel.add(fishersOptions);
						fishersOptions.setBounds(149, 137, 27, 21);
						fishersOptions.addActionListener(this);
					}
					{
						mantelOptions = new JButton();
						nomByNomPanel.add(mantelOptions);
						mantelOptions.setBounds(149, 199, 27, 21);
						mantelOptions.addActionListener(this);
					}
					{
						liklihoodOptions =  new IconButton("/icons/advanced_21.png","Liklihood Ratio Options",
								this,"Liklihood Ratio Options");
						nomByNomPanel.add(liklihoodOptions);
						liklihoodOptions.setBounds(149, 82, 27,27);
					}
					{
						lrgAssump6 = new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
						nomByNomPanel.add(lrgAssump6);
						lrgAssump6.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
						lrgAssump6.setBounds(32, 94, 27,27);
					}
					{
						jSeparator1 = new JSeparator();
						nomByNomPanel.add(jSeparator1);
						jSeparator1.setPreferredSize(new java.awt.Dimension(79, 3));
						jSeparator1.setBounds(75, 122, 79, 3);
					}
					{
						lrgAssump5 = new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
						nomByNomPanel.add(lrgAssump5);
						lrgAssump5.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
						lrgAssump5.setBounds(32, 215, 27,27);
					}
					{
						homoAssump = new AssumptionIcon("/icons/homo_assump.png","Homogeneity Across Strata",null,"Homogeneity Across Strata");
						nomByNomPanel.add(homoAssump);
						homoAssump.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
						homoAssump.setBounds(63, 215, 27,27);
					}
				}
				{
					ordByOrdPanel = new JPanel();
					getContentPane().add(ordByOrdPanel);
					ordByOrdPanel.setLayout(null);
					ordByOrdPanel.setBounds(217, 12, 192, 136);
					ordByOrdPanel.setBorder(BorderFactory.createTitledBorder("Ordinal By Ordinal"));
					{
						kendall = new JCheckBox();
						ordByOrdPanel.add(kendall);
						kendall.setText("Kendall's Tau");
						kendall.setBounds(17, 20, 130, 19);
					}
					{
						lrgAssump4 = new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
						ordByOrdPanel.add(lrgAssump4);
						lrgAssump4.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
						lrgAssump4.setBounds(33, 39, 27,27);
					}
					{
						kendallOptions = new JButton();
						ordByOrdPanel.add(kendallOptions);
						kendallOptions.setText("Kendall Options");
						kendallOptions.setBounds(147, 27, 27, 21);
						kendallOptions.addActionListener(this);
					}
					{
						spearmans = new JCheckBox();
						ordByOrdPanel.add(spearmans);
						spearmans.setText("Spearman's Rho");
						spearmans.setBounds(17, 78, 131, 19);
					}
					{
						lrgAssump3 = new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
						ordByOrdPanel.add(lrgAssump3);
						lrgAssump3.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
						lrgAssump3.setBounds(33, 39, 27,27);
					}
					{
						jSeparator4 = new JSeparator();
						ordByOrdPanel.add(jSeparator4);
						jSeparator4.setBounds(70, 70, 77, 7);
					}
					{
						lrgAssump2 = new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
						ordByOrdPanel.add(lrgAssump2);
						lrgAssump2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
						lrgAssump2.setBounds(33, 39, 27,27);
					}
					{
						lrgAssump1 = new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
						ordByOrdPanel.add(lrgAssump1);
						lrgAssump1.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
						lrgAssump1.setBounds(33, 103, 27,27);
					}
					{
						spearmanOptions = new JButton();
						ordByOrdPanel.add(spearmanOptions);
						spearmanOptions.setBounds(148, 85, 27, 21);
						spearmanOptions.addActionListener(this);
					}
				}
				{
					nomByOrdPanel = new JPanel();
					getContentPane().add(nomByOrdPanel);
					nomByOrdPanel.setBounds(217, 160, 190, 99);
					nomByOrdPanel.setBorder(BorderFactory.createTitledBorder("Nominal By Ordinal"));
					nomByOrdPanel.setLayout(null);
					{
						kruskal = new JCheckBox();
						nomByOrdPanel.add(kruskal);
						kruskal.setText("Kruskal-Wallis");
						kruskal.setBounds(17, 20, 179, 19);
					}
					{
						lrgAssump = new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
						nomByOrdPanel.add(lrgAssump);
						lrgAssump.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
						lrgAssump.setBounds(32, 39, 27,27);
					}
					{
						exchAssump = new AssumptionIcon("/icons/eqvar_assump.png","Exchangability",null,"Exchangability");
						nomByOrdPanel.add(exchAssump);
						exchAssump.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
						exchAssump.setBounds(63, 39, 27,27);
					}
					{
						kruskalOptions = new JButton();
						nomByOrdPanel.add(kruskalOptions);
						kruskalOptions.setText("Kruskal-Wallis Options");
						kruskalOptions.setBounds(146, 32, 27, 21);
						kruskalOptions.addActionListener(this);
					}
				}
				{
					helpButton = new JButton();
					getContentPane().add(helpButton);
					helpButton.setText("Help");
					helpButton.setBounds(12, 293, 41, 32);
				}
				{
					custom = new JButton();
					getContentPane().add(custom);
					custom.setText("Custom");
					custom.setBounds(171, 265, 82, 22);
					custom.addActionListener(this);
				}
				this.setResizable(false);
				this.setSize(421, 361);
				this.setTitle("Table Statistics");
				chiSquared = new ChiOptions();
				lrTest = new LikeOptions();
				//Unimplemented options
				helpButton.setVisible(false);
				kruskalOptions.setVisible(false);
				spearmanOptions.setVisible(false);
				kendallOptions.setVisible(false);
				mantelOptions.setVisible(false);
				fishersOptions.setVisible(false);
				helpButton.setVisible(false);
				custom.setVisible(false);
			} catch (Exception e) {
				new ErrorMsg(e);
			}
		}

		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if(cmd == "Cancel"){
				this.dispose();
			}else if(cmd == "OK"){
				statOpt = this.getOptions();
				this.dispose();
			}else if(cmd == "Chi-Squared Options"){
				ChiOptionDialog chi = new ChiOptionDialog(this,chiSquared);
				chi.setLocationRelativeTo(this);
				chi.setVisible(true);
			}else if(cmd == "Liklihood Ratio Options"){
				LikeOptionDialog like = new LikeOptionDialog(this,lrTest);
				like.setLocationRelativeTo(this);
				like.setVisible(true);
			}
			
		}
		
		
		public class ChiOptionDialog extends JDialog implements ActionListener {
			protected JSeparator sep;
			protected JSeparator jSeparator3;
			protected JSeparator jSeparator2;
			protected OkayCancelPanel okcan;
			protected JLabel simSizeLabel;
			protected JTextField simSize;
			protected JSeparator jSeparator1;
			protected JCheckBox monteCarlo;
			protected JCheckBox asymptTest;
			protected JCheckBox conservative;

			
			public ChiOptionDialog(JDialog d,ChiOptions chi) {
				super(d);
				initGUI();
				setOptions(chi);
			}
			
			public void setOptions(ChiOptions chi){
				monteCarlo.setSelected(chi.mc);
				asymptTest.setSelected(chi.asy);
				conservative.setSelected(chi.conservative);
				simSize.setText((new Long(chi.b)).toString());			
			}
			
			public ChiOptions getOptions(){
				ChiOptions chi = new ChiOptions();
				long ss;
				try{
					ss =Long.parseLong(simSize.getText());
				}catch(Exception exp){
					ss=-1;
				}
				chi.b=ss;
				chi.mc=monteCarlo.isSelected();
				chi.asy=asymptTest.isSelected();
				chi.conservative=conservative.isSelected();	
				return chi;
				
			}
			
			protected void initGUI() {
				try {
					AnchorLayout thisLayout = new AnchorLayout();
					getContentPane().setLayout(thisLayout);
					{
						jSeparator3 = new JSeparator();
						getContentPane().add(jSeparator3, new AnchorConstraint(178, 237, 756, 205, AnchorConstraint.ANCHOR_REL, 
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						jSeparator3.setPreferredSize(new java.awt.Dimension(10, 151));
						jSeparator3.setOrientation(SwingConstants.VERTICAL);
					}
					{
						okcan = new OkayCancelPanel(false,false,this);
						getContentPane().add(okcan, new AnchorConstraint(825, 944, 975, 444, AnchorConstraint.ANCHOR_REL, 
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						
					}
					{
						simSizeLabel = new JLabel();
						getContentPane().add(simSizeLabel, new AnchorConstraint(545, 583, 603, 309, AnchorConstraint.ANCHOR_REL, 
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						simSizeLabel.setText("Sample Size: ");
						simSizeLabel.setPreferredSize(new java.awt.Dimension(87, 15));
					}
					{
						simSize = new JTextField();
						getContentPane().add(simSize, new AnchorConstraint(530, 825, 614, 602, AnchorConstraint.ANCHOR_REL, 
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						simSize.setText("5000");
						simSize.setPreferredSize(new java.awt.Dimension(71, 22));
					}
					{
						monteCarlo = new JCheckBox();
						getContentPane().add(monteCarlo, new AnchorConstraint(434, 825, 507, 256, AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						monteCarlo.setText("Monte Carlo Simulation");
						monteCarlo.setPreferredSize(new java.awt.Dimension(181, 19));
						monteCarlo.addActionListener(this);
					}
					{
						asymptTest = new JCheckBox();
						getContentPane().add(asymptTest, new AnchorConstraint(239, 800, 312, 256, AnchorConstraint.ANCHOR_REL,
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						asymptTest.setText("Asymptotic");
						asymptTest.setPreferredSize(new java.awt.Dimension(100, 19));
						asymptTest.addActionListener(this);
					}
					{
						conservative = new JCheckBox();
						getContentPane().add(conservative, new AnchorConstraint(82, 794, 155, 256, AnchorConstraint.ANCHOR_REL, 
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						conservative.setText("Conservative");
						conservative.setPreferredSize(new java.awt.Dimension(171, 19));
						conservative.addActionListener(this);
						conservative.setVisible(false);
					}
					{
						sep = new JSeparator();
						getContentPane().add(sep, new AnchorConstraint(178, 825, 216, 205, AnchorConstraint.ANCHOR_REL, 
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						sep.setPreferredSize(new java.awt.Dimension(197, 10));
					}
					{
						jSeparator1 = new JSeparator();
						getContentPane().add(jSeparator1, new AnchorConstraint(381, 693, 411, 290, AnchorConstraint.ANCHOR_REL, 
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						jSeparator1.setPreferredSize(new java.awt.Dimension(128, 8));
					}
					{
						jSeparator2 = new JSeparator();
						getContentPane().add(jSeparator2, new AnchorConstraint(660, 693, 706, 290, AnchorConstraint.ANCHOR_REL, 
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						jSeparator2.setPreferredSize(new java.awt.Dimension(128, 12));
					}
					this.setResizable(false);
					this.setSize(318, 283);
					this.setTitle("Chi Squared Test Options");
				} catch (Exception e) {
					new ErrorMsg(e);
				}
			}

			public void actionPerformed(ActionEvent e) {
				String cmd = e.getActionCommand();
				
				if(cmd == "Cancel"){
					this.dispose();
				}else if(cmd=="OK"){
					ChiOptions tmp = this.getOptions();
					if(tmp.b<1 && monteCarlo.isSelected()){
							JOptionPane.showMessageDialog(this, "Please enter a valid monte carlo sample size");
							simSize.setText("10000");
							return;
					}
					if(!tmp.isValid()){
						JOptionPane.showMessageDialog(this, "Please select the type of test you wish to perform." +
															"\n\t(Asymptotic and/or Monte Carlo)");
						return;
					}
					chiSquared = tmp;
					if(chiSquared.mc){
						lrgAssump7.setVisible(false);
						approxAssump.setVisible(true);
					}else{
						lrgAssump7.setVisible(true);
						approxAssump.setVisible(false);					
					}
					this.dispose();
				}
				
			}
			


		}
		

		
		public class LikeOptionDialog extends JDialog implements ActionListener{
			protected JCheckBox conservative;
			OkayCancelPanel okcan;
			
			public LikeOptionDialog(JDialog d,LikeOptions lrt) {
				super(d);
				initGUI();
				setOptions(lrt);
			}	
			
			public void setOptions(LikeOptions lrt){
				conservative.setSelected(lrt.conservative);
			}
			
			public LikeOptions getOptions(){
				LikeOptions lrt = new LikeOptions();
				lrt.conservative= conservative.isSelected();
				return lrt;
			}
			
			protected void initGUI() {
				try {
					this.setLayout(null);
					{
						conservative = new JCheckBox();
						getContentPane().add(conservative, BorderLayout.CENTER);
						conservative.setLayout(null);
						conservative.setText("Conservative");
						conservative.setBounds(100, 18, 145, 27);
					}
					{
						okcan = new OkayCancelPanel(false,false,this);
						getContentPane().add(okcan);
						okcan.setBounds(80, 65, 170, 40);
					}
					this.setTitle("Liklihood Ratio Options");
					this.setResizable(false);
					this.setSize(305, 134);
				} catch (Exception e) {
					new ErrorMsg(e);
				}
			}

			public void actionPerformed(ActionEvent arg0) {
				String cmd = arg0.getActionCommand();
				if(cmd == "OK"){
					lrTest = this.getOptions();
					this.dispose();
				}else if(cmd=="Cancel")
					this.dispose();
				
			}

		}
		
	}
	
	public class ResultsDialog extends JDialog implements ActionListener{
		protected JCheckBox keep;
		protected OkayCancelPanel okcan;
		protected JTextField resultName;
		protected JLabel name;
		
		public ResultsDialog(JDialog d,ResultsOptions opt) {
			super(d);
			initGUI();
			this.setOptions(opt);
		}
		
		public void setOptions(ResultsOptions opt){
			if(opt.name=="")
				resultName.setText("<auto>");
			else
				resultName.setText(opt.name);
			keep.setSelected(opt.keep);
		}
		
		public ResultsOptions getOptions(){
			ResultsOptions opt = new ResultsOptions();
			if(resultName.getText()!="<auto>")
				opt.name = resultName.getText();
			else
				opt.name="";
			opt.keep = keep.isSelected();
			return opt;
		}
		
		protected void initGUI() {
			try {
				{
					getContentPane().setLayout(null);
					{
						keep = new JCheckBox();
						getContentPane().add(keep);
						keep.setText("Keep results in workspace");
						keep.setBounds(49, 53, 216, 19);
					}
					{
						name = new JLabel();
						getContentPane().add(name);
						name.setText("Result name:");
						name.setBounds(54, 23, 86, 15);
					}
					{
						resultName = new JTextField();
						getContentPane().add(resultName);
						resultName.setText("<auto>");
						resultName.setBounds(140, 19, 98, 22);
					}
					{
						okcan = new OkayCancelPanel(false,false,this);
						getContentPane().add(okcan);
						okcan.setBounds(97, 95, 160, 40);
					}
				}
				this.setTitle("Result Options");
				this.setResizable(false);
				this.setSize(288, 165);
			} catch (Exception e) {
				new ErrorMsg(e);
			}
		}

		public void actionPerformed(ActionEvent arg0) {
			String cmd = arg0.getActionCommand();
			if(cmd =="Cancel"){
				this.dispose();
			}else if(cmd == "OK"){
				resultOpt = this.getOptions();
				this.dispose();
			}
			
		}
	}
	

	
}
