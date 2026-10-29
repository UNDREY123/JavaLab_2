public class Cat {
    public String name;

    Cat() {
        name = "";
    }

    Cat(String name) {
        this.name = name;
    }

    public void meow() {
        System.out.println(name + ": мяу!");
    }

    public void meow(int count) {
        System.out.print(name + ": ");
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                if (i == 0) System.out.print("Мяу");
                else System.out.print("-мяу");
            }
            System.out.println("!");
        } else System.out.println("...");
    }

    @Override
    public String toString() {
        return "Кот: " + name;
    }

}
