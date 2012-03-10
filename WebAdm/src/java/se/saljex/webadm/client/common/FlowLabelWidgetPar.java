/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.webadm.client.common;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import se.saljex.webadm.client.commmon.constants.Const;

/**
 *
 * @author Ulf
 */
public class FlowLabelWidgetPar extends FlowPanel{

	private Widget label;
	private Widget widget;
	private String labelWidth=null;
	private String widgetWidth=null;
	
	
	
	public FlowLabelWidgetPar(Widget label, Widget widget, String labelWidth, String widgetWidth) {
		this.label=label;
		this.widget=widget;
		this.labelWidth=labelWidth;
		this.widgetWidth=widgetWidth;
		this.addStyleName(Const.Style_FlowLabelWidgetPar);
		addWidgets();
	}
	
	public FlowLabelWidgetPar(Widget label, Widget widget) {
		this(label, widget,null,null);
	}

	public FlowLabelWidgetPar(String label, Widget widget) {
		this(new Label(label), widget);
	}
	public FlowLabelWidgetPar(String label, Widget widget, String labelWidth, String widgetWidth) {
		this(new Label(label), widget, labelWidth, widgetWidth);
	}
	
	
	public Widget getWidget() {return widget; }
	public void setWidget(Widget widget) {
		this.widget=widget;
		addWidgets();
	}
	public Widget getLabel() {return label; }
	public void setLabel(Widget label) {
		this.label=label;
		addWidgets();
	}
	
	private void addWidgets() {
		this.clear();
		if (label!=null) {
			this.label.addStyleName(Const.Style_FlowLabelWidgetPar_Label);
			if (labelWidth!=null) label.setWidth(labelWidth);
			this.add(label);
		}
		if (widget!=null) {
			this.widget.addStyleName(Const.Style_FlowLabelWidgetPar_Widget);
			if (widgetWidth!=null) widget.setWidth(widgetWidth);
			this.add(widget);
		}
	}
	
}
