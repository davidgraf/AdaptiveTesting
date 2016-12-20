package at.reisisoft.jku.ce.adaptivelearning.topic.accounting.test;

/*This file is part of the project "Reisisoft Adaptive Testing",
 * which is licenced under LGPL v3+. You may find a copy in the source,
 * or obtain one at http://www.gnu.org/licenses/lgpl-3.0-standalone.html */
import at.reisisoft.jku.ce.adaptivelearning.topic.accounting.ProfitDataStorage;
import at.reisisoft.jku.ce.adaptivelearning.topic.accounting.ProfitQuestion;
import at.reisisoft.jku.ce.adaptivelearning.vaadin.ui.MockQuestion;

public class ProfitMockQuestion extends
		MockQuestion<ProfitQuestion, ProfitDataStorage> {

	public ProfitMockQuestion(ProfitDataStorage solution,
			ProfitDataStorage dataStorage, float difficulty, String s) {
		super(new ProfitQuestion(solution, dataStorage, difficulty, s));
	}

	public ProfitMockQuestion() {
		super(new ProfitQuestion(new ProfitDataStorage(null), 0f, ""));
	}

	private static final long serialVersionUID = -4498805944562368205L;

}
