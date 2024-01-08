package HashMapsExc.KumanovskiDijalektt;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.IOException;
//
//You are given a dictionary of words in the Kumanovo dialect and how they are written in the Macedonian language.
// Then you are given a text written in the Kumanovo dialect. It is necessary to replace all occurrences of
// the words in the Kumanov dialect that are given in the dictionary with corresponding words
// in the Macedonian language.
//
//Notes: Punctuation such as periods (.), hyphens (,), exclamation points (!) and question marks (?)
// should be ignored. Also, the words in the text can appear with a first capital letter and in that case
// its synonym in Macedonian language should also be printed with a first capital letter.
//
//There is in the class: KumanovskiDijalekt


class SLLNode<E> {
    protected E element;
    protected SLLNode<E> succ;

    public SLLNode(E elem, SLLNode<E> succ) {
        this.element = elem;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}


class MapEntry<K extends Comparable<K>, E> implements Comparable<K> {

    // Each MapEntry object is a pair consisting of a key (a Comparable
    // object) and a value (an arbitrary object).
    K key;
    E value;

    public MapEntry(K key, E val) {
        this.key = key;
        this.value = val;
    }

    public int compareTo(K that) {
        // Compare this map entry to that map entry.
        @SuppressWarnings("unchecked")
        MapEntry<K, E> other = (MapEntry<K, E>) that;
        return this.key.compareTo(other.key);
    }

    public String toString() {
        return "<" + key + "," + value + ">";
    }
}


class CBHT<K extends Comparable<K>, E> {

    // An object of class CBHT is a closed-bucket hash table, containing
    // entries of class MapEntry.
    private SLLNode<MapEntry<K, E>>[] buckets;

    @SuppressWarnings("unchecked")
    public CBHT(int m) {
        // Construct an empty CBHT with m buckets.
        buckets = (SLLNode<MapEntry<K, E>>[]) new SLLNode[m];
    }

    private int hash(K key) {
        // Translate key to an index of the array buckets.
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public SLLNode<MapEntry<K, E>> search(K targetKey) {
        // Find which if any node of this CBHT contains an entry whose key is
        // equal
        // to targetKey. Return a link to that node (or null if there is none).
        int b = hash(targetKey);
        for (SLLNode<MapEntry<K, E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (targetKey.equals(((MapEntry<K, E>) curr.element).key))
                return curr;
        }
        return null;
    }

    public void insert(K key, E val) {        // Insert the entry <key, val> into this CBHT.
        MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
        int b = hash(key);
        for (SLLNode<MapEntry<K, E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (key.equals(((MapEntry<K, E>) curr.element).key)) {
                // Make newEntry replace the existing entry ...
                curr.element = newEntry;
                return;
            }
        }
        // Insert newEntry at the front of the 1WLL in bucket b ...
        buckets[b] = new SLLNode<MapEntry<K, E>>(newEntry, buckets[b]);
    }

    public void delete(K key) {
        // Delete the entry (if any) whose key is equal to key from this CBHT.
        int b = hash(key);
        for (SLLNode<MapEntry<K, E>> pred = null, curr = buckets[b]; curr != null; pred = curr, curr = curr.succ) {
            if (key.equals(((MapEntry<K, E>) curr.element).key)) {
                if (pred == null)
                    buckets[b] = curr.succ;
                else
                    pred.succ = curr.succ;
                return;
            }
        }
    }

    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < buckets.length; i++) {
            temp.append(i).append("");
            for (SLLNode<MapEntry<K, E>> curr = buckets[i]; curr != null; curr = curr.succ) {
                temp.append(curr.element.toString()).append(" ");
            }
            temp.append("\n");
        }
        return temp.toString();
    }
}


public class KumanovskiDijalekt {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        CBHT<String, String> mapDialectToStd = new CBHT<>(N);

        String pairs[] = new String[N];
        for (int i = 0; i < N; i++) {
            pairs[i] = br.readLine();
            String[] parts = pairs[i].split("\\s+");
            mapDialectToStd.insert(parts[0], parts[1]);
        }
        String text = br.readLine();

        if (N == 0) {
            System.out.println(text);
            return;
        }

        String[] words = text.split("\\s+");
        char character = '#';
        String query, value;
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            character = '#';

            if (word.charAt(word.length() - 1) == '.' ||
                    word.charAt(word.length() - 1) == ',' ||
                    word.charAt(word.length() - 1) == '!' ||
                    word.charAt(word.length() - 1) == '?') {
                character = word.charAt(word.length() - 1);
                word = word.substring(0, word.length() - 1);
            }

            query = word.toLowerCase();

            if (mapDialectToStd.search(query) != null) {
                value = mapDialectToStd.search(query).element.value;
                if (Character.isUpperCase(word.charAt(0)))
                    result.append(Character.toUpperCase(value.charAt(0))).append(value.substring((1)));
                else result.append(value);
            } else result.append(word);

            if(character != '#')
                result.append(character);
            result.append(" ");
        }
        System.out.println(result);
    }
}
