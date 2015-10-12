package org.rosuda.deducer.models;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.DJList;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;

import org.rosuda.JGR.JGR;
import org.rosuda.JGR.util.ErrorMsg;


public class GLMExplorerPostHoc extends javax.swing.JDialog implements ActionListener {
	private JPanel factorPanel;
	private JList factors;
	private JPanel postHocPanel;
	private JPanel okayCancel;
	private JCheckBox confInt;
	private JScrollPane postHocScroller;
	private JComboBox correction;
	private JComboBox type;
	private JLabel correctionLabel;
	private JLabel typeLabel;
	private JButton remove;
	private JButton add;
	private JList postHoc;
	private JScrollPane factorScroller;
	
	private String[] contrTypes = {"Tukey", "Dunnett", "Sequen", "AVE", 
            "Changepoint", "Williams", "Marcus", 
            "McDermott", "UmbrellaWilliams", "GrandMean"};
	private String[] corrTypes = {"No Correction","single-step","  ---------  ", "Shaffer", "Westfall", "free","  ---------  ",
							"holm", "hochberg", "hommel", "bonferroni", "BH", "BY","fdr"};
	RModel rmodel;
	GLMModel  model;

	
	public GLMExplorerPostHoc(JFrame frame,GLMModel mod,RModel rmod) {
		super(frame);
		initGUI();
		setModel(mod,rmod);
		this.setModal(true);
	}
	
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					factorPanel = new JPanel();
					BorderLayout factorPanelLayout = new BorderLayout();
					factorPanel.setLayout(factorPanelLayout);
					getContentPane().add(factorPanel);
					factorPanel.setBounds(12, 12, 170, 230);
					factorPanel.setBorder(BorderFactory.createTitledBorder("Factors"));
					{
						factorScroller = new JScrollPane();
						factorPanel.add(factorScroller, BorderLayout.CENTER);
						{
							ListModel factorsModel = 
								new DefaultListModel();
							factors = new DJList();
							factorScroller.setViewportView(factors);
							factors.setModel(factorsModel);
						}
					}
				}
				{
					postHocPanel = new JPanel();
					BorderLayout postHocPanelLayout = new BorderLayout();
					postHocPanel.setLayout(postHocPanelLayout);
					getContentPane().add(postHocPanel);
					postHocPanel.setBounds(250, 12, 170, 230);
					postHocPanel.setBorder(BorderFactory.createTitledBorder("Post-Hoc"));
					{
						postHocScroller = new JScrollPane();
						postHocPanel.add(postHocScroller, BorderLayout.CENTER);
						{
							ListModel postHocModel = 
								new DefaultListModel();
							postHoc = new DJList();
							postHocScroller.setViewportView(postHoc);
							postHoc.setModel(postHocModel);
						}
					}
				}
				{
					add = new IconButton("/icons/1rightarrow_32.png", "Add", this,"Add");
					getContentPane().add(add);
					add.setBounds(197, 86, 38, 38);
				}
				{
					remove = new IconButton("/icons/1leftarrow_32.png", "Remove", this,"Remove");
					getContentPane().add(remove);
					remove.setBounds(197, 124, 38, 38);
				}
				{
					typeLabel = new JLabel();
					getContentPane().add(typeLabel);
					typeLabel.setText("Type:");
					typeLabel.setBounds(113, 254, 47, 15);
					typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				}
				{
					correctionLabel = new JLabel();
					getContentPane().add(correctionLabel);
					correctionLabel.setText("Correction:");
					correctionLabel.setBounds(77, 303, 83, 15);
					correctionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				}
				{
					ComboBoxModel typeModel = 
						new DefaultComboBoxModel(contrTypes);
					type = new JComboBox();
					getContentPane().add(type);
					type.setModel(typeModel);
					type.setBounds(166, 250, 143, 22);
				}
				{
					ComboBoxModel correctionModel = 
						new DefaultComboBoxModel(corrTypes);
					correction = new JComboBox();
					getContentPane().add(correction);
					correction.setModel(correctionModel);
					correction.setBounds(166, 299, 143, 22);
					correction.addActionListener(this);
				}
				{
					confInt = new JCheckBox();
					getContentPane().add(confInt);
					confInt.setText("Estimate confidence intervals");
					confInt.setBounds(166, 274, 247, 19);
				}
				{
					okayCancel = new OkayCancelPanel(false,false,this);
					getContentPane().add(okayCancel);
					okayCancel.setBounds(213, 333, 207, 36);
				}
			}
			this.setTitle("Post-Hoc Comparisons");
			this.setSize(432, 403);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	
	public void updateModel(){
		model.posthoc.posthoc = (DefaultListModel) postHoc.getModel();
		model.posthoc.confInt = confInt.isSelected();
		model.posthoc.type=(String)type.getSelectedItem();
		model.posthoc.correction=(String)correction.getSelectedItem();
		
	}
	public void setModel(GLMModel mod,RModel rmod){
		model = mod;
		rmodel = rmod;
		try{
			String[] mainEffects = Deducer.timedEval("labels(terms("+rmod.modelName+"))[!grepl(\":\",labels(terms("+
					rmod.modelName+")))]").asStrings();
			
			if(mainEffects!=null && mainEffects.length>0){
				String tmpForm = "~"+mainEffects[0];
				for(int i=1;i<(mainEffects.length);i++)
					tmpForm+="+"+mainEffects[i];
				int[] isFactor = Deducer.timedEval("as.integer(sapply(model.frame("+tmpForm+
										",data="+rmod.data+",na.action=na.omit),is.factor))").asIntegers();
				if(isFactor!=null)
					for(int i=0;i<mainEffects.length;i++){
						if(isFactor[i]==1)
							((DefaultListModel)factors.getModel()).addElement(mainEffects[i]);
					}
			}
			DefaultListModel factModel = ((DefaultListModel)factors.getModel());
			for(int i=0;i<model.posthoc.posthoc.getSize();i++)
				if(factModel.contains(model.posthoc.posthoc.elementAt(i))){
					factModel.removeElement(model.posthoc.posthoc.elementAt(i));
					((DefaultListModel)postHoc.getModel()).addElement(model.posthoc.posthoc.elementAt(i));
				}
			confInt.setSelected(model.posthoc.confInt);
			type.setSelectedItem(model.posthoc.type);
			correction.setSelectedItem(model.posthoc.correction);
		}catch (Exception e) {
			e.printStackTrace();
			new ErrorMsg(e);
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if(cmd=="Cancel"){
			this.dispose();
		}else if(cmd=="OK"){
			updateModel();
			this.dispose();
		}else if(cmd=="Add"){
			Object[] objs=factors.getSelectedValues();
			for(int i=0;i<objs.length;i++){
				((DefaultListModel)factors.getModel()).removeElement(objs[i]);
				if(objs[i]!=null)
					((DefaultListModel)postHoc.getModel()).addElement(objs[i]);
			}
		}else if(cmd=="Remove"){
			Object[] objs=postHoc.getSelectedValues();
			for(int i=0;i<objs.length;i++){
				((DefaultListModel)postHoc.getModel()).removeElement(objs[i]);
				if(objs[i]!=null)
					((DefaultListModel)factors.getModel()).addElement(objs[i]);
			}
		}else{
			if(correction.getSelectedIndex()==2 || correction.getSelectedIndex()==6){
				correction.setSelectedIndex(1);
			}
		}
		
	}

}
