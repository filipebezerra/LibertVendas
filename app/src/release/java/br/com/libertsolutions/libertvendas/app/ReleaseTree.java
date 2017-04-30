package br.com.libertsolutions.libertvendas.app;

import android.util.Log;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class ReleaseTree extends Timber.Tree {

    private static final int MAX_LOG_LENGTH = 4000;

    @Override protected boolean isLoggable(final String tag, final int priority) {
        return !(priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO);
    }

    @Override protected void log(
            final int priority, final String tag, final String message, final Throwable t) {
        if (isLoggable(tag, priority)) {
            if (priority == Log.ERROR && t != null) {
                //TODO: Crashlytics.logException(t);
            }

            if (message.length() < MAX_LOG_LENGTH) {
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, message);
                } else {
                    Log.println(priority, tag, message);
                }
                return;
            }

            // Split by line, then ensure each line can fit into Log's maximum length.
            for (int i = 0, length = message.length(); i < length; i++) {
                int newline = message.indexOf('\n', i);
                newline = newline != -1 ? newline : length;
                do {
                    int end = Math.min(newline, i + MAX_LOG_LENGTH);
                    String part = message.substring(i, end);
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, part);
                    } else {
                        Log.println(priority, tag, part);
                    }
                    i = end;
                } while (i < newline);
            }
        }
    }
}
