public class Main {
    static class Trie {
        static class Node {
            Node[] children = new Node[26];
            boolean isWord;
        }

        private final Node root = new Node();

        void insert(String word) {
            Node current = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (current.children[index] == null) {
                    current.children[index] = new Node();
                }
                current = current.children[index];
            }
            current.isWord = true;
        }

        boolean search(String word) {
            Node node = findNode(word);
            return node != null && node.isWord;
        }

        boolean startsWith(String prefix) {
            return findNode(prefix) != null;
        }

        private Node findNode(String text) {
            Node current = root;
            for (char c : text.toCharArray()) {
                int index = c - 'a';
                if (current.children[index] == null) return null;
                current = current.children[index];
            }
            return current;
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("code");
        System.out.println(trie.search("code"));
        System.out.println(trie.startsWith("co"));
    }
}

