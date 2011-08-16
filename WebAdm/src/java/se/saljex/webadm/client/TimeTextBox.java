/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.text.client.DateTimeFormatRenderer;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ValueBox;
import java.text.ParseException;
import java.util.Date;

/**
 * A standard single-line text box.
 *
 * <p>
 * <img class='gallery' src='doc-files/TextBox.png'/>
 * </p>
 *
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.gwt-TextBox { primary style }</li>
 * <li>.gwt-TextBox-readonly { dependent style set when the text box is
 * read-only }</li>
 * </ul>
 *
 * <p>
 * <h3>Built-in Bidi Text Support</h3>
 * This widget is capable of automatically adjusting its direction according to
 * the input text. This feature is controlled by {@link #setDirectionEstimator},
 * and is available by default when at least one of the application's locales is
 * right-to-left.
 * </p>
 *
 * <p>
 * <h3>Example</h3>
 * {@example com.google.gwt.examples.TextBoxExample}
 * </p>
 */
public class TimeTextBox extends ValueBox<Date> {

  /**
   * Creates a TextBox widget that wraps an existing &lt;input type='text'&gt;
   * element.
   *
   * This element must already be attached to the document. If the element is
   * removed from the document, you must call
   * {@link RootPanel#detachNow(Widget)}.
   *
   * @param element the element to be wrapped
   */

   private Date prevValue=null;

  public static TimeTextBox wrap(Element element) {
    // Assert that the element is attached.
    assert Document.get().getBody().isOrHasChild(element);

    TimeTextBox textBox = new TimeTextBox(element);

    // Mark it attached and remember it for cleanup.
    textBox.onAttach();
    RootPanel.detachOnWindowClose(textBox);

    return textBox;
  }

  /**
   * Creates an empty text box.
   */
  public TimeTextBox() {
    this(Document.get().createTextInputElement(), "gwt-TextBox");
  }

  /**
   * This constructor may be used by subclasses to explicitly use an existing
   * element. This element must be an &lt;input&gt; element whose type is
   * 'text'.
   *
   * @param element the element to be used
   */
  protected TimeTextBox(Element element) {
    super(element, new DateTimeFormatRenderer(DateTimeFormat.getFormat("H:m")), new DateTimeParser(DateTimeFormat.getFormat("H:m")));
    assert InputElement.as(element).getType().equalsIgnoreCase("text");
  }

  TimeTextBox(Element element, String styleName) {
    this(element);
    if (styleName != null) {
      setStyleName(styleName);
    }
  }

  /**
   * Gets the maximum allowable length of the text box.
   *
   * @return the maximum length, in characters
   */
  public int getMaxLength() {
    return getInputElement().getMaxLength();
  }

  /**
   * Gets the number of visible characters in the text box.
   *
   * @return the number of visible characters
   */
  public int getVisibleLength() {
    return getInputElement().getSize();
  }

  /**
   * Sets the maximum allowable length of the text box.
   *
   * @param length the maximum length, in characters
   */
  public void setMaxLength(int length) {
    getInputElement().setMaxLength(length);
  }

  /**
   * Sets the number of visible characters in the text box.
   *
   * @param length the number of visible characters
   */
  public void setVisibleLength(int length) {
    getInputElement().setSize(length);
  }

  private InputElement getInputElement() {
    return getElement().cast();
  }
}