package org.rosuda.deducer.menu;
//import com.cloudgarden.layout.AnchorConstraint;
//import com.cloudgarden.layout.AnchorLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import org.rosuda.JGR.JGR;
import org.rosuda.JGR.RController;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.JGR.util.ErrorMsg;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.menu.twosample.TwoSampleModel;
import org.rosuda.deducer.toolkit.AddButton;
import org.rosuda.deducer.toolkit.AssumptionIcon;
import org.rosuda.deducer.toolkit.DJList;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;
import org.rosuda.deducer.toolkit.RemoveButton;
import org.rosuda.deducer.toolkit.SingletonAddRemoveButton;
import org.rosuda.deducer.toolkit.SingletonDJList;
import org.rosuda.deducer.toolkit.VariableSelector;



public class KSampleDialog extends javax.swing.JDialog implements ActionListener{
	private VariableSelector variableSelector;
	private SingletonDJList factor;
	private JPanel meanPanel;
	private JButton exchAssump;
	private JButton largeAssump2;
	private JSeparator sep;
	private JCheckBox kwTest;
	private JButton eqVarAssump;
	private JPanel okayCancelPanel;
	private JSeparator sep1;
	private JButton help;
	private JButton largeAssump3;
	private JCheckBox median;
	private JButton outliersAssump2;
	private JButton nOrNormAssump;
	private JCheckBox anova;
	private JButton outlierAssump;
	private JButton largeAssump;
	private JCheckBox welch;
	private JPanel medianPanel;
	private JButton plots;
	private JButton pairwise;
	private SubsetPanel subset;
	private JPanel subsetPanel;
	private SingletonAddRemoveButton addFactor;
	private JPanel factorPanel;
	private JButton remove;
	private JButton add;
	private DJList outcomes;
	private JScrollPane outcomeScroller;
	private JPanel outcomePanel;
	
