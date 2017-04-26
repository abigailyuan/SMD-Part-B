package com.unimelb.swen30006.metromadness.trains;

import java.util.ArrayList;

import com.unimelb.swen30006.metromadness.mapping.Mapping;
import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

public class SmallCargoTrain extends SmallPassengerTrain {
	
	public int SMALL_CARGO_CAPACITY = 200;
	private int current_cargo_capacity = 0;

	public SmallCargoTrain(Line trainLine, Station start, boolean forward, String name) {
		super(trainLine, start, forward, name);

	}
	
	public void addCargo(int weight){
		this.current_cargo_capacity += weight;
	}

	public int getCargoWeight(){
		return this.current_cargo_capacity;
	}
	
	@Override
	public void embark(Passenger p) throws Exception{
		ArrayList<Passenger> passengersOnTrain = Mapping.getTrainPassengers(this);
		if(passengersOnTrain.size() > this.getMaxPassengers() || this.current_cargo_capacity > SMALL_CARGO_CAPACITY){
			throw new Exception();
		}
		passengersOnTrain.add(p);
	}
}
