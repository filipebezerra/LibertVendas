package br.com.libertsolutions.libertvendas.app.presentation.utils;

import java.util.List;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public class ObservableUtils {

    private ObservableUtils() {/* No instances */}

    public static  <T> Observable<List<T>> toObservable(List<T> list) {
        return list != null && ! list.isEmpty() ?
                Observable.just(list) : Observable.empty();
    }
}
