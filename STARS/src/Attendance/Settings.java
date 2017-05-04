package Attendance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Attendance/Settings")
public class Settings extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("Courses");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int instructorID = (int) request.getSession().getAttribute("instructorID");
		String courseName = request.getParameter("courseName");

		int action = Integer.parseInt(request.getParameter("action"));
		Connection c = null;
		
		switch(action){
		case 1:
			int hour = Integer.parseInt(request.getParameter("hour"));
			int min = Integer.parseInt(request.getParameter("min"));
			Time deadline = new Time(hour,min,00);
			
			try{
				String url = "jdbc:mysql://localhost/stars";
				String username = "";
				String password = "";
	            
	            String sql = "UPDATE class SET deadline=? WHERE instructor_id='"+instructorID+"' AND course_name='"+courseName+"'";
	            c = DriverManager.getConnection( url, username, password );
	            
	            PreparedStatement pstmt = c.prepareStatement( sql );
	            pstmt.setTime(1, deadline);
	
	            pstmt.executeUpdate();
			 }catch( SQLException e ){
				 throw new ServletException( e );
		     }
		     finally{
	            try{
	                if( c != null ) c.close();
	            }
	            catch( SQLException e ){
	                throw new ServletException( e );
	            }
		     }
			break;
		case 2:
			try{
				String url = "jdbc:mysql://localhost/stars";
				String username = "";
				String password = "";
	            
				String sql = "DELETE from class WHERE course_name=? AND instructor_id=?";
	            c = DriverManager.getConnection( url, username, password );
	            
	            PreparedStatement pstmt = c.prepareStatement( sql );
	            pstmt.setString(1, courseName);
	            pstmt.setInt(2, instructorID);
	
	            pstmt.executeUpdate();
	            
	            sql = "DROP TABLE "+courseName+"_"+instructorID+"";
	            pstmt = c.prepareStatement( sql );
	            pstmt.executeUpdate();

			 }catch( SQLException e ){
				 throw new ServletException( e );
		     }
		     finally{
	            try{
	                if( c != null ) c.close();
	            }
	            catch( SQLException e ){
	                throw new ServletException( e );
	            }
		     }
			break;
		}
		response.sendRedirect("Courses");
	}

}
