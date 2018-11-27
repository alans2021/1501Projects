import java.security.SecureRandom;

public class Add128 implements SymCipher{

    private byte[] key;

    public Add128(){
        key = new byte[128];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(key); //Create random 128 byte array
    }

    public Add128(byte[] b){
        key = new byte[b.length];
        for(int i = 0; i < b.length; i++)
            key[i] = b[i];
    }
    public byte[] getKey(){
        return key;
    }

    public byte [] encode(String S){
        byte[] stringBytes = S.getBytes();
        for(int i = 0; i < stringBytes.length; i++){
            byte keyVal = key[i % 128];
            stringBytes[i] = (byte) (stringBytes[i] + keyVal); //Value at location is added by the key
        }
        return stringBytes;
    }

    // Decrypt the array of bytes and generate and return the corresponding String.
    public String decode(byte [] bytes){
        for(int i = 0; i < bytes.length; i++)
            bytes[i] = (byte) (bytes[i] - key[i % 128]);
        String str = new String(bytes);
        return str;
    }
}
