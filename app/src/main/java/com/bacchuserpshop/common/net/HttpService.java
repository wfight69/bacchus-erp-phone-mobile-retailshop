
package com.bacchuserpshop.common.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.util.Log;

public class HttpService {
	
	private static final String LOG_TAG = "HttpService";
    
    private Context context = null;
    
    private HttpURLConnection httpConn = null;
    private String jsonString = null;
    private String down_file_path  = null;
    private String down_file_name = null;

    // 토큰처리
	private String jwtToken = null;
    
    // fileupload FormField Setting.
    String CRLF = "\r\n";			// lineEnd
    String twoHyphens = "--";
    String boundary = "*****";

	//
	private String req_method = "POST";
	private String cookies = "";

	//constructor
	public HttpService() {
	}
	  //constructor 
    public HttpService(String jwtToken) {
    	this.jwtToken = jwtToken;
    }

    public HttpService(Context context) {
    	this.context = context;
    }
    
	public boolean doOpen(String url) {
		try {
			URL urlOpen = new URL(url);
			httpConn = (HttpURLConnection) urlOpen.openConnection();
			httpConn.setConnectTimeout(9500);			// 5초처리
			httpConn.setReadTimeout(9000);				// 10초처리
			httpConn.setDefaultUseCaches(false);
            httpConn.setDoOutput(true);					// If you invoke the method setDoOutput(true) on the URLConnection, it will always use the POST method.
			httpConn.setUseCaches(false);
			httpConn.setInstanceFollowRedirects(false);
			httpConn.setRequestMethod(req_method);
			//httpConn.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=utf-8");
			httpConn.setRequestProperty("content-type", "application/json");
			httpConn.setRequestProperty("Connection", "keep-alive");
			httpConn.setRequestProperty("Authorization", jwtToken); // Bearer 토큰
			//
			if (req_method.equals("GET")) {
				if (cookies == null || cookies.equals("")) {
					Map m = httpConn.getHeaderFields();
					if(m.containsKey("Set-Cookie")) {
						Collection c =(Collection)m.get("Set-Cookie");
						for(Iterator i = c.iterator(); i.hasNext(); ) {
							cookies = (String)i.next();
						}
					}
					Log.i(LOG_TAG, "== doOpen() openConnection() cookies => " + cookies );
				} else {
					httpConn.setRequestProperty("Cookie", cookies);
				}
			} else {
//				// 세션id설정
//				String session_id = ConfigUtils.getSessionId(this.context);
//				httpConn.setRequestProperty("Cookie", "JSESSIONID=" + session_id);
//
//				Log.i(LOG_TAG, "== doOpen() openConnection() session_id => " + session_id );
			}
			/*
			if (httpConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
		   	   	Log.i(LOG_TAG, "== doOpen() openConnection() Fail.." );
				return false;
			}
			*/
	   	   	Log.i(LOG_TAG, "== doOpen() openConnection() Success..");
	   	   	
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(LOG_TAG, "== doOpen() Exception Fail.." + e.getMessage() );
			cookies = "";
			return false;
		}
		return true;
    }


