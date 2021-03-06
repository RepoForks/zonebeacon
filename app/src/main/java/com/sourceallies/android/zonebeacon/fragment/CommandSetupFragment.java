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

package com.sourceallies.android.zonebeacon.fragment;


import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.sourceallies.android.zonebeacon.R;
import com.sourceallies.android.zonebeacon.adapter.CommandTypeSpinnerAdapter;
import com.sourceallies.android.zonebeacon.data.DataSource;
import com.sourceallies.android.zonebeacon.data.model.CommandType;

import java.util.List;

import lombok.Getter;

public class CommandSetupFragment extends AbstractSetupFragment {

    public CommandSetupFragment() { }

    @Getter
    private TextInputLayout name;

    @Getter
    private TextInputLayout loadNumber;

    @Getter
    private TextInputLayout controllerNumber;

    @Getter
    private Spinner commandTypeSpinner;

    private CommandTypeSpinnerAdapter commandTypeSpinnerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_command, container, false);

        name = (TextInputLayout) root.findViewById(R.id.name);
        loadNumber = (TextInputLayout)root.findViewById(R.id.load_number);
        commandTypeSpinner = (Spinner) root.findViewById(R.id.command_type);
        controllerNumber = (TextInputLayout)root.findViewById(R.id.controller_number);

        setCommandSpinnerAdapter();

        return root;
    }

    @Override
    public boolean isComplete() {
        boolean complete = true;

        if (isEmpty(name)) complete = false;
        if (isEmpty(loadNumber)) complete = false;
        if (isEmpty(controllerNumber)) complete = false;

        return complete;
    }

    @VisibleForTesting
    protected void insertNewCommand(DataSource source, String name, Long gatewayId, Integer loadNumber, Long commandTypeId, Integer controllerNumber) {
        source.open();
        source.insertNewCommand(name, gatewayId, loadNumber, commandTypeId, controllerNumber);
        source.close();
    }

    @Override
    public void save() {
        String name = getText(this.name);
        int loadNumber = Integer.parseInt((getText(this.loadNumber)));
        CommandType currentCommandType = commandTypeSpinnerAdapter.getItem(commandTypeSpinner.getSelectedItemPosition());
        Integer controllerNum = null;

        if (controllerNumber.getVisibility() == View.VISIBLE) {
            controllerNum = Integer.parseInt((getText(this.controllerNumber)));
        }

        insertNewCommand(getDataSource(), name, getCurrentGateway().getId(), loadNumber, currentCommandType.getId(), controllerNum);
    }

    @VisibleForTesting
    protected List<CommandType> findCommandTypes(DataSource source) {
        source.open();
        List<CommandType> commandTypes = source.findCommandTypesShownInUI(getCurrentGateway());
        source.close();

        return commandTypes;
    }

    @VisibleForTesting
    protected DataSource getDataSource() {
        return DataSource.getInstance(getActivity());
    }

    private void setCommandSpinnerAdapter() {
        commandTypeSpinnerAdapter = new CommandTypeSpinnerAdapter(getActivity(), findCommandTypes(getDataSource()));
        commandTypeSpinner.setAdapter(commandTypeSpinnerAdapter);

        commandTypeSpinner.setOnItemSelectedListener(getItemSelectedListener());
    }

    @VisibleForTesting
    protected AdapterView.OnItemSelectedListener getItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override public void onNothingSelected(AdapterView<?> parent) { }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (commandTypeSpinnerAdapter.getItem(position).isActivateControllerSelection()) {
                    controllerNumber.setVisibility(View.VISIBLE);
                } else {
                    controllerNumber.setVisibility(View.INVISIBLE);
                }
            }
        };
    }
}
