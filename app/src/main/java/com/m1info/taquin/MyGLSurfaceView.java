/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.m1info.taquin;

import android.content.Context;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/* La classe MyGLSurfaceView avec en particulier la gestion des événements
  et la création de l'objet renderer

*/


/* On va dessiner un carré qui peut se déplacer grace à une translation via l'écran tactile */

public class MyGLSurfaceView extends GLSurfaceView {

    /* Un attribut : le renderer (GLSurfaceView.Renderer est une interface générique disponible) */
    /* MyGLRenderer va implémenter les méthodes de cette interface */

    private final MyGLRenderer mRenderer;
    private MediaPlayer mp;

    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        // Création d'un context OpenGLES 2.0
        setEGLContextClientVersion(3);

        // Création du renderer qui va être lié au conteneur View créé
        mRenderer = new MyGLRenderer();
        setRenderer(mRenderer);

        // Option pour indiquer qu'on redessine uniquement si les données changent
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        this.mp = MediaPlayer.create(context,R.raw.win_sound);
    }

    /* pour gérer la translation */
    private float mPreviousX;
    private float mPreviousY;

    /* Comment interpréter les événements sur l'écran tactile */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // Les coordonnées du point touché sur l'écran
        float x = e.getX();
        float y = e.getY();

        // la taille de l'écran en pixels
        float screen_x = getWidth();
        float screen_y = getHeight();



        // Des messages si nécessaires */
        Log.d("message", "x"+Float.toString(x));
        Log.d("message", "y"+Float.toString(y));
        Log.d("message", "screen_x="+Float.toString(screen_x));
        Log.d("message", "screen_y="+Float.toString(screen_y));


        /* accès aux paramètres du rendu (cf MyGLRenderer.java)
        soit la position courante du centre du carré
         */
        float[] pos = mRenderer.getPosition();

        /* Conversion des coordonnées pixel en coordonnées OpenGL
        Attention l'axe x est inversé par rapport à OpenGLSL
        On suppose que l'écran correspond à un carré d'arête 2 centré en 0
         */

        float get_width=getWidth()/100.0f;
        float get_heigth=getHeight()/100.0f;

        float x_opengl = get_width*x/getWidth() - get_width;
        float y_opengl = -get_heigth*y/getHeight() + get_heigth;


        Log.d("message","x_opengl="+Float.toString(x_opengl));
        Log.d("message","y_opengl="+Float.toString(y_opengl));

        /* Le carré représenté a une arête de 2 (oui il va falloir changer cette valeur en dur !!)
        /* On teste si le point touché appartient au carré ou pas car on ne doit le déplacer que si ce point est dans le carré
        */

       boolean top_left = ((x_opengl <= -6.5f) && (x_opengl >= -8.5f) &&
               (y_opengl <= 13.0f) && (y_opengl >= 11.5f));
       boolean top_middle = ((x_opengl <= -4.75f) && (x_opengl >= -6.0f) &&
                (y_opengl <= 13.0f) && (y_opengl >= 11.5f));
       boolean top_right = ((x_opengl <= -2.75f) && (x_opengl >= -4.0f) &&
                (y_opengl <= 13.0f) && (y_opengl >= 11.5f));

       boolean middle_left = ((x_opengl<= -6.5f) && (x_opengl>+ -8.5f) &&
               (y_opengl <= 11.0f) && (y_opengl >= 9.5f));
       boolean middle_middle = ((x_opengl <= -4.75f) && (x_opengl >= -6.0f) &&
                (y_opengl <= 11.0f) && (y_opengl >= 9.5f));
       boolean middle_right = ((x_opengl <= -2.75f) && (x_opengl >= -4.0f) &&
                (y_opengl <= 11.0f) && (y_opengl >= 9.5f));

       boolean bottom_left = ((x_opengl<= -6.5f) && (x_opengl>+ -8.5f) &&
                (y_opengl <= 9.0f) && (y_opengl >= 7.5f));
       boolean bottom_middle = ((x_opengl <= -4.75f) && (x_opengl >= -6.0f) &&
                (y_opengl <= 9.0f) && (y_opengl >= 7.5f));
       boolean bottom_right = ((x_opengl <= -2.75f) && (x_opengl >= -4.0f) &&
                (y_opengl <= 9.0f) && (y_opengl >= 7.5f));

        ArrayList<Boolean>lesZones = new ArrayList<>(Arrays.asList(top_left,top_middle,top_right,
                middle_left,middle_middle,middle_right,bottom_left,bottom_middle,bottom_right));

        if (top_left || top_middle || top_right || middle_left || middle_middle || middle_right ||
                bottom_left || bottom_middle || bottom_right) {
            switch (e.getAction()) {
                /* Lorsqu'on touche l'écran on mémorise juste le point */
                case MotionEvent.ACTION_UP:
                    if(this.mRenderer.getGame().getmGameState().getIsShuffle()) {
                        for (int i = 0; i < lesZones.size(); i++) {
                            if (lesZones.get(i)) {
                                if (this.mRenderer.getGame().getmGameState().isMoveable(i / 3, i % 3)) {
                                    this.mRenderer.getGame().getmGameState().moveShape(i / 3, i % 3);
                                    requestRender();
                                    break;
                                } else {
                                    Toast.makeText(getContext(), "Mouvement impossible !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        if (this.mRenderer.getGame().getmGameState().isSolved()) {
                            Log.d("message", "Partie terminée");
                            Toast.makeText(getContext(), "Appuyer sur l'écran pour rejouer", Toast.LENGTH_SHORT).show();
                            this.mp.start();
                        }
                    }else {
                        for (int i = 0;i<50;i++) {
                            this.mRenderer.getGame().getmGameState().randomizeMoves();
                        }
                        this.mRenderer.getGame().getmGameState().setIsShuffle(true);
                        requestRender();
                    }
            }
        }
        return true;
    }

}
