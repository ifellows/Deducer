package org.rosuda.deducer.plots;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class ScaleBrewerPanel extends DefaultElementView{

	public ScaleBrewerPanel(ElementModel em){
		super(em);
	}
	
	
	public void updatePanel(){
		super.updatePanel();
		URL url = getClass().getResource("/icons/ggplot_icons/brewer_palettes.png");
		JLabel brewer = new JLabel(new ImageIcon(url));
		brewer.setAlignmentX(CENTER_ALIGNMENT);
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		p.add(brewer);
		p.setMaximumSize(new Dimension(261,564));
		paramPanel.add(p);
		paramPanel.validate();
	}
}
