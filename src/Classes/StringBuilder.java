package Classes;

import Interfaces.Comparator;
import Interfaces.TypeBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.stream.Collectors;

public class StringBuilder implements TypeBuilder {
    @Override
    public String typeName() {
        return "String";
    }

    @Override
    public Object create() {
        return getRandomString(5);
    }

    @Override
    public Object read(InputStream in) {
        return new BufferedReader(new InputStreamReader(in))
                .lines().collect(Collectors.joining("\n"));
    }

    public static String getRandomString(int length){
        String allSymbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < length; i++) {
            int number = random.nextInt(allSymbols.length() - 1);
            sb.append(allSymbols.charAt(number));
        }
        return sb.toString();
    }

    @Override
    public Comparator getComparator() {
        return (o1, o2) -> ((String)o1).compareTo((String)o2);
    }
}
