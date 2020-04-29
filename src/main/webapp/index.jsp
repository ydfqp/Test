<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<h2>学生列表!</h2>
<%--<c:out value="${studentlist}"/> <br/>--%>
<a href="${pageContext.request.contextPath}/insert.jsp"> 插入 </a>
<a href="${pageContext.request.contextPath}/student?method=StudentList"> 刷新 </a>
<table>
    <tr>
        <td>学生id</td>
        <td>学生姓名</td>
        <td>学生生日</td>
        <td>描述</td>
        <td>平均分</td>
        <td>操</td>
        <td>作</td>
    </tr>
<c:forEach items="${studentlist.data}" var="student">
    <tr>
        <td>${student.id }</td>
        <td>${student.name }</td>
        <td>${student.birthday }</td>
        <td>${student.description }</td>
        <td>${student.avgscore }</td>
        <td><a href="${pageContext.request.contextPath}/student?method=DeleteStudent&&id=${student.id }">删除</a></td>
        <td><a href="${pageContext.request.contextPath}/update.jsp?id=${student.id }&&name=${student.name }&&birthday=${student.birthday }&&description=${student.description }&&avgscore=${student.avgscore }">修改</a></td>
    </tr>
</c:forEach>
</table>
<%--${studentlist.maxPage}<<br>--%>
<%--${studentlist.nowPage}<<br>--%>
<%--${studentlist.limit}<<br>--%>
<c:forEach begin="1" end="${studentlist.maxPage}" var="i" >
    <a href="${pageContext.request.contextPath}/student?method=StudentList&&page=${i}">  ${i} </a>
</c:forEach>
</body>
</html>
