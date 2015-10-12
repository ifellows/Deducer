package org.rosuda.deducer.widgets.param;


import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.deducer.widgets.SingleVariableWidget;
import org.rosuda.deducer.widgets.VariableSelectorWidget;

public class ParamVariableWidget extends ParamWidget{
	VariableSelectorWidget selector;
	SingleVariableWidget variable;
	ParamVariable model;
	ParamVariableWidget(ParamVariable p, VariableSelectorWidget sel){
		super();
		selector = sel;		
		initAsVariable();
		setModel(p);		
	}
	
	public void setModel(Param p) {
		model =(ParamVariable) p;
		String curdat = selector.getSelectedData();
		if(model.getData() !=null && !model.getData().equals(curdat)){
			selector.setSelectedData(model.getData());
		}
		if(model.getVariable()!=null){
			variable.setSelectedVariable(model.getVariable());			
		}
		if(model!=null && model.title!=null){
			variable.setTitle(model.title, true);
		}
	}

	public void updateModel() {
		model.setVariable(variable.getSelectedVariable());
		model.setData(selector.getSelectedData());
	}

	
	protected void initAsVariable(){
		this.removeAll();
		AnchorLayout thisLayout = new AnchorLayout();
		this.setLayout(thisLayout);
		variable = new SingleVariableWidget(selector);
		this.setSize(241,75);
		this.add(variable, new AnchorConstraint(0, 10, 1000, 16, 
				AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, 
				AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
		this.setPreferredSize(new java.awt.Dimension(241, 75));
		this.setMaximumSize(new java.awt.Dimension(2000, 75));
	}

}
