# TT20 
TT20 helps reduce lag by optimizing how ticks work when the server's TPS is low.

## What does TT20 stand for?
TT20 is an abbreviation of the `ticks*tps/20` formula. It's used to calculate the number of ticks something takes while taking the TPS into account.

## Caveats
TT20 only fixes the symptoms of TPS lag, not the actual lag. It re-calculates the number of ticks things take. For example, when you're breaking a block, it takes the original break time, multiplies it by ticks and divides it by 20 (the maximum TPS). This ensures that the end user feels almost no lag, even if the TPS is very low.

## Roadmap
- [X] Block break delay
- [X] Eating delay
- [X] Item pickup delay
- [X] Block entity tick acceleration (experimental)
- [ ] Block state delay
- [X] Portal delay
- [X] Sleeping delay
- [X] Potion delay
- [X] Fluid spread speed
- [X] Random tickspeed acceleration
- [X] Day/nighttime acceleration

If you believe there is features missing, please tell us by creating a new issue\
(yes, also if you want compatibility with other mods!)

### Supported versions
|   TT20    | Fabric/Quilt | Forge | NeoForge |
|:---------:|:------------:|:-----:|:--------:|
| 26.1(.1)  |      ✅       |   ❌   |    ✅     |
|  1.21.11  |      ✅       |   ❌   |    ✅     |
| 1.21.9/10 |      ✅       |   ❌   |    ✅     |
| 1.21.2-8  |      ✅       |   ❌   |    ✅     |
| 1.21(.1)  |      ✅       |   ❌   |    ✅     |
| 1.20.5/6  |      ✅       |   ❌   |    ✅     |
| 1.20.2-4  |      ✅       |   ❌   |    ✅     |
| 1.20(.1)  |      ✅       |   ✅   |    ❌     |
|  1.19.2   |      ✅       |   ✅   |    ❌     |
|  1.18.2   |      ❌       |   ⏳   |    ❌     |
|  1.16.5   |      ❌       |   ⏳   |    ❌     |
|  1.12.2   |      ❌       |   ✅   |    ❌     |
|   1.8.9   |      ❌       |   ⏳   |    ❌     |
|  1.7.10   |      ❌       |   ⏳   |    ❌     |
|   1.6.4   |      ❌       |   ⏳   |    ❌     |
✅ - Supported\
⏳ - Planned/WIP\
❌ - Unsupported