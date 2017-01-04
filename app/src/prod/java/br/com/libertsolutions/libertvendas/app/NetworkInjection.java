package br.com.libertsolutions.libertvendas.app;

import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.PresentationInjection.provideContext;

/**
 * @author Filipe Bezerra
 */
public final class NetworkInjection {

    private static final long HTTP_CACHE_SIZE = 10 * 1024 * 1024;

    private static final String HTTP_CACHE_FILE_NAME = "http-cache";

    private static final String CACHE_CONTROL = "Cache-Control";

    private static Retrofit sRetrofit;

    private static String sBaseUrl;

    private NetworkInjection() {/* No instances */}

    public static Retrofit provideRetrofit() {
        Settings settings = PresentationInjection.provideSettingsRepository().loadSettings();
        String authenticationKey = settings.getAuthenticationKey();
        String serverAddress = settings.getServerAddress();
        if (sRetrofit == null || !sBaseUrl.equalsIgnoreCase(serverAddress)) {
            sBaseUrl = serverAddress;

            sRetrofit = new Retrofit.Builder()
                    .baseUrl(sBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create(provideGson()))
                    .addCallAdapterFactory(
                            RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(provideOkHttpClient(authenticationKey))
                    .build();
        }
        return sRetrofit;
    }

    private static Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    private static OkHttpClient provideOkHttpClient(String authenticationKey) {
        return new OkHttpClient
                .Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(BuildConfig.DEBUG ? 30 : 10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cache(provideHttpCache())
                .addInterceptor(provideLoggingInterceptor())
                .addInterceptor(provideApiInterceptor(authenticationKey))
                .addNetworkInterceptor(provideCacheInterceptor())
                .build();
    }

    private static Cache provideHttpCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(provideContext().getCacheDir(), HTTP_CACHE_FILE_NAME),
                    HTTP_CACHE_SIZE);
        } catch (Exception e) {
            Timber.e(e, "Could not create Cache!");
        }
        return cache;
    }

    private static Interceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ?
                        HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    }

    private static Interceptor provideApiInterceptor(@NonNull final String authKey) {
        return chain -> {
            Request original = chain.request();

            final HttpUrl httpUrl = original.url()
                    .newBuilder()
                    .addQueryParameter("key", authKey)
                    .build();

            final Request request = original.newBuilder()
                    .url(httpUrl)
                    .header("Accept", "applicaton/json")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        };
    }

    private static Interceptor provideCacheInterceptor() {
        return chain -> {
            Response response = chain.proceed(chain.request());

            // re-write response header to force use of cache
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(2, TimeUnit.MINUTES)
                    .build();

            return response.newBuilder()
                    .header(CACHE_CONTROL, cacheControl.toString())
                    .build();
        };
    }
}
