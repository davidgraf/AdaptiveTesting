package at.reisisoft.jku.ce.adaptivelearning.topic.accounting;

/*This file is part of the project "Reisisoft Adaptive Testing",
 * which is licenced under LGPL v3+. You may find a copy in the source,
 * or obtain one at http://www.gnu.org/licenses/lgpl-3.0-standalone.html */
public enum ProfitPossibleAnswers {
	Increase("Gewinnerhöhend"), Decrease("Gewinnmindernd"), NO_Change(
			"Beeinflusst den Gewinn nicht");
	String name;

	private ProfitPossibleAnswers(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
