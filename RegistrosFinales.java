import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RegistrosFinales {
    private static final String REGISTROS_FINALES = "ranking_final.txt";
    public static synchronized void registrar(String nombre, int puesto, int turnosTotales) {
        try {
            File archivo = new File(REGISTROS_FINALES);
            boolean esPrimerRegistro = !archivo.exists() || archivo.length() == 0;

            try (FileWriter escritor = new FileWriter(REGISTROS_FINALES, true)) {

                   if (esPrimerRegistro) {
                    escritor.write("\n======================================================\n");
                    escritor.write("                   CLASIFICACIÓN FINAL                  \n");
                    escritor.write("======================================================\n");
                }
               if(puesto ==1){
                 escritor.write("\n********* REGISTROS DE LA ÚLTIMA CARRERA**********\n");
               }
                escritor.write("\n-----------------------------\n");
                escritor.write("Nombre del participante: " + nombre + "\n");
                escritor.write("Puesto en el que quedó: " + puesto + "\n");
                escritor.write("Turnos necesarios para completar la carrera: " + turnosTotales + "\n");
                escritor.write("--------------------------------\n");
                escritor.flush();
                System.out.println("Registro guardado para: " + nombre);

            } catch (IOException e) {
                System.err.println("Error al escribir en el archivo: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error al verificar archivo: " + e.getMessage());
        }
    }
}
