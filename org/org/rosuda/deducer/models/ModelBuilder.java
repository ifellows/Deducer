package org.rosuda.deducer.models;


import org.rosuda.JGR.RController;
import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.REngine.REXPLogical;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.IconButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;




public class ModelBuilder extends javax.swing.JDialog implements ActionListener, KeyListener, MouseListener {
	protected JPanel varPanel;
	protected JScrollPane varScroller;
	protected JList varList;
	protected JPanel modelPanel;
	protected JButton contrasts;
	protected JButton poly;
	protected JButton more;
	protected JList outcomes;
	protected JScrollPane outcomeScroller;
	protected JPanel outcomePanel;
	protected JButton threeWay;
	protected JButton twoWay;
	protected JSeparator sep1;
	protected JButton in;
	protected JSeparator sep;
	protected JButton minus;
	protected JButton factorial;
	protected JButton interaction;
	protected JButton addMain;
	protected JButton remove;
	protected JList modelTerms;
	protected HelpButton help;
	protected OkayCancelPanel okayCancelPanel;
	protected JScrollPane modelScroller;
	protected DefaultListModel modelTermsModel;
	protected ModelModel model;
	
	public ModelBuilder(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	public ModelBuilder(JDialog d,ModelModel mod) {	
		super(d);
		initGUI();
		setModel(mod);
	}
	public ModelBuilder(ModelModel mod) {	
		super();
		initGUI();
		setModel(mod);
	}
	
	
	private void initGUI() {
		try {
			{
				AnchorLayout thisLayout = new AnchorLayout();
				getContentPane().setLayout(thisLayout);
				{
					help = new HelpButton("");
					getContentPane().add(help, new AnchorConstraint(916, 73, 7, 12, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
					help.setPreferredSize(new java.awt.Dimension(32, 32));
				}
				{
					okayCancelPanel = new OkayCancelPanel(true,true,this);
					getContentPane().add(okayCancelPanel, new AnchorConstraint(927, 12, 7, 547, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE));
					okayCancelPanel.setPreferredSize(new java.awt.Dimension(350, 28));
					okayCancelPanel.getApproveButton().setText("Continue");
				}
				{
					varPanel = new JPanel();
					BorderLayout varPanelLayout = new BorderLayout();
					varPanel.setLayout(varPanelLayout);
					getContentPane().add(varPanel, new AnchorConstraint(233, 365, 872, 20, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					varPanel.setBorder(BorderFactory.createTitledBorder("Variables"));
					varPanel.setPreferredSize(new java.awt.Dimension(215, 302));
					{
						varScroller = new JScrollPane();
						varPanel.add(varScroller, BorderLayout.CENTER);
						{
							ListModel varListModel = 
								new DefaultListModel();
							varList = new JList();
							varScroller.setViewportView(varList);
							varList.setModel(varListModel);
						}
					}
				}
				{
					modelPanel = new JPanel();
					BorderLayout modelPanelLayout = new BorderLayout();
					modelPanel.setLayout(modelPanelLayout);
					getContentPane().add(modelPanel, new AnchorConstraint(233, 994, 872, 487, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					modelPanel.setBorder(BorderFactory.createTitledBorder("Model"));
					modelPanel.setPreferredSize(new java.awt.Dimension(315, 302));
					{
						modelScroller = new JScrollPane();
						modelPanel.add(modelScroller, BorderLayout.CENTER);
						{
							modelTermsModel = new DefaultListModel();
							modelTerms = new JList();
							modelScroller.setViewportView(modelTerms);
							modelTerms.setModel(modelTermsModel);
							modelTerms.addKeyListener(this);
							modelTerms.addMouseListener(this);
							modelTerms.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
						}
						{

						}
					}
				}
				{
					remove = new JButton();
					getContentPane().add(remove, new AnchorConstraint(869, 814, 916, 674, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					remove.setText("Remove");
					remove.setPreferredSize(new java.awt.Dimension(87, 22));
					remove.addActionListener(this);
				}
				{
					addMain = new IconButton("/icons/edit_add_24.png","Add main effect",null,"+");
					getContentPane().add(addMain, new AnchorConstraint(383, 457, 459, 399, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					addMain.addActionListener(this);

					addMain.setPreferredSize(new java.awt.Dimension(36, 36));
				}
				{
					sep = new JSeparator();
					getContentPane().add(sep, new AnchorConstraint(472, 478, 485, 377, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					sep.setPreferredSize(new java.awt.Dimension(63, 6));
				}
				
				{
					interaction = new IconButton("/icons/colon_24.png","Add interaction effect",null,":");
					getContentPane().add(interaction, new AnchorConstraint(485, 457, 561, 399, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					interaction.setPreferredSize(new java.awt.Dimension(36, 36));
					interaction.addActionListener(this);
				}
				{
					factorial = new IconButton("/icons/interaction_24.png","Add factorial interaction",null,"*");
					getContentPane().add(factorial, new AnchorConstraint(561, 457, 637, 399, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					factorial.setPreferredSize(new java.awt.Dimension(36, 36));
					factorial.addActionListener(this);
				}
				{
					minus = new IconButton("/icons/edit_minus_24.png","Add minus effect",null,"-");
					getContentPane().add(minus, new AnchorConstraint(637, 457, 713, 399, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					minus.setPreferredSize(new java.awt.Dimension(36, 36));
					minus.addActionListener(this);
				}
				{
					in = new IconButton("/icons/in_24.png","Add Nested term",null,"in");
					getContentPane().add(in, new AnchorConstraint(713, 457, 789, 399, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL,
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					in.setPreferredSize(new java.awt.Dimension(36, 36));
					in.addActionListener(this);
				}
				{
					sep1 = new JSeparator();
					getContentPane().add(sep1, new AnchorConstraint(366, 478, 371, 375, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					sep1.setPreferredSize(new java.awt.Dimension(64, 2));
				}
				{
					twoWay = new JButton();
					getContentPane().add(twoWay, new AnchorConstraint(248, 478, 294, 375, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					twoWay.setText("2-way");
					twoWay.setFont(new Font("Dialog",Font.PLAIN,10));
					twoWay.setPreferredSize(new java.awt.Dimension(64, 22));
					twoWay.addActionListener(this);
				}
				{
					threeWay = new JButton();
					getContentPane().add(threeWay, new AnchorConstraint(307, 478, 354, 375, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					threeWay.setText("3-way");
					threeWay.setFont(new Font("Dialog",Font.PLAIN,10));
					threeWay.setPreferredSize(new java.awt.Dimension(64, 22));
					threeWay.addActionListener(this);
				}
				{
					outcomePanel = new JPanel();
					BorderLayout outcomePanelLayout = new BorderLayout();
					outcomePanel.setLayout(outcomePanelLayout);
					getContentPane().add(outcomePanel, new AnchorConstraint(13, 994, 233, 487, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
					outcomePanel.setBorder(BorderFactory.createTitledBorder("Outcomes"));
					outcomePanel.setPreferredSize(new java.awt.Dimension(315, 104));
					{
						outcomeScroller = new JScrollPane();
						outcomePanel.add(outcomeScroller, BorderLayout.CENTER);
						{
							ListModel outcomesModel = 
								new DefaultListModel();
							outcomes = new JList();
							outcomeScroller.setViewportView(outcomes);
							outcomes.setModel(outcomesModel);
							outcomes.addMouseListener(this);
						}
					}
				}
				{
					more = new JButton();
					getContentPane().add(more, new AnchorConstraint(70, 259, 195, 58, 
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
					more.setText("Specify");
					more.setPreferredSize(new java.awt.Dimension(103, 22));
					more.addActionListener(this);
				}
				{
					poly = new JButton();
					getContentPane().add(poly, new AnchorConstraint(802, 478, 848, 375, 
							AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE));
					poly.setText("poly");
					poly.setPreferredSize(new java.awt.Dimension(64, 22));
					poly.addActionListener(this);
				}
				{
					contrasts = new JButton();
					getContentPane().add(contrasts, new AnchorConstraint(31, 259, 113, 58,
							AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, 
							AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
					contrasts.setText("Contrasts");
					contrasts.setPreferredSize(new java.awt.Dimension(103, 22));
				}
			}
			this.setTitle("Model Builder");
			this.setSize(630, 507);
			contrasts.setVisible(false);
		} catch (Exception e) {
			new ErrorMsg(e);
		}
	}
	
	public void setModel(ModelModel mod){
		
		DefaultListModel varModel = new DefaultListModel();
		String tmp="";
		for(int i=0;i<mod.numericVars.getSize();i++){
			if(!mod.numericVars.getElementAt(i).equals(
					RController.makeValidVariableName((String)mod.numericVars.getElementAt(i)))){
				tmp="`"+mod.numericVars.getElementAt(i)+"`";
			}else
				tmp=(String)mod.numericVars.getElementAt(i);
			if(((REXPLogical)Deducer.timedEval("is.numeric("+mod.data+"$"+tmp+")")).isFALSE()[0])
				tmp="as.numeric("+tmp+")";
			varModel.addElement(tmp);
		}
		for(int i=0;i<mod.factorVars.getSize();i++){
			if(!mod.factorVars.getElementAt(i).equals(
					RController.makeValidVariableName((String)mod.factorVars.getElementAt(i)))){
				tmp="`"+mod.factorVars.getElementAt(i)+"`";
			}else
				tmp=(String)mod.factorVars.getElementAt(i);
			if(((REXPLogical)Deducer.timedEval("is.factor("+mod.data+"$"+tmp+")")).isFALSE()[0])
				tmp="as.factor("+tmp+")";
			varModel.addElement(tmp);
		}
		varList.setModel(varModel);
		
		DefaultListModel outModel = new DefaultListModel();
		for(int i=0;i<mod.outcomes.getSize();i++){
			tmp=(String)mod.outcomes.getElementAt(i);
			outModel.addElement(tmp);
		}
		outcomes.setModel(outModel);
		if(mod.terms.size()>0){
			modelTermsModel = mod.terms;
			modelTerms.setModel(modelTermsModel);
		}else{
			DefaultListModel terms = new DefaultListModel();
			for(int i=0;i<varModel.size();i++)
				terms.addElement(varModel.elementAt(i));
			modelTermsModel = terms;
			modelTerms.setModel(modelTermsModel);
		}
		model=mod;
	}
	
	public void updateModel(){
		model.terms = modelTermsModel;
		model.outcomes = (DefaultListModel) outcomes.getModel();
	}
	public void editSelectedTerm(){
		String term = JOptionPane.showInputDialog(modelTerms,"Edit term", modelTerms.getSelectedValue());
		if(term!=null){
			if(modelTerms.getSelectedIndex()>=0)
				modelTermsModel.setElementAt(term, modelTerms.getSelectedIndex());
			else
				modelTermsModel.addElement(term);
		}		
	}
	
	public void editSelectedOutcome(){
		if(outcomes.getSelectedIndex()>=0){
			String term = JOptionPane.showInputDialog(outcomes,"Edit  outcome term", outcomes.getSelectedValue());
			if(term!=null)
				((DefaultListModel)outcomes.getModel()).setElementAt(term, outcomes.getSelectedIndex());			
		}
	}

	
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_DELETE || e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
			int[] inds = modelTerms.getSelectedIndices();
			if(inds!=null)
				for(int i = inds.length-1;i>=0;i--)
					modelTermsModel.remove(inds[i]);
		}		
	}
	
	public void keyTyped(KeyEvent e) {}

	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getClickCount()==2 && arg0.getSource()==modelTerms){
			editSelectedTerm();
		}else if(arg0.getClickCount()==2 && arg0.getSource()==outcomes){
			editSelectedOutcome();
		}
	}
	
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

	
	public void actionPerformed(ActionEvent ev) {
		String cmd = ev.getActionCommand();
		if(cmd=="+"){
			Object[] sel = varList.getSelectedValues();
			for(int i=0;i<sel.length;i++)
				if(!modelTermsModel.contains(sel[i]))
					modelTermsModel.addElement((String)sel[i]);
		}else if(cmd=="Remove"){
			int[] inds = modelTerms.getSelectedIndices();
			if(inds!=null)
				for(int i = inds.length-1;i>=0;i--)
					modelTermsModel.remove(inds[i]);
		}else if(cmd == ":"){
			Object[] sel = varList.getSelectedValues();
			if(sel==null || sel.length<2){
				JOptionPane.showMessageDialog(null, "Interaction (\":\") requires at least two variables.\nPlease select two or more variables");
				return;
			}
			String term = (String) sel[0];
			for(int i =1;i<sel.length;i++){
				term+=":"+sel[i];
			}
			if(!modelTermsModel.contains(term))
				modelTermsModel.addElement(term);
		}else if(cmd == "*"){
			Object[] sel = varList.getSelectedValues();
			if(sel==null || sel.length<2){
				JOptionPane.showMessageDialog(null, "Factorial interaction (\"*\") requires at least two variables.\nPlease select two or more variables");
				return;
			}
			String term = (String) sel[0];
			for(int i =1;i<sel.length;i++){
				term+="*"+sel[i];
			}
			if(!modelTermsModel.contains(term))
				modelTermsModel.addElement(term);
		}else if(cmd == "-"){
			Object[] sel = varList.getSelectedValues();
			if(sel==null || sel.length<1){
				JOptionPane.showMessageDialog(null, "Please select at least one variable");
				return;
			}
			String term = (String) sel[0];
			for(int i =1;i<sel.length;i++){
				term+=":"+sel[i];
			}
			term="-"+term;
			if(!modelTermsModel.contains(term))
				modelTermsModel.addElement(term);
		}else if(cmd == "2-way" || cmd == "3-way"){
			Object[] sel = varList.getSelectedValues();
			if(sel==null || sel.length<(cmd=="3-way" ? 3:2)){
				JOptionPane.showMessageDialog(null, "Two way interactions require two or more variables." +
													"\nThree way interactions require three or more");
				return;
			}
			String term = (String) sel[0];
			for(int i =1;i<sel.length;i++){
				term+="+"+sel[i];
			}
			term="("+term+")^"+(cmd=="3-way" ? 3:2);
			if(!modelTermsModel.contains(term))
				modelTermsModel.addElement(term);
		}else if(cmd == "in"){
			Object[] sel = varList.getSelectedValues();
			if(sel==null || sel.length!=2){
				JOptionPane.showMessageDialog(null, "Please select two variables");
				return;
			}
			int result = JOptionPane.showOptionDialog(in, "Nesting", "Nesting", JOptionPane.OK_CANCEL_OPTION, 
										JOptionPane.QUESTION_MESSAGE, null, new String[]{sel[0]+" %in% "+sel[1],sel[1]+" %in% "+sel[0]}, null);
			String term;
			if(result==JOptionPane.OK_OPTION)
				term=sel[0]+" %in% "+sel[1];
			else
				term=sel[1]+" %in% "+sel[0];
			if(!modelTermsModel.contains(term))
				modelTermsModel.addElement(term);
		}else if(cmd == "poly"){
			Object[] sel = varList.getSelectedValues();
			if(sel==null || sel.length!=1){
				if(sel!=null)
					varList.setSelectedIndices(new int[]{varList.getSelectedIndex()});
				return;
			}
			String order = JOptionPane.showInputDialog(poly, "Polynomial Order", "Orthoginal Polynomials", JOptionPane.QUESTION_MESSAGE);
			int ord = 1;
			try{
				ord=Integer.parseInt(order);
			}catch(Exception ex){
				JOptionPane.showMessageDialog(poly, "Invalid Order\nOrder must be a positive integer");
				return;
			}
			if(ord<1){
				JOptionPane.showMessageDialog(poly, "Invalid Order\nOrder must be a positive integer");
				return;
			}
			String term = "poly("+sel[0]+","+ord+")";
			if(!modelTermsModel.contains(term))
				modelTermsModel.addElement(term);
		}else if(cmd=="Cancel"){
			this.dispose();
		}else if(cmd=="Continue"){
			done();
		}else if(cmd=="Reset"){
			reset();
		}else if(cmd=="Specify"){
			specify();
		}
	}

	public void specify() {}
	public void done(){}
	public void reset(){}

}
