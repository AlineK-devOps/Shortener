import strategy.StorageStrategy;

/** Укорачиватель ссылок **/

public class Shortener {
    private Long lastId = 0L;                /* последнее значение идентификатора, которое было использовано
                                                при добавлении новой строки в хранилище */

    private StorageStrategy storageStrategy; //стратегия хранения данных

    public Shortener(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    /** Возвращает идентификатор id для заданной строки **/
    public synchronized Long getId(String string){
        if (storageStrategy.containsValue(string))
            return storageStrategy.getKey(string);
        else{
            lastId++;
            storageStrategy.put(lastId, string);
            return lastId;
        }
    }

    /** Возвращает сторку для заданной идентификатора
     * или null, если передан неверный идентификатор **/
    public synchronized String getString(Long id){
        return storageStrategy.getValue(id);
    }
}
