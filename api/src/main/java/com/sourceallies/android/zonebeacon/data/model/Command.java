/*
 * Copyright (C) 2016 Source Allies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sourceallies.android.zonebeacon.data.model;

import android.database.Cursor;

import lombok.Getter;
import lombok.Setter;

/**
 * Model for holding information on executing commands to activate or deactivate lights.
 */
public class Command implements DatabaseTable {

    public static final String TABLE = "command";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GATEWAY_ID = "gateway_id";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_COMMAND_TYPE_ID = "command_type_id";
    public static final String COLUMN_CONTROLLER_NUMBER = "controller_number";

    public static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_GATEWAY_ID,
            COLUMN_NUMBER,
            COLUMN_COMMAND_TYPE_ID,
            COLUMN_CONTROLLER_NUMBER
    };

    private static final String DATABASE_CREATE = "create table if not exists " +
            TABLE + " (" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NAME + " varchar(255) not null, " +
            COLUMN_GATEWAY_ID + " integer not null, " +
            COLUMN_NUMBER + " integer not null, " +
            COLUMN_COMMAND_TYPE_ID + " integer not null, " +
            COLUMN_CONTROLLER_NUMBER + " integer" +
            ");";

    private static final String[] INDEXES = {
            "create index if not exists gateway_id_index on " + TABLE +
                    " (" + COLUMN_GATEWAY_ID + ");",
            "create index if not exists command_type_id_index on " + TABLE +
                    " (" + COLUMN_COMMAND_TYPE_ID + ");"
    };

    @Setter
    @Getter
    private long id;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private long gatewayId;

    @Setter
    @Getter
    private int number;

    @Setter
    @Getter
    private long commandTypeId;

    @Setter
    @Getter
    private Integer controllerNumber;

    @Setter
    @Getter
    private CommandType commandType;

    @Override
    public String getCreateStatement() {
        return DATABASE_CREATE;
    }

    @Override
    public String getTableName() {
        return TABLE;
    }

    @Override
    public String[] getIndexStatements() {
        return INDEXES;
    }

    @Override
    public String[] getDefaultDataStatements() {
        return new String[0];
    }

    @Override
    public void fillFromCursor(Cursor cursor) {
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            String column = cursor.getColumnName(i);

            if (column.equals(COLUMN_ID)) {
                setId(cursor.getLong(i));
            } else if (column.equals(COLUMN_NAME)) {
                setName(cursor.getString(i));
            } else if (column.equals(COLUMN_GATEWAY_ID)) {
                setGatewayId(cursor.getLong(i));
            } else if (column.equals(COLUMN_NUMBER)) {
                setNumber(cursor.getInt(i));
            } else if (column.equals(COLUMN_COMMAND_TYPE_ID)) {
                setCommandTypeId(cursor.getLong(i));
            } else if (column.equals(COLUMN_CONTROLLER_NUMBER)) {
                try {
                    Integer controllerNumber = Integer.parseInt(cursor.getString(i));
                    setControllerNumber(controllerNumber);
                } catch (Exception e) {
                    setControllerNumber(null);
                }
            }
        }

        CommandType commandType = new CommandType();
        commandType.fillFromCursor(cursor);
        setCommandType(commandType);
    }

    @Override
    public String toString() {
        return getName();
    }

}
