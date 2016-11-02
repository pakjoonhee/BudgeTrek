package com.example.joonheepak.finalproject.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by joonheepak on 10/5/16.
 */

public class CalendarColumns {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    public static final String _ID = "id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String Trip_Name = "tripname";

    @DataType(DataType.Type.TEXT)
    public static final String Month_Day = "monthday";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    public static final String Icon = "icon";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String Budget = "budget";

    @DataType(DataType.Type.TEXT)
    public static final String Specific_Budget = "specificbudget";

    @DataType(DataType.Type.TEXT)
    public static final String Budget_Name = "budgetname";

}
