package com.orasi.utils.dataHelpers;
import java.util.HashMap;
import java.util.Map;

/**
* A simple converter to get state names or codes. Also contains Canadian and other values
* @author Justin Phlegar
* @version 12/07/2015 Justin Phlegar - original
*/
public class StateMapper {
	//Mapping containers
	private static Map<String, String> stateNameToCode = new HashMap<String, String>();
	private static Map<String, String> stateCodeToName = new HashMap<String, String>();
	

	/**
	* Get the state code/abbreviation by the state/province name
	* @author Justin Phlegar
	* @version 12/07/2015 Justin Phlegar - original
	* @param name - State or province name to look up and get code for
	* @return State or Province code based on name sent in
	*/
	public static String getStateCode(String name){
		populateCodeMap();
		return stateNameToCode.get(name.toLowerCase());
	}
	
	/**
	* Get the state/province name by the state code/abbreviation
	* @author Justin Phlegar
	* @version 12/07/2015 Justin Phlegar - original
	* @param code - State or province code/abbreviation to look up and get name for
	* @return State or Province name based on code sent in
	*/
	public static String getStateName(String code){
		populateNameMap();
		return stateCodeToName.get(code.toUpperCase());
	}
	
	
	/**
	* @summary Populate map for name to code conversion
	* @author Justin Phlegar
	* @version 12/07/2015 Justin Phlegar - original
	*/
	private static void populateCodeMap(){
		stateNameToCode.put("alabama","AL");
		stateNameToCode.put("alaska","AK");
		stateNameToCode.put("alberta","AB");
		stateNameToCode.put("american samoa","AS");
		stateNameToCode.put("arizona","AZ");
		stateNameToCode.put("arkansas","AR");
		stateNameToCode.put("armed forces (ae)","AE");
		stateNameToCode.put("armed forces americas","AA");
		stateNameToCode.put("armed forces pacific","AP");
		stateNameToCode.put("british columbia","BC");
		stateNameToCode.put("california","CA");
		stateNameToCode.put("colorado","CO");
		stateNameToCode.put("connecticut","CT");
		stateNameToCode.put("delaware","DE");
		stateNameToCode.put("district of columbia","DC");
		stateNameToCode.put("florida","FL");
		stateNameToCode.put("georgia","GA");
		stateNameToCode.put("guam","GU");
		stateNameToCode.put("hawaii","HI");
		stateNameToCode.put("idaho","ID");
		stateNameToCode.put("illinois","IL");
		stateNameToCode.put("indiana","IN");
		stateNameToCode.put("iowa","IA");
		stateNameToCode.put("kansas","KS");
		stateNameToCode.put("kentucky","KY");
		stateNameToCode.put("louisiana","LA");
		stateNameToCode.put("maine","ME");
		stateNameToCode.put("manitoba","MB");
		stateNameToCode.put("maryland","MD");
		stateNameToCode.put("massachusetts","MA");
		stateNameToCode.put("michigan","MI");
		stateNameToCode.put("minnesota","MN");
		stateNameToCode.put("mississippi","MS");
		stateNameToCode.put("missouri","MO");
		stateNameToCode.put("montana","MT");
		stateNameToCode.put("nebraska","NE");
		stateNameToCode.put("nevada","NV");
		stateNameToCode.put("new brunswick","NB");
		stateNameToCode.put("new hampshire","NH");
		stateNameToCode.put("new jersey","NJ");
		stateNameToCode.put("new mexico","NM");
		stateNameToCode.put("new york","NY");
		stateNameToCode.put("newfoundland","NF");
		stateNameToCode.put("north carolina","NC");
		stateNameToCode.put("north dakota","ND");
		stateNameToCode.put("northwest territories","NT");
		stateNameToCode.put("nova scotia","NS");
		stateNameToCode.put("nunavut","NU");
		stateNameToCode.put("ohio","OH");
		stateNameToCode.put("oklahoma","OK");
		stateNameToCode.put("ontario","ON");
		stateNameToCode.put("oregon","OR");
		stateNameToCode.put("pennsylvania","PA");
		stateNameToCode.put("prince Edward Island","PE");
		stateNameToCode.put("puerto Rico","PR");
		stateNameToCode.put("quebec","PQ");
		stateNameToCode.put("rhode island","RI");
		stateNameToCode.put("saskatchewan","SK");
		stateNameToCode.put("south carolina","SC");
		stateNameToCode.put("south dakota","SD");
		stateNameToCode.put("tennessee","TN");
		stateNameToCode.put("texas","TX");
		stateNameToCode.put("utah","UT");
		stateNameToCode.put("vermont","VT");
		stateNameToCode.put("virgin islands","VI");
		stateNameToCode.put("virginia","VA");
		stateNameToCode.put("washington","WA");
		stateNameToCode.put("west cirginia","WV");
		stateNameToCode.put("wisconsin","WI");
		stateNameToCode.put("wyoming","WY");
		stateNameToCode.put("yukon territory","YT");
	}

