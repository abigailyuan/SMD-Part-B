package com.unimelb.swen30006.metromadness.passengers;

import java.util.ArrayList;
import java.util.Random;

import com.unimelb.swen30006.metromadness.Simulation;
import com.unimelb.swen30006.metromadness.mapping.Mapping;
import com.unimelb.swen30006.metromadness.stations.NonCargoStation;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

public class PassengerGenerator {
	
	// Random number generator
	static final private Random random = new Random(30006);
	
	// Passenger id generator
	static private int idGen = 1;
	
	
	// The station that passengers are getting on
	//public Station origin;
	//public String destination;
	// The line they are travelling on
	public ArrayList<Line> lines;
	
	// The max volume
	public float maxVolume;
	
	/*public PassengerGenerator(Station origin, ArrayList<Line> lines, float max){
		this.origin = origin;
		this.lines = lines;
		this.maxVolume = max;
	}*/

	
	public PassengerGenerator(ArrayList<Line> lines, float max){
		
		this.lines = lines;
		this.maxVolume = max;
	}
	
	public static Passenger[] generatePassengers(Station origin){
		int count = random.nextInt(4)+1;                      // ??????????
		Passenger[] passengers = new Passenger[count];
		for(int i=0; i<count; i++){
			passengers[i] = generatePassenger(origin, random);
		}
		return passengers;
	}
	
	public static Passenger generatePassenger(Station origin, Random random){
		// Pick a random station from the line
		Line l = Mapping.getStationLines(origin).get(random.nextInt(Mapping.getStationLines(origin).size()));
		int current_station = Mapping.getLineStations(l).indexOf(origin);
		
		boolean forward = random.nextBoolean();
		
		// If we are the end of the line then set our direction forward or backward
		if(current_station == 0){
			forward = true;
		} else if (current_station == Mapping.getLineStations(l).size()-1){
			forward = false;
		}
		
		// Find the station
		int index = 0;
		
		if (forward){
			index = random.nextInt(Mapping.getLineStations(l).size()-1-current_station) + current_station + 1;
		} else {
			index = current_station - 1 - random.nextInt(current_station);
		}
		Station destination = Mapping.getLineStations(l).get(index);
		
		//if origin is a cargo station, set the destination to a cargo station
		
		if(!(origin instanceof NonCargoStation)){
			int CargoNum = 0;
			int i = 0;
			ArrayList<Station> cargoStations = new ArrayList<Station>();
			for(i=0;i< Mapping.getLineStations(l).size();i++){
				if(!(Mapping.getLineStations(l).get(i) instanceof NonCargoStation)){
					cargoStations.add(Mapping.getLineStations(l).get(i));
				}
			}
			if(cargoStations.size() == 1){
				destination = origin;
			}else{
				int indexOrigin = cargoStations.indexOf(origin);
				if(forward){
					index = random.nextInt(cargoStations.size()-1-indexOrigin) + indexOrigin + 1;
				} else {
					index = indexOrigin - 1 - random.nextInt(indexOrigin);
				}
			}
			destination = cargoStations.get(index);
		}
		
		Passenger p = new Passenger(idGen++, random, origin.getName(), destination.getName());
		
		//if origin is a non-cargo station , set the cargo weight to 0.
		if(origin instanceof NonCargoStation){
			p.setCargo(0);
		}
		
		return p;
		
		// return this.s.generatePassenger(idGen++, random, destination);
	}
	
}
