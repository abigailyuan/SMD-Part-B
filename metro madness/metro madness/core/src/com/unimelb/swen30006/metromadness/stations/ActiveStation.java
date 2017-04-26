package com.unimelb.swen30006.metromadness.stations;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.mapping.Mapping;
import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.tracks.Line;

public class ActiveStation extends Station {
	
	public int maxVolume;
	
	public ActiveStation(float x, float y, PassengerRouter router, String name, int maxPax) {
		super(x, y, router, name);
		
		this.maxVolume = maxPax;
	}
	

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
