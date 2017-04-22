package com.unimelb.swen30006.metromadness.stations;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.mapping.Mapping;
import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.tracks.Line;

public class ActiveStation extends Station {
	// Logger
	private static Logger logger = LogManager.getLogger();
	
	//public PassengerGenerator g;
	//public ArrayList<Passenger> waiting;
	public int maxVolume;
	
	public ActiveStation(float x, float y, PassengerRouter router, String name, int maxPax) {
		super(x, y, router, name);
		//this.waiting = new ArrayList<Passenger>();
		//this.g = new PassengerGenerator(this, this.lines, maxPax);
		this.maxVolume = maxPax;
	}
	
//	@Override
/*	public void enter(Train t) throws Exception {
		if(trains.size() >= PLATFORMS){
			throw new Exception();
		} else {
			// Add the train
			this.trains.add(t);
			// Add the waiting passengers
			Iterator<Passenger> pIter = this.waiting.iterator();
			while(pIter.hasNext()){
				Passenger p = pIter.next();
				try {
					logger.info("Passenger "+ p.getId()+" carrying "+p.getCargo() +" kg cargo embarking at "+this.name+" heading to "+p.getDestination());
					t.embark(p);
					pIter.remove();
				} catch (Exception e){
					// Do nothing, already waiting
					break;
				}
			}
			
			//Do not add new passengers if there are too many already
			if (this.waiting.size() > maxVolume){
				return;
			}
			// Add the new passenger
			Passenger[] ps = PassengerGenerator.generatePassengers(this);
			for(Passenger p: ps){
				try {
					logger.info("Passenger "+p.getId()+" carrying "+p.getCargo() +" kg embarking at "+ this.name+" heading to "+p.getDestination());
					t.embark(p);
				} catch(Exception e){
					this.waiting.add(p);
				}
			}
		}
	}
*/
	public void render(ShapeRenderer renderer){
		float radius = RADIUS;
		for(int i=0; (i<Mapping.getStationLines(this).size() && i<MAX_LINES); i++){
			Line l = Mapping.getStationLines(this).get(i);
			renderer.setColor(l.lineColour);
			renderer.circle(this.position.x, this.position.y, radius, NUM_CIRCLE_STATMENTS);
			radius = radius - 1;
		}
		
		// Calculate the percentage
		float t = this.trains.size()/(float)PLATFORMS;
		Color c = Color.WHITE.cpy().lerp(Color.DARK_GRAY, t);
		if(Mapping.getStationPassengers(this).size() > 0){
			c = Color.RED;
		}
		
		renderer.setColor(c);
		renderer.circle(this.position.x, this.position.y, radius, NUM_CIRCLE_STATMENTS);		
	}

}
