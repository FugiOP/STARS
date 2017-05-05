package Attendance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Attendance/TEST")
public class TEST extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("user")!=null){
			if(request.getSession().getAttribute("courseIsSelected") != null){
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
		String action = request.getParameter("action");
		
		if(action.equals("login")){
			String username = request.getParameter("username");
			String pw = request.getParameter("pw");
			
			if(username.equals("fugi") && pw.equals("123")){
				request.getSession().setAttribute("user", "YUP");
			}
		}
		
		if(action.equals("courseSelection")){
			request.getSession().setAttribute("courseIsSelected", "YEP");
		}

		if(action.equals("logout")){
			request.getSession().invalidate();
		}
		
		response.sendRedirect("TEST");
	}

}
