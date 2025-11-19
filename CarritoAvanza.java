public class CarritoAvanza {
    public static void main(String[] args) {
        int posicion = 0; // posición inicial del carrito

        while (posicion <= 100) {  // límite del movimiento
            // Limpia la consola (truco simple simulando)
            System.out.print("\033[H\033[2J");
            System.out.flush();

            // Imprime espacios antes del carrito
            for (int i = 0; i < posicion; i++) {
                System.out.print(" ");
            }

            // Imprime el carrito
            System.out.println("🚗");

            // Espera 200 ms para simular animación
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            posicion++; // avanza el carrito
        }
    }
}