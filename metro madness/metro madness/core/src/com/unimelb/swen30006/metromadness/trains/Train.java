package com.unimelb.swen30006.metromadness.trains;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.mapping.Mapping;
import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.passengers.PassengerGenerator;
import com.unimelb.swen30006.metromadness.stations.ActiveStation;
import com.unimelb.swen30006.metromadness.stations.CargoStation;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.tracks.Track;

public class Train {
	
	// Logger
	private static Logger logger = LogManager.getLogger();
	// The state that a train can be in 
	public enum State {
		IN_STATION, READY_DEPART, ON_ROUTE, WAITING_ENTRY, FROM_DEPOT
	}

	// Constants
	public static final int MAX_TRIPS=4;
	public static final Color FORWARD_COLOUR = Color.ORANGE;
	public static final Color BACKWARD_COLOUR = Color.VIOLET;
	public static final float TRAIN_WIDTH=4;
	public static final float TRAIN_LENGTH = 6;
	public static final float TRAIN_SPEED=50f;
	
	// The train's name
	
	private String name;

	// The line that this is traveling on
	private Line trainLine;

	// Passenger Information
	private float departureTimer;
	
	// Station and track and position information
	private Station station; 
	private Track track;
	private Point2D.Float pos;

	// Direction and direction
	private boolean forward;
	private State state;
	private State previousState = null;

	// State variables
	private int numTrips;
	private boolean disembarked;

	private int maxPassengers;
	
	public Train(Line trainLine, Station start, boolean forward, String name, int maxPassengers){
		this.trainLine = trainLine;
		this.station = start;
		this.state = State.FROM_DEPOT;
		this.setForward(forward);
		this.name = name;
		this.setMaxPassengers(maxPassengers);
	}
	
	
	public Train(Line trainLine, Station start, boolean forward, String name){
		this.trainLine = trainLine;
		this.station = start;
		this.state = State.FROM_DEPOT;
		this.setForward(forward);
		this.name = name;
	}

