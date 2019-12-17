<%@page contentType="text/html" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Page de connexion</title>
	</head>
	<body>

		<h1>Bienvenue dans notre application</h1>
                <p>Veuillez vous identifier pour pouvoir accéder à tous vos droits </p>
		<%--
		La servlet fait : request.setAttribute("errorMessage", "Login/Password incorrect");
		La JSP récupère cette valeur dans ${errorMessage}
		--%>
		<div style="color:red">${errorMessage}</div>

		<form action="<c:url value="ConnexionController" />" method="POST"> <!-- l'action par défaut est l'URL courant, qui va rappeler la servlet -->
			Utilisateur  : <input name='loginParam'><br>
			Mot de passe  : <input name='passwordParam' type='password'><br>
                        <input type='submit' name='action' value='login'>
		</form>
		<!-- On montre le nombre d'utilisateurs connectés -->
		<!-- Cette information est stockée dans le scope "application" par le listener -->
	</body>
</html>