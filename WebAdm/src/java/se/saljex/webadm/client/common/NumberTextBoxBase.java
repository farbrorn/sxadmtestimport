/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common;

/**
 *
 * @author Ulf
 */
import com.google.gwt.dom.client.Element;
import com.google.gwt.text.client.DoubleParser;
import com.google.gwt.text.client.DoubleRenderer;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.ValueBoxBase;

/**
 * Abstract base class for most text entry widgets.
 *
 * <p>
 * The names of the static members of {@link TextBoxBase}, as well as simple
 * alignment names (<code>left</code>, <code>center</code>, <code>right</code>,
 * <code>justify</code>), can be used as values for a <code>textAlignment</code>
 * attribute.
 * <p>
 * For example,
 *
 * <pre>
 * &lt;g:TextBox textAlignment='ALIGN_RIGHT'/&gt;
 * &lt;g:TextBox textAlignment='right'/&gt;
 * </pre>
 */
public class NumberTextBoxBase<T> extends ValueBoxBase<T> implements
    SourcesChangeEvents {



  /**
   * Creates a text box that wraps the given browser element handle. This is
   * only used by subclasses.
   *
   * @param elem the browser element to wrap
   */
  protected NumberTextBoxBase(Element elem, Renderer<T> renderer, Parser<T> parser) {
	  super(elem, renderer, parser);

    //super(elem, DoubleRenderer.instance(), DoubleParser.instance());
  }

  /**
   * @deprecated Use {@link #addChangeHandler} instead
   */
  @Deprecated
  public void addChangeListener(ChangeListener listener) {
  }

  /**
   * Overridden to return "" from an empty text box.
   */
  @Override
  public T getValue() {
    return super.getValue();
  }

  /**
   * Legacy wrapper for {@link #setAlignment(TextAlignment)}.
   *
   * @deprecated use {@link #setAlignment(TextAlignment)}
   */
}