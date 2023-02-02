package com;

import com.hero.herocat.HeroCat;

/**
 * 规范：你写的Servlet必须在指定目录：com\hero\webapp
 * UserServlet 实现了doGet 和 doPost
 * http://localhost:8080/xx/dd/UserServlet?name=sxx
 */
public class UserApplication {
    public static void main(String[] args) throws Exception {
        HeroCat.run(args);//启动HeroCat
    }
}
