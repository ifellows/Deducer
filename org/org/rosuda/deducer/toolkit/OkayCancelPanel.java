package org.rosuda.deducer.toolkit;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.JFrame;



public class OkayCancelPanel extends JPanel {
	private JButton okayButton;
	private JButton resetButton;
	private JButton cancelButton;

	
	public OkayCancelPanel(boolean showReset,boolean isRun,ActionListener lis) {
		super();
		initGUI(showReset?1:0,System.getProperty("os.name").startsWith("Window"));
		if(!showReset)
			resetButton.setVisible(false);
		if(isRun)
			okayButton.setText("Run");
		if(lis!=null){
			resetButton.addActionListener(lis);
			okayButton.addActionListener(lis);
			cancelButton.addActionListener(lis);
		}
	}
	
	public OkayCancelPanel(boolean showReset,boolean isRun) {
		super();
		initGUI(showReset?1:0,System.getProperty("os.name").startsWith("Window"));
		if(!showReset)
			resetButton.setVisible(false);
		if(isRun)
			okayButton.setText("Run");
	}
	
	public void addActionListener(ActionListener lis){
		if(lis!=null){
			resetButton.addActionListener(lis);
			okayButton.addActionListener(lis);
			cancelButton.addActionListener(lis);
		}		
	}
	
	public void removeAllActionListeners(){
		ActionListener[] ls = resetButton.getActionListeners();
		for(int i=0;i<ls.length;i++)
			resetButton.removeActionListener(ls[i]);
		 ls = okayButton.getActionListeners();
		for(int i=0;i<ls.length;i++)
			okayButton.removeActionListener(ls[i]);
		ls = cancelButton.getActionListeners();
		for(int i=0;i<ls.length;i++)
			cancelButton.removeActionListener(ls[i]);
		
	}
	
	private void initGUI(int reset,boolean windowsOrder) {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(365, 57));
			AnchorConstraint resetConst;
			AnchorConstraint okayConst;
			AnchorConstraint cancelConst;
			if(reset==1){
				resetConst = new AnchorConstraint(8, 310, 798, 9,
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL);
				okayConst = new AnchorConstraint(8, 1001, 1008, 700, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL);
				cancelConst = new AnchorConstraint(8, 660, 798, 360, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL);
			}else{
				resetConst = new AnchorConstraint(8, 310, 798, 9,
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL);
				okayConst = new AnchorConstraint(8, 1000, 1008, 550, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL);
				cancelConst = new AnchorConstraint(8, 450, 798, 0, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL);
			}
			if(windowsOrder){
				AnchorConstraint tmp;
				tmp =okayConst;
				if(reset==0){
					okayConst=cancelConst;
					cancelConst=tmp;
				}else{
					okayConst=resetConst;
					resetConst=cancelConst;
					cancelConst=tmp;
				}
			}
			{
				resetButton = new JButton();
				this.add(resetButton, resetConst);
				resetButton.setText("Reset");
				resetButton.setPreferredSize(new java.awt.Dimension(110, 32));
			}
			{
				cancelButton = new JButton();
				this.add(cancelButton, cancelConst);
				cancelButton.setText("Cancel");
				
				cancelButton.setPreferredSize(new java.awt.Dimension(109, 32));
			}
			{
				okayButton = new JButton();
				this.add(okayButton, okayConst);
				okayButton.setText("OK");
				okayButton.setPreferredSize(new java.awt.Dimension(109, 32));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JButton getApproveButton(){
		return okayButton;
	}
	
	public JButton getResetButton(){
		return resetButton;
	}
	
	public JButton getCancelButton(){
		return cancelButton;
	}

}

