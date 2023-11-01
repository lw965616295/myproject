package com.weil.maze;

/**
 * @Name: MiGong
 * @Description: 迷宫问题
 * @Author: weil
 * @Date: 2023-03-27 10:42
 * @Version: 1.0
 */
public class MiGong {
    /**
     * 0:该点没有走过, 1:表示墙, 2:可以走, 3:该点已经走过,但是走不通\
     * 策略: 下->右->上->左, 如果该点走不通，再回溯
     */
    private int[][] map;
    private int desX;
    private int desY;

    /**
     * 构建 row*col的迷宫
     *
     * @param row 行
     * @param col 列
     */
    public MiGong(int row, int col) {
        if (row <= 0 || col <= 0) {
            return;
        }
        map = new int[row][col];
        // 默认 上下左右 全部为墙
        for (int i = 0; i < col; i++) {
            map[0][i] = 1;
            map[row - 1][i] = 1;
        }
        for (int i = 0; i < row; i++) {
            map[i][0] = 1;
            map[i][col - 1] = 1;
        }
    }

    /**
     * 在迷宫内部添加挡板
     *
     * @param i 横坐标
     * @param j 纵坐标
     */
    public void addBaffle(int i, int j) {
        if (map == null) {
            return;
        }
        // 外面一周都是墙
        if (i > 0 && i < map.length - 1 && j > 0 && j < map[0].length - 1) {
            map[i][j] = 1;
        }
    }

    /**
     * 设置迷宫的终点位置
     *
     * @param desX 横坐标
     * @param desY 纵坐标
     */
    public void setDes(int desX, int desY) {
        this.desX = desX;
        this.desY = desY;
    }

    public boolean setWay(int i, int j) {
        // 通路已经找到
        if (map[desX][desY] == 2) {
            return true;
        } else {
            if (map[i][j] != 0) {
                return false;
            }
            // map[i][j] == 0 按照策略 下->右->上->左 递归
            // 假定该点是可以走通.
            map[i][j] = 2;
            if (setWay(i + 1, j)) {
                return true;
            } else if (setWay(i, j + 1)) {
                return true;
            } else if (setWay(i - 1, j)) {
                return true;
            } else if (setWay(i, j - 1)) {
                return true;
            } else {
                // 说明该点是走不通，是死路
                map[i][j] = 3;
                return false;
            }
        }
    }

    // 显示地图
    public void show() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }


    // 测试方法
    public static void main(String[] args) {
        MiGong miGong = new MiGong(8, 7);
        miGong.addBaffle(3, 1);
        miGong.addBaffle(3, 2);
//        miGong.addBaffle(6, 4);
        miGong.setDes(6, 5); // 设置目的地
        System.out.println("初始地图的情况");
        miGong.show();
        miGong.setWay(1, 1); // 设置起始位置
        System.out.println("小球走过的路径,地图的情况");
        miGong.show();
    }

}
