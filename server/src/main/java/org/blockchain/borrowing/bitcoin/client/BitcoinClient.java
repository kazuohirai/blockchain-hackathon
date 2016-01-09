package org.blockchain.borrowing.bitcoin.client;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BitcoinClient {

    private static final Logger LOG = Logger.getLogger(BitcoinClient.class);
    private final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON_MEDIA = MediaType.parse("application/json; charset=utf-8");

    private final static String STORE_URL = " ";
    private final static String GET_URL = " ";

    public void store() {
        RequestBody requestBody = RequestBody.create(JSON_MEDIA, JSON.toJSON(new Object()).toString());
        Request request = new Request.Builder()
                .url(STORE_URL)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            response.body().string();
        } catch (IOException e) {
            LOG.error("can't get response of " + STORE_URL);
        }
    }
}
