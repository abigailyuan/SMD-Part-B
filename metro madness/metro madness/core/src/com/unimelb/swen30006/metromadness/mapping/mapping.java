package com.unimelb.swen30006.metromadness.mapping;

import java.util.ArrayList;
import java.util.HashMap;

import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.tracks.Track;

public class mapping {
	
	private HashMap<Line, ArrayList<Station>> lineStations;
	private HashMap<Station, ArrayList<Line>> stationLines;
	private HashMap<Line, ArrayList<Track>> lineTracks;
	
	public mapping(){
		
		lineStations = new HashMap<Line, ArrayList<Station>>();
		stationLines = new HashMap<Station, ArrayList<Line>>();
		lineTracks = new HashMap<Line, ArrayList<Track>>();
		
	}
	
	public ArrayList<Station> getLineStations(Line l){
		return lineStations.get(l);
	}
	public ArrayList<Line> getStationLines(Station s){
		return stationLines.get(s);
	}
	public ArrayList<Track> getLineTracks(Line l){
		return lineTracks.get(l);
	}
	
	public void addLineStations(Line l, Station s){
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
	
	public void addStationLines(Station s, Line l){
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
	
	public void addlineTracks(Line l, Track t){

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
	
}
