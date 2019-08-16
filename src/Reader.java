import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
	private static final int totalData = 10; // size of each input data
	private int N; // size of user input data
	private String filePath;
	private String[] readValues;
	private String[] readDates;
	
	public Reader(int N, String company) throws IOException{
		this.N = N;
		readValues = new String[totalData+2];
		readDates = new String[totalData+2];
		filePath = "data/"+ company + ".csv";
		read();
	}
	
	private void read() throws IOException{
		int count = 0;
		FileReader fr = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fr);
		while(br.ready()){
			String line = br.readLine();
			//System.out.println(line);
			String element[] = line.split(",");
			readValues[count] = element[2]; //count = 0 -> price, count = 2~10 -> input, 
											//count = 1 -> actual value of predict point
			readDates[count] = element[1];
			count++;
		}
		fr.close();	
	}
	
	/**
	 * getInputValue
	 * @return inputValue to predict
	 */
	public double[] getInputValue(){
		double[] inputValue = new double[N];
		for(int i = N + 1; i > 1; i--){
			inputValue[N+1-i] = Double.parseDouble(readValues[i]);
		}
		return inputValue;
	}
	
	/**
	 * getActualPrice
	 * @return real price of the predict point
	 */
	public double getActualPrice(){
		return Double.parseDouble(readValues[1]);
	}
	
	public String getPredictDate(){
		return readDates[1];
	}
	 
}
