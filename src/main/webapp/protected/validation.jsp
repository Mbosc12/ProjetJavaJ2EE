  
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
                for (var i = 1; i < localStorage.length; i++) {
                    var it = localStorage.getItem("Panier" + i);        
                    console.log(it);
                    ShowProdName(i)
                    document.getElementById("commande").innerHTML +="<li> Reference : "+ it[0] + " en " + it[2] + " fois</li>";
            }
        }

            function ShowProdName(code) {
                $.ajax({
                    url: "../ShowOneProduit",
                    data: {"code": code},
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#nameTemplate').html();
                                // On combine le template avec le résultat de la requête
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('#nameliste').html(processedTemplate);
                            }
                });
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

        
        <div id="commande">
            
        </div>
        <br><br>
        <form id="info">
            <label>Destinataire</label>
            <input type="text" placeholder="Destinataire" required>
            <br>
            <label>Adresse</label>
            <input type="text" placeholder="Adresse" required>
            <label>Ville</label>
            <input type="text" placeholder="Ville" required>
            <label>Code Postal</label>
            <input type="text" placeholder="Code Postal" required>
            <br>
            <label>Region</label>
            <input type="text" placeholder="type" required>
            <label>Pays</label>
            <input type="text" placeholder="Pays" required>
            <br><br>
            <input type='submit' name='action' value='Commander'>
        </form>
        
        <script id="nameTemplate" type="text/template">
            {{#prod}}
                {{nom}}
            {{/prod}}
        </script>
    </body>
</html>
