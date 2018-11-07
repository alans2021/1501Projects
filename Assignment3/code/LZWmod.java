
import java.util.NoSuchElementException;
public class LZWmod {

    private static final int R = 256;        // number of input chars
    private static final int L = 65536;      // number of codewords = 2^16, max size
    private static int W = 9;         // codeword width, 9 at first

    public static void compress() {
        DLB<Integer> dict = new DLB<Integer>();
        for (int i = 0; i < R; i++) {
            char ascii = (char) i;
            StringBuilder s = new StringBuilder();
            s.append(ascii);
            dict.add(s, i);
        }
        int code = R+1;  // R is codeword for EOF

        StringBuilder prefix = new StringBuilder();
        char byteinput = BinaryStdIn.readChar(8); //Read character from input file
        while(true) {
            try{
                prefix.append(byteinput); //Add character read to prefix
                int search = dict.searchPrefix(prefix); //Search prefix in dictionary
                while(search == 2) { //That means still potential children of DLB
                    prefix.append(BinaryStdIn.readChar(8)); //So append another character from input file
                    search = dict.searchPrefix(prefix); //Search in dictionary
                }

                if(search == 1){ //Value equals 1 means this sequence exists
                    BinaryStdOut.write(dict.get(prefix), W); //Write that sequence via BinaryStdOut
                    byteinput = BinaryStdIn.readChar(); //Read another character
                    prefix.append(byteinput); //Append to prefix
                }
                else{ //Value equals 0 means sequence does not exist in dictionary
                    byteinput = prefix.charAt(prefix.length() - 1); //Save the last character
                    prefix.deleteCharAt(prefix.length() - 1); //Remove that last character from prefix
                    BinaryStdOut.write(dict.get(prefix), W); //Write remaining characters
                    prefix.append(byteinput); //Now put last character back
                }

                if(code < L) {
                    dict.add(prefix, code); //Add to dictionary
                    code++;
                    if( (Math.pow(2, W)) - code < .5)
                        W++;
                }
                prefix.delete(0, prefix.length());
            }

            catch(NoSuchElementException e){
                BinaryStdOut.write(dict.get(prefix), W);
                BinaryStdOut.write(R, W);
                BinaryStdOut.close();
                break;
            }
        }
    }


    public static void expand() {
        String[] st = new String[L];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R)
                break;
            String s = st[codeword];
            if (i == codeword)
                s = val + val.charAt(0);   // special case hack
            if (i < L)
                st[i++] = val + s.charAt(0);
            val = s;
            if (Math.pow(2, W) - (i + 1) < 0.1)
                W++;
        }
        BinaryStdOut.close();

    }



    public static void main(String[] args) {
        if (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new RuntimeException("Illegal command line argument");

    }

}
