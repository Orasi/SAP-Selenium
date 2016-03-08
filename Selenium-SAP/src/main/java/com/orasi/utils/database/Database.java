package com.orasi.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public abstract class Database {

	protected String strDriver = null;	
	private String strDbHost = null;
	private String strDbPort= null;
	private String strDbService= null;
	private String strDbUser= null;
	private String strDbPassword= null;
	protected String strConnectionString= null; 
	
	protected abstract void setDbDriver(String driver);
	
	protected String getDbDriver(){
		return strDriver;
	}
	
	protected void setDbHost(String host){
		strDbHost = host;
	}
	
	protected String getDbHost(){
		return strDbHost;
	}
	
	protected void setDbPort(String port){
		strDbPort = port;
	}
	
	protected String getDbPort(){
		return strDbPort;
	}
	
	protected void setDbService(String serivce){
		strDbService = serivce;
	}
	
	protected String getDbService(){
		return strDbService;
	}
	
	public void setDbUserName(String user){
		strDbUser = user;
	}
	
	protected String getDbUserName(){
		return strDbUser;
	}
	
	public void setDbPassword(String pass){
		strDbPassword = pass;
	}
	
	protected String getDbPassword(){
		return strDbPassword;
	}
	
	protected abstract void setDbConnectionString(String connection);
	
	protected String getDbConnectionString(){
		return strConnectionString;
	}
		
	public Object[][] getResultSet(String query) {
	    loadDriver();
		
	    Connection connection = null;
		try {
			
			connection = DriverManager.getConnection(getDbConnectionString(), getDbUserName(), getDbPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		ResultSet rs = (runQuery(connection, query));
	    try {
			return extract(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	 
	
	private void loadDriver(){			
		try{
			  Class.forName(getDbDriver());
		} catch(ClassNotFoundException cnfe) {
		      System.err.println("Error loading driver: " + cnfe);
		}		 
	}

	private static ResultSet runQuery(Connection connection, String query) {
	    try {
	      Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	      ResultSet resultSet = statement.executeQuery(query);
	
	      return(resultSet);
	      
	    } catch(SQLException sqle) {
	      System.err.println("Error executing query: " + sqle);
	      return(null);	     
	    } 
	}
	
    /** 
     * Returns an ArrayList of ArrayLists of Strings extracted from a 
     * ResultSet retrieved from the database. 
     * @param resultSet ResultSet to extract Strings from 
     * @return an ArrayList of ArrayLists of Strings 
     * @throws SQLException if an SQL exception occurs 
     */  
    public static Object[][] extract(ResultSet resultSet)  
    throws SQLException {  
        // get row and column count
        int rowCount = 0;
        try {
            resultSet.last();
            rowCount = resultSet.getRow();
            resultSet.beforeFirst();
        }
        catch(Exception ex) {
        	rowCount = 0;
        }
       
        int columnCount = resultSet.getMetaData().getColumnCount();  
      
        Object[][] table = new String[rowCount+1][columnCount];  
        ResultSetMetaData rsmd = resultSet.getMetaData();        
        
        for(int rowNum = 0; rowNum <= rowCount; rowNum++) {  
            for(int colNum = 0, rsColumn = 1; colNum < columnCount; colNum++, rsColumn++){
            	
            	if(rowNum == 0){
            		table[rowNum][colNum] = resultSet.getMetaData().getColumnName(rsColumn);   
            	}else if(resultSet.getString(colNum+1) == null){
            		//System.out.println("null");
            		table[rowNum][colNum] = "NULL";
            	}else{
            		try{
            			
	            		//
	            		switch (rsmd.getColumnType(rsColumn)){
	            		
	            		case Types.DATE:
	            			table[rowNum][colNum] = String.valueOf(resultSet.getTimestamp(rsColumn));
	            			break;
	
	            		case Types.TIMESTAMP:
	            			table[rowNum][colNum] = String.valueOf(resultSet.getTimestamp(rsColumn));
	            			break;
	            			
	            		case Types.TIME:
	            			table[rowNum][colNum] = resultSet.getTime(rsColumn);
	            			break;	            				            	
	            			
	            		default:
	            			table[rowNum][colNum] = resultSet.getString(rsColumn).intern();
	            			break;
	            		}
            		}catch (Exception e){
            			table[rowNum][colNum] = resultSet.getString(rsColumn).intern();
            		}
            		//System.out.println(resultSet.getString(c).intern());
            		//table[rowNum][colNum] = resultSet.getString(colNum+1).intern();              
            	}
           }
            resultSet.next();
        }  
        return table;  
    }  
	
}
