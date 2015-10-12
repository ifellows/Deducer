package org.rosuda.deducer.models;

import javax.swing.DefaultListModel;

public class ModelModel extends Object{
	public DefaultListModel terms = new DefaultListModel();
	public String data = "";
	public String subset = "";
	public DefaultListModel outcomes = new DefaultListModel();
	public DefaultListModel outcomeVars = new DefaultListModel();
	public DefaultListModel numericVars = new DefaultListModel();
	public DefaultListModel factorVars = new DefaultListModel();
	

	
	public void copyInto(ModelModel model){
		model.data=data;
		model.subset=subset;
		for(int i =0;i<terms.getSize();i++)
			model.terms.addElement(terms.get(i));
		for(int i =0;i<outcomes.getSize();i++)
			model.outcomes.addElement(outcomes.get(i));
		for(int i =0;i<outcomeVars.getSize();i++)
			model.outcomeVars.addElement(outcomeVars.get(i));
		for(int i =0;i<numericVars.getSize();i++)
			model.numericVars.addElement(numericVars.get(i));
		for(int i =0;i<factorVars.getSize();i++)
			model.factorVars.addElement(factorVars.get(i));
	}
	

}
