 
package org.rosuda.deducer;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


import org.rosuda.JGR.JGR;
import org.rosuda.JGR.RController;
import org.rosuda.JGR.robjects.RObject;
import org.rosuda.JGR.toolkit.FileSelector;
import org.rosuda.JGR.toolkit.PrefDialog;
import org.rosuda.JGR.util.ErrorMsg;


import org.rosuda.deducer.menu.*;
import org.rosuda.deducer.menu.twosample.TwoSampleDialog;
import org.rosuda.deducer.models.*;
import org.rosuda.deducer.plots.*;
import org.rosuda.deducer.toolkit.DeducerPrefs;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.LoadData;
import org.rosuda.deducer.toolkit.PrefPanel;
import org.rosuda.deducer.toolkit.SaveData;
import org.rosuda.deducer.toolkit.VariableSelectionDialog;
import org.rosuda.deducer.data.DataFrameSelector;
import org.rosuda.deducer.data.DataViewer;


import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPLogical;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngine;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.JRI.JRIEngine;


import org.rosuda.ibase.Common;
import org.rosuda.ibase.toolkit.EzMenuSwing;

public class Deducer {
	ConsoleListener cListener =  new ConsoleListener();
	static final int MENUMODIFIER = Common.isMac() ? Event.META_MASK : Event.CTRL_MASK;
	static int menuIndex=3;
	static String recentActiveData = "";
	static final String Version= "0.7-9";
	public static String guiEnv = ".gui.working.env";
	public static boolean insideJGR;
	public static boolean started;
	private static RConnector rConnection = null;
	public static Vector DATA = new Vector();
	
	public Deducer(boolean jgr){
		started=false;
		Common.getScreenRes(); //initializes value if not already set
		try{
			if(jgr && JGR.isJGRmain()){
				startWithJGR();
				(new Thread() {
					public void run() {
					    if(DeducerPrefs.VIEWERATSTARTUP){
						   	DataViewer inst = new DataViewer();
					    	inst.setLocationRelativeTo(null);
					    	inst.setVisible(true);
					    	JGR.MAINRCONSOLE.toFront(); 
				    	}
						new Thread(new DataRefresher()).start();
					}
				}).start();

			}
		}catch(Exception e){
			new ErrorMsg(e);
		}	
	}
	
	public void startNoJGR(){
		try{
			
			insideJGR=false;
		    String nativeLF = UIManager.getSystemLookAndFeelClassName();
		    try {
		        UIManager.setLookAndFeel(nativeLF);
		    } catch (InstantiationException e) {
		    } catch (ClassNotFoundException e) {
		    }  catch (IllegalAccessException e) {
		    }
			org.rosuda.util.Platform.initPlatform("org.rosuda.JGR.toolkit.");
			
			try {
				rConnection = new DefaultRConnector(new JRIEngine(org.rosuda.JRI.Rengine.getMainEngine()));
			} catch (REngineException e) {
				new ErrorMsg(e);
			}
			
			
			DeducerPrefs.initialize();
			
			started=true;
			timedEval("JavaGD:::.javaGD.set.class.path(\"org/rosuda/JGR/JavaGD\")");
			new Thread(new DataRefresher()).start();
		}catch(Exception e){new ErrorMsg(e);}
	}
	
