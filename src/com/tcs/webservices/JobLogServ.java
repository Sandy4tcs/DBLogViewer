package com.tcs.webservices;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.Entity;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;


@WebService()
@Entity
@Path("/joblog")
public class JobLogServ {
	

	@GET
    @Path("retrivejoblog")
    @Produces("text/plain")
    @WebMethod(operationName = "retrivejoblog")
	public String retrivejoblog(@QueryParam("runid") String runID,@QueryParam("etl_date") String etlDate,@QueryParam("tabs") String tabs) throws SQLException {
		
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
        String strHeader = ""; 
        String strData = "";
        String finalStr = "";
        
        Properties prop = new Properties();
        InputStream propertiesInputStream = null;
        
        
        try 
        {
        
        	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			propertiesInputStream = classLoader.getResourceAsStream("com/tcs/webservices/dbprop.properties");
			prop.load(propertiesInputStream);
			propertiesInputStream.close();
			
        	Class.forName("com.mysql.jdbc.Driver");
            //con = DriverManager.getConnection("jdbc:mysql://192.168.225.109:3306/wso2devregdb", "devdbusr", "devdbusr");

        	con = DriverManager.getConnection(prop.getProperty("LogDBURL"), prop.getProperty("LogDBUID"), prop.getProperty("LogDBPwd"));
        	
            //String query = "SELECT job_id, runid, entity, run_mode, ExtractStart, ExtractEnd, S3LoadStart, S3LoadEnd, RedShiftLoadStart, RedShiftLoadEnd, job_status, subsidiary_id FROM job_log;";
            
            String query = "select job_id, runid, entity, run_mode, ExtractStart, ExtractEnd, S3LoadStart, S3LoadEnd, RedShiftLoadStart, RedShiftLoadEnd, job_status, subsidiary_id from job_log a"
            		+ " where a.runid= coalesce(?,a.runid)"
            		+ "and DATE_FORMAT(a.ExtractStart,'%Y-%m-%d') = coalesce(?,DATE_FORMAT(a.ExtractStart,'%Y-%m-%d'))"
            		+ "and a.entity = coalesce(?,a.entity)";
            
            st = con.prepareStatement(query);
            
            st.setString(1, (runID != null && runID.length() != 0  ? runID:null));
            st.setString(2, (etlDate != null && etlDate.length() != 0  ? etlDate:null));
            st.setString(3,(tabs != null && tabs.length() != 0 ? tabs:null));
            //System.out.println(st.toString());
            rs = st.executeQuery();
            strHeader = "{"
            		+ "\"data\": [";
            int i = 0;
            if(rs.next()) {
            	rs.beforeFirst();            
	            while (rs.next()) 
	            {
	            	if(i == 0) {
	            		strData	= strData	+ "{ "
	            				+ "\"JobId\":" + rs.getInt("job_id") + ","
	            				+ "\"RunId\":" + rs.getInt("runid") + ","
			            		+ "\"Entity\": \"" + rs.getString("entity") + "\","
			            		+ "\"Run Mode\": \"" + rs.getString("run_mode") + "\","
			            		+ "\"ExtractStartTime\": \"" + rs.getTimestamp("ExtractStart") + "\","
			            		+ "\"ExtractEndTime\": \"" + rs.getTimestamp("ExtractEnd") + "\","
			            		+ "\"S3LoadStartTime\": \"" + rs.getTimestamp("S3LoadStart") + "\","
			            		+ "\"S3LoadEndTime\": \"" + rs.getTimestamp("S3LoadEnd") + "\","
			            		+ "\"RedShiftLoadStart\": \"" + rs.getTimestamp("RedShiftLoadStart") + "\","
			            		+ "\"RedShiftLoadEnd\": \"" + rs.getTimestamp("RedShiftLoadEnd") + "\","
			            		+ "\"Job Status\": \"" + rs.getString("job_status") + "\","
			            		+ "\"Subsidiary ID\": \"" + rs.getString("subsidiary_id") + "\""
			            		+ "}";
	            	} else {
	            		strData = strData + "," 
	            				+  "{ "
	            				+ "\"JobId\":" + rs.getInt("job_id") + ","
	            				+ "\"RunId\":" + rs.getInt("runid") + ","
			            		+ "\"Entity\": \"" + rs.getString("entity") + "\","
			            		+ "\"Run Mode\": \"" + rs.getString("run_mode") + "\","
			            		+ "\"ExtractStartTime\": \"" + rs.getTimestamp("ExtractStart") + "\","
			            		+ "\"ExtractEndTime\": \"" + rs.getTimestamp("ExtractEnd") + "\","
			            		+ "\"S3LoadStartTime\": \"" + rs.getTimestamp("S3LoadStart") + "\","
			            		+ "\"S3LoadEndTime\": \"" + rs.getTimestamp("S3LoadEnd") + "\","
			            		+ "\"RedShiftLoadStart\": \"" + rs.getTimestamp("RedShiftLoadStart") + "\","
			            		+ "\"RedShiftLoadEnd\": \"" + rs.getTimestamp("RedShiftLoadEnd") + "\","
			            		+ "\"Job Status\": \"" + rs.getString("job_status") + "\","
			            		+ "\"Subsidiary ID\": \"" + rs.getString("subsidiary_id") + "\""
			            		+ "}";
	            	         		
	            	}
	            	
	            	i++;
	            }
            
	            strData = strData + "] }";
	            finalStr = strHeader + strData;
	            finalStr = finalStr.replaceAll("null", "-");
	            finalStr = finalStr.replaceAll("(\\r|\\n|\\r\\n)+", "");
	            finalStr = finalStr.replaceAll("\\\\", "\\\\\\\\");
            } else {
            	finalStr = "No Data Present";
            }
            
        }
		
		catch (Exception e) 
        {
            System.out.println(e.getMessage());
        } finally {
        	
        	if(st != null){
        		st.close();
        	}
        	
        	if(con != null){
        		con.close();
        	}
        }
        
        return finalStr;
		
	}
	
