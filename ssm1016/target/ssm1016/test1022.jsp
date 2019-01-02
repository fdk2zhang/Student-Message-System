<%@ page import="com.bean.UserTb" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/10/22
  Time: 9:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="my"  uri="http://java.sun.com/jsp/mytag/my" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <h1>test1022.jsp</h1>
  <% UserTb users=new UserTb();
  users.setUserName("刘而能");
  pageContext.setAttribute("u1",users);%>
  <my:abc test="${1>2}" yonghu="${u1}">aaaaaaaa</my:abc>

</body>
</html>
