package br.com.libertsolutions.libertvendas.app.data.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import br.com.libertsolutions.libertvendas.app.BuildConfig;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * @author Filipe Bezerra
 */
public class ServiceFactory {
    private static final long HTTP_CACHE_SIZE = 10 * 1024 * 1024;

    private static final String HTTP_CACHE_FILE_NAME = "http";

    private static Retrofit sRetrofit;

    private static String sBaseUrl;

    private static SettingsRepository sSettingsRepository;

    public static <S> S createService(@NonNull Context context, @NonNull Class<S> serviceClass) {
        final Settings settings = getSettingsRepository(context).loadSettings();
        final String urlServidor = settings.getUrlServidor();
        final String chaveAutenticacao = settings.getChaveAutenticacao();

        if (TextUtils.isEmpty(urlServidor) || TextUtils.isEmpty(chaveAutenticacao)) {
            return null;
        }

        return getRetrofit(context, urlServidor, chaveAutenticacao).create(serviceClass);
    }

    private static SettingsRepository getSettingsRepository(Context context) {
        if (sSettingsRepository == null) {
            sSettingsRepository = Injection.provideSettingsRepository(context);
        }
        return sSettingsRepository;
    }

    private static Retrofit getRetrofit(
            Context context, String enderecoServidor, String chaveAutenticacao) {
        if (sRetrofit == null || !sBaseUrl.equalsIgnoreCase(enderecoServidor)) {
            sBaseUrl = enderecoServidor;

            sRetrofit = new Retrofit.Builder()
                    .baseUrl(sBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create(createGson()))
                    .addCallAdapterFactory(
                            RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(createOkHttpClient(context, chaveAutenticacao))
                    .build();
        }
        return sRetrofit;
    }

    private static OkHttpClient createOkHttpClient(Context context, String chaveAutenticacao) {
        return new OkHttpClient
                .Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cache(createCache(context))
                .addInterceptor(createLoggingInterceptor())
                .addInterceptor(createApiInterceptor(chaveAutenticacao))
                .build();
    }

    private static Gson createGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    private static Cache createCache(@NonNull Context context) {
        return new Cache(new File(context.getCacheDir(), HTTP_CACHE_FILE_NAME), HTTP_CACHE_SIZE);
    }

    private static Interceptor createLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ?
                        HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    }

    private static Interceptor createApiInterceptor(@NonNull final String authKey) {
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
}
