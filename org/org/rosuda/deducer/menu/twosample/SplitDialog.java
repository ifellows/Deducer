package org.rosuda.deducer.menu.twosample;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;
import org.rosuda.JGR.JGR;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;


public class SplitDialog extends JDialog implements ActionListener, FocusListener{
	private JRadioButton cut;
	private JRadioButton define;
	private JList group1List;
	private ButtonGroup buttonGroup;
	private JPanel okayCancel;
	private JList group2List;
	private JList levelsList;
	private JButton remove1;
	private JScrollPane grp2Scroller;
	private JScrollPane grp1Scroller;
	private JScrollPane levelsScroller;
	private JButton remove2;
	private JButton add2;
	private JButton add1;
	private JPanel group2Panel;
	private JPanel group1Panel;
	private JPanel Levels;
	private JTextField cutValue;

	private TwoSampleModel.SplitModel model;
	private String data;
	private String factor;

	
	public SplitDialog(JDialog d,TwoSampleModel.SplitModel mod,String dataName,String factorName) {
		super(d);
		model=mod;
		data=dataName;
		factor=factorName;
		initGUI();
		reset();
	}
	
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					cut = new JRadioButton();
					getContentPane().add(cut);
					cut.setText("Cut at");
					cut.setBounds(30, 12, 77, 19);
				}
				{
					cutValue = new JTextField();
					getContentPane().add(cutValue);
					cutValue.setBounds(107, 10, 86, 22);
					cutValue.addFocusListener(this);
				}
				{
					define = new JRadioButton();
					getContentPane().add(define);
					define.setText("Define Groups");
					define.setBounds(30, 38, 135, 19);
				}
				{
					Levels = new JPanel();
					BorderLayout LevelsLayout = new BorderLayout();
					Levels.setLayout(LevelsLayout);
					getContentPane().add(Levels);
					Levels.setBounds(45, 57, 153, 169);
					Levels.setBorder(BorderFactory.createTitledBorder("Levels"));
					{
						levelsScroller = new JScrollPane();
						Levels.add(levelsScroller, BorderLayout.CENTER);
						levelsScroller.setPreferredSize(new java.awt.Dimension(150, 144));
						{
							DefaultListModel levelsListModel = new DefaultListModel();
							levelsList = new JList();
							levelsScroller.setViewportView(levelsList);
							levelsList.setModel(levelsListModel);
							levelsList.addFocusListener(this);
						}
					}
				}
				{
					group1Panel = new JPanel();
					BorderLayout group1PanelLayout = new BorderLayout();
					group1Panel.setLayout(group1PanelLayout);
					getContentPane().add(group1Panel);
					group1Panel.setBounds(247, 54, 150, 80);
					group1Panel.setBorder(BorderFactory.createTitledBorder("Group 1"));
					{
						grp1Scroller = new JScrollPane();
						group1Panel.add(grp1Scroller, BorderLayout.CENTER);
						{

							group1List = new JList();
							group1List.setModel(new DefaultListModel());
							grp1Scroller.setViewportView(group1List);
							group1List.addFocusListener(this);
						}
					}
				}
				{
					group2Panel = new JPanel();
					BorderLayout group2PanelLayout = new BorderLayout();
					group2Panel.setLayout(group2PanelLayout);
					getContentPane().add(group2Panel);
					group2Panel.setBounds(246, 146, 150, 80);
					group2Panel.setBorder(BorderFactory.createTitledBorder("Group 2"));
					{
						grp2Scroller = new JScrollPane();
						group2Panel.add(grp2Scroller, BorderLayout.CENTER);
						{

							group2List = new JList();
							group2List.setModel(new DefaultListModel());
							grp2Scroller.setViewportView(group2List);
							group2List.addFocusListener(this);
						}
					}
				}
				{
					add1 = new IconButton("/icons/1rightarrow_32.png","Add to Group 1",this,"Add to Group 1");
					getContentPane().add(add1);
					add1.setBounds(210, 68, 32, 32);
					add1.addFocusListener(this);
				}
				{
					remove1 = new IconButton("/icons/1leftarrow_32.png","Remove from Group 1",this,"Remove from Group 1");
					getContentPane().add(remove1);
					remove1.setBounds(210, 99, 32, 32);
					remove1.addFocusListener(this);
				}
				{
					add2 = new IconButton("/icons/1rightarrow_32.png","Add to Group 2",this,"Add to Group 2");
					getContentPane().add(add2);
					add2.setBounds(210, 158, 32, 32);
					add2.addFocusListener(this);
				}
				{
					remove2 = new IconButton("/icons/1leftarrow_32.png","Remove from Group 2",this,"Remove from Group 2");
					getContentPane().add(remove2);
					remove2.setBounds(210, 189, 32, 32);
					remove2.addFocusListener(this);
				}
				{
					okayCancel = new OkayCancelPanel(false,false,this);
					getContentPane().add(okayCancel);
					okayCancel.setBounds(171, 232, 225, 45);
				}
			}
			getButtonGroup();
			define.setSelected(true);
			this.setTitle("Define Groups");
			this.setResizable(false);
			this.setSize(409, 295);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ButtonGroup getButtonGroup() {
		if(buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(cut);
			buttonGroup.add(define);
		}
		return buttonGroup;
	}
	
	public void reset(){
		if(data!=null && factor!=null){
			String[] levs=null;
			try {
				levs = Deducer.timedEval("levels(as.factor("+data+"[[\""+factor+"\"]]))").asStrings();
			} catch (REXPMismatchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(levs!=null && levs.length<50){
				for(int i=0;i<levs.length;i++)
					((DefaultListModel)levelsList.getModel()).addElement(levs[i]);
				/*if(model.group1.size()==0 && model.group2.size()==0){
					if(levelsList.getModel().getSize()>0){
						((DefaultListModel)group1List.getModel()).addElement(levs[0]);
						((DefaultListModel)levelsList.getModel()).remove(0);
					}
					if(levelsList.getModel().getSize()>0){
						((DefaultListModel)group2List.getModel()).addElement(levs[1]);
						((DefaultListModel)levelsList.getModel()).remove(0);
					}
				}*/
			}
			else{
				model.group1=new Vector();
				model.group2=new Vector();
				define.setEnabled(false);
				group1List.setEnabled(false);
				group2List.setEnabled(false);
				levelsList.setEnabled(false);
				add1.setEnabled(false);
				remove1.setEnabled(false);
				add2.setEnabled(false);
				remove2.setEnabled(false);
			}
		}
		if(model.isCut){
			cut.setSelected(true);
			cutValue.setText(model.cutPoint);
		}else{
			define.setSelected(true);
			((DefaultListModel)group1List.getModel()).removeAllElements();
			for(int i=0;i<model.group1.size();i++){
				if( ((DefaultListModel)levelsList.getModel()).contains(model.group1.get(i))){
					((DefaultListModel)group1List.getModel()).addElement(model.group1.get(i));
					((DefaultListModel)levelsList.getModel()).removeElement(model.group1.get(i));
				}
			}
			((DefaultListModel)group2List.getModel()).removeAllElements();
			for(int i=0;i<model.group2.size();i++){
				if( ((DefaultListModel)levelsList.getModel()).contains(model.group2.get(i))){
					((DefaultListModel)group2List.getModel()).addElement(model.group2.get(i));
					((DefaultListModel)levelsList.getModel()).removeElement(model.group2.get(i));
				}
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if(cmd =="Add to Group 1"){
			Object[] inds=levelsList.getSelectedValues();
			for(int i=0;i<inds.length;i++){
				((DefaultListModel)levelsList.getModel()).removeElement(inds[i]);
				((DefaultListModel)group1List.getModel()).addElement(inds[i]);
			}
		}else if(cmd =="Add to Group 2"){
			Object[] inds=levelsList.getSelectedValues();
			for(int i=0;i<inds.length;i++){
				((DefaultListModel)levelsList.getModel()).removeElement(inds[i]);
				((DefaultListModel)group2List.getModel()).addElement(inds[i]);
			}
		}else if(cmd =="Remove from Group 1"){
			Object[] inds=group1List.getSelectedValues();
			for(int i=0;i<inds.length;i++){
				((DefaultListModel)group1List.getModel()).removeElement(inds[i]);
				((DefaultListModel)levelsList.getModel()).addElement(inds[i]);
			}
		}else if(cmd =="Remove from Group 2"){
			Object[] inds=group2List.getSelectedValues();
			for(int i=0;i<inds.length;i++){
				((DefaultListModel)group2List.getModel()).removeElement(inds[i]);
				((DefaultListModel)levelsList.getModel()).addElement(inds[i]);
			}
		}else if(cmd=="OK"){
			if(cut.isSelected()){
				model.isCut=true;
				model.cutPoint=cutValue.getText();
			}else{
				model.isCut=false;
				Vector vec = new Vector();
				DefaultListModel mod = (DefaultListModel)group1List.getModel();
				for(int i=0;i<mod.getSize();i++)
					vec.add(mod.get(i));
				model.group1=vec;
				vec = new Vector();
				mod = (DefaultListModel)group2List.getModel();
				for(int i=0;i<mod.getSize();i++)
					vec.add(mod.get(i));
				model.group2=vec;
			}
			this.dispose();
		}else if(cmd=="Cancel"){
			this.dispose();
		}
		
	}

	public void focusGained(FocusEvent evnt) {
		if(evnt.getSource()==cutValue){
			cut.setSelected(true);
		}else{
			define.setSelected(true);
		}
		
	}

	public void focusLost(FocusEvent arg0) {}

}

