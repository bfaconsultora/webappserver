package com.bfaconsultora;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(value="/holamundo")
public class SimpleServlet extends HttpServlet {
	private static final long serialVersionUID = -4751096228274971485L;
	@Override
	protected void doGet(HttpServletRequest reqest, HttpServletResponse response) 
			throws ServletException, IOException {
		response.getWriter().println("Hola Mundo! Esto es un servlet");
	}
}
