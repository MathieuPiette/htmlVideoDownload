package be.mpiette.htmlvideodownload;

import java.util.Set;

public class Page {
	public final String title;
	public final Set<Link> links;
	public final Set<String> videos;

	public Page(String title, Set<Link> links, Set<String> videos) {
		super();
		this.title = title;
		this.links = links;
		this.videos = videos;
	}

	@Override
	public String toString() {
		return "Page [\n    title=" + title + "\n    links=" + links + "\n    videos=" + videos + "]";
	}
}
