import static org.junit.Assert.*;

import org.junit.Test;


public class HaiCollection_minus_test {
	Hai.Type SOUZU = Hai.Type.SOUZU;
	Hai.Type MANZU = Hai.Type.MANZU;
	Hai.Type PINZU = Hai.Type.PINZU;
	Hai.Type SANGEN = Hai.Type.SANGEN;
	Hai.Type KAZE = Hai.Type.KAZE;
	@Test
	public void test() {
		HaiCollection h = new HaiCollection();
		h.add(new Hai(1,SOUZU));
		h.add(new Hai(3,MANZU));
		h.add(new Hai(2,PINZU));
		HaiCollection h2 = new HaiCollection();
		h2.add(new Hai(1,SOUZU));
		System.out.println(h.minus(h2));
	}

}
