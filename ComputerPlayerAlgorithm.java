import java.util.ArrayList;
import java.util.HashMap;

public class ComputerPlayerAlgorithm {

	private HaiCollection sutehai;
	private HaiCollection taatsu;
	private HaiCollection tehai;
	
	/**
	 * Constructor for Choosing a discard tile algorithm.
	 * @param _sutehai
	 * @param _taatsu
	 */
	public ComputerPlayerAlgorithm(HaiCollection tehai, HaiCollection _sutehai, HaiCollection _taatsu) {
		sutehai = _sutehai;
		taatsu = _taatsu;	
	}
	

	/**
	 * 
	 * @return HaiCollection that stores all the tiles that would complete the taatsu
	 * @throws IllegalTaatsuException
	 */
	public HaiCollection getWaitsMentsu() throws IllegalTaatsuException {
		HaiCollection waits = new HaiCollection();
		if(taatsu instanceof Penchan) {
			switch(taatsu.get(0).getNumber()) {
			case 1:
				waits.add(new Hai(3, taatsu.get(0).getType()));
				break;
			case 2:
				waits.add(new Hai(3, taatsu.get(0).getType()));
				break;
			case 8:
				waits.add(new Hai(7, taatsu.get(0).getType()));
				break;
			case 9:
				waits.add(new Hai(7, taatsu.get(0).getType()));
				break;
			default:
				throw new IllegalTaatsuException();
			}
		}else if(taatsu instanceof Kanchan) {
			waits.add(new Hai((taatsu.get(0).getNumber() + taatsu.get(1).getNumber())/2, taatsu.get(0).getType()));
		}else if(taatsu instanceof Ryanmen) {
			waits.add(new Hai((taatsu.get(0).getNumber() - 1), taatsu.get(0).getType()));
		}else if(taatsu instanceof Toitsu) {
			waits.add(taatsu.get(0));
		}else {
			throw new IllegalTaatsuException();
		}
		return waits;
	}
	
	/**
	 * 
	 * @return the HaiCollection that stores the tiles that makes ryanmen from the taatsu
	 */
	public HaiCollection getWaitsRyanmen() {
		HaiCollection ryanmenWait = new HaiCollection();
		if(taatsu instanceof Penchan) {
		}else if(taatsu instanceof Kanchan) {
			if(taatsu.get(0).getNumber() != 1) {
				ryanmenWait.add(new Hai(taatsu.get(0).getNumber(), taatsu.get(0).getType()));
			}
			if(taatsu.get(1).getNumber() != 9) {
				ryanmenWait.add(new Hai(taatsu.get(1).getNumber(), taatsu.get(1).getType()));
			}
		}else if(taatsu instanceof Toitsu) {
			if(taatsu.get(0).getNumber() > 2) {
				ryanmenWait.add(new Hai(taatsu.get(0).getNumber(), taatsu.get(0).getType()));
			}
			if(taatsu.get(0).getNumber() < 8) {
				ryanmenWait.add(new Hai(taatsu.get(0).getNumber(), taatsu.get(0).getType()));
			}
		}
		return ryanmenWait;
	}
	
	/**
	 * Counts the number of tiles that are not visible
	 * @return the number of tiles that are still not visible to make mentsu
	 * @throws IllegalTaatsuException
	 */
	public int countWaits() throws IllegalTaatsuException{
		HaiCollection mentsu_waits = getWaitsMentsu();
		int wait_count = mentsu_waits.size() * 4;

		for(int i = 0; i < mentsu_waits.size(); i++) {
			for(int j = 0; j <  sutehai.size();j++) {
				if(sutehai.get(j).equals(mentsu_waits.get(i))) {
					wait_count--;
				}
			}
			for(int j = 0; j < tehai.size(); j++) {
				if(tehai.get(j).equals(mentsu_waits.get(i))) {
					wait_count--;}
				}
		}
		return wait_count;
	}
	
	/**
	 * 
	 * @return the P = ....... thing.
	 */
	public float getProbability() {
		
	}
	
}
