package org.rosuda.deducer.models;

import javax.swing.JButton;

import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.WindowTracker;
import org.rosuda.deducer.toolkit.AssumptionIcon;
import org.rosuda.deducer.toolkit.HelpButton;
import org.rosuda.deducer.toolkit.IconButton;

public class LinearExplorer extends GLMExplorer{
	protected IconButton assumpOutlier;
	LinearExplorer(GLMModel mod) {
		super(mod);
		help.setUrl(HelpButton.baseUrl + "pmwiki.php?n=Main.LinearModel");
		this.setTitle("Linear Regression Model Explorer");
	}
	
	protected void initAssumptions(){
		try{
		{
			assumpN =  new AssumptionIcon("/icons/N_assump.png","Large Sample",null,"Large Sample");
			topPanel.add(assumpN);
			assumpN.setBounds(108, 8, 27, 27);
		}
		{
			assumpFunc = new AssumptionIcon("/icons/func_assump.png","Correct Functional Form",
					null,"Correct Functional Form");
			topPanel.add(assumpFunc);
			assumpFunc.setBounds(44, 8, 27, 27);
		}
		{
			assumpHomo = new AssumptionIcon("/icons/eqvar_assump.png","Equal Variance",null,"Equal Variance");
			topPanel.add(assumpHomo);
			assumpHomo.setBounds(76, 8, 27, 27);
		}
		{
			assumpOutlier = new AssumptionIcon("/icons/outlier_assump.png","No Outliers",null,"No Outliers");
			topPanel.add(assumpOutlier);
			assumpOutlier.setBounds(12, 8, 27, 27);
		}
		refreshAssumptions();
		}catch(Exception e){
			new ErrorMsg(e);
		}
	}
	
	public void run(){
		if(((LinearModel)model).hccm){
			model.plots.confInt=false;
			model.effects.confInt=false;
		}
		model.run(false,pre);
		this.dispose();
		LinearDialog.setLastModel(model);
		Deducer.timedEval("suppressWarnings(rm('"+pre.data.split("\\$")[1]+"','"+pre.modelName.split("\\$")[1]+"',envir="+Deducer.guiEnv+"))");
	}
	
	public void optionsClicked(){
		LinearExplorerOptions opt = new LinearExplorerOptions(this,(LinearModel)model);
		opt.setLocationRelativeTo(this);
		opt.setVisible(true);
		setModel(model);
	}
	
	public void plotsClicked(){
		LinearExplorerPlots p = new LinearExplorerPlots(this,model,pre,((LinearModel)model).hccm);
		p.setLocationRelativeTo(this);
		p.setVisible(true);
		setModel(model);	
	}
	
	public void meansClicked(){
		GLMExplorerMeans m = new GLMExplorerMeans(this,model,pre);
		m.setLocationRelativeTo(this);
		if(((LinearModel)model).hccm){
			m.disableConfInt();
		}
		m.setVisible(true);
		setModel(model);		
	}
	
	public void exportClicked(){
		GLMExplorerExport exp = new GLMExplorerExport(this,model);
		exp.setLocationRelativeTo(this);
		exp.setSinglePredicted();
		exp.setVisible(true);
	}
	
	public void updateClicked(){
		LinearBuilder bld = new LinearBuilder(model);
		bld.setLocationRelativeTo(this);
		bld.setVisible(true);
		WindowTracker.addWindow(bld);
		this.dispose();
	}
	
	public void setModel(GLMModel mod){
		super.setModel(mod);
		refreshAssumptions();
	}
	
	private void refreshAssumptions(){
		if(model!=null && model instanceof LinearModel && assumpHomo!=null){
			if(((LinearModel)model).hccm){
				assumpHomo.setVisible(false);	
				assumpN.setSize(27, 27);
				assumpN.setIcon("/icons/N_assump.png");
				assumpN.setToolTipText("Large sample");				
			}else{
				assumpN.setIcon("/icons/N_or_norm_assump.png");
				assumpN.setToolTipText("Large sample or normal residuals");
				assumpN.setSize(47, 27);
				assumpHomo.setVisible(true);
			}
		}
	}
	
}
