<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2020/4/28
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增学生</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/student" method="post">
    <input name="method" value="InsertStudent" type="hidden">
    姓名：<input type="text" name="name"> <br>
    生日：<input type="date" name="birthday"> <br>
    描述：<input type="text" name="description"> <br>
    分数：<input type="number" name="avgscore" onkeyup="value=value.replace(/[^[0-9]{1,2}$]/,'')"> <br>
    <input type="submit" value="提交">
</form>
</body>
</html>
