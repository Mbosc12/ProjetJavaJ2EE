<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


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
                    }
            );

            function ShowALLProducts() {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "../ShowProducts",
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
        <section style="width: 80%;">
            <div id="prodcontent" style="margin: 0 auto; width: 90%; display: flex; justify-content: space-between;">
                <ul class="product" style="width:80%;"></ul>
            </div>
        </section>

        <script id="productsTemplate" type="text/template">
            {{#prod}}
            <li style="display: flex; margin-top: 5px;">
            <img src="../images/index.png" alt="image du produit" height="50" width="50">
            <div class="desc" style="line-height:50px;">
                <span>{{nom}}</span> <span class="price"> Prix : {{prix}}</span> <span class="stock"> Disponibilités:{{stock}}</span>
            </div>
            <button>Modifier</button>
            <button>Supprimer</button>
    </li>
    {{/prod}}
        </script>

    </body>
</html>
