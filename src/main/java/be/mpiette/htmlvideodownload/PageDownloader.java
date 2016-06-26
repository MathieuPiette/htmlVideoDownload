package be.mpiette.htmlvideodownload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PageDownloader {

	private static String DocumentToString(Document doc) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ElementToStream(doc.getDocumentElement(), baos);
		return new String(baos.toByteArray());
	}

	private static void ElementToStream(Element element, ByteArrayOutputStream baos) {
		try {
			DOMSource source = new DOMSource(element);
			StreamResult result = new StreamResult(baos);
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.transform(source, result);
		} catch (Exception ex) {
		}
	}

	private final URL url;

	public PageDownloader(URL url) {
		super();
		this.url = url;
	}

	public String get() throws IOException {
		Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setActiveXNative(false);
		webClient.getOptions().setAppletEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setPopupBlockerEnabled(true);
		webClient.getOptions().setPrintContentOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		HtmlPage doc = webClient.getPage(url);
		webClient.close();
		return DocumentToString(doc);
	}
}
