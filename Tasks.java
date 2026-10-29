import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Tasks {
    private final Getter getter = new Getter();
    private final Scanner scanner = new Scanner(System.in);
    private final ArrayList<Name> namesList = new ArrayList<>();
    private final ArrayList<Human> humansList = new ArrayList<>();
    private final ArrayList<City> citiesList = new ArrayList<>();
    private final ArrayList<Cat> catsList = new ArrayList<>();
    public void start() {
        mainMenu();
    }

    private void mainMenu() {
        while (true) {
            System.out.println("Категории:");
            System.out.println("1) Люди и имена");
            System.out.println("2) Города");
            System.out.println("3) Коты");
            System.out.println("Введите 0 для выхода\n");

            int category = getter.getInt(0, 3, "Введите номер категории: ");
            System.out.println();

            if (category == 0) {
                System.out.println("Работа завершена.");
                break;
            }

            switch (category) {
                case 1 -> humanMenu();
                case 2 -> cityMenu();
                case 3 -> catMenu();
            }
        }
    }

    private void humanMenu() {
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1) Добавить имя");
            System.out.println("2) Добавить человека");
            System.out.println("3) Вывести имена");
            System.out.println("4) Вывести людей");
            System.out.println("5) Указать отца");
            System.out.println("6) Вывести данные человека");
            System.out.println("Введите 0 для возвращения в меню\n");

            int action = getter.getInt(0, 6, "Выберите действие: ");
            System.out.println();

            if (action == 0) break;

            switch (action) {
                case 1 -> inputAndAddName();
                case 2 -> addHuman();
                case 3 -> printNames();
                case 4 -> printHumans();
                case 5 -> setFatherForHuman();
                case 6 -> printHumanPedigree();
            }
        }
    }

    private void inputAndAddName() {
        Name nameObj = requestNameFields();
        if (nameObj != null) {
            namesList.add(nameObj);
            System.out.println("Имя успешно добавлено");
        }
    }

    private Name requestNameFields() {
        while (true) {
            System.out.print("Введите фамилию (введите \"--\", чтобы не указывать): ");
            String surname = scanner.nextLine().trim();
            System.out.print("Введите имя (введите \"--\", чтобы не указывать): ");
            String name = scanner.nextLine().trim();
            System.out.print("Введите отчество (введите \"--\", чтобы не указывать): ");
            String patronymic = scanner.nextLine().trim();

            String s = surname.equals("--") ? null : surname;
            String n = name.equals("--") ? null : name;
            String p = patronymic.equals("--") ? null : patronymic;

            try {
                return new Name(s, n, p);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка при создании имени: " + e.getMessage() + ". Попробуйте снова");
            }
        }
    }

    private void addHuman() {
        System.out.println("Выберите способ задания имени:");
        System.out.println("1 - создать новое");
        System.out.println("2 - выбрать существующее");
        int method = getter.getInt(1, 2, "Выберите способ: ");

        Name humanName = null;
        if (method == 1) {
            humanName = requestNameFields();
        } else {
            if (namesList.isEmpty()) {
                System.out.println("Ошибка! Список имен пуст. Сначала создайте имя");
                return;
            }
            printNames();
            int nameIndex = getter.getInt(1, namesList.size(), "Введите номер имени: ") - 1;
            humanName = namesList.get(nameIndex);
        }

        int height = getter.getInt(1, Integer.MAX_VALUE, "Введите рост: ");

        Human father = null;
        if (!humansList.isEmpty()) {
            printHumans();
            System.out.print("Введите номер человека-отца (введите \"--\", чтобы не указывать): ");
            String fatherInput = scanner.nextLine().trim();
            if (!fatherInput.equals("--")) {
                try {
                    int fatherIndex = Integer.parseInt(fatherInput) - 1;
                    if (fatherIndex >= 0 && fatherIndex < humansList.size()) {
                        father = humansList.get(fatherIndex);
                    } else {
                        System.out.println("Ошибка! Такого человека не существует");
                        return;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка! Введено некорректное значение");
                    return;
                }
            }
        }

        try {
            humansList.add(new Human(humanName, height, father));
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка! " + e.getMessage());
            return;
        }
        System.out.println("Человек успешно добавлен");
    }

    private void printNames() {
        if (namesList.isEmpty()) {
            System.out.println("Список имён пуст");
            return;
        }
        for (int i = 0; i < namesList.size(); i++) {
            System.out.println((i + 1) + " - " + namesList.get(i) + (i == namesList.size() - 1 ? "" : ","));
        }
        System.out.println();
    }

    private void printHumans() {
        if (humansList.isEmpty()) {
            System.out.println("Список людей пуст");
            return;
        }
        for (int i = 0; i < humansList.size(); i++) {
            System.out.println((i + 1) + " - " + humansList.get(i) + (i == humansList.size() - 1 ? "" : ","));
        }
        System.out.println();
    }

    private void setFatherForHuman() {
        if (humansList.isEmpty()) {
            System.out.println("Ошибка! Список людей пуст");
            return;
        }
        printHumans();
        int childIndex = getter.getInt(1, humansList.size(), "Введите номер человека, которому хотите изменить отца: ") - 1;

        System.out.print("Введите номер человека-отца (введите \"null\", чтобы удалить отца): ");
        String input = scanner.nextLine().trim();

        Human newFather = null;
        if (!input.equalsIgnoreCase("null")) {
            try {
                int fatherIndex = Integer.parseInt(input) - 1;
                if (fatherIndex >= 0 && fatherIndex < humansList.size()) {
                    newFather = humansList.get(fatherIndex);
                } else {
                    System.out.println("Ошибка! Такого человека не существует");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Неверный формат ввода");
                return;
            }
        }

        try {
            humansList.get(childIndex).setFather(newFather);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка! " + e.getMessage());
            return;
        }
        System.out.println("Данные об отце успешно обновлены");
    }

    private void printHumanPedigree() {
        if (humansList.isEmpty()) {
            System.out.println("Ошибка! Список людей пуст");
            return;
        }
        printHumans();
        int index = getter.getInt(1, humansList.size(), "Введите номер человека: ") - 1;

        Human current = humansList.get(index);
        StringBuilder tabs = new StringBuilder();
        System.out.println(current);

        while (current.getFather() != null) {
            tabs.append("\t");
            System.out.println(tabs + "Отец: " + current.getFather());
            current = current.getFather();
        }
    }

    private void cityMenu() {
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1) Добавить город");
            System.out.println("2) Добавить дорогу");
            System.out.println("3) Вывести города");
            System.out.println("4) Вывести города и дороги");
            System.out.println("5) Удалить город");
            System.out.println("6) Удалить дорогу");
            System.out.println("Введите 0 для возвращения в меню\n");

            int action = getter.getInt(0, 6, "Выберите действие: ");
            System.out.println();

            if (action == 0) break;

            switch (action) {
                case 1 -> addCity();
                case 2 -> addRoadBetweenCities();
                case 3 -> printCitiesOnly();
                case 4 -> printCitiesAndRoads();
                case 5 -> removeCityByName();
                case 6 -> removeRoadBetweenCities();
            }
        }
    }

    private City findCityByName(String cityName) {
        for (City c : citiesList) {
            if (c.name.equalsIgnoreCase(cityName)) return c;
        }
        return null;
    }

    private void addCity() {
        System.out.print("Введите имя города: ");
        String name = scanner.nextLine().trim();
        if (findCityByName(name) != null) {
            System.out.println("Ошибка! Такой город уже существует");
            return;
        }

        City newCity = new City(name);
        int roadsCount = getter.getInt(0, Integer.MAX_VALUE, "Введите количество дорог: ");

        for (int i = 1; i <= roadsCount; i++) {
            System.out.print("Введите дорогу " + i + " в формате: <имя_города> <стоимость>: ");
            String roadLine = scanner.nextLine().trim();
            String[] parts = roadLine.split("\\s+");

            if (parts.length < 2) {
                System.out.println("Предупреждение! Некорректный формат ввода дороги " + i);
                continue;
            }
            String targetName = parts[0];
            int cost;
            try {
                cost = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Предупреждение! Некорректная стоимость в дороге " + i);
                continue;
            }
            City targetCity = findCityByName(targetName);
            if (targetCity == null) {
                System.out.println("Предупреждение! Невозможно создать дорогу " + i + ", отсутствует город " + targetName);
            } else {
                newCity.addRoad(targetCity, cost);
            }
        }
        citiesList.add(newCity);
        System.out.println("Город успешно добавлен");
    }
    private void addRoadBetweenCities() {
        System.out.print("Введите дорогу в формате: <имя_нач_города> <имя_конеч_города> <стоимость>: ");
        String line = scanner.nextLine().trim();
        String[] parts = line.split("\s+");
        if (parts.length < 3) {
            System.out.println("Ошибка! Некорректный формат ввода");
            return;
        }
        String startName = parts[0];
        String endName = parts[1];
        int cost;
        try {
            cost = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка! Некорректная стоимость дороги");
            return;
        }
        City startCity = findCityByName(startName);
        City endCity = findCityByName(endName);
        if (startCity == null) {
            System.out.println("Ошибка! Невозможно создать дорогу, отсутствует город " + startName);
            return;
        }
        if (endCity == null) {
            System.out.println("Ошибка! Невозможно создать дорогу, отсутствует город " + endName);
            return;
        }
        if (startCity.getRoads().containsKey(endCity)) {
            System.out.println("Ошибка! Такая дорога уже существует");
            return;
        }
        startCity.addRoad(endCity, cost);
        System.out.println("Дорога успешно создана");
    }
    private void printCitiesOnly() {
        if (citiesList.isEmpty()) {
            System.out.println("Список городов пуст");
            return;
        }
        for (int i = 0; i < citiesList.size(); i++) {
            System.out.println(" - " + citiesList.get(i).name + (i == citiesList.size() - 1 ? "" : ","));
        }
        System.out.println();
    }
    private void printCitiesAndRoads() {
        if (citiesList.isEmpty()) {
            System.out.println("Список городов пуст");
            return;
        }
        for (City city : citiesList) {
            System.out.println(city.toString() + "\n");
        }
    }
    private void removeCityByName() {
        System.out.print("Введите имя города для удаления: ");
        String name = scanner.nextLine().trim();
        City toRemove = findCityByName(name);
        if (toRemove == null) {
            System.out.println("Ошибка! Такого города не существует");
            return;
        }
        citiesList.remove(toRemove);
        for (City c : citiesList) {
            c.removeRoad(toRemove);
        }
        System.out.println("Город " + name + " и все связанные с ним дороги удалены");
    }
    private void removeRoadBetweenCities() {
        System.out.print("Введите дорогу для удаления в формате: <имя_нач_города> <имя_конеч_города>: ");
        String line = scanner.nextLine().trim();
        String[] parts = line.split("\s+");
        if (parts.length < 2) {
            System.out.println("Ошибка! Некорректный формат ввода");
            return;
        }
        String startName = parts[0];
        String endName = parts[1];
        City startCity = findCityByName(startName);
        City endCity = findCityByName(endName);
        if (startCity == null || endCity == null || !startCity.getRoads().containsKey(endCity)) {
            System.out.println("Ошибка! Дороги между городами " + startName + " и " + endName + " не существует");
            return;
        }
        startCity.removeRoad(endCity);
        System.out.println("Дорога успешно удалена");
    }

    private void catMenu() {
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1) Добавить кота");
            System.out.println("2) Помяукать");
            System.out.println("3) Вывести котов");
            System.out.println("4) Удалить кота");
            System.out.println("Введите 0 для возвращения в меню\n");
            int action = getter.getInt(0, 4, "Выберите действие: ");
            System.out.println();
            if (action == 0) break;
            switch (action) {
                case 1 -> addCat();
                case 2 -> makeCatMeow();
                case 3 -> printCats();
                case 4 -> removeCatByName();
            }
        }
    }
    private Cat findCatByName(String catName) {
        for (Cat cat : catsList) {
            if (cat.name.equalsIgnoreCase(catName)) return cat;
        }
        return null;
    }
    private void addCat() {
        System.out.print("Введите имя кота: ");
        String name = scanner.nextLine().trim();
        if (findCatByName(name) != null) {
            System.out.println("Ошибка! Такой кот уже существует");
            return;
        }
        catsList.add(new Cat(name));
        System.out.println("Кот успешно добавлен");
    }
    private void makeCatMeow() {
        System.out.print("Введите имя кота: ");
        String name = scanner.nextLine().trim();
        Cat cat = findCatByName(name);
        if (cat == null) {
            System.out.println("Ошибка! Такого кота не существует");
            return;
        }
        int count = getter.getInt(0, Integer.MAX_VALUE, "Введите количество мяуканий: ");
        cat.meow(count);
    }
    private void printCats() {
        if (catsList.isEmpty()) {
            System.out.println("Список котов пуст");
            return;
        }
        for (int i = 0; i < catsList.size(); i++) {
            System.out.println(" - " + catsList.get(i).name + (i == catsList.size() - 1 ? "" : ","));
        }
        System.out.println();
    }
    private void removeCatByName() {
        System.out.print("Введите имя кота для удаления: ");
        String name = scanner.nextLine().trim();
        Cat cat = findCatByName(name);
        if (cat == null) {
            System.out.println("Ошибка! Такого кота не существует");
            return;
        }
        catsList.remove(cat);
        System.out.println("Кот " + name + " успешно удален. Зачем ты это сделал? :(");
    }
}