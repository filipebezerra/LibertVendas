package br.com.libertsolutions.libertvendas.app.data.util;

import io.realm.Realm;
import io.realm.RealmModel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Filipe Bezerra
 */
public final class RealmAutoIncrement {
    private Map<Class<? extends RealmModel>, AtomicInteger> modelMap = new HashMap<>();

    private static RealmAutoIncrement autoIncrementMap;

    private Class<? extends RealmModel> mObj;

    private RealmAutoIncrement(Class<? extends RealmModel> obj) {
        mObj = obj;
        modelMap.put(obj, new AtomicInteger(getLastIdFromModel(mObj)));
    }

    private int getLastIdFromModel(Class<? extends RealmModel> clazz) {
        String primaryKeyColumnName = "id";
        Number lastId = Realm.getDefaultInstance().where(clazz).max(primaryKeyColumnName);
        return lastId == null ? 0 : lastId.intValue();
    }

    public Integer getNextIdFromModel() {
        if (isValidMethodCall()) {
            AtomicInteger modelId = modelMap.get(mObj);

            if (modelId == null) {
                return 0;
            }
            return modelId.incrementAndGet();
        }
        return 0;
    }

    private boolean isValidMethodCall() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if (stackTraceElement.getMethodName().equals("newInstance")) {
                return false;
            }
        }
        return true;
    }

    public static RealmAutoIncrement getInstance(Class<? extends RealmModel> obj) {
        if (autoIncrementMap == null) {
            autoIncrementMap = new RealmAutoIncrement(obj);
        }
        return autoIncrementMap;
    }
}
