<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=0.8 user-scalable=0" name="viewport">
    <title></title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/login.css">
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body style="width: 100%">
<div style="width: 80%;margin: 0 auto">
    <p style="font-size: 1.25em">
        当前已获总学分:${requestScope.sum_points}&nbsp;&nbsp;&nbsp;
    </p>
    <p>当前学年绩点:${requestScope.this_gp}&nbsp;&nbsp;&nbsp;</p>
    <p>总绩点:${requestScope.gp}</p>
    <div class="form-group">
        <button type="button" style="outline: none;" class="btn btn-success btn-group-sm">
            成绩详情
        </button>
    </div>
</div>
<table class="table" style="width: 80%;margin: 0 auto">
    <tr>
        <td>课程名称</td>
        <td>课程类别</td>
        <td>学分</td>
        <td>成绩</td>
    </tr>
    <c:forEach items="${requestScope.grades}" varStatus="st">
        <tr>
            <td>${requestScope.names[st.index]}</td>
            <td>${requestScope.sub_types[st.index]}</td>
            <td>${requestScope.points[st.index]}</td>
            <td>${requestScope.scores[st.index]}</td>
        </tr>
    </c:forEach>
</table>
<br/>
<br/>
<div class="form-group" style="width: 80%;margin: 0 auto">
    <button type="button" style="outline: none;" class="btn btn-success btn-group-sm">
        Tips
    </button>
</div>
<table class="table" style="width: 80%;margin: 0 auto">
    <tr>
        <td style="border: none">
            1.本系统由黑大计软科协完成,制作初期,尚存在微小bug,正努力调试中。
        </td>
    </tr>
    <tr>
        <td>
            2.目前该项目已在<a href="https://github/yuanguangxin">Github</a>上开源,
            希望大家给star,给予我信心,另外有兴趣小伙伴,欢迎给我发PR。
        </td>
    </tr>
    <tr>
        <td>
            3.如有技术问题或功能需要。可以加我qq:274841922,也可以给我留言,欢迎大家提出宝贵意见。
        </td>
    </tr>
</table>
<br/>
<div class="form-group" style="width: 80%;margin: 0 auto">
        <button type="button" style="outline: none;" class="btn btn-success btn-group-sm">
            更新预告
        </button>
</div>
<table class="table" style="width: 80%;margin: 0 auto">
    <tr>
        <td style="border: none">
            1.增加查看各模块详细修读情况,给予大家对自己学分更加直观的分析。
        </td>
    </tr>
    <tr>
        <td>
            2.增加排行榜系统,让你了解周围的学霸。(为确保个人隐私,只会排出前五名)。
            学霸们不要害羞~~
        </td>
    </tr>
</table>
<br/>
<div id="footer" style="width: 100%;line-height: 1.8;">
    <div style="width:100px;margin: 0 auto">
        <small>
            友情链接:<a href="http://www.pc6.com/az/311004.html">黑大盒子</a>
        </small>
    </div>
    <div style="width:220px;margin: 0 auto">
        <small>©黑大计软科协</small>
        Powered by <a href="http://yuanguangxin.me">@袁广鑫</a>
    </div>
</div>
</body>
</html>