	private KSampleModel model; 
	private static KSampleModel lastModel;


	
	public KSampleDialog(JFrame frame) {
		super(frame);
		initGUI();
		variableSelector.getJComboBox().addActionListener(this);
		median.setEnabled(false);
		largeAssump3.setEnabled(false);
		reset();
		if(lastModel!=null)
			setModel(lastModel);
	}
	
	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			{
				variableSelector = new VariableSelector();
				getContentPane().add(variableSelector, new AnchorConstraint(12, 435, 556, 4, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
				variableSelector.setPreferredSize(new java.awt.Dimension(234, 315));
			}
			{
				okayCancelPanel = new OkayCancelPanel(true,true,this);
				getContentPane().add(okayCancelPanel, new AnchorConstraint(910, 979, 980, 469, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				okayCancelPanel.setPreferredSize(new java.awt.Dimension(279, 42));
			}
			{
				help = new HelpButton("pmwiki.php?n=Main.K-SampleTest");
				getContentPane().add(help, new AnchorConstraint(943, 97, 970, 22, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				help.setPreferredSize(new java.awt.Dimension(32, 32));
			}
			{
				medianPanel = new JPanel();
				getContentPane().add(medianPanel, new AnchorConstraint(665, 12, 910, 528, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				medianPanel.setPreferredSize(new java.awt.Dimension(247, 144));
				medianPanel.setBorder(BorderFactory.createTitledBorder("Median"));
				medianPanel.setLayout(null);
				{
					kwTest = new JCheckBox();
					medianPanel.add(kwTest);
					kwTest.setText("Kruskal-Wallis");
					kwTest.setBounds(17, 27, 157, 19);
				}
				{
					largeAssump2 = new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
					medianPanel.add(largeAssump2);
					largeAssump2.setBounds(36, 46, 27, 27);
				}
				{
					exchAssump = new AssumptionIcon("/icons/eqvar_assump.png","Exchangablility",null,"Exchangablility");
					medianPanel.add(exchAssump);
					exchAssump.setBounds(63, 46, 27, 27);
				}
				{
					median = new JCheckBox();
					medianPanel.add(median);
					median.setText("Median Test");
					median.setBounds(17, 85, 157, 19);
				}
				{
					largeAssump3 =new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
					medianPanel.add(largeAssump3);
					largeAssump3.setBounds(36, 105, 27, 27);
				}
				{
					sep1 = new JSeparator();
					medianPanel.add(sep1);
					sep1.setBounds(51, 79, 118, 5);
				}
			}
			{
				meanPanel = new JPanel();
				getContentPane().add(meanPanel, new AnchorConstraint(665, 469, 910, 22, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				meanPanel.setPreferredSize(new java.awt.Dimension(245, 144));
				meanPanel.setBorder(BorderFactory.createTitledBorder("Mean"));
				meanPanel.setLayout(null);
				{
					welch = new JCheckBox();
					meanPanel.add(welch);
					welch.setText("One-Way ANOVA (Welch) ");
					welch.setBounds(17, 27, 211, 19);
				}
				{
					largeAssump = new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
					meanPanel.add(largeAssump);
					largeAssump.setBounds(34, 46, 27, 27);
				}
				{
					outlierAssump = new AssumptionIcon("/icons/outlier_assump.png","No Outliers",this,"No Outliers");
					meanPanel.add(outlierAssump);
					outlierAssump.setBounds(61, 46, 27, 27);
				}
				{
					anova = new JCheckBox();
					meanPanel.add(anova);
					anova.setText("One-Way ANOVA");
					anova.setBounds(17, 85, 211, 19);
				}
				{
					nOrNormAssump = new AssumptionIcon("/icons/N_or_norm_assump.png","Large Sample or Normal",this,"Large Sample or Normal");
					meanPanel.add(nOrNormAssump);
					nOrNormAssump.setBounds(34, 105, 47, 27);
				}
				{
					outliersAssump2 = new AssumptionIcon("/icons/outlier_assump.png","No Outliers",this,"No Outliers");
					meanPanel.add(outliersAssump2);
					outliersAssump2.setBounds(81, 105, 27, 27);
				}
				{
					eqVarAssump = new AssumptionIcon("/icons/eqvar_assump.png","Equal Variances",null,"Equal Variances");
					meanPanel.add(eqVarAssump);
					eqVarAssump.setBounds(106, 105, 27, 27);
				}
				{
					sep = new JSeparator();
					meanPanel.add(sep);
					sep.setBounds(53, 79, 118, 5);
				}
			}
			{
				plots = new JButton();
				getContentPane().add(plots, new AnchorConstraint(565, 303, 737, 4, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
				plots.setText("Plots");
				plots.addActionListener(this);
				plots.setPreferredSize(new java.awt.Dimension(84, 22));
				
				pairwise = new JButton();
				getContentPane().add(pairwise, new AnchorConstraint(565, 425, 737, 110, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
				pairwise.setText("Pairwise");
				pairwise.addActionListener(this);
				pairwise.setPreferredSize(new java.awt.Dimension(84, 22));
			}
			{
				subsetPanel = new JPanel();
				BorderLayout subsetPanelLayout = new BorderLayout();
				subsetPanel.setLayout(subsetPanelLayout);
				getContentPane().add(subsetPanel, new AnchorConstraint(480, 979, 638, 570, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				subsetPanel.setPreferredSize(new java.awt.Dimension(224, 93));
				subsetPanel.setBorder(BorderFactory.createTitledBorder("Subset"));
				{
					subset = new SubsetPanel(variableSelector.getJComboBox());
					subsetPanel.add(subset, BorderLayout.CENTER);
				}
			}

			{
				factorPanel = new JPanel();
				BorderLayout factorPanelLayout = new BorderLayout();
				factorPanel.setLayout(factorPanelLayout);
				getContentPane().add(factorPanel, new AnchorConstraint(375, 979, 471, 570, AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				factorPanel.setPreferredSize(new java.awt.Dimension(224, 57));
				factorPanel.setBorder(BorderFactory.createTitledBorder("Factor"));
				{
					
					factor = new SingletonDJList();
					factorPanel.add(factor, BorderLayout.CENTER);
					factor.setModel(new DefaultListModel());
				}
			}
			{
				addFactor = new SingletonAddRemoveButton(new String[]{"Add Factor","Remove Factor"},
						new String[]{"Add Factor","Remove Factor"},factor,variableSelector);
				getContentPane().add(addFactor, new AnchorConstraint(400, 586, 529, 479, AnchorConstraint.ANCHOR_REL,
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				addFactor.setPreferredSize(new java.awt.Dimension(34, 34));
			}
			{
				outcomePanel = new JPanel();
				BorderLayout outcomePanelLayout = new BorderLayout();
				outcomePanel.setLayout(outcomePanelLayout);
				getContentPane().add(outcomePanel, new AnchorConstraint(12, 13, 375, 570, AnchorConstraint.ANCHOR_ABS, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				outcomePanel.setPreferredSize(new java.awt.Dimension(223, 208));
				outcomePanel.setBorder(BorderFactory.createTitledBorder("Outcomes"));
				{
					outcomeScroller = new JScrollPane();
					outcomePanel.add(outcomeScroller, BorderLayout.CENTER);
					{
						outcomes = new DJList();
						outcomeScroller.setViewportView(outcomes);
						outcomes.setModel(new DefaultListModel());
					}
				}
			}
			{
				remove = new RemoveButton("Remove",variableSelector,outcomes);
				getContentPane().add(remove, new AnchorConstraint(199, 570, 304, 479, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				remove.setPreferredSize(new java.awt.Dimension(34, 34));
			}
			{
				add = new AddButton("Add",variableSelector,outcomes);
				getContentPane().add(add, new AnchorConstraint(142, 537, 192, 479, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				add.setPreferredSize(new java.awt.Dimension(34, 34));
			}		
			this.setTitle("Multiple Independent Samples");
			this.setMinimumSize(new Dimension(359,300));
			this.setSize(548, 610);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	public void reset(){
		setModel(new KSampleModel());
	}
	
	public void setModel(KSampleModel mod){
		boolean allExist;

		factor.setModel(new DefaultListModel());
		addFactor.refreshListListener();
		outcomes.setModel(new DefaultListModel());
		variableSelector.reset();
		if(mod.dataName!=null){
			variableSelector.setSelectedData(mod.dataName);
			allExist=variableSelector.removeAll(mod.variables);
			if(allExist)
				outcomes.setModel(mod.variables);
			else{
				reset();
				return;
			}
			allExist=variableSelector.removeAll(mod.factorName);
			if(allExist){
				factor.setModel(mod.factorName);
				addFactor.refreshListListener();
			}else{
				reset();
				addFactor.refreshListListener();
				return;
			}
			if(mod.subset=="" || RController.isValidSubsetExp(mod.subset,mod.dataName)){
				subset.setText(mod.subset);
			}
		}
		model=mod;		
		welch.setSelected(model.doWelch);
		anova.setSelected(model.doAnova);
		kwTest.setSelected(model.doKW);
		median.setSelected(model.doMedian);
		
	}
	public void setDataName(String dataName){
		if(!dataName.equals(variableSelector.getSelectedData())){
			variableSelector.setSelectedData(dataName);
		}
	}
	public void actionPerformed(ActionEvent ae) {
		String cmd = ae.getActionCommand();
		
		if(cmd=="Cancel"){
			this.dispose();
		}else if(cmd == "Reset"){
			this.setModel(new KSampleModel());
		}else if(cmd == "Run"){
			model.doWelch=welch.isSelected();
			model.doAnova=anova.isSelected();
			model.doKW=kwTest.isSelected();
			model.doMedian=median.isSelected();	
			model.subset=subset.getText();
			model.variables=(DefaultListModel) outcomes.getModel();
			model.factorName=(DefaultListModel) factor.getModel();
			model.dataName=variableSelector.getSelectedData();
			boolean valid = model.run();
			if(valid){
				lastModel=model;
				SubsetDialog.addToHistory(model.dataName, model.subset);
				Deducer.setRecentData(model.dataName);
				this.dispose();
			}
		}else if(cmd=="Plots"){
			KSamplePlots plt= new KSamplePlots(this,model.plots);
			plt.setLocationRelativeTo(this);
			plt.setVisible(true);
		}else if(cmd == "comboBoxChanged"){
			setModel(new KSampleModel());
		}else if(cmd == "Pairwise"){
			PairwiseSubDialog sd = new PairwiseSubDialog(KSampleDialog.this);
			sd.setLocationRelativeTo(pairwise);
			sd.setVisible(true);
		}
		
	}
	
	class KSampleModel{
		public boolean doWelch=true;
		public boolean doAnova=false;
		public boolean doKW=false;
		public boolean doMedian=false;
		public DefaultListModel variables = new DefaultListModel();
		public DefaultListModel factorName = new DefaultListModel();
		public String subset="";
		public String dataName="";
		public OneWayPlotModel plots= new OneWayPlotModel();
		public String pairwiseMethod = "";
		public String pairwiseCorrection = "none";
		
		public boolean run(){
			if(dataName==null)
				return false;
			if(variables.size()==0){
				JOptionPane.showMessageDialog(null, "Please select one or more outcome variables.");
				return false;
			}
			if(factorName.size()==0){
				JOptionPane.showMessageDialog(null, "Please select a factor.");
				return false;			
			}
			subset = subset.trim();
			String cmd="";
			String subn;
			String outcomes = Deducer.makeRCollection(variables, "d", false);
			String factor = (String) factorName.get(0);
			if(dataName=="")
				return false;
			boolean isSubset=false;
			if(!subset.equals("") ){
				if(!SubsetDialog.isValidSubsetExp(subset,dataName)){
					JOptionPane.showMessageDialog(null, "Sorry, the subset expression seems to be invalid.");
					return false;
				}
				subn = Deducer.getUniqueName(dataName+".sub");
				cmd=subn+"<-subset("+dataName+","+subset+")"+"\n";
				isSubset=true;
			}else
				subn=dataName;
			
			if(doWelch){
				cmd += "k.sample.test(formula="+outcomes+" ~ "+factor+",\n\t\tdata="+subn+
					",\n\ttest=oneway.test)"+"\n";
			}
			if(doAnova){
				cmd += "k.sample.test(formula="+outcomes+" ~ "+factor+",\n\t\tdata="+subn+
					",\n\ttest=oneway.test,var.equal=TRUE)"+"\n";
			}
			if(doKW){
				cmd += "k.sample.test(formula="+outcomes+" ~ "+factor+",\n\t\tdata="+subn+
					",\n\ttest=kruskal.test)"+"\n";
			}
			
			if(pairwiseMethod == "t-test (welch)"){
				for(int i=0;i<variables.size();i++)
					cmd += "pairwise.t.test(" +subn + "$" + variables.get(i).toString() + "," + 
										subn + "$" + factor + 
										", p.adjust.method = '" + model.pairwiseCorrection +"')\n";
			}else if(pairwiseMethod == "t-test (equal variance)"){
				for(int i=0;i<variables.size();i++)
					cmd += "pairwise.t.test(" +subn + "$" + variables.get(i).toString() + "," + 
										subn + "$" + factor + ", pool.sd = TRUE" +
										", p.adjust.method = '" + model.pairwiseCorrection +"')\n";
			}else if(pairwiseMethod == "wilcoxon"){
				for(int i=0;i<variables.size();i++)
					cmd += "pairwise.wilcox.test(" +subn + "$" + variables.get(i).toString() + "," + 
										subn + "$" + factor + 
										", p.adjust.method = '" + model.pairwiseCorrection +"')\n";
			}
			
			cmd+=model.plots.getCmd(subn, outcomes, factor);
			if(isSubset)
				cmd+="rm("+subn+")\n";
			Deducer.execute(cmd);
			return true;
		}
	}

	class PairwiseSubDialog extends javax.swing.JDialog {
		private JComboBox correction;
		private JLabel label1;
		private OkayCancelPanel okayCancel;
		private JComboBox method;
		private JLabel label2;
		
		public PairwiseSubDialog(javax.swing.JDialog frame) {
			super(frame);
			initGUI();
			method.setSelectedItem(model.pairwiseMethod);
			correction.setSelectedItem(model.pairwiseCorrection);
		}
		
		private void initGUI() {
			try {
				{
					getContentPane().setLayout(null);
					{
						ComboBoxModel correctionModel = 
							new DefaultComboBoxModel(
									new String[] { "holm"   ,    "hochberg" ,  "hommel"  ,   "bonferroni", "BH" ,
											"BY"   ,      "fdr"      ,  "none"      });
						correction = new JComboBox();
						getContentPane().add(correction);
						correction.setModel(correctionModel);
						correction.setBounds(113, 66, 113, 22);
					}
					{
						label1 = new JLabel();
						getContentPane().add(label1);
						label1.setText("Correction:");
						label1.setBounds(12, 70, 89, 15);
						label1.setHorizontalAlignment(SwingConstants.TRAILING);
					}
					{
						label2 = new JLabel();
						getContentPane().add(label2);
						label2.setText("Method:");
						label2.setBounds(24, 32, 77, 15);
						label2.setHorizontalAlignment(SwingConstants.TRAILING);
					}
					{
						ComboBoxModel methodModel = 
							new DefaultComboBoxModel(
									new String[] { "","t-test (welch)","t-test (equal variance)","wilcoxon" });
						method = new JComboBox();
						getContentPane().add(method);
						method.setModel(methodModel);
						method.setBounds(113, 28, 113, 22);
					}
					{
						ActionListener al = new ActionListener(){

							public void actionPerformed(ActionEvent arg0) {
								if(arg0.getActionCommand() == "OK"){
									model.pairwiseMethod = (String) method.getSelectedItem();
									model.pairwiseCorrection = (String) correction.getSelectedItem();
									
								}
								PairwiseSubDialog.this.dispose();
							}
							
						};
						okayCancel = new OkayCancelPanel(false,false,al);
						getContentPane().add(okayCancel);
						okayCancel.setBounds(64, 100, 162, 34);
					}
				}
				this.setModal(true);
				this.setSize(249, 168);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}



