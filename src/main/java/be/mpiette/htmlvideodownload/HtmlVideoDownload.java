package be.mpiette.htmlvideodownload;

import be.mpiette.htmlvideodownload.queue.PageQueue;
import be.mpiette.htmlvideodownload.queue.PageQueueElement;
import be.mpiette.htmlvideodownload.queue.VideoQueue;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

public class HtmlVideoDownload {

	public static void main(String[] args) throws Exception {
		Options options = new Options();

		options.addOption("url", true, "Url");
		options.addOption("horizon", true, "Horizon");
		options.addOption("target", true, "Target directory");
		options.addOption("crawlers", true, "Number of crawlers");
		options.addOption("downloaders", true, "Number of downloaders");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		if (cmd.hasOption("url") && cmd.hasOption("horizon") && cmd.hasOption("target")) {
			URL url = new URL(cmd.getOptionValue("url"));
			Integer horizon = Integer.valueOf(cmd.getOptionValue("horizon"));
			String target = cmd.getOptionValue("target");
			Integer numberOfCrawlers = 1;
			if (cmd.hasOption("crawlers")) {
				numberOfCrawlers = Integer.valueOf(cmd.getOptionValue("crawlers"));
			}
			Integer numberOfDownloaders = 1;
			if (cmd.hasOption("downloaders")) {
				numberOfDownloaders = Integer.valueOf(cmd.getOptionValue("downloaders"));
			}

			VideoQueue videoQueue = new VideoQueue();
			PageQueue pageQueue = new PageQueue();

			Set<String> urlsToIgnore = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

			for (int i = 0; i < numberOfCrawlers; i++) {
				new SiteCrawler(horizon, urlsToIgnore, pageQueue, videoQueue).start();
			}

			File targetDirectory = new File(target);
			for (int i = 0; i < numberOfDownloaders; i++) {
				new VideoDownloadListener(videoQueue, targetDirectory).start();
			}

			pageQueue.add(new PageQueueElement(url, 0));
		}
	}

}
