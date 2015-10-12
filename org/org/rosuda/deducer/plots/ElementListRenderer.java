package org.rosuda.deducer.plots;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class ElementListRenderer implements ListCellRenderer{
	
	public ElementListRenderer(){
	}

	public Component getListCellRendererComponent(                                       JList list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {
		PlottingElement el;
		try{
			el = (PlottingElement) value;
		}catch(ClassCastException e){
			if(value==null){
				System.out.println("null value");
				return null;
			}else{
				System.out.println(value.toString());
			}
			return null;
		}
		JPanel 	panel = el.makeComponent();
		if (isSelected) {
			panel.setBackground(list.getSelectionBackground());
			panel.setForeground(list.getSelectionForeground());
		} else {
			panel.setBackground(list.getBackground());
			panel.setForeground(list.getForeground());
		}

		return panel;
	}

}
