package org.rosuda.deducer.toolkit;

import java.util.Collection;
import java.util.Vector;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLHelper {

	public static Vector getChildrenElementsByTag(Node node, String name){
		Vector result = new Vector();
		NodeList nl = node.getChildNodes();
		for(int i=0;i<nl.getLength();i++)
			if(nl.item(i) instanceof Element && 
					(((Element)nl.item(i)).getTagName().equals(name) || name ==null)){
				result.add(nl.item(i));
				//System.out.println(nl.item(i).toString());
				//System.out.println(((Element)nl.item(i)).getAttribute("className"));
				//System.out.println(((Element)nl.item(i)).getTagName());
			}
		return result;
	}
	
	public static void debugPrintChildren(Node node){
		NodeList nl = node.getChildNodes();
		System.out.println("-------------------");
		for(int i=0;i<nl.getLength();i++){
				System.out.println(nl.item(i).toString());
				if(nl.item(i) instanceof Element){
					System.out.println(((Element)nl.item(i)).getAttribute("className"));
					System.out.println(((Element)nl.item(i)).getTagName());
				}
				System.out.println("---");
		}

	}
	
	public static void appendCollection(Node node, Collection c,String tag){
		appendCollection(node,c.toArray(),tag);
	}
	
	public static void appendCollection(Node node, Object[] o,String tag){
		Element child = node.getOwnerDocument().createElement(tag);
		for(int i=0;i<o.length;i++){
			child.setAttribute("element_"+i, (String) o[i]);
		}
		node.appendChild(child);
	}
	
	public static String[] getChildCollection(Node node, String tag){
		Vector childNodes =  XMLHelper.getChildrenElementsByTag(node, tag);
		if(childNodes.size()==0)
			return null;
		Element childNode = (Element) childNodes.get(0);
		NamedNodeMap attr = childNode.getAttributes();
		if(attr.getLength()>0){
			String[] elements = new String[attr.getLength()];
			for(int i=0;i<attr.getLength();i++)
				elements[i] = attr.item(i).getNodeValue();
			return elements;
		}
		return new String[]{};
	}
	
}
