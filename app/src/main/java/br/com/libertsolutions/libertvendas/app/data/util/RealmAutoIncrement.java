package br.com.libertsolutions.libertvendas.app.data.util;

import io.realm.Realm;
import io.realm.RealmModel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Filipe Bezerra
 */
public class RealmAutoIncrement {
    private static final String PRIMARY_KEY_COLUMN_NAME = "id";

    private static RealmAutoIncrement sAutoIncrement;

    private Map<Class<? extends RealmModel>, AtomicInteger> mModelMap = new HashMap<>();
    private Class<? extends RealmModel> mEntityClass;

    private RealmAutoIncrement(Class<? extends RealmModel> entityClass) {
        mEntityClass = entityClass;
        mModelMap.put(entityClass, new AtomicInteger(getLastIdFromModel(mEntityClass)));
    }

    private int getLastIdFromModel(Class<? extends RealmModel> clazz) {
        Number lastId = Realm.getDefaultInstance().where(clazz).max(PRIMARY_KEY_COLUMN_NAME);
        return lastId == null ? 0 : lastId.intValue();
    }

    public Integer getNextIdFromModel() {
        if (isValidMethodCall()) {
            AtomicInteger modelId = mModelMap.get(mEntityClass);

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

    public static RealmAutoIncrement getInstance(Class<? extends RealmModel> entityClass) {
        if (sAutoIncrement == null) {
            sAutoIncrement = new RealmAutoIncrement(entityClass);
        }
        return sAutoIncrement;
    }
}
