package Attendance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Attendance/DeleteCourse")
public class DeleteCourse extends HttpServlet {
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
		response.sendRedirect("Courses");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action")==null?"":(String) request.getParameter("action");

		if(request.getSession().getAttribute("removeCourse")!=null){
			if(action.equals("yes")){
				removeCourse(request,response);
			}else{
				response.sendRedirect("Courses");
			}
		}else{
			response.sendRedirect("Courses");
		}
	}

	private void removeCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int instructorID = request.getSession().getAttribute("instructorID")==null?-1:(int) request.getSession().getAttribute("instructorID");
		String courseName = (String) request.getSession().getAttribute("courseToRemove");
		
		if(instructorID != -1 && courseName != null){
			Connection c = null;
			try{
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu98";
			String username = "cs3220stu98";
			String password = "!SagHy*C";
	        
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
		}
		response.sendRedirect("Courses");

	}
}
