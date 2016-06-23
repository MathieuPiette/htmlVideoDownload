package be.mpiette.htmlvideodownload;

import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

public class HtmlVideoDownload {

	public static void main(String[] args) throws Exception {
		Options options = new Options();

		options.addOption("u", true, "Url");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		if (cmd.hasOption("u")) {
			String url = cmd.getOptionValue("u");
			System.out.println(url);

			SiteCrawler crawler = new SiteCrawler(url, 0);
			Map<String, Page> pages = crawler.crawl();

			System.out.println(pages);
		}
	}

}
