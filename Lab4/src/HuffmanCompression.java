import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.regex.Pattern;

//This program takes a text file and outputs two files, the text file encoded, then the text file decoded from the encoding
//Note that as a result, you do not need to store and retrieve the HuffmanTree from file, which would normally be required
public class HuffmanCompression
{
	private final File file;
	private HuffmanTree<Character> tree;
	
	public HuffmanCompression(final String filename) throws IOException
	{
		this(new File(filename));
	}

	public HuffmanCompression(final File file) throws IOException
	{
		if(!file.exists() || !file.isFile())
		{
			throw new IllegalArgumentException("Must be a valid file");
		}
		this.file = file;
		this.tree = new HuffmanTree<>(this.takeCount());
	}
	
	public void encode(final String inputFilename, final String outputFilename) throws IOException
	{
		this.encode(new File(inputFilename), new File(outputFilename));
	}

	public void encode(final File inputFile, final File outputFile) throws IOException
	{
		if(!inputFile.exists() || !inputFile.isFile())
		{
			throw new IllegalArgumentException("Input file must be a valid file");
		}
		
		final FileReader reader = new FileReader(inputFile);
		final LinkedList<Character> characters = new LinkedList<>();
		int charInt = reader.read();
		while(charInt != -1)
		{
			characters.add(Character.valueOf((char)charInt));
			charInt = reader.read();
		}
		reader.close();
		
		final Integer[] encoding = this.tree.encode(characters.toArray(new Character[0]));
		
		final DataOutputStream writer = new DataOutputStream(new FileOutputStream(outputFile));
		for(Integer i : encoding)
		{
			writer.writeInt(i);
		}
		writer.flush();
		writer.close();
	}

	public void decode(final String inputFilename, final String outputFilename) throws IOException
	{
		this.decode(new File(inputFilename), new File(outputFilename));
	}
	
	public void decode(final File inputFile, final File outputFile) throws IOException
	{
		if(!inputFile.exists() || !inputFile.isFile())
		{
			throw new IllegalArgumentException("Input file must be a valid file");
		}

		final DataInputStream reader = new DataInputStream(new FileInputStream(inputFile));
		final LinkedList<Integer> binaryRepresentation = new LinkedList<>();
		try
		{
			while(true)
			{
				binaryRepresentation.add(reader.readInt());
			}
		}
		catch(EOFException e)
		{
		}

		reader.close();
		
		final Object[] decoding = this.tree.decode(binaryRepresentation.toArray(new Integer[0]));
		StringBuilder sb = new StringBuilder(decoding.length);
		for(int i = 0; i < decoding.length; i++)
		{
			if(decoding[i] == null)
			{
				continue;
			}
			sb.append(((Character)decoding[i]).charValue());
		}
		final char[] decodingPrimitive = sb.toString().toCharArray();
		
		final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputFile));
		writer.write(decodingPrimitive, 0, decodingPrimitive.length);
		writer.flush();
		writer.close();
	}

	private final Hashtable<Character,Integer> takeCount() throws IOException
	{
		final FileReader reader = new FileReader(this.file);
		final Hashtable<Character,Integer> count = new Hashtable<>();
		int charInt = reader.read();
		while(charInt != -1)
		{
			final Character c = Character.valueOf((char)charInt);
			if(!count.containsKey(c))
			{
				count.put(c,1);
			}
			else
			{
				count.put(c,count.get(c)+1);
			}
			
			charInt = reader.read();
		}
		reader.close();

		return count;
	}

	public static void main(String[] args)
	{
		if(args.length == 0)
		{
			System.out.println("Requires an input filename...");
			return;
		}
		final String inputFilename = args[0];
		final String outputFilename = inputFilename.split(Pattern.quote("."))[0] + ".encode";
		final String decodedFilename = inputFilename.split(Pattern.quote("."))[0] + ".decode";
		try
		{
			final HuffmanCompression hc = new HuffmanCompression(inputFilename);
			hc.encode(inputFilename, outputFilename);
			hc.decode(outputFilename, decodedFilename);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
