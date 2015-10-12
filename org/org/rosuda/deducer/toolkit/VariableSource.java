package org.rosuda.deducer.toolkit;

public interface VariableSource {

	public abstract String[] getDataObjects();
	public abstract String[] getVariableNames(String data);
	public abstract String fix(String data, String variable);
}
