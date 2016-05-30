package fnmcore.constants;

import xml.SimpleFileXMLDocumentHandler;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 9, 2015, 11:50:53 PM 
 */
public class ApplicationConstantsDocumentHandler extends SimpleFileXMLDocumentHandler {
	public ApplicationConstantsDocumentHandler( ApplicationConstants con ) {
		this.val = con;
		FILE = "Settings";
		ROOT_NODE_NAME = "FREENASMONITOR";
		EXT = ".fnm";
		READABLE = "FreeNAS Monitor Config";
		DIR = ".";
		init( null, EXT, READABLE + " (" + EXT + ")", DIR, ROOT_NODE_NAME );
	}
}