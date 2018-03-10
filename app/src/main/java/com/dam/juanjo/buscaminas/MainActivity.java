package com.dam.juanjo.buscaminas;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Window;



public class MainActivity extends AppCompatActivity {
    TableLayout tableroLayout;
    ImageButton b;
    ImageButton boton;
    Matriz m= new Matriz(8);
    int contador;
    int numeroMinas=10;
    int monaImg=R.drawable.mona1;
    String imageNameDatabase[] = {"Mona 1", "Mona 2", "Mona 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Configuraciones de la ActionBar
        getSupportActionBar().setTitle("Buscamonas");
        getSupportActionBar().setSubtitle("El juego");
        getSupportActionBar().setLogo(monaImg);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //bloquear la rotación
        crearTablero();

    }

    //A éste método se llama desde el XML en el atributo onclick
    public void pulsacionSimple(View view){

        String valor="";
        valor= String.valueOf(view.getId()); //Almaceno el valor del id en un String

        //Se ejecutan procedimientos para del string sacar las coordenadas de la matriz tablero
        int i=0;
        int j=0;
        if(valor.length()<=2){
            i=0;
            j=view.getId();
        }
        else if (valor.length()==3){ //Cuando la matriz es de mas de 9x9 necesitamo otros indices del string
            String aux="";
            aux+=valor.charAt(1);
            aux+=valor.charAt(2);
            j= Integer.parseInt(aux);
            i=Character.getNumericValue( valor.charAt(0));
        }
        else{
            String aux1="";
            String aux2="";
            aux1+=valor.charAt(2);
            aux1+=valor.charAt(3);
            aux2+=valor.charAt(0);
            aux2+=valor.charAt(1);
            j= Integer.parseInt(aux1);
            i = Integer.parseInt(aux2);
        }
        b =(ImageButton) findViewById(view.getId()); //Obtengo la vista del botón
        if(m.tablero[i][j]==-1) { //en -1 es bomba
            b.setImageResource(monaImg); //pone el drawable de la bomba
            gameOver(1,"GAME OVER"); //pierdes la partida
        }
        else{
            switch(m.tablero[i][j]) { //Si no es bomba
                case 0: { //Casilla vacia
                    b.setImageDrawable(null); //mostramos casilla vacia
                    m.tablero[i][j] = 99; //99 para visible
                    b.setEnabled(false);
                    //Compruebo las 8 casillas vecinas
                    //En cada condicion hay que comprobar que la casilla no este fuera de la matriz
                    //caso que se da en los bordes

                    //ARRIBA A LA IZQUIERDA
                    if (!m.fueraTablero(i - 1) && !m.fueraTablero(j - 1)) {
                        if (m.tablero[i - 1][j - 1] != -1) { //Si no es bomba
                            int pos = ((i - 1) * 100) + (j - 1); //Construyo la id
                            boton =(ImageButton) findViewById(pos);
                            //Si es casilla vacia es necesario que la funcion se llame a si misma (recursividad)
                            if (m.tablero[i - 1][j - 1] == 0) pulsacionSimple(boton);
                            else ponerImagenNumero(boton, m.tablero[i - 1][j - 1]); //sino, ponemos el drawable numero correspondiente
                        }
                    }

                    //IZQUIERDA
                    if (!m.fueraTablero(i - 1) && !m.fueraTablero(j)) {
                        if (m.tablero[i - 1][j] != -1) {
                            int pos = ((i - 1) * 100) + (j);
                            boton =(ImageButton) findViewById(pos);
                            if (m.tablero[i - 1][j] == 0) pulsacionSimple(boton);
                            else ponerImagenNumero(boton, m.tablero[i - 1][j]);
                        }
                    }

                    //ABAJO A LA DERECHA
                    if (!m.fueraTablero(i - 1) && !m.fueraTablero(j + 1)) {
                        if (m.tablero[i - 1][j + 1] != -1) {
                            int pos = ((i - 1) * 100) + (j + 1);
                            boton =(ImageButton) findViewById(pos);
                            if (m.tablero[i - 1][j + 1] == 0) pulsacionSimple(boton);
                            else ponerImagenNumero(boton, m.tablero[i - 1][j + 1]);
                        }
                    }

                    if (!m.fueraTablero(i) && !m.fueraTablero(j - 1)) {
                        if (m.tablero[i][j - 1] != -1) {
                            int pos = ((i) * 100) + (j - 1);
                            boton =(ImageButton) findViewById(pos);
                            if (m.tablero[i][j - 1] == 0) pulsacionSimple(boton);
                            else ponerImagenNumero(boton, m.tablero[i][j - 1]);
                        }
                    }

                    if (!m.fueraTablero(i) && !m.fueraTablero(j + 1)) {
                        if (m.tablero[i][j + 1] != -1) {
                            int pos = ((i) * 100) + (j + 1);
                            boton =(ImageButton) findViewById(pos);
                            if (m.tablero[i][j + 1] == 0) pulsacionSimple(boton);
                            else ponerImagenNumero(boton, m.tablero[i][j + 1]);
                        }
                    }

                    if (!m.fueraTablero(i + 1) && !m.fueraTablero(j - 1)) {
                        if (m.tablero[i + 1][j - 1] != -1) {
                            int pos = ((i + 1) * 100) + (j - 1);
                            boton =(ImageButton) findViewById(pos);
                            if (m.tablero[i + 1][j - 1] == 0) pulsacionSimple(boton);
                            else ponerImagenNumero(boton, m.tablero[i + 1][j - 1]);
                        }
                    }

                    if (!m.fueraTablero(i + 1) && !m.fueraTablero(j)) {
                        if (m.tablero[i + 1][j] != -1) {
                            int pos = ((i + 1) * 100) + (j);
                            boton =(ImageButton) findViewById(pos);
                            if (m.tablero[i + 1][j] == 0) pulsacionSimple(boton);
                            else ponerImagenNumero(boton, m.tablero[i + 1][j]);
                        }
                    }

                    if (!m.fueraTablero(i + 1) && !m.fueraTablero(j + 1)) {
                        if (m.tablero[i + 1][j + 1] != -1) {
                            int pos = ((i + 1) * 100) + (j + 1);
                            boton =(ImageButton) findViewById(pos);
                            if (m.tablero[i + 1][j + 1] == 0) pulsacionSimple(boton);
                            else ponerImagenNumero(boton, m.tablero[i + 1][j + 1]);
                        }
                    }
                }
                    break;
                case 1:
                    b.setImageResource(R.drawable.n1);
                    b.setEnabled(false);
                    break;
                case 2:
                    b.setImageResource(R.drawable.n2);
                    b.setEnabled(false);
                    break;
                case 3:
                    b.setImageResource(R.drawable.n3);
                    b.setEnabled(false);
                    break;
                case 4:
                    b.setImageResource(R.drawable.n4);
                    b.setEnabled(false);
                    break;
                case 5:
                    b.setImageResource(R.drawable.n5);
                    b.setEnabled(false);
                    break;
                case 6:
                    b.setImageResource(R.drawable.n6);
                    b.setEnabled(false);
                    break;
                case 7:
                    b.setImageResource(R.drawable.n7);
                    b.setEnabled(false);
                    break;
                case 8:
                    b.setImageResource(R.drawable.n8);
                    b.setEnabled(false);
                    break;

            }
        }
        b.setEnabled(false);
    }

