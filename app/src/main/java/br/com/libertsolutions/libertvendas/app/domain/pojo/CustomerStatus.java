package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;

import static br.com.libertsolutions.libertvendas.app.domain.pojo.CustomerStatus.STATUS_CREATED;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.CustomerStatus.STATUS_MODIFIED;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.CustomerStatus.STATUS_UNMODIFIED;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author Filipe Bezerra
 */
@Retention(SOURCE)
@IntDef({STATUS_UNMODIFIED, STATUS_CREATED, STATUS_MODIFIED})
public @interface CustomerStatus {

    int STATUS_UNMODIFIED = 0;
    int STATUS_CREATED = 1;
    int STATUS_MODIFIED = 2;
}
