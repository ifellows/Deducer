package org.rosuda.deducer.plots;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.widgets.VariableListWidget;
import org.rosuda.deducer.widgets.VariableSelectorWidget;
import org.rosuda.deducer.widgets.param.Param;
import org.rosuda.deducer.widgets.param.ParamWidget;

public class ParamFacetWidget extends ParamWidget implements ActionListener{
	private JTabbedPane tabs;
	private VariableListWidget lhsVariables;
	private VariableListWidget wrapVars;
	private JButton wrapOptions;
	private JButton gridOptions;
	private VariableListWidget rhsVariables;
	private JPanel wrapPanel;
	private JPanel gridPanel;

	private JCheckBox xFreeGrid;
	private JCheckBox yFreeGrid;
	private JCheckBox margins;
	private JCheckBox spaceFixed;
	private JCheckBox asTableGrid;
	private JCheckBox dropGrid;
	
	private JTextField nrow;
	private JTextField ncol;
	private JCheckBox xFreeWrap;
	private JCheckBox yFreeWrap;	
	private JCheckBox asTableWrap;
	private JCheckBox drop;

	private ParamFacet model;
	
	public ParamFacetWidget(VariableSelectorWidget v,Param p) {
		super();
		initGUI(v);
		setModel(p);
	}
	
