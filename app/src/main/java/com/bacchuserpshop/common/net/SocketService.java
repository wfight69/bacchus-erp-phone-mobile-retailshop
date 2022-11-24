
package com.bacchuserpshop.common.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;

import com.google.gson.Gson;


public class SocketService {
	
	private static final String LOG_TAG = "SocketService";
    
	private String 	server			= null;
	private Integer	port			= 6000;
    public	Socket 	socket			= null;
    
    OutputStreamWriter 	out_stm_wr 	= null;
    PrintWriter			prt_wr	    = null;
    InputStreamReader 	in_stm_rd 	= null;
    BufferedReader 		buf_rd 		= null;
    
    // file처리시 주로
    BufferedInputStream  buf_in_stm		= null;
    BufferedOutputStream buf_out_stm	= null;
    DataInputStream		 data_in_stm	= null;
    DataOutputStream	 data_out_stm	= null;
 
    final Integer TIME_OUT = (5 * 1000);		// 5초
    
    private String jsonString = null;
    private String down_file_path  = null;
    private String down_file_name = null;
    
 	//constructor 
    public SocketService(String server, Integer port) {
    	this.server = server;
    	this.port   = port;
    	
    	// 소켓오픈 처리
    	doOpen();
    }

	public boolean doOpen() {
		try {
			 // IP 주소와 포트 번호를 관리하는 객체를 생성한다.
            SocketAddress sock_addr = new InetSocketAddress(this.server, this.port);
            // 소켓을 생성한다.
            socket = new Socket();
            // 수신 버퍼 크기를 1024 바이트로 설정한다.
            socket.setReceiveBufferSize(1024); 
            // 송신 버퍼 크기를 1024 바이트로 설정한다.
            socket.setSendBufferSize(1024);
            // 소켓을 닫을 때 TIME_OUT 에 저장해둔 밀리세컨만큼 대기한 후 닫는다.
            socket.setSoLinger(true, TIME_OUT); 
            // 15분간 수신되는 데이터가 없으면 연결이 자동으로 끊긴다.
            socket.setSoTimeout(1000*60*15); 
            
            // 서버와 연결을 시도한다. TIME_OUT 시간 내에 응답이 오지 않으면 연결을 포기한다.
            socket.connect(sock_addr, TIME_OUT);
            
            // 연결된 경우
            if(socket != null && socket.isConnected()){
                // 서버에서 보내는 데이터를 받을 수 있도록 read 상태에 들어간다.
            }
            
	   	   	System.out.println("== doOpen() openConnection() Success.." );
	   	   	
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("== doOpen() Exception Fail.." + e.getMessage() );
			return false;
		}
		return true;
    }

