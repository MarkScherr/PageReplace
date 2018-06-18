/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pagereplace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author mrsch
 */
public class PageReplacement {

    public static void main(String args[]) throws IOException {
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        int[] pages = {0, 7, 0, 1, 2, 0, 8, 9, 0, 3, 0, 4, 5, 6, 7, 0, 8, 9, 1, 2, 3, 4, 5, 9, 7, 8, 5, 6, 0, 3};
        int numberOfFrames, page, selection, pageFault = 0, frameChanges = 0;

        boolean flag;

        // sets a menu to chose which algorithm to run
        System.out.println("Menu");
        System.out.println("1.FIFO");
        System.out.println("2.LRU");
        System.out.println("3.Optimal Replacement");
        System.out.println("4.EXIT");
        System.out.println("ENTER YOUR CHOICE: ");
        selection = Integer.parseInt(obj.readLine());

        switch (selection) {
            // case 1 runs the FIFO algorithm 
            case 1:
                int pt = 0;

                System.out.println("enter no. of frames: ");
                numberOfFrames = Integer.parseInt(obj.readLine());

                int frame[] = new int[numberOfFrames];

                for (int i = 0; i < numberOfFrames; i++) {
                    frame[i] = -1;
                }

                do {

                    for (int x = 0; x < 30; x++) {
                        page = pages[x];
                        flag = true;

                        for (int j = 0; j < numberOfFrames; j++) {
                            if (page == frame[j]) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            frame[pt] = page;
                            pt++;

                            if (pt == numberOfFrames) {
                                pt = 0;
                            }

                            System.out.print("frame: ");

                            for (int j = 0; j < numberOfFrames; j++) {
                                System.out.print(frame[j] + "   ");
                            }
                            System.out.println();

                            pageFault++;
                        } else {
                            System.out.print("frame: ");

                            for (int j = 0; j < numberOfFrames; j++) {
                                System.out.print(frame[j] + "  ");
                            }
                            System.out.println();
                        }

                        frameChanges++;
                    }
                } while (frameChanges != 30);
                System.out.println("Page fault:" + pageFault);

                break;
            //case 2 runs the LRU algorithm
            case 2:

                int k = 0;

                System.out.println("enter no. of frames: ");
                numberOfFrames = Integer.parseInt(obj.readLine());

                int frame1[] = new int[numberOfFrames];
                int a[] = new int[numberOfFrames];
                int b[] = new int[numberOfFrames];

                for (int i = 0; i < numberOfFrames; i++) {
                    frame1[i] = -1;
                    a[i] = -1;
                    b[i] = -1;
                }

                do {
                    for (int pagePointer = 0; pagePointer < 30; pagePointer++) {
                        page = pages[pagePointer];
                        flag = true;
                        for (int j = 0; j < numberOfFrames; j++) {

                            if (page == frame1[j]) {
                                flag = false;
                                break;
                            }
                        }

                        for (int j = 0; j < numberOfFrames && flag; j++) {
                            if (frame1[j] == a[numberOfFrames - 1]) {
                                k = j;
                                break;
                            }
                        }

                        if (flag) {
                            frame1[k] = page;
                            System.out.println("frame: ");

                            for (int j = 0; j < numberOfFrames; j++) {
                                System.out.print(frame1[j] + "  ");
                            }
                            pageFault++;
                            System.out.println();
                        } else {
                            System.out.println("frame: ");

                            for (int j = 0; j < numberOfFrames; j++) {
                                System.out.print(frame1[j] + "  ");
                            }
                            System.out.println();
                        }

                        int p = 1;
                        b[0] = page;

                        for (int j = 0; j < a.length; j++) {
                            if (page != a[j] && p < numberOfFrames) {
                                b[p] = a[j];
                                p++;
                            }
                        }

                        System.arraycopy(b, 0, a, 0, numberOfFrames);

                        frameChanges++;

                    }
                } while (frameChanges != 30);
                System.out.println("Page fault:" + pageFault);
                break;

            //case 3 runs the Optimal Replacement algorithm
            case 3:
                System.out.println("enter no. of frames: ");
                numberOfFrames = Integer.parseInt(obj.readLine());

                do {

                    int[] buffer = new int[numberOfFrames];
                    int[] index = new int[numberOfFrames];
                    int pointer = 0;
                    boolean isFull = false;

                    for (int j = 0; j < numberOfFrames; j++) {
                        buffer[j] = -1;
                    }

                    for (int i = 0; i < 30; i++) {
                        int search = -1;

                        for (int j = 0; j < numberOfFrames; j++) {

                            if (buffer[j] == pages[i]) {
                                search = j;
                                frameChanges++;
                                break;
                            }
                        }

                        if (search == -1) {
                            if (isFull) {

                                boolean index_flag[] = new boolean[numberOfFrames];

                                for (int j = i + 1; j < 30; j++) {
                                    for (int l = 0; l < numberOfFrames; l++) {
                                        if ((pages[j] == buffer[l]) && (index_flag[l] == false)) {
                                            index[l] = j;
                                            index_flag[l] = true;
                                            break;
                                        }
                                    }
                                }

                                int max = index[0];
                                pointer = 0;
                                if (max == 0) {
                                    max = 200;
                                }
                                for (int j = 0; j < numberOfFrames; j++) {
                                    if (index[j] == 0) {
                                        index[j] = 200;
                                    }
                                    if (index[j] > max) {
                                        max = index[j];
                                        pointer = j;
                                    }
                                }
                            }

                            buffer[pointer] = pages[i];
                            frameChanges++;
                            pageFault++;

                            if (!isFull) {
                                pointer++;

                                if (pointer == numberOfFrames) {
                                    pointer = 0;
                                    isFull = true;
                                }
                            }
                        }
                        System.out.println("frame: ");
                        for (int j = 0; j < numberOfFrames; j++) {
                            System.out.print(buffer[j] + "  ");
                        }
                        System.out.println();
                    }

                } while (frameChanges != 30);

                System.out.println("Page fault:" + pageFault);
                break;

            case 4:
                break;
        }
    }

}