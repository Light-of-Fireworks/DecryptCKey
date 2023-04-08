package com.ckey.decrypt;

import com.ckey.decrypt.pojo.CKeyToken;
import com.ckey.decrypt.utils.AESUtils;
import com.ckey.decrypt.utils.CompuToken;
import com.ckey.decrypt.utils.LoadToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class DecryptApplicationTests {

    @Autowired
    private LoadToken loadToken;

    @Test
    void contextLoads() {
        // 加载ckey
        CKeyToken load = loadToken.load("ckey.xialiang");
        // 解密token
        String decrypt = AESUtils.decrypt(AESUtils.DECRYPT_STR1, AESUtils.DECRYPT_STR2, load.getToken());
        // 解码为数组
        byte[] hex2Bytes = AESUtils.hex2Bytes(decrypt);
        // HmacSHA1算法认证生成令牌
        CompuToken compuToken = new CompuToken();
        String key = compuToken.TokenComput("", hex2Bytes, false);
        // 预测下一个key
        String nextKey = compuToken.TokenComputNext("", hex2Bytes, false);
        System.out.println("ckey:"+load.getCkey());
        System.out.println("token:"+load.getToken());
        System.out.println("当前时间段令牌:"+key);
        System.out.println("下一个时间段令牌:"+nextKey);
    }

}
