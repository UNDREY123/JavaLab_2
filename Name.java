public class Name {
    private String surname;
    private String name;
    private String patronymic;

    public Name() {
        this(null, null, null);
    }

    public Name(String name) {
        this(null, name, null);
    }

    public Name(String name, String surname) {
        this(surname, name, null);
    }

    public Name(String surname, String name, String patronymic) {
        if (!checkString(name)) {
            throw new IllegalArgumentException("Некорректное личное имя");
        }
        if (!checkString(surname)) {
            throw new IllegalArgumentException("Некорректная фамилия");
        }
        if (!checkString(patronymic)) {
            throw new IllegalArgumentException("Некорректное отчество");
        }

        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
    }

    private boolean checkString(String val) {
        if (val == null) return true;
        return !val.trim().isEmpty() && val.matches("^[А-ЯЁ][а-яё]*$");
    }

    public void setName(String val) {
        if (val == null || !checkString(val)) throw new IllegalArgumentException("Некорректное личное имя");
        this.name = val;
    }

    public void setSurname(String val) {
        if (!checkString(val)) throw new IllegalArgumentException("Некорректная фамилия");
        this.surname = val;
    }

    public void setPatronymic(String val) {
        if (!checkString(val)) throw new IllegalArgumentException("Некорректное отчество");
        this.patronymic = val;
    }

    public String getSurname() { return surname; }
    public String getName() { return name; }
    public String getPatronymic() { return patronymic; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (surname != null && !surname.trim().isEmpty()) sb.append(surname).append(" ");
        if (name != null && !name.trim().isEmpty()) sb.append(name).append(" ");
        if (patronymic != null && !patronymic.trim().isEmpty()) sb.append(patronymic);
        if (sb.isEmpty()) return "--";
        return sb.toString().trim();
    }
}