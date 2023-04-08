package com.ckey.decrypt.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class CompuToken {
    static Mac sha1_HMAC;
    private Date Basedate;
    private String token = "";
    private String time = "2007/01/01 00:00:00";
    private Date Nowdate = new Date();

    public CompuToken() {
        this.Nowdate.getTime();
        if (sha1_HMAC == null) {
            try {
                sha1_HMAC = Mac.getInstance("HmacSHA1");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    public String TokenComput(String str, byte[] bArr, Date date, boolean z) {
        try {
            this.token = GetPasswd2(str, bArr, date, z);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.token;
    }

    public String TokenComput(String str, byte[] bArr, boolean z) {
        try {
            Date date = new Date();
            this.token = GetPasswd2(str, bArr, date, z);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.token;
    }

    public String TokenComputNext(String str, byte[] bArr, boolean z) {
        try {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.MINUTE, 1);
            Date date = now.getTime();
            this.token = GetPasswd2(str, bArr, date, z);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.token;
    }

    public String TokenComput(String str, byte[] bArr, Date date) {
        try {
            this.token = GetPasswd(str, bArr, date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.token;
    }

    public static byte[] Comput0ath(byte[] bArr, byte[] bArr2) {
        try {
            if (sha1_HMAC == null) {
                try {
                    sha1_HMAC = Mac.getInstance("HmacSHA1");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
            sha1_HMAC.init(new SecretKeySpec(bArr, "HmacSHA1"));
            return sha1_HMAC.doFinal(bArr2);
        } catch (InvalidKeyException e2) {
            throw new UnsupportedOperationException(e2);
        }
    }

    public String GetPasswd2(String str, byte[] bArr, Date date, boolean z) throws ParseException {
        long j;
        long time = date.getTime();
        if (z) {
            j = (time / 1000) / 30;
        } else {
            j = (time / 1000) / 60;
        }
        byte[] bArr2 = new byte[8];
        for (int length = bArr2.length - 1; length >= 0; length--) {
            bArr2[length] = (byte) (255 & j);
            j >>= 8;
        }
        byte[] Comput0ath = Comput0ath(bArr, bArr2);
        int i = Comput0ath[Comput0ath.length - 1] & 15;
        if (31 < Comput0ath.length - 4) {
            i = 31;
        }
        String valueOf = String.valueOf(((((Comput0ath[i + 2] & 255) << 8) | (((Comput0ath[i] & Byte.MAX_VALUE) << 24) | ((Comput0ath[i + 1] & 255) << 16))) | (Comput0ath[i + 3] & 255)) % 1000000);
        while (valueOf.length() < 6) {
            valueOf = "0" + valueOf;
        }
        return valueOf;
    }

    public String GetPasswd(String str, byte[] bArr, Date date) throws ParseException {
        long time = (date.getTime() / 1000) / 60;
        byte[] bArr2 = new byte[8];
        for (int length = bArr2.length - 1; length >= 0; length--) {
            bArr2[length] = (byte) (255 & time);
            time >>= 8;
        }
        byte[] Comput0ath = Comput0ath(bArr, bArr2);
        int i = Comput0ath[Comput0ath.length - 1] & 15;
        if (31 < Comput0ath.length - 4) {
            i = 31;
        }
        String valueOf = String.valueOf(((((Comput0ath[i + 2] & 255) << 8) | (((Comput0ath[i] & Byte.MAX_VALUE) << 24) | ((Comput0ath[i + 1] & 255) << 16))) | (Comput0ath[i + 3] & 255)) % 1000000);
        while (valueOf.length() < 6) {
            valueOf = "0" + valueOf;
        }
        return valueOf;
    }
}