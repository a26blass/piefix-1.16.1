# PieFix

## About
This mod allows you to resize and reposition the pie chart in Minecraft 1.16.1. This version has a flaw where the debug chart is rendered directly onto the framebuffer with a constant pixel radius and fixed offsets. Since the size of the framebuffer depends on screen resolution, the pie chart appears very small for high resolution screens.

Here are the faulty lines of code. This is a snippet from the function `drawProfilerResults()`:

```java
// ...
RenderSystem.ortho(0.0, (double)this.window.getFramebufferWidth(), (double)this.window.getFramebufferHeight(), 0.0, 1000.0, 3000.0);
// ...
int i = 160;
int j = this.window.getFramebufferWidth() - 160 - 10;
int k = this.window.getFramebufferHeight() - 320;
```

Here they incorrectly use `getFramebufferWidth/Height()` instead of `getScaledWidth/Height()`, which incorporates GUI scale.

The mod fixes this by allowing you to configure a `scale`, `offsetX`, and `offsetY` in your instance's config/piefix.txt. Redirects are used to replace calls to `getFramebufferWidth/Height()` with calls to a handler that scales down the apparent size of the framebuffer by a factor of `scale`, increasing the pixels per coordinate unit after the `ortho()` call.

Why this approach instead of scaling with GUI? It offers more flexibility, for instance I prefer GUI scale 4, but the pie chart is too large at that scale. Also, due to the fixed offset in `drawProfilerResults()`, as `scale` increases, the chart drifts upwards and left. The easiest approach was to let the user choose a `scale` and adjust the `offsetX` and `offsetY` to account for drift.

## Configuration
The config file is located at: `minecraft/config/piefix.txt` and will be generated automatically on first launch. Here is the default config, which does not modify the position or scale of the chart:

```piefix.txt
1.0
0
0
```

Where the format is 

```piefix.txt
scale
offsetX
offsetY
```
Scale (float) modifies the size of the pie chart. A scale of 2.0 means that the pie chart will be rendered twice as large. You do not need to use integer values for scale, e.g a scale of 2.5 is valid 

OffsetX (int) is respective to the right of the screen. By default (with `scale=1.0`) the pie chart is x-offset by 170, e.g. 170 pixels left starting at the right of the screen.

OffsetY (int) is respective to the bottom of the screen. By default (with `scale=1.0`) the pie chart is y-offset by 320, e.g. 320 pixels up starting at the bottom of the screen. 

Tip: At higher scales, the chart naturally drifts toward the screen center because of how Minecraft's chart renders. Use OffsetX/OffsetY to nudge it back to a position you like.

Changes to the config are reflected upon exiting and re-launching the game.