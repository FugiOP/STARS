package Attendance;
import java.io.File;


import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelDatabase 
{
   public static void main(String[] args) throws Exception 
   {

	   			Class.forName("com.mysql.jdbc.Driver");
	   			Connection connect = DriverManager.getConnection( 
			   "jdbc:mysql://cs3.calstatela.edu/cs3220stu98" , 
			   "cs3220stu98" , 
			   "!SagHy*C"
	   															);
	   			Statement statement = connect.createStatement();
	   			ResultSet resultSet = statement
	   					.executeQuery("select * from student");
	   			XSSFWorkbook workbook = new XSSFWorkbook(); 
	   			XSSFSheet spreadsheet = workbook
    		  //"C:\\Users\\clinica digital\\Desktop\\starsss\\
	   					.createSheet("student db");
	   			XSSFRow row=spreadsheet.createRow(1);
	   			XSSFCell cell;
	   			  cell=row.createCell(1);
	   			  cell.setCellValue("FName");
			      cell=row.createCell(2);
			      cell.setCellValue("LName");
			      cell=row.createCell(3);
			      cell.setCellValue("CIN");
			      cell=row.createCell(4);
			      cell.setCellValue("user_id");
      
    
			      int i=2;
		      while(resultSet.next())
		      {
         
 
		         row=spreadsheet.createRow(i);
		
		         cell=row.createCell(1);
		         cell.setCellValue(resultSet.getString("firstName"));
		         cell=row.createCell(2);
		         cell.setCellValue(resultSet.getString("lastName"));
		         cell=row.createCell(3);
		         cell.setCellValue(resultSet.getInt("cin"));
		         cell=row.createCell(4);
		         cell.setCellValue(resultSet.getInt("user_id"));
		         i++;
		      }
			      FileOutputStream out = new FileOutputStream(
			      new File("exceldatabase.xlsx"));
			      workbook.write(out);
			      out.close();
			      System.out.println(
			      "exceldatabase.xlsx written successfully");

      }
}