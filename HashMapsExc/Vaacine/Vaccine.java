package HashMapsExc.Vaacine;

public class Vaccine {

    private int dose;
    private String name;

    public Vaccine(int dose, String name) {
        this.dose = dose;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Vaccine{" +
                "dose=" + dose +
                ", name='" + name + '\'' +
                '}';
    }
}
