<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=0.8 user-scalable=0" name="viewport">
    <title>成绩详情</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/login.css">
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/echarts.common.min.js"></script>
</head>
<body style="width: 100%">
<br/>
<div class="form-group" style="width: 80%;margin: 0 auto">
    <button type="button" style="outline: none;" class="btn btn-success btn-group-sm">
        Tips
    </button>
</div>
<table class="table" style="width: 80%;margin: 0 auto">
    <tr>
        <td style="border: 0">
            1.欢迎大家加群:<b>258277784</b>,提出宝贵意见。
        </td>
    </tr>
    <tr>
        <td>
            2.该项目已在<a style="font-size: 1.1em" href="https://github.com/yuanguangxin/Grade-Point-Calculation">Github</a>上开源,
            <span style="font-size: 1.1em;color: indianred">希望大家Star</span>,给予我信心,另外有兴趣小伙伴,欢迎给我发PR。
        </td>
    </tr>
</table>
<hr/>
<br/>
<div style="width: 80%;margin: 0 auto">
    <p style="font-size: 1.2em">
        ${requestScope.info}
    </p>
    <p style="font-size: 1.2em">
        当前已获总学分:${requestScope.sum_points}&nbsp;&nbsp;&nbsp;
    </p>
    <p style="font-size: 1.2em">总绩点:${requestScope.gp}</p>
    <p style="font-size: 1.2em">个人当前学年绩点:${requestScope.this_gp}&nbsp;&nbsp;&nbsp;</p>
    <p style="font-size: 1.2em" id="cal">选择项绩点:<span style='color:indianred'>${requestScope.gp}</span></p>
</div>

<div style="width: 80%;margin: 0 auto">
    <div id="main" style="width: 380px;height:300px"></div>
</div>
<br/>
<c:forEach items="${requestScope.poi}" var="po">
    <input type="hidden" class="hid1" value="${po}">
</c:forEach>
<div style="width: 80%;margin: 0 auto">
    <div id="main1" style="width: 380px;height:380px"></div>
</div>
<script>
    var myChart1 = echarts.init(document.getElementById('main1'));
    var a1 = document.getElementsByClassName("hid1")[0].value;
    var b1 = document.getElementsByClassName("hid1")[1].value;
    var c1 = document.getElementsByClassName("hid1")[2].value;
    var d1 = document.getElementsByClassName("hid1")[3].value;
    option1 = {
        title : {
            text: '学分修读情况',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['2015-2016','2014-2015','2013-2014','2012-2013']
        },
        series : [
            {
                name: '修读学分',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:a1, name:'2015-2016'},
                    {value:b1, name:'2014-2015'},
                    {value:c1, name:'2013-2014'},
                    {value:d1, name:'2012-2013'}
                ],
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
</script>
<div class="form-group" style="width: 80%;margin: 0 auto">
    <button type="button" style="outline: none;" class="btn btn-success btn-group-sm">
        排行榜
    </button>
</div>
<table class="table" style="width: 80%;margin: 0 auto">
    <tr>
        <td style="border: 0;">排名</td>
        <td style="border: 0;">学号</td>
        <td style="border: 0">信息</td>
        <td style="border: 0">总绩点</td>
    </tr>
    <c:forEach items="${requestScope.ranking}" var="rank" varStatus="st">
        <tr>
            <c:choose>
                <c:when test="${st.index==0}">
                    <td style="color: #F44336;"><b>1</b></td>
                    <td style="color: #F44336;"><b>${rank.stuId}</b></td>
                    <td style="color: #F44336;"><b>${rank.info}</b></td>
                    <td style="color: #F44336;"><b>${rank.point}</b></td>
                </c:when>
                <c:when test="${st.index==1}">
                    <td style="color: #F80;"><b>2</b></td>
                    <td style="color: #F80;"><b>${rank.stuId}</b></td>
                    <td style="color: #F80;"><b>${rank.info}</b></td>
                    <td style="color: #F80;"><b>${rank.point}</b></td>
                </c:when>
                <c:when test="${st.index==2}">
                    <td style="color: #FFC407;">3</td>
                    <td style="color: #FFC407;"><b>${rank.stuId}</b></td>
                    <td style="color: #FFC407;"><b>${rank.info}</b></td>
                    <td style="color: #FFC407;"><b>${rank.point}</b></td>
                </c:when>
                <c:otherwise>
                    <td>${st.index+1}</td>
                    <td>${rank.stuId}</td>
                    <td>${rank.info}</td>
                    <td>${rank.point}</td>
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
    <c:forEach items="${requestScope.grades}" varStatus="st">
        <tr>
            <td><input type="checkbox" class="checkbox" checked="checked"/></td>
            <td>${requestScope.names[st.index]}</td>
            <td>${requestScope.sub_types[st.index]}</td>
            <td>${requestScope.points[st.index]}</td>
            <c:choose>
                <c:when test="${requestScope.scores[st.index]<60.0}">
                    <td style="color: red">${requestScope.scores[st.index]}</td>
                </c:when>
                <c:when test="${requestScope.scores[st.index]>=60.0}">
                    <td>${requestScope.scores[st.index]}</td>
                </c:when>
            </c:choose>
        </tr>
    </c:forEach>
</table>
<br/>
<c:forEach items="${requestScope.test}" var="t" varStatus="st">
    <input type="hidden" class="hid" value="${t}">
</c:forEach>
<script>
    var a = document.getElementsByClassName("hid")[0].value;
    var b = document.getElementsByClassName("hid")[1].value;
    var c = document.getElementsByClassName("hid")[2].value;
    var d = document.getElementsByClassName("hid")[3].value;
    var myChart = echarts.init(document.getElementById('main'));
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '年度绩点情况'
        },
        tooltip: {},
        legend: {
            data:['绩点']
        },
        xAxis: {
            data: ["2015-2016","2014-2015","2013-2014","2012-2013"]
        },
        yAxis: {
            min:6,
            max:10
        },
        series: [{
            name: '绩点',
            type: 'bar',
            data: [a, b, c, d],
        }]
    };
    myChart.setOption(option);
</script>



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
<script>
    $("input[type='checkbox']").on("change",function () {
        var selbox = document.getElementsByClassName("checkbox");
        var score = 0;
        var point = 0;
        var last = 0;
        for(var i=0;i<selbox.length;i++){
            if(selbox[i].checked){
                if(parseFloat(selbox[i].parentNode.parentNode.childNodes[9].innerHTML)>=60.0){
                    score+=parseFloat(selbox[i].parentNode.parentNode.childNodes[9].innerHTML)*parseFloat(selbox[i].parentNode.parentNode.childNodes[7].innerHTML);
                    point+=parseFloat(selbox[i].parentNode.parentNode.childNodes[7].innerHTML);
                }
            }
        }
        last = score/(10*point);
        document.getElementById("cal").innerHTML = "选择项绩点:<span style='color:indianred'>"+last+"</span>";
    });
</script>
</body
</html>
