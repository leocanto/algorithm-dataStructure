import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {

        WordTree arvore = new WordTree();

        String aux[];
        Path path = Paths.get("E2-Trees/nomes.csv");

        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                aux = line.split(";");
                Palavra p = new Palavra(aux[0].toLowerCase(),aux[1].toLowerCase());
                arvore.addWord(p.getPalavra(),p.getSignificado());
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }


        System.out.println("Escrever todas as palavras e seus significados:\n");
        arvore.traversalPre().forEach((key,value)-> System.out.println(key+" : "+value));

        System.out.println("\nProcurar pelo prefixo 'ig':");
        arvore.searchAll("ig").forEach((key,value)-> System.out.println(key+" : "+value));

    }
}
