package filesize;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.apache.commons.net.ftp.*;

public class update {

	public static void main(String[] args) throws SQLException, SAXException, IOException, ParserConfigurationException

	{

		database db = new database();
		int id = 1993;
		@SuppressWarnings("unchecked")
		ArrayList<String> aa = db.updatefilesizev2(id);
		// ArrayList<String>aa= db.updatefilesizeruili(id);
		// System.out.println(aa);

		for (String i : aa) {
			// System.out.println(i);
			BigInteger ii = BigInteger.valueOf(getFile_size(i));

			db.updatefilesize(i, ii);
		}

		db.close();
	}

	public static long getFile_size(String file_location) throws IOException {
		// String
		// ftp_site=(String)schema.getTable("dataset").getAttribute("ftp_site");
		file_location = file_location.replace("ftp://penguin.genomics.cn", "ftp://parrot.genomics.cn/gigadb");

		String ftp_site = "parrot.genomics.cn";
		System.out.println(file_location);

		// String path = ftp_site + file_location;
		// FtpClient ftpClient = new FtpClient();
		file_location = file_location.trim();

		FTPClient ftpClient = new FTPClient();

		// ftpClient.openServer(ftp_site);
		ftpClient.connect(ftp_site);
		// ftpClient.connect(ftp_site1);//when use vpn
		long fileSize = -1;
		ftpClient.login("anonymous", "anonymous");
		ftpClient.enterLocalPassiveMode();
		// ftpClient.binary();;
		int beginIndex = 0;
		if (file_location.indexOf(ftp_site) != -1)
			beginIndex = file_location.indexOf(ftp_site) + ftp_site.length();
		String location = file_location.substring(beginIndex);
		String request = "SIZE " + location + "\r\n";
		// ftpClient.sendServer(request);
		ftpClient.sendCommand(request);
		// String temp = ftpClient.getResponseString();
		String temp = ftpClient.getReplyString();
		// int status = ftpClient.readServerResponse();

		int status = ftpClient.getReplyCode();
		if (status == 213) {
			String msg = ftpClient.getReplyString();
			// System.out.println(msg);
			fileSize = Long.parseLong(msg.substring(3).trim());
			// System.out.println(fileSize);
		} else {

			System.out.println("We can't get the file, please check its path: ");
			System.out.println(location);
		}
		ftpClient.disconnect();
		return fileSize;
	}

}
