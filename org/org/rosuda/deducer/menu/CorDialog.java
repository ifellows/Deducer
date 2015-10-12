package org.rosuda.deducer.menu;

import org.rosuda.JGR.RController;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.JGR.util.ErrorMsg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListModel;

import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.AddButton;
import org.rosuda.deducer.toolkit.AssumptionIcon;
import org.rosuda.deducer.toolkit.DJList;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;
import org.rosuda.deducer.toolkit.RemoveButton;
import org.rosuda.deducer.toolkit.VariableSelector;

public class CorDialog extends JDialog implements ActionListener{
	private VariableSelector variableSelector;
	private JButton help;
	private JButton plots;
	private JButton options;
	private JPanel typePanel;
	private SubsetPanel subset;
	private JPanel subsetPanel;
	private DJList with;
	private JScrollPane withScroller;
	private JButton outlierAssump;
	private JButton removeWith;
	private JButton addWith;
	private JButton removeVar;
	private JButton addVar;
	private JButton linearAssump;
	private JButton largeSampeAssump2;
	private JButton monoAssump;
	private JButton monoAssump1;
	private JRadioButton spearman;
	private JSeparator sep1;
	private JButton largeSampleAssump1;
	private JRadioButton kendall;
	private JSeparator sep;
	private JButton largeSampleAssump;
	private JRadioButton pearson;
	private JPanel okayCancel;
	private JPanel withVariablesPanel;
	private DJList variables;
	private JScrollPane variableScroller;
	private JPanel variablePanel;
	
	private CorModel model;
	
