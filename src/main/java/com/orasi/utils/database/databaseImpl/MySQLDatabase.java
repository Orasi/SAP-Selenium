package com.orasi.utils.database.databaseImpl;

import com.orasi.utils.database.Database;

public class MySQLDatabase extends Database {
	public MySQLDatabase(String host, String port, String dbName){
		setDbDriver("com.mysql.jdbc.Driver");
		setDbConnectionString("jdbc:mysql://" + host + ":" + port + "/"+ dbName );		
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
