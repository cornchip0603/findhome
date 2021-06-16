package com.itwillbs.controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Kakao_restapi {
	public JsonNode getAccessToken(String autorize_code) {
		 
        final String RequestUrl = "https://kauth.kakao.com/oauth/token";
 
        final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
 
        postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
 
        postParams.add(new BasicNameValuePair("client_id", "cb04aa3b14b605e3f4ce52f66ec99303"));
 
        postParams.add(new BasicNameValuePair("redirect_uri", "http://itwillbs2.cafe24.com/findhome/kakaologin"));
 
        postParams.add(new BasicNameValuePair("code", autorize_code));
 
        final HttpClient client = HttpClientBuilder.create().build();

 
        final HttpPost post = new HttpPost(RequestUrl);
 
        JsonNode returnNode = null;
 
        try {
        	
            post.setEntity(new UrlEncodedFormEntity(postParams));

  	        final HttpResponse response = client.execute(post);
 
  	        final int responseCode = response.getStatusLine().getStatusCode();



  	        System.out.println("\nSending 'POST' request to URL : " + RequestUrl);

  	        System.out.println("Post parameters : " + postParams);
 
  	        System.out.println("Response Code : " + responseCode);
  	      
 
            ObjectMapper mapper = new ObjectMapper();
 
            returnNode = mapper.readTree(response.getEntity().getContent());
 
        } catch (UnsupportedEncodingException e) {
 
            e.printStackTrace();
 
        } catch (ClientProtocolException e) {
 
            e.printStackTrace();
 
        } catch (IOException e) {
 
            e.printStackTrace();
 
        } finally {
 
        }
 
        return returnNode;
 
    }
	
	
	 public JsonNode Logout(String autorize_code) {
	        final String RequestUrl = "https://kapi.kakao.com/v1/user/logout";
	 
	        final HttpClient client = HttpClientBuilder.create().build();
	 
	        final HttpPost post = new HttpPost(RequestUrl);
	 
	        post.addHeader("Authorization", "Bearer " + autorize_code);
	 
	        JsonNode returnNode = null;
	 
	        try {
	 
	            final HttpResponse response = client.execute(post);
	 
	            ObjectMapper mapper = new ObjectMapper();
	 
	            returnNode = mapper.readTree(response.getEntity().getContent());
	 
	        } catch (UnsupportedEncodingException e) {
	 
	            e.printStackTrace();
	 
	        } catch (ClientProtocolException e) {
	 
	            e.printStackTrace();
	 
	        } catch (IOException e) {
	 
	            e.printStackTrace();
	 
	        } finally {
	 
	        }
	 
	        return returnNode;
	 
	    }
	 
	 public static JsonNode getKakaoUserInfo(JsonNode accessToken) {

		         final String RequestUrl = "https://kapi.kakao.com/v2/user/me";
		         final HttpClient client = HttpClientBuilder.create().build();
		         final HttpPost post = new HttpPost(RequestUrl);
		 
		         // add header
		         post.addHeader("Authorization", "Bearer " + accessToken);  
		  
		         JsonNode returnNode = null;
		 
		         try {
		             final HttpResponse response = client.execute(post);
		             final int responseCode = response.getStatusLine().getStatusCode();
		             final String msg=response.getStatusLine().getReasonPhrase();
		             System.out.println("\nSending 'POST' request to URL : " + RequestUrl);
		             System.out.println("Response Code : " + responseCode);
		             System.out.println("Response Code : " + msg);

//		             HttpEntity entity = response.getEntity();  
//		             String responseString = EntityUtils.toString(entity, "UTF-8");
//		             logger.info("responseString----->"+responseString);
		             // JSON 형태 반환값 처리
		             ObjectMapper mapper = new ObjectMapper();
		             returnNode = mapper.readTree(response.getEntity().getContent());  
		  
		         } catch (ClientProtocolException e) {
		             e.printStackTrace();
		         } catch (IOException e) {
		             e.printStackTrace();
		         } finally {
		             
		         }
		         return returnNode;
		     }


}
