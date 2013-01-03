package com.sim.entities;

import java.util.ArrayList;
import java.util.List;

public class Speciality {

	private int id;
	private String speciality;
	private List<Objective> objectives = new ArrayList<Objective>();
	
	
	public List<Objective> getObjectives() {
		return objectives;
	}
	public void setObjectives(List<Objective> objectives) {
		this.objectives = objectives;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	
	
}
