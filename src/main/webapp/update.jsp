<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2020/4/28
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/student" method="post">
    <input name="method" value="UpdateStudent" type="hidden">
    <input name="id" value="${param.id}" type="hidden">
    姓名：<input type="text" name="name" value="${param.name}" > <br>
    生日：<input type="date" name="birthday" > <br>
    描述：<input type="text" name="description" value="${param.description}"> <br>
    分数：<input type="number" name="avgscore" value="${param.avgscore}" onkeyup="value=value.replace(/[^[0-9]{1,2}$]/,'')"> <br>
    <input type="submit" value="提交">
</form>
</body>
</html>
