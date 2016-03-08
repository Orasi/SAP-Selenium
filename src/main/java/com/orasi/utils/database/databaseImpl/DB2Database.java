package com.orasi.utils.database.databaseImpl;

import com.orasi.utils.database.Database;

public class DB2Database extends Database {
	public DB2Database(String host, String port, String dbName){
		setDbDriver("COM.ibm.db2.jdbc.app.DB2Driver");
		setDbConnectionString("jdbc:db2://" + host + ":" + port + "/"+ dbName);
	}

	@Override
	protected void setDbDriver(String driver) {
		super.strDriver = driver;	
	}

	@Override
	protected void setDbConnectionString(String connection) {
		super.strConnectionString = connection;
	}
	
}
