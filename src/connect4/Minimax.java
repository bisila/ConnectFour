package connect4;

class Minimax {

    public static void jugar(String[][] fichasEnJuego, String Color) throws InterruptedException {
        //HACER TANTOS WHILE COMO HAGAN FALTA PARA EXPLORAR MAS NIVELES
        int mejorTirada = 0;
        int profundidad = 1;
        String[][] fichasEnJuegoAuxPrincipal = new String[6][7]; //Usar este array para trabajar con el minimax
        for (int i = 0; i < fichasEnJuego.length; i++) {
            System.arraycopy(fichasEnJuego[i], 0, fichasEnJuegoAuxPrincipal[i], 0, fichasEnJuego[i].length);
        }
        while (profundidad > 0) {
            String[][] fichasEnJuegoAux1 = new String[6][7];
            String[][] fichasEnJuegoAux2 = new String[6][7];
            for (int i = 0; i < fichasEnJuego.length; i++) {
                System.arraycopy(fichasEnJuego[i], 0, fichasEnJuegoAux1[i], 0, fichasEnJuego[i].length);
                System.arraycopy(fichasEnJuego[i], 0, fichasEnJuegoAux2[i], 0, fichasEnJuego[i].length);
            }
            int valor1, valor2 = 0;
            mejorTirada = -1;

            //COMPRUEBA SI ALGUNA TIRADA DA VICTORIA O IMPIDE LA VICTORIA DEL RIVAL, PARA DARLE PRIORIDAD
            for (int columna = 0; columna < 7; columna++) {
                int fila = buscarFila(fichasEnJuego, columna);
                if (fila < 6) {
                    if (Color == "X") {
                        fichasEnJuegoAux2[fila][columna] = "O";
                        if (Check4.metodo(fichasEnJuegoAux2, "O") != null) {
                            mejorTirada = columna;
                        }
                        fichasEnJuegoAux1[fila][columna] = "X";
                        if (Check4.metodo(fichasEnJuegoAux1, "X") != null) {
                            mejorTirada = columna;
                            break;
                        }
                    } else {
                        fichasEnJuegoAux1[fila][columna] = "X";
                        if (Check4.metodo(fichasEnJuegoAux1, "X") != null) {
                            mejorTirada = columna;
                        }
                        fichasEnJuegoAux2[fila][columna] = "O";
                        if (Check4.metodo(fichasEnJuegoAux2, "O") != null) {
                            mejorTirada = columna;
                            break;
                        }
                    }
                    fichasEnJuegoAux1[buscarFila(fichasEnJuego, columna)][columna] = " ";
                    fichasEnJuegoAux2[buscarFila(fichasEnJuego, columna)][columna] = " ";
                }
            }
            if (mejorTirada == - 1) {
                for (int columna = 0; columna < 7; columna++) {
                    int fila = buscarFila(fichasEnJuego, columna);
                    if (fila < 6) {
                        valor1 = calcHorizontal(fichasEnJuego, fila, columna, Color) + calcVertical(fichasEnJuego, fila, columna, Color)
                                + calAscendente(fichasEnJuego, fila, columna, Color) + calDescendente(fichasEnJuego, fila, columna, Color);

                        //CALCULO EL VALOR EN POSITIVO, Y DESPUES HARIA LO MISMO PERO EN NEGATIVO CON EL OTRO COLOR
                        if (valor1 > valor2) {
                            valor2 = valor1;
                            mejorTirada = columna;
                        } else if (valor1 == valor2) {
                            if (((int) (Math.random() * 2)) == 0) {
                                valor2 = valor1;
                                mejorTirada = columna;
                            }
                        }
                    }
                }
            }
            profundidad--;
        }
        //ES EL RESULTADO, LA FICHA QUE SE VA A TIRAR
        fichasEnJuego[buscarFila(fichasEnJuego, mejorTirada)][mejorTirada] = Color;
        Thread.sleep(1000);
    }

    //ME BUSCA LA FILA QUE ESTÁ LIBRE SI ES QUE NO ESTÁ LLENA
    private static int buscarFila(String[][] fichasEnJuegoAux, int columna) {
        int fila = 0;
        while (fila < 6) {
            if (fichasEnJuegoAux[fila][columna] == " ") {
                return fila;
            }
            fila++;
        }
        return fila;
    }

    //METODO PARA CALCULAR EL VALOR PARA LA LINEA HORIZONTAL
    private static int calcHorizontal(String[][] fichasEnJuegoAux, int fila, int columna, String Color) {
        int valor = 0;
        if ((columna >= 0) && (columna <= 3)) {
            valor = valor + MetodosMinimax.operarHorizontal(fichasEnJuegoAux, fila, 0, Color);
        }
        if ((columna >= 1) && (columna <= 4)) {
            valor = valor + MetodosMinimax.operarHorizontal(fichasEnJuegoAux, fila, 1, Color);
        }
        if ((columna >= 2) && (columna <= 5)) {
            valor = valor + MetodosMinimax.operarHorizontal(fichasEnJuegoAux, fila, 2, Color);
        }
        if ((columna >= 3) && (columna <= 6)) {
            valor = valor + MetodosMinimax.operarHorizontal(fichasEnJuegoAux, fila, 3, Color);
        }
        return valor;
    }

