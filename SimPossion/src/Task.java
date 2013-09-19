import java.io.FileWriter;
import java.util.Random;
import java.util.TimerTask;


public class Task extends TimerTask{

	@Override
	public void run() {
		
		System.out.println("Start!");
		// TODO Auto-generated method stub
		AccessDatabase acc = new AccessDatabase();
		String BidRequest = "";
		
			CalculateProbability cal = new CalculateProbability();
			long interval = (long) cal.poissonRandomInterarrivalDelay(30.0 * 1000.0);
			try {
				
				Thread.sleep(interval);

				FileWriter fout = new FileWriter(
						"/Users/Zhefan/Dropbox/BidRequest.csv");

				Random random = new Random();
				int DayPart = random.nextInt(11) % (11 - 1 + 1) + 1;
				int Week = random.nextInt(15) % (15 - 14 + 1) + 14;
				
				System.out.println(DayPart + "  " + Week);

				BidRequest = acc.getBidRequest(DayPart, Week);

				fout.write("TimeStamp:DayPart " + DayPart + ", Week " + Week
						+ ", BidRequestJSON: " + BidRequest + "\r\n");

				System.out.println(BidRequest);
				fout.close();
			}catch(Exception e){
				   e.printStackTrace();
			}
		
			System.out.println("End!");
	}

}
