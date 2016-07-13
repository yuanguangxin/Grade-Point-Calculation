<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=0.8 user-scalable=0" name="viewport">
    <title>黑大成绩查询站</title>
    <jsp:include page="import/static.jsp"/>
</head>
<body>
<jsp:include page="import/navbar.jsp"/>
<div class="container-fluid" style="display: none;">
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
                2.该项目已在<a style="font-size: 1.1em"
                          href="https://github.com/yuanguangxin/Grade-Point-Calculation">Github</a>上开源,
                <span style="font-size: 1.1em;color: indianred">希望大家Star</span>,给予我信心,另外有兴趣小伙伴,欢迎给我发PR。
            </td>
        </tr>
    </table>
    <hr/>
    <br/>
    <div style="width: 80%;margin: 0 auto">
        <p style="font-size: 1.2em">
            <c:out value="${info}"/>
        </p>
        <p style="font-size: 1.2em">
            当前已获总学分:<c:out value="${sum_points}"/>&nbsp;&nbsp;&nbsp;
        </p>
        <p style="font-size: 1.2em">总绩点:<c:out value="${gp}"/></p>
        <p style="font-size: 1.2em">个人当前学年绩点:<c:out value="${this_gp}"/>&nbsp;&nbsp;&nbsp;</p>
        <p style="font-size: 1.2em" id="cal">选择项绩点:<span style='color:indianred'><c:out value="${gp}"/></span></p>
    </div>

    <div style="width: 80%;margin: 0 auto">
        <div id="main" style="width: 380px;height:300px"></div>
    </div>
    <br/>
    <c:forEach items="${poi}" var="po">
        <input type="hidden" class="hid1" value="<c:out value="${po}" />">
    </c:forEach>
    <div style="width: 80%;margin: 0 auto">
        <div id="main1" style="width: 380px;height:380px"></div>
    </div>
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
        <c:forEach items="${ranking}" var="rank" varStatus="st">
            <tr>
                <c:choose>
                    <c:when test="${st.index==0}">
                        <td style="color: #F44336;"><b>1</b></td>
                        <td style="color: #F44336;"><b><c:out value="${rank.stuId}"/></b></td>
                        <td style="color: #F44336;"><b><c:out value="${rank.info}"/></b></td>
                        <td style="color: #F44336;"><b><c:out value="${rank.point}"/></b></td>
                    </c:when>
                    <c:when test="${st.index==1}">
                        <td style="color: #F80;"><b>2</b></td>
                        <td style="color: #F80;"><b><c:out value="${rank.stuId}"/></b></td>
                        <td style="color: #F80;"><b><c:out value="${rank.info}"/></b></td>
                        <td style="color: #F80;"><b><c:out value="${rank.point}"/></b></td>
                    </c:when>
                    <c:when test="${st.index==2}">
                        <td style="color: #FFC407;">3</td>
                        <td style="color: #FFC407;"><b><c:out value="${rank.stuId}"/></b></td>
                        <td style="color: #FFC407;"><b><c:out value="${rank.info}"/></b></td>
                        <td style="color: #FFC407;"><b><c:out value="${rank.point}"/></b></td>
                    </c:when>
                    <c:otherwise>
                        <td><c:out value="${st.index+1}"/></td>
                        <td><c:out value="${rank.stuId}"/></td>
                        <td><c:out value="${rank.info}"/></td>
                        <td><c:out value="${rank.point}"/></td>
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
        <c:forEach items="${grades}" varStatus="st">
            <tr>
                <td><input type="checkbox" class="checkbox" checked="checked"/></td>
                <td><c:out value="${names[st.index]}"/></td>
                <td><c:out value="${sub_types[st.index]}"/></td>
                <td><c:out value="${points[st.index]}"/></td>
                <c:choose>
                    <c:when test="${scores[st.index]<60.0}">
                        <td style="color: red"><c:out value="${scores[st.index]}"/></td>
                    </c:when>
                    <c:when test="${scores[st.index]>=60.0}">
                        <td><c:out value="${scores[st.index]}"/></td>
                    </c:when>
                </c:choose>
            </tr>
        </c:forEach>
    </table>
    <br/>
    <c:forEach items="${test}" var="t" varStatus="st">
        <input type="hidden" class="hid" value="<c:out value="${t}" />">
    </c:forEach>
    <br/>
    <jsp:include page="import/footer.jsp"/>
