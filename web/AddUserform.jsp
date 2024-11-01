    <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Link to cdnjs -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    
    <!-- Link to swiper from cdn -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css"/>
    
    <!-- Link to sweetalert2 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
    
    <!-- Box icon -->
    <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
    
    <!-- Link to custom CSS -->
    <link rel="stylesheet" href="CSS/Restoku.css">
    <title>Add Admin</title>
</head>
<body>
    <!-- header -->

    <!-- Add Admin Form -->
    <section class="order" id="order">
        <h3 class="sub-heading">Add</h3>
        <h1 class="heading">New Admin</h1>

        <form id="adminForm" action="AddUser.jsp" method="post" onsubmit="return validateForm()">
            <div class="inputbox">
                <div class="input">
                    <label>Username</label>
                    <input type="text" name="username" required>
                </div>
                <div class="input">
                    <label>Password</label>
                    <input type="password" name="password" required>
                </div>
            </div>
            <input type="submit" value="Add Admin" class="btn">
        </form>
    </section>

    <!-- js sweetalert2 -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <!-- js swiper -->
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>

    <!-- link js -->
    <script src="JS/Restoku.js"></script>
</body>
</html>
