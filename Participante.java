import java.util.Random;

public class Participante implements Runnable {
    //Atributos

    // Nombre del participante
    private final String nombre;

    //Posicion actual en la pista
    private int posicion;

    //Turnos que el cohce permanece detenido por un objeto
    private int turnosBloqueado;

    //Turnos que el coche temdra el dado reducido
    private int turnosReducido;

    //Indica si ya llego a la meta
    private boolean llegoMeta;
    //Almacena los turnos que ocupo cada participante para llegar
    private int turnosTotales=0;

    private final Carrera carrera;
    private Random random;


    public Participante(String nombre, Carrera carrera) {
        this.nombre = nombre;
        this.posicion = 0;
        this.carrera = carrera;
        this.turnosBloqueado = 0;
        this.turnosReducido = 0;
        this.llegoMeta = false;
        this.random = new Random();
    }

    @Override
    public void run() {

        //El hilo continua hasta que llegue a la meta
        while (!llegoMeta) {
            //Cada while representa un turno más
            turnosTotales++;
            if (turnosBloqueado > 0) {
                //No se mueve por este turno
                turnosBloqueado--;
                carrera.refrescarPantalla();
                dormirTurno();
                continue;
            }

            //Elige que tipo de dado realiza dependiendo si tiene efecto de Objeto
            int maxDado;
            if (turnosReducido > 0){
                maxDado = 3;
            }else {
                maxDado = 6;
            }

            //Avance aleatorio entre 1 y maxDado
            int avance = random.nextInt(maxDado) + 1;
            posicion += avance;

            // Reducimos los turnos restantes del estado reducido
            if (turnosReducido > 0) {
                turnosReducido--;
            }

            // Notificamos a la carrera el movimiento
            carrera.actualizarParticipante(this);

            //Verifica si llego a la meta
            if (posicion >= Carrera.META) {
                posicion = Carrera.META;
                llegoMeta = true;
                carrera.registrarLlegada(this,turnosTotales);
            }

            //Refrescamos la animacion
            carrera.refrescarPantalla();

            //Pausa para simular la animacion
            dormirTurno();
        }
    }


    /**
     * Simula el tiempo de espera entre turnos
     */
    private void dormirTurno(){
        try{
            Thread.sleep(300); //Velocidad de animacion
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    //Aplica un bloqueo de N turnos por efecto de un objeto
    public void aplicarBloqueo(int turnos){
        //Se toma el maximo bloqueo por si ya tenia algo aplicado
        this.turnosBloqueado = Math.max(this.turnosBloqueado, turnos);
    }

    /**
     * Aplica una reduccion de dado durante N turnos
     * Avanzara con dado de 1 a 3
     */

    public void aplicarReduccion(int turnos){

        this.turnosReducido = Math.max(this.turnosReducido, turnos);
    }

    //GETTERS

    public String getNombre() {
        return nombre;
    }

    public int getPosicion() {
        return posicion;
    }
}
