/*****************************************************************************
* 파일명 : FileUtils.java
* 패키지 : com.epc.common.util
* 생성일 : 2010. 07. 07.
* 작성자 : 
* ===========================================================================
* 변경이력:
* DATE             AUTHOR      DESCRIPTION
* ---------------------------------------------------------------------------
* 변경 이력은 이곳에 추가 합니다.
*****************************************************************************/
package com.bacchuserpshop.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


/**
 * 날짜 관련 유틸리티
 *  - 기본적으로 Joda Time API(http://joda-time.sourceforge.net)를 사용
 *  - 양력/음력 변환은 IBM 패키지를 사용 (icu4j.jar)
 *
 * @author 
 */

public class FileUtils {

    // 하루(1일)의 Millisecond
    public static final long MILLIS_PER_DAY = 86400000L;

    private FileUtils() {
    }

	
	/**
	 * 날짜를 Long타입으로 리턴한다.
	 *
	 * @param int year
	 * @param int month
	 * @param int date
	 * @return long
	 */
	public static long getMillis(int year,int month,int day) {

		return (new DateTime(year, month, day, 0, 0, 0, 0)).getMillis();
	}

	/**
	 * 화일존재하는지  여부.
	 *
	 * @param string fullFileName
	 * @return boolean
	 */
	public static boolean isFileExists(String fullFileName) {

		File f = new File(fullFileName);
		if (!f.exists()) return false;		
	    
	    return true;
	}	
	
	/**
	 * 해당화일 삭제
	 *
	 * @param string fullFileName
	 * @return boolean
	 */
	public static boolean isFileDelete(String fullFileName) {

		File f = new File(fullFileName);
		if (!f.delete()) return false;		
	    
	    return true;
	}	
	
	/**
	 * 디렉토리를 생성한다.
	 *
	 * @param string dirPath
	 * @return boolean
	 */
	public static boolean makeDir(String dirPath) {

		File f = new File(dirPath);
		if (!f.mkdirs()) return false;		// mkdirs()는 상위디렉토리가 없을시 같이 생성함.
	    
	    return true;
	}
	
	/**
	 * 해당디렉토리에 모든 화일 삭제한다.
	 *
	 * @param string source
	 * @return boolean
	 */
	public static boolean removeFiles(String source){
		File[] listFile = new File(source).listFiles(); 
		try{
			if(listFile.length > 0){
				for(int i = 0 ; i < listFile.length ; i++){
					if(listFile[i].isFile()){
						listFile[i].delete(); 
					}
				}
			}
		}catch(Exception e){
			System.err.println(System.err);
			return false;
		}
		return true;
	}
	
	/**
	 * 디렉토리 이하 모든 화일 및 하위 디렉토리 삭제한다.
	 *
	 * @param string source
	 * @return boolean
	 */
	public static boolean removeDIR(String source){
		File[] listFile = new File(source).listFiles(); 
		try{
			if(listFile.length > 0){
				for(int i = 0 ; i < listFile.length ; i++){
					if(listFile[i].isFile()){
						listFile[i].delete(); 
					}else{
						removeDIR(listFile[i].getPath());
					}
					listFile[i].delete();
				}
			}
		}catch(Exception e){
			System.err.println(System.err);
			return false;
		}
		return true;
	}	
	
	/**
	 * 오늘일자기준 - 설정일자 이전 디렉토리 이하 모든 화일 및 하위 디렉토리 삭제한다.
	 *
	 * @param string source, String 삭제일(오늘기준 이전일자)
	 * @return boolean
	 */
	public static boolean removeDateDIR(String source, int del_day){
		File[] listFile = new File(source).listFiles(); 
		try{
			if(listFile.length > 0){
				for(int i = 0 ; i < listFile.length ; i++){
					if(listFile[i].isDirectory()){
						String minusDate = DateUtils.minusDays(del_day, "yyyy-MM-dd");
						String dirDate   = listFile[i].getName();
						
						System.err.println("== minusDate => " + minusDate + ", listFile["+i+"].dirDate() => " + dirDate);	
						if(minusDate.equals(dirDate.trim())) {
							removeDIR(listFile[i].getPath());	
							listFile[i].delete();
						}
					}
				}
			}
		}catch(Exception e){
			System.err.println(System.err);
			return false;
		}
		return true;
	}		
	
	// 파일 src->target copy
	public static void copyFiles(String srcImgFullName, String tgtImgFullName) {
		        
		try{
			File srcFile = new File(srcImgFullName);
			File tgtFile = new File(tgtImgFullName);
			
	        InputStream in = new FileInputStream(srcFile);
	        OutputStream out = new FileOutputStream(tgtFile);
	        
	        // Copy the bits from instream to outstream
	        byte[] buf = new byte[1024];
	        int len;
	        while ((len = in.read(buf)) > 0) {
	            out.write(buf, 0, len);
	        }
	        in.close();
	        out.close();
		}catch(Exception e){
			System.err.println(System.err);
		}
	}
	
	
    public static void main(String[] args) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = fmt.parseDateTime("20070627");

		fmt = DateTimeFormat.forPattern("HH:mm:ss");
		dt = new DateTime();

		fmt = DateTimeFormat.forPattern("a");
		fmt = fmt.withLocale(Locale.KOREA);

		String format = "YYYY";
		format = format.replace("Y", "y");
		format = format.replace("D", "d");
		fmt = DateTimeFormat.forPattern(format);

		DateTime.Property pDoW = dt.dayOfWeek();

		DateTime tmp = new DateTime(2001, 1, 21, 0, 0, 0, 0);

		tmp = new DateTime(2009, 1, 1, 0, 0, 0, 0);
		tmp = tmp.minusDays(1);

		tmp = new DateTime();
    }

}
