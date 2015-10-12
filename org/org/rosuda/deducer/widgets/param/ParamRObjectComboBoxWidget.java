package org.rosuda.deducer.widgets.param;

import javax.swing.DefaultComboBoxModel;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.deducer.Deducer;

public class ParamRObjectComboBoxWidget extends ParamComboBoxWidget{
	
	public ParamRObjectComboBoxWidget(){
		super();
	}
	
	public ParamRObjectComboBoxWidget(Param p){
		super();
		setModel(p);
	}
	
	public void setModel(Param p){
		model = p;
		initAsComboBox(false);
		label.setText(p.getTitle());
		String className = ((ParamRObject)p).getRObjectClass();
		String call;
		if(className!=null)
			call = "get.objects('"+className+"')";
		else
			call = "get.objects()";
		String[] objs;
		try {
			objs = Deducer.timedEval(call).asStrings();
		} catch (Exception e) {
			objs = new String[]{};
		}
		if(objs == null)
			objs = new String[]{};
		
		DefaultComboBoxModel comboBoxModel = (DefaultComboBoxModel) comboBox.getModel();
		comboBoxModel.removeAllElements();
		comboBoxModel.addElement(null);
		for(int i=0;i<objs.length;i++)
			comboBoxModel.addElement(objs[i]);
		if(p.getValue()!=null)
			comboBox.setSelectedItem(p.getValue());
		else if(p.getDefaultValue()!=null)
			comboBox.setSelectedItem(p.getDefaultValue());
	}
	
	public void updateModel(){
		String val = (String) comboBox.getSelectedItem();
		if(val!=null && val.length()>0)
			model.setValue(val);
		else
			model.setValue(null);
	}
	
	
}
