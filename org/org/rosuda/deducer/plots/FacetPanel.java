package org.rosuda.deducer.plots;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;

import org.rosuda.deducer.widgets.VariableSelectorWidget;
import org.rosuda.deducer.widgets.param.Param;


public class FacetPanel extends ElementView {
	private VariableSelectorWidget variableSelector;
	private ParamFacetWidget facetWidget;
	
	private Facet model;
	
	public FacetPanel() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(413, 422));
			{
				variableSelector = new VariableSelectorWidget();
				this.add(variableSelector, new AnchorConstraint(53, 398, 972, 30, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				variableSelector.setPreferredSize(new java.awt.Dimension(152, 388));
				variableSelector.setCopyMode(true);
			}			
			{
				facetWidget = new ParamFacetWidget(variableSelector,new ParamFacet());
				this.add(facetWidget, new AnchorConstraint(150, 972, 850, 427, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				//facetWidget.setPreferredSize(new java.awt.Dimension(225, 216));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ElementModel getModel() {
		updateModel();
		return model;
	}

	public void setModel(ElementModel el) {
		model = (Facet) el;
		if(model.data!=null)
			variableSelector.setSelectedData(model.data);
		facetWidget.setModel(model.param);
		facetWidget.setType(model.facetType);
	}

	public void updateModel() {
		model.data = variableSelector.getSelectedData();
		model.facetType = facetWidget.getType();
		facetWidget.updateModel();
	}
	
	public VariableSelectorWidget getVariableSelector(){
		return variableSelector;
	}

}
