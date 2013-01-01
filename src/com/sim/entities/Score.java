package com.sim.entities;

public class Score {

	private int id;
	private float score;
	private String pseudo;
	private String date;
	private int ObjectiveId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getObjectiveId() {
		return ObjectiveId;
	}
	public void setObjectiveId(int objectiveId) {
		ObjectiveId = objectiveId;
	}
	
	
}
