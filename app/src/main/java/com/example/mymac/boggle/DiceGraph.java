package com.example.mymac.boggle;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;
import java.util.Set;
import java.util.HashSet;

/**
 * Created by brettchafin on 2/9/17.
 */



public class DiceGraph {

    static final int BOARD_SIZE = 16;
    // empty ArrayList
    ArrayList<ArrayList<Integer>> adjLists = new ArrayList<ArrayList<Integer>>();
    // insert n=6 ArrayLists


    private Stack<Integer> path  = new Stack<Integer>();   // the current path
    private Set<Integer> onPath  = new HashSet<Integer>();     // the set of vertices on the path

    DiceGraph() {
        for(int i=0; i<=BOARD_SIZE; i++){
            adjLists.add(new ArrayList<Integer>());
        }

        createGraph();

    }

    private void enumerate( int v, int t) {

        // add node v to current path from s
        path.push(v);
        onPath.add(v);

        // found path from s to t - currently prints in reverse order because of stack
        if (v == t) {
            if(path.size() < 9) {
                final int myCode = Log.i("myCode", path.toString());
            }
        }
            // consider all neighbors that would continue path with repeating a node
        else {
            for (int w : isAdjacentTo(v)) {
                if (!onPath.contains(w)) enumerate(w, t);
            }
        }

        // done exploring from v, so remove from path
        path.pop();
        onPath.remove(v);
    }

    public void AllPaths(int s, int t){
        enumerate(s, t);
    }




    private void addEdge(int source, int destination) {
        adjLists.get(source).add(destination);
    }



    //returns an array of adjacent nodes
    private int[] isAdjacentTo(int node) {
        int[] adjacentNodes = new int[adjLists.get(node).size()];

        for(int i = 0; i < adjLists.get(node).size(); i++){
            adjacentNodes[i] = adjLists.get(node).get(i);
        }
        return adjacentNodes;
    }

    //Creats all edges of the 4 x 4 board
    private void createGraph(){
        addEdge(1, 2);
        addEdge(1, 5);
        addEdge(1, 6);

        addEdge(2, 1);
        addEdge(2, 3);
        addEdge(2, 5);
        addEdge(2, 6);
        addEdge(2, 7);

        addEdge(3, 2);
        addEdge(3, 6);
        addEdge(3, 7);
        addEdge(3, 8);
        addEdge(3, 4);

        addEdge(4, 3);
        addEdge(4, 7);
        addEdge(4, 8);

        addEdge(5, 1);
        addEdge(5, 2);
        addEdge(5, 6);
        addEdge(5, 10);
        addEdge(5, 9);

        addEdge(6, 2);
        addEdge(6, 1);
        addEdge(6, 5);
        addEdge(6, 9);
        addEdge(6, 10);
        addEdge(6, 11);
        addEdge(6, 7);
        addEdge(6, 3);

        addEdge(7, 2);
        addEdge(7, 6);
        addEdge(7, 10);
        addEdge(7, 11);
        addEdge(7, 12);
        addEdge(7, 8);
        addEdge(7, 4);
        addEdge(7, 3);

        addEdge(8, 4);
        addEdge(8, 3);
        addEdge(8, 7);
        addEdge(8, 11);
        addEdge(8, 12);

        addEdge(9, 5);
        addEdge(9, 6);
        addEdge(9, 10);
        addEdge(9, 14);
        addEdge(9, 13);

        addEdge(10, 14);
        addEdge(10, 15);
        addEdge(10, 11);
        addEdge(10, 7);
        addEdge(10, 6);
        addEdge(10, 5);
        addEdge(10, 9);
        addEdge(10, 13);

        addEdge(11, 7);
        addEdge(11, 8);
        addEdge(11, 12);
        addEdge(11, 16);
        addEdge(11, 15);
        addEdge(11, 14);
        addEdge(11, 10);
        addEdge(11, 6);

        addEdge(12, 8);
        addEdge(12, 7);
        addEdge(12, 11);
        addEdge(12, 15);
        addEdge(12, 16);

        addEdge(13, 9);
        addEdge(13, 10);
        addEdge(13, 14);

        addEdge(14, 13);
        addEdge(14, 9);
        addEdge(14, 10);
        addEdge(14, 11);
        addEdge(14, 15);

        addEdge(15, 14);
        addEdge(15, 10);
        addEdge(15, 11);
        addEdge(15, 12);
        addEdge(15, 16);

        addEdge(16, 15);
        addEdge(16, 11);
        addEdge(16, 12);

    }

}
