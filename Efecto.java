public abstract class Efecto {
    protected String nombre;
    protected int duracion_turnos;

    Efecto(String nombre, int duracion_turnos){
        this.nombre = nombre;
        this.duracion_turnos = duracion_turnos;
    }

    //Metodo que se sobreescribira dependiendo que efecto querramos usar:
    public abstract void aplicarEfecto(Coche coche);
    
}
