package strategy;

import java.io.Serializable;
import java.util.Objects;

/** Элементы OurHashMap (на основе Node из HashMap) **/

public class Entry implements Serializable {
    Long key;     //ключ
    String value; //значение
    Entry next;   //указатель на следующий элемент в цепочке с тем же индексом
    int hash;     //хешкод ключа

    public Entry(int hash, Long key, String value, Entry next) {
        this.key = key;
        this.value = value;
        this.next = next;
        this.hash = hash;
    }

    /** Возвращает ключ **/
    public Long getKey(){
        return key;
    }

    /** Возвращает значение **/
    public String getValue(){
        return value;
    }

    /** Возвращает хешкод **/
    public final int hashCode() {
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }

    /** Сравнивает объекты **/
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o instanceof Entry) {
            Entry e = (Entry)o;

            return Objects.equals(key, e.getKey()) &&
                    Objects.equals(value, e.getValue());
        }
        return false;
    }

    /** Строковое представление **/
    public final String toString() {
        return key + "=" + value;
    }
}