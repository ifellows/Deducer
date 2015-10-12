package org.rosuda.deducer.widgets;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;


import java.util.EventListener;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JSlider;

/**
 * A slider with values ranging from 0-100
 * @author Ian
 *
 */
public class SliderWidget extends javax.swing.JPanel implements DeducerWidget{
	private JSlider slider;
	private String title;
	private String[] endPoints;
	private Integer initialModel;
	private Integer lastModel;
	
	public SliderWidget(String panelTitle,String[] sliderEndPointLabels) {
		super();
		title = panelTitle;
		endPoints = sliderEndPointLabels;
		initGUI();
	}
	
	public SliderWidget(String[] sliderEndPointLabels) {
		this(null,sliderEndPointLabels);
	}
	
	public SliderWidget(String panelTitle) {
		this(panelTitle,null);
	}
	
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(185, 40));
			if(title!=null)
				this.setBorder(BorderFactory.createTitledBorder(title));
			
			slider = new JSlider(JSlider.HORIZONTAL,0,100,1);
			
			if(endPoints!=null && endPoints.length==2){
				slider.setMajorTickSpacing(100);
				Hashtable labelTable = new Hashtable();
				labelTable.put( new Integer( 0 ), new JLabel(endPoints[0]) );
				labelTable.put( new Integer( 100), new JLabel(endPoints[1]) );
				slider.setLabelTable( labelTable );
				slider.setPaintTicks(true);
				slider.setPaintLabels(true);
			}
			this.add(slider, BorderLayout.CENTER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getValue(){
		return slider.getValue();
	}
	
	public void setValue(int val){
		slider.setValue(val);
	}

	public JSlider getSlider(){
		return slider;
	}
	
	/**
	 * adds either a mouse listener to the slider
	 * @param lis
	 */
	public void addListener(EventListener lis) {
		if(lis instanceof MouseListener)
			slider.addMouseListener((MouseListener) lis);
	}
	
	public Object getModel() {
		return new Integer(slider.getValue());
	}

	public String getRModel() {
		return getModel().toString();
	}

	public String getTitle() {
		return title;
	}

	public void reset() {
		setModel(initialModel);
	}

	public void resetToLast() {
		setModel(lastModel);
	}

	public void setDefaultModel(Object model) {
		initialModel = (Integer) model;
		if(lastModel==null)
			lastModel = (Integer) model;
	}

	public void setLastModel(Object model)  {
		lastModel = (Integer) model;
	}

	public void setModel(Object model) {
		if(model == null)
			slider.setValue(0);
		else
			slider.setValue(((Integer)model).intValue());
	}

	public void setTitle(String t, boolean show) {
		title=t;
		if(t==null)
			this.setBorder(BorderFactory.createEmptyBorder());
		else if(show)
			this.setBorder(BorderFactory.createTitledBorder(title));
		
	}

	public void setTitle(String t) {
		setTitle(t,false);
	}


}
