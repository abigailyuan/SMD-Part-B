package com.unimelb.swen30006.metromadness.mapping;

import java.util.ArrayList;
import java.util.HashMap;

import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.tracks.Track;

public class Mapping {
	
	private static HashMap<Line, ArrayList<Station>> lineStations = new HashMap<Line, ArrayList<Station>>();
	private static HashMap<Station, ArrayList<Line>> stationLines = new HashMap<Station, ArrayList<Line>>();
	private static HashMap<Line, ArrayList<Track>> lineTracks = new HashMap<Line, ArrayList<Track>>();
	private static HashMap<Station, ArrayList<Passenger>> stationPassengers = new HashMap<Station, ArrayList<Passenger>>();
	
	
	/*public Mapping(){
		
		lineStations = new HashMap<Line, ArrayList<Station>>();
		stationLines = new HashMap<Station, ArrayList<Line>>();
		lineTracks = new HashMap<Line, ArrayList<Track>>();
		stationPassengers = new HashMap<Station, ArrayList<Passenger>>();
		
	}*/
	
	public static ArrayList<Station> getLineStations(Line l){
		return lineStations.get(l);
	}
	public static ArrayList<Line> getStationLines(Station s){
		return stationLines.get(s);
	}
	public static ArrayList<Track> getLineTracks(Line l){
		return lineTracks.get(l);
	}
	public static ArrayList<Passenger> getStationPassnegers(Station s){
		return stationPassengers.get(s);
	}
	
	public static void addLineStations(Line l, Station s){
		//if the line has not been inserted in mapping
		if(!lineStations.containsKey(l)){
			//create ArrayList for l
			ArrayList<Station> stations = new ArrayList<Station>();
			stations.add(s);
			//insert
			lineStations.put(l, stations);
		}else{
			//insert
			lineStations.get(l).add(s);
		}
	}
	
	public static void addStationLines(Station s, Line l){
		//if the station has not been inserted in mapping
				if(!stationLines.containsKey(s)){
					//create ArrayList for l
					ArrayList<Line> lines = new ArrayList<Line>();
					lines.add(l);
					//insert
					stationLines.put(s, lines);
				}else{
					//insert
					stationLines.get(s).add(l);
				}
	}
	
	public static void addlineTracks(Line l, Track t){

		//if the line has not been inserted in mapping
				if(!lineTracks.containsKey(l)){
					//create ArrayList for l
					ArrayList<Track> tracks = new ArrayList<Track>();
					tracks.add(t);
					//insert
					lineTracks.put(l, tracks);
				}else{
					//insert
					lineTracks.get(l).add(t);
				}
	}
	public static void addStationPassengers(Station s, Passenger p){
		//if the station has not been inserted in mapping
		if(!stationPassengers.containsKey(s)){
			//create ArrayList for p
			ArrayList<Passenger> passengers = new ArrayList<Passenger>();
			passengers.add(p);
			//insert
			stationPassengers.put(s, passengers);
		}else{
			//insert
			stationPassengers.get(s).add(p);
		}
	}
	
	
}
