

package org.rosuda.deducer.data;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;



/**
 * Tiger Stripes the rows for easier visibility
 * 
 * @author Ian Fellows
 *
 */
public class ExCellRenderer extends DefaultTableCellRenderer
{
	private Color whiteColor = new Color(254, 254, 254);
	private Color alternateColor = new Color(237, 243, 254);
	private Color selectedColor = new Color(61, 128, 223);

	public Component getTableCellRendererComponent(JTable table,
					Object value, boolean selected, boolean focused,
					int row, int column){
		super.getTableCellRendererComponent(table, value,
				selected, focused, row, column);

		// Set the background color
		Color bg;
		if (!selected)
			bg = (row % 2 == 0 ? alternateColor : whiteColor);
		else
			bg = selectedColor;
		setBackground(bg);

		// Set the foreground to white when selected
		Color fg;
		if (selected)
			fg = Color.white;
		else
			fg = Color.black;
		setForeground(fg);

		return this;
	}
}