	private static CorModel lastModel;

	
	public CorDialog(JFrame frame) {
		super(frame);
		initGUI();
		this.setModal(true);
		if(lastModel==null)
			reset();
		else
			setModel(lastModel);
		variableSelector.getJComboBox().addActionListener(this);	
	}
	
	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			{
				help = new HelpButton("pmwiki.php?n=Main.Correlation");
				getContentPane().add(help, new AnchorConstraint(906, 60, 960, 15, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				help.setPreferredSize(new java.awt.Dimension(36, 36));
			}
			{
				plots = new JButton();
				getContentPane().add(plots, new AnchorConstraint(135, 877, 178, 772, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
				plots.setText("Plots");
				plots.setPreferredSize(new java.awt.Dimension(84, 22));
				plots.addActionListener(this);
			}
			{
				options = new JButton();
				getContentPane().add(options, new AnchorConstraint(202, 877, 245, 772, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
				options.setText("Options");
				options.setPreferredSize(new java.awt.Dimension(84, 22));
				options.addActionListener(this);
			}
			{
				typePanel = new JPanel();
				getContentPane().add(typePanel, new AnchorConstraint(385, 12, 862, 671, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				typePanel.setBorder(BorderFactory.createTitledBorder("Correlation type"));
				typePanel.setLayout(null);
				typePanel.setPreferredSize(new java.awt.Dimension(251, 242));
				{
					pearson = new JRadioButton();
					typePanel.add(pearson);
					pearson.setText("Pearson's");
					pearson.setBounds(17, 32, 160, 19);
				}
				{
					largeSampleAssump =  new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
					typePanel.add(largeSampleAssump);
					largeSampleAssump.setBounds(34, 51, 27, 27);
				}
				{
					outlierAssump = new AssumptionIcon("/icons/outlier_assump.png","No Outliers",this,"No Outliers");
					typePanel.add(outlierAssump);
					outlierAssump.setBounds(60, 51, 27, 27);
				}
				{
					sep = new JSeparator();
					typePanel.add(sep);
					sep.setBounds(48, 90, 129, 6);
				}
				{
					kendall = new JRadioButton();
					typePanel.add(kendall);
					kendall.setText("Kendall's");
					kendall.setBounds(17, 102, 160, 19);
				}
				{
					largeSampleAssump1 = new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
					typePanel.add(largeSampleAssump1);
					largeSampleAssump1.setBounds(34, 121, 27, 27);
				}
				{
					monoAssump1 = new AssumptionIcon("/icons/func_assump.png","Monotonic",null,"Monotonic");
					typePanel.add(monoAssump1);
					monoAssump1.setBounds(65, 121, 27, 27);
				}
				{
					sep1 = new JSeparator();
					typePanel.add(sep1);
					sep1.setBounds(48, 160, 129, 5);
				}
				{
					spearman = new JRadioButton();
					typePanel.add(spearman);
					spearman.setText("Spearman's");
					spearman.setBounds(17, 171, 160, 19);
				}
				{
					largeSampeAssump2 =  new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
					typePanel.add(largeSampeAssump2);
					largeSampeAssump2.setBounds(34, 190, 27, 27);
				}
				{
					monoAssump = new AssumptionIcon("/icons/func_assump.png","Monotonic",null,"Monotonic");
					typePanel.add(monoAssump);
					monoAssump.setBounds(65, 190, 27, 27);
				}
				{
					linearAssump = new AssumptionIcon("/icons/func_assump.png","Linear Relationship",
													null,"Linear Relationship");
					typePanel.add(linearAssump);
					linearAssump.setBounds(87, 51, 27, 27);
				}
			}

			{
				withVariablesPanel = new JPanel();
				BorderLayout withVariablesPanelLayout = new BorderLayout();
				withVariablesPanel.setLayout(withVariablesPanelLayout);
				getContentPane().add(withVariablesPanel, new AnchorConstraint(385, 656, 716, 381, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				withVariablesPanel.setPreferredSize(new java.awt.Dimension(219, 168));
				withVariablesPanel.setBorder(BorderFactory.createTitledBorder("With (Optional)"));
				{
					withScroller = new JScrollPane();
					withVariablesPanel.add(withScroller, BorderLayout.CENTER);
					{
						ListModel withModel = new DefaultListModel();
						with = new DJList();
						withScroller.setViewportView(with);
						with.setModel(withModel);
					}
				}
			}
			{
				variablePanel = new JPanel();
				BorderLayout variablesLayout = new BorderLayout();
				variablePanel.setLayout(variablesLayout);
				getContentPane().add(variablePanel, new AnchorConstraint(16, 656, 385, 381, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				variablePanel.setBorder(BorderFactory.createTitledBorder("Variables"));
				variablePanel.setPreferredSize(new java.awt.Dimension(219, 187));
				{
					variableScroller = new JScrollPane();
					variablePanel.add(variableScroller, BorderLayout.CENTER);
					{
						ListModel jList1Model = new DefaultListModel();
						variables = new DJList();
						variableScroller.setViewportView(variables);
						variables.setModel(jList1Model);
					}
				}
			}
			{
				variableSelector = new VariableSelector();
				getContentPane().add(variableSelector, new AnchorConstraint(16, 300, 862, 15, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				variableSelector.setPreferredSize(new java.awt.Dimension(219, 400));
			}
			{
				subsetPanel = new JPanel();
				BorderLayout subsetPanelLayout = new BorderLayout();
				subsetPanel.setLayout(subsetPanelLayout);
				getContentPane().add(subsetPanel, new AnchorConstraint(716, 656, 862, 381, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				subsetPanel.setPreferredSize(new java.awt.Dimension(219, 74));
				subsetPanel.setBorder(BorderFactory.createTitledBorder("Subset"));
				{
					subset = new SubsetPanel(variableSelector.getJComboBox());
					subsetPanel.add(subset, BorderLayout.CENTER);
				}
			}
			{
				okayCancel = new OkayCancelPanel(true,true,this);
				getContentPane().add(okayCancel, new AnchorConstraint(874, 985, 977, 539, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE));
				okayCancel.setPreferredSize(new java.awt.Dimension(356, 52));
				{
					addVar = new AddButton("Add",variableSelector,variables);
					getContentPane().add(addVar, new AnchorConstraint(135, 366, 210, 318, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					addVar.setPreferredSize(new java.awt.Dimension(38, 38));
				}
				{
					removeVar = new RemoveButton("Remove",variableSelector,variables);
					getContentPane().add(removeVar, new AnchorConstraint(210, 366, 285, 318, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					removeVar.setPreferredSize(new java.awt.Dimension(38, 38));
				}
				{
					addWith = new AddButton("Add",variableSelector,with);
					getContentPane().add(addWith, new AnchorConstraint(488, 366, 563, 318, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					addWith.setPreferredSize(new java.awt.Dimension(38, 38));
				}
				{
					removeWith = new RemoveButton("Remove",variableSelector,with);
					getContentPane().add(removeWith, new AnchorConstraint(563, 366, 638, 318, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					removeWith.setPreferredSize(new java.awt.Dimension(38, 38));
				}
			}
			ButtonGroup grp = new ButtonGroup();
			grp.add(pearson);
			grp.add(spearman);
			grp.add(kendall);
			this.setTitle("Correlation");
			this.setMinimumSize(new Dimension(500,527));
			this.setSize(798, 529);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	public void setDataName(String dataName){
		if(!dataName.equals(variableSelector.getSelectedData())){
			variableSelector.setSelectedData(dataName);
		}
	}
	
	public void reset(){
		CorModel mod=new CorModel();
		setModel(mod);
	}
	
	public void setModel(CorModel mod){
		boolean allExist;
		model=mod;
		variables.setModel(new DefaultListModel());
		with.setModel(new DefaultListModel());
		variableSelector.reset();
		if(mod.dataName!=null){
			variableSelector.setSelectedData(mod.dataName);
			allExist=variableSelector.removeAll(mod.variables);
			if(allExist)
				variables.setModel(mod.variables);
			else{
				reset();
				return;
			}
			allExist=variableSelector.removeAll(mod.with);
			if(allExist)
				with.setModel(mod.with);
			else{
				reset();
				return;
			}
			if(mod.subset=="" || RController.isValidSubsetExp(mod.subset,mod.dataName)){
				subset.setText(mod.subset);
			}
		}
		if(mod.method.equals("pearson"))
			pearson.setSelected(true);
		else if(mod.method.equals("spearman"))
			spearman.setSelected(true);
		else
			kendall.setSelected(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if(cmd=="Run"){
			if(pearson.isSelected())
				model.method="pearson";
			else if(spearman.isSelected())
				model.method="spearman";
			else
				model.method="kendall";
			model.variables=(DefaultListModel)variables.getModel();
			model.with=(DefaultListModel)with.getModel();
			model.dataName=variableSelector.getSelectedData();
			model.subset=subset.getText();
			boolean valid =true;
			valid=model.run();
			if(valid){
				SubsetDialog.addToHistory(model.dataName, model.subset);
				Deducer.setRecentData(model.dataName);
				lastModel=model;
				this.dispose();
			}
		}else if(cmd=="Cancel"){
			this.dispose();
		}else if(cmd=="Reset"){
			reset();
		}else if(cmd=="Options"){
			CorOptions opt = new CorOptions(this,model.options);
			opt.setLocationRelativeTo(this);
			opt.setVisible(true);
		}else if(cmd=="Plots"){
			CorPlots cor = new CorPlots(this,model.plots,with.getModel().getSize()>0);
			cor.setLocationRelativeTo(this);
			cor.setVisible(true);
		}else if(cmd == "comboBoxChanged"){
			reset();
		}
	}

}
