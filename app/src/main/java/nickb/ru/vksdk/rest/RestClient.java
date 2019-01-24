package nickb.ru.vksdk.rest;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static final String VK_BASE_URL = "https://api.vk.com/method/";

    private Retrofit mRetrofit;

    public RestClient() {

        mRetrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(VK_BASE_URL)
                .build();
    }

    public <S> S createService(Class<S> serviceClass) {
        return mRetrofit.create(serviceClass);
    }

}
