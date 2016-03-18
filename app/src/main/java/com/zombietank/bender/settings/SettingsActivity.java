package com.zombietank.bender.settings;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.EditText;

import com.zombietank.bender.R;

import java.util.List;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || AuthenticationPreferenceCategory.class.getName().equals(fragmentName)
                || ValvesPreferenceCategory.class.getName().equals(fragmentName);
    }

    private static Preference.OnPreferenceChangeListener summaryUpdatingListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            if (preference instanceof EditTextPreference) {
                EditTextPreference editTextPreference = (EditTextPreference) preference;
                EditText editText = editTextPreference.getEditText();
                String pref = editText.getTransformationMethod().getTransformation(value.toString(), editText).toString();
                preference.setSummary(pref);
            }
            return true;
        }
    };

    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(summaryUpdatingListener);
        summaryUpdatingListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class AuthenticationPreferenceCategory extends PreferenceCategory {
        @Override
        protected int getPreferencesXmlResource() {
            return R.xml.pref_authentication;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            bindPreferenceSummaryToValue(findPreference("device_id"));
            bindPreferenceSummaryToValue(findPreference("email_address"));
            bindPreferenceSummaryToValue(findPreference("password"));
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class ValvesPreferenceCategory extends PreferenceCategory {
        @Override
        protected int getPreferencesXmlResource() {
            return R.xml.pref_valves;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            bindPreferenceSummaryToValue(findPreference("valve_one_type"));
            bindPreferenceSummaryToValue(findPreference("valve_two_type"));
            bindPreferenceSummaryToValue(findPreference("valve_three_type"));
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static abstract class PreferenceCategory extends PreferenceFragment {
        protected abstract int getPreferencesXmlResource();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(getPreferencesXmlResource());
            setHasOptionsMenu(true);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}
