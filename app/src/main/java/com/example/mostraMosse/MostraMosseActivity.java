package com.example.mostraMosse;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class MostraMosseActivity extends Activity {

    SharedPreferences prefs;
    CubeView cubeView;
    Bundle bundle;
    static String[] configurazione;
    static char[] coloriFacce;
    char[] configAsChArr;

    private char[] prova={'g','b','o','y','w','g','o','o','r','y','w','w','r','r','o','y','o','w','g','b','g','y','g','b','y','w','o','r','r','b','g','y','w','w','o','r','o','r','y','y','o','g','g','r','b','b','w','w','y','b','b','b','g','r'};

    /*private String strtmp="gboywgoorywwrroyowgbgygbyworrbgywwororyyoggrbbwwybbbgr";
    private char[] prova2=strtmp.toCharArray();*/

    /*private String strtmp2="gboyrowwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww";
    private char[] prova3=strtmp2.toCharArray();*/

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);//ottiene il riferimento al file col le preferencies dell'app


        //comment that to see preferences working on first onResume() (ONLY DEBUG PURPOSE, le preferences have to be already saved)
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean("isConfigSaved", false);
        edit.apply();


/*
 *       TODO:
 *         (1) impostare correttamente faceColors (indipendentemente dall'ordine delle facce in "configurazione") in RubeCube al momento della prima cofigurazione,
 *              in modo da poter ripristinare la configurazione salvata nelle Preferences on resume
 *         (2) una volta completato il punto (1) è necessario impostare la Preference "isConfigSaved" come False PRIMA di chiamare la funzione onCreate() di questa Activity
 *              (deve essere impostata della Activity "Scanner")
 */

        bundle = getIntent().getExtras();
        configurazione = bundle.getStringArray ("configurazione");
        coloriFacce = bundle.getCharArray("colori facce");
        configAsChArr=convertConfig(configurazione);

        //System.out.println("||||CONFIG "+ Arrays.toString(configAsChArr));
        //System.out.println("||||CONFIG "+configurazione[0]);
        cubeView = new CubeView(this, configAsChArr);

        long xtime= System.nanoTime();
        cubeView.initialize(prefs);
        //cubeView.initialize();
        System.out.println("TEMPO initialize "+ (System.nanoTime()-xtime));
        //Button btnnxtmove=(Button)findViewById(R.id.button_id);

        setContentView(cubeView);


        //this.addContentView(btnnxtmove ,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        cubeView.save(prefs);
        //cubeView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        cubeView.restore(prefs);
        //cubeView.onResume();
    }

    private char[] convertConfig(String[] conf1){
        char[] conf2;
        String s = "";

        for (String n:conf1)
            s+= n;

        s = s.toLowerCase();
        conf2 = s.toCharArray();

        return conf2;
    }

    public static String[] getConfigurazione() {
        return configurazione;
    }
}
