# TT20 Forge
TT20 helps reduce lag by optimizing how ticks work when the server's TPS is low.

# Regarding singleplayer
While you can still use this mod in singleplayer, some things may not work correctly. You can always toggle any features in the configuration. We're trying our best to make this mod compatible with singleplayer, so if you find any issues, [please create an issue](https://github.com/snackbag/TT20/issues).

## What does TT20 stand for?
TT20 is an abbreviation of the `ticks*tps/20` formula. It's used to calculate the amount of ticks something takes, while taking the TPS into account.

## Caveats
TT20 only fixes the symptoms of TPS lag, not the actual lag. It re-calculates the amount of ticks things take. For example, when you're breaking a block it takes the original break time, multiplies it by ticks and divides it by 20 (the maximum TPS). This ensures that the end user feels almost no lag, even if the TPS is very low.

## Roadmap
- [X] Block break delay
- [ ] Eating delay
- [ ] Item pickup delay
- [/] Block entity tick acceleration (experimental)
- [/] Entity death animation(?)
- [/] Block state delay
- [ ] Portal delay
- [ ] Sleeping delay
- [ ] Potion delay
- [ ] Fluid spread speed
- [ ] Random tickspeed acceleration
- [ ] Time acceleration

If you believe there is features missing, please tell us by creating a new issue (yes, also if you want compatibility with other mods!)
