package Attendance;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.File;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet("/Attendance/toexcel")
public class toexcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public toexcel() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init( ServletConfig config ) throws ServletException
    {
        super.init( config );

        try
        {
            Class.forName( "com.mysql.jdbc.Driver" );
        }
        catch( ClassNotFoundException e )
        {
            throw new ServletException( e );
        }
    }
	//try using ver 3.9 for apache poi
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Date date = new Date();
		 //"%1$s %2$tB %2$td, %2$tY",date
	//	 String sheetsname = date.toString();
		PrintWriter out = response.getWriter();
//        out.print( "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0" );
//        out.print( "Transitional//EN\">\n" );
//        out.print( "<html><head><title>To Excel!</title></head>\n<body>" );
		
		 Connection c = null;
		// XSSFWorkbook workbook = new XSSFWorkbook(); 
	        try
	        {
	        	
	        	String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu98";
				String username = "cs3220stu98";
				String password = "!SagHy*C";
				c = DriverManager.getConnection( url, username, password );
	            
	            Statement stmt = c.createStatement();
	            ResultSet rs = stmt.executeQuery( "select * from student" );
	  	     
	  	     
	          XSSFWorkbook workbook = new XSSFWorkbook(); 
	  	      XSSFSheet spreadsheet = workbook.createSheet("student db");
	  	      XSSFRow row=spreadsheet.createRow(1);
	  	      XSSFCell cell;
	  	      cell=row.createCell(1);
	  	      cell.setCellValue("firstName");
	  	      cell=row.createCell(2);
	  	      cell.setCellValue("lastName");
	  	      cell=row.createCell(3);
	  	      cell.setCellValue("cin");
	  	      cell=row.createCell(4);
	  	      cell.setCellValue("user_id");
	  	      
	  	    
	  	      int i=2;
	  	      while(rs.next())
	  	      {

	  	         row=spreadsheet.createRow(i);
	  	         cell=row.createCell(1);
	  	         cell.setCellValue(rs.getString("firstName"));
	  	         cell=row.createCell(2);
	  	         cell.setCellValue(rs.getString("lastName"));
	  	         cell=row.createCell(3);
	  	         cell.setCellValue(rs.getInt("cin"));
	  	         cell=row.createCell(4);
	  	         cell.setCellValue(rs.getInt("user_id"));
	  	         i++;
	  	      }
	  	      FileOutputStream fout = new FileOutputStream(
	  	      new File("exceldatabase.xlsx"));
	  	      workbook.write(fout);
	  	      fout.close();
	  	   // response.sendRedirect("Login");
	  	      out.println("excel sheet has been  written successfully");
	  	      //maybe remove c.close
	  	     c.close();
	        }
	        catch( SQLException e )
	        {
	            throw new ServletException( e );
	        }
	        finally
	        {
	            try
	            {
	                if( c != null ) c.close();
	            }
	            catch( SQLException e )
	            {
	                throw new ServletException( e );
	            }
	        }

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
