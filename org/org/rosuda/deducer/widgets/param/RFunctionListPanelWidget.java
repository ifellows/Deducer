package org.rosuda.deducer.widgets.param;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.widgets.VariableSelectorWidget;

public class RFunctionListPanelWidget extends ParamWidget{

	protected RFunctionList model;
	protected Vector widgets = new Vector();
	protected VariableSelectorWidget selector;
	private Vector params = new Vector();
	
	public RFunctionListPanelWidget(){
		super();
		initGui();
	}
	
	public RFunctionListPanelWidget(Param p){
		super();
		initGui();
		setModel(p);
	}
	
	public RFunctionListPanelWidget(Param p,VariableSelectorWidget sel){
		initGui();
		selector = sel;
		setModel(p);
	}

	
	public void setModel(Param param) {
		this.removeAll();
		params = new Vector();
		BoxLayout thisLayout = new BoxLayout(this, javax.swing.BoxLayout.Y_AXIS);
		this.setLayout(thisLayout);
		model = (RFunctionList) param;
		if(model.getGlobalParams()!=null){
			for(int i=0;i<model.getGlobalParams().length;i++){
				Param p = model.getGlobalParams()[i];
				params.add(p);
				ParamWidget a ;
				if(!p.requiresVariableSelector())
					a = p.getView();
				else{
					a = p.getView(selector);
				}
				a.setAlignmentX(CENTER_ALIGNMENT);
				widgets.add(a);
				this.add(a);
				this.add(Box.createRigidArea(new Dimension(0,5)));
			}
			this.add(Box.createRigidArea(new Dimension(0,5)));
		}
		
		for(int i=0;i<model.getOptions().length;i++){
			String funcName = model.getOptions()[i];
			RFunction rf = (RFunction) model.getFunctionMap().get(funcName);
			JPanel panel = this.getRFunctionPanel(rf,funcName,model.active.contains(funcName),
					model.isFunctionRequired(funcName));
			//panel.setBorder(new TitledBorder(funcName));
			this.add(panel);
		}
		
		this.validate();
		this.repaint();
	}
	
	public Param getModel(){
		return model;
	}
	
	public JPanel getRFunctionPanel(RFunction func,String title,boolean active,boolean required){
		final JPanel panel = new JPanel();
		BoxLayout panelLayout = new BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS);
		panel.setLayout(panelLayout);
		final HeaderPanel header = new HeaderPanel(model,title,active,required);
		panel.add(header);
		panel.add(Box.createRigidArea(new Dimension(0,10)));
		int maxHt = 50;
		int minHt = 50;
		int prefHt = 50;
		int maxMinWidth = 0;
		int maxPrefWidth = 0;
		for(int i=0;i<func.getParams().size();i++){
			Param p = (Param) func.getParams().get(i);
			if(params.contains(p))
				continue;
			params.add(p);
			//System.out.println(p.toString());
			ParamWidget a ;
			if(!p.requiresVariableSelector())
				a = p.getView();
			else{
				a = p.getView(selector);
			}
			a.setAlignmentX(CENTER_ALIGNMENT);
			widgets.add(a);
			panel.add(a);
			panel.add(Box.createRigidArea(new Dimension(0,5)));
			maxHt += a.getMaximumSize().height+5;
			minHt += a.getMinimumSize().height+5;
			maxMinWidth = Math.max(maxMinWidth, a.getMinimumSize().width);
			prefHt += a.getPreferredSize().height+5;
			maxPrefWidth = Math.max(maxPrefWidth, a.getPreferredSize().width);
		}
		if(header.getCheckBox().isSelected()){
			panel.setMaximumSize(new Dimension(2000,maxHt));
			panel.setMinimumSize(new Dimension(maxMinWidth,minHt));
			panel.setPreferredSize(new Dimension(maxPrefWidth,prefHt));
		}else{
			panel.setMaximumSize(new Dimension(2000,30));
			panel.setMinimumSize(new Dimension(maxMinWidth,30));
			panel.setPreferredSize(new Dimension(maxPrefWidth,30));
		}
		
		final int maxHtf = maxHt;
		final int maxMinWidthf = maxMinWidth;
		final int minHtf = minHt;
		final int maxPrefWidthf = maxPrefWidth;
		final int prefHtf = prefHt;
		final String name = title;
		header.getCheckBox().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				JCheckBox check = header.getCheckBox();
				if(check.isSelected()){
					panel.setMaximumSize(new Dimension(2000,maxHtf));
					panel.setMinimumSize(new Dimension(maxMinWidthf,minHtf));
					panel.setPreferredSize(new Dimension(maxPrefWidthf,prefHtf));
					RFunctionListPanelWidget.this.resize(maxPrefWidthf, prefHtf);
					if(!model.active.contains(name))
						model.active.add(name);
				}else{
					panel.setMaximumSize(new Dimension(2000,30));
					panel.setMinimumSize(new Dimension(maxMinWidthf,30));
					panel.setPreferredSize(new Dimension(maxPrefWidthf,30));
					RFunctionListPanelWidget.this.resize(250, 30);
					model.active.remove(name);
				}
			}
			
		});
		return panel;
	}

	public void updateModel() {
		for(int i=0;i<widgets.size();i++){
			Object o = widgets.get(i);
			((ParamWidget)o).updateModel();
		}
	}
	
	public void initGui(){
		BoxLayout thisLayout = new BoxLayout(this, javax.swing.BoxLayout.Y_AXIS);
		this.setLayout(thisLayout);
	}

}



