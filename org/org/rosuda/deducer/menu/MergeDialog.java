package org.rosuda.deducer.menu;



import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import org.rosuda.JGR.*;
import org.rosuda.JGR.robjects.*;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.WindowTracker;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;

public class MergeDialog extends javax.swing.JDialog implements ActionListener{
	private JList dataList;
	private JLabel data1;
	private JLabel newDataLabel;
	private JTextField newName;
	private JList jList1;
	private JLabel data2;
	private OkayCancelPanel okcan;
	private static String lastSelected1;
	private static String lastSelected2;
	private static String lastNewData;
	private HelpButton help;

	
	public MergeDialog() {
		super();
		initGUI();
	}
	
	public MergeDialog(JFrame f) {
		super(f);
		initGUI();
	}
	
	private void initGUI() {
		try {
			RController.refreshObjects();
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			this.setTitle("Merge Data");
			{
				newName = new JTextField();
				getContentPane().add(newName, new AnchorConstraint(855, 428, 945, 110, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				if(lastNewData==null)
					newName.setText(Deducer.getUniqueName("data.merged"));
				else
					newName.setText(lastNewData);
				newName.setPreferredSize(new java.awt.Dimension(166, 22));
			}
			{
				newDataLabel = new JLabel();
				getContentPane().add(newDataLabel, new AnchorConstraint(766, 428, 827, 110, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				newDataLabel.setText("Merged Data Name:");
				newDataLabel.setPreferredSize(new java.awt.Dimension(166, 15));
			}
			{
				okcan = new OkayCancelPanel(false,false,this);
				getContentPane().add(okcan, new AnchorConstraint(827, 931, 977, 488, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				JButton cont = okcan.getApproveButton();
				cont.setText("Continue");				
				cont.setActionCommand("mergedata");				
			}
			{
				data2 = new JLabel();
				getContentPane().add(data2, new AnchorConstraint(136, 972, 176, 569, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
				data2.setText("Select Second Data Frame");
				data2.setPreferredSize(new java.awt.Dimension(186, 15));
			}
			{
				ListModel jList1Model = 
					new DefaultComboBoxModel();
				for(int i=0;i<Deducer.DATA.size();i++){
					((DefaultComboBoxModel)jList1Model).addElement(((RObject)Deducer.DATA.elementAt(i)).getName());
				}
				jList1 = new JList();
				JScrollPane pane = new JScrollPane(jList1,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);				
				getContentPane().add(pane, new AnchorConstraint(197, 926, 72, 569, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE));
				jList1.setModel(jList1Model);
				jList1.setPreferredSize(new java.awt.Dimension(149, 126));
				jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			}
			{
				data1 = new JLabel();
				getContentPane().add(data1, new AnchorConstraint(135, 444, 176, 29, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				data1.setText("Select First Data Frame:");
				data1.setPreferredSize(new java.awt.Dimension(173, 15));
			}
			{
				ListModel dataListModel = 
					new DefaultComboBoxModel();
				for(int i=0;i<Deducer.DATA.size();i++){
					((DefaultComboBoxModel)dataListModel).addElement(((RObject)Deducer.DATA.elementAt(i)).getName());
				}
				dataList = new JList();
				JScrollPane spane = new JScrollPane(dataList,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);	
				getContentPane().add(spane, new AnchorConstraint(197, 447, 72, 13, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
				dataList.setModel(dataListModel);
				dataList.setPreferredSize(new java.awt.Dimension(147, 126));
				dataList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			}
			if(lastSelected1!=null && lastSelected2!=null){
				dataList.setSelectedValue(lastSelected1, true);
				jList1.setSelectedValue(lastSelected2, true);
			}
			{
				help = new HelpButton("pmwiki.php?n=Main.MergeData");
				getContentPane().add(help, new AnchorConstraint(135, 444, 965, 12, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				help.setPreferredSize(new java.awt.Dimension(32, 32));				
			}
			pack();
			//this.setMinimumSize(new Dimension(430,268));
			this.setResizable(false);
			this.setSize(430, 268);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}

	public void actionPerformed(ActionEvent e) {
		String cmd= e.getActionCommand();
		if(cmd=="mergedata"){
			if(!newName.getText().startsWith("data.merged"))
				lastNewData =newName.getText();
			lastSelected1 = (String)dataList.getSelectedValue();
			lastSelected2 = (String)jList1.getSelectedValue();
			if(lastSelected1==null || lastSelected2==null || 
					lastSelected1.equals(lastSelected2)){
				JOptionPane.showMessageDialog(this,"Please Select Two Unique Data Frames to Merge");
				return;
			}
			MergeData inst = new MergeData(newName.getText(), lastSelected1,lastSelected2);
			inst.setLocationRelativeTo(this);
			inst.setVisible(true);
			WindowTracker.addWindow(inst);
			this.dispose();
		}else if(cmd == "Cancel"){
			this.dispose();
		}
	}
}
