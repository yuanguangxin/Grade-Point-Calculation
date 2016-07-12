<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: TimeLiar
  Date: 2016/7/11
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<c:url value="/assets/css/bootstrap.min.css"/>"/>
</head>
<body>
<div class="container-fluid">
    <div class="input-group">
        <textarea name="aa" id="aa" cols="30" rows="10" class="form-control"><c:out value="${publicKey}"/></textarea>
    </div>
    <div class="input-group">
        <label class="input-group-addon btn" id="submit">
            <span class="glyphicon glyphicon-lock"></span>
        </label>
        <input type="text" class="form-control" id="enctype" title="加密内容"/>
    </div>
</div>

<script type="application/javascript" src="<c:url value="/assets/js/jquery.js"/>"></script>
<script type="application/javascript" src="<c:url value="/assets/js/bootstrap.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/assets/js/jsencrypt.min.js"/>"></script>
<script type="application/javascript" src="<c:url value="/assets/js/jquery.cookie.js"/>"></script>
<script>
    $("#submit").click(function () {
        var encrypt = new JSEncrypt();
        encrypt.setPublicKey($.cookie("publicKey"));
        console.log("cookie:" + $.cookie("publicKey"));
        var result = encrypt.encrypt($("#enctype").val());
        if (result) {
            $.ajax({
                url: "/enctype",
                type: "post",
                data: {
                    enctype: result
                },
                success: function (data) {
                    console.log(data);
                }
            });
        } else {
            alert("failed");
        }
    });
</script>
</body>
</html>
