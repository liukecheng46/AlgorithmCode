import aJianZhiOffer.*;
import leetcode.BFS.MultiSourceBFS;
import leetcode.BinarySearch.SearchRotateArray;
import leetcode.BinarySearch.binarySearchAnswerRange;
import leetcode.BinarySearch.findNearstK;
import leetcode.ClassicProblem.*;
import leetcode.LinkedList.addTwoNumberInLinkedList;
import leetcode.StringAndRegex.decodeString;
import leetcode.dfsBrackTracePrune.*;
import leetcode.panCake;
import leetcode.rotate;

import java.util.Arrays;
import java.util.Scanner;

public class debug {
    public static void main(String[] args) {
//     MaxQueue obj = new MaxQueue();
//     int param_1 = obj.max_value();
//     System.out.println(param_1);
//     obj.push_back(3);
//     int param_3 = obj.pop_front();
//    System.out.println(param_3);
//    obj.push_back(4);
//    System.out.println(obj.max_value());


//        ListNodeMerge.ListNode node1 = new ListNodeMerge.ListNode (1);
//        node1.next = new ListNodeMerge.ListNode (3);
//        ListNodeMerge.ListNode node2 = new ListNodeMerge.ListNode (2);
//        node2.next = new ListNodeMerge.ListNode (4);
//        ListNodeMerge merge = new ListNodeMerge();
//        ListNodeMerge.ListNode[] lists = new ListNodeMerge.ListNode[] {node1,node2};
//        ListNodeMerge.ListNode result = merge.mergeKListsRecurse(lists,0,1);
//        System.out.println(result.val);

//        int[] houses = new int[] {1};
//        int[] heaters = new int[] {2};
//        maxAppleToEat.countNum(houses,heaters);
//        int[] nums = new int[]{5, 2, 3, 1,-2,4,2,};
////        mergeSort s = new mergeSort();
////        System.out.println(Arrays.toString(s.sortIterate(nums)));
//        quickSelect s = new quickSelect();
////        System.out.println(s.quickSelect(nums,2));
//        System.out.println(Arrays.toString(s.quickPartition2(nums, 5, 0, nums.length - 1)));

//        sortList s = new sortList();
//        sortList.ListNode head5 = s.new ListNode(0);
//        sortList.ListNode head4 = s.new ListNode(4,head5);
//        sortList.ListNode head3 = s.new ListNode(3,head4);
//        sortList.ListNode head2 = s.new ListNode(5,head3);
//        sortList.ListNode head = s.new ListNode(-1,head2);
//        sortList.ListNode newHead = s.mergeSortListIterative(head);
//        while(newHead!=null) {
//            System.out.println(newHead.val);
//            newHead=newHead.next;
//        }
//        int[][] nums = new int[][] {{2,1,1},{2,3,1},{3,4,1}};
//        Dijkstra s = new Dijkstra();
//        System.out.println(s.networkDelayTime2(nums,4,2));
//        topVoteCandidate topVotedCandidate = new topVoteCandidate(new int[] {0, 1, 2, 2, 1 },new int[]  {20, 28, 29, 54, 56});
//        System.out.println(topVotedCandidate.q(57));
//        System.out.println(topVotedCandidate.q(29));
//        System.out.println(topVotedCandidate.q(29));
//        System.out.println(Arrays.toString(topVotedCandidate.topCandidate));

//        int[] nums = {2,4,-2,-3};
//        int[] nums = {4,3,2,3,5,2,1};
//        nums = Arrays.stream(nums).boxed().sorted(Comparator.reverseOrder()).mapToInt(Integer::intValue).toArray();
//        System.out.println(Arrays.stream(nums).sum()/5);
//        PartitionKEqualSubsets p = new PartitionKEqualSubsets();
//        System.out.println(p.PartitionKSubsets(nums,4));
//        IncreasingTripleLet_LIS_Mutant s = new IncreasingTripleLet_LIS_Mutant();
//        System.out.println(s.increasingTriplet(nums));
//        int[] nums= {100,-23,-23,404,100,23,23,23,3,404};
//        ShipWithinDays s = new ShipWithinDays();
//        System.out.println(s.shipWithinDays(nums,5));
//        minWindow s = new minWindow();
//        System.out.println(s.minWindows("a","aa"));
//        MaxSlidingWindow s = new MaxSlidingWindow();
//        System.out.println(Arrays.toString(s.maxSlidingWindow(nums, 3)));
//        largestRectangleArea s = new largestRectangleArea();
//        s.largestRectangleArea(nums);
//        hammingDistance s = new hammingDistance();
//        System.out.println(s.hammingDistance(1,4));
//        removeKdigits s = new removeKdigits();
//        System.out.println(s.removeKdigits("10",2));
//        twoSumConclusion s = new twoSumConclusion();
//        System.out.println( s.numberOfSubarrays(nums,3));
//        getSum s = new getSum();
//        s.getSum(-12,-8);
//        JumpGameConclusion s  =new JumpGameConclusion();
//        System.out.println(s.minJumps2(nums));
//        String s ="ss  s ssss sss  sss   ss";
//        System.out.println(s.split("\s+")[2]);
//        catalanNumber s = new catalanNumber();
//        System.out.println(s.generateParenthesis(3));
//        SubSet s = new SubSet();
//        System.out.println(s.subsets(new int[] {1,2,2}));
//        PartitionPalindrome s  = new PartitionPalindrome();
////        System.out.println(s.partition("a"));
//        combinationSum s = new combinationSum();
////        System.out.println(s.combine(1,1));
//        Premute s = new Premute();
//        System.out.println(s.letterCasePermutation("a1b2"));
//        GoldMiner s = new GoldMiner();
//        System.out.println(s.getMaximumGold(new int[][] {{0,6,0},{5,8,7},{0,9,0}}));

//        addTwoNumberInLinkedList s  =new addTwoNumberInLinkedList();
//        addTwoNumberInLinkedList.ListNode tail = new addTwoNumberInLinkedList.ListNode(5);
//        addTwoNumberInLinkedList.ListNode forth = new addTwoNumberInLinkedList.ListNode(4,tail);
//        addTwoNumberInLinkedList.ListNode third = new addTwoNumberInLinkedList.ListNode(3,forth);
//        addTwoNumberInLinkedList.ListNode second = new addTwoNumberInLinkedList.ListNode(2,third);
//        addTwoNumberInLinkedList.ListNode head = new addTwoNumberInLinkedList.ListNode(1,second);
//
//        s.reverseKGroup(head,2);

//        ArrayReserveConclusion s = new ArrayReserveConclusion();
//        System.out.println(s.removeElement(new int[] {0,1,0,3,12},0));
//        reconstructQueue s = new reconstructQueue();
//        System.out.println(Arrays.deepToString(s.reconstructQueue(new int[][]{{7, 0}, {4, 4}, {7, 1}, {5, 0}, {6, 1}, {5, 2}})));
//        MonotoneStackConclusion s=  new MonotoneStackConclusion();
//        System.out.println(Arrays.toString(s.dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})));
//
//        MultiSourceBFS s= new MultiSourceBFS();
//        System.out.println(s.numEnclaves(new int[][] {{1,1,1,0},{1,0,0,0},{0,1,1,0},{0,0,0,0}}));

//        decodeString s = new decodeString();
//        System.out.println(s.decodeString("3[a2[c]]"));

//        chuoqiqiu s = new chuoqiqiu();
//        System.out.println(s.maxCoins(new int[] {3,1,5,8}));

//        findNearstK s  =new findNearstK();
//        System.out.println(s.findClosestElements(new int[] {1},4,3));

//        J021_exchange s = new J021_exchange();
//
//        System.out.println(Arrays.toString(s.exchange(new int[]{2,16,3,5,13,1,16,1,12,18,11,8,11,11,5,1})));
//
//        J037_SerializeTree s = new J037_SerializeTree();
//        J037_SerializeTree.TreeNode root = new J037_SerializeTree.TreeNode(1);
//        root.left =  new J037_SerializeTree.TreeNode(2);
//        root.right =  new J037_SerializeTree.TreeNode(3);
////        System.out.println(s.serialize(root));
//
//        J065_add s = new J065_add();
//        System.out.println(s.add(3,41));
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int full = sc.nextInt();
            int avg = sc.nextInt();
            int[][] nums = new int[n][2];
            for (int i = 0; i < n; i++) {
                nums[i][0] = sc.nextInt();
                nums[i][1] = sc.nextInt();
            }
            //假定不会出现拿不到奖学金的情况
            if (n == 1) {
                System.out.println((avg - nums[0][0]) * nums[0][1]);
                continue;
            }
            Arrays.sort(nums, (o1, o2) -> o1[1] - o2[1]);//按复习代价从小到大排序
            long sum = 0;
            for (int[] a : nums) {
                sum += a[0];
            }
            long limit = avg * n;
            int index = 0;
            long time = 0;
            while (sum < limit) {
                int tmp = full - nums[index][0];
                //如果一门课程复习到满分，小于限制
                if (tmp + sum <= limit) {
                    time += tmp * nums[index][1];
                    sum += tmp;
                    index++;
                }
                //如果一门课程复习到满分，大于限制
                else {
                    time += (limit - sum) * nums[index][1];
                    sum = limit;
                }
            }
            // 输出描述:
            // 一行输出答案。
            // 输出
            // 43
            System.out.println(time);
        }
    }
}

