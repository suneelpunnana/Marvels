import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BMIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		double weight = Float.parseFloat(request.getParameter("weight"));
		double height = Float.parseFloat(request.getParameter("height"));
		double bm = bmi(weight,height);
		request.setAttribute("bmi", bm);
		RequestDispatcher rd = request.getRequestDispatcher("bmi.jsp");
		rd.forward(request,response);
	}
	public double bmi(double w,double h) {
		double a = h*h;
		double b = w;
		double bmi = b/a;
		return bmi;
	}
}
