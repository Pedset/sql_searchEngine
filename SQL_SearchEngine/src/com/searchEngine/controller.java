package com.searchEngine;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.searchEngine.data;

/**
 * Servlet implementation class controller
 */
@WebServlet("/controller")
public class controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		data dt = new data();
		
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9 ‰ƒÂ≈ˆ÷]*$");
		
		Matcher matcher = pattern.matcher(request.getParameter("txt").toString());
		
		
		if (matcher.matches()) {
			try {
				out.print(dt.allSearch(request.getParameter("txt").toString()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			out.print("<br><p style=\"color:red;\">" + "Only alphanumeric characters allowed" + "</p><br><br>");
			
			request.getRequestDispatcher("index.jsp").include(request, response);
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
