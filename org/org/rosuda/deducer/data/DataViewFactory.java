package org.rosuda.deducer.data;

public class DataViewFactory implements DataViewerTabFactory{

	public DataViewerTab makeViewerTab(String dataName) {
		return new DataView(dataName);
	}

}
