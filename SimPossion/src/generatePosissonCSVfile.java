import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;



public class generatePosissonCSVfile {
	
	public generatePosissonCSVfile(){
		
	}
	
	public void outputCSV() throws IOException {
		generateIP IPClass = new generateIP();

		FileWriter fout = new FileWriter(
				"/Users/Zhefan/Dropbox/SimPoissonRates.csv");
		FileWriter fout2 = new FileWriter("/Users/Zhefan/Dropbox/IPAddr.txt");
		int i = 0;
		int j = 1;
		int k = 14;
		String ip = "";
		

		for (i = 0; i < 100; i++) {
			FileReader fin = new FileReader(
					"/Users/Zhefan/Dropbox/categoryID.txt");
			BufferedReader b1 = new BufferedReader(fin);
			ip = IPClass.IPAddress();

			String Temp = null;
			while ((Temp = b1.readLine()) != null) {
				String CategoryID = Temp;

				for (k = 14; k < 16; k++) {
					for (j = 1; j < 12; j++) {

						fout.write(ip + "," + CategoryID + "," + j + "," + k
								+ "," +  (new Random().nextInt(10) + 1) + "\r\n");
					}
				}
			}
			System.out.println(ip);
			fout2.write(ip + "\r\n");
			fin.close();
		}
		fout.close();
		fout2.close();

	}

	

}
