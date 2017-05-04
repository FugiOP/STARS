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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Attendance/AddCourse")
public class AddCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init( ServletConfig config ) throws ServletException{
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher( "/WEB-INF/Courses.jsp" ).forward(request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<CourseModel> courses = (ArrayList<CourseModel>) request.getSession().getAttribute("courses");

		String courseName = request.getParameter("courseName");	
		int hour = -1;
		int min = -1;
		int instructorID = -1;
		Time deadline = null;
		boolean pass = true;
		
		request.getSession().setAttribute("invalidName", null);
		request.getSession().setAttribute("duplicateError", null);
		request.getSession().setAttribute("addError", null);
				
		try{
			hour = Integer.parseInt(request.getParameter("hour"));
			min = Integer.parseInt(request.getParameter("min"));
			instructorID = (int) request.getSession().getAttribute("instructorID");
		}catch(Exception e){
			request.getSession().setAttribute("addError", "You must input a Course Name and Deadline Time");
		}
		
		if(courseName!=null && courseName!="" && hour != -1 && min != -1 && instructorID != -1){
			
			Connection c = null;
			try{
				String url = "jdbc:mysql://localhost/stars";
				String username = "";
				String password = "";
	            
				c = DriverManager.getConnection( url, username, password );
				Statement stmt = c.createStatement();
				
				String query = "SELECT course_name FROM class WHERE instructor_id='"+instructorID+"'";
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs.next()){
					if(rs.getString("course_name").equals(courseName)){
						request.getSession().setAttribute("duplicateError", "The course already exists!");
						pass = false;
					}
				}
				
				if(pass){ 
		            String name = courseName+"_"+instructorID;
		            String sql = "CREATE TABLE "+name+"(cin int,firstname VARCHAR(32),lastname VARCHAR(32))";
		            PreparedStatement pstmt = c.prepareStatement(sql);
		            pstmt.executeUpdate();
		            
		            sql = "INSERT INTO class (course_name,instructor_id,deadline) VALUES(?,?,?)";
		            deadline = new Time(hour,min,00);
		            pstmt = c.prepareStatement( sql );
		            
		            pstmt.setString(1, courseName);
		            pstmt.setInt(2,instructorID);
		            pstmt.setTime(3, deadline);
		            pstmt.executeUpdate();
		            
				}
				
			 }catch( SQLException e ){
				request.getSession().setAttribute("invalidName", "Course name must contain chacaters A-Z, a-z, 0-9 or ' _ '");
				pass=false;
		     }
		     finally{
	            try{
	                if( c != null ) c.close();
	            }
	            catch( SQLException e ){
	                throw new ServletException( e );
	            }
		     }
		}
		
		if(pass){
			courses.add(new CourseModel(courseName,hour,min));
			request.getSession().setAttribute("courses", courses);
		}
		response.sendRedirect("Courses");
	}

}
