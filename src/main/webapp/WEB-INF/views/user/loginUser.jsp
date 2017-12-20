<%--
  Created by IntelliJ IDEA.
  User: aibekrakhim
  Date: 11/11/17
  Time: 6:15 AM
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
    <title>User auth</title>
    <!-- Bootstrap core CSS-->
    <link href="../../resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom fonts for this template-->
    <link href="../../resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- Custom styles for this template-->
    <link href="../../../../../../../Downloads/startbootstrap-sb-admin-gh-pages/css/sb-admin.css" rel="stylesheet">
</head>

<body class="bg-dark">
<div class="container">
    <div class="card card-login mx-auto mt-5">
        <div class="card-header">Login</div>
        <div class="card-body">
            <form action="/userAuth", method="post">
                <div class="form-group">
                    <label for="exampleInputEmail1">Enter login</label>
                    <input class="form-control" id="exampleInputEmail1" name="login" type="text" aria-describedby="emailHelp" placeholder="login">
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1">Enter password</label>
                    <input class="form-control" name="password" id="exampleInputPassword1" type="password" placeholder="password">
                </div>
                <div class="form-group">
                    <div class="form-check">
                        <label class="form-check-label">
                    </div>
                </div>
                <input class="btn btn-primary btn-block" type="submit" value="LOGIN">
            </form>
            <div class="text-center">
            </div>
        </div>
    </div>
</div>
<!-- Bootstrap core JavaScript-->
<script src="../../resources/vendor/jquery/jquery.min.js"></script>
<script src="../../resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- Core plugin JavaScript-->
<script src="../../resources/vendor/jquery-easing/jquery.easing.min.js"></script>
</body>

</html>

