package com.noone.blockchain;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Block {

    private static Logger logger = Logger.getLogger(Block.class.getName());

    private String hash;
    private String previousHash;
    private String data;
    private long timeStamp;
    private int nonce;

    public Block(String data, String previousHash, long timeStamp) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.hash = calculateBlockHash();
    }

    public String mineBlock(int prefix) {
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        while (!hash.substring(0, prefix)
            .equals(prefixString)) {
            nonce++;
            hash = calculateBlockHash();
        }
        return hash;
    }

    public String calculateBlockHash() {
        String dataToHash = previousHash + timeStamp + nonce + data;
        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }
        StringBuilder buffer = new StringBuilder();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

    public String getHash() {
        return this.hash;
    }

    public String getPreviousHash() {
        return this.previousHash;
    }

    public void setData(String data) {
        this.data = data;
    }
}
