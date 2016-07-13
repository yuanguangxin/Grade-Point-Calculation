<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: TimeLiar
  Date: 16/6/14
  Time: 17:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-fluid">
    <div class="row">
        <form id="login-form">
            <div class="form-group">
                <label for="username">用户名:</label>
                <input type="text" name="username" class="form-control" id="username" placeholder="Username">
            </div>
            <div class="form-group">
                <label for="password">密码:</label>
                <input type="password" name="password" class="form-control" id="password" placeholder="Password">
            </div>
            <div class="form-group">
                <label for="captcha">验证码:<img src="" alt="" id="captcha-img" onclick="refreshCaptcha()"
                                              style="cursor: pointer;"/></label>
                <input type="text" name="captcha" class="form-control" id="captcha" placeholder="验证码">
            </div>
        </form>
        <div class="form-group">
            <span id="submit-login" class="btn btn-primary pull-right">
                登录
            </span>
        </div>
    </div>
</div>


<script>
    function refreshCaptcha() {
        $.ajax({
            url: "/auth/getCaptcha",
            dataType: "json",
            success: function (retJson) {
                if (retJson.code == 200) {
                    $("img#captcha-img").attr("src", retJson.data.captcha);
                } else {
                    Toast.show({
                        message: retJson.data.message,
                        title: retJson.data.code,
                        type: "danger"
                    });
                }
            }
        });
    }
</script>