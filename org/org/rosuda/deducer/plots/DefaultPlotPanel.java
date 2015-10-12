package org.rosuda.deducer.plots;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class DefaultPlotPanel extends javax.swing.JPanel implements ActionListener {
	private JLabel label;
	private JButton histogram;
	private JPanel buttonPanel;
	private JButton barplot;
	private JButton line;
	private JButton dotPlot;
	private JButton mean;
	private JButton densTwo;
	private JLabel jLabel1;
	private JLabel orLabel;
	private JButton scatterPlot;
	private JButton bubble;

	PlotBuilder builder;
	
	public DefaultPlotPanel(PlotBuilder builder) {
		super();
		this.builder = builder;
		initGUI();
	}
	
	private void initGUI() {
		try {
			JPanel p ;
			setPreferredSize(new Dimension(400, 300));
			BoxLayout thisLayout = new BoxLayout(this, javax.swing.BoxLayout.Y_AXIS);
			this.setLayout(thisLayout);
			this.add(Box.createRigidArea(new Dimension(0, 20)));

			{
				label = new JLabel();
				label.setAlignmentX(Component.CENTER_ALIGNMENT);
				this.add(label);
				label.setText("Drag a component from above");
				label.setFont(new java.awt.Font("Dialog",0,18));
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setPreferredSize(new java.awt.Dimension(376, 38));
			}
			{
				this.add(Box.createRigidArea(new Dimension(0, 5)));
				orLabel = new JLabel();
				orLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				this.add(orLabel);
				orLabel.setText("OR");
				orLabel.setHorizontalAlignment(SwingConstants.CENTER);
				orLabel.setFont(new java.awt.Font("Dialog",2,26));
				orLabel.setPreferredSize(new java.awt.Dimension(376, 32));
			}
			{
				this.add(Box.createRigidArea(new Dimension(0, 5)));
				jLabel1 = new JLabel();
				jLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
				this.add(jLabel1);
				jLabel1.setText("Select a plot type:");
				jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
				jLabel1.setFont(new java.awt.Font("Dialog",0,18));
				jLabel1.setPreferredSize(new java.awt.Dimension(376, 38));
			}
			{
				this.add(Box.createRigidArea(new Dimension(0, 20)));
				buttonPanel = new JPanel();
				buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
				GridLayout buttonPanelLayout = new GridLayout(2, 4);
				//buttonPanelLayout.setColumns(1);
				buttonPanelLayout.setHgap(5);
				buttonPanelLayout.setVgap(5);
				buttonPanel.setPreferredSize(new java.awt.Dimension(400, 200));
				buttonPanel.setMaximumSize(new java.awt.Dimension(400, 200));
				this.add(buttonPanel);
				buttonPanel.setLayout(buttonPanelLayout);
				int butWid =80;
				int butHt = 90;
				{
					histogram = new JButton();
					buttonPanel.add(histogram);
					histogram.setPreferredSize(new java.awt.Dimension(butWid, butHt));
					histogram.setBounds(64, 125, butWid, butHt);
					p = PlottingElement.createElement("template","histogram").makeComponent();
					p.setBorder(null);
					histogram.add(p);
					histogram.addActionListener(this);
				}				
				{
					barplot = new JButton();
					buttonPanel.add(barplot);
					barplot.setPreferredSize(new java.awt.Dimension(butWid, butHt));
					barplot.setBounds(132, 126, butWid, butHt);
					p = PlottingElement.createElement("template","bar").makeComponent();
					p.setBorder(null);
					barplot.add(p);
					barplot.addActionListener(this);
				}
				{
					mean = new JButton();
					buttonPanel.add(mean);
					mean.setPreferredSize(new java.awt.Dimension(butWid,butHt));
					mean.setBounds(202, 126, butWid, butHt);
					p = PlottingElement.createElement("template","mean").makeComponent();
					p.setBorder(null);
					mean.add(p);
					mean.addActionListener(this);
				}
				{
					scatterPlot = new JButton();
					buttonPanel.add(scatterPlot);
					scatterPlot.setPreferredSize(new java.awt.Dimension(butWid, butHt));
					scatterPlot.setBounds(272, 126, butWid, butHt);
					p = PlottingElement.createElement("template","scatter").makeComponent();
					p.setBorder(null);
					scatterPlot.add(p);
					scatterPlot.addActionListener(this);
				}

				{
					dotPlot = new JButton();
					buttonPanel.add(dotPlot);
					dotPlot.setPreferredSize(new java.awt.Dimension(butWid, butHt));
					dotPlot.setBounds(62, 206, butWid, butHt);
					p = PlottingElement.createElement("template","grouped_dotplot").makeComponent();
					p.setBorder(null);
					dotPlot.add(p);
					dotPlot.addActionListener(this);
				}
				{
					line = new JButton();
					buttonPanel.add(line);
					line.setPreferredSize(new java.awt.Dimension(butWid, butHt));
					line.setBounds(132, 206, butWid, butHt);
					p = PlottingElement.createElement("template","grouped_line").makeComponent();
					p.setBorder(null);
					line.add(p);
					line.addActionListener(this);
				}
				{
					densTwo = new JButton();
					buttonPanel.add(densTwo);
					densTwo.setPreferredSize(new java.awt.Dimension(butWid, butHt));
					densTwo.setBounds(202, 206, butWid, butHt);
					p = PlottingElement.createElement("template","histogram_2d").makeComponent();
					p.setBorder(null);
					densTwo.add(p);
					densTwo.addActionListener(this);
				}				
				{
					bubble = new JButton();
					buttonPanel.add(bubble);
					bubble.setPreferredSize(new java.awt.Dimension(butWid, butHt));
					bubble.setBounds(272, 206, butWid, butHt);
					p = PlottingElement.createElement("template","bubble").makeComponent();
					p.setBorder(null);
					bubble.add(p);
					bubble.addActionListener(this);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		PlottingElement p;
		if(o == histogram)
			p = (PlottingElement) PlotController.getTemplates().get("histogram");
		else if(o == barplot)
			p = (PlottingElement) PlotController.getTemplates().get("bar");
		else if(o == mean)
			p = (PlottingElement) PlotController.getTemplates().get("mean");
		else if(o == scatterPlot)
			p = (PlottingElement) PlotController.getTemplates().get("scatter");
		else if(o == densTwo)
			p = (PlottingElement) PlotController.getTemplates().get("histogram_2d");
		else if(o == dotPlot)
			p = (PlottingElement) PlotController.getTemplates().get("grouped_dotplot");
		else if(o == line)
			p = (PlottingElement) PlotController.getTemplates().get("grouped_line");
		else if(o == bubble)
			p = (PlottingElement) PlotController.getTemplates().get("bubble");
		else
			return;
		
		builder.addElement((PlottingElement) p.clone());
		
	}

}