	public void startWithJGR(){
		insideJGR=true;
		rConnection = new JGRConnector();
		String dataMenu = "Data";
		String analysisMenu = "Analysis";
		try{
			DeducerPrefs.initialize();
			if(DeducerPrefs.SHOWDATA){
				insertMenu(JGR.MAINRCONSOLE,dataMenu,menuIndex);
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, dataMenu, "Edit Factor", "factor", cListener);
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, dataMenu, "Recode Variables", "recode", cListener);
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, dataMenu, "Transform", "transform", cListener);
				EzMenuSwing.getMenu(JGR.MAINRCONSOLE, dataMenu).addSeparator();				
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, dataMenu, "Reset Row Names", "reset rows", cListener);
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, dataMenu, "Sort", "sort", cListener);				
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, dataMenu, "Transpose", "trans", cListener);
				EzMenuSwing.getMenu(JGR.MAINRCONSOLE, dataMenu).addSeparator();						
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, dataMenu, "Merge Data", "merge", cListener);
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, dataMenu, "Subset", "subset", cListener);
				menuIndex++;
			}
			
			if(DeducerPrefs.SHOWANALYSIS){
				insertMenu(JGR.MAINRCONSOLE,analysisMenu,menuIndex);
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, analysisMenu, "Frequencies", "frequency", cListener);
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, analysisMenu, "Descriptives", "descriptives", cListener);
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, analysisMenu, "Contingency Tables", "contingency", cListener);
				EzMenuSwing.getMenu(JGR.MAINRCONSOLE, analysisMenu).addSeparator();
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, analysisMenu, "One Sample Test", "onesample", cListener);
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, analysisMenu, "Two Sample Test", "two sample", cListener);
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, analysisMenu, "K-Sample Test", "ksample", cListener);
				EzMenuSwing.getMenu(JGR.MAINRCONSOLE, analysisMenu).addSeparator();
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, analysisMenu, "Paired Test", "pairedtest", cListener);
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, analysisMenu, "Correlation", "corr", cListener);
				EzMenuSwing.getMenu(JGR.MAINRCONSOLE, analysisMenu).addSeparator();

				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, analysisMenu, "Linear Model", "linear", cListener);
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, analysisMenu, "Logistic Model", "logistic", cListener);
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, analysisMenu, "Generalized Linear Model", "glm", cListener);
				menuIndex++;
			}
			

		    
		    insertMenu(JGR.MAINRCONSOLE,"Plots",menuIndex);
		    //EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, "Plots", "Open plot", "openplot", cListener);
		    EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, "Plots", "Plot Builder", "plotbuilder", cListener);
		    EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, "Plots", "Import Template", "Import template", cListener);
		    EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, "Plots", "Open Plot", "Open plot", cListener);		
		    JMenu sm = new JMenu("Quick");
			sm.setMnemonic(KeyEvent.VK_S);
			String[] labels = PlotController.getTemplateNames();
			for(int i = 0;i<labels.length;i++){
				JMenuItem mi = new JMenuItem();
				mi.setText(labels[i].replace('_',' '));
				mi.setActionCommand("template___"+labels[i]);
				mi.addActionListener(cListener);
				sm.add(mi);
			}
			JMenu menu = EzMenuSwing.getMenu(JGR.MAINRCONSOLE, "Plots");
			menu.add(sm);
			menu.addSeparator();	
			
			sm = new JMenu("Interactive");
			sm.setMnemonic(KeyEvent.VK_S);
			labels = new String[] {"Scatter","Histogram","Bar","Box-plot (long)","Box-plot (wide)","Mosaic","Parallel Coordinate"};
			String[] cmds = new String[] {"iplot","ihist","ibar","iboxl","iboxw","imosaic","ipcp"};
			for(int i = 0;i<labels.length;i++){
				JMenuItem mi = new JMenuItem();
				mi.setText(labels[i]);
				mi.setActionCommand(cmds[i]);
				mi.addActionListener(cListener);
				sm.add(mi);
			}
			menu.add(sm);
			
		    menuIndex++;
	    	
			//Replace DataTable with Data Viewer
			JGR.MAINRCONSOLE.getJMenuBar().getMenu(menuIndex).remove(1);
			insertJMenuItem(JGR.MAINRCONSOLE, "Packages & Data", "Data Viewer", "table", cListener, 1);

			sm = new JMenu("GUI Add-ons");
			sm.setMnemonic(KeyEvent.VK_S);
			labels = new String[] {"Text","Psychometrics","Extras","Spatial"};
			cmds = new String[] {"DeducerText","DeducerPlugInScaling",
					"DeducerExtras","DeducerSpatial"};
			for(int i = 0;i<labels.length;i++){
				JMenuItem mi = new JMenuItem();
				mi.setText(labels[i]);
				mi.setActionCommand(cmds[i]);
				mi.addActionListener(cListener);
				sm.add(mi);
			}
			EzMenuSwing.getMenu(JGR.MAINRCONSOLE, "Packages & Data").add(sm, 3);

			//Override New Data with Data Viewer enabled version
			JGR.MAINRCONSOLE.getJMenuBar().getMenu(0).remove(0);
			insertJMenuItem(JGR.MAINRCONSOLE, "File", "New Data", "New Data Set", cListener, 0);
			
			//Override Open Data with Data Viewer enabled version
			JGR.MAINRCONSOLE.getJMenuBar().getMenu(0).remove(1);
			insertJMenuItem(JGR.MAINRCONSOLE, "File", "Open Data", "Open Data Set", cListener, 1);
			JMenuItem open = (JMenuItem)JGR.MAINRCONSOLE.getJMenuBar().getMenu(0).getMenuComponent(1);
			open.setAccelerator(KeyStroke.getKeyStroke('L',MENUMODIFIER));
			
			//Save Data
			insertJMenuItem(JGR.MAINRCONSOLE, "File", "Save Data", "Save Data Set", cListener, 2);
			
			//help
			EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, "Help", "Deducer Help", "dhelp", cListener);
			
			//preferences
			PrefPanel prefs = new PrefPanel();
			PrefDialog.addPanel(prefs, prefs);
			started=true;
		}catch(Exception e){
			e.printStackTrace();
			new ErrorMsg(e);}		
	}
	
	public static boolean isJGR(){
		
		return insideJGR;
	}
	
	public void detach(){
		JMenuBar mb = JGR.MAINRCONSOLE.getJMenuBar();
		for(int i=0;i<mb.getMenuCount();i++){
			if(mb.getMenu(i).getText().equals("Data") ||
					mb.getMenu(i).getText().equals("Analysis")){
				mb.remove(i);
				i--;
			}
		}
	}
	
	public static void startViewerAndWait(){
	   	DataViewer inst = new DataViewer();
    	inst.setLocationRelativeTo(null);
    	inst.setVisible(true);
    	while(inst.isVisible()==false){
    		try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
    	}
	}

	public static String addSlashes(String str){
		if(str==null) return "";

		StringBuffer s = new StringBuffer(str);
		for(int i = 0; i < s.length(); i++){
			if(s.charAt (i) == '\\')
				s.insert(i++, '\\');
			else if(s.charAt (i) == '\"')
				s.insert(i++, '\\');
			else if(s.charAt (i) == '\'')
				s.insert(i++, '\\');
		}
		
		return s.toString();
	}

	class ConsoleListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			String cmd = arg0.getActionCommand();
			runCmdThreaded(cmd);
		}
	}
	
	public static void runCmdThreaded(String cmd){
		final String c = cmd;
		(new Thread() {
			public void run() {runCmd(c,false);					}
		}).start();
	}
		
	public static void runCmd(String cmd,boolean fromConsole){
		boolean needsRLocked=false;
		if(cmd.equals("dhelp")){
			HelpButton.showInBrowser(HelpButton.baseUrl+"pmwiki.php?n=Main.DeducerManual");
		} else if(cmd.equals("New Data Set")){
			String inputValue = JOptionPane.showInputDialog("Data Name: ");
			if(inputValue!=null){
				String var = RController.makeValidVariableName(inputValue.trim());
				execute(var+"<-data.frame()");
				//DataFrameWindow.setTopDataWindow(var);
			}
		}else if (cmd.equals("Open Data Set")){
			//needsRLocked=true;
			LoadData inst = new LoadData();
			//DataFrameWindow.setTopDataWindow(inst.getDataName());
			Deducer.setRecentData(inst.getDataName());
		}else if(cmd.equals("Save Data Set")){
			//needsRLocked=true;
			RObject data = (new DataFrameSelector(JGR.MAINRCONSOLE)).getSelection();
			if(data!=null){
				SaveData inst = new SaveData(data.getName());
				//WindowTracker.addWindow(inst);
			}
		}else if(cmd.equals("recode")){
			needsRLocked=true;
			RecodeDialog recode =new RecodeDialog(JGR.MAINRCONSOLE); 
			recode.setLocationRelativeTo(null);
			recode.setVisible(true);
			WindowTracker.addWindow(recode);
		}else if(cmd.equals("transform")){
			needsRLocked=true;
			TransformDialog trans =new TransformDialog(JGR.MAINRCONSOLE); 
			trans.setLocationRelativeTo(null);
			trans.setVisible(true);
			WindowTracker.addWindow(trans);
		}else if(cmd.equals("factor")){
			needsRLocked=true;
			VariableSelectionDialog inst =new VariableSelectionDialog(JGR.MAINRCONSOLE);
			inst.SetSingleSelection(true);
			inst.setLocationRelativeTo(null);
			inst.setRFilter("is.factor");
			inst.setTitle("Select Factor to Edit");
			inst.setVisible(true);
			String variable = inst.getSelecteditem();
			if(variable==null)
				return;
			FactorDialog fact = new FactorDialog(JGR.MAINRCONSOLE,variable);
			fact.setLocationRelativeTo(null);
			fact.setVisible(true);
			WindowTracker.addWindow(fact);
		}else if(cmd.equals("reset rows")){
			String name = null;
			RObject data = null;
			DataFrameSelector sel = new DataFrameSelector(JGR.MAINRCONSOLE);
			data = sel.getSelection();
			if(data!=null){
				name = data.getName();
				execute("rownames("+name+") <-1:dim("+name+")[1]");
				//DataFrameWindow.setTopDataWindow(name);
			}
			JGR.MAINRCONSOLE.toFront();
		}else if(cmd.equals("sort")){
			needsRLocked=true;
			SortDialog sort = new SortDialog(JGR.MAINRCONSOLE);
			sort.setLocationRelativeTo(null);
			sort.setVisible(true);
			WindowTracker.addWindow(sort);
		}else if(cmd.equals("merge")){
			needsRLocked=true;
			MergeDialog merge =new MergeDialog(JGR.MAINRCONSOLE); 
			merge.setLocationRelativeTo(null);
			merge.setVisible(true);
			WindowTracker.addWindow(merge);
		}else if (cmd.equals("trans")){
			String name = null;
			RObject data = null;
			DataFrameSelector sel = new DataFrameSelector(JGR.MAINRCONSOLE);
			data = sel.getSelection();
			if(data!=null){
				name = data.getName();
				String newDat = (String) JOptionPane.showInputDialog("New dataset name:");
				newDat = Deducer.makeValidVariableName(newDat);
				execute(newDat+"<-as.data.frame(t("+name+"))");
				//DataFrameWindow.setTopDataWindow(name);
			}
		}else if(cmd.equals("subset")){
			needsRLocked=true;
			SubsetDialog sub = new SubsetDialog(JGR.MAINRCONSOLE);
			sub.setLocationRelativeTo(null);
			sub.setVisible(true);
			WindowTracker.addWindow(sub);
		}else if(cmd.equals("frequency")){
			needsRLocked=true;
			FrequencyDialog freq = new FrequencyDialog(JGR.MAINRCONSOLE);
			WindowTracker.addWindow(freq);
			freq.setLocationRelativeTo(null);
			freq.setVisible(true);
		}else if(cmd.equals("descriptives")){
			needsRLocked=true;
			DescriptivesDialog desc = new DescriptivesDialog(JGR.MAINRCONSOLE);
			desc.setLocationRelativeTo(null);
			desc.setVisible(true);
			WindowTracker.addWindow(desc);
		}else if(cmd.equals("contingency")){
			needsRLocked=true;
			ContingencyDialog cont = new ContingencyDialog(JGR.MAINRCONSOLE);
			cont.setLocationRelativeTo(null);
			cont.setVisible(true);
			WindowTracker.addWindow(cont);
		}else if (cmd.equals("table")){
			needsRLocked=true;
			DataViewer inst = new DataViewer();
			inst.setLocationRelativeTo(null);
			inst.setVisible(true);
			WindowTracker.addWindow(inst);
		}else if(cmd.equals("pairedtest")){
			Deducer.timedEval(".getDialog('Paired Test')$run()");
		}else if(cmd.equals("onesample")){
			needsRLocked=true;
			OneSampleDialog inst = new OneSampleDialog(JGR.MAINRCONSOLE);
			inst.setLocationRelativeTo(JGR.MAINRCONSOLE);
			inst.setVisible(true);
			WindowTracker.addWindow(inst);
		}else if(cmd.equals("two sample")){
			needsRLocked=true;
			TwoSampleDialog inst = new TwoSampleDialog(JGR.MAINRCONSOLE);
			inst.setLocationRelativeTo(null);
			inst.setVisible(true);	
			WindowTracker.addWindow(inst);
		}else if(cmd.equals("ksample")){
			needsRLocked=true;
			KSampleDialog inst = new KSampleDialog(JGR.MAINRCONSOLE);
			inst.setLocationRelativeTo(null);
			inst.setVisible(true);
			WindowTracker.addWindow(inst);
		}else if(cmd.equals("corr")){
			needsRLocked=true;
			CorDialog inst = new CorDialog(JGR.MAINRCONSOLE);
			inst.setLocationRelativeTo(null);
			inst.setVisible(true);			
			WindowTracker.addWindow(inst);
		}else if(cmd.equals("glm")){
			needsRLocked=true;
			GLMDialog d = new GLMDialog(JGR.MAINRCONSOLE);
			d.setLocationRelativeTo(null);
			d.setVisible(true);
			WindowTracker.addWindow(d);
		}else if(cmd.equals("logistic")){
			needsRLocked=true;
			LogisticDialog d = new LogisticDialog(JGR.MAINRCONSOLE);
			d.setLocationRelativeTo(null);
			d.setVisible(true);
			WindowTracker.addWindow(d);
		}else if(cmd.equals("linear")){
			needsRLocked=true;
			LinearDialog d = new LinearDialog(JGR.MAINRCONSOLE);
			d.setLocationRelativeTo(null);
			d.setVisible(true);
			WindowTracker.addWindow(d);
		}else if(cmd.equals("plotbuilder")){
			needsRLocked=true;
			PlotBuilder d = new PlotBuilder();
			d.setLocationRelativeTo(null);
			d.setVisible(true);
			WindowTracker.addWindow(d);			
		}else if(cmd.equals("Open plot")){
			needsRLocked=true;
			FileSelector c = new FileSelector(null, "Open plot", FileSelector.LOAD, null, true);
			c.setVisible(true);
			File f = c.getSelectedFile();
			if(f!=null){
				if(!f.getName().endsWith(".ggp")){
					JOptionPane.showMessageDialog(null, "This does not appear to be a"
							+" ggplot2 PlotBuilder file (extension .ggp)");
					return;
				}
				if(f!=null && f.exists()){
					PlotBuilderModel newModel = new PlotBuilderModel();
					newModel.setFromFile(f);
					PlotBuilder d = new PlotBuilder(newModel);
					d.setLocationRelativeTo(null);
					d.setVisible(true);
					WindowTracker.addWindow(d);	
				}
			}
		}else if(cmd.equals("Import template")){
			FileSelector fileDialog = new FileSelector(null, 
					"Import template", FileSelector.LOAD, null, true);
			fileDialog.setVisible(true);
			
			if(fileDialog.getFile() !=null){
				File f = fileDialog.getSelectedFile();
				if(!f.getName().endsWith(".ggtmpl")){
					JOptionPane.showMessageDialog(null, "This does not appear to be a"
							+" ggplot2 template file (extension .ggtmpl)");
					return;
				}
				if(f!=null && f.exists()){
					PlottingElement el = new PlottingElement();
					el.setFromFile(f);
					PlotController.addTemplate(el);
				}
			}
		}else if(cmd.startsWith("template___")
				){
			try{
				PlotController.init();
				String tmp = cmd.split("___")[1];
				PlottingElementMenuDialog d = PlotController.getMenuDialog(tmp);
				d.setLocationRelativeTo(null);
				d.setVisible(true);
			}catch(Exception e){e.printStackTrace();}
		}else if(cmd.equals("iplot")){
			Deducer.timedEval(".getDialog('Interactive Scatter Plot')$run()");
		}else if(cmd.equals("ihist")){
			Deducer.timedEval(".getDialog('Interactive Histogram')$run()");
		}else if(cmd.equals("ibar")){
			Deducer.timedEval(".getDialog('Interactive Bar Plot')$run()");
		}else if(cmd.equals("iboxl")){
			Deducer.timedEval(".getDialog('Interactive Box Plot Long')$run()");
		}else if(cmd.equals("iboxw")){
			Deducer.timedEval(".getDialog('Interactive Box Plot Wide')$run()");
		}else if(cmd.equals("imosaic")){
			Deducer.timedEval(".getDialog('Interactive Mosaic Plot')$run()");
		}else if(cmd.equals("ipcp")){
			Deducer.timedEval(".getDialog('Interactive Parallel Coordinate Plot')$run()");
		}else if(cmd.startsWith("Deducer")){
			RController.loadPackage(cmd);
		}
		
		if(needsRLocked && fromConsole && !isJGR()){
			WindowTracker.waitForAllClosed();
		}
		
	}
	//temporary until new version of ibase
	public static void insertMenu(JFrame f, String name,int index) {
		JMenuBar mb = f.getJMenuBar();
		JMenu m = EzMenuSwing.getMenu(f,name);
		if (m == null && index<mb.getMenuCount()){
			JMenuBar mb2 = new JMenuBar(); 
			int cnt = mb.getMenuCount();
			for(int i=0;i<cnt;i++){
				if(i==index)
					mb2.add(new JMenu(name));
				mb2.add(mb.getMenu(0));
			}
			f.setJMenuBar(mb2);			
		}else if(m==null && index==mb.getMenuCount())
			EzMenuSwing.addMenu(f,name);
	}
	public static void insertJMenuItem(JFrame f, String menu, String name,
			String command, ActionListener al,int index) {
		JMenu m = EzMenuSwing.getMenu(f, menu);
		JMenuItem mi = new JMenuItem(name);
		mi.addActionListener(al);
		mi.setActionCommand(command);
		m.insert(mi,index);
	}
	
	public static String getRecentData(){
		return recentActiveData;
	}
	
	public static void setRecentData(String data){
		recentActiveData=data;
	}
	
	public static REngine getREngine(){
		return rConnection.getREngine();
	}
	
	public static RConnector getRConnector(){
		return rConnection;
	}
	
	public static void setRConnector(RConnector rc){
		rConnection = rc;
	}
	
	public static REXP eval(String cmd){
		return rConnection.eval(cmd);
	}
	
	public static void threadedEval(String cmd){
		final String c = cmd;
		new Thread(new Runnable(){

			public void run() {
				rConnection.eval(c);
			}
			
		}).start();
	}
	public static REXP timedEval(String cmd){
		return timedEval(cmd,15000,true);
	}
	
	public static REXP timedEval(String cmd,boolean ask){
		return timedEval(cmd,15000,ask);
	}
	
	public static REXP timedEval(String cmd,int interval,boolean ask){
		return new MonitoredEval(interval,ask).run(cmd);
	}
	
	public static REXP idleEval(String cmd){
		return rConnection.idleEval(cmd);
	}
	
	public static void execute(String cmd){
		rConnection.execute(cmd);
	}
	
	public static void execute(String cmd, boolean hist){
		rConnection.execute(cmd,hist);
	}
	
	public static void executeAndContinue(String cmd){
		final String c = cmd;
		(new Thread() {
			public void run() {
				execute(c);
			}
			}).start();
	}
				
	public static String makeValidVariableName(String var) {
		return var.replaceAll("[ -+*/\\()=!~`@#$%^&*<>,?;:\"\']", ".");
	}
	
	public static String makeFormula(DefaultListModel outcomes,DefaultListModel terms){
		String formula = "";
		if(outcomes.getSize()==1){
			formula+=outcomes.get(0)+" ~ ";
		}else{
			formula+="cbind(";
			for(int i=0;i<outcomes.getSize();i++){
				formula+=outcomes.get(i);
				if(i<outcomes.getSize()-1)
					formula+=",";
			}
			formula+=") ~ ";
		}
		for(int i=0;i<terms.getSize();i++){
			formula+=terms.get(i);
			if(i<terms.getSize()-1)
				formula+=" + ";
		}
		return formula;
	}
	
	public static String makeRCollection(Collection lis,String func, boolean quotes) {
		String q = "";
		if(quotes){
			q = "\"";
		}
		if (lis.size() == 0)
			return func+"()";
		String result = func+"(";
		Iterator it = lis.iterator();
		int ins = 1;
		while(it.hasNext()){
			String s = it.next().toString();
			result+= q + s + q;
			if(it.hasNext())
				result += ",";
			if (ins % 10 == 9)
				result += "\n";	
			ins++;
		}
		return result+")";
	}
	
	public static String makeRCollection(ListModel lis,String func, boolean quotes) {
		ArrayList a = new ArrayList();
		for(int i=0;i<lis.getSize();i++)
			a.add(lis.getElementAt(i));
		return makeRCollection(a,func,quotes);
	}
	
	public static String makeRCollection(String[] lis,String func, boolean quotes) {
		ArrayList a = new ArrayList();
		for(int i=0;i<lis.length;i++)
			a.add(lis[i]);
		return makeRCollection(a,func,quotes);
	}

	/**
	 * Gets a unique name based on a starting string
	 * 
	 * @param var
	 * @return the value of var concatinated with a number
	 */
	public static String getUniqueName(String var) {
		JGR.refreshObjects();
		var = RController.makeValidVariableName(var);
		if (!JGR.OBJECTS.contains(var))
			return var;
		int i = 1;
		while (true) {
			if (!JGR.OBJECTS.contains(var + i))
				return var + i;
			i++;
		}
	}
	
	/**
	 * Gets a unique name based on a starting string
	 * 
	 * @param var
	 * @param envName
	 *            The name of the enviroment in which to look
	 * @return the value of var concatinated with a number
	 */
	public static String getUniqueName(String var, String envName) {
		var = RController.makeValidVariableName(var);

		try {
			REXPLogical temp = (REXPLogical) eval("is.environment("
					+ envName + ")");
			boolean isEnv = temp.isTRUE()[0];
			if (!isEnv)
				return var;
		} catch (Exception e) {
			new ErrorMsg(e);
			return var;
		}

		boolean isUnique = false;

		try {

			REXPLogical temp = (REXPLogical) eval("exists('" + var
					+ "',where=" + envName + ",inherits=FALSE)");
			isUnique = temp.isFALSE()[0];
			if (isUnique)
				return var;

		} catch (Exception e) {
			new ErrorMsg(e);
			return var;
		}

		int i = 1;
		while (true) {

			try {

				REXPLogical temp = (REXPLogical) eval("exists('" + (var + i) + "',where=" + envName
								+ ",inherits=FALSE)");
				isUnique = temp.isFALSE()[0];

			} catch (Exception e) {
				new ErrorMsg(e);
			} 

			if (isUnique)
				return var + i;
			i++;
		}
	}
	
	/**
	 * is package installed
	 * @param packageName
	 * @return
	 */
	public static boolean isInstalled(String packageName){
		REXP installed = Deducer.eval("'" + packageName +"' %in% installed.packages()[,1]");
		if(installed!=null && installed instanceof REXPLogical){
			return ((REXPLogical) installed).isTRUE()[0];
		}
		return false;
	}
	
	/**
	 * is package loaded
	 * @param packageName
	 * @return
	 */
	public static boolean isLoaded(String packageName){
		REXP loaded = Deducer.eval("'" + packageName +"' %in% c(names(sessionInfo()$otherPkgs), sessionInfo()$base)");
		if(loaded!=null && loaded instanceof REXPLogical){
			return ((REXPLogical) loaded).isTRUE()[0];
		}
		return false;
	}
	
	/**
	 * Checks if package is installed, and asks to install if not.
	 * @param packageName 
	 * @return "loaded", "installed" or "not-installed"
	 */
	public static String requirePackage(String packageName){
		if(isLoaded(packageName))
			return "loaded";
		if(isInstalled(packageName))
			return "installed";
		int inst = JOptionPane.showOptionDialog(null, "Package " + packageName + " not installed. \nWould you like to install it now?",
												"Install", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,
												new String[]{"Yes","No"}, "Yes");
		if(inst==JOptionPane.OK_OPTION){
			Deducer.eval("install.packages('" + packageName + "',,'http://cran.r-project.org')");
			if(isInstalled(packageName))
				return "installed";
		}
		return "not-installed";
	}
	


	public static synchronized void refreshData(){
		REXP x = Deducer.idleEval("ls()[sapply(ls(),function(x) \"data.frame\" %in% class(get(x)))]");
		if(x==null)
			return;
		String[] data;
		Deducer.DATA.clear();
		try {
			if (!x.isNull() && (data = x.asStrings()) != null) {	
				int a = 1;
				for (int i = 0; i < data.length; i++) {
					boolean b = (data[i].equals("null") || data[i].trim().length() == 0);
					String name = b ? a + "" : data[i];
					Deducer.DATA.add(RController.createRObject(name, "data.frame", null, (!b)));
					a++;
				}

			}
		} catch (REXPMismatchException e) {}
	}
	
	public static Vector getData(){
		return Deducer.DATA;
	}
	
	/**
	 * Refreshes objects and keywords if JGR is not doing so.
	 */
	class DataRefresher implements Runnable {

		public DataRefresher() {}

		public void run() {
			while (true)
				try {
					Thread.sleep(5000);
					refreshData();
				} catch (Exception e) {
					new ErrorMsg(e);
				}
		}
	}
}



