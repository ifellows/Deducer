package org.rosuda.deducer.widgets.param;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.deducer.widgets.VariableListWidget;
import org.rosuda.deducer.widgets.VariableSelectorWidget;

public class ParamMultipleVariablesWidget extends ParamWidget {
	VariableSelectorWidget selector;
	VariableListWidget variables;
	ParamMultipleVariables model;
	
	public ParamMultipleVariablesWidget(ParamMultipleVariables p, VariableSelectorWidget sel){
		super();
		selector = sel;		
		initAsVariable();
		setModel(p);		
	}
	
	public void setModel(Param p) {
		model =(ParamMultipleVariables) p;
		String curdat = selector.getSelectedData();
		if(model.getData() !=null && !model.getData().equals(curdat)){
			selector.setSelectedData(model.getData());
		}
		if(model.getVariables()!=null){
			variables.setModel(model.getVariables(), true);			
		}
		if(model!=null && model.title!=null){
			variables.setTitle(model.title, true);
		}
	}

	public void updateModel() {
		model.setVariables(variables.getVariables());
		model.setData(selector.getSelectedData());
	}

	
	protected void initAsVariable(){
		this.removeAll();
		AnchorLayout thisLayout = new AnchorLayout();
		this.setLayout(thisLayout);
		this.setPreferredSize(new java.awt.Dimension(241, 150));
		this.setMaximumSize(new java.awt.Dimension(2000, 150));
		variables = new VariableListWidget(selector);

		this.add(variables, new AnchorConstraint(0, 10, 1000, 16, 
				AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, 
				AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
	}

}
