package InterViewCode;

import java.util.Random;

//leetcode手撕跳表 https://leetcode-cn.com/problems/design-skiplist/solution/javashou-xie-shi-xian-tiao-biao-by-feng-omdm0/
// 标准写法其实应该Node带right和down节点，但是这种写法会开很多个重复Node的空间（值为5的Node在第六层，那么第一到第六层都要有一个值为5的Node节点）
// 下面的解法参考的redis跳表实现，每个节点Node指向的是他的高度的所有下一个节点（可能有点难理解，看这个 https://leetcode-cn.com/problems/design-skiplist/solution/javashou-xie-shi-xian-tiao-biao-by-feng-omdm0/）
// 这种写法能避免开重复Node的空间，只用多存引用就可以了
public class SkipList {
    class Skiplist {
        /**
         * 最大层数
         */
        private static int DEFAULT_MAX_LEVEL = 32;
        /**
         * 随机层数概率，也就是随机出的层数，在 第1层以上(不包括第一层)的概率，层数不超过maxLevel，层数的起始号为1
         */
        private static double DEFAULT_P_FACTOR = 0.25;

        Node head = new Node(null,DEFAULT_MAX_LEVEL); //头节点

        int currentLevel = 1; //表示当前nodes的实际层数，它从1开始


        public Skiplist() {
        }

        public boolean search(int target) {
            Node searchNode = head;
            for (int i = currentLevel-1; i >=0; i--) {
                searchNode = findClosest(searchNode, i, target);
                if (searchNode.next[i]!=null && searchNode.next[i].value == target){
                    return true;
                }
            }
            return false;
        }

        /**
         *
         * @param num
         */
        public void add(int num) {
            int level = randomLevel();
            Node updateNode = head;
            Node newNode = new Node(num,level);
            // 计算出当前num 索引的实际层数，从该层开始添加索引
            for (int i = currentLevel-1; i>=0; i--) {
                //找到本层最近离num最近的list
                updateNode = findClosest(updateNode,i,num);
                if (i<level){
                    if (updateNode.next[i]==null){
                        updateNode.next[i] = newNode;
                    }else{
                        Node temp = updateNode.next[i];
                        updateNode.next[i] = newNode;
                        newNode.next[i] = temp;
                    }
                }
            }
            if (level > currentLevel){ //如果随机出来的层数比当前的层数还大，那么超过currentLevel的head 直接指向newNode
                for (int i = currentLevel; i < level; i++) {
                    head.next[i] = newNode;
                }
                currentLevel = level;
            }

        }

        public boolean erase(int num) {
            boolean flag = false;
            Node searchNode = head;
            for (int i = currentLevel-1; i >=0; i--) {
                searchNode = findClosest(searchNode, i, num);
                if (searchNode.next[i]!=null && searchNode.next[i].value == num){
                    //找到该层中该节点
                    searchNode.next[i] = searchNode.next[i].next[i];
                    flag = true;
                    continue;
                }
            }
            return flag;
        }

        /**
         * 找到level层 value 大于node 的节点
         * @param node
         * @param levelIndex
         * @param value
         * @return
         */
        private Node findClosest(Node node,int levelIndex,int value){
            while ((node.next[levelIndex])!=null && value >node.next[levelIndex].value){
                node = node.next[levelIndex];
            }
            return node;
        }


        /**
         * 随机一个层数
         */
        private static int randomLevel(){
            int level = 1;
            while (Math.random()<DEFAULT_P_FACTOR && level<DEFAULT_MAX_LEVEL){
                level ++ ;
            }
            return level;
        }


        class Node{
            Integer value;
            Node[] next;

            public Node(Integer value,int size) {
                this.value = value;
                this.next = new Node[size];
            }
        }

    }
}
