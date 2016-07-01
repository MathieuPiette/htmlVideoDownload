package be.mpiette.htmlvideodownload.queue;

import java.net.URL;

public class VideoQueueElement {
	public final URL url;

	public final String title;

	public VideoQueueElement(URL url, String title) {
		super();
		this.url = url;
		this.title = title;
	}

}