    //Pasamos un boton y el numero que pondrá el drawable correspondiente
    public void ponerImagenNumero(ImageButton boton, int num){
        switch(num){
            case 0:
                boton.setImageDrawable(null);
                break;
            case 1:
                boton.setImageResource(R.drawable.n1);
                break;
            case 2:
                boton.setImageResource(R.drawable.n2);
                break;
            case 3:
                boton.setImageResource(R.drawable.n3);
                break;
            case 4:
                boton.setImageResource(R.drawable.n4);
                break;
            case 5:
                boton.setImageResource(R.drawable.n5);
                break;
            case 6:
                boton.setImageResource(R.drawable.n6);
                break;
            case 7:
                boton.setImageResource(R.drawable.n7);
                break;
            case 8:
                boton.setImageResource(R.drawable.n8);
                break;
            case 99:
                boton.setImageDrawable(null);
                break;
            case -1:
                boton.setImageResource(monaImg);
        }
        boton.setEnabled(false);
    }

    //Metodo para descubrir todo el tablero
    public void descubreTodo(){
        for (int i=0; i<m.dimension;i++){
            for(int j=0;j<m.dimension; j++){
                int pos = ((i) * 100) + (j);

                ImageButton bot = (ImageButton) findViewById(pos);

                ponerImagenNumero(bot,m.tablero[i][j]);
            }
        }
    }

    public void resetear() {
        m = new Matriz(m.dimension);
        crearTablero();
        for (int i = 0; i < m.dimension; i++) {
            for (int j = 0; j < m.dimension; j++) {
                b = (ImageButton) findViewById(i * 100 + j);
                b.setImageResource(R.drawable.boton1);
                b.setEnabled(true);


            }
        }
    }



    //Devuelve el contenido en la matriz del botón según la id que le pasas
    public int devuelveContenido(ImageButton ib){
        int contenido=0;

        String valor="";
        valor= String.valueOf(ib.getId());

        int i=0;
        int j=0;
        if(valor.length()<=2){
            i=0;
            j=ib.getId();
        }
        else if (valor.length()==3){
            String aux="";
            aux+=valor.charAt(1);
            aux+=valor.charAt(2);
            j= Integer.parseInt(aux);
            i=Character.getNumericValue( valor.charAt(0));
        }
        else{
            String aux1="";
            String aux2="";
            aux1+=valor.charAt(2);
            aux1+=valor.charAt(3);
            aux2+=valor.charAt(0);
            aux2+=valor.charAt(1);
            j= Integer.parseInt(aux1);
            i = Integer.parseInt(aux2);
        }

        contenido=m.tablero[i][j];
        return contenido;
    }

