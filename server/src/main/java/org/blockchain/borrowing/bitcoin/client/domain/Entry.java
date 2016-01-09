package org.blockchain.borrowing.bitcoin.client.domain;

import org.apache.commons.codec.binary.Hex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Entry {

    private String ChainID;
    private List<String> ExtIDs = new ArrayList<>(2);
    private String Content;

    public String getChainID() {
        return ChainID;
    }

    public void setChainID(String chainID) {
        ChainID = chainID;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public List<String> getExtIDs() {
        return ExtIDs;
    }

    public void setExtIDs(List<String> extIDs) {
        ExtIDs = extIDs;
    }

    public Entry toReadable() {
        Entry entry = new Entry();

        try {
            entry.setContent(new String(Hex.decodeHex(this.Content.toCharArray())));
            entry.setExtIDs(Arrays.asList(new String(Hex.decodeHex(this.ExtIDs.get(0).toCharArray())),
                    new String(Hex.decodeHex(this.ExtIDs.get(1).toCharArray()))));
            entry.setChainID(this.ChainID);
        } catch (Exception e) {

        }

        return entry;
    }
}
