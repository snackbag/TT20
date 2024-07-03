# TT20
TT20 helps reduce lag by optimizing how ticks work when the server's TPS is low.

## What does TT20 stand for?
TT20 is an abbreviation of the `ticks*tps/20` formula. It's used to calculate the amount of ticks something takes, while taking the TPS into account.

## Caution
TT20 only fixes the symptoms of TPS lag, not the actual lag. It re-calculates the amount of ticks things take. For example, when you're breaking a block it takes the original break time, multiplies it by ticks and divides it by 20 (the maximum TPS). This ensures that the end user feels almost no lag, even if the TPS is very low.