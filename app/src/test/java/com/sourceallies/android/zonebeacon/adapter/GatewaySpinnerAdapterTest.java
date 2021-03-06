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

package com.sourceallies.android.zonebeacon.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sourceallies.android.zonebeacon.R;
import com.sourceallies.android.zonebeacon.ZoneBeaconRobolectricSuite;
import com.sourceallies.android.zonebeacon.ZoneBeaconSuite;
import com.sourceallies.android.zonebeacon.data.model.Gateway;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GatewaySpinnerAdapterTest extends ZoneBeaconSuite {

    @Mock
    Activity activity;
    @Mock
    View view;
    @Mock
    TextView tv;
    @Mock
    LayoutInflater layoutInflater;

    List<Gateway> gateways = new ArrayList<>();
    GatewaySpinnerAdapter adapter;

    @Before
    public void setUp() {
        Gateway one = new Gateway();
        one.setId(1);
        one.setName("test 1");

        Gateway two = new Gateway();
        two.setId(2);
        two.setName("test 2");

        gateways.add(one);
        gateways.add(two);

        Mockito.when(activity.getString(R.string.create_gateway)).thenReturn("Create New Gateway");
        Mockito.when(view.findViewById(Mockito.anyInt())).thenReturn(tv);
        Mockito.when(activity.getLayoutInflater()).thenReturn(layoutInflater);
        Mockito.when(layoutInflater.inflate(Mockito.anyInt(), Mockito.any(ViewGroup.class), Mockito.anyBoolean()))
                .thenReturn(view);

        adapter = new GatewaySpinnerAdapter(activity, gateways);
    }

    @Test
    public void test_adapterSize() {
        assertEquals(gateways.size() + 1, adapter.getCount());
    }

    @Test
    public void test_noItems() {
        adapter = new GatewaySpinnerAdapter(activity, new ArrayList<Gateway>());
        assertEquals(1, adapter.getCount());
    }

    @Test
    public void test_title() {
        assertEquals("test 1", adapter.getTitle(0));
        assertEquals("test 2", adapter.getTitle(1));
        assertEquals("Create New Gateway", adapter.getTitle(2));
    }

    @Test
    public void test_getItemId() {
        assertEquals(0, adapter.getItemId(0));
        assertEquals(1, adapter.getItemId(1));
        assertEquals(2, adapter.getItemId(2));
    }

    @Test
    public void test_getItem() {
        assertEquals(1, adapter.getItem(0).getId());
        assertEquals(2, adapter.getItem(1).getId());
        assertEquals(null, adapter.getItem(2));
    }

    @Test
    public void test_newDropdownView() {
        adapter.getDropDownView(0, view, null);
        Mockito.verify(view).setTag(Mockito.eq("DROPDOWN"));
    }

    @Test
    public void test_newView() {
        adapter.getView(0, view, null);
        Mockito.verify(view).setTag(Mockito.eq("NON_DROPDOWN"));
    }

    @Test
    public void test_oldDropdownView() {
        Mockito.when(view.getTag()).thenReturn("DROPDOWN");

        adapter.getDropDownView(0, view, null);
        Mockito.verify(view, Mockito.times(0)).setTag(Mockito.eq("DROPDOWN"));
    }

    @Test
    public void test_oldView() {
        Mockito.when(view.getTag()).thenReturn("NON_DROPDOWN");

        adapter.getView(0, view, null);
        Mockito.verify(view, Mockito.times(0)).setTag(Mockito.eq("NON_DROPDOWN"));
    }

    @Test
    public void test_convertDropdownView() {
        Mockito.when(view.getTag()).thenReturn("NON_DROPDOWN");

        adapter.getDropDownView(0, view, null);
        Mockito.verify(view).setTag(Mockito.eq("DROPDOWN"));
    }

    @Test
    public void test_convertView() {
        Mockito.when(view.getTag()).thenReturn("DROPDOWN");

        adapter.getView(0, view, null);
        Mockito.verify(view).setTag(Mockito.eq("NON_DROPDOWN"));
    }
}