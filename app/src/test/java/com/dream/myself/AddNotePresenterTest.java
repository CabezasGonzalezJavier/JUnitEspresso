package com.dream.myself;

/**
 * Created by javiergonzalezcabezas on 11/12/17.
 */

public class AddNotePresenterTest {

    class Solution {

        static int[] pancakeSort(int[] arr) {
            //
            int first = 0;
            int second = 0;
            for(int i=0; i<arr.length-1; i++) {
                first = arr[i];
                second = arr[i+1];
                if(first>second) {
                    arr[i+1] = first;
                    arr[i] = second;
                }
                if(i==arr.length-2) {
                    if(!check(arr)) {
                        pancakeSort(arr);
                    }
                }
            }
            return arr;
        }

        static boolean check(int[] arr) {
            for(int i=0; i<arr.length-1; i++) {
                if(arr[i]>arr[i+1]) {
                    return false;
                }
            }
            return true;
        }

        public static void main(String[] args) {
            int[] arr= new int[args.length];
            for(int i=0; i<args.length; i++) {
                arr[i] = Integer.valueOf(args[i]);
            }
            pancakeSort(arr);
        }

    }
}
}
