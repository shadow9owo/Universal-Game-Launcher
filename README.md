# Universal-Game-Launcher
Universal Crossplatform Game Launcher
<br>
<p>doomgames 2025# targetting legacy hardware and yo shiz</p>
<br>
<img src="image.png"></img>
<br>
<p>java 11+</p>
<p>targetting DG2D .INI wrapper</p>
<br>

## how to implement?

(ini format explained)

```performancemode``` - 0 (quality) , 1 (performance) <br>
```windowresolution``` - more info later related to the resolution alg

the ini format is based of DG2D INI wrapper <a href="https://github.com/shadow9owo/DG2D/tree/main/ini">link</a>

<br>

### how is the window resolution list generated?

(java)
````
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode[] modes = gd.getDisplayModes();
        Set<String> resolutionSet = new HashSet<>();

        for (DisplayMode mode : modes) {
            resolutionSet.add(mode.getWidth() + "x" + mode.getHeight());
        }

        List<String> resolutionList = new ArrayList<>(resolutionSet);

        resolutionList.sort((a, b) -> {
            String[] partsA = a.split("x");
            String[] partsB = b.split("x");
            int pixelsA = Integer.parseInt(partsA[0]) * Integer.parseInt(partsA[1]);
            int pixelsB = Integer.parseInt(partsB[0]) * Integer.parseInt(partsB[1]);
            return Integer.compare(pixelsB, pixelsA); // descending
        });
````
