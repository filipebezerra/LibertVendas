package br.com.libertsolutions.libertvendas.app.presentation;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.BaseApplication;
import br.com.libertsolutions.libertvendas.app.BuildConfig;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepositoryImpl;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;
import br.com.libertsolutions.libertvendas.app.presentation.helper.ConnectivityHelper;
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
import org.greenrobot.eventbus.EventBus;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class PresentationInjector {

    private PresentationInjector() {/* No instances */}

    public static Context provideApplicationContext() {
        return BaseApplication.getInstance();
    }

    private static ConnectivityHelper sConnectivityHelper;

    public static ConnectivityHelper provideConnectivityHelper() {
        if (sConnectivityHelper == null) {
            sConnectivityHelper = new ConnectivityHelper(provideApplicationContext());
        }
        return sConnectivityHelper;
    }

    private static EventBus sEventBus;

    public static EventBus provideEventBus() {
        if (sEventBus == null) {
            sEventBus = EventBus.builder()
                    .logNoSubscriberMessages(BuildConfig.DEBUG)
                    .sendNoSubscriberEvent(BuildConfig.DEBUG)
                    .build();
        }
        return sEventBus;
    }

    private static SettingsRepository sSettingsRepository;

    public static SettingsRepository provideSettingsRepository() {
        if (sSettingsRepository == null) {
            sSettingsRepository = new SettingsRepositoryImpl(provideApplicationContext());
        }
        return sSettingsRepository;
    }

    private static final long HTTP_CACHE_SIZE = 10 * 1024 * 1024;

    private static final String HTTP_CACHE_FILE_NAME = "http-cache";

    private static final String CACHE_CONTROL = "Cache-Control";

    public static Retrofit provideRetrofit() {
        final Settings settings = provideSettingsRepository().getSettings();
        final String serverAddress = settings.getServerAddress();
        final String authKey = settings.getAuthKey();

        return new Retrofit.Builder()
                .baseUrl(serverAddress)
                .addConverterFactory(GsonConverterFactory.create(provideGson()))
                .addCallAdapterFactory(
                        RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(provideOkHttpClient(authKey))
                .build();
    }

    private static Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    private static OkHttpClient provideOkHttpClient(String authenticationKey) {
        return new OkHttpClient
                .Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
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
            cache = new Cache(new File(provideApplicationContext().getCacheDir(),
                    HTTP_CACHE_FILE_NAME), HTTP_CACHE_SIZE);
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
