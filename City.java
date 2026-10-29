import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class City {
    public String name;
    private Map<City, Integer> roads;

    City() {
        this.name = "";
        this.roads = new HashMap<>();
    }

    City(String name) {
        this.name = name;
        this.roads = new HashMap<>();
    }

    public Map<City, Integer> getRoads() {
        return Collections.unmodifiableMap(roads);
    }

    public void addRoad(City city, int cost) {
        roads.put(city, cost);
    }

    public void removeRoad(City city) {
        roads.remove(city);
    }

    public void resetRoads(City city) {
        roads.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name + ":\n");
        if (roads.isEmpty()) {
            sb.append("\t(нет дорог)");
        } else {
            int count = 0;
            for (Map.Entry<City, Integer> entry : roads.entrySet()) {
                count++;
                sb.append("\\_  -> ").append(entry.getKey().name).append(" - ").append(entry.getValue());
                sb.append(count == roads.size() ? "" : ";").append("\n");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
