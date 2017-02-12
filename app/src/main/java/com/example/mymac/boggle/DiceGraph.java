package com.example.mymac.boggle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by brettchafin on 2/9/17.
 */



public class DiceGraph {

    static final int BOARD_SIZE = 16;
    List<String> adjList[] = new ArrayList[16];

    DiceGraph() {
        for(int i = 0; i < adjList.length; i++){
            adjList[i] = new LinkedList<String>();
        }

        createGraph();

    }

    private void addEdge(String source, String destination) {
        adjList[Integer.getInteger(source) - 1].add(destination);
    }

    private ListIterator<String> adjacentTo(String node) {

        ListIterator<String> listIterator = adjList[Integer.getInteger(node)].listIterator();

        return listIterator;
        }

    private void createGraph(){
        addEdge("1", "2");
        addEdge("1", "5");
        addEdge("1", "6");

        addEdge("2", "1");
        addEdge("2", "3");
        addEdge("2", "5");
        addEdge("2", "6");
        addEdge("2", "7");

        addEdge("3", "2");
        addEdge("3", "6");
        addEdge("3", "7");
        addEdge("3", "8");
        addEdge("3", "4");

        addEdge("4", "3");
        addEdge("4", "7");
        addEdge("4", "8");

        addEdge("5", "1");
        addEdge("5", "2");
        addEdge("5", "6");
        addEdge("5", "10");
        addEdge("5", "9");

        addEdge("6", "2");
        addEdge("6", "1");
        addEdge("6", "5");
        addEdge("6", "9");
        addEdge("6", "10");
        addEdge("6", "11");
        addEdge("6", "7");
        addEdge("6", "3");

        addEdge("7", "2");
        addEdge("7", "6");
        addEdge("7", "10");
        addEdge("7", "11");
        addEdge("7", "12");
        addEdge("7", "8");
        addEdge("7", "4");
        addEdge("7", "3");

        addEdge("8", "4");
        addEdge("8", "3");
        addEdge("8", "7");
        addEdge("8", "11");
        addEdge("8", "12");

        addEdge("9", "5");
        addEdge("9", "6");
        addEdge("9", "10");
        addEdge("9", "14");
        addEdge("9", "13");

    }

}
