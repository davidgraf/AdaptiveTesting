package at.reisisoft.jku.ce.adaptivelearning.xml.topic.accounting;

/*This file is part of the project "Reisisoft Adaptive Testing",
 * which is licenced under LGPL v3+. You may find a copy in the source,
 * or obtain one at http://www.gnu.org/licenses/lgpl-3.0-standalone.html */
import at.reisisoft.jku.ce.adaptivelearning.topic.accounting.AccountingQuestion;
import at.reisisoft.jku.ce.adaptivelearning.topic.accounting.ProfitQuestion;

public final class AccountingXmlHelper {
	public static AccountingQuestion fromXml(XmlAccountingQuestion xml) {
		return new AccountingQuestion(xml.getDataStorage(),
				xml.getDifficulty(), xml.getQuestion()
						.replace("\\n", " <br />"));
	}

	public static ProfitQuestion fromXml(XmlProfitQuestion xml) {
		return new ProfitQuestion(xml.getDataStorage(), xml.getDifficulty(),
				xml.getQuestion().replace("\\n", " <br />"));

	}
}
