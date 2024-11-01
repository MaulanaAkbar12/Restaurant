package Servlet;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServletTest {
    private LoginServlet loginServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @Before
    public void setUp() {
        loginServlet = new LoginServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    public void testLoginSuccess() throws ServletException, IOException {
        // Setup data and behavior
        when(request.getParameter("username")).thenReturn("correctUser");
        when(request.getParameter("password")).thenReturn("correctPass");
        when(request.getRequestDispatcher("success.jsp")).thenReturn(dispatcher);

        // Call the method under test
        loginServlet.doPost(request, response);

        // Verify that the servlet forwarded to success.jsp
        verify(dispatcher).forward(request, response);
        verify(request).setAttribute("status", "success");
    }

    @Test
    public void testLoginFail() throws ServletException, IOException {
        // Setup data and behavior
        when(request.getParameter("username")).thenReturn("wrongUser");
        when(request.getParameter("password")).thenReturn("wrongPass");
        when(request.getRequestDispatcher("error.jsp")).thenReturn(dispatcher);

        // Call the method under test
        loginServlet.doPost(request, response);

        // Verify that the servlet forwarded to error.jsp
        verify(dispatcher).forward(request, response);
        verify(request).setAttribute("status", "fail");
    }

    @Test
    public void testSQLExceptionHandling() throws ServletException, IOException {
        // Simulate exception in DBConnection
        when(request.getParameter("username")).thenReturn("anyUser");
        when(request.getParameter("password")).thenReturn("anyPass");
        doThrow(new ServletException("DB error")).when(response).sendRedirect("error.jsp");

        // Call the method under test
        loginServlet.doPost(request, response);

        // Verify that the servlet redirected to error.jsp on exception
        verify(response).sendRedirect("error.jsp");
    }
}


