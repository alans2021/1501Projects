
public class Substitute implements SymCipher{
    private byte[] key; //The input is added by 128, and that is the index. So, index 0 maps byte value -128 to the value
                        //stored in index 0
    private byte[] inverse; //If value 23 is stored in index 0 of the key array, add 128 + 23 = 151. Then, index 151
                            //in inverse array will store 0 (from key location) - 128 = -128.

    public Substitute(){
        key = new byte[256];
        inverse = new byte[256];
        int[] intermediate = new int[256];
        for(int i = 1; i < 257; i++){
            int index = (int) (Math.random() * 256); //Generate random index between 0 and 255
            while(intermediate[index] != 0) //If that index already has a value, generate another random index
                index = (int) (Math.random() * 256);
            intermediate[index] = i; //Place value of i into generated location
        }
        for(int i = 0; i < intermediate.length; i++)
            key[i] = (byte) (intermediate[i] - 129); //Subtract 129 from each location so all values are from -128 to 127

        for(int i = 0; i < key.length; i++){ //Create the inverse mapping
            int index = key[i] + 128; //Location is adding 128 to what's stored in key array
            inverse[index] = (byte) (i - 128); //What's placed in that location is the value of i subtracted by 128
        }
    }

    public Substitute(byte[] b){
        key = new byte[b.length];
        inverse = new byte[b.length];
        for(int i = 0; i < b.length; i++)
            key[i] = b[i];
        for(int i = 0; i < key.length; i++){
            int index = key[i] + 128;
            inverse[index] = (byte) (i - 128);
        }
    }
    public byte[] getKey(){
        return key;
    }

    public byte [] encode(String S){
        byte[] byteString = S.getBytes();
        for(int i = 0; i < byteString.length; i++){
            int location = byteString[i] + 128;
            byteString[i] = key[location]; //Each byte value in array gets mapped to different byte value based on array
        }
        return byteString;
    }

    // Decrypt the array of bytes and generate and return the corresponding String.
    public String decode(byte [] bytes){
        for(int i = 0; i < bytes.length; i++){
            int location = bytes[i] + 128;
            bytes[i] = inverse[location]; //Use inverse array to get original value
        }
        String str = new String(bytes);
        return str;
    }
}
