public class Main {
    public static void main(String[] args) {
        Carrera carrera = new Carrera();

        Participante p1 = new Participante("Coche 1", carrera);
        Participante p2 = new Participante("Coche 2", carrera);
        Participante p3 = new Participante("Coche 3", carrera);
        Participante p4 = new Participante("Coche 4", carrera);

        carrera.agregarParticipante(p1);
        carrera.agregarParticipante(p2);
        carrera.agregarParticipante(p3);
        carrera.agregarParticipante(p4);

        carrera.iniciarCarrera();
    }
}
