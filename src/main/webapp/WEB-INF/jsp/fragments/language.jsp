<%--
  Created by IntelliJ IDEA.
  User: Ilya
  Date: 15.05.2018
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div class="dropdown" style="margin-left: 10px">
    <button class="btn btn-info" type="button" id="dropdownMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${pageContext.response.locale}</button>
    <div class="dropdown-menu" aria-labelledby="dropdownMenu">
        <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_uri']}?lang=ru">Русский</a>
        <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_uri']}?lang=en">English</a>
    </div>
</div>
</body>
</html>
