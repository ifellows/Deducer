package org.rosuda.deducer.plots;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import org.rosuda.deducer.widgets.param.Param;
import org.rosuda.deducer.widgets.param.ParamWidget;

public class DefaultElementView extends ElementView{
	protected JScrollPane scroller;
	protected JPanel paramPanel;
	protected ElementModel model;
	
	protected Vector widgets = new Vector();
	
	public DefaultElementView(){
		initGui();
	}
	
	public DefaultElementView(ElementModel el){
		initGui();
		setModel(el);
	}
	
	
	private void initGui(){
		this.setLayout(new BorderLayout());
		scroller = new JScrollPane();
		scroller.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Border border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		scroller.setBorder(border);
		this.add(scroller);
		{
			paramPanel = new JPanel();
			scroller.setViewportView(paramPanel);
		}
	}
	
	public void updatePanel(){
		paramPanel.removeAll();
		BoxLayout thisLayout = new BoxLayout(paramPanel, javax.swing.BoxLayout.Y_AXIS);
		paramPanel.setLayout(thisLayout);	
		//Vector paramNames = new Vector();
		for(int i=0;i<model.getParams().size();i++){
			Param p = (Param) model.getParams().get(i);
			ParamWidget a = p.getView();
			a.setAlignmentX(CENTER_ALIGNMENT);
			a.setMaximumSize(new Dimension(365,a.getMaximumSize().height));
			//paramNames.add(a.getModel().title);			
			widgets.add(a);
			paramPanel.add(a);
			paramPanel.add(Box.createRigidArea(new Dimension(0,10)));
		}
		paramPanel.validate();
		paramPanel.repaint();
	}
	
	public ElementModel getModel() {
		updateModel();
		return model;
	}

	public void setModel(ElementModel el) {
		model = el;
		updatePanel();
	}

	public void updateModel() {
		for(int i=0;i<widgets.size();i++){
			Object o = widgets.get(i);
			if(o instanceof AesWidget){
				((AesWidget)o).updateModel();
			}else if(o instanceof ParamWidget){
				((ParamWidget)o).updateModel();
			}
		}
	}

}
