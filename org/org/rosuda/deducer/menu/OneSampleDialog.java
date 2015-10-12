package org.rosuda.deducer.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.JGR.JGR;
import org.rosuda.JGR.RController;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.AssumptionIcon;
import org.rosuda.deducer.toolkit.DJList;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;
import org.rosuda.deducer.toolkit.VariableSelector;


public class OneSampleDialog extends javax.swing.JDialog implements ActionListener{
	private VariableSelector variableSelector;
	private IconButton remove;
	private JButton plots;
	private JPanel testPanel;
	private IconButton ttestOptions;
	private JSeparator sep;
	private JCheckBox swTest;
	private JLabel swLabel;
	private JButton help;
	private JPanel navPanel;
	private JCheckBox ttest;
	private JPanel subsetWrapper;
	private IconButton add;
	private JPanel variables;
	private IconButton assumpNOrNorm;
	private IconButton assumpOutlier;
	private IconButton assumpN;
	private DJList vars = new DJList();
	private SubsetPanel subsetPanel;
	
	
	private OneSampleModel model = new OneSampleModel();
	private static OneSampleModel lastModel;

	
	public OneSampleDialog(JFrame frame) {
		super(frame);
		initGUI();
		if(lastModel!=null)
			setModel(lastModel);
		variableSelector.getJComboBox().addActionListener(this);
	}
	
