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

package com.sourceallies.android.zonebeacon.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;
import com.sourceallies.android.zonebeacon.R;


import lombok.Getter;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_get_help)
public class GetHelpActivity extends RoboAppCompatActivity {

    @Getter
    private Link videoLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        
        buildLinkToSetupVideo();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Create a clickable link to the YouTube app for help setting up your hardware.
     */
    private void buildLinkToSetupVideo() {
        videoLink = new Link("this YouTube channel")
                .setUnderlined(false)
                .setOnClickListener(getClickListener(this));

        // make the text view clickable with the LinkBuilder library
        LinkBuilder
                .on((TextView) findViewById(R.id.gateway_description))
                .addLink(videoLink)
                .build();
    }

    @VisibleForTesting
    protected Link.OnClickListener getClickListener(final Activity activity) {
        return new Link.OnClickListener() {
            @Override
            public void onClick(String clickedText) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.youtube.com/playlist?list=PL4A84439A46A149C0"));
                activity.startActivity(i);
            }
        };
    }
}