	/**
	* @summary Populate map for code to name conversion
	* @author Justin Phlegar
	* @version 12/07/2015 Justin Phlegar - original
	*/
	private static void populateNameMap(){
		stateCodeToName.put("AL", "Alabama");
	    stateCodeToName.put("AK", "Alaska");
	    stateCodeToName.put("AB", "Alberta");
	    stateCodeToName.put("AZ", "Arizona");
	    stateCodeToName.put("AR", "Arkansas");
	    stateCodeToName.put("BC", "British Columbia");
	    stateCodeToName.put("CA", "California");
	    stateCodeToName.put("CO", "Colorado");
	    stateCodeToName.put("CT", "Connecticut");
	    stateCodeToName.put("DE", "Delaware");
	    stateCodeToName.put("DC", "District Of Columbia");
	    stateCodeToName.put("FL", "Florida");
	    stateCodeToName.put("GA", "Georgia");
	    stateCodeToName.put("GU", "Guam");
	    stateCodeToName.put("HI", "Hawaii");
	    stateCodeToName.put("ID", "Idaho");
	    stateCodeToName.put("IL", "Illinois");
	    stateCodeToName.put("IN", "Indiana");
	    stateCodeToName.put("IA", "Iowa");
	    stateCodeToName.put("KS", "Kansas");
	    stateCodeToName.put("KY", "Kentucky");
	    stateCodeToName.put("LA", "Louisiana");
	    stateCodeToName.put("ME", "Maine");
	    stateCodeToName.put("MB", "Manitoba");
	    stateCodeToName.put("MD", "Maryland");
	    stateCodeToName.put("MA", "Massachusetts");
	    stateCodeToName.put("MI", "Michigan");
	    stateCodeToName.put("MN", "Minnesota");
	    stateCodeToName.put("MS", "Mississippi");
	    stateCodeToName.put("MO", "Missouri");
	    stateCodeToName.put("MT", "Montana");
	    stateCodeToName.put("NE", "Nebraska");
	    stateCodeToName.put("NV", "Nevada");
	    stateCodeToName.put("NB", "New Brunswick");
	    stateCodeToName.put("NH", "New Hampshire");
	    stateCodeToName.put("NJ", "New Jersey");
	    stateCodeToName.put("NM", "New Mexico");
	    stateCodeToName.put("NY", "New York");
	    stateCodeToName.put("NF", "Newfoundland");
	    stateCodeToName.put("NC", "North Carolina");
	    stateCodeToName.put("ND", "North Dakota");
	    stateCodeToName.put("NT", "Northwest Territories");
	    stateCodeToName.put("NS", "Nova Scotia");
	    stateCodeToName.put("NU", "Nunavut");
	    stateCodeToName.put("OH", "Ohio");
	    stateCodeToName.put("OK", "Oklahoma");
	    stateCodeToName.put("ON", "Ontario");
	    stateCodeToName.put("OR", "Oregon");
	    stateCodeToName.put("PA", "Pennsylvania");
	    stateCodeToName.put("PE", "Prince Edward Island");
	    stateCodeToName.put("PR", "Puerto Rico");
	    stateCodeToName.put("QC", "Quebec");
	    stateCodeToName.put("RI", "Rhode Island");
	    stateCodeToName.put("SK", "Saskatchewan");
	    stateCodeToName.put("SC", "South Carolina");
	    stateCodeToName.put("SD", "South Dakota");
	    stateCodeToName.put("TN", "Tennessee");
	    stateCodeToName.put("TX", "Texas");
	    stateCodeToName.put("UT", "Utah");
	    stateCodeToName.put("VT", "Vermont");
	    stateCodeToName.put("VI", "Virgin Islands");
	    stateCodeToName.put("VA", "Virginia");
	    stateCodeToName.put("WA", "Washington");
	    stateCodeToName.put("WV", "West Virginia");
	    stateCodeToName.put("WI", "Wisconsin");
	    stateCodeToName.put("WY", "Wyoming");
	    stateCodeToName.put("YT", "Yukon Territory");
	}
}
