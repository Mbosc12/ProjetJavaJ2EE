<%-- 
    Document   : editClient
    Author     : lauriecoumes
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% String userName = request.getParameter("userName");%>

<!DOCTYPE html>
<html>
    <head>
        <title>Client</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- On charge jQuery -->
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!-- On charge le moteur de template mustache https://mustache.github.io/ -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        <script>
            $(document).ready(// Exécuté à la fin du chargement de la page
                    function () {
                        showClient();
                        ShowALLProducts();
                        ShowCategories();
                        isLogin(this);
                    }
            );
    
           function ShowCategories() {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "ShowCategories",
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#categorieTemplate').html();
                                // On combine le template avec le résultat de la requête
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('.categories').html(processedTemplate);
                            }
                });
            }


            function ShowALLProducts() {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "ShowProducts",
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#productsTemplate').html();
                                // On combine le template avec le résultat de la requête
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('.product').html(processedTemplate);
                            }
                });
            }
            
            function isLogin(usr) {
                $.ajax({
                    url: "ConnexionController",
                    data: {"action": $(usr).val()},
                    dataType: "text",
                    error: showError,
                    success:
                            function (result) {
                                    var user = document.getElementById("user").innerHTML;
                                    if (user === ""){
                                        $("#log").html('<a href="Login.jsp"><button>Se connecter</button></a>');
                                    } else {
                                        $("#log").html('<a href="protected/commandes.jsp"><button>Mon Compte</button></a>');
                                    }
                            }
                });
            }
    
            function showClient() {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "ShowClientInformations",
                    //data: {"client": userName},
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#clientTemplate').html();
                                // On combine le template avec le résultat de la requête
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('#client').html(processedTemplate);
                            }
                });
            }

            function editClient() {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "EditClient",
                    // serialize() renvoie tous les paramètres saisis dans le formulaire
                    data: $("#codeForm").serialize(),
                    dataType: "json",
                    success: // La fonction qui traite les résultats
                            function (result) {
                                showClient();                        
                                console.log(result);
                            },
                    error: showError
                });
                return false;
            }

            // Fonction qui traite les erreurs de la requête
            function showError(xhr, status, message) {
                alert(JSON.parse(xhr.responseText).message);
            }

        </script>
        <!-- un CSS pour formatter la table -->
        <link rel="stylesheet" type="text/css" href="css/include.css">
        <link rel="stylesheet" type="text/css" href="css/index.css">
    </head>


    <body>
        <header>
            <nav>
                <div class="navbar">
                    <div class="left">
                        <img src="images/logo.png" alt="logo" width="380">
                    </div>
                    <div class="right">
                        <img class="icon" src="images/icon/magnifier.png" alt="logo" width="40">
                        <img class="icon" src="images/icon/shopping-cart.png" alt="logo" width="40">
                        <div id="log"></div>
                    </div>
                </div>
                <div class="navbar categories">
                </div>
            </nav>
        </header>
        <section>
            <ul class="product"></ul>          
        </section>

        <script id="categorieTemplate" type="text/template">
        </script>

        <!-- On montre le formulaire de saisie -->
        <form id="codeForm" onsubmit="event.preventDefault(); editClient();">
            <fieldset><legend>Modifier mes information personnelles :</legend>
            Prénom + nom : <input id="nom" name="nom"><br/>
            Societe : <input id="societe" name="societe"><br/>
            Fonction : <input id="fonction" name="fonction"><br/>
            Adresse : <input id="adresse" name="adresse"><br/>
            Ville : <input id="ville" name="ville"><br/>
            Région : <input id="region" name="region"><br/>
            Code postal : <input id="codepostal" name="codepostal"><br/>
            Pays : <input id="pays" name="pays"><br/>
            Telephone : <input id="telephone" name="telephone"><br/>
            Fax : <input id="fax" name="fax"><br/>
            <input type="submit" value="Enregistrer les modifications">
            </fieldset>
        </form>
        
        <div id="client"></div>
        
        <!-- Le template qui sert à formatter la liste des codes -->
        <script id="clientTemplate" type="text/template">
            <TABLE>
            <tr>
            <th>Prénom + Nom</th>
            <th>Societe</th>
            <th>Fonction</th>
            <th>Adresse</th>
            <th>Ville</th>
            <th>Region</th>
            <th>Code Postal</th>
            <th>Pays</th>
            <th>Telephone</th>
            <th>Fax</th>
            </tr>
            {{! Pour chaque enregistrement }}
            {{#info}}
                {{! Une ligne dans la table }}
                <TR>
                <TD>{{contact}}</TD>
                <TD>{{societe}}</TD>
                <TD>{{fonction}}</TD>
                <TD>{{adresse}}</TD>
                <TD>{{ville}}</TD>
                <TD>{{region}}</TD>
                <TD>{{code_postal}}</TD>
                <TD>{{pays}}</TD>
                <TD>{{telephone}}</TD>
                <TD>{{fax}}</TD>
                <TD><button onclick="deleteCode('{{discountCode}}')">Supprimer</button></TD>
                </TR>
            {{/info}}
            </TABLE>
        </script>
    </body>
</html>
