package biz.ritter.app.visitor;

/* TODO check for existing classes/interfaces */

/**
 * Follow steps are important for XMLHttpRequest:
 * <ol><li> .open ("<i>HTTP-method</i>", "<i>URI</i>"[, <i>async =</i>(true|false) [, <i>username =</i> beastie [, <i>password =</i> bsd]]] ) </li>
 *     <li> .setRequestHeader ("Content-Type", ("text/html"|"application/xml"]) ) </li>
 *     <li> <i>more headers</i> </li>
 *     <li> .send ( [<i>body</i>] ) </li>  
 *     <li> .responseXML </li>
 *     <li> <i>Do something with responseXML-Document</i> </li>
 * </ol>  
 * @author Sͬeͥbͭaͭsͤtͬian
 *
 */
public class XMLHttpRequest {

	public static String [] SUPPORTED_METHODS = {"POST"}; 

	/**
	 * open method is calling.
	 * @param method
	 * @param url
	 */
	public void open(String method, String url) {
		System.err.println(new Exception().getStackTrace()[0].getMethodName());
		System.err.println(">>"+method+","+url);
	}

	/**
	 * send method is calling and the sender waits for answer.
	 * <i>In HTTPResponse the content-type is provided.</i> 
	 * @param body
	 * @return answer
	 */
	public String send(String... body) {
		System.err.println(new Exception().getStackTrace()[0].getMethodName());
		if (null!= body) for (String s : body) {
			System.err.println(">>"+s);
		}
		return "";
	}
	
	/**
	 * close method is calling
	 */
	public void close() {
		System.err.println(new Exception().getStackTrace()[0].getMethodName());
	}	
}