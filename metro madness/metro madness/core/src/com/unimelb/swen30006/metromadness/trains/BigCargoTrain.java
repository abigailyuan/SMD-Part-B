/**
 * 
 */
package com.unimelb.swen30006.metromadness.trains;

import java.util.ArrayList;

import com.unimelb.swen30006.metromadness.mapping.Mapping;
import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

/**
 * @author 
 *
 */
public class BigCargoTrain extends BigPassengerTrain {

	/**
	 * @param trainLine
	 * @param start
	 * @param forward
	 * @param name
	 */
	
	public static final int BIG_CARGO_CAPACITY = 1000;
	private int currentCargoCapacity = 0;
	
	public BigCargoTrain(Line trainLine, Station start, boolean forward, String name) {
		super(trainLine, start, forward, name);
	}
	
	public void addCargo(int weight){
		this.currentCargoCapacity += weight;
	}

	public int getCargoWeight(){
		return this.currentCargoCapacity;
	}
	
	@Override
	public void embark(Passenger p) throws Exception{
		ArrayList<Passenger> passengersOnTrain = Mapping.getTrainPassengers(this);
		if(passengersOnTrain.size() > this.getMaxPassengers() || this.currentCargoCapacity > BIG_CARGO_CAPACITY){
			throw new Exception();
		}
		passengersOnTrain.add(p);
	}
	
}
