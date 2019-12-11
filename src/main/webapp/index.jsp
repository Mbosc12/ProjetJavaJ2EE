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
            $(document).ready(// Ex�cut� � la fin du chargement de la page

                    );
            
            function ShowCategories() {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "ShowCategories",
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les r�sultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#categorieTemplate').html();
                                // On combine le template avec le r�sultat de la requ�te
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('.categories').html(processedTemplate);
                            }
                });
            }


            function ShowProducts() {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "ShowProducts",
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les r�sultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#productsTemplate').html();
                                // On combine le template avec le r�sultat de la requ�te
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('.product').html(processedTemplate);
                            }
                });
            }
            window.onload = function() { ShowProducts(); ShowCategories(); }; 
    // Fonction qui traite les erreurs de la requ�te
            function showError(xhr, status, message) {
                alert(JSON.parse(xhr.responseText).message);
            }
            
            
            
            
            
            
            
            
            
            
                     
            $(function () {
                $(".itemprod").slice(0, 4).show();
                $("#loadMore").on('click', function (e) {
                    e.preventDefault();
                    $("ul li:hidden").slice(0, 4).slideDown();
                    if ($(".product:hidden").length == 0) {
                        $("#load").fadeOut('slow');
                    }
                    $('section').animate({
                        scrollTop: $(this).offset().top
                    }, 1500);
                });
            });

            $('a[href=#top]').click(function () {
                $('section').animate({
                    scrollTop: 0
                }, 600);
                return false;
            });

            $(window).scroll(function () {
                if ($(this).scrollTop() > 50) {
                    $('.totop a').fadeIn();
                } else {
                    $('.totop a').fadeOut();
                }
            });

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
                        <h3 style="margin-top: 22px; margin-left: 10px;"> ${userName} </h3>
                        
                    </div>
                </div>
                <div class="navbar categories">
                </div>
            </nav>
        </header>
        
     
        <section>
            <ul class="product"></ul>          
            


            <a href="#" id="loadMore">Load More</a>

            <p class="totop"> 
                <a class="atop" href="#top">Back to top</a> 
            </p>
        </section>

        
        
        
            <script id="productsTemplate" type="text/template">
                {{#prod}}
                <li class="itemprod">
                <img src="images/index.png" alt="balek" height="280" width="280">
                <h3>{{nom}}</h3>
                <button class="addcart" height="10px">Ajouter au panier</button></li>
                {{/prod}}
            </script>
            
            <script id="categorieTemplate" type="text/template">
                <ul>
                {{#records}}
                <li>{{libelle}}</li>
                {{/records}}
                </ul>
            </script>
</body>
</html>