	// 데이타 보내기
	public boolean doSendPost(String sendData) {
		try {
			
			System.out.println("== doSendPost() connUrl => " + server + ", sendData => "  + sendData);         
			
			// 소켓연결 처리
			if (socket == null) {
		   	   	if (!doOpen()) {
		   	   		return false;
		   	   	}
			}
	     
            // Set the request
            out_stm_wr 	= new OutputStreamWriter(socket.getOutputStream());
            prt_wr	   	= new PrintWriter(out_stm_wr);
            prt_wr.println(sendData);
            prt_wr.flush();

            System.out.println("== doSendPost() sending. => "  + sendData);
        
            // Get the response
            in_stm_rd 	= new InputStreamReader(socket.getInputStream(),"UTF-8");
            buf_rd 		= new BufferedReader(in_stm_rd);
            String response  = buf_rd.readLine();
            
			if(!response.equals("") && response != null){
				  jsonString = response;
				  System.out.println("== doSendPost() response => "  + jsonString);
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonString = e.getMessage();
			return false;
		} finally {
				/*
			  try{ in_stm_rd.close(); } catch( Exception se ){}
			  try{ out_stm_wr.close();  }catch( Exception se ){}
			  try{ buf_rd.close(); } catch( Exception se ){}
			  try{ prt_wr.close();  }catch( Exception se ){}			  
			 try{ socket.close(); } catch( Exception s ){}
			 */
		}

	    return true;
    }
	
	//////////////////////
	// 헤더 전송 및 테이타화일 다운
	public boolean doDownStream(String sendData, String down_file_path, String down_file_name, String format) {
		try {
			
			System.out.println("== socket doDownStream() connUrl => " + server + ", sendData => "  + sendData);         
			
			// 소켓연결 처리
			if (socket == null) {
		   	   	if (!doOpen()) {
		   	   		return false;
		   	   	}
			}
            
            out_stm_wr 	= new OutputStreamWriter(socket.getOutputStream());
            prt_wr	   	= new PrintWriter(out_stm_wr);
            prt_wr.println(sendData);
            prt_wr.flush();
                        
            buf_in_stm = new BufferedInputStream( socket.getInputStream() );
            data_in_stm = new DataInputStream(buf_in_stm);
            
            // 서버쪽에서 사이즈 지정해서 보냄(현재 10자리.. ex) 0000041576
			byte[] content = new byte[ 10 ];
			data_in_stm.read( content, 0, 10 );
			String fileSize = new String( content  ).trim();
			
            String fileName = down_file_path + down_file_name + format;

			System.out.println("== socket doDownStream() fileSize => " + fileSize + ", fileName => " + fileName);     
			  
            FileOutputStream fos = new FileOutputStream(fileName);
			long lfileSize	= Long.parseLong(fileSize);
			int  nPos		= 0;
			int  n			= 0;
			byte[] buf		= new byte[1024];
			while (nPos < lfileSize) 
			{
				n = data_in_stm.read(buf,0,buf.length);
				if(n == -1) break;
				if (n != 0) {
					nPos += n;
					fos.write(buf, 0, n);
				}
				System.out.println("readen total bytes=" + nPos + ", read bytes = " + n);
			}
			fos.close();
            
            ////////////
            this.down_file_path = down_file_path;
            this.down_file_name = down_file_name;            		
			
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("== doDownStream() Exception message => "  +  e.getMessage());
			return false;
		} finally {
			/*
			// 메시지 처리
			try{ in_stm_rd.close(); } catch( Exception se ){}
			try{ out_stm_wr.close();  }catch( Exception se ){}
			try{ buf_rd.close(); } catch( Exception se ){}
			try{ prt_wr.close();  }catch( Exception se ){}
			
			// file stream
			try{ buf_in_stm.close(); } catch( Exception se ){}
			try{ data_in_stm.close();  }catch( Exception se ){}
			try{ buf_out_stm.close(); } catch( Exception se ){}
			try{ data_out_stm.close();  }catch( Exception se ){}		
			  
			  */
			//try{ socket.close(); } catch( Exception s ){}
		}
	    return true;
    }
	

	// 데이타 보내기
	public boolean doDownStream2(String server, String sendData, String down_file_path, String down_file_name, String format) {
		try {
			
			System.out.println("== socket doDownStream() connUrl => " + server + ", sendData => "  + sendData);         
			
			// 소켓연결 처리
			if (socket == null) {
		   	   	if (!doOpen()) {
		   	   		return false;
		   	   	}
			}
            
            // Set the request
            buf_out_stm = new BufferedOutputStream(socket.getOutputStream());
            data_out_stm = new DataOutputStream(buf_out_stm);
            data_out_stm.write(sendData.getBytes());
            data_out_stm.flush();
            
            ///////////////////////////////////////////////////////
            // Get the filesize
            buf_in_stm = new BufferedInputStream( socket.getInputStream() );
            data_in_stm = new DataInputStream(buf_in_stm);
            
			byte[] content = new byte[ 30 ];
			data_in_stm.read( content );
			String fileSize = new String( content  ).trim();
            
            System.out.println("== doDownStream() fileSize => " + fileSize); 
   
            ////////////////////////////////////////
            // Get the file down
            int totalSize = Integer.parseInt(fileSize);
            String file_name = down_file_path + down_file_name + "." + format;
            File file = null;
            FileOutputStream f = null;
            if (totalSize >= 0) {
                // 파일생성
                file = new File(file_name);
                f = new FileOutputStream(file);
            }
            System.out.println("== doDownStream() totalSize => "  + totalSize + ", file_name => "  + file_name);
            
            ///////////////////////////////////////////////
            //variable to store total downloaded bytes
            int downloadedSize = 0;

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0; //used to store a temporary size of the buffer
            
            //now, read through the input buffer and write the contents to the file
            while ( (bufferLength = data_in_stm.read(buffer)) > 0 ) {
                    f.write(buffer, 0, bufferLength);
                    
                    //add up the size so we know how much is downloaded
                    downloadedSize += bufferLength;
                    
    				System.out.println("readen total Size =" + totalSize + ", downloadedSize bytes = " + downloadedSize);
            }
            //close the output stream when done
            f.close();                
            
            ////////////
            this.down_file_path = down_file_path;
            this.down_file_name = down_file_name;            		
			
		} catch (Exception e) {
			e.printStackTrace();
			 System.out.println("== doDownStream() Exception message => "  +  e.getMessage());
			return false;
		} finally {
			// 메시지 처리
			try{ in_stm_rd.close(); } catch( Exception se ){}
			try{ out_stm_wr.close();  }catch( Exception se ){}
			try{ buf_rd.close(); } catch( Exception se ){}
			try{ prt_wr.close();  }catch( Exception se ){}
			
			// file stream
			try{ buf_in_stm.close(); } catch( Exception se ){}
			try{ data_in_stm.close();  }catch( Exception se ){}
			try{ buf_out_stm.close(); } catch( Exception se ){}
			try{ data_out_stm.close();  }catch( Exception se ){}		
			  
			try{ socket.close(); } catch( Exception s ){}
		}
	    return true;
    }
	
	
	// 소켓 종료
	public void doClose() {
		try {       
			
			// 소켓연결 처리
			if (socket != null) {
				
		   		// 종료처리== bye
		   		HashMap mp = new HashMap();
		   		mp.put("msg_type",  "bye");
		   		//
		   		Gson gson = new Gson();
		   		String jsonString = gson.toJson(mp);
				if(!this.doSendPost(jsonString)) {
					System.out.println("== doSendPost() Socket Data Bye Error...");         
				}
				
				try{ socket.close();  }catch( Exception se ){}
				
				System.out.println("== socket doClose() server => " + server + ", port => " + port);    
			}

		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			
			// 메시지 처리
			try{ in_stm_rd.close(); } catch( Exception se ){}
			try{ out_stm_wr.close();  }catch( Exception se ){}
			try{ buf_rd.close(); } catch( Exception se ){}
			try{ prt_wr.close();  }catch( Exception se ){}
			
			// file stream
			try{ buf_in_stm.close(); } catch( Exception se ){}
			try{ data_in_stm.close();  }catch( Exception se ){}
			try{ buf_out_stm.close(); } catch( Exception se ){}
			try{ data_out_stm.close();  }catch( Exception se ){}
			
			try{ socket.close();  }catch( Exception se ){}
		}
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