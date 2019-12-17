<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% String userName = request.getParameter("userName");%>

<!DOCTYPE html>
<html>
    <head>
        <title>Page d'accueil</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- On charge jQuery -->
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!-- On charge le moteur de template mustache https://mustache.github.io/ -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        <script type="text/javascript" src="js/Panier.js"></script>
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
                                if (user === "") {
                                    $("#log").html('<a href="Login.jsp"><button>Se connecter</button></a>');
                                } else if (user === "admin") {
                                    $("#log").html('<a href="admin/admin.jsp"><button>Panel admin</button></a>');
                                } else {
                                    $("#log").html('<a href="protected/commandes.jsp"><button>Mon Compte</button></a>'); 
                                }
                            }
                });
            }

            function ajouterPan(Ref, Prix) {
                var monPanier = new Panier();
                var storage = localStorage.getItem("Panier"+Ref);
                if (storage === null) {
                    localStorage.setItem("Panier"+Ref,[Ref, 1, Prix]);
                } else {
                    localStorage.setItem("Panier"+Ref, [Ref, parseInt(storage[2], 10)+1, Prix]);
                }
                
                
                
                
                
                monPanier.ajouterArticle(Ref, 1, Prix);
                var tableau = document.getElementById("tableau");
                var longueurTab = parseInt(document.getElementById("nbreLignes").innerHTML);
                if (longueurTab > 0)
                {
                    for (var i = longueurTab; i > 0; i--)
                    {
                        monPanier.ajouterArticle(parseInt(tableau.rows[i].cells[0].innerHTML), parseInt(tableau.rows[i].cells[1].innerHTML), parseInt(tableau.rows[i].cells[2].innerHTML));
                        tableau.deleteRow(i);
                    }
                }
                var longueur = monPanier.liste.length;
                for (var i = 0; i < longueur; i++)
                {
                    var ligne = monPanier.liste[i];
                    var ligneTableau = tableau.insertRow(-1);
                    var colonne1 = ligneTableau.insertCell(0);
                    colonne1.innerHTML += ligne.getCode();
                    var colonne2 = ligneTableau.insertCell(1);
                    colonne2.innerHTML += ligne.qteArticle;
                    var colonne3 = ligneTableau.insertCell(2);
                    colonne3.innerHTML += ligne.prixArticle;
                    var colonne5 = ligneTableau.insertCell(3);
                    colonne5.innerHTML += "<button class=\"btn btn-primary\" type=\"submit\" onclick=\"supprimer(this.parentNode.parentNode.cells[0].innerHTML)\"><span class=\"glyphicon glyphicon-remove\"></span> Retirer</button>";
                }
                document.getElementById("prixTotal").innerHTML = monPanier.getPrixPanier();
                document.getElementById("nbreLignes").innerHTML = longueur;
            }
            
            function supprimer(code)
            {
                var monPanier = new Panier();
                localStorage.removeItem("Panier"+code);
                var tableau = document.getElementById("tableau");
                var longueurTab = parseInt(document.getElementById("nbreLignes").innerHTML);
                if (longueurTab > 0)
                {
                    for(var i = longueurTab ; i > 0  ; i--)
                    {
                        monPanier.ajouterArticle(parseInt(tableau.rows[i].cells[0].innerHTML), parseInt(tableau.rows[i].cells[1].innerHTML), parseInt(tableau.rows[i].cells[2].innerHTML));
                        tableau.deleteRow(i);
                    }
                }
                monPanier.supprimerArticle(code);
                var longueur = monPanier.liste.length;
                for(var i = 0 ; i < longueur ; i++)
                {
                    var ligne = monPanier.liste[i];
                    var ligneTableau = tableau.insertRow(-1);
                    var colonne1 = ligneTableau.insertCell(0);
                    colonne1.innerHTML += ligne.getCode();
                    var colonne2 = ligneTableau.insertCell(1);
                    colonne2.innerHTML += ligne.qteArticle;
                    var colonne3 = ligneTableau.insertCell(2);
                    colonne3.innerHTML += ligne.prixArticle;
                    var colonne4 = ligneTableau.insertCell(3);
                    colonne4.innerHTML += ligne.getPrixLigne();
                    var colonne5 = ligneTableau.insertCell(4);
                    colonne5.innerHTML += "<button class=\"btn btn-primary\" type=\"submit\" onclick=\"supprimer(this.parentNode.parentNode.cells[0].innerHTML)\"><span class=\"glyphicon glyphicon-remove\"></span> Retirer</button>";
                }
                document.getElementById("prixTotal").innerHTML = monPanier.getPrixPanier();
                document.getElementById("nbreLignes").innerHTML = longueur;
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
                        <div id="log" style="line-height: 40px;"></div>
                    </div>
                </div>
                <div class="navbar categories">
                </div>
            </nav>
        </header>
        <section style="width: 80%;">
            <div id="prodcontent" style="margin: 0 auto; width: 90%; display: flex; justify-content: space-between;">
                <ul class="product" style="width:80%; list-style-type: none;"></ul>
                <article class="well form-inline pull-left col-lg-5">
                    <h1>Contenu du panier</h1>
                    <table id="tableau" class="table">
                        <thead>
                            <tr>
                                <th>Nom</th>
                                <th>Qte</th>
                                <th>Prix unitaire</th>
                                <th>Supprimer</th>
                            </tr>
                        </thead>
                    </table>
                    <br><label>Prix du panier total</label> : <label id = "prixTotal"></label> €
                    <label id = "nbreLignes" hidden>0</label><br><br>
                    <a href="/protected/validation.jsp"><button>Valider le panier</button></a>
                </article>
            </div>
        </section>

        <script id="productsTemplate" type="text/template">
            {{#prod}}
            <li>
            <img src="images/index.png" alt="image du produit" height="280" width="280">
            <h3>{{nom}}</h3>
            <div class="catinfo" style="display: flex; justify-content: space-between;">
                <p class="price"> Prix : {{prix_unitaire}} €</p>
                <p class="stock"> Disponibilités:{{unites_en_stock}}</p>
            </div>
            <button class="addcart" onclick="ajouterPan({{reference}}, {{prix_unitaire}})" height="10px">Ajouter au panier</button>
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
