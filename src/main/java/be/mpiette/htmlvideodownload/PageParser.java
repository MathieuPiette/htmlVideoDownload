package be.mpiette.htmlvideodownload;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageParser {
	private Document doc;

	public PageParser(Document doc) {
		super();
		this.doc = doc;
	}

	public Page parse() {
		String title = doc.title();

		Elements docLinks = doc.select("a[href]");
		Set<Link> links = new HashSet<Link>();
		for (Element docLink : docLinks) {
			Link link = new Link(docLink.text(), docLink.attr("href"));
			links.add(link);
		}

		Elements docVideos = doc.select("source");
		Set<String> videos = new HashSet<String>();
		for (Element docVideo : docVideos) {
			String src = docVideo.attr("src");
			videos.add(src);
		}

		return new Page(title, links, videos);
	}
}
