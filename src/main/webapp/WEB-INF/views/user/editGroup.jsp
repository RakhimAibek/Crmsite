<%@ page import="com.crmsite.dao.SuperAdmins" %>
<%@ page import="com.crmsite.dao.Users" %>
<%@ page import="com.crmsite.dao.Groups" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: aibekrakhim
  Date: 11/11/17
  Time: 9:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% SuperAdmins superAdmin = (SuperAdmins) session.getAttribute("superUserData"); %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Edit group</title>
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
    <a class="navbar-brand" href="">Home</a>
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

            <!-- Create new user -->
            <li class="nav-item" data-toggle="tooltip" data-placement="right">
                <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseMulti" data-parent="#exampleAccordion">
                    <span class="nav-link-text">New user</span>
                </a>
                <ul class="sidenav-second-level collapse" id="collapseMulti">
                    <li>
                        <form action="/newUser", method="post">
                            <input type="submit" value="Add user">
                        </form>
                    </li>
                    <li>
                        <form action="/redirectEditTeacherAndStudent", method="post">
                            <input type="submit" value="Edit user">
                        </form>
                    </li>
                </ul>
            </li>

            <!-- New group -->
            <li class="nav-item" data-toggle="tooltip" data-placement="right">

                <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseMulti3" data-parent="#exampleAccordion">
                    <span class="nav-link-text">New group</span>
                </a>

                <ul class="sidenav-second-level collapse" id="collapseMulti3">
                    <li>
                        <form action="/redirectAddGroup", method="get">
                            <input type="submit" value="Add new group">
                        </form>
                    </li>
                    <li>
                        <form action="/allGroups", method="get">
                            <input type="submit" value="Edit groups">
                        </form>
                    </li>
                </ul>

            </li>

            <!-- Create new course -->
            <li class="nav-item" data-toggle="tooltip" data-placement="right">

                <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseMulti2" data-parent="#exampleAccordion">
                    <span class="nav-link-text">New course</span>
                </a>

                <ul class="sidenav-second-level collapse" id="collapseMulti2">
                    <li>
                        <form action="/redirectAddCourse", method="post">
                            <input type="submit" value="Add course">
                        </form>
                    </li>
                    <li>
                        <form action="/allCourses", method="get">
                            <input type="submit" value="Edit course">
                        </form>
                    </li>
                    <li>
                        <form action="/redirectAddLesson", method="get">
                            <input type="submit" value="New lesson">
                        </form>
                    </li>
                </ul>

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
                <i></i>Edit group</div>
            <div class="card-header">
                <div class="card-body">
                    <form action="/editGroup", method="post">
                        <table id="myAreaChart" width="100%">
                            <%Groups group = (Groups) request.getAttribute("editedGroup");%>
                            <tr>
                                <td>New name:<br>
                                    <input type="hidden" name="id" value="<%out.print(group.getId());%>">
                                    <input type="text" name="groupName" value="<%out.print(group.getName());%>" placeholder="group name"></td>
                            </tr>

                            <tr>
                                <td><br><input type="submit" value="Change"></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>

            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                        <thead>
                        <tr>
                            <th>Name and surname</th>
                            <th>Role</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            List<Users> usersInGroup = (List<Users>)request.getAttribute("usersInGroup");
                            Users user = (Users) session.getAttribute("adminSession");

                            if(usersInGroup!=null){

                                for (Users u: usersInGroup){
                                    if (u.getActive()==1 && u.getCabinet_id().getName().equals(user.getCabinet_id().getName())) {
                        %>

                        <tr>
                            <td><%out.print(u.getName()+" "+u.getSurname());%></td>

                            <td><%out.print(u.getRole_id().getName());%></td>


                            <form action="/deleteFromGroup", method="post">
                                <td>
                                    <input type="hidden" value="<%out.print(u.getId());%>" name="userId"/>
                                    <input type="hidden" value="<%out.print(group.getId());%>" name="groupId"/>
                                    <input type="submit" value="Delete from the group">
                                </td>
                            </form>


                        </tr>

                        <%
                                    }
                                }

                            }

                        %>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- /.container-fluid-->
    <!-- /.content-wrapper-->
    <footer class="sticky-footer">
        <div class="container">
            <div class="text-center">
                <small>Copyright © Created by Rakhim Aibek</small>
            </div>
        </div>
    </footer>
    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fa fa-angle-up"></i>
    </a>
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


