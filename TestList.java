package Test;

import avro.shaded.com.google.common.collect.Lists;

import java.util.List;

/**
 * @program: ad-flink
 * @description:
 * @author: joshua.Wang
 * @create: 2019-11-13 08:41
 **/
public class TestList {

    /**
     * 找到有重复元素的有序数组的某个值第一次出现的位置
     * <p>
     * 直接方式是二分法先找到target,确定target在数组中存在后，再向前找第一次出现
     * <p>
     * 本方式是直接在寻找时判断target的前一个元素与target不同时，直接返回位置即为第一次出现元素（待验证）
     */
    public static int findPosition(int[] nums, int target) {

        int mid;
        int first = 1;
        int end = nums.length;
        if (first == end && nums[0] == target) {
            return 1;
        }

        //此处需加上等于！！否则在两个数的情况下，无法判断第二个数
        while (first <= end) {
            mid = (first + end) / 2;
            if (nums[mid - 1] < target) {
                //此处一定要赋值为mid的后一个，否则会一直死循环无法跳出！！
                first = mid + 1;
            } else if (nums[mid - 1] > target) {
                //同上，找上一个
                end = mid - 1;
            } else {
                //若只有两个数，mid与前一个相等，取第一个
                if (mid == first) {
                    return first;
                }
                //如果mid之前的数比mid小，说明mid为第一个
                else if (nums[mid - 2] < target) {
                    return mid;
                }
                //如果相等，重设end
                else {
                    end = mid - 1;
                }
            }
        }
        return -1;
    }

    public static List<Integer> remove() {
        List<Integer> list1 = Lists.newArrayList(1, 2, 3, 4);
        list1.remove(2);
        list1.add(5);
        System.out.println(list1);
        return list1;
    }

    public static String quickSort(int[] list) {
        int low = 0;
        int high = list.length - 1;
        quickSort1(list, low, high);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.length; i++) {
            sb.append(list[i]).append(" ");
        }
        return sb.toString();
    }

    private static void quickSort1(int[] list, int low, int high) {
        //TODO 需要结束条件！！！
        if (low > high) {
            return;
        }
        int i = low;
        int j = high;
        int temp = list[low];
        int t;
        while (i < j) {
            //左右各找到不满足条件的数
            while (list[j] >= temp && i < j) {
                j--;
            }
            while (list[i] <= temp && i < j) {
                i++;
            }
            //换位
            if (i < j) {
                t = list[i];
                list[i] = list[j];
                list[j] = t;
            }
        }
        //将哨兵换到中间位置
        list[low] = list[i];
        list[i] = temp;
        //对哨兵左右两边分别做快排
        quickSort1(list, low, i - 1);
        quickSort1(list, i + 1, high);
    }

    /**
     * 堆排序
     */
    public static String heapSort(int[] list) {
        for (int i = list.length / 2; i > 0; i--) {
            heapAdjust(list, i, list.length);
        }
        for (int i = list.length - 1; i > 0; i--) {
            int temp = list[i];
            list[i] = list[0];
            list[0] = temp;
            heapAdjust(list, 0, i);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.length; i++) {
            sb.append(list[i]).append(" ");
        }
        return sb.toString();
    }

    //check每一个结点是否都满足堆结构
    private static void heapAdjust(int[] list, int i, int length) {
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        //默认父节点最大
        int largest = i;
        if (l < length && list[l] > list[largest]) {
            largest = l;
        }
        if (r < length && list[r] > list[largest]) {
            largest = r;
        }
        if (largest != i) {
            int temp = list[i];
            list[i] = list[largest];
            list[largest] = temp;
            //若有变动，则check是否需要继续向下替换
            heapAdjust(list, largest, length);
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 6, 6, 6, 6, 6, 6, 6, 78, 78, 1000};
        int[] nums2 = {10, 7, 2, 4, 7, 62, 3, 4, 2, 1, 8, 9, 19};
        //System.out.println(findPosition(nums, 6));
        //System.out.println(remove());
        System.out.println(quickSort(nums2));


    }
}
