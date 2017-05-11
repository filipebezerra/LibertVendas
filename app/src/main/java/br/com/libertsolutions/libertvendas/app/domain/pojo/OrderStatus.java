package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;

import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus.STATUS_CANCELLED;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus.STATUS_CREATED;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus.STATUS_INVOICED;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus.STATUS_MODIFIED;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus.STATUS_SYNCED;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author Filipe Bezerra
 */
@Retention(SOURCE)
@IntDef({STATUS_CREATED, STATUS_MODIFIED, STATUS_SYNCED, STATUS_CANCELLED, STATUS_INVOICED})
public @interface OrderStatus {

    int STATUS_CREATED = 0;
    int STATUS_MODIFIED = 1;
    int STATUS_SYNCED = 2;
    int STATUS_CANCELLED = 3;
    int STATUS_INVOICED = 5;
}
