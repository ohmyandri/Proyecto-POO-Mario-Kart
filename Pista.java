import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Pista {
    //Atributo random:
    private final Random randomizer = new Random();
    //Definimos la longitud de la pista
    private final int LONGITUD_PISTA = 100;
    //guardaremos a todos los coches participantes en una lista de coches:
    private List <Coche> participantes;
    //Lista sincronizada que guardara el orden de llegada de los coches en la carrera:
    private final List<Coche> LISTA_LLEGADA = Collections.synchronizedList(new ArrayList<>());
    
    //Constructor de la pista:
    public void setParticipantes(List <Coche> participantes){
        this.participantes = participantes;
    }

    //Metodo para registrar las llegadas:
    public synchronized void registrarLlegada(Coche coche){
        //Guardamos al auto en el ranking de llegada, donde se va ir guardando uno a uno
        this.LISTA_LLEGADA.add(coche);
    }

    //Este metodo aun falta completarlo ya que no tengo aun el metodo para elegir efecto de la caja
    public synchronized void actualizarPosicion(Coche coche, int avance){
        //Simulamos el avance uno en uno, asi no hay saltos de posicion 1 hasta la 6, si no que va revisando durante cada momento que avanza la casilla
        
        for(int i = 1; i <= avance; i++){
            coche.avanzar(1);
            //Verificacion de llegada a la meta:
            if(coche.getPosicion() >= LONGITUD_PISTA){
                registrarLlegada(coche);
            }
            //Verificacion de caja misteriosa:
            else if(coche.getPosicion() % 10 == 0){
                //Llamamos al metodo aun no implementado de usar la caja de objetos en la que estamos:

                //Al llegar a la caja, paramos el avance, abrimos la caja, tomamos el efecto
                //los pasos restantes no se tomaran en cuenta ya que caimos en la caja, por tanto 
                return;
            }
        }
    }


    //Metodo aplicar efecto:
    public synchronized Efecto aplicarObjeto(Coche coche){
        //Decidiendo a que jugador se le aplicara
        /*variable que dira el tamano de la lista de participantes para saber que rango de numeros tomar un num random*/
        int num_participantes = participantes.size();
        int index_participante_afectado = randomizer.nextInt(num_participantes);
        Coche coche_objetivo = participantes.get(index_participante_afectado);

        //Usamos la instancia randomizer para obtener un numero 0-1, decidiendo asi cual sera el efecto que se aplicara
        int eleccion_efecto = randomizer.nextInt(2);
        //Efecto que se va aplicar:
        Efecto efecto_aplicar = null;
        
        try {
            if(eleccion_efecto == 0){
                efecto_aplicar = new ReducirTurnos("Reducir turnos", 3);
                efecto_aplicar.aplicarEfecto(coche_objetivo);
            }
            //Else significara salio 1
            else{
                efecto_aplicar = new DetenerAutoTurnos("Detener", 3);
                efecto_aplicar.aplicarEfecto(coche_objetivo);
            }

        } catch (Exception e) {
            System.out.println("Ocurrio un error inesperado");
            e.printStackTrace();
        }
        
        return efecto_aplicar;
    }
}