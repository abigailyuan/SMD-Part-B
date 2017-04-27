package com.unimelb.swen30006.metromadness.trains;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.mapping.Mapping;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

public class BigPassengerTrain extends Train {

	public static final int BIG_TRAIN_CAPACITY = 80;
	
	public BigPassengerTrain(Line trainLine, Station start, boolean forward, String name) {
		super(trainLine, start, forward, name, BIG_TRAIN_CAPACITY);
	}
	
	@Override
	public void render(ShapeRenderer renderer){
		if(!this.inStation()){
			Color col = this.isForward() ? FORWARD_COLOUR : BACKWARD_COLOUR;
			float percentage = Mapping.getTrainPassengers(this).size()/20f;
			renderer.setColor(col.cpy().lerp(Color.LIGHT_GRAY, percentage));
			renderer.circle(this.getPos().x, this.getPos().y, TRAIN_WIDTH*(1+percentage));
		}
	}

}
