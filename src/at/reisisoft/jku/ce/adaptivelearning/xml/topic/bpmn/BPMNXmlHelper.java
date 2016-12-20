package at.reisisoft.jku.ce.adaptivelearning.xml.topic.bpmn;

import at.reisisoft.jku.ce.adaptivelearning.topic.bpmn.BPMNQuestion;

public class BPMNXmlHelper {
	
	/**
	 * topic: modeling
	 * created by David Graf 06-2016
	 */
	
	public static BPMNQuestion fromXml(XmlBPMNQuestion xml) {
		return new BPMNQuestion(xml.getDataStorage(), xml.getDifficulty(),
				xml.getQuestion().replace("\\n", " <br />"));

	}
}
