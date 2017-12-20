<%@ page import="java.util.List" %>
<%@ page import="com.crmsite.dao.Users" %>
<%@ page import="com.crmsite.dao.News" %>
<%--
  Created by IntelliJ IDEA.
  User: aibekrakhim
  Date: 11/11/17
  Time: 9:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Teacher home</title>
    <!-- Bootstrap core CSS-->
    <link href="../../resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="../../resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- Page level plugin CSS-->
    <link href="../../resources/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
    <!-- Custom styles for this template-->
    <link href="../../../../../../../Downloads/startbootstrap-sb-admin-gh-pages/css/sb-admin.css" rel="stylesheet">
</head>

<body class="fixed-nav sticky-footer bg-dark" id="page-top">
<!-- Navigation-->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
    <a class="navbar-brand" href="">Teacher page</a>
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav navbar-sidenav" id="exampleAccordion">

            <!-- Edit my profile -->
            <li class="nav-item" data-toggle="tooltip" data-placement="right">
                <a class="nav-link" href="/sendEditProfile">
                    <span class="nav-link-text">Edit profile</span>
                </a>
            </li>

            <!-- My Groups -->
            <li class="nav-item" data-toggle="tooltip" data-placement="right">
                <a class="nav-link" href="/techMyGroups">
                    <span class="nav-link-text">My groups</span>
                </a>
            </li>

            <!-- My courses -->
            <li class="nav-item" data-toggle="tooltip" data-placement="right">
                <a class="nav-link" href="/techMyCourses">
                    <span class="nav-link-text">My courses</span>
                </a>
            </li>

        </ul>

        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" data-toggle="modal" data-target="#exampleModal">
                    <i class="fa fa-fw fa-sign-out"></i>Logout</a>
            </li>
        </ul>
    </div>
</nav>
<div class="content-wrapper">
    <div class="container-fluid">
        <!-- Example DataTables Card-->
        <div class="card mb-3">
            <div class="card-header">
                <br><br>
            </div>
            <div class="card-body">
                <%Users user = (Users) session.getAttribute("teacherSession");%>
                <%List<News> newsList = (List<News>) request.getAttribute("newsList");%>
                <h1>Welcome <%out.print(user.getName()+" "+user.getSurname());%>!</h1><br>

                <table class="" id="" width="100%" cellspacing="0">
                    <thead>
                        <tr>
                            <th><h4>Last news:</h4></th>
                        </tr>
                    </thead>

                    <tbody>

                    <%for (News n:newsList) {
                            if (n.getActive()==1) {%>
                            <tr><td><h4><%out.print(n.getTitle());%></h4></td></tr>
                            <td><h6><%out.print(n.getContent());%></h6></td>
                    <%}}%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Logout Modal-->
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-primary" href="login.html">Logout</a>
                </div>
            </div>
        </div>
    </div>
    <!-- Bootstrap core JavaScript-->
    <script src="../../resources/vendor/jquery/jquery.min.js"></script>
    <script src="../../resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <!-- Core plugin JavaScript-->
    <script src="../../resources/vendor/jquery-easing/jquery.easing.min.js"></script>
    <!-- Page level plugin JavaScript-->
    <script src="../../resources/vendor/chart.js/Chart.min.js"></script>
    <script src="../../resources/vendor/datatables/jquery.dataTables.js"></script>
    <script src="../../resources/vendor/datatables/dataTables.bootstrap4.js"></script>
    <!-- Custom scripts for all pages-->
    <script src="../../resources/js/sb-admin.min.js"></script>
    <!-- Custom scripts for this page-->
    <script src="../../resources/js/sb-admin-datatables.min.js"></script>
    <script src="../../resources/js/sb-admin-charts.min.js"></script>
</div>
</body>

</html>





