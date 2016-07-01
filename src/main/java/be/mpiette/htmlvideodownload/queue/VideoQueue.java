package be.mpiette.htmlvideodownload.queue;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class VideoQueue {
	private BlockingQueue<VideoQueueElement> queue;
	private Set<URL> alreadyDownloaded;

	public VideoQueue() {
		queue = new LinkedBlockingQueue<VideoQueueElement>();
		alreadyDownloaded = new HashSet<URL>();
	}

	public void add(VideoQueueElement e) {
		if (!alreadyDownloaded.contains(e)) {
			alreadyDownloaded.add(e.url);
			queue.add(e);
		}
	}

	public VideoQueueElement take() throws InterruptedException {
		return queue.take();
	}
}
