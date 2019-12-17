<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
    <head>
        <title>Panel admin</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- On charge jQuery -->
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!-- On charge le moteur de template mustache https://mustache.github.io/ -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        <!--Load the AJAX API-->
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script>
            $(document).ready(// Exécuté à la fin du chargement de la page
                    function () {
                        ShowALLProducts();
                          $("#flip").click(function(){
                            $("#flip").hide();
                            $(".down").slideDown("slow");
                          });
                          $("#flop").click(function(){
                            $("#flop").hide();
                            $(".bottom").slideDown("slow");
                          });
                          
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

            function Supprimer(code) {
                $.ajax({
                    url: "../SupprimerProduit",
                    data: {"id": code},
                    dataType: "text",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                $('#' + code).append("<span style='color: red;'>Le produit numéro: " + code + " a été supprimé.</span>");
                                $('#mod').remove();
                            }
                });

            }

            function EditionProduit() {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "../EditionProduit",
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
    
            function ShowDataProduct() {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "../ShowProducts",
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                console.log("produit modifié");
                            }
                });
            }

            // Load the Visualization API and the corechart package.
            google.charts.load('current', {'packages': ['corechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(drawChart);

            // Callback that creates and populates a data table,
            // instantiates the pie chart, passes in the data and
            // draws it.
            function drawChart() {

                // Create the data table.
                var data = google.visualization.arrayToDataTable([
                    ['Task', 'Hours per Day'],
                    ['Work', 11],
                    ['Eat', 2],
                    ['Commute', 2],
                    ['Watch TV', 2],
                    ['Sleep', 7]
                ]);

                // Set chart options
                var options = {'title': "Chiffre d'affaires par catégorie de produit",
                    'width': 600,
                    'height': 300}
                ;
                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById('chartdiv'));
                chart.draw(data, options);
                var chart2 = new google.visualization.PieChart(document.getElementById('chartdiv2'));
                chart2.draw(data, options);
                var chart3 = new google.visualization.PieChart(document.getElementById('chartdiv3'));
                chart3.draw(data, options);
            }

            // Fonction qui traite les erreurs de la requête
            function showError(xhr, status, message) {
                alert(JSON.parse(xhr.responseText).message);
            }

        </script>
        <link rel="stylesheet" type="text/css" href="../css/admin.css">
    </head>

    <div id="user" style="display: none;">${userName}</div>

    <body>
        
        <h1> Bienvenue dans le panel Administrateur</h1>
        
        <section style="width: 80%;">
            <div id="prodcontent" style="margin: 0 auto; width: 90%; display: flex; justify-content: space-between;">
                <div id="flip">Pour modifier les produits, cliquer ici</div>
                
                <ul class="product down" style="width:80%; display:none;"></ul>
            </div>
        </section>
        <br>
        <br>
        <br>
        
    <div id="flop">Pour modifier les produits, cliquer ici</div>
                
        <div class="ca bottom" style="display: none;">
            <div class="cacontent">
                <div id="chartdiv" style="width: auto;">
                </div>
                <div id="datesForm">
                    <label for="saisie_le">Date de début</label>
                    <input type="date" id="saisie_le" >

                    <label for="envoyee_le">Date de fin</label>
                    <input type="date" id="envoyee_le" >
                </div>
                <div>
                    <button onclick="ShowCAByCategorie()">Afficher les résultats</button>
                </div>
            </div>
            <div class="cacontent">
                <div id="chartdiv2" style="width: auto;">
                </div>
                <div id="datesForm">
                    <label for="saisie_le">Date de début</label>
                    <input type="date" id="saisie_le" >

                    <label for="envoyee_le">Date de fin</label>
                    <input type="date" id="envoyee_le" >
                </div>
                <div>
                    <button onclick="ShowCAByCategorie()">Afficher les résultats</button>
                </div>
            </div>
            <div class="cacontent">
                <div id="chartdiv3" style="width: auto;">
                </div>
                <div id="datesForm">
                    <label for="saisie_le">Date de début</label>
                    <input type="date" id="saisie_le" >

                    <label for="envoyee_le">Date de fin</label>
                    <input type="date" id="envoyee_le" >
                </div>
                <div>
                    <button onclick="ShowCAByCategorie()">Afficher les résultats</button>
                </div>
            </div>
        </div>

        <form action="<c:url value="../ConnexionController"/>" method="POST"> 
            <input id="log" type='submit' name='action' value='logout'>

        <script id="productsTemplate" type="text/template">
            {{#prod}}
            <li style="display: flex; margin-top: 5px;">
            <div id="use">
              <img src="../images/index.png" alt="image du produit" height="50" width="50">
                <div class="desc" id="{{reference}}" style="line-height:50px;">
                    <span><strong>Ref :</strong> {{reference}} </span>
                    <span><strong>Nom :</strong> {{nom}}</span>
                    <span class="price"><strong>Prix :</strong> {{prix_unitaire}}</span>
                    <span class="stock"><strong>Disponibilités :</strong> {{unites_en_stock}}</span>
                </div>
            </div>
    <div id="mod">
        <button class="btn cl{{reference}}" onclick="Modifier()">Modifier</button>
        <button class="btn cl{{reference}}" onclick="Supprimer({{reference}})">Supprimer</button>
    </div>
</li>
{{/prod}}
        </script>

    </body>
</html>