    //METODO PARA CALCULAR EL VALOR PARA LA LÍNEA VERTICAL
    private static int calcVertical(String[][] fichasEnJuegoAux, int fila, int columna, String Color) {
        int valor = 0;
        int cantidadFichasMias = 0;
        int cantidadFichasOtro = 0;//SIRVE PARA INDICAR QUE COLOR ES EL QUE TOMAMOS. SI EL NUESTRO O EL DEL ENEMIGO. SE SUMA TANTO SI NOS BENEFICIA, COMO SI PERJUDICA AL RIVAL.
        int vacio = 0;
        for (int i = 5; i > 0; i--) {
            if (fichasEnJuegoAux[i][columna] == " ") {
                vacio++;
            } else {
                break;
            }
        }
        for (int i = 0; i < fila; i++) {
            if (fichasEnJuegoAux[i][columna] == Color) {
                cantidadFichasMias++;
                cantidadFichasOtro = 0;
            } else {
                cantidadFichasOtro++;
                cantidadFichasMias = 0;
            }
        }
        /*if ((vacio > 0) && ((cantidadFichasMias == 3) || (cantidadFichasOtro == 3))) {
         return 100000;
         }*/
        if ((cantidadFichasMias + vacio >= 4) || (cantidadFichasOtro + vacio >= 4)) {
            valor = 5;
            return (((int) Math.pow(valor, cantidadFichasOtro)) + ((int) Math.pow(valor, cantidadFichasMias)));
        }
        return 0;
    }

    //METODO PARA CALCULAR EL VALOR PARA LA LÍNEA OBLICUA ASCENDENTE
    private static int calAscendente(String[][] fichasEnJuegoAux, int fila, int columna, String Color) {
        int valor = 0;
        if (zonaMuerta(1, fila, columna)) {
            return 0;
        }
        //COMPROBAR OPTIMIZACION
        if ((columna >= 0) && (columna <= 3) && (fila - columna >= 0) && (fila - columna <= 2)) {
            valor = valor + MetodosMinimax.operarAscendente(fichasEnJuegoAux, fila - columna, 0, Color);
        }
        if ((columna >= 1) && (columna <= 4) && (fila - columna >= -1) && (fila - columna <= 1)) {
            valor = valor + MetodosMinimax.operarAscendente(fichasEnJuegoAux, fila - columna + 1, 1, Color);
        }
        if ((columna >= 2) && (columna <= 5) && (fila - columna >= -2) && (fila - columna <= 0)) {
            valor = valor + MetodosMinimax.operarAscendente(fichasEnJuegoAux, fila - columna + 2, 2, Color);
        }
        if ((columna >= 3) && (columna <= 6) && (fila - columna >= -3) && (fila - columna <= -1)) {
            valor = valor + MetodosMinimax.operarAscendente(fichasEnJuegoAux, fila - columna + 3, 3, Color);
        }
        return valor;
    }

    //METODO PARA CALCULAR EL VALOR PARA LA LÍNEA OBLICUA DESCENDENTE
    private static int calDescendente(String[][] fichasEnJuegoAux, int fila, int columna, String Color) {
        int valor = 0;
        if (zonaMuerta(2, fila, columna)) {
            return 0;
        }
        if ((columna >= 0) && (columna <= 3) && (fila + columna >= 3) && (fila + columna <= 5)) {
            valor = valor + MetodosMinimax.operarDescendente(fichasEnJuegoAux, fila + columna, 0, Color);
        }
        if ((columna >= 1) && (columna <= 4) && (fila + columna >= 4) && (fila + columna <= 6)) {
            valor = valor + MetodosMinimax.operarDescendente(fichasEnJuegoAux, fila + columna - 1, 1, Color);
        }
        if ((columna >= 2) && (columna <= 5) && (fila + columna >= 5) && (fila + columna <= 7)) {
            valor = valor + MetodosMinimax.operarDescendente(fichasEnJuegoAux, fila + columna - 2, 2, Color);
        }
        if ((columna >= 3) && (columna <= 6) && (fila + columna >= 6) && (fila + columna <= 8)) {
            valor = valor + MetodosMinimax.operarDescendente(fichasEnJuegoAux, fila + columna - 3, 3, Color);
        }
        return valor;
    }

    //PARA CALCULAR LAS ZONAS DONDE NO SE PUEDEN HACER 4 EN RAYA DIAGONALES
    private static boolean zonaMuerta(int zona, int fila, int columna) {
        //ASCENDENTES
        if ((zona == 1) && ((fila == 0) && (columna > 3)) || ((fila == 1) && (columna > 4)) || ((fila == 2) && (columna == 6))
                || ((fila == 3) && (columna == 0)) || ((fila == 4) && (columna < 2)) || ((fila == 5) && (columna < 3))) {
            return true;
            //DESCENDENTES
        } else if (((fila == 0) && (columna < 3)) || ((fila == 1) && (columna < 2)) || ((fila == 2) && (columna == 0))
                || ((fila == 3) && (columna == 6)) || ((fila == 4) && (columna > 4)) || ((fila == 5) && (columna > 3))) {
            return true;
        }
        return false;
    }
}