    //Ventana de game over. Le paso un entero para mostrar la frase que quiera del switch
    public void gameOver(int frase, String titulo){
        String fraseAMostrar="";

        switch(frase){
            case 1:
                fraseAMostrar="Había una mona! Has perdido!";
                break;
            case 2:
                fraseAMostrar="No había mona en esa casilla! Has perdido!";
                break;
            case 3:
                fraseAMostrar="ENHORABUENA! HAS GANADO!\nTe has convertido en un maestro buscamonas!";
                break;
        }

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(fraseAMostrar);
        dlgAlert.setTitle(titulo);
        dlgAlert.setPositiveButton("Nuevo juego",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        resetear();
                    }
                });
        dlgAlert.setCancelable(false);
        dlgAlert.create().show();

        descubreTodo();
        contador=numeroMinas;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_buscaminas, menu);
        inflater.inflate(R.menu.spinner, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);



        //SPINNER
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, imageNameDatabase);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch(position){
                    case 0:
                        monaImg=R.drawable.mona1;
                        getSupportActionBar().setLogo(monaImg);
                        break;
                    case 1:
                        monaImg=R.drawable.mona2;
                        getSupportActionBar().setLogo(monaImg);
                        break;
                    case 2:
                        monaImg=R.drawable.mona3;
                        getSupportActionBar().setLogo(monaImg);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        return true;
    }

    //Gestionamos el menu de OPCIONES
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nuevoJuego:
                resetear();
                break;
            case R.id.config:
                showRadioButtonDialog();
                break;
            case R.id.instru:
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                dlgAlert.setMessage("El juego es de tipo buscaminas. Ten cuidado porque si pulsas en una casilla que tiene" +
                        "una mona escondida perderás! Haz un click largo sobre la casilla que crees que tiene" +
                        "una mona para señalarla. Ganas una vez hayas encontrado todas las monas.\n" +
                        "Conviértete en el maestro buscamonas!");
                dlgAlert.setTitle("Instrucciones");
                dlgAlert.setPositiveButton("Aceptar",null);
                dlgAlert.setCancelable(false);
                dlgAlert.create().show();

                break;
            default:
                break;
        }

        return true;
    }

    private void showRadioButtonDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radiobutton_dialog);
        final RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);

        RadioButton rb1=new RadioButton(this);
        rb1.setText("Fácil");
        RadioButton rb2=new RadioButton(this);
        rb2.setText("Medio");
        RadioButton rb3=new RadioButton(this);
        rb3.setText("Difícil");
        rg.addView(rb1);
        rg.addView(rb2);
        rg.addView(rb3);
        if(m.dimension==8) rg.check(rb1.getId());
        else if(m.dimension==12) rg.check(rb2.getId());
        else rg.check(rb3.getId());
        rg.setPadding(50,50,50,50);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = rg.findViewById(checkedId);
                int index = rg.indexOfChild(radioButton);
                switch (index){
                    case 0:
                        m.dimension=8;
                        numeroMinas=10;
                        resetear();
                        break;
                    case 1:
                        m.dimension=12;
                        numeroMinas=30;
                        resetear();
                        break;
                    case 2:
                        m.dimension=16;
                        numeroMinas=60;
                        resetear();
                        break;
                }
            }
        });

        Button bMenu= (Button) dialog.findViewById(R.id.botoMenu);
        bMenu.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        dialog.show();

    }

    //Crea nuestro trablero de juego con un tablelayout
    public void crearTablero(){
        setContentView(R.layout.activity_main);
        //Conseguimos el ancho y alto de la pantalla
        Display pantalla = getWindowManager().getDefaultDisplay();
        Point medidasPantalla = new Point();
        pantalla.getSize(medidasPantalla);
        int ancho = medidasPantalla.x;
        int alto = medidasPantalla.y;
        contador=numeroMinas;

        tableroLayout=(TableLayout) findViewById(R.id.tablaLay);
        m.rellenaMinas(numeroMinas);
        m.cuentaMinasVecinos();

        for(int i=0;i<m.dimension;i++){
            TableRow tr = new TableRow(this);
            //tr.setBackground(getResources().getDrawable(R.drawable.borde));
            for(int j=0;j<m.dimension;j++){
                b = (ImageButton) getLayoutInflater().inflate(R.layout.boton,null);
                b.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //Toast.makeText(v.getContext(), "Pulsacion larga", Toast.LENGTH_SHORT).show();
                        if(devuelveContenido((ImageButton) v)==-1 && v.isEnabled()){
                            contador--;
                            if(contador >0) {
                                Toast.makeText(v.getContext(), "Muy bien! Había una mona! "+contador+" restantes", Toast.LENGTH_SHORT).show();
                                ponerImagenNumero((ImageButton) v, -1);
                                v.setEnabled(false);
                            }
                            else{
                                gameOver(3,"VICTORIA!");
                            }
                        }
                        else{
                            gameOver(2,"GAME OVER");
                        }

                        return true;
                    }
                });
                b.setPadding(2,2,2,2);
                b.setId(i*100+j);
                b.setImageResource(R.drawable.boton1);

                tr.setPadding(0,0,0,0);
                tr.addView(b,ancho/m.dimension, (((alto-100)/m.dimension)-12));
            }
            tableroLayout.addView(tr);
        }
    }






}


