package br.com.libertsolutions.libertvendas.app.presentation.base;

/**
 * @author Filipe Bezerra
 */
public abstract class BaseEvent<T> {

    private final T mEventValue;

    protected BaseEvent(T pEventValue) {
        mEventValue = pEventValue;
    }

    public T getEventValue() {
        return mEventValue;
    }
}
