package strategy;

import java.util.Objects;

/** Хранилище, используещее собственную коллекцию на основе HashMap **/

public class OurHashMapStorageStrategy implements StorageStrategy{
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;  //дефолтный начальный размер массива
    static final float DEFAULT_LOAD_FACTOR = 0.75f;      //дефолтный коэффициент загрузки

    Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];                    //массив с данными
    int size;                                                               //размер массива
    int threshold = (int)(DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);  //предельное количество элементов, при достижении которого, размер хэш-таблицы увеличивается вдвое
    float loadFactor = DEFAULT_LOAD_FACTOR;                                 //коэффициент загрузки

    /** Возвращает хеш ключа **/
    final int hash(Long k){
        k ^= (k >>> 20) ^ (k >>> 12);
        return (int) (k ^ (k >>> 7) ^ (k >>> 4));
    }

    /** Получение позиции в массиве **/
    static int indexFor(int hash, int length){
        return hash & (length - 1);
    }

    /** Получение элемента из мапы **/
    final Entry getEntry(Long key){
        int hash = (key == null) ? 0 : hash(key);
        for (Entry e = table[indexFor(hash, table.length)];
             e != null;
             e = e.next) {

            if (e.hash == hash && e.key.equals(key))
                return e;
        }
        return null;
    }

    /** Изменение размера массива **/
    void resize(int newCapacity){
        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int)(newCapacity * loadFactor);
    }

    /** Пересчитывает индексы элемента нового массива **/
    void transfer(Entry[] newTable){
        Entry[] src = table;
        int newCapacity = newTable.length;
        for (int j = 0; j < src.length; j++) {
            Entry e = src[j];
            if (e != null) {
                src[j] = null;
                do {
                    Entry next = e.next;
                    int i = indexFor(e.hash, newCapacity);
                    e.next = newTable[i];
                    newTable[i] = e;
                    e = next;
                } while (e != null);
            }
        }
    }

    /** Добавление элемента в мапу **/
    void addEntry(int hash, Long key, String value, int bucketIndex){
        if (size >= threshold){
            resize(2 * table.length);
            hash = hash(key);
            bucketIndex = indexFor(hash, table.length);
        }
         createEntry(hash, key, value, bucketIndex);
    }

    /** Создание нового элемента **/
    void createEntry(int hash, Long key, String value, int bucketIndex){
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, e);
        size++;
    }

    /** Содержит ли хранилище идентификатор **/
    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    /** Содержит ли хранилище строку **/
    @Override
    public boolean containsValue(String value) {
        for (Entry entry : table){
            for (Entry e = entry; e != null; e = e.next) {
                if (e.value.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Добавить в хранилище новую пару ключ-значение **/
    @Override
    public void put(Long key, String value) {
        int hash = hash(key);
        int i = indexFor(hash, table.length);
        for (Entry e = table[i]; e != null; e = e.next) {
            if (e.hash == hash && e.key.equals(key)) {
                e.value = value;
                return;
            }
        }
        addEntry(hash, key, value, i);
    }

    /** Вернуть идентификатор для строки **/
    @Override
    public Long getKey(String value) {
        for (Entry entry : table){
            for (Entry e = entry; e != null; e = e.next) {
                if (e.value.equals(value)) {
                    return e.getKey();
                }
            }
        }
        return null;
    }

    /** Вернуть строку для идентификатора **/
    @Override
    public String getValue(Long key) {
        return Objects.requireNonNull(getEntry(key)).getValue();
    }
}
