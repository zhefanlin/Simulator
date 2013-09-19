import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class Simulator {

	public static void main(String[] args) throws IOException, SQLException,
			ClassNotFoundException, InterruptedException {

		AccessDatabase acc = new AccessDatabase();

		 GenerateCSV gen = new GenerateCSV();
		//
		//
		// String query1 =
		// "SELECT DISTINCT DeviceID FROM datamart.GenreViews_Dvc_DW LIMIT 100;";
		//
		String query2 = "SELECT IPAddr FROM simulator.SimIP2DeviceID;";
		//
		// gen.SimIP2DeviceIDcsv(acc.selectRecords(query1,"DeviceID"));
		//
		// String query3 =
		// "CREATE TABLE SimIP2DeviceID (IPAddr varchar(15), DeviceID varchar(12));";
		// String query4 =
		// "load data local infile '/Users/Zhefan/Dropbox/SimIP2DeviceID.csv' into table SimIP2DeviceID fields terminated by ',' lines terminated by '\r\n';";
		//
		//
		// acc.updateTable(query3);
		// acc.updateTable(query4);
		//
		gen.SimPoissonRatescsv(acc.selectRecords(query2,"IPAddr"));
		//
		String query5 =
		 "CREATE TABLE SimPoissonRates (IPAddr varchar(15), ProgGenre varchar(255), DayPart int(11), Week int(11), PoissonRate float);";
		 String query6 =
		 "load data local infile '/Users/Zhefan/Dropbox/SimPoissonRates.csv' into table SimPoissonRates fields terminated by ',' lines terminated by '\r\n';";
		
		 acc.updateTable(query5);
		 acc.updateTable(query6);

		String BidRequest = "";

		FileWriter fout = new FileWriter("/Users/Zhefan/Dropbox/BidRequest.csv");

		CalculateProbability cal = new CalculateProbability();
		while (true) {
			// Note -- lambda is 5 seconds, convert to milleseconds
			long interval = (long) cal.poissonRandomInterarrivalDelay(5.0 * 1000.0);
			try {
				Thread.sleep(interval);
				//for(int Week = 14;Week<16; Week++)
					//for(int DayPart = 1;DayPart<12;DayPart++){
					Random random = new Random();
				int DayPart = random.nextInt(11) % (11 - 1 + 1) + 1;
				int Week = random.nextInt(15) % (15 - 14 + 1) + 14;
						
						System.out.println(DayPart+"  "+Week);
				BidRequest = acc.getBidRequest(DayPart, Week);

				fout.write("TimeStamp:DayPart " + DayPart + ", Week " + Week
						+ ", BidRequestJSON: " + BidRequest + "\r\n");

				System.out.println(BidRequest);
//					}
				fout.close();

				System.out.println("Done");

			} finally {
				System.out.println("Interrupted");
			}

		}
		

	}

}
