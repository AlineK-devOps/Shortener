package strategy;

/** Стратегия хранения строк и индентификаторов **/

public interface StorageStrategy {
    boolean containsKey(Long key); //содержит ли хранилище идентификатор
    boolean containsValue(String value); //содержит ли хранилище строку
    void put(Long key, String value); //добавить в хранилище новую пару ключ-значение
    Long getKey(String value); //вернуть идентификатор для строки
    String getValue(Long key); //вернуть строку для идентификатора
}
