Colour Byte Array - Draw

Draw (CBA-Draw) is intended to be used as a tool for creating simple pixel images.
Simple post proccessing is applied to images to give a 3D tiled effect. Both single
images and tile-maps are supported with the use of the cutout tool.

Draw makes use of a palette file for the selection and recording of pixel colours.
An exmaple palette file (palette.png) has been included. Palettes may be changed
by replacing the file "palette.png". Palettes current require the same number of elements
as the example palette, this may be subject to change in future versions.

The colour palette is referenced in the CBA save file to determin which colour lies
at each co-ordinant. For this reason any software intending to implement a CBA file
must share the same palette file as Draw.

UI Elements:
	-File Menu: Expands when clicked with the left mouse button. The menu expands
		to display a number of common controls:
			-New: clears the screen of any pixels currently present. Warning,
				no prompt will be shown, so ensure any working files are
				saved before using this control.
			-Save: Splashed the save file dialog to save the image as a CBA
				file.
			-Load: Splashed the load file dialog to load a CBA file.
			-Quit: Closes the program.
	-Palette: Located in the bottom right by default, the palette contains a selection
		of colours as outlined by the "palette.png" file. Pressing on a palette
		swatch with the left mouse button will set the colour as the current active
		colour. The pallete UI element is able to be moved around the screen by
		pressing and holding the left mouse button on the small orange bar at the top
		of the palette and draging around.

	-Location Cursor (Black Crosshair): used to keep track of the centre of the image
		particularly useful when zooming or working on a large image.
	-Image Boundary Indicator (White square behind the image): Indicated the boundaries of
		the image, ie: what parts of the image are to be saved.
	-Image Top-Left Indicator (Red Crosshair at the top-left of the Image Boundary Indicator):
		Indicated where the image file will start.
	-Current Working File Name: The name of the current working file is displayed in the
		window for reference when switching between multiple images, or when multiple
		instances of Draw are open at once.
		

Controls:
	-Left Mouse Button: Place a pixel of selected colour under the mouse cursor.
	-Right Mouse Button: Delete a pixel under the mouse cursor.
	-Middle Mouse Button: Hold to drag the image around the screen (Usefull when
		working zoomed or on larger images).

	-Plus Key "+": Zoom in, multiply the size of each pixel by 2.
	-Minus Key "-": Zoom out, divide the size of each pixel by 2.
	-Space Key " ": Reset the location cursor (Black crosshair) to the centre of
		the screen.

	-Z Key "z": Switch to "Edit Mode"
	-X Key "x": Switch to "Cutout Mode"

Modes:
	-Edit Mode: When in edit mode the mouse buttons work as outlined in 'Controls'.
		This mode is intended to be used when creating or editing images.

	-Cutout Mode: When in Cutout Mode, the left mouse button changes it's function.
		The new function drags a orange box (only from top left to bottom right).
		The orange box represents the section of the image to be saved (Only when
		saving from Cutout Mode). This function is usefull for saving individual
		elements of a tile map.
