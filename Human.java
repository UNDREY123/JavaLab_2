public class Human {
    private final Name name;
    private int height;
    private Human father;

    public Human() {
        this(new Name(), 0, null);
    }

    public Human(String nameStr, int height) {
        this(new Name(nameStr), height, null);
    }

    public Human(String nameStr, int height, Human father) {
        this(new Name(nameStr), height, father);
    }

    public Human(Name name, int height) {
        this(name, height, null);
    }

    public Human(Name name, int height, Human father) {
        if (height <= 0) throw new IllegalArgumentException("Рост должен быть больше 0");
        Human potentialFather = father;
        while (potentialFather != null) {
            if (this.equals(potentialFather)) throw new IllegalArgumentException("Возник цикл в отцах");
            potentialFather = potentialFather.father;
        }
        this.name = name;
        this.height = height;
        this.father = father;
    }

    public void setHeight(int height) {
        if (height <= 0) throw new IllegalArgumentException("Рост должен быть больше 0");
        this.height = height;
    }

    public void setFather(Human father) {
        Human potentialFather = father;
        while (potentialFather != null) {
            if (this.equals(potentialFather)) throw new IllegalArgumentException("Возник цикл в отцах");
            potentialFather = potentialFather.father;
        }
        this.father = father;
    }

    public Name getName() { return this.name; }
    public int getHeight() { return this.height; }
    public Human getFather() { return this.father; }

    private static String generatePatronymic(String fatherName) {
        if (fatherName == null || fatherName.isEmpty()) return null;
        if (fatherName.endsWith("й")) {
            return fatherName.substring(0, fatherName.length() - 1) + "евич";
        }
        return fatherName + "ович";
    }

    @Override
    public String toString() {
        if (this.father == null) {
            return this.name + ", рост: " + this.height;
        }
        String finalSurname = (this.name.getSurname() != null) ? this.name.getSurname() : this.father.getName().getSurname();
        String finalPatronymic = (this.name.getPatronymic() != null) ? this.name.getPatronymic() : generatePatronymic(this.father.getName().getName());
        Name temporaryFullName = new Name(finalSurname, this.name.getName(), finalPatronymic);

        return temporaryFullName + ", рост: " + height;
    }
}