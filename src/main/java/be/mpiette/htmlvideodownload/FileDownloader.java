package be.mpiette.htmlvideodownload;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class FileDownloader {
	private final static int CONNECTION_TIMEOUT = 5000;
	private final static int READ_TIMEOUT = 5000;

	private final URL url;
	private final File targetDirectory;
	private final String fileName;

	public FileDownloader(URL url, File targetDirectory, String fileName) {
		super();
		this.url = url;
		this.targetDirectory = targetDirectory;
		this.fileName = fileName;
	}

	public void download(boolean overrideIfExists) throws IOException {
		File destination = new File(targetDirectory.getAbsolutePath() + File.separator + fileName);
		if (overrideIfExists || !destination.exists()) {
			FileUtils.copyURLToFile(url, destination, CONNECTION_TIMEOUT, READ_TIMEOUT);
		}
	}
}
