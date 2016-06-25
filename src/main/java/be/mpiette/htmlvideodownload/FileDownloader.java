package be.mpiette.htmlvideodownload;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class FileDownloader {
	private final static int CONNECTION_TIMEOUT = 5000;
	private final static int READ_TIMEOUT = 5000;

	private final URL url;
	private final File destinationDirectory;

	public FileDownloader(String url, String destination) throws MalformedURLException {
		super();
		this.url = new URL(url.startsWith("//") ? "http:" + url : url);
		this.destinationDirectory = new File(destination);
	}

	public void download(boolean overrideIfExists) throws IOException {
		String fileName = url.getPath().replaceAll("[^A-Za-z0-9]", "-");
		File destination = new File(destinationDirectory.getAbsolutePath() + File.separator + fileName);
		if (overrideIfExists || !destination.exists()) {
			FileUtils.copyURLToFile(url, destination, CONNECTION_TIMEOUT, READ_TIMEOUT);
		}
	}
}
