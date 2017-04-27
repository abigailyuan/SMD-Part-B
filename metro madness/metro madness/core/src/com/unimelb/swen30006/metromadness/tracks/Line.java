package com.unimelb.swen30006.metromadness.tracks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.mapping.Mapping;
import com.unimelb.swen30006.metromadness.stations.CargoStation;
import com.unimelb.swen30006.metromadness.stations.Station;

public class Line {
	
	// The colour of this line
	private Color lineColour;
	private Color trackColour;
	
	// The name of this line
	private String name;
	// Create a line
	public Line(Color stationColour, Color lineColour, String name){
		// Set the line colour
		this.setLineColour(stationColour);
		this.setTrackColour(lineColour);
		this.setName(name);
		
	}
	
	
	@Override
	public String toString() {
		return "Line [lineColour=" + getLineColour() + ", trackColour=" + getTrackColour() + ", name=" + getName() + "]";
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
	
		// Draw all the track sections
		for(Track t: Mapping.getLineTracks(this)){
			renderer.setColor(t.getTrackColour());
			t.render(renderer);
		}	
	}
	
	public boolean nextCargoStation(Station s, boolean forward) throws Exception{
		ArrayList<Station> cargoStations = new ArrayList<Station>();
		
		for (Station station: Mapping.getLineStations(this)){
			if(station instanceof CargoStation){
				cargoStations.add(station);
			}
		}
		int current_station = cargoStations.indexOf(s);
		if(forward){ current_station+=1;}else{ current_station -=1;}
		// Check index is within range
		if((current_station < 0) || (current_station > cargoStations.size()-1)){
			return false;
		} else {
			return true;
		}
	}


	public Color getLineColour() {
		return lineColour;
	}


	public void setLineColour(Color lineColour) {
		this.lineColour = lineColour;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Color getTrackColour() {
		return trackColour;
	}


	public void setTrackColour(Color trackColour) {
		this.trackColour = trackColour;
	}
	
}
