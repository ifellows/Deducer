package org.rosuda.deducer.toolkit;

import java.awt.Point;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

public class SideWindow extends JFrame implements WindowListener, ComponentListener{

	protected Window parent;
	protected int offset = 20;
	protected boolean ordering = false;
	
	public SideWindow(Window theParent) {
		super();
		this.setUndecorated(true);
		parent=theParent;
		parent.addComponentListener(this);
		parent.addWindowListener(this);
		updateLocation();
		updateSize();
	}
	
	public void updateLocation(){
		Point p = parent.getLocationOnScreen();
		int width = parent.getWidth();
		this.setLocation(p.x+width, p.y+offset);	
	}
	
	public void updateSize(){
		this.setSize(this.getWidth(),parent.getHeight()-2*offset);
	}
	
	public void windowActivated(WindowEvent arg0) {
		final Window win = this;
		if(this.isVisible() && !ordering){
			  SwingUtilities.invokeLater(new Runnable() {
				    public void run() {
				      ordering = true;
				      win.toFront();
				      parent.toFront();	

				    }
			  });
			  (new Thread(new Runnable(){
				public void run() {
					try {
						Thread.sleep(500);
						ordering = false;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}			  
			  })).start();
		}
	}

	public void windowClosed(WindowEvent arg0) {
		this.dispose();
	}

	public void windowClosing(WindowEvent arg0) {
		this.dispose();
	}

	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {
		this.setVisible(false);
	}

	public void windowOpened(WindowEvent arg0) {
		updateLocation();
		updateSize();
	}

	public void componentHidden(ComponentEvent e) {}

	public void componentMoved(ComponentEvent e) {
		updateLocation();
	}

	public void componentResized(ComponentEvent e) {
		updateSize();
		updateLocation();
	}

	public void componentShown(ComponentEvent e) {}

	public void setVisible(boolean visible){
		if(parent==null || !parent.isVisible()){
			super.setVisible(visible);
			return;
		}
		
		if(visible){
			if(isVisible())
				return;
			parent.toFront();
			updateSize();
			this.setLocation(parent.getLocation().x + parent.getWidth() - this.getWidth(), parent.getY()+offset);
			parent.setAlwaysOnTop(true);
			super.setVisible(true);
			parent.setAlwaysOnTop(false);
			int width = this.getWidth();
			int x = parent.getLocation().x + parent.getWidth() - width;
			int y = this.getY();
			while(x< parent.getBounds().getMaxX() +1){
				this.setLocation(x, y);
				x = x+30;
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			updateLocation();
		}else{
			if(!isVisible())
				return;
			parent.toFront();

			int width = getWidth();
			int x = getX();
			int y = getY();
			while(getX()+width > parent.getX() + parent.getWidth()){
				setLocation(x, y);
				x = x-30;
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	
			super.setVisible(false);
		}
		
		
	}
	

}
