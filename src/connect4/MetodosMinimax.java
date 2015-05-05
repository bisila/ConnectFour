package connect4;

public class MetodosMinimax {

    static int operarHorizontal(String[][] fichasEnJuegoAux, int fila, int columna, String Color) {
        int valor = 3;
        int cantidadFichasMias = 0;
        int cantidadFichasOtro = 0;//SIRVE PARA INDICAR QUE COLOR ES EL QUE TOMAMOS. SI EL NUESTRO O EL DEL ENEMIGO. SE SUMA TANTO SI NOS BENEFICIA, COMO SI PERJUDICA AL RIVAL.

        for (int i = columna; i < columna + 4; i++) {
            if (fichasEnJuegoAux[fila][i] == " ") {
                if ((fila == 0) || (fichasEnJuegoAux[fila - 1][i] != " ")) {
                    valor++;
                }
            } else if (fichasEnJuegoAux[fila][i] == Color) {
                if (cantidadFichasOtro > 0) {
                    return 0;
                }
                valor = valor + 2;
                cantidadFichasMias++;
            } else {
                if (cantidadFichasMias > 0) {
                    return 0;
                }
                valor = valor + 2;
                cantidadFichasOtro++;
            }
        }
        /*if ((cantidadFichasMias == 3) || (cantidadFichasOtro == 3)) {
            return 100000;
        }*/
        if ((cantidadFichasOtro > 0) && (cantidadFichasOtro < 3)) {
            return (int) Math.pow(valor, cantidadFichasOtro);
        }
        return (int) Math.pow(valor, cantidadFichasMias);
    }

    static int operarAscendente(String[][] fichasEnJuegoAux, int fila, int columna, String Color) {
        int valor = 4;
        int cantidadFichasMias = 0;
        int cantidadFichasOtro = 0;//SIRVE PARA INDICAR QUE COLOR ES EL QUE TOMAMOS. SI EL NUESTRO O EL DEL ENEMIGO. SE SUMA TANTO SI NOS BENEFICIA, COMO SI PERJUDICA AL RIVAL.
        int miFila = fila;
        for (int i = columna; i < columna + 4; i++) {
            if (fichasEnJuegoAux[miFila][i] == " ") {
                if ((miFila == 0) || (fichasEnJuegoAux[miFila - 1][i] != " ")) {
                    valor++;
                }
            } else if (fichasEnJuegoAux[miFila][i] == Color) {
                if (cantidadFichasOtro > 0) {
                    return 0;
                }
                valor = valor + 2;
                cantidadFichasMias++;
            } else {
                if (cantidadFichasMias > 0) {
                    return 0;
                }
                valor = valor + 2;
                cantidadFichasOtro++;
            }
            miFila++;
        }
        /*if ((cantidadFichasMias == 3) || (cantidadFichasOtro == 3)) {
            return 100000;
        }*/
        if (cantidadFichasOtro > 0) {
            return (int) Math.pow(valor, cantidadFichasOtro);
        }
        return (int) Math.pow(valor, cantidadFichasMias);
    }

    static int operarDescendente(String[][] fichasEnJuegoAux, int fila, int columna, String Color) {
        int valor = 4;
        int cantidadFichasMias = 0;
        int cantidadFichasOtro = 0;//SIRVE PARA INDICAR QUE COLOR ES EL QUE TOMAMOS. SI EL NUESTRO O EL DEL ENEMIGO. SE SUMA TANTO SI NOS BENEFICIA, COMO SI PERJUDICA AL RIVAL.
        int miFila = fila;
        for (int i = columna; i < columna + 4; i++) {
            if (fichasEnJuegoAux[miFila][i] == " ") {
                if ((miFila == 0) || (fichasEnJuegoAux[miFila - 1][i] != " ")) {
                    valor++;
                }
            } else if (fichasEnJuegoAux[miFila][i] == Color) {
                if (cantidadFichasOtro > 0) {
                    return 0;
                }
                valor = valor + 2;
                cantidadFichasMias++;
            } else {
                if (cantidadFichasMias > 0) {
                    return 0;
                }
                valor = valor + 2;
                cantidadFichasOtro++;
            }
            miFila--;
        }
        /*if ((cantidadFichasMias == 3) || (cantidadFichasOtro == 3)) {
            return 100000;
        }*/
        if (cantidadFichasOtro > 0) {
            return (int) Math.pow(valor, cantidadFichasOtro);
        }
        return (int) Math.pow(valor, cantidadFichasMias);
    }
}
