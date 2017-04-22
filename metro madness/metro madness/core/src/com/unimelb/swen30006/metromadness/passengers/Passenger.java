package com.unimelb.swen30006.metromadness.passengers;

import java.util.Random;

import com.unimelb.swen30006.metromadness.stations.Station;

public class Passenger {

	private int id;
	//public Station beginning;
	//public Station destination;
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
	/*
	public Cargo getCargo(){
		return cargo;
	}
	public Cargo generateCargo(Random random){
		return new Cargo(random.nextInt(51));
	}*/
	
	public int getCargo() {
		return this.cargo;
	}
	
	public void setCargo(int newWeight) {
		this.cargo = newWeight;
	}
	
	public int generateCargo(Random random){
		return random.nextInt(MAX_CARGO + 1);         
	}
	
	/*
	public class Cargo{
		private int weight;
		
		public Cargo(int weight){
			this.setWeight(weight);
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}
	} */

	
	
}
