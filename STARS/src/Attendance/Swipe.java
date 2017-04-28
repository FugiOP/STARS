
package Attendance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
		if(request.getSession().getAttribute("user")!=null){
			String idParam = request.getParameter("ID")==null?"yes":request.getParameter("ID");
			Boolean ID = true;
			if(idParam.equals("yes")){
				ID = true;
			}else{
				ID=false;
			}
			request.getSession().setAttribute("ID", ID);
			request.getRequestDispatcher( "/WEB-INF/Swipe.jsp" ).forward(request, response );
		}else{
			response.sendRedirect("Login");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("user")!= null){
		
			//Verifies data read from card reader
		String swipe = request.getParameter("swipe")==null?"":request.getParameter("swipe");
		
		if(request.getSession().getAttribute("settingError")==null){
			if(swipe != "" && swipe != null){
				
				//Get current time;
		        SimpleDateFormat hour = new SimpleDateFormat("HH");
		        SimpleDateFormat minute = new SimpleDateFormat("mm");
		        Date d = new Date();

		        int currentHour = Integer.parseInt(hour.format(d));
		        int currentMinute = Integer.parseInt(minute.format(d));
		        
	
				String lastName = "";
				String firstName = "";
				String cin = "";
				boolean pass = false;
				try{
					lastName = swipe.substring(swipe.indexOf('^')+1, swipe.indexOf('/'));
					firstName = swipe.substring(swipe.indexOf('/')+1, swipe.indexOf('^', swipe.indexOf('/')+1));
					cin = swipe.substring(swipe.length()-10, swipe.length()-1);
					request.getSession().setAttribute("swipeError", null);
				}catch(Exception e){
					request.getSession().setAttribute("swipeError", "Invalid Swipe. Please Re-Swipe Your Card.");
					request.getRequestDispatcher( "/WEB-INF/Swipe.jsp" ).forward(request, response );
				}
				
				//Verifies if student exists n database
				Connection c = null;
				
				try{
					String url = "jdbc:mysql://localhost/stars";
					String username = "";
					String password = "";
					
					c = DriverManager.getConnection(url,username,password);
					Statement stmt = c.createStatement();
					
					String query = "select * from student where cin = "+cin;
					ResultSet rs = stmt.executeQuery(query);
					
					while(rs.next()){
						if(rs.getString("cin")==cin){
							pass=true;
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
				
				StudentModel student = new StudentModel(firstName,lastName,cin);
				request.setAttribute("swipe", student);
				
				Time courseDeadline = (Time) request.getSession().getAttribute("courseDeadline");
				int lateHour = Integer.parseInt(hour.format(courseDeadline));
				int lateMinute = Integer.parseInt(minute.format(courseDeadline));

				if(currentHour<lateHour){
					request.getSession().setAttribute("status", "On Time!");
				}else if(currentHour == lateHour){
					if(currentMinute <= lateMinute){
						request.getSession().setAttribute("status", "On Time!");
					}else{
						request.getSession().setAttribute("status", "Late!");
					}
				}else{
					request.getSession().setAttribute("status", "Late!");
				}

				
			}else{
				request.setAttribute("swipe", null);
			}
		}
		request.getRequestDispatcher( "/WEB-INF/Swipe.jsp" ).forward(request, response );
		}else{
			response.sendRedirect("Login");
		}

	}
}