package main;

import containers.Backpack;
import data.Shape;
import data.Sphere;
import graphics.Window;

import java.awt.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Backpack<Shape> backpack = new Backpack<>(Backpack.DEFAULT_CAPACITY);
        Window window = new Window(backpack);
    }

}
