package org.rosuda.deducer.toolkit;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

import org.rosuda.ibase.Common;
import org.rosuda.JGR.JGR;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;

public class DeducerPrefs {

	public static boolean SHOWDATA ;
	public static boolean SHOWANALYSIS;
	public static boolean USEQUAQUACHOOSER;
	public static boolean VIEWERATSTARTUP;
	
	private static HashMap map;
	
	private static boolean started = false;
	
	public static void initialize() {
		if(!started){
			SHOWDATA = true;
			SHOWANALYSIS = true;
			USEQUAQUACHOOSER = false;//Common.isMac() ? true : false;
			VIEWERATSTARTUP = true;		
			map = new HashMap();
			readPrefs();
			started = true;
		}
	}
	
	public static void readPrefs() {
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(System
					.getProperty("user.home")
					+ File.separator + ".DeducerPrefs"));
		} catch (FileNotFoundException e) {
		}

		try {
			if (is != null) {
				Preferences prefs = Preferences
						.userNodeForPackage(org.rosuda.deducer.Deducer.class);
				try {
					prefs.clear();
				} catch (Exception x) {
					new ErrorMsg(x);
				}
				prefs = null;
				Preferences.importPreferences(is);
			}
		} catch (InvalidPreferencesFormatException e) {
		} catch (IOException e) {
		}

		if (is == null){
			return;
		}
		Preferences prefs = Preferences.userNodeForPackage(org.rosuda.deducer.Deducer.class);
		SHOWDATA = prefs.getBoolean("SHOWDATA",true);
		SHOWANALYSIS = prefs.getBoolean("SHOWANALYSIS",true);
		USEQUAQUACHOOSER = prefs.getBoolean("USEQUAQUACHOOSER",Common.isMac() ? true : false);
		VIEWERATSTARTUP = prefs.getBoolean("VIEWERATSTARTUP",true);
		String[] names;
		try {
			names = prefs.keys();
			for(int i=0;i<names.length;i++){
				if(!names[i].equals("SHOWDATA") && !names[i].equals("SHOWANALYSIS") &&
					!names[i].equals("USEQUAQUACHOOSER") && !names[i].equals("VIEWERATSTARTUP")){
					
					map.put(names[i], prefs.get(names[i], ""));
				}
			}			
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		//NOTE: quaqua always disabled due to lion incompatability
		USEQUAQUACHOOSER = false;
	}
	
	public static void writePrefs() {
		Preferences prefs = Preferences
				.userNodeForPackage(org.rosuda.deducer.Deducer.class);

		try {
			prefs.clear();
		} catch (Exception x) {
		}

		Object[] keys = map.keySet().toArray();
		for(int i=0;i<keys.length;i++){
			prefs.put((String) keys[i], (String) map.get(keys[i]));
		}
		prefs.putBoolean("SHOWDATA", SHOWDATA);
		prefs.putBoolean("SHOWANALYSIS", SHOWANALYSIS);
		prefs.putBoolean("USEQUAQUACHOOSER", USEQUAQUACHOOSER);
		prefs.putBoolean("VIEWERATSTARTUP", VIEWERATSTARTUP);
		try {
			prefs.exportNode(new FileOutputStream(System
					.getProperty("user.home")
					+ File.separator + ".DeducerPrefs"));
		} catch (IOException e) {
		} catch (BackingStoreException e) {
		}

	}
	
	public static String get(String key){
		if(key.equals("SHOWDATA"))
			return SHOWDATA ? "true" : "false";
		if(key.equals("SHOWANALYSIS"))
			return SHOWANALYSIS ? "true" : "false";
		if(key.equals("USEQUAQUACHOOSER"))
			return USEQUAQUACHOOSER ? "true" : "false";
		if(key.equals("VIEWERATSTARTUP"))
			return VIEWERATSTARTUP ? "true" : "false";
		
		return (String) map.get(key);
	}
	
	public static void set(String key, String value){
		if(key.equals("SHOWDATA"))
			SHOWDATA = value.equals("true");
		else if(key.equals("SHOWANALYSIS"))
			SHOWANALYSIS = value.equals("true");
		else if(key.equals("USEQUAQUACHOOSER"))
			USEQUAQUACHOOSER = value.equals("true");
		else if(key.equals("VIEWERATSTARTUP"))
			VIEWERATSTARTUP = value.equals("true");	
		else{
			map.put(key, value);
		}
	}
	
}
