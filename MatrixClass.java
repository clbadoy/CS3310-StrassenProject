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
 * This file is the MatrixClass file to hold the method calls for conducting Matrix Multiplication.
 */

import java.util.Random;

public class MatrixClass 
{
	private int[][] matrixOne;
	private int[][] matrixTwo;
	private int size;
	private Random rand;


	// Initialize a MatrixClass
	public MatrixClass(int si)
	{
		size = si;
		int matOne[][] = new int[size][size];
		int matTwo[][] = new int[size][size];
		matrixOne = matOne;
		matrixTwo = matTwo;
		rand = new Random();
	}
	// Generates a random Matrix containing numbers from (-9) to (+9).
	public void generateMatrix()
	{
		Random secRand = new Random();
		int neg = 0;
		
		// Traverse through each matrix slot in Matrix A and insert a random number.
		for(int ro = 0; ro < matrixOne.length ; ro++)
			for(int co = 0; co < matrixOne[ro].length ; co++)
				{
					
					neg = (int)(rand.nextInt(2));
					switch(neg)
					{
						case 0:
							neg = 1;
							break;
						case 1:
							neg = -1;
							break;
					}
					matrixOne[ro][co] = (int) (rand.nextInt(10) * neg);
				}
		// Traverse through each matrix slot in Matrix B and insert a random number.
		for(int ro = 0; ro < matrixTwo.length ; ro++)
			for(int co = 0; co < matrixTwo[ro].length ; co++)
				{
					
					neg = (int)(secRand.nextInt(2));
					switch(neg)
					{
						case 0:
							neg = 1;
							break;
						case 1:
							neg = -1;
							break;
					}
					matrixTwo[ro][co] = (int) (secRand.nextInt(10) * neg);
				}
	}

	// Returns Matrix A.
	public int[][] getFirstMatrix()
	{
		int[][] tempMatrix = new int[matrixOne.length][matrixOne[0].length];

		for(int i = 0; i < matrixOne.length; i++)
			for(int j = 0; j < matrixOne[i].length; j++)
				tempMatrix[i][j] = matrixOne[i][j];

		return tempMatrix;
	}

	// Returns Matrix B.
	public int[][] getSecondMatrix()
	{
		int[][] tempMatrix = new int[matrixTwo.length][matrixTwo[0].length];

		for(int i = 0; i < matrixTwo.length; i++)
			for(int j = 0; j < matrixTwo[i].length; j++)
				tempMatrix[i][j] = matrixTwo[i][j];

		return tempMatrix;
	}

	// Prints any Matrix onto Console.
	// int[][] mat is the Matrix that is to be printed.
	public static void printMatrix(int[][] mat)
	{
		for(int i = 0; i < mat.length; i++)
			for(int j = 0; j < mat[i].length; j++)
				{
					System.out.print(mat[i][j] + " ");
					if(j == mat[i].length - 1)
						System.out.println();
				}
	}


	// Method that conducts Classical Matrix Multiplication
	// Three for loops means that its Runtime can be estimated with Theta(n^3).
	public int[][] calcClassicMultMatrix()
	{
		int[][] newMatrix = new int[matrixOne.length][matrixOne[0].length];
		for(int ro = 0; ro < matrixOne.length; ro++)
			for(int co = 0; co < matrixTwo[ro].length; co++)
				for(int i = 0; i < matrixOne[ro].length; i++)
					{
						newMatrix[ro][co] += matrixOne[ro][i] * matrixTwo[i][co];
					}
		return newMatrix;
	}

	// Method that conducts Classical Matrix Multiplication for Sanity Check.
	// Three for loops means that its Runtime can be estimated with Omega(n^3).
	public int[][] calcClassicMultMatrix(int[][] matrixOne, int[][] matrixTwo)
	{
		int[][] newMatrix = new int[matrixOne.length][matrixOne[0].length];
		for(int ro = 0; ro < matrixOne.length; ro++)
			for(int co = 0; co < matrixTwo[ro].length; co++)
				for(int i = 0; i < matrixOne[ro].length; i++)
					{
						newMatrix[ro][co] += matrixOne[ro][i] * matrixTwo[i][co];
					}
		return newMatrix;
	}

