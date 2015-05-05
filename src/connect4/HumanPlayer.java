//MOVIMIENTOS UGADOS POR UN HUMANO

package connect4;

import java.util.Scanner;
class HumanPlayer {
    public static void jugar(String[][] fichasEnJuego, String Color) {
        int casilla;
        while (true) {
            Scanner scan = new Scanner(System.in);
            casilla = scan.nextInt();
            if ((casilla < 1) || (casilla > 7)) {
                System.out.println("Tiene que insertar la ficha en una casilla entre 1 y 7");
            } else {
                if ((fichasEnJuego[5][casilla - 1]) != " ") {
                    System.out.println("La columna " + casilla + " está llena. Inserte la ficha en otra columna");
                } else {
                    for (int i = 0; i < 6; i++) {
                        if (fichasEnJuego[i][casilla - 1] == " ") {
                            fichasEnJuego[i][casilla - 1] = Color;
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }
}
