package Interfaces;

import java.io.InputStream;

public interface TypeBuilder {
    String typeName();
    Object create();
    Object read(InputStream in);
    Interfaces.Comparator getComparator();
}
