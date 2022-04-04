import java.util.Scanner;

class Main {
  public static void main(String[] args) {

		MatrixClass.sanityCheck();
		Scanner keyboard = new Scanner(System.in);
		System.out.print("What is the size of your matrix square, assuming n is a power of 2: ");
		int size = keyboard.nextInt();
		MatrixClass test = new MatrixClass(size);
		System.out.println("Loading... ");
		test.generateMatrix();
	/*	System.out.println("\nMatrix One\n**********");
		MatrixClass.printMatrix(test.getFirstMatrix());
		System.out.println("\nMatrix Two\n**********");
		MatrixClass.printMatrix(test.getSecondMatrix()); */
		int[][] matOne = test.getFirstMatrix();
		int[][] matTwo = test.getSecondMatrix();
		System.out.println("\nResults\n*******\nBeginning Classical Matrix Multiplication... ");
		
		long timeStart1 = System.nanoTime();
		int[][] result1 = test.calcClassicMultMatrix();
		long timeEnd1 = System.nanoTime();
		
		System.out.printf("\nTime elapsed for Classical: %d nanoseconds.\n\n", timeEnd1 - timeStart1);
		System.out.println("Beginning Naive Matrix Multiplication...");
		long timeStart2 = System.nanoTime();
		int[][] result2 = test.calcDivConMatrix(matOne,matTwo);
		long timeEnd2 = System.nanoTime();

		System.out.printf("Time elapsed for Naive: %d nanoseconds.\n\n", timeEnd2 - timeStart2);

		System.out.println("Beginning Strassen's Matrix Multiplication...");
		long timeStart3 = System.nanoTime();
		int[][] result3 = test.strassen(matOne,matTwo);
		long timeEnd3 = System.nanoTime();
		
		System.out.printf("Time elapsed for Strassen: %d nanoseconds.\n\n", timeEnd3 - timeStart3);
		//System.out.println("\nMatrix Answer\n*************");
		//MatrixClass.printMatrix(result3);
  }


}