package org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.output;

import org.qualipso.interop.semantics.securecomm.asmFrontend.consumer.services.Constants;


/** Encapsulates the output produced from the requested Action (e.g. upload, delete etc.) 
 *  into a common look&feel.
 * 
 * @author wp32
 *
 */
public class ResponsePrinter implements Constants {
	
	/** generates the style of the html page.
	 * 
	 * @return: html output as String
	 */
	private static String getStyle() {
		
		StringBuffer tmp = new StringBuffer(512);
		
		tmp.append("\n<style type=\"text/css\">");
		tmp.append("\nbody {margin:0; padding:20px; font-family:Arial, Helvetica, sans-serif;}");
		tmp.append("\nh1{color:#000000; margin:0; padding:0; line-height:50px;}");
		tmp.append("\nh2{padding:1% 0 1% 0%; width:100%; border-bottom:1px solid #036; color:#036;font-size:23px;font-weight:bold;text-align:left;}");
		tmp.append("\nh3{padding:1% 0 1% 0%; width:100%; border-bottom:1px solid #036; color:#036;font-size:16px;font-weight:bold;text-align:left;}");
		tmp.append("\n#top {padding: 10px;margin: 0 0 20px 0;border: 5px solid #ccc;background:#DDDDDD;height: 50px;}");
		tmp.append("\n#box {padding:0 0 0 0; width:100%;}");
		tmp.append("\n#left {display:block;float:left;width:15%;background:#DDDDDD;}");
		tmp.append("\n#main {margin-left:17%;background:#eee;width:71%;padding:0 1%; height:800px;}");
		tmp.append("\n#content {width:80%; margin-top:10px;}");
		tmp.append("\n#list {margin:5px 0 20px 13px; padding:5px 0 0 10px; color:#036;}");
		tmp.append("\n#btn {width:60px; text-align:left; margin-top:10px;}");
		tmp.append("\n#serviceInfoLink{width:60%; margin-top:10px;}");
		tmp.append("\n#serviceName{margin-top:59px;}");
		tmp.append("\n#servicetodelete{margin-top:5px; width:100px;}");
		tmp.append("\n#delete_form {width:60%; margin-top:10px;}");
		tmp.append("</style>\n");
		
		return tmp.toString();
	}
	
	/** Encapsulates the head content.
	 * 
	 * @param headContent: the content produced from the requested action .
	 * @return: html head as String.
	 */
	public static String generateHTMLHead(String headContent) {
		StringBuffer head = new StringBuffer();
		head.append("<head>");
		head.append(getStyle());
		head.append(headContent);
		head.append("</head>");
		
		return head.toString();
	}
	
	/** Encapsulates the body content.
	 * 
	 * @param content: the body content produced from the requested action .
	 * @return: html body as String.
	 */
	public static String generateHTMLBody(String content) {
		StringBuffer body = new StringBuffer();
		body.append("\n<body>");
		body.append(generateTopPanel());
		body.append("\n<div id=\"box\">");
		
		body.append(generateLeftPanel());
		body.append(generateRightPanel(content));
		
		body.append("\n</div>");
		body.append("\n</body>");
		return body.toString();
	}
	
	/** Encapsulates the error message.
	 * 
	 * @param operName: operation name (e.g. deleteService).
	 * @param errata: error message or/and cause.
	 * @return
	 */
	public static String generateHTMLError(String operName, String errata) {
		String error = "<head><title>Error</title></head><body>";
		error += "<h2> Error during operation <i>" + operName + "</i></h>";
		error += "<p>" + errata + "</p></body>";
		
		return error;
	}
	
	/** html page top panel generation. */
	public static String generateTopPanel() {
		StringBuffer topPan = new StringBuffer();
		topPan.append("\n<div id=\"top\" style=\"padding: 10px;margin: 0 0 20px 0;border: 5px solid #ccc;background:#DDDDDD;height: 50px;\">");
		topPan.append("\n<h1>Security Administration Service</h1>");
		topPan.append("\n</div>");
		return topPan.toString();
	}
	
	/** html page right panel generation. */
	private static String generateRightPanel(String content) {
		
		StringBuffer rightPanel = new StringBuffer();
		rightPanel.append("<div id=\"main\">");
		rightPanel.append(content);
		rightPanel.append("</div>");
		return rightPanel.toString();
	}
	
	/** html page left panel (holding real content) generation. */
	private static String generateLeftPanel() {
	
		String baseUrl = SERVICE_URL + OPERATION_NAME_ON_URL + "=";
		
		StringBuffer leftPanel = new StringBuffer();
		leftPanel.append("\n<div id=\"left\">");
		leftPanel.append("<ul id=\"list\">");
		leftPanel.append("\n<li><a href=\"" + baseUrl + OPERATION_LIST_SERVICES +"\">" + OPERATION_LIST_LABEL + "</a></li>");
		leftPanel.append("\n<li><a href=\"" + baseUrl + OPERATION_UPLOAD_SERVICE +"\">" + OPERATION_UPLOAD_LABEL + "</a></li>");
		leftPanel.append("\n<li><a href=\"" + baseUrl + OPERATION_DELETE_SERVICE +"\">" + OPERATION_DELETE_LABEL + "</a></li>");
		leftPanel.append("\n</ul>");
		leftPanel.append("</div>");
		
		return leftPanel.toString();
	}

}
