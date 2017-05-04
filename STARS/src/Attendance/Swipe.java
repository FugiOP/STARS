
package Attendance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Attendance/Swipe")
public class Swipe extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e){
			throw new ServletException(e);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher( "/WEB-INF/Swipe.jsp" ).forward(request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action").toString();

		switch (action){
		case "readSwipe":
			String swipeData = request.getParameter("swipeData");
			readSwipe(request,response,swipeData);
			break;
		case "update":
			update(request,response);
			break;
		case "login":
			String username = request.getParameter("username");
			String pw = request.getParameter("pw");
			login(request,response,username,pw);
			break;
		case "select":
			String courseSelected = request.getParameter("courseSelected");
			select(request,response,courseSelected);
			break;
		}
		
		request.getRequestDispatcher( "/WEB-INF/Swipe.jsp" ).forward(request, response );
	}
	
	private void readSwipe(HttpServletRequest request, HttpServletResponse response,String swipeData)throws ServletException, IOException{
		String lastName = null;
		String firstName = null;
		int cin = 0;
		String selectedCourse = request.getSession().getAttribute("selectedCourse").toString();
		int id = (int) request.getSession().getAttribute("currentID");
		String status = "";
		
		
		
		try{
			lastName = swipeData.substring(swipeData.indexOf('^')+1, swipeData.indexOf('/'));
			firstName = swipeData.substring(swipeData.indexOf('/')+1, swipeData.indexOf('^', swipeData.indexOf('/')+1));
			String cinString = swipeData.substring(swipeData.length()-10, swipeData.length()-1);
			cin=Integer.parseInt(cinString);
			request.getSession().setAttribute("swipeError", null);
		}catch(Exception e){
			request.getSession().setAttribute("swipeError", "Invalid Swipe. Please Re-Swipe Your Card.");
		}
		
		Connection c = null;
		
		try{
			String url = "jdbc:mysql://localhost/stars";
			String username = "";
			String password = "";
			
			c = DriverManager.getConnection(url,username,password);
			Statement stmt = c.createStatement();
			
			String query = "SELECT * FROM "+selectedCourse+"_"+id+"";
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			
			SimpleDateFormat sdf = new SimpleDateFormat("M_d_yyyy");
			Date d = new Date();
			String date = sdf.format(d);
			boolean pass = false;
			int columnCount = rsmd.getColumnCount();
			
			for(int i = 1; i<=columnCount; i++){
				if(date.equals(rsmd.getColumnName(i))){
					pass=true;
				}
			}
			
			if(pass){
	            String sql = "INSERT INTO "+selectedCourse+"_"+id+"(cin,firstName,lastName,"+date+") VALUES(?,?,?,?)";
	            PreparedStatement pstmt = c.prepareStatement( sql );
	            pstmt.setInt(1, cin);
	            pstmt.setString(2, firstName);
	            pstmt.setString(3, lastName);
	            pstmt.setString(4, "O");
	
	            pstmt.executeUpdate();
			}else{
				String sql = "ALTER TABLE "+selectedCourse+"_"+id+" ADD "+date+" VARCHAR(60)";
				PreparedStatement pstmt = c.prepareStatement( sql );
	
	            pstmt.executeUpdate();
	            
	            boolean exists = false;
	            
	            query = "SELECT cin FROM "+selectedCourse+"_"+id+"";
	            rs = stmt.executeQuery(query);
	            
	            while(rs.next()){
	            	if(rs.getInt("cin")==cin){
	            		exists = true;
	            	}
	            }
	            
	            if(!exists){
	            sql = "INSERT INTO "+selectedCourse+"_"+id+"(cin,firstName,lastName,"+date+") VALUES(?,?,?,?)";
	            pstmt = c.prepareStatement( sql );
	            pstmt.setInt(1, cin);
	            pstmt.setString(2, firstName);
	            pstmt.setString(3, lastName);
	            pstmt.setString(4, status);
	
	            pstmt.executeUpdate();
	            }else{
	            	sql = "UPDATE "+selectedCourse+"_"+id+" SET "+date+"="+status+" where cin="+cin+"";
	            	pstmt = c.prepareStatement( sql );
	            	pstmt.executeUpdate();
	            }
			}
			
		}catch(SQLException e){
			throw new ServletException(e);
		}finally{
			try{
				if(c != null) c.close();
			}catch(SQLException e){
				throw new ServletException(e);
			}
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response){
		
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response,String username, String pw)throws ServletException, IOException{
		ArrayList<CourseModel> courses = new ArrayList<>();
		boolean pass = false;
		int id = -1;
		
		Connection c = null;
		try{
			String url = "jdbc:mysql://localhost/stars";
            
			c = DriverManager.getConnection(url,"","");
			Statement stmt = c.createStatement();
			
			//Queries all instructors that are in DB
			ResultSet rs = stmt.executeQuery("select * from instructors");
			while(rs.next()){
				if(rs.getString("username").equals(username) && rs.getString("password").equals(pw)){
					request.getSession().setAttribute("instructorName", rs.getString("last_name"));
					id = rs.getInt("id");
					pass = true;
				}
			}
			
			if(pass){
				//Queries for all courses that the user has under his ID
				rs = stmt.executeQuery("select * from class where instructor_id = '"+id+"'");
				while(rs.next()){
					courses.add(new CourseModel(rs.getString("course_name"),null, rs.getTime("deadline").getHours(),rs.getTime("deadline").getMinutes()));
				}
				request.getSession().setAttribute("courseSelectView", true);
				request.getSession().setAttribute("loginView", false);
				request.getSession().setAttribute("courses", courses);
				request.getSession().setAttribute("currentID", id);
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
	}
	
	private void select(HttpServletRequest request, HttpServletResponse response,String selectedCourse){
		ArrayList<CourseModel> courses = (ArrayList<CourseModel>) request.getSession().getAttribute("courses");
		
		for(int i = 0; i<courses.size(); i++){
			if(courses.get(i).getCourseName().equals(selectedCourse)){
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
				request.getSession().setAttribute("ampm", ampm);
				request.getSession().setAttribute("courseDeadline", courseDeadline);
			}
		}
		request.getSession().setAttribute("selectedCourse", selectedCourse);
		request.getSession().setAttribute("swipeView", true);
		request.getSession().setAttribute("courseSelectView", false);
	}
}