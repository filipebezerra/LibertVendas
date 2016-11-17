package br.com.libertsolutions.libertvendas.app.presentation.util;

import android.util.Log;
import com.crashlytics.android.Crashlytics;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class CrashReportingTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }

        if (t == null) {
            Crashlytics.log(priority, tag, message);
        } else {
            if (priority == Log.ERROR) {
                Crashlytics.logException(t);
            } else {
                Crashlytics.log(Log.WARN, tag, message);
            }
        }
    }
}