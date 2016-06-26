package be.mpiette.htmlvideodownload;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import be.mpiette.htmlvideodownload.queue.PageQueue;
import be.mpiette.htmlvideodownload.queue.PageQueueElement;
import be.mpiette.htmlvideodownload.queue.VideoQueue;

public class HtmlVideoDownload {

	public static void main(String[] args) throws Exception {
		Options options = new Options();

		options.addOption("url", true, "Url");
		options.addOption("horizon", true, "Horizon");
		options.addOption("target", true, "Target directory");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		if (cmd.hasOption("url") && cmd.hasOption("horizon") && cmd.hasOption("target")) {
			URL url = new URL(cmd.getOptionValue("url"));
			Integer horizon = Integer.valueOf(cmd.getOptionValue("horizon"));
			String target = cmd.getOptionValue("target");

			VideoQueue videoQueue = new VideoQueue();
			PageQueue pageQueue = new PageQueue();

			Set<String> urlsToIgnore = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

			new SiteCrawler(horizon, urlsToIgnore, pageQueue, videoQueue).start();
			new SiteCrawler(horizon, urlsToIgnore, pageQueue, videoQueue).start();
			new SiteCrawler(horizon, urlsToIgnore, pageQueue, videoQueue).start();
			new SiteCrawler(horizon, urlsToIgnore, pageQueue, videoQueue).start();
			new SiteCrawler(horizon, urlsToIgnore, pageQueue, videoQueue).start();

			File targetDirectory = new File(target);
			new VideoDownloadListener(videoQueue, targetDirectory).start();
			new VideoDownloadListener(videoQueue, targetDirectory).start();
			new VideoDownloadListener(videoQueue, targetDirectory).start();
			new VideoDownloadListener(videoQueue, targetDirectory).start();
			new VideoDownloadListener(videoQueue, targetDirectory).start();

			pageQueue.add(new PageQueueElement(url, 0));
		}
	}

}
