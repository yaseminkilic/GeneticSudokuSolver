
import java.util.Map;
import java.util.HashMap;

class Data {

    private static Data instance;
    private Map<String, Object> map;

    Data() { map = new HashMap<>(); }
    static Data instance() { 
    	return (instance != null) ? instance : (instance = new Data());
    }

    Object get(String id) {
        if(!map.containsKey(id)){ throw new IllegalArgumentException("In Data, Map doesnt contain " + id); }
        return map.get(id);
    }

    void set(String id, Object obj) {
        if (map.containsKey(id)) { throw new IllegalArgumentException("In Data, same map-ID (Conflict)"); }
        map.put(id, obj);
    }
}
