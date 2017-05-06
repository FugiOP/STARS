package Attendance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

@WebServlet("/Attendance/Settings")
public class Settings extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("Courses");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int instructorID = (int) request.getSession().getAttribute("instructorID");
		String courseName = request.getParameter("courseName");

		String action = request.getParameter("action")==null?"":request.getParameter("action");
		Connection c = null;
		
		switch(action){
		case "change":
			int hour = Integer.parseInt(request.getParameter("hour"));
			int min = Integer.parseInt(request.getParameter("min"));
			String ampm = request.getParameter("ampm");
			
			Time deadline = new Time(hour,min,00);
			
			if(ampm.equals("PM")){
				deadline = new Time(hour+12,min,00);
			}
			
			try{
				String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu98";
				String username = "cs3220stu98";
				String password = "!SagHy*C";
	            
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
			response.sendRedirect("Courses");
			break;
		case "remove":
			request.getSession().setAttribute("courseToRemove", courseName);
			request.getSession().setAttribute("removeCourse", "yes");
			request.getRequestDispatcher( "/WEB-INF/DeleteCourse.jsp" ).forward(request, response );

			break;
		}
	}

}
