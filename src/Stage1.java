import java.util.Scanner;




public class Stage1 {
	// Declare constant variables
	private static final int SCORE_NUMS=6;
	private static final int REMAINING_NUMS=4;
	 // Declare the scanner to read data from the console screen
	static Scanner inConsole= new Scanner(System.in);  
	// Declare tow arrays to store the score of the athletes 
	static int[] iaScoreRun1=new int[SCORE_NUMS];
	static int[] iaScoreRun2=new int[SCORE_NUMS];
	// Declare necessary variables to store the overall score of each run
	static float fFinalScore,fOverallScoreRun1, fOverallScoreRun2;
	// the main method
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Enter the scores of the run 1
		System.out.println("---->The first run scores [0-100]<----");
		for(int iI=0,iJ=1; iI<SCORE_NUMS; iI++, iJ++){
			System.out.print("Judge "+ iJ+": ");
			iaScoreRun1[iI]=inConsole.nextInt();
		}
		// Enter the scores of the run 2
		System.out.println("---->The second run scores [0-100]<----");
		for(int iI=0,iJ=1; iI<SCORE_NUMS; iI++, iJ++){
			System.out.print("Judge "+ iJ+": ");
			iaScoreRun2[iI]=inConsole.nextInt();
		}
		// calculate overall score of Run1 and Run2
		fOverallScoreRun1= CalculatingOverallScore(iaScoreRun1);
		fOverallScoreRun2= CalculatingOverallScore(iaScoreRun2);
		// release iFinal score
		fFinalScore= findingFinalScore(fOverallScoreRun1, fOverallScoreRun2);
		// print out the screen
		System.out.println("----------------------------------");
		System.out.println("The final score is: "+ fFinalScore);
	}
	// function to return the maximum number in the array
	public static int maxInArray(int[] iArray){
		// Declare variable iMax to return the maximum number of the array 
		int iMax=iArray[0];
		// Finding the maximum number
		for(int iI=0; iI<iArray.length; iI++){
			if (iMax < iArray[iI])
				iMax=iArray[iI];
		}
		return iMax;
	}
	// function to return the minimum score in an array
	public static int minInArray(int[] iArray){
		// Declare variable iMin to return the minimum number of the array 
		int iMin=iArray[0];
		// Finding the minimum number
		for(int iI=0; iI<iArray.length; iI++){
			if (iMin > iArray[iI])
					iMin=iArray[iI];
		}
		// return 
		return iMin;
	}
	// method to return the overall score for each run
	public static float CalculatingOverallScore(int[] iArray){
		// Declare iTotal to save the sum of scores
		// and iOverallScore to return the overall score
		float iTotal=0;
		float fOverallScore=0;
		// counting the total score
		for(int iI=0; iI< iArray.length; iI++){
			iTotal+= iArray[iI];
		}
		// counting the average score
		fOverallScore= (iTotal-maxInArray(iArray)-minInArray(iArray))/REMAINING_NUMS;
		return fOverallScore;
	}
	// method to return the final score
	public static float findingFinalScore(float fOverallScore1, float fOverallScore2){
		if (fOverallScore1 > fOverallScore2)
			return fOverallScore1;
		else
			return fOverallScore2;
	}
}
