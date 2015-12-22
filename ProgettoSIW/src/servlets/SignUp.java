package servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import project.BeanDBManager;
import project.JsonDBManager;
import project.beans.User;

@WebServlet("/SignUp")
public class SignUp extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		User user = null;
		if (request.getParameter("social").equals("true"))
			user  = new User(request.getParameter("name"), "",
					request.getParameter("email"), "", request.getParameter("imageUrl"), request.getParameter("social"));
		else
			user  = new User(request.getParameter("username"), request.getParameter("password"),
					request.getParameter("email"), "", "",request.getParameter("social"));
		BeanDBManager.getInstance().saveUser(user);
		BeanDBManager.getInstance().setImageProfile(user);
		
		HttpSession session = request.getSession(true);
		session.setAttribute("user", user);
		response.setCharacterEncoding("UTF-8");

		response.getWriter().write(user.toJSON());
	}
}
