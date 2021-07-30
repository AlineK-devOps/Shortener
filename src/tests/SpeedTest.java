package tests;

import main.Helper;
import main.Shortener;
import org.junit.Assert;
import org.junit.Test;
import strategy.HashBiMapStorageStrategy;
import strategy.HashMapStorageStrategy;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/** Тестирование скорости работы стратегий **/

public class SpeedTest {
    /** Замеряем время получения идентификаторов **/
    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids){
        Date start = new Date();

        for (String s : strings)
            ids.add(shortener.getId(s));

        Date end = new Date();

        return end.getTime() - start.getTime();
    }

    /** Замеряем время получения строк **/
    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings){
        Date start = new Date();

        for (Long id : ids)
            strings.add(shortener.getString(id));

        Date end = new Date();

        return end.getTime() - start.getTime();
    }

    /** Тестирует скорость работы стратегий HashMapStorageStrategy и HashBiMapStorageStrategy **/
    @Test
    public void testHashMapStorage(){
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

        //генерируем тестовые данные
        HashSet<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10000; i++){
            origStrings.add(Helper.generateRandomString());
        }

        HashSet<Long> ids1 = new HashSet<>();
        HashSet<Long> ids2 = new HashSet<>();

        //получаем время работы стратегий при получении идентификаторов
        long time1 = getTimeToGetIds(shortener1, origStrings, ids1);
        long time2 = getTimeToGetIds(shortener2, origStrings, ids2);

        //проверяем, что время работы HashMapStorageStrategy больше, чем HashBiMapStorageStrategy
        Assert.assertTrue(time1 > time2);

        HashSet<String> strings1 = new HashSet<>();
        HashSet<String> strings2 = new HashSet<>();

        //получаем время работы стратегий при получении строк
        long time11 = getTimeToGetStrings(shortener1, ids1, strings1);
        long time22 = getTimeToGetStrings(shortener2, ids2, strings2);

        //проверяем, что время работы HashMapStorageStrategy примерно равно HashBiMapStorageStrategy
        Assert.assertEquals(time11, time22, 30);
    }
}
