package strategy;

import org.apache.commons.collections4.bidimap.DualHashBidiMap;

/** Хранилище, использующее двусторонюю мапу DualHashBidiMap **/

public class DualHashBidiMapStorageStrategy implements StorageStrategy{
    private DualHashBidiMap<Long, String> data = new DualHashBidiMap<>();

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
        return data.getKey(value);
    }

    /** Вернуть строку для идентификатора **/
    @Override
    public String getValue(Long key) {
        return data.get(key);
    }
}
