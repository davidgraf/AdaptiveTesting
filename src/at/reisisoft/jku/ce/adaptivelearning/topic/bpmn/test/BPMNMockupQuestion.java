package at.reisisoft.jku.ce.adaptivelearning.topic.bpmn.test;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import at.reisisoft.jku.ce.adaptivelearning.topic.bpmn.BPMNDataStorage;
import at.reisisoft.jku.ce.adaptivelearning.xml.XmlQuestionData;
import at.reisisoft.jku.ce.adaptivelearning.xml.topic.bpmn.XmlBPMNQuestion;

public class BPMNMockupQuestion {
	
	/**
	 * topic: modeling
	 * class creating xml (actual not used)
	 * created by David Graf 06-2016
	 */
	
	public static void main(String[] args) throws JAXBException  {
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				
		XmlBPMNQuestion export = new XmlBPMNQuestion();
		BPMNDataStorage ds = new BPMNDataStorage();
				
		JAXBContext context = JAXBContext.newInstance(XmlQuestionData.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal(export, byteArrayOutputStream);
		System.out.println(new String(byteArrayOutputStream.toByteArray()));
	}

}
