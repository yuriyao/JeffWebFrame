/**
 *  Copyright 2013 HIT IBMTC
 *  All right reserved
 *  created on 2013-10-26 上午9:32:05 by jeff 
 *  This is open source by GPL
 */
package org.footoo.jeffwebframe.datastruct;

/**
 * @author jeff
 * byte数组的一些比较操作
 */
public class JWFByteCompare 
{
	/**
	 * 
	 * @param bases 被比较的串
	 */
	public JWFByteCompare(byte bases[])
	{
		if(bases == null)
			bases = new byte[0];
		this.bases = bases;
	}
	
	public boolean contains(byte words[])
	{
		return (indexOf(words) != -1);
	}
	
	/**
	 * 测试words是否以words开始（不理会大小写）
	 * @param words
	 * @return
	 */
	public boolean startWithNoCase(byte words[], int begin, int length)
	{
		if(words == null || begin < 0 || length < 0 || begin + length > words.length || length > bases.length)
			return false;
		for(int i = 0; i < length; i ++)
		{
			if(!compareNoCase(words[begin + i], bases[i]))
				return false;
		}
		return true;
	}
	
	public boolean startWithNoCase(byte words[], int begin)
	{
		if(words == null)
			return false;
		return startWithNoCase(words, begin, words.length - begin);
	}
	
	public boolean startWithNoCase(byte words[])
	{
		if(words == null)
			return false;
		return startWithNoCase(words, 0, words.length);
	}
	
	
	/**
	 * 获取从begin到begin+length的数据
	 * @param begin
	 * @param length
	 * @return
	 */
	public byte[] gets(int begin, int length)
	{
		byte ret[] = null;
		
		if(begin < 0 || begin > bases.length || length <= 0)
			return new byte[0];
		length = length < (bases.length - begin) ? length : (bases.length - begin);
		
		ret = new byte[length];
		System.arraycopy(bases, begin, ret, 0, length);
		return ret;
	}
	
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private boolean compareNoCase(byte a, byte b)
	{
		if(a >= 'A' || a <= 'Z')
			a += 'a' - 'A';
		if(b >= 'A' || b <= 'Z')
			b += 'a' - 'A';
		return a == b;
	}
	
	/**
	 * 获取从baseFrom为索引的bases的words的索引
	 * @param basesFrom
	 * @param words
	 * @return
	 */
	public int indexOf(int basesFrom, byte words[])
	{
		if(words == null || words.length == 0 || basesFrom < 0 || basesFrom >= bases.length || words.length > bases.length - basesFrom)
			return -1;
		//初始化KMP辅助函数
		int pi[] = kmpInit(words);
		int k = 0;
		//开始KMP算法
		for(int i = basesFrom; i < bases.length; i ++)
		{
			if(words[k] != bases[i])
			{
				k = pi[k];
			}
			else 
			{
				if(k == words.length - 1)
					return (i - words.length + 1);
				k ++;
			}
		}
		return -1;
	}
	
	/**
	 * 获取words在bases中，最开始字符串的索引
	 * @param words
	 */
	public int indexOf(byte words[])
	{
		if(words == null || words.length == 0 || words.length > bases.length)
			return -1;
		//初始化KMP辅助函数
		int pi[] = kmpInit(words);
		int k = 0;
		//开始KMP算法
		for(int i = 0; i < bases.length; i ++)
		{
			if(words[k] != bases[i])
			{
				k = pi[k];
			}
			else 
			{
				if(k == words.length - 1)
					return (i - words.length + 1);
				k ++;
			}
		}
		return -1;
	}
	
	/**
	 * 测试bases是否以words[begin...begin+length]结尾
	 * @param words
	 * @param begin
	 * @param length
	 * @return
	 */
	public boolean endWith(byte words[], int begin, int length)
	{
		if(words == null || words.length < begin + length || begin < 0 || length < 0 || length > bases.length)
			return false;
		for(int i = 0; i < length; i ++)
		{
			if(bases[bases.length - length + i] != words[begin + i])
				return false;
		}
		return true;
	}
	
