import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AccessDatabase{

	public AccessDatabase() {

	}

	String hostname = "psydmdb.csjq2kdwwxxb.us-west-2.rds.amazonaws.com";
	String port = "3306";
	String userName = "salil";
	String password = "fonatix";
	String dbName = "simulator";
	String dbName2 = "datamart";

	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://" + hostname + ":" + port + "/";
	
	public ArrayList<String> selectRecords(String query, String columnName) throws SQLException,
	ClassNotFoundException {
				
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, userName, password);

		Statement sql_statement = conn.createStatement();
		ResultSet result = sql_statement.executeQuery(query);
		
		ArrayList<String> list = new ArrayList<String>(100); 
		
		while (result.next()) {
		list.add(result.getString(columnName));
		}
		conn.close();
		return list;
	}
	
	
	public void updateTable(String query) throws SQLException,
	ClassNotFoundException {
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url + dbName, userName, password);
		
		PreparedStatement pstmt = conn.prepareStatement(query);	
		pstmt.executeUpdate();
	}
	

	public float getNhd(String DeviceID, String ProgGenre) throws SQLException,
			ClassNotFoundException {
		float views = 0;
		float Nhd = 0;

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url + dbName2, userName, password);

		Statement sql_statement = conn.createStatement();
		ResultSet result = sql_statement
				.executeQuery("SELECT SUM(TotalGenreViews) FROM datamart.GenreViews_Dvc_DW"
						+ " WHERE "
						+ "DeviceID="
						+ "'"
						+ DeviceID
						+ "'"
						+ " AND "
						+ "ProgGenre="
						+ "'"
						+ ProgGenre
						+ "'"
						+ " AND " + "DayPart !=0" + ";");

		while (result.next()) {
			views = result.getInt("SUM(TotalGenreViews)");
		}
		Nhd = views/2;
		sql_statement.close();
		conn.close();
		return Nhd;
	}

	public float getEGRP(String ProgGenre, int Week) throws SQLException,
			ClassNotFoundException {
		
		float views = 0;
		float EGRP = 0;
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url + dbName2, userName, password);

		Statement sql_statement = conn.createStatement();
		ResultSet result = sql_statement.executeQuery("SELECT SUM(TotalGenreViews) FROM datamart.GenreViews_Dvc_DW JOIN  simulator.SimIP2DeviceID ON simulator.SimIP2DeviceID.DeviceID = datamart.GenreViews_Dvc_DW.DeviceID"
						+ " WHERE "
						+ "datamart.GenreViews_Dvc_DW.WeekPart= "
						+ Week
						+ " AND "
						+ "datamart.GenreViews_Dvc_DW.ProgGenre="
						+ "'" + ProgGenre + "'" + " AND " + "datamart.GenreViews_Dvc_DW.DayPart !=0" + ";");

		while (result.next()) {
			views = result.getInt("SUM(TotalGenreViews)");	
		}
		
		EGRP = views;
		sql_statement.close();
		conn.close();
		return EGRP;
	}

	@SuppressWarnings("unchecked")
	public String getBidRequest(int DayPart, int Week) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub

		String str = "";
		int imp_id = 0;
		
		JSONObject BidRequest = new JSONObject();
		JSONArray impArray = new JSONArray();

		JSONObject banner = new JSONObject();
		banner.put("w", 300);
		banner.put("h", 200);
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url + dbName, userName, password);

		Statement sql_statement = conn.createStatement();
		ResultSet result = sql_statement.executeQuery("SELECT * FROM simulator.SimPoissonRates" + " WHERE " + "DayPart=" + DayPart + " AND " + "Week=" + Week + ";");

		while (result.next()) {
			String IPAddr = result.getString("IPAddr");
			String ProgGenre = result.getString("ProgGenre");	
			float lambda = result.getFloat("PoissonRate");
			
			CalculateProbability cal = new CalculateProbability();
			//int k = cal.getRandomK();
			for(int k =1;k<11;k++){
				double p = cal.Probability(lambda, k);
			
				if (p >0.3) {
					imp_id++;

					JSONObject imp = new JSONObject();
					imp.put("id", imp_id);
					imp.put("banner", banner);
					imp.put("IPAddr", IPAddr);
					imp.put("ProgGenre", ProgGenre);

					impArray.add(imp);
				}
				//System.out.println(IPAddr+"  "+ProgGenre+"  "+DayPart + "  " + Week + "  " + lambda+"  "+k+"  "+p);			
			}
		}

		BidRequest.put("id", 1);
		BidRequest.put("imp", impArray);
		str = BidRequest.toString();

		if (imp_id == 0) {
			str = "null";
		}
		sql_statement.close();
		conn.close();
		return str;
	}
}
