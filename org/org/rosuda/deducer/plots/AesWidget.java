package org.rosuda.deducer.plots;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPLogical;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.toolkit.VariableSelector;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboBoxUI;


public class AesWidget extends javax.swing.JPanel implements ActionListener, MouseListener{
	private IconButton addRemoveButton;
	private JTextField value;
	private JButton scale;
	private JButton colour;
	private Color colourValue;
	private JToggleButton toggleValue;
	private JToggleButton toggleVariable;
	private JComboBox variable;
	private JSlider slider;
	private Double sliderValue;
	private JComboBox options;
	private JLabel nameLab;
	private ComboBoxUI defaultComboBoxUI;
	private AesComboBoxEditor editor;
	private VariableSelector variableSelector;
	
	private boolean showToggle = true;
	

	
	private Color neededItemBackground = Color.decode("#fff3f6");
	
	private static String iconRoot = "";
	
	private DefaultComboBoxModel statVarModel;
	
	public static Vector lineOptions;
	
	public static Vector shapeOptions;
	
	private Aes model;
	
	private Component valueComponent;
	
	private FocusListener valueValidator = new ValueValidator();

	private AesTransferHandler transferHandler;
	
	public AesWidget() {
		super();
		if(lineOptions==null)
			initLineOptions();
		if(shapeOptions==null)
			initShapeOptions();
		initGUI();
	}
	