	// Method that conducts Naive Matrix Multiplication.
	// Has 8 recursive method calls to itself with size n/2
	// Makes 8T(n/2).
	// Has partitionMatrix() and combineMatrix() with two for loops to traverse partitioned Matrix. (Theta(n^2)).
	// Combined makes T(n) = 8T(n/2) + Theta(n^2).
	// By Master's Theorem, order is estimated to be at O(n^log_2(8)) or O(n^3).
	public int[][] calcDivConMatrix(int[][] matOne, int[][] matTwo)
	{
		int[][] newMatrix = new int[matOne.length][matOne[0].length];
		int n = newMatrix.length;
		if(n == 1)
		{
			newMatrix[0][0] = matOne[0][0] * matTwo[0][0];
		}
		else
		{
			int[][] quadOneA = new int[n/2][n/2]; // A11
			int[][] quadOneB = new int[n/2][n/2];
			int[][] quadTwoA = new int[n/2][n/2]; // A21
			int[][] quadTwoB = new int[n/2][n/2];
			int[][] quadThreeA = new int[n/2][n/2]; //A 22
			int[][] quadThreeB = new int[n/2][n/2];
			int[][] quadFourA = new int[n/2][n/2]; //A 12
			int[][] quadFourB = new int[n/2][n/2];

			partitionMatrix(matOne, quadOneA, 0, 0); //11
			partitionMatrix(matOne, quadTwoA, n/2, 0); //21
			partitionMatrix(matOne, quadThreeA, n/2, n/2); //22
			partitionMatrix(matOne, quadFourA, 0, n/2); //12
		
			partitionMatrix(matTwo, quadOneB, 0, 0);
			partitionMatrix(matTwo, quadTwoB, n/2, 0);
			partitionMatrix(matTwo, quadThreeB, n/2, n/2);
			partitionMatrix(matTwo, quadFourB, 0, n/2);
			
			int[][] quadOneC = matrixAdd(calcDivConMatrix(quadOneA,quadOneB), calcDivConMatrix(quadFourA,quadTwoB));
			int[][] quadTwoC = matrixAdd(calcDivConMatrix(quadTwoA,quadOneB), calcDivConMatrix(quadThreeA,quadTwoB));
			int[][] quadThreeC = matrixAdd(calcDivConMatrix(quadTwoA,quadFourB), calcDivConMatrix(quadThreeA,quadThreeB));
			int[][] quadFourC = matrixAdd(calcDivConMatrix(quadOneA,quadFourB), calcDivConMatrix(quadFourA,quadThreeB));

			combineMatrix(newMatrix, quadOneC, 0, 0);
			combineMatrix(newMatrix, quadTwoC, n/2, 0);
			combineMatrix(newMatrix, quadThreeC, n/2, n/2);
			combineMatrix(newMatrix, quadFourC, 0, n/2);
		}
		return newMatrix;
		
	}
	

