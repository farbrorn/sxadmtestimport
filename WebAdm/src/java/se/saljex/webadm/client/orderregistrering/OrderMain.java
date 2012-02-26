/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.orderregistrering;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import se.saljex.webadm.client.commmon.constants.Const;

/**
 *
 * @author Ulf
 */
public class OrderMain extends FlowPanel{

	private final OrderHeaderWidget head = new OrderHeaderWidget();
	private final OrderFormWidget form = new OrderFormWidget();
	private final ScrollPanel formScroll = new ScrollPanel(form);

	public OrderMain() {
		head.setHeight("10em");
		formScroll.setHeight("30em");
		formScroll.addStyleName(Const.Style_FloatLeft);
		add(head);
		add(formScroll);
	}


}
