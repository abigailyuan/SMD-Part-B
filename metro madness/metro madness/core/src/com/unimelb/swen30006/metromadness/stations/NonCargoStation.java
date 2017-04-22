/**
 * 
 */
package com.unimelb.swen30006.metromadness.stations;

import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.trains.BigCargoTrain;
import com.unimelb.swen30006.metromadness.trains.SmallCargoTrain;
import com.unimelb.swen30006.metromadness.trains.Train;

/**
 * @author 
 *
 */
public class NonCargoStation extends ActiveStation {

	/**
	 * @param x
	 * @param y
	 * @param router
	 * @param name
	 * @param maxPax
	 */
	public NonCargoStation(float x, float y, PassengerRouter router, String name, int maxPax) {
		super(x, y, router, name, maxPax);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canEnter(Train t) {
		// Only cargo train can enter
		if (!( t instanceof SmallCargoTrain || t instanceof BigCargoTrain) ) {
			return trains.size() < PLATFORMS;
		}
		return false;
	}
}
