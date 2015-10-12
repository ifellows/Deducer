package org.rosuda.deducer.menu.twosample;


import org.rosuda.JGR.RController;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.menu.KSamplePlots;
import org.rosuda.deducer.menu.SubsetDialog;
import org.rosuda.deducer.menu.SubsetPanel;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;


public class TwoSampleDialog extends javax.swing.JDialog implements ActionListener {
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JPanel outcomePanel;
	private IconButton lrgAssump;
	private IconButton lrgAssump2;
	private JCheckBox boot;
	private JSeparator sep1;
	private IconButton ttestOptions;
	private IconButton lrgAssump3;
	private JCheckBox bmTest;
	private JSeparator sep2;
	private IconButton exchAssump;
	private IconButton jButton1;
	private IconButton mwOptions;
	private SingletonAddRemoveButton addFactor;
	private IconButton removeOutcome;
	private IconButton addOutcome;
	private JButton help;
	private JPanel buttonPanel;
	private JButton plots;
	private JLabel kmLabel;
	private JButton kmOptions;
	private IconButton lrgSample4;
	private JCheckBox kmTest;
	private JPanel distributionPanel;
	private JButton bmOptions;
	private JCheckBox mannWhitney;
	private JPanel aucPanel;
	private JPanel subset;
	private SubsetPanel subsetPanel;
	private IconButton bootOptions;
	private JCheckBox ttest;
	private SingletonDJList factor;
	private JList outcomes;
	private JScrollPane varScroller;
	private JButton Options;
	private JButton other;
	private IconButton outlierAssump1;
	private IconButton outlierAssum;
	private JPanel meanPanel;
	private JButton split;
	private JPanel factorPanel;
	private VariableSelector variableSelector;
	
	private TwoSampleModel testModel = new TwoSampleModel();
	private IconButton exchAssump2;
	private IconButton approxAssump;
	
