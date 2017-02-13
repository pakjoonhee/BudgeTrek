package com.joon.pak.travelite.data;

import net.simonvt.schematic.annotation.Table;

/**
 * Created by joonheepak on 9/25/16.
 */

@net.simonvt.schematic.annotation.Database(version = Database.VERSION)
public class Database {

    public static final int VERSION = 5;
    @Table(DatabaseColumns.class)
    public static final String Trips = "Trips";

    @Table(CalendarColumns.class)
    public static final String Calendar = "Calendar";


    private Database(){}
}
