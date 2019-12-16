  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!-- On charge le moteur de template mustache https://mustache.github.io/ -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            $(document).ready(// Exécuté à la fin du chargement de la page
                    function () {
                        var user = document.getElementById("user").innerHTML;
                        ShowCommandes(user);
                        ShowClientInformations(user);
                    }
            );
            function ShowCommandes(client) {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "../ShowCommande",
                    dataType: "json",
                    data: {"client": client},
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#commandsTemplate').html();
                                // On combine le template avec le résultat de la requête
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('.commands').html(processedTemplate);
                            }
                });
            }

            function ShowClientInformations(client) {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "../ShowClientInformations",
                    dataType: "json",
                    data: {"client": client},
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#personnaldata').html();
                                // On combine le template avec le résultat de la requête
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('.persodata').html(processedTemplate);
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

        <a href="../index.jsp"><button>Menu</button></a>

        
        <h1> Mes commandes </h1>
        <div class="commands">

        </div>

        <h1> Editer mes informations personnelles </h1>
        
        <div class="persodata">

        </div>

        <form action="<c:url value="../ConnexionController"/>" method="POST"> 
            <input type='submit' name='action' value='logout'>
    </form>

    <style type="text/css">

        td {
            border: 2px double grey;
            padding: 2px 5px;
        }

    </style>

    <script id="commandsTemplate" type="text/template">

        <table>
        <tr>
        <td>Numero de commande</td>
        <td>Destinataire</td>
        <td>Adresse de livraison</td>
        <td>Pays</td>
        </tr>
        {{#commands}}
        <tr>
        <td>{{numero}}</td>
        <td>{{destinataire}}</td>
        <td>{{adresse_livraison}}, {{ville_livraison}}, {{code_postal_livrais}}</td>
        <td>{{pays_livraison}}</td>
        </tr>
        {{/commands}}
        </table>
    </script>

    <script id="personnaldata" type="text/template">
        <form>
        {{#info}}
        <div class="data">
        <label>Nom</label>
        <input type="text" placeholder="{{contact}}">

        <label>Adresse</label>
        <input type="text" placeholder="{{adresse}}">
        </div>

        <div class="data">
        <label>Ville</label>
        <input type="text" placeholder="{{ville}}">

        <label>Code Postal</label>
        <input type="text" placeholder="{{code_postal}}">

        <label>Pays</label>
        <input type="text" placeholder="{{pays}}">
        </div>

        <div class="data">
        <label>Telephone</label>
        <input type="text" placeholder="{{telephone}}">

        <label>Fax</label>
        <input type="text" placeholder="{{fax}}">
        </div>

        <div class="data">
        <label>Société</label>
        <input type="text" placeholder="{{societe}}">

        <label>Fonction</label>
        <input type="text" placeholder="{{fonction}}">
        </div>
        {{/info}}
        </form>
    </script>
</body>
</html>
