package leetcode.ClassicProblem;

import java.net.Inet4Address;
import java.util.*;

// 跳跃游戏总结
public class JumpGameConclusion {
    // 跳跃游戏1
    // 动态规划 可以优化至 用贪心维护最远可到达的距离
    public boolean canJump(int[] nums) {
        int pre;
        int cur;
        pre = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (pre < i) return false;
            cur = Math.max(pre, i + nums[i]);
            if (cur >= nums.length) return true;
            pre = cur;
        }
        return true;
    }


    // 跳跃游戏2 https://leetcode-cn.com/problems/jump-game-ii/
    // 基础动规（O（n^2）） 贪心+动规（O（n）） bfs
    // 做的时候只想到基础动规和bfs,感觉贪心是不正确的

    //基础动规 dp[i]表示到达第i个位置最少跳几次，每次都从0遍历到i-1更新最小跳跃次数 O(n^2)
    public int jump(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = 0;
        for (int i = 1; i < n; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < i; j++) {
                if (j + nums[j] >= i) {
                    if (dp[j] + 1 < min) {
                        min = dp[j] + 1;
                    }
                }
            }
            dp[i] = min;
        }
        return dp[n - 1];
    }

    // 贪心+动规
    public int jump2(int[] nums) {
        int n = nums.length;
        int boundary = 0;
        int maxPosition = 0;
        int step = 0;
        for (int i = 0; i < n - 1; i++) {
            maxPosition = Math.max(maxPosition, i + nums[i]);
            if (i == boundary) {
                boundary = maxPosition;
                step++;
            }
        }
        return step;
    }

    // 贪心+双指针
    public int jump3(int[] nums) {
        int n = nums.length;
        int[] f = new int[n];
        for (int i = 1, j = 0; i < n; i++) {
            while (j + nums[j] < i) j++;
            f[i] = f[j] + 1;
        }
        return f[n - 1];
    }

    // BFS
    // 如果每个点跳跃的距离足够长的话，每次都会将当前点「后面的所有点」进行循环入队操作（由于 st 的存在，不一定都能入队，但是每个点都需要被循环一下）。复杂度为 O(n^2)
    public int jump4(int[] nums) {
        int n = nums.length;
        boolean[] visited = new boolean[n];
        Deque<Integer> d = new ArrayDeque<>();
        d.addLast(0);
        int res = 0;
        while (!d.isEmpty()) {
            int x = d.size();
            while (x-- > 0) {
                int cur = d.poll();
                if (cur == n - 1) return res;
                for (int i = cur + 1; i <= cur + nums[cur] & i < n; i++) {
                    if (!visited[i]) {
                        visited[i] = true;
                        d.offer(i);
                    }
                }
            }
            res++;
        }
        return res;
    }


    // 跳跃游戏3
    // 从向右范围跳跃改为只能跳两个指定位置，所以dp和贪心不行，得用bfs
    public boolean canReach(int[] arr, int start) {
        int n = arr.length;
        boolean[] visited = new boolean[n];
        Deque<Integer> d = new ArrayDeque<>();
        d.addLast(start);
        while (!d.isEmpty()) {
            int x = d.poll();
            if (arr[x] == 0) return true;
            if (x + arr[x] < n && !visited[x + arr[x]]) {
                visited[x + arr[x]] = true;
                d.addLast(x + arr[x]);
            }
            if (x - arr[x] >= 0 && !visited[x - arr[x]]) {
                visited[x - arr[x]] = true;
                d.addLast(x - arr[x]);
            }
        }
        return false;
    }

    // 跳跃游戏4 https://leetcode-cn.com/problems/jump-game-iv/solution/gong-shui-san-xie-noxiang-xin-ke-xue-xi-q9tb1/
    // 可以向左向右走一格或者跳跃到等值的位置
    // 用哈希表存Map<value,list<index>> 等值的位置list转化为图模型再bfs，这点想到了
    // 但是还是要加一个剪枝不然会超时，这个没想到，或者使用哈希表+双向bfs
    public int minJumps(int[] arr) {
        HashMap<Integer, List<Integer>> mp = new HashMap<>();
        //这里也可以改成倒序插入，这样同值的索引，会先找后面的，更有可能接近终点
        for (int i = 0; i < arr.length; i++) {
            List<Integer> cur = mp.getOrDefault(arr[i], new ArrayList<>());
            cur.add(i);
            mp.put(arr[i], cur);
        }
        Deque<Integer> dq = new ArrayDeque<>();
        dq.addLast(0);
        boolean[] visitied = new boolean[arr.length];
        int step = 0;
        while(!dq.isEmpty()) {
            int size = dq.size();
            while(size-->0) {
                int cur = dq.poll();
                if(cur==arr.length-1) return step;
                if(cur+1<arr.length && !visitied[cur+1]) {
                    visitied[cur+1]=true;
                    dq.offer(cur+1);
                }
                if(cur-1>=0 && !visitied[cur-1]) {
                    visitied[cur-1]=true;
                    dq.offer(cur-1);
                }
                List<Integer> equalList = mp.getOrDefault(arr[cur],new ArrayList<>());
                for(int x:equalList) {
                    if(!visitied[x]) {
                        visitied[x] = true;
                        dq.offer(x);
                    }
                }
                //这个剪枝很关键，不加就会在都是7的案例超时
                mp.remove(arr[cur]);
            }
            step++;
        }
        return -1;
    }

    // 跳跃游戏4
    // 双向bfs来优化，顺便复习下双向bfs
    public int minJumps2(int[] arr) {
        HashMap<Integer, List<Integer>> mp = new HashMap<>();
        if(arr.length == 1) return 0;
        //这里也可以改成倒序插入，这样同值的索引，会先找后面的，更有可能接近终点
        for (int i = 0; i < arr.length; i++) {
            List<Integer> cur = mp.getOrDefault(arr[i], new ArrayList<>());
            cur.add(i);
            mp.put(arr[i], cur);
        }
        //初始化两端的bfs队列和距离数组（因为双向bfs还需要访问另一个队列以前经过的点，所以要记录历史距离，不能只用visitied，需要用dist数组来兼具visitied和记录距离的功能）
        Deque<Integer> dq1 = new ArrayDeque<>();
        Deque<Integer> dq2 = new ArrayDeque<>();
        dq1.addLast(0);
        dq2.addLast(arr.length-1);

        int[] dist1 = new int[arr.length];
        Arrays.fill(dist1,Integer.MAX_VALUE/2);
        dist1[0] = 0;
        int[] dist2 = new int[arr.length];
        Arrays.fill(dist2,Integer.MAX_VALUE/2);
        dist2[arr.length-1] = 0;

        while(!dq1.isEmpty()&&!dq2.isEmpty()) {
            int res = -1;
            if(dq1.size()<dq2.size()) res = bfs(dq1,dq2,dist1,dist2,arr,mp);
            else res = bfs(dq2,dq1,dist2,dist1,arr,mp);
            if(res!=-1) return res;
        }
        return -1;
    }

    public int bfs(Deque<Integer> dq1,Deque<Integer> dq2,int[] dist1,int[] dist2,int[] arr,HashMap<Integer, List<Integer>> mp) {
        int len = arr.length;
        int size = dq1.size();
        while(size-->0) {
            int cur = dq1.poll(),curStep = dist1[cur];
            if(cur+1<len) {
                if (dist2[cur + 1] != Integer.MAX_VALUE/2) return curStep + 1 + dist2[cur + 1];
                if (dist1[cur + 1] == Integer.MAX_VALUE/2) {
                    dq1.addLast(cur + 1);
                    dist1[cur + 1] = curStep + 1;
                }
            }

            if(cur-1>=0) {
                if (dist2[cur - 1] != Integer.MAX_VALUE/2) return curStep + 1 + dist2[cur - 1];
                if (dist1[cur - 1] == Integer.MAX_VALUE/2) {
                    dq1.addLast(cur - 1);
                    dist1[cur - 1] = curStep + 1;
                }
            }

            List<Integer> equalList = mp.getOrDefault(arr[cur],new ArrayList<>());
            for(int x:equalList) {
                if(dist2[x]!=Integer.MAX_VALUE/2) return curStep+1+dist2[x];
                if(dist1[x]==Integer.MAX_VALUE/2) {
                    dq1.addLast(x);
                    dist1[x] = curStep+1;
                }
            }
        }
        return -1;
    }


    // 跳跃游戏6
    // 想出dp方程： dp[i]=Max(dp[j])+1    i-k<j<i
    // 所以用单调队列或者优先队列来维护k大小的窗口，记录dp的最大值
    // 将dp与滑动窗口最大值相关联 todo
    public int maxResult(int[] nums, int k) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        // 构造滑动窗口的索引所对应的队列，队首至队尾的索引依次增大，但对应dp数组中的值依次降低
        Deque<Integer> windowIndices = new LinkedList<>();
        windowIndices.offer(0);
        for (int i = 1; i < nums.length; i++) {
            dp[i] = dp[windowIndices.peekFirst()]+nums[i];
            while(!windowIndices.isEmpty() && dp[windowIndices.peekLast()]<=dp[i]) {
                windowIndices.pollLast();
            }
            windowIndices.offerLast(i);
            if(windowIndices.peekFirst()<=i-k) {
                windowIndices.pollFirst();
            }
        }
        return dp[nums.length - 1];
    }

}