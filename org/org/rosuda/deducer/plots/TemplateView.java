package org.rosuda.deducer.plots;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.deducer.widgets.VariableSelectorWidget;
import org.rosuda.deducer.widgets.param.Param;
import org.rosuda.deducer.widgets.param.ParamWidget;

public class TemplateView extends ElementView{
	protected JScrollPane scroller;
	protected JPanel paramPanel;
	protected Template model;
	protected VariableSelectorWidget selector;
	
	protected Vector widgets = new Vector();
	
	public TemplateView(){
		initGui();
	}
	
	public TemplateView(Template el){
		initGui();
		setModel(el);
	}
	
	
	private void initGui(){
		paramPanel = new JPanel();
		scroller = new JScrollPane();
		scroller.setViewportView(paramPanel);
		scroller.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Border border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		scroller.setBorder(border);
		if(selector == null){
			selector = new VariableSelectorWidget();
			selector.setPreferredSize(new Dimension(150,300));
			selector.setCopyMode(true);
		}
		this.removeAll();
		AnchorLayout thisLayout = new AnchorLayout();
		this.setLayout(thisLayout);
		this.add(scroller, new AnchorConstraint(60, 1000, 1000, 160, 
				AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL,
				AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
		this.add(selector, new AnchorConstraint(20, 90, 1000, 10, 
				AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE,
				AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));	
	}
	
	public void updatePanel(){
		paramPanel.removeAll();
		BoxLayout thisLayout = new BoxLayout(paramPanel, javax.swing.BoxLayout.Y_AXIS);
		paramPanel.setLayout(thisLayout);	

		for(int i=0;i<model.getAess().size();i++){
			Aes p = (Aes) model.getAess().get(i);
			AesWidget a = new AesWidget();
			a.setVariableSelector(selector);
			a.setModel(p);
			a.setShowToggle(false);
			a.setAlignmentX(CENTER_ALIGNMENT);
			a.setMaximumSize(new Dimension(365,a.getMaximumSize().height));	
			a.setCalculatedVariables(((Template.MaskingAes)model.getMAess().get(i)).generated);
			widgets.add(a);
			paramPanel.add(a);
			paramPanel.add(Box.createRigidArea(new Dimension(0,10)));
		}
		

		for(int i=0;i<model.getParams().size();i++){
			Param p = (Param) model.getParams().get(i);
			ParamWidget a;
			if(p.requiresVariableSelector())
				a = p.getView(selector);
			else
				a = p.getView();
			a.setAlignmentX(CENTER_ALIGNMENT);
			a.setMaximumSize(new Dimension(365,a.getMaximumSize().height));	
			widgets.add(a);
			paramPanel.add(a);
			paramPanel.add(Box.createRigidArea(new Dimension(0,10)));
		}
		paramPanel.validate();
		paramPanel.repaint();
	}
	
	public ElementModel getModel() {
		updateModel();
		return model;
	}

	public void setModel(Template el) {
		model = el;
		if(selector.getSelectedData()==null || 
				!selector.getSelectedData().equals(model.getData()) && model.getData()!=null)
			selector.setSelectedData(model.getData());
		updatePanel();
	}

	public void updateModel() {
		model.setData(selector.getSelectedData());
		for(int i=0;i<widgets.size();i++){
			Object o = widgets.get(i);
			if(o instanceof AesWidget){
				((AesWidget)o).updateModel();
			}else if(o instanceof ParamWidget){
				((ParamWidget)o).updateModel();
			}
		}
		model.updateElementModels();
	}

	public void setModel(ElementModel el) {
		setModel((Template) el);
	}
}
