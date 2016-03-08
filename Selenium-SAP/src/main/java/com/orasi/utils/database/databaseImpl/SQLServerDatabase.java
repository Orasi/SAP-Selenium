package com.orasi.utils.database.databaseImpl;

import com.orasi.utils.database.Database;

public class SQLServerDatabase extends Database {
	public SQLServerDatabase(String host, String port, String dbName){
		setDbDriver("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		setDbConnectionString("jdbc:microsoft:sqlserver://" + host + ":" + port + ";DatabaseName="+ dbName);
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
