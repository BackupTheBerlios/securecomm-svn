package org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.output;

import org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.services.Constants;


public class HtmlElements implements Constants {
	
	
	public static String getFileInputTypeHidden(String id) {
		StringBuffer it = new StringBuffer();
		it.append("\n<input type='file' size='32' name='");
		it.append(id);
		it.append("' id='").append(id);
		it.append("' style=\"visibility:hidden;\"/>");
		it.trimToSize();
		return it.toString();
	}
	
	
	public static String getFileInputType(String id) {
		StringBuffer it = new StringBuffer();
		it.append("\n<input type='file' size='32' name='");
		it.append(id);
		it.append("' id='").append(id);
		it.append("' />");
		it.trimToSize();
		return it.toString();
	}

	
	public static String getRadioInputType(String name, String value, String onClick) {
		StringBuffer it = new StringBuffer();
		it.append("\n<input type='radio' name='");
		it.append(name).append("' value='").append(value).append("' onclick='");
		it.append(onClick);
		it.append("' />");
		it.trimToSize();
		return it.toString();
	}
	
	public static String getRadioInputType(String name, String value) {
		StringBuffer it = new StringBuffer();
		it.append("\n<input type='radio' name='");
		it.append(name).append("' value='").append(value);
		it.append("' />");
		it.trimToSize();
		return it.toString();
	}
	
	public static String getParagraph(String id, String label) {
		StringBuffer it = new StringBuffer();
		it.append("\n<p><label for='");
		it.append(id).append("'>").append(label).append("</label>");
		it.append(HtmlElements.getFileInputType(id)).append("</p>");
		
		it.trimToSize();
		return it.toString();
	}
	
	
	
	
}
