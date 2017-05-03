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
		
		ArrayList<CourseModel> courses = new ArrayList<>();
		int instructorID = (int) request.getSession().getAttribute("instructorID");
		
		Connection c = null;
		
		try{
			String url = "jdbc:mysql://localhost/stars";
			String username = "";
			String password = "";
            
			c = DriverManager.getConnection(url,username,password);
			Statement stmt = c.createStatement();
			
			//Queries all instructors that are in DB
			ResultSet rs = stmt.executeQuery("select * from class where instructor_id = '"+instructorID+"'");
			while(rs.next()){
				courses.add(new CourseModel(rs.getString("course_name"),null, rs.getTime("deadline").getHours(),rs.getTime("deadline").getMinutes()));
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
	     }
		request.getSession().setAttribute("courses", courses);
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
		if(action.equals("toggleOnline")){
			toggleOnline(request,response,instructorID);
			response.sendRedirect("Courses");
		}

//		ArrayList<CourseModel> courses = (ArrayList<CourseModel>) request.getSession().getAttribute("courses");
//		for(int i = 0; i<courses.size(); i++){
//			if(courses.get(i).getCourseName().equals(currentCourse)){
//				int hour = courses.get(i).getHour();
//				int min = courses.get(i).getMin();
//				Time courseDeadline;
//				String ampm;
//				if(hour<12){
//					courseDeadline = new Time(hour,min,00);
//					ampm="AM";
//				}else{
//					courseDeadline = new Time(hour-12,min,00);
//					ampm="PM";
//				}
//				request.getSession().setAttribute("ampm", ampm);
//				request.getSession().setAttribute("courseDeadline", courseDeadline);
//			}
//		}
	}
	
	private void toggleOnline(HttpServletRequest request, HttpServletResponse response,int id){
		String status = request.getServletContext().getAttribute(""+id+"").toString();
		if(status.equals("offline")){
			request.getServletContext().setAttribute(""+id+"", "online");
		}else if (status.equals("online")){
			request.getServletContext().setAttribute(""+id+"", "offline");
		}
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response,int id){
		request.getServletContext().setAttribute(""+id+"", "offline");
		request.getSession().invalidate();
	}

}