	public AesWidget(Aes aes,VariableSelector var){
		this();
		setVariableSelector(variableSelector);
		setModel(aes);		
	}
	
	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(200, 70));
			{
				{
					scale = new JButton();
					this.add(scale, new AnchorConstraint(32, 961, 764, 860, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
					scale.setPreferredSize(new java.awt.Dimension(22, 22));
					scale.addActionListener(this);
					scale.setToolTipText("Edit scale");
					scale.setVisible(false);
				}

				{
					value = new JTextField();
					this.add(value, new AnchorConstraint(450, 667, 764, 356, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
					value.setPreferredSize(new java.awt.Dimension(77, 22));
				}
				{
					colour = new JButton();
					this.add(colour, new AnchorConstraint(450, 647, 764, 264, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					colour.setText("Set colour");
					colour.setPreferredSize(new java.awt.Dimension(95, 22));
					colour.addActionListener(this);
				}
				{
					toggleValue = new JToggleButton();
					this.add(toggleValue, new AnchorConstraint(13, 24, 376, 731, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					toggleValue.setPreferredSize(new java.awt.Dimension(19, 13));
					toggleValue.setToolTipText("Use a specific value");
					toggleValue.addActionListener(this);
				}
				{
					toggleVariable = new JToggleButton();
					this.add(toggleVariable, new AnchorConstraint(13, 5, 401, 852,
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					toggleVariable.setPreferredSize(new java.awt.Dimension(19, 13));
					toggleVariable.setToolTipText("Define by a variable");
					toggleVariable.addActionListener(this);
				}
				{
					nameLab = new JLabel("aes");
					this.add(nameLab, new AnchorConstraint(13, 860, 472, 56, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
					nameLab.setPreferredSize(new java.awt.Dimension(60, 13));
				}
				{
					statVarModel = new DefaultComboBoxModel();
					editor = new AesComboBoxEditor();
					variable = new JComboBox();
					this.add(variable, new AnchorConstraint(32, 50, 472, 56, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
					variable.setModel(statVarModel);
					variable.setPreferredSize(new java.awt.Dimension(196, 22));
					variable.setEditable(true);
					variable.setEditor(editor);
					Border b = new SoftBevelBorder(BevelBorder.LOWERED);
					//Border b = new EtchedBorder(BevelBorder.LOWERED);
					editor.setBorder(b);
					variable.addMouseListener(this);
					editor.addMouseListener(this);
					variable.addActionListener(this);
					defaultComboBoxUI = variable.getUI();
					if(transferHandler == null)
						transferHandler = new AesTransferHandler();
					editor.setTransferHandler(transferHandler);
					MouseListener listener = new MouseListener() {
						public void mousePressed(MouseEvent me) {
							JComponent comp = (JComponent) me.getSource();
							TransferHandler handler = comp.getTransferHandler();
					        handler.exportAsDrag(comp, me, TransferHandler.MOVE);
						}

						public void mouseClicked(MouseEvent arg0) {
						}

						public void mouseEntered(MouseEvent arg0) {
						}

						public void mouseExited(MouseEvent arg0) {
						}

						public void mouseReleased(MouseEvent arg0) {
						}
					};
					DragGestureListener dgl = new DragGestureListener(){

						public void dragGestureRecognized(DragGestureEvent dge) {
							try{
								dge.startDrag(null, transferHandler.createTransferable(
										(AesComboBoxEditor) variable.getEditor()));
							}catch(Exception e){
								
							}
						}
						
					};
					DragSource dragSource = DragSource.getDefaultDragSource();
					dragSource.createDefaultDragGestureRecognizer(editor,
					        DnDConstants.ACTION_COPY_OR_MOVE, dgl);
					editor.addMouseListener(listener);
					
				}
				{
					slider = new JSlider(0,10000);
					this.add(slider, new AnchorConstraint(28, 933, 472, 70, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
					slider.setPreferredSize(new java.awt.Dimension(214, 41));
					slider.setMajorTickSpacing(5000);
					slider.setMinorTickSpacing(1000);
					slider.setPaintTicks(true);
					slider.setPaintLabels(true);
				}
				{
					options = new JComboBox();
					this.add(options, new AnchorConstraint(32, 933, 472, 70, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL));
					options.setPreferredSize(new java.awt.Dimension(214, 33));
					options.setRenderer(new IconComboBoxRenderer());
					options.setMaximumRowCount(7);
				}
			}
			{
				addRemoveButton = new IconButton("/icons/1rightarrow_16.png", "add", null, "add");
				this.add(addRemoveButton, new AnchorConstraint(32, 128, 483, 24, 
						AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, 
						AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
				addRemoveButton.setPreferredSize(new java.awt.Dimension(22, 22));
				addRemoveButton.addActionListener(this);
			}
			if(variableSelector!=null)
				variableSelector.getJComboBox().addActionListener(this);
			this.setMinimumSize(new Dimension(40,20));
			this.setMaximumSize(new Dimension(500,100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void initLineOptions(){
		lineOptions = new Vector();
		ImageIcon i;
		URL url;
		i = new ImageIcon();
		i.setDescription(" ");
		lineOptions.add(i);
		url = getClass().getResource("/icons/ggplot_icons/points_and_lines/solid_line.png");
		i = new ImageIcon(url);
		i.setDescription("Solid");
		lineOptions.add(i);
		url = getClass().getResource("/icons/ggplot_icons/points_and_lines/dashed_line.png");
		i = new ImageIcon(url);
		i.setDescription("Dash");
		lineOptions.add(i);
		url = getClass().getResource("/icons/ggplot_icons/points_and_lines/dotted_line.png");
		i = new ImageIcon(url);
		i.setDescription("Dot");
		lineOptions.add(i);
		url = getClass().getResource("/icons/ggplot_icons/points_and_lines/dot_dashed_line.png");
		i = new ImageIcon(url);
		i.setDescription("Dot dash");
		lineOptions.add(i);
		url = getClass().getResource("/icons/ggplot_icons/points_and_lines/long_dashed_line.png");
		i = new ImageIcon(url);
		i.setDescription("Long dash");
		lineOptions.add(i);
		url = getClass().getResource("/icons/ggplot_icons/points_and_lines/two_dashed_line.png");
		i = new ImageIcon(url);
		i.setDescription("Double dash");
		lineOptions.add(i);
	}
	
	private void initShapeOptions(){
		shapeOptions = new Vector();
		ImageIcon i;
		URL url;
		i = new ImageIcon();
		i.setDescription(" ");
		shapeOptions.add(i);
		for(int k=1;k<=25;k++){
			url = getClass().getResource("/icons/ggplot_icons/points_and_lines/"+k+".png");
			i = new ImageIcon(url);
			i.setDescription("  : " + k);
			shapeOptions.add(i);
		}
	}
	
	public void setVariableSelector(VariableSelector var){
		if(variableSelector!=null)
			variableSelector.getJComboBox().removeActionListener(this);
		variableSelector = var;
		variableSelector.getJComboBox().addActionListener(this);
	}
	
	public VariableSelector getVariableSelector(){
		return variableSelector;
	}
	
	public void setModel(Object model) {
		setModel((Aes) model);
	}
	
	public void setModel(Aes newModel){
		Color c = new Color(90,90,90);
		editor.setBackground(Color.white);
		boolean set = newModel.defaultVariable!=null || newModel.variable!=null || newModel.value!=null;
		if(newModel.required && !set){
			c = new Color(150,0,0);
			editor.setBackground(neededItemBackground);
		}
		if(newModel.required && set)
			c = new Color(0,150,0);
		
		int nameWidth = SwingUtilities.computeStringWidth(
				nameLab.getFontMetrics(nameLab.getFont()),
				newModel.title);
		nameLab.setText(newModel.title);
		nameLab.setPreferredSize(new Dimension(nameWidth,22));
		nameLab.setForeground(c);
		int leftPos = Math.max(10+nameWidth, 56);
		this.remove(value);
		this.add(value, new AnchorConstraint(13, 50, 764, leftPos, 
				AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, 
				AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
		value.setVisible(false);
		this.remove(colour);
		this.add(colour, new AnchorConstraint(13, 50, 764, leftPos, 
				AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, 
				AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));		
		colour.setVisible(false);
		this.remove(slider);
		this.add(slider, new AnchorConstraint(13, 50, 764, leftPos, 
				AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, 
				AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
		slider.setVisible(false);
		this.remove(options);
		this.add(options, new AnchorConstraint(13, 50, 764, leftPos, 
				AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, 
				AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
		options.setVisible(false);

		value.removeFocusListener(valueValidator);
		value.addFocusListener(valueValidator);
		variable.setSelectedItem(null);		
		if(newModel.variable!=null)
			variable.setSelectedItem(newModel.variable);
		
		if(newModel.dataType.equals(Aes.DATA_ANY) ||
				newModel.dataType.equals(Aes.DATA_NUMERIC) ||
				(newModel.dataType.equals(Aes.DATA_NUMERIC_BOUNDED) && 
						(newModel.lowerBound==null || newModel.upperBound==null))){
			valueComponent = value;
			if(newModel.value!=null)
				value.setText(newModel.value.toString());
				
		}else if(newModel.dataType.equals(Aes.DATA_COLOUR)){
			valueComponent = colour;
			if(newModel.value!=null && newModel.value instanceof Color){
				colour.setForeground((Color) newModel.value);
				colourValue = (Color) newModel.value;
			}else if(newModel.value!=null)
				JOptionPane.showMessageDialog(this, "invalid colour value:"+ 
						newModel.value.toString());
		}else if(newModel.dataType.equals(Aes.DATA_NUMERIC_BOUNDED)){
			Double d = new Double(0.0);
			int ind;
			if(newModel.value!=null){
				double dv = ((Double)newModel.value).doubleValue() - newModel.lowerBound.doubleValue();
				dv = dv / (newModel.upperBound.doubleValue() - newModel.lowerBound.doubleValue());
				dv = dv*10000.0;
				d = new Double(dv);
				sliderValue = d;
				ind = Math.round(d.floatValue());
			}else
				ind = 1;
			if(ind<0)
				ind=0;
			if(ind>10000)
				ind=10000;
			slider.setValue(ind);
			valueComponent = slider;
			Hashtable labelTable = new Hashtable();
			labelTable.put( new Integer( 0 ), new JLabel(newModel.lowerBound.toString()));
			labelTable.put( new Integer( 5000 ), 
					new JLabel((newModel.upperBound.doubleValue() - newModel.lowerBound.doubleValue())/2.0+""));
			labelTable.put( new Integer( 10000 ), 
					new JLabel(newModel.upperBound.toString()));
			slider.setLabelTable( labelTable );
			slider.setPaintLabels(true);
			this.setMinimumSize(new Dimension(0,70));
		}else if(newModel.dataType.equals(Aes.DATA_NONE) ){
			valueComponent = new JPanel();
		}else if(newModel.dataType.equals(Aes.DATA_LINE)){
			DefaultComboBoxModel cm = new DefaultComboBoxModel();
			for(int i=0;i<lineOptions.size();i++)
				cm.addElement(lineOptions.get(i));
			options.setModel(cm);
			if(newModel.value!= null && newModel.value instanceof Integer)
				options.setSelectedIndex(((Integer)newModel.value).intValue());
			valueComponent = options;
		}else if(newModel.dataType.equals(Aes.DATA_SHAPE)){
			DefaultComboBoxModel cm = new DefaultComboBoxModel();
			for(int i=0;i<shapeOptions.size();i++)
				cm.addElement(shapeOptions.get(i));
			options.setModel(cm);
			if(newModel.value!= null && newModel.value instanceof Integer)
				options.setSelectedIndex(((Integer)newModel.value).intValue());
			valueComponent = options;
		}
		toggleVariable(newModel.useVariable);
		model=newModel;
		refreshAddRemoveButton();
	}
	
	public void updateModel(){
			model.useVariable = toggleVariable.isSelected();
			Object var = variable.getSelectedItem();
			if(var!=null && var.toString().length()>0)
				model.variable = var.toString();
			else
				model.variable=null;
			Object val = null;
			if(valueComponent == value){
				val = value.getText();
				if(val.toString().length()<=0)
					model.value = null;
				else if(model.dataType.equals(Aes.DATA_NUMERIC) || model.dataType.equals(Aes.DATA_NUMERIC_BOUNDED)){
					try{
						Double d = new Double(Double.parseDouble((String) val));
						if(model.lowerBound!=null && d.doubleValue()<model.lowerBound.doubleValue()){
							d = (Double) model.value;
							value.setText(model.value.toString());
						}
						if(model.upperBound!=null && d.doubleValue()>model.upperBound.doubleValue()){
							d = (Double) model.value;
							value.setText(model.value.toString());
						}
						model.value = d;
					}catch(Exception e){
						if(model.value!=null)
							value.setText(model.value.toString());
					}
				}else
					model.value = val.toString();
			}else if(valueComponent == colour){
				model.value = colourValue;
			}else if(valueComponent == slider){
				double num = slider.getValue();
				num = num / 10000.0;
				num = num*(model.upperBound.doubleValue()-model.lowerBound.doubleValue());
				num = num + model.lowerBound.doubleValue();
				if(sliderValue==null &&slider.getValue()==1){
					model.value = null;
				}else
					model.value = new Double(num);
			}else if(valueComponent == options){
				int ind = options.getSelectedIndex();
				if(ind<=0)
					model.value = null;
				else
					model.value = new Integer(ind);
				
			}
	}
	
	public Aes getModel(){
		updateModel();
		return model;
	}

	public void setCalculatedVariables(Vector vars){
		Object var = variable.getSelectedItem();
		statVarModel.removeAllElements();
		statVarModel.addElement(null);
		for(int i=0;i<vars.size();i++){
			statVarModel.addElement(".."+vars.get(i).toString()+"..");
		}
		if(statVarModel.getSize()<2){
			variable.setUI(new BasicComboBoxUI() {
			    protected JButton createArrowButton() {
			        return new JButton() {
			                public int getWidth() {
			                        return 0;
			                }
			        };
			    }
			});
			variable.validate();
			variable.repaint();		
		}else{
			variable.setUI(defaultComboBoxUI);
			variable.validate();
			variable.repaint();
		}
		variable.setSelectedItem(var);
	}

	
	public void toggleVariable(boolean showVariable){
		if(!showVariable){
			this.remove(nameLab);
			this.add(nameLab, new AnchorConstraint(13, 860, 472, 5, 
					AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, 
					AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
			variable.setVisible(false);
			addRemoveButton.setVisible(false);
			scale.setVisible(false);
			toggleVariable.setSelected(false);
			toggleValue.setSelected(true);
			valueComponent.setVisible(true);
			if(valueComponent!=slider)
				this.setPreferredSize(new Dimension(200,35));
			else
				this.setPreferredSize(new Dimension(200,50));
		}else{
			this.remove(nameLab);
			this.add(nameLab, new AnchorConstraint(13, 860, 472, 56, 
					AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, 
					AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
			variable.setVisible(true);
			addRemoveButton.setVisible(true);
			scale.setVisible(false);
			toggleVariable.setSelected(true);
			toggleValue.setSelected(false);
			valueComponent.setVisible(false);
			this.setPreferredSize(new Dimension(200,60));
		}
	}
	
	public void refreshTitleColour(){
		Color c = new Color(90,90,90);
		boolean set = model.defaultVariable!=null || 
		model.variable!=null || model.value!=null;
		if(model.required && !set){
			c = new Color(150,0,0);
			editor.setBackground(neededItemBackground);
		}else
			editor.setBackground(Color.white);
		if(model.required && set)
			c = new Color(0,150,0);
		nameLab.setForeground(c);
	}
	
	public void refreshAddRemoveButton(){
		if(addRemoveButton==null || variable ==null)
			return;
		if(variable.getSelectedItem()== null || 
				variable.getSelectedItem().toString().length()<=0){
			
			addRemoveButton.setToolTipText("add");
			addRemoveButton.setActionCommand("add");
			ImageIcon icon = 
				new ImageIcon(getClass().getResource("/icons/1rightarrow_16.png"));
			addRemoveButton.setIcon(icon);
		}else{
			addRemoveButton.setToolTipText("remove");
			addRemoveButton.setActionCommand("remove");
			ImageIcon icon =
				new ImageIcon(getClass().getResource("/icons/1leftarrow_16.png"));
			addRemoveButton.setIcon(icon);
		}
	}
	
	public void setVariable(String var){
		String data = variableSelector.getSelectedData();
		if(model.preferNumeric){
			REXP exp = Deducer.timedEval("plyr::is.discrete(" + data + "$" + var+")");
			if(exp!=null && exp.isLogical()){
				if(((REXPLogical)exp).isTRUE()[0])
					var = "as.numeric("+var + ")";
			}
		}else if(model.preferCategorical){
			REXP exp = Deducer.timedEval("plyr::is.discrete(" + data + "$" + var+")");
			//System.out.println(exp.toDebugString());
			if(exp!=null && exp.isLogical()){
				if(((REXPLogical)exp).isFALSE()[0])
					var = "as.factor("+var + ")";
			}
		}
		variable.setSelectedItem(var);
	}

	public void actionPerformed(ActionEvent ev) {
		boolean switchToVariable = false;
		if(ev.getSource() == toggleValue){
			switchToVariable = !toggleValue.isSelected();
			toggleVariable(switchToVariable);
			return;
		}
		if(ev.getSource() == toggleVariable){
			switchToVariable = toggleVariable.isSelected();
			toggleVariable(switchToVariable);
			return;
		}
		if(ev.getSource()==colour){
			Color c =JColorChooser.showDialog(this, "Choose Colour", 
					colourValue!=null ? colourValue : Color.white);
			if(c!=null){
				colour.setForeground(c);
				colourValue = c;
			}
		}
		if(ev.getSource()==scale){
			JOptionPane.showMessageDialog(this, "TODO: unimplemented");
		}
		if(ev.getSource() == addRemoveButton){
			String cmd = ev.getActionCommand();
			if(variableSelector!=null){
				if(cmd == "add"){
					ArrayList l = variableSelector.getSelectedVariables();
					if(l.size()>0)
						setVariable((String)l.get(0));
				}else if(cmd == "remove"){
					variable.setSelectedItem(null);
				}
			}
		}
		if(ev.getSource() == variableSelector.getJComboBox()){
			//System.out.println(variable.getSelectedItem().toString());
			Object o = variable.getSelectedItem();
			String data = variableSelector.getSelectedData();
			if(o!=null && !o.toString().startsWith("..")){
				boolean exists = ((REXPLogical)Deducer.timedEval("'" +o.toString()+"' %in% names("+data+")")).isTRUE()[0];
				if(!exists)
					variable.setSelectedItem(null);
				refreshAddRemoveButton();
			}
		}
		if(ev.getSource() == variable){
			refreshAddRemoveButton();
		}
		if(model!=null){
			updateModel();
			refreshTitleColour();
		}
	}
	
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getClickCount()==2){
			Object cur = variable.getSelectedItem();
			String a = JOptionPane.showInputDialog(variable, 
					"Edit variable by hand. For example log(variable)",
					cur==null ? null : cur.toString());
			if(a!=null){
				variable.setSelectedItem(a);
			}			
		}
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {
		if(arg0.isPopupTrigger()){
			Object cur = variable.getSelectedItem();
			String a = JOptionPane.showInputDialog(variable, 
					"Edit variable by hand. For example log(variable)",
					cur==null ? null : cur.toString());
			if(a!=null){
				variable.setSelectedItem(a);
			}
		}		
	}

	public void mouseReleased(MouseEvent arg0) {
		if(arg0.isPopupTrigger()){
			Object cur = variable.getSelectedItem();
			String a = JOptionPane.showInputDialog(variable, 
					"Edit variable by hand. For example log(variable)",
					cur==null ? null : cur.toString());
			if(a!=null){
				variable.setSelectedItem(a);
			}
		}
	}
	
	public void setShowToggle(boolean showToggle) {
		this.showToggle = showToggle;
		toggleVariable.setVisible(showToggle);
		toggleValue.setVisible(showToggle);

	}

	public boolean isShowToggle() {
		return showToggle;
	}

	class AesComboBoxEditor extends JLabel implements ComboBoxEditor {

		public AesComboBoxEditor() {
			super();
	        setOpaque(true);
	        this.setBackground(Color.white);
	    }

		public Component getEditorComponent() {
			return this;
		}

		public Object getItem() {
			return this.getText();
		}

		public void setItem(Object o) {
			if(o!=null){
				this.setText(o.toString());
				variable.setSelectedItem(o.toString());
			}else{
				this.setText("");
				variable.setSelectedItem("");
			}
			refreshAddRemoveButton();
		}

		public void addActionListener(ActionListener l) {}
		public void removeActionListener(ActionListener l) {}
		public void selectAll() {}

	}
	
	class IconComboBoxRenderer extends JLabel implements ListCellRenderer {
		public IconComboBoxRenderer() {
				super();
				setOpaque(true);
				setVerticalAlignment(CENTER);
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			
			if (isSelected) {
	            setBackground(list.getSelectionBackground());
	            setForeground(list.getSelectionForeground());
	        } else {
	            setBackground(list.getBackground());
	            setForeground(list.getForeground());
	        }
			if(!(value instanceof ImageIcon)){
				this.setIcon(null);
				this.setText(value.toString());
				return this;
			}
			ImageIcon i = (ImageIcon) value;
			if(i.getImage()!=null)
				this.setIcon(i);
			else
				this.setIcon(null);
			this.setText(i.getDescription());
			return this;
		}
	}
	
	class ValueValidator implements FocusListener{

		public void focusGained(FocusEvent arg0) {}

		public void focusLost(FocusEvent arg0) {
			if(model!=null){
				updateModel();
				refreshTitleColour();
			}
		}
	}

	class AesTransferHandler extends TransferHandler{
		

		DataFlavor arrayListFlavor = new DataFlavor(ArrayList.class, "ArrayList");
		
		ArrayList lastData = new ArrayList();
		
		public boolean canImport(JComponent c, DataFlavor[] flavors) {
			return true;
		}
		
		public int getSourceActions(JComponent c) {
			return COPY_OR_MOVE;
		}
		public boolean importData(JComponent c, Transferable t) {
			try{
				if(t.isDataFlavorSupported(arrayListFlavor)){
					ArrayList al = (ArrayList) t.getTransferData(arrayListFlavor);
					if(al.size()>1)
						return false;
					String val = al.get(0).toString();
					//AesComboBoxEditor editor = (AesComboBoxEditor) c;
					setVariable(val);
					return true;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return false;
		}
		
		public void exportAsDrag(JComponent comp, InputEvent e, int action){
		}
		
		protected void exportDone(JComponent c, Transferable data, int action) {
			AesComboBoxEditor editor = (AesComboBoxEditor) c;
			//if(action == DnDConstants.ACTION_MOVE)
				editor.setItem("");
		}
		
		public Transferable createTransferable(JComponent c){
			ArrayList l = new ArrayList();
			AesComboBoxEditor editor = (AesComboBoxEditor) c;
			l.add(editor.getText());
			editor.setItem("");
			return new ArrayListTransferable(l);
		}
	}
	
	public class ArrayListTransferable implements Transferable {
		DataFlavor arrayListFlavor = new DataFlavor(ArrayList.class, "ArrayList");
	    ArrayList data;

	    public ArrayListTransferable(ArrayList alist) {
	      data = alist;
	    }

	    public Object getTransferData(DataFlavor flavor)
	        throws UnsupportedFlavorException {
	      if (!isDataFlavorSupported(flavor)) {
	        throw new UnsupportedFlavorException(flavor);
	      }
	      return data;
	    }

	    public DataFlavor[] getTransferDataFlavors() {
	      return new DataFlavor[] { arrayListFlavor};
	    }

	    public boolean isDataFlavorSupported(DataFlavor flavor) {
	      if (arrayListFlavor.equals(flavor)) {
	        return true;
	      }
	      return false;
	    }
	  }


}
