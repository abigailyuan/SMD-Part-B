package com.unimelb.swen30006.metromadness.passengers;

import java.util.Random;

public class Passenger {

	private int id;
	private String beginning;
	private String destination;
	
	private float travelTime;
	private boolean reachedDestination;
	
	//public Cargo cargo;
	private int cargo;
	public static final int MAX_CARGO = 50;
	
	public Passenger(int id, Random random, String start, String end){
		this.id = id;
		this.beginning = start;
		this.destination = end;
		this.reachedDestination = false;
		this.travelTime = 0;
		this.cargo = generateCargo(random);
	}
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBeginning() {
		return beginning;
	}

	public void setBeginning(String beginning) {
		this.beginning = beginning;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public float getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(float travelTime) {
		this.travelTime = travelTime;
	}

	public boolean isReachedDestination() {
		return reachedDestination;
	}

	public void setReachedDestination(boolean reachedDestination) {
		this.reachedDestination = reachedDestination;
	}

	public void update(float time){
		if(!this.reachedDestination){
			this.travelTime += time;
		}
	}
	
	public int getCargo() {
		return this.cargo;
	}
	
	public void setCargo(int newWeight) {
		this.cargo = newWeight;
	}
	
	public int generateCargo(Random random){
		return random.nextInt(MAX_CARGO + 1);         
	}
	
}
