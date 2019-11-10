package com.huijiewei.agile.base.until;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();

        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }

        return null;
    }
}
