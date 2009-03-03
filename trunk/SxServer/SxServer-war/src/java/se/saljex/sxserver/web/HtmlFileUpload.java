package se.saljex.sxserver.web;
import java.io.FileOutputStream;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public class HtmlFileUpload {

	private Hashtable parameters = new Hashtable();
	private byte[] fileBytes, bytes;
	private final int FILE_SIZE_LIMIT = 1024*1024*50; // 50mb limit
	private String originalFileName = null;
	private String contentType = null;

	public HtmlFileUpload(HttpServletRequest request) throws IOException {

		ServletInputStream in = request.getInputStream();
		int contentLength = request.getContentLength();
		Vector vBytes = new Vector();
		Vector vFileBytes = new Vector();
		byte[] b = new byte[1];
		int paramCount = 0;
		
		// if the file they are trying to upload is too big, throw an exception
		if(contentLength > FILE_SIZE_LIMIT) throw new IOException("File has exceeded size limit.");
		// Check that theree is something attached
			if(contentLength > 0) {
			// read the request into a vector of Bytes
			while(in.read(b) > -1) vBytes.add(new Byte(b[0]));

			// create a byte array from the vector
			bytes = new byte[vBytes.size()];
			for(int i = 0; i < bytes.length; i++) {

				Byte temp = (Byte)vBytes.get(i);
				bytes[i] = temp.byteValue();
			}

			// data string that will be used to hack things up into pieces
			String data = new String(bytes, "ISO-8859-1");
			// the boundry is the first line that occurs
			String boundary = data.substring(0,data.indexOf('\n'));
			// all the elements are separated by the boundary
			String[] elements = data.split(boundary);

System.out.println("Elements length: " + elements.length);

			for(int i = 0; i < elements.length; i++) {
				if(elements[i].length() > 0) {
System.out.println("Element : " + i + " " + elements[i]);

					String[] descval = elements[i].split("\n");
System.out.println("descval length: " + descval.length);

					// if it's got more than 4 lines, it's a file
					if(descval.length > 4) {

						// take the first line of this element and split it by ";"
						String[] disp = descval[1].split(";");
System.out.println("disp length: " + disp.length);
						// the long file name is the second part of the first line.. take only what is between the quotes
						String longFileName = disp[2].substring(disp[2].indexOf('"')+1,disp[2].length()-2).trim();
					//	parameters.put("longFileName",longFileName);
						// the fileName is the longFileName without the directorys
						originalFileName = longFileName.substring(longFileName.lastIndexOf("\\")+1,longFileName.length());
						//parameters.put("originalfilename",longFileName.substring(longFileName.lastIndexOf("\\")+1,longFileName.length()));
						// gab the content type from the second line in this element
						contentType = descval[2].substring(descval[2].indexOf(' ')+1,descval[2].length()-1);
						//parameters.put("contenttype",descval[2].substring(descval[2].indexOf(' ')+1,descval[2].length()-1));

						int pos = 0;
						int lineCount = 0;

						// count the lines and the bytes up to this point
						while(lineCount != ((paramCount+1) * 4)) {
								if((char)bytes[pos] == '\n') lineCount++;
								pos++;
						}
System.out.println("pos: " + pos + " linecount " + lineCount + " paramcount " + paramCount);

						// grab all the bytes from the current position all the way to
						// right befor the last boundary
						for(int k = pos; k < (bytes.length - boundary.length() - 4); k++) {

							// add each byte to the vFileBytes Vector
							vFileBytes.add(new Byte(bytes[k]));
						}

						// convert the Vector to a byte array
						fileBytes = new byte[vFileBytes.size()];
						for(int j = 0; j < fileBytes.length; j++) {

							Byte temp = (Byte)vFileBytes.get(j);
							fileBytes[j] = temp.byteValue();
						}

						//parameters.put("contentLength",""+fileBytes.length);

					// if it's got 4 lines, it's just a regular parameter
					} else if(descval.length == 4) {

						paramCount++;

						parameters.put(
							// key
							descval[1].substring(descval[1].indexOf('"')+1,descval[1].length()-2).trim(),
							// value
							descval[3].trim()
						);
					}
				}
			}
		} else {		// We have no multipart

		}
	}

	public boolean saveFile(String fileName) throws IOException {
		if (originalFileName == null) return false;
		FileOutputStream fos = new FileOutputStream(fileName);
		fos.write(fileBytes);
		fos.close();
		return true;
	}
	
	public byte[] getFile() { return fileBytes; }
	public Hashtable getParameters() { return parameters; }

	public String getContentType() {		return contentType;	}
	public String getOriginalFileName() {		return originalFileName;	}

	public String getFieldValue(String s) { return (String)parameters.get(s); }
	// returns the entire request as a string
	public String toString() { return new String(bytes); }
}
