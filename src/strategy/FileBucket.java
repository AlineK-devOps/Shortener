package strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/** Элементы FileStorageStrategy **/

public class FileBucket {
    Path path; //путь к файлу

    public FileBucket(){
        try {
            path = Files.createTempFile(null, null);

            Files.deleteIfExists(path);
            Files.createFile(path);

            path.toFile().deleteOnExit();
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }

    /** Возвращает размер файла path **/
    public long getFileSize(){
        long size = 0;

        try {
            size = Files.size(path);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return size;
    }

    /** Сериализация entry в файл **/
    public void putEntry(Entry entry){
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))){
            out.writeObject(entry);
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
    }

    /** Получает entry из файла **/
    public Entry getEntry(){
        if (getFileSize() == 0)
            return null;

        Entry entry = null;

        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))){
            entry = (Entry)in.readObject();
        }
        catch (IOException | ClassNotFoundException exception){
            exception.printStackTrace();
        }

        return entry;
    }

    /** Удаление файла path **/
    public void remove(){
        try {
            Files.delete(path);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
