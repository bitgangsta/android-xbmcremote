/*
 *      Copyright (C) 2005-2009 Team XBMC
 *      http://xbmc.org
 *
 *  This Program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2, or (at your option)
 *  any later version.
 *
 *  This Program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with XBMC Remote; see the file license.  If not, write to
 *  the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 *  http://www.gnu.org/copyleft/gpl.html
 *
 */

package org.xbmc.android.remote.presentation.activity;

import java.io.IOException;

import org.xbmc.android.remote.R;
import org.xbmc.android.remote.business.ManagerFactory;
import org.xbmc.android.remote.presentation.controller.ActorListController;
import org.xbmc.android.remote.presentation.controller.FileListController;
import org.xbmc.android.remote.presentation.controller.MovieGenreListController;
import org.xbmc.android.remote.presentation.controller.MovieListController;
import org.xbmc.android.remote.presentation.controller.TvShowListController;
import org.xbmc.android.widget.slidingtabs.SlidingTabActivity;
import org.xbmc.android.widget.slidingtabs.SlidingTabHost;
import org.xbmc.android.widget.slidingtabs.SlidingTabHost.OnTabChangeListener;
import org.xbmc.api.business.IEventClientManager;
import org.xbmc.api.type.MediaType;
import org.xbmc.eventclient.ButtonCodes;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.FrameLayout;
import android.widget.ListView;

public class TvShowLibraryActivity extends SlidingTabActivity  {

	private SlidingTabHost mTabHost;
	
	private TvShowListController mTvShowController;
	private ActorListController mActorController;
	private MovieGenreListController mGenresController;
	private FileListController mFileController;
	
	private static final int MENU_NOW_PLAYING = 301;
	private static final int MENU_UPDATE_LIBRARY = 302;
	private static final int MENU_REMOTE = 303;
	
