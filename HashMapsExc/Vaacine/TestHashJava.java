package HashMapsExc.Vaacine;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class TestHashJava {
    public static void main(String[] args) {
        Map<Vaccine, String> map = new HashMap<>();
        Vaccine vaccine1 = new Vaccine(1, "PHY");

        map.put(vaccine1, "John");
        System.out.println(map);
    }
}
