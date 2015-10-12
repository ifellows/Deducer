package org.rosuda.deducer.models;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListModel;

import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.deducer.toolkit.DJList;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;



public class LogisticDialogSplit extends JDialog implements ActionListener, FocusListener {
	private JRadioButton cut;
	private JComboBox cutDirection;
	private JButton remove;
	private JScrollPane levelsScroller;
	private JRadioButton expression;
	private JList suc;
	private JScrollPane sucScroller;
	private JSeparator sep1;
	private JPanel okayCancelPanel;
	private JTextField expr;
	private JPanel sucPanel;
	private JList levels;
	private JButton add;
	private JPanel levelsPanel;
	private JRadioButton defineLevels;
	private JSeparator sep;
	private JTextField cutValue;
	private LogisticDialogSplitModel model;

	
	public LogisticDialogSplit(JDialog d,LogisticDialogSplitModel m) {
		super(d);
		this.setModal(true);
		initGUI();
		setModel(m);
	}
	
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					cut = new JRadioButton();
					getContentPane().add(cut);
					cut.setText("Success");
					cut.setBounds(12, 12, 83, 18);
				}
				{
					ComboBoxModel cutDirectionModel = 
						new DefaultComboBoxModel(
								new String[] { ">", "<" });
					cutDirection = new JComboBox();
					getContentPane().add(cutDirection);
					cutDirection.setModel(cutDirectionModel);
					cutDirection.setBounds(100, 11, 80, 25);
				}
				{
					cutValue = new JTextField();
					getContentPane().add(cutValue);
					cutValue.setBounds(180, 11, 75, 21);
					cutValue.addFocusListener(this);
				}
				{
					sep = new JSeparator();
					getContentPane().add(sep);
					sep.setBounds(129, 44, 137, 10);
				}
				{
					defineLevels = new JRadioButton();
					getContentPane().add(defineLevels);
					defineLevels.setBounds(12, 113, 21, 17);
				}
				{
					levelsPanel = new JPanel();
					BorderLayout levelsPanelLayout = new BorderLayout();
					levelsPanel.setLayout(levelsPanelLayout);
					getContentPane().add(levelsPanel);
					levelsPanel.setBounds(49, 54, 127, 140);
					levelsPanel.setBorder(BorderFactory.createTitledBorder("Levels"));
					{
						levelsScroller = new JScrollPane();
						levelsPanel.add(levelsScroller, BorderLayout.CENTER);
						{
							ListModel levelsModel = 
								new DefaultListModel();
							levels = new DJList();
							levelsScroller.setViewportView(levels);
							levels.setModel(levelsModel);
							levels.addFocusListener(this);
						}
					}
				}
				{
					add = new IconButton("/icons/1rightarrow_32.png","Add",this,"Add");
					getContentPane().add(add);
					add.setBounds(191, 87, 38, 38);
					add.addFocusListener(this);
				}
				{
					remove = new IconButton("/icons/1leftarrow_32.png","Remove",this,"Remove");
					getContentPane().add(remove);
					remove.setBounds(191, 130, 38, 38);
					remove.addFocusListener(this);
				}
				{
					sucPanel = new JPanel();
					BorderLayout sucPanelLayout = new BorderLayout();
					sucPanel.setLayout(sucPanelLayout);
					getContentPane().add(sucPanel);
					sucPanel.setBounds(240, 54, 127, 140);
					sucPanel.setBorder(BorderFactory.createTitledBorder("Success:"));
					sucPanel.addFocusListener(this);
					{
						sucScroller = new JScrollPane();
						sucPanel.add(sucScroller, BorderLayout.CENTER);
						{
							ListModel sucModel = 
								new DefaultListModel();
							suc = new DJList();
							sucScroller.setViewportView(suc);
							suc.setModel(sucModel);
							suc.addFocusListener(this);
						}
					}
				}
				{
					expression = new JRadioButton();
					getContentPane().add(expression);
					expression.setText("Custom Expression:");
					expression.setBounds(12, 219, 180, 18);
				}
				{
					expr = new JTextField();
					getContentPane().add(expr);
					expr.setBounds(200, 218, 103, 21);
					expr.addFocusListener(this);
				}
				{
					okayCancelPanel = new OkayCancelPanel(false,false,this);
					getContentPane().add(okayCancelPanel);
					okayCancelPanel.setBounds(171, 258, 190, 36);
					{
						sep1 = new JSeparator();
						getContentPane().add(sep1);
						sep1.setBounds(129, 205, 137, 10);
					}
				}
			}
			this.setSize(381, 340);
			this.setTitle("Define Success");
			ButtonGroup b = new ButtonGroup();
			b.add(cut);
			b.add(defineLevels);
			b.add(expression);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}

	public void setModel(LogisticDialogSplitModel m){
		model = m;
		
		DefaultListModel tl = (DefaultListModel) levels.getModel();
		tl.removeAllElements();
		for(int i=0;i<m.levels.size();i++){
			tl.addElement(m.levels.get(i));
		}
		tl = (DefaultListModel) suc.getModel();
		tl.removeAllElements();
		for(int i=0;i<m.suc.size();i++){
			tl.addElement(m.suc.get(i));
		}
		cutValue.setText(m.cutValue);
		expr.setText(m.expr);
		if(m.which==1){
			cut.setSelected(true);
		}else if(m.which==2){
			defineLevels.setSelected(true);
		}else 
			expression.setSelected(true);
		cutDirection.setSelectedItem(m.cutDirection);
		boolean cont =m.levels.getSize()==0 && m.suc.getSize()==0;
		defineLevels.setEnabled(!cont);
		levels.setEnabled(!cont);
		add.setEnabled(!cont);
		remove.setEnabled(!cont);
		suc.setEnabled(!cont);
		cut.setEnabled(m.isNumeric);
		cutValue.setEnabled(m.isNumeric);
		cutDirection.setEnabled(m.isNumeric);
	}
	
	public boolean updateModel(){
		if(cut.isSelected()){
			try{
				Double.parseDouble(cutValue.getText());
			}catch(Exception e){
				JOptionPane.showMessageDialog(this, "The cut value must be a number");
				cutValue.setText("");
				return false;
			}
		}
		if(defineLevels.isSelected() && (levels.getModel().getSize()==0 ||
				suc.getModel().getSize()==0)){
			JOptionPane.showMessageDialog(this, "Success must be a non-empty list of levels, and cannot contain all of the levels");
			return false;
		}
		model.levels=(DefaultListModel) levels.getModel();
		model.suc = (DefaultListModel)suc.getModel();
		model.which = cut.isSelected() ? 1 : defineLevels.isSelected()?2:3;
		model.expr="";
		model.cutValue="";
		if(model.which==1){
			model.cutValue = cutValue.getText();
			model.cutDirection = (String)cutDirection.getSelectedItem();
		}
		if(model.which==3){
			model.expr = expr.getText();
		}
		return true;
	}

	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if(cmd=="OK"){
			if(updateModel())
				this.dispose();
		}else if(cmd=="Cancel"){
			this.dispose();
		}else if(cmd=="Add"){
			Object[] inds=levels.getSelectedValues();
			for(int i=0;i<inds.length;i++){
				((DefaultListModel)levels.getModel()).removeElement(inds[i]);
				((DefaultListModel)suc.getModel()).addElement(inds[i]);
			}
		}else if(cmd == "Remove"){
			Object[] inds=suc.getSelectedValues();
			for(int i=0;i<inds.length;i++){
				((DefaultListModel)suc.getModel()).removeElement(inds[i]);
				((DefaultListModel)levels.getModel()).addElement(inds[i]);
			}
		}
		
	}

	public void focusGained(FocusEvent arg0) {
		Object src = arg0.getSource();
		if(src==cutValue)
			cut.setSelected(true);
		else if(src==levels || src==suc || src==add || src==remove)
			defineLevels.setSelected(true);
		else if(src==expr)
			expression.setSelected(true);
	}
	public void focusLost(FocusEvent arg0) {}

}
