package Attendance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
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
		
		int instructorID = request.getSession().getAttribute("instructorID")==null?-1:(int) request.getSession().getAttribute("instructorID");

		if(instructorID != -1){
			Connection c = null;
			try{
				String url = "jdbc:mysql://localhost/stars";
				String username = "";
				String password = "";
	            
				c = DriverManager.getConnection(url,username,password);
				Statement stmt = c.createStatement();
				
				//Queries for all courses that the user has under his ID
				ResultSet rs = stmt.executeQuery("select * from class where instructor_id = '"+instructorID+"'");
				while(rs.next()){
					courses.add(new CourseModel(rs.getString("course_name"), rs.getTime("deadline").getHours(),rs.getTime("deadline").getMinutes()));
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
		}
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
		if(action.equals("export")){
			export(request,response,currentCourse,instructorID);
		}
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response,int id){
		request.getSession().invalidate();
	}
	private void export(HttpServletRequest request, HttpServletResponse response, String currentCourse, int instructorID) throws ServletException{
		String course = currentCourse;
		Connection c = null;
		
		String filePath = ""+course+"_Attendance.csv";
		try{
			String url = "jdbc:mysql://localhost/stars";
			String username = "";
			String password = "";
            
			c = DriverManager.getConnection(url,username,password);
			Statement stmt = c.createStatement();
			
			//Queries all instructors that are in DB
			ResultSet rs = stmt.executeQuery("select * from "+course+"_"+instructorID+"");
			ResultSetMetaData rsmd = rs.getMetaData();
			
			//-----------------------------------------------------//
			BufferedWriter bw = null;
			FileWriter fw = null;
			try {

				fw = new FileWriter(filePath);
				bw = new BufferedWriter(fw);
				for(int i = 1;i<=rsmd.getColumnCount(); i++){
					bw.write(rsmd.getColumnName(i));
					bw.write(" , ");
				}
				bw.newLine();
				
				while(rs.next()){
					for(int i = 1;i<=rsmd.getColumnCount(); i++){
						bw.write(rs.getObject(i).toString());
						bw.write(" , ");
					}
					bw.newLine();
				}

				System.out.println("Done");
				
			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					if (bw != null)
						bw.close();

					if (fw != null)
						fw.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}

			}
			//----------------------------------------------//
			
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
		
		try{
			File downloadFile = new File(filePath);
			FileInputStream inStream = new FileInputStream(downloadFile);
	         
	        // obtains ServletContext
	        ServletContext context = getServletContext();
	         
	        // gets MIME type of the file
	        String mimeType = context.getMimeType(filePath);
	        if (mimeType == null) {        
	            // set to binary type if MIME mapping not found
	            mimeType = "application/octet-stream";
	        }
	         
	        // modifies response
	        response.setContentType(mimeType);
	        response.setContentLength((int) downloadFile.length());
	         
	        // forces download
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
	        response.setHeader(headerKey, headerValue);
	         
	        // obtains response's output stream
	        OutputStream outStream = response.getOutputStream();
	         
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	         
	        while ((bytesRead = inStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	         
	        inStream.close();
	        outStream.close();    
		}catch(IOException e){

		}
	}
}