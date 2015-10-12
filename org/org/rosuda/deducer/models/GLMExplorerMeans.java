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
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.DJList;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;


public class GLMExplorerMeans extends javax.swing.JDialog implements ActionListener{
	private JPanel termPanel;
	private JList terms;
	private JPanel meansPanel;
	private JPanel okayCancel;
	private JCheckBox confInt;
	private JScrollPane meansScroller;
	private JButton remove;
	private JButton add;
	private JList effects;
	private JScrollPane termScroller;
	GLMModel  model;

	
	public GLMExplorerMeans(JFrame frame,GLMModel mod,RModel rmod) {
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
					confInt = new JCheckBox();
					getContentPane().add(confInt);
					confInt.setText("Estimate confidence intervals");
					confInt.setBounds(132, 259, 250, 19);
				}
				{
					okayCancel = new OkayCancelPanel(false,false,this);
					getContentPane().add(okayCancel);
					okayCancel.setBounds(209, 297, 207, 36);
				}
			}
			this.setTitle("Post-Hoc Comparisons");
			this.setSize(432, 379);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	
	public void updateModel(){
		model.effects.effects = (DefaultListModel) effects.getModel();
		model.effects.confInt = confInt.isSelected();
		
	}
	public void setModel(GLMModel mod,RModel rmod){
		try{
			model = mod;
			DefaultListModel termModel = new DefaultListModel();
			String[] t = Deducer.timedEval("attr(terms("+rmod.modelName+
										"),\"term.labels\")").asStrings();
			if(t!=null)
				for(int j=0;j<t.length;j++)
					termModel.addElement(t[j]);
			terms.setModel(termModel);
			for(int i=0;i<model.effects.effects.getSize();i++)
				if(termModel.contains(model.effects.effects.elementAt(i))){
					termModel.removeElement(model.effects.effects.elementAt(i));
					((DefaultListModel)effects.getModel()).addElement(model.effects.effects.elementAt(i));
				}
			confInt.setSelected(model.effects.confInt);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disableConfInt(){
		confInt.setEnabled(false);
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