</div>
<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="schoolNumInput" id="schoolNumInput">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="snLabel">请输入学号</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <label for="schoolNum" class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </label>
                            <input type="text" class="form-control" id="schoolNum"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <span id="get-pub-key" class="btn btn-primary">下一步</span>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="schoolNumInput" id="passwordInput">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">请输入密码</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <label for="password" class="input-group-addon">
                                <i class="glyphicon glyphicon-lock"></i>
                            </label>
                            <input type="password" class="form-control" id="password"/>
                        </div>
                        <br/>
                        <div class="form-group">
                            <label for="captcha-val">验证码:<img src="" alt="" id="captcha-img" onclick="refreshCaptcha()"
                                                              style="cursor: pointer;"/></label>
                            <input type="text" class="form-control" id="captcha-val" placeholder="验证码"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <span id="submitAuth" class="btn btn-primary">提交登录</span>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        /**
         * 判断是否有cookie
         */
        if (!$.cookie("sessionKey")) {
            $.cookie("loginToken", '', {expires: -1});
            $("#schoolNumInput").modal({
                backdrop: 'static',
                keyboard: false,
                show: true
            });
        } else if (!$.cookie("loginToken")) {
            refreshCaptcha();
            $("#passwordInput").modal({
                backdrop: 'static',
                keyboard: false,
                show: true
            });
        } else {
            //js getData
            getData();
        }
    });

    function refreshCaptcha() {
        $.ajax({
            url: "/auth/getCaptcha",
            dataType: "json",
            success: function (retJson) {
                if (retJson.code == 200) {
                    $("img#captcha-img").attr("src", retJson.data.captcha);
                } else {
                    Toast.show({
                        message: retJson.message,
                        title: retJson.code,
                        type: "danger"
                    });
                }
            }
        });
    }

    /**
     * 点击请求publicKey
     */
    $(document).on('click', "#get-pub-key", function () {
        $.ajax({
            url: '/auth/getPublicKey',
            data: {
                schoolNum: $('#schoolNum').val()
            },
            dataType: 'json',
            success: function (retJson) {
                if (retJson.code = 200) {
                    $.cookie("sessionKey", retJson.data.sessionKey, {
                        expires: 365
                    });
                    $("#schoolNumInput").modal('hide');
                    refreshCaptcha();
                    $("#passwordInput").modal({
                        backdrop: 'static',
                        keyboard: false,
                        show: true
                    });
                } else {
                    Toast.show({
                        message: retJson.message,
                        title: retJson.code,
                        type: "danger"
                    });
                }
            }
        });
    });
    /**
     * 点击提交登录
     */
    $(document).on('click', '#submitAuth', function () {
        var cookieArray = $.base64.decode($.cookie("sessionKey")).split('|');
        var num = cookieArray[0];
        var pubKey = cookieArray[1];
        var encrypt = new JSEncrypt();
        encrypt.setPublicKey(pubKey);
        var passwd = encrypt.encrypt($("input#password").val());
        $.ajax({
            url: '/auth/login',
            type: 'post',
            data: {
                schoolNum: num,
                password: passwd,
                captcha: $("input#captcha-val").val()
            },
            dataType: 'json',
            success: function (retJson) {
                if (retJson == 200) {

                } else {
                    Toast.show({
                        message: retJson.message,
                        title: retJson.code,
                        type: "danger"
                    });
                }
            }
        });
    });

    function getData() {
        $("body").show();
    }
</script>
</body>
</html>
