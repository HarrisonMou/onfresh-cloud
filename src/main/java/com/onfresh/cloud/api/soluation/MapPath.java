package com.onfresh.cloud.api.soluation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapPath {
    /**
     *  实现 通过回溯算法找到 start 与 end点之间的所有路径 再找出最小值
     * @param n
     * @param edges
     * @param start
     * @param end
     * @return
     */
    int minPath(int n, int[][] edges, int start, int end){

        //记录所有通过的点的集合
        List<Integer> point = new ArrayList<>();
        //记录回溯算法的所有可能路径
        List<List<Integer>> pathPoint = new ArrayList<>();
        path(pathPoint, point, edges, start, end);

        int min = 0;
        for (int i = 0; i < pathPoint.size(); i++){
            int totalPath = 0;
            for(int j = 0; j < pathPoint.get(i).size() - 1; j++)
                totalPath += pointPath(edges, pathPoint.get(i).get(j), pathPoint.get(i).get(j + 1));
            if(min == 0)
                min = totalPath;
            else
                min = Math.min(min, totalPath);
        }
        return min;
    }

    /**
     * 进行回溯算法
     * @param pathPoint 所有可能的路径
     * @param point 当前路径经过的点
     * @param edges 图
     * @param current 当前节点
     * @param end 目标节点
     */
    void path(List<List<Integer>> pathPoint, List<Integer> point, int[][] edges, int current, int end){
        //当已经找到目标节点后 存储路径节点
        if(current == end)
            pathPoint.add(point);

        List<List<Integer>> nextPoint = getNextPoint(edges, current);
        for(int i = 0; i < nextPoint.size(); i++){
            //进行回溯
            path(pathPoint, point, edges, nextPoint.get(i).get(1), end);
            point.remove(nextPoint.get(i).get(1));
        }
    }

    /**
     *  找出与输入的节点所有有关联的节点
     * @param edges
     * @param start 当前节点
     * @return
     */
    List<List<Integer>> getNextPoint(int[][] edges, int start){
        List<List<Integer>> result = new ArrayList<>();
        for(int i = 0; i < edges.length; i++){
            if(edges[i][0] == start) {
                result.add(Arrays.asList(edges[i][0], edges[i][0], edges[i][0]));
            }
        }
        return result;
    }

    /**
     * 寻找点与点之间的路径
     * @param edges
     * @param start
     * @param end
     * @return
     */
    int pointPath(int[][] edges, int start, int end){
        for(int i = 0; i < edges.length; i++){
            if(edges[i][0] == start && edges[i][1] == end) {
                return edges[i][2];
            }
        }
        return 0;
    }
}
