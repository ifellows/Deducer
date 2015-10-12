


package org.rosuda.deducer.data;

import java.awt.BorderLayout;


import javax.swing.WindowConstants;
import javax.swing.table.TableModel;

/**
 * Table window implements a scrollable pane in which to house ExTable.
 * It also implements the row headers.
 * 
 * 
 * @author Ian Fellows
 *
 */
public class TableWindow extends javax.swing.JFrame {

	private ExScrollableTable jScrollPane1;
	private ExTable table;
	private TableModel tableModel;
	



	/**
	 * Sets up a Window
	 * @param t
	 * @param tm
	 */
	public TableWindow(ExTable t,TableModel tm) {
		super();
		table = t;
		tableModel = tm;
		initGUI();
	}
	
	
	/**
	 * Starts up the GUI components
	 * 
	 */
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jScrollPane1 = new ExScrollableTable(table);
				getContentPane().add(jScrollPane1, BorderLayout.CENTER);
				jScrollPane1.setPreferredSize(new java.awt.Dimension(537, 334));
			}
			pack();
			this.setSize(758, 441);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	

	
}
