import com.chilkatsoft.*;

public class ChilkatExample {

  static {
    try {
        System.loadLibrary("chilkat");
    } catch (UnsatisfiedLinkError e) {
      System.err.println("Native code library failed to load.\n" + e);
      System.exit(1);
    }
  }

  public static void main(String argv[])
  {
    // This example assumes the Chilkat API to have been previously unlocked.
    // See Global Unlock Sample for sample code.

    CkRsa rsa = new CkRsa();

    CkPrivateKey key = new CkPrivateKey();

    // Load an RSA private key from a file:
    boolean success = key.LoadAnyFormatFile("rsaPrivateKey.key","");
    if (success != true) {
        System.out.println(key.lastErrorText());
        return;
        }

    // Get the key as XML:
    String keyXml;
    keyXml = key.getXml();

    // We'll encrypt with the public key and decrypt with the private
    // key.  (It's also possible to do the reverse.)
    // Note: An RSA private key is composed of different parts internally: modulus, exponent, P, Q, etc.
    // An RSA public-key is a sub-set of the private key.
    // Therefore, when you have a private key, you really have
    // both public and private keys.
    success = rsa.ImportPublicKey(keyXml);
    if (success != true) {
        System.out.println(rsa.lastErrorText());
        return;
        }

    // Encrypt a VISA credit card number:
    // 1234-5678-0000-9999
    String ccNumber = "1234567800009999";

    boolean usePrivateKey = false;
    rsa.put_EncodingMode("base64");
    String encryptedStr = rsa.encryptStringENC(ccNumber,usePrivateKey);
    System.out.println("Encrypted:");
    System.out.println(encryptedStr);

    // Now decrypt:
    CkRsa rsaDecryptor = new CkRsa();

    rsaDecryptor.put_EncodingMode("base64");
    success = rsaDecryptor.ImportPrivateKey(keyXml);

    usePrivateKey = true;
    String decryptedStr = rsaDecryptor.decryptStringENC(encryptedStr,usePrivateKey);

    System.out.println("Decrypted:");
    System.out.println(decryptedStr);

    // Important: RSA encryption should only be used to encrypt small amounts of data.
    // It is typically used for encrypting symmetric encryption
    // keys such that a symmetric encryption algorithm, such as 
    // AES is then used to encrypt/decrypt bulk data
  }
}
