package com.sourceallies.android.zonebeacon.data.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Model for linking together zones and buttons.
 */
public class ZoneButtonLink implements DatabaseTable {

    public static final String TABLE_ZONE_BUTTON_LINK = "zone_button_link";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ZONE_ID = "zone_id";
    public static final String COLUMN_BUTTON_ID = "button_id";

    public static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_ZONE_ID,
            COLUMN_BUTTON_ID
    };

    private static final String DATABASE_CREATE = "create table if not exists " +
            TABLE_ZONE_BUTTON_LINK + " (" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_ZONE_ID + " integer not null, " +
            COLUMN_BUTTON_ID + " integer not null" +
            ");";

    private static final String[] INDEXES = {
            "create index if not exists zone_button_button_id_index on " + TABLE_ZONE_BUTTON_LINK +
                    " (" + COLUMN_BUTTON_ID + ");",
            "create index if not exists zone_button_zone_id_index on " + TABLE_ZONE_BUTTON_LINK +
                    " (" + COLUMN_ZONE_ID + ");"
    };

    @Setter
    @Getter
    private int id;

    @Setter
    @Getter
    private int zoneId;

    @Setter
    @Getter
    private int buttonId;

    @Override
    public String getCreateStatement() {
        return DATABASE_CREATE;
    }

    @Override
    public String getTableName() {
        return TABLE_ZONE_BUTTON_LINK;
    }

    @Override
    public String[] getIndexStatements() {
        return INDEXES;
    }

    @Override
    public String[] getDefaultDataStatements() {
        return new String[0];
    }

}