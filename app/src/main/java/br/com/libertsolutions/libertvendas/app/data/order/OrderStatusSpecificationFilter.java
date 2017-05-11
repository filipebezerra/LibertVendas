package br.com.libertsolutions.libertvendas.app.data.order;

/**
 * @author Filipe Bezerra
 */

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;

import static br.com.libertsolutions.libertvendas.app.data.order.OrderStatusSpecificationFilter.CREATED_OR_MODIFIED;
import static br.com.libertsolutions.libertvendas.app.data.order.OrderStatusSpecificationFilter.INVOICED;
import static br.com.libertsolutions.libertvendas.app.data.order.OrderStatusSpecificationFilter.NONE;
import static br.com.libertsolutions.libertvendas.app.data.order.OrderStatusSpecificationFilter.NOT_CANCELLED;
import static br.com.libertsolutions.libertvendas.app.data.order.OrderStatusSpecificationFilter.SYNCED;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({ NONE, NOT_CANCELLED, CREATED_OR_MODIFIED, SYNCED, INVOICED })
public @interface OrderStatusSpecificationFilter {

    int NONE = 0;
    int NOT_CANCELLED = 1;
    int CREATED_OR_MODIFIED = 2;
    int SYNCED = 3;
    int INVOICED = 4;
}
