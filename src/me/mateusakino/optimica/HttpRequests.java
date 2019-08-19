package me.mateusakino.optimica;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpRequests {
	public static String req(String host, String request) throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost(host);
	    String encoding = "text/plain";
	    while (!request.startsWith("\n")){
	    	String str = request.split("\n")[0];
	    	String[] header = str.split(": ");
	    	httpPost.setHeader(header[0], header[1]);
	    	request=request.substring(str.length()+1);
	    	if (header[0].equals("Content-Type"))
	    		encoding = header[1];
	    }
	    
	    if (encoding.contains("multipart")) {        
	    	StringBody stringBody = new StringBody(request, ContentType.create("multipart/form-data"));
	    	httpPost.setEntity(
	    			MultipartEntityBuilder.create()
	    				.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
	    				.addTextBody("__EVENTTARGET", "ctl00$ctl00$ContentSection$ContentPlaceHolder1$SearchControl1$btnSubmit")
	    				.addPart("__EVENTARGUMENT", stringBody)
	    				.setBoundary(encoding.split("boundary=")[1])
	    				.build()
	    	);
	    } else {
	    	request=request.substring(1);
	    	StringEntity entity = new StringEntity(request);
	    	entity.setContentType(encoding);
	    	httpPost.setEntity(entity);
	    }
	 
	    CloseableHttpResponse response = client.execute(httpPost);
	    String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
	    client.close();
	    
	    return responseString;
	}
}