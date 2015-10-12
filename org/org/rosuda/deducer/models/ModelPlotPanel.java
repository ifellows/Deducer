package org.rosuda.deducer.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.JGR.util.ErrorMsg;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.GDPreviewJPanel;


public class ModelPlotPanel extends javax.swing.JPanel implements ActionListener {
	private JPanel plotPanel;
	private JButton popOut;
	private String plotCall;
	private Integer devNum;


	
	public ModelPlotPanel(String call) {
		super();
		initGUI();
		plotCall = call;
		GDPreviewJPanel.plot(call);
		devNum = GDPreviewJPanel.mostRecentDevNumber;
		plotPanel = GDPreviewJPanel.mostRecent;
		if(plotPanel==null){
			Deducer.timedEval("cat('null canvas')");
			plotPanel = new JPanel();
		}
		this.add(plotPanel, new AnchorConstraint(1, 1001, 901, 1, AnchorConstraint.ANCHOR_REL, 
				AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(480, 349));
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			{
				popOut = new JButton();
				this.add(popOut, new AnchorConstraint(918, 980, 981, 407, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE));
				popOut.setText("Pop Out");
				popOut.setPreferredSize(new java.awt.Dimension(91, 22));
				popOut.addActionListener(this);
			}
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	
	public JPanel getPlot(){return plotPanel;}
	
	public void executeDevOff(){
		Deducer.timedEval("dev.off("+devNum+")");
	}

	public void actionPerformed(ActionEvent arg0) {
		Deducer.timedEval("dev.new()");
		String[] lines = plotCall.split("\n");
		for(int i=0;i<lines.length;i++)
			Deducer.timedEval(lines[i]);
	}
	
}
