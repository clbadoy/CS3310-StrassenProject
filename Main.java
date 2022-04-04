/**
 * Christian Badoy
 * CS 3310
 * Professor Luu
 * 03 April 2022
 * 
 * Project: Matrix Multiplication
 * The purpose of this project is to code and visualize the runtime of three forms of Matrix Multiplication:
 * 1. Classical
 * 2. Naive
 * 3. Strassen's
 * 
 * This file is the runner file for all Matrix Multiplication.
 */
import java.util.Scanner;

class Main {
  public static void main(String[] args) {

		// Prints Sanity Check for all three Multiplications onto Console.
		MatrixClass.sanityCheck();

		// Main Runner Class

		// Takes User input on size of initializing Matrix.
		Scanner keyboard = new Scanner(System.in);
		System.out.print("What is the size of your matrix square, assuming n is a power of 2: ");
		int size = keyboard.nextInt();
		keyboard.close();

		// Creates a MatrixClass of test and generates a psuedo-random matrix of Matrix A and B.
		MatrixClass test = new MatrixClass(size);
		System.out.println("Loading... ");
		test.generateMatrix();

		// Initialize matrix A and B on Runner class to avoid having to make calls to the method again in matrix Multiplication.
		// Reduce runtime.
		int[][] matOne = test.getFirstMatrix();
		int[][] matTwo = test.getSecondMatrix();

		System.out.println("\nResults\n*******\nBeginning Classical Matrix Multiplication... ");
		
		// Conducts Classical Matrix Multiplication
		long timeStart1 = System.nanoTime();
		int[][] result1 = test.calcClassicMultMatrix();
		long timeEnd1 = System.nanoTime();
		
		// Prints time elapsed for Classical Multiplication in nanoseconds.
		System.out.printf("\nTime elapsed for Classical: %d nanoseconds.\n\n", timeEnd1 - timeStart1);

		System.out.println("Beginning Naive Matrix Multiplication...");

		// Conducts Naive Matrix Multiplication
		long timeStart2 = System.nanoTime();
		int[][] result2 = test.calcDivConMatrix(matOne,matTwo);
		long timeEnd2 = System.nanoTime();

		// Prints time elapsed for naive multiplication in nanoseconds.
		System.out.printf("Time elapsed for Naive: %d nanoseconds.\n\n", timeEnd2 - timeStart2);

		System.out.println("Beginning Strassen's Matrix Multiplication...");

		// Conducts Strassen's Matrix Multiplication
		long timeStart3 = System.nanoTime();
		int[][] result3 = test.strassen(matOne,matTwo);
		long timeEnd3 = System.nanoTime();

		// Prints time elapsed for Strassen'smultiplication in nanoseconds.
		System.out.printf("Time elapsed for Strassen: %d nanoseconds.\n\n", timeEnd3 - timeStart3);
  }


}