	public boolean endWith(byte words[], int begin)
	{
		if(words == null)
			return false;
		return endWith(words, begin, words.length - begin);
	}
	
	public boolean endWith(byte words[])
	{
		if(words == null)
			return false;
		return endWith(words, 0, words.length);
	}
	
	/**
	 * 比较base是否以word[begin...begin+length)结尾
	 * @param base
	 * @param word
	 * @param begin
	 * @param length
	 * @return
	 */
	public static boolean endWith(byte base[], byte word[], int begin, int length)
	{
		if(word == null || base == null || begin < 0 || begin + length > word.length || base.length < length)
			return false;
		for(int i = begin; i < length; i ++)
		{
			if(base[base.length - length + i] != word[begin + i])
				return false;
		}
		return true;
	}
	
	/**
	 * 比较base[baseBegin...baseBegin+length]是否以word[wordBegin...wordBegin +wordLength]结尾
	 * @param base
	 * @param baseBegin
	 * @param baseLength
	 * @param word
	 * @param wordBegin
	 * @param wordLength
	 * @return
	 */
	public static boolean endWith(byte base[], int baseBegin, int baseLength, byte word[], int wordBegin, int wordLength)
	{
		if(base == null || baseBegin < 0 || baseBegin + baseLength > base.length || word == null ||
				wordBegin < 0 || wordLength + wordBegin > word.length || baseLength < wordBegin)
			return false;
		for(int i = 0; i < wordLength; i ++)
		{
			if(base[baseBegin + baseLength - wordLength + i] != word[i + wordBegin])
				return false;
		}
		return true;
	}
	
	/**
	 * 比较bases是否是以words[begin ... begin+length]开始
	 * @param words
	 * @return
	 */
	public boolean startWith(byte words[], int begin, int length)
	{
		if(words == null || begin < 0 || length < 0 || begin + length > words.length || length > bases.length)
			return false;
		for(int i = 0; i < length; i ++)
		{
			if(bases[i] != words[begin + i])
				return false;
		}
		return true;
	}
	
	public boolean startWith(byte words[], int begin)
	{
		if(words == null)
			return false;
		return startWith(words, begin, words.length - begin);
	}
	
	public boolean startWith(byte words[])
	{
		return startWith(words, 0, words.length);
	}
	
	/**
	 * bases的内容是否为words[begin...begin+length]
	 * @param words
	 * @param begin
	 * @param length
	 * @return
	 */
	public boolean contentIs(byte words[], int begin, int length)
	{
		if(words == null || length != bases.length || begin < 0 || length < 0|| begin + length > words.length)
			return false;
		for(int i = 0; i < words.length; i ++)
		{
			if(words[i] != bases[i + begin])
				return false;
		}
		return true;
	}
	
	public boolean contentIs(byte words[], int begin)
	{
		if(words == null)
			return false;
		return contentIs(words, begin, words.length - begin);
	}
	
	public boolean contentIs(byte words[])
	{
		if(words == null)
			return false;
		return contentIs(words, 0, words.length);
	}
	
	/**
	 * 初始化KMP算法的辅助函数数据
	 * @param words
	 * @return
	 */
	private static int[] kmpInit(byte words[])
	{
		int pi[] = new int[words.length];
		for(int i = 0; i < words.length; i ++)
		{
			pi[i] = 0;
			for(int k = 1; k <= i; k ++)
			{
				if(endWith(words, 0, i + 1, words, 0, k))
					pi[i] = k;
			}
		}
		return pi;
	}
	
	private byte bases[] = null;
	
	public static void main(String args[])
	{
		byte words[] = "hellohel".getBytes();
		byte bases[] = "bacbababhellohelababcabaab".getBytes();
		JWFByteCompare compare = new JWFByteCompare(bases);
		//int pi[] = kmpInit(words);
		//for(int i = 0; i < pi.length; i ++)
			//System.out.println(pi[i]);
		System.out.println("Hello");
		System.out.println("index:" + (new JWFByteCompare(bases)).indexOf(words));
		System.out.println("Hello");
		
		System.out.println(compare.startWith("bac".getBytes()));
		System.out.println(compare.endWith("caab".getBytes()));
	}
}
