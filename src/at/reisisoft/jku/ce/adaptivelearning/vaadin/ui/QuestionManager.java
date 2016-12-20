package at.reisisoft.jku.ce.adaptivelearning.vaadin.ui;

/*This file is part of the project "Reisisoft Adaptive Testing",
 * which is licenced under LGPL v3+. You may find a copy in the source,
 * or obtain one at http://www.gnu.org/licenses/lgpl-3.0-standalone.html */
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import at.reisisoft.jku.ce.adaptivelearning.core.AnswerStorage;
import at.reisisoft.jku.ce.adaptivelearning.core.IQuestion;
import at.reisisoft.jku.ce.adaptivelearning.core.IResultView;
import at.reisisoft.jku.ce.adaptivelearning.core.LogHelper;
import at.reisisoft.jku.ce.adaptivelearning.core.engine.EngineException;
import at.reisisoft.jku.ce.adaptivelearning.core.engine.ICurrentQuestionChangeListener;
import at.reisisoft.jku.ce.adaptivelearning.core.engine.IEngine;
import at.reisisoft.jku.ce.adaptivelearning.core.engine.IResultFiredListener;
import at.reisisoft.jku.ce.adaptivelearning.core.engine.ResultFiredArgs;
import at.reisisoft.jku.ce.adaptivelearning.core.engine.engines.SimpleEngine;
import at.reisisoft.jku.ce.adaptivelearning.html.HtmlLabel;
import at.reisisoft.jku.ce.adaptivelearning.vaadin.ui.core.VaadinUI;
import at.reisisoft.jku.ce.adaptivelearning.vaadin.ui.core.Views;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

public abstract class QuestionManager extends VerticalLayout implements
		ICurrentQuestionChangeListener, IResultFiredListener, View {

	private static final long serialVersionUID = -4764723794449575244L;
	private SingleComponentLayout questionHolder = new SingleComponentLayout();
	private IEngine iEngine;
	private GridLayout southLayout = new GridLayout(3, 1);
	private final Button next;
	private Component helpComponent = null;
	private Label title;
	private Class<? extends IResultView> resultViewClass = null;

	public QuestionManager(String quizName) {
		this(quizName, null);
	}

	public QuestionManager(String quizName, IEngine engine) {
		setMargin(true);
		title = HtmlLabel.getCenteredLabel("h1", quizName);
		addComponent(title);
		addComponent(questionHolder);

		addComponent(southLayout);

		next = new Button("Next question ->");
		next.addClickListener(e -> {
			e.getButton().setEnabled(false);
			try {
				iEngine.requestCalculation();
			} catch (EngineException e1) {
				Notification.show("Error on calculating the next question.",
						"Visit the log for more infos", Type.ERROR_MESSAGE);
				LogHelper.logThrowable(e1);
			}
		});
		southLayout.addComponent(next, 2, 0);
		southLayout.setSizeFull();
		southLayout.setMargin(true);
		// Ensure we have an engine
		if (engine == null) {
			try {
				iEngine = new SimpleEngine();
			} catch (EngineException e1) {
				Notification.show("Engine could not be initialized",
						Type.ERROR_MESSAGE);
				LogHelper.logThrowable(e1);

			}
		} else {
			iEngine = engine;
		}
		// Register to engine events
		iEngine.addQuestionChangeListener(this);
		iEngine.addResultFiredListener(this);
		setResultView(VaadinResultView.class);
	}

	/**
	 *
	 * @param question
	 *            A question which is a component as well
	 */
	public <QuestionComponent extends IQuestion<? extends AnswerStorage> & Component & Sizeable> void addQuestion(
			QuestionComponent question) {
		iEngine.addQuestionToPool(question);
	}

	protected final void addHelpButton(Component c) {
		assert c != null;
		helpComponent = c;
		southLayout.addComponent(helpComponent, 1, 0);
	}

	@Override
	public void resultFired(ResultFiredArgs args) throws EngineException {
		IResultView result;
		if (resultViewClass == null) {
			String msg = "You forget to set the result view";
			LogHelper.logError(msg);
			throw new NullPointerException(msg);
		}
		Constructor<? extends IResultView> resultConstructor;
		try {
			resultConstructor = resultViewClass.getConstructor(
					ResultFiredArgs.class, String.class);
			result = resultConstructor.newInstance(args, title.getValue());
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NullPointerException e) {
			LogHelper.logInfo(resultViewClass.getName()
					+ " does not implement the constructors of "
					+ IResultView.class.getName());
			throw new EngineException(e);
		}

		// Add it to the navigator
		Navigator navigator = getUI().getNavigator();
		assert navigator != null;
		// Cast cannot fail, as the setResultView takes care, that it is a View
		// as well
		navigator.addView(Views.RESULT.toString(), (View) result);
		navigator.navigateTo(Views.RESULT.toString());

	}

	// As QuestionManager only listens to the engine, every Question is a
	// Component as well
	@Override
	public void questionChanged(IQuestion<? extends AnswerStorage> question) {
		// This cast won't fail as every question in the engine is a Component
		// as well
		if (question != null) {
			Component c = (Component) question;
			questionHolder.addComponent(c);
			Sizeable s = (Sizeable) question;
			s.setSizeFull();
		}
		next.setEnabled(true);
	}

	/**
	 * Loads questions to the {@code iEngine}
	 */
	public abstract void loadQuestions();

	public void startQuiz() {
		iEngine.resetQuestions();
		loadQuestions();
		try {
			iEngine.start();
		} catch (EngineException e) {
			Notification.show("Engine could not be started", e.getCause()
					.getMessage(), Type.ERROR_MESSAGE);
			LogHelper.logThrowable(e);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		startQuiz();
		VaadinUI.setCurrentPageTitle(event);
	}

	public <RView extends View & IResultView> void setResultView(
			Class<? extends RView> class1) {
		resultViewClass = class1;
	}
}
