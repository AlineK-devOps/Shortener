package strategy;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/** Хранилище, использующее двусторонюю мапу **/

public class HashBiMapStorageStrategy implements StorageStrategy{
    private HashBiMap<Long, String> data = HashBiMap.create();

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
        BiMap<String, Long> reverseMap = data.inverse();
        return reverseMap.get(value);
    }

    /** Вернуть строку для идентификатора **/
    @Override
    public String getValue(Long key) {
        return data.get(key);
    }
}
