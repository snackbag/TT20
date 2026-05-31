# TT20 Changelog

0.8.3
=====
* Additions


* Changes


* Bug fixes
  - Fixed TNT's timer being broken
  - Fixed time advance even when it shouldn't

0.8.0
=====
* Additions
  - Now available from 1.18.2 to 26.1.2!
  - Added (cross)bow acceleration
  - Added TNT acceleration
  - Added an attempt to reduce rubber banding


* Changes
  - Client-side is fully compatible now

* Bug fixes
  - Potentially(!) fixed daytime progressing when game is paused
  - Fixed compatibility with Somnia Awoken
  - Fixed watchdog setting not being followed
  - Fixed issues with shields
  - Fixed issues with spears

0.7.2
=====
* Additions


* Changes
  - Changed license to PolyForm Shield

* Bug fixes
  - Fixed a few internal issues

0.7.1
=====
* Additions


* Changes


* Bug fixes
  - Fixed a bug regarding server crashing on startup when the update request fails (mainly caused when playing offline). [#18](https://github.com/snackbag/TT20/issues/18) [#19](https://github.com/snackbag/TT20/pull/19) @FurryRbl

0.7.0
=====
* Additions
  - Added world time acceleration
  - Added random tick speed acceleration
  - Added an automatic updater, sending a warning to server operators when the mod is outdated.


* Changes
  - Missed ticks are now more easily readable by a human
  

* Bug fixes
  - Fixed `getAverageTPS` causing a `ConcurrentModificationException`, often in correlation with c2me. Thanks to @Dreeam-qwq! [#12](https://github.com/snackbag/TT20/pull/12) [#5](https://github.com/snackbag/TT20/issues/5)

0.6.0
=====
* Additions
  - Added log warning when running on the client
  - Added chat warning in singleplayer
  - Pickup delay acceleration is now compatible with singleplayer
  - Portal acceleration is now compatible with singleplayer
  - Block entity acceleration is now compatible with singleplayer
  - Block breaking acceleration is now compatible with singleplayer
  - Potion effect time delay acceleration is now compatible with singleplayer
  - Added `singleplayer-warning` config setting


* Changes
  - Updated README singleplayer warning text


* Bug fixes
  - Fixed an issue when running the mod on 1.20.3, 1.20.4 or 1.20.6, causing servers to crash on startup ([#4](https://github.com/snackbag/TT20/issues/4))
  - Switched fabric.mod.json license from `GPL-3.0` to `AGPL-3.0` (typo)

0.5.1
=====
* Additions
  - Added fluid-acceleration config setting
  - Added pickup-acceleration config setting
  - Added eating-acceleration config setting
  - Added block-breaking-acceleration config setting
  - Added portal-acceleration config setting
  - Added sleeping-acceleration config setting


* Changes
  - Updated icon to the new logo


* Bug fixes
  - Improved stability of mixins (by @Bawnorton [#1](https://github.com/snackbag/TT20/pull/1))
  - Fixed brightness bug in singleplayer ([#2](https://github.com/snackbag/TT20/issues/2))
  - Fixed crash with invalid namespace in the `block_entity_mask.json` configuration

0.5.0
=====
* Additions
  - Added `potion-effect-acceleration` config setting


* Changes
  - Fixed nether portal delay
  - Fixed fluid spreading delay
  - Fixed sleeping time delay
  - Fixed potion time delay


* Bug fixes
  - Fixed item drop times (for real this time)
  - Remove debug log for block breaking