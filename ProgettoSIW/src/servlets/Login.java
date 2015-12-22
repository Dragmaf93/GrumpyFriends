package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import project.beans.User;
import project.BeanDBManager;

@WebServlet("/Login")
public class Login extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		User user = null;
//		if (request.getParameter("social").equals("0"))
//		user = new User(request.getParameter("username"), request.getParameter("password"), request.getParameter("email"), "", "",
//				request.getParameter("social"));
//		else
			user = BeanDBManager.getInstance().getUser(req.getParameter("username"), req.getParameter("password"));
		
		if (user != null) {
			HttpSession session = req.getSession();
			session.setAttribute("user", user);

			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(user.toJSON());

		}
		else {
			resp.getWriter().write("{'user':'null'}");
		}


	}

}