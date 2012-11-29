GardenHexTD
===========

A Tower Defense Android game with a Hex grid and the ability to rotate towers that have different attack areas.
A* and AIs that mimic ants in nature are used.

How to compile/run
==================

Method 1: IntelliJ

1) Download the free community edition of IntelliJ from http://www.jetbrains.com/idea/download/index.html
2) Download the Android SDK for your platform from http://developer.android.com/sdk/index.html
3) Run the command "android" (Linux) or "android.exe" (Windows) from the "tools" subdirectory
    -This will allow you to download an emulator and the needed SDK class files
4) Run "android avd" or the equivalent command for Windows and click "New" to make a new virtual device
5) Unpack the IntelliJ tar file and open IntelliJ (run "idea-IC-xxx/bin/idea.sh" in linux)
6) Go to File-->Open Project and open the HexTD IntelliJ project included
7) Right-click on the HexTD module in the project browser and go to "Open Module Settings"
8) Click on the SDKs tab
    -Click the + sign to add your JDK, point it to the base directory for your JDK files
    -Click the + sign to add your Android SDK, point it to the base directory for the Android SDK you downloaded
    -(If these already exist then edit them rather than creating new ones)
9) Go to Run-->Edit Configurations
    -Click the "+" and create a new Android run configuration
    -Check "Launch Default Activity" and under Emulator choose your android virtual device, which should show up if you
    created it in step 4)
10) Select your run config and press the play button to run it!

Method 2: Linux command-line

1) Download the Android SDK for your platform from http://developer.android.com/sdk/index.html
2) Add the "xxx/android-sdk/tools" and "xxx/android-sdk/platform-tools" directories to your PATH variable
3) Run the command "android"
    -This will allow you to download an emulator and the needed SDK class files
4) Run "android avd" or the equivalent command for Windows and click "New" to make a new virtual device
5) Make sure you have the Apache ant build tool, or run "sudo apt-get install ant"
6) Make sure JAVA_HOME points to the location of your JDK
7)