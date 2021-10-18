package Classes;

import Interfaces.TypeBuilder;

import java.util.HashMap;
import java.util.Set;

public class TypeFactory {
    private static HashMap<String, TypeBuilder> typeNames;

    static {
        typeNames = new HashMap<>();

        TypeBuilder typeBuilder = new IntegerBuilder();
        typeNames.put(typeBuilder.typeName(), typeBuilder);

        typeBuilder = new StringBuilder();
        typeNames.put(typeBuilder.typeName(), typeBuilder);
    }

    public static Set<String> getTypeNamesSet() { return typeNames.keySet(); }

    public static TypeBuilder getTypeBuilderByName(String name) { return typeNames.get(name); }
}