	private static TwoSampleModel lastModel;

	
	public TwoSampleDialog(JFrame frame) {
		super(frame);
		initGUI();
		
		if(lastModel!=null)
			setModel(lastModel);
		else
			setModel(new TwoSampleModel());
		bmOptions.setVisible(false);
		mwOptions.setVisible(false);
		kmOptions.setVisible(false);
		other.setVisible(false);
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			getContentPane().setLayout(thisLayout);
			{
				bottomPanel = new JPanel();
				getContentPane().add(bottomPanel, BorderLayout.SOUTH);
				AnchorLayout bottomPanelLayout = new AnchorLayout();
				bottomPanel.setLayout(bottomPanelLayout);
				bottomPanel.setPreferredSize(new java.awt.Dimension(547, 231));
				{
					Options = new JButton();
					bottomPanel.add(Options, new AnchorConstraint(117, 910, 603, 756, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
					Options.setText("Options");
					Options.setPreferredSize(new java.awt.Dimension(94, 22));
					Options.addActionListener(this);
				}
				{
					other = new JButton();
					bottomPanel.add(other, new AnchorConstraint(145, 794, 651, 756, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
					other.setText("Other Tests");
					other.setPreferredSize(new java.awt.Dimension(94, 22));
				}
				{
					help = new HelpButton("pmwiki.php?n=Main.TwoSampleTest");
					bottomPanel.add(help, new AnchorConstraint(820, 83, 900, 14, 
							AnchorConstraint.ANCHOR_NONE,AnchorConstraint.ANCHOR_NONE, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
					help.setPreferredSize(new java.awt.Dimension(32, 32));
				}
				{
					buttonPanel = new OkayCancelPanel(true,true,this);
					bottomPanel.add(buttonPanel, new AnchorConstraint(172, 12, 950, 578, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					buttonPanel.setPreferredSize(new java.awt.Dimension(300, 59));
				}
				{
					distributionPanel = new JPanel();
					bottomPanel.add(distributionPanel, new AnchorConstraint(12, 981, 482, 666, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
					distributionPanel.setPreferredSize(new java.awt.Dimension(192, 99));
					distributionPanel.setBorder(BorderFactory.createTitledBorder("Distribution"));
					distributionPanel.setLayout(null);
					{
						kmTest = new JCheckBox();
						distributionPanel.add(kmTest);
						kmTest.setText("Kolmogorov-");
						kmTest.setBounds(17, 19, 132, 18);
					}
					{
						lrgSample4 = new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
						distributionPanel.add(lrgSample4);
						lrgSample4.setBounds(38, 60, 27, 27);
					}
					{
						kmOptions = new JButton();
						distributionPanel.add(kmOptions);
						kmOptions.setText("Kolmogorov-Smirnov Options");
						kmOptions.setBounds(149, 27, 27, 27);
						kmOptions.addActionListener(this);
					}
					{
						kmLabel = new JLabel();
						distributionPanel.add(kmLabel);
						kmLabel.setText("Smirnov");
						kmLabel.setBounds(50, 40, 58, 14);
					}
				}
				{
					aucPanel = new JPanel();
					bottomPanel.add(aucPanel, new AnchorConstraint(12, 666, 751, 318, AnchorConstraint.ANCHOR_ABS, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
					aucPanel.setPreferredSize(new java.awt.Dimension(212, 160));
					aucPanel.setBorder(BorderFactory.createTitledBorder("Central Tendency (AUC)"));
					aucPanel.setLayout(null);
					{
						mannWhitney = new JCheckBox();
						aucPanel.add(mannWhitney);
						mannWhitney.setText("Wilcoxon");
						mannWhitney.setBounds(17, 19, 151, 18);
					}
					{
						mwOptions = new IconButton("/icons/advanced_21.png","Wilcoxon Options",this,"Wilcoxon Options");
						aucPanel.add(mwOptions);
						mwOptions.setText("Mann-Whitney Options");
						mwOptions.setBounds(168, 15, 27, 27);
					}
					{
						jButton1 = new IconButton("/icons/N_assump.png","Large Sample",null,"Large Sample");
						aucPanel.add(jButton1);
						jButton1.setBounds(35, 40, 27, 27);
					}
					{
						exchAssump = new AssumptionIcon("/icons/eqvar_assump.png","Exchangablility",null,"Exchangablility");
						aucPanel.add(exchAssump);
						exchAssump.setBounds(62, 40, 27, 27);
					}
					{
						sep2 = new JSeparator();
						aucPanel.add(sep2);
						sep2.setBounds(35, 78, 128, 10);
					}
					{
						bmTest = new JCheckBox();
						aucPanel.add(bmTest);
						bmTest.setText("Brunner-Munzel");
						bmTest.setBounds(17, 88, 151, 18);
					}
					{
						lrgAssump3 = new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
						aucPanel.add(lrgAssump3);
						lrgAssump3.setBounds(35, 109, 27, 27);
					}
					{
						bmOptions = new JButton();
						aucPanel.add(bmOptions);
						bmOptions.setText("Brunner-Munzel Options");
						bmOptions.setBounds(168, 84, 27, 27);
						bmOptions.addActionListener(this);
					}
				}
				{
					meanPanel = new JPanel();
					bottomPanel.add(meanPanel, new AnchorConstraint(12, 318, 569, 23, AnchorConstraint.ANCHOR_ABS, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
					meanPanel.setPreferredSize(new java.awt.Dimension(180, 160));
					meanPanel.setLayout(null);
					meanPanel.setBorder(BorderFactory.createTitledBorder(null, "Mean", TitledBorder.LEADING, 
							TitledBorder.TOP));
					{
						ttest = new JCheckBox();
						meanPanel.add(ttest);
						ttest.setText("T-Test");
						ttest.setBounds(17, 19, 85, 18);
					}
					{
						lrgAssump = new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
						meanPanel.add(lrgAssump);
						lrgAssump.setBounds(34, 40, 27, 27);
					}
					{
						ttestOptions = new IconButton("/icons/advanced_21.png","t-test options",this,"t-test options");
						meanPanel.add(ttestOptions);
						ttestOptions.setBounds(140, 15, 27, 27);
					}
					{
						exchAssump2 = new AssumptionIcon("/icons/eqvar_assump.png","Equal Variance",null,"Equal Variance");
						meanPanel.add(exchAssump2);
						exchAssump2.setBounds(108, 40, 27, 27);
					}
					{
						sep1 = new JSeparator();
						meanPanel.add(sep1);
						sep1.setBounds(34, 83, 129, 10);
					}
					{
						boot = new JCheckBox();
						meanPanel.add(boot);
						boot.setText("Permutation");
						boot.setBounds(17, 91, 111, 18);
					}
					{
						lrgAssump2 = new AssumptionIcon("/icons/N_or_exch_assump.png","Large Sample or exchangable",null,"Large Sample or exchangable");
						meanPanel.add(lrgAssump2);
						lrgAssump2.setBounds(34, 112, 47, 27);
					}
					{
						bootOptions = new IconButton("/icons/advanced_21.png","Permutation Options",this,"Permutation Options");
						meanPanel.add(bootOptions);
						bootOptions.setBounds(140, 89, 27, 27);
					}
					{
						outlierAssum = new AssumptionIcon("/icons/outlier_assump.png","No Outliers",this,"No Outliers");
						meanPanel.add(outlierAssum);
						outlierAssum.setBounds(81, 40, 27, 27);
					}
					{
						outlierAssump1 =  new AssumptionIcon("/icons/outlier_assump.png","No Outliers",this,"No Outliers");
						meanPanel.add(outlierAssump1);
						outlierAssump1.setBounds(81, 112, 27, 27);
					}
					{
						approxAssump =  new AssumptionIcon("/icons/mcapprox_assump.png","Monte Carlo Approximation",
								this,"Monte Carlo Approximation");
						meanPanel.add(approxAssump);
						approxAssump.setBounds(108, 112, 27, 27);
					}
				}
			}
			{
				topPanel = new JPanel();
				getContentPane().add(topPanel, BorderLayout.CENTER);
				AnchorLayout topPanelLayout = new AnchorLayout();
				topPanel.setLayout(topPanelLayout);
				topPanel.setPreferredSize(new java.awt.Dimension(547, 304));
				{
					removeOutcome = new IconButton("/icons/1leftarrow_32.png","Remove",this,"Remove");
					topPanel.add(removeOutcome, new AnchorConstraint(295, 535, 378, 484, AnchorConstraint.ANCHOR_NONE, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE));
					removeOutcome.setPreferredSize(new java.awt.Dimension(35, 35));
				}
				{
					addOutcome =  new IconButton("/icons/1rightarrow_32.png","Add",this,"Add");
					topPanel.add(addOutcome, new AnchorConstraint(161, 535, 264, 474, AnchorConstraint.ANCHOR_NONE, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE));
					addOutcome.setPreferredSize(new java.awt.Dimension(35, 35));
				}
				{
					split = new JButton();
					topPanel.add(split, new AnchorConstraint(751, 861, 810, 709, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
					split.setText("Split");
					split.setPreferredSize(new java.awt.Dimension(83, 21));
					split.addActionListener(this);
				}
				{
					factorPanel = new JPanel();
					BorderLayout factorPanelLayout = new BorderLayout();
					factorPanel.setLayout(factorPanelLayout);
					topPanel.add(factorPanel, new AnchorConstraint(620, 979, 750, 582, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					factorPanel.setPreferredSize(new java.awt.Dimension(242, 51));
					factorPanel.setBorder(BorderFactory.createTitledBorder("Factor"));
					{
						ListModel factorModel = new DefaultListModel();
						factor = new SingletonDJList();
						factorPanel.add(factor, BorderLayout.CENTER);
						factor.setModel(factorModel);
					}
				}
				{
					addFactor =  new SingletonAddRemoveButton(new String[]{"Add Factor","Remove Factor"},this,
							new String[]{"Add Factor","Remove Factor"},factor);
					topPanel.add(addFactor, new AnchorConstraint(628, 539, 750, 464, AnchorConstraint.ANCHOR_NONE, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE));
					addFactor.setPreferredSize(new java.awt.Dimension(37, 37));
				}
				{
					outcomePanel = new JPanel();
					BorderLayout outcomePanelLayout = new BorderLayout();
					topPanel.add(outcomePanel, new AnchorConstraint(32, 980, 612, 584, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					outcomePanel.setPreferredSize(new java.awt.Dimension(216, 203));
					outcomePanel.setLayout(outcomePanelLayout);
					outcomePanel.setBorder(BorderFactory.createTitledBorder("Outcomes"));
					{
						varScroller = new JScrollPane();
						outcomePanel.add(varScroller, BorderLayout.CENTER);
						{
							ListModel outcomesModel = new DefaultListModel();
							outcomes = new DJList();
							varScroller.setViewportView(outcomes);
							outcomes.setModel(outcomesModel);
						}
					}
				}
				{
					variableSelector = new VariableSelector();
					topPanel.add(variableSelector, new AnchorConstraint(31, 430, 827, 23, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					variableSelector.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
					variableSelector.setPreferredSize(new java.awt.Dimension(248, 288));
				}
				{
					subset = new JPanel();
					subset.setLayout(new BorderLayout());
					subsetPanel = new SubsetPanel(variableSelector.getJComboBox());
					subset.add(subsetPanel);
					topPanel.add(subset, new AnchorConstraint(800, 979, 1003, 582, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					subset.setBorder(BorderFactory.createTitledBorder("Subset"));
				}
				{
					plots = new JButton();
					topPanel.add(plots, new AnchorConstraint(893, 297, 968, 156, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					plots.setText("Plots");
					plots.addActionListener(this);
					plots.setPreferredSize(new java.awt.Dimension(86, 27));
				}
			}
			variableSelector.getJComboBox().addActionListener(this);
			this.setTitle("Two Independent Sample Tests");
			this.setMinimumSize(new Dimension(600,400));
			this.setSize(610, 630);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	
	public void setModel(TwoSampleModel mod){
		boolean allExist;
		testModel=mod;
		factor.setModel(new DefaultListModel());
		addFactor.refreshListListener();
		outcomes.setModel(new DefaultListModel());
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
				subsetPanel.setText(mod.subset);
			}
		}
		if(mod.subset=="")
			subsetPanel.setText(mod.subset);
		ttest.setSelected(mod.doT);
		boot.setSelected(mod.doBoot);
		kmTest.setSelected(mod.doKS);
		mannWhitney.setSelected(mod.doMW);
		bmTest.setSelected(mod.doBM);
		testModel=mod;
		if(testModel.bootStat=="t"){
			lrgAssump2.setIcon("/icons/N_or_exch_assump.png");
			lrgAssump2.setToolTipText("Large sample or Exchangable");
			lrgAssump2.setSize(47, 27);
		}else{
			lrgAssump2.setIcon("/icons/eqvar_assump.png");
			lrgAssump2.setToolTipText("Exchangable");
			lrgAssump2.setSize(27, 27);
		}
		if(testModel.tEqVar==false){
			exchAssump2.setVisible(false);
			lrgAssump.setIcon("/icons/N_assump.png");
			lrgAssump.setToolTipText("Large Sample");
			lrgAssump.setSize(27, 27);
		}else{
			exchAssump2.setVisible(true);
			lrgAssump.setIcon("/icons/N_or_norm_assump.png");
			lrgAssump.setToolTipText("Large sample or Normal");
			lrgAssump.setSize(47, 27);
		}
	}
	public void setDataName(String dataName){
		if(!dataName.equals(variableSelector.getSelectedData())){
			variableSelector.setSelectedData(dataName);
		}
	}
	public void reset(){
		
		setModel(new TwoSampleModel());
	}
	
	

	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		
		if(cmd=="Split"){
			SplitDialog split = new SplitDialog(this,testModel.splitMod,variableSelector.getSelectedData(),
					(String)(factor.getModel().getSize()==0?null:factor.getModel().getElementAt(0)));
			split.setLocationRelativeTo(this);
			split.setVisible(true);
		}else if(cmd=="Options"){
			TestOptions opt = new TestOptions(this,testModel.optMod);
			opt.setLocationRelativeTo(this);
			opt.setVisible(true);
		}else if(cmd=="Run"){
			testModel.doT=ttest.isSelected();
			testModel.doBoot=boot.isSelected();
			testModel.doKS=kmTest.isSelected();
			testModel.doMW=mannWhitney.isSelected();
			testModel.doBM=bmTest.isSelected();	
			testModel.factorName=(DefaultListModel)factor.getModel();
			testModel.variables=(DefaultListModel)outcomes.getModel();
			testModel.subset=subsetPanel.getText();
			testModel.dataName=variableSelector.getSelectedData();
			boolean valid = testModel.run();
			if(valid){
				lastModel=testModel;
				SubsetDialog.addToHistory(testModel.dataName, testModel.subset);
				Deducer.setRecentData(testModel.dataName);
				this.dispose();
			}
		}else if(cmd=="Cancel"){
			this.dispose();
		}else if(cmd=="Reset"){
			reset();
		}else if(cmd == "t-test options"){
			String ttestType=(String)JOptionPane.showInputDialog(this, "t-test Variant", "t-test Options", 
									JOptionPane.INFORMATION_MESSAGE, null, 
									new String[]{"Unequal variance (Welch) (Recommended)","Equal variance (Student)"},
									testModel.tEqVar ?  "Equal variance (Student)":"Unequal variance (Welch) (Recommended)");
			if(ttestType==null)
				return;
			if(ttestType.startsWith("Unequal")){
				exchAssump2.setVisible(false);
				lrgAssump.setIcon("/icons/N_assump.png");
				lrgAssump.setToolTipText("Large Sample");
				lrgAssump.setSize(27, 27);
				testModel.tEqVar=false;
			}else{
				exchAssump2.setVisible(true);
				lrgAssump.setIcon("/icons/N_or_norm_assump.png");
				lrgAssump.setToolTipText("Large sample or Normal");
				lrgAssump.setSize(47, 27);
				testModel.tEqVar=true;
			}
		}else if(cmd=="Permutation Options"){
			String permType=(String)JOptionPane.showInputDialog(this, "Permutation Statistic", "Permutation Options", 
					JOptionPane.INFORMATION_MESSAGE, null, 
					new String[]{"t (Recommended)","mean"},
					testModel.bootStat=="t" ?  "t (Recommended)":"mean");		
			if(permType==null)
				return;
			if(permType.startsWith("t")){
				testModel.bootStat="t";
				lrgAssump2.setIcon("/icons/N_or_exch_assump.png");
				lrgAssump2.setToolTipText("Large sample or Exchangable");
				lrgAssump2.setSize(47, 27);
			}else{
				testModel.bootStat="mean";
				lrgAssump2.setIcon("/icons/eqvar_assump.png");
				lrgAssump2.setToolTipText("Exchangable");
				lrgAssump2.setSize(27, 27);
			}
		}else if(cmd=="Add"){
			Object[] objs=variableSelector.getJList().getSelectedValues();
			for(int i=0;i<objs.length;i++){
				variableSelector.remove(objs[i]);
				((DefaultListModel)outcomes.getModel()).addElement(objs[i]);
			}
		}else if(cmd=="Remove"){
			Object[] objs=outcomes.getSelectedValues();
			for(int i=0;i<objs.length;i++){
				variableSelector.add(objs[i]);
				((DefaultListModel)outcomes.getModel()).removeElement(objs[i]);
			}
		}else if(cmd=="Add Factor"){
			Object[] objs=variableSelector.getJList().getSelectedValues();
			if(objs.length>1){
				variableSelector.getJList().setSelectedIndex(variableSelector.getJList().getSelectedIndex());
			}else if(objs.length==1 && factor.getModel().getSize()==0){
					variableSelector.remove(objs[0]);
					((DefaultListModel)factor.getModel()).addElement(objs[0]);
			}
		}else if(cmd=="Remove Factor"){
			DefaultListModel tmpModel =(DefaultListModel)factor.getModel();
			if(tmpModel.getSize()>0){
				variableSelector.add(tmpModel.remove(0));	
			}
		}else if(cmd == "comboBoxChanged"){
			setModel(new TwoSampleModel());
		}else if(cmd=="Plots"){
			KSamplePlots plt= new KSamplePlots(this,testModel.plots);
			plt.setLocationRelativeTo(this);
			plt.setVisible(true);
		}
	}
}

