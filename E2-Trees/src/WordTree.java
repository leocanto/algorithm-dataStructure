import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordTree {
    private CharNode root;
    private int totalNodes = 0;
    private int totalWords = 0;

    private class CharNode {
        private char character;
        private boolean isFinal;
        private CharNode father;
        private List<CharNode> children;
        private String significado;

        public CharNode(){
            children = new ArrayList<>();
        }

        public CharNode(char character) {
            this.character=character;
            children = new ArrayList<>();
        }

        public CharNode(char character, boolean isFinal) {
            this.character=character;
            this.isFinal = isFinal;
            children = new ArrayList<>();
        }

        /**
         * Adiciona um filho (caracter) no nodo. Não pode aceitar caracteres repetidos.
         * @param character - caracter a ser adicionado
         * @param isfinal - se é final da palavra ou não
         */

        public CharNode addChild(char character, boolean isfinal) {
            CharNode child = new CharNode(character,isfinal);
            children.add(child);
            return child;
        }

        public int getNumberOfChildren() {return children.size();}

        public CharNode getChild(int index) {

            if(getNumberOfChildren()>0) {
                return children.get(index);
            }else{
                return null;
            }
        }

        /**
         * Obtém a palavra correspondente a este nodo, subindo até a raiz da árvore
         * @return a palavra
         */
        private String getWord() {
            String word=""+character;
            CharNode pai=father;

            while(pai!=root){
                word=pai.character+word;
                pai=pai.father;
            }
            return word;
        }

        /**
         * Encontra e retorna o nodo que tem determinado caracter.
         * @param character - caracter a ser encontrado.
         */
        public CharNode findChildChar (char character) {
            if(getNumberOfChildren()>0) {
                for (CharNode c : children) {
                    if (c.character == character) {
                        return c;
                    }
                }
            }
            return null;
        }

    }

    // ctor
    public WordTree() {
        root = new CharNode();
    }

    public int getTotalWords() {return totalWords;}

    public int getTotalNodes() {return totalNodes;}

    /**
     *Adiciona palavra na estrutura em árvore
     *@param word
     *@param significado
     */
    public void addWord(String word, String significado) {
        int index;
        CharNode father=findCharNodeForWord(word);

        if(father!=root) {
            index = father.getWord().length();
        }else{
            index = 0;
        }

        while(index<word.length()){
            if(index!=word.length()-1) {
                CharNode aux = father.addChild(word.charAt(index),false);
                aux.father=father;
                father=aux;
            }else{
                CharNode aux = father.addChild(word.charAt(index),true);
                aux.father=father;
                aux.significado=significado;
                father=aux;
            }
            index++;
        }
    }

    /**
     * Vai descendo na árvore até onde conseguir encontrar a palavra
     * @param word
     * @return o nodo final encontrado
     */

    public CharNode findCharNodeForWord(String word) {

        int i=0;

        CharNode aux = root.findChildChar(word.charAt(i));
        CharNode father=root;
        i++;

        while(i<=word.length() && aux!=null){

            father=aux;

            if(i<word.length()) {
                aux = father.findChildChar(word.charAt(i));
            }

            i++;
        }

        return father;
    }
    /**
     * Percorre a árvore e retorna uma lista com as palavras iniciadas pelo prefixo dado.
     * Tipicamente, um método recursivo.
     * @param prefix
     */
    public Map<String,String> searchAll(String prefix) {
        Map<String,String> res=new HashMap<>();
        CharNode init = findCharNodeForWord(prefix);
        if(init!=root){
            traversalPre(init, res);
            return res;
        }else{
            return null;
        }
    }

    public Map<String,String> traversalPre(){
        Map<String,String> res=new HashMap<>();
        traversalPre(root, res);
        return res;
    }

    private void traversalPre(CharNode n, Map<String,String> res){
        if (n != null) {
            if(n.isFinal) {
                res.put(n.getWord(),n.significado);
            }
            for (int i = 0; i < n.getNumberOfChildren(); i++) {
                traversalPre(n.getChild(i), res);
            }
        }
    }

}
