package net.atesu.atesvcdataapi.util.weixin;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AES加密
 * @author liuyazhuang
 *
 */
public class AES {

    public static boolean initialized = false;
    static Logger logger = LoggerFactory.getLogger(AES.class);

    /**
     * AES解密
     *
     * @param content
     *            密文
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchProviderException
     */
    public byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) throws InvalidAlgorithmParameterException {
        initialize();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            Key sKeySpec = new SecretKeySpec(keyByte, "AES");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// 初始化
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (NoSuchAlgorithmException e) {
            logger.error("小程序解密出错1", e);
        } catch (NoSuchPaddingException e) {
            logger.error("小程序解密出错2", e);
        } catch (InvalidKeyException e) {
            logger.error("小程序解密出错3", e);
        } catch (IllegalBlockSizeException e) {
            logger.error("小程序解密出错4", e);
        } catch (BadPaddingException e) {
            logger.error("小程序解密出错6", e);
        } catch (NoSuchProviderException e) {
            // TODO Auto-generated catch block
            logger.error("小程序解密出错7", e);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("小程序解密出错8", e);
        }
        return null;
    }

    public static void initialize() {
        if (initialized)
            return;
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }

    // 生成iv
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }
}
