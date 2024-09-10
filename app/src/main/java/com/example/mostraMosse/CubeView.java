package com.example.mostraMosse;

import android.content.Context;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.scanner.R;
import com.threeDBJ.MGraphicsLib.texture.TextureFont;

import timber.log.Timber;

public class CubeView extends GLSurfaceView {

    private CubeRenderer renderer;
    TextureFont font;
    RubeCube cube;
    GLWorld world;
    CubeMenu menu;
    char [] configurazione;

    static boolean stopInput= false;

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (world != null) {
            world.setDimensions(w, h);
            menu.setDimensions(w, h);
            //renderer.worldBoundsSet = false;
        }
        Timber.d("onSizeChanged %d %d", w, h);
    }

    public CubeView(Context context) {
        this(context, (AttributeSet) null);
    }

    public CubeView(Context context, char[] configurazione) {
        this(context, null, configurazione);
    }

    public CubeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        font = new TextureFont(getContext(), R.drawable.roboto_regular, "roboto_regular_dims.txt");
        world = new GLWorld();
    }

    public CubeView(Context context, AttributeSet attrs, char [] configurazione) {
        super(context, attrs);
        font = new TextureFont(getContext(), R.drawable.roboto_regular, "roboto_regular_dims.txt");
        world = new GLWorld();
        this.configurazione=configurazione;
    }


    public void initialize(SharedPreferences prefs) {
        //long xtime= System.nanoTime();

        cube = new RubeCube(world, 3,configurazione);
        //System.out.println("||||TEMPO initialize RubeCube "+ (System.nanoTime()-xtime));

        //xtime= System.nanoTime();
        menu = new CubeMenu(cube, font);
        //System.out.println("||||TEMPO initialize CubeMenu "+ (System.nanoTime()-xtime));

        //xtime= System.nanoTime();
        renderer = new CubeRenderer(getContext(), font, world, cube, menu, prefs);
        //System.out.println("||||TEMPO initialize CubeRender "+ (System.nanoTime()-xtime));

        //xtime= System.nanoTime();
        cube.setRenderer(renderer);
        //System.out.println("||||TEMPO initialize setRender "+ (System.nanoTime()-xtime));

        //xtime= System.nanoTime();
        world.setRubeCube(cube);
        //System.out.println("||||TEMPO initialize setRubeCube "+ (System.nanoTime()-xtime));

        //xtime= System.nanoTime();
        setRenderer(renderer);
        //System.out.println("TEMPO initialize render"+ (System.nanoTime()-xtime));

    }
/*
    public void initialize() {
        //long xtime= System.nanoTime();

        cube = new RubeCube(world, 3,configurazione);
        //System.out.println("||||TEMPO initialize RubeCube "+ (System.nanoTime()-xtime));

        //xtime= System.nanoTime();
        menu = new CubeMenu(cube, font);
        //System.out.println("||||TEMPO initialize CubeMenu "+ (System.nanoTime()-xtime));

        //xtime= System.nanoTime();
        //renderer = new CubeRenderer(getContext(), font, world, cube, menu, prefs);
        renderer = new CubeRenderer(getContext(), font, world, cube, menu);
        //System.out.println("||||TEMPO initialize CubeRender "+ (System.nanoTime()-xtime));

        //xtime= System.nanoTime();
        cube.setRenderer(renderer);
        //System.out.println("||||TEMPO initialize setRender "+ (System.nanoTime()-xtime));

        //xtime= System.nanoTime();
        world.setRubeCube(cube);
        //System.out.println("||||TEMPO initialize setRubeCube "+ (System.nanoTime()-xtime));

        //xtime= System.nanoTime();
        setRenderer(renderer);
        //System.out.println("TEMPO initialize render"+ (System.nanoTime()-xtime));

    }

 */

    public void save(SharedPreferences prefs) {
        cube.save(prefs); //salva la configurazione corrente del cubo
        menu.save(prefs); //salva l'iteratore per la lista delle mosse
        //menu.timer.stop();
    }


    public void restore(SharedPreferences prefs) {
        //menu.restore(prefs);
    }


    public boolean onTouchEvent(final MotionEvent e) {
        //System.out.println("||||||rotatingFace|||||||| "+CubeView.stopInput);

        if(CubeView.stopInput) //se c'è un'animazione in corso, non gestire gli input
            return false;

        if (!menu.handleTouch(e)) {
            cube.handleTouch(e);
        }
        return true;
    }

}