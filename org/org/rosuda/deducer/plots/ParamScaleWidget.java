package org.rosuda.deducer.plots;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.deducer.data.ExDefaultTableModel;
import org.rosuda.deducer.plots.LegendPanel;
import org.rosuda.deducer.plots.ParamScaleLegend;
import org.rosuda.deducer.widgets.param.Param;
import org.rosuda.deducer.widgets.param.ParamWidget;

public class ParamScaleWidget extends ParamWidget{
	private LegendPanel legendPanel;
	public ParamScaleWidget(){
		super();
	}
	
	public ParamScaleWidget(Param p){
		super();
		setModel(p);
	}
	
	public void setModel(Param p){
		model = p;		
		initAsScale();
		if(model.getValue()!=null){
			Vector v = (Vector) model.getValue();
			String text = (String) v.get(0);
			Boolean show = (Boolean) v.get(1);
			ExDefaultTableModel tm = (ExDefaultTableModel) v.get(2);
			legendPanel.setName(text);
//			legendPanel.setShowLegend(show.booleanValue());
			
			legendPanel.setTableModel(tm);
		}
		legendPanel.setNumeric(((ParamScaleLegend)p).isNumeric());
		legendPanel.getBreaksWidget().setModel(
				((ParamScaleLegend)model).getBreaksModel());
		legendPanel.getLabelsWidget().setModel(
				((ParamScaleLegend)model).getLabelsModel());
		legendPanel.getGuideWidget().setModel(
				((ParamScaleLegend)model).getGuideModel());
		String aes = ((ParamScaleLegend)model).getAes();
		legendPanel.showGuide(!aes.equals("y") && !aes.equals("x"));
		//System.out.println(aes);
		if(aes.equals("y") || aes.equals("x")){
			legendPanel.setBorder(BorderFactory.createTitledBorder(aes+"-axis"));
			legendPanel.showGuide(false);
		}else
			legendPanel.setBorder(BorderFactory.createTitledBorder("Legend"));
	}
	
	public void updateModel(){
		Vector newValue = new Vector();
		newValue.add(legendPanel.getName());
		newValue.add(new Boolean(true));
		ExDefaultTableModel tm = legendPanel.getTableModel();
		if(((ParamScaleLegend)model).isNumeric())
			for(int j=0;j<tm.getRowCount();j++){
				String val = (String) tm.getValueAt(j, 0);
				try{
					Double.parseDouble(val);
				}catch(Exception e){
					tm.setValueAt("", j, 0);
				}
			}
		legendPanel.setTableModel(tm);
		newValue.add(tm);
		model.setValue(newValue);
		legendPanel.getBreaksWidget().updateModel();
		legendPanel.getLabelsWidget().updateModel();
		legendPanel.getGuideWidget().updateModel();
	}
	
	public Param getModel(){
		updateModel();
		return model;
	}
	
	private void initAsScale() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(291, 166));
			int labelWidth = leftPos-22; 
			/*{
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

			}	*/
			{
				int textPos = Math.max(labelWidth+22, leftPos);
				legendPanel = new LegendPanel();
				this.add(legendPanel, new AnchorConstraint(15, 820, 1003, 180, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				legendPanel.setPreferredSize(new java.awt.Dimension(255, 255));
				legendPanel.setMaximumSize(new java.awt.Dimension(255, 700));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setPreferredSize(new Dimension(300,325));
		this.setMaximumSize(new Dimension(500,325));
	}
}
