/**
 * 
 */
package com.unimelb.swen30006.metromadness.trains;

import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

/**
 * @author Tina
 *
 */
public class BigCargoTrain extends BigPassengerTrain {

	/**
	 * @param trainLine
	 * @param start
	 * @param forward
	 * @param name
	 */
	
	public int CARGO_CAPACITY = 1000;
	
	public BigCargoTrain(Line trainLine, Station start, boolean forward, String name) {
		super(trainLine, start, forward, name);
		// TODO Auto-generated constructor stub
	}

}
