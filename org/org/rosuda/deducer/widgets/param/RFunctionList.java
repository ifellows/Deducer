package org.rosuda.deducer.widgets.param;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Vector;

import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.XMLHelper;
import org.rosuda.deducer.widgets.VariableSelectorWidget;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RFunctionList extends Param{
	
	protected Vector active = new Vector();
	protected Vector defaultActive = new Vector();
	protected String[] requiredFuncs = new String[]{};
	protected String[] assignTo = new String[]{};
	protected String[] print = new String[]{};
	protected String[] keep = new String[]{};
	protected HashMap map = new HashMap();
	
	protected Param[] globalParams = new Param[]{};
	
	public RFunctionList(){
		name = "";
		title = "";
		view = VIEW_RFUNCTION_CHOOSER;
		options = new String[]{};
	}
	
	public RFunctionList(String nm){
		name = nm;
		title = nm;
		view = VIEW_RFUNCTION_CHOOSER;
		options = new String[]{};
	}
	
	public ParamWidget getView(VariableSelectorWidget sel){
		try{
			Class cl =Class.forName(view);
			Constructor constructor = cl.getConstructor(new Class[]{
					Param.class,VariableSelectorWidget.class});
			ParamWidget pw = (ParamWidget) constructor.newInstance(new Object[]{this,sel});
			return pw;
		}catch(Exception e){e.printStackTrace();return null;}
		
	}
	
	public Object clone(){
		RFunctionList p = new RFunctionList();
		p.setName(this.getName());
		p.setTitle(this.getTitle());
		p.setViewType(this.getViewType());
		if(this.getOptions()!=null){
			String[] v = new String[this.getOptions().length];
			for(int i=0;i<this.getOptions().length;i++)
				v[i] = this.getOptions()[i];
			p.setOptions(v);
		}
		Vector vNew = new Vector();
		for(int i=0;i<getActiveFunctions().size();i++)
			vNew.add(getActiveFunctions().get(i));
		p.setActiveFunctions(vNew);
		
		vNew = new Vector();
		for(int i=0;i<getDefaultActiveFunctions().size();i++)
			vNew.add(getDefaultActiveFunctions().get(i));
		p.setDefaultActiveFunctions(vNew);
		
		p.requiredFuncs = new String[]{};
		if(this.requiredFuncs!=null){
			String[] v = new String[this.requiredFuncs.length];
			for(int i=0;i<this.requiredFuncs.length;i++)
				v[i] = this.requiredFuncs[i];
			p.requiredFuncs = v;
		}
		
		p.required = this.required;
		
		p.print = new String[]{};
		if(this.print!=null){
			String[] v = new String[this.print.length];
			for(int i=0;i<this.print.length;i++)
				v[i] = this.print[i];
			p.print = v;
		}
		
		p.assignTo = new String[]{};
		if(this.assignTo!=null){
			String[] v = new String[this.assignTo.length];
			for(int i=0;i<this.assignTo.length;i++)
				v[i] = this.assignTo[i];
			p.assignTo = v;
		}
		
		p.keep = new String[]{};
		if(this.keep!=null){
			String[] v = new String[this.keep.length];
			for(int i=0;i<this.keep.length;i++)
				v[i] = this.keep[i];
			p.keep = v;
		}
		p.requiresVariableSelector = this.requiresVariableSelector;
		
		Vector oldParams = new Vector();
		Vector newParams = new Vector();
		
		p.setGlobalParams(new Param[getGlobalParams().length]);
		for(int i=0;i<getGlobalParams().length;i++){
			Param oldPar = getGlobalParams()[i];
			Param par =(Param) oldPar.clone();
			if(oldParams.contains(oldPar)){
				int ind = oldParams.indexOf(oldPar);
				par = (Param) newParams.get(ind);
			}else{
				oldParams.add(oldPar);
				newParams.add(par);				
			}
			p.getGlobalParams()[i] = par;

		}
		
		
		HashMap hm = getFunctionMap();
		HashMap newHm = new HashMap();
		for(int i=0;i<getOptions().length;i++){
			RFunction rFunc = ((RFunction)hm.get(getOptions()[i]));
			RFunction s = new RFunction();
			for(int j=0;j<rFunc.getParams().size();j++){
				Param oldPar =(Param)rFunc.getParams().get(j);
				Param par =(Param) oldPar.clone();
				if(oldParams.contains(oldPar)){
					int ind = oldParams.indexOf(oldPar);
					par = (Param) newParams.get(ind);
				}else{
					oldParams.add(oldPar);
					newParams.add(par);					
				}
				s.getParams().add(par);
			}
			s.name = rFunc.name;
			s.view = rFunc.view;
			s.requiresVariableSelector = rFunc.requiresVariableSelector;
			s.required = rFunc.required;
			p.updateResultReferences(s);
			newHm.put(getOptions()[i], s);
		}
		p.setFunctionMap(newHm);
		
		return p;
	}
	
	public void updateResultReferences(RFunction rf){
		for(int i=0;i<rf.getParams().size();i++){
			Param p = (Param) rf.getParams().get(i);
			if(p instanceof RFunction)
				updateResultReferences((RFunction )p);
			else if(p instanceof ParamRFunctionResult)
				((ParamRFunctionResult)p).setFunctionList(this);
		}
	}
	
	public String[] getParamCalls(){

		String val="";
		String fName=null;
		Vector temp = new Vector();
		for(int i=0;i<getActiveFunctions().size();i++){
			fName = (String) getActiveFunctions().get(i);
			RFunction rf = (RFunction) getFunctionMap().get(fName);
			if(rf != null)
				val = rf.getCall();
			else
				val = "";
			if(val.length()>0)
				temp.add((name!=null ? (name + " = ") : "")+val);
		}
		String[] calls=new String[temp.size()];		
		for(int i=0;i<calls.length;i++)
			calls[i] = (String) temp.get(i);
		return calls;
	}
	
	public String getCall() {
		String val="";
		String fName=null;
		String call = "";
		Vector pr = new Vector();
		Vector rm = new Vector();
		int cnt = 0;
		String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for(int i=0;i<options.length;i++){

			if(!active.contains(options[i]))
				continue;
			
			String lhs = assignTo[i] ;
			if(lhs.equals("<auto>"))
				lhs = Deducer.getUniqueName("obj_"+ letters.charAt(cnt++));
			assignTo[i] = lhs;
			fName = (String) options[i];
			RFunction rf = (RFunction) getFunctionMap().get(fName);
			if(rf != null)
				val = rf.getCall();
			else
				val = "";
			if(val.length()>0){
				call += (i==0?"":"\n") + lhs + " <- " + val;
			}
			if(print[i].equals("true"))
				pr.add(lhs);
			if(!keep[i].equals("true"))
				rm.add(lhs);
		}
		for(int i=0;i<pr.size();i++)
			call += "\n" + "print(" + pr.get(i).toString() +")";
		if(rm.size()>0)
			call += "\n" + Deducer.makeRCollection(rm,"rm",false);
		return call;
	}
	
	public void setDefaultValue(Object defaultValue) {
		if(defaultValue instanceof Vector || defaultValue ==null)
			this.setDefaultActiveFunctions((Vector) getDefaultActiveFunctions());
		else
			System.out.println("ParamRFunction: invalid setDefaultValue");
	}
	
	public Object getDefaultValue() {
		return getDefaultActiveFunctions();
	}
	
	public void setValue(Object value) {
		if(value instanceof Vector){
			this.setActiveFunctions((Vector) ((Vector)value).get(0));
			this.setFunctionMap((HashMap) ((Vector)value).get(1));
		}else{
			System.out.println("RFunctionList: invalid setValue");
			Exception e = new Exception();
			e.printStackTrace();
		}
	}
	
	public Object getValue() {
		Vector v=new Vector();
		v.add(getActiveFunctions());
		v.add(getFunctionMap());
		return v;
	}
	
	public void addRFunction(String name,RFunction rf,boolean requireFunction,
			boolean printResult,boolean keepResult,String assignToVariable){
		int l =0;
		try{
			if(getOptions()!=null)
				l = getOptions().length;
			String[] opts = new String[l+1];
			for(int i=0;i<l;i++)
				opts[i] = getOptions()[i];
			opts[l] = name;
			setOptions(opts);
			getFunctionMap().put(name, rf);
			
			l = requiredFuncs.length;
			String[] req = new String[l+1];
			for(int i=0;i<l;i++)
				req[i] = requiredFuncs[i];                      
			req[l] = requireFunction ? "true" : "false";
			requiredFuncs = req;
			
			l = print.length;
			req = new String[l+1];
			for(int i=0;i<l;i++)
				req[i] = print[i];                      
			req[l] = printResult ? "true" : "false";
			print = req;
			
			l = keep.length;
			req = new String[l+1];
			for(int i=0;i<l;i++)
				req[i] = keep[i];                      
			req[l] = keepResult ? "true" : "false";
			keep = req;
			
			l = assignTo.length;
			req = new String[l+1];
			for(int i=0;i<l;i++)
				req[i] = assignTo[i];                      
			req[l] = assignToVariable;
			assignTo = req;
			
			if(requireFunction && !active.contains(name)){
				active.add(name);
			}
		}catch(Exception e){e.printStackTrace();}
	}
	public void addRFunction(String name,RFunction rf){
		addRFunction(name,rf,false,true,false,"<auto>");
	}
	
	public void addRFunction(String name,RFunction rf,boolean requireFunction){
		addRFunction(name,rf,requireFunction,true,false,"<auto>");
	}
	
	boolean isFunctionRequired(String name){
		for(int i=0;i<options.length;i++){
			if(options[i].equals(name) && requiredFuncs[i].equals("true"))
				return true;
		}
		return false;
	}
	
	/**
	 * Send to xml.
	 * TODO: does not account for aliased Params
	 */
	public Element toXML(){
		Element e = super.toXML();
		Document doc = e.getOwnerDocument();
		XMLHelper.appendCollection(e, active, "active");
		XMLHelper.appendCollection(e, active, "defaultActive");
		XMLHelper.appendCollection(e, requiredFuncs, "requiredFuncs");
		XMLHelper.appendCollection(e, print, "print");
		XMLHelper.appendCollection(e, keep, "keep");
		XMLHelper.appendCollection(e, assignTo, "assignTo");
		e.setAttribute("requiresVariableSelector", requiresVariableSelector ? "true" : "false");
		HashMap hm = map;
		Element el = doc.createElement("valueMap");
		for(int i=0;i<options.length;i++){
			RFunction fn = (RFunction) hm.get(options[i]);
			Element element = fn.toXML();
			element = (Element) doc.importNode(element, true);
			Element element1 = doc.createElement("keyValuePair");
			element1.setAttribute("key", options[i]);
			element1.appendChild(element);
			el.appendChild(element1);
		}
		e.appendChild(el);

		e.setAttribute("className", "org.rosuda.deducer.widgets.param.RFunctionList");
		return e;
	}
	
	/**
	 * load model from an xml representation.
	 * TODO: Does not handle aliased Params
	 */
	public void setFromXML(Element node){
		String cn = node.getAttribute("className");
		if(!cn.equals("org.rosuda.deducer.widgets.param.RFunctionList")){
			System.out.println("Error ParamRFunction: class mismatch: " + cn);
			(new Exception()).printStackTrace();
		}
		super.setFromXML(node);

		Vector nv = XMLHelper.getChildrenElementsByTag(node, "valueMap");
		if(nv.size()>0){
			HashMap hm = new HashMap();
			Element map = (Element) nv.get(0);
			Vector pairs = XMLHelper.getChildrenElementsByTag(map, "keyValuePair");
			for(int i=0;i<pairs.size();i++){
				Element pair = (Element) pairs.get(i);
				String key = pair.getAttribute("key");
				Element val = (Element) XMLHelper.getChildrenElementsByTag(pair, "Param").get(0);
				RFunction rf = new RFunction();
				rf.setFromXML(val);
				hm.put(key, rf);
			}
			this.map = hm;
		}
		
		if(node.hasAttribute("requiresVariableSelector"))
			requiresVariableSelector = node.getAttribute("requiresVariableSelector").equals("true");
		else
			requiresVariableSelector = false;
		
		options = XMLHelper.getChildCollection(node, "options");
		
		String[] tmp = XMLHelper.getChildCollection(node, "active");
		active = new Vector();
		for(int i=0;i<tmp.length;i++)
			active.add(tmp[i]);
		
		tmp = XMLHelper.getChildCollection(node, "defaultActive");
		defaultActive = new Vector();
		for(int i=0;i<tmp.length;i++)
			defaultActive.add(tmp[i]);
		
		requiredFuncs = XMLHelper.getChildCollection(node, "requiredFuncs");
		print = XMLHelper.getChildCollection(node, "print");
		keep = XMLHelper.getChildCollection(node, "keep");
		assignTo = XMLHelper.getChildCollection(node, "assignTo");
	}
	
	public boolean hasValidEntry(){
		return getActiveFunctions().size()>0;
	}

	public void setActiveFunctions(Vector active) {
		this.active = active;
	}
	
	public void setActiveFunctions(String[] active) {
		Vector v = new Vector();
		for(int i=0;i<active.length;i++)
			v.add(active[i]);
		this.active = v;
	}
	public void setActiveFunctions(String active) {
		Vector v = new Vector();
		v.add(active);
		this.active = v;
	}

	public Vector getActiveFunctions() {
		return active;
	}

	public void setDefaultActiveFunctions(Vector defaultActive) {
		this.defaultActive = defaultActive;
	}

	public Vector getDefaultActiveFunctions() {
		return defaultActive;
	}

	public void setFunctionMap(HashMap map) {
		this.map = map;
	}

	public HashMap getFunctionMap() {
		return map;
	}

	public String checkValid() {
		for(int i=0;i<options.length;i++){
			if(!active.contains(options[i]))
				continue;
			RFunction p = (RFunction) map.get(options[i]);
			String s = p.checkValid();
			if(s!=null)
				return s;
		}
		return null;
	}

	public void setGlobalParams(Param[] globalParams) {
		this.globalParams = globalParams;
	}

	public Param[] getGlobalParams() {
		return globalParams;
	}

}
