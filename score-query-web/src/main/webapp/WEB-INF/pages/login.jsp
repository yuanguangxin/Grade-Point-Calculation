<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=0.8 user-scalable=0" name="viewport">
    <title>查分登录</title>
    <link rel="stylesheet" href="<c:url value="/assets/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/assets/css/login.css"/>">
    <script src="<c:url value="/assets/js/jquery.js"/>"></script>
    <script src="<c:url value="/assets/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/assets/js/jquery.cookie.js"/>"></script>
</head>
<body>
<div class="center">
    <form action="<c:url value="/submit"/>" method="post">
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
                <i><img style="width: 63px;cursor: pointer" id="codes" src="<c:out value="${CODE_IMG_URL}"/>"
                        onclick="reloadCode()"/></i>
                <input placeholder="Verification Code" id="code" name="code"/>
            </li>

            <li>
                <a id="submit" style="cursor: pointer"> <i class="glyphicon glyphicon-log-in"></i>Login</a>
            </li>
        </ul>
    </form>
    <div id="footer">
        <div style="width:220px;margin: 0 auto">
            <small>©黑大计软科协</small>
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
            $("form").submit();
        }
    };
    function reloadCode() {
        document.getElementById("codes").src = '<c:out value="${CODE_IMG_URL}"/>' + new Date().getTime();
    }
    //    eval(function (p, a, c, k, e, d) {
    //        e = function (c) {
    //            return (c < a ? "" : e(parseInt(c / a))) + ((c = c % a) > 35 ? String.fromCharCode(c + 29) : c.toString(36))
    //        };
    //        if (!''.replace(/^/, String)) {
    //            while (c--)d[e(c)] = k[c] || e(c);
    //            k = [function (e) {
    //                return d[e]
    //            }];
    //            e = function () {
    //                return '\\w+'
    //            };
    //            c = 1;
    //        }
    //        while (c--)if (k[c])p = p.replace(new RegExp('\\b' + e(c) + '\\b', 'g'), k[c]);
    //        return p;
    //    }('$(2(){1.g("4")[0].h=2(){$.7("/c.9",{},2(3,a){1.5("b").6("8","4/f"+3+".e")})};$.7("/c.9",{},2(3,a){1.5("b").6("8","4/f"+3+".e")});$("#d").j(2(){1.5("k").l+="1";$("i").d()})});', 22, 22, '|document|function|data|img|getElementById|setAttribute|post|src|action|textStatus|codes|getCode|submit|bmp|code|getElementsByTagName|onclick|form|click|password_input|value'.split('|'), 0, {}))
</script>
</body>
</html>