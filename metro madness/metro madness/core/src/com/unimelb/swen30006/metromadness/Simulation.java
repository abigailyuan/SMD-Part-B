package com.unimelb.swen30006.metromadness;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.mapping.Mapping;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.trains.Train;
//import com.unimelb.swen30006.metromadness.Mapping;

public class Simulation {
	
	public ArrayList<Station> stations;
	public ArrayList<Line> lines;
	public ArrayList<Train> trains;
	//public static PassengerGenerator passengerGen;
	//public ArrayList<Passenger> passengers;
	//public static Mapping mappings;
	
	public Simulation(String fileName){
		// Create a map reader and read in the file
		MapReader m = new MapReader(fileName);
		m.process();
		
		//Create a mapping	
		//Simulation.mappings = new Mapping();		
		
		
		// Create a list of lines
		this.lines = new ArrayList<Line>();
		this.lines.addAll(m.getLines());
				
		// Create a list of stations
		this.stations = new ArrayList<Station>();
		this.stations.addAll(m.getStations());
		
		// Create a list of trains
		this.trains = new ArrayList<Train>();
		this.trains.addAll(m.getTrains());
		
		// Create a list of passengers
		// this.passengers = new ArrayList<Passenger>();
		//passengerGen = new PassengerGenerator();
		
	}
	
	
	// Update all the trains in the simulation
	public void update(){
		// Update all the trains
		for(Train t: this.trains){
			System.out.println("1" + t.name + "  "+ t.state);
			t.update(Gdx.graphics.getDeltaTime());
			System.out.println("2");
		}
		System.out.println("simulation updated.");
	}
	
	public void render(ShapeRenderer renderer){
		for(Line l: this.lines){
			l.render(renderer);
		}
		


		for(Train t: this.trains){
			t.render(renderer);
		}
		for(Station s: this.stations){
			s.render(renderer);
		}
	}
}