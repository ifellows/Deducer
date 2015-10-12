package org.rosuda.deducer.widgets.param;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.WindowTracker;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;
import org.rosuda.deducer.widgets.VariableSelectorWidget;


public class RFunctionListDialog extends  JDialog implements ActionListener{
	private JPanel panel;
	private OkayCancelPanel okayCancel;
	private HelpButton help;
	private ParamWidget view;
	private RFunctionList initialModel;
	private RFunctionList model;
	private VariableSelectorWidget selector;
	private JScrollPane scroller;
	private Param[] globalParams = new Param[]{};
	private boolean isRun = true;
	
	public RFunctionListDialog(JFrame frame,RFunctionList el) {
		super(frame);
		try{
			initGUI();
			setModel(el);
			setRun(true);
		}catch(Exception ex){ex.printStackTrace();}

	}
	
	public RFunctionListDialog(RFunctionList el) {
		super();
		try{
			initGUI();
			setModel(el);
			setRun(true);
		}catch(Exception ex){ex.printStackTrace();}

	}
	
	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			{
				help = new HelpButton("");
				this.add(help, new AnchorConstraint(923, 92, 12, 12, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
				help.setPreferredSize(new java.awt.Dimension(29, 26));
				help.setVisible(false);
			}
			{
				okayCancel = new OkayCancelPanel(false,false,this);
				this.add(okayCancel, new AnchorConstraint(923, 21, 0, 521, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE));
				okayCancel.setPreferredSize(new java.awt.Dimension(195, 38));
			}
			{
				panel = new JPanel();
			}		
			{
				scroller = new JScrollPane();
				scroller.setHorizontalScrollBarPolicy(
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				Border border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
				scroller.setBorder(border);
				scroller.setViewportView(panel);		
			}
			if(selector == null){
				selector = new VariableSelectorWidget();
				selector.setPreferredSize(new Dimension(150,300));
			}
			this.setSize(450, 515);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setModel(RFunctionList el){
		
		if(globalParams==null || globalParams.length==0){
			if(el.requiresVariableSelector())
				panel = view = el.getView(selector);
			else 
				panel = view = el.getView();
		}else{
			if(el.requiresVariableSelector()){
				RFunctionListPanelWidget w = new RFunctionListPanelWidget(el,selector);
				panel = view = w;
			}else{
				RFunctionListPanelWidget w = new RFunctionListPanelWidget(el);
				panel = view = w;
			}
		}
		scroller.setViewportView(panel);
		
		initialModel = (RFunctionList) el.clone();
		model = el;
		this.setTitle(el.getName());
		this.remove(scroller);
		
		boolean showSelector = showSelector = model.requiresVariableSelector();
		if(showSelector){
			this.add(scroller, new AnchorConstraint(60, 10, 50, 170, 
					AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS,
					AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
			this.add(selector, new AnchorConstraint(20, 90, 50, 10, 
					AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE,
					AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));		
		}else{
			this.remove(scroller);
			if(selector!=null)
				this.remove(selector);
			this.add(scroller, new AnchorConstraint(60, 1000, 50, 1, 
					AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL,
					AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
		}	
		validate();
		repaint();
	}
	
	
	public void setToInitialModel(){
		RFunctionList newModel = (RFunctionList) initialModel.clone();
		setModel(newModel);
	}
	
	public void run(){
		try{
			if(selector!=null){
				selector.refreshDataNames();
				selector.setSelectedData(selector.getSelectedData());
			}
			if(initialModel!=null)
				this.setToInitialModel();
			this.setVisible(true);
			if(!Deducer.isJGR()){
				WindowTracker.addWindow(this);
				WindowTracker.waitForAllClosed();
			}
		}catch(Exception ex){ex.printStackTrace();}
	}
	
	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if(cmd == "OK"){
			view.updateModel();
			String s = model.checkValid();
			if(s!=null){
				JOptionPane.showMessageDialog(this, s);
			}else{
				initialModel = (RFunctionList) model.clone();
				this.setVisible(false);
			}
		}else if(cmd == "Cancel"){
			setToInitialModel();
			this.setVisible(false);
		}else if(cmd == "Run"){
			view.updateModel();
			String s = model.checkValid();
			if(s!=null){
				JOptionPane.showMessageDialog(this, s);
			}else{
				initialModel = (RFunctionList) model.clone();
				this.setVisible(false);
				Deducer.execute(model.getCall());
			}
		}
	}

	private HelpButton getHelpButton() {
		return help;
	}

	public void setRun(boolean isRun) {
		if(isRun)
			okayCancel.getApproveButton().setText("Run");
		else
			okayCancel.getApproveButton().setText("OK");
		this.isRun = isRun;
	}

	public boolean isRun() {
		return isRun;
	}
	
}




