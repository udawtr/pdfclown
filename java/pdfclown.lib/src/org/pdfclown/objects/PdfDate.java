/*
  Copyright 2006-2011 Stefano Chizzolini. http://www.pdfclown.org

  Contributors:
    * Stefano Chizzolini (original code developer, http://www.stefanochizzolini.it)

  This file should be part of the source code distribution of "PDF Clown library"
  (the Program): see the accompanying README files for more info.

  This Program is free software; you can redistribute it and/or modify it under the terms
  of the GNU Lesser General Public License as published by the Free Software Foundation;
  either version 3 of the License, or (at your option) any later version.

  This Program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY,
  either expressed or implied; without even the implied warranty of MERCHANTABILITY or
  FITNESS FOR A PARTICULAR PURPOSE. See the License for more details.

  You should have received a copy of the GNU Lesser General Public License along with this
  Program (see README files); if not, go to the GNU website (http://www.gnu.org/licenses/).

  Redistribution and use, with or without modification, are permitted provided that such
  redistributions retain the above copyright notice, license and disclaimer, along with
  this list of conditions.
*/

package org.pdfclown.objects;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.pdfclown.tokens.Encoding;

/**
  PDF date object [PDF:1.6:3.8.3].

  @author Stefano Chizzolini (http://www.stefanochizzolini.it)
  @version 0.1.1, 04/19/11
*/
public final class PdfDate
  extends PdfString
{
  // <class>
  // <static>
  // <fields>
  protected static final SimpleDateFormat formatter;
  // </fields>

  // <constructors>
  static
  {formatter = new SimpleDateFormat("yyyyMMddHHmmssZ");}
  // </constructors>

  // <interface>
  // <public>
  /**
    Gets the object equivalent to the given value.
  */
  public static PdfDate get(
    Date value
    )
  {return value == null ? null : new PdfDate(value);}

  /**
    Converts a PDF-formatted date value to a date value.
  */
  public static Date toDate(
    String value
    )
  {
    StringBuilder dateBuilder = new StringBuilder();
    {
      int length = value.length();
      // Year (YYYY).
      dateBuilder.append(value.substring(2, 6)); // NOTE: Skips the "D:" prefix; Year is mandatory.
      // Month (MM).
      dateBuilder.append(length < 7 ? "01" : value.substring(6, 8));
      // Day (DD).
      dateBuilder.append(length < 9 ? "01" : value.substring(8, 10));
      // Hour (HH).
      dateBuilder.append(length < 11 ? "00" : value.substring(10, 12));
      // Minute (mm).
      dateBuilder.append(length < 13 ? "00" : value.substring(12, 14));
      // Second (SS).
      dateBuilder.append(length < 15 ? "00" : value.substring(14, 16));
      // Local time / Universal Time relationship (O).
      dateBuilder.append(length < 16 || value.substring(16, 17).equals("Z") ? "+" : value.substring(16, 17));
      // UT Hour offset (HH').
      dateBuilder.append(length < 19 ? "00" : value.substring(17, 19));
      // UT Minute offset (mm').
      dateBuilder.append(length < 22 ? "00" : value.substring(20, 22));
    }
    try
    {return formatter.parse(value);}
    catch(Exception e)
    {throw new RuntimeException(e);}
  }
  // </public>
  // </interface>
  // </static>

  // <dynamic>
  // <constructors>
  public PdfDate(
    )
  {}

  public PdfDate(
    Date value
    )
  {setValue(value);}
  // </constructors>

  // <interface>
  // <public>
  @Override
  public Date getValue(
    )
  {return toDate(Encoding.decode(getRawValue()));}
  // </public>

  // <protected>
  @Override
  protected void setValue(
    Object value
    )
  {
    byte[] buffer = new byte[23];
    {
      byte[] valueBytes = Encoding.encode(formatter.format(value));
      buffer[0] = 68; buffer[1] = 58;
      System.arraycopy(valueBytes, 0, buffer, 2, 17);
      buffer[19] = 39;
      System.arraycopy(valueBytes, 17, buffer, 20, 2);
      buffer[22] = 39;
    }
    setRawValue(buffer);
  }
  // </protected>
  // </interface>
  // </dynamic>
  // </class>
}