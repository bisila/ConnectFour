//COMPROBACION DE JUGADAS GANADORAS(4 EN RAYA)
package connect4;

class Check4 {
    
    public static String metodo(String[][] fichasEnJuego, String Color) {
        
        //COMPRUEBA LAS HORIZONTALES
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if ((fichasEnJuego[i][j] == Color)
                        && (fichasEnJuego[i][j + 1] == Color)
                        && (fichasEnJuego[i][j + 2] == Color)
                        && (fichasEnJuego[i][j + 3] == Color)) {
                    return fichasEnJuego[i][j];
                }
            }
        }

        //COMPRUEBA LAS VERTICALES
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 7; j++) {
                if ((fichasEnJuego[i][j] == Color)
                        && (fichasEnJuego[i + 1][j] == Color)
                        && (fichasEnJuego[i + 2][j] == Color)
                        && (fichasEnJuego[i + 3][j] == Color)) {
                    return fichasEnJuego[i][j];
                }
            }
        }
        
        //COMPRUEBA DIAGONALES ASCENDENTES
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 4; j++) {
                if ((fichasEnJuego[i][j] == Color)
                        && (fichasEnJuego[i + 1][j + 1] == Color)
                        && (fichasEnJuego[i + 2][j + 2] == Color)
                        && (fichasEnJuego[i + 3][j + 3] == Color)) {
                    return fichasEnJuego[i][j];
                }
            }
        }
        
        //COMPRUEBA DIAGONALES DESCENDENTES
        for (int i = 3; i < 6; i ++) {
            for (int j = 0; j < 4; j++) {
                if ((fichasEnJuego[i][j] == Color)
                        && (fichasEnJuego[i - 1][j + 1] == Color)
                        && (fichasEnJuego[i - 2][j + 2] == Color)
                        && (fichasEnJuego[i - 3][j + 3] == Color)) {
                    return fichasEnJuego[i][j];
                }
            }
        }
        return null;
    }
}
