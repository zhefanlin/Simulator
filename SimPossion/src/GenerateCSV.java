import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenerateCSV {

	public GenerateCSV() {

	}

	public void SimIP2DeviceIDcsv(ArrayList<String> DeviceID)
			throws IOException {
		GenerateIP IP = new GenerateIP();
		FileWriter fout = new FileWriter(
				"/Users/Zhefan/Dropbox/SimIP2DeviceID.csv");

		for (int i = 0; i < DeviceID.size(); i++) {
			fout.write(IP.IPAddress() + "," + DeviceID.get(i) + "\r\n");
		}
		fout.close();
	}

	public void SimPoissonRatescsv(ArrayList<String> IPAddr)
			throws IOException, ClassNotFoundException, SQLException {

		FileWriter fout = new FileWriter(
				"/Users/Zhefan/Dropbox/SimPoissonRates.csv");

		String query = "SELECT DISTINCT ProgGenre FROM datamart.GenreViews_Dvc_DW;";
		AccessDatabase acc = new AccessDatabase();
		ArrayList<String> genre = acc.selectRecords(query, "ProgGenre");
		
		float EGRP = 0;
		float Nhd = 0;
		float lambda = 0;

		for (int i = 0; i < IPAddr.size(); i++) {
			String ip = IPAddr.get(i);
			
			String query2 = "SELECT DeviceID FROM simulator.SimIP2DeviceID WHERE IPAddr=" + "'" + ip+ "'" + ";";
			ArrayList<String> DeviceID = acc.selectRecords(query2, "DeviceID");
			
			for (int j = 0; j < genre.size(); j++) {
				for (int k = 14; k < 16; k++) {					
					Nhd = acc.getNhd(DeviceID.get(0), genre.get(j));
					if (Nhd == 0) {
						lambda = 0;
					}
					else{
						EGRP = acc.getEGRP(genre.get(j),k);
						lambda = Nhd/EGRP;
					}
				
					for (int l = 1; l < 12; l++) {
						fout.write(ip + "," + genre.get(j) + "," + l + "," + k + ","
								+ lambda + "\r\n");
						
						System.out.println(ip + ",  " + genre.get(j) + ",  " + l + ",  " + k + ",  "
								+Nhd+",  "+EGRP+",  "+lambda);
					}
				}
			}

		}
		fout.close();

	}

}
