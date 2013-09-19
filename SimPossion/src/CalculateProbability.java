import java.util.Random;

public class CalculateProbability {

	public CalculateProbability() {

	}

	public double Probability(float lambda, int k) {
		int i =0;
		double p = 0, a=0, b=0, c=1.0;
		
		a = Math.exp(0-lambda);
		b = Math.pow(lambda,k);
		
		for(i=2;i<=k;i++){
			c=c*i;
		}
		p=a*b/c;
		
		return p;
	}
	
	public double poissonRandomInterarrivalDelay(double lambda) {
		
	    return (Math.log(1.0-Math.random())/-lambda);
	}
	
	public int getRandomK(){
		Random random = new Random();
		int k = random.nextInt(10) + 1;
		return k;
	}
	
}