	private void initGUI() {
		try {
			{
				AnchorLayout thisLayout = new AnchorLayout();
				getContentPane().setLayout(thisLayout);
				{
					help = new HelpButton("pmwiki.php?n=Main.OneSampleTest");
					getContentPane().add(help, new AnchorConstraint(915, 86, 990, 23, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					help.setPreferredSize(new java.awt.Dimension(32, 32));
				}
				{
					variableSelector = new VariableSelector();
					getContentPane().add(variableSelector, new AnchorConstraint(26, 406, 630, 12, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
					variableSelector.setBorder(BorderFactory.createTitledBorder("variables"));
					variableSelector.setPreferredSize(new java.awt.Dimension(206, 303));
				}
				{
					variables = new JPanel();
					getContentPane().add(variables, new AnchorConstraint(27, 20, 630, 567, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					variables.setBorder(BorderFactory.createTitledBorder("Variables"));
					variables.setLayout(new BorderLayout());
					vars = new DJList();
					vars.setModel(new DefaultListModel());
					JScrollPane varScroller = new JScrollPane();
					varScroller.setViewportView(vars);
					variables.add(varScroller, BorderLayout.CENTER);
					variables.setPreferredSize(new java.awt.Dimension(213, 275));
				}
				{
					add = new IconButton("/icons/1rightarrow_32.png","Add",this,"Add");;
					getContentPane().add(add, new AnchorConstraint(101, 521, 297, 458, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
					add.setPreferredSize(new java.awt.Dimension(36, 36));
					add.setContentAreaFilled(false);
				}
				{
					remove = new IconButton("/icons/1leftarrow_32.png","Remove",this,"Remove");
					getContentPane().add(remove, new AnchorConstraint(138, 521, 371, 458, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
					remove.setPreferredSize(new java.awt.Dimension(36, 36));
					remove.setContentAreaFilled(false);

				}
				{
					subsetWrapper = new JPanel();
					subsetWrapper.setLayout(new BorderLayout());
					subsetPanel = new SubsetPanel(variableSelector.getJComboBox());
					subsetWrapper.add(subsetPanel);
					getContentPane().add(subsetWrapper, new AnchorConstraint(702, 20, 876, 567, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					subsetWrapper.setBorder(BorderFactory.createTitledBorder("Subset"));
					subsetWrapper.setPreferredSize(new java.awt.Dimension(213, 79));
				}
				{
					plots = new JButton();
					getContentPane().add(plots, new AnchorConstraint(645, 842, 691, 696, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					plots.setText("Plots");
					plots.setPreferredSize(new java.awt.Dimension(79, 21));
					plots.addActionListener(this);
				}
				{
					testPanel = new JPanel();
					getContentPane().add(testPanel, new AnchorConstraint(642, 406, 63, 12, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
					testPanel.setBorder(BorderFactory.createTitledBorder(null, "Tests",
							TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));
					testPanel.setLayout(null);
					testPanel.setPreferredSize(new java.awt.Dimension(206, 140));
					{
						ttest = new JCheckBox();
						testPanel.add(ttest);
						ttest.setText("One-sample t-test");
						ttest.setBounds(17, 19, 155, 18);
					}
					{
						ttestOptions = new IconButton("/icons/advanced_21.png","t-test Options",
								this,"t-test Options");
						testPanel.add(ttestOptions);
						ttestOptions.setBounds(171, 17, 27, 27);
						ttestOptions.setContentAreaFilled(false);
					}
					{
						assumpNOrNorm = new AssumptionIcon("/icons/N_or_norm_assump.png",
								"Large Sample or Normality",this,
								"Large Sample or Normality");
						testPanel.add(assumpNOrNorm);
						assumpNOrNorm.setBounds(40, 40, 47, 27);
					}
					{
						assumpOutlier = new AssumptionIcon("/icons/outlier_assump.png",
								"No Outliers",this,
								"No Outliers");
						testPanel.add(assumpOutlier);
						assumpOutlier.setBounds(90, 40, 27, 27);
					}
					{
						swTest = new JCheckBox();
						testPanel.add(swTest);
						swTest.setText("Shapiro-Wilk test");
						swTest.setBounds(17, 77, 170, 18);
					}
					{
						swLabel = new JLabel();
						testPanel.add(swLabel);
						swLabel.setText("against normality");
						swLabel.setBounds(40, 93, 116, 18);
					}
					{
						assumpN = new AssumptionIcon("/icons/N_assump.png","Large Sample",
								this,"Large Sample");
						testPanel.add(assumpN);
						assumpN.setBounds(40, 107, 27, 27);
					}
					{
						sep = new JSeparator();
						testPanel.add(sep);
						sep.setBounds(40, 71, 105, 6);
					}
				}
				{
					navPanel = new OkayCancelPanel(true,true,this);
					getContentPane().add(navPanel, new AnchorConstraint(889, 963, 976, 480,
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE));
					navPanel.setPreferredSize(new java.awt.Dimension(280, 46));
				}
			}
			this.setMinimumSize(new Dimension(546,331));
			this.setSize(546, 536);
			this.setTitle("One Sample Test");
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	public void setModel(OneSampleModel mod){
		model=new OneSampleModel();
		variableSelector.reset();
		if(mod.data!=null){
			variableSelector.setSelectedData(mod.data);
		}
		model=mod;
		ttest.setSelected(mod.doT);
		swTest.setSelected(mod.doSW);
		boolean allExist=variableSelector.removeAll(mod.vars);
		if(allExist){
			vars.setModel(mod.vars);
			model.subset=mod.subset;
			subsetPanel.setText(mod.subset);
			model.vars=mod.vars;
		}else{
			variableSelector.reset();
			vars.setModel(new DefaultListModel());
		}

	}
	
	public void setDataName(String dataName){
		if(!dataName.equals(variableSelector.getSelectedData())){
			variableSelector.setSelectedData(dataName);
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		String cmd =arg0.getActionCommand();

		if(cmd=="t-test Options"){
			OneSampleTOptions inst = new OneSampleTOptions(this,model.alternative,model.mu);
			inst.setLocationRelativeTo(this);
			inst.setVisible(true);
		}else if(cmd=="Run"){
			model.data=variableSelector.getSelectedData();
			model.vars=(DefaultListModel)vars.getModel();
			model.doT=ttest.isSelected();
			model.doSW=swTest.isSelected();
			model.subset = subsetPanel.getText();
			boolean valid = model.run();
			if(valid){
				lastModel=model;
				Deducer.setRecentData(model.data);
				SubsetDialog.addToHistory(model.data, model.subset);
				this.dispose();
			}
		}else if(cmd=="Cancel"){
			this.dispose();
		}else if(cmd=="Reset"){
			setModel(new OneSampleModel());
		}else if(cmd=="Add"){
			Object[] objs=variableSelector.getJList().getSelectedValues();
			for(int i=0;i<objs.length;i++){
				variableSelector.remove(objs[i]);
				((DefaultListModel)vars.getModel()).addElement(objs[i]);
			}
		}else if(cmd=="Remove"){
			Object[] objs=vars.getSelectedValues();
			for(int i=0;i<objs.length;i++){
				variableSelector.add(objs[i]);
				((DefaultListModel)vars.getModel()).removeElement(objs[i]);
			}
		}else if(cmd == "comboBoxChanged"){
			setModel(new OneSampleModel());
		}else if(cmd=="Plots"){
			OneSamplePlots plt= new OneSamplePlots(this,model.plots);
			plt.setLocationRelativeTo(this);
			plt.setVisible(true);
		}
	}
	
	class OneSampleModel{
		double mu = 0;
		String alternative = "two.sided";
		
		boolean doT = true;
		boolean doSW = false;
		
		String data = null;
		DefaultListModel vars = new DefaultListModel();
		String subset = "";
		
		PlotModel plots = new PlotModel();
		
		public boolean run(){
			if(vars.getSize()==0){
				JOptionPane.showMessageDialog(null, "Please select one or more variables.");
				return false;
			}
			String variables = Deducer.makeRCollection(vars, "d", false);//RController.makeRVector(vars);			
			String cmd="";
			String subn="";
			boolean isSubset=false;
			if(!subset.equals("") ){
				if(!SubsetDialog.isValidSubsetExp(subset,data)){
					JOptionPane.showMessageDialog(null, "Sorry, the subset expression seems to be invalid.");
					return false;
				}
				subn = Deducer.getUniqueName(data+".sub");
				cmd=subn+"<-subset("+data+","+subset+")"+"\n";
				isSubset=true;
			}else
				subn=data;
			cmd+="descriptive.table(vars="+variables+
							",data="+subn+
							",func.names =c(\"Mean\",\"St. Deviation\",\"Valid N\"))\n";
			
			if(doT){
				cmd += "one.sample.test(variables="+variables+
					",\n\tdata="+subn+
					",\n\ttest=t.test"+
					",\n\talternative=\""+alternative+"\""+
					(mu!=0.0 ? ",\n\tmu="+Double.toString(mu) : "")+
					")\n";
			}
			if(doSW){
				cmd += "one.sample.test(variables="+variables+
					",\n\tdata="+subn+
					",\n\ttest=shapiro.test"+
					")\n";
			}
			if(plots.plot){
				cmd+="onesample.plot(variables="+variables+",data="+subn+
					(doT ? (",test.value="+Double.toString(mu)) : "")+
					(!plots.scale?"":",scale=TRUE")+
					(plots.box?",type='box'":",type='hist'")+
					",alpha="+plots.alpha+")\n";
			}
			if(isSubset)
				cmd+="rm("+subn+")\n";
			Deducer.execute(cmd);
			return true;
		}
		
		public class PlotModel{
			public boolean plot=false;
			public boolean box=false;
			public boolean scale=false;
			public double alpha=.2;
			
			
		}
	}

	
	class OneSampleTOptions extends JDialog implements ActionListener{
		private JPanel alternativePanel;
		private ButtonGroup alternativeGroup;
		private JRadioButton twosided;
		private JPanel okayCancelPanel;
		private JLabel muLabel;
		private JTextField mu;
		private JRadioButton greater;
		private JRadioButton less;


		
		public OneSampleTOptions(JDialog d,String alt,double mean) {
			super(d);
			initGUI();
			if(alt=="two.sided")
				twosided.setSelected(true);
			else if(alt=="less")
				less.setSelected(true);
			else
				greater.setSelected(true);
			mu.setText(Double.toString(mean));
		}
		
		private void initGUI() {
			try {
				getContentPane().setLayout(null);
				{
					alternativePanel = new JPanel();
					getContentPane().add(alternativePanel);
					alternativePanel.setBounds(42, 12, 145, 115);
					alternativePanel.setBorder(BorderFactory.createTitledBorder("Alternative"));
					alternativePanel.setLayout(null);
					{
						twosided = new JRadioButton();
						alternativePanel.add(twosided);
						twosided.setText("Two-sided");
						twosided.setBounds(17, 24, 115, 18);
					}
					{
						less = new JRadioButton();
						alternativePanel.add(less);
						less.setText("Less");
						less.setBounds(17, 49, 115, 18);
					}
					{
						greater = new JRadioButton();
						alternativePanel.add(greater);
						greater.setText("Greater");
						greater.setBounds(17, 79, 115, 18);
					}
					
				}
				{
					mu = new JTextField(0+"");
					getContentPane().add(mu);
					mu.setBounds(115, 136, 48, 21);
				}
				{
					muLabel = new JLabel();
					muLabel.setText("Test mean:");
					getContentPane().add(muLabel);				
					muLabel.setBounds(24, 139, 88, 14);		
				}
				{
					okayCancelPanel = new OkayCancelPanel(false,false,this);
					getContentPane().add(okayCancelPanel);	
					okayCancelPanel.setBounds(10, 165, 200, 38);			
				}
				getAlternativeGroup();
				this.setTitle("One-sample t-test options");
				this.setResizable(false);
				this.setSize(246, 249);
				this.setModal(true);
			} catch (Exception e) {
				new ErrorMsg(e);
			}
		}
		
		private ButtonGroup getAlternativeGroup() {
			if(alternativeGroup == null) {
				alternativeGroup = new ButtonGroup();
				alternativeGroup.add(twosided);
				alternativeGroup.add(less);
				alternativeGroup.add(greater);
			}
			return alternativeGroup;
		}
		
		public String getAlternative(){
			if(twosided.isSelected())
				return "two.sided";
			if(less.isSelected())
				return "less";
			return "greater";
		}
		

		public void actionPerformed(ActionEvent arg0) {
			String cmd = arg0.getActionCommand();
			if(cmd == "OK"){
				try{
					model.mu=Double.parseDouble(mu.getText());
				}catch(Exception e){
					JOptionPane.showMessageDialog(this, "Sorry, the entered mean doesn't seem to be valid." +
														"\n please enter a number.");
					return;
				}			
				model.alternative=getAlternative();
				this.dispose();
			}else if(cmd=="Cancel"){
				this.dispose();
			}
			
		}
	}
}
