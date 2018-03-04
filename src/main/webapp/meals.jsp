<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>

<h1>List of Meals</h1>

<table>
    <thead>
    <tr>
        <th width="30%">Description</th>
        <th width="50%">Date and Time</th>
        <th width="20%">Calories</th>
    </tr>

    <c:forEach items="${listOfAllMeals}" var="meal">
        <tr bgcolor="${meal.exceed} ? red : green" align="center">
            <td>${meal.description}</td>
            <td><fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}" /> </td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>

    </thead>
</table>


</body>
</html>
