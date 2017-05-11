package br.com.libertsolutions.libertvendas.app.presentation.util;

import android.support.annotation.ColorRes;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus;

import static android.R.color.transparent;
import static br.com.libertsolutions.libertvendas.app.R.color.color_order_invoiced;
import static br.com.libertsolutions.libertvendas.app.R.color.color_order_is_pending;
import static br.com.libertsolutions.libertvendas.app.R.color.color_order_was_cancelled;
import static br.com.libertsolutions.libertvendas.app.R.color.color_order_was_synced;

/**
 * @author Filipe Bezerra
 */
public class OrderUtils {

    private OrderUtils() {}

    public static @ColorRes int getStatusColor(@OrderStatus final int status) {
        switch (status) {
            case OrderStatus.STATUS_CREATED:
            case OrderStatus.STATUS_MODIFIED: {
                return color_order_is_pending;
            }
            case OrderStatus.STATUS_SYNCED: {
                return color_order_was_synced;
            }
            case OrderStatus.STATUS_CANCELLED: {
                return color_order_was_cancelled;
            }
            case OrderStatus.STATUS_INVOICED: {
                return color_order_invoiced;
            }
            default:
                return transparent;
        }
    }
}