    private ConfigurationManager mConfigurationManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movielibrary);
		
		// remove nasty top fading edge
		FrameLayout topFrame = (FrameLayout)findViewById(android.R.id.content);
		topFrame.setForeground(null);
		
		mTabHost = getTabHost();
		
		// add the tabs
		mTabHost.addTab(mTabHost.newTabSpec("tab_tv", "TV Shows", R.drawable.st_tv_on, R.drawable.st_tv_off).setBigIcon(R.drawable.st_tv_over).setContent(R.id.tvshowlist_outer_layout));
		mTabHost.addTab(mTabHost.newTabSpec("tab_actors", "Actors", R.drawable.st_actor_on, R.drawable.st_actor_off).setBigIcon(R.drawable.st_actor_over).setContent(R.id.actorlist_outer_layout));
		mTabHost.addTab(mTabHost.newTabSpec("tab_genres", "Genres", R.drawable.st_genre_on, R.drawable.st_genre_off).setBigIcon(R.drawable.st_genre_over).setContent(R.id.genrelist_outer_layout));
		mTabHost.addTab(mTabHost.newTabSpec("tab_files", "File Mode", R.drawable.st_filemode_on, R.drawable.st_filemode_off).setBigIcon(R.drawable.st_filemode_over).setContent(R.id.filelist_outer_layout));
		mTabHost.setCurrentTab(0);

		// assign the gui logic to each tab
		mTvShowController = new TvShowListController();
		mTvShowController.findTitleView(findViewById(R.id.tvshowlist_outer_layout));
		mTvShowController.findMessageView(findViewById(R.id.tvshowlist_outer_layout));
		mTvShowController.onCreate(this, (ListView)findViewById(R.id.tvshowlist_list)); // first tab can be updated now.

		mActorController = new ActorListController(ActorListController.TYPE_TVSHOW);
		mActorController.findTitleView(findViewById(R.id.actorlist_outer_layout));
		mActorController.findMessageView(findViewById(R.id.actorlist_outer_layout));

		mGenresController = new MovieGenreListController(MovieGenreListController.TYPE_TVSHOW);
		mGenresController.findTitleView(findViewById(R.id.genrelist_outer_layout));
		mGenresController.findMessageView(findViewById(R.id.genrelist_outer_layout));

		mFileController = new FileListController(MediaType.VIDEO);
		mFileController.findTitleView(findViewById(R.id.filelist_outer_layout));
		mFileController.findMessageView(findViewById(R.id.filelist_outer_layout));
		
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			public void onTabChanged(String tabId) {
				
				if (tabId.equals("tab_tv")) {
					mTvShowController.onCreate(TvShowLibraryActivity.this, (ListView)findViewById(R.id.movielist_list));
				}
				if (tabId.equals("tab_actors")) {
					mActorController.onCreate(TvShowLibraryActivity.this, (ListView)findViewById(R.id.actorlist_list));
				}
				if (tabId.equals("tab_genres")) {
					mGenresController.onCreate(TvShowLibraryActivity.this, (ListView)findViewById(R.id.genrelist_list));
				}
				if (tabId.equals("tab_files")) {
					mFileController.onCreate(TvShowLibraryActivity.this, (ListView)findViewById(R.id.filelist_list));
				}
			}
		});
		mConfigurationManager = ConfigurationManager.getInstance(this);
		mConfigurationManager.initKeyguard();
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		menu.add(0, MENU_NOW_PLAYING, 0, "Now playing").setIcon(R.drawable.menu_nowplaying);
		switch (mTabHost.getCurrentTab()) {
			case 0:
				mTvShowController.onCreateOptionsMenu(menu);
				break;
			case 1:
				mActorController.onCreateOptionsMenu(menu);
				break;
			case 2:
				mGenresController.onCreateOptionsMenu(menu);
				break;
			case 3:
				mFileController.onCreateOptionsMenu(menu);
				break;
		}
		menu.add(0, MENU_UPDATE_LIBRARY, 0, "Update Library").setIcon(R.drawable.menu_refresh);
		menu.add(0, MENU_REMOTE, 0, "Remote control").setIcon(R.drawable.menu_remote);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// first, process individual menu events
		switch (mTabHost.getCurrentTab()) {
		case 0:
			mTvShowController.onOptionsItemSelected(item);
			break;
		case 1:
			mActorController.onOptionsItemSelected(item);
			break;
		case 2:
			mGenresController.onOptionsItemSelected(item);
			break;
		case 3:
			mFileController.onOptionsItemSelected(item);
			break;
		}
		
		// then the generic ones.
		switch (item.getItemId()) {
			case MENU_REMOTE:
				startActivity(new Intent(this, RemoteActivity.class));
				return true;
			case MENU_UPDATE_LIBRARY:
				mTvShowController.refreshMovieLibrary(this);
				return true;
			case MENU_NOW_PLAYING:
				startActivity(new Intent(this,  NowPlayingActivity.class));
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		switch (mTabHost.getCurrentTab()) {
			case 0:
				mTvShowController.onCreateContextMenu(menu, v, menuInfo);
				break;
			case 1:
				mActorController.onCreateContextMenu(menu, v, menuInfo);
				break;
			case 2:
				mGenresController.onCreateContextMenu(menu, v, menuInfo);
				break;
			case 3:
				mFileController.onCreateContextMenu(menu, v, menuInfo);
				break;
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (mTabHost.getCurrentTab()) {
		case 0:
			mTvShowController.onContextItemSelected(item);
			break;
		case 1:
			mActorController.onContextItemSelected(item);
			break;
		case 2:
			mGenresController.onContextItemSelected(item);
			break;
		case 3:
			mFileController.onContextItemSelected(item);
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		IEventClientManager client = ManagerFactory.getEventClientManager(mTvShowController);
		try {
			switch (keyCode) {
				case KeyEvent.KEYCODE_VOLUME_UP:
					client.sendButton("R1", ButtonCodes.REMOTE_VOLUME_PLUS, false, true, true, (short)0, (byte)0);
					return true;
				case KeyEvent.KEYCODE_VOLUME_DOWN:
					client.sendButton("R1", ButtonCodes.REMOTE_VOLUME_MINUS, false, true, true, (short)0, (byte)0);
					return true;
			}
		} catch (IOException e) {
			client.setController(null);
			return false;
		} 
		client.setController(null);
		return super.onKeyDown(keyCode, event);
	}
	

	@Override
	protected void onResume() {
		super.onResume();
		mTvShowController.onActivityResume(this);
		mActorController.onActivityResume(this);
		mGenresController.onActivityResume(this);
		mFileController.onActivityResume(this);
		mConfigurationManager.onActivityResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mTvShowController.onActivityPause();
		mActorController.onActivityPause();
		mGenresController.onActivityPause();
		mFileController.onActivityPause();
		mConfigurationManager.onActivityPause();
	}
}