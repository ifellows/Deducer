package org.rosuda.deducer.toolkit;

import javax.swing.SwingUtilities;

import org.rosuda.JGR.TxtTableLoader;
import org.rosuda.deducer.Deducer;

public class LoadData extends org.rosuda.JGR.DataLoader{

	public void loadTxtFile(String fileName, String directory, String rName) {
		final String theFile = directory.replace('\\','/') + fileName;
		final String theName = rName;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				TxtTableLoader inst = new TxtTableLoader(theFile, theName){
					public void execute(String cmd,boolean hist){
						Deducer.execute(cmd,hist);
					}
				};
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);

			}
		});
	}
	
	public void execute(String cmd,boolean show){
		Deducer.execute(cmd,show);
	}
	
	public String getUniqueName(String name){
		return Deducer.getUniqueName(name);
	}
}


