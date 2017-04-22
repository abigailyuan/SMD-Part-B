/**
 * 
 */
package com.unimelb.swen30006.metromadness.stations;

import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.tracks.Line;

/**
 * @author 
 *
 */
public class CargoStation extends ActiveStation {

	/**
	 * @param x
	 * @param y
	 * @param router
	 * @param name
	 * @param maxPax
	 */
	public CargoStation(float x, float y, PassengerRouter router, String name, float maxPax) {
		super(x, y, router, name, maxPax);

	}
	
	

}
