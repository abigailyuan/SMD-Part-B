package com.unimelb.swen30006.metromadness.tracks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.mapping.Mapping;
import com.unimelb.swen30006.metromadness.stations.CargoStation;
import com.unimelb.swen30006.metromadness.stations.Station;

public class Line {
	
	// The colour of this line
	public Color lineColour;
	public Color trackColour;
	
	// The name of this line
	public String name;
	// The stations on this line
	public ArrayList<Station> stations;
	// The tracks on this line between stations
	public ArrayList<Track> tracks;
		
	// Create a line
	public Line(Color stationColour, Color lineColour, String name){
		// Set the line colour
		this.lineColour = stationColour;
		this.trackColour = lineColour;
		this.name = name;
		
		// Create the data structures
		//this.stations = new ArrayList<Station>();
		//this.tracks = new ArrayList<Track>();
	}
	
	
	public void addStation(Station s, Boolean two_way){
		// We need to build the track if this is adding to existing stations
		if(Mapping.getLineStations(this).size() > 0){
			// Get the last station
			Station last = Mapping.getLineStations(this).get(Mapping.getLineStations(this).size()-1);
			
			// Generate a new track
			Track t;
			if(two_way){
				t = new DualTrack(last.position, s.position, this.trackColour);
			} else {
				t = new Track(last.position, s.position, this.trackColour);
			}
			this.tracks.add(t);
		}
		
		// Add the station
		s.registerLine(this);
		Mapping.getLineStations(this).add(s);
	}
	
	
	@Override
	public String toString() {
		return "Line [lineColour=" + lineColour + ", trackColour=" + trackColour + ", name=" + name + "]";
	}


	public boolean endOfLine(Station s) throws Exception{
		if(Mapping.getLineStations(this).contains(s)){
			int index = Mapping.getLineStations(this).indexOf(s);
			return (index==0 || index==Mapping.getLineStations(this).size()-1);
		} else {
			throw new Exception();
		}
	}

	
	
	public Track nextTrack(Station currentStation, boolean forward) throws Exception {
		if(Mapping.getLineStations(this).contains(currentStation)){
			// Determine the track index
			int curIndex = Mapping.getLineStations(this).lastIndexOf(currentStation);
			// Increment to retrieve
			if(!forward){ curIndex -=1;}
			
			// Check index is within range
			if((curIndex < 0) || (curIndex > Mapping.getLineTracks(this).size()-1)){
				throw new Exception();
			} else {
				return Mapping.getLineTracks(this).get(curIndex);
			}
			
		} else {
			throw new Exception();
		}
	}
	
	public Station nextStation(Station s, boolean forward) throws Exception{
		if(Mapping.getLineStations(this).contains(s)){
			int curIndex = Mapping.getLineStations(this).lastIndexOf(s);
			if(forward){ curIndex+=1;}else{ curIndex -=1;}
			
			// Check index is within range
			if((curIndex < 0) || (curIndex > Mapping.getLineStations(this).size()-1)){
				throw new Exception();
			} else {
				return Mapping.getLineStations(this).get(curIndex);
			}
		} else {
			throw new Exception();
		}
	}
	
	public void render(ShapeRenderer renderer){
		// Set the color to our line
		//renderer.setColor(trackColour);
	
		// Draw all the track sections
		for(Track t: Mapping.getLineTracks(this)){
			renderer.setColor(t.trackColour);
			t.render(renderer);
		}	
	}
	
	public CargoStation nextCargoStation(Station s, boolean forward) throws Exception{
		ArrayList<Station> cargoStations = new ArrayList<Station>();
		int i = 0;
		for (Station station: Mapping.getLineStations(this)){
			if(station instanceof CargoStation){
				cargoStations.add(station);
			}
		}
		int current_station = cargoStations.indexOf(s);
		if(forward){ current_station+=1;}else{ current_station -=1;}
		// Check index is within range
		if((current_station < 0) || (current_station > cargoStations.size()-1)){
			return null;
		} else {
			return (CargoStation)cargoStations.get(current_station);
		}
	}
	
}
