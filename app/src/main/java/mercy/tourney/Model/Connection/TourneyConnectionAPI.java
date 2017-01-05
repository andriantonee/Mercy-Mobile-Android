package mercy.tourney.Model.Connection;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import mercy.tourney.Model.Controller.TourneyAPIModel;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Andrianto on 11/25/2016.
 */
public class TourneyConnectionAPI {
    private static final String BASEURL = "http://192.168.137.1";
    private static TourneyConnectionAPI instance;
    private static TourneyAPIModel api;
    private static Retrofit retrofit;

    /**
     * Instances / Singleton
     *
     * @return Object TourneyConnectionAPI
     */
    public static TourneyConnectionAPI getInstance() {
        return (instance == null) ? new TourneyConnectionAPI() : instance;
    }

    /**
     * Get all Routing From TourneyAPIModel
     *
     * @return Object TourneyAPIModel
     */
    public TourneyAPIModel getAPIModel() {
        return api;
    }

    /**
     * Build : Retrofit
     * Create : TourneyAPIModel
     */
    private TourneyConnectionAPI() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("X-Requested-With", "XMLHttpRequest")
                        .header("Accept", "application/json")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        api = retrofit.create(TourneyAPIModel.class);
    }

    public static String getBASEURL() {
        return BASEURL;
    }
}
