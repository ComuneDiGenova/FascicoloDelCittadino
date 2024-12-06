package it.liguriadigitale.ponmetro.portale.presentation.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AmtEncrypterUtil {
  private static final String SALT = "11d2a8b19dd70f0f3d5c8c0deef32c02";

  private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

  private static final int RIJNDAEL = 128;

  public static String encrypt(String input)
      throws NoSuchPaddingException,
          NoSuchAlgorithmException,
          InvalidAlgorithmParameterException,
          InvalidKeyException,
          BadPaddingException,
          IllegalBlockSizeException,
          UnsupportedEncodingException {

    SecretKeySpec secretKeySpec = new SecretKeySpec(SALT.getBytes(), "AES");

    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, generateIv());
    byte[] cipherText = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
    return Base64.getUrlEncoder().withoutPadding().encodeToString(cipherText);
  }

  public static String decrypt(String cipherText)
      throws NoSuchPaddingException,
          NoSuchAlgorithmException,
          InvalidAlgorithmParameterException,
          InvalidKeyException,
          BadPaddingException,
          IllegalBlockSizeException,
          UnsupportedEncodingException {

    Cipher cipher = Cipher.getInstance(ALGORITHM);
    SecretKeySpec secretKeySpec = new SecretKeySpec(SALT.getBytes(), "AES");
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, generateIv());
    byte[] plainText = cipher.doFinal(Base64.getUrlDecoder().decode(cipherText));
    return new String(plainText);
  }

  private static IvParameterSpec generateIv() {

    String vi = "eef8c1a6a1dc4672";
    byte[] iv = vi.getBytes();
    return new IvParameterSpec(iv);
  }
}
