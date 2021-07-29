package strategy;

import java.util.Objects;

/** Файловое хранилище **/

public class FileStorageStrategy implements StorageStrategy{
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;  //дефолтный начальный размер массива
    static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000; //дефолтный максимальный размер файла

    FileBucket[] table;                                       //массив с данными
    int size;                                                 //размер массива
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT; //максимальный размер файла
    long maxBucketSize;                                       //маскимальный размер

    public FileStorageStrategy(){
        init();
    }

    /** Инициализируем массив **/
    private void init(){
        table =  new FileBucket[DEFAULT_INITIAL_CAPACITY];

        for (int i = 0; i < table.length; i++)
            table[i] = new FileBucket();
    }

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

        FileBucket bucket = table[indexFor(hash, table.length)];

        for (Entry entry = bucket.getEntry(); entry != null; entry = entry.next){
            if (entry.hash == hash && entry.key.equals(key))
                return entry;
        }
        return null;
    }

    /** Изменение размера массива **/
    void resize(int newCapacity){
        FileBucket[] newTable = new FileBucket[newCapacity];

        for (int i = 0; i < newTable.length; i++)
            newTable[i] = new FileBucket();

        transfer(newTable);

        for (FileBucket fileBucket : table)
            fileBucket.remove();

        table = newTable;
    }

    /** Пересчитывает индексы элемента нового массива **/
    void transfer(FileBucket[] newTable){
        int newCapacity = newTable.length;
        maxBucketSize = 0;

        for (FileBucket bucket : table) {
            Entry e = bucket.getEntry();

            while (e != null){
                Entry next = e.next;
                int indexInNewTable = indexFor(hash(e.getKey()), newCapacity);
                e.next = newTable[indexInNewTable].getEntry();
                newTable[indexInNewTable].putEntry(e);
                e = next;
            }

            long currentBucketSize = bucket.getFileSize();
            if (currentBucketSize > maxBucketSize)
                maxBucketSize = currentBucketSize;
        }
    }

    /** Добавление элемента в мапу **/
    void addEntry(int hash, Long key, String value, int bucketIndex){
        if (maxBucketSize >= bucketSizeLimit){
            resize(2 * table.length);
            hash = hash(key);
            bucketIndex = indexFor(hash, table.length);
        }
        createEntry(hash, key, value, bucketIndex);
    }

    /** Создание нового элемента **/
    void createEntry(int hash, Long key, String value, int bucketIndex){
        FileBucket bucket = table[bucketIndex];
        Entry e = bucket.getEntry();

        bucket.putEntry(new Entry(hash, key, value, e));
        size++;

        long currentBucketSize = table[bucketIndex].getFileSize();
        if (currentBucketSize > maxBucketSize)
            maxBucketSize = currentBucketSize;
    }

    /** Содержит ли хранилище идентификатор **/
    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    /** Содержит ли хранилище строку **/
    @Override
    public boolean containsValue(String value) {
        for (FileBucket bucket : table){
            for (Entry e = bucket.getEntry(); e != null; e = e.next) {
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
        for (Entry e = table[i].getEntry(); e != null; e = e.next) {
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
        for (FileBucket bucket : table){
            for (Entry e = bucket.getEntry(); e != null; e = e.next) {
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

    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }
}
