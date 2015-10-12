package org.rosuda.deducer.plots;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class PlottingElementMenuDialog extends javax.swing.JDialog implements ActionListener {
	private JPanel panel;
	private OkayCancelPanel okayCancel;
	private HelpButton help;
	private ElementView view;
	private ElementModel initialModel;
	private PlottingElement element;
	
	public PlottingElementMenuDialog(JFrame frame,PlottingElement el) {
		super(frame);
		try{
			initGUI();
			initialModel = (ElementModel) el.getModel().clone();
			setElement(el);
		}catch(Exception e){e.printStackTrace();}
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
				okayCancel = new OkayCancelPanel(true,true,this);
				getContentPane().add(okayCancel, new AnchorConstraint(923, 21, 0, 521, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE));
				okayCancel.setPreferredSize(new java.awt.Dimension(250, 38));
				okayCancel.getResetButton().setText("Builder");
				okayCancel.getResetButton().setActionCommand("Builder");
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
		this.setTitle(element.getName());
	}
	
	public void setView(ElementView v){
		view = v;
		panel.removeAll();
		panel.add(view);
		
	}
	
	public void setToInitialModel(){
		element.setModel(initialModel);
	}
	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if(cmd == "Run"){
			view.updateModel();
			String s = view.getModel().checkValid();
			if(s!=null){
				JOptionPane.showMessageDialog(this, s);
			}else{
				PlotBuilderModel b = new PlotBuilderModel();
				b.getListModel().addElement(element);
				Deducer.execute(b.getCall());
				this.dispose();
			}
		}else if(cmd == "Builder"){
			view.updateModel();
			String s = view.getModel().checkValid();
			if(s!=null){
				JOptionPane.showMessageDialog(this, s);
			}else{
				PlotBuilderModel b = new PlotBuilderModel();
				b.getListModel().addElement(element.clone());
				PlotBuilder pb = new PlotBuilder(b);
				pb.setLocationRelativeTo(this);
				this.dispose();
				pb.setVisible(true);
			}
		}else if(cmd == "Cancel"){
			setToInitialModel();
			this.dispose();
		}
	}
}