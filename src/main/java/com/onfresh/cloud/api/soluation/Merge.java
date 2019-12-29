package com.onfresh.cloud.api.soluation;

import java.util.ArrayList;
import java.util.List;

public class Merge {

    public static void main(String[] args) {
//        int[][] intervals = {{1,3},{2,6},{8,10},{15,18}};
//        int[][] intervals = {{1,4},{4,5}};
//        int[][] intervals = {{1,3},{2,6},{8,10},{15,18}, {18,20}};
        int[][] intervals = {};
        Merge merge = new Merge();
        int[][] merge1 = merge.merge(intervals);
        System.out.println(merge1);
    }

    int[][] merge(int[][] intervals){
        List<List<Integer>> result = new ArrayList<>();
        if(intervals.length == 0)
            return new int[0][0];
        int start = intervals[0][0];
        int end = intervals[0][1];
        for(int i = 0; i < intervals.length - 1; i++){
            if(Math.max(intervals[i][1], intervals[i + 1][1]) - Math.min(intervals[i][0], intervals[i + 1][0])
            <= ((intervals[i][1] - intervals[i][0]) + (intervals[i + 1][1] - intervals[i + 1][0]))){
                //表示第i与第i+1区间是可合并的；
                start = Math.min(intervals[i][0], intervals[i + 1][0]);
                end = Math.max(intervals[i][1], intervals[i + 1][1]);
            } else {
                addToList(result, start, end);
                start = intervals[i + 1][0];
                end = intervals[i + 1][1];
            }
        }
        if(end < intervals[intervals.length - 1][0])
            addToList(result, intervals[intervals.length - 1][0], intervals[intervals.length - 1][1]);
        else
            addToList(result, start, end);
        return transfer(result);
    }

    //将数组按照起始添加到list
    List<List<Integer>> addToList(List<List<Integer>> list, int start, int end){
        list.add(new ArrayList<Integer>(){{
            add(start);
            add(end);
        }});
        return list;
    }

    int[][] transfer(List<List<Integer>> intervals){
        int[][] result = new int[intervals.size()][2];
        for(int i = 0; i < intervals.size(); i++){
            result[i][0] = intervals.get(i).get(0);
            result[i][1] = intervals.get(i).get(1);
        }
        return result;
    }
}
