package com.orasi.utils.database.databaseImpl;

import com.orasi.utils.Constants;
import com.orasi.utils.database.Database;

public class OracleDatabase extends Database {

    public OracleDatabase(String tnsName) {
	setDbDriver("oracle.jdbc.driver.OracleDriver");
	setDbConnectionString("jdbc:oracle:thin:@" + tnsName.toUpperCase());
    }

    public OracleDatabase(String host, String port, String sid) {
	setDbDriver("oracle.jdbc.driver.OracleDriver");
	setDbConnectionString("jdbc:oracle:thin:@" + host + ":" + port + ":"
		+ sid);
    }

    @Override
    protected void setDbDriver(String driver) {
	super.strDriver = driver;
    }

    @Override
    protected void setDbConnectionString(String connection) {
	String tns = getClass()
		.getResource(Constants.TNSNAMES_PATH + "tnsnames.ora")
		.getPath().toString();
	tns = tns.substring(0, tns.lastIndexOf("/"));
	System.setProperty("oracle.net.tns_admin", tns);

	super.strConnectionString = connection;
    }

}
