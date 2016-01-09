package org.blockchain.borrowing.bitcoin.client;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.*;
import org.apache.log4j.Logger;
import org.blockchain.borrowing.bitcoin.client.domain.Entry;
import org.blockchain.borrowing.bitcoin.client.domain.EntryCommit;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BitcoinClient {

    private static final String CHAIN_ID = "ef709aedfafeb7fb60e76c4714f7f18df2c36d479edd016f78cff7ec08dd8911";
    private static final Logger LOG = Logger.getLogger(BitcoinClient.class);
    private final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON_MEDIA = MediaType.parse("application/json; charset=utf-8");

    private final static String BLOCK_COMMIT = "http://192.168.0.233:8089/v1/compose-entry-submit/zeros";
    private final static String COMMIT_URL = "http://192.168.0.233:8088/v1/commit-entry";
    private final static String REVEAL_URL = "http://192.168.0.233:8088/v1/reveal-entry";
    private final static String FIND_URL = "http://192.168.0.233:8088/v1/entry-by-hash/";


    public EntryCommit composeCommit(Entry entry) {
        EntryCommit entryCommit = blockCommit(entry);
        commit(entryCommit);
        reveal(entryCommit);

        return entryCommit;
    }


    public EntryCommit blockCommit(Entry entry) {
        entry.setChainID(CHAIN_ID);

        RequestBody requestBody = RequestBody.create(JSON_MEDIA, JSON.toJSON(entry).toString());
        Request request = new Request.Builder()
                .url(BLOCK_COMMIT)
                .post(requestBody)
                .build();
        try {
            Response response = client.newCall(request).execute();

            return JSON.parseObject(response.body().string(), EntryCommit.class);
        } catch (IOException e) {
            LOG.error("can't get response of " + BLOCK_COMMIT);
        }

        return null;
    }

    public void commit(EntryCommit entryCommit) {
        RequestBody requestBody = RequestBody.create(JSON_MEDIA, JSON.toJSON(entryCommit.getEntryCommit()).toString());
        Request request = new Request.Builder()
                .url(COMMIT_URL)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            LOG.info(response.code());
        } catch (IOException e) {
            LOG.error("can't get response of " + COMMIT_URL);
        }
    }

    public void reveal(EntryCommit entryCommit) {
        RequestBody requestBody = RequestBody.create(JSON_MEDIA, JSON.toJSON(entryCommit.getEntryReveal()).toString());
        Request request = new Request.Builder()
                .url(REVEAL_URL)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            LOG.info(response.code());
        } catch (IOException e) {
            LOG.error("can't get response of " + REVEAL_URL);
        }
    }

    public Entry findByHash(String hash) {
        Request request = new Request.Builder()
                .url(FIND_URL + hash)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            return JSON.parseObject(response.body().string(), Entry.class);
        } catch (IOException e) {
            LOG.error("can't get response of " + FIND_URL);
        }

        return null;
    }
}
