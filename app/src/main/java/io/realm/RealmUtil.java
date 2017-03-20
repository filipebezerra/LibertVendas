package io.realm;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.EnumMap;
import java.util.Map;

public class RealmUtil {

    private static final Field cachesMapField;
    private static final Field refAndCountMapField;
    private static Field globalCountField;

    static {
        cachesMapField = findCachesMapField(RealmCache.class);
        if (cachesMapField != null) {
            cachesMapField.setAccessible(true);
        }

        refAndCountMapField = findRefAndCountMapField(RealmCache.class);
        if (refAndCountMapField != null) {
            refAndCountMapField.setAccessible(true);
        }
    }

    public static synchronized String dumpRealmCount() {
        final StringBuilder sb = new StringBuilder();
        if (cachesMapField == null) {
            return "RealmCache#cachesMap field not found.";
        }
        if (refAndCountMapField == null) {
            return "RealmCache#refAndCountMap field not found.";
        }

        int totalCount = 0;

        synchronized (RealmCache.class) {
            final Map cachesMap = getCachesMap();
            for (Object entryObj : cachesMap.entrySet()) {
                //noinspection unchecked
                final Map.Entry<String, RealmCache> entry = (Map.Entry<String, RealmCache>) entryObj;

                final String path = entry.getKey();
                final RealmCache cache = entry.getValue();
                final EnumMap refAndCountMap = getRefAndCountMap(cache);

                for (Object cacheEntryObj : refAndCountMap.entrySet()) {
                    Map.Entry<Enum/*RealmCache.RealmCacheType*/, Object/*RealmCache.RefAndCount*/> cacheEntry;
                    //noinspection unchecked
                    cacheEntry = (Map.Entry<Enum, Object>) cacheEntryObj;
                    if (globalCountField == null) {
                        globalCountField = findGlobalCountField(cacheEntry.getValue().getClass());
                        if (globalCountField == null) {
                            return "RealmCache.RefAndCount#globalCount field not found.";
                        }
                        globalCountField.setAccessible(true);
                    }

                    final String realmInstanceType = cacheEntry.getKey().name();
                    final int instanceCount = getGlobalCount(cacheEntry.getValue());
                    totalCount += instanceCount;

                    sb.append(' ').append(path).append("(").append(realmInstanceType).append(")");
                    sb.append(" has ").append(instanceCount).append(" instance(s).");
                }
            }
        }
        return "total: " + totalCount + " instance(s)." + sb.toString();
    }

    private static Field findCachesMapField(Class<RealmCache> clazz) {
        for (Field f : clazz.getDeclaredFields()) {
            if ((f.getModifiers() & Modifier.STATIC) == 0) {
                continue;
            }
            if (f.getType().equals(Map.class)) {
                return f;
            }
        }
        return null;
    }

    private static Map getCachesMap() {
        try {
            return (Map) cachesMapField.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Field findRefAndCountMapField(Class<RealmCache> clazz) {
        for (Field f : clazz.getDeclaredFields()) {
            if ((f.getModifiers() & Modifier.STATIC) != 0) {
                continue;
            }
            if (f.getType().equals(EnumMap.class)) {
                return f;
            }
        }
        return null;
    }

    private static EnumMap getRefAndCountMap(RealmCache cache) {
        try {
            return (EnumMap) refAndCountMapField.get(cache);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Field findGlobalCountField(Class<?> clazz) {
        for (Field f : clazz.getDeclaredFields()) {
            if ((f.getModifiers() & Modifier.STATIC) != 0) {
                continue;
            }
            if (f.getType().equals(int.class)) {
                return f;
            }
        }
        return null;
    }

    private static int getGlobalCount(Object/*RealmCache.RefAndCount*/ value) {
        try {
            return globalCountField.getInt(value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
