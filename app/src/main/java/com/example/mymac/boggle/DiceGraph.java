package com.example.mymac.boggle;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by brettchafin on 2/9/17.
 */



public class DiceGraph {

    static final int BOARD_SIZE = 16;
    List<Edge> adjList[];

    DiceGraph(Die[][] dice){
        adjList = new LinkedList[BOARD_SIZE];
        for(int i = 0; i < adjList.length; i++){
            adjList[i] = new LinkedList<Edge>();
        }

        /*
        //creates
        for(int i = 0; i < 4; i++){
            for (int j = 0; i < 4; j++){
                Edge toAdd = new Edge(dice[i][j].boardPosition);
                adjList[dice[i][j].boardPosition].add(toAdd);
            }

        }*/

    }





    public void addAdjacentEdges(){

    }

    public class Edge {

        int die;

        Edge(int die){
             this.die = die;
        }
    }
}
