package com.demo.in.demoprojacc.utils;




import com.demo.in.demoprojacc.BuildConfig;
import com.demo.in.demoprojacc.network_models.APIError;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by 1122744 on 6/23/2016.
 */
public class RetrofitUtils {
   public static String url ="https://jsonplaceholder.typicode.com";

    public static Retrofit getRetrofitRequestBase(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }




    public static OkHttpClient getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());




            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .readTimeout(30*1000, TimeUnit.SECONDS)
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                    .followRedirects(true)
                    .followSslRedirects(true)
                    .connectTimeout(30*1000, TimeUnit.SECONDS)
                    .readTimeout(60*1000, TimeUnit.SECONDS)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                getRetrofitRequestBase()
                        .responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {

            APIError apiError = new APIError();
            apiError.setMessage("Something went wrong, please try again later");
            return apiError;
        }

        return error;
    }


}
