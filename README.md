# Mirror-UI

User interface for a smart mirror, built with [Compose for Desktop](https://github.com/JetBrains/compose-jb/).

My hypothetical smart mirror would have touch input with which the user is able to control devices and do other similar simple actions.
If any, navigations should occur inside a single widget without taking over the whole mirror.

Most UI elements will use black on white outlined styles, with blue-ish ripple effects as touch feedback.
I might deviate from this rule for (red) error feedback or images.

The interface currently has several widgets (partially) implemented, like:

* Time & date display
* Brush timer
* Ventilation controls

With several other widgets planned:

* Weather
* Todo items
* News
* Quote of the day


## Brush timer

The brush timer will dynamically detect when someone is brushing using BLE advertisements sent out by Oral-B 
electric toothbrushes. See my Go-library for it [here](https://github.com/raqbit/goralb). It will also be able to detect 
the remaining time and which quadrant the user should be brushing.

I might also add feedback to the user if they are using too much pressure, as this is sent via BLE too.

## Ventilation controls

Will hook up to my bathroom ventilation which is based on a proprietary 868Mhz radio protocol called [RAMSES II](https://github.com/zxdavb/ramses_protocol).