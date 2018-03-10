package com.dam.juanjo.buscaminas;
import android.util.Log;

import java.util.Random;

/**
 * Created by juanjo on 30/10/17.
 */

public class Matriz {
    int dimension;
    int tablero[][];
    int casillas=0;

    public Matriz(int dimension){ //Constructor de la matriz segun la dimension
        this.dimension=dimension;
        tablero= new int[dimension][dimension];
        this.casillas=dimension*dimension;
        this.borrado();
    }

    void borrado() { //Pone todo a cero
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                tablero[i][j] = 0;
            }
        }
    }

    void rellenaMinas(int nMinas){ //pone -1 random el numero de veces nMinas
        Random rand= new Random();
        for (int i=0; i<nMinas; i++) {
            boolean hayBomba=false;
            do {
                int casillaBombaX = rand.nextInt(dimension);
                int casillaBombaY = rand.nextInt(dimension);
                if(tablero[casillaBombaX][casillaBombaY]==-1) hayBomba=true;
                else {
                    tablero[casillaBombaX][casillaBombaY] = -1;
                    hayBomba = false;
                }
            }while(hayBomba);
        }
    }

    boolean compruebaMina(int x, int y){
        if(tablero[x][y]==-1) return true;
        else return false;
    }

    //Metodo booleano sencillo para saber si una casillas está o no fuera de tablero
    boolean fueraTablero(int p){
        if(p<0||p>dimension-1) return true;
        else return false;
    }

    void cuentaMinasVecinos(){
        for(int i=0; i<dimension;i++){
            for(int j=0;j<dimension;j++){
                if (!compruebaMina(i, j)) {
                    int cont = 0;
                    if (!fueraTablero(i-1) && !fueraTablero(j-1) && compruebaMina(i-1,j-1)) cont++;
                    if (!fueraTablero(i-1) && !fueraTablero(j) && compruebaMina(i-1,j)) cont++;
                    if (!fueraTablero(i-1) && !fueraTablero(j+1) && compruebaMina(i-1,j+1)) cont++;
                    if (!fueraTablero(i) && !fueraTablero(j-1) && compruebaMina(i,j-1)) cont++;
                    if (!fueraTablero(i) && !fueraTablero(j+1) && compruebaMina(i,j+1)) cont++;
                    if (!fueraTablero(i+1) && !fueraTablero(j-1) && compruebaMina(i+1,j-1)) cont++;
                    if (!fueraTablero(i+1) && !fueraTablero(j) && compruebaMina(i+1,j)) cont++;
                    if (!fueraTablero(i+1) && !fueraTablero(j+1) && compruebaMina(i+1,j+1)) cont++;

                    tablero[i][j] = cont;
                }

            }
        }
    }





}
