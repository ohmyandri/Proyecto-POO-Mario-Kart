//Clase auxiliar para tener el avance del auto

import java.util.Random;

public class Dado {
    //Generador de numeros aleatorios de las bibliotecas de JAVA
    private final Random randomizer;
    private final int numero_caras;
    
    //Constructor que sirve para establecer el numero de caras del dado, saber si asi puede caer de 1-3 o de 1-6
    public Dado(int numero_caras){
        this.randomizer = new Random();
        this.numero_caras = numero_caras;
    }

    public int tirar() {
        //next int de un random, sirve para tomar un num 'random', del 0 al numMaximo, asi que por eso se agrega 1
        return randomizer.nextInt(numero_caras) + 1;
    }
}
