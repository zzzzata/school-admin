package io.renren.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64 {

	public Base64() {}

	protected static boolean isWhiteSpace(char octect) {
		return octect == ' ' || octect == '\r' || octect == '\n' || octect == '\t';
	}

	protected static boolean isPad(char octect) {
		return octect == '=';
	}

	protected static boolean isData(char octect) {
		return base64Alphabet[octect] != -1;
	}

	protected static boolean isBase64(char octect) {
		return isWhiteSpace(octect) || isPad(octect) || isData(octect);
	}

	public static String encode(byte binaryData[]) {
		return encode(binaryData, 0);
	}

	public static String encode(byte binaryData[], int maxCharsPerLine) {
		int numQuartetPerLine = 19;
		if (maxCharsPerLine == 0) numQuartetPerLine = 19;
		else if (maxCharsPerLine % 4 == 0) numQuartetPerLine = maxCharsPerLine / 4;
		else
			numQuartetPerLine = 19;
		if (binaryData == null) return null;
		int lengthDataBits = binaryData.length * 8;
		if (lengthDataBits == 0) return "";
		int fewerThan24bits = lengthDataBits % 24;
		int numberTriplets = lengthDataBits / 24;
		int numberQuartet = fewerThan24bits == 0 ? numberTriplets : numberTriplets + 1;
		int numberLines = (numberQuartet - 1) / numQuartetPerLine + 1;
		char encodedData[] = null;
		if (maxCharsPerLine == 0) encodedData = new char[numberQuartet * 4];
		else
			encodedData = new char[numberQuartet * 4 + numberLines];
		byte k = 0;
		byte l = 0;
		byte b1 = 0;
		byte b2 = 0;
		byte b3 = 0;
		int encodedIndex = 0;
		int dataIndex = 0;
		int i = 0;
		for (int line = 0; line <numberLines - 1; line++) {
			for (int quartet = 0; quartet <numQuartetPerLine; quartet++) {
				b1 = binaryData[dataIndex++];
				b2 = binaryData[dataIndex++];
				b3 = binaryData[dataIndex++];
				l = (byte) (b2 & 0xf);
				k = (byte) (b1 & 3);
				byte val1 = (b1 & 0xffffff80) != 0 ? (byte) (b1>> 2 ^ 0xc0) : (byte) (b1>> 2);
				byte val2 = (b2 & 0xffffff80) != 0 ? (byte) (b2>> 4 ^ 0xf0) : (byte) (b2>> 4);
				byte val3 = (b3 & 0xffffff80) != 0 ? (byte) (b3>> 6 ^ 0xfc) : (byte) (b3>> 6);
				encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
				encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | k <<4];
				encodedData[encodedIndex++] = lookUpBase64Alphabet[l <<2 | val3];
				encodedData[encodedIndex++] = lookUpBase64Alphabet[b3 & 0x3f];
				i++;
			}

			if (maxCharsPerLine != 0) encodedData[encodedIndex++] = '\n';
		}

		for (; i <numberTriplets; i++) {
			b1 = binaryData[dataIndex++];
			b2 = binaryData[dataIndex++];
			b3 = binaryData[dataIndex++];
			l = (byte) (b2 & 0xf);
			k = (byte) (b1 & 3);
			byte val1 = (b1 & 0xffffff80) != 0 ? (byte) (b1>> 2 ^ 0xc0) : (byte) (b1>> 2);
			byte val2 = (b2 & 0xffffff80) != 0 ? (byte) (b2>> 4 ^ 0xf0) : (byte) (b2>> 4);
			byte val3 = (b3 & 0xffffff80) != 0 ? (byte) (b3>> 6 ^ 0xfc) : (byte) (b3>> 6);
			encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | k <<4];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[l <<2 | val3];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[b3 & 0x3f];
		}

		if (fewerThan24bits == 8) {
			b1 = binaryData[dataIndex];
			k = (byte) (b1 & 3);
			byte val1 = (b1 & 0xffffff80) != 0 ? (byte) (b1>> 2 ^ 0xc0) : (byte) (b1>> 2);
			encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[k <<4];
			encodedData[encodedIndex++] = '=';
			encodedData[encodedIndex++] = '=';
		} else if (fewerThan24bits == 16) {
			b1 = binaryData[dataIndex];
			b2 = binaryData[dataIndex + 1];
			l = (byte) (b2 & 0xf);
			k = (byte) (b1 & 3);
			byte val1 = (b1 & 0xffffff80) != 0 ? (byte) (b1>> 2 ^ 0xc0) : (byte) (b1>> 2);
			byte val2 = (b2 & 0xffffff80) != 0 ? (byte) (b2>> 4 ^ 0xf0) : (byte) (b2>> 4);
			encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | k <<4];
			encodedData[encodedIndex++] = lookUpBase64Alphabet[l <<2];
			encodedData[encodedIndex++] = '=';
		}
		if (maxCharsPerLine != 0) encodedData[encodedIndex] = '\n';
		return new String(encodedData);
	}

	public static byte[] decode(String encoded) {
		if (encoded == null) return null;
		char base64Data[] = encoded.toCharArray();
		int len = removeWhiteSpace(base64Data);
		if (len % 4 != 0) return null;
		int numberQuadruple = len / 4;
		if (numberQuadruple == 0) return new byte[0];
		byte decodedData[] = null;
		byte b1 = 0;
		byte b2 = 0;
		byte b3 = 0;
		byte b4 = 0;
		char d1 = '\0';
		char d2 = '\0';
		char d3 = '\0';
		char d4 = '\0';
		int i = 0;
		int encodedIndex = 0;
		int dataIndex = 0;
		decodedData = new byte[numberQuadruple * 3];
		for (; i <numberQuadruple - 1; i++) {
			if (!isData(d1 = base64Data[dataIndex++]) || !isData(d2 = base64Data[dataIndex++]) || !isData(d3 = base64Data[dataIndex++])
					|| !isData(d4 = base64Data[dataIndex++])) return null;
			b1 = base64Alphabet[d1];
			b2 = base64Alphabet[d2];
			b3 = base64Alphabet[d3];
			b4 = base64Alphabet[d4];
			decodedData[encodedIndex++] = (byte) (b1 <<2 | b2>> 4);
			decodedData[encodedIndex++] = (byte) ((b2 & 0xf) <<4 | b3>> 2 & 0xf);
			decodedData[encodedIndex++] = (byte) (b3 <<6 | b4);
		}

		if (!isData(d1 = base64Data[dataIndex++]) || !isData(d2 = base64Data[dataIndex++])) return null;
		b1 = base64Alphabet[d1];
		b2 = base64Alphabet[d2];
		d3 = base64Data[dataIndex++];
		d4 = base64Data[dataIndex++];
		if (!isData(d3) || !isData(d4)) {
			if (isPad(d3) && isPad(d4)) if ((b2 & 0xf) != 0) {
				return null;
			} else {
				byte tmp[] = new byte[i * 3 + 1];
				System.arraycopy(decodedData, 0, tmp, 0, i * 3);
				tmp[encodedIndex] = (byte) (b1 <<2 | b2>> 4);
				return tmp;
			}
			if (!isPad(d3) && isPad(d4)) {
				b3 = base64Alphabet[d3];
				if ((b3 & 3) != 0) {
					return null;
				} else {
					byte tmp[] = new byte[i * 3 + 2];
					System.arraycopy(decodedData, 0, tmp, 0, i * 3);
					tmp[encodedIndex++] = (byte) (b1 <<2 | b2>> 4);
					tmp[encodedIndex] = (byte) ((b2 & 0xf) <<4 | b3>> 2 & 0xf);
					return tmp;
				}
			} else {
				return null;
			}
		} else {
			b3 = base64Alphabet[d3];
			b4 = base64Alphabet[d4];
			decodedData[encodedIndex++] = (byte) (b1 <<2 | b2>> 4);
			decodedData[encodedIndex++] = (byte) ((b2 & 0xf) <<4 | b3>> 2 & 0xf);
			decodedData[encodedIndex++] = (byte) (b3 <<6 | b4);
			return decodedData;
		}
	}

	protected static int removeWhiteSpace(char data[]) {
		if (data == null) return 0;
		int newSize = 0;
		int len = data.length;
		for (int i = 0; i <len; i++)
			if (!isWhiteSpace(data[i])) data[newSize++] = data[i];

		return newSize;
	}

	private static final byte base64Alphabet[];

	private static final char lookUpBase64Alphabet[];

	static {
		base64Alphabet = new byte[255];
		lookUpBase64Alphabet = new char[64];
		int i;
		for (i = 0; i <255; i++)
			base64Alphabet[i] = -1;

		for (i = 90; i>= 65; i--)
			base64Alphabet[i] = (byte) (i - 65);

		for (i = 122; i>= 97; i--)
			base64Alphabet[i] = (byte) ((i - 97) + 26);

		for (i = 57; i>= 48; i--)
			base64Alphabet[i] = (byte) ((i - 48) + 52);

		base64Alphabet[43] = 62;
		base64Alphabet[47] = 63;
		for (i = 0; i <= 25; i++)
			lookUpBase64Alphabet[i] = (char) (65 + i);

		i = 26;
		for (int j = 0; i <= 51; j++) {
			lookUpBase64Alphabet[i] = (char) (97 + j);
			i++;
		}

		i = 52;
		for (int j = 0; i <= 61; j++) {
			lookUpBase64Alphabet[i] = (char) (48 + j);
			i++;
		}

		lookUpBase64Alphabet[62] = '+';
		lookUpBase64Alphabet[63] = '/';
	}
	
	
	/*
	   * 加密
	   * 1.构造密钥生成器
	   * 2.根据ecnodeRules规则初始化密钥生成器
	   * 3.产生密钥
	   * 4.创建和初始化密码器
	   * 5.内容加密
	   * 6.返回字符串
	   */
	    public static String AESEncode(String encodeRules,String content){
	        try {
	            //1.构造密钥生成器，指定为AES算法,不区分大小写
	            KeyGenerator keygen=KeyGenerator.getInstance("AES");
	            //2.根据ecnodeRules规则初始化密钥生成器
	            //生成一个128位的随机源,根据传入的字节数组
	            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
	              //3.产生原始对称密钥
	            SecretKey original_key=keygen.generateKey();
	              //4.获得原始对称密钥的字节数组
	            byte [] raw=original_key.getEncoded();
	            //5.根据字节数组生成AES密钥
	            SecretKey key=new SecretKeySpec(raw, "AES");
	              //6.根据指定算法AES自成密码器
	            Cipher cipher=Cipher.getInstance("AES");
	              //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
	            cipher.init(Cipher.ENCRYPT_MODE, key);
	            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
	            byte [] byte_encode=content.getBytes("utf-8");
	            //9.根据密码器的初始化方式--加密：将数据加密
	            byte [] byte_AES=cipher.doFinal(byte_encode);
	          //10.将加密后的数据转换为字符串
	            //这里用Base64Encoder中会找不到包
	            //解决办法：
	            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
	            String AES_encode=new String(new BASE64Encoder().encode(byte_AES));
	          //11.将字符串返回
	            return AES_encode;
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        } catch (NoSuchPaddingException e) {
	            e.printStackTrace();
	        } catch (InvalidKeyException e) {
	            e.printStackTrace();
	        } catch (IllegalBlockSizeException e) {
	            e.printStackTrace();
	        } catch (BadPaddingException e) {
	            e.printStackTrace();
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        
	        //如果有错就返加nulll
	        return null;         
	    }
	    /*
	     * 解密
	     * 解密过程：
	     * 1.同加密1-4步
	     * 2.将加密后的字符串反纺成byte[]数组
	     * 3.将加密内容解密
	     */
	    public static String AESDncode(String encodeRules,String content){
	        try {
	            //1.构造密钥生成器，指定为AES算法,不区分大小写
	            KeyGenerator keygen=KeyGenerator.getInstance("AES");
	            //2.根据ecnodeRules规则初始化密钥生成器
	            //生成一个128位的随机源,根据传入的字节数组
	            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
	              //3.产生原始对称密钥
	            SecretKey original_key=keygen.generateKey();
	              //4.获得原始对称密钥的字节数组
	            byte [] raw=original_key.getEncoded();
	            //5.根据字节数组生成AES密钥
	            SecretKey key=new SecretKeySpec(raw, "AES");
	              //6.根据指定算法AES自成密码器
	            Cipher cipher=Cipher.getInstance("AES");
	              //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
	            cipher.init(Cipher.DECRYPT_MODE, key);
	            //8.将加密并编码后的内容解码成字节数组
	            byte [] byte_content= new BASE64Decoder().decodeBuffer(content);
	            /*
	             * 解密
	             */
	            byte [] byte_decode=cipher.doFinal(byte_content);
	            String AES_decode=new String(byte_decode,"utf-8");
	            return AES_decode;
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        } catch (NoSuchPaddingException e) {
	            e.printStackTrace();
	        } catch (InvalidKeyException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (IllegalBlockSizeException e) {
	            e.printStackTrace();
	        } catch (BadPaddingException e) {
	            e.printStackTrace();
	        }
	        
	        //如果有错就返加nulll
	        return null;         
	    }
	
	
	
}

