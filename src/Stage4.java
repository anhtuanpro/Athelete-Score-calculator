import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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



public class Stage4 {

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
	private static final int LINE_NUMBER = 5; // the number of line in each report page
	// Declare JLIST variable
	private static JList<String> jList_Athlete;
	// declare two arrays to save scores for each run
	int[] iaScoreRun1 = new int[SCORE_NUMS];
	int[] iaScoreRun2 = new int[SCORE_NUMS];
	private static int iIndex = -1;
	// create MyPanel variable, MyPanel is a class which extends JPanel class 
	private static MyPanel myDrawing;
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
					Stage4 window = new Stage4();
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
	public Stage4() throws IOException {
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
		
		// button quit
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// exit application
				System.exit(0);
			}
		});
		btnQuit.setBounds(171, 505, 107, 28);
		frame.getContentPane().add(btnQuit);
		
		
		// Declare variable jList_Athelete to hold athletes's name
		jList_Athlete = new JList<String>();
		setJList();// run method to get athletes's name from database and show them on GUI
		// drawing bar chart when item on list selected
		jList_Athlete.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				// clear drawing area to update a new drawing
				if(iIndex!=-1){
					myDrawing.clear(myDrawing.getGraphics());
				}
				iIndex= jList_Athlete.getSelectedIndex();
				// read mdb file and take data from row of the selected item
				Connection conn;
				try {
					File file = new File("scores.mdb");
					String path = file.getAbsolutePath(); // get absolute path
					conn = DriverManager.getConnection(
					        "jdbc:ucanaccess://"+path);
					Statement s = conn.createStatement();
					ResultSet rs = s.executeQuery("SELECT * FROM tblScores WHERE athleteName = '"+jList_Athlete.getSelectedValue()+"'");
					rs.next();
					// update scores for labels
					jLabel_Nationality.setText(rs.getString(2));
					// take score of each run and put in an array
					for(int iI=0, iJ=3; iI < SCORE_NUMS; iI++, iJ++){
						iaScoreRun1[iI] = rs.getInt(iJ);
					}
					for(int iI=0, iJ=9; iI < SCORE_NUMS; iI++, iJ++){
						iaScoreRun2[iI] = rs.getInt(iJ);
					}
					jLabel_OverallScoreRun1.setText(String.valueOf(overallScore(iaScoreRun1)));
					jLabel_OverallScoreRun2.setText(String.valueOf(overallScore(iaScoreRun2)));
					jLabel_FinalScore.setText(String.valueOf(finalScore(overallScore(iaScoreRun1),overallScore(iaScoreRun2))));
					// draw bar chart and lines
					//update bar graph
					myDrawing.repaint();
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
		
		// print report button
		JButton btnPrintReport = new JButton("Print Report");
		btnPrintReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// connect to database
				Connection conn;
				try {
					File file = new File("scores.mdb");
					String path = file.getAbsolutePath();
					conn = DriverManager.getConnection(
					        "jdbc:ucanaccess://"+path);
					Statement s = conn.createStatement();
					ResultSet rs = s.executeQuery("SELECT * FROM tblScores ORDER BY athleteName ASC ");
					printReport(rs, "Athlete", "database_report.txt");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnPrintReport.setBounds(10, 505, 107, 28);
		frame.getContentPane().add(btnPrintReport);
		
		img = new BufferedImage(myDrawing.getWidth(), 
                myDrawing.getHeight(), 
                BufferedImage.TYPE_INT_RGB);

		// Get its graphics context
		g2dImg = (Graphics2D)img.getGraphics();
		
		// Draw a filled white rectangle on the entire area to clear it.
		g2dImg.setPaint(Color.WHITE);
		g2dImg.fill(new Rectangle2D.Double(0, 0, img.getWidth(), img.getHeight()));
	}
	
	// method read file mdb and set JList
	public static void setJList(){
		Connection conn;
		try {
			File file = new File("scores.mdb");
			String path = file.getAbsolutePath();
			conn = DriverManager.getConnection(
			        "jdbc:ucanaccess://"+path);
			Statement s = conn.createStatement();
			// get all data in tblScores table
			ResultSet rs = s.executeQuery("SELECT * FROM tblScores");
			// create a vector to hold athlete's name list
			Vector<String> sAthleteNames = new Vector<String>();
			while (rs.next()) {
				// add name of the athlete to vector where rs is staying
				sAthleteNames.addElement(rs.getString(1));
			}
			jList_Athlete.setListData(sAthleteNames);	// Update List Box with athletes's name
			rs.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
					 if (iaScoreRun1[iJ] == maxInArray(iaScoreRun1) && !maxFound){
						 g2dImg.setPaint(Color.ORANGE);
						 maxFound = true;
					 }
					 else if(iaScoreRun1[iJ] == minInArray(iaScoreRun1) && !minFound){
						 g2dImg.setPaint(Color.ORANGE);
						 minFound = true;
					 }
					 else{
						 g2dImg.setPaint(Color.gray);
					 }
					 // set y, width, height
					 iY = ORIGINAL_Y - iaScoreRun1[iJ]*2;
					 iWidth = BAR_WIDTH;
					 iHeight = iaScoreRun1[iJ]*2;
					 rectangle = new Rectangle2D.Double(iX, iY, iWidth, iHeight); 
					// rectangle.
					// fill rectangle 	 							
					 g2dImg.fill(rectangle);
					// g2dImg.setPaint(Color.yellow);
					 // draw the score
					 g2dImg.setPaint(Color.gray);
					 g2dImg.drawString(String.valueOf(iaScoreRun1[iJ]), iX, iY - ABOVE_SPACE);
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
				 iY = ORIGINAL_Y - (int)(overallScore(iaScoreRun1)*2);
				 iX1 = iX + BARCHART_WIDTH + 3*B_A_SPACE;
				 iY1 = ORIGINAL_Y - (int)(overallScore(iaScoreRun1)*2);
				 g2dImg.drawLine(iX, iY, iX1, iY1);
				 // draw the overall score number 
				 g2dImg.setPaint(Color.gray);
				 g2dImg.drawString(String.valueOf(overallScore(iaScoreRun1)), iX, iY - ABOVE_SPACE);
				 // draw bar chart for run 2
				 for (int iX= ORIGINAL_X + SPACE_R1_R2, iJ = 0; iJ < SCORE_NUMS; iX+= SPACE_BARS, iJ+=1){
					//set color
					 if (iaScoreRun2[iJ] == maxInArray(iaScoreRun2) && !maxFound){
						 g2dImg.setPaint(Color.ORANGE);
						 maxFound = true;
					 }
					 else if(iaScoreRun2[iJ] == minInArray(iaScoreRun2) && !minFound){
						 g2dImg.setPaint(Color.ORANGE);
						 minFound = true;
					 }
					 else{
						 g2dImg.setPaint(Color.gray);
					 }
					 // set y, width, height
					 iY = ORIGINAL_Y - iaScoreRun2[iJ]*2;
					 iWidth = BAR_WIDTH;
					 iHeight = iaScoreRun2[iJ]*2;
					 rectangle = new Rectangle2D.Double(iX, iY, iWidth, iHeight); 
					// fill rectangle 	 							
					 g2dImg.fill(rectangle);
					 // draw the score
					 g2dImg.setPaint(Color.gray);
					 g2dImg.drawString(String.valueOf(iaScoreRun2[iJ]), iX, iY - ABOVE_SPACE);
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
				 iY = ORIGINAL_Y - (int)(overallScore(iaScoreRun2)*2);
				 iX1 = iX + BARCHART_WIDTH + 3*B_A_SPACE;
				 iY1 = ORIGINAL_Y - (int)(overallScore(iaScoreRun2)*2);
				 g2dImg.drawLine(iX, iY, iX1, iY1);
				// draw the overall score number 
				 g2dImg.setPaint(Color.gray);
				 g2dImg.drawString(String.valueOf(overallScore(iaScoreRun2)), iX, iY - ABOVE_SPACE);
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
	// implement print the report function
	private void pageHead(String sHeader, PrintWriter pw) 
	{
		pw.println(sHeader);	// Print the header string
		
		// Print a number of = signs under it as a way of underlining
		// Work out how many = we need from the sHeader length
		for (int iI = 0; iI < sHeader.length(); iI++)
			pw.print("=");
		pw.println();
		
		// Print an empty line at the end
		pw.println();
	}
	
	/**
	 * Print footer of the report page
	 * @param iPageNum
	 * @param pw
	 */
	private void pageFooter(int iPageNum, PrintWriter pw) {
		pw.println();	// Print an empty line at the beginning of the footer
		
		// Print the page number
		pw.println("--- page "+ iPageNum +" ---");
		
		// Print 3 lines after it to simulate the beginning of a new report page
		for (int iI = 0; iI < 3; iI++)
			pw.println();
	}
	
	// this method is used to print body page of one page in report
	private void pageBody(ResultSet rs, int iFirstRow, int iNumRows, PrintWriter pw) throws SQLException {
		int iLastRow = iFirstRow + LINE_NUMBER - 1;
		String sLineUp = "|";
		// As our last report page may not have the exact number of rows
		// required per page, we need to fill it up with blank rows.
		// Work out how many we need.1
		int iBlankRows = 0;
		if (iLastRow > iNumRows - 1)
		{
			iBlankRows = iLastRow - (iNumRows - 1);
			iLastRow = iNumRows - 1;
		}
		
		// Print the column heads
		ResultSetMetaData rmsd = rs.getMetaData();
		String columnHeads = String.format("%1$1s %2$-30s %3$1s %4$-20s %5$1s %6$-11s %7$1s %8$-11s %9$1s",
				sLineUp, rmsd.getColumnName(1), sLineUp, rmsd.getColumnName(2), sLineUp,
				"Total Run 1", sLineUp, "Total Run 2", sLineUp);
		pw.println(columnHeads);
		// print a bottom line for header
		for(int iI = 0; iI <= 84; iI++){
			pw.print("-");
		}
		pw.println();
		// Print the report lines for the current report page
		for (int iRow = iFirstRow; iRow <= iLastRow; iRow++)
		{
			int iTotalRun1 = 0;
			int iTotalRun2 = 0;
			// count total scores of run 1 and run 2
			for(int iI = 3; iI < 15; iI++){
				if (iI < 9){
					iTotalRun1 += rs.getInt(iI);
				}
				else{
					iTotalRun2 += rs.getInt(iI);
				}
			}
			// print data rows
			String reportLine = String.format("%1$1s %2$-30s %3$1s %4$-20s %5$1s %6$11s %7$1s %8$11s %9$1s", sLineUp, 
					rs.getString(1), sLineUp, rs.getString(2), sLineUp, String.valueOf(iTotalRun1), sLineUp, String.valueOf(iTotalRun2), sLineUp); 
			pw.println(reportLine);
			rs.next();
		}
		
		// Now print the blank rows, if needed
		for (int iRow = 0; iRow < iBlankRows; iRow++)
			pw.println();
	
	}
	
	// print database report method 
	private void printReport(ResultSet rs, String sHeader, String sFileName) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(sFileName)));
        	int iNumRows = 0;
        	// Get the number of rows
        	while(rs.next()){
        		iNumRows++;
        	}
        	// because the cursor of rs go to the last and ucanaccess not support function first() and so on, thus connect database again
        	// to get new result set
        	Connection conn = null;
	        try {
	        	File file = new File("scores.mdb");
				String path = file.getAbsolutePath();
				conn = DriverManager.getConnection(
				        "jdbc:ucanaccess://"+path);
				Statement s = conn.createStatement();
				// get database result following alphabetical order
				ResultSet rsNew = s.executeQuery("SELECT * FROM tblScores ORDER BY athleteName ASC"); 
	    		rsNew.next();
	    		int iNumPages = iNumRows / LINE_NUMBER;	// Work out how many report pages we need
	            if (iNumPages * LINE_NUMBER < iNumRows)
	            	iNumPages++;
	    		int iFirstRow = 0;				// Need to keep track of starting row for next report page
	    			// Write report
    			for (int iPageNum = 0; iPageNum < iNumPages; iPageNum++) {
    				pageHead(sHeader, out);
    				pageBody(rsNew, iFirstRow, iNumRows, out);
    				pageFooter(iPageNum + 1, out);
    				iFirstRow += LINE_NUMBER;
    			} 			
	    		out.close();
	           
	        } catch(SQLException s) {
	            System.out.println(s);
	        } finally {
	            if(conn != null) {
	                try {
	                    conn.close();		// Close connection to database
	                } catch(SQLException ignore) {}
	            }
	        }
        				
			
			
			// Show a pop up message when completed
			JOptionPane.showMessageDialog(frame, "Database query report written to "+ 
					sFileName +".");

		} catch(IOException ioex) {
			System.out.println(ioex);
		} catch(SQLException s) {
			System.out.println(s);
		}

	}
}

