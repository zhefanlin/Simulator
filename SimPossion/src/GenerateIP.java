import java.util.Random;

public class GenerateIP {

	public Random r = new Random();

	public String IPAddress() {
		StringBuilder sb = new StringBuilder();
		sb.append(r.nextInt(256));
		sb.append(".");
		sb.append(r.nextInt(256));
		sb.append(".");
		sb.append(r.nextInt(256));
		sb.append(".");
		sb.append(r.nextInt(256));
		String ip = sb.toString();
		return ip;
	}

}
