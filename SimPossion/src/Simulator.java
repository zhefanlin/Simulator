import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Simulator {

	public static void main(String[] args) throws IOException, SQLException,
			ClassNotFoundException, InterruptedException {

//		GenerateCSV gen = new GenerateCSV();
//
//		 String query1 =
//		 "SELECT DISTINCT DeviceID FROM datamart.GenreViews_Dvc_DW LIMIT 100;";
//		
//		 String query2 = "SELECT IPAddr FROM simulator.SimIP2DeviceID;";
//		
//		 gen.SimIP2DeviceIDcsv(acc.selectRecords(query1,"DeviceID"));
//		
//		 String query3 =
//		 "CREATE TABLE SimIP2DeviceID (IPAddr varchar(15), DeviceID varchar(12));";
//		 String query4 =
//		 "load data local infile '/Users/Zhefan/Dropbox/SimIP2DeviceID.csv' into table SimIP2DeviceID fields terminated by ',' lines terminated by '\r\n';";
//		
//		
//		 acc.updateTable(query3);
//		 acc.updateTable(query4);
//		
//		 gen.SimPoissonRatescsv(acc.selectRecords(query2,"IPAddr"));
//		
//		 String query5 =
//		 "CREATE TABLE SimPoissonRates (IPAddr varchar(15), ProgGenre varchar(255), DayPart int(11), Week int(11), PoissonRate float);";
//		 String query6 =
//		 "load data local infile '/Users/Zhefan/Dropbox/SimPoissonRates.csv' into table SimPoissonRates fields terminated by ',' lines terminated by '\r\n';";
//
//		 acc.updateTable(query5);
//		 acc.updateTable(query6);
		
		

		
		
		TimerTask service = new Task();

		Timer time=new Timer();
		System.out.println("Service Start!");
		time.schedule(service,10000,30000);
		  
		  
		
	}
}

