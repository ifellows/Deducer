package org.rosuda.deducer.toolkit;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;

import javax.swing.JPanel;

import org.rosuda.JGR.JGR;
import org.rosuda.JGR.toolkit.PrefDialog;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.deducer.Deducer;
import org.rosuda.ibase.Common;

public class PrefPanel extends PrefDialog.PJPanel implements ActionListener{
	private JPanel menuPanel;
	private JCheckBox data;
	private JCheckBox macChooser;
	private JPanel macPanel;
	private JCheckBox dataViewerStartUp;
	private JPanel dataViewerPanel;
	private JCheckBox analysis;

	public PrefPanel() {
		super();
		initGUI();
		reset();
		if(!Common.isMac()){
			macChooser.setSelected(false);
			macChooser.setEnabled(false);
		}
	}
	
	private void initGUI() {
		try {
			setPreferredSize(new Dimension(560, 400));
			this.setLayout(null);
			{
				menuPanel = new JPanel();
				menuPanel.setLayout(null);
				this.add(menuPanel);
				menuPanel.setBounds(118, 12, 305, 140);
				menuPanel.setBorder(BorderFactory.createTitledBorder("Console Menus"));
				{
					data = new JCheckBox();
					menuPanel.add(data);
					data.setText("'Data' menu at start-up");
					data.setBounds(17, 41, 271, 19);
				}
				{
					analysis = new JCheckBox();
					menuPanel.add(analysis);
					analysis.setText("'Analysis' menu at start-up");
					analysis.setBounds(17, 72, 271, 19);
				}
			}
			{
				dataViewerPanel = new JPanel();
				this.add(dataViewerPanel);
				dataViewerPanel.setBounds(118, 158, 305, 105);
				dataViewerPanel.setBorder(BorderFactory.createTitledBorder("Data Viewer"));
				dataViewerPanel.setLayout(null);
				{
					dataViewerStartUp = new JCheckBox();
					dataViewerPanel.add(dataViewerStartUp);
					dataViewerStartUp.setText("Show Data Viewer on Start-up");
					dataViewerStartUp.setBounds(17, 43, 271, 19);
				}
			}
			{
				macPanel = new JPanel();
				this.add(macPanel);
				macPanel.setBounds(118, 269, 305, 75);
				macPanel.setBorder(BorderFactory.createTitledBorder("Mac OS X"));
				macPanel.setLayout(null);
				{
					macChooser = new JCheckBox();
					macPanel.add(macChooser);
					macChooser.setText("Native Look and Feel (quaqua)");
					macChooser.setBounds(17, 32, 283, 19);
				}
				//NOTE: quaqua always disabled due to lion incompatability
				macPanel.setVisible(false);
			}
			this.setName("Deducer");
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	
	public void saveAll(){
		DeducerPrefs.SHOWANALYSIS = analysis.isSelected();
		DeducerPrefs.SHOWDATA= data.isSelected();
		//DeducerPrefs.USEQUAQUACHOOSER = macChooser.isSelected();
		//if(DeducerPrefs.USEQUAQUACHOOSER)
		//	Deducer.timedEval(".jChooserMacLAF()");
		DeducerPrefs.VIEWERATSTARTUP = dataViewerStartUp.isSelected();
		DeducerPrefs.writePrefs();
	}
	
	public void reset(){
		analysis.setSelected(DeducerPrefs.SHOWANALYSIS);
		data.setSelected(DeducerPrefs.SHOWDATA);
		macChooser.setSelected(DeducerPrefs.USEQUAQUACHOOSER);
		dataViewerStartUp.setSelected(DeducerPrefs.VIEWERATSTARTUP);
	}
	
	public void resetToFactory(){
		analysis.setSelected(true);
		data.setSelected(true);
		macChooser.setSelected(Common.isMac() ? true : false);
		dataViewerStartUp.setSelected(true);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd == "Save All"){
			saveAll();
		}else if(cmd == "Cancel"){
			reset();
		}else if(cmd == "Reset All"){
			resetToFactory();
		}
		
	}

}
