package org.rosuda.deducer.models;

import javax.swing.JEditorPane;

public class PreviewComponentFactory {

	public JEditorPane makePreviewComponent(){
		JEditorPane preview = new JEditorPane();
		preview.setFont(new java.awt.Font("Monospaced",0,12));
		preview.setEditable(false);
		return preview;
	}
	
	
}
