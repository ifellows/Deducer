package org.rosuda.deducer;

import org.rosuda.JGR.JGRConsole;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.REngine.JRI.JRIEngine;
import org.rosuda.ibase.toolkit.TJFrame;

public class JGRConsolePlaceholder extends JGRConsole {
	private JRIEngine R;
	public JGRConsolePlaceholder(JRIEngine eng){
		super();
		R=eng;
	}
	
    public void execute(String command){
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
	public void executeLater(String cmd){
		execute(cmd);
	}
	public void execute(String cmd,boolean b){
		execute(cmd);
	}
	public void executeLater(String cmd,boolean b){
		execute(cmd);
	}
}
