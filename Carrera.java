import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Carrera {
    //Distancia de la pista
    public static final int META = 100;

    //lista de todos los participantes
    private final List<Participante> participantes;

    //Lista que almacena el orden de llegada a la meta
    private final List<String> ranking;

    //Generador de numeros aletoriso para objetps y decisiones aleatorias
    private final Random random;


    public Carrera(){
        this.participantes = new ArrayList<>();
        this.ranking = new ArrayList<>();
        this.random = new Random();
    }

    //Metodo para agregar Participante
    public synchronized void agregarParticipante(Participante p){
        participantes.add(p);
    }

    /**
     * Metodo para registrar nuevas posiciones
     * Aqui se revisa si cayo en un espacio de objetos
     */

    public synchronized void actualizarParticipante(Participante participante){
        //Cada 10 espacios hay un objeto
        if (participante.getPosicion() % 10 == 0 && participante.getPosicion() > 0 && participante.getPosicion() < META){
            aplicarObjeto(participante);
        }
    }

    /**
     * Metodo para aplicar los objetos
     * 0 = reduccion del dado por 3 turnos
     * 1 = bloquo por 3 turnos
     * @param lanzador Participante que lanza el objeto
     */
    public synchronized void aplicarObjeto(Participante lanzador){

        // Si hay un solo coche, no se puede seleccionar objetivo
        if (participantes.size() <= 1){
            return;
        }

        //Elegir vistima aleatoria
        Participante objetivo;

        do {
            objetivo = participantes.get(random.nextInt(participantes.size()));
        }while (objetivo == lanzador);


        //Seleccionar efecto aleatorio
        int tipo = random.nextInt(2); //0 = reduccion, 1 = bloqueo

        if (tipo == 0){
            System.out.println(lanzador.getNombre() + " lanzó REDUCCIÓN a " + objetivo.getNombre());
            objetivo.aplicarReduccion(3);

        }else {
            System.out.println(lanzador.getNombre() + " lanzó BLOQUEO a " + objetivo.getNombre());
            objetivo.aplicarBloqueo(3);
        }
    }

    /**
     * Dibuja la carrera en la consola al estilo del codigo que pasaron
     * Se limpia la pnatlla y se imprime el avance de cada cohce usando espacios
     */
    public synchronized void refrescarPantalla(){

        //Limpia la consola usando codigos ASCII
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("=== MARIO KART ===");

        //Recorrer cada participante para visualizarlo
        for (int i = 0; i < participantes.size(); i++){

            Participante p = participantes.get(i);
            System.out.print("- "+p.getNombre()+": ");

            // Espacios que represneta el avance en la pista
            for (int j = 0; j < participantes.get(i).getPosicion(); j++){
                System.out.print(" ");
            }

            System.out.println("🚗 " + (i + 1));
        }
    }

    /**
     * Refistra la llegada a la meta y lo almacena en el ranking
     * Cuando llega el ultimo, imprime el ranking
     * @param p Participante de la carrera
     */
    public synchronized void registrarLlegada(Participante p, int turnosTotales){
        ranking.add(p.getNombre());
        int puesto= ranking.size();
        RegistrosFinales.registrar(p.getNombre(),puesto,turnosTotales);

        if (ranking.size() == participantes.size()){
            System.out.println("\n=== RANKING FINAL ===");
            for (int i = 0; i < ranking.size(); i++){
                System.out.println((i+1)+". "+ranking.get(i));
            }
        }
    }


    /**
     * Inicia la carrera creando los hilos
     */
    public synchronized void iniciarCarrera(){
        for (Participante p: participantes){
            Thread t = new Thread(p);
            t.start();
        }
    }
}
