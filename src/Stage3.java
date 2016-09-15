import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JLabel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;




public class Stage3 {

	private JFrame frame;
	// Declare constant variables
	private static final int SCORE_NUMS = 6;
	private static final int REMAINING_NUMS = 4;
	private static final int MAX_SCORE = 100;
	private static final int BAR_WIDTH = 20;
	private static final int ORIGINAL_X = 90; // x of the first bar
	private static final int ORIGINAL_Y = 300;	// y of the base line
	private static final int SPACE_BARS = 25;  // distance from x of previous bar and next bar
	private static final int SPACE_R1_R2 = 240; // space between bar graph of run 1 and run 2
	private static final int ABOVE_SPACE = 5; // to create a space between a bar and a score on it and so on
	private static final int BELOW_SPACE = 25; // to create a space between bar and score and so on
	private static final int BESIDE_SPACE = 5; // to create a suitable space when drawing text
	private static final int B_A_SPACE =25; // to create a space before the first bar and after the last bar for the horizontal average scored line 
	private static final int BARCHART_WIDTH = 145;
	private static final int UNIT_DISTANCE = 20; // unit distance in vertical line
	private static final int Y_AXIS_HEIGHT = 250; // the extra distance from the marked position for score of 100 
	// Declare JLIST variable
	private static JList<String> jList_Athlete;
	private static Vector<Athlete> vListAthlete= new Vector<Athlete>();
	private static int iSize = 0, iIndex = -1;
	private static String sFileName = "scores.txt";	
	private static DefaultListModel<String> lModel = new DefaultListModel<String>();
	// create MyPanel variable
	private static MyPanel myDrawing;
	//	public static JList<String> tempList = new JList<String>(lModel);
	// Declare a BufferedImage and its corresponding Graphics2D context object
	private BufferedImage img;
	private Graphics2D g2dImg;

	// Other global variables
	int iX, iY, iWidth, iHeight, iX1, iY1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stage3 window = new Stage3();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public Stage3() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	protected void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblAthleteList = new JLabel("Athlete List");
		lblAthleteList.setBounds(26, 11, 150, 28);
		frame.getContentPane().add(lblAthleteList);
		
		JLabel lblNewLabel = new JLabel("Overall Score Run 1:");
		lblNewLabel.setBounds(10, 357, 131, 28);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblOverallScoreRun = new JLabel("Overall Score Run 2:");
		lblOverallScoreRun.setBounds(10, 384, 131, 28);
		frame.getContentPane().add(lblOverallScoreRun);
		
		JLabel lblFinalScore = new JLabel("Final Score:");
		lblFinalScore.setBounds(10, 411, 98, 28);
		frame.getContentPane().add(lblFinalScore);
		
		JLabel lblNationality = new JLabel("Nationality:");
		lblNationality.setBounds(10, 329, 75, 28);
		frame.getContentPane().add(lblNationality);
		
		JLabel jLabel_Nationality = new JLabel("");
		jLabel_Nationality.setBounds(81, 333, 131, 20);
		frame.getContentPane().add(jLabel_Nationality);
		
		JLabel jLabel_OverallScoreRun1 = new JLabel("");
		jLabel_OverallScoreRun1.setBounds(145, 357, 55, 28);
		frame.getContentPane().add(jLabel_OverallScoreRun1);
		
		JLabel jLabel_OverallScoreRun2 = new JLabel("");
		jLabel_OverallScoreRun2.setBounds(145, 384, 55, 28);
		frame.getContentPane().add(jLabel_OverallScoreRun2);
		
