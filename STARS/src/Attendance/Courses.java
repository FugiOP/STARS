package Attendance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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

@WebServlet("/Attendance/Courses")
public class Courses extends HttpServlet {
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
		String currentCourse = request.getParameter("courseName");
		request.getSession().setAttribute("currentCourse", currentCourse);
		
		int instructorID = (int) request.getSession().getAttribute("instructorID");
		
		ArrayList<CourseModel> courses = (ArrayList<CourseModel>) request.getSession().getAttribute("courses");
		for(int i = 0; i<courses.size(); i++){
			if(courses.get(i).getCourseName().equals(currentCourse)){
				int hour = courses.get(i).getHour();
				int min = courses.get(i).getMin();
				Time courseDeadline;
				String ampm;
				if(hour<12){
					courseDeadline = new Time(hour,min,00);
					ampm="AM";
				}else{
					courseDeadline = new Time(hour-12,min,00);
					ampm="PM";
				}
				request.getSession().setAttribute("courseDeadline", "Deadline for this course is: "+courseDeadline+" "+ampm);
			}
		}
		response.sendRedirect("Swipe");
	}

}
/*Connection c = null;
try{
	String url = "jdbc:mysql://localhost/stars";
	String username = "";
	String password = "";
    
	c = DriverManager.getConnection(url,username,password);
	Statement stmt = c.createStatement();
	
	//Searches for the deadline of the selected course
	ResultSet rs = stmt.executeQuery("select deadline from class where course_name='"+currentCourse+"' AND instructor_id='"+instructorID+"'");
	while(rs.next()){
		Time courseDeadline = rs.getTime("deadline");
		
		request.getSession().setAttribute("courseDeadline", courseDeadline);
	}
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
 }*/