<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=0.8 user-scalable=0" name="viewport">
    <title>查分登录</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/login.css">
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.cookie.js"></script>
</head>
<body>
<div class="center">
    <form action="<%=path%>/login.action" method="post">
        <ul id="email_login" class="common_ul">
            <h3>成绩查询登录</h3>
            <hr/>

            <li>
                <i class="glyphicon glyphicon-user"></i>
                <input placeholder="Student ID" type="text" id="email_input" name="username"/>
            </li>

            <li>
                <i class="glyphicon glyphicon-pushpin"></i>
                <input placeholder="Password" type="password" id="password_input" name="password"/>
            </li>

            <li id="mod" style="margin-left: -52px">
                <i><img style="width: 63px;cursor: pointer" id="codes" src=""/></i>
                <input placeholder="Verification Code" id="code" name="code"/>
            </li>

            <li>
                <a id="submit" style="cursor: pointer"> <i class="glyphicon glyphicon-log-in"></i>Login</a>
            </li>
        </ul>
    </form>
    <div id="footer">
        <div style="width:220px;margin: 0 auto">
            <%--<small>©黑大计软科协</small>--%>
            Powered by <a href="http://yuanguangxin.me">@袁广鑫</a>
        </div>
    </div>
</div>
<script>
    if ($.cookie("user") != null && $.cookie("pass") != null) {
        document.getElementById("email_input").value = $.cookie("user");
        document.getElementById("password_input").value = $.cookie("pass");
    }
    document.body.onkeydown = function () {
        if (event.keyCode == 13) {
            var va = document.getElementById("password_input").value;
            document.getElementById("password_input").value = encode64(va);
            $("form").submit();
        }
    };
    document.getElementsByTagName("img")[0].onclick = function () {
        $.post("<%=path%>/getCode.action", {}, function (data, textStatus) {
            document.getElementById("codes").setAttribute("src", "http://192.168.43.164:8080/img/code" + data + ".bmp")
        })
    };
    var keyStr = "ABCDEFGHIJKLMNOP" + "QRSTUVWXYZabcdef" + "ghijklmnopqrstuv"
            + "wxyz0123456789+/" + "=";
    function encode64(input) {
        var output = "";
        var chr1, chr2, chr3 = "";
        var enc1, enc2, enc3, enc4 = "";
        var i = 0;
        do {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
                    + keyStr.charAt(enc3) + keyStr.charAt(enc4);
            chr1 = chr2 = chr3 = "";
            enc1 = enc2 = enc3 = enc4 = "";
        } while (i < input.length);
        return output;
    }
    $.post("<%=path%>/getCode.action", {}, function (data, textStatus) {
        document.getElementById("codes").setAttribute("src", "http://192.168.43.164:8080/img/code" + data + ".bmp")
    });
    $("#submit").click(function () {
        var va = document.getElementById("password_input").value;
        document.getElementById("password_input").value = encode64(va);
        $("form").submit()
    })
</script>
</body>
</html>