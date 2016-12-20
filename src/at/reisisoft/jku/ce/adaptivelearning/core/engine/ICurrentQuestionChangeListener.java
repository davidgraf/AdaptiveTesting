package at.reisisoft.jku.ce.adaptivelearning.core.engine;

/*This file is part of the project "Reisisoft Adaptive Testing",
 * which is licenced under LGPL v3+. You may find a copy in the source,
 * or obtain one at http://www.gnu.org/licenses/lgpl-3.0-standalone.html */
import at.reisisoft.jku.ce.adaptivelearning.core.AnswerStorage;
import at.reisisoft.jku.ce.adaptivelearning.core.IQuestion;

public interface ICurrentQuestionChangeListener {

	public void questionChanged(IQuestion<? extends AnswerStorage> question);

}
