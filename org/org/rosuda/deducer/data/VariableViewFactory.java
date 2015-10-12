package org.rosuda.deducer.data;

public class VariableViewFactory implements DataViewerTabFactory{

	public DataViewerTab makeViewerTab(String dataName) {
		return new VariableView(dataName);
	}

}
