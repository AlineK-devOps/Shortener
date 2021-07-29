import strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/** Точка входа программы **/

public class Solution {
    public static void main(String[] args) {
        Solution.testStrategy(new HashMapStorageStrategy(), 10000);
        Solution.testStrategy(new OurHashMapStorageStrategy(), 10000);
        Solution.testStrategy(new FileStorageStrategy(), 100);
        Solution.testStrategy(new OurHashBiMapStorageStrategy(), 10000);
        Solution.testStrategy(new HashBiMapStorageStrategy(), 10000);
    }

    /** Возвращает множество идентификаторов для переданного множества строк **/
    public static Set<Long> getIds(Shortener shortener, Set<String> strings){
        Set<Long> ids = new HashSet<>();

        for (String s : strings)
            ids.add(shortener.getId(s));

        return ids;
    }

    /** Возвращает множество строк для переданного множества идентификаторов **/
    public static Set<String> getStrings(Shortener shortener, Set<Long> keys){
        Set<String> strings = new HashSet<>();

        for (Long id : keys)
            strings.add(shortener.getString(id));

        return strings;
    }

    /** Тестирование работы переданной стратегии на определенное количество элементов **/
    public static void testStrategy(StorageStrategy strategy, long elementsNumber){
        Helper.printMessage(strategy.getClass().getSimpleName()); //выводим имя стратегии

        HashSet<String> strings = new HashSet<>(); //генерируем множество случайных строк
        for (int i = 0; i < elementsNumber; i++){
            strings.add(Helper.generateRandomString());
        }

        Shortener shortener = new Shortener(strategy); //создаем сокращатель ссылок

        Date startId = new Date(); //замеряем время для обработки метода getIds
        Set<Long> testIds = Solution.getIds(shortener, strings);
        Date endId = new Date();
        Helper.printMessage(String.valueOf(endId.getTime() - startId.getTime()));

        Date startString = new Date(); //замеряем время для обработки метода getString
        Set<String> testStrings = Solution.getStrings(shortener, testIds);
        Date endString = new Date();
        Helper.printMessage(String.valueOf(endString.getTime() - startString.getTime()));

        boolean isDone = true; //сравниваем два множества

        if (strings.size() == testStrings.size()){
            Iterator<String> iterator1 = strings.iterator();
            Iterator<String> iterator2 = testStrings.iterator();

            while (iterator1.hasNext()){
                if (!iterator1.next().equals(iterator2.next())){
                    isDone = false;
                    break;
                }
            }
        }
        else isDone = false;

        if (isDone)
            Helper.printMessage("Тест пройден.");
        else
            Helper.printMessage("Тест не пройден.");
    }
}
