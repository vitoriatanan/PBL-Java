package FileData.Entidade;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serializador {

    private String nomeArquivo;

    public static <T> void salvarDados(String nomeArquivo, List<T> lista) throws IOException {
        FileOutputStream fos = new FileOutputStream(nomeArquivo);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(lista);
        oos.close();
    }

    public static Object leituraDados(String nomeArquivo) throws  FileNotFoundException, IOException, ClassNotFoundException, InvalidClassException {
        FileInputStream fis = new FileInputStream(nomeArquivo);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }
}

