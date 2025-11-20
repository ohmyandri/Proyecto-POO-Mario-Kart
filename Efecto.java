public abstract class Efecto {
    public abstract String getNombre();
    
    public abstract void aplicar(Coche coche) throws InterruptedException;
}
