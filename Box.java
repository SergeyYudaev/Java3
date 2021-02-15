import java.util.ArrayList;
import java.util.Arrays;

public class Box<T extends Fruit> {
    ArrayList<T> fruits;
    private float BoxWieght = 0.0f;

    public Box(T... fruits) {
        this.fruits = new ArrayList<>(Arrays.asList(fruits));
    }

    public void add(T... fruits) {
        this.fruits.addAll(Arrays.asList(fruits));
    }

    float getBoxWieght() {
        if (fruits.size() > 0) {
            BoxWieght = this.fruits.get(0).getWeight() * fruits.size();
        }
        return BoxWieght;
    }

    boolean compare(Box box) {
        return (box.getBoxWieght() == this.getBoxWieght());
    }

    void move(Box<? super T> box) {
        box.fruits.addAll(this.fruits);
        fruits.clear();
    }
}
