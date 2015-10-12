package org.rosuda.deducer.toolkit;

import java.util.Vector;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.deducer.Deducer;
import org.rosuda.JGR.robjects.*;

public class DefaultVariableSource implements VariableSource{

	public String[] getDataObjects() {

		Deducer.refreshData();
		Vector vals = Deducer.getData();
		String[] values = new String[vals.size()];
		for(int i=0;i<vals.size();i++){
			values[i] = ((RObject)vals.get(i)).getName();
		}
		return values;
	}

	public String[] getVariableNames(String dataName) {
		String[] vals = new String[]{};
		try {
			vals=Deducer.timedEval("names("+dataName+")").asStrings();
		} catch (Exception e) {
		}
		return vals;
	}

	public String fix(String data, String variable) {
		return data + "[[\"" + variable + "\"]]";
	}


}
