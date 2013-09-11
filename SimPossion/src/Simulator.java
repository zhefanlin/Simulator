import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public class Simulator {

	public static void main(String[] args) throws IOException, SQLException,
			ClassNotFoundException {

		// TimeClass get_spec = new TimeClass();

		// generatePosissonCSVfile output = new generatePosissonCSVfile();

		// output.outputCSV();

		int DayPart = 0;
		int Week = 0;
		String BidRequest = "";
		
		FileWriter fout = new FileWriter(
				"/Users/Zhefan/Dropbox/BidRequest.csv");

		for (Week = 14; Week < 16; Week++)
			for (DayPart = 1; DayPart < 12; DayPart++) {
				AccessDatabase result = new AccessDatabase();
				String query = result.getPoissonRate(DayPart, Week);
				BidRequest = result.getBidRequest(query);
				
				fout.write(BidRequest+"\r\n");
				
				//System.out.println(BidRequest);
			}
		
		fout.close();
		System.out.println("Done");
	}

}
