//Importando bibliotecas que se usaran a lo largo del codigo
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Carrera {

    //Distancia de la pista
    public static final int META = 100;

    //lista de todos los participantes
    private final List<Participante> participantes;

    //Lista que almacena el orden de llegada a la meta
    private final List<Participante> ranking;

    //Generador de numeros aletorios para objetps y decisiones aleatorias
    private final Random random;

    //Constructor de la carrera
    public Carrera() {
        this.participantes = new ArrayList<>();
        this.ranking = new ArrayList<>();
        this.random = new Random();
    }

    //Metodo para agregar Participante
    public synchronized void agregarParticipante(Participante p) {
        participantes.add(p);
    }

    /**
     * Metodo para registrar nuevas posiciones Aqui se revisa si cayo en un
     * espacio de objetos
     */
    public synchronized void actualizarParticipante(Participante participante) {
        //Cada 10 espacios hay un objeto
        if (participante.getPosicion() % 10 == 0 && participante.getPosicion() > 0 && participante.getPosicion() < META) {
            aplicarObjeto(participante);
        }
    }

    /**
     * Metodo para aplicar los objetos 0 = reduccion del dado por 3 turnos 1 =
     * bloquo por 3 turnos
     *
     * @param lanzador Participante que lanza el objeto
     */
    public synchronized void aplicarObjeto(Participante lanzador) {

        //Si hay un solo coche, no se puede seleccionar objetivo
        if (participantes.size() <= 1) {
            return;
        }

        //Elegir vistima aleatoria
        Participante objetivo;

        //Usando un ciclo do-while, para primero intentar obtener un participante
        do {
            objetivo = participantes.get(random.nextInt(participantes.size()));
        }
        //Se ejecutara mientras el objetivo seleccionado, sea el mismo que el participante que lanzo el objeto
        //Asegurando asi que el lanzador no sea afectado por su mismo objeto
        while (objetivo == lanzador);

        //Seleccionar efecto aleatorio
        int tipo = random.nextInt(2);
        
        //0 = reduccion, 1 = bloqueo temporal

        //Caso de reduccion
        if (tipo == 0) {
            System.out.println(lanzador.getNombre() + " lanzó REDUCCIÓN a " + objetivo.getNombre());

            //Del objetivo que fue seleccionado, se le aplica una reduccion de 3 turnos teniendo dado 1-3
            objetivo.aplicarReduccion(3);

        }
        //Si no es tipo uno, solo puede ser tipo 2, es decir un bloqueo
        else {
            System.out.println(lanzador.getNombre() + " lanzó BLOQUEO a " + objetivo.getNombre());
            objetivo.aplicarBloqueo(3);
        }

        //Como no logramos implementar que se reescribiera con /r en una misma linea, se decidio por pausar un segundo, para poder apreciar cual
        //Fue el efecto que se encontro en la caja misteriosa
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    //metodo para obtener el lugar del participante
    private int obtenerLugar(Participante p) {
        //Inicia el lugar en primero
        int lugar = 1;
        //usamos un for each para iterar por cada participante, y revisar si la posicion es mayor o menor
        for (Participante otro : participantes) {
            //Si el participante en el que estamos iterando tiene una posicion mas adelante
            //Se sumara un lugar, ya que significa no es el mas adelantado de la carrera
            if (otro.getPosicion() > p.getPosicion()) {
                lugar++;
            }
        }
        //devolvemos el entero del lugar
        return lugar;
    }

    /**
     * Dibuja la carrera en la consola al estilo del codigo que pasaron Se
     * limpia la pnatlla y se imprime el avance de cada cohce usando espacios
     */
    public synchronized void refrescarPantalla() {

        //Limpia la consola usando codigos ASCII
        System.out.print("\033[H\033[2J");
        System.out.flush();

        //Imprimiendo un titulo de lo que es, la carrera
        System.out.println("=== CAMPEONATO MARIO KART ===");

        //Recorrer cada participante para visualizarlo
        for (int i = 0; i < participantes.size(); i++) {

            //Usando el getter del participante lo tomamos en p
            Participante p = participantes.get(i);
            //El lugar se obtiene mediante el metodo obtener lugar del participante declarado en linea 96
            int lugar = obtenerLugar(p);
            //E imprimimos el nombre del auto que aparecera en esa linea
            System.out.print("- " + p.getNombre() + ": ");

            //Espacios que representa el avance en la pista
            for (int j = 0; j < participantes.get(i).getPosicion(); j++) {
                System.out.print(" ");
            }

            //Imprimimos el lugar del carro actual, y una representacion de un 'carro'
            System.out.println(lugar+"° o==> ");
        }

        //Hace la animación más lenta y perceptible, para que no sea un refrescamiento tan veloz
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Refistra la llegada a la meta y lo almacena en el ranking Cuando llega el
     * ultimo, imprime el ranking
     *
     * @param p Participante de la carrera
     */

    //Registramos la llegada de los participantes como un metodo synchronized
    public synchronized void registrarLlegada(Participante p, int turnosTotales) {
        //Agregamios al ranking al participante que ya se encuentra ahi
        ranking.add(p);
        //guardamos el puesto de llegada, ya que .size es de 1->n
        int puesto = ranking.size();
        //Usamos el metodo registrar para enviarle el nombre del player, su puesto logrado, y los turnos que tardo para llegar a la meta
        RegistrosFinales.registrar(p.getNombre(), puesto, turnosTotales);

        //verificamos que si ya todos los participantes se encuentran registrados
        if (ranking.size() == participantes.size()) {
            //Imprimiremos el ranking
            System.out.println("\n=== RANKING FINAL ===");
            //Con el uso de un for imprimiremos uno a uno
            for (int i = 0; i < ranking.size(); i++) {
                //imprimiendo el ranking usando .get, ya que es una lista de participantes
                System.out.println((i + 1) + ". " + ranking.get(i).getNombre());
            }
        }
    }

    /**
     * Inicia la carrera creando los hilos
     */
    public synchronized void iniciarCarrera() {
        //Usando un for each para iterar sobre la lista de participantes y poder iniciar a todos
        for (Participante p : participantes) {
            //Creando un hilo del participante
            Thread t = new Thread(p);
            //Iniciandolo
            t.start();
        }
    }
}
