// Importando bibliotecas que se usaran a lo largo del codigo
import java.util.Random;

/**
 * La clase Participante representa a un coche dentro de la carrera.
 * Cada participante es un hilo independiente que avanza turno por turno
 * hasta llegar a la meta. Puede ser afectado por objetos como bloqueo
 * o reducción de avance, y reporta su progreso a la clase Carrera
 */
public class Participante implements Runnable {
    // =====================
    // ===== ATRIBUTOS =====
    // =====================

    /** Nombre del participante/coche */
    private final String nombre;

    /** Posición actual en la pista (0 = inicio) */
    private int posicion;

    /** Cantidad de turnos que el coche permanece detenido por un objeto */
    private int turnosBloqueado;

    /** Cantidad de turnos que el dado estará reducido (solo avanza 1-3) */
    private int turnosReducido;

    /** Indica si el coche ya ha llegado a la meta */
    private boolean llegoMeta;


    /** Número total de turnos que el participante ha utilizado en la carrera */
    private int turnosTotales=0;

    /** Referencia al objeto Carrera para reportar movimientos y llegadas */
    private final Carrera carrera;

    /** Generador de números aleatorios para simular el dado de avance */
    private Random random;

    /**
     * Constructor que inicializa todos los atributos del participante
     * @param nombre Nombre del participante
     * @param carrera Referencia a la carrera en la que participa
     */
    public Participante(String nombre, Carrera carrera) {
        this.nombre = nombre;
        this.posicion = 0; //Comienza en la línea de salida
        this.carrera = carrera;
        this.turnosBloqueado = 0;
        this.turnosReducido = 0;
        this.llegoMeta = false;
        this.random = new Random();
    }


    /**
     * Metodo ejecutado cuando se inicia el hilo
     * Controla el ciclo de turnos del coche hasta que llegue a la meta
     */
    @Override
    public void run() {

        // El participante continua hasta que llegue a la meta
        while (!llegoMeta) {

            // Cada iteración del ciclo representa un turno
            turnosTotales++;

            // REVISAR SI ESTA BLOQUEADO
            if (turnosBloqueado > 0) {

                // No avanza este turno
                turnosBloqueado--;

                // Se actualiza pantalla para mostrar el estado del coche
                carrera.refrescarPantalla();

                // Pausa visual entre turnos
                dormirTurno();

                // Saltamos al siguiente turno
                continue;
            }

            // SELECCIONAR EL DADO (normal o reducido)
            int maxDado;

            if (turnosReducido > 0){

                // Dado reducido (efecto de objeto)
                maxDado = 3;

            }else {

                // Dado normal
                maxDado = 6;
            }

            // AVANZAR DE MANERA ALEATORIA
            int avance = random.nextInt(maxDado) + 1;
            posicion += avance;

            // Reducir duración del efecto de reducción si estaba activo
            if (turnosReducido > 0) {
                turnosReducido--;
            }

            // Reportar a Carrera el avance actual
            carrera.actualizarParticipante(this);

            // VERIFICAR SI LLEGO A LA META
            if (posicion >= Carrera.META) {

                // Ajustar si se pasa exacto
                posicion = Carrera.META;
                llegoMeta = true;

                // Registrar al participante en el ranking final
                carrera.registrarLlegada(this,turnosTotales);
            }

            // REFRESCAR ANIMACIÓN
            carrera.refrescarPantalla();

            // Espera entre turnos para controlar velocidad
            dormirTurno();
        }
    }

    // ==============================
    // ===== METODOS AUXILIARES =====
    // ==============================


    /**
     * Simula el tiempo que tarda un turno en completarse.
     * Controla la velocidad de la animación en consola.
     */
    private void dormirTurno(){
        try{
            //Velocidad de animación
            Thread.sleep(300);
            
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Aplica un bloqueo de N turnos al participante
     *
     * @param turnos Cantidad de turnos que no podrá avanzar
     */
    public void aplicarBloqueo(int turnos){
        //Se toma el maximo bloqueo por si ya tenía algo aplicado
        this.turnosBloqueado = Math.max(this.turnosBloqueado, turnos);
    }

    /**
     * Aplica una reducción de dado durante N turnos.
     * Durante ese tiempo el coche solo podrá avanzar de 1 a 3 espacios
     * @param turnos Cantidad de turnos que se aplicara la reducción
     */
    public void aplicarReduccion(int turnos){

        this.turnosReducido = Math.max(this.turnosReducido, turnos);
    }


    // ===================
    // ===== GETTERS =====
    // ===================

    /**
     *
     * @return Nombre del participante
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return Posición actual en la pista
     */
    public int getPosicion() {
        return posicion;
    }
}
