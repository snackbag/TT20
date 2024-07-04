# TT20
TT20 helps reduce lag by optimizing how ticks work when the server's TPS is low.

# Regarding singleplayer
While you can still use this mod in singleplayer, most things will not run at normal speed, because you are running an integrated server. This mod is made for Minecraft servers, not for singleplayer use. **If you do have a way around this**, please tell us and we would be happy to fix this issue!

## What does TT20 stand for?
TT20 is an abbreviation of the `ticks*tps/20` formula. It's used to calculate the amount of ticks something takes, while taking the TPS into account.

## Disclaimer
TT20 only fixes the symptoms of TPS lag, not the actual lag. It re-calculates the amount of ticks things take. For example, when you're breaking a block it takes the original break time, multiplies it by ticks and divides it by 20 (the maximum TPS). This ensures that the end user feels almost no lag, even if the TPS is very low.

## What's covered?
- [X] Block break delay
- [X] Eating delay
- [X] Item pickup delay
- [X] Block entity tick delay
- [ ] Entity tick delay

If you believe there is features missing, please tell us by creating a new issue (yes, also if you want compatibility with other mods!)
