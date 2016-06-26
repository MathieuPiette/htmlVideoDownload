package be.mpiette.htmlvideodownload;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

import org.jsoup.Jsoup;

import be.mpiette.htmlvideodownload.queue.PageQueue;
import be.mpiette.htmlvideodownload.queue.PageQueueElement;
import be.mpiette.htmlvideodownload.queue.VideoQueue;
import be.mpiette.htmlvideodownload.queue.VideoQueueElement;

public class SiteCrawler extends Thread {
	private final Collection<String> urlsToIgnore;
	private final int maxHorizon;
	private final VideoQueue videoQueue;
	private final PageQueue pageQueue;

	public SiteCrawler(int maxDistance, Collection<String> urlsToIgnore, PageQueue pageQueue, VideoQueue videoQueue)
			throws URISyntaxException {
		super();
		this.maxHorizon = maxDistance;
		this.urlsToIgnore = urlsToIgnore;
		this.videoQueue = videoQueue;
		this.pageQueue = pageQueue;
	}

	private void consume(PageQueueElement pageQueueElement) {
		System.out.println("Consuming " + pageQueueElement.url + " (" + pageQueueElement.horizon + ")");
		try {
			String doc = new PageDownloader(pageQueueElement.url).get();
			Page page = new PageParser(Jsoup.parse(doc)).parse();

			for (String video : page.videos) {
				URL videoUrl = new URL(video.startsWith("//") ? "http:" + video : video);
				videoQueue.add(new VideoQueueElement(videoUrl, page.title));
			}

			if (maxHorizon - pageQueueElement.horizon > 0) {
				for (Link link : page.links) {
					String subLink = link.url;
					if (subLink.startsWith("/")) {
						subLink = pageQueueElement.url.getPath() + link.url;
					} else if (subLink.startsWith("www")) {
						subLink = "http://" + link.url;
					}

					String domain = getDomain(pageQueueElement.url);
					if (subLink.startsWith("#") || !domain.equals(getDomain(new URL(subLink)))
							|| pageQueueElement.equals(new URL(subLink))) {
						continue;
					}
					synchronized (urlsToIgnore) {
						if (urlsToIgnore.contains(subLink)) {
							System.out.println("Ignoring " + subLink + " (already visited or in queue)");
							continue;
						}
						urlsToIgnore.add(subLink);
					}
					pageQueue.add(new PageQueueElement(new URL(subLink), pageQueueElement.horizon + 1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getDomain(URL url) throws URISyntaxException {
		String domain = url.getHost();
		if (domain.startsWith("www.")) {
			domain = domain.substring(4);
		}
		return domain;
	}

	@Override
	public void run() {
		while (true) {
			try {
				consume(pageQueue.take());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
