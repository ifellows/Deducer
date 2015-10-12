package org.rosuda.deducer.data;

import javax.swing.JMenuBar;
import javax.swing.JPanel;

public abstract class DataViewerTab extends JPanel{
	
	public abstract void setData(String data);
	public abstract void refresh();
	public abstract JMenuBar generateMenuBar();
	public abstract void cleanUp();
}
