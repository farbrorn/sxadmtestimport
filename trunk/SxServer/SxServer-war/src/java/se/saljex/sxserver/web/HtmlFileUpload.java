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
	private int paramCount = 0;
	private String boundary;
	private Vector vFileBytes;
	private int scannadeRader = 0;
	private String[] descval;

	public HtmlFileUpload(HttpServletRequest request) throws IOException {

		ServletInputStream in = request.getInputStream();
		int contentLength = request.getContentLength();
		Vector vBytes = new Vector();
		vFileBytes = new Vector();
		byte[] b = new byte[1];
		
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
//FileOutputStream fos2 = new FileOutputStream("c:/dum/formdump");
//fos2.write(bytes);
//fos2.close();

			// data string that will be used to hack things up into pieces
			String data = new String(bytes, "ISO-8859-1");
			// the boundry is the first line that occurs
			boundary = data.substring(0,data.indexOf('\n'));
			// all the elements are separated by the boundary
			String[] elements = data.split(boundary);

//System.out.print("data: " + data);
//System.out.print("boundry: " + boundary);
//System.out.print("Element antal: " + elements.length);
//for (int cn = 0 ; cn < elements.length; cn++) System.out.print("Element " + cn + ":" + elements[cn]);

			scannadeRader = 0;
			for(int i = 0; i < elements.length; i++) {
//System.out.print("element " + i + " " + elements[i]);
				if(elements[i].length() > 0) {
	//				scannadeRader = scannadeRader+1;	//Räkna raden med Boundry

					descval = elements[i].split("\n");
//System.out.print("descvan antal: " + descval.length);
//for (int cn=0; cn < descval.length; cn++) System.out.print("descval " + cn + " " + descval[cn] + ":längd: " + descval[cn].length());
					// if it's got more than 4 lines, it's a file
					// Men kan likväl vara en anna parameter typ textarea
					if(descval.length > 4) {

						// take the first line of this element and split it by ";"
						String[] disp = descval[1].split(";");
//System.out.print("disp antal: " + disp.length);
						// Om det är en fil så har vi filnamnet i delsträng 2 likt: Content-Disposition: form-data; name="filnamn"; filename=""
						// Om det är en parameter ser det ut såhär: Content-Disposition: form-data; name="notering"
						if (disp.length <= 2) { // Det är en parameter
							paramCount++;

							parameters.put(
								// key
								descval[1].substring(descval[1].indexOf('"')+1,descval[1].length()-2).trim(),
								// value
								new String(getBytes(), "ISO-8859-1")
							);
						} else { // Det är en fil

							// the long file name is the second part of the first line.. take only what is between the quotes
							String longFileName = disp[2].substring(disp[2].indexOf('"')+1,disp[2].length()-2).trim();
						//	parameters.put("longFileName",longFileName);
							// the fileName is the longFileName without the directorys
							originalFileName = longFileName.substring(longFileName.lastIndexOf("\\")+1,longFileName.length());
							//parameters.put("originalfilename",longFileName.substring(longFileName.lastIndexOf("\\")+1,longFileName.length()));
							// gab the content type from the second line in this element
							contentType = descval[2].substring(descval[2].indexOf(' ')+1,descval[2].length()-1);
							//parameters.put("contenttype",descval[2].substring(descval[2].indexOf(' ')+1,descval[2].length()-1));
							scannadeRader++;			//Öka tillfälligt för att mata förbi raden med content-type, som står på extra rad när det är en fil
							fileBytes = getBytes();
							scannadeRader--;			//Sätt tillbaka temporär ökning. Ökning med samtliga rader sker senare
							//parameters.put("contentLength",""+fileBytes.length);
//FileOutputStream fos = new FileOutputStream("c:/dum/formdump-fil");
//fos.write(fileBytes);
//fos.close();
						}

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
					scannadeRader = scannadeRader + descval.length;		//Öka med antalet rader i descval
				}
			}
		} else {		// We have no multipart

		}
	}

	public byte[] getBytes() {
							int pos = 0;
							int lineCount = 0;
							vFileBytes.clear();

							// count the lines and the bytes up to this point
//							while(lineCount != ((paramCount+1) * 4)) {
//System.out.print("ScannadeRader:" + scannadeRader);
							while(lineCount != (scannadeRader+3)) {
									if((char)bytes[pos] == '\n') lineCount++;
									pos++;
							}

							// grab all the bytes from the current position all the way to
							// right befor the last boundary
							// Räkna bytes i detta element
							int elementBytes=0;
							for (int cn = 3 ; cn < descval.length ; cn++) {
								elementBytes = elementBytes+descval[cn].length()+1; //Lägg till 1 fö \n som inte räknas annars
							}

//System.out.print("bytes.length:" + bytes.length);
//System.out.print("elementBytes:" + elementBytes);
//System.out.print("pos:" + pos);
//System.out.print("pos + elementBytes-1: " + (pos + elementBytes-1));

//							for(int k = pos; k < (bytes.length - boundary.length() - 4); k++) {
							for(int k = pos; (k < (pos + elementBytes-1)) && k < (bytes.length - boundary.length() - 5); k++) {

								// add each byte to the vFileBytes Vector
								vFileBytes.add(new Byte(bytes[k]));
							}

							// convert the Vector to a byte array
							fileBytes = new byte[vFileBytes.size()];
							for(int j = 0; j < fileBytes.length; j++) {

								Byte temp = (Byte)vFileBytes.get(j);
								fileBytes[j] = temp.byteValue();
							}
//System.out.print("fileBytes:" + new String(fileBytes));
							return fileBytes;
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
