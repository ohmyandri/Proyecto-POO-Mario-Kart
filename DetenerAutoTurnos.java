public class DetenerAutoTurnos extends Efecto{

    DetenerAutoTurnos(String nombre, int duracion_turnos) {
        super(nombre, duracion_turnos);
    }

    @Override
    public void aplicarEfecto(Coche coche) {
        //hacemos que el auto este detenido
        coche.setEsta_detenido(true);
        //marcamos durante cuantos turnos es que le durara el efecto
        coche.setTurnosEfecto(3);
    }
}
