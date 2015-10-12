package org.rosuda.deducer.widgets.param;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.deducer.widgets.VectorBuilderWidget;

public class ParamVectorBuilderWidget extends ParamWidget{
	private VectorBuilderWidget vectorBuilder;
	
	
	public ParamVectorBuilderWidget(){
		super();
	}
	
	public ParamVectorBuilderWidget(Param p){
		super();
		setModel(p);
	}
	
	public void setModel(Param p){
		model = p;
		initAsVectorBuilder();
		vectorBuilder.removeAllItems();
		vectorBuilder.addItems((String[]) (model.getValue()!=null ? model.getValue() : new String[]{}));
		vectorBuilder.setNumeric(((ParamVector)p).isNumeric());
	}
	
	public void updateModel(){
		DefaultListModel lm = vectorBuilder.getListModel();
		if(lm.size()==0)
			model.setValue(null);
		else{
			String[] s = new String[lm.size()];
			for(int i=0;i<s.length;i++)
				s[i] = lm.get(i).toString();
			model.setValue(s);
		}
	}
	
	public Param getModel(){
		updateModel();
		return model;
	}
	
	private void initAsVectorBuilder() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(291, 166));
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
				vectorBuilder = new VectorBuilderWidget();
				this.add(vectorBuilder, new AnchorConstraint(3, 750, 1003, textPos, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
						AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_ABS));
				vectorBuilder.setPreferredSize(new java.awt.Dimension(113, 166));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setPreferredSize(new Dimension(200,100));
		this.setMaximumSize(new Dimension(2000,100));
	}
}