	// 데이타 보내기
	public boolean doSendPost(String connUrl, String sendData) {
		try {
			
			Log.i(LOG_TAG, "== doSendPost() connUrl => " + connUrl + ", sendData => "  + sendData);         
			
	   	   	if (!doOpen(connUrl)) {
	   	   		return false;
	   	   	}
	     
            // Set the request
            OutputStreamWriter wr = new OutputStreamWriter(httpConn.getOutputStream());
            wr.write(sendData);
            wr.flush();

			 //Get the response
			int responseCode = httpConn.getResponseCode();
			if (responseCode == 400 || responseCode == 401 || responseCode == 500 ) {
				jsonString = responseCode + " Error!";
				return false;
			} else {
				BufferedReader rd = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
				String line = null;
				StringBuilder sb = new StringBuilder();
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}
				//
				jsonString = sb.toString();
				//
				Log.i(LOG_TAG, "== doSendPost() connUrl => " + connUrl + ", BufferedReader => "  + jsonString);
			}

			/*
            JSONObject commands = new JSONObject();
            int responseCode = conn.getResponseCode();
            if (responseCode == 400 || responseCode == 401 || responseCode == 500 ) {
                System.out.println(responseCode + " Error!");
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                responseJson = new JSONObject(sb.toString());
                System.out.println(responseJson);
            }
             */

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			jsonString = e.getMessage();
			return false;
		} finally {
			httpConn.disconnect();
		}
    }

	// 데이타 보내기(GET방식)
	public boolean doSendGet(String connUrl) {
		try {

			Log.i(LOG_TAG, "== doSendGet() connUrl => " + connUrl);

			req_method = "GET";

			if (!doOpen(connUrl)) {
				return false;
			}

			//Get the response
			int responseCode = httpConn.getResponseCode();
			if (responseCode == 400 || responseCode == 401 || responseCode == 500 ) {
				jsonString = responseCode + " Error!";
				return false;
			} else {
				BufferedReader rd = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
				String line = null;
				StringBuilder sb = new StringBuilder();
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}
				//
				jsonString = sb.toString();
				//
				Log.i(LOG_TAG, "== doSendPost() connUrl => " + connUrl + ", BufferedReader => "  + jsonString);
			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			jsonString = e.getMessage();
			return false;
		} finally {
			httpConn.disconnect();
		}
	}

	// 데이타 보내기
	public boolean doDownStream(String connUrl, String sendData, String down_file_path, String down_file_name, String format) {
		try {
			
			Log.i(LOG_TAG, "== doDownStream() connUrl => " + connUrl + ", sendData => "  + sendData);         
			
	   	   	if (!doOpen(connUrl)) {
	   	   		return false;
	   	   	}
            
            // Set the request
            OutputStreamWriter wr = new OutputStreamWriter(httpConn.getOutputStream());
            wr.write(sendData);
            wr.flush();
            
            // Get the stream
            InputStream in = httpConn.getInputStream();
            
            //this is the total size of the file
            int totalSize = httpConn.getContentLength();
            
            String file_name = down_file_path + down_file_name + "." + format;
            File file = null;
            FileOutputStream f = null;
            if (totalSize >= 0) {
                // 파일생성
                file = new File(file_name);
                f = new FileOutputStream(file);
            }
            Log.i(LOG_TAG, "== doDownStream() totalSize => "  + totalSize + ", file_name => "  + file_name);
            
            //variable to store total downloaded bytes
            int downloadedSize = 0;

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0; //used to store a temporary size of the buffer

            //now, read through the input buffer and write the contents to the file
            while ( (bufferLength = in.read(buffer)) > 0 ) {
                    f.write(buffer, 0, bufferLength);
                    
                    //add up the size so we know how much is downloaded
                    downloadedSize += bufferLength;
                    
                    //this is where you would do something to report the prgress, like this maybe
                    //updateProgress(downloadedSize, totalSize);
            }
            //close the output stream when done
            f.close();
            
            ////////////
            this.down_file_path = down_file_path;
            this.down_file_name = down_file_name;            		
			
		} catch (Exception e) {
			e.printStackTrace();
			 Log.i(LOG_TAG, "== doDownStream() Exception message => "  +  e.getMessage());
			return false;
		} finally {
			httpConn.disconnect();
		}

	    return true;
    }
	
	
	// 로컬파일 업로드처리(자산품목 Item)
	public boolean doUploadFile(String upLoadServerUri, String fullFilePathName, String uploadFileName) {
      	    
		 Log.i(LOG_TAG, "== doUploadFile() uploadFileName => "  +  uploadFileName);
		 
	    int serverResponseCode = 0;
	    
        HttpURLConnection conn = null;
        DataOutputStream dos = null;  
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024; 
        
		 try { 
		      	 
			File sourceFile = new File(fullFilePathName); 
			if (!sourceFile.isFile()) {
			    return false;
			}
		        
			 // open a URL connection to the Servlet
			 URL url = new URL(upLoadServerUri);
			  
			 // Open a HTTP  connection to  the URL
			 conn = (HttpURLConnection) url.openConnection(); 
			 conn.setDoInput(true); 		// Allow Inputs
			 conn.setDoOutput(true); 		// Allow Outputs
			 conn.setUseCaches(false); 		// Don't use a Cached Copy
			 conn.setRequestMethod("POST");
			 conn.setRequestProperty("Connection", "Keep-Alive");
			 conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			 conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			 //conn.setRequestProperty("_filename", 	uploadFileName);

			 /*
			 // 세션id설정
			 String session_id = ConfigUtils.getSessionId(this.context);
			 conn.setRequestProperty("Cookie", "JSESSIONID=" + session_id);
			 */
			 dos = new DataOutputStream(conn.getOutputStream());

             writeFormField(dos, "entprs_cd", 	"davada");
             writeFormField(dos, "_filename", 	uploadFileName);								// NtsRfidItem.txt
			 writeFileField(dos, "_file", 		fullFilePathName, "text/plain");		// /mnt/bacchus_erp/voice/NtsRfidItem.txt
			 //writeFileField(dos, "_file", 		fullFilePathName, "image/jpg");		// /mnt/salesasset/image/0707_0001.jpg

			 //===== 실제화일 전송함.
    		 FileInputStream fileInputStream = new FileInputStream(sourceFile);

			 // create a buffer of  maximum size
			 bytesAvailable = fileInputStream.available(); 
			
			 bufferSize = Math.min(bytesAvailable, maxBufferSize);
			 buffer = new byte[bufferSize];
			
			 // read file and write it into form...
			 bytesRead = fileInputStream.read(buffer, 0, bufferSize);  		    
			 while (bytesRead > 0) {
			   dos.write(buffer, 0, bufferSize);
			   bytesAvailable = fileInputStream.available();
			   bufferSize = Math.min(bytesAvailable, maxBufferSize);
			   bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			  }		
			 // send multipart form data necesssary after file data...
			 dos.writeBytes(CRLF);
			 dos.writeBytes(twoHyphens + boundary + twoHyphens + CRLF);

			 // 신규버전 json을 통한  정확히 업로드 여부 체크하기 위함
	        // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line = null;
            String response = "";
            while ((line = rd.readLine()) != null) {
               response += line;
            }       
			//
			if(!response.equals("") && response != null){
				  jsonString = response;
				  Log.i(LOG_TAG, "== doUploadFile() response => "  + jsonString);
			}

			 //close the streams //
			 fileInputStream.close();
		     dos.flush();
		     dos.close();

		} catch (Exception e) {              
		    e.printStackTrace();
		     
		    Log.e("Upload file to server Exception", "Exception : "  + e.getMessage(), e);  
		    
		    return false; 
		} finally {
			try {
				dos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.disconnect();
		}
		return true; 
   } 
	
	
    /**
     * write one form field to dataSream
     * @param fieldName
     * @param fieldValue
     */
    private void writeFormField(DataOutputStream dos, String fieldName, String fieldValue)  {

        try  {
        	dos.writeBytes(twoHyphens + boundary + CRLF);    
        	dos.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\"" + CRLF);
        	dos.writeBytes(CRLF);
        	dos.writeBytes(fieldValue);
        	dos.writeBytes(CRLF);      	
        }   
        catch(Exception e)   {
            //System.out.println("AndroidUploader.writeFormField: got: " + e.getMessage());
            //Log.e(TAG, "AndroidUploader.writeFormField: " + e.getMessage());
        }
    }
  
    /**
     * write one file field to dataSream
     * @param fieldName - name of file field
     * @param fieldValue - file name
     * @param type - mime type
     */
    private void writeFileField(DataOutputStream dos,
        String fieldName,
        String fieldValue, String type)  {
    	
        try {
            // opening boundary line
            dos.writeBytes(twoHyphens + boundary + CRLF);    
            dos.writeBytes("Content-Disposition: form-data; name=\""
                                  + fieldName
                                  + "\";filename=\"" 
                                  + fieldValue
                                  + "\"" 
                                  + CRLF);
            dos.writeBytes("Content-Type: " + type +  CRLF);
            dos.writeBytes(CRLF); 
        }

        catch(Exception e)  {
            //System.out.println("GeoPictureUploader.writeFormField: got: " + e.getMessage());
            //Log.e(TAG, "AndroidUploader.writeFormField: got: " + e.getMessage());

        }

    }
	
	// http 종료
	public void doClose() {
		try {       
			
			// http 연결해제
			if (httpConn != null) {
				httpConn.disconnect();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
	
	public HttpURLConnection getHttpConn() {
		return httpConn;
	}

	public void setHttpConn(HttpURLConnection httpConn) {
		this.httpConn = httpConn;
	}

	public String getReq_method() {
		return req_method;
	}

	public void setReq_method(String req_method) {
		this.req_method = req_method;
	}

	public void setToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getDown_file_path() {
		return down_file_path;
	}

	public void setDown_file_path(String down_file_path) {
		this.down_file_path = down_file_path;
	}

	public String getDown_file_name() {
		return down_file_name;
	}

	public void setDown_file_name(String down_file_name) {
		this.down_file_name = down_file_name;
	}
    
      
}