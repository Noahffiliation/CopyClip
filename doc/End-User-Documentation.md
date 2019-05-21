# End User Documentation

## Introduction
CopyClip is an Android application intended to give users access to an expansive clipboard interface.

## Interfaces

### Application Window
![alt text] (deliverables/web_hi_res_512.png)

Clicking the CopyClip icon from the Android menu brings up the main CopyClip application interface.  
The main interface has three views: Recent, Pinned, and User Preferences.  

#### Recent
The Recent view can be accessed by opening the main application interface and tapping on the Recent tab near the top left part of the screen.  
Initially, the Recent view will be empty. However upon [copying](https://repo.cse.taylor.edu/group-work/frog/blob/master/doc/End-User-Documentation.md#copying "Copying") a text item, a new list item will appear in the Recent view.  

Each copied item in the Recent view can be selected and [pasted](https://repo.cse.taylor.edu/group-work/frog/blob/master/doc/End-User-Documentation.md#pasting "Pasting") or [pinned](https://repo.cse.taylor.edu/group-work/frog/blob/master/doc/End-User-Documentation.md#pinning "Pinning").

#### Pinned
The Pinned view can be accessed by opening the main application interface and tapping on the Pinned tab near the top right part of the screen.  
Initially, the Pinned view will be empty. However, when a list item in the [Recent](https://repo.cse.taylor.edu/group-work/frog/blob/master/doc/End-User-Documentation.md#recent "Recent") interface is [pinned](https://repo.cse.taylor.edu/group-work/frog/blob/master/doc/End-User-Documentation.md#pinning "Pinning"), it appears in the Pinned interface.  

#### User Preferences
![alt text] (deliverables/screenshot_2017-11-29-20-29-44_720.png)

The User Preferences view can be accessed by opening the main application interface and tapping on the gear symbol in the top right corner of the screen.  
User Preferences contains two settings that can be adjusted at any time:  
"Maximum Buffer Items" is used to set how many items the [Recent](https://repo.cse.taylor.edu/group-work/frog/blob/master/doc/End-User-Documentation.md#recent "Recent") view should hold at most.  
"Maximum Pinned Items" is used to set how many items the **Pinned** view should hold at most.  

### Overlay
The Overlay view can be accessed by pulling down on the top of your screen and opening the Android overlay.
Within the Android overlay, there are several **Notification** objects.  

### Notification
Notification objects can be accessed within the **Overlay** view.
Whenever a text item is [copied](https://repo.cse.taylor.edu/group-work/frog/blob/master/doc/End-User-Documentation.md#copying "Copying"), a Notification object is created.
Tapping on a Notification object opens the [copied](https://repo.cse.taylor.edu/group-work/frog/blob/master/doc/End-User-Documentation.md#copying "Copying") item within the Recent view.

## Features

### Copying
While CopyClip is running on an Android device, users have the ability to copy text.  
Upon tapping and selecting an area of text, a "copy" option becomes available. Upon selecting the copy option, the selected text is added as a copied item to top of the [Recent](https://repo.cse.taylor.edu/group-work/frog/blob/master/doc/End-User-Documentation.md#recent "Recent") view.

### Pinning
Within the [Recent](https://repo.cse.taylor.edu/group-work/frog/blob/master/doc/End-User-Documentation.md#recent "Recent") view, users have the option to pin text items by tapping on the pushpin symbol to the right of a text item.  
The symbol turns blue upon being tapped, indicating that the text item has been added to the Pinned list.

### Deleting
Press the delete button under a clip to delete the item

### Edit
Press the edit button under a clip to launch the edit dialog. Enter your new text and press save to save it

### Pasting
To paste a copied item from your [Recent](https://repo.cse.taylor.edu/group-work/frog/blob/master/doc/End-User-Documentation.md#recent "Recent") view, simply tap it and paste it like you would normally paste a copied piece of text.
