import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AccessDatabase {

	public AccessDatabase() {

	}

	String hostname = "psydmdb.csjq2kdwwxxb.us-west-2.rds.amazonaws.com";
	String port = "3306";
	String userName = "salil";
	String password = "fonatix";
	String dbName = "simulator";

	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://" + hostname + ":" + port + "/";

	// String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName
	// + "?user=" + userName + "&password=" + password;

	public String getPoissonRate(int DayPart, int Week) throws SQLException {

		String query = "SELECT * FROM simulator.SimPoissonRates" + " " + " "
				+ "WHERE" + " " + "DayPart=" + DayPart + " " + "AND" + " "
				+ "Week=" + Week + ";";
		// String result = getProbability(query);

		return query;

	}

	public String getBidRequest(String query) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub

		String str = "";
		int imp_id = 0;

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url + dbName, userName,
				password);

		Statement sql_statement = conn.createStatement();
		ResultSet result = sql_statement.executeQuery(query);

		JSONObject BidRequest = new JSONObject();
		JSONArray impArray = new JSONArray();

		while (result.next()) {
			String IPAddr = result.getString("IPAddr");
			String ProgGenre = result.getString("ProgGenre");
			int DayPart = result.getInt("DayPart");
			int Week = result.getInt("Week");
			float Lamda = result.getFloat("PoissonRate");

			CalculateProbability cal = new CalculateProbability();
			double p = cal.Probability(Lamda, 1);

			JSONObject banner = new JSONObject();
			banner.put("w", 300);
			banner.put("h", 200);

			if (p > 0.3) {
				imp_id++;

				JSONObject imp = new JSONObject();
				imp.put("id", imp_id);
				imp.put("banner", banner);
				imp.put("IPAddr", IPAddr);
				imp.put("ProgGenre", ProgGenre);

				impArray.add(imp);

				// System.out.println(IPAddr+"  "+ProgGenre+"  "+DayPart + "  "
				// + Week + "  " + Lamda+"  "+p);
			}

		}
		
		BidRequest.put("id", 1);
		BidRequest.put("imp", impArray);
		str = BidRequest.toString();
		// System.out.println(str);

		sql_statement.close();
		conn.close();
		
		if(imp_id==0){str ="null";
		
		}
		return str;
	}

}
