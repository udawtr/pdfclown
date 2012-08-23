/*
  Copyright 2008-2012 Stefano Chizzolini. http://www.pdfclown.org

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

package org.pdfclown.documents.interaction.forms;

import org.pdfclown.PDF;
import org.pdfclown.VersionEnum;
import org.pdfclown.documents.Document;
import org.pdfclown.documents.interaction.annotations.Widget;
import org.pdfclown.objects.PdfArray;
import org.pdfclown.objects.PdfDirectObject;
import org.pdfclown.objects.PdfName;
import org.pdfclown.util.EnumUtils;
import org.pdfclown.util.NotImplementedException;

/**
  Choice field [PDF:1.6:8.6.3].

  @author Stefano Chizzolini (http://www.stefanochizzolini.it)
  @since 0.0.7
  @version 0.1.2, 08/23/12
*/
@PDF(VersionEnum.PDF12)
public abstract class ChoiceField
  extends Field
{
  // <class>
  // <dynamic>
  // <constructors>
  /**
    Creates a new choice field within the given document context.
  */
  protected ChoiceField(
    String name,
    Widget widget
    )
  {super(PdfName.Ch, name, widget);}

  protected ChoiceField(
    PdfDirectObject baseObject
    )
  {super(baseObject);}
  // </constructors>

  // <interface>
  // <public>
  @Override
  public ChoiceField clone(
    Document context
    )
  {throw new NotImplementedException();}

  public ChoiceItems getItems(
    )
  {return new ChoiceItems(getBaseDataObject().get(PdfName.Opt, PdfArray.class));}

  /**
    Gets whether more than one of the field's items may be selected simultaneously.
  */
  public boolean isMultiSelect(
    )
  {return getFlags().contains(FlagsEnum.MultiSelect);}

  /**
    Gets whether validation action is triggered as soon as a selection is made,
    without requiring the user to exit the field.
  */
  public boolean isValidatedOnChange(
    )
  {return getFlags().contains(FlagsEnum.CommitOnSelChange);}

  /**
    @see #getItems()
  */
  public void setItems(
    ChoiceItems value
    )
  {getBaseDataObject().put(PdfName.Opt, value.getBaseObject());}

  /**
    @see #isMultiSelect()
  */
  public void setMultiSelect(
    boolean value
    )
  {setFlags(EnumUtils.mask(getFlags(), FlagsEnum.MultiSelect, value));}

  /**
    @see #isValidatedOnChange()
  */
  public void setValidatedOnChange(
    boolean value
    )
  {setFlags(EnumUtils.mask(getFlags(), FlagsEnum.CommitOnSelChange, value));}
  // </public>
  // </interface>
  // </dynamic>
  // </class>
}