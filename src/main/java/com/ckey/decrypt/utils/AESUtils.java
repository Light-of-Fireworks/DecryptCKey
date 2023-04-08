package com.ckey.decrypt.utils;//
// Decompiled by Jadx - 567ms
//

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AESUtils {
    protected static Cipher decryptCipher;

    public static String DECRYPT_STR1 = "T#s)STq~whp]b52G";
    public static String DECRYPT_STR2 = "Yw*M3^6JpV%0U@qk";

    public static String encrypt(String str, String str2, String str3) {
        byte[] bArr;
        try {
            bArr = encrypt(str.getBytes(StandardCharsets.US_ASCII), str2.getBytes(), str3.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            bArr = null;
        }
        return Base64.encodeToString(bArr, 0);
    }

    public static String decrypt(String str, String str2, String str3) {
        try {
            return new String(decrypt(str.getBytes(StandardCharsets.US_ASCII), str2.getBytes(), Base64.decode(str3, 0)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] encrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, 0, bArr.length, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(1, secretKeySpec, new IvParameterSpec(bArr2));
        return cipher.doFinal(bArr3, 0, bArr3.length);
    }

    protected static Cipher decryptCipher(byte[] bArr, byte[] bArr2) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, secretKeySpec, new IvParameterSpec(bArr2));
        return cipher;
    }

    private static byte[] decrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) throws Exception {
        if (decryptCipher == null) {
            decryptCipher = decryptCipher(bArr, bArr2);
        }
        return decryptCipher.doFinal(bArr3);
    }

    public static byte[] hex2Bytes(String str) {
        byte[] bArr = new byte[str.length() / 2];
        char[] charArray = str.toCharArray();
        int i = 0;
        int i2 = 0;
        while (i < charArray.length) {
            bArr[i2] = (byte) Integer.parseInt(new String(charArray, i, 2), 16);
            i += 2;
            i2++;
        }
        return bArr;
    }
}