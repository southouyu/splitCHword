package com.datamodel;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @CLASSNAME splitCHword
 * @Description TODO
 * @Author OUY
 * @Date 2019/9/11 16:08
 **/
public class SplitCHwordUtil {


    public static String splitDetail(String word){
        Set<String> expectedNature = new HashSet<String>(){{
            add("n");add("v");add("vd");add("vn");add("vf");
            add("vx");add("vi");add("vl");add("vg");
            add("nt");add("nz");add("nw");add("nl");
            add("ng");add("userDefine");add("wh");add("ns");
        }};
//        String str = "欢迎使用ansj_seg,(ansj中文分词)在这里如果你遇到什么问题都可以联系我.我一定尽我所能.帮助大家.ansj_seg更快,更准,更自由!" ;
        Result result = ToAnalysis.parse(word);
        StringBuilder stringBuilder = new StringBuilder(" ");
        List<Term> termList = result.getTerms();
        termList.forEach(term->{
            if(expectedNature.contains(term.getNatureStr())){
                stringBuilder.append(term.getName()+" ");
            }
        });
        return  stringBuilder.toString();
    }
}

