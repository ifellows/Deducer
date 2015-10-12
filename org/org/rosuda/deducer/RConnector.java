package org.rosuda.deducer;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REngine;
import org.w3c.dom.Document;

public interface RConnector {

	public void execute(String cmd);
	public void execute(String cmd,boolean addToHist);
	public void execute(String cmd,boolean addToHist,String title,Document xmlDialogState);
	public REXP eval(String cmd);
	public REXP idleEval(String cmd);
	public REngine getREngine();
}
