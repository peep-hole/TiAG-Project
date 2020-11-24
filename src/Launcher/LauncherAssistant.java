package Launcher;

import GraphElements.Vertex;

import java.util.Set;

public class LauncherAssistant {

    public LauncherAssistant(){}

    public Vertex getFromSet (Set<Vertex> set, int idOfWanted) {
        for (Vertex vertex : set) {
            if (vertex.getId() == idOfWanted) {
                return vertex;
            }
        }
        throw new IllegalArgumentException("There is no vertex of id: " + idOfWanted + " in this graph!");
    }
}
