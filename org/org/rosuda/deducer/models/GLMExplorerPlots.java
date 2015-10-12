package org.rosuda.deducer.models;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpinnerListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.DJList;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;




public class GLMExplorerPlots extends javax.swing.JDialog implements ActionListener{
	protected JPanel termPanel;
	protected JTextField ylab;
	protected JCheckBox rug;
	protected JLabel ylabLabel;
	protected JSpinner levels;
	protected JLabel pointsLabel;
	protected JCheckBox multi;
	protected JList terms;
	protected JPanel meansPanel;
	protected JPanel okayCancel;
	protected JCheckBox confInt;
	protected JCheckBox scaled;
	protected JPanel optionsPanel;
	protected JScrollPane meansScroller;
	protected JButton remove;
	protected JButton add;
	protected JList effects;
	protected JScrollPane termScroller;
	protected GLMModel  model;

	
	public GLMExplorerPlots(JFrame frame,GLMModel mod,RModel rmod) {
		super(frame);
		initGUI();
		setModel(mod,rmod);
		this.setModal(true);
	}
	
	protected void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					termPanel = new JPanel();
					BorderLayout factorPanelLayout = new BorderLayout();
					termPanel.setLayout(factorPanelLayout);
					getContentPane().add(termPanel);
					termPanel.setBounds(12, 12, 170, 230);
					termPanel.setBorder(BorderFactory.createTitledBorder("Model Terms"));
					{
						termScroller = new JScrollPane();
						termPanel.add(termScroller, BorderLayout.CENTER);
						{
							ListModel factorsModel = 
								new DefaultListModel();
							terms = new DJList();
							termScroller.setViewportView(terms);
							terms.setModel(factorsModel);
						}
					}
				}
				{
					meansPanel = new JPanel();
					BorderLayout postHocPanelLayout = new BorderLayout();
					meansPanel.setLayout(postHocPanelLayout);
					getContentPane().add(meansPanel);
					meansPanel.setBounds(250, 12, 170, 230);
					meansPanel.setBorder(BorderFactory.createTitledBorder("Effects"));
					{
						meansScroller = new JScrollPane();
						meansPanel.add(meansScroller, BorderLayout.CENTER);
						{
							ListModel postHocModel = 
								new DefaultListModel();
							effects = new DJList();
							meansScroller.setViewportView(effects);
							effects.setModel(postHocModel);
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
					okayCancel = new OkayCancelPanel(false,false,this);
					getContentPane().add(okayCancel);
					okayCancel.setBounds(205, 378, 207, 36);
					{
						optionsPanel = new JPanel();
						getContentPane().add(optionsPanel);
						optionsPanel.setBounds(35, 254, 358, 118);
						optionsPanel.setLayout(null);
						optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
						{
							confInt = new JCheckBox();
							optionsPanel.add(confInt);
							confInt.setText("point-wise intervals");
							confInt.setBounds(17, 29, 171, 19);
						}
						{
							scaled = new JCheckBox();
							optionsPanel.add(scaled);
							scaled.setText("Scaled y-axis");
							scaled.setBounds(215, 88, 124, 18);
						}
						{
							multi = new JCheckBox();
							optionsPanel.add(multi);
							multi.setText("Multiple lines per panel");
							multi.setBounds(17, 60, 200, 18);
						}
						{
							pointsLabel = new JLabel();
							optionsPanel.add(pointsLabel);
							pointsLabel.setText("# of levels:");
							pointsLabel.setBounds(5, 90, 71, 14);
							pointsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
						}
						{
							String[] ints = new String[80];
							for(int i=1;i<=80;i++)
								ints[i-1]=i+"";
							SpinnerListModel levelsModel = 
								new SpinnerListModel(ints);
							levels = new JSpinner();
							optionsPanel.add(levels);
							levels.setModel(levelsModel);
							levels.setBounds(81, 87, 45, 21);
						}
						{
							ylabLabel = new JLabel();
							optionsPanel.add(ylabLabel);
							ylabLabel.setText("y-axis label");
							ylabLabel.setBounds(215, 20, 121, 14);
							ylabLabel.setHorizontalAlignment(SwingConstants.CENTER);
							ylabLabel.setHorizontalTextPosition(SwingConstants.CENTER);
						}
						{
							ylab = new JTextField();
							optionsPanel.add(ylab);
							ylab.setText("<auto>");
							ylab.setBounds(215, 34, 121, 21);
							ylab.setHorizontalAlignment(SwingConstants.CENTER);
						}
						{
							rug = new JCheckBox();
							optionsPanel.add(rug);
							rug.setText("rug");
							rug.setBounds(215, 60, 73, 18);
						}
					}
				}
			}
			this.setTitle("Effect Plots");
			this.setSize(432, 460);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	
	public void updateModel(){
		model.plots.effects = (DefaultListModel) effects.getModel();
		model.plots.confInt=confInt.isSelected();
		model.plots.multi=multi.isSelected();
		model.plots.defaultLevels=Integer.parseInt((String) levels.getValue());
		model.plots.ylab=ylab.getText();
		model.plots.rug=rug.isSelected();
		model.plots.scaled=scaled.isSelected();
	}
	public void setModel(GLMModel mod,RModel rmod){
		model = mod;
		DefaultListModel termModel = new DefaultListModel();
		String[] t = new String[]{};
		try {
			t = Deducer.timedEval("attr(terms("+rmod.modelName+
										"),\"term.labels\")").asStrings();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int j=0;j<t.length;j++)
			termModel.addElement(t[j]);
		terms.setModel(termModel);
		for(int i=0;i<model.plots.effects.getSize();i++)
			if(termModel.contains(model.plots.effects.elementAt(i))){
				termModel.removeElement(model.plots.effects.elementAt(i));
				((DefaultListModel)effects.getModel()).addElement(model.plots.effects.elementAt(i));
			}
		confInt.setSelected(model.plots.confInt);
		multi.setSelected(model.plots.multi);
		levels.setValue(model.plots.defaultLevels+"");
		ylab.setText(model.plots.ylab);
		rug.setSelected(model.plots.rug);
		scaled.setSelected(model.plots.scaled);
	}

	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if(cmd=="Cancel"){
			this.dispose();
		}else if(cmd=="OK"){
			updateModel();
			this.dispose();
		}else if(cmd=="Add"){
			Object[] objs=terms.getSelectedValues();
			for(int i=0;i<objs.length;i++){
				((DefaultListModel)terms.getModel()).removeElement(objs[i]);
				if(objs[i]!=null)
					((DefaultListModel)effects.getModel()).addElement(objs[i]);
			}
		}else if(cmd=="Remove"){
			Object[] objs=effects.getSelectedValues();
			for(int i=0;i<objs.length;i++){
				((DefaultListModel)effects.getModel()).removeElement(objs[i]);
				if(objs[i]!=null)
					((DefaultListModel)terms.getModel()).addElement(objs[i]);
			}
		}
		
	}

}