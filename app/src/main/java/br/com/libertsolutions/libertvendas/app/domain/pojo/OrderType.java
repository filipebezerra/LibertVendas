package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;

import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderType.ORDER_TYPE_NORMAL;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderType.ORDER_TYPE_RETURNED;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author Filipe Bezerra
 */
@Retention(SOURCE)
@IntDef({ORDER_TYPE_NORMAL, ORDER_TYPE_RETURNED})
public @interface OrderType {
    int ORDER_TYPE_NORMAL = 0;
    int ORDER_TYPE_RETURNED = 1;
}
