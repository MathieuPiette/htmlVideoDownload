package be.mpiette.htmlvideodownload.queue;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PageQueue {
	private BlockingQueue<PageQueueElement> queue;
	private Set<String> alreadyVisisted;

	public PageQueue() {
		queue = new LinkedBlockingQueue<PageQueueElement>();
		alreadyVisisted = new HashSet<String>();
	}

	public void add(PageQueueElement e) {
		if (!alreadyVisisted.contains(e.url)) {
			alreadyVisisted.add(e.url.toString());
			queue.add(e);
		}
	}

	public PageQueueElement take() throws InterruptedException {
		return queue.take();
	}
}
