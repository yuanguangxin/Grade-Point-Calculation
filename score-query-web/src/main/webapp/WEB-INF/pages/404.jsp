<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: TimeLiar
  Date: 16/7/8
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title>404</title>
    <style>
        .page404 {
            background: #f0f0f0;
            text-align: center;
            color: #9aa8b3
        }

        .page404 img {
            width: 100%;
            margin-top: 60px;
            max-width: 640px;
        }

        .page404 .num {
            margin-top: -10px;
            font-size: 35px;
            padding-bottom: 5px;
        }
    </style>
</head>
<body class="page404">
<img src="<c:url value="/assets/img/404.png"/>">
<p class="num"><b>404</b></p>
<p class="tips">很抱歉,该页面不存在</p>
</body>
</html>
