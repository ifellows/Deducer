package org.rosuda.deducer;

import org.rosuda.JGR.JGR;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngine;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.JRI.JRIEngine;
import org.w3c.dom.Document;

public class DefaultRConnector implements RConnector{
	private JRIEngine R;
	
	public DefaultRConnector(JRIEngine eng){
		R=eng;
		JGR.setREngine(eng);
	}
	
	public void execute(String command) {
    	final String cmd = command;
        final String[] cmds = cmd.split("\n");
		    		int l = R.lock();
		    		try {
		    			R.parseAndEval("cat('\\n-- Deducer Command --\\n')");
		    			for(int i=0;i<cmds.length;i++)
		    				R.parseAndEval("cat(\""+Deducer.addSlashes(cmds[i])+"\\n\")");
		    			R.parseAndEval("cat('-- End Command     --\\n')");
		    			R.parseAndEval(".deducerExecute(\""+Deducer.addSlashes(cmd)+"\\n\")");
		    		} catch (Exception e){
		    			new ErrorMsg(e);
		    		}finally{
		    			R.unlock(l);
		    		}
	}

	public void execute(String cmd, boolean addToHist) {
		execute(cmd);
	}
	
	public void execute(String cmd,boolean addToHist,String title,Document xmlDialogState){
		execute(cmd);
	}

	public REXP eval(String cmd) {
		try {
			return R.parseAndEval(cmd);
		} catch (REngineException e) {
			e.printStackTrace();
			return null;
		} catch (REXPMismatchException e) {
			e.printStackTrace();
			return null;
		}
	}

	public REXP idleEval(String cmd) {
		try {
			int lock = R.tryLock();
			if(lock==0)
				return null;
			else{
				REXP e = R.parseAndEval(cmd);
				R.unlock(lock);
				return e;
			}
				
		} catch (REngineException e) {
			e.printStackTrace();
			return null;
		} catch (REXPMismatchException e) {
			e.printStackTrace();
			return null;
		}
	}

	public REngine getREngine() {
		return R;
	}

}
