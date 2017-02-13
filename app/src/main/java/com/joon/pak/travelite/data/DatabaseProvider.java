package com.joon.pak.travelite.data;

/**
 * Created by joonheepak on 9/25/16.
 */
import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Valerio on 09/04/2016.
 */
@ContentProvider(authority = DatabaseProvider.AUTHORITY, database = Database.class)
public class DatabaseProvider {
    public static final String AUTHORITY = "com.joon.pak.travelite.data.DatabaseProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    interface Path {
        String TRIPS = "Trips";
        String CALENDAR = "Calendar";
    }

    @TableEndpoint(table = Database.Trips)
    public static class Trips {
        @ContentUri(
                path = Path.TRIPS,
                type = "vnd.android.cursor.dir/Trips"
        )
        public static final Uri CONTENT_URI = buildUri(Path.TRIPS);

        @InexactContentUri(
                name = "TRIPS_ID",
                path = Path.TRIPS + "/#",
                type = "vnd.android.cursor.trip_content/TRIPS",
                whereColumn = DatabaseColumns._ID,
                pathSegment = 1
        )
        public static Uri withId(int id) {

            return buildUri(Path.TRIPS, String.valueOf(id));
        }

    }
    @TableEndpoint(table = Database.Calendar)
    public static class Calendar {
        @ContentUri(
                path = Path.CALENDAR,
                type = "vnd.android.cursor.dir/Calendar"
        )
        public static final Uri CONTENT_URI = buildUri(Path.CALENDAR);

        @InexactContentUri(
                name = "CALENDAR_ID",
                path = Path.CALENDAR + "/#",
                type = "vnd.android.cursor.trip_content/TRIPS",
                whereColumn = DatabaseColumns._ID,
                pathSegment = 1
        )
        public static Uri withId(int id) {

            return buildUri(Path.CALENDAR, String.valueOf(id));
        }
    }



}