	private void initGUI(VariableSelectorWidget v) {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(241, 350));
			this.setMinimumSize(new java.awt.Dimension(100, 350));
			this.setMaximumSize(new java.awt.Dimension(241, 350));
			{
				tabs = new JTabbedPane();
				this.add(tabs, BorderLayout.CENTER);
				tabs.setPreferredSize(new java.awt.Dimension(264, 420));
				{
					gridPanel = new JPanel();
					AnchorLayout gridPanelLayout = new AnchorLayout();
					gridPanel.setLayout(gridPanelLayout);
					tabs.addTab("grid", null, gridPanel, null);
					{
						gridOptions = new IconButton("/icons/advanced_32.png","Grid options",this,"gridOptions");
						gridPanel.add(gridOptions, new AnchorConstraint(887, 955, 979, 673, 
								AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, 
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE));
						gridOptions.setPreferredSize(new java.awt.Dimension(32, 32));
					}
					{
						rhsVariables = new VariableListWidget("columns",v);
						gridPanel.add(rhsVariables, new AnchorConstraint(428, 955, 805, 48, 
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						rhsVariables.setPreferredSize(new java.awt.Dimension(235, 82));
					}
					{
						lhsVariables = new VariableListWidget("rows",v);
						gridPanel.add(lhsVariables, new AnchorConstraint(57, 955, 428, 48, 
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						lhsVariables.setPreferredSize(new java.awt.Dimension(235, 81));
					}
				}
				{
					wrapPanel = new JPanel();
					AnchorLayout wrapPanelLayout = new AnchorLayout();
					wrapPanel.setLayout(wrapPanelLayout);
					tabs.addTab("wrap", null, wrapPanel, null);
					{
						wrapOptions = new IconButton("/icons/advanced_32.png","Wrap options",this,"wrapOptions");
						wrapPanel.add(wrapOptions, new AnchorConstraint(887, 955, 979, 673, 
								AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, 
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE));
						wrapOptions.setPreferredSize(new java.awt.Dimension(32, 32));
					}
					{
						wrapVars = new VariableListWidget("facet by",v);
						wrapPanel.add(wrapVars, new AnchorConstraint(57, 955, 745, 48, 
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
								AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						wrapVars.setPreferredSize(new java.awt.Dimension(235, 150));
					}
				}
			}
			
			xFreeGrid = new JCheckBox("x-axis free");
			yFreeGrid = new JCheckBox("y-axis free");
			margins = new JCheckBox("show margins");
			spaceFixed = new JCheckBox("fix facet size");
			asTableGrid = new JCheckBox("as table");
			dropGrid = new JCheckBox("drop");
			
			nrow = new JTextField();
			ncol = new JTextField();
			xFreeWrap = new JCheckBox("x-axis free");
			yFreeWrap = new JCheckBox("y-axis free");	
			asTableWrap = new JCheckBox("as table");
			drop = new JCheckBox("drop");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showGridOptions(){
		final JDialog d = new JDialog(new JFrame(),"Grid Options");
		d.getContentPane().setLayout(null);
		JButton okay = new JButton();
		d.getContentPane().add(okay);
		okay.setText("Okay");
		okay.setBounds(146, 150, 69, 22);
		okay.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				d.dispose();
			}
			
		});
		d.getContentPane().add(xFreeGrid);
		xFreeGrid.setBounds(12, 12, 156, 17);
		d.getContentPane().add(yFreeGrid);
		yFreeGrid.setBounds(12, 35, 156, 17);
		d.getContentPane().add(margins);
		margins.setBounds(12, 58, 156, 17);
		d.getContentPane().add(spaceFixed);
		spaceFixed.setBounds(12, 81, 156, 17);
		d.getContentPane().add(asTableGrid);
		asTableGrid.setBounds(12, 104, 156, 17);
		d.getContentPane().add(dropGrid);
		dropGrid.setBounds(12, 127, 156, 17);		
		d.setModal(true);
		d.setSize(227, 210);
		d.setLocationRelativeTo(gridOptions);
		d.setVisible(true);
	}
	
	public void showWrapOptions(){
		final JDialog d = new JDialog(new JFrame(),"Wrap options");
		d.getContentPane().setLayout(null);
		{
			JButton okay = new JButton();
			d.getContentPane().add(okay);
			okay.setText("Okay");
			okay.setBounds(146, 133, 69, 22);
			okay.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					try{
						int i = Integer.parseInt(nrow.getText());
						if(i<1)
							nrow.setText("");
					}catch(Exception e){
						nrow.setText("");
					}
					
					try{
						int i = Integer.parseInt(ncol.getText());
						if(i<1)
							ncol.setText("");
					}catch(Exception e){
						ncol.setText("");
					}
					
					d.dispose();
				}
				
			});
		}
		{
			JLabel rowLab = new JLabel();
			d.getContentPane().add(rowLab);
			rowLab.setText("# of rows");
			rowLab.setBounds(12, 12, 80, 15);
			rowLab.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		{
			d.getContentPane().add(nrow);
			nrow.setBounds(104, 8, 42, 22);
			nrow.setHorizontalAlignment(SwingConstants.CENTER);
		}
		{
			JLabel colLab = new JLabel();
			d.getContentPane().add(colLab);
			colLab.setText("# of Columns");
			colLab.setBounds(0, 39, 92, 15);
			colLab.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		{
			d.getContentPane().add(ncol);
			ncol.setBounds(104, 35, 42, 22);
			ncol.setHorizontalAlignment(SwingConstants.CENTER);
		}
		{
			d.getContentPane().add(xFreeWrap);
			xFreeWrap.setBounds(12, 66, 104, 19);
		}
		{
			d.getContentPane().add(yFreeWrap);
			yFreeWrap.setBounds(12, 91, 104, 19);
		}
		{
			d.getContentPane().add(asTableWrap);
			asTableWrap.setBounds(122, 66, 92, 19);
		}
		{
			d.getContentPane().add(drop);
			drop.setBounds(122, 91, 100, 19);
		}
		d.setModal(true);
		d.setSize(227, 188);
		d.setLocationRelativeTo(wrapOptions);
		d.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == gridOptions){
			showGridOptions();
		}else
			showWrapOptions();
		
	}

	public void setModel(Param p) {
		model = (ParamFacet) p;
		lhsVariables.setModel(model.yVarsGrid, false);
		rhsVariables.setModel(model.xVarsGrid, false);
		wrapVars.setModel(model.varsWrap, false);
		xFreeGrid.setSelected(model.scaleGrid.equals("free") || model.scaleGrid.equals("free_x"));
		yFreeGrid.setSelected(model.scaleGrid.equals("free") || model.scaleGrid.equals("free_y"));
		xFreeWrap.setSelected(model.scaleWrap.equals("free") || model.scaleWrap.equals("free_x"));
		yFreeWrap.setSelected(model.scaleWrap.equals("free") || model.scaleWrap.equals("free_y"));
		margins.setSelected(model.margins.booleanValue());
		spaceFixed.setSelected(model.spaceFixed.booleanValue());
		asTableGrid.setSelected(model.asTableGrid.booleanValue());
		if(model.nrow!=null)
			nrow.setText(model.nrow.toString());
		else
			nrow.setText("");
		if(model.ncol!=null)
			ncol.setText(model.ncol.toString());
		else
			ncol.setText("");
		asTableWrap.setSelected(model.asTableWrap.booleanValue());
		drop.setSelected(model.drop.booleanValue());
		dropGrid.setSelected(model.dropGrid.booleanValue());
		this.setType(model.facetType);
	}

	public void updateModel() {
		model.yVarsGrid = lhsVariables.getVariables();
		model.xVarsGrid = rhsVariables.getVariables();
		model.varsWrap = wrapVars.getVariables();
		
		if(xFreeGrid.isSelected() && yFreeGrid.isSelected())
			model.scaleGrid = "free";
		else if(xFreeGrid.isSelected() && !yFreeGrid.isSelected())
			model.scaleGrid = "free_x";
		else if(!xFreeGrid.isSelected() && yFreeGrid.isSelected())
			model.scaleGrid = "free_y";
		else if(!xFreeGrid.isSelected() && !yFreeGrid.isSelected())
			model.scaleGrid = "fixed";
		
		if(xFreeWrap.isSelected() && yFreeWrap.isSelected())
			model.scaleWrap = "free";
		else if(xFreeWrap.isSelected() && !yFreeWrap.isSelected())
			model.scaleWrap = "free_x";
		else if(!xFreeWrap.isSelected() && yFreeWrap.isSelected())
			model.scaleWrap = "free_y";
		else if(!xFreeWrap.isSelected() && !yFreeWrap.isSelected())
			model.scaleWrap = "fixed";
		model.margins = new Boolean(margins.isSelected());
		model.spaceFixed = new Boolean(spaceFixed.isSelected());
		model.asTableGrid = new Boolean(asTableGrid.isSelected());
		model.asTableWrap = new Boolean(asTableWrap.isSelected());
		model.drop = new Boolean(drop.isSelected());
		model.dropGrid = new Boolean(dropGrid.isSelected());
		try{
			model.nrow = new Integer(Integer.parseInt(nrow.getText()));
		}catch(Exception e){model.nrow = null;}
		try{
			model.ncol = new Integer(Integer.parseInt(ncol.getText()));
		}catch(Exception e){model.nrow = null;}
		model.facetType = this.getType();
	}

	
	public String getType(){
		int i = tabs.getSelectedIndex();
		if(i==0)
			return "grid";
		else
			return "wrap";
	}
	
	public void setType(String t){
		if(t==null)
			return;
		if(t.equals("grid"))
			tabs.setSelectedIndex(0);
		else
			tabs.setSelectedIndex(1);
	}


}
