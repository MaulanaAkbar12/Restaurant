<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <!-- Link to cdnjs -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

    <!-- Link to swiper from cdn -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css"/>

    <!-- Link to sweetalert2 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">

    <!-- Box icon -->
    <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">

    <!-- Link to css -->
    <link rel="stylesheet" href="CSS/Restoku.css">
    <title>Restoku</title>
    <title>Edit Form</title>
</head>
<body>
<%@ page import="Controller.ReservationDAO, Model.Reservation" %>

        <%
            String id = request.getParameter("id_reservation");
            Reservation rs = ReservationDAO.getRecordById(Integer.parseInt(id));
        %>


             <!-- Order -->
             <section class="order" id="order">
                <h3 class="sub-heading">Edit</h3>
                <h1 class="heading">Reservation</h1>

                <form id= "reservationForm" action="editReservation.jsp" method="post" onsubmit="return validateForm()">
                    <input type="hidden" name="id_reservation" value="<%= rs.getId_reservation() %>"/>
                    <div class="inputbox">
                        <div class="input">
                            <label>Your Name</label>
                            <input type="text" name="first_name" value="<%= rs.getFirst_name()%>" id="first-input">
                        </div>

                        <div class="input">
                            <label>Your Last Name</label>
                            <input type="text" name="last_name" value="<%= rs.getLast_name()%>" id="last-input">
                        </div>

                        <div class="input">
                            <label>Your Phone Number</label>
                            <input type="number" name="phone" value="<%= rs.getPhone()%>" id="number-input">
                        </div>

                        <div class="input">
                            <label>Your Email</label>
                            <input type="email" name="email" value="<%= rs.getEmail()%>" id="email-input">
                        </div>

                        <div class="input">
                            <label>How Many People</label>
                            <input type="number" name="people" value="<%= rs.getPeople()%>" id="people-input">
                        </div>

                        <div class="input">
                            <label>Date</label>
                            <input type="date" name="date" id="datePicker" value="<%= rs.getDate()%>">
                        </div>

                          <div class="input">
                            <label>Time (10 am - 8 pm)</label>
                            <select name="time" id="timePicker">
                                <option value="10:00">10:00 AM</option>
                                <option value="11:00">11:00 AM</option>
                                <option value="12:00">12:00 PM</option>
                                <option value="13:00">01:00 PM</option>
                                <option value="14:00">02:00 PM</option>
                                <option value="15:00">03:00 PM</option>
                                <option value="16:00">04:00 PM</option>
                                <option value="17:00">05:00 PM</option>
                                <option value="18:00">06:00 PM</option>
                                <option value="19:00">07:00 PM</option>
                                <option value="20:00">08:00 PM</option>
                            </select>
                        </div>
                    </div>
                    <input type="submit" value="edit reservation" class="btn" id="booknow">
                </form> 
            </section>
            <!-- js sweetalert2 -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <!-- js swiper -->
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>

    <!-- link js -->
    <script src="JS/edit.js"></script>
</body>
</html>
