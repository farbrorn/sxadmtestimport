/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package se.saljex.webadm.client.common;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.text.shared.Parser;

import java.text.ParseException;
import java.util.Date;

/**
 * A localized parser based on {@link NumberFormat#getDecimalFormat}.
 */
public class DateTimeParser implements Parser<Date> {
  private final DateTimeFormat format;

  private static DateTimeParser INSTANCE;

  /**
   * Returns the instance of the no-op renderer.
   */
  public static Parser<Date> instance() {
    if (INSTANCE == null) {
      INSTANCE = new DateTimeParser();
    }
    return INSTANCE;
  }

  protected DateTimeParser() {
	  this(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT));
  }
	public DateTimeParser(DateTimeFormat format) {
	  this.format = format;
	}

  public Date parse(CharSequence object) throws ParseException {
    if ("".equals(object.toString())) {
      return null;
    }

    try {
      return format.parse(object.toString());
    } catch (IllegalArgumentException e) {
      throw new ParseException(e.getMessage(), 0);
    }
  }
}
