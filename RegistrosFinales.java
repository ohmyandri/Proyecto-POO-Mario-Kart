import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RegistrosFinales {
    //nombre del archivo donde se guardaran los registros de cada carrera al finalizar 
    private static final String REGISTROS_FINALES = "ranking_final.txt";
    //Método que se encarga de registrar los resultados de cada participante en un archivo de texto, usa synchronized para evitar que varios hilos intenten escribir dentro del archivo al mismo tiempo
    public static synchronized void registrar(String nombre, int puesto, int turnosTotales) {

        //Verifica que el archivo existe
        try {
            File archivo = new File(REGISTROS_FINALES);
            //Verifica si el archivo no existe o esta vacío
            boolean esPrimerRegistro = !archivo.exists() || archivo.length() == 0;
            //Abre el archivo en modo append para agregar los registros nuevos al final del archivo sin borrar los registros ya existentes
            try (FileWriter escritor = new FileWriter(REGISTROS_FINALES, true)) {
                //Si es la primera vez que se ejecuta el programa se añade un encabezado
                   if (esPrimerRegistro) {
                    escritor.write("\n======================================================\n");
                    escritor.write("                   CLASIFICACIÓN FINAL                  \n");
                    escritor.write("======================================================\n");
                }
                //Para diferenciar entre una carrera y otra una vez que haya llegado el primer lugar se le añade el encabezado siguiente
               if(puesto ==1){
                   //Texto que aparecerá cada que se ejecute el programa 
                 escritor.write("\n********* REGISTROS DE LA ÚLTIMA CARRERA**********\n");
               }
                //Se escriben los datos del participante dentro del archivo de texto
                escritor.write("\n-----------------------------\n");
                escritor.write("Nombre del participante: " + nombre + "\n");
                escritor.write("Puesto en el que quedó: " + puesto + "\n");
                escritor.write("Turnos necesarios para completar la carrera: " + turnosTotales + "\n");
                escritor.write("--------------------------------\n");
                //Forza la escritura en el archivo
                escritor.flush();
               //Manejo de errores en caso de que no sea posible escribir dentro de el 
            } catch (IOException e) {
                System.err.println("Error al escribir en el archivo: " + e.getMessage());
            }
            //Manejo de errores en cuanto a la verifiación del archivo
        } catch (Exception e) {
            System.err.println("Error al verificar archivo: " + e.getMessage());
        }
    }
}
