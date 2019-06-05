# Project CopyClip
Formally Project Evergreen, henceforth will be known as Project CopyClip.

Author: Seth Lugibihl (TEAM FROG)

Contributors: Cole Calamos, Jake Masters, Daniel Grube, & Noah Lindsey

Functional Specification for Project CopyClip

- Platform: Native Android Application

## Overview
CopyClip is a Android-based clipboard buffer and manager that retains a user-defined amount of copied and/or pinned text.

### Basic Scenario 1
> When a user copies text from a source using their native copying
> functionality, the app should intercept and store the text.    
> The text should be represented in a buffer that can be accessed by the user in the future without
> interfering with the current activity that is being used by the user.

### Basic Scenario 2
> The user should be able to access their recent copies in the buffer
> by simply clicking on the persistent notification
> in the status bar.  
After seeing the copy card, they should be able to
> just simply click on a card to copy it to the native Android clipboard
> so it can be pasted in the current activity.
> Afterwards the buffer will close

## Constraints
Memory usage will be constrained by the amount of memory allocated by Android Shared Preferences.
The project must be completed by the mid-December 2017 (less than four months).
Some requirements may change as a result of the short-term nature of this project.
The buffer should only be capable of holding text objects.
Only native Android libraries should be used in the development of CopyClip.

### Assumptions and Dependencies
All requirements are subject to change and alteration throughout the development of CopyClip according to our client's desires.

## App Requirements
- The app should run on Android Device 4.2 and higher
- Only requests permissions that are essential to application operation (ACTION_MANAGE_OVERLAY_PERMISSION, SYSTEM_ALERT_WINDOW)

## BUFFER
The buffer has two primary views: a persistent notification and a in-app fragment.  
They will have similar behavior and identical look/feel.

The buffer features a Material-based tab layout page viewer with a recent buffer and pinned items buffer.

Both should represent the buffer items as cards.

- The card style should adhere to Material Design Guidelines

### Baseline Buffer Requirements
- The buffer should not steal the focus of the current Activity
- The buffer should be able to be accessed within three clicks or less
- Items intercepted from the native Android clipboard should be immediately saved to the device
- Buffer items should be able to be "pinned" and preserved
- Buffer items should be able to be "unpinned"
- Buffer should allow items to be deleted
- In-app buffer should allow items to be edited and saved
- The buffer should only represent the string data type
- Preserves truncated text
- Chronological order of items
- No identical items

### Notification
The notification is persistent and lies within the Android native status bar.

It has a collapsed view and an expanded view that operated as a screen overlay over the current open activity.

It has the option to be clicked to reveal the screen overlay. It also shows the most recent copy with a quick copy button and another button that links straight to the prefs activity

- When clicked, an expanded visual screen overlay representation of the buffer will appear
- This is the same buffer view that appears in the in-app buffer, only in overlay form per Native Android Style

### In-App
- An identical version of the expanded visual screen overlay notification should be replicated
- The in app buffer should have a title bar that has button linked to the PREFS activity described below

## PREFS
The Prefs is a separate in-app activity that saves user-defined preferences

- Users should be able to specify how many items the buffer retains
- Users should be able to specify how many items the pinned buffer retains
- A reasonable default value of items retained by the buffer should be implemented

## UI/UX
The User Interface and User Experience should be similar/identical to Google's Material Design Guidelines and Android Native Behavior.

Functional and visually appealing icons should be used throughout the app, and the launcher icon should give context to the app.

- If possible, only official Material Design Icons will be utilized

The color scheme and text/contrast should adhere to Material Design Typography Guidelines

- The color scheme will be as followed:
    - Primary: #b2ebf2
    - Light: #e5ffff
    - Dark: #81b9bf

The Text Size and Typography should adhere to Google Material Design Guidelines

## Lasting Thoughts
The app should not start any unnecessary background tasks that significantly decrease the performance and/or battery life of the device.  
Basic usability standards should be adhered to.

## Release
CopyClip will be attempted to be released on the Google Play Store before Christmas of 2017.
