package com.unimelb.swen30006.metromadness.trains;

import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

public class SmallCargoTrain extends SmallPassengerTrain {
	
	public int CARGO_CAPACITY = 200;

	public SmallCargoTrain(Line trainLine, Station start, boolean forward, String name) {
		super(trainLine, start, forward, name);

	}

}
