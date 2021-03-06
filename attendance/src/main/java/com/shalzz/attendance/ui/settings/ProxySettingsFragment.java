/*
 * Copyright (c) 2013-2016 Shaleen Jain <shaleen.jain95@gmail.com>
 *
 * This file is part of UPES Academics.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.shalzz.attendance.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.bugsnag.android.Bugsnag;
import com.shalzz.attendance.MyApplication;
import com.shalzz.attendance.R;
import com.shalzz.attendance.ui.main.MainActivity;

public class ProxySettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private String key_proxy_username;
    private MainActivity mainActivity;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        Context mContext = getActivity();
        MyApplication.get(mContext).getComponent().inject(this);
        Bugsnag.setContext("ProxySettings");
        mainActivity = ((MainActivity) getActivity());
        mainActivity.setDrawerAsUp(true);

        addPreferencesFromResource(R.xml.pref_proxy);

        key_proxy_username = getString(R.string.pref_key_proxy_username);
        Preference connectionPref = findPreference(key_proxy_username);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        connectionPref.setSummary(sharedPref.getString(key_proxy_username, ""));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(key_proxy_username)) {
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key, ""));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        mainActivity.setTitle(getString(R.string.proxy_settings_title));
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }
}
