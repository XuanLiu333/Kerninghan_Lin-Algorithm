import java.io.File;
import java.util.Scanner;


public class KLprogram {

	
	public static void main(String[] args){
		
		try{
		int [][] graph = create2DIntMatrixFromFile("graph.txt");
		
		//print out the matrix
		for (int i = 0; i < graph.length; i++) {
		    for (int j = 0; j < graph[i].length; j++) {
		        System.out.print(graph[i][j] + " ");
		    }
		    System.out.println();
		}
		
		KernighanLin test = new KernighanLin(graph);
		test.process();
		
		System.out.println("final partition A :" + test.A);
		System.out.println("final partition B :" + test.B);
		System.out.println("cut cost :" + test.cutCost);


		
		
		
		}
		catch (Exception e) {
			// need modified
			 e.printStackTrace();			
		}	
		
		
	}
		
	

	

	//public create 2-d array from input txt file	
	public static int[][] create2DIntMatrixFromFile(String filename) throws Exception {
		int[][] matrix ;

		File inFile = new File(filename);
		Scanner in = new Scanner(inFile);

		int intLength = 0;
		String[] length = in.nextLine().trim().split("\\s+");
		  for (int i = 0; i < length.length; i++) {
		    intLength++;
		  }

		in.close();
		matrix = new int[intLength][intLength];
		in = new Scanner(inFile);

		int lineCount = 0;
		while (in.hasNextLine()) {
		  String[] currentLine = in.nextLine().trim().split("\\s+"); 
		     for (int i = 0; i < currentLine.length; i++) {
		        matrix[lineCount][i] = Integer.parseInt(currentLine[i]);    
		            }
		     lineCount++;
		 }                                 
		 return matrix;
		}


		public static boolean isMagicSquare(int[][] square) {

		  return false;
		}
	
	
	
	
}
