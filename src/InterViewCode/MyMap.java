package InterViewCode;

import java.util.Set;

/**
 *@Title: MyMap
 * @Description: 自定义map接口
 * @date: 2019年7月13日 下午3:56:57
 */
public interface MyMap<K,V> {

    /**
     * @Description: 插入键值对方法
     * @param k
     * @param v
     * @return
     *@date: 2019年7月13日 下午3:59:16
     */
    public V put(K k,V v);
    /**
     * @Description:根据key获取value
     * @param k
     * @return
     *@date: 2019年7月13日 下午3:59:40
     */
    public V get(K k);

    /**
     * @Description: 判断key键是否存在
     * @param k  key键
     * @return
     *@date: 2019年7月23日 下午4:07:22
     */
    public boolean containsKey(K k);


    /**
     * @Description: 获取map集合中所有的key，并放入set集合中
     * @return
     *@date: 2019年7月23日 下午4:24:19
     */
    public Set<K>  keySet();





//------------------------------内部接口 Entry（存放key-value）---------------------

    /**
     * @Title: Enter
     * @Description: 定义内部接口 Entry，存放键值对的Entery接口
     * @date: 2019年7月13日 下午4:00:33
     */
    interface Entry<K,V>{
        /**
         * @Description: 获取key方法
         * @return
         *@date: 2019年7月13日 下午4:02:06
         */
        public K getKey();
        /**
         * @Description:获取value方法
         * @return
         *@date: 2019年7月13日 下午4:02:10
         */
        public V getValue();
    }

}