	// Method that conducts Strassen's matrix Multiplication.
	// Does 7 recursive calls with size n/2, making 7T(n/2).
	// An additional Theta(n^2) time complexity is included for partitionMatrix() and combineMatrix()
	// Results to time complexity of T(n) = 7(n/2) + Theta(n^2).
	// Time complexity is O(n^log_2(7)) or O(n^2.81).
	public int[][] strassen(int[][] matOne, int[][] matTwo)
	{
		int[][] newMatrix = new int[matOne.length][matOne[0].length];
		int n = newMatrix.length;
		if(n == 1)
		{
			newMatrix[0][0] = matOne[0][0] * matTwo[0][0];
		}
		else
		{
			int[][] quadOneA = new int[n/2][n/2]; // A11
			int[][] quadOneB = new int[n/2][n/2];
			int[][] quadTwoA = new int[n/2][n/2]; // A21
			int[][] quadTwoB = new int[n/2][n/2];
			int[][] quadThreeA = new int[n/2][n/2]; //A 22
			int[][] quadThreeB = new int[n/2][n/2];
			int[][] quadFourA = new int[n/2][n/2]; //A 12
			int[][] quadFourB = new int[n/2][n/2];

			partitionMatrix(matOne, quadOneA, 0, 0); //11
			partitionMatrix(matOne, quadTwoA, n/2, 0); //21
			partitionMatrix(matOne, quadThreeA, n/2, n/2); //22
			partitionMatrix(matOne, quadFourA, 0, n/2); //12
		
			partitionMatrix(matTwo, quadOneB, 0, 0);
			partitionMatrix(matTwo, quadTwoB, n/2, 0);
			partitionMatrix(matTwo, quadThreeB, n/2, n/2);
			partitionMatrix(matTwo, quadFourB, 0, n/2);

			int[][] S1 = matrixSub(quadFourB, quadThreeB);
			int[][] S2 = matrixAdd(quadOneA, quadFourA);
			int[][] S3 = matrixAdd(quadTwoA, quadThreeA);
			int[][] S4 = matrixSub(quadTwoB, quadOneB);
			int[][] S5 = matrixAdd(quadOneA, quadThreeA);
			int[][] S6 = matrixAdd(quadOneB, quadThreeB);
			int[][] S7 = matrixSub(quadFourA, quadThreeA);
			int[][] S8 = matrixAdd(quadTwoB, quadThreeB);
			int[][] S9 = matrixSub(quadOneA, quadTwoA);
			int[][] S10 = matrixAdd(quadOneB, quadFourB);

			int[][] P1 = strassen(quadOneA, S1);
			int[][] P2 = strassen(S2, quadThreeB);
			int[][] P3 = strassen(S3, quadOneB);
			int[][] P4 = strassen(quadThreeA, S4);
			int[][] P5 = strassen(S5, S6);
			int[][] P6 = strassen(S7, S8);
			int[][] P7 = strassen(S9, S10);

			int[][] quadOneC = matrixSub(matrixAdd(P4,matrixAdd(P6,P5)),P2);
			int[][] quadTwoC = matrixAdd(P3,P4);
			int[][] quadThreeC = matrixAdd(matrixSub(P1,P3),matrixSub(P5,P7));
			int[][] quadFourC = matrixAdd(P2,P1);
			
			combineMatrix(newMatrix, quadOneC, 0, 0);
			combineMatrix(newMatrix, quadTwoC, n/2, 0);
			combineMatrix(newMatrix, quadThreeC, n/2, n/2);
			combineMatrix(newMatrix, quadFourC, 0, n/2);
			}

		return newMatrix;
	}

	// Helper Method that partitions the original matrix into a quadrant.
	private void partitionMatrix(int[][] mainMat,int[][] partMat, int ro, int co)
	{
		for(int i = 0, row = ro; i < partMat.length; i++, row++)
			for(int j = 0, col = co; j < partMat.length; j++, col++)
				partMat[i][j] = mainMat[row][col];

	}
	
	// Helper method that combines a quadrant back to a larger matrix size.
	private void combineMatrix(int[][] mainMat, int[][] partMat, int ro, int co)
	{
		for(int i = 0, row = ro; i < partMat.length; i++, row++)
			for(int j = 0, col = co; j < partMat[0].length; j++, col++)
			{
				mainMat[row][col] =  partMat[i][j];
			}
		
	}

	// Helper method that conducts Matrix Addition.
	private int[][] matrixAdd(int[][] matOne, int[][] matTwo)
	{
		int[][] tempMat = new int[matOne.length][matOne[0].length];
		for(int i = 0; i < matOne.length;i++)
			for(int j = 0; j < matOne[0].length;j++)
				{
					tempMat[i][j] = matOne[i][j] + matTwo[i][j];
				}
		return tempMat;
	}

	// Helper Method that conducts Matrix Subtraction.
	private int[][] matrixSub(int[][] matOne, int[][] matTwo)
	{
		int[][] tempMat = new int[matOne.length][matOne[0].length];
		for(int i = 0; i < matOne.length;i++)
			for(int j = 0; j < matOne[0].length;j++)
				{
					tempMat[i][j] = matOne[i][j] - matTwo[i][j];
				}
		return tempMat;
	}
	
	// Static method that prints sanity check to the console.
	public static void sanityCheck()
	{
		MatrixClass temp = new MatrixClass(4);
		int[][] matOne = {{2,0,-1,6},{3,7,8,0},{-5,1,6,-2},{8,0,2,7}};
		int[][] matTwo = {{0,1,6,3},{-2,8,7,1},{2,0,-1,0},{9,1,6,-2}};
		
		int[][] matRes1 = temp.calcClassicMultMatrix(matOne, matTwo);
		int[][] matRes2 = temp.calcDivConMatrix(matOne, matTwo);
		int[][] matRes3 = temp.strassen(matOne, matTwo);
		
		System.out.println("SANITY CHECK\nClassic Result:\n");
		MatrixClass.printMatrix(matRes1);
		System.out.println("\nNaive Result:\n");
		MatrixClass.printMatrix(matRes2);
		System.out.println("\nStrassen Result:\n");
		MatrixClass.printMatrix(matRes3);
	}
}