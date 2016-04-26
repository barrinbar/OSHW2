/**
 * @author Tal Danay <203626312> Barr Inbar <ID>
 * @since May 2016  
 * 
 * HW2 in OS course @ Afeka College for Dr. Barak Shenhav
 * Implements matrix multiplication of 2 2D matrices
 * Input: A and B matrices in given input files
 * Output: C matrix in given output file 
 */
public class Main {
	// Members
	private int[][] A;
	private int[][] B;
	private int[][] C;
	
	// Methods
	public static void main(String[] args) {

		new Main(args);
	}
	public Main() {
		super();
	}

	public Main(String[] args) {
		super();
		 ThreadGroup tg = new ThreadGroup("CalcMatrix");

		// Code Section
		
		// Check there are enough arguments for running
		if(args.length < 3)
			System.out.println("Please run with 3 file names for each matrix A, B and C");
		else 
		{
			// Try to read each of the matrices
			if (!readMatrix(A, args[0]))
				System.out.println("Ilegal input in file A");
			else 
			{
				if (!readMatrix(B, args[1]))
					System.out.println("Ilegal input in file B");
				else 
				{
					// Assign C in the proper size
					C = new int[A.length][B[0].length];
					
					// Make sure matrix size are OK
					if (A[0].length != B.length)
						System.out.println("Columns of A must comply with rows of  B");
					else
					{
						// For each element in C
						for (int row = 0; row < C.length; row++)
						{
							for (int col = 0; col < C[0].length; col++)
							{
								// Create a new thread for calculating the cell in C
								(new Thread(tg, new WorkerThread(row, col,A, B, C))).start();
							}
						}
						// Wait for all threads to finish
						while(tg.activeCount() > 0);
						
						writeMatrix(C, args[2]);
					}
				}
			}
		}		
	}
	
	private static boolean readMatrix(int[][] Matrix, String strFileName) {
		Scanner in;
		try 
		{
			in = new Scanner(new File(strFileName));
		}
		catch (FileNotFoundException e1) {return false;}	
		try 
		{ 
			// Read matrix dimensions
			Matrix = new int[in.nextInt()][in.nextInt()];
			in.nextLine();
			// Read matrix values
			for (int i=0; i<Matrix.length; i++) 
			{
				for (int j=0; j<Matrix[i].length; j++) 
				{
					if (!in.hasNext()) return false;
					Matrix[i][j] = in.nextInt();
				}
				if (in.hasNextLine()) in.nextLine(); // Clear \n from buffer
			}
		}
		catch (InputMismatchException e) {return false;}
		catch (IllegalArgumentException e) {return false;}
		finally 
		{
			in.close();
		}
		return true;
	}
	
	private static boolean writeMatrix(int[][] Matrix, String strFileName) {
		PrintWriter pw;
		try 
		{
			pw = new PrintWriter(new File(strFileName));		
		}
		catch (FileNotFoundException e) {return false;}
		// Write matrix dimensions
		pw.println(Matrix.length+" "+Matrix[0].length);
		// Write matrix values
		for (int i=0; i<Matrix.length; i++) 
		{
			for (int j=0; j<Matrix[0].length; j++) 
			{
				pw.print(Matrix[i][j]+" "); 
			}
			pw.println(); 
		}
		pw.close();
		return true;
	}
}
