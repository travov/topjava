<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Add new Meal</title>
</head>
<body>

<br>
<br>

<form method="post" action="" name="frmAddMeal">
    <table align="center">
    <tr><td>ID:</td> <td><input type="text" readonly="readonly" name="id" value="<c:out value="${meal.id}" />" /></td></tr> <br>
    <tr><td>Description:</td> <td><input type="text" name="description" value="<c:out value="${meal.description}" />" /></td> </tr> <br>
   <tr><td>Date and Time:</td> <td><input type="text" name="date" placeholder="yyyy MM dd hh:mm" value="<c:out value="${meal.dateTime}" />" /></td> </tr> <br>
   <tr><td>Calories:</td> <td><input type="text" name="calories" value="<c:out value="${meal.calories}" />"/></td> </tr><br>
    </table>
    <input type="submit" value="Submit" align="center" />
</form>
</body>
</html>