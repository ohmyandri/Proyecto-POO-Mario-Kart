public class Main {
    public static void main(String[] args) {
        //Creando instancia de la carrera
        Carrera carrera = new Carrera();

        //Creando las instancias de cada participante
        Participante p1 = new Participante("Mario", carrera);
        Participante p2 = new Participante("Luigi", carrera);
        Participante p3 = new Participante("Peach", carrera);
        Participante p4 = new Participante("Boo", carrera);
        // Otras instancias que se podrian crear:
        Participante p5 = new Participante("Toad", carrera);
        Participante p6 = new Participante("Yoshi", carrera);
        Participante p7 = new Participante("Daisy", carrera);
        Participante p8 = new Participante("Wario", carrera);

        //Agregamos los participantes a la lista de participantes de la carrera
        carrera.agregarParticipante(p1);
        carrera.agregarParticipante(p2);
        carrera.agregarParticipante(p3);
        carrera.agregarParticipante(p4);
        carrera.agregarParticipante(p5);
        carrera.agregarParticipante(p6);
        carrera.agregarParticipante(p7);
        carrera.agregarParticipante(p8);

        //Usamos el metodo iniciar carrera el cual incluye para usar .start de los hilos
        carrera.iniciarCarrera();
    }
}
