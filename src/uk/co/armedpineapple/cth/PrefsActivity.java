/*
 *   Copyright (C) 2012 Alan Woolley
 *   
 *   See LICENSE.TXT for full license
 */
package uk.co.armedpineapple.cth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import uk.co.armedpineapple.cth.R;

public class PrefsActivity extends PreferenceActivity implements
		SharedPreferences.OnSharedPreferenceChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Make sure we're using the correct preferences
		PreferenceManager prefMgr = getPreferenceManager();
		prefMgr.setSharedPreferencesName(CTHApplication.PREFERENCES_KEY);
		prefMgr.setSharedPreferencesMode(MODE_PRIVATE);

		// Save the configuration to the preferences to make sure that everything is
		// up-to-date

		final CTHApplication application = (CTHApplication) getApplication();
		final SharedPreferences preferences = application.getPreferences();

		application.getConfiguration().saveToPreferences(preferences);

		addPreferencesFromResource(R.xml.prefs);

		// Custom Preference Listeners

		findPreference("setupwizard_pref").setOnPreferenceClickListener(
				new OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference arg0) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								PrefsActivity.this);
						DialogInterface.OnClickListener alertListener = new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								
								Editor editor = preferences.edit();
								editor.putBoolean("wizard_run", false);
								editor.commit();

							}

						};
						builder
								.setMessage(
										PrefsActivity.this.getResources().getString(
												R.string.setup_wizard_dialog)).setCancelable(false)
								.setNeutralButton(R.string.ok, alertListener);

						AlertDialog alert = builder.create();
						alert.show();
						return true;
					}

				});

	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceManager().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceManager().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub

	}

}
