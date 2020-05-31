package com.searchEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.searchEngine.serverInfo;
import com.searchEngine.data;

public class data {

	public ArrayList<String> takeWords(String text){
		
	        ArrayList<String> finallist = new ArrayList<>(Arrays.asList(text.split("\\s+")));
	        
		return finallist;
	}
	
	
	
	public String queryMaker (ArrayList<String> list) {
		
		String h = "";
        
		for (int x = 0; x < list.size(); x++) {
                    h += " + * ";
                    h+=list.get(x);
                    h+=   " *";
		}
		
		return h;
	}
	
	public String allSearch(String text) throws Exception {
		String thisIsIt = "<style>\r\n" + 
				"table {\r\n" + 
				"  font-family: arial, sans-serif;\r\n" + 
				"  border-collapse: collapse;\r\n" + 
				"  width: 100%;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"td, th {\r\n" + 
				"  border: 1px solid #dddddd;\r\n" + 
				"  text-align: left;\r\n" + 
				"  padding: 8px;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"tr:nth-child(even) {\r\n" + 
				"  background-color: #dddddd;\r\n" + 
				"}\r\n" + 
				"</style>";
		
		
		
		
		
		
		
		String txt1 = search1(text);
		String txt2 = search2(text);
		String txt3 = search3(text);
		
		if (txt1.length() > txt2.length() && txt1.length() > txt3.length()) {

			if(txt2.length()>txt3.length())
			{
				thisIsIt += txt1;
				thisIsIt += txt2;
				thisIsIt += txt3;
			}
			else{
				thisIsIt += txt1;
				thisIsIt += txt3;
				thisIsIt += txt2;
			}
		}

		if (txt2.length() > txt1.length() && txt2.length() > txt3.length()) {
			if(txt1.length()>txt3.length())
			{
				thisIsIt += txt2;
				thisIsIt += txt1;
				thisIsIt += txt3;
			}
			else{
				thisIsIt += txt2;
				thisIsIt += txt3;
				thisIsIt += txt1;
			}
		}    

		if (txt3.length() > txt2.length() && txt3.length() > txt1.length()) {
			if(txt1.length()>txt2.length())
			{
				thisIsIt += txt3;
				thisIsIt += txt1;
				thisIsIt += txt2;
			}
			else{
				thisIsIt += txt3;
				thisIsIt += txt2;
				thisIsIt += txt1;
			}
		}
		
		
		
		return thisIsIt;
	}
	
	
public String search1(String text) throws Exception{
		
		String txt1 = "";
		
		String query = "(SELECT * FROM list.bearbase where match(`Notes`) against(?)\r\n" + 
			"limit 1)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.bearbase where match(`origin`) against(?)\r\n" + 
			"limit 1)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.bearbase where match(`character`) against(?)\r\n" + 
			"limit 1)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.bearbase where match(`creator`) against(?)\r\n" + 
			"limit 1)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.bearbase where match(`Notes`) against(?)\r\n" + 
			"limit 50)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.bearbase where match(`origin`) against(?)\r\n" + 
			"limit 50)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.bearbase where match(`character`) against(?)\r\n" + 
			"limit 50)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.bearbase where match(`creator`) against(?)\r\n" + 
			"limit 50)";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(serverInfo.getUrl(), serverInfo.getName1(), serverInfo.getPass());
			PreparedStatement st = con.prepareStatement(query);
	
			st.setString(1, text);
			st.setString(2, text);
			st.setString(3, text);
			st.setString(4, text);
			st.setString(5, queryMaker(takeWords(text)));
			st.setString(6, queryMaker(takeWords(text)));
			st.setString(7, queryMaker(takeWords(text)));
			st.setString(8, queryMaker(takeWords(text)));
			
			ResultSet rs = st.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			
			txt1 += "<h2>BearBase</h2>\r\n" + 
					"\r\n" + 
					"<table>\r\n" + 
					"  <tr>";
			
			for (int i = 1; i <= columnCount ; i++){  
				String col_name = metaData.getColumnName(i);  
				txt1 += "<th>"+ col_name + "</th>"; 
				}
				
			txt1 += "</tr>";
			
			while(rs.next()) {
				txt1 += "<tr>";
				
				for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
					Object object = rs.getObject(columnIndex);
		        
				        if(object==null) {
				        	txt1 += "<td> Null </td>";
				        }
				        else {
				        	txt1 += "<td>" + object.toString() +"</td>";
				        }
			       
				}
				 txt1 += "</tr>";
			}
			txt1 += "</table>";
		st.close();
		con.close();
		}
		catch (SQLException e) {
			System.out.println("error222222");
		}
		return txt1;
	}

public String search2(String text) throws Exception{
	
	String txt1 = "";
	
	String query = "(SELECT * FROM list.ikea_names where match(`name`) against(?)\r\n" + 
			"limit 3)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.ikea_names where match(`description`) against(?)\r\n" + 
			"limit 3)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.ikea_names where match(`name`) against(?)\r\n" + 
			"limit 50)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.ikea_names where match(`description`) against(?)\r\n" + 
			"limit 50)";
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(serverInfo.getUrl(), serverInfo.getName1(), serverInfo.getPass());
		PreparedStatement st = con.prepareStatement(query);

		st.setString(1, text);
		st.setString(2, text);
		st.setString(3, queryMaker(takeWords(text)));
		st.setString(4, queryMaker(takeWords(text)));
		
		ResultSet rs = st.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		
		txt1 += "<h2>Ikea Items</h2>\r\n" + 
				"\r\n" + 
				"<table>\r\n" + 
				"  <tr>";
		
		for (int i = 1; i <= columnCount ; i++){  
			String col_name = metaData.getColumnName(i);  
			txt1 += "<th>"+ col_name + "</th>"; 
			}
			
		txt1 += "</tr>";
		
		while(rs.next()) {
			txt1 += "<tr>";
			
			for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				Object object = rs.getObject(columnIndex);
	        
			        if(object==null) {
			        	txt1 += "<td> Null </td>";
			        }
			        else {
			        	txt1 += "<td>" + object.toString() +"</td>";
			        }
		       
			}
			 txt1 += "</tr>";
		}
		txt1 += "</table>";
	st.close();
	con.close();
	}
	catch (SQLException e) {
		System.out.println("error222222");
	}
	return txt1;
}