	public void update(float delta){
		// Update all passengers
		ArrayList<Passenger> psgOnTrain = Mapping.getTrainPassengers(this);
		if (psgOnTrain.size() > 0) {
			for (Passenger p : psgOnTrain){
				p.update(delta);
			}
		}
		boolean hasChanged = false;
		if(previousState == null || previousState != this.state){
			previousState = this.state;
			hasChanged = true;
		}
		
		// Update the state
		switch(this.state) {
		case FROM_DEPOT:
			if(hasChanged){
				logger.info(this.name+ " is travelling from the depot: "+this.station.getName()+" Station...");
			}
			
			// We have our station initialized we just need to retrieve the next track, enter the
			// current station officially and mark as in station
			try {
				if(this.station.canEnter(this)){
					
					//this.station.enter(this);
					enter(this.station);
					this.setPos((Point2D.Float) this.station.position.clone());
					this.state = State.IN_STATION;
					this.disembarked = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		case IN_STATION:
			if(hasChanged){
				logger.info(this.name+" is in "+this.station.getName()+" Station.");
			}
			
			// When in station we want to disembark passengers 
			// and wait 10 seconds for incoming passengers
			if(!this.disembarked){
				this.disembark();
				this.departureTimer = this.station.getDepartureTime();
				this.disembarked = true;
			} else {
				// Count down if departure timer. 
				if(this.departureTimer>0){
					this.departureTimer -= delta;
				} else {
					// We are ready to depart, find the next track and wait until we can enter 
					try {
						boolean endOfLine = this.trainLine.endOfLine(this.station);
						boolean nextCargoStation = this.trainLine.nextCargoStation(this.station, this.isForward());
						if(endOfLine || (!nextCargoStation && 
						  (this instanceof SmallCargoTrain || this instanceof BigCargoTrain))){
							this.setForward(!this.isForward());
						}
						this.track = this.trainLine.nextTrack(this.station, this.isForward());
						this.state = State.READY_DEPART;
						break;
					} catch (Exception e){
						// Massive error.
						return;
					}
				}
			}
			break;
		case READY_DEPART:
			if(hasChanged){
				logger.info(this.name+ " is ready to depart for "+this.station.getName()+" Station!");
			}
			
			// When ready to depart, check that the track is clear and if
			// so, then occupy it if possible.
			if(this.track.canEnter(this.isForward())){
				try {
					// Find the next
					Station next = this.trainLine.nextStation(this.station, this.isForward());
					// Depart our current station
					this.station.depart(this);
					this.station = next;

				} catch (Exception e) {
					e.printStackTrace();
				}
				this.track.enter(this);
				this.state = State.ON_ROUTE;
			}		
			break;
		case ON_ROUTE:
			if(hasChanged){
				logger.info(this.name+ " enroute to "+this.station.getName()+" Station!");
			}
			
			// Checkout if we have reached the new station
			if(this.getPos().distance(this.station.position) < 10 ){
				this.state = State.WAITING_ENTRY;
			} else {
				move(delta);
			}
			break;
		case WAITING_ENTRY:
			if(hasChanged){
				logger.info(this.name+ " is awaiting entry "+this.station.getName()+" Station..!");
			}
			
			// Waiting to enter, we need to check the station has room and if so
			// then we need to enter, otherwise we just wait
			try {
				if(this.station.canEnter(this)){
				    
				    this.track.leave(this);
				    this.setPos((Point2D.Float) this.station.position.clone());
				    
				    if(!(this.station instanceof CargoStation) && 
				      (this instanceof SmallCargoTrain || this instanceof BigCargoTrain)) {
				        this.track = this.trainLine.nextTrack(this.station, this.isForward());
				        this.state = State.READY_DEPART;
				    } else {
	                    enter(station);
	                    this.state = State.IN_STATION;
	                    this.disembarked = false;
				    }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	public void enter(Station station) throws Exception {
		if (station instanceof ActiveStation) {
			ActiveStation s = (ActiveStation)station;
			if(s.getTrains().size() >= Station.PLATFORMS){
				throw new Exception();
			} else {
				// Add the train
				s.getTrains().add(this);
				// Add the waiting passengers
				ArrayList<Passenger> waiting = Mapping.getStationPassengers(s);
				Iterator<Passenger> pIter = Mapping.getStationPassengers(s).iterator();
				while(pIter.hasNext()){
					Passenger p = pIter.next();
					try {
						logger.info("Passenger "+ p.getId()+" carrying "+p.getCargo() +" kg cargo embarking at "+this.name+" heading to "+p.getDestination());
						this.embark(p);
						pIter.remove();
					} catch (Exception e){
						// Do nothing, already waiting
						break;
					}
				}
				
				//Do not add new passengers if there are too many already
				if (waiting.size() > s.getMaxVolume()){
					return;
				}
				// Add the new passenger
				Passenger[] ps = PassengerGenerator.generatePassengers(station);
				for(Passenger p: ps){
					try {
						logger.info("Passenger "+p.getId()+" carrying "+p.getCargo() +" kg embarking at "+ this.name+" heading to "+p.getDestination());
						this.embark(p);
					} catch(Exception e){
						Mapping.getStationPassengers(s).add(p);
					}
				}
			}
		}
	}
	
	public void move(float delta){
		// Work out where we're going
		float angle = angleAlongLine(this.getPos().x,this.getPos().y,this.station.position.x,this.station.position.y);
		float newX = this.getPos().x + (float)( Math.cos(angle) * delta * TRAIN_SPEED);
		float newY = this.getPos().y + (float)( Math.sin(angle) * delta * TRAIN_SPEED);
		this.getPos().setLocation(newX, newY);
	}

	public void embark(Passenger p) throws Exception {
		ArrayList<Passenger> passengersOnTrain = Mapping.getTrainPassengers(this);
		if(passengersOnTrain.size() > this.getMaxPassengers()){
			throw new Exception();
		}
		passengersOnTrain.add(p);
	}


	public ArrayList<Passenger> disembark(){
		ArrayList<Passenger> disembarking = new ArrayList<Passenger>();
		Iterator<Passenger> iterator = Mapping.getTrainPassengers(this).iterator();
		while(iterator.hasNext()){
			Passenger p = iterator.next();
			if(this.station.shouldLeave(p)){
				logger.info("Passenger "+p.getId()+" is disembarking at "+this.station.getName());
				disembarking.add(p);
				iterator.remove();
			}
		}
		return disembarking;
	}

	@Override
	public String toString() {
		return "Train [line=" + this.trainLine.getName() +", departureTimer=" + departureTimer + ", pos=" + getPos() + ", forward=" + isForward() + ", state=" + state
				+ ", numTrips=" + numTrips + ", disembarked=" + disembarked + "]";
	}

	public boolean inStation(){
		return (this.state == State.IN_STATION || this.state == State.READY_DEPART);
	}
	
	public float angleAlongLine(float x1, float y1, float x2, float y2){	
		return (float) Math.atan2((y2-y1),(x2-x1));
	}

	public void render(ShapeRenderer renderer){
		if(!this.inStation()){
			Color col = this.isForward() ? FORWARD_COLOUR : BACKWARD_COLOUR;
			renderer.setColor(col);
			renderer.circle(this.getPos().x, this.getPos().y, TRAIN_WIDTH);
		}
	}


	public int getMaxPassengers() {
		return maxPassengers;
	}


	public void setMaxPassengers(int maxPassengers) {
		this.maxPassengers = maxPassengers;
	}


	public boolean isForward() {
		return forward;
	}


	public void setForward(boolean forward) {
		this.forward = forward;
	}


	public Point2D.Float getPos() {
		return pos;
	}


	public void setPos(Point2D.Float pos) {
		this.pos = pos;
	}
	
}
