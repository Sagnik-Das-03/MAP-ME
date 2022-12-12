# MAP-ME

## *Sagnik Das*

**MAP ME** displays a list of maps, each of which show user-defined markers with a title, description, and location. The user can also create a new map and delete it.

Time spent: **72** hours spent in total

## Functionality

The following **required** functionality is completed:

* [x] The list of map titles is displayed.
* [x] After tapping on a map title, the associated markers in the map are shown.
* [x] The user is able to create a new map.
* [x] The user is able to delete the existing maps.

The following **additional functionalities** are also implemented:

* [x] When a map marker is created, the pin is animated, also the marker can be deleted
* [x] The map marker is customized
* [x] The numbers of user-defined makers on the map is shown below the maps
* [x] Added transition animations between activities using animatoo library
* [x] Added various map types like Terrain map, Satellite maps and Hybrid maps on both Create Map and Display Map Screen
* [x] Stores all the created maps in a local file


## Video Walk Through

Here's a walk through of implemented user stories:

<p float="middle">
    <img src='https://github.com/Sagnik-Das-03/mapMe/blob/master/mapme_homescreen.png' title='mainscreen' width='300' alt='Home Screen' />
    <img src='https://github.com/Sagnik-Das-03/mapMe/blob/master/mapme.gif' title='Walk Through' width='240' alt='Video Walkthrough' />
 </p>

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Features
* Pure Kotlin
* Multi Screen
* Used Animatoo library

### Method 1:
* [x] Download Android Studio 

* [x] Click the icon to download
 
<p align="left">
   <a href='https://developer.android.com/studio?gclid=Cj0KCQjwqoibBhDUARIsAH2OpWi2VQ6w50tP7G8OeiMmIt9gK13cN1et0AU5tZ1O2KnjsOxrTWpP0aAr7TEALw_wcB&gclsrc=aw.ds'><img         width="150" src='https://github.com/Sagnik-Das-03/TIPPER/blob/master/studioicon.jpeg' /></a>
</p>
 
* [x] Set up Android Studio

* [x] Download the project zip file 
<a href='https://github.com/Sagnik-Das-03/MAP-ME/archive/refs/heads/master.zip'>Click here to Download Zip</a>
* [x] Extract it and open in android studio
* [x] Get your Google Maps Api Key from 
<a href = 'https://developers.google.com/maps/documentation/android-sdk/get-api-key'>Click here to get the Google Maps Api Key</a>
 * [x]  To get one, follow the directions on the above link :
        Once you have your API key (it starts with "AIza"), define a new property in your
             project's local.properties file (e.g. MAPS_API_KEY=Aiza...), and replace the
             "YOUR_API_KEY" string in this file with "${MAPS_API_KEY}".
    Add this ${MAPS_API_KEY} in the <meta-data> tag of androidManifest file like below image
    <p float="middle">
    <img src='https://github.com/Sagnik-Das-03/MAP-ME/blob/master/manifest.png' title='mainscreen' width='300' alt='home screen' />
 </p>

* [x] Run the app from android studio 


### Method 2:
* [x] Download the apk
<a href='https://drive.google.com/file/d/15nNfyJTHydmgV1mMMjcb7LvlULSer78K/view?usp=share_link'>Click here to Download Apk</a>
* [x] Install the apk on your android device
* [x] Open the app 
    
 ## Some TestCases with Inputs
<p float="middle">
    <img src='https://github.com/Sagnik-Das-03/MAP-ME/blob/master/mapCase%20(1).jpg' title='input 1' width='200' alt='input 1' />
    <img src='https://github.com/Sagnik-Das-03/MAP-ME/blob/master/mapCase%20(2).jpg' title='input 2' width='200' alt='input 2' />
    <img src='https://github.com/Sagnik-Das-03/MAP-ME/blob/master/mapCase%20(3).jpg' title='input 3' width='200' alt='input 3' />
    <img src='https://github.com/Sagnik-Das-03/MAP-ME/blob/master/mapCase%20(4).jpg' title='input 4' width='200' alt='input 4' />
    <img src='https://github.com/Sagnik-Das-03/MAP-ME/blob/master/mapCase%20(5).jpg' title='input 5' width='200' alt='input 5' />
    <img src='https://github.com/Sagnik-Das-03/MAP-ME/blob/master/mapCase%20(6).jpg' title='input 6' width='200' alt='input 6' />
    <img src='https://github.com/Sagnik-Das-03/MAP-ME/blob/master/mapCase%20(7).jpg' title='input 7' width='200' alt='input 7' />
 </p>

## License

    Copyright [2022] [SAGNIK DAS]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
