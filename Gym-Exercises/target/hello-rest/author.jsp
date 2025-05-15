<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.text.SimpleDateFormat, java.util.Date" %>

<!DOCTYPE html>
<html>
<head>
    <title>Autore</title>
</head>
<body>
  <%
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = sdf.format(new Date());
    %>
 
    <h1>Informazioni sull'Autore</h1>
    <p>Nome: Raffaele</p>
    <p>Cognome: Restifo</p>
    <p>Classe: 5BI</p>
    <p>Oggi è il giorno <%= todayDate %></p>
    <p><a href="index.jsp">Torna alla Home</a></p>
</body>
</html>