	@GET
    @Path("retrivemsglog")
    @Produces("text/plain")
    @WebMethod(operationName = "retrivemsglog")
	
	public String retrivemsglog(@QueryParam("runid") String runID,@QueryParam("etl_date") String etlDate,@QueryParam("tabs") String tables) throws SQLException {
		
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection con = null;
        String strHeader = ""; 
        String strData = "";
        String finalStr = "";
        
        Properties prop = new Properties();
        InputStream propertiesInputStream = null;
		
        try 
        {
        
        	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			propertiesInputStream = classLoader.getResourceAsStream("com/tcs/webservices/dbprop.properties");
			prop.load(propertiesInputStream);
			propertiesInputStream.close();
			
        	Class.forName("com.mysql.jdbc.Driver");
        	con = DriverManager.getConnection(prop.getProperty("LogDBURL"), prop.getProperty("LogDBUID"), prop.getProperty("LogDBPwd"));
        	
        	String query = "select message_id, runid, message_desc, target_table, message_stage, message_type, message_timestamp, subsidiary_id from message_log a"
            		+ " where a.runid= coalesce(?,a.runid)"
            		+ "and DATE_FORMAT(a.message_timestamp,'%Y-%m-%d') = coalesce(?,DATE_FORMAT(a.message_timestamp,'%Y-%m-%d'))"
            		+ "and a.target_table = coalesce(?,a.target_table)";
        	
        
            st = con.prepareStatement(query);
            st.setString(1, (runID != null && runID.length() != 0  ? runID:null));
            st.setString(2, (etlDate != null && etlDate.length() != 0 ? etlDate:null));
            st.setString(3,(tables != null && tables.length() != 0 ? tables:null));
            //System.out.println(st.toString());
            rs = st.executeQuery();
            strHeader = "{"
            		+ "\"data\": [";
            
            int i = 0;
            if(rs.next()) {
            	rs.beforeFirst();            
	            while (rs.next()) 
	            {
	            	if(i == 0) {
	            		strData	= strData	+ "{ "
	            				+ "\"MessageId\":" + rs.getInt("message_id") + ","
	            				+ "\"RunId\":" + rs.getInt("runid") + ","
			            		+ "\"Message Desc\": \"" + rs.getString("message_desc").replaceAll("\"","") + "\","
			            		+ "\"Target Table\": \"" + rs.getString("target_table") + "\","
			            		+ "\"Message Stage\": \"" + rs.getString("message_stage") + "\","
			            		+ "\"Message Type\": \"" + rs.getString("message_type") + "\","
			            		+ "\"Message TimeStamp\": \"" + rs.getTimestamp("message_timestamp") + "\","
			            		+ "\"Subsidiary ID\": \"" + rs.getString("subsidiary_id") + "\""
			            		+ "}";
	            	} else {
	            		strData = strData + "," 
	            				+  "{ "
 	            				+ "\"MessageId\":" + rs.getInt("message_id") + ","
	            				+ "\"RunId\":" + rs.getInt("runid") + ","
			            		+ "\"Message Desc\": \"" + rs.getString("message_desc").replaceAll("\"","") + "\","
			            		+ "\"Target Table\": \"" + rs.getString("target_table") + "\","
			            		+ "\"Message Stage\": \"" + rs.getString("message_stage") + "\","
			            		+ "\"Message Type\": \"" + rs.getString("message_type") + "\","
			            		+ "\"Message TimeStamp\": \"" + rs.getTimestamp("message_timestamp") + "\","
			            		+ "\"Subsidiary ID\": \"" + rs.getString("subsidiary_id") + "\""
			            		+ "}";
	            	         		
	            	}
	            	
	            	i++;
	            }
            
	            strData = strData + "] }";
	            finalStr = strHeader + strData;
	            finalStr = finalStr.replaceAll("(\\r|\\n|\\r\\n)+", "");
	            finalStr = finalStr.replaceAll("null", "-");
	            finalStr = finalStr.replaceAll("\\\\", "\\\\\\\\");
            } else {
            	finalStr = "No Data Present";
            }
            
        }
        catch (Exception e) 
      {
          System.out.println(e.getMessage());
      }   
		finally {
      	
      	if(st != null){
      		st.close();
      	}
      	
      	if(con != null){
      		con.close();
      	}
      }
		return finalStr;
	}

	
	
@GET
@Path("retriveerrorlog")
@Produces("text/plain")
@WebMethod(operationName = "retriveerrorlog")

public String retriveerrorlog(@QueryParam("runid") String runID,@QueryParam("etl_date") String etlDate,@QueryParam("tabs") String tables) throws SQLException {
	
	ResultSet rs = null;
	PreparedStatement st = null;
	Connection con = null;
    String strHeader = ""; 
    String strData = "";
    String finalStr = "";
    String colHead = "";
    String colData = "";
    String colFinal = "";
    String rsCls = null;
    String rsURL = null;
    String rsUID = null;
    String rsPwd = null;
    String query=null;
    String query2=null;
    
    Properties prop = new Properties();
    InputStream propertiesInputStream = null;
	
    try 
    {
    
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		propertiesInputStream = classLoader.getResourceAsStream("com/tcs/webservices/dbprop.properties");
		prop.load(propertiesInputStream);
		propertiesInputStream.close();
		
    	
		
		Class.forName("com.mysql.jdbc.Driver");
    	con = DriverManager.getConnection(prop.getProperty("LogDBURL"), prop.getProperty("LogDBUID"), prop.getProperty("LogDBPwd"));
    	
    	
    	query = "SELECT *  FROM p2p_config WHERE property in ('RSCLASS','RSUID','RSDBURL','RSPWD')";
    	System.out.println(query);
    	st = con.prepareStatement(query);
    	rs = st.executeQuery();
    	
    	while(rs.next()){
    		
    		//System.out.println(rs.getString(2));
    		if(rs.getString(2).equals("RSCLASS")){
    			rsCls = EncryptionUtil.decrypt(rs.getString(3), "EncryptingKey$@!") ;
    			System.out.println(rsCls);
    		}
    		if(rs.getString(2).equals("RSUID")){
    			rsUID = EncryptionUtil.decrypt(rs.getString(3),"EncryptingKey$@!");
    			System.out.println(rsUID);    			
    		}
    		if(rs.getString(2).equals("RSDBURL")){
    			rsURL = EncryptionUtil.decrypt(rs.getString(3),"EncryptingKey$@!");
    			System.out.println(rsURL);
    		}
    		if(rs.getString(2).equals("RSPWD")){
    			rsPwd = EncryptionUtil.decrypt(rs.getString(3),"EncryptingKey$@!");
    			System.out.println(rsPwd);
    		}
    	
    	}
    	st.close();
    	con.close();
    	
    	
    	Class.forName(rsCls);
    	con = DriverManager.getConnection(rsURL,rsUID,rsPwd);
    	query2 = "Select * from dw."+tables+"_error" ;
    	System.out.println(query2);
    	st = con.prepareStatement(query2);
    	//st.setString(1,tables );
    	rs = st.executeQuery(); 
    	ResultSetMetaData rsmd = rs.getMetaData();
    	int cols = rsmd.getColumnCount();
    	colHead = "{"
				+ "\"columns\": [";
    	
    		
    			for (int i = 1; i <= cols; i++) {
    				if (i!=cols){
    				colData	= colData + "[\"" + rsmd.getColumnName(i) + "\"],";
    				}
    				else
    				{
    				colData	= colData + "[\"" + rsmd.getColumnName(i) + "\"]";
    				}
		            }
    				colData = colData + "],";
    				colFinal = colHead + colData;
    				colFinal = colFinal.replaceAll("_", " ");
    		
          strHeader ="\"data\": [";
    		int a=0;
    		while(rs.next()){
    			if(a==0)
    			{
    				strData= strData+"[";
    				for (int i = 1; i <= cols; i++) {
    					if (i!=cols){
    						strData	= strData + "\"" + rs.getString(i) + "\",";
    						}
    					else
    					{
    						strData	= strData + "\"" + rs.getString(i) + "\"";
    					}
    			}
    			strData =strData + "]";
    			}
    			else
    			{
    				strData= strData+",[";
        			for (int i = 1; i <= cols; i++) {
        				if (i!=cols){
        				strData	= strData + "\"" + rs.getString(i) + "\",";
        				}
        				else
        				{
        				strData	= strData + "\"" + rs.getString(i) + "\"";
        				}
        			}
        			strData =strData + "]";
    			}
    			a++;
    		}
    		strData = strData + "] }";
			finalStr = strHeader + strData;
			finalStr = colFinal + finalStr;
			finalStr = finalStr.replaceAll("(\\r|\\n|\\r\\n)+", "");
            finalStr = finalStr.replaceAll("null", "-");
            finalStr = finalStr.replaceAll("\\\\", "\\\\\\\\");

    }
    
    catch (Exception e) 
    {
        System.out.println(e.getMessage());
    }   
  	finally {
    	
    	if(st != null){
    		st.close();
    	}
    	
    	if(con != null){
    		con.close();
    	}
    }

	return finalStr;
}
}
