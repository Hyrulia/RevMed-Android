package com.sim.pattern;

import com.sim.dao.ChoiceDAO;
import com.sim.dao.ObjectiveDAO;
import com.sim.dao.QuestionDAO;
import com.sim.dao.RevisionDAO;
import com.sim.dao.ScoreDAO;
import com.sim.dao.SpecialityDAO;

public class DAOFactory {

	public final static int CHOICE     	= 0;
	public final static int QUESTION 	= 1;
	public final static int REVISION 	= 2;
	public final static int SPECIALITY 	= 3;
	public final static int SCORE 		= 4;
	public final static int OBJECTIVE 	= 5;
	
	public static DAO<?> create(int dao) {
		switch(dao) {
		case CHOICE:
			return new ChoiceDAO();
		case QUESTION:
			return new QuestionDAO();
		case REVISION:
			return new RevisionDAO();
		case SPECIALITY:
			return new SpecialityDAO();
		case SCORE:
			return new ScoreDAO();
		case OBJECTIVE:
			return new ObjectiveDAO();
		}
		return null;
	}
}
