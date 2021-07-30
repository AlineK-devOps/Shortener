package tests;

import main.Shortener;
import org.junit.Assert;
import org.junit.Test;
import strategy.*;

/** Тестирование стратегий **/

public class FunctionalTest {

    /** Тестирование отдельной стратегии **/
    public void testStorage(Shortener shortener){
        String s1 = "test_string_13";
        String s2 = "test_string_2";
        String s3 = "test_string_13";

        Long id1 = shortener.getId(s1);
        Long id2 = shortener.getId(s2);
        Long id3 = shortener.getId(s3);

        //проверяем, что id2 не равен id1 и id3
        Assert.assertNotEquals(id2, id1);
        Assert.assertNotEquals(id2, id3);

        //проверяем, что id1 равен id3
        Assert.assertEquals(id1, id3);

        String testString1 = shortener.getString(id1);
        String testString2 = shortener.getString(id2);
        String testString3 = shortener.getString(id3);

        //проверяем, что полученные строки эквиваленты оригинальным
        Assert.assertEquals(testString1, s1);
        Assert.assertEquals(testString2, s2);
        Assert.assertEquals(testString3, s3);
    }

    /** Тестирование стратегии HashMapStorageStrategy **/
    @Test
    public void testHashMapStorageStrategy(){
        Shortener shortener = new Shortener(new HashMapStorageStrategy());
        testStorage(shortener);
    }

    /** Тестирование стратегии OurHashMapStorageStrategy **/
    @Test
    public void testOurHashMapStorageStrategy(){
        Shortener shortener = new Shortener(new OurHashMapStorageStrategy());
        testStorage(shortener);
    }

    /** Тестирование стратегии FileStorageStrategy **/
    @Test
    public void testFileStorageStrategy(){
        Shortener shortener = new Shortener(new FileStorageStrategy());
        testStorage(shortener);
    }

    /** Тестирование стратегии HashBiMapStorageStrategy **/
    @Test
    public void testHashBiMapStorageStrategy(){
        Shortener shortener = new Shortener(new HashBiMapStorageStrategy());
        testStorage(shortener);
    }

    /** Тестирование стратегии DualHashBidiMapStorageStrategy **/
    @Test
    public void testDualHashBidiMapStorageStrategy(){
        Shortener shortener = new Shortener(new DualHashBidiMapStorageStrategy());
        testStorage(shortener);
    }

    /** Тестирование стратегии OurHashBiMapStorageStrategy **/
    @Test
    public void testOurHashBiMapStorageStrategy(){
        Shortener shortener = new Shortener(new OurHashBiMapStorageStrategy());
        testStorage(shortener);
    }
}
