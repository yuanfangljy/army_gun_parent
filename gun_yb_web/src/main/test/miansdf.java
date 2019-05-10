/**
 * @项目名称：
 * @类名称：
 * @类描述：
 * @创建人：liujiayi
 * @创建时间：2018/12/3 18:36
 * @修改时间：2018/12/3 18:36
 * @version：1.0
 */
public class miansdf {
    public static void main(String[] args) {
        int a [] = new int[20];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }

        int n = 4 ;//列
        int m = a.length/4;//行
        int[][] b = new int[m][n];
        int num = -1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                num++;
                b[i][j] = a[num];
            }
        }


        System.out.println("转换后的二维数组是：");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(b[i][j] + " ");
            }
            System.out.println();
        }
    }
}
