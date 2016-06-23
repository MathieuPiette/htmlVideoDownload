package be.mpiette.htmlvideodownload;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;

public class SiteCrawler {
	private Map<String, Page> pages;

	private final Collection<String> urlsToIgnore;

	private final String url;
	private final String domain;
	private final int maxDistance;

	public SiteCrawler(String url, int maxDistance) throws URISyntaxException {
		this(url, maxDistance, new ArrayList<String>());
	}

	public SiteCrawler(String url, int maxDistance, Collection<String> urlsToIgnore) throws URISyntaxException {
		super();
		this.url = url;
		this.maxDistance = maxDistance;
		this.urlsToIgnore = urlsToIgnore;
		pages = new HashMap<String, Page>();
		domain = getDomain(url);
	}

	public Map<String, Page> crawl() throws IOException {
		System.out.println("crawling " + url);

		try {
			String doc = new PageDownloader(url).get();
			Page page = new PageParser(Jsoup.parse(doc)).parse();

			pages.put(url, page);

			if (maxDistance > 0) {
				for (Link link : page.links) {
					String subLink = link.url;
					if (subLink.startsWith("/")) {
						subLink = url + link.url;
					} else if (subLink.startsWith("www")) {
						subLink = "http://" + link.url;
					}

					if (subLink.startsWith("#") || !domain.equals(getDomain(subLink)) || subLink.equals(url)
							|| urlsToIgnore.contains(subLink)) {
						System.out.println("[Ignoring " + subLink + "]");
						continue;
					}

					SiteCrawler crawler = new SiteCrawler(subLink, maxDistance - 1, pages.keySet());
					pages.putAll(crawler.crawl());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pages;
	}

	private String getDomain(String url) throws URISyntaxException {
		URI uri = new URI(url);
		String domain = uri.getHost();
		if (domain.startsWith("www.")) {
			domain = domain.substring(4);
		}
		return domain;
	}
}
