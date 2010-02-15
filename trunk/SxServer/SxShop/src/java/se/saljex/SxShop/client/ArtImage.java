/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;

/**
 *
 * @author ulf
 */

//En bild som skalas om så att den passar inom angiven höjd/bredd
//Priorites ges på höjd
public class ArtImage extends Image {
	public ArtImage(String url, final int maxDim) {
		super(url);
//		setPixelSize(0, 0);	//Sätt här temporärt bara så att omskalningen vid färdiginläst bild inte ska bli så påtaglig
		resizeImage(maxDim);
		addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
				resizeImage(maxDim);
			}
		});
		addErrorHandler(new ErrorHandler() {
			public void onError(ErrorEvent event) {
				removeFromParent();
			}
		});
	}

	public void resizeImage(int maxDim) {
		int h = getHeight();
		int w = getWidth();
		if ( h > 0 && w > 0) {		// Om någon storlek är 0  kan det betyda att bilden inte är laddad. Hursomhelst gör vi inget med 0-storlek
			if (w > maxDim) {
				setPixelSize(maxDim, Math.round(maxDim * h/w));
			} else {
				setPixelSize(Math.round(maxDim * w/h), maxDim);
			}
		}
	}

}
