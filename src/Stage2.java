import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;




public class Stage2 {
	// Declare JFrame, JTextFiled, JLabel, JButton for application
	private JFrame frame;
	private JTextField textField_AthleteName;
	// textField_ScoreRun to get scores from six judges
	private JTextField textField_ScoreRun1Judge1;
	private JTextField textField_ScoreRun1Judge2;
	private JTextField textField_ScoreRun1Judge3;
	private JTextField textField_ScoreRun1Judge4;
	private JTextField textField_ScoreRun1Judge5;
	private JTextField textField_ScoreRun1Judge6;
	private JTextField textField_ScoreRun2Judge1;
	private JTextField textField_ScoreRun2Judge2;
	private JTextField textField_ScoreRun2Judge3;
	private JTextField textField_ScoreRun2Judge4;
	private JTextField textField_ScoreRun2Judge5;
	private JTextField textField_ScoreRun2Judge6;
	// declare JLabels
	private JLabel lblJudge_1;
	private JLabel lblJudge_2;
	private JLabel lblJudge_3;
	private JLabel lblJudge_4;
	private JLabel lblJudge_5;
	private JLabel lblJudge_6;
	private JLabel jLabel_OverallRun1Score;
	private JLabel jLabel_OverallRun2Score;
	private JLabel jLabel_EnterScores;
	private JLabel jLabel_BestScore;
	// declare the horizontal line
	private JSeparator separator;
	// declare two buttons Calculate and Quit
	private JButton btnCalculate;
	private JButton btnQuit;
	// Declare constant variables
	private static final int SCORE_NUMS=6;
	private static final int REMAINING_NUMS=4;
	// Declare tow arrays to store the score of the athletes 
	static int[] iaScoreRun1=new int[SCORE_NUMS];
	static int[] iaScoreRun2=new int[SCORE_NUMS];
	// Declare necessary variables to store the overall score of each run and final score
	static float fFinalScore,fOverallScoreRun1, fOverallScoreRun2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stage2 window = new Stage2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Stage2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Athlete's name: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 51, 98, 26);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Snowboard Halfpipe 2018 Winter Olympic Games Thredbo");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 11, 414, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField_AthleteName = new JTextField();
		textField_AthleteName.setBounds(109, 51, 155, 26);
		frame.getContentPane().add(textField_AthleteName);
		textField_AthleteName.setColumns(10);
		
		JLabel lblRun_1 = new JLabel("Run 1");
		lblRun_1.setBounds(10, 158, 46, 14);
		frame.getContentPane().add(lblRun_1);
		
		textField_ScoreRun1Judge1 = new JTextField();
		textField_ScoreRun1Judge1.setBounds(51, 155, 40, 20);
		frame.getContentPane().add(textField_ScoreRun1Judge1);
		textField_ScoreRun1Judge1.setColumns(10);
		
		textField_ScoreRun1Judge2 = new JTextField();
		textField_ScoreRun1Judge2.setBounds(116, 155, 40, 20);
		frame.getContentPane().add(textField_ScoreRun1Judge2);
		textField_ScoreRun1Judge2.setColumns(10);
		
		textField_ScoreRun1Judge3 = new JTextField();
		textField_ScoreRun1Judge3.setBounds(183, 155, 40, 20);
		frame.getContentPane().add(textField_ScoreRun1Judge3);
		textField_ScoreRun1Judge3.setColumns(10);
		
		textField_ScoreRun1Judge4 = new JTextField();
		textField_ScoreRun1Judge4.setBounds(249, 155, 40, 20);
		frame.getContentPane().add(textField_ScoreRun1Judge4);
		textField_ScoreRun1Judge4.setColumns(10);
		
		textField_ScoreRun1Judge5 = new JTextField();
		textField_ScoreRun1Judge5.setBounds(311, 155, 40, 20);
		frame.getContentPane().add(textField_ScoreRun1Judge5);
		textField_ScoreRun1Judge5.setColumns(10);
		
		textField_ScoreRun1Judge6 = new JTextField();
		textField_ScoreRun1Judge6.setBounds(378, 155, 40, 20);
		frame.getContentPane().add(textField_ScoreRun1Judge6);
		textField_ScoreRun1Judge6.setColumns(10);
		
		JLabel lblRun_2 = new JLabel("Run 2");
		lblRun_2.setBounds(10, 186, 46, 14);
		frame.getContentPane().add(lblRun_2);
		
		textField_ScoreRun2Judge1 = new JTextField();
		textField_ScoreRun2Judge1.setColumns(10);
		textField_ScoreRun2Judge1.setBounds(51, 183, 40, 20);
		frame.getContentPane().add(textField_ScoreRun2Judge1);
		
		textField_ScoreRun2Judge2 = new JTextField();
		textField_ScoreRun2Judge2.setColumns(10);
		textField_ScoreRun2Judge2.setBounds(116, 183, 40, 20);
		frame.getContentPane().add(textField_ScoreRun2Judge2);
		
		textField_ScoreRun2Judge3 = new JTextField();
		textField_ScoreRun2Judge3.setColumns(10);
		textField_ScoreRun2Judge3.setBounds(183, 183, 40, 20);
		frame.getContentPane().add(textField_ScoreRun2Judge3);
		
		textField_ScoreRun2Judge4 = new JTextField();
		textField_ScoreRun2Judge4.setColumns(10);
		textField_ScoreRun2Judge4.setBounds(249, 183, 40, 20);
		frame.getContentPane().add(textField_ScoreRun2Judge4);
		
