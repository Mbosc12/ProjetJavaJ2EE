<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<% String userName = request.getParameter("userName"); %>

<!DOCTYPE html>
<html>
    <head>
        <title>Visiteur</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- On charge jQuery -->
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!-- On charge le moteur de template mustache https://mustache.github.io/ -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        <script>
            $(document).ready(// Exécuté à la fin du chargement de la page
                    function () {
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

            function ShowProdList(code) {
                $.ajax({
                    url: "ShowProduitByCategorie",
                    data: {"categorie": code},
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

            // Fonction qui traite les erreurs de la requête
            function showError(xhr, status, message) {
                alert(JSON.parse(xhr.responseText).message);
            }

        </script>
        <!-- un CSS pour formatter la table -->
        <link rel="stylesheet" type="text/css" href="css/include.css">
        <link rel="stylesheet" type="text/css" href="css/index.css">
    </head>
    
    <div id="user" style="display: none;">${userName}</div>
    
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

        <script id="productsTemplate" type="text/template">
            {{#prod}}
            <li>
            <img src="images/index.png" alt="balek" height="280" width="280">
            <h3>{{nom}}</h3>
            <div class="catinfo" style="display: flex; justify-content: space-between;">
            <p class="price"> Prix : {{prix}}</p>
            <p class="stock"> Disponibilités:{{stock}}</p>
            </div>    
            <button class="addcart" height="10px">Ajouter au panier</button>
            </li>
            {{/prod}}
        </script>

        <script id="categorieTemplate" type="text/template">
            <ul>
            {{#records}}
            <li onclick="ShowProdList('{{code}}')">{{libelle}}</li>
            {{/records}}
            </ul>
        </script>
    </body>
</html>
