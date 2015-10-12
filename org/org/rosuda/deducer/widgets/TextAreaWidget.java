package org.rosuda.deducer.widgets;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentListener;

import org.rosuda.deducer.Deducer;

/**
 * A widget for entering text
 * @author Ian Fellows
 *
 */
public class TextAreaWidget extends javax.swing.JPanel implements DeducerWidget{
	private JScrollPane scroller;
	private JTextArea text;
	private String title;
	private String initialModel;
	private String lastModel;
	
	
	public TextAreaWidget(){
		super();		
		initGUI();
	}
	
	/**
	 * 
	 * @param panelTitle title
	 */
	public TextAreaWidget(String panelTitle) {
		super();
		title = panelTitle;
		initGUI();
	}
	
	/**
	 * set-up gui components
	 */
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(233, 61));
			if(title!=null)
				this.setBorder(BorderFactory.createTitledBorder(title));
			{
				scroller = new JScrollPane();
				this.add(scroller, BorderLayout.CENTER);
				{
					text = new JTextArea();
					scroller.setViewportView(text);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return the text area
	 */
	public JTextArea getTextArea(){
		return text;
	}

	/**
	 * gets the current text
	 * @return the text
	 */
	public String getText(){
		return (String) getModel();
	}
	
	/**
	 * sets the text
	 * @param t the new text
	 */
	public void setText(String t){
		setModel(t);
	}
	
	
	/**
	 * adds either an caret, document or mouse listener 
	 * @param lis
	 */
	public void addListener(EventListener lis) {
		if(lis instanceof CaretListener)
			text.addCaretListener((CaretListener) lis);
		if(lis instanceof MouseListener)
			text.addMouseListener((MouseListener) lis);
		if(lis instanceof DocumentListener)
			text.getDocument().addDocumentListener((DocumentListener) lis);
	}
	
	/*
	 * Start DeducerWidget methods
	 * 
	 * The state (or model) is a String
	 */
	
	public Object getModel() {
		return text.getText();
	}

	public String getRModel() {
		return "\""+Deducer.addSlashes((String) getModel())+"\"";
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

	public void setDefaultModel(Object model)  {
		initialModel=(String) model;
		if(lastModel==null)
			lastModel = (String) model;
	}

	public void setLastModel(Object model){
		lastModel = (String)model;
	}

	public void setModel(Object model){
		if(model == null)
			model = "";
		text.setText((String) model);
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
	
	/*
	 * End DeducerWidget methods
	 */
}
