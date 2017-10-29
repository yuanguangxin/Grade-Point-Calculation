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
    <script src="/assets/js/echarts.common.min.js"></script>
</head>
<body style="width: 100%">
<br/>
<div style="width: 80%;margin: 0 auto">
    <p style="font-size: 1.2em">
        ${requestScope.mes.userInfo}
    </p>
    <p style="font-size: 1.2em">
        当前已获总学分:${requestScope.mes.credit}&nbsp;&nbsp;&nbsp;
    </p>
    <p style="font-size: 1.2em">总绩点:${requestScope.mes.gp}</p>
    <p style="font-size: 1.2em">当前学年绩点:${requestScope.mes.this_gp}&nbsp;&nbsp;&nbsp;</p>
    <p style="font-size: 1.2em" id="cal">选择项绩点:<span style='color:indianred'>${requestScope.mes.gp}</span></p>
</div>

<div style="width: 80%;margin: 0 auto">
    <div id="main" style="width: 380px;height:300px"></div>
</div>
<br/>

<c:forEach items="${requestScope.mes.every_year_credit}" var="po">
    <input type="hidden" class="hid1" value="${po}">
</c:forEach>
<div style="width: 80%;margin: 0 auto">
    <div id="mains" style="width: 380px;height:380px"></div>
</div>
<div class="form-group" style="width: 80%;margin: 0 auto">
    <button type="button" style="outline: none;" class="btn btn-success btn-group-sm">
        总绩点排行
    </button>
</div>
<table class="table" style="width: 80%;margin: 0 auto">
    <tr>
        <td style="border: 0;">排名</td>
        <td style="border: 0;">学号</td>
        <td style="border: 0">信息</td>
        <td style="border: 0">绩点</td>
    </tr>
    <c:forEach items="${requestScope.mes.rank}" var="rank" varStatus="st">
        <tr>
            <c:choose>
                <c:when test="${st.index==0}">
                    <td style="color: #F44336;"><b>1</b></td>
                    <td style="color: #F44336;"><b>${rank.stuId}</b></td>
                    <td style="color: #F44336;"><b>${rank.info}</b></td>
                    <td style="color: #F44336;"><b>${rank.tpoint}</b></td>
                </c:when>
                <c:when test="${st.index==1}">
                    <td style="color: #F80;"><b>2</b></td>
                    <td style="color: #F80;"><b>${rank.stuId}</b></td>
                    <td style="color: #F80;"><b>${rank.info}</b></td>
                    <td style="color: #F80;"><b>${rank.tpoint}</b></td>
                </c:when>
                <c:when test="${st.index==2}">
                    <td style="color: #FFC407;">3</td>
                    <td style="color: #FFC407;"><b>${rank.stuId}</b></td>
                    <td style="color: #FFC407;"><b>${rank.info}</b></td>
                    <td style="color: #FFC407;"><b>${rank.tpoint}</b></td>
                </c:when>
                <c:otherwise>
                    <td>${st.index+1}</td>
                    <td>${rank.stuId}</td>
                    <td>${rank.info}</td>
                    <td>${rank.tpoint}</td>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
</table>
<br/>

<div class="form-group" style="width: 80%;margin: 0 auto">
    <button type="button" style="outline: none;" class="btn btn-success btn-group-sm">
        成绩详情
    </button>
</div>
<table class="table" style="width: 80%;margin: 0 auto">
    <tr>
        <td style="border: 0;">选择计算</td>
        <td style="border: 0">课程名称</td>
        <td style="border: 0">课程类别</td>
        <td style="border: 0">学分</td>
        <td style="border: 0">成绩</td>
    </tr>
    <c:forEach items="${requestScope.mes.sub_names}" varStatus="st">
        <tr>
            <td><input type="checkbox" class="checkbox" checked="checked"/></td>
            <td>${requestScope.mes.sub_names[st.index]}</td>
            <td>${requestScope.mes.sub_types[st.index]}</td>
            <td>${requestScope.mes.sub_points[st.index]}</td>
            <c:choose>
                <c:when test="${requestScope.mes.sub_scores[st.index]<60.0}">
                    <td style="color: red">${requestScope.mes.sub_scores[st.index]}</td>
                </c:when>
                <c:when test="${requestScope.mes.sub_scores[st.index]>=60.0}">
                    <td>${requestScope.mes.sub_scores[st.index]}</td>
                </c:when>
            </c:choose>
        </tr>
    </c:forEach>
</table>
<br/>
<c:forEach items="${requestScope.mes.every_year_points}" var="t" varStatus="st">
    <input type="hidden" class="hid" value="${t}">
</c:forEach>
<script>
    var myChart1 = echarts.init(document.getElementById('mains'));

    var len = document.getElementsByClassName("hid1").length;
    var a1 = [];
    for (var i = 0; i < len; i++) {
        a1.push(document.getElementsByClassName("hid1")[i].value);
    }
    var tdata = [];
    var tdatas = [];
    if (len == 1) {
        tdata = ['大一'];
        tdatas = [{value: a1[0], name: '大一'}]
    } else if (len == 2) {
        tdata = ['大二', '大一'];
        tdatas = [
            {value: a1[0], name: '大二'},
            {value: a1[1], name: '大一'}
        ]
    } else if (len == 3) {
        tdata = ['大三', '大二', '大一'];
        tdatas = [
            {value: a1[0], name: '大三'},
            {value: a1[1], name: '大二'},
            {value: a1[2], name: '大一'}
        ]
    } else {
        tdata = ['大四', '大三', '大二', '大一'];
        tdatas = [
            {value: a1[0], name: '大四'},
            {value: a1[1], name: '大三'},
            {value: a1[2], name: '大二'},
            {value: a1[3], name: '大一'}
        ]
    }


    option1 = {
        title: {
            text: '学分修读情况',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: tdata
        },
        series: [
            {
                name: '修读学分',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: tdatas,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    myChart1.setOption(option1);

    var a = [];
    for (var i = 0; i < document.getElementsByClassName("hid").length; i++) {
        a[i] = document.getElementsByClassName("hid")[i].value;
    }
    var myChart = echarts.init(document.getElementById('main'));
    var option = {
        title: {
            text: '年度绩点情况'
        },
        tooltip: {},
        legend: {
            data: ['绩点']
        },
        xAxis: {
            data: tdata
        },
        yAxis: {
            min: 6,
            max: 10
        },
        series: [{
            name: '绩点',
            type: 'bar',
            data: a
        }]
    };
    myChart.setOption(option);
</script>

<br/>

<center>
    <div id="footer" style="width: 100%;line-height: 1.8;">
        <div>
            Powered by <a href="https://github.com/yuanguangxin/Grade-Point-Calculation">@袁广鑫</a>
        </div>
    </div>
</center>

<script>
    $("input[type='checkbox']").on("change", function () {
        var selbox = document.getElementsByClassName("checkbox");
        var score = 0;
        var point = 0;
        var last = 0;
        for (var i = 0; i < selbox.length; i++) {
            if (selbox[i].checked) {
                if (parseFloat(selbox[i].parentNode.parentNode.childNodes[9].innerHTML) >= 60.0) {
                    score += parseFloat(selbox[i].parentNode.parentNode.childNodes[9].innerHTML) * parseFloat(selbox[i].parentNode.parentNode.childNodes[7].innerHTML);
                    point += parseFloat(selbox[i].parentNode.parentNode.childNodes[7].innerHTML);
                }
            }
        }
        last = score / (10 * point);
        document.getElementById("cal").innerHTML = "选择项绩点:<span style='color:indianred'>" + last.toFixed(3) + "</span>";
    });
</script>
</body>
</html>