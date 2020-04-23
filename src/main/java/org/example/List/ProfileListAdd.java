package org.example.List;


import org.example.List.Profiler.Timeable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jfree.data.xy.XYSeries;

public class ProfileListAdd {
    public ProfileListAdd() {
    }

    public static void main(String[] args) {
        profileArrayListAddEnd();
        //profileArrayListAddBeginning();

        profileLinkedListAddEnd();
        //profileLinkedListAddBeginning();

        //List에 대한 알고리즘 선택은 데이터가 클때 중요하다. 데이터가 작으면 크게 의미가 없음
        //ArrayList - get/set이 많은 연산인경우 좋음
        //          - 아예 참조가 끊어져야 garbage collect 됨
        //          - 메모리가 나란히 저장되어 낭비되는 공간이 없음
       //LinkedList - add/remove가 많이 연산인 경우 좋음
        //           - 사용되지 않는 node는 garbage collect 됨
        //           - 노드가 메모리 여기저기에 흩어져 있을 수 있어서 하드웨어 효율이 떨어질수 있음
    }


    public static void profileArrayListAddEnd() {
        Timeable timeable = new Timeable() {
            List<String> list;

            public void setup(int n) {
                this.list = new ArrayList();
            }

            public void timeMe(int n) {
                for (int i = 0; i < n; ++i) {
                    this.list.add("a string");
                }

            }
        };
        int startN = 4000; //시간 측정을 시작하는 n 값
        int endMillis = 1000; // 임계치
        runProfiler("profileArrayListAddEnd", timeable, startN, endMillis);
    }

    public static void profileArrayListAddBeginning() {
        Timeable timeable = new Timeable() {
            List<String> list;

            public void setup(int n) {
                this.list = new ArrayList();
            }

            public void timeMe(int n) {
                for (int i = 0; i < n; ++i) {
                    this.list.add(0, "a string");
                }

            }
        };
        int startN = 4000;
        int endMillis = 10000;
        runProfiler("profileArrayListAddBeginning", timeable, startN, endMillis);
    }

    public static void profileLinkedListAddBeginning() {
        Timeable timeable = new Timeable() {
            List<String> list;

            public void setup(int n) {
                this.list = new LinkedList<>();
            }

            public void timeMe(int n) {
                for (int i = 0; i < n; ++i) {
                    this.list.add(0, "a string");
                }

            }
        };
        int startN = 128000;
        int endMillis = 2000;
        runProfiler("profileLinkedListAddBeginning", timeable, startN, endMillis);
    }

    public static void profileLinkedListAddEnd() {
        Timeable timeable = new Timeable() {
            List<String> list;

            public void setup(int n) {
                this.list = new LinkedList<>();
            }

            public void timeMe(int n) {
                for (int i = 0; i < n; ++i) {
                    this.list.add("a string");
                }

            }
        };
        int startN = 64000;
        int endMillis = 1000;
        runProfiler("profileLinkedListAddEnd", timeable, startN, endMillis);
    }

    private static void runProfiler(String title, Timeable timeable, int startN, int endMillis) {
        Profiler profiler = new Profiler(title, timeable);
        XYSeries series = profiler.timingLoop(startN, endMillis);
        profiler.plotResults(series);
    }
}

