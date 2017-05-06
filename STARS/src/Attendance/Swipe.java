
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
		if(request.getSession().getAttribute("instructorName")!=null){
			if(request.getSession().getAttribute("selectedCourse") != null){
				update(request,response);
				request.getRequestDispatcher( "/WEB-INF/SwipeView.jsp" ).forward(request, response );
			}else{
				request.getRequestDispatcher( "/WEB-INF/CourseView.jsp" ).forward(request, response );
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		        response.setDateHeader("Expires", 0);
			}
		}else{
			request.getRequestDispatcher( "/WEB-INF/LoginView.jsp" ).forward(request, response );
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	        response.setDateHeader("Expires", 0);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action").toString();

		switch (action){
		case "readSwipe":
			String swipeData = request.getParameter("swipeData");
			readSwipe(request,response,swipeData);
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
			
		case "logout":
			request.getSession().invalidate();
			break;
		}
		
		response.sendRedirect("Swipe");
	}
	
	private void readSwipe(HttpServletRequest request, HttpServletResponse response,String swipeData)throws ServletException, IOException{
		String lastName = null;
		String firstName = null;
		int cin = 0;
		String selectedCourse = request.getSession().getAttribute("selectedCourse").toString();
		int id = (int) request.getSession().getAttribute("currentID");
		String status = getStatus(request,response);
		boolean validSwipe = true;
		
		try{
			lastName = swipeData.substring(swipeData.indexOf('^')+1, swipeData.indexOf('/'));
			firstName = swipeData.substring(swipeData.indexOf('/')+1, swipeData.indexOf('^', swipeData.indexOf('/')+1));
			String cinString = swipeData.substring(swipeData.length()-10, swipeData.length()-1);
			cin=Integer.parseInt(cinString);
			request.getSession().setAttribute("swipeError", null);
		}catch(Exception e){
			validSwipe=false;
			request.getSession().setAttribute("animation", "fail");
			request.getSession().setAttribute("swipeError", "Invalid Swipe. Please Re-Swipe Your Card.");
		}
		
		if(validSwipe){
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
				query = "SELECT cin FROM "+selectedCourse+"_"+id+"";
	            rs = stmt.executeQuery(query);
	            
	            boolean exists = false;
	            
	            while(rs.next()){
	            	if(rs.getInt("cin")==cin){
	            		exists = true;
	            	}
	            }
	            
	            if(!exists){
		            String sql = "INSERT INTO "+selectedCourse+"_"+id+"(cin,firstname,lastname,"+date+") VALUES(?,?,?,?)";
		            PreparedStatement pstmt = c.prepareStatement( sql );
		            pstmt.setInt(1, cin);
		            pstmt.setString(2, firstName);
		            pstmt.setString(3, lastName);
		            pstmt.setString(4, status);
		
		            pstmt.executeUpdate();
	            }else{
	            	String sql = "UPDATE "+selectedCourse+"_"+id+" SET "+date+"='"+status+"' WHERE cin='"+cin+"'";
		            PreparedStatement pstmt = c.prepareStatement( sql );
	            	pstmt.executeUpdate();
	            }
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
	            	sql = "INSERT INTO "+selectedCourse+"_"+id+"(cin,firstname,lastname,"+date+") VALUES(?,?,?,?)";
		            pstmt = c.prepareStatement( sql );
		            pstmt.setInt(1, cin);
		            pstmt.setString(2, firstName);
		            pstmt.setString(3, lastName);
		            pstmt.setString(4, status);
		
		            pstmt.executeUpdate();
	            }else{
	            	sql = "UPDATE "+selectedCourse+"_"+id+" SET "+date+"='"+status+"' WHERE cin='"+cin+"'";
	            	pstmt = c.prepareStatement( sql );
	            	pstmt.executeUpdate();
	            }
			}
			request.getSession().setAttribute("animation", "pass");
		}catch(SQLException e){
			request.getSession().setAttribute("animation", "fail");
		}finally{
			try{
				if(c != null) c.close();
			}catch(SQLException e){
				throw new ServletException(e);
			}
		}
		}else{
			request.getSession().setAttribute("animation", "fail");
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException{
		ArrayList<CourseModel> courses = new ArrayList<>();
		
		Connection c = null;
		try{
			int id = (int) request.getSession().getAttribute("currentID");
			String courseName = (String) request.getSession().getAttribute("selectedCourse");
			
			String url = "jdbc:mysql://localhost/stars";
            
			c = DriverManager.getConnection(url,"","");
			Statement stmt = c.createStatement();

			//Queries for all courses that the user has under his ID
			ResultSet rs = stmt.executeQuery("select deadline from class where instructor_id = '"+id+"' AND course_name = '"+courseName+"'");
			while(rs.next()){
				Time courseDeadline;
				String ampm;
				
				if(rs.getTime("deadline").getHours()>12){
					courseDeadline = new Time(rs.getTime("deadline").getHours()-12,rs.getTime("deadline").getMinutes(),00);
					ampm="PM";
					request.getSession().setAttribute("lateTime", new Time(rs.getTime("deadline").getHours(),rs.getTime("deadline").getMinutes(),00));
				}else{
					courseDeadline = new Time(rs.getTime("deadline").getHours(),rs.getTime("deadline").getMinutes(),00);
					ampm="AM";
					request.getSession().setAttribute("lateTime", new Time(rs.getTime("deadline").getHours(),rs.getTime("deadline").getMinutes(),00));
				}
				request.getSession().setAttribute("ampm", ampm);
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
		}
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
					courses.add(new CourseModel(rs.getString("course_name"), rs.getTime("deadline").getHours(),rs.getTime("deadline").getMinutes()));
				}
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
	}
	private String getStatus(HttpServletRequest request, HttpServletResponse response){
		String status = null;
		
		SimpleDateFormat hour = new SimpleDateFormat("HH");
	    SimpleDateFormat minute = new SimpleDateFormat("mm");
	    Date d = new Date();
	    
	    int currentHour = Integer.parseInt(hour.format(d));
	    int currentMinute = Integer.parseInt(minute.format(d));
		
	    Time lateTime = (Time) request.getSession().getAttribute("lateTime");
		int lateHour = Integer.parseInt(hour.format(lateTime));
		int lateMinute = Integer.parseInt(minute.format(lateTime));

		if(currentHour<lateHour){
			status = "O";
		}else if(currentHour == lateHour){
			if(currentMinute <= lateMinute){
				status = "O";
			}else{
				status = "L";
			}
		}else{
			status = "L";
		}
		
		return status;
	}
}