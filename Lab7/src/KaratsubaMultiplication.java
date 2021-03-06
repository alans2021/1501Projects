import java.math.BigInteger;

import java.util.Random;

//cannot use BigInteger predefined methods for multiplication
//cannot use Strings except in computing appropriate exponent
public class KaratsubaMultiplication
{
	private static final BigInteger MAX_INT_VALUE = BigInteger.valueOf(Integer.MAX_VALUE);
	
	public static BigInteger karatsuba(final BigInteger factor0, final BigInteger factor1, final int base)
	{
		if(factor0 == null || factor1 == null)
			throw new IllegalArgumentException("Factors may not be null");
		if(base <= 1)
			throw new IllegalArgumentException("Base cannot be less than 2");
		if(factor0.equals(0) || factor1.equals(0))
			return BigInteger.ZERO;
		if(factor0.equals(1))
			return factor1;
		if(factor1.equals(1))
			return factor0;
		//base cases

		if(factor0.compareTo(MAX_INT_VALUE) < 0 && factor1.compareTo(MAX_INT_VALUE) < 0) {
			long first = factor0.longValue();
			long second = factor1.longValue();
			return BigInteger.valueOf(first * second);
		}

		int N = Math.max(factor0.bitLength(), factor1.bitLength());

		N = (N / 2) + (N % 2);

		BigInteger b = factor0.shiftRight(N);
		BigInteger a = factor0.subtract(b.shiftLeft(N));
		BigInteger d = factor1.shiftRight(N);
		BigInteger c = factor1.subtract(d.shiftLeft(N));

		BigInteger ac    = karatsuba(a, c, base);
		BigInteger bd    = karatsuba(b, d, base);
		BigInteger abcd  = karatsuba(a.add(b), c.add(d), base);

		return ac.add(abcd.subtract(ac).subtract(bd).shiftLeft(N)).add(bd.shiftLeft(2*N));
	}

	public static void main(String[] args)
	{
		//test cases
		if(args.length < 3)
		{
			System.out.println("Need two factors and a base value as input");
			return;
		}
		BigInteger factor0 = null;
		BigInteger factor1 = null;
		final Random r = new Random();
		if(args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("rand") || args[0].equalsIgnoreCase("random"))
		{
			factor0 = new BigInteger(r.nextInt(100000), r);
			System.out.println("First factor : " + factor0.toString());
		}
		else
		{
			factor0 = new BigInteger(args[0]);
		}
		if(args[1].equalsIgnoreCase("r") || args[1].equalsIgnoreCase("rand") || args[1].equalsIgnoreCase("random"))
		{
			factor1 = new BigInteger(r.nextInt(100000), r);
			System.out.println("Second factor : " + factor1.toString());
		}
		else
		{
			factor1 = new BigInteger(args[1]);
		}
		final BigInteger result = karatsuba(factor0, factor1, Integer.parseInt(args[2]));
		System.out.println(result);
		System.out.println(result.equals(factor0.multiply(factor1)));
	}
}