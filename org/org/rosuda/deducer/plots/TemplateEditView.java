package org.rosuda.deducer.plots;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

public class TemplateEditView extends ElementView{

	Template model;
	private JScrollPane scroller;
	private JPanel panel;
	
	Vector optionPanels = new Vector();
	
	public TemplateEditView(Template templ){
		initGui();
		setModel(templ);
	}
	
	public ElementModel getModel() {
		return model;
	}

	public void setModel(ElementModel el) {
		model = (Template) el;
		updatePanel();
	}

	public void updateModel() {
		for(int i=0;i<optionPanels.size();i++)
			((MaskOptions)optionPanels.get(i)).updateModel();
	}
	
	public void initGui(){
		this.setLayout(new BorderLayout());
		scroller = new JScrollPane();
		scroller.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Border border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		scroller.setBorder(border);
		this.add(scroller);
		{
			panel = new JPanel();
			scroller.setViewportView(panel);
		}
	}
	
	public void updatePanel(){
		panel.removeAll();
		BoxLayout thisLayout = new BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS);
		panel.setLayout(thisLayout);
		
		
		Template.MaskingAes[] maes = model.mAess;
		for(int i=0;i<maes.length;i++){
			MaskOptions mo = new MaskOptions(i,true);
			panel.add(mo);
			optionPanels.add(mo);
		}
		
		Template.MaskingParam[] mparam = model.mParams;
		for(int i=0;i<mparam.length;i++){
			MaskOptions mo = new MaskOptions(i,false);
			panel.add(mo);
			optionPanels.add(mo);
		}
		
		
		panel.validate();
		panel.repaint();		
	}
	
	
class MaskOptions extends JPanel{
	public JCheckBox show = new JCheckBox("show");
	public JLabel label = new JLabel();
	public JTextField name = new JTextField();
	
	public int index;
	public boolean isAes = false;
	
	public MaskOptions(int i,boolean isAes){
		super();
		GridLayout l = new GridLayout(0,3);
		this.setLayout(l);
		this.add(label);
		this.add(show);
		this.add(name);
		this.setPreferredSize(new java.awt.Dimension(241, 37));
		this.setMaximumSize(new java.awt.Dimension(1000, 37));
		setModel(i,isAes);
	}
	
	public void setModel(int i,boolean isAes){
		index = i;
		this.isAes = isAes;
		
		if(isAes){
			Template.MaskingAes maes = model.mAess[index];
			label.setText(maes.name);
			show.setSelected(maes.show);
			name.setText(maes.aes.title);
		}else{
			Template.MaskingParam mparam = model.mParams[index];
			label.setText(mparam.paramName);
			show.setSelected(mparam.show);
			name.setText(mparam.param.getTitle());			
		}
	}
	
	public void updateModel(){
		if(isAes){
			Template.MaskingAes maes = model.mAess[index];
			maes.show = show.isSelected();
			maes.aes.title = name.getText();
		}else{
			Template.MaskingParam mparam = model.mParams[index];
			mparam.show = show.isSelected();
			mparam.param.setTitle(name.getText());
		}
	}
	
}
}




