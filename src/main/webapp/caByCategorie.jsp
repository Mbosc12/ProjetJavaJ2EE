<%-- 
    Document   : caByCategorie
    Created on : 15 déc. 2019, 11:44:43
    Author     : lauriecoumes
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% String userName = request.getParameter("userName");%>

<!DOCTYPE html>
<html>
    <head>
        <title>Chiffre d'affaires par catégorie de produit</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- On charge jQuery -->
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!-- On charge le moteur de template mustache https://mustache.github.io/ -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        <script>
            $(document).ready(// Exécuté à la fin du chargement de la page
                    function () {
                        isLogin(this);
                    }
            );

            function isLogin(usr) {
                $.ajax({
                    url: "ConnexionController",
                    data: {"action": $(usr).val()},
                    dataType: "text",
                    error: showError,
                    success:
                            function (result) {
                                var user = document.getElementById("user").innerHTML;
                                if (user === "") {
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

        <!--Load the AJAX API-->
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript">

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
                    'width': 800,
                    'height': 800}
                ;
                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
                chart.draw(data, options);
            }

        </script>
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

        <div id="datesForm">
            <label for="saisie_le">Date de début</label>
            <input type="date" id="saisie_le" >

            <label for="envoyee_le">Date de fin</label>
            <input type="date" id="envoyee_le" >
        </div>

        <div>
            <button onclick="ShowCAByCategorie()">Afficher les résultats</button>
        </div>

        <!--Div that will hold the pie chart-->
        <div id="chart_div"></div>

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
