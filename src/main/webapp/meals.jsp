<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>

<h1>List of Meals</h1>

<table width="90%" align="center" style="font-size: large" >
    <thead>
    <tr>
        <th width="10%">ID</th>
        <th width="30%">Description</th>
        <th width="40%">Date and Time</th>
        <th width="20%">Calories</th>
    </tr>

    <c:forEach items="${listOfAllMeals}" var="meal">
        <tr align="center" style="color: ${meal.exceed ? "red" : "green"}">
            <td>${meal.id}</td>
            <td>${meal.description}</td>
            <td><fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}" /> </td>
            <td>${meal.calories}</td>
            <td><a href="?action=edit&id=${meal.id}">Update</a> </td>
            <td><a href="?action=delete&id=<c:out value="${meal.id}" />">Delete</a> </td>
        </tr>
    </c:forEach>

    </thead>
</table>

<p align="center"> <a href="?action=insert">Add Meal</a> </p>

</body>
</html>
