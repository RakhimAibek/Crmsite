<%@ page import="java.util.List" %>
<%@ page import="com.crmsite.dao.Cabinets" %>
<%@ page import="com.crmsite.dao.Users" %>
<%@ page import="com.crmsite.dao.Roles" %>
<%@ page import="javax.management.relation.Role" %>
<%@ page import="com.crmsite.dao.Groups" %><%--
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
    <title>Update teacher/student page</title>
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

            <!-- Add new cabinet -->
            <li class="nav-item" data-toggle="tooltip" data-placement="right">
                <a class="nav-link" href="/sendEditProfile">
                    <span class="nav-link-text">Edit profile</span>
                </a>
            </li>

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
                <i></i>Profile edit page</div>
            <div class="card-header">
                <div class="card-body">
                    <form action="/updateTeacherAndStudent", method="post">
                        <table id="myAreaChart" width="100%">
                            <%Users user = (Users) request.getAttribute("editedUser");%>
                            <tr>
                                <td>Change login:<br>
                                    <input type="hidden" name="userId" value="<%out.print(user.getId());%>">
                                    <input type="text" name="login" value="<%out.print(user.getLogin());%>"></td>
                            </tr>

                            <tr>
                                <td>Change password:<br>
                                    <input type="text" name="password" value="<%out.print(user.getPassword());%>"></td>
                            </tr>

                            <tr>
                                <td>Change name:<br>
                                    <input type="text" name="name" value="<%out.print(user.getName());%>"></td>
                            </tr>

                            <tr>
                                <td>Change surname:<br>
                                    <input type="text" name="surname" value="<%out.print(user.getSurname());%>"></td>
                            </tr>
                            <tr>
                                <td>Select role:<br>
                                    <select name="roleName">


                                        <%
                                            List<Roles> roles = (List<Roles>)request.getAttribute("allRoles");

                                            if(roles!=null){

                                                for (Roles role: roles){
                                                    if (role.getId()!=2 && !role.getName().equals(user.getRole_id().getName())) {
                                        %>
                                        <option value="<%out.print(user.getRole_id().getName());%>" selected><%out.print(user.getRole_id().getName());%></option>
                                        <option value="<%out.print(role.getName());%>"><%out.print(role.getName());%></option>


                                        <%
                                                    }
                                                }

                                            }

                                        %>

                                    </select>
                                </td>
                            </tr>

                            <%if (user.getGroups().isEmpty()) {%>
                            <tr>
                                <td>You don`t have a group, CHOOSE group:<br>
                                    <select name="groupName">
                                        <%
                                            List<Groups> groupsList = (List<Groups>)request.getAttribute("allGroups");


                                                for (Groups group: groupsList){
                                                    if (group.getActive()==1 && group.getCabinet_id().getName().equals(user.getCabinet_id().getName())) {
                                        %>

                                                    <option value="<%out.print(group.getName());%>"><%out.print(group.getName());%></option>

                                        <%
                                                    }
                                                }
                                        %>

                                    </select>
                                </td>
                            </tr>
                            <%}%>

                            <%if(!user.getGroups().isEmpty()) {%>
                                <tr>
                                    <td>Change group:<br>
                                        <%if (user.getRole_id().getId()==3) {%>
                                        <select name="groupName">
                                            <%
                                                List<Groups> groups = (List<Groups>)request.getAttribute("allGroups");
                                                for (Groups group: groups){
                                                    if(group.getUsers().contains(user) && group.getActive()==1 && group.getCabinet_id().getName().equals(user.getCabinet_id().getName())) {
                                            %>
                                                        <option value="<%out.print(group.getName());%>" selected><%out.print(group.getName());%></option>
                                                    <%}%>

                                                    <%if(group.getActive()==1 && group.getCabinet_id().getName().equals(user.getCabinet_id().getName())){%>
                                                        <option value="<%out.print(group.getName());%>"><%out.print(group.getName());%></option>
                                                    <%}%>

                                                <%}%>
                                        </select>
                                        <%}%>
                                    </td>
                                </tr>
                            <%}%>

                            <tr>
                                <td><br><input type="submit" value="Change"></td>
                            </tr>

                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- /.container-fluid-->
    <!-- /.content-wrapper-->
    <footer class="sticky-footer">
        <div class="container">
            <br><br><br><br><br><br><br><br><br><br><br>
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


