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
		String action = request.getParameter("action");
		if(action.equals("logout")){
			logout(request,response,instructorID);
			response.sendRedirect("Login");
		}
		if(action.equals("viewAttendance")){
			response.sendRedirect("View");
		}
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response,int id){
		request.getSession().invalidate();
	}
}