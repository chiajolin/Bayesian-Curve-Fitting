import Jama.Matrix;

public class CurveFormula {
	private static final int M = 5; //order of polynomial fitting curve, assume 5.
	private int N;
	private static final double alpha = 0.005;
	private static final double beta = 11.1;
	double[] inputIntervals; //input time intervals : 1 to N
	double[] inputValues; //input values from csv
	
	public CurveFormula(double[] inputValues){
		this.inputValues = inputValues;
		this.N = inputValues.length;
		inputIntervals = new double[N]; //input time intervals : 1 to N
		//generate time intervals, each interval = 1
		for(int i = 0; i < N; i++){
			inputIntervals[i] = i + 1;
		}
	}
	
	public int getN(){
		return N;
	}
	
	/**
	 * Generate phi(x) = x^i where i = 0,1,...,M
	 * @param x : a given x
	 * @return [M+1][1] matrix phix
	 */
	private Matrix gen_phi_x(double x){
		Matrix phix = new Matrix(M + 1, 1);
		for (int i = 0; i < M + 1; i++) {
			phix.set(i, 0, (Math.pow(x, i)));
		}		
		return phix;
	}
	
	/**
	 * Generate phi(x)_t
	 * @param phix : the matrix needed to be transposed
	 * @return [1][M+1] transpose matrix 
	 */
	private Matrix gen_phi_xt(Matrix phix){
		return phix.transpose();
	}
	
	/**
	 * Generate S
	 * @return [M+1][M+1] matrix S
	 */
	private Matrix gen_S(){
		//aplha*I
		Matrix I = Matrix.identity(M + 1, M + 1);
		Matrix firstElement = I.times(alpha);
		//sum of phi(xn)*phi(xn)t
		Matrix sum = new Matrix(M + 1, M + 1);
		for(int i = 0; i < N; i++){
			Matrix phix = gen_phi_x(inputIntervals[i]);
			sum.plusEquals(phix.times(gen_phi_xt(phix)));
		}
		Matrix SInverse = firstElement.plus(sum.times(beta));
		return SInverse.inverse();
	}
	
	/**
	 * Generate variance: s^2
	 * @param x: a given x
	 * @return s^2
	 */
	@SuppressWarnings("unused")
	private double gen_sSquare(double x){
		Matrix phix = gen_phi_x(x);
		Matrix temp = gen_phi_xt(phix).times(gen_S()).times(phix);
		double sSquare = 1/beta + temp.get(0, 0);
		return sSquare;
	}
	
	/**
	 * Generate mean: m(x)
	 * @param x: a given x
	 * @return m(x)
	 */
	public double mx(double x){
		Matrix sum = new Matrix(M + 1, 1);
		for(int i = 0; i < N; i++){
			Matrix phix = gen_phi_x(inputIntervals[i]);
			sum.plusEquals(phix.times(inputValues[i]));
		}
		Matrix temp = gen_phi_xt(gen_phi_x(x)).times(gen_S()).times(sum);
		double mx = beta * temp.get(0, 0);
		return mx;
	}
	
}
