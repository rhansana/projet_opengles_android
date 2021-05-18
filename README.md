#### Projet du Taquin en OpenGLES
 Fait par Robert HANSANA
 
 ### Application
 
 ## Partie OpenGLES
 
 Pour le choix des figures à affichées, je suis partie du code qui a été donnée en TD.
 Il y a un carré, un triangle, un cercle et un plateau.
 Le code des figures ne changent pas grandement et reste à peu près similiraire si ce n'est dans les sommets donnés.
 On fera hérité toutes les figures d'une interface Shape afin de faciliter leurs manipulation dans le modèle.
 
 ## Partie Taquin
 
 Pour créer cette application, la partie qui va s'occuper du jeu et de ces différents états sont les classes Game et GameState.
 La classe Game sert à initier les figures, leurs couleurs et leurs position.
 Le GameState s'occupera de gérer la grille, les mélanges, les déplacements et les vérifications.
 
 ## Le résultat

 Au lancement de l'application, le plateau s'affiche avec une grille déjà mélangée.

![img1](images/img1)

 Lorsqu'on effectue un mouvement impossible, une pop-up apparaitra indiquant que le mouvement n'est pas possible

 ![img2](images/img2)

 Lorsqu'on réussi le taquin une petit musique se lance pour montrer la réussite.
 Un pop-up apparait aussi indiquant qu'en ré-appuyant cela mélangera le grille une nouvelle fois.

 ![img3](images/img3)
 
 
