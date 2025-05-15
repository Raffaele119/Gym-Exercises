<%@ page import="java.text.SimpleDateFormat, java.util.Date" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Benvenuto</title>
</head>
<body>
    <h1>Benvenuto Utente Curioso, questa è la mia prima JSP.</h1>
    <%
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = sdf.format(new Date());
    %>
    <p>Oggi è il giorno <%= todayDate %></p>
    <p>Autore: <a href="author.jsp">Clicca qui</a></p>
</body>
</html>
