package org.rosuda.deducer.widgets;



import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;

import org.rosuda.deducer.toolkit.AddButton;
import org.rosuda.deducer.toolkit.RemoveButton;

public class AddRemoveButtons extends JPanel{
	
	AddButton add;
	RemoveButton remove;
	
	JList left;
	JList right;
	
	
	public AddRemoveButtons(ListWidget leftList, ListWidget rightList){
		left = leftList.getList();
		right = rightList.getList();
		initGUI();
	}
	
	public AddRemoveButtons(VariableSelectorWidget selector, ListWidget rightList){
		left = selector.getJList();
		right = rightList.getList();
		initGUI();
	}
	
	protected void initGUI(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Dimension minSize = new Dimension(0, 0);
		Dimension prefSize = new Dimension(0, Short.MAX_VALUE);
		Dimension maxSize = new Dimension(0, Short.MAX_VALUE);

		
		add = new AddButton("Add",left,right);
		add.setMinimumSize(new Dimension(40,40));
		remove = new RemoveButton("Remove",left,right);
		remove.setMinimumSize(new Dimension(40,40));
		this.add(new Box.Filler(minSize, prefSize, maxSize));

		this.add(add);
		//this.add(Box.createRigidArea(new Dimension(0,5)));
		this.add(remove);
		this.add(new Box.Filler(minSize, prefSize, maxSize));
		add.setAlignmentX(CENTER_ALIGNMENT);
		add.setAlignmentY(CENTER_ALIGNMENT);
		remove.setAlignmentX(CENTER_ALIGNMENT);
		remove.setAlignmentY(CENTER_ALIGNMENT);		
		this.setPreferredSize(new Dimension(50,100));
	}
	

}
