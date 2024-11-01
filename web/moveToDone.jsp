<%@page import="Controller.ReservationDAO"%>
<%@page import="java.io.*"%>
<%
    String idStr = request.getParameter("id_reservation");
    int id = Integer.parseInt(idStr);
    int status = ReservationDAO.moveToDone(id);

    if (status > 0) {
        response.sendRedirect("admin.jsp");
    } else {
        out.println("Failed to move the reservation.");
    }
%>
