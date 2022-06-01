package com.vincent_falzon.discreetlauncher.menu ;

// License
/*

	This file is part of Discreet Launcher.

	Copyright (C) 2019-2022 Vincent Falzon

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */

// Imports
import android.annotation.SuppressLint ;
import android.content.Context ;
import android.content.Intent ;
import android.view.LayoutInflater ;
import android.view.MotionEvent ;
import android.view.View ;
import android.view.ViewGroup ;
import android.widget.TextView ;
import android.app.AlertDialog ;
import android.app.Dialog ;
import com.vincent_falzon.discreetlauncher.ActivityMain ;
import com.vincent_falzon.discreetlauncher.R ;
import com.vincent_falzon.discreetlauncher.settings.ActivitySettingsAppearance ;
import com.vincent_falzon.discreetlauncher.settings.ActivitySettingsOperation ;

/**
 * Display the main menu of Discreet Launcher.
 */
public class DialogMenu extends Dialog implements View.OnClickListener, View.OnTouchListener
{
	// Attributes
	private final View dialogView ;


	/**
	 * Constructor.
	 */
	@SuppressWarnings({"RedundantCast", "RedundantSuppression"})
	public DialogMenu(Context context)
	{
		// Let the parent actions be performed
		super(context) ;

		// Load the XML layout
		dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_menu, (ViewGroup)null) ;
		setContentView(dialogView) ;

		// Initializations
		initializeMenuEntry(R.id.menu_favorites) ;
		initializeMenuEntry(R.id.menu_hidden_apps) ;
		initializeMenuEntry(R.id.menu_refresh_list) ;
		initializeMenuEntry(R.id.menu_settings_appearance) ;
		initializeMenuEntry(R.id.menu_settings_operation) ;
	}


	/**
	 * Initialize a menu entry with the given resource ID.
	 */
	@SuppressLint("ClickableViewAccessibility")
	private void initializeMenuEntry(int id)
	{
		TextView menuEntry = dialogView.findViewById(id) ;
		menuEntry.setOnClickListener(this) ;
		menuEntry.setOnTouchListener(this) ;
	}


	/**
	 * Called when an element is clicked.
	 */
	@Override
	public void onClick(View view)
	{
		// Retrieve the clicked element
		int selection = view.getId() ;
		Context context = view.getContext() ;

		// Perform the related actions
		if(selection == R.id.menu_favorites)
			{
				// Open the Favorites activity
				context.startActivity(new Intent().setClass(context, ActivityFavorites.class)) ;
			}
			else if(selection == R.id.menu_hidden_apps)
			{
				// Open the Hidden apps dialog
				DialogHiddenApps.showHiddenAppsDialog(context) ;
			}
			else if(selection == R.id.menu_refresh_list)
			{
				// Refresh the list of apps and go back to the home screen
				ActivityMain.updateList(context) ;
				Intent homeIntent = new Intent() ;
				homeIntent.setClass(context, ActivityMain.class) ;
				homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
				context.startActivity(homeIntent) ;
			}
			else if(selection == R.id.menu_settings_appearance)
			{
				// Open the Settings > Appearance activity
				context.startActivity(new Intent().setClass(context, ActivitySettingsAppearance.class)) ;
			}
			else if(selection == R.id.menu_settings_operation)
			{
				// Open the Settings > Operation activity
				context.startActivity(new Intent().setClass(context, ActivitySettingsOperation.class)) ;
			}

		// Dismiss the menu
		dismiss() ;
	}


	/**
	 * Called when an element is touched.
	 */
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		// Do not continue if the view is not a TextView
		if(!(view instanceof TextView)) return false ;

		// Toggle visual feedback on the selected menu entry
		switch(event.getAction())
		{
			// Gesture started
			case MotionEvent.ACTION_DOWN :
				view.setBackgroundColor(view.getContext().getResources().getColor(R.color.for_menu_highlight_and_dividers)) ;
				break ;
			// Gesture finished or aborted
			case MotionEvent.ACTION_UP :
			case MotionEvent.ACTION_CANCEL :
				view.setBackground(null) ;
				break ;
		}

		// Do not consider the event as consumed
		return false ;
	}
}
