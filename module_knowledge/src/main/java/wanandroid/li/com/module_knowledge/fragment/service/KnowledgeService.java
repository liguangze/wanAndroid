package wanandroid.li.com.module_knowledge.fragment.service;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import wanandroid.li.com.module_knowledge.fragment.bean.KnowledgeBean;

/**
 * Created by liguangze on 2019/3/18.
 */

public interface KnowledgeService {



    @GET("/tree/json")
    Observable<List<KnowledgeBean>> getKnowledgeData();
}
