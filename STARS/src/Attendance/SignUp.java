package Attendance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Attendance/SignUp")
public class SignUp extends HttpServlet {
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

		request.getSession().setAttribute("usernameError", null);
		request.getSession().setAttribute("nameError", null);
		request.getSession().setAttribute("pwError", null);
		request.getSession().setAttribute("pwMismatch", null);
		request.getSession().setAttribute("usernameTaken", null);
		request.getRequestDispatcher( "/WEB-INF/SignUp.jsp" ).forward(request, response );

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String firstName = null;
		String lastName = null;
		String userName = null;
		String pw1 = null;
		String pw2 = null;
		boolean pass = true;
		
		request.getSession().setAttribute("usernameError", "Username cannot be left blank!");
		request.getSession().setAttribute("nameError", "First and Last name cannot be left blank!");
		request.getSession().setAttribute("pwError", "Password cannot be left blank!");
		request.getSession().setAttribute("pwMismatch", "Your passwords don't match!");

		firstName = request.getParameter("firstName");	
		lastName = request.getParameter("lastName");
		userName = request.getParameter("username");
		pw1 = request.getParameter("pw1");
		pw2 = request.getParameter("pw2");

		if(firstName.equals("") || lastName.equals("")){
			pass = false;
		}else{
			request.getSession().setAttribute("nameError", null);
		}
		
		if(userName.equals("")){
			pass = false;
		}else{
			request.getSession().setAttribute("usernameError", null);
		}
		
		if(pw1.equals("") || pw2.equals("")){
			pass = false;
		}else{
			request.getSession().setAttribute("pwError", null);
		}
		
		if(pw1.equals(pw2)){
			request.getSession().setAttribute("pwMismatch", null);
		}else{
			pass = false;
		}
		
		if(pass){
			Connection c = null;
			try{
				String url = "jdbc:mysql://localhost/stars";
				String username = "";
				String password = "";
				
				c = DriverManager.getConnection( url, username, password );			
				Statement stmt = c.createStatement();
				
				String query1 = "SELECT username FROM instructors";
				ResultSet rs = stmt.executeQuery(query1);
				
				while(rs.next()){
					if(rs.getString("username").equals(userName)){
						pass = false;
					}
				}
				
				if(pass){
		            String sql1 = "INSERT INTO instructors (username,password,first_name,last_name) VALUES(?,?,?,?)";
		            
		            PreparedStatement pstmt = c.prepareStatement( sql1 );
		            pstmt.setString(1,userName);
		            pstmt.setString(2,pw1);
		            pstmt.setString(3,firstName);
		            pstmt.setString(4, lastName);
		            pstmt.executeUpdate();
		            request.getSession().setAttribute("usernameTaken", null);
				}else{
					request.getSession().setAttribute("usernameTaken", "That username is taken!");
					request.getRequestDispatcher( "/WEB-INF/SignUp.jsp" ).forward(request, response );
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
			response.sendRedirect("Login");
		}else{
			request.getRequestDispatcher( "/WEB-INF/SignUp.jsp" ).forward(request, response );
		}
	}

}
