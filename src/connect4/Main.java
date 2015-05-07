package connect4;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        //PRESENTACION
        System.out.println("Bienvenido al conecta 4");
        System.out.println("¿El jugador 1 que va a ser?");
        int player1 = tipoJugador();
        System.out.println("¿Y el jugador 2?");
        int player2 = tipoJugador();
        System.out.println("Empieza el jugador 1 con fichas rojas(X)");

        //ARRAY CON LAS FICHAS. LA PRIMERA FICHA A LA IZQUIERA INFERIOR SERIA LA POSICION [0][0]
        String[][] fichasEnJuego = new String[6][7];

        for (int i = 0; i < fichasEnJuego.length; i++) {
            for (int j = 0; j < fichasEnJuego[i].length; j++) {
                fichasEnJuego[i][j] = " ";
            }
        }
        /*
        fichasEnJuego[0][1] = "O";
        fichasEnJuego[0][2] = "X";
        fichasEnJuego[0][3] = "O";
        fichasEnJuego[1][1] = "X";
        fichasEnJuego[1][2] = "O";
        fichasEnJuego[1][3] = "X";
        fichasEnJuego[2][2] = "X";
        fichasEnJuego[2][3] = "O";
        fichasEnJuego[3][3] = "X";
        */
        
        boolean loop = true;
        int count = 0;
        imprimirTablero(fichasEnJuego);
        String colorGanador;//SACA EL COLOR GANADOR

        //MOVIMIENTO DE FICHAS
        while (loop) {
            if (count % 2 == 0) {
                System.out.println("Coloca una ficha roja(X) en una columna (1-7): ");
                jugarFicha(fichasEnJuego, player1, "X");
                colorGanador = Check4.metodo(fichasEnJuego, "X");
            } else {
                System.out.println("Coloca una ficha amarilla(O) en una columna (1-7): ");
                jugarFicha(fichasEnJuego, player2, "O");
                colorGanador = Check4.metodo(fichasEnJuego, "O");
            }
            count++;
            imprimirTablero(fichasEnJuego);
            //COMPRUEBA SI HAY ALGUN 4 EN RAYA
            if (colorGanador != null) {
                if (colorGanador == "X") {
                    System.out.println("El jugador rojo gana(X).");
                } else if (colorGanador == "O") {
                    System.out.println("El jugador amarillo gana(O).");
                }
                loop = false;
            }
            if (tableroLleno(fichasEnJuego)) {
                System.out.println("Se ha llenado el tablero, y los dos jugadores han quedado en tablas");
                break;
            }
        }
    }

    //PREGUNTA QUE TIPO DE JUGADORES VAN A COMPETIR
    private static int tipoJugador() {
        System.out.println("    1.Humano");
        System.out.println("    2.Máquina");
        int player = 0;
        while (true) {
            Scanner scan = new Scanner(System.in);
            player = scan.nextInt();
            if ((player < 1) || (player > 2)) {
                System.out.println("Tienes que elegir un numero de la lista");
            } else {
                return player;
            }
        }
    }

    //JUEGA LAS FICHAS DEPENDIENDO DE QUE TIPO DE JUGADOR SEAN
    private static void jugarFicha(String[][] fichasEnJuego, int player, String color) throws InterruptedException {
        switch (player) {
            case 1:
                HumanPlayer.jugar(fichasEnJuego, color);
                break;
            case 2:
                Minimax.jugar(fichasEnJuego, color);
                break;
        }
    }

    //COMPRUEBA SI EL TABLERO ESTÁ LLENO
    private static boolean tableroLleno(String[][] fichasEnJuego) {
        int cuenta = 0;
        for (int j = 0; j < 7; j++) {
            if (fichasEnJuego[5][j] != " ") {
                cuenta++;
            }
        }
        if (cuenta == 7) {
            return true;
        }
        return false;
    }

    //IMPRIME EL TABLERO
    public static void imprimirTablero(String[][] fichasEnJuego) {
        int saltos = 20;
        for (int i = 0; i < saltos; i++) {
            System.out.println();
        }
        int filaJuego = 5;
        for (int i = 0; i < 8; i++) {
            int columnaJuego = 0;
            for (int j = 0; j < 15; j++) {
                if ((i < 6) && (j % 2 == 1)) {
                    System.out.print(fichasEnJuego[filaJuego][columnaJuego++]);
                } else if ((j % 2 == 0) && (i != 6)) {
                    System.out.print("|");
                } else if (i == 6) {
                    System.out.print("-");
                } else {
                    System.out.print(++columnaJuego);
                }
            }
            filaJuego--;
            System.out.println();
        }
    }
}
