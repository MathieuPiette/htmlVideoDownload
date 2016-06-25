package be.mpiette.htmlvideodownload;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

public class HtmlVideoDownload {

	public static void main(String[] args) throws Exception {
		Options options = new Options();

		options.addOption("url", true, "Url");
		options.addOption("horizon", true, "Horizon");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		if (cmd.hasOption("url")) {
			String url = cmd.getOptionValue("url");
			Integer horizon = Integer.valueOf(cmd.getOptionValue("horizon"));

			SiteCrawler crawler = new SiteCrawler(url, horizon);
			Map<String, Page> pages = crawler.crawl();

			Set<String> videos = new HashSet<String>();
			for (Page p : pages.values()) {
				videos.addAll(p.videos);
			}

		}
	}

}