class HeaderPanel extends javax.swing.JPanel implements ActionListener{
	private JSeparator sep1;
	private JButton options;
	private JSeparator jSeparator1;
	private JCheckBox check;
	private RFunctionList model;
	private String fName;
	private boolean active;
	private boolean required;
	public HeaderPanel(RFunctionList f,String funcName,boolean active,boolean required) {
		super();
		model = f;
		fName = funcName;
		this.active = active;
		this.required = required;
		initGUI();
	}
	
	private void initGUI() {
		try {
			Color sepColor = new Color(50,50,50);
			this.setPreferredSize(new java.awt.Dimension(400, 30));
			this.setMinimumSize(new java.awt.Dimension(250, 30));
			this.setMaximumSize(new java.awt.Dimension(400, 30));
			BoxLayout panelLayout = new BoxLayout(this, javax.swing.BoxLayout.X_AXIS);
			this.setLayout(panelLayout);
			{
				sep1 = new JSeparator();
				this.add(sep1);
				sep1.setPreferredSize(new java.awt.Dimension(50, 2));
				sep1.setMaximumSize(new java.awt.Dimension(50, 2));
				sep1.setAlignmentY(Component.CENTER_ALIGNMENT);
				sep1.setForeground(sepColor);
			}
			this.add(Box.createRigidArea(new Dimension(5, 0)));
			{
				check = new JCheckBox();
				this.add(check);
				check.setText(fName);
				check.setSelected(active);
				if(required){
					check.setSelected(true);
					check.setEnabled(false);
				}
				check.setAlignmentY(Component.CENTER_ALIGNMENT);
				check.setMinimumSize(new Dimension(100,check.getMinimumSize().height));
				Dimension s = check.getPreferredSize();
				check.setPreferredSize(new Dimension(Math.max(s.width, 100),s.height));
				s = check.getMaximumSize();
				check.setMaximumSize(new Dimension(Math.max(s.width, 100),s.height));
			}
			this.add(Box.createRigidArea(new Dimension(5, 0)));
			{
				options = new IconButton("/icons/advanced_21.png", "options", this, "options");
				this.add(options);
				options.setPreferredSize(new Dimension(24,24));
				options.setAlignmentY(Component.CENTER_ALIGNMENT);
			}
			this.add(Box.createHorizontalGlue());
			{
				jSeparator1 = new JSeparator();
				this.add(jSeparator1);
				jSeparator1.setPreferredSize(new java.awt.Dimension(50, 2));
				jSeparator1.setMaximumSize(new java.awt.Dimension(50, 2));
				jSeparator1.setAlignmentY(Component.CENTER_ALIGNMENT);
				jSeparator1.setForeground(sepColor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JCheckBox getCheckBox(){
		return check;
	}

	public void actionPerformed(ActionEvent e) {
		OptionDialog d = new OptionDialog(null);
		d.setModal(true);
		d.setLocationRelativeTo(options);
		d.setVisible(true);
	}

	class OptionDialog extends javax.swing.JDialog implements ActionListener {
		private JCheckBox keep;
		private JLabel nameLabel;
		private JButton okay;
		private JButton cancel;
		private JCheckBox print;
		private JTextField name;
		
		public OptionDialog(JFrame frame) {
			super(frame);
			initGUI();
		}
		
		private void initGUI() {
			try {
				{
					int ind=-1;
					for(int i =0;i<model.getOptions().length;i++)
						if(model.getOptions()[i].equals(fName))
							ind = i;
					
					getContentPane().setLayout(null);
					{
						keep = new JCheckBox();
						getContentPane().add(keep);
						keep.setText("Keep in workspace");
						keep.setBounds(12, 12, 163, 19);
						keep.setSelected(model.keep[ind].equals("true"));
					}
					{
						nameLabel = new JLabel();
						getContentPane().add(nameLabel);
						nameLabel.setText("Name:");
						nameLabel.setBounds(22, 37, 54, 15);
						nameLabel.setHorizontalAlignment(SwingConstants.TRAILING);
					}
					{
						name = new JTextField();
						getContentPane().add(name);
						name.setBounds(82, 33, 68, 22);
						name.setText(model.assignTo[ind]);
					}
					{
						print = new JCheckBox();
						getContentPane().add(print);
						print.setText("Print");
						print.setBounds(12, 67, 163, 19);
						print.setSelected(model.print[ind].equals("true"));
					}
					{
						okay = new JButton();
						getContentPane().add(okay);
						okay.setText("Okay");
						okay.setBounds(110, 110, 78, 22);
						okay.addActionListener(this);
					}
					{
						cancel = new JButton();
						getContentPane().add(cancel);
						cancel.setText("Cancel");
						cancel.setBounds(33, 110, 71, 22);
						cancel.addActionListener(this);
					}
				}
				this.setSize(200, 166);
				this.setResizable(false);
				this.setTitle("Options");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void actionPerformed(ActionEvent ae) {
			String cmd = ae.getActionCommand();
			
			if(cmd.equals("Okay")){
				for(int i=0;i<model.getOptions().length;i++)
					if(model.getOptions()[i].equals(fName)){
						model.assignTo[i] = name.getText();
						model.keep[i] = keep.isSelected() ? "true" : "false";
						model.print[i] = print.isSelected() ? "true" : "false";
					}
				this.dispose();
			}else if(cmd.equals("Cancel")){
				this.dispose();
			}
		}

	}
}