public String search3(String text) throws Exception{
	
	String txt1 = "";
	
	String query = "(SELECT * FROM list.masterscplist where match(`title`) against(?)\r\n" + 
			"limit 20)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.masterscplist where match(`Classification`) against(?)\r\n" + 
			"limit 20)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.masterscplist where match(`Type`) against(?)\r\n" + 
			"limit 20)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.masterscplist where match(`Location_Notes`) against(?)\r\n" + 
			"limit 20)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.masterscplist where match(`Author`) against(?)\r\n" + 
			"limit 20)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.masterscplist where match(`title`) against(?)\r\n" + 
			"limit 50)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.masterscplist where match(`Classification`) against(?)\r\n" + 
			"limit 50)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.masterscplist where match(`Type`) against(?)\r\n" + 
			"limit 50)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.masterscplist where match(`Location_Notes`) against(?)\r\n" + 
			"limit 50)\r\n" + 
			"union\r\n" + 
			"(SELECT * FROM list.masterscplist where match(`Author`) against(?)\r\n" + 
			"limit 50)\r\n";
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(serverInfo.getUrl(), serverInfo.getName1(), serverInfo.getPass());
		PreparedStatement st = con.prepareStatement(query);

		st.setString(1, text);
		st.setString(2, text);
		st.setString(3, text);
		st.setString(4, text);
		st.setString(5, text);
		st.setString(6, queryMaker(takeWords(text)));
		st.setString(7, queryMaker(takeWords(text)));
		st.setString(8, queryMaker(takeWords(text)));
		st.setString(9, queryMaker(takeWords(text)));
		st.setString(10, queryMaker(takeWords(text)));
		
		ResultSet rs = st.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		
		txt1 += "<h2>Masterscplist</h2>\r\n" + 
				"\r\n" + 
				"<table>\r\n" + 
				"  <tr>";
		
		for (int i = 1; i <= columnCount ; i++){  
			String col_name = metaData.getColumnName(i);  
			txt1 += "<th>"+ col_name + "</th>"; 
			}
			
		txt1 += "</tr>";
		
		while(rs.next()) {
			txt1 += "<tr>";
			
			for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				Object object = rs.getObject(columnIndex);
	        
			        if(object==null) {
			        	txt1 += "<td> Null </td>";
			        }
			        else {
			        	txt1 += "<td>" + object.toString() +"</td>";
			        }
		       
			}
			 txt1 += "</tr>";
		}
		txt1 += "</table>";
	st.close();
	con.close();
	}
	catch (SQLException e) {
		System.out.println("error222222");
	}
	return txt1;
}
}