		textField_ScoreRun2Judge5 = new JTextField();
		textField_ScoreRun2Judge5.setColumns(10);
		textField_ScoreRun2Judge5.setBounds(311, 183, 40, 20);
		frame.getContentPane().add(textField_ScoreRun2Judge5);
		
		textField_ScoreRun2Judge6 = new JTextField();
		textField_ScoreRun2Judge6.setColumns(10);
		textField_ScoreRun2Judge6.setBounds(378, 183, 40, 20);
		frame.getContentPane().add(textField_ScoreRun2Judge6);
		
		lblJudge_1 = new JLabel("Judge 1");
		lblJudge_1.setBounds(51, 130, 46, 14);
		frame.getContentPane().add(lblJudge_1);
		
		lblJudge_2 = new JLabel("Judge 2");
		lblJudge_2.setBounds(116, 130, 46, 14);
		frame.getContentPane().add(lblJudge_2);
		
		lblJudge_3 = new JLabel("Judge 3");
		lblJudge_3.setBounds(183, 130, 46, 14);
		frame.getContentPane().add(lblJudge_3);
		
		lblJudge_4 = new JLabel("Judge 4");
		lblJudge_4.setBounds(249, 130, 46, 14);
		frame.getContentPane().add(lblJudge_4);
		
		lblJudge_5 = new JLabel("Judge 5");
		lblJudge_5.setBounds(311, 130, 46, 14);
		frame.getContentPane().add(lblJudge_5);
		
		lblJudge_6 = new JLabel("Judge 6");
		lblJudge_6.setBounds(378, 130, 46, 14);
		frame.getContentPane().add(lblJudge_6);
		
		jLabel_OverallRun1Score = new JLabel("Overall score of Run 1:");
		jLabel_OverallRun1Score.setBounds(10, 233, 146, 14);
		frame.getContentPane().add(jLabel_OverallRun1Score);
		
		jLabel_OverallRun2Score = new JLabel("Overall score of Run 2:");
		jLabel_OverallRun2Score.setBounds(10, 258, 146, 14);
		frame.getContentPane().add(jLabel_OverallRun2Score);
		
		separator = new JSeparator();
		separator.setBounds(10, 211, 414, 11);
		frame.getContentPane().add(separator);
		
		jLabel_EnterScores = new JLabel("Enter the scores");
		jLabel_EnterScores.setBounds(10, 96, 98, 14);
		frame.getContentPane().add(jLabel_EnterScores);
		
		jLabel_BestScore = new JLabel("Final score:");
		jLabel_BestScore.setBounds(10, 286, 146, 14);
		frame.getContentPane().add(jLabel_BestScore);
		
		JLabel jLabel_OverallScoreRun1 = new JLabel("");
		jLabel_OverallScoreRun1.setBounds(177, 233, 46, 14);
		frame.getContentPane().add(jLabel_OverallScoreRun1);
		
		JLabel jLabel_OverallScoreRun2 = new JLabel("");
		jLabel_OverallScoreRun2.setBounds(177, 258, 46, 14);
		frame.getContentPane().add(jLabel_OverallScoreRun2);
		
		JLabel jLabel_FinalScore = new JLabel("");
		jLabel_FinalScore.setBounds(177, 286, 46, 14);
		frame.getContentPane().add(jLabel_FinalScore);
		
		btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// get data for two score arrays of run1 and run2
				try{
					iaScoreRun1[0]= Integer.parseInt(textField_ScoreRun1Judge1.getText());
					iaScoreRun1[1]= Integer.parseInt(textField_ScoreRun1Judge2.getText());
					iaScoreRun1[2]= Integer.parseInt(textField_ScoreRun1Judge3.getText());
					iaScoreRun1[3]= Integer.parseInt(textField_ScoreRun1Judge4.getText());
					iaScoreRun1[4]= Integer.parseInt(textField_ScoreRun1Judge5.getText());
					iaScoreRun1[5]= Integer.parseInt(textField_ScoreRun1Judge6.getText());
					iaScoreRun2[0]= Integer.parseInt(textField_ScoreRun2Judge1.getText());
					iaScoreRun2[1]= Integer.parseInt(textField_ScoreRun2Judge2.getText());
					iaScoreRun2[2]= Integer.parseInt(textField_ScoreRun2Judge3.getText());
					iaScoreRun2[3]= Integer.parseInt(textField_ScoreRun2Judge4.getText());
					iaScoreRun2[4]= Integer.parseInt(textField_ScoreRun2Judge5.getText());
					iaScoreRun2[5]= Integer.parseInt(textField_ScoreRun2Judge6.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				// calculate score for the athlete
				fOverallScoreRun1= CalculatingOverallScore(iaScoreRun1);
				fOverallScoreRun2= CalculatingOverallScore(iaScoreRun2);
				// release iFinal score
				fFinalScore= findingFinalScore(fOverallScoreRun1, fOverallScoreRun2);
				// set scores for jLabels
				jLabel_OverallScoreRun1.setText(String.valueOf(fOverallScoreRun1));
				jLabel_OverallScoreRun2.setText(String.valueOf(fOverallScoreRun2));
				jLabel_FinalScore.setText(String.valueOf(fFinalScore));
			}
		});
		btnCalculate.setBounds(293, 227, 108, 26);
		frame.getContentPane().add(btnCalculate);
		
		btnQuit = new JButton("Quit");
		// Implement Quit button
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					// exit program 
					System.exit(0);
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		});
		btnQuit.setBounds(293, 274, 108, 26);
		frame.getContentPane().add(btnQuit);
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