		JLabel jLabel_FinalScore = new JLabel("");
		jLabel_FinalScore.setBounds(145, 411, 55, 28);
		frame.getContentPane().add(jLabel_FinalScore);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// exit application
				System.exit(0);
			}
		});
		btnQuit.setBounds(26, 505, 107, 28);
		frame.getContentPane().add(btnQuit);
		
		// create list data for jList
		createList();
		// Declare variable for jList
		jList_Athlete = new JList<String>(lModel);
		jList_Athlete.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				//myDrawing.clear(myDrawing.getGraphics());
				if(iIndex!=-1){
					myDrawing.clear(myDrawing.getGraphics());
				}
				iIndex= jList_Athlete.getSelectedIndex();
				// update scores for labels
				jLabel_Nationality.setText(vListAthlete.elementAt(iIndex).sNationality);
				jLabel_OverallScoreRun1.setText(String.valueOf(vListAthlete.elementAt(iIndex).fOverallScoreRun1));
				jLabel_OverallScoreRun2.setText(String.valueOf(vListAthlete.elementAt(iIndex).fOverallScoreRun2));
				jLabel_FinalScore.setText(String.valueOf(vListAthlete.elementAt(iIndex).fFinalScore));
				// draw bar chart and lines
				//myDrawing.clear(myDrawing.getGraphics());
				myDrawing.repaint();
				//myDrawing.getGraphics().drawImage(img, 0, 0, null);
				//if(bFlag==true) {
				//	bFlag=false;
				//}
			}
		});
		
		// declare a scroll pane for JList
		JScrollPane jScrollPane_ListAthlete = new JScrollPane(jList_Athlete);
		jScrollPane_ListAthlete.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jScrollPane_ListAthlete.setBounds(10, 50, 145, 268);
		frame.getContentPane().add(jScrollPane_ListAthlete);
		
		// set viewpoint of scroll pane same as jList
		jScrollPane_ListAthlete.setViewportView(jList_Athlete);
		
		// create an object of chartJPanel
		myDrawing = new MyPanel();
		myDrawing.setBackground(Color.WHITE);
		myDrawing.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		myDrawing.setBounds(200, 50, 500, 400);
		frame.getContentPane().add(myDrawing);
		
		img = new BufferedImage(myDrawing.getWidth(), 
                myDrawing.getHeight(), 
                BufferedImage.TYPE_INT_RGB);

		// Get its graphics context. A graphics context of a particular object allows us to draw on it.
		g2dImg = (Graphics2D)img.getGraphics();
		
		// Draw a filled white rectangle on the entire area to clear it.
		g2dImg.setPaint(Color.WHITE);
		g2dImg.fill(new Rectangle2D.Double(0, 0, img.getWidth(), img.getHeight()));
	}
	
	public static void createList() throws IOException{
		readFileTxt(sFileName);
		iSize= vListAthlete.size();
		for(int iI=0; iI < iSize; iI++ ){
			lModel.addElement(nameArray(vListAthlete)[iI]);
		}
	}
	// method read file txt and create a vector containing athlete elements
	public static void readFileTxt(String sName) throws IOException{
		String sLine;
		String sSubLine;
		int iPosition=0;
		int iCount=0;
		//
		BufferedReader in = new BufferedReader(new 
				FileReader(sName));
		while((sLine = in.readLine())!= null){
			iCount=0;
			// create new athlete element
			Athlete aElement= new Athlete();
			while(sLine.length() != 0){
				iCount++;
				if(sLine.contains(";")){ // check that is not the last run score
					iPosition=sLine.indexOf(";");
					// a sub line cut from beginning of line to the first appearance of a semicolon  
					sSubLine=sLine.substring(0, iPosition);
					// cut sSubLine from sLine
					sLine= sLine.substring(iPosition+1, sLine.length());
				}
				else{
					// the last run score is assigned to sSubLine
					sSubLine = sLine;
				}
				if (iCount==1){
					aElement.sName= sSubLine;
				}
				else if(iCount==2){
					aElement.sNationality= sSubLine;
				}
				else if (iCount==3){
					aElement.iaScoreRun1= convertStringToIntArray(sSubLine);
				}
				else {
					aElement.iaScoreRun2= convertStringToIntArray(sSubLine);
					break;
				}
			}
			// calculate overall score of each run and final score
			aElement.fOverallScoreRun1= overallScore(aElement.iaScoreRun1);
			aElement.fOverallScoreRun2= overallScore(aElement.iaScoreRun2);
			aElement.fFinalScore= finalScore(aElement.fOverallScoreRun1, aElement.fOverallScoreRun2);
			// add new element to vector
			vListAthlete.addElement(aElement);
		}
		// close file
		in.close();
	}
	// return an array of name from a vector<Athlete> 
	public static String[] nameArray(Vector<Athlete> vListAthlete){
		int iSize = vListAthlete.size();
		String[] nameList = new String[iSize];
		for(int iI=0; iI < iSize; iI++){
			nameList[iI]= vListAthlete.elementAt(iI).sName;  
		}
		return nameList;
	}
	// method to change a number string to an integer array
	public static int[] convertStringToIntArray(String sNumbers){
		int iLastPosition = SCORE_NUMS-1;
		int[] iArray = new int[SCORE_NUMS];
		for(int iI=0; iI < iLastPosition; iI++){
			iArray[iI]= Integer.parseInt(sNumbers.substring(0, sNumbers.indexOf(',')));
			sNumbers=sNumbers.substring(sNumbers.indexOf(',')+1, sNumbers.length());
		}
		iArray[iLastPosition]=Integer.parseInt(sNumbers);
		return iArray;
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
	public static float overallScore(int[] iArray){
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
	public static float finalScore(float fOverallScore1, float fOverallScore2){
		if (fOverallScore1 > fOverallScore2)
			return fOverallScore1;
		else
			return fOverallScore2;
	}
	// function to return true if the number is max/minimum in an array
	public boolean isMaxMin(int iNum, int[] iArray){
		if (iNum == maxInArray(iArray) || iNum == minInArray(iArray)){
			return true;
		}
		else{
			return false;
		}
	}
	class MyPanel extends JPanel 
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * 
		 */
		// Declare and instantiate an ellipse and rectangle object of 
		// dimension zero. We will later just change their dimension 
		// and location for efficiency.
		 private Rectangle2D.Double rectangle;
		 private boolean minFound = false; // to make sure only one minimum bar and 
		 private boolean maxFound = false; //one max bar will be changed the color 
		 
		 /**
		  * Override paintComnponent method with our draw commands
		  * 
		  */
		 public void paintComponent(Graphics g) 
		 {
			 //Must be called to draw the JPanel control. 
			 // As a side effect, it also clears it.
			 super.paintComponent(g);
			 
			 
			 
			 // draw bars for run 1
			 if(iIndex != -1){
				 // draw X and Y axis
				 this.drawAxisXY();
				 // draw bar chart for run 1
				 for (int iX= ORIGINAL_X, iJ = 0; iJ < SCORE_NUMS; iX+= SPACE_BARS, iJ+=1){
					 //set color
					 if (vListAthlete.elementAt(iIndex).iaScoreRun1[iJ] == maxInArray(vListAthlete.elementAt(iIndex).iaScoreRun1) && !maxFound){
						 g2dImg.setPaint(Color.ORANGE);
						 maxFound = true;
					 }
					 else if(vListAthlete.elementAt(iIndex).iaScoreRun1[iJ] == minInArray(vListAthlete.elementAt(iIndex).iaScoreRun1) && !minFound){
						 g2dImg.setPaint(Color.ORANGE);
						 minFound = true;
					 }
					 else{
						 g2dImg.setPaint(Color.gray);
					 }
					 // set y, width, height
					 iY = ORIGINAL_Y - vListAthlete.elementAt(iIndex).iaScoreRun1[iJ]*2;
					 iWidth = BAR_WIDTH;
					 iHeight = vListAthlete.elementAt(iIndex).iaScoreRun1[iJ]*2;
					 rectangle = new Rectangle2D.Double(iX, iY, iWidth, iHeight); 
					// rectangle.
					// fill rectangle 	 							
					 g2dImg.fill(rectangle);
					// g2dImg.setPaint(Color.yellow);
					 // draw the score
					 g2dImg.setPaint(Color.gray);
					 g2dImg.drawString(String.valueOf(vListAthlete.elementAt(iIndex).iaScoreRun1[iJ]), iX, iY - ABOVE_SPACE);
					 // draw the order of judge
					 g2dImg.setPaint(Color.black);
					 g2dImg.drawString(String.valueOf(iJ+1), iX + BESIDE_SPACE, ORIGINAL_Y + BELOW_SPACE);
					 // draw name of run
					 if (iJ == 2){
						 g2dImg.drawString("Run 1 Scores",iX , ORIGINAL_Y + 2*BELOW_SPACE);
					 }
				 }
				 // reset maxfound and minfound to false
				 maxFound = false;
				 minFound = false;
				 // draw the horizontal line of average score Run 1
				 g2dImg.setPaint(Color.red);
				 iX = ORIGINAL_X - 2*B_A_SPACE;
				 iY = ORIGINAL_Y - (int)(overallScore(vListAthlete.elementAt(iIndex).iaScoreRun1)*2);
				 iX1 = iX + BARCHART_WIDTH + 3*B_A_SPACE;
				 iY1 = ORIGINAL_Y - (int)(overallScore(vListAthlete.elementAt(iIndex).iaScoreRun1)*2);
				 g2dImg.drawLine(iX, iY, iX1, iY1);
				 // draw the overall score number 
				 g2dImg.setPaint(Color.gray);
				 g2dImg.drawString(String.valueOf(overallScore(vListAthlete.elementAt(iIndex).iaScoreRun1)), iX, iY - ABOVE_SPACE);
				 // draw bar chart for run 2
				 for (int iX= ORIGINAL_X + SPACE_R1_R2, iJ = 0; iJ < SCORE_NUMS; iX+= SPACE_BARS, iJ+=1){
					//set color
					 if (vListAthlete.elementAt(iIndex).iaScoreRun2[iJ] == maxInArray(vListAthlete.elementAt(iIndex).iaScoreRun2) && !maxFound){
						 g2dImg.setPaint(Color.ORANGE);
						 maxFound = true;
					 }
					 else if(vListAthlete.elementAt(iIndex).iaScoreRun2[iJ] == minInArray(vListAthlete.elementAt(iIndex).iaScoreRun2) && !minFound){
						 g2dImg.setPaint(Color.ORANGE);
						 minFound = true;
					 }
					 else{
						 g2dImg.setPaint(Color.gray);
					 }
					 // set y, width, height
					 iY = ORIGINAL_Y - vListAthlete.elementAt(iIndex).iaScoreRun2[iJ]*2;
					 iWidth = BAR_WIDTH;
					 iHeight = vListAthlete.elementAt(iIndex).iaScoreRun2[iJ]*2;
					 rectangle = new Rectangle2D.Double(iX, iY, iWidth, iHeight); 
					// fill rectangle 	 							
					 g2dImg.fill(rectangle);
					 // draw the score
					 g2dImg.setPaint(Color.gray);
					 g2dImg.drawString(String.valueOf(vListAthlete.elementAt(iIndex).iaScoreRun2[iJ]), iX, iY - ABOVE_SPACE);
					// draw the order of judge
					 g2dImg.setPaint(Color.black);
					 g2dImg.drawString(String.valueOf(iJ+1), iX + BESIDE_SPACE,ORIGINAL_Y + BELOW_SPACE);
					 // draw the name of run
					 if (iJ == 2){
						 g2dImg.drawString("Run 2 Scores",iX , ORIGINAL_Y + 2*BELOW_SPACE);
					 }
				 }
				 // reset maxfound and minfound to false
				 maxFound = false;
				 minFound = false;
				// draw the horizontal line of average score Run 2
				 g2dImg.setPaint(Color.red);
				 iX = ORIGINAL_X + SPACE_R1_R2 - 2*B_A_SPACE;
				 iY = ORIGINAL_Y - (int)(overallScore(vListAthlete.elementAt(iIndex).iaScoreRun2)*2);
				 iX1 = iX + BARCHART_WIDTH + 3*B_A_SPACE;
				 iY1 = ORIGINAL_Y - (int)(overallScore(vListAthlete.elementAt(iIndex).iaScoreRun2)*2);
				 g2dImg.drawLine(iX, iY, iX1, iY1);
				// draw the overall score number 
				 g2dImg.setPaint(Color.gray);
				 g2dImg.drawString(String.valueOf(overallScore(vListAthlete.elementAt(iIndex).iaScoreRun2)), iX, iY - ABOVE_SPACE);
			 }
			 // Transfer the image from the BufferedImage to the JPanel to make it visible.
			 g.drawImage(img, 0, 0, null);
		 }
		 // method draw  X axis and Y Axis
		 protected void drawAxisXY(){
			// draw base line
			 g2dImg.setPaint(Color.black);
			 g2dImg.drawLine(0, ORIGINAL_Y, img.getWidth(), ORIGINAL_Y);
			 g2dImg.drawString("Judge", B_A_SPACE, ORIGINAL_Y + BELOW_SPACE);
			 // draw vertical line
			 g2dImg.drawLine(B_A_SPACE, ORIGINAL_Y - Y_AXIS_HEIGHT , B_A_SPACE , ORIGINAL_Y);
			 // draw marked line
			 for (int iY=ORIGINAL_Y, iI=0; iY >= MAX_SCORE; iY-= 2*UNIT_DISTANCE, iI+=1){
				 this.drawSLine(B_A_SPACE, iY);
				 if(UNIT_DISTANCE*iI <10){
					 g2dImg.drawString(String.valueOf(UNIT_DISTANCE*iI),BESIDE_SPACE*2, iY);
				 }
				 else if(UNIT_DISTANCE*iI <100){
					 g2dImg.drawString(String.valueOf(UNIT_DISTANCE*iI),BESIDE_SPACE, iY);
				 }
				 else{
					 g2dImg.drawString(String.valueOf(UNIT_DISTANCE*iI),BESIDE_SPACE/2, iY);
				 }
			 }
		 }
		 // method draw a small horizontal line 5 pixel for marking scores on the vertical line 
		 protected void drawSLine(int iX, int iY ){ // iX is iX of the vertical line, iY is iY of marked line
			 g2dImg.setPaint(Color.black);
			 g2dImg.drawLine(iX-2, iY, iX+2, iY);
		 }
		 
		 // super.paintComponent clears off screen pixmap,
		 // since we're using double buffering by default.
		 protected void clear(Graphics g) {
			 super.paintComponent(g);
			 
			 // Also clear the BufferedImage object by drawing a white coloured filled rectangle all over.
			 g2dImg.setPaint(Color.WHITE);
			 g2dImg.fill(new Rectangle2D.Double(0, 0, img.getWidth(), img.getHeight()));
		 }
	}
}

