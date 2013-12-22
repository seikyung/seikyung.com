
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * 
 * @author Seikyung Jung
 *
 */
public class VByteCodec {

	/**
	 * Students: explain this method
	 * @param input
	 * @param output
	 */
	public static void encode (int[] input, ByteBuffer output) {
		for (int i : input) {
			while (i >= 128) {
				output.put((byte) (i & 0x7f));
				i >>>= 7;
			}
			output.put((byte) (i | 0x80));
		}
	}
	
	/**
	 * Students: explain this method
	 * @param input
	 * @param output
	 */
	public static void decode (byte[] input, IntBuffer output) {
		for (int i = 0; i < input.length; i++) {
			int position = 0;
			int result = ((int) input[i] & 0x7f);
			
			while ( (input[i] & 0x80) == 0) {
				i ++;
				position ++;
				int unsignedByte = ((int)input[i] & 0x7f);
				result |= (unsignedByte << (7*position));
			}
			output.put(result);
		}
	}

	/**
	 * 
	 * @param k
	 * @return number of bytes needed in v-byte encoding
	 */
	public static int bytes(int k) {
		if (k < 128) return 1;
		if (k < 16384) return 2;
		if (k < 2097152) return 3;
		if (k < 268435456) return 4;
		return 0;
	}
	/**
	 * 
	 * @param numbers
	 * @return sum of total bytes needed
	 */
	public static int bytes(int[] numbers) {
		int sum = 0;
		for (int k : numbers)
			sum += bytes(k);
		return sum;
	}
	
	/**
	 * This is to avoid printing true or false 
	 * @param b
	 * @return
	 */
	public static int bit(boolean b) {
		return (b) ? 1 : 0;
	}
	
	/**
	 * This is for bytes
	 * @param input
	 */
	public static void printBits(byte input) {
		for (int i = 128; i > 0; i >>= 1) {
			System.out.print(bit((input & i) == i));
		}
		System.out.println();
	}
	
	/**
	 * This is for integers
	 * @param input
	 */
	public static void printBits(int input) {
		for (long i = 4294967296L; i > 0; i >>= 1) {
			System.out.print(bit((input & i) == i));
		}
		System.out.println();
	}

	public static void main(String[] args) {
		// Original numbers, you can change these.
		int[] numbers = new int[] {
			130, 1345134, 12344, 2
		};
		
		System.out.println("Original numbers");
		for (int i : numbers) {
			System.out.println(i);
		}
		System.out.println();
		
		// allocate proper bytes
		ByteBuffer buf = ByteBuffer.allocate(bytes(numbers));
		// encode
		encode(numbers, buf);

		// print out encoded numbers
		System.out.println("Numbers encoded into bytes");
		for (byte b : buf.array()) {
			printBits(b);
		}
		System.out.println();
		
		// allocate proper length of integer array
		IntBuffer intbuf = IntBuffer.allocate(numbers.length);
		// Decode bytes
		decode(buf.array(), intbuf);

		// print out decoded numbers
		System.out.println("Decoded numbers from bytes");
		for (int i : intbuf.array()) {
			System.out.println(i);
		}
	}

}

