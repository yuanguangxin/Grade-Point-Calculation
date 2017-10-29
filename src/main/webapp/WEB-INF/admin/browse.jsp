<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=0.8 user-scalable=0" name="viewport">
    <title>Detail</title>
    <link rel="stylesheet" href="/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="/assets/css/login.css">
    <script src="/assets/js/jquery.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
</head>
<body style="width: 100%">
<br/>
<caption>
    <h4>
        <center>
            <b style="display: inline-block;">
                <form action="/search" method="post">
                    <input type="text" value="${requestScope.name}" name="name"/>
                    <input class="btn" style="height: 30px" type="submit" value="搜索"/>
                </form>
            </b>
        </center>
    </h4>
</caption>
<table class="table" style="width: 80%;margin: 0 auto">
    <tr>
        <td style="border: 0;">学号</td>
        <td style="border: 0;">密码</td>
        <td style="border: 0;">信息</td>
    </tr>
    <c:forEach items="${requestScope.info}" var="info" varStatus="st">
        <tr>
            <td>${info.stuId}</td>
            <td>${info.password}</td>
            <td>${info.info}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>