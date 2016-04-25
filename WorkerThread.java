public class WorkerThread implements Runnable {
	private int row;
	private int col;
	private int[][] A;
	private int[][] B;
	private int[][] C;

	public WorkerThread(int row, int col, int[][] A, int[][] B, int[][] C) {
		this.row = row;
		this.col = col;
		this.A = A;
		this.B = B;
		this.B = B;
	}

	public WorkerThread(){
	}
	
	public void run() {
		System.out.println("Hello from a thread!");
		C[row][col] = 0;
		/* add your calculations for C[row][col] here */
		for (int i = 0; i < B.length; i++) {
			C[row][col] += A[row][i] + B[i][col]; 
		}
	}
}