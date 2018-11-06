
import java.util.NoSuchElementException;
public class LZWmod {

    private static final int R = 256;        // number of input chars
    private static final int L = 4096;       // number of codewords = 2^W
    private static final int W = 12;         // codeword width

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
        char byteinput = BinaryStdIn.readChar(8);
        while(true) {
            try{
//                System.out.println(code + " ");
//                if(code == 3040)
//                    System.out.println("Testing");
                prefix.append(byteinput);
                int search = dict.searchPrefix(prefix);
                while(search == 2) {
                    prefix.append(BinaryStdIn.readChar(8));
                    search = dict.searchPrefix(prefix);
                }

                if(search == 1){
                    BinaryStdOut.write(dict.get(prefix), W);
                    byteinput = BinaryStdIn.readChar();
                    prefix.append(byteinput);
                }
                else{
                    byteinput = prefix.charAt(prefix.length() - 1);
                    prefix.deleteCharAt(prefix.length() - 1);
                    BinaryStdOut.write(dict.get(prefix), W);
                    prefix.append(byteinput);
                }

                if(code < L) {
                    dict.add(prefix, code);
                    code++;
                }
                prefix.delete(0, prefix.length());
            }

            catch(NoSuchElementException e){
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
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) st[i++] = val + s.charAt(0);
            val = s;
        }
        BinaryStdOut.close();
    }



    public static void main(String[] args) {
        if (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new RuntimeException("Illegal command line argument");

    }

}
