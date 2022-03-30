package com.itmuch.contentcenter.configuration;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;

import java.util.List;


/**基于权重的负载均衡算法
 * @author 何林冲
 */
public class ExtendBalancer extends Balancer {
    public static Instance getHostByRandomWeight2(List<Instance> host){
        return getHostByRandomWeight(host);
    }
}
