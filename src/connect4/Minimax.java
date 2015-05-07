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
            int valor1 = 0;
            int valor2 = -1;
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
        for (int i = 0; i < fichasEnJuegoAux[0].length - 3; i++) {
            if ((columna >= i) && (columna <= i + 3)) {
                valor = valor + MetodosMinimax.operarHorizontal(fichasEnJuegoAux, fila, i, Color);
            }
        }
        return valor;
    }

    //METODO PARA CALCULAR EL VALOR PARA LA LÍNEA VERTICAL
    private static int calcVertical(String[][] fichasEnJuegoAux, int fila, int columna, String Color) {
        int valor = 0;
        int cantidadFichasMias = 0;
        int cantidadFichasOtro = 0;
        for (int i = 0; i < fila; i++) {
            if (fichasEnJuegoAux[i][columna] == Color) {
                cantidadFichasMias++;
                cantidadFichasOtro = 0;
            } else {
                cantidadFichasOtro++;
                cantidadFichasMias = 0;
            }
        }
        if ((cantidadFichasMias + fichasEnJuegoAux.length - fila >= 5) || (cantidadFichasOtro + fichasEnJuegoAux.length - fila >= 5)) {
            valor = 5;
            return (((int) Math.pow(valor, cantidadFichasOtro)) + ((int) Math.pow(valor, cantidadFichasMias)));
        }
        return 0;
    }

    //METODO PARA CALCULAR EL VALOR PARA LA LÍNEA OBLICUA ASCENDENTE
    private static int calAscendente(String[][] fichasEnJuegoAux, int fila, int columna, String Color) {
        int valor = 0;
        for (int i = 0; i < fichasEnJuegoAux[0].length - 3; i++) {
            if ((columna >= i) && (columna <= i + 3) && (fila - columna >= -i) && (fila - columna <= -i + 2)) {
                valor = valor + MetodosMinimax.operarAscendente(fichasEnJuegoAux, fila - columna + i, i, Color);
            }
        }
        return valor;
    }

    //METODO PARA CALCULAR EL VALOR PARA LA LÍNEA OBLICUA DESCENDENTE
    private static int calDescendente(String[][] fichasEnJuegoAux, int fila, int columna, String Color) {
        int valor = 0;
        for (int i = 0; i < fichasEnJuegoAux[0].length - 3; i++) {
            if ((columna >= i) && (columna <= i + 3) && (fila + columna >= i + 3) && (fila + columna <= i + 5)) {
                valor = valor + MetodosMinimax.operarDescendente(fichasEnJuegoAux, fila + columna - i, i, Color);
            }
        }
        return valor;
    }
}
