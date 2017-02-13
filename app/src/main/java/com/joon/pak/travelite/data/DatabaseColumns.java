package com.joon.pak.travelite.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by joonheepak on 9/25/16.
 */
public class DatabaseColumns {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    public static final String _ID = "_id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String Trip_Name = "tripname";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String Start_Date = "startdate";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String Currency_Symbol = "currencysymbol";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String End_Date = "enddate";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String Budget = "budget";

    @DataType(DataType.Type.BLOB)
    @NotNull
    public static final String Country_Flag = "countryflag";

    @DataType(DataType.Type.BLOB)
    @NotNull
    public static final String Background_Image = "backgroundimage";

}


