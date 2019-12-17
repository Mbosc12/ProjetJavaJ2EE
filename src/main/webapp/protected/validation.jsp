  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!-- On charge le moteur de template mustache https://mustache.github.io/ -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Panel Client</title>
        <script>
            $(document).ready(// Exécuté à la fin du chargement de la page
                    function () {
                        validationCommande();
                    }
            );
            function validationCommande() {
                for (var i = 1; i < localStorage.length - 1; i++) {
                    var it = localStorage.getItem("Panier" + i);        
                    console.log(it);
                    document.getElementById("commande").innerHTML +="<p>Produit " + it[0] + " en " + it[2] + " fois</p>";
            }
        }

            // Fonction qui traite les erreurs de la requête
            function showError(xhr, status, message) {
                alert(JSON.parse(xhr.responseText).message);
            }
        </script>
    </head>
    <body>
        <h1 id="user" style="display:none;">${code}</h1>

        <h1> Récapitulatif de votre commande </h1>


        <div id="commande"></div>
    </body>
</html>
