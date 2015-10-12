package org.rosuda.deducer.widgets;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import org.rosuda.JGR.layout.AnchorConstraint;
import org.rosuda.JGR.layout.AnchorLayout;
import org.rosuda.REngine.REXP;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.WindowTracker;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.OkayCancelPanel;

/**
 * A JDialog that keeps track of any widgets put into it or widgets
 * put into an RDialog of which it is owner.
 * @author Ian
 *
 */
public class RDialog extends JDialog {

	protected Vector widgets = new Vector();		//Widgets in this dialog
	protected Vector components = new Vector();		//Components in this dialog
	
	protected HashMap models;						//Working models
	protected RDialog parent;						//Parent of this dialog
	protected Vector children = new Vector();		//Any RDialogs belonging to this one
	
	protected OkayCancelPanel okayCancelPanel;		//Control buttons
	protected HelpButton helpButton;				//Online help
	


	/*
	 * JDialog overrides
	 */
	
	public RDialog(){
		super();
		init(null);
		initGUI();
	}
	public RDialog(Dialog owner){
		super(owner);
		init(owner);
		initGUI();
	}
	public RDialog(Dialog owner, boolean modal){
		super(owner, modal);
		init(owner);
		initGUI();
	}
	public RDialog(Dialog owner, String title){
		super(owner, title);
		init(owner);
		initGUI();
	}
	public RDialog(Dialog owner, String title, boolean modal){
		super(owner, title, modal);
		init(owner);
		initGUI();
	}
	public RDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc){
		super(owner, title, modal,gc);
		init(owner);
		initGUI();
	}
	public RDialog(Frame owner){
		super(owner);
		init(owner);
		initGUI();
	}
	public RDialog(Frame owner, boolean modal){
		super(owner, modal);
		init(owner);
		initGUI();
	}
	public RDialog(Frame owner, String title){
		super(owner, title);
		init(owner);
		initGUI();
	}
	public RDialog(Frame owner, String title, boolean modal){
		super(owner, title, modal);
		init(owner);
		initGUI();
	}
	public RDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc){
		super(owner, title, modal,gc);
		init(owner);
		initGUI();
	}
	
	/*
	 * Component overrides
	*/
	
	 public Component 	add(Component comp){
		 track(comp);
		 return super.add(comp);
	 }
	 public Component 	add(Component comp, int index){
		 track(comp);
		 if(parent!=null)
			 parent.track(comp);
		 return super.add(comp,index);
	 }
	 public void 	add(Component comp, Object constraints){
		 track(comp);
		 if(parent!=null)
			 parent.track(comp);
		 super.add(comp,constraints);
	 }
	 public void 	add(Component comp, Object constraints, int index){
		 track(comp);
		 if(parent!=null)
			 parent.track(comp);
		 super.add(comp,constraints,index);
	 }
	 public Component 	add(String name, Component comp){
		 track(comp);
		 if(parent!=null)
			 parent.track(comp);
		 return super.add(name,comp);
	 }
	 public void 	remove(Component comp){
		 super.remove(comp);
		 untrack(comp);
		 if(parent!=null)
			 parent.untrack(comp);
	 }
	 public void 	remove(int index){
		 Component comp = this.getComponent(index);
		 untrack(comp); 
		 if(parent!=null)
			 parent.untrack(comp);
		 super.remove(index);
	 }
	 public void 	removeAll(){
		 super.removeAll();
		 components.removeAllElements();
		 widgets.removeAllElements();
	 }
	 
	 /*
	  * end Component overrides
	*/
	
	protected void init(Component owner){
		if(widgets == null)
			widgets = new Vector();
		if(components == null)
			components = new Vector();
		if(models == null)
			models = new HashMap();
		if(owner instanceof RDialog){
			parent = (RDialog) owner;
			parent.addSubDialog(this);
		}else
			parent=null;
	}
	
	 public void initGUI(){
		AnchorLayout thisLayout = new AnchorLayout();
		this.setLayout(thisLayout);
		this.setSize(555, 645);
	}
	
	/**
	 * adds a help button
	 * @param pageLocation
	 */
	public void addHelpButton(String pageLocation){
		if(helpButton!=null)
			this.remove(helpButton);
		
		helpButton = new HelpButton(pageLocation);
		this.add(helpButton, new AnchorConstraint(940, 77, 980, 23, 
				AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_NONE, 
				AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
		helpButton.setPreferredSize(new java.awt.Dimension(32, 32));		
	}
	
	public HelpButton getHelpButton(){return helpButton;}
	
	/**
	 * adds the okay, cancel and run buttons
	 * @param showReset show the reset button?
	 * @param isRun should the approve button be named "okay" or "run"
	 * @param lis the action listener
	 */
	public void setOkayCancel(boolean showReset,boolean isRun,ActionListener lis){
		if(okayCancelPanel!=null){
			this.remove(okayCancelPanel);
		}
		okayCancelPanel = new OkayCancelPanel(showReset, isRun, lis);
		this.add(okayCancelPanel, new AnchorConstraint(926, 978, 980, 402, 
				AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_REL, 
				AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_NONE));
		okayCancelPanel.setPreferredSize(new java.awt.Dimension(307, 32));
	}
	
	public OkayCancelPanel getOkayCancel(){return okayCancelPanel;}
	
	/**
	 * Sets all of the widgets to their last state
	 */
	public void setToLast() {
		for(int i=0;i<widgets.size();i++)
			((DeducerWidget)widgets.get(i)).resetToLast();
	}
	
	/**
	 * resets widget states to default
	 */
	public void reset(){
		for(int i=0;i<widgets.size();i++)
			((DeducerWidget)widgets.get(i)).reset();
		for(int i=0;i<children.size();i++)
			((RDialog)children.get(i)).reset();
		models = new HashMap();
	}
	
	/**
	 * dialog successfully completed
	 */
	public void completed(){
		for(int i=0;i<widgets.size();i++){
			DeducerWidget wid = (DeducerWidget)widgets.get(i);
			if(parent==null)
				wid.setLastModel(wid.getModel());
			else
				models.put(wid, wid.getModel());
		}
	}
	
	/**
	 * sets the help page for the help button
	 * @param page
	 */
	public void setHelpWikiPage(String page){
		helpButton.setUrl(HelpButton.baseUrl + page);
	}
	
	/**
	 * toggles help button visibility
	 * @param show
	 */
	public void setHelpVisible(boolean show){
		helpButton.setVisible(show);
	}
	
	/**
	 * toggles okay cancel and run button visibility
	 * @param show
	 */
	public void setOkayCancelVisible(boolean show){
		okayCancelPanel.setVisible(show);
	}
	
	/**
	 * 
	 * @return a string which can be evaluated to an R list representation of
	 * 			the widget states
	 */
	public String getWidgetStatesAsString(){
		Vector items = new Vector();
		for(int i=0;i<widgets.size();i++){
			DeducerWidget wid = (DeducerWidget)widgets.get(i);
			items.add("\n'" + wid.getTitle()+"'="+wid.getRModel());
		}
		String states = Deducer.makeRCollection(items, "list", false);	
		return states;
	}

	/**
	 * 
	 * @return an r list of widget states
	 */
	public REXP getWidgetStates(){
		return Deducer.timedEval(getWidgetStatesAsString());
	}
	
	public void add(Component comp,int top,int right, int bottom, int left, String topType,
			String rightType, String bottomType, String leftType){
			int topTyp = topType.equals("REL") ? AnchorConstraint.ANCHOR_REL : (topType.equals("ABS") ? AnchorConstraint.ANCHOR_ABS : AnchorConstraint.ANCHOR_NONE );
			int rightTyp = rightType.equals("REL") ? AnchorConstraint.ANCHOR_REL : (rightType.equals("ABS") ? AnchorConstraint.ANCHOR_ABS : AnchorConstraint.ANCHOR_NONE );
			int bottomTyp = bottomType.equals("REL") ? AnchorConstraint.ANCHOR_REL : (bottomType.equals("ABS") ? AnchorConstraint.ANCHOR_ABS : AnchorConstraint.ANCHOR_NONE );
			int leftTyp = leftType.equals("REL") ? AnchorConstraint.ANCHOR_REL : (leftType.equals("ABS") ? AnchorConstraint.ANCHOR_ABS : AnchorConstraint.ANCHOR_NONE );
			AnchorConstraint constr = new AnchorConstraint(top, right, bottom, left, 
					topTyp, rightTyp, bottomTyp, leftTyp);
			this.add(comp,constr);
	}
	
	
	/**
	 * Sets this RDialog and all ancestors to track component
	 * @param comp
	 */
	public void track(Component comp){
		 if(widgets == null)
			 widgets = new Vector();
		 if(components == null)
			 components = new Vector();
		 if(comp instanceof DeducerWidget)
			 trackWidget((DeducerWidget) comp);
		 else if(!components.contains(comp))
			 components.add(comp);
		 if(parent!=null)
			 parent.track(comp);
	}
	
	/**
	 * Stop tracking a component
	 * @param comp
	 * @return was the component being tracked by this RDialog
	 */
	public boolean untrack(Component comp){
		 if(widgets == null)
			 widgets = new Vector();
		 if(components == null)
			 components = new Vector();
		boolean isInWidgets =  widgets.remove(comp);
		boolean isTracked = isInWidgets;
		if(!isInWidgets)
			isTracked = components.remove(comp);
		 if(isTracked && parent!=null)
			 parent.untrack(comp);
		return isTracked;
	}
	
	/**
	 * Notifies RDialog that a widget should be tracked. useful when adding widgets to non-widget aware containers (e.g. a JPanel)
	 * @param wid the widget to track
	 */
	public void trackWidget(DeducerWidget wid){
		if(!widgets.contains(wid))
			widgets.add(wid);
	}
	
	/**
	 * Stop tracking a widget
	 * @param wid the widget to remove
	 * @return a boolean indicating if the widget was being tracked.
	 */
	public boolean untrackWidget(DeducerWidget wid){
		return widgets.remove(wid);
	}
	
	/**
	 * link a child dialog
	 * @param d
	 */
	public void addSubDialog(RDialog d){
		if(!children.contains(d))
			children.add(d);
	}
	
	protected void clearWorkingModels(){
		this.models = new HashMap();
		for(int i=0;i<children.size();i++)
			((RDialog)children.get(i)).clearWorkingModels();
	}
	
	/**
	 * unlink a child dialog
	 * @param d
	 * @return was tracked
	 */
	public boolean removeSubDialog(RDialog d){
		return children.remove(d);
	}
	
	/**
	 * run the dialog
	 */
	public void run(){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				if(parent==null)
					RDialog.this.setToLast();
				else{
					DeducerWidget wid;
					Object widModel;
					for(int i=0;i < widgets.size();i++){
						wid = (DeducerWidget) widgets.get(i);
						widModel = models.get(wid);
						if(widModel!=null)
							wid.setModel(widModel);
					}
				}
				RDialog.this.setVisible(true);
				if(!Deducer.isJGR()){
					WindowTracker.addWindow(RDialog.this);
					if(parent==null)
						WindowTracker.waitForAllClosed();
				}
			}
			
		});

	}
	
}
