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

import project.JsonDBManager;

@WebServlet("/GetMenuDaily")
public class MenuServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
		
		String dailyMenu = JsonDBManager.getInstance().getMenuDaily(dateFormat.format(date));
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(dailyMenu);
	}
}
