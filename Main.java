import java.io.*;
import java.util.*;

/**
 * @author Tal Danay <203626312>
 * @author Barr Inbar <039173232>
 * @since May 2016
 * 
 *        HW2 in OS course @ Afeka College for Dr. Barak Shenhav.<br/>
 *        Implements matrix multiplication of 2 2D matrices.<br/>
 *        Input: A and B matrices in given input files.<br/>
 *        Output: C matrix in given output file.<br/>
 *        Run with 3 runtime arguments with paths of input and output files.
 */
public class Main {

	public static void main(String[] args) {
		new Main(args);
	}

	/**
	 * Main Code for running the Matrix Multiplication program
	 * 
	 * @param args
	 *            Runtime arguments passed to the main (I/O file paths)
	 */
	public Main(String[] args) {

		// Variables definition
		ThreadGroup tgMatMul = new ThreadGroup("MatMul");
		int[][] A;
		int[][] B;
		int[][] C;

		// Code Section

		// Make sure there are enough arguments for running
		if (args.length < 3)
			System.out.println("Please run with 3 file names "
					+ "for each matrix A, B and C");
		else {

			// Try to read each of the matrices
			A = readMatrix(args[0]);
			B = readMatrix(args[1]);
			if (A == null)
				System.out.println("Ilegal input in file A");
			else if (B == null)
				System.out.println("Ilegal input in file B");

			// Make sure matrices sizes are OK
			else if (A[0].length != B.length)
				System.out.println("Columns of A must comply with rows of B");
			else {

				// Allocate C in the proper size
				C = new int[A.length][B[0].length];

				// For each element in C
				for (int row = 0; row < C.length; row++)
					for (int col = 0; col < C[0].length; col++)
						
						// Create a new thread for calculating the cell in C[row][col]
						(new Thread(tgMatMul, new WorkerThread(row, col, 
								A, B, C))).start();

				// Wait for all threads to finish
				while (tgMatMul.activeCount() > 0);

				// Write matrix to output file
				writeMatrix(C, args[2]);
			}
		}
	}

	/**
	 * Reads a matrix from a given input file. The function reads the first line
	 * which indicates the rows and columns and then allocates the matrix. Then
	 * it reads the elements of the matrix line by line and populates the
	 * matrix.
	 * 
	 * @param strFileName
	 *            The path of the input file.
	 * @return <code>int[][]</code> The read matrix (if an exception is caught
	 *         returns null).
	 */
	private static int[][] readMatrix(String strFileName) {

		// Variable definition
		Scanner in;
		int[][] arrMatrix;

		// Code Section

		try {

			// Open the file for reading
			in = new Scanner(new File(strFileName));
		} catch (FileNotFoundException e1) {

			// File couldn't be opened
			return null;
		}
		try {
			// Read matrix dimensions
			arrMatrix = new int[in.nextInt()][in.nextInt()];
			in.nextLine();

			// Read matrix values
			for (int i = 0; i < arrMatrix.length; i++) {
				for (int j = 0; j < arrMatrix[i].length; j++) {
					if (!in.hasNext())
						return null;
					arrMatrix[i][j] = in.nextInt();
				}
				if (in.hasNextLine())
					in.nextLine(); // Clear \n from buffer
			}
		} catch (InputMismatchException e) {
			return null;
		} catch (IllegalArgumentException e) {
			return null;
		} finally {
			in.close();
		}
		return arrMatrix;
	}

	/**
	 * Writes a matrix to a given output file. The function writes the matrix
	 * dimensions in the first line. Then it writes the elements of the matrix
	 * line by line.
	 * 
	 * @param Matrix
	 *            The matrix to write.
	 * @param strFileName
	 *            The path of the output file.
	 * @return <code>boolean</code> Whether the writing succeeded or not (if an
	 *         exception is caught returns false).
	 */
	private static boolean writeMatrix(int[][] Matrix, String strFileName) {
		PrintWriter pw;
		try {

			// Open the file for writing
			pw = new PrintWriter(new File(strFileName));
		} catch (FileNotFoundException e) {

			// File couldn't be opened
			return false;
		}

		// Write matrix dimensions
		pw.println(Matrix.length + " " + Matrix[0].length);

		// Write matrix values
		for (int i = 0; i < Matrix.length; i++) {
			for (int j = 0; j < Matrix[0].length; j++)
				pw.print(Matrix[i][j] + " ");
			pw.println();
		}
		pw.close();
		return true;
	}
}
