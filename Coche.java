public class Coche implements Runnable {
    //Atributo del limite del dado, para decir que va de 1-6 en un inicio
    int limite_avanzar = 6;
    //Atributo nombre, siendo asi este el corredor, como mario, luigi, entre otros.
    private String nombre;
    //referencia al recurso compartido, la pista
    private Pista pista_compartida;
    //Atributos del estado de la carrera:
    private int posicion = 1; //Ya que todos inician en 1, el 1/100 de la pista
    private int turnos_efecto = 0;
    private Efecto efecto_actual = null; //Es el efecto que se le aplico al abrir la caja
    private String mensaje_turno_actual;
    //Atributo para verificar si el auto se vio detenido por el efecto:
    private Boolean esta_detenido;

    //Constructor con el nombre y el recurso compartido que sera la pista:
    Coche(String nombre, Pista pista_compartida){
        this.nombre = nombre;
        this.pista_compartida = pista_compartida;
    }

    //Getters y setters requeridos:
    //Getters:
    public String getNombre(){
        return this.nombre;
    }

    public Pista getPista(){
        return this.pista_compartida;
    }

    public int getPosicion(){
        return this.posicion;
    }

    public int getTurnos_efecto(){
        return this.turnos_efecto;
    }

    public Efecto getEfecto_actual(){
        return this.efecto_actual;
    }

    public String getMensaje_turno_actual(){
        return this.mensaje_turno_actual;
    }
    
    //Setters:
    public void setPista(Pista pista_compartida){
        this.pista_compartida = pista_compartida;
    }

    public void setMensaje_turno(String mensaje_turno_actual){
        this.mensaje_turno_actual = mensaje_turno_actual;
    }

    public void setLimite_avanzar(int limite_avanzar){
        this.limite_avanzar = limite_avanzar;
    }

    public void setTurnosEfecto(int turnos_efecto){
        this.turnos_efecto = turnos_efecto;
    }

    public void setEsta_detenido(Boolean esta_detenido){
        this.esta_detenido = esta_detenido;
    }

    //Metodo para avanzar casillas:
    public void avanzar(int casillas) {
        this.posicion += casillas;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    
}
