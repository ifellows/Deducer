package org.rosuda.deducer.plots;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import org.rosuda.deducer.widgets.param.Param;
import org.rosuda.deducer.widgets.param.ParamWidget;

public class ParamStatSummaryWidget extends ParamWidget implements ActionListener{
	private JLabel modelLabel;
	private JComboBox methodCombo;
	private JTextField multField;
	private JTextPane dataText;
	private JScrollPane jScrollPane3;
	private JPanel jPanel3;
	private JTextPane ymaxText;
	private JScrollPane jScrollPane2;
	private JPanel jPanel2;
	private JTextPane yminText;
	private JScrollPane jScrollPane1;
	private JPanel jPanel1;
	private JTextPane yText;
	private JScrollPane scroller1;
	private JPanel jPanel0;
	private JTextField simSizeField;
	private JLabel simulationLabel;
	private JLabel sdMultLabel;
	private JTextField confidenceField;
	private JLabel confidenceLabel;


	
	public ParamStatSummaryWidget() {
		super();
		initGUI();
		updateView();
	}
	
	public ParamStatSummaryWidget(ParamStatSummary p){
		this();
		setModel(p);
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(199, 536));
			this.setMinimumSize(new java.awt.Dimension(140, 536));
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			{
				jPanel0 = new JPanel();
				BorderLayout yPanelLayout = new BorderLayout();
				jPanel0.setLayout(yPanelLayout);
				this.add(jPanel0, new AnchorConstraint(61, 1002, 343, 2, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				jPanel0.setPreferredSize(new java.awt.Dimension(199, 107));
				jPanel0.setBorder(BorderFactory.createTitledBorder("y function"));
				{
					scroller1 = new JScrollPane();
					jPanel0.add(scroller1, BorderLayout.CENTER);
					{
						yText = new JTextPane();
						scroller1.setViewportView(yText);
					}
				}
			}
			{
				modelLabel = new JLabel();
				this.add(modelLabel, new AnchorConstraint(12, 263, 68, 0, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
				modelLabel.setText("Method");
				modelLabel.setHorizontalAlignment(SwingConstants.TRAILING);
				modelLabel.setPreferredSize(new java.awt.Dimension(52, 15));
			}
			{
				ComboBoxModel methodComboModel = 
					new DefaultComboBoxModel(
							new String[] { "Mean with standard deviation limits (mean_sdl)",
									"Mean with confidence interval (mean_cl_normal)",
									"Median  with quantile limits (median_hilow)",
									"Bootstrap mean with confidence interval (mean_cl_boot)",
									"Custom"});
				methodCombo = new JComboBox();
				this.add(methodCombo, new AnchorConstraint(21, 0, 75, 58, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
				methodCombo.setModel(methodComboModel);
				methodCombo.setPreferredSize(new java.awt.Dimension(141, 22));
				methodCombo.addActionListener(this);
			}
			{
				jPanel1 = new JPanel();
				this.add(jPanel1, new AnchorConstraint(180, 1002, 343, 2, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				BorderLayout jPanel1Layout2 = new BorderLayout();
				jPanel1.setPreferredSize(new java.awt.Dimension(199, 107));
				jPanel1.setBorder(BorderFactory.createTitledBorder("y-min function"));
				jPanel1.setLayout(jPanel1Layout2);
				{
					jScrollPane1 = new JScrollPane();
					jPanel1.add(jScrollPane1, BorderLayout.CENTER);
					{
						yminText = new JTextPane();
						jScrollPane1.setViewportView(yminText);
					}
				}
			}
			{
				jPanel2 = new JPanel();
				this.add(jPanel2, new AnchorConstraint(293, 1002, 343, 2, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				BorderLayout jPanel2Layout1 = new BorderLayout();
				jPanel2.setPreferredSize(new java.awt.Dimension(199, 107));
				jPanel2.setBorder(BorderFactory.createTitledBorder("y-max function"));
				jPanel2.setLayout(jPanel2Layout1);
				{
					jScrollPane2 = new JScrollPane();
					jPanel2.add(jScrollPane2, BorderLayout.CENTER);
					{
						ymaxText = new JTextPane();
						jScrollPane2.setViewportView(ymaxText);
					}
				}
			}
			{
				jPanel3 = new JPanel();
				this.add(jPanel3, new AnchorConstraint(406, 1002, 343, 2, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				BorderLayout jPanel3Layout = new BorderLayout();
				jPanel3.setPreferredSize(new java.awt.Dimension(199, 107));
				jPanel3.setBorder(BorderFactory.createTitledBorder("data function"));
				jPanel3.setLayout(jPanel3Layout);
				{
					jScrollPane3 = new JScrollPane();
					jPanel3.add(jScrollPane3, BorderLayout.CENTER);
					{
						dataText = new JTextPane();
						jScrollPane3.setViewportView(dataText);
					}
				}
			}
			{
				confidenceLabel = new JLabel();
				this.add(confidenceLabel, new AnchorConstraint(42, 711, 142, 2, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				confidenceLabel.setText("Confidence Level");
				confidenceLabel.setHorizontalAlignment(SwingConstants.TRAILING);
				confidenceLabel.setPreferredSize(new java.awt.Dimension(141, 15));
			}
			{
				confidenceField = new JTextField();
				this.add(confidenceField, new AnchorConstraint(38, 992, 150, 771, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				confidenceField.setToolTipText("Confidence level for confidence interval");
				confidenceField.setPreferredSize(new java.awt.Dimension(44, 22));
				confidenceField.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				sdMultLabel = new JLabel();
				this.add(sdMultLabel, new AnchorConstraint(70, 711, 212, 2, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				sdMultLabel.setText("Std. Multiplier");
				sdMultLabel.setHorizontalAlignment(SwingConstants.TRAILING);
				sdMultLabel.setPreferredSize(new java.awt.Dimension(141, 15));
			}
			{
				multField = new JTextField();
				this.add(multField, new AnchorConstraint(66, 992, 219, 771, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				multField.setToolTipText("Limits defined by ___ times the computed standard deviation");
				multField.setPreferredSize(new java.awt.Dimension(44, 22));
				multField.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				simulationLabel = new JLabel();
				this.add(simulationLabel, new AnchorConstraint(97, 711, 279, 2, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				simulationLabel.setText("Simulation size");
				simulationLabel.setHorizontalAlignment(SwingConstants.TRAILING);
				simulationLabel.setPreferredSize(new java.awt.Dimension(141, 15));
			}
			{
				simSizeField = new JTextField();
				this.add(simSizeField, new AnchorConstraint(93, 992, 286, 771, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
				simSizeField.setToolTipText("Sample size for bootstrap simulation");
				simSizeField.setPreferredSize(new java.awt.Dimension(44, 22));
				simSizeField.setHorizontalAlignment(SwingConstants.CENTER);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateView(){
		int m = methodCombo.getSelectedIndex();
		if(m==4){
			confidenceLabel.setVisible(false);
			confidenceField.setVisible(false);
			sdMultLabel.setVisible(false);
			multField.setVisible(false);
			simulationLabel.setVisible(false);
			simSizeField.setVisible(false);		
			jPanel3.setVisible(true);
			jPanel2.setVisible(true);
			jPanel1.setVisible(true);
			jPanel0.setVisible(true);
			
		}else{
			jPanel3.setVisible(false);
			jPanel2.setVisible(false);
			jPanel1.setVisible(false);
			jPanel0.setVisible(false);
		}
		if(m==0){
			confidenceLabel.setVisible(false);
			confidenceField.setVisible(false);
			sdMultLabel.setVisible(true);
			multField.setVisible(true);
			simulationLabel.setVisible(false);
			simSizeField.setVisible(false);
		}
		if(m==1){
			confidenceLabel.setVisible(true);
			confidenceField.setVisible(true);
			confidenceField.setToolTipText("Confidence level for confidence interval");
			sdMultLabel.setVisible(false);
			multField.setVisible(false);
			simulationLabel.setVisible(false);
			simSizeField.setVisible(false);
		}
		if(m==2){
			confidenceLabel.setVisible(true);
			confidenceField.setVisible(true);
			confidenceField.setToolTipText("Quantile limits. For example, .95 gives limits with 2.5% of"+
					"observations below the lower limit, and 2.5% above the upper limit");
			sdMultLabel.setVisible(false);
			multField.setVisible(false);
			simulationLabel.setVisible(false);
			simSizeField.setVisible(false);
		}
		if(m==3){
			confidenceLabel.setVisible(true);
			confidenceField.setVisible(true);
			confidenceField.setToolTipText("Confidence level for confidence interval");
			sdMultLabel.setVisible(false);
			multField.setVisible(false);
			simulationLabel.setVisible(true);
			simSizeField.setVisible(true);
		}		
	}

	public void actionPerformed(ActionEvent arg0) {
		updateView();
	}

	public void setModel(Param p) {
		model = p;
		if(p.getValue()!=null){
			Vector val = (Vector) p.getValue();
			methodCombo.setSelectedIndex(((Integer)val.get(0)).intValue());
			confidenceField.setText(val.get(1).toString());
			multField.setText(val.get(2).toString());
			simSizeField.setText(val.get(3).toString());
			yText.setText(val.get(4).toString());
			yminText.setText(val.get(5).toString());
			ymaxText.setText(val.get(6).toString());
			dataText.setText(val.get(7).toString());
		}
	}

	public void updateModel() {
		Vector newValue = new Vector();
		newValue.add(new Integer(methodCombo.getSelectedIndex()));
		newValue.add(confidenceField.getText());
		newValue.add(multField.getText());
		newValue.add(simSizeField.getText());
		newValue.add(yText.getText());
		newValue.add(yminText.getText());
		newValue.add(ymaxText.getText());
		newValue.add(dataText.getText());
		model.setValue(newValue);
	}

}
