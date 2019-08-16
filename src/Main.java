import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException{
		double accumAbsoluteError = 0; //accumulate absolute Error
		double accumActualPrice = 0; //accumulate actual price
		int daysBeforePredict;	
		Scanner userInputN = new Scanner(System.in);
		
		System.out.println("Enter how many days before the predict date you want to use as inputs(1-10):");	
		daysBeforePredict = userInputN.nextInt();
		for(int count = 1; count <= 10; count++){
			Reader reader = new Reader(daysBeforePredict, Integer.toString(count));
			CurveFormula predict = new CurveFormula(reader.getInputValue());
			System.out.println("--------------------------Result" + count + "--------------------------");
			System.out.println("inputs are: ");
			for(int j = 0; j < daysBeforePredict; j++){
				System.out.println((reader.getInputValue())[j]);
			}
			double actualPrice = reader.getActualPrice();
			double predictPrice = predict.mx(predict.getN()+1);
			accumAbsoluteError = accumAbsoluteError + Math.abs(predictPrice - actualPrice);
			accumActualPrice = accumActualPrice + actualPrice;
			
			System.out.println("predict date: " + reader.getPredictDate());
			System.out.println("actual price: " + actualPrice);
			System.out.println("predict price: " + predictPrice);
			System.out.println("absolute error: " + Math.abs(predictPrice - actualPrice));
			System.out.println("relative error: " + Math.abs((predictPrice - actualPrice)/actualPrice));
			System.out.println("");
		}
		System.out.println("--------------------------Evaluation--------------------------");
		//compute absolute mean error
		System.out.println("absolute mean error: " + accumAbsoluteError/10);
		
		//compute average relative error
		System.out.println("average relative error: " + accumAbsoluteError / accumActualPrice);
		
		userInputN.close();
	}
}