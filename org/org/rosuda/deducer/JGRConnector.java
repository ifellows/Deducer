package org.rosuda.deducer;

import org.rosuda.JGR.JGR;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngine;
import org.rosuda.REngine.REngineException;
import org.w3c.dom.Document;

public class JGRConnector implements RConnector{
	REngine engine = JGR.getREngine();
	public void execute(String cmd) {
		JGR.MAINRCONSOLE.execute(cmd);
	}

	public void execute(String cmd, boolean addToHist) {
		JGR.MAINRCONSOLE.execute(cmd,addToHist);
	}
	
	public void execute(String cmd,boolean addToHist,String title,Document xmlDialogState){
		JGR.MAINRCONSOLE.execute(cmd,addToHist);
	}

	public REXP eval(String cmd) {
		try {
			return engine.parseAndEval(cmd);
		} catch (REngineException e) {
			new ErrorMsg(e);
			return null;
		} catch (REXPMismatchException e) {
			new ErrorMsg(e);
			return null;
		}
	}

	public REXP idleEval(String cmd) {
		try {
			int lock = engine.tryLock();
			if(lock==0)
				return null;
			else{
				REXP e = engine.parseAndEval(cmd);
				engine.unlock(lock);
				return e;
			}
				
		} catch (REngineException e) {
			new ErrorMsg(e);
			return null;
		} catch (REXPMismatchException e) {
			new ErrorMsg(e);
			return null;
		}
	}

	public REngine getREngine() {
		// TODO Auto-generated method stub
		return engine;
	}

	
}
