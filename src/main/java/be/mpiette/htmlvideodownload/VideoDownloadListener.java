package be.mpiette.htmlvideodownload;

import java.io.File;
import java.io.IOException;

import be.mpiette.htmlvideodownload.queue.VideoQueue;
import be.mpiette.htmlvideodownload.queue.VideoQueueElement;

public class VideoDownloadListener extends Thread {
	private final VideoQueue queue;
	private final File targetDirectory;

	public VideoDownloadListener(VideoQueue queue, File targetDirectory) {
		super();
		this.queue = queue;
		this.targetDirectory = targetDirectory;
	}

	private void consume(VideoQueueElement element) throws IOException {
		new FileDownloader(element.url, targetDirectory, element.title).download(false);
	}

	@Override
	public void run() {
		while (true) {
			try {
				consume(queue.take());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// Error during file download
				e.printStackTrace();
			}
		}
	}
}
