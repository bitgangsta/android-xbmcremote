Note
====

This is my personal fork of the Official XBMC Remote for Android. It contains modifications I've made to keep it more current against lightly XBMC builds. If you're using a nightly build I hope you'll find this useful.

Changes from official (freezy) repo:

* Includes pull request [37](https://github.com/freezy/android-xbmcremote/pull/37), which fixes the TV library mode (broken when used against XBMC's master branch).
* Includes pull request [32](https://github.com/freezy/android-xbmcremote/pull/32), which adds an 'auto rotate' setting, per feature request 226.
* Includes pull request [44](https://github.com/freezy/android-xbmcremote/pull/44), which fixes "Play on XBMC" on android apps that support it.
* Includes pull request [34](https://github.com/freezy/android-xbmcremote/pull/34), which adds support for rtmp:// links via the share menu.
* Includes pull request [33](https://github.com/freezy/android-xbmcremote/pull/33), which adds runtime sorting for movies.

Official XBMC Remote for Android
================================

![Android Market](http://chart.apis.google.com/chart?cht=qr&chs=135x135&chl=market://details?id=org.xbmc.android.remote)

_Control your [XBMC Media Center](http://xbmc.org/) with your Android device. Officially brought to you by Team XBMC._

*The first beta version has hit [Android Market](https://market.android.com/details?id=org.xbmc.android.remote), use the QR Code above or search for "Official XBMC Remote".*

This is still considered as "in development". You're of course welcome to suggest features and ideas to the [forum](http://forum.xbmc.org/forumdisplay.php?f=129) and post bugs to the [tracker](http://code.google.com/p/android-xbmcremote/issues/list).

*NEW*: IRC Channel: *#xbmc-android* on Freenode.


Current Features
----------------

### General

* Remote control with the look of the Xbox' DVD Dongle
* Control XBMC's volume directly with your device's volume buttons
* Fast access to items in list via hard- or soft keyboard (long-press _Menu_)
* Manage multiple XBMC instances
* Live view of currently playing playlist
* Notification bar shows what's playing with direct access to "Now playing" screen (can be disabled)
* Large screen (854x480) support, while maintaining 1.5 compatibility
* On incoming call, display who's calling on TV screen and pause video until call is over
* On incoming message, display on TV screen
* Setting that prevents your phone locking the screen. You can apply it either for remote control only or all screens (or disable it completely)
* Tabs looking and behaving like HTC's Sense UI, also check out the [Gallery](http://code.google.com/p/android-xbmcremote/wiki/Gallery).
* Ultra fast cover download for recent XBMC builds (>r27770)
* Texture support for HTC Tattoo, Google Nexus One as well as Motorola's Droid/Milestone.


### Music Library

* Cover art is shown where available
* _Now playing_ screen allows skipping, seeking, stop and pause
* Browse by album, artist, genre
* XBMC's "Hide artists who appear only on compilations" setting is taken into account when listing artists
* Within a genre, browse by albums, artists and songs
* List all albums and songs of an artist
* Browse compilations only
* Browse files directly
* Sort by album, artist, title, track number etc..
* Play and queue albums, songs, genre selections and much more directly without having to turn on your TV. For instance it's possible to queue/play all songs from an artist but of a certain genre.
* Primitive listing of current playlist with options to jump to another song or remove a song.


### Movie Library

* Browse by title, actors, genre.
* Display all movies of a genre or with an actor.
* Displays movie poster and actor thumbs where available.
* Play trailer from details page where available.


### TV Shows Library

* Browse by show, season, episode or by actors and genre.
* Dynamic thumb rendering with wide banner support
* Displays episode and show details


Features until Market release
-----------------------------


### General
* Boxee compatibility


Longterm features
-----------------


### General
* Download media locally to device's SD card
* Stream media to device
* Multilingualism


### Music Library
* Rating support in _Now playing_
* Last.FM integration


