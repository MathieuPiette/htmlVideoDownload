package be.mpiette.htmlvideodownload.queue;

import java.net.URL;

public class PageQueueElement {
	public final URL url;
	public final int horizon;

	public PageQueueElement(URL url, int horizon) {
		super();
		this.url = url;
		this.horizon = horizon;
	}
}
