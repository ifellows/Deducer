package org.rosuda.deducer.plots;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.deducer.toolkit.VariableSelector;
import org.rosuda.deducer.widgets.param.Param;
import org.rosuda.deducer.widgets.param.ParamWidget;


import javax.swing.BorderFactory;

import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;

public class LayerPanel extends ElementView implements ActionListener{
	private VariableSelector variableSelector;
	private JPanel paramPanel;
	private JButton positionButton;
	private JComboBox geomBox;
	private JComboBox statBox;
	private JScrollPane statScroller;
	private JPanel aesPanel;
	private JScrollPane geomScroller;
	private PositionPanel positionPanel;
	private JTabbedPane tabs;
	
	private Vector widgets = new Vector();
	
	private Layer model;

	
	public LayerPanel(Layer layer) {
		super();
		initGUI();
		setModel(layer);
	}
	
	public LayerPanel(){
		super();	
		initGUI();
	}
	
	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(437, 535));
			{
				positionButton = new JButton();
				this.add(positionButton, new AnchorConstraint(32, 294, 73, 74, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				positionButton.setText("Position");
				positionButton.setPreferredSize(new java.awt.Dimension(96, 22));
				positionButton.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						showPosition();
					}
					
				});
			}
			{
				ComboBoxModel geomBoxModel = 
					new DefaultComboBoxModel(PlotController.getGeomNames());
				geomBox = new JComboBox();
				this.add(geomBox, new AnchorConstraint(32, 973, 73, 685, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				geomBox.setModel(geomBoxModel);
				geomBox.setPreferredSize(new java.awt.Dimension(126, 22));
			}
			{
				ComboBoxModel statBoxModel = 
					new DefaultComboBoxModel(PlotController.getStatNames());
				statBox = new JComboBox();
				this.add(statBox, new AnchorConstraint(34, 671, 73, 376, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				statBox.setModel(statBoxModel);
				statBox.setPreferredSize(new java.awt.Dimension(129, 22));
			}
			{
				positionPanel = new PositionPanel();
				positionPanel.setPreferredSize(new java.awt.Dimension(180, 110));
				positionPanel.setMaximumSize(new java.awt.Dimension(190, 110));
				positionPanel.setAlignmentX(CENTER_ALIGNMENT);

			}
			{
				tabs = new JTabbedPane();
				this.add(tabs, new AnchorConstraint(85, 1001, 1000, 376, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				tabs.setPreferredSize(new java.awt.Dimension(273, 490));
				{
					geomScroller = new JScrollPane();
					geomScroller.setHorizontalScrollBarPolicy(
							ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					Border border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
					geomScroller.setBorder(border);
					tabs.addTab("Mappings", null, geomScroller, null);
					{
						aesPanel = new JPanel();
						geomScroller.setViewportView(aesPanel);
					}
				}
				{
					statScroller = new JScrollPane();
					statScroller.setHorizontalScrollBarPolicy(
							ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					Border border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
					statScroller.setBorder(border);
					tabs.addTab("Options", null, statScroller, null);
					{
						paramPanel = new JPanel();
						statScroller.setViewportView(paramPanel);
					}
				}
			}
			{
				variableSelector = new VariableSelector();
				this.add(variableSelector, new AnchorConstraint(96, 362, 991, 12, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				variableSelector.setPreferredSize(new java.awt.Dimension(153, 479));
				variableSelector.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				variableSelector.setCopyMode(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setModel(Layer layer){
		statBox.removeActionListener(this);
		geomBox.removeActionListener(this);
		model = layer;
		geomBox.setEnabled(true);
		statBox.setEnabled(true);
		geomBox.setSelectedItem(model.geom.name);
		statBox.setSelectedItem(model.stat.name);
		if(model.isGeom)
			geomBox.setEnabled(false);
		if(model.isStat)
			statBox.setEnabled(false);
		if(layer.data!=null)
			variableSelector.setSelectedData(layer.data);
		updateAesPanel();
		updateOptionsPanel();
		statBox.addActionListener(this);
		geomBox.addActionListener(this);		
	}
	
	public void updateModel(){
		for(int i=0;i<widgets.size();i++){
			Object o = widgets.get(i);
			if(o instanceof AesWidget){
				((AesWidget)o).updateModel();
			}else if(o instanceof ParamWidget){
				((ParamWidget)o).updateModel();
			}
		}
		model.data = variableSelector.getSelectedData();
	}
	
	public ElementModel getModel(){
		updateModel();
		return model;
	}
	
	public void updateAesPanel(){
		aesPanel.removeAll();
		BoxLayout thisLayout = new BoxLayout(aesPanel, javax.swing.BoxLayout.Y_AXIS);
		aesPanel.setLayout(thisLayout);
		for(int i=0;i<model.aess.size();i++){
			AesWidget a = new AesWidget();
			a.setAlignmentX(CENTER_ALIGNMENT);
			a.setVariableSelector(variableSelector);
			a.setModel(model.aess.get(i));
			a.setCalculatedVariables(model.stat.generated);
			widgets.add(a);
			aesPanel.add(a);
		}
		aesPanel.validate();
		aesPanel.repaint();
	}
	
	public void updateOptionsPanel(){
		paramPanel.removeAll();
		BoxLayout thisLayout = new BoxLayout(paramPanel, javax.swing.BoxLayout.Y_AXIS);
		paramPanel.setLayout(thisLayout);	
		Vector paramNames = new Vector();
		for(int i=0;i<model.stat.params.size();i++){
			Param p = (Param) model.stat.params.get(i);
			ParamWidget a = p.getView();
			a.setAlignmentX(CENTER_ALIGNMENT);
			paramNames.add(p.getTitle());			
			widgets.add(a);
			paramPanel.add(a);
		}
		for(int i=0;i<model.geom.params.size();i++){
			Param p = (Param) model.geom.params.get(i);
			ParamWidget a = p.getView();
			a.setAlignmentX(CENTER_ALIGNMENT);
			if(!paramNames.contains(a.getModel().getTitle())){
				widgets.add(a);
				paramPanel.add(a);
			}
		}
		paramPanel.validate();
		paramPanel.repaint();
	}
	
	public void showPosition(){
		final JDialog d = new JDialog();
		positionPanel.setModel(model.pos);
		d.setTitle("Position");
		BoxLayout lo = new BoxLayout(d.getContentPane(), javax.swing.BoxLayout.Y_AXIS);
		d.getContentPane().setLayout(lo);
		d.getContentPane().add(positionPanel);
		
		JButton b = new JButton("Okay");
		b.setPreferredSize(new Dimension(60,25));
		b.setMaximumSize(new Dimension(60,25));
		b.setMinimumSize(new Dimension(60,25));
		b.setAlignmentX(CENTER_ALIGNMENT);
		b.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				positionPanel.updateModel();
				d.dispose();
			}
			
		});
		d.getContentPane().add(b);
		d.setLocationRelativeTo(positionButton);
		d.setModal(true);
		d.pack();
		d.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		//System.out.println(cmd);
		if(arg0.getSource() == statBox){
			updateModel();
			String statName = statBox.getSelectedItem().toString();
			Stat s = Stat.makeStat(statName);
			model.setStat(s);
			setModel(model);
		}
		if(arg0.getSource() == geomBox){
			updateModel();
			String geomName = geomBox.getSelectedItem().toString();
			Geom g = Geom.makeGeom(geomName);
			model.setGeom(g);
			setModel(model);
		}
	}

	public void setModel(ElementModel el) {
		setModel((Layer) el);
	}
	
}

