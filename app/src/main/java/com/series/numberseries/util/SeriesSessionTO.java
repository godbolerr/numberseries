package com.series.numberseries.util;

import java.util.ArrayList;
import java.util.List;

public class SeriesSessionTO {

	public SeriesSessionTO() {
		// TODO Auto-generated constructor stub
	}
	
	long id;
	
	long startTime;
		
	long endTime;

	int current = 0;

	InteractionTO currentIto;
	
	List<InteractionTO> interactionTos ;

	public InteractionTO getNext(){

		int total = interactionTos.size();



        InteractionTO ito = null;

		if ( current < total ) {
			ito = interactionTos.get(current);
			currentIto = ito;
			System.out.println("Interactions are " + total + " current one " + current + " > " + interactionTos.get(current).getSto().getSeries());
		}

		current++;

		return ito;
	}


	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

    public List<InteractionTO> getInteractionTos() {
        return interactionTos;
    }

    public void setInteractionTos(List<InteractionTO> interactionTos) {
        this.interactionTos = interactionTos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCurrent() {
        return current;
    }

	public InteractionTO getCurrentIto() {
		return currentIto;
	}

	public void setInput(String input) {
//		InteractionTO ito = interactionTos.get(current);
//		if ( ito != null ){
//			ito.setInput(input);
//			System.out.println(ito + ":" + ito.getSto().getSeries());
//		}
	}

}
