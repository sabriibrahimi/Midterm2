package HashMapsExc.BestOfffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

class Lecture implements Comparable<Lecture> {
    String date;
    String time;
    String place;
    Integer fee;

    public Lecture(String date, String time, String place, Integer fee) {
        this.date = date;
        this.time = time;
        this.place = place;
        this.fee = fee;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {

        this.fee = fee;
    }

    @Override
    public int compareTo(Lecture obj) {
        if (this.fee > obj.fee)
            return 1;
        else if (this.fee < obj.fee)
            return -1;
        else
            return 0;
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

    public ArrayList<Lecture> search(K targetKey) {
        // Find which is any node of this CBHT contains an entry whose key is
        // equal to targetKey. Return a link to that node (or null if there is none).
        int b = hash(targetKey);
        for (SLLNode<MapEntry<K, E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (targetKey.equals(((MapEntry<K, E>) curr.element).key))
                return (ArrayList<Lecture>) curr.element.value;
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
        String temp = "";
        for (int i = 0; i < buckets.length; i++) {
            temp += i + ":";
            for (SLLNode<MapEntry<K, E>> curr = buckets[i]; curr != null; curr = curr.succ) {
                temp += curr.element.toString() + " ";
            }
            temp += "\n";
        }
        return temp;
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

public class BestOffer {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bufferedReader.readLine());

        CBHT<String, ArrayList<Lecture>> hashTable = new CBHT<String, ArrayList<Lecture>>(2 * N);

        for (int i = 0; i < N; i++) {
            String input[] = bufferedReader.readLine().split(" ");
            Lecture lecture = new Lecture(input[0], input[1], input[2], Integer.parseInt(input[3]));
            insertLecture(hashTable, lecture);
        }

        String targetDate = bufferedReader.readLine();

        ArrayList<Lecture> bestOffer = findBestOffer(hashTable, targetDate);

        if (bestOffer == null || bestOffer.isEmpty()) {
            System.out.println("No offers");
        } else {
            for (Lecture lecture : bestOffer) {
                System.out.println(lecture.getTime() + " " + lecture.getPlace() + " " + lecture.getFee());
            }
        }
    }

    public static void insertLecture(CBHT<String, ArrayList<Lecture>> hashTable, Lecture lecture) {
        String lectureDate = lecture.getDate();

        ArrayList<Lecture> lectures = hashTable.search(lectureDate);

        if (lectures == null) {
            lectures = new ArrayList<>();
            lectures.add(lecture);
            hashTable.insert(lectureDate, lectures);
        } else {
            lectures.add(lecture);
        }

    }

    public static ArrayList<Lecture> findBestOffer(CBHT<String, ArrayList<Lecture>> hashTable, String targetDate) {

        ArrayList<Lecture> lectures = hashTable.search(targetDate);

        if (lectures == null) {
            return null;
        }
        Collections.sort(lectures);

        ArrayList<Lecture> bestOffer = new ArrayList<>();
        bestOffer.add(lectures.get(lectures.size() - 1));
        return bestOffer;
    }
}
