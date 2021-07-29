package strategy;

import java.util.HashMap;

/** Хранилище, лишенное недостатка долгого получения id по строке **/

public class OurHashBiMapStorageStrategy implements StorageStrategy{
    private HashMap<Long, String> k2v = new HashMap<>(); //Мапа id - string
    private HashMap<String, Long> v2k = new HashMap<>(); //Мапа string - id

    /** Содержит ли хранилище идентификатор **/
    @Override
    public boolean containsKey(Long key) {
        return k2v.containsKey(key);
    }

    /** Содержит ли хранилище строку **/
    @Override
    public boolean containsValue(String value) {
        return v2k.containsKey(value);
    }

    /** Добавить в хранилище новую пару ключ-значение **/
    @Override
    public void put(Long key, String value) {
        k2v.put(key, value);
        v2k.put(value, key);
    }

    /** Вернуть идентификатор для строки **/
    @Override
    public Long getKey(String value) {
        return v2k.get(value);
    }

    /** Вернуть строку для идентификатора **/
    @Override
    public String getValue(Long key) {
        return k2v.get(key);
    }
}
