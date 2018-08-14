package net.minecraftforge.gradle.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.io.IOUtils;

//These are all standard hashing functions the JRE is REQUIRED to have, so add a nice factory that doesnt require catching annoying exceptions;
public enum HashFunction {
    MD5("md5"),
    SHA1("SHA-1"),
    SHA256("SHA256");

    private String algo;
    private HashFunction(String algo) {
        this.algo = algo;
    }

    public MessageDigest get() {
        try {
            return MessageDigest.getInstance(algo);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e); //Never happens
        }
    }

    public String hash(File file) throws IOException {
        try (FileInputStream fin = new FileInputStream(file)) {
            return hash(fin);
        }
    }

    public String hash(String data) {
        return hash(data.getBytes(StandardCharsets.UTF_8));
    }

    public String hash(InputStream stream) throws IOException {
        return hash(IOUtils.toByteArray(stream));
    }

    public String hash(byte[] data) {
        return new BigInteger(1, get().digest(data)).toString(16);
    }
}
