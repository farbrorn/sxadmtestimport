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
		// Om getheight==0 så har vi förmodligen inte laddat bilden än.
		// Vi döljer den då, och om den redan är cashad så sätter vi storleken direkt
		//eftersom det inte blir loadevent då
		if (getHeight()==0) setVisible(false); else resizeImage(maxDim);
		addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
				resizeImage(maxDim);
				setVisible(true);
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
		if ( h > 0 && w > 0) {		// Om någon storlek är 0  kan det betyda att bilden inte är laddad.
			if (w > h) {
				setPixelSize(maxDim, Math.round(maxDim * h/w));
			} else {
				setPixelSize(Math.round(maxDim * w/h), maxDim);
			}
		} 	else setPixelSize(maxDim, maxDim);
		// Ibland sätts inte height och width när bilden är laddad. Verkar vara så i IE...
		//För att inte kluttra ner läsaren med stora bilder sätts här maxdimensionen och vi får stå ut med felproportioner
	}

}
