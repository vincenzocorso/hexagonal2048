# Hexagonal 2048 :sunglasses:
This is a simple replica of the famous [2048](https://github.com/gabrielecirulli/2048). 

This is also my first attempt to use [MVC](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller) design pattern.

I made this mainly to practise with Swing framework.

## :bulb: Features

 - Two grid types: Hexagonal or Classic
 - Different grid sizes
 - You can undo last move
 
## :framed_picture: Screenshot

<p align="center">
  <img src="https://s5.gifyu.com/images/hexagonal.gif">
  <img src="https://s5.gifyu.com/images/classic.gif">
</p>

## :joystick: Keys
Press Q to move the tiles to the top left.

Press W to move the tiles upward.

Press E to move the tiles to the top right.

Press A to move the tiles to the left.

Press D to move the tiles to the right.

Press Z to move the tiles to the bottom left.

Press X to move the tiles down.

Press C to move the tiles to the bottom right.

## :computer: How to compile this project
From the root folder:

    javac -d ./ src/util/*.java
    javac -d ./ src/model/*.java
    javac -d ./ src/view/*.java
    javac -d ./ src/controller/*.java
    javac Main.java
At the end, run `Main.java`

## :page_with_curl: License
Distributed under the MIT License. See [here](https://github.com/vincenzocorso/hexagonal2048/blob/master/LICENSE) for more information.

## :books: What I learned
Don't use Swing if you want play animations :joy:
