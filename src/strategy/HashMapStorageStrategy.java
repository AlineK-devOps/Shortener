package strategy;

import java.util.HashMap;
import java.util.Map;

/** Хранилище, используещее HashMap **/

public class HashMapStorageStrategy implements StorageStrategy{
    private HashMap<Long, String> data = new HashMap<>(); //хранилище данных

    /** Содержит ли хранилище идентификатор **/
    @Override
    public boolean containsKey(Long key) {
        return data.containsKey(key);
    }

    /** Содержит ли хранилище строку **/
    @Override
    public boolean containsValue(String value) {
        return data.containsValue(value);
    }

    /** Добавить в хранилище новую пару ключ-значение **/
    @Override
    public void put(Long key, String value) {
        data.put(key, value);
    }

    /** Вернуть идентификатор для строки **/
    @Override
    public Long getKey(String value) {
        for (Map.Entry<Long, String> entry : data.entrySet()){
            if (entry.getValue().equals(value))
                return entry.getKey();
        }
        return null;
    }

    /** Вернуть строку для идентификатора **/
    @Override
    public String getValue(Long key) {
        return data.get(key);
    }
}
