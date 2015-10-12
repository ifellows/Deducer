package org.rosuda.deducer.plots;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class PlottingElementDialog extends javax.swing.JDialog implements ActionListener {
	private JPanel panel;
	private JPanel okayCancel;
	private HelpButton help;
	private ElementView view;
	private ElementModel initialModel;
	private PlottingElement element;
	
	private int exitType = -1;
	
	public PlottingElementDialog(JFrame frame,PlottingElement el) {
		super(frame);
		initGUI();
		setElement(el);
	}
	
	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			getContentPane().setLayout(thisLayout);
			{
				help = new HelpButton("");
				getContentPane().add(help, new AnchorConstraint(923, 92, 12, 12, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
				help.setPreferredSize(new java.awt.Dimension(36, 36));
			}
			{
				okayCancel = new OkayCancelPanel(false,false,this);
				getContentPane().add(okayCancel, new AnchorConstraint(923, 21, 0, 521, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE));
				okayCancel.setPreferredSize(new java.awt.Dimension(195, 38));
			}
			{
				panel = new JPanel();
				BorderLayout panelLayout = new BorderLayout();
				panel.setLayout(panelLayout);
				getContentPane().add(panel, new AnchorConstraint(1, 994, 44, 1, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL));
				panel.setPreferredSize(new java.awt.Dimension(447, 449));
			}
			this.setSize(450, 515);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setElement(PlottingElement el){
		setView((ElementView) el.getPanel());
		initialModel = (ElementModel) el.getModel().clone();
		help.setUrl(el.getUrl());
		help.setToolTipText("Open online help from: " +el.getUrl());
		element = el;
	}
	
	public void setView(ElementView v){
		view = v;
		panel.removeAll();
		panel.add(view);
		
	}
	
	public void setToInitialModel(){
		element.setModel(initialModel);
	}
	
	public int getExitType(){return exitType;}
	
	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if(cmd == "OK"){
			view.updateModel();
			String s = view.getModel().checkValid();
			if(s!=null){
				JOptionPane.showMessageDialog(this, s);
			}else{
				exitType=1;
				this.dispose();
			}
		}else if(cmd == "Cancel"){
			exitType = 0;
			setToInitialModel();
			this.dispose();
		}
	}
}
