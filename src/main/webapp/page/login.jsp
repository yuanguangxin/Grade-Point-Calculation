<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=0.8 user-scalable=0" name="viewport">
    <title>Login</title>
    <link rel="stylesheet" href="/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="/assets/css/login.css">
    <script src="/assets/js/jquery.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
    <script src="/assets/js/jquery.cookie.js"></script>
</head>
<body>
<div class="center">
    <form action="/login" method="post">
        <ul id="stu_login" class="common_ul">
            <h3>Login</h3>
            <hr/>

            <li>
                <i class="glyphicon glyphicon-user"></i>
                <input placeholder="Student ID" type="text" id="stu_id" name="stuId"/>
            </li>

            <li>
                <i class="glyphicon glyphicon-pushpin"></i>
                <input placeholder="Password" type="password" id="password_input" name="password"/>
            </li>

            <li id="mod" style="margin-left: -52px">
                <i><img style="width: 63px;cursor: pointer" id="codes" src=""/></i>
                <input placeholder="Captcha" id="code" name="code"/>
            </li>

            <li>
                <a id="submit" style="cursor: pointer"> <i class="glyphicon glyphicon-log-in"></i>Login</a>
            </li>
        </ul>
    </form>
    <center>
        <div id="footer">
            Powered by <a href="https://github.com/yuanguangxin/Grade-Point-Calculation">@袁广鑫</a>
        </div>
    </center>
</div>
<script>
    if ($.cookie("user") != null && $.cookie("pass") != null) {
        document.getElementById("stu_id").value = $.cookie("user");
        document.getElementById("password_input").value = $.cookie("pass");
    }

    $.post("/code", {}, function (data, textStatus) {
        document.getElementById("codes").setAttribute("src", "data:image/jpg;base64," + data);
    });

    document.getElementsByTagName("img")[0].onclick = function () {
        $.post("/code", {}, function (data, textStatus) {
            document.getElementById("codes").setAttribute("src", "data:image/jpg;base64," + data);
        })
    };

    document.body.onkeydown = function () {
        if (event.keyCode == 13) {
            $.cookie("user",document.getElementById("stu_id").value);
            $.cookie("pass",document.getElementById("password_input").value);
            $("form").submit();
        }
    };

    $("#submit").click(function () {
        $.cookie("user",document.getElementById("stu_id").value);
        $.cookie("pass",document.getElementById("password_input").value);
        $("form").submit()
    })

</script>
</body>
</html>
