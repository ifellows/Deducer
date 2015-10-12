package org.rosuda.deducer.widgets.param;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.deducer.toolkit.IconButton;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;




public class RFunctionListChooserWidget extends ParamWidget implements ActionListener{
	private JLabel label;
	private RFunctionList model;
	private JComboBox comboBox;
	private JButton options;
	
	
	public RFunctionListChooserWidget() {
		super();
		initGUI();
	}
	public RFunctionListChooserWidget(Param p) {
		super();
		initGUI();
		setModel(p);
	}
	
	private void initGUI(){
		this.removeAll();
		AnchorLayout thisLayout = new AnchorLayout();
		this.setLayout(thisLayout);
		{
			options = new IconButton("/icons/advanced_32.png","Options",this,"Options");
			this.add(options, new AnchorConstraint(16, 1, 1050, 867, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
			options.setPreferredSize(new java.awt.Dimension(32, 31));
		}
		this.setPreferredSize(new java.awt.Dimension(241, 37));
		int labelWidth = leftPos-22; 
		{
			label = new JLabel();
			this.add(label, new AnchorConstraint(202, 234, 689, 12, 
					AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, 
					AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
			if(model!=null){
				label.setText(model.getTitle());
				labelWidth = SwingUtilities.computeStringWidth(
						label.getFontMetrics(label.getFont()),
						model.getTitle());
			}

		}	
		{
			int textPos = Math.max(labelWidth+22, leftPos);
			DefaultComboBoxModel comboBoxModel = 
				new DefaultComboBoxModel();
			comboBoxModel.addElement(null);
			if(model!=null){
				if(model.getOptions()!=null)
					for(int i=0;i<model.getOptions().length;i++)
						comboBoxModel.addElement(model.getOptions()[i]);
			}
			comboBox = new JComboBox();
			this.add(comboBox, new AnchorConstraint(150, 39, 743, textPos, 
					AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS, 
					AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
			comboBox.setModel(comboBoxModel);
			comboBox.setPreferredSize(new java.awt.Dimension(159, 22));
		}

		this.setPreferredSize(new Dimension(200,30));
		this.setMaximumSize(new Dimension(2000,30));
	}

	public void setModel(Param p) {
		model = (RFunctionList) p;
		initGUI();
		label.setText(p.getTitle());
		if(p.getValue() !=null){
			if(model.getActiveFunctions().size()>0){
				String val = (String) model.getActiveFunctions().get(0);
				comboBox.setSelectedItem(val);
			}
		}

	}
	
	public Param getModel(){
		return model;
	}

	public void updateModel() {
		Vector v = new Vector();
		if(comboBox.getSelectedItem()!=null)
			v.add(comboBox.getSelectedItem());
		model.setActiveFunctions(v);
		
	}

	public void actionPerformed(ActionEvent arg0) {
		Object f = comboBox.getSelectedItem();
		if(f!=null && f.toString().length()>0){
			String fun = f.toString();
			HashMap hm = model.getFunctionMap();
			RFunction rf = (RFunction) hm.get(fun);
			RFunctionDialog d = new RFunctionDialog(rf);
			d.setRun(false);
			d.setModal(true);
			d.setLocationRelativeTo(options);
			d.setSize(300, 300);
			d.setVisible(true);
		}
	}

}
