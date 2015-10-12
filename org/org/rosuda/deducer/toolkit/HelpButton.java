package org.rosuda.deducer.toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

public class HelpButton extends IconButton {
	public static String baseUrl = "http://www.deducer.org/pmwiki/";
	public String url;
	public HelpButton(String helpUrl) {
		super("/icons/info_28.png", "help",null, "help");
		if(helpUrl==null)
			url = baseUrl;
		else if(helpUrl.startsWith("http://"))
			url = helpUrl;
		else
			url = baseUrl + helpUrl;
		this.addActionListener(new actLis());
		this.setContentAreaFilled(false);
	}

	public void setUrl(String newUrl){
		url=newUrl;
	}
	
	public static boolean showInBrowser(String url)
	  {

	    
	    String os = System.getProperty("os.name").toLowerCase();
	    Runtime rt = Runtime.getRuntime();
	    try
	    {
	            if (os.indexOf( "win" ) >= 0)
	            {
	              String[] cmd = new String[4];
	              cmd[0] = "cmd.exe";
	              cmd[1] = "/C";
	              cmd[2] = "start";
	              cmd[3] = url;
	              rt.exec(cmd);
	            }
	            else if (os.indexOf( "mac" ) >= 0)
	            {
	                rt.exec( "open " + url);
	            }
	            else
	            {
	              //prioritized 'guess' of users' preference
	              String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
	                  "netscape","opera","links","lynx","chrome"};
	 
	              StringBuffer cmd = new StringBuffer();
	              for (int i=0; i<browsers.length; i++)
	                cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");
	 
	              rt.exec(new String[] { "sh", "-c", cmd.toString() });
	              //rt.exec("firefox http://www.google.com");
	              //System.out.println(cmd.toString());
	              
	           }
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null,
	                                            "\n\n The system failed to invoke your default web browser while attempting to access: \n\n " + url + "\n\n",
	                                            "Browser Error",
	                                            JOptionPane.WARNING_MESSAGE);
	 
	        return false;
	    }
	    return true;
	}
	class actLis implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			if(url!=null && url.length()>0)
				showInBrowser(url);
		}
		
	}
	
}


