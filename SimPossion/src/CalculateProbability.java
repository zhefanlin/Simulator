public class CalculateProbability {

	public CalculateProbability() {

	}

	public double Probability(double Lamda, int k) {
		int i =0;
		double p = 0, a=0, b=0, c=1.0;
		
		a = Math.exp(0-Lamda);
		b = Math.pow(Lamda,k);
		
		for(i=2;i<=k;i++){
			c=c*i;
		}
		p=a*b/c;
		
		return p;
	}
}