final class MonitoredEval{
	volatile boolean done;
	volatile REXP result;
	int interval;
	int checkInterval;
	boolean ask;
	String c;
	public MonitoredEval(int inter,boolean ak){
		done = false;
		interval = inter;
		checkInterval = interval;
		ask=ak;
	}
	
	protected void startMonitor(){
		int t = 0;
		while(true){
			try {
				Thread.sleep(checkInterval);
				
			} catch (InterruptedException e) {
				return;
			}
			if(done)
				return;
			if(t+checkInterval <interval){
				t = t + checkInterval;
				continue;
			}
			int cancel;
			new ErrorMsg(new Exception(c));//TODO:delete
			if(ask){
				cancel = JOptionPane.showConfirmDialog(null, 
					"This R process is taking some time.\nWould you like to cancel it?",
					"Cancel R Process",
						 JOptionPane.YES_NO_OPTION);
				
			}else
				cancel = JOptionPane.YES_OPTION;
			if(cancel==JOptionPane.YES_OPTION){
				((org.rosuda.REngine.JRI.JRIEngine) JGR.getREngine())
				.getRni().rniStop(0);
				return;
			}else{
				t=0;
			}
		}			
	}

	public REXP run(String cmd) {
		
		try{
			if(SwingUtilities.isEventDispatchThread() && ask){
				c = cmd;
				new Thread(new Runnable(){
					public void run() {
						result = Deducer.eval(c);
						done = true;
					}
				}).start();	
				checkInterval = 10;
				startMonitor();
			}else{
				new Thread(new Runnable(){
					public void run() {
						startMonitor();
					}
				}).start();	
					
				result = Deducer.eval(c=cmd);
			}
			done = true;				
			return result;
		}catch(Exception e){
			return null;
		}
	}